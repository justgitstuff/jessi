<?php
session_start(); 
include 'db_open.php';

//@date_default_timezone_set("America/Mexico_City");

function isDate($i_sDate)
{
  $blnValid = TRUE;
   // check the format first (may not be necessary as we use checkdate() below)
   if(!ereg ("^[0-9]{4}-[0-9]{2}-[0-9]{2}$", $i_sDate))
   {
    $blnValid = FALSE;
   }
   else //format is okay, check that days, months, years are okay
   {
      $arrDate = explode("-", $i_sDate); // break up date by slash
      $intDay = $arrDate[2];
      $intMonth = $arrDate[1];
      $intYear = $arrDate[0];
 
      $intIsDate = checkdate($intMonth, $intDay, $intYear);
   
     if(!$intIsDate)
     {
        $blnValid = FALSE;
     }
 
   }//end else
   
   return ($blnValid);
} //end function isDate

if ($_GET["type"] == "login"){

	$adusername = "admin";
	$adpassword = "access";

	$sql="SELECT * FROM admin WHERE admin_user='$adusername' and admin_pass='$adpassword'";
	$result = mysql_query($sql);

	$count = mysql_num_rows($result);
	if($count>=1){
		$_SESSION['adusername'] = $adusername;
		$_SESSION['adpassword'] = $adpassword;
		header("location:menu.php");
	}	

} else if ($_GET["type"] == "logout"){

	session_destroy();
	header("location:index.php");
	
} else if ($_GET["type"] == "alumno_add"){

	if (empty($_POST["Alumno_Matricula"]) || empty($_POST["Alumno_Nombre"])){
		header("location:alumnos_edit.php?error=blank");
	} else {
		$sql = "INSERT INTO alumno (Alumno_Matricula,Alumno_Nombre,Alumno_Carrera,Alumno_Semestre) ".
		"VALUES ('$_POST[Alumno_Matricula]','$_POST[Alumno_Nombre]','$_POST[Carrera_Id]','$_POST[Alumno_Semestre]')";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
 		header("location:alumnos.php?success=add");
	}

} else if ($_GET["type"] == "alumno_mod"){

	if (empty($_POST["Alumno_Matricula"]) || empty($_POST["Alumno_Nombre"])){
		header("location:alumnos_edit.php?type=mod&id=".$_GET["id"]."&error=blank");
	} else {
		$sql = "UPDATE alumno SET Alumno_Matricula='$_POST[Alumno_Matricula]', Alumno_Nombre='$_POST[Alumno_Nombre]', Alumno_Carrera='$_POST[Carrera_Id]', Alumno_Semestre='$_POST[Alumno_Semestre]' WHERE Alumno_Id='".$_GET['id']."'";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
 		header("location:alumnos.php?success=mod");
	}

} else if ($_GET["type"] == "alumno_del"){

	$sql = "DELETE FROM alumno WHERE Alumno_Id='".$_GET['id']."'";
	if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
	header("location:alumnos.php?success=del");
}


include 'db_close.php';
?> 
