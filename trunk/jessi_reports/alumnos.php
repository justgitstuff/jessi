<?php
session_start();
include 'db_open.php';
?> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
	<script type="text/javascript">

	/***********************************************
	* Highlight Table Cells Script- © Dynamic Drive DHTML code library (www.dynamicdrive.com)
	* Visit http://www.dynamicDrive.com for hundreds of DHTML scripts
	* This notice must stay intact for legal use
	***********************************************/

	//Specify highlight behavior. "TD" to highlight table cells, "TR" to highlight the entire row:
	var highlightbehavior="TR"

	var ns6=document.getElementById&&!document.all
	var ie=document.all

	function changeto(e,highlightcolor){
	source=ie? event.srcElement : e.target
	if (source.tagName=="TABLE")
	return
	while(source.tagName!=highlightbehavior && source.tagName!="HTML")
	source=ns6? source.parentNode : source.parentElement
	if (source.style.backgroundColor!=highlightcolor&&source.id!="ignore")
	source.style.backgroundColor=highlightcolor
	}

	function contains_ns6(master, slave) { //check if slave is contained by master
	while (slave.parentNode)
	if ((slave = slave.parentNode) == master)
	return true;
	return false;
	}

	function changeback(e,originalcolor){
	if (ie&&(event.fromElement.contains(event.toElement)||source.contains(event.toElement)||source.id=="ignore")||source.tagName=="TABLE")
	return
	else if (ns6&&(contains_ns6(source, e.relatedTarget)||source.id=="ignore"))
	return
	if (ie&&event.toElement!=source||ns6&&e.relatedTarget!=source)
	source.style.backgroundColor=originalcolor
	}

	</script>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>ITESM - Sistema de Calendarización</title>
<link href="css/admin.css" rel="stylesheet" type="text/css" />
</head>
<body>
	
	<?php
	$sql="SELECT * FROM admin WHERE admin_user='".@$_SESSION['adusername']."' AND admin_pass='".@$_SESSION['adpassword']."'";
	$result = mysql_query($sql);
	$count = mysql_num_rows($result);
	if($count>=1){
	?>

	<table align="center" class="AdminSystem" width="400" border="0" cellspacing="0" cellpadding="0">
    <tr height="50"><td></td></tr>
    <td rowspan="4"><img src="images/JessiLogoWeb.png"></td>
    <tr><td><b><font color="#006699">SISTEMA DE CALENDARIZACIÓN</font></b></td></tr>
    <tr height="20"><td></td></tr>
    <tr><td><b><font color="#000000">[ REPORTES - ALUMNOS ]</font></b></td></tr>
    <tr height="50"><td></td></tr>
    <tr><td>
	</table>
            <?php
			$query  = "SELECT * FROM alumno";
			$result = mysql_query($query);
			$count=mysql_num_rows($result);
			if($count>=1){
			echo "<table onMouseover=\"changeto(event, 'lightblue')\" onMouseout=\"changeback(event, 'white')\" align='center' width='900' border='0'>";
			echo "<tr id=\"ignore\"><td><b>MATRÍCULA</b></td><td><b>NOMBRE</b></td><td><b>CARRERA</b></td><td><b>SEMESTRE</b></td><td></td><td></td><td></td></tr>";			
			while ($row = @mysql_fetch_array($result, MYSQL_ASSOC)) {
				echo "<tr><td>".@$row['Alumno_Matricula']."</td><td>".@$row['Alumno_Nombre']."</td><td>".@$row['Alumno_Carrera']."</td><td>".@$row['Alumno_Semestre']."</td><td width='100' style='font-size:11px'><a href='alumnos_edit.php?type=mod&id=".@$row['Alumno_Id']."'>MODIFICAR</a>";
				echo "</td>".
				"<td width='100' style='font-size:11px'>";
				echo"<a href='alumnos_materias.php?type=mod&carr=".@$row['Alumno_Carrera']."&sem=".@$row['Alumno_Semestre']."&id=".@$row['Alumno_Id']."'>VER MATERIAS POSIBLES</a>";
				echo "</td><td width='100' style='font-size:11px'><a href='alumnos_grupos.php?type=mod&carr=".@$row['Alumno_Carrera']."&sem=".@$row['Alumno_Semestre']."&id=".@$row['Alumno_Id']."'>VER GRUPOS POSIBLES</a></td></tr>";
			}
			echo "</table><br />";
			} else {
			echo "<br><b>No existen alumnos registrados.</b>";
			}
			@mysql_free_result($result); 
			?>        
    </td></tr>
   	<?php if (@$_GET["success"] == "add"){ ?>
    <tr height="10"><td></td></tr><tr><td><font color="#FF0000">EL ALUMNO FUE AGREGADO</font></td></tr>
    <?php } else if (@$_GET["success"] == "mod"){ ?>
    <tr height="10"><td></td></tr><tr><td><font color="#FF0000">EL ALUMNO FUE MODIFICADO</font></td></tr>
    <?php } else if (@$_GET["success"] == "del"){ ?>
    <tr height="10"><td></td></tr><tr><td><font color="#FF0000">EL ALUMNO FUE ELIMINADO</font></td></tr>
    <?php } ?>
    <tr height="20"><td></td></tr>
    <tr><td colspan="2"><center><a href="alumnos_edit.php">AGREGAR NUEVO ALUMNO</a></center></td></tr>
    <tr height="20"><td></td></tr>
    <tr><td colspan="2"><center><a href="menu.php">REGRESAR</a></center></td></tr>
    <tr height="30"><td></td></tr>
    </table>
    
   	<?php
	} else {
	?>
    
	<table class="LoginBar" width="225" border="0" cellspacing="0" cellpadding="0">
    <tr><td><font color="#FF0000">ERROR DE ACCESO</font></td></tr>
    <tr height="10"><td colspan="2"></td></tr>
    <tr><td><a href="index.php">REGRESAR</a></td></tr>
    </table>
    
    <?php
	}
	?>

</body>
</html>

<?php
include 'db_close.php';
?> 