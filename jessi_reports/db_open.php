<?php
$dbhost = 'localhost'; $dbuser = 'root'; $dbpass = '';

$conn = mysql_connect($dbhost, $dbuser, $dbpass) or die
                      ('Error connecting to mysql');

$dbname = 'itesm';
mysql_select_db($dbname);
@mysql_query("SET NAMES 'utf8'");

function strtolowerExtended($str)
{     
        $low = array(chr(193) => chr(225), //�
                     chr(201) => chr(233), //�
                     chr(205) => chr(237), //�
                     chr(211) => chr(243), //�
                     chr(218) => chr(250), //�
                     chr(220) => chr(252), //�
                     chr(209) => chr(241)  //�
                     );
 
 
      return utf8_encode(strtolower(strtr(utf8_decode($str),$low)));
 
}

function strtoupperExtended($str)

{     

        $low = array(chr(225) => chr(193), //� 

                     chr(233) => chr(201), //� 

                     chr(237) => chr(205), //� 

                     chr(243) => chr(211), //� 

                     chr(250) => chr(218), //� 

                     chr(252) => chr(220), //� 

                     chr(241) => chr(209)  //� 

                     );

 

 

      return utf8_encode(strtoupper(strtr(utf8_decode($str),$low)));

}

?>