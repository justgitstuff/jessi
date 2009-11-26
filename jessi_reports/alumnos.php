<?php
session_start();
include 'db_open.php';
?> 

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
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

	<table align="center" class="AdminSystem" width="900" border="0" cellspacing="0" cellpadding="0">
    <tr height="50"><td></td></tr>
    <tr><td><b><font color="#006699">SISTEMA DE CALENDARIZACIÓN</font></b></td></tr>
    <tr height="20"><td></td></tr>
    <tr><td><b><font color="#000000">[ REPORTES - ALUMNOS ]</font></b></td></tr>
    <tr height="50"><td></td></tr>
    <tr><td>
            <?php
			$query  = "SELECT * FROM alumno";
			$result = mysql_query($query);
			$count=mysql_num_rows($result);
			if($count>=1){
			echo "<table width='100%' border='1'>";
			echo "<tr><td><b>MATRÍCULA</b></td><td><b>NOMBRE</b></td><td><b>CARRERA</b></td><td><b>SEMESTRE</b></td><td></td><td></td><td></td></tr>";			
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
    <tr><td colspan="2"><a href="alumnos_edit.php">AGREGAR NUEVO ALUMNO</a></td></tr>
    <tr height="20"><td></td></tr>
    <tr><td colspan="2"><a href="menu.php">REGRESAR</a></td></tr>
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