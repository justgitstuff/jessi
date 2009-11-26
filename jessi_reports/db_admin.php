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

function upload($type,$text){
	$ruta = getcwd()."/../images/".$type."/";
	$nombre_archivo = str_replace(array(" ","(",")"),"_",strtolower($text)).".jpg";
	$tipo_archivo = $_FILES['fileupload']['type'];
	$tamano_archivo = $_FILES['fileupload']['size'];
	if (!((strpos($tipo_archivo, "jpeg") || strpos($tipo_archivo, "jpg")) && ($tamano_archivo < 2000000000000000))) {
		return "error";
	}else{
	    if (move_uploaded_file($_FILES['fileupload']['tmp_name'], $ruta.$nombre_archivo)){
			chmod ($ruta.$nombre_archivo, 0777);
			return "success";
	    }else{
			return "error";
	    }
	} 
}

function createthumb($name,$filename,$new_w,$new_h){
	$system=explode('.',$name);
	if (preg_match('/jpg|jpeg/',$system[3])){
		$src_img=imagecreatefromjpeg($name);
	}
	if (preg_match('/png/',$system[3])){
		$src_img=imagecreatefrompng($name);
	}
	$old_x=imageSX($src_img);
	$old_y=imageSY($src_img);
	if ($old_x > $old_y) {
		$thumb_w=$new_w;
		$thumb_h=$old_y*($new_h/$old_x);
	}
	if ($old_x < $old_y) {
		$thumb_w=$old_x*($new_w/$old_y);
		$thumb_h=$new_h;
	}
	if ($old_x == $old_y) {
		$thumb_w=$new_w;
		$thumb_h=$new_h;
	}
	$dst_img=ImageCreateTrueColor($thumb_w,$thumb_h);
	imagecopyresampled($dst_img,$src_img,0,0,0,0,$thumb_w,$thumb_h,$old_x,$old_y); 
	if (preg_match("/png/",$system[1]))
	{
		imagepng($dst_img,$filename); 
	} else {
		imagejpeg($dst_img,$filename); 
	}
	imagedestroy($dst_img); 
	imagedestroy($src_img); 
}

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
	
} else if ($_GET["type"] == "password_change"){

	if (empty($_POST["pass_act"]) || empty($_POST["pass_new"]) || empty($_POST["pass_rew"])){
		header("location:password.php?error=blank");
	} else if ($_POST["pass_act"] <> @$_SESSION['adpassword']){
		header("location:password.php?error=badpass");
	} else if ($_POST["pass_new"] <> $_POST["pass_rew"]){
		header("location:password.php?error=match");
	} else {
		$sql = "UPDATE admin SET admin_pass='$_POST[pass_new]'".
		"WHERE admin_user='".@$_SESSION['adusername']."' and admin_pass='".@$_SESSION['adpassword']."'";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
		@$_SESSION['adpassword'] = $_POST[pass_new];
 		header("location:password.php?success=mod");
	}
	
} else if ($_GET["type"] == "adminlist_add"){

	$query = "SELECT admin_user FROM admin WHERE admin_user = '".$_POST["user"]."'";
	$result = mysql_query($query);

	if (empty($_POST["user"]) || empty($_POST["password"])){
		header("location:adminlist_edit.php?error=blank");
	} else if (!ctype_alnum($_POST["user"]) || !ctype_alnum($_POST["password"])){
		header("location:adminlist_edit.php?error=alfa");
	} else if (mysql_num_rows($result)>0){
		header("location:adminlist_edit.php?error=duplicate");
	} else {
		$sql = "INSERT INTO admin (admin_user,admin_pass) ".
		"VALUES ('$_POST[user]','$_POST[password]')";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
 		header("location:adminlist.php?success=add");
	}

} else if ($_GET["type"] == "adminlist_mod"){

	$query = "SELECT admin_user FROM admin WHERE admin_user = '".$_POST["user"]."' AND id_user <> ".$_GET["id"];
	$result = mysql_query($query);

	if (empty($_POST["user"])){
		header("location:adminlist_edit.php?type=mod&id=".$_GET["id"]."&error=blank");
	} else if (!ctype_alnum($_POST["user"])){
		header("location:adminlist_edit.php?type=mod&id=".$_GET["id"]."&error=alfa");
	} else if (mysql_num_rows($result)>0){
		header("location:adminlist_edit.php?type=mod&id=".$_GET["id"]."&error=duplicate");
	} else {
		$sql = "UPDATE admin SET admin_user='$_POST[user]' WHERE admin_user='".$_GET['id']."'";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
 		header("location:adminlist.php?success=mod");
	}

} else if ($_GET["type"] == "adminlist_del"){

	$sql = "DELETE FROM admin WHERE admin_user='".$_GET['id']."'";
	if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
	header("location:adminlist.php?success=del");
	
} else if ($_GET["type"] == "productos_add"){
	
	if ((empty($_POST["id"]) || empty($_POST["nombre"])) || (mysql_num_rows($result)>0)){
		echo "<form name='returnform' method='post'>";
		echo "<input name='nombre' type='hidden' value='".@$_POST['nombre']."' />";
		echo "<input name='desc' type='hidden' value='".@$_POST['desc']."' />";
		echo "<input name='id_categoria' type='hidden' value='".@$_POST['id_categoria']."' />";
	echo "</form>";
	}

	if (empty($_POST["nombre"])){
		echo "<script>document.returnform.action='productos_edit.php?error=blank'; document.returnform.submit();</script>";
	} else {
		$sql = "INSERT INTO productos (producto_nombre, producto_desc, id_categoria) ".
		"VALUES ('".mysql_real_escape_string($_POST['nombre'])."','".mysql_real_escape_string($_POST['desc'])."','$_POST[id_categoria]')";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
		$error=false;
		$last_id=mysql_insert_id();
		if (upload("productos",$last_id) == "error"){ $error=true; }
 		if ($error==true) { header("location:productos.php?success=file"); } else { 
		createthumb('../images/productos/'.$last_id.'.jpg','../images/productos_thumbs/'.$last_id.'.jpg',200,200);
		header("location:productos.php?success=add"); 
		}
	}

} else if ($_GET["type"] == "productos_mod"){
		
	if ((empty($_POST["id"]) || empty($_POST["nombre"])) || (mysql_num_rows($result)>0)){
		echo "<form name='returnform' method='post'>";
		echo "<input name='nombre' type='hidden' value='".@$_POST['nombre']."' />";
		echo "<input name='desc' type='hidden' value='".@$_POST['desc']."' />";
		echo "<input name='id_categoria' type='hidden' value='".@$_POST['id_categoria']."' />";
	echo "</form>";
	}

	if (empty($_POST["nombre"])){
		echo "<script>document.returnform.action='productos_edit.php?type=mod&id=".$_GET["id"]."&error=blank'; document.returnform.submit();</script>";
	} else {
		$sql = "UPDATE productos SET producto_nombre='".mysql_real_escape_string($_POST['nombre'])."', producto_desc='".mysql_real_escape_string($_POST['desc'])."', id_categoria='$_POST[id_categoria]' WHERE id_producto='".$_GET['id']."'";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
		$error=false;
		if (upload("productos",$_GET["id"]) == "error"){ $error=true; }
 		if ($error==true) { header("location:productos.php?success=file&page=".$_POST["page"]); } else { 
		createthumb('../images/productos/'.$_GET["id"].'.jpg','../images/productos_thumbs/'.$_GET["id"].'.jpg',200,200);
		header("location:productos.php?success=mod"); 
		}
	}

} else if ($_GET["type"] == "productos_del"){

	$sql = "DELETE FROM productos WHERE id_producto='".$_GET['id']."'";
	if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
	@unlink("../images/productos/".$_GET['id'].".jpg");
	@unlink("../images/productos_thumbs/".$_GET['id'].".jpg");
	header("location:productos.php?success=del");
		
} else if ($_GET["type"] == "categoria_add"){

	if (empty($_POST["nombre"])){
		header("location:categorias_edit.php?error=blank");
	} else {
		$sql = "INSERT INTO categorias (categoria_nombre) ".
		"VALUES ('$_POST[nombre]')";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
 		header("location:categorias.php?success=add");
	}

} else if ($_GET["type"] == "categoria_mod"){

	if (empty($_POST["nombre"])){
		header("location:categorias_edit.php?type=mod&id=".$_GET["id"]."&error=blank");
	} else {
		$sql = "UPDATE categorias SET categoria_nombre='$_POST[nombre]' WHERE id_categoria='".$_GET['id']."'";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
 		header("location:categorias.php?success=mod");
	}

} else if ($_GET["type"] == "categoria_del"){

	$sql = "DELETE FROM categorias WHERE id_categoria='".$_GET['id']."'";
	if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
	header("location:categorias.php?success=del");

} else if ($_GET["type"] == "galeria_add"){

	if (empty($_POST["fecha"]) || empty($_POST["nombre"])){
		header("location:galerias_edit.php?error=blank");
	} else if (!isDate($_POST["fecha"])){
		header("location:galerias_edit.php?error=date");
	} else {
		$sql = "INSERT INTO galerias (galeria_fecha,galeria_nombre,galeria_desc) ".
		"VALUES ('$_POST[fecha]','$_POST[nombre]','$_POST[desc]')";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
		header("location:galerias.php?success=add");
	}

} else if ($_GET["type"] == "galeria_mod"){

	if (empty($_POST["fecha"]) || empty($_POST["nombre"])){
		header("location:galerias_edit.php?type=mod&id=".$_GET["id"]."&error=blank");
	} else if (!isDate($_POST["fecha"])){
		header("location:galerias_edit.php?type=mod&id=".$_GET["id"]."&error=date");
	} else {
		$sql = "UPDATE galerias SET galeria_fecha='$_POST[fecha]', galeria_nombre='$_POST[nombre]', galeria_desc='$_POST[desc]' WHERE id_galeria='".$_GET['id']."'";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
 		header("location:galerias.php?success=mod");
	}

} else if ($_GET["type"] == "galeria_del"){

	$sql = "DELETE FROM galerias WHERE id_galeria='".$_GET['id']."'";
	if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
	header("location:galerias.php?success=del");
	
} else if ($_GET["type"] == "galeria_foto_add"){

	$sql = "INSERT INTO galeria_fotos (id_galeria, galeria_foto_desc) ".
	"VALUES ('$_GET[id_user]','$_POST[desc]')";
	if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
	$error=false;
	$last_id=mysql_insert_id();
	if (upload("galerias",@$_GET['id_user']."-".$last_id) == "error"){ $error=true; }
	if ($error==true) { header("location:galerias_fotos.php?success=file&id_user=".@$_GET['id_user']); } 
	else { createthumb('../images/galerias/'.@$_GET['id_user']."-".$last_id.'.jpg','../images/galerias_thumbs/'.@$_GET['id_user']."-".$last_id.'.jpg',200,200);
	header("location:galerias_fotos.php?success=add&id_user=".@$_GET['id_user']); }

} else if ($_GET["type"] == "galeria_foto_mod"){

	$sql = "UPDATE galeria_fotos SET galeria_foto_desc='$_POST[desc]' WHERE id_galeria_foto='".$_GET['id']."'";
	if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
	$error=false;
	if (upload("galerias",@$_GET['id_user']."-".@$_GET['id']) == "error"){ $error=true; }
	if ($error==true) { header("location:galerias_fotos.php?success=file&id_user=".@$_GET['id_user']); } 
	else { createthumb('../images/galerias/'.@$_GET['id_user']."-".@$_GET['id'].'.jpg','../images/galerias_thumbs/'.@$_GET['id_user']."-".@$_GET['id'].'.jpg',200,200);
	header("location:galerias_fotos.php?success=mod&id_user=".@$_GET['id_user']); }

} else if ($_GET["type"] == "galeria_foto_del"){

	$sql = "DELETE FROM galeria_fotos WHERE id_galeria_foto='".$_GET['id']."'";
	if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
	unlink("../images/galerias/".@$_GET['id_user']."-".@$_GET['id'].".jpg");
	unlink("../images/galerias_thumbs/".@$_GET['id_user']."-".@$_GET['id'].".jpg");
	header("location:galerias_fotos.php?success=del&id_user=".@$_GET['id_user']);
	
} else if ($_GET["type"] == "news_add"){

	if (empty($_POST["fecha"]) || empty($_POST["titulo"]) || empty($_POST["desc"])){
		header("location:news_edit.php?error=blank");
	} else if (!isDate($_POST["fecha"])){
		header("location:news_edit.php?error=date");
	} else {
		$sql = "INSERT INTO noticias (noticia_fecha, noticia_titulo, noticia_desc, noticia_link) ".
		"VALUES ('$_POST[fecha]','$_POST[titulo]','$_POST[desc]','$_POST[link]')";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
		$last_id = mysql_insert_id();
		if (upload("noticias",$last_id) == "error"){ header("location:news.php?success=file"); } else { 
			createthumb('../images/noticias/'.$last_id.'.jpg','../images/noticias_thumbs/'.$last_id.'.jpg',220,220);
			header("location:news.php?success=add"); }
	}

} else if ($_GET["type"] == "news_mod"){

	if (empty($_POST["fecha"]) || empty($_POST["titulo"]) || empty($_POST["desc"])){
		header("location:news_edit.php?type=mod&id=".$_GET["id"]."&error=blank");
	} else if (!isDate($_POST["fecha"])){
		header("location:news_edit.php?type=mod&id=".$_GET["id"]."&error=date");
	} else {
		$sql = "UPDATE noticias SET noticia_fecha='$_POST[fecha]', noticia_titulo='$_POST[titulo]', noticia_desc='$_POST[desc]', noticia_link='$_POST[link]' WHERE id_noticia='".$_GET['id']."'";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
		if (upload("noticias",$_GET["id"]) == "error"){ header("location:news.php?success=file"); }	else { 
			createthumb('../images/noticias/'.$_GET["id"].'.jpg','../images/noticias_thumbs/'.$_GET["id"].'.jpg',220,220);
			header("location:news.php?success=mod"); }
	}

} else if ($_GET["type"] == "news_del"){

	$sql = "DELETE FROM noticias WHERE id_noticia='".$_GET['id']."'";
	if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
	@unlink("../images/noticias/".$_GET['id'].".jpg");
	@unlink("../images/noticias_thumbs/".$_GET['id'].".jpg");
	header("location:news.php?success=del");
	
} else if ($_GET["type"] == "evento_add"){

	if (empty($_POST["fecha"]) || empty($_POST["titulo"]) || empty($_POST["desc"])){
		header("location:eventos_edit.php?error=blank");
	} else if (!isDate($_POST["fecha"])){
		header("location:eventos_edit.php?error=date");
	} else {
		$sql = "INSERT INTO eventos (evento_fecha, evento_titulo, evento_desc) ".
		"VALUES ('$_POST[fecha]','$_POST[titulo]','$_POST[desc]')";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
		$last_id = mysql_insert_id();
		if (upload("eventos",$last_id) == "error"){ header("location:eventos.php?success=file"); } else { 
			createthumb('../images/eventos/'.$last_id.'.jpg','../images/eventos_thumbs/'.$last_id.'.jpg',220,220);
			header("location:eventos.php?success=add"); }
	}

} else if ($_GET["type"] == "evento_mod"){

	if (empty($_POST["fecha"]) || empty($_POST["titulo"]) || empty($_POST["desc"])){
		header("location:eventos_edit.php?type=mod&id=".$_GET["id"]."&error=blank");
	} else if (!isDate($_POST["fecha"])){
		header("location:eventos_edit.php?type=mod&id=".$_GET["id"]."&error=date");
	} else {
		$sql = "UPDATE eventos SET evento_fecha='$_POST[fecha]', evento_titulo='$_POST[titulo]', evento_desc='$_POST[desc]' WHERE id_evento='".$_GET['id']."'";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
		if (upload("eventos",$_GET["id"]) == "error"){ header("location:eventos.php?success=file"); }	else { 
			createthumb('../images/eventos/'.$_GET["id"].'.jpg','../images/eventos_thumbs/'.$_GET["id"].'.jpg',220,220);
			header("location:eventos.php?success=mod"); }
	}

} else if ($_GET["type"] == "evento_del"){

	$sql = "DELETE FROM eventos WHERE id_evento='".$_GET['id']."'";
	if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
	@unlink("../images/eventos/".$_GET['id'].".jpg");
	@unlink("../images/eventos_thumbs/".$_GET['id'].".jpg");
	header("location:eventos.php?success=del");
		
} else if ($_GET["type"] == "promo_add"){

	if (empty($_POST["fecha"]) || empty($_POST["titulo"]) || empty($_POST["desc"])){
		header("location:promociones_edit.php?error=blank");
	} else if (!isDate($_POST["fecha"])){
		header("location:promociones_edit.php?error=date");
	} else {
		$sql = "INSERT INTO promociones (promo_fecha, promo_titulo, promo_desc) ".
		"VALUES ('$_POST[fecha]','$_POST[titulo]','$_POST[desc]')";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
		$last_id = mysql_insert_id();
		if (upload("promociones",$last_id) == "error"){ header("location:promociones.php?success=file"); } else { 
			createthumb('../images/promociones/'.$last_id.'.jpg','../images/promociones_thumbs/'.$last_id.'.jpg',220,220);
			header("location:promociones.php?success=add"); }
	}

} else if ($_GET["type"] == "promo_mod"){

	if (empty($_POST["fecha"]) || empty($_POST["titulo"]) || empty($_POST["desc"])){
		header("location:promociones_edit.php?type=mod&id=".$_GET["id"]."&error=blank");
	} else if (!isDate($_POST["fecha"])){
		header("location:promociones_edit.php?type=mod&id=".$_GET["id"]."&error=date");
	} else {
		$sql = "UPDATE promociones SET promo_fecha='$_POST[fecha]', promo_titulo='$_POST[titulo]', promo_desc='$_POST[desc]' WHERE id_promo='".$_GET['id']."'";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
		if (upload("promociones",$_GET["id"]) == "error"){ header("location:promociones.php?success=file"); }	else { 
			createthumb('../images/promociones/'.$_GET["id"].'.jpg','../images/promociones_thumbs/'.$_GET["id"].'.jpg',220,220);
			header("location:promociones.php?success=mod"); }
	}

} else if ($_GET["type"] == "promo_del"){

	$sql = "DELETE FROM promociones WHERE id_promo='".$_GET['id']."'";
	if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
	@unlink("../images/promociones/".$_GET['id'].".jpg");
	@unlink("../images/promociones_thumbs/".$_GET['id'].".jpg");
	header("location:promociones.php?success=del");
		
} else if ($_GET["type"] == "concurso_add"){

	if (empty($_POST["fecha"]) || empty($_POST["nombre"])){
		header("location:concursos_edit.php?error=blank");
	} else if (!isDate($_POST["fecha"]) || !isDate($_POST["fecha_comienzo"])){
		header("location:concursos_edit.php?error=date");
	} else {
		$sql = "INSERT INTO concursos (concurso_fecha, concurso_nombre, concurso_bases, concurso_restricciones, concurso_notas, concurso_ganador, pregunta, fecha_comienzo, hora_inicio, hora_final) ".
		"VALUES ('$_POST[fecha]','$_POST[nombre]','$_POST[bases]','$_POST[restricciones]','$_POST[notas]','$_POST[ganador]','$_POST[pregunta]','$_POST[fecha_comienzo]','$_POST[hora_inicio]','$_POST[hora_final]')";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
		header("location:concursos.php?success=add");
	}

} else if ($_GET["type"] == "concurso_mod"){

	if (empty($_POST["fecha"]) || empty($_POST["nombre"])){
		header("location:concursos_edit.php?type=mod&id=".$_GET["id"]."&error=blank");
	} else if (!isDate($_POST["fecha"]) || !isDate($_POST["fecha_comienzo"])){
		header("location:concursos_edit.php?type=mod&id=".$_GET["id"]."&error=date");
	} else {
		$sql = "UPDATE concursos SET concurso_fecha='$_POST[fecha]', concurso_nombre='$_POST[nombre]', concurso_bases='$_POST[bases]', concurso_restricciones='$_POST[restricciones]', concurso_notas='$_POST[notas]', concurso_ganador='$_POST[ganador]', pregunta='$_POST[pregunta]', fecha_comienzo='$_POST[fecha_comienzo]', hora_inicio='$_POST[hora_inicio]', hora_final='$_POST[hora_final]' WHERE id_concurso='".$_GET['id']."'";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
		header("location:concursos.php?success=mod");
	}

} else if ($_GET["type"] == "concursos_del"){

	$sql = "DELETE FROM concursos WHERE id_concurso='".$_GET['id']."'";
	if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
	header("location:concursos.php?success=del");
	
} else if ($_GET["type"] == "users_add"){

	$query = "SELECT * FROM users WHERE user_name = '".$_POST["name"]."'";
	$result = mysql_query($query);
	
	$query_extra = "SELECT * FROM users WHERE user_email = '".$_POST["email"]."'";
	$result_extra = mysql_query($query_extra);

	if (empty($_POST["name"]) || empty($_POST["nombre"]) || empty($_POST["email"])){
		header("location:users_edit.php?error=blank");
	} else if (!ctype_alnum($_POST["name"])){
		header("location:users_edit.php?error=alfa");
	} else if (mysql_num_rows($result)>0){
		header("location:users_edit.php?error=duplicate");
	} else if (mysql_num_rows($result_extra)>0){
		header("location:users_edit.php?error=duplicatemail");
	} else {
		$sql = "INSERT INTO users (user_name,user_nombre,user_email,user_direccion,user_telefono,user_newsletter) ".
		"VALUES ('$_POST[name]','$_POST[nombre]','$_POST[email]','$_POST[direccion]','$_POST[telefono]','$_POST[newsletter]')";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
 		header("location:users.php?success=add");
	}

} else if ($_GET["type"] == "users_mod"){

	$query = "SELECT * FROM users WHERE user_name = '".$_POST["name"]."' AND id_user <> ".$_GET["id"];
	$result = mysql_query($query);
	
	$query_extra = "SELECT * FROM users WHERE user_email = '".$_POST["email"]."' AND id_user <> ".$_GET["id"];
	$result_extra = mysql_query($query_extra);

	if (empty($_POST["name"]) || empty($_POST["nombre"]) || empty($_POST["email"])){
		header("location:users_edit.php?type=mod&id=".$_GET["id"]."&error=blank");
	} else if (!ctype_alnum($_POST["name"])){
		header("location:users_edit.php?type=mod&id=".$_GET["id"]."&error=alfa");
	} else if (mysql_num_rows($result)>0){
		header("location:users_edit.php?type=mod&id=".$_GET["id"]."&error=duplicate");
	} else if (mysql_num_rows($result_extra)>0){
		header("location:users_edit.php?type=mod&id=".$_GET["id"]."&error=duplicatemail");
	} else {
		$sql = "UPDATE users SET user_name='$_POST[name]', user_nombre='$_POST[nombre]', user_email='$_POST[email]', user_direccion='$_POST[direccion]', user_telefono='$_POST[telefono]', user_newsletter='$_POST[newsletter]' WHERE id_user='".$_GET['id']."'";
		if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
 		header("location:users.php?success=mod");
	}

} else if ($_GET["type"] == "users_del"){

	$sql = "DELETE FROM users WHERE id_user='".$_GET['id']."'";
	if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
	header("location:users.php?success=del");

} else if ($_GET["type"] == "comment_del"){

	$sql = "DELETE FROM comms WHERE id_comm='".$_GET['id']."'";
	if (!mysql_query($sql,$conn)) { die('Error: ' . mysql_error()); }
	header("location:comentarios.php?success=del&id_user=".@$_GET['id_user']);
}


include 'db_close.php';
?> 
