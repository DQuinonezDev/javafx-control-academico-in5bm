						/* 	
Curso: Taller
Catedrático: Jorge Luis Pérez Canto

	Nombres: Eldrick Aldair Hernández Bautista----David Andre Quiñones Zeta
	Carné:	 2021099 - 2021045
	Código técnico: IN5BM
	Grupo: 1
	Fecha: 23/04/2022
*/

-- -----------------------------------------------------
-- Nombre de la base de datos: db_control_academico
-- -----------------------------------------------------
DROP DATABASE IF EXISTS db_control_academico_in5bm;
CREATE DATABASE IF NOT EXISTS db_control_academico_in5bm;				
USE db_control_academico_in5bm;

-- -----------------------------------------------------
-- Tabla: alumnos
-- -----------------------------------------------------
DROP TABLE IF EXISTS alumnos;
CREATE TABLE IF NOT EXISTS alumnos (
  carne VARCHAR(7) NOT NULL,
  nombre1 VARCHAR(15) NOT NULL,
  nombre2 VARCHAR(15) NULL,
  nombre3 VARCHAR(15) NULL,
  apellido1 VARCHAR(15) NOT NULL,
  apellido2 VARCHAR(15) NULL,
  CONSTRAINT pk_alumnos PRIMARY KEY (carne)
);

-- -----------------------------------------------------
-- Tabla: instructores
-- -----------------------------------------------------
DROP TABLE IF EXISTS instructores;
CREATE TABLE IF NOT EXISTS instructores (
  id INT AUTO_INCREMENT NOT NULL,
  nombre1 VARCHAR(15) NOT NULL,
  nombre2 VARCHAR(15) NULL,
  nombre3 VARCHAR(15) NULL,
  apellido1 VARCHAR(15) NOT NULL,
  apellido2 VARCHAR(15) NULL,
  direccion VARCHAR(45) NULL,
  email VARCHAR(45) NOT NULL,
  telefono VARCHAR(8) NOT NULL,
  fecha_nacimiento DATE NULL,
  CONSTRAINT pk_intructores PRIMARY KEY (id)
);



-- -----------------------------------------------------
-- Tabla: salones
-- -----------------------------------------------------
DROP TABLE IF EXISTS salones;
CREATE TABLE IF NOT EXISTS salones (
  codigo_salon VARCHAR(5) NOT NULL,
  descripcion VARCHAR(45) NULL,
  capacidad_maxima INT NOT NULL,
  edificio VARCHAR(15) NULL,
  nivel INT NULL,
  CONSTRAINT pk_salones PRIMARY KEY (codigo_salon)
);

-- -----------------------------------------------------
-- Tabla: carreras_tecnicas
-- -----------------------------------------------------
DROP TABLE IF EXISTS carreras_tecnicas;
CREATE TABLE IF NOT EXISTS carreras_tecnicas (
  codigo_tecnico VARCHAR(6) NOT NULL,
  carrera VARCHAR(45) NOT NULL,
  grado VARCHAR(10) NOT NULL,
  seccion CHAR(1) NOT NULL,
  jornada VARCHAR(10) NOT NULL,
  CONSTRAINT pk_carreras_tecnicas PRIMARY KEY (codigo_tecnico)
);

-- -----------------------------------------------------
-- Tabla: horarios
-- -----------------------------------------------------
DROP TABLE IF EXISTS horarios;
CREATE TABLE IF NOT EXISTS horarios (
 id INT AUTO_INCREMENT NOT NULL,
  horario_inicio TIME NOT NULL,
  horario_final TIME NOT NULL,
  lunes TINYINT(1) NULL,
  martes TINYINT(1) NULL,
  miercoles TINYINT(1) NULL,
  jueves TINYINT(1) NULL,
  viernes TINYINT(1) NULL,
 CONSTRAINT pk_horarios PRIMARY KEY (id)
);

