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
	$sql="SELECT * FROM admin WHERE admin_user='".@$_SESSION['adusername']."' and admin_pass='".@$_SESSION['adpassword']."'";
	$result = mysql_query($sql);
	$count = mysql_num_rows($result);
	if($count>=1){
	?>

	<table align="center" class="AdminSystem" width="400" border="0" cellspacing="0" cellpadding="0">
    <tr height="50"><td></td></tr>
	<td rowspan="4"><img src="images/JessiLogoWeb.png"></td>
    <tr><td><b><font color="#006699">SISTEMA DE CALENDARIZACIÓN</font></b></td></tr>
    <tr height="20"><td></td></tr>
    <tr><td><b><font color="#000000">[ REPORTES ]</font></b></td></tr>
    <tr height="50"><td></td></tr>
    <tr><td>
	</table>
		<table align="center" width="400" border="0" cellpadding="5" cellspacing="5">
    	<tr><td><b><a href="carreras.php">LISTA DE CARRERAS Y MATERIAS</a></b></td></tr>
    	<tr><td><b><a href="alumnos.php">LISTA DE ALUMNOS</a></b></td></tr>
        </table><br />
		<table align="center" width="400" border="0" cellpadding="5" cellspacing="5">
    	<tr><td><b><a href="profesores.php">HORARIOS DE PROFESORES</a></b></td></tr>
        </table><br />
		<table align="center" width="400" border="0" cellpadding="5" cellspacing="5">
    	<tr><td><b><a href="materias.php">HORARIOS DE MATERIAS</a></b></td></tr>
        </table><br />
		<table align="center" width="400" border="0" cellpadding="5" cellspacing="5">
    	<tr><td><b><a href="salones.php">HORARIOS DE SALONES</a></b></td></tr>
    	<tr><td><b><a href="laboratorios.php">HORARIOS DE LABORATORIOS</a></b></td></tr>
        </table><br />
    </td></tr>
    <tr height="30"><td></td></tr>
    <tr><td><center><a href="db_admin.php?type=logout">SALIR DEL SISTEMA</a></center></td></tr>
    <tr height="30"><td></td></tr>
    </table>
    
   	<?php
	} else {
	?>
    
	<table align="center" class="LoginBar" width="225" border="0" cellspacing="0" cellpadding="0">
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