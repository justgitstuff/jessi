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

	<table align="center" class="AdminSystem" width="400" border="0" cellspacing="0" cellpadding="0">
    <tr height="100"><td colspan="2"></td></tr>
    <form action="db_admin.php?type=login" method="post">
    <tr><td colspan="2"><b><font color="#006699">INSTITUTO TECNOLÓGICO Y DE ESTUDIOS SUPERIORES DE MONTERREY</font></b></td></tr>
    <tr height='40'><td colspan='2'></td></tr>
    <tr><td colspan="2"><b><font color="#000000">SISTEMA DE CALENDARIZACIÓN</font></b></td></tr>
    <tr height='10'><td colspan='2'></td></tr>
    <tr><td colspan="2"><b><font color="#000000">[ REPORTES ]</font></b></td></tr>
    <tr height='50'><td colspan='2'></td></tr>
	<tr><td colspan="2"><input name="registersubmit" type="submit" value="Ingresar al sistema" /></td></tr>
    </table>

</body>
</html>

<?php
include 'db_close.php';
?> 