-- -----------------------------------------------------
-- Tabla: cursos
-- -----------------------------------------------------
DROP TABLE IF EXISTS cursos;
CREATE TABLE IF NOT EXISTS cursos (
  id INT AUTO_INCREMENT NOT NULL,
  nombre_curso VARCHAR(255) NOT NULL,
  ciclo YEAR,
  cupo_maximo INT,
  cupo_minimo INT,
  carrera_tecnica_id VARCHAR(8),
  horario_id INT,
  instructor_id INT,
  salon_id VARCHAR(5),
  CONSTRAINT pk_cursos PRIMARY KEY (id),
  CONSTRAINT fk_carrera_tecnica_cursos
    FOREIGN KEY (carrera_tecnica_id)
    REFERENCES carreras_tecnicas (codigo_tecnico)
     ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_horario_id_cursos
    FOREIGN KEY (horario_id)
    REFERENCES horarios (id)
     ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_instructor_id_cursos
    FOREIGN KEY (instructor_id)
    REFERENCES instructores (id)
     ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT fk_salone_id_cursos
    FOREIGN KEY (salon_id)
    REFERENCES salones (codigo_salon)
     ON DELETE CASCADE ON UPDATE CASCADE
);

-- -----------------------------------------------------
-- Tabla: asignacion_alumnos
-- -----------------------------------------------------
DROP TABLE IF EXISTS asignacion_alumnos;
CREATE TABLE IF NOT EXISTS asignacion_alumnos (
  id INT NOT NULL AUTO_INCREMENT,
  alumno_id VARCHAR(7) NOT NULL,
  curso_id INT NOT NULL,
  fecha_asignacion DATETIME,
  CONSTRAINT pk_asignacion_alumnos PRIMARY KEY (id),
  CONSTRAINT fk_curso_id 
	FOREIGN KEY (curso_id) 
	REFERENCES cursos (id)
    ON UPDATE CASCADE ON DELETE CASCADE,
	CONSTRAINT fk_alumno 
	FOREIGN KEY (alumno_id)
    REFERENCES alumnos (carne)
    ON DELETE CASCADE ON UPDATE CASCADE
  
);





-- -----------------------------------------------------
-- 1. Procedimiento almacenado para insertar alumnos
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_create $$
CREATE PROCEDURE sp_alumnos_create(
	IN _carne VARCHAR(7),
	IN _nombre1 VARCHAR(15),
	IN _nombre2 VARCHAR(15),
	IN _nombre3 VARCHAR(15),
	IN _apellido1 VARCHAR(15),
	IN _apellido2 VARCHAR(15)
    )
BEGIN
	INSERT INTO alumnos(carne, nombre1, nombre2, nombre3, apellido1, apellido2)
    VALUES(_carne, _nombre1, _nombre2, _nombre3, _apellido1, _apellido2);
END $$
DELIMITER ;

-- -----------------------------------------------------
-- 2. Procedimiento almacenado para insertar instructores
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_create $$
CREATE PROCEDURE sp_instructores_create(
	IN _nombre1 VARCHAR(15),
	IN _nombre2 VARCHAR(15),
	IN _nombre3 VARCHAR(15),
	IN _apellido1 VARCHAR(15),
	IN _apellido2 VARCHAR(15),
	IN _direccion VARCHAR(45),
	IN _email VARCHAR(45),
	IN _telefono VARCHAR(8),
    IN _fecha_nacimiento DATE
    )
BEGIN
	INSERT INTO instructores(nombre1, nombre2, nombre3, apellido1, apellido2, direccion, email, telefono, fecha_nacimiento)
    VALUES(_nombre1, _nombre2, _nombre3, _apellido1, _apellido2, _direccion, _email, _telefono, _fecha_nacimiento);
END $$
DELIMITER ;

-- -----------------------------------------------------
-- 3. Procedimiento almacenado para insertar salones
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_create $$
CREATE PROCEDURE sp_salones_create(
	IN _codigo_salon VARCHAR(5),
	IN _descripcion VARCHAR(45),
	IN _capacidad_maxima INT,
	IN _edificio VARCHAR(15),
    IN _nivel INT
    )
