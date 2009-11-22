-- MySQL Administrator dump 1.4
--
-- ------------------------------------------------------
-- Server version   5.0.81-community-nt


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


--
-- Create schema itesm
--

CREATE DATABASE IF NOT EXISTS itesm;
USE itesm;

--
-- Definition of table `assignation`
--

DROP TABLE IF EXISTS `assignation`;
CREATE TABLE `assignation` (
  `Class_Id` int(11) default NULL,
  `Teacher_Id` int(11) default NULL,
  `Location_Id` int(11) default NULL,
  `Horario` varchar(15) default NULL,
  KEY `Class_Id` (`Class_Id`),
  KEY `Teacher_Id` (`Teacher_Id`),
  KEY `Location_Id` (`Location_Id`),
  CONSTRAINT `assignation_ibfk_1` FOREIGN KEY (`Class_Id`) REFERENCES `class` (`Class_Id`),
  CONSTRAINT `assignation_ibfk_2` FOREIGN KEY (`Teacher_Id`) REFERENCES `teacher` (`Teacher_Id`),
  CONSTRAINT `assignation_ibfk_3` FOREIGN KEY (`Location_Id`) REFERENCES `location` (`Location_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `assignation`
--

/*!40000 ALTER TABLE `assignation` DISABLE KEYS */;
INSERT INTO `assignation` (`Class_Id`,`Teacher_Id`,`Location_Id`,`Horario`) VALUES 
 (1,908969,1,'16:00L'),
 (2,908970,2,'10:00Vi'),
 (3,908971,3,'17:00D'),
 (4,908972,4,'10:00Ju'),
 (5,908973,5,'16:00D'),
 (6,908974,6,'11:30Ma'),
 (7,908975,7,'9:00D'),
 (8,908976,8,'10:00D'),
 (9,908977,9,'10:00Lu'),
 (10,908978,10,'10:00Ma');
/*!40000 ALTER TABLE `assignation` ENABLE KEYS */;


--
-- Definition of table `class`
--

DROP TABLE IF EXISTS `class`;
CREATE TABLE `class` (
  `Class_Id` int(11) NOT NULL default '0',
  `Course_Id` int(11) default NULL,
  PRIMARY KEY  (`Class_Id`),
  KEY `Course_Id` (`Course_Id`),
  CONSTRAINT `class_ibfk_1` FOREIGN KEY (`Course_Id`) REFERENCES `course` (`Course_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `class`
--

/*!40000 ALTER TABLE `class` DISABLE KEYS */;
INSERT INTO `class` (`Class_Id`,`Course_Id`) VALUES 
 (1,831),
 (2,883),
 (3,892),
 (4,894),
 (5,1000),
 (6,1001),
 (7,1002),
 (8,1004),
 (9,1005),
 (10,2014);
/*!40000 ALTER TABLE `class` ENABLE KEYS */;


--
-- Definition of table `class_student`
--

DROP TABLE IF EXISTS `class_student`;
CREATE TABLE `class_student` (
  `Class_Id` int(11) default NULL,
  `Student_Id` int(11) default NULL,
  KEY `Class_Id` (`Class_Id`),
  KEY `Student_Id` (`Student_Id`),
  CONSTRAINT `class_student_ibfk_1` FOREIGN KEY (`Class_Id`) REFERENCES `class` (`Class_Id`),
  CONSTRAINT `class_student_ibfk_2` FOREIGN KEY (`Student_Id`) REFERENCES `student` (`Student_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `class_student`
--

/*!40000 ALTER TABLE `class_student` DISABLE KEYS */;
INSERT INTO `class_student` (`Class_Id`,`Student_Id`) VALUES 
 (1,457934),
 (3,462193),
 (2,460339),
 (4,462685),
 (5,462686),
 (6,462876),
 (7,463006),
 (8,467829),
 (9,475909),
 (10,968945);
/*!40000 ALTER TABLE `class_student` ENABLE KEYS */;


--
-- Definition of table `classroom`
--

DROP TABLE IF EXISTS `classroom`;
CREATE TABLE `classroom` (
  `Classroom_Id` int(11) NOT NULL default '0',
  `Location_Id` int(11) default NULL,
  `Capacity` int(11) default NULL,
  PRIMARY KEY  (`Classroom_Id`),
  KEY `Location_Id` (`Location_Id`),
  CONSTRAINT `classroom_ibfk_1` FOREIGN KEY (`Location_Id`) REFERENCES `location` (`Location_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `classroom`
--

/*!40000 ALTER TABLE `classroom` DISABLE KEYS */;
INSERT INTO `classroom` (`Classroom_Id`,`Location_Id`,`Capacity`) VALUES 
 (1,1,13),
 (2,2,15),
 (3,3,17),
 (4,4,19),
 (5,5,21),
 (6,6,23),
 (7,7,25),
 (8,8,27),
 (9,9,29),
 (10,10,31);
/*!40000 ALTER TABLE `classroom` ENABLE KEYS */;


--
-- Definition of table `course`
--

DROP TABLE IF EXISTS `course`;
CREATE TABLE `course` (
  `Course_Id` int(11) NOT NULL default '0',
  `Course_Name` varchar(25) default NULL,
  `Course_Div` varchar(5) default NULL,
  `Course_Dpto` varchar(5) default NULL,
  `Course_Students` int(11) default NULL,
  PRIMARY KEY  (`Course_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `course`
--

/*!40000 ALTER TABLE `course` DISABLE KEYS */;
INSERT INTO `course` (`Course_Id`,`Course_Name`,`Course_Div`,`Course_Dpto`,`Course_Students`) VALUES 
 (831,'Estructura de Datos','DIA','SI',15),
 (883,'Traductores','DIA','SI',13),
 (892,'Inversión Extranjera','DN','FZ',20),
 (894,'Administración Emp','DN','CF',7),
 (1000,'Dibujo Artístico','DCSH','CO',27),
 (1001,'Intro a la arquitectura','DIA','AR',10),
 (1002,'Dibujo Computarizado','DIA','M',25),
 (1004,'Perspectiva Internacional','DCSH','ESRI',19),
 (1005,'Inglés Remedial V','DP','PI',12),
 (2014,'Marcas y Productos','DN','NI',26);
/*!40000 ALTER TABLE `course` ENABLE KEYS */;


--
-- Definition of table `laboratory`
--

DROP TABLE IF EXISTS `laboratory`;
CREATE TABLE `laboratory` (
  `Laboratory_Id` int(11) NOT NULL default '0',
  `Location_Id` int(11) default NULL,
  `Capacity` int(11) default NULL,
  PRIMARY KEY  (`Laboratory_Id`),
  KEY `Location_Id` (`Location_Id`),
  CONSTRAINT `laboratory_ibfk_1` FOREIGN KEY (`Location_Id`) REFERENCES `location` (`Location_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `laboratory`
--

/*!40000 ALTER TABLE `laboratory` DISABLE KEYS */;
INSERT INTO `laboratory` (`Laboratory_Id`,`Location_Id`,`Capacity`) VALUES 
 (1,1,13),
 (2,2,15),
 (3,3,17),
 (4,4,19),
 (5,5,21),
 (6,6,23),
 (7,7,25),
 (8,8,27),
 (9,9,29),
 (10,10,31);
/*!40000 ALTER TABLE `laboratory` ENABLE KEYS */;


--
-- Definition of table `location`
--

DROP TABLE IF EXISTS `location`;
CREATE TABLE `location` (
  `Location_Id` int(11) NOT NULL default '0',
  PRIMARY KEY  (`Location_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `location`
--

/*!40000 ALTER TABLE `location` DISABLE KEYS */;
INSERT INTO `location` (`Location_Id`) VALUES 
 (1),
 (2),
 (3),
 (4),
 (5),
 (6),
 (7),
 (8),
 (9),
 (10);
/*!40000 ALTER TABLE `location` ENABLE KEYS */;


--
-- Definition of table `student`
--

DROP TABLE IF EXISTS `student`;
CREATE TABLE `student` (
  `Student_Id` int(11) NOT NULL default '0',
  `Student_Name` varchar(20) default NULL,
  `Student_Program` varchar(3) default NULL,
  `Student_Status` varchar(5) default NULL,
  `Student_Semester` int(11) default NULL,
  PRIMARY KEY  (`Student_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `student`
--

/*!40000 ALTER TABLE `student` DISABLE KEYS */;
INSERT INTO `student` (`Student_Id`,`Student_Name`,`Student_Program`,`Student_Status`,`Student_Semester`) VALUES 
 (457934,'Javier Pindter','ISE','REG',9),
 (460339,'Daanizu Félix','ISC','COND',5),
 (462193,'Antonio Castelazo','LRI','REG',7),
 (462685,'Montserrat López','ISC','REG',3),
 (462686,'Abraham López','LSC','reg',9),
 (462876,'Jimena Espinoza','LAE','REG',8),
 (463006,'René España','LDI','COND',6),
 (467829,'Elias Chavez','IIS','REG',9),
 (475909,'Genaro Navarro','ITC','REG',9),
 (968945,'Gabriela Carrillo','LAF','COND',6);
/*!40000 ALTER TABLE `student` ENABLE KEYS */;


--
-- Definition of table `teacher`
--

DROP TABLE IF EXISTS `teacher`;
CREATE TABLE `teacher` (
  `Teacher_Id` int(11) NOT NULL default '0',
  `Teacher_Name` varchar(20) default NULL,
  `Horario_disp` varchar(15) default NULL,
  PRIMARY KEY  (`Teacher_Id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `teacher`
--

/*!40000 ALTER TABLE `teacher` DISABLE KEYS */;
INSERT INTO `teacher` (`Teacher_Id`,`Teacher_Name`,`Horario_disp`) VALUES 
 (908969,'Paul Landers','15:00-18:00D'),
 (908970,'René Zurita','10:00-11:30Vi'),
 (908971,'Ariel Ortiz','9:00-18:00D'),
 (908972,'Juan Carlos','8:30-12Ju'),
 (908973,'Hector Ramirez','15:00-18:00D'),
 (908974,'Ricardo Gonzales','10:30-14:00Ma'),
 (908975,'Elena Melón','9:00-18:00D'),
 (908976,'Roberto Roman','9:00-18:00D'),
 (908977,'Jorge Uresti','8:30-12:00Lu'),
 (908978,'Juanito Landa','10:00-11:30Ma');
/*!40000 ALTER TABLE `teacher` ENABLE KEYS */;




/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
