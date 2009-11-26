/*
Navicat MySQL Data Transfer

Source Server         : intercity
Source Server Version : 50136
Source Host           : localhost:3306
Source Database       : itesm

Target Server Type    : MYSQL
Target Server Version : 50136
File Encoding         : 65001

Date: 2009-11-25 23:26:09
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `admin`
-- ----------------------------
DROP TABLE IF EXISTS `admin`;
CREATE TABLE `admin` (
  `admin_user` varchar(10) NOT NULL DEFAULT '',
  `admin_pass` varchar(10) NOT NULL DEFAULT '',
  PRIMARY KEY (`admin_user`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of admin
-- ----------------------------
INSERT INTO `admin` VALUES ('admin', 'access');

-- ----------------------------
-- Table structure for `alumno`
-- ----------------------------
DROP TABLE IF EXISTS `alumno`;
CREATE TABLE `alumno` (
  `Alumno_Id` int(11) NOT NULL AUTO_INCREMENT,
  `Alumno_Matricula` varchar(10) DEFAULT NULL,
  `Alumno_Nombre` varchar(25) DEFAULT NULL,
  `Alumno_Carrera` varchar(3) DEFAULT NULL,
  `Alumno_Semestre` int(11) DEFAULT NULL,
  PRIMARY KEY (`Alumno_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of alumno
-- ----------------------------

-- ----------------------------
-- Table structure for `alumno_grupo`
-- ----------------------------
DROP TABLE IF EXISTS `alumno_grupo`;
CREATE TABLE `alumno_grupo` (
  `Grupo_Id` int(11) NOT NULL DEFAULT '0',
  `Alumno_Id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Grupo_Id`,`Alumno_Id`),
  KEY `Grupo_Id` (`Grupo_Id`),
  KEY `Alumno_Id` (`Alumno_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of alumno_grupo
-- ----------------------------

-- ----------------------------
-- Table structure for `asignacion`
-- ----------------------------
DROP TABLE IF EXISTS `asignacion`;
CREATE TABLE `asignacion` (
  `Grupo_Id` int(11) NOT NULL DEFAULT '0',
  `Profesor_Id` int(11) NOT NULL DEFAULT '0',
  `Lugar_Id` int(11) NOT NULL DEFAULT '0',
  `Horario_Disp_Id` int(11) DEFAULT NULL,
  PRIMARY KEY (`Grupo_Id`,`Profesor_Id`,`Lugar_Id`),
  KEY `Profesor_Id` (`Profesor_Id`),
  KEY `Lugar_Id` (`Lugar_Id`),
  KEY `Horario_Disp_Id` (`Horario_Disp_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of asignacion
-- ----------------------------

-- ----------------------------
-- Table structure for `carrera`
-- ----------------------------
DROP TABLE IF EXISTS `carrera`;
CREATE TABLE `carrera` (
  `Carrera_Id` varchar(5) NOT NULL DEFAULT '',
  `Carrera_Nombre` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`Carrera_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of carrera
-- ----------------------------
INSERT INTO `carrera` VALUES ('ITC', 'Ingeniero en Tecnologías Computacionales');
INSERT INTO `carrera` VALUES ('LATI', 'Licenciado en Administración de las Tecnologías de la Información');
INSERT INTO `carrera` VALUES ('ITIC', 'Ingeniería en Tecnología de Información y Comunicación');
INSERT INTO `carrera` VALUES ('ISC', 'Ingeniero en Sistemas Computacionales');
INSERT INTO `carrera` VALUES ('LSCA', 'Licenciado en Sistemas Computacionales y Administración');

-- ----------------------------
-- Table structure for `carrera_materia`
-- ----------------------------
DROP TABLE IF EXISTS `carrera_materia`;
CREATE TABLE `carrera_materia` (
  `Carrera_Id` varchar(5) NOT NULL DEFAULT '',
  `Materia_Id` varchar(20) NOT NULL DEFAULT '',
  `Semestre` int(11) DEFAULT NULL,
  PRIMARY KEY (`Carrera_Id`,`Materia_Id`),
  KEY `Carrera_Id` (`Carrera_Id`),
  KEY `Materia_Id` (`Materia_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of carrera_materia
-- ----------------------------
INSERT INTO `carrera_materia` VALUES ('ITC', 'CB00821', '1');
INSERT INTO `carrera_materia` VALUES ('ITC', 'CB00831', '1');
INSERT INTO `carrera_materia` VALUES ('ITC', 'CB00841', '2');
INSERT INTO `carrera_materia` VALUES ('ITC', 'CB00858', '3');
INSERT INTO `carrera_materia` VALUES ('ITC', 'CB00881', '2');
INSERT INTO `carrera_materia` VALUES ('ITC', 'CB00883', '4');
INSERT INTO `carrera_materia` VALUES ('ITC', 'CB00899', '5');
INSERT INTO `carrera_materia` VALUES ('ITC', 'CS00883', '6');
INSERT INTO `carrera_materia` VALUES ('ITC', 'IS00882', '3');
INSERT INTO `carrera_materia` VALUES ('ITC', 'MR1001', '3');
INSERT INTO `carrera_materia` VALUES ('ITC', 'SI00003-CLIN', '2');
INSERT INTO `carrera_materia` VALUES ('ITC', 'SI00004-CLIN', '1');
INSERT INTO `carrera_materia` VALUES ('ITC', 'SI00865', '2');
INSERT INTO `carrera_materia` VALUES ('ITC', 'SI00866', '3');
INSERT INTO `carrera_materia` VALUES ('ITC', 'SI00868', '6');
INSERT INTO `carrera_materia` VALUES ('ITC', 'SI00877', '4');
INSERT INTO `carrera_materia` VALUES ('ITC', 'SI00886', '5');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC1001', '3');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC1002', '4');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC1004', '2');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC1005', '1');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC1006', '2');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC1007', '3');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC1009', '2');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC1010', '3');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC1011', '4');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC1012', '3');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC2001', '2');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC2002', '3');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC2003', '4');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC2004', '2');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC2005', '1');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC2006', '2');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC2008', '2');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC2009', '2');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC2010', '1');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC2011', '1');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC2012', '1');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC2013', '2');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC2014', '3');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC3003', '4');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC3021', '5');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC3037', '6');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC3041', '4');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC3043', '3');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TC3044', '2');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI1000', '3');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI1001', '4');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI1002', '5');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI1004', '6');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI1005', '7');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI1007', '8');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI2000', '9');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI2001', '9');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI2002', '8');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI2003', '7');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI2004', '7');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI2006', '6');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI2007', '6');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI2009', '5');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI3001', '5');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI3002', '6');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI3010', '8');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI3014', '9');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI3023', '8');
INSERT INTO `carrera_materia` VALUES ('ITC', 'TI3024', '7');
INSERT INTO `carrera_materia` VALUES ('LATI', 'CB00821', '6');
INSERT INTO `carrera_materia` VALUES ('LATI', 'CB00831', '7');
INSERT INTO `carrera_materia` VALUES ('LATI', 'CB00841', '8');
INSERT INTO `carrera_materia` VALUES ('LATI', 'CB00858', '6');
INSERT INTO `carrera_materia` VALUES ('LATI', 'CB00881', '7');
INSERT INTO `carrera_materia` VALUES ('LATI', 'CB00883', '8');
INSERT INTO `carrera_materia` VALUES ('LATI', 'CB00899', '6');
INSERT INTO `carrera_materia` VALUES ('LATI', 'CS00883', '9');
INSERT INTO `carrera_materia` VALUES ('LATI', 'IS00882', '6');
INSERT INTO `carrera_materia` VALUES ('LATI', 'MR1001', '5');
INSERT INTO `carrera_materia` VALUES ('LATI', 'SI00003-CLIN', '4');
INSERT INTO `carrera_materia` VALUES ('LATI', 'SI00004-CLIN', '5');
INSERT INTO `carrera_materia` VALUES ('LATI', 'SI00865', '6');
INSERT INTO `carrera_materia` VALUES ('LATI', 'SI00866', '4');
INSERT INTO `carrera_materia` VALUES ('LATI', 'SI00868', '5');
INSERT INTO `carrera_materia` VALUES ('LATI', 'SI00877', '6');
INSERT INTO `carrera_materia` VALUES ('LATI', 'SI00886', '4');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TC1001', '3');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TC3003', '4');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TC3021', '5');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TC3037', '3');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TC3041', '5');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TC3043', '3');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TC3044', '4');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI1000', '2');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI1001', '2');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI1002', '1');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI1004', '1');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI1005', '2');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI1007', '3');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI2000', '1');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI2001', '2');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI2002', '1');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI2003', '1');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI2004', '1');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI2006', '1');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI2007', '2');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI2009', '3');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI3001', '3');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI3002', '2');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI3010', '4');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI3014', '3');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI3023', '5');
INSERT INTO `carrera_materia` VALUES ('LATI', 'TI3024', '4');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'CB00821', '6');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'CB00831', '6');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'CB00841', '4');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'CB00858', '6');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'CB00881', '4');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'CB00883', '3');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'CB00899', '2');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'CS00883', '3');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'IS00882', '4');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'MR1001', '5');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'SI00003-CLIN', '4');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'SI00004-CLIN', '3');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'SI00865', '2');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'SI00866', '2');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'SI00868', '1');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'SI00877', '1');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'SI00886', '1');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC1001', '1');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC1002', '1');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC1004', '1');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC1005', '2');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC1006', '3');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC1007', '4');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC1009', '5');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC1010', '6');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC1011', '7');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC1012', '4');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC2001', '9');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC2002', '8');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC2003', '7');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC2004', '6');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC2005', '6');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC2006', '5');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC2008', '6');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC2009', '7');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC2010', '8');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC2011', '9');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC2012', '8');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC2013', '7');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC2014', '6');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC3003', '5');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TC3021', '5');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TI2001', '4');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TI2002', '3');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TI2003', '2');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TI2004', '3');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TI2006', '2');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TI2007', '1');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TI2009', '2');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TI3001', '3');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TI3002', '2');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TI3010', '2');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TI3014', '2');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TI3023', '3');
INSERT INTO `carrera_materia` VALUES ('ITIC', 'TI3024', '4');
INSERT INTO `carrera_materia` VALUES ('ISC', 'CB00821', '5');
INSERT INTO `carrera_materia` VALUES ('ISC', 'CB00831', '5');
INSERT INTO `carrera_materia` VALUES ('ISC', 'CB00841', '5');
INSERT INTO `carrera_materia` VALUES ('ISC', 'CB00858', '5');
INSERT INTO `carrera_materia` VALUES ('ISC', 'CB00881', '5');
INSERT INTO `carrera_materia` VALUES ('ISC', 'CB00883', '6');
INSERT INTO `carrera_materia` VALUES ('ISC', 'CB00899', '6');
INSERT INTO `carrera_materia` VALUES ('ISC', 'CS00883', '6');
INSERT INTO `carrera_materia` VALUES ('ISC', 'IS00882', '6');
INSERT INTO `carrera_materia` VALUES ('ISC', 'MR1001', '6');
INSERT INTO `carrera_materia` VALUES ('ISC', 'SI00003-CLIN', '6');
INSERT INTO `carrera_materia` VALUES ('ISC', 'SI00004-CLIN', '7');
INSERT INTO `carrera_materia` VALUES ('ISC', 'SI00865', '7');
INSERT INTO `carrera_materia` VALUES ('ISC', 'SI00866', '7');
INSERT INTO `carrera_materia` VALUES ('ISC', 'SI00868', '7');
INSERT INTO `carrera_materia` VALUES ('ISC', 'SI00877', '7');
INSERT INTO `carrera_materia` VALUES ('ISC', 'SI00886', '7');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC1001', '7');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC1002', '8');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC1004', '8');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC1005', '8');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC1006', '8');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC1007', '8');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC1009', '8');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC1010', '8');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC1011', '9');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC1012', '9');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC2001', '9');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC2002', '9');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC2003', '9');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC2004', '9');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC2005', '9');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC2006', '1');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC2008', '1');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC2009', '1');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC2010', '1');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC2011', '1');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC2012', '1');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC2013', '1');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC2014', '2');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC3003', '2');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC3021', '2');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC3037', '2');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC3041', '2');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC3043', '2');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TC3044', '2');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TI1000', '2');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TI1001', '3');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TI1002', '3');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TI1004', '3');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TI1005', '3');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TI1007', '3');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TI2000', '3');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TI2001', '3');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TI2002', '3');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TI2003', '4');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TI2004', '4');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TI2006', '4');
INSERT INTO `carrera_materia` VALUES ('ISC', 'TI2007', '4');

-- ----------------------------
-- Table structure for `dia`
-- ----------------------------
DROP TABLE IF EXISTS `dia`;
CREATE TABLE `dia` (
  `Dia_Id` int(11) NOT NULL AUTO_INCREMENT,
  `Dia_Nombre` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`Dia_Id`)
) ENGINE=MyISAM AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of dia
-- ----------------------------
INSERT INTO `dia` VALUES ('1', 'L');
INSERT INTO `dia` VALUES ('2', 'M');
INSERT INTO `dia` VALUES ('3', 'W');
INSERT INTO `dia` VALUES ('4', 'J');
INSERT INTO `dia` VALUES ('5', 'V');
INSERT INTO `dia` VALUES ('6', 'S');
INSERT INTO `dia` VALUES ('7', 'LJ');
INSERT INTO `dia` VALUES ('8', 'MV');
INSERT INTO `dia` VALUES ('9', 'LMWJV');

-- ----------------------------
-- Table structure for `grupo`
-- ----------------------------
DROP TABLE IF EXISTS `grupo`;
CREATE TABLE `grupo` (
  `Grupo_Id` int(11) NOT NULL AUTO_INCREMENT,
  `Materia_Id` varchar(8) DEFAULT NULL,
  PRIMARY KEY (`Grupo_Id`),
  KEY `Materia_Id` (`Materia_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of grupo
-- ----------------------------

-- ----------------------------
-- Table structure for `hora`
-- ----------------------------
DROP TABLE IF EXISTS `hora`;
CREATE TABLE `hora` (
  `Hora_Id` int(11) NOT NULL DEFAULT '0',
  `Hora_Nombre` int(11) DEFAULT NULL,
  PRIMARY KEY (`Hora_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of hora
-- ----------------------------
INSERT INTO `hora` VALUES ('1', '700');
INSERT INTO `hora` VALUES ('2', '730');
INSERT INTO `hora` VALUES ('3', '800');
INSERT INTO `hora` VALUES ('4', '830');
INSERT INTO `hora` VALUES ('5', '900');
INSERT INTO `hora` VALUES ('6', '930');
INSERT INTO `hora` VALUES ('7', '1000');
INSERT INTO `hora` VALUES ('8', '1030');
INSERT INTO `hora` VALUES ('9', '1100');
INSERT INTO `hora` VALUES ('10', '1130');
INSERT INTO `hora` VALUES ('11', '1200');
INSERT INTO `hora` VALUES ('12', '1230');
INSERT INTO `hora` VALUES ('13', '1300');
INSERT INTO `hora` VALUES ('14', '1330');
INSERT INTO `hora` VALUES ('15', '1400');
INSERT INTO `hora` VALUES ('16', '1430');
INSERT INTO `hora` VALUES ('17', '1500');
INSERT INTO `hora` VALUES ('18', '1530');
INSERT INTO `hora` VALUES ('19', '1600');
INSERT INTO `hora` VALUES ('20', '1630');
INSERT INTO `hora` VALUES ('21', '1700');
INSERT INTO `hora` VALUES ('22', '1730');
INSERT INTO `hora` VALUES ('23', '1800');
INSERT INTO `hora` VALUES ('24', '1830');
INSERT INTO `hora` VALUES ('25', '1900');
INSERT INTO `hora` VALUES ('26', '1930');
INSERT INTO `hora` VALUES ('27', '2000');
INSERT INTO `hora` VALUES ('28', '2030');
INSERT INTO `hora` VALUES ('29', '2100');
INSERT INTO `hora` VALUES ('30', '2130');
INSERT INTO `hora` VALUES ('31', '2200');

-- ----------------------------
-- Table structure for `horarios_disp`
-- ----------------------------
DROP TABLE IF EXISTS `horarios_disp`;
CREATE TABLE `horarios_disp` (
  `Horario_Disp_Id` int(11) NOT NULL DEFAULT '0',
  `Dia_Id` varchar(2) DEFAULT NULL,
  `Hora_Inicio` int(11) DEFAULT NULL,
  `Hora_Fin` int(11) DEFAULT NULL,
  PRIMARY KEY (`Horario_Disp_Id`),
  KEY `Dia_Id` (`Dia_Id`),
  KEY `Hora_Inicio` (`Hora_Inicio`),
  KEY `Hora_Fin` (`Hora_Fin`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of horarios_disp
-- ----------------------------
INSERT INTO `horarios_disp` VALUES ('1', '1', '23', '29');
INSERT INTO `horarios_disp` VALUES ('2', '2', '23', '29');
INSERT INTO `horarios_disp` VALUES ('3', '3', '23', '29');
INSERT INTO `horarios_disp` VALUES ('4', '4', '23', '29');
INSERT INTO `horarios_disp` VALUES ('5', '5', '23', '29');
INSERT INTO `horarios_disp` VALUES ('6', '6', '1', '7');
INSERT INTO `horarios_disp` VALUES ('7', '6', '7', '13');
INSERT INTO `horarios_disp` VALUES ('8', '7', '1', '4');
INSERT INTO `horarios_disp` VALUES ('9', '7', '4', '7');
INSERT INTO `horarios_disp` VALUES ('10', '7', '7', '10');
INSERT INTO `horarios_disp` VALUES ('11', '7', '10', '13');
INSERT INTO `horarios_disp` VALUES ('12', '7', '13', '16');
INSERT INTO `horarios_disp` VALUES ('13', '7', '16', '19');
INSERT INTO `horarios_disp` VALUES ('14', '7', '19', '22');
INSERT INTO `horarios_disp` VALUES ('15', '8', '1', '4');
INSERT INTO `horarios_disp` VALUES ('16', '8', '4', '7');
INSERT INTO `horarios_disp` VALUES ('17', '8', '7', '10');
INSERT INTO `horarios_disp` VALUES ('18', '8', '10', '13');
INSERT INTO `horarios_disp` VALUES ('19', '8', '13', '16');
INSERT INTO `horarios_disp` VALUES ('20', '8', '16', '19');
INSERT INTO `horarios_disp` VALUES ('21', '8', '19', '22');

-- ----------------------------
-- Table structure for `lugar`
-- ----------------------------
DROP TABLE IF EXISTS `lugar`;
CREATE TABLE `lugar` (
  `Lugar_Id` int(11) NOT NULL AUTO_INCREMENT,
  `Capacidad` int(11) DEFAULT NULL,
  `Tipo` varchar(2) DEFAULT NULL,
  `Codigo` varchar(11) DEFAULT NULL,
  PRIMARY KEY (`Lugar_Id`)
) ENGINE=MyISAM AUTO_INCREMENT=135 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of lugar
-- ----------------------------
INSERT INTO `lugar` VALUES ('1', '40', 'S', '4101');
INSERT INTO `lugar` VALUES ('2', '35', 'S', '4102');
INSERT INTO `lugar` VALUES ('3', '35', 'S', '4103');
INSERT INTO `lugar` VALUES ('4', '35', 'S', '4104');
INSERT INTO `lugar` VALUES ('5', '35', 'S', '4105');
INSERT INTO `lugar` VALUES ('6', '35', 'S', '4106');
INSERT INTO `lugar` VALUES ('7', '35', 'S', '4107');
INSERT INTO `lugar` VALUES ('8', '35', 'S', '4108');
INSERT INTO `lugar` VALUES ('9', '35', 'S', '4109');
INSERT INTO `lugar` VALUES ('10', '35', 'S', '4110');
INSERT INTO `lugar` VALUES ('11', '35', 'S', '4111');
INSERT INTO `lugar` VALUES ('12', '40', 'S', '4112');
INSERT INTO `lugar` VALUES ('13', '40', 'S', '4201');
INSERT INTO `lugar` VALUES ('14', '35', 'S', '4202');
INSERT INTO `lugar` VALUES ('15', '35', 'S', '4203');
INSERT INTO `lugar` VALUES ('16', '35', 'S', '4204');
INSERT INTO `lugar` VALUES ('17', '35', 'S', '4205');
INSERT INTO `lugar` VALUES ('18', '35', 'S', '4206');
INSERT INTO `lugar` VALUES ('19', '35', 'S', '4207');
INSERT INTO `lugar` VALUES ('20', '35', 'S', '4208');
INSERT INTO `lugar` VALUES ('21', '35', 'S', '4209');
INSERT INTO `lugar` VALUES ('22', '40', 'S', '4301');
INSERT INTO `lugar` VALUES ('23', '35', 'S', '4302');
INSERT INTO `lugar` VALUES ('24', '35', 'S', '4303');
INSERT INTO `lugar` VALUES ('25', '35', 'S', '4304');
INSERT INTO `lugar` VALUES ('26', '35', 'S', '4305');
INSERT INTO `lugar` VALUES ('27', '35', 'S', '4306');
INSERT INTO `lugar` VALUES ('28', '35', 'S', '4307');
INSERT INTO `lugar` VALUES ('29', '35', 'S', '4308');
INSERT INTO `lugar` VALUES ('30', '35', 'S', '4309');
INSERT INTO `lugar` VALUES ('31', '35', 'S', '4310');
INSERT INTO `lugar` VALUES ('32', '35', 'S', '4311');
INSERT INTO `lugar` VALUES ('33', '40', 'S', '4312');
INSERT INTO `lugar` VALUES ('34', '15', 'S', '5010');
INSERT INTO `lugar` VALUES ('35', '42', 'S', '5103');
INSERT INTO `lugar` VALUES ('36', '42', 'S', '5104');
INSERT INTO `lugar` VALUES ('37', '42', 'S', '5105');
INSERT INTO `lugar` VALUES ('38', '42', 'S', '5106');
INSERT INTO `lugar` VALUES ('39', '36', 'S', '5108');
INSERT INTO `lugar` VALUES ('40', '36', 'S', '5109');
INSERT INTO `lugar` VALUES ('41', '36', 'S', '5110');
INSERT INTO `lugar` VALUES ('42', '52', 'S', '5111');
INSERT INTO `lugar` VALUES ('43', '60', 'S', '5201');
INSERT INTO `lugar` VALUES ('44', '42', 'S', '5202');
INSERT INTO `lugar` VALUES ('45', '42', 'S', '5203');
INSERT INTO `lugar` VALUES ('46', '42', 'S', '5204');
INSERT INTO `lugar` VALUES ('47', '42', 'S', '5205');
INSERT INTO `lugar` VALUES ('48', '42', 'S', '5206');
INSERT INTO `lugar` VALUES ('49', '36', 'S', '5208');
INSERT INTO `lugar` VALUES ('50', '36', 'S', '5209');
INSERT INTO `lugar` VALUES ('51', '36', 'S', '5210');
INSERT INTO `lugar` VALUES ('52', '52', 'S', '5211');
INSERT INTO `lugar` VALUES ('53', '60', 'S', '5301');
INSERT INTO `lugar` VALUES ('54', '42', 'S', '5302');
INSERT INTO `lugar` VALUES ('55', '42', 'S', '5303');
INSERT INTO `lugar` VALUES ('56', '42', 'S', '5304');
INSERT INTO `lugar` VALUES ('57', '42', 'S', '5305');
INSERT INTO `lugar` VALUES ('58', '42', 'S', '5306');
INSERT INTO `lugar` VALUES ('59', '36', 'S', '5308');
INSERT INTO `lugar` VALUES ('60', '36', 'S', '5309');
INSERT INTO `lugar` VALUES ('61', '36', 'S', '5310');
INSERT INTO `lugar` VALUES ('62', '52', 'S', '5311');
INSERT INTO `lugar` VALUES ('63', '32', 'S', '6101');
INSERT INTO `lugar` VALUES ('64', '32', 'S', '6102');
INSERT INTO `lugar` VALUES ('65', '44', 'S', '6103');
INSERT INTO `lugar` VALUES ('66', '128', 'S', '6104');
INSERT INTO `lugar` VALUES ('67', '133', 'S', '6105');
INSERT INTO `lugar` VALUES ('68', '123', 'S', '6106');
INSERT INTO `lugar` VALUES ('69', '24', 'S', '6107');
INSERT INTO `lugar` VALUES ('70', '32', 'S', '6108');
INSERT INTO `lugar` VALUES ('71', '32', 'S', '6109');
INSERT INTO `lugar` VALUES ('72', '32', 'S', '6110');
INSERT INTO `lugar` VALUES ('73', '18', 'S', '6201');
INSERT INTO `lugar` VALUES ('74', '19', 'S', '6202');
INSERT INTO `lugar` VALUES ('75', '20', 'S', '6205');
INSERT INTO `lugar` VALUES ('76', '24', 'S', '6206');
INSERT INTO `lugar` VALUES ('77', '125', 'S', '6207');
INSERT INTO `lugar` VALUES ('78', '16', 'S', '6208');
INSERT INTO `lugar` VALUES ('79', '16', 'S', '6209');
INSERT INTO `lugar` VALUES ('80', '18', 'S', '6210');
INSERT INTO `lugar` VALUES ('81', '24', 'S', '6301');
INSERT INTO `lugar` VALUES ('82', '12', 'S', '6303');
INSERT INTO `lugar` VALUES ('83', '30', 'S', '6304');
INSERT INTO `lugar` VALUES ('84', '12', 'S', '6305');
INSERT INTO `lugar` VALUES ('85', '30', 'S', '6306');
INSERT INTO `lugar` VALUES ('86', '32', 'S', '6307');
INSERT INTO `lugar` VALUES ('87', '32', 'S', '6308');
INSERT INTO `lugar` VALUES ('88', '32', 'S', '6309');
INSERT INTO `lugar` VALUES ('89', '32', 'S', '6310');
INSERT INTO `lugar` VALUES ('90', '32', 'S', '6311');
INSERT INTO `lugar` VALUES ('91', '15', 'L', 'S-A5L001');
INSERT INTO `lugar` VALUES ('92', '32', 'L', 'S-A5L010');
INSERT INTO `lugar` VALUES ('93', '14', 'L', 'S-CDT101L08');
INSERT INTO `lugar` VALUES ('94', '12', 'L', 'S-CDT102L01');
INSERT INTO `lugar` VALUES ('95', '16', 'L', 'S-CDT102L03');
INSERT INTO `lugar` VALUES ('96', '16', 'L', 'S-CDT102L04');
INSERT INTO `lugar` VALUES ('97', '6', 'L', 'S-CDT102L05');
INSERT INTO `lugar` VALUES ('98', '16', 'L', 'S-CDT102L07');
INSERT INTO `lugar` VALUES ('99', '10', 'L', 'S-CDT103L01');
INSERT INTO `lugar` VALUES ('100', '14', 'L', 'S-CDT103L02');
INSERT INTO `lugar` VALUES ('101', '24', 'L', 'S-CDT103L03');
INSERT INTO `lugar` VALUES ('102', '9', 'L', 'S-CDT103L04');
INSERT INTO `lugar` VALUES ('103', '12', 'L', 'S-CDT103L05');
INSERT INTO `lugar` VALUES ('104', '30', 'L', 'S-CDT104L01');
INSERT INTO `lugar` VALUES ('105', '18', 'L', 'S-CDT104L02');
INSERT INTO `lugar` VALUES ('106', '35', 'L', 'S-CDT104L03');
INSERT INTO `lugar` VALUES ('107', '36', 'L', 'S-CDT104L04');
INSERT INTO `lugar` VALUES ('108', '24', 'L', 'S-CDT104L05');
INSERT INTO `lugar` VALUES ('109', '16', 'L', 'S-CDT104L06');
INSERT INTO `lugar` VALUES ('110', '30', 'L', 'S-CDT105L02');
INSERT INTO `lugar` VALUES ('111', '10', 'L', 'S-CDT105L04');
INSERT INTO `lugar` VALUES ('112', '50', 'L', 'S-CDT105L15');
INSERT INTO `lugar` VALUES ('113', '0', 'L', 'S-CDT106L24');
INSERT INTO `lugar` VALUES ('114', '16', 'L', 'S-CDT202L13');
INSERT INTO `lugar` VALUES ('115', '16', 'L', 'S-CDT202L14');
INSERT INTO `lugar` VALUES ('116', '16', 'L', 'S-CDT202L15');
INSERT INTO `lugar` VALUES ('117', '16', 'L', 'S-CDT202L17');
INSERT INTO `lugar` VALUES ('118', '16', 'L', 'S-CDT203L02');
INSERT INTO `lugar` VALUES ('119', '16', 'L', 'S-CDT203L03');
INSERT INTO `lugar` VALUES ('120', '16', 'L', 'S-CDT203L04');
INSERT INTO `lugar` VALUES ('121', '17', 'L', 'S-CDT204L01');
INSERT INTO `lugar` VALUES ('122', '18', 'L', 'S-CDT204L02');
INSERT INTO `lugar` VALUES ('123', '18', 'L', 'S-CDT204L03');
INSERT INTO `lugar` VALUES ('124', '15', 'L', 'S-CDT204L04');
INSERT INTO `lugar` VALUES ('125', '15', 'L', 'S-CDT204L05');
INSERT INTO `lugar` VALUES ('126', '15', 'L', 'S-CDT204L06');
INSERT INTO `lugar` VALUES ('127', '18', 'L', 'S-CDT204L07');
INSERT INTO `lugar` VALUES ('128', '0', 'L', 'S-CDT204L07');
INSERT INTO `lugar` VALUES ('129', '17', 'L', 'S-CDT204L08');
INSERT INTO `lugar` VALUES ('130', '34', 'L', 'S-CDT301L08');
INSERT INTO `lugar` VALUES ('131', '18', 'L', 'S-CDT301L10');
INSERT INTO `lugar` VALUES ('132', '14', 'L', 'S-CDT305L01');
INSERT INTO `lugar` VALUES ('133', '30', 'L', 'S-CDT306L02');
INSERT INTO `lugar` VALUES ('134', '12', 'L', 'S-LABCL101');

-- ----------------------------
-- Table structure for `materia`
-- ----------------------------
DROP TABLE IF EXISTS `materia`;
CREATE TABLE `materia` (
  `Materia_Id` varchar(20) NOT NULL DEFAULT '',
  `Materia_Nombre` varchar(100) DEFAULT NULL,
  `Materia_Division` varchar(5) DEFAULT NULL,
  `Materia_Horas` int(11) DEFAULT NULL,
  `Materia_Poblacion` int(11) DEFAULT NULL,
  PRIMARY KEY (`Materia_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of materia
-- ----------------------------
INSERT INTO `materia` VALUES ('CB00821', 'Computación para ingeniería', 'SI', '3', '50');
INSERT INTO `materia` VALUES ('CB00831', 'Estructura de datos', 'SI', '3', '20');
INSERT INTO `materia` VALUES ('CB00841', 'Teoría de lenguajes', 'SI', '3', '40');
INSERT INTO `materia` VALUES ('CB00858', 'Desarrollo de aplic con multim', 'SI', '3', '90');
INSERT INTO `materia` VALUES ('CB00881', 'Tecnologías de inf emergentes', 'SI', '3', '10');
INSERT INTO `materia` VALUES ('CB00883', 'Traductores', 'SI', '3', '80');
INSERT INTO `materia` VALUES ('CB00899', 'Clínica empresarial', 'SI', '3', '50');
INSERT INTO `materia` VALUES ('CS00883', 'Redes III', 'SI', '3', '20');
INSERT INTO `materia` VALUES ('IS00882', 'Tecnologías de información', 'SI', '3', '40');
INSERT INTO `materia` VALUES ('MR1001', 'Informática industrial', 'SI', '3', '90');
INSERT INTO `materia` VALUES ('SI00003-CLIN', 'Comercio electrónico', 'SI', '3', '10');
INSERT INTO `materia` VALUES ('SI00004-CLIN', 'Admon electrónic neg (e-bus)', 'SI', '3', '80');
INSERT INTO `materia` VALUES ('SI00865', 'Sist infor para toma decisione', 'SI', '3', '50');
INSERT INTO `materia` VALUES ('SI00866', 'Técnicas de produc de sistemas', 'SI', '3', '20');
INSERT INTO `materia` VALUES ('SI00868', 'Sist de apoyo admon y planeac', 'SI', '3', '40');
INSERT INTO `materia` VALUES ('SI00877', 'Planeac rec emp para neg elect', 'SI', '3', '90');
INSERT INTO `materia` VALUES ('SI00886', 'Admon de calidad de software', 'SI', '3', '10');
INSERT INTO `materia` VALUES ('TC1001', 'Introducción a la computación', 'SI', '3', '80');
INSERT INTO `materia` VALUES ('TC1002', 'Fundamentos de programación', 'SI', '3', '50');
INSERT INTO `materia` VALUES ('TC1004', 'Organización computacional', 'SI', '3', '20');
INSERT INTO `materia` VALUES ('TC1005', 'Programación y estruc de datos', 'SI', '3', '40');
INSERT INTO `materia` VALUES ('TC1006', 'Proy soluc probl con progr', 'SI', '3', '90');
INSERT INTO `materia` VALUES ('TC1007', 'Redes I', 'SI', '3', '10');
INSERT INTO `materia` VALUES ('TC1009', 'Desarr de sis y bases de datos', 'SI', '5', '80');
INSERT INTO `materia` VALUES ('TC1010', 'Programación para el diseño', 'SI', '3', '50');
INSERT INTO `materia` VALUES ('TC1011', 'Programación I', 'SI', '3', '20');
INSERT INTO `materia` VALUES ('TC1012', 'Programación II', 'SI', '3', '40');
INSERT INTO `materia` VALUES ('TC2001', 'Análisis y diseño de algorit', 'SI', '3', '90');
INSERT INTO `materia` VALUES ('TC2002', 'Redes II', 'SI', '3', '10');
INSERT INTO `materia` VALUES ('TC2003', 'Teoría de la computación', 'SI', '3', '80');
INSERT INTO `materia` VALUES ('TC2004', 'Anális y mod de sist softw', 'SI', '3', '50');
INSERT INTO `materia` VALUES ('TC2005', 'Desar de aplic distr', 'SI', '3', '20');
INSERT INTO `materia` VALUES ('TC2006', 'Lenguajes de programación', 'SI', '3', '40');
INSERT INTO `materia` VALUES ('TC2008', 'Sistemas operativos', 'SI', '3', '90');
INSERT INTO `materia` VALUES ('TC2009', 'Uso y admon de sis oper', 'SI', '3', '10');
INSERT INTO `materia` VALUES ('TC2010', 'Seguridad informática', 'SI', '3', '80');
INSERT INTO `materia` VALUES ('TC2011', 'Sistemas inteligentes', 'SI', '3', '50');
INSERT INTO `materia` VALUES ('TC2012', 'Proy de desarrollo softw', 'SI', '3', '20');
INSERT INTO `materia` VALUES ('TC2013', 'Introducción gráficas computac', 'SI', '3', '40');
INSERT INTO `materia` VALUES ('TC2014', 'Análisis y diseño d algoritmos', 'SI', '3', '90');
INSERT INTO `materia` VALUES ('TC3003', 'Diseño y arq de softw', 'SI', '3', '10');
INSERT INTO `materia` VALUES ('TC3021', 'Desarrollo de videojuegos', 'SI', '3', '80');
INSERT INTO `materia` VALUES ('TC3037', 'Programación avanzada', 'SI', '3', '50');
INSERT INTO `materia` VALUES ('TC3041', 'Bases de datos avanzadas', 'SI', '3', '20');
INSERT INTO `materia` VALUES ('TC3043', 'Sistemas operativos II', 'SI', '3', '40');
INSERT INTO `materia` VALUES ('TC3044', 'Aseguramiento de la cal. y p s', 'SI', '3', '90');
INSERT INTO `materia` VALUES ('TI1000', 'Computación', 'SI', '3', '10');
INSERT INTO `materia` VALUES ('TI1001', 'Introd interf y aplic Intern', 'SI', '3', '80');
INSERT INTO `materia` VALUES ('TI1002', 'Introd a prof tecn inf elect', 'SI', '2', '50');
INSERT INTO `materia` VALUES ('TI1004', 'Ciencia cognitiva', 'SI', '3', '20');
INSERT INTO `materia` VALUES ('TI1005', 'Proy de desar de sis info', 'SI', '3', '40');
INSERT INTO `materia` VALUES ('TI1007', 'Intr a Sistemas de Información', 'SI', '3', '90');
INSERT INTO `materia` VALUES ('TI2000', 'Gestión de tecnol de info', 'SI', '3', '10');
INSERT INTO `materia` VALUES ('TI2001', 'Negocios electrónicos', 'SI', '3', '80');
INSERT INTO `materia` VALUES ('TI2002', 'Admon de procesos de neg', 'SI', '3', '50');
INSERT INTO `materia` VALUES ('TI2003', 'Admon proy de tecn info elect', 'SI', '3', '20');
INSERT INTO `materia` VALUES ('TI2004', 'Interacc human-computad', 'SI', '2', '40');
INSERT INTO `materia` VALUES ('TI2006', 'Proy integ de elec compt e inf', 'SI', '3', '90');
INSERT INTO `materia` VALUES ('TI2007', 'Proy integr proc neg tom dec', 'SI', '3', '10');
INSERT INTO `materia` VALUES ('TI2009', 'B. D. para Toma de Decisiones', 'SI', '2', '80');
INSERT INTO `materia` VALUES ('TI3001', 'Planeación estrat de tecn inf', 'SI', '3', '50');
INSERT INTO `materia` VALUES ('TI3002', 'Auditoría de sistemas de infor', 'SI', '3', '20');
INSERT INTO `materia` VALUES ('TI3010', 'Admon del conocimiento', 'SI', '3', '40');
INSERT INTO `materia` VALUES ('TI3014', 'Proy integrad de tecn de info', 'SI', '3', '90');
INSERT INTO `materia` VALUES ('TI3023', 'Sistemas información empresari', 'SI', '2', '10');
INSERT INTO `materia` VALUES ('TI3024', 'Admón servic tecno información', 'SI', '3', '80');

-- ----------------------------
-- Table structure for `profesor`
-- ----------------------------
DROP TABLE IF EXISTS `profesor`;
CREATE TABLE `profesor` (
  `Profesor_Id` int(11) NOT NULL AUTO_INCREMENT,
  `Profesor_Nombre` varchar(50) DEFAULT NULL,
  `Profesor_Tipo` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`Profesor_Id`)
) ENGINE=MyISAM AUTO_INCREMENT=21 DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of profesor
-- ----------------------------
INSERT INTO `profesor` VALUES ('1', 'Ariel Ortiz Ramírez', 'Planta');
INSERT INTO `profesor` VALUES ('2', 'Jorge Ramírez Uresti', 'Planta');
INSERT INTO `profesor` VALUES ('3', 'Ángeles Junco Rey', 'Planta');
INSERT INTO `profesor` VALUES ('4', 'Isaac Rudomin Goldberg', 'Planta');
INSERT INTO `profesor` VALUES ('5', 'Alfonso Esparza', 'Planta');
INSERT INTO `profesor` VALUES ('6', 'Iván Escobar', 'Planta');
INSERT INTO `profesor` VALUES ('7', 'Miguel Salais', 'Planta');
INSERT INTO `profesor` VALUES ('8', 'María Elena Melón', 'Planta');
INSERT INTO `profesor` VALUES ('9', 'Marissa Díaz', 'Planta');
INSERT INTO `profesor` VALUES ('10', 'Edgar Vallejo', 'Planta');
INSERT INTO `profesor` VALUES ('11', 'Salvador Venegas', 'Cátedra');
INSERT INTO `profesor` VALUES ('12', 'Osvaldo Bulos', 'Cátedra');
INSERT INTO `profesor` VALUES ('13', 'Malto Magaña', 'Cátedra');
INSERT INTO `profesor` VALUES ('14', 'Pablo Suárez', 'Cátedra');
INSERT INTO `profesor` VALUES ('15', 'Antonio Morales', 'Cátedra');
INSERT INTO `profesor` VALUES ('16', 'Rogelio Ramírez', 'Cátedra');
INSERT INTO `profesor` VALUES ('17', 'Benjamín Sanchez', 'Cátedra');
INSERT INTO `profesor` VALUES ('18', 'Roberto Martínez', 'Cátedra');
INSERT INTO `profesor` VALUES ('19', 'Nora Erika Sanchez', 'Cátedra');
INSERT INTO `profesor` VALUES ('20', 'Claudia Bellido', 'Cátedra');

-- ----------------------------
-- Table structure for `profesor_horario`
-- ----------------------------
DROP TABLE IF EXISTS `profesor_horario`;
CREATE TABLE `profesor_horario` (
  `Horario_Disp_Id` int(11) NOT NULL DEFAULT '0',
  `Profesor_Id` int(11) NOT NULL DEFAULT '0',
  PRIMARY KEY (`Horario_Disp_Id`,`Profesor_Id`),
  KEY `Horario_Disp_Id` (`Horario_Disp_Id`),
  KEY `Profesor_Id` (`Profesor_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of profesor_horario
-- ----------------------------
INSERT INTO `profesor_horario` VALUES ('1', '1');
INSERT INTO `profesor_horario` VALUES ('1', '3');
INSERT INTO `profesor_horario` VALUES ('1', '4');
INSERT INTO `profesor_horario` VALUES ('1', '10');
INSERT INTO `profesor_horario` VALUES ('1', '13');
INSERT INTO `profesor_horario` VALUES ('1', '17');
INSERT INTO `profesor_horario` VALUES ('1', '18');
INSERT INTO `profesor_horario` VALUES ('1', '19');
INSERT INTO `profesor_horario` VALUES ('2', '2');
INSERT INTO `profesor_horario` VALUES ('2', '4');
INSERT INTO `profesor_horario` VALUES ('2', '8');
INSERT INTO `profesor_horario` VALUES ('2', '10');
INSERT INTO `profesor_horario` VALUES ('2', '13');
INSERT INTO `profesor_horario` VALUES ('2', '15');
INSERT INTO `profesor_horario` VALUES ('2', '16');
INSERT INTO `profesor_horario` VALUES ('2', '17');
INSERT INTO `profesor_horario` VALUES ('2', '19');
INSERT INTO `profesor_horario` VALUES ('3', '1');
INSERT INTO `profesor_horario` VALUES ('3', '3');
INSERT INTO `profesor_horario` VALUES ('3', '4');
INSERT INTO `profesor_horario` VALUES ('3', '6');
INSERT INTO `profesor_horario` VALUES ('3', '11');
INSERT INTO `profesor_horario` VALUES ('3', '18');
INSERT INTO `profesor_horario` VALUES ('3', '19');
INSERT INTO `profesor_horario` VALUES ('4', '2');
INSERT INTO `profesor_horario` VALUES ('4', '3');
INSERT INTO `profesor_horario` VALUES ('4', '4');
INSERT INTO `profesor_horario` VALUES ('4', '11');
INSERT INTO `profesor_horario` VALUES ('4', '17');
INSERT INTO `profesor_horario` VALUES ('4', '18');
INSERT INTO `profesor_horario` VALUES ('5', '1');
INSERT INTO `profesor_horario` VALUES ('5', '3');
INSERT INTO `profesor_horario` VALUES ('5', '4');
INSERT INTO `profesor_horario` VALUES ('5', '6');
INSERT INTO `profesor_horario` VALUES ('5', '8');
INSERT INTO `profesor_horario` VALUES ('5', '12');
INSERT INTO `profesor_horario` VALUES ('5', '16');
INSERT INTO `profesor_horario` VALUES ('5', '17');
INSERT INTO `profesor_horario` VALUES ('5', '19');
INSERT INTO `profesor_horario` VALUES ('6', '2');
INSERT INTO `profesor_horario` VALUES ('6', '6');
INSERT INTO `profesor_horario` VALUES ('6', '12');
INSERT INTO `profesor_horario` VALUES ('7', '3');
INSERT INTO `profesor_horario` VALUES ('7', '6');
INSERT INTO `profesor_horario` VALUES ('7', '9');
INSERT INTO `profesor_horario` VALUES ('7', '12');
INSERT INTO `profesor_horario` VALUES ('7', '16');
INSERT INTO `profesor_horario` VALUES ('8', '6');
INSERT INTO `profesor_horario` VALUES ('8', '12');
INSERT INTO `profesor_horario` VALUES ('8', '16');
INSERT INTO `profesor_horario` VALUES ('8', '19');
INSERT INTO `profesor_horario` VALUES ('9', '12');
INSERT INTO `profesor_horario` VALUES ('9', '20');
INSERT INTO `profesor_horario` VALUES ('10', '8');
INSERT INTO `profesor_horario` VALUES ('10', '13');
INSERT INTO `profesor_horario` VALUES ('10', '20');
INSERT INTO `profesor_horario` VALUES ('11', '6');
INSERT INTO `profesor_horario` VALUES ('11', '8');
INSERT INTO `profesor_horario` VALUES ('11', '15');
INSERT INTO `profesor_horario` VALUES ('11', '20');
INSERT INTO `profesor_horario` VALUES ('12', '9');
INSERT INTO `profesor_horario` VALUES ('12', '10');
INSERT INTO `profesor_horario` VALUES ('12', '18');
INSERT INTO `profesor_horario` VALUES ('12', '20');
INSERT INTO `profesor_horario` VALUES ('13', '7');
INSERT INTO `profesor_horario` VALUES ('13', '9');
INSERT INTO `profesor_horario` VALUES ('13', '20');
INSERT INTO `profesor_horario` VALUES ('14', '15');
INSERT INTO `profesor_horario` VALUES ('14', '18');
INSERT INTO `profesor_horario` VALUES ('14', '20');
INSERT INTO `profesor_horario` VALUES ('15', '5');
INSERT INTO `profesor_horario` VALUES ('15', '7');
INSERT INTO `profesor_horario` VALUES ('15', '9');
INSERT INTO `profesor_horario` VALUES ('15', '13');
INSERT INTO `profesor_horario` VALUES ('15', '20');
INSERT INTO `profesor_horario` VALUES ('16', '5');
INSERT INTO `profesor_horario` VALUES ('16', '7');
INSERT INTO `profesor_horario` VALUES ('16', '9');
INSERT INTO `profesor_horario` VALUES ('16', '14');
INSERT INTO `profesor_horario` VALUES ('16', '20');
INSERT INTO `profesor_horario` VALUES ('17', '2');
INSERT INTO `profesor_horario` VALUES ('17', '5');
INSERT INTO `profesor_horario` VALUES ('17', '7');
INSERT INTO `profesor_horario` VALUES ('17', '14');
INSERT INTO `profesor_horario` VALUES ('17', '20');
INSERT INTO `profesor_horario` VALUES ('18', '5');
INSERT INTO `profesor_horario` VALUES ('18', '9');
INSERT INTO `profesor_horario` VALUES ('18', '10');
INSERT INTO `profesor_horario` VALUES ('18', '18');
INSERT INTO `profesor_horario` VALUES ('18', '20');
INSERT INTO `profesor_horario` VALUES ('19', '5');
INSERT INTO `profesor_horario` VALUES ('19', '7');
INSERT INTO `profesor_horario` VALUES ('20', '1');
INSERT INTO `profesor_horario` VALUES ('20', '2');
INSERT INTO `profesor_horario` VALUES ('20', '15');
INSERT INTO `profesor_horario` VALUES ('21', '8');
INSERT INTO `profesor_horario` VALUES ('21', '14');

-- ----------------------------
-- Table structure for `profesor_materia`
-- ----------------------------
DROP TABLE IF EXISTS `profesor_materia`;
CREATE TABLE `profesor_materia` (
  `Materia_Id` varchar(20) NOT NULL DEFAULT '',
  `Profesor_Id` int(11) NOT NULL DEFAULT '0',
  `Prioridad` varchar(5) DEFAULT NULL,
  PRIMARY KEY (`Materia_Id`,`Profesor_Id`),
  KEY `Profesor_Id` (`Profesor_Id`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of profesor_materia
-- ----------------------------
INSERT INTO `profesor_materia` VALUES ('TI2004', '12', '1');
INSERT INTO `profesor_materia` VALUES ('TI2006', '15', '1');
INSERT INTO `profesor_materia` VALUES ('CB00821', '1', '1');
INSERT INTO `profesor_materia` VALUES ('CB00821', '18', '2');
INSERT INTO `profesor_materia` VALUES ('CB00821', '14', '3');
INSERT INTO `profesor_materia` VALUES ('CB00821', '8', '4');
INSERT INTO `profesor_materia` VALUES ('CB00831', '1', '1');
INSERT INTO `profesor_materia` VALUES ('CB00832', '8', '2');
INSERT INTO `profesor_materia` VALUES ('CB00833', '3', '3');
INSERT INTO `profesor_materia` VALUES ('CB00834', '19', '4');
INSERT INTO `profesor_materia` VALUES ('CB00835', '18', '5');
INSERT INTO `profesor_materia` VALUES ('CB00841', '5', '1');
INSERT INTO `profesor_materia` VALUES ('CB00841', '11', '2');
INSERT INTO `profesor_materia` VALUES ('CB00858', '5', '1');
INSERT INTO `profesor_materia` VALUES ('CB00858', '17', '2');
INSERT INTO `profesor_materia` VALUES ('CB00858', '19', '3');
INSERT INTO `profesor_materia` VALUES ('CB00881', '12', '1');
INSERT INTO `profesor_materia` VALUES ('CB00881', '15', '2');
INSERT INTO `profesor_materia` VALUES ('CB00881', '6', '3');
INSERT INTO `profesor_materia` VALUES ('CB00883', '16', '1');
INSERT INTO `profesor_materia` VALUES ('CB00883', '10', '2');
INSERT INTO `profesor_materia` VALUES ('CB00883', '7', '3');
INSERT INTO `profesor_materia` VALUES ('CB00899', '13', '1');
INSERT INTO `profesor_materia` VALUES ('CB00899', '20', '2');
INSERT INTO `profesor_materia` VALUES ('CS00883', '6', '1');
INSERT INTO `profesor_materia` VALUES ('CS00883', '19', '2');
INSERT INTO `profesor_materia` VALUES ('CS00883', '9', '3');
INSERT INTO `profesor_materia` VALUES ('IS00882', '15', '1');
INSERT INTO `profesor_materia` VALUES ('IS00882', '9', '2');
INSERT INTO `profesor_materia` VALUES ('IS00882', '5', '3');
INSERT INTO `profesor_materia` VALUES ('MR1001', '7', '1');
INSERT INTO `profesor_materia` VALUES ('MR1001', '18', '2');
INSERT INTO `profesor_materia` VALUES ('SI00003-CLIN', '20', '1');
INSERT INTO `profesor_materia` VALUES ('SI00004-CLIN', '20', '1');
INSERT INTO `profesor_materia` VALUES ('SI00004-CLIN', '12', '2');
INSERT INTO `profesor_materia` VALUES ('SI00865', '8', '1');
INSERT INTO `profesor_materia` VALUES ('SI00865', '10', '2');
INSERT INTO `profesor_materia` VALUES ('SI00866', '12', '1');
INSERT INTO `profesor_materia` VALUES ('SI00866', '9', '2');
INSERT INTO `profesor_materia` VALUES ('SI00866', '8', '3');
INSERT INTO `profesor_materia` VALUES ('SI00868', '13', '1');
INSERT INTO `profesor_materia` VALUES ('SI00868', '9', '2');
INSERT INTO `profesor_materia` VALUES ('SI00868', '8', '3');
INSERT INTO `profesor_materia` VALUES ('SI00877', '20', '1');
INSERT INTO `profesor_materia` VALUES ('SI00877', '19', '2');
INSERT INTO `profesor_materia` VALUES ('SI00886', '1', '1');
INSERT INTO `profesor_materia` VALUES ('SI00886', '2', '2');
INSERT INTO `profesor_materia` VALUES ('SI00886', '3', '3');
INSERT INTO `profesor_materia` VALUES ('TC1001', '12', '1');
INSERT INTO `profesor_materia` VALUES ('TC1001', '16', '2');
INSERT INTO `profesor_materia` VALUES ('TC1002', '2', '1');
INSERT INTO `profesor_materia` VALUES ('TC1002', '10', '2');
INSERT INTO `profesor_materia` VALUES ('TC1002', '8', '3');
INSERT INTO `profesor_materia` VALUES ('TC1002', '5', '4');
INSERT INTO `profesor_materia` VALUES ('TC1002', '19', '1');
INSERT INTO `profesor_materia` VALUES ('TC1004', '17', '1');
INSERT INTO `profesor_materia` VALUES ('TC1005', '15', '1');
INSERT INTO `profesor_materia` VALUES ('TC1006', '4', '1');
INSERT INTO `profesor_materia` VALUES ('TC1007', '2', '1');
INSERT INTO `profesor_materia` VALUES ('TC1009', '15', '1');
INSERT INTO `profesor_materia` VALUES ('TC1009', '6', '2');
INSERT INTO `profesor_materia` VALUES ('TC1009', '8', '3');
INSERT INTO `profesor_materia` VALUES ('TC1009', '3', '4');
INSERT INTO `profesor_materia` VALUES ('TC1010', '1', '1');
INSERT INTO `profesor_materia` VALUES ('TC1010', '4', '2');
INSERT INTO `profesor_materia` VALUES ('TC1011', '7', '1');
INSERT INTO `profesor_materia` VALUES ('TC1012', '5', '1');
INSERT INTO `profesor_materia` VALUES ('TC2001', '3', '1');
INSERT INTO `profesor_materia` VALUES ('TC2002', '2', '1');
INSERT INTO `profesor_materia` VALUES ('TC2002', '12', '2');
INSERT INTO `profesor_materia` VALUES ('TC2002', '15', '3');
INSERT INTO `profesor_materia` VALUES ('TC2003', '4', '1');
INSERT INTO `profesor_materia` VALUES ('TC2004', '6', '1');
INSERT INTO `profesor_materia` VALUES ('TC2005', '19', '1');
INSERT INTO `profesor_materia` VALUES ('TC2006', '1', '1');
INSERT INTO `profesor_materia` VALUES ('TC2006', '17', '2');
INSERT INTO `profesor_materia` VALUES ('TC2006', '15', '3');
INSERT INTO `profesor_materia` VALUES ('TC2006', '14', '4');
INSERT INTO `profesor_materia` VALUES ('TC2008', '13', '1');
INSERT INTO `profesor_materia` VALUES ('TC2009', '12', '1');
INSERT INTO `profesor_materia` VALUES ('TC2010', '18', '1');
INSERT INTO `profesor_materia` VALUES ('TC2011', '4', '1');
INSERT INTO `profesor_materia` VALUES ('TC2011', '7', '2');
INSERT INTO `profesor_materia` VALUES ('TC2011', '2', '3');
INSERT INTO `profesor_materia` VALUES ('TC2012', '6', '1');
INSERT INTO `profesor_materia` VALUES ('TC2013', '13', '1');
INSERT INTO `profesor_materia` VALUES ('TC2014', '18', '1');
INSERT INTO `profesor_materia` VALUES ('TC3003', '13', '1');
INSERT INTO `profesor_materia` VALUES ('TC3021', '11', '1');
INSERT INTO `profesor_materia` VALUES ('TC3021', '2', '2');
INSERT INTO `profesor_materia` VALUES ('TC3021', '16', '3');
INSERT INTO `profesor_materia` VALUES ('TC3037', '1', '1');
INSERT INTO `profesor_materia` VALUES ('TC3037', '7', '1');
INSERT INTO `profesor_materia` VALUES ('TC3041', '2', '1');
INSERT INTO `profesor_materia` VALUES ('TC3043', '4', '1');
INSERT INTO `profesor_materia` VALUES ('TC3044', '5', '1');
INSERT INTO `profesor_materia` VALUES ('TI1000', '3', '2');
INSERT INTO `profesor_materia` VALUES ('TI1000', '13', '3');
INSERT INTO `profesor_materia` VALUES ('TI1001', '19', '1');
INSERT INTO `profesor_materia` VALUES ('TI1002', '20', '2');
INSERT INTO `profesor_materia` VALUES ('TI1004', '14', '3');
INSERT INTO `profesor_materia` VALUES ('TI1005', '5', '4');
INSERT INTO `profesor_materia` VALUES ('TI1005', '3', '2');
INSERT INTO `profesor_materia` VALUES ('TI1007', '8', '3');
INSERT INTO `profesor_materia` VALUES ('TI2000', '12', '1');
INSERT INTO `profesor_materia` VALUES ('TI2001', '11', '2');
INSERT INTO `profesor_materia` VALUES ('TI2002', '14', '1');
INSERT INTO `profesor_materia` VALUES ('TI2003', '1', '1');
INSERT INTO `profesor_materia` VALUES ('TI2003', '4', '2');
INSERT INTO `profesor_materia` VALUES ('TI2006', '20', '2');
INSERT INTO `profesor_materia` VALUES ('TI2006', '1', '3');
INSERT INTO `profesor_materia` VALUES ('TI2006', '4', '4');
INSERT INTO `profesor_materia` VALUES ('TI2007', '18', '1');
INSERT INTO `profesor_materia` VALUES ('TI2009', '7', '2');
INSERT INTO `profesor_materia` VALUES ('TI3001', '6', '3');
INSERT INTO `profesor_materia` VALUES ('TI3001', '5', '1');
INSERT INTO `profesor_materia` VALUES ('TI3001', '2', '2');
INSERT INTO `profesor_materia` VALUES ('TI3002', '1', '3');
INSERT INTO `profesor_materia` VALUES ('TI3010', '3', '4');
INSERT INTO `profesor_materia` VALUES ('TI3014', '9', '1');
INSERT INTO `profesor_materia` VALUES ('TI3023', '7', '2');
INSERT INTO `profesor_materia` VALUES ('TI3024', '12', '3');
INSERT INTO `profesor_materia` VALUES ('TI3024', '13', '1');
INSERT INTO `profesor_materia` VALUES ('TI3024', '15', '1');