BEGIN
	INSERT INTO salones(codigo_salon, descripcion, capacidad_maxima, edificio, nivel)
    VALUES(_codigo_salon, _descripcion, _capacidad_maxima, _edificio, _nivel);
END $$
DELIMITER ;



-- -----------------------------------------------------
-- 4. Procedimiento almacenado para insertar carreras_tecnicas
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_create $$
CREATE PROCEDURE sp_carreras_tecnicas_create(
	IN _codigo_tecnico VARCHAR(6),
	IN _carrera VARCHAR(45),
	IN _grado VARCHAR(10),
	IN _seccion CHAR(1),
	IN _jornada VARCHAR(10)
    )
BEGIN
	INSERT INTO carreras_tecnicas(codigo_tecnico, carrera, grado, seccion, jornada)
    VALUES(_codigo_tecnico, _carrera, _grado, _seccion, _jornada);
END $$
DELIMITER ;


-- -----------------------------------------------------
-- 5. Procedimiento almacenado para insertar horarios
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_horarios_create $$
CREATE PROCEDURE sp_carreras_horarios_create(
	IN _horario_inicio TIME,
	IN _horario_final TIME,
	IN _lunes TINYINT(1),
	IN _martes TINYINT(1),
	IN _miercoles TINYINT(1),
	IN _jueves TINYINT(1),
	IN _viernes TINYINT(1)
    )
BEGIN
	INSERT INTO horarios(horario_inicio, horario_final, lunes, martes, miercoles, jueves, viernes)
    VALUES(_horario_inicio, _horario_final, _lunes, _martes, _miercoles, _jueves, _viernes);
END $$
DELIMITER ;

-- -----------------------------------------------------
-- 6. Procedimiento almacenado para insertar cursos
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_create $$
CREATE PROCEDURE sp_cursos_create(
	IN _nombre_curso VARCHAR(255),
	IN _ciclo YEAR,
	IN _cupo_maximo INT,
	IN _cupo_minimo INT,
	IN _carrera_tecnica_id VARCHAR(128),
	IN _horario_id INT,
	IN _instructor_id INT,
	IN _salon_id VARCHAR(5)
    )
BEGIN
	INSERT INTO cursos(nombre_curso, ciclo, cupo_maximo, cupo_minimo, carrera_tecnica_id, horario_id, instructor_id, salon_id)
    VALUES(_nombre_curso, _ciclo, _cupo_maximo, _cupo_minimo, _carrera_tecnica_id, _horario_id, _instructor_id, _salon_id);
END $$
DELIMITER ;

-- -----------------------------------------------------
-- 7. Procedimiento almacenado para insertar asignacion_alumnos
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignacion_alumnos_create $$
CREATE PROCEDURE sp_asignacion_alumnos_create(
	IN _alumno_id VARCHAR(7),
    IN _curso_id INT,
	IN _fecha_asignacion DATETIME
    )
BEGIN
	INSERT INTO asignacion_alumnos(alumno_id, curso_id, fecha_asignacion)
    VALUES( _alumno_id,_curso_id,  _fecha_asignacion);
END $$
DELIMITER ;




/*Llamando sp_alumnos_create*/
call sp_alumnos_create("2022100", "David", "Josue", "Andre", "Ramos", "Jimenez");
call sp_alumnos_create("2022101", "Pedro", "Lucas", "Job", "Perez", "Duvon");
call sp_alumnos_create("2022102", "Lia", "Maite", "", "Quiñonez", "Zapata");
call sp_alumnos_create("2022103", "Aldair", "", "", "Moreno", "Garcia");
call sp_alumnos_create("2022104", "Diego", "Martin", "", "Palma", "Muños");
call sp_alumnos_create("2022105", "Josué", "Sandron", "Didier", "Lisboa", "Cruz");
call sp_alumnos_create("2022106", "Hugo", "Luis", "", "Pino", "Castillo");
call sp_alumnos_create("2022107", "Felipe", "Alejandro", "", "Mazarriegos", "Castro");
call sp_alumnos_create("2022108", "Rodrigo", "Jose", "", "Gomez", "Bautista");
call sp_alumnos_create("2022109", "Eldrick", "Emanuel", "Aldair", "Morales", "Ruiz");


