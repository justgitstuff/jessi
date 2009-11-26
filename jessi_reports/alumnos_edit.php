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
    
    <?php if (@$_GET['type'] == "mod"){ 
	$sql_type="SELECT * FROM alumno WHERE Alumno_Id='".@$_GET['id']."'";
	$result_type = mysql_query($sql_type);
	$row = @mysql_fetch_array($result_type, MYSQL_ASSOC);
	} ?>

	<table align="center" class="AdminSystem" width="400" border="0" cellspacing="0" cellpadding="0">
    <tr height="50"><td colspan="2"></td></tr>
    <td rowspan="4"><img src="images/JessiLogoWeb.png"></td>
    <tr><td colspan="2"><b><font color="#006699">SISTEMA DE CALENDARIZACIÓN</font></b></td></tr>
    <tr height="20"><td colspan="2"></td></tr>
    <tr><td colspan="2"><b><font color="#000000">
    <?php if (@$_GET['type'] <> "mod"){ ?>[ REPORTES - ALUMNOS > AGREGAR ]<?php } else { ?>[ REPORTES - ALUMNOS > MODIFICAR ]<?php } ?>
    </font></b></td></tr>
    <tr height="50"><td colspan="2"></td></tr>
    <tr><td>
	</table>
		<table align="center" class="AdminSubSystem" width="350" border="0" cellspacing="0" cellpadding="0">
        <?php if (@$_GET['type'] <> "mod"){?><form name="formdb" action="db_admin.php?type=alumno_add" method="post">
        <?php } else {?><form name="formdb" action="db_admin.php?type=alumno_mod&id=<?php echo @$_GET['id'] ?>" method="post"><?php }?>
                
	    <tr><td><b>MATRÍCULA</b></td><td><input name="Alumno_Matricula" type="text" style="width:100px;" maxlength="50" 
		<?php if (@$_GET['type'] == "mod"){ echo "value='".@$row['Alumno_Matricula']."'"; } ?> /></td></tr>
        <tr height="10"><td colspan="2"></td></tr>
        
	    <tr><td><b>NOMBRE</b></td><td><input name="Alumno_Nombre" type="text" style="width:200px;" maxlength="200" 
		<?php if (@$_GET['type'] == "mod"){ echo "value='".@$row['Alumno_Nombre']."'"; } ?> /></td></tr>
        <tr height="10"><td colspan="2"></td></tr>
        
        <tr><td><b>CARRERA</b></td><td><select name="Carrera_Id">
        <?php $query  = "SELECT * FROM carrera "; $result = mysql_query($query);
		while ($row_type = mysql_fetch_array($result, MYSQL_ASSOC)) {
			echo "<option value='".$row_type['Carrera_Id']."'"; if (@$row_type['Carrera_Id'] == @$_POST['Carrera_Id']) { echo " selected "; }
			else if (@$row_type['Carrera_Id'] == @$row['Carrera_Id']) { echo " selected "; }
			echo ">".$row_type['Carrera_Id']."</option>"; }?>
        </select></td></tr>
        <tr height="10"><td colspan="2"></td></tr>
        
	    <tr><td><b>SEMESTRE</b></td><td><select name="Alumno_Semestre">
        <?php 
		for ($i=1;$i<=9;$i++){
			echo "<option value=".$i; if (@$_POST['Alumno_Semestre'] == $i) { echo " selected "; }
			else if (@$row['Alumno_Semestre'] == $i) { echo " selected "; } echo ">".$i."</option>";
		}
		?>
        </select></td></tr>
        <tr height="10"><td colspan="2"></td></tr>    
        
   	    <tr height="10"><td colspan="2"></td></tr>
    	<?php if (@$_GET["error"] == "blank"){ ?>
	    <tr height="10"><td colspan="2"></td></tr>
	    <tr><td colspan="2"><font color="#FF0000">NO DEJE EN BLANCO EL CAMPO DE MATRÍCULA Y NOMBRE</font></td></tr>
    	<?php }?>
	    <tr height="20"><td colspan="2"></td></tr>
    	<tr><td colspan="2" align="center">
        <?php if (@$_GET['type'] <> "mod"){?>
        <input name="submit_add" type="submit" value="Registrar nuevo alumno"/>
		<?php } else {?>
        <script type="text/javascript">
		function delete_record(id)
		{
			var name = confirm("¿Seguro que quieres eliminar al alumno?")
			if (name == true){javascript:document.formdb.action="db_admin.php?type=alumno_del&id=" + id;
			document.formdb.submit();}
		}
		</script>
        <input align="right" name="submit_mod" type="submit" value="Modificar alumno"/><br /><br />
        <input align="right" name="submit_del" type="button" value="Eliminar alumno" onClick="delete_record('<?php echo @$_GET['id'] ?>');"/>
		<?php }?>
		</td></tr>
	    </form>
	    </table>
    </td></tr>
    <tr height="30"><td></td></tr>
    <tr><td colspan="2"><center><a href="alumnos.php">REGRESAR</a></center></td></tr>
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
	
	@mysql_free_result($result_type); 
	?>

</body>
</html>

<?php
include 'db_close.php';
?> 