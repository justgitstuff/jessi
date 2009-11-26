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

	<table align="center" class="AdminSystem" width="800" border="0" cellspacing="0" cellpadding="0">
    <tr height="50"><td></td></tr>
    <tr><td><b><font color="#006699">SISTEMA DE CALENDARIZACIÓN</font></b></td></tr>
    <tr height="20"><td></td></tr>
    <tr><td><b><font color="#000000">[ REPORTES - MATERIAS ]</font></b></td></tr>
    <tr height="50"><td></td></tr>
    <tr><td>
            <?php
			$query  = "SELECT * FROM materia";
			$result = mysql_query($query);
			$count=mysql_num_rows($result);
			if($count>=1){
			echo "<table width='100%' border='1'>";
			echo "<tr><td><b>ID</b></td><td><b>NOMBRE</b></td><td><b>DIVISIÓN</b></td><td><b>HORAS</b></td><td><b>NÚMERO DE ALUMNOS</b></td><td></td><td></td></tr>";			
			while ($row = @mysql_fetch_array($result, MYSQL_ASSOC)) {
				echo "<tr><td>".@$row['Materia_Id']."</td><td>".@$row['Materia_Nombre']."</td><td>".@$row['Materia_Division']."</td><td>".@$row['Materia_Horas']."</td><td>".@$row['Materia_Poblacion']."</td>".
				"<td width='150' style='font-size:11px'>";
				echo "<a href='materias_profesores.php?type=mod&id=".@$row['Materia_Id']."'>VER PROFESORES QUE LA PUEDEN DAR</a>";
				echo "</td><td width='150' style='font-size:11px'>";
				echo "<a href='materias_grupos.php?type=mod&id=".@$row['Materia_Id']."'>VER GRUPOS DE LA MATERIA (PROFESOR, LUGAR, HORARIO)</a>";
				echo "</td></tr>";
			}
			echo "</table><br />";
			} else {
			echo "<br><b>No existen materias registradas.</b>";
			}
			@mysql_free_result($result); 
			?>        
    </td></tr>
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