/*Llamando sp_instructores_create*/
call sp_instructores_create("Carlos", "Jose", "", "Archila", "Flores", "zona 3 Ciudad capital", "carbravo@kinal.edu.gt", "57384323", "1982-12-27");
call sp_instructores_create("Carlos", "Alejandro", "", "Zapata", "Avila", "zona l", "carbravo@kinal.edu.gt", "57384323", "1975-09-28");
call sp_instructores_create("Carlos", "Angel", "", "Davila", "Bris", "4ta Ave 5-18 zona 3 Ciudad capital", "carbravo@kinal.edu.gt", "57384323", "1985-10-12");
call sp_instructores_create("Carlos", "Juan", "", "Cardona", "Sanabria", "3era Ave 4 zona 4 Ciudad capital", "carbravo@kinal.edu.gt", "57384323", "1955-8-18");
call sp_instructores_create("Carlos", "Martin", "", "Cadiz", "Sanches", "Villa Linda", "carbravo@kinal.edu.gt", "57384323", "1985-12-12");
call sp_instructores_create("Carlos", "Antonio", "", "Cordova", "Estua", "Xenacoj", "carbravo@kinal.edu.gt", "57384323", "1994-12-12");
call sp_instructores_create("Carlos", "Martines", "", "Cardona", "Archila", "Villa Nueva zona 2", "carbravo@kinal.edu.gt", "57384323", "1980-12-22");
call sp_instructores_create("Carlos", "Renato", "Estuardo", "Mel", "", "La reformita", "carbravo@kinal.edu.gt", "57384323", "1995-12-02");
call sp_instructores_create("Carlos", "Josefa", "", "Cardosa", "Lourdes", "Bosques de San Nicolas", "carbravo@kinal.edu.gt", "57384323", "1995-12-22");
call sp_instructores_create("Carlos", "Fernand", "", "Fernandez", "Martinez", "Zona 6", "carbravo@kinal.edu.gt", "57384323", "1995-12-02");

/*Llamando sp_salones_create */
call sp_salones_create("C24", "Salon mediano", 15, "B", '1');
call sp_salones_create("C31", "Salon pequeño ", 10, "C", '1');
call sp_salones_create("C22", "Salon pequeño con ventilacion", 10, "C", '2');
call sp_salones_create("C23", "Salon grande con espacio amplio ", 18, "B",'3');
call sp_salones_create("C25", "Salon mediano con ventilacion de aire", 15, "C",'2');
call sp_salones_create("C26", "Salon grande con espacio amplio", 18, "C",'4');
call sp_salones_create("C27", "Salon mediano con ventilacion de aire", 15, "A",'6');
call sp_salones_create("C28", "Salon grande con espacio amplio", 18, "A",'3');
call sp_salones_create("C29", "Salon mediano con ventilacion", 15, "A", '5');
call sp_salones_create("C30", "Salon grande con espacio amplio", 18, "A",'5');


/*Llamando sp_carreras_tecnicas_create */
call sp_carreras_tecnicas_create("ME5AM", "Mecanica", "5to Perito", "A", "Matutina");
call sp_carreras_tecnicas_create("ME4AM", "Mecanica", "4to Perito", "B", "Vespertina");
call sp_carreras_tecnicas_create("ME4BM", "Dibujo", "4to Perito", "B", "Matutina");
call sp_carreras_tecnicas_create("ME5BM", "Dibujo", "5to Perito", "A", "Vespertina");
call sp_carreras_tecnicas_create("EL4BM", "Electronica", "4to Perito", "B", "Matutina");
call sp_carreras_tecnicas_create("EL5BM", "Electronica", "5to Perito", "B", "Vespertina");
call sp_carreras_tecnicas_create("IN4AM", "Informatica", "4to Perito", "A", "Matutina");
call sp_carreras_tecnicas_create("IN5AM", "Informatica", "5to Perito", "A", "Vespertina");
call sp_carreras_tecnicas_create("IN4BM", "Informatica", "4to Perito", "B", "Matutina");
call sp_carreras_tecnicas_create("IN5BM", "Informatica", "5to Perito", "B", "Vespertina");

