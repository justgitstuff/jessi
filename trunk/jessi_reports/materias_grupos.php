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
    <tr><td><b><font color="#000000">[ REPORTES - MATERIAS > GRUPOS ]</font></b></td></tr>
    <tr height="50"><td></td></tr>
    <tr><td>
	</table>
            <?php
			$query_aux  = "SELECT * FROM materia WHERE Materia_Id ='".@$_GET['id']."'";
			$result_aux = mysql_query($query_aux);
			$row_aux = @mysql_fetch_array($result_aux, MYSQL_ASSOC);
			echo "<center><div style='font-size:15px; font-weight:bold'>Materia: &nbsp;".$row_aux['Materia_Nombre']."</div></center><br/><br/>";
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
			echo "<table onMouseover=\"changeto(event, 'lightblue')\" onMouseout=\"changeback(event, 'white')\" align='center' width='600' border='0'>";
			echo "<tr id=\"ignore\"><td><b>ID</b></td><td><b>PROFESOR</b></td><td><b>LUGAR</b></td><td><b>DÍA</b></td><td><b>HORA</b></td></tr>";			
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
    <tr><td colspan="2"><center><a href="materias.php">REGRESAR</a></center></td></tr>
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