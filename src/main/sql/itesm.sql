/*
Navicat MySQL Data Transfer

Source Server         : MySQL
Source Server Version : 50045
Source Host           : localhost:3306
Source Database       : itesm

Target Server Type    : MYSQL
Target Server Version : 50045
File Encoding         : 65001

Date: 2009-11-21 00:50:39
*/

SET FOREIGN_KEY_CHECKS=0;
-- ----------------------------
-- Table structure for `alumno`
-- ----------------------------
DROP TABLE IF EXISTS `alumno`;
CREATE TABLE `alumno` (
  `Alumno_Id` int(11) NOT NULL default '0',
  `Alumno_Nombre` varchar(25) default NULL,
  `Alumno_Carrera` varchar(3) default NULL,
  `Alumno_Semestre` int(11) default NULL,
  PRIMARY KEY  (`Alumno_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of alumno
-- ----------------------------

-- ----------------------------
-- Table structure for `alumno_grupo`
-- ----------------------------
DROP TABLE IF EXISTS `alumno_grupo`;
CREATE TABLE `alumno_grupo` (
  `Grupo_Id` int(11) default NULL,
  `Alumno_Id` int(11) default NULL,
  KEY `Grupo_Id` (`Grupo_Id`),
  KEY `Alumno_Id` (`Alumno_Id`),
  CONSTRAINT `alumno_grupo_ibfk_1` FOREIGN KEY (`Grupo_Id`) REFERENCES `grupo` (`Grupo_Id`),
  CONSTRAINT `alumno_grupo_ibfk_2` FOREIGN KEY (`Alumno_Id`) REFERENCES `alumno` (`Alumno_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of alumno_grupo
-- ----------------------------

-- ----------------------------
-- Table structure for `asignacion`
-- ----------------------------
DROP TABLE IF EXISTS `asignacion`;
CREATE TABLE `asignacion` (
  `Grupo_Id` int(11) default NULL,
  `Profesor_Id` int(11) default NULL,
  `Lugar_Id` int(11) default NULL,
  `Horario_Disp_Id` int(11) default NULL,
  KEY `Grupo_Id` (`Grupo_Id`),
  KEY `Profesor_Id` (`Profesor_Id`),
  KEY `Lugar_Id` (`Lugar_Id`),
  KEY `Horario_Disp_Id` (`Horario_Disp_Id`),
  CONSTRAINT `asignacion_ibfk_1` FOREIGN KEY (`Grupo_Id`) REFERENCES `grupo` (`Grupo_Id`),
  CONSTRAINT `asignacion_ibfk_2` FOREIGN KEY (`Profesor_Id`) REFERENCES `profesor` (`Profesor_Id`),
  CONSTRAINT `asignacion_ibfk_3` FOREIGN KEY (`Lugar_Id`) REFERENCES `lugar` (`Lugar_Id`),
  CONSTRAINT `asignacion_ibfk_4` FOREIGN KEY (`Horario_Disp_Id`) REFERENCES `horarios_disp` (`Horario_Disp_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of asignacion
-- ----------------------------

-- ----------------------------
-- Table structure for `carrera`
-- ----------------------------
DROP TABLE IF EXISTS `carrera`;
CREATE TABLE `carrera` (
  `Carrera_Id` varchar(3) NOT NULL default '',
  `Carrera_Nombre` varchar(25) default NULL,
  PRIMARY KEY  (`Carrera_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of carrera
-- ----------------------------

-- ----------------------------
-- Table structure for `carrera_materia`
-- ----------------------------
DROP TABLE IF EXISTS `carrera_materia`;
CREATE TABLE `carrera_materia` (
  `Carrera_Id` varchar(3) default NULL,
  `Materia_Id` varchar(8) default NULL,
  KEY `Carrera_Id` (`Carrera_Id`),
  KEY `Materia_Id` (`Materia_Id`),
  CONSTRAINT `carrera_materia_ibfk_1` FOREIGN KEY (`Carrera_Id`) REFERENCES `carrera` (`Carrera_Id`),
  CONSTRAINT `carrera_materia_ibfk_2` FOREIGN KEY (`Materia_Id`) REFERENCES `materia` (`Materia_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of carrera_materia
-- ----------------------------

-- ----------------------------
-- Table structure for `dia`
-- ----------------------------
DROP TABLE IF EXISTS `dia`;
CREATE TABLE `dia` (
  `Dia_Id` varchar(2) NOT NULL default '',
  `Dia_Nombre` varchar(10) default NULL,
  PRIMARY KEY  (`Dia_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of dia
-- ----------------------------

-- ----------------------------
-- Table structure for `grupo`
-- ----------------------------
DROP TABLE IF EXISTS `grupo`;
CREATE TABLE `grupo` (
  `Grupo_Id` int(11) NOT NULL default '0',
  `Materia_Id` varchar(8) default NULL,
  `Max_Alumnos` int(11) default NULL,
  PRIMARY KEY  (`Grupo_Id`),
  KEY `Materia_Id` (`Materia_Id`),
  CONSTRAINT `grupo_ibfk_1` FOREIGN KEY (`Materia_Id`) REFERENCES `materia` (`Materia_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of grupo
-- ----------------------------

-- ----------------------------
-- Table structure for `hora`
-- ----------------------------
DROP TABLE IF EXISTS `hora`;
CREATE TABLE `hora` (
  `Hora_Id` int(11) NOT NULL default '0',
  `Hora_Nombre` int(11) default NULL,
  PRIMARY KEY  (`Hora_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of hora
-- ----------------------------

-- ----------------------------
-- Table structure for `horario_profesor`
-- ----------------------------
DROP TABLE IF EXISTS `horario_profesor`;
CREATE TABLE `horario_profesor` (
  `Horario_Disp_Id` int(11) default NULL,
  `Profesor_Id` int(11) default NULL,
  KEY `Horario_Disp_Id` (`Horario_Disp_Id`),
  KEY `Profesor_Id` (`Profesor_Id`),
  CONSTRAINT `horario_profesor_ibfk_1` FOREIGN KEY (`Horario_Disp_Id`) REFERENCES `horarios_disp` (`Horario_Disp_Id`),
  CONSTRAINT `horario_profesor_ibfk_2` FOREIGN KEY (`Profesor_Id`) REFERENCES `profesor` (`Profesor_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of horario_profesor
-- ----------------------------

-- ----------------------------
-- Table structure for `horarios_disp`
-- ----------------------------
DROP TABLE IF EXISTS `horarios_disp`;
CREATE TABLE `horarios_disp` (
  `Horario_Disp_Id` int(11) NOT NULL default '0',
  `Dia_Id` varchar(2) default NULL,
  `Hora_Inicio` int(11) default NULL,
  `Hora_Fin` int(11) default NULL,
  PRIMARY KEY  (`Horario_Disp_Id`),
  KEY `Dia_Id` (`Dia_Id`),
  KEY `Hora_Inicio` (`Hora_Inicio`),
  KEY `Hora_Fin` (`Hora_Fin`),
  CONSTRAINT `horarios_disp_ibfk_1` FOREIGN KEY (`Dia_Id`) REFERENCES `dia` (`Dia_Id`),
  CONSTRAINT `horarios_disp_ibfk_2` FOREIGN KEY (`Hora_Inicio`) REFERENCES `hora` (`Hora_Id`),
  CONSTRAINT `horarios_disp_ibfk_3` FOREIGN KEY (`Hora_Fin`) REFERENCES `hora` (`Hora_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of horarios_disp
-- ----------------------------

-- ----------------------------
-- Table structure for `laboratorio`
-- ----------------------------
DROP TABLE IF EXISTS `laboratorio`;
CREATE TABLE `laboratorio` (
  `Laboratorio_Id` int(11) NOT NULL default '0',
  `Lugar_Id` int(11) default NULL,
  `Capacidad` int(11) default NULL,
  PRIMARY KEY  (`Laboratorio_Id`),
  KEY `Lugar_Id` (`Lugar_Id`),
  CONSTRAINT `laboratorio_ibfk_1` FOREIGN KEY (`Lugar_Id`) REFERENCES `lugar` (`Lugar_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of laboratorio
-- ----------------------------

-- ----------------------------
-- Table structure for `lugar`
-- ----------------------------
DROP TABLE IF EXISTS `lugar`;
CREATE TABLE `lugar` (
  `Lugar_Id` int(11) NOT NULL default '0',
  PRIMARY KEY  (`Lugar_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of lugar
-- ----------------------------

-- ----------------------------
-- Table structure for `materia`
-- ----------------------------
DROP TABLE IF EXISTS `materia`;
CREATE TABLE `materia` (
  `Materia_Id` varchar(8) NOT NULL default '',
  `Materia_Horas` int(11) default NULL,
  PRIMARY KEY  (`Materia_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of materia
-- ----------------------------

-- ----------------------------
-- Table structure for `profesor`
-- ----------------------------
DROP TABLE IF EXISTS `profesor`;
CREATE TABLE `profesor` (
  `Profesor_Id` int(11) NOT NULL default '0',
  `Profesor_Nombre` varchar(20) default NULL,
  `Profesor_Tipo` varchar(1) default NULL,
  PRIMARY KEY  (`Profesor_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of profesor
-- ----------------------------

-- ----------------------------
-- Table structure for `profesor_materia`
-- ----------------------------
DROP TABLE IF EXISTS `profesor_materia`;
CREATE TABLE `profesor_materia` (
  `Asignatura_Id` int(11) NOT NULL default '0',
  `Materia_Id` varchar(8) default NULL,
  `Profesor_Id` int(11) default NULL,
  `Prioridad` varchar(5) default NULL,
  PRIMARY KEY  (`Asignatura_Id`),
  KEY `Materia_Id` (`Materia_Id`),
  KEY `Profesor_Id` (`Profesor_Id`),
  CONSTRAINT `profesor_materia_ibfk_1` FOREIGN KEY (`Materia_Id`) REFERENCES `materia` (`Materia_Id`),
  CONSTRAINT `profesor_materia_ibfk_2` FOREIGN KEY (`Profesor_Id`) REFERENCES `profesor` (`Profesor_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of profesor_materia
-- ----------------------------

-- ----------------------------
-- Table structure for `salon`
-- ----------------------------
DROP TABLE IF EXISTS `salon`;
CREATE TABLE `salon` (
  `Salon_Id` int(11) NOT NULL default '0',
  `Lugar_Id` int(11) default NULL,
  `Capacidad` int(11) default NULL,
  PRIMARY KEY  (`Salon_Id`),
  KEY `Lugar_Id` (`Lugar_Id`),
  CONSTRAINT `salon_ibfk_1` FOREIGN KEY (`Lugar_Id`) REFERENCES `lugar` (`Lugar_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of salon
-- ----------------------------

-- ----------------------------
-- Table structure for `semestre`
-- ----------------------------
DROP TABLE IF EXISTS `semestre`;
CREATE TABLE `semestre` (
  `Semestre_Id` int(11) NOT NULL default '0',
  `Comentario` varchar(20) default NULL,
  PRIMARY KEY  (`Semestre_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

-- ----------------------------
-- Records of semestre
-- ----------------------------
