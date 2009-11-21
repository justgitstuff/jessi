
CREATE DATABASE Itesm;

USE Itesm;

CREATE TABLE Profesor(
	Profesor_Id INT,
	Profesor_Nombre varchar(20),
	Profesor_Tipo varchar(1),
	PRIMARY KEY(Profesor_Id)
);

CREATE TABLE Alumno(
	Alumno_Id INT,
	Alumno_Nombre varchar(25),
	Alumno_Carrera varchar(3),
	Alumno_Semestre INT,
	PRIMARY KEY(Alumno_Id)
);

CREATE TABLE Materia(
	Materia_Id VARCHAR(8),
	Materia_Horas INT,
	PRIMARY KEY(Materia_Id)
);

CREATE TABLE Grupo(
	Grupo_Id INT,
	Materia_Id VARCHAR(8),
	Max_Alumnos INT,
	PRIMARY KEY(Grupo_Id),
	FOREIGN KEY(Materia_Id) REFERENCES Materia(Materia_Id)
);

CREATE TABLE Alumno_Grupo(
	Grupo_Id INT,
	Alumno_Id INT,
	FOREIGN KEY(Grupo_Id) REFERENCES Grupo(Grupo_Id),
	FOREIGN KEY(Alumno_Id) REFERENCES Alumno(Alumno_Id)
);

CREATE TABLE Lugar(
	Lugar_Id INT,
	PRIMARY KEY(Lugar_Id)
);

CREATE TABLE Salon(
	Salon_Id INT,
	Lugar_Id INT,
	Capacidad INT,
	PRIMARY KEY(Salon_Id),
	FOREIGN KEY(Lugar_Id) REFERENCES Lugar(Lugar_Id)
);

CREATE TABLE Laboratorio(
	Laboratorio_Id INT,
	Lugar_Id INT,
	Capacidad INT,
	PRIMARY KEY(Laboratorio_Id),
	FOREIGN KEY(Lugar_Id) REFERENCES Lugar(Lugar_Id)
);

CREATE TABLE Dia(
	Dia_Id VARCHAR(2),
	Dia_Nombre VARCHAR(10),
	PRIMARY KEY(Dia_Id)
);

CREATE TABLE Hora(
	Hora_Id INT,
	Hora_Nombre INT,
	PRIMARY KEY(Hora_Id)
);

CREATE TABLE Horarios_Disp(
	Horario_Disp_Id INT,
	Dia_Id VARCHAR(2),
	Hora_Inicio INT,
	Hora_Fin INT,
	PRIMARY KEY(Horario_Disp_Id),
	FOREIGN KEY(Dia_Id) REFERENCES Dia(Dia_Id),
	FOREIGN KEY(Hora_Inicio) REFERENCES Hora(Hora_Id),
	FOREIGN KEY(Hora_Fin) REFERENCES Hora(Hora_Id)
);

CREATE TABLE Asignacion(
	Grupo_Id INT,
	Profesor_Id INT,
	Lugar_Id INT,
	Horario_Disp_Id INT,
	FOREIGN KEY(Grupo_Id) REFERENCES Grupo(Grupo_Id),
	FOREIGN KEY(Profesor_Id) REFERENCES Profesor(Profesor_Id),
	FOREIGN KEY(Lugar_Id) REFERENCES Lugar(Lugar_Id),
	FOREIGN KEY(Horario_Disp_Id) REFERENCES Horarios_Disp(Horario_Disp_Id)
);

CREATE TABLE Semestre(
	Semestre_Id INT,
	Comentario VARCHAR(20),
	PRIMARY KEY(Semestre_Id)
);

CREATE TABLE Carrera(
	Carrera_Id VARCHAR(3),
	Carrera_Nombre VARCHAR(25),
	PRIMARY KEY(Carrera_Id)
);

CREATE TABLE Carrera_Materia(
	Carrera_Id VARCHAR(3),
	Materia_Id VARCHAR(8),
	FOREIGN KEY(Carrera_Id) REFERENCES Carrera(Carrera_Id),
	FOREIGN KEY(Materia_Id) REFERENCES Materia(Materia_Id)
);

CREATE TABLE Asignatura(
	Asignatura_Id INT,
	Materia_Id VARCHAR(8),
	Profesor_Id INT,
	Prioridad VARCHAR(5),
	PRIMARY KEY(Asignatura_Id),
	FOREIGN KEY(Materia_Id) REFERENCES Materia(Materia_Id),
	FOREIGN KEY(Profesor_Id) REFERENCES Profesor(Profesor_Id)
);

CREATE TABLE Horario_Profesor(
	Horario_Disp_Id INT,
	Profesor_Id INT,
	FOREIGN KEY(Horario_Disp_Id) REFERENCES Horarios_Disp(Horario_Disp_Id),
	FOREIGN KEY(Profesor_Id) REFERENCES Profesor(Profesor_Id)
);



