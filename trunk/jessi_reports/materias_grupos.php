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

	<table align="center" class="AdminSystem" width="600" border="0" cellspacing="0" cellpadding="0">
    <tr height="50"><td></td></tr>
    <tr><td><b><font color="#006699">SISTEMA DE CALENDARIZACIÓN</font></b></td></tr>
    <tr height="20"><td></td></tr>
    <tr><td><b><font color="#000000">[ REPORTES - MATERIAS > GRUPOS ]</font></b></td></tr>
    <tr height="50"><td></td></tr>
    <tr><td>
            <?php
			$query_aux  = "SELECT * FROM materia WHERE Materia_Id ='".@$_GET['id']."'";
			$result_aux = mysql_query($query_aux);
			$row_aux = @mysql_fetch_array($result_aux, MYSQL_ASSOC);
			echo "<div style='font-size:15px; font-weight:bold'>Materia: &nbsp;".$row_aux['Materia_Nombre']."</div><br/><br/>";
						$query_aux  = "SELECT * FROM hora";
			$result_aux = mysql_query($query_aux);
			$str = "";
			while ($row_aux = @mysql_fetch_array($result_aux, MYSQL_ASSOC)){
				$str = $str.substr($row_aux['Hora_Nombre'],0,-2).":".substr($row_aux['Hora_Nombre'],-2).",";
			}
			$stack = explode(",",$str);
			
			$query  = "SELECT * FROM asignacion LEFT JOIN profesor ON asignacion.Profesor_Id = profesor.Profesor_Id LEFT JOIN lugar ON asignacion.Lugar_Id = lugar.Lugar_Id LEFT JOIN horarios_disp ON asignacion.Horario_Disp_Id = horarios_disp.Horario_Disp_Id LEFT JOIN dia ON horarios_disp.Dia_Id = dia.Dia_Id  LEFT JOIN grupo ON asignacion.Grupo_Id = grupo.Grupo_Id WHERE grupo.Materia_Id = '".$_GET["id"]."'";
			$result = mysql_query($query);
			$count=mysql_num_rows($result);
			if($count>=1){
			echo "<table width='100%' border='1'>";
			echo "<tr><td><b>ID</b></td><td><b>PROFESOR</b></td><td><b>LUGAR</b></td><td><b>DÍA</b></td><td><b>HORA</b></td></tr>";			
			while ($row = @mysql_fetch_array($result, MYSQL_ASSOC)) {
				echo "<tr><td>".@$row['Grupo_Id']."</td><td>".@$row['Profesor_Nombre']."</td><td>".@$row['Codigo']."</td><td>".@$row['Dia_Nombre']."</td><td>".$stack[@$row['Hora_Inicio']-1]." - ".$stack[@$row['Hora_Fin']-1]."</td></tr>";
			}
			echo "</table><br />";
			} else {
			echo "<br><b>No existen grupos registrados para la materia.</b>";
			}
			@mysql_free_result($result); 
			?>        
    </td></tr>
    <tr height="20"><td></td></tr>
    <tr><td colspan="2"><a href="materias.php">REGRESAR</a></td></tr>
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