/*Llamando sp_carreras_horarios_create*/
call sp_carreras_horarios_create("7:30", "12:30", 1, 0, 1, 1, 1);
call sp_carreras_horarios_create("7:31", "12:31", 1 , 1 ,1, 0 ,1);
call sp_carreras_horarios_create("7:32", "12:32", 0 , 1 ,0 , 1 ,0);
call sp_carreras_horarios_create("7:33", "12:33", 1 , 1 , 1 , 1 ,1);
call sp_carreras_horarios_create("7:34", "12:34", 1, 0 , 0 , 1 ,0);
call sp_carreras_horarios_create("7:35", "12:35", 1 , 0 , 0 , 1,1);
call sp_carreras_horarios_create("7:36", "12:36", 1,1, 0,1 ,0);
call sp_carreras_horarios_create("7:37", "12:37", 1 , 0 , 1 , 1 , 1);
call sp_carreras_horarios_create("7:38", "12:38", 1 , 1 ,0 ,1 ,0);
call sp_carreras_horarios_create("7:39", "12:39", 1 , 0 , 1 , 1,0);

/*Llamando sp_cursos_create*/    

call sp_cursos_create("Quimica", "2022", "29", "10","ME5AM", "1", "1", "C23");
call sp_cursos_create("Estadistica","2022", "37", "14", "ME4AM", "2", "2", "C31");
call sp_cursos_create("Mecanica","2022", "56", "19", "ME4BM", "3", "3", "C25");
call sp_cursos_create("Lengua y Literatura","2022", "57", "15", "ME5BM", "4", "4", "C24");
call sp_cursos_create("Matematicas","2022", "55", "12", "EL4BM", "5", "5", "C25");
call sp_cursos_create("Dibujo","2022", 25, 20, "EL5BM", 6, 6, "C26");
call sp_cursos_create("Calculo","2022", 24, 19, "IN4AM", 7, 7, "C27");
call sp_cursos_create("Taller","2022", 47, 12, "IN5AM", 8, 8, "C28");
call sp_cursos_create("Sociales","2022", 40, 29, "IN4BM", 9, 9, "C29");
call sp_cursos_create("Ciencias","2022", 36, 19, "IN5BM", 10, 10, "C30");
	
    

/*Llamando sp_asignacion_alumnos_create*/
call sp_asignacion_alumnos_create( "2022100", 1,  "2022/01/25");
call sp_asignacion_alumnos_create( "2022101", 2, "2022/01/25");
call sp_asignacion_alumnos_create( "2022102", 3,"2022/01/25");
call sp_asignacion_alumnos_create( "2022103", 4,"2022/01/25");
call sp_asignacion_alumnos_create( "2022104", 5,"2022/01/25");
call sp_asignacion_alumnos_create( "2022105", 6, "2022/01/30");
call sp_asignacion_alumnos_create( "2022106", 7, "2022/01/30");
call sp_asignacion_alumnos_create( "2022107", 8,"2022/01/30");
call sp_asignacion_alumnos_create( "2022108", 9,"2022/01/30");
call sp_asignacion_alumnos_create( "2022109", 10,"2022/01/30");


-- -----------------------------------------------------
-- ALUMNOS READ
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read $$
CREATE PROCEDURE sp_alumnos_read()
BEGIN
    SELECT
        alumnos.carne,
        alumnos.nombre1,
        alumnos.nombre2,
        alumnos.nombre3,
        alumnos.apellido1,
        alumnos.apellido2
    FROM
        alumnos;
END $$
DELIMITER ;

-- -----------------------------------------------------
-- INSTRUCTORES READ
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read $$
CREATE PROCEDURE sp_instructores_read()
BEGIN
    SELECT
       *
    FROM
        instructores;
END $$
DELIMITER ;

-- -----------------------------------------------------
-- SALONES READ
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read $$
CREATE PROCEDURE sp_salones_read()
BEGIN
    SELECT
        s.codigo_salon,
        s.descripcion,
        s.capacidad_maxima,
        s.edificio,
        s.nivel
    FROM
        salones as s;
END $$
DELIMITER ;

-- -----------------------------------------------------
-- CARRERAS TECNICAS READ
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_read $$
CREATE PROCEDURE sp_carreras_read()
BEGIN
    SELECT
        ct.codigo_tecnico,
        ct.carrera,
        ct.grado,
        ct.seccion,
        ct.jornada
    FROM
        carreras_tecnicas as ct;
END $$
DELIMITER ;

-- -----------------------------------------------------
-- HORARIOS READ
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read $$
CREATE PROCEDURE sp_horarios_read()
BEGIN
    SELECT
        h.id,
        h.horario_inicio,
        h.horario_final,
        h.lunes,
        h.martes,
        h.miercoles,
        h.jueves,
        h.viernes
    FROM
        horarios as h;
END $$
DELIMITER ;

-- -----------------------------------------------------
-- CURSOS READ
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read $$
CREATE PROCEDURE sp_cursos_read()
BEGIN
    SELECT
        c.id,
        c.nombre_curso,
        c.ciclo,
        c.cupo_maximo,
        c.cupo_minimo,
        c.carrera_tecnica_id,
        c.horario_id,
        c.instructor_id,
        c.salon_id
    FROM
        cursos as c;
END $$
DELIMITER ;

-- -----------------------------------------------------
-- ASIGNACION ALUMNOS READ
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignacion_read $$
CREATE PROCEDURE sp_asignacion_read()
BEGIN
    SELECT
        a.id,
        a.alumno_id,
        a.curso_id,
        a.fecha_asignacion
    FROM
        asignacion_alumnos as a;
END $$
DELIMITER ;


-- -----------------------------------------------------
-- ALUMNOS UPDATE
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_update $$		
CREATE PROCEDURE sp_alumnos_update(
	IN _carne VARCHAR(7),
    IN _nombre1 VARCHAR(15),
    IN _nombre2 VARCHAR(15),
    IN _nombre3 VARCHAR(15),
    IN _apellido1 VARCHAR(15),
    IN _apellido2 VARCHAR(15)
    )
BEGIN
    UPDATE
        alumnos
    SET
		carne = _carne,
        nombre1 = _nombre1,
        nombre2 = _nombre2,
        nombre3 = _nombre3,
        apellido1 = _apellido1,
        apellido2 = _apellido2
	WHERE
		carne = _carne;
END $$

-- -----------------------------------------------------
-- INSTRUCTORES UPDATE
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_update $$		
CREATE PROCEDURE sp_instructores_update(
	IN _id INT,
	IN _nombre1 VARCHAR(15),
	IN _nombre2 VARCHAR(15),
	IN _nombre3 VARCHAR(15),
	IN _apellido1 VARCHAR(15),
	IN _apellido2 VARCHAR(15),
	IN _direccion VARCHAR(45),
	IN _email VARCHAR(45),
	IN _telefono VARCHAR(8),
	IN _fecha_nacimiento DATE
    )
BEGIN
    UPDATE
        instructores
    SET
		nombre1 = _nombre1,
		nombre2 = _nombre2,
		nombre3 = _nombre3,
		apellido1 = _apellido1,
		apellido2 = _apellido2,
		direccion = _direccion, 
		email = _email,
		telefono = _telefono,
		fecha_nacimiento = _fecha_nacimiento
	WHERE
		id = _id;
END $$

-- -----------------------------------------------------
-- SALONES UPDATE
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_update $$		
CREATE PROCEDURE sp_salones_update(
	IN _codigo_salon VARCHAR(5),
	IN _descripcion VARCHAR(45),
	IN _capacidad_maxima INT,
		IN _edificio VARCHAR(15),
	IN _nivel INT
    )
BEGIN
    UPDATE
        salones
    SET
		codigo_salon = _codigo_salon,
		descripcion = _descripcion,
		capacidad_maxima = _capacidad_maxima,
		edificio = _edificio,
        nivel = _nivel

	WHERE
		codigo_salon = _codigo_salon;
END $$


-- -----------------------------------------------------
-- CARRERAS_TECNICAS UPDATE
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_update $$		
CREATE PROCEDURE sp_carreras_tecnicas_update(
	IN _codigo_tecnico VARCHAR(6),
	IN _carrera VARCHAR(45),
	IN _grado VARCHAR(10),
	IN _seccion CHAR(1),
	IN _jornada VARCHAR(10)
    )
BEGIN
    UPDATE
        carreras_tecnicas
    SET
		codigo_tecnico = _codigo_tecnico,
		carrera = _carrera,
		grado = _grado,
		seccion = _seccion,
		jornada = _jornada
	WHERE
		codigo_tecnico = _codigo_tecnico;
END $$

-- -----------------------------------------------------
-- HORARIOS UPDATE
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_update $$		
CREATE PROCEDURE sp_horarios_update(
	In _id INT,
	IN _horario_inicio TIME,
	IN _horario_final TIME,
	IN _lunes TINYINT(1),
	IN _martes TINYINT(1),
	IN _miercoles TINYINT(1),
	IN _jueves TINYINT(1),
	IN _viernes TINYINT(1)
    )
BEGIN
    UPDATE
        horarios
    SET
		horario_inicio = _horario_inicio,
		horario_final = _horario_final,
		lunes = _lunes,
		martes = _martes,
		miercoles = _miercoles,
		jueves = _jueves,
		viernes = _viernes
	WHERE
		id = _id;
END $$

-- -----------------------------------------------------
-- CURSOS UPDATE
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_update $$		
CREATE PROCEDURE sp_cursos_update(
	IN _id INT,
	IN _nombre_curso VARCHAR(255),
	IN _ciclo YEAR,
	IN _cupo_maximo INT,
	IN _cupo_minimo INT,
	IN _carrera_tecnica_id VARCHAR(128),
	IN _horario_id INT,
	IN _instructor_id INT,
	IN _salon_id VARCHAR(5)
    )
BEGIN
    UPDATE
        cursos
    SET
		nombre_curso = _nombre_curso,
		ciclo = _ciclo,
		cupo_maximo = _cupo_maximo,
		cupo_minimo = _cupo_minimo,
		carrera_tecnica_id  = _carrera_tecnica_id,
		horario_id = _horario_id,
		instructor_id = _instructor_id,
		salon_id = _salon_id

	WHERE
		id = _id;
END $$

-- -----------------------------------------------------
-- ASIGNACION ALUMNOS UPDATE
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignacion_alumnos_update $$		
CREATE PROCEDURE sp_asignacion_alumnos_update(
	IN	_id INT,
	IN _alumno_id VARCHAR(7),
	IN _curso_id INT,
	IN _fecha_asignacion DATETIME
    )
BEGIN
    UPDATE
		asignacion_alumnos 
    SET
		alumno_id = _alumno_id,
        curso_id = _curso_id,
        fecha_asignacion = _fecha_asignacion
	WHERE
		id = _id;
END $$


/*DELETS*/
-- -----------------------------------------------------
-- ALUMNOS DELETE
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_delete $$		
CREATE PROCEDURE sp_alumnos_delete(
	_carne VARCHAR(7)
    )
BEGIN
    DELETE FROM
		alumnos
	WHERE
		carne = _carne;
END $$

-- -----------------------------------------------------
-- INSTRUCTORES DELETE
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_delete $$		
CREATE PROCEDURE sp_instructores_delete(
	_id INT
    )
BEGIN
    DELETE FROM
		instructores
	WHERE
		id = _id;
END $$

-- -----------------------------------------------------
-- SALONES DELETE
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_delete $$		
CREATE PROCEDURE sp_salones_delete(
	_codigo_salon VARCHAR(5)
    )
BEGIN
    DELETE FROM
		salones
	WHERE
		codigo_salon = _codigo_salon;
END $$

-- -----------------------------------------------------
-- CARRERAS TECNICAS DELETE
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_delete $$		
CREATE PROCEDURE sp_carreras_tecnicas_delete(
	_codigo_tecnico VARCHAR(6)
    )
BEGIN
    DELETE FROM
		carreras_tecnicas
	WHERE
		codigo_tecnico = _codigo_tecnico;
END $$

-- -----------------------------------------------------
-- HORARIOS DELETE
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_delete $$		
CREATE PROCEDURE sp_horarios_delete(
	_id INT
    )
BEGIN
    DELETE FROM
		horarios
	WHERE
		id = _id;
END $$

-- -----------------------------------------------------
-- CURSOS DELETE
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_delete $$		
CREATE PROCEDURE sp_cursos_delete(
	_id INT
    )
BEGIN
    DELETE FROM
		cursos
	WHERE
		id = _id;
END $$


-- -----------------------------------------------------
-- ASIGNACION ALUMNOS DELETE
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignacion_alumnos_delete $$		
CREATE PROCEDURE sp_asignacion_alumnos_delete(
	_id INT
    )
BEGIN
    DELETE FROM
		asignacion_alumnos
	WHERE
		id = _id;
END $$

/*READ ID*/
-- -----------------------------------------------------
-- ALUMNOS READ BY ID
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read_by_id $$
CREATE PROCEDURE sp_alumnos_read_by_id(IN _carne VARCHAR(15) )
BEGIN
    SELECT
        * 
    FROM
        alumnos Where _carne = carne;
	
END $$
DELIMITER ;


-- -----------------------------------------------------
-- INSTRUCTORES READ BY ID
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read_by_id $$
CREATE PROCEDURE sp_instructores_read_by_id(IN _id INT)
BEGIN
    SELECT
        *
    FROM
        instructores WHERE _id = id;
END $$
DELIMITER ;


-- -----------------------------------------------------
-- SALONES READ BY ID
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read_by_id $$
CREATE PROCEDURE sp_salones_read_by_id(IN _codigo_salon VARCHAR(5))
BEGIN
    SELECT
        *
    FROM
        salones WHERE _codigo_salon = codigo_salon;
END $$
DELIMITER ;
-- -----------------------------------------------------
-- CARRERAS TECNICAS READ BY ID
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read_by_id $$
CREATE PROCEDURE sp_carreras_tecnicas_read_by_id(IN _codigo_tecnico VARCHAR(6))
BEGIN
    SELECT
        *
    FROM
        carreras_tecnicas WHERE _codigo_tecnico = codigo_tecnico;
END $$
DELIMITER ;



-- -----------------------------------------------------
-- HORARIO READ BY ID
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horario_read_by_id $$
CREATE PROCEDURE sp_horario_read_by_id(IN _id INT)
BEGIN
    SELECT
        *
    FROM
        horarios WHERE _id = id;
END $$
DELIMITER ;

-- -----------------------------------------------------
-- CURSOS READ BY ID
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read_by_id $$
CREATE PROCEDURE sp_cursos_read_by_id(IN _id INT)
BEGIN
    SELECT
        *
    FROM
        cursos WHERE _id = id;
END $$
DELIMITER ;


-- -----------------------------------------------------
-- ASIGNACION ALUMNOS READ BY ID
-- -----------------------------------------------------
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignacion_alumnos_read_by_id $$
CREATE PROCEDURE sp_asignacion_alumnos_read_by_id(IN _id INT)
BEGIN
    SELECT
		*
    FROM
        asignacion_alumnos  WHERE _id = id;
END $$
DELIMITER ;




			