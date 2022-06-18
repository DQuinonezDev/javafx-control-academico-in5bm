/*
David Josue Andre Quiñonez Zeta - 2021045
Eldrick Aldair Hernandez Bautista - 2021099
Codigo Tecnico: IN5BM
*/


DROP DATABASE IF EXISTS db_control_academico_in5bm;
CREATE DATABASE IF NOT EXISTS db_control_academico_in5bm;
USE db_control_academico_in5bm;

#---------------------------------------------------------------------------------------------------#
DROP TABLE IF EXISTS alumnos;
DROP TABLE IF EXISTS instructores;
DROP TABLE IF EXISTS salones;
DROP TABLE IF EXISTS carreras_tecnicas;
DROP TABLE IF EXISTS horarios;
DROP TABLE IF EXISTS cursos;
DROP TABLE IF EXISTS asignaciones_alumnos;

-- tabla alumnos
CREATE TABLE IF NOT EXISTS alumnos(
	carne VARCHAR(16) NOT NULL,
    nombre1 VARCHAR(15) NOT NULL,
    nombre2 VARCHAR(15) NULL,
    nombre3 VARCHAR(15) NULL,
    apellido1 VARCHAR(15) NOT NULL,
	apellido2 VARCHAR(15) NULL,
    PRIMARY KEY(carne)
);

-- Tabla instructores
CREATE TABLE IF NOT EXISTS instructores(
	id INT NOT NULL AUTO_INCREMENT,
    nombre1 VARCHAR(15) NOT NULL,
    nombre2 VARCHAR(15) NULL,
    nombre3 VARCHAR(15) NULL,
    apellido1 VARCHAR(15) NOT NULL,
	apellido2 VARCHAR(15) NULL,
    direccion VARCHAR(45) NULL,
    email VARCHAR(45) NOT NULL,
    telefono VARCHAR(8) NOT NULL,
    fecha_nacimiento DATE NULL,
    PRIMARY KEY (id)
);

-- Tabla Salones
CREATE TABLE IF NOT EXISTS salones(
	codigo_salon VARCHAR(5) NOT NULL,
    descripcion VARCHAR(45) NULL,
    capacidad_maxima INT NOT NULL,
    edificio VARCHAR(15) NULL,
    nivel INT NULL,
    PRIMARY KEY (codigo_salon)
);

-- Tabla carreras
CREATE TABLE IF NOT EXISTS carreras_tecnicas(
	codigo_tecnico VARCHAR(6) NOT NULL,
    carrera VARCHAR(45) NOT NULL,
    grado VARCHAR(10) NOT NULL,
    seccion CHAR(1) NOT NULL,
    jornada VARCHAR(10) NOT NULL,
    PRIMARY KEY (codigo_tecnico)
);

-- tabla horarios
CREATE TABLE IF NOT EXISTS horarios(
	id INT NOT NULL AUTO_INCREMENT,
    horario_inicio TIME NOT NULL,
    horario_final TIME NOT NULL,
    lunes TINYINT(1) NULL,
    martes TINYINT(1) NULL,
    miercoles TINYINT(1) NULL,
    jueves TINYINT(1) NULL,
    viernes TINYINT(1) NULL,
    PRIMARY KEY (id)
);

-- tabla cursos
CREATE TABLE IF NOT EXISTS cursos(
	id INT NOT NULL AUTO_INCREMENT,
    nombre_curso VARCHAR(255) NOT NULL,
    ciclo YEAR NULL,
    cupo_maximo INT NULL,
    cupo_minimo INT NULL,
	carrera_tecnica_id 	VARCHAR(128) NOT NULL,
    horario_id INT NOT NULL,
    instructor_id INT NOT NULL,
    salon_id VARCHAR(5) NOT NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_cursos_carreras_tecnicas
		FOREIGN KEY (carrera_tecnica_id)
		REFERENCES carreras_tecnicas (codigo_tecnico)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_cursos_horarios
		FOREIGN KEY (horario_id)
        REFERENCES horarios (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_cursos_instructores
		FOREIGN KEY (instructor_id)
		REFERENCES instructores (id)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_cursos_salones
		FOREIGN KEY (salon_id)
        REFERENCES salones (codigo_salon)
        ON DELETE CASCADE ON UPDATE CASCADE
);

-- tabla asignaciones
CREATE TABLE IF NOT EXISTS asignaciones_alumnos(
	id INT NOT NULL AUTO_INCREMENT,
    alumno_id VARCHAR(16) NOT NULL,
    curso_id INT NOT NULL,
    fecha_asignacion DATETIME NULL,
    PRIMARY KEY (id),
    CONSTRAINT fk_asignaciones_alumnos_alumno
		FOREIGN KEY (alumno_id)
        REFERENCES alumnos (carne)
        ON DELETE CASCADE ON UPDATE CASCADE,
	CONSTRAINT fk_asignaciones_alumnos_curso
		FOREIGN KEY (curso_id)
        REFERENCES cursos (id)
        ON DELETE CASCADE ON UPDATE CASCADE
);



-- ALUMNOS CREATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_create $$
CREATE PROCEDURE sp_alumnos_create(
	IN _carne VARCHAR(16),
    IN _nombre1 VARCHAR(15),
    IN _nombre2 VARCHAR(15),
    IN _nombre3 VARCHAR(15),
    IN _apellido1 VARCHAR(15),
    IN _apellido2 VARCHAR(15)
)
BEGIN
	INSERT INTO alumnos(
		carne,
        nombre1,
        nombre2,
        nombre3,
        apellido1,
        apellido2
    )
	VALUES
	(
		_carne,
		_nombre1,
		_nombre2,
		_nombre3,
		_apellido1,
		_apellido2
    );
END $$
DELIMITER ;

-- Alumnos READ
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

-- ALumnos READ BY ID
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_read_by_id $$
CREATE PROCEDURE sp_alumnos_read_by_id(
IN _carne VARCHAR (16)
)
BEGIN
	SELECT
		alumnos.carne,
        alumnos.nombre1,
        alumnos.nombre2,
        alumnos.nombre3,
        alumnos.apellido1,
        alumnos.apellido2
	FROM
		alumnos
    WHERE 
		alumnos.carne=_carne;
END $$
DELIMITER ;

-- Alumnos UPDATE
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_update $$
CREATE PROCEDURE sp_alumnos_update(
	IN _carne VARCHAR(16),
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
		alumnos.nombre1=_nombre1,
        alumnos.nombre2=_nombre2,
        alumnos.nombre3=_nombre3,
        alumnos.apellido1=_apellido1,
        alumnos.apellido2=_apellido2
	WHERE
		alumnos.carne=_carne;
END$$
DELIMITER ;

-- ALumnos Delete
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_alumnos_delete $$
CREATE PROCEDURE sp_alumnos_delete(
	IN _carne VARCHAR(16)
)
BEGIN
	DELETE FROM
		alumnos
	WHERE
		alumnos.carne=_carne;
END$$
DELIMITER ;

-- Instructores CREATE
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
	INSERT INTO instructores(
		nombre1,
        nombre2,
        nombre3,
        apellido1,
        apellido2,
        direccion,
        email,
        telefono,
        fecha_nacimiento
    )
    VALUES
    (
		_nombre1,
        _nombre2,
        _nombre3,
        _apellido1,
        _apellido2,
        _direccion,
        _email,
        _telefono,
        _fecha_nacimiento
    );
END$$
DELIMITER ;

-- Instructores READ
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read $$
CREATE PROCEDURE sp_instructores_read()
BEGIN
	SELECT
		instructores.id,
        instructores.nombre1,
        instructores.nombre2,
        instructores.nombre3,
        instructores.apellido1,
        instructores.apellido2,
        instructores.direccion,
        instructores.email,
        instructores.telefono,
        instructores.fecha_nacimiento
	FROM
		instructores;
END$$
DELIMITER ;



-- Instructores UPDATE
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
		instructores.nombre1=_nombre1,
        instructores.nombre2=_nombre2,
        instructores.nombre3=_nombre3,
        instructores.apellido1=_apellido1,
        instructores.apellido2=_apellido2,
        instructores.direccion=_direccion,
        instructores.email=_email,
        instructores.telefono=_telefono,
        instructores.fecha_nacimiento=_fecha_nacimiento
	WHERE
		instructores.id=_id;
END$$
DELIMITER ;

-- Instructores READ BY ID
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_read_by_id $$
CREATE PROCEDURE sp_instructores_read_by_id(
	IN _id INT
)
BEGIN
	SELECT
		instructores.id,
        instructores.nombre1,
        instructores.nombre2,
        instructores.nombre3,
        instructores.apellido1,
        instructores.apellido2,
        instructores.direccion,
        instructores.email,
        instructores.telefono,
        instructores.fecha_nacimiento
	FROM
		instructores
	WHERE
		instructores.id=_id;
END$$
DELIMITER ;
-- Instructores Delete
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_instructores_delete $$
CREATE PROCEDURE sp_instructores_delete(
	IN _id INT
)
BEGIN
	DELETE FROM
		instructores
	WHERE
		instructores.id=_id;
END$$
DELIMITER ;

-- salones create
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
	INSERT INTO salones(
		codigo_salon,
        descripcion,
        capacidad_maxima,
        edificio,
        nivel
    ) 
    VALUES
    (
		_codigo_salon,
        _descripcion,
        _capacidad_maxima,
        _edificio,
        _nivel
    );
END $$
DELIMITER ;

-- salones read
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read $$
CREATE PROCEDURE sp_salones_read()
BEGIN
	SELECT
		salones.codigo_salon,
        salones.descripcion,
		salones.capacidad_maxima,
        salones.edificio,
        salones.nivel
	FROM
		salones;
END$$
DELIMITER ;


-- salones read by id
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_read_by_id $$
CREATE PROCEDURE sp_salones_read_by_id(
	IN _codigo_salon VARCHAR(5)
)
BEGIN
	SELECT
		salones.codigo_salon,
        salones.descripcion,
		salones.capacidad_maxima,
        salones.edificio,
        salones.nivel
	FROM
		salones
	WHERE
		salones.codigo_salon=_codigo_salon;
END$$
DELIMITER ;

-- salones update
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
        salones.descripcion=_descripcion,
        salones.capacidad_maxima=_capacidad_maxima,
        salones.edificio=_edificio,
        salones.nivel=_nivel
	WHERE
		salones.codigo_salon=_codigo_salon;
END$$
DELIMITER ;

-- salones delete
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_salones_delete $$
CREATE PROCEDURE sp_salones_delete(
	IN _codigo_salon VARCHAR(5)
)
BEGIN
	DELETE FROM
		salones
	WHERE
		salones.codigo_salon=_codigo_salon;
END$$
DELIMITER ;

-- carreras create
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
	INSERT INTO carreras_tecnicas(
		codigo_tecnico,
		carrera,
        grado,
        seccion,
        jornada
    )
	VALUES
    (
		_codigo_tecnico,
		_carrera,
		_grado,
		_seccion,
		_jornada		
);
END$$
DELIMITER ;

-- carreras read
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read $$
CREATE PROCEDURE sp_carreras_tecnicas_read()
BEGIN
	SELECT
		carreras_tecnicas.codigo_tecnico,
		carreras_tecnicas.carrera,
       	carreras_tecnicas.grado,
		carreras_tecnicas.seccion,
		carreras_tecnicas.jornada
	FROM
		carreras_tecnicas;	
END$$
DELIMITER ;

-- carreras read by id
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_read_by_id $$
CREATE PROCEDURE sp_carreras_tecnicas_read_by_id(
	IN _codigo_tecnico VARCHAR(6)
)
BEGIN
	SELECT
		carreras_tecnicas.codigo_tecnico,
		carreras_tecnicas.carrera,
       	carreras_tecnicas.grado,
		carreras_tecnicas.seccion,
		carreras_tecnicas.jornada
	FROM
		carreras_tecnicas
	WHERE
		carreras_tecnicas.codigo_tecnico=_codigo_tecnico;
END$$
DELIMITER ;
-- carreras update
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
		carreras_tecnicas.carrera=_carrera,
		carreras_tecnicas.grado=_grado,
		carreras_tecnicas.seccion=_seccion,
		carreras_tecnicas.jornada=_jornada
	WHERE
		carreras_tecnicas.codigo_tecnico=_codigo_tecnico;
END$$
DELIMITER ;
-- carreras delete
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_carreras_tecnicas_delete $$
CREATE PROCEDURE sp_carreras_tecnicas_delete(
	IN _codigo_tecnico VARCHAR(6)
)
BEGIN
	DELETE FROM
		carreras_tecnicas
	WHERE
		carreras_tecnicas.codigo_tecnico=_codigo_tecnico;	
END$$
DELIMITER ;

-- horarios create
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_create $$
CREATE PROCEDURE sp_horarios_create(
	IN _horario_inicio TIME,
	IN _horario_final TIME,
  	IN _lunes tinyint(1),
	IN _martes tinyint(1),
  	IN _miercoles tinyint(1),
	IN _jueves tinyint(1),
	IN _viernes tinyint(1)
)
BEGIN 
	INSERT INTO horarios(
		horario_inicio,
		horario_final,
		lunes,
		martes,
		miercoles,
		jueves,
		viernes
    )
	VALUES
    (
		_horario_inicio,
		_horario_final,
  		_lunes,
		_martes,
  		_miercoles,
		_jueves,
		_viernes
);
END$$
DELIMITER ;

-- horarios read
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read $$
CREATE PROCEDURE sp_horarios_read()
BEGIN
	SELECT
		horarios.id,
		horarios.horario_inicio,
		horarios.horario_final,
		horarios.lunes,
		horarios.martes,
		horarios.miercoles,
		horarios.jueves,
		horarios.viernes
	FROM
		horarios;
END $$
DELIMITER ;
-- horarios read by id
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_read_by_ $$
CREATE PROCEDURE sp_horarios_read_by_id(
	IN _id INT
)
BEGIN
	SELECT
		horarios.id,
		horarios.horario_inicio,
		horarios.horario_final,
		horarios.lunes,
		horarios.martes,
		horarios.miercoles,
		horarios.jueves,
		horarios.viernes
	FROM
		horarios
    WHERE
		horarios.id=_id;
END $$
DELIMITER ;

-- horarios uodate
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_update $$
CREATE PROCEDURE sp_horarios_update(
	IN _id INT,
	IN _horario_inicio TIME,
	IN _horario_final TIME,
  	IN _lunes tinyint(1),
	IN _martes tinyint(1),
  	IN _miercoles tinyint(1),
	IN _jueves tinyint(1),
	IN _viernes tinyint(1)  
)
BEGIN
	UPDATE
		horarios
	SET
		horarios.horario_inicio = _horario_inicio,
		horarios.horario_final = _horario_final,
		horarios.lunes = _lunes,
		horarios.martes =_martes,
		horarios.miercoles = _miercoles,
		horarios.jueves = _jueves, 
		horarios.viernes = _viernes
	WHERE
		horarios.id=_id;
	 
END$$
DELIMITER ;

-- horarios delete
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_horarios_delete $$
CREATE PROCEDURE sp_horarios_delete(
	IN _id INT
)
BEGIN
	DELETE FROM
		horarios
	WHERE
		horarios.id=_id;	
END$$
DELIMITER ;

-- cursos create
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
	INSERT INTO cursos(
		nombre_curso,
		ciclo,
  		cupo_maximo,   
		cupo_minimo,
		carrera_tecnica_id,
		horario_id,
		instructor_id,
		salon_id
)
	VALUES(
        _nombre_curso,
		_ciclo,
  		_cupo_maximo,   
		_cupo_minimo,
		_carrera_tecnica_id,
		_horario_id,
		_instructor_id,
		_salon_id  	
);
END $$
DELIMITER ;

-- cursos read
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read $$
CREATE PROCEDURE sp_cursos_read()
BEGIN
	SELECT
		cursos.id,
		cursos.nombre_curso,
		cursos.ciclo,
  		cursos.cupo_maximo,   
		cursos.cupo_minimo,
		cursos.carrera_tecnica_id,
		cursos.horario_id,
		cursos.instructor_id,
		cursos.salon_id
	FROM
        cursos;
END $$
DELIMITER ;

-- cursos  read by id
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_read_by_id $$
CREATE PROCEDURE sp_cursos_read_by_id(
	IN _id INT 
)
BEGIN
	SELECT
		cursos.id,
		cursos.nombre_curso,
		cursos.ciclo,
  		cursos.cupo_maximo,   
		cursos.cupo_minimo,
		cursos.carrera_tecnica_id,
		cursos.horario_id,
		cursos.instructor_id,
		cursos.salon_id
	FROM
        cursos;
END $$
DELIMITER ;

-- cursos uodate
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
		cursos.nombre_curso = _nombre_curso,
		cursos.ciclo = _ciclo,
  		cursos.cupo_maximo = _cupo_maximo,
		cursos.cupo_minimo = _cupo_minimo,
		cursos.carrera_tecnica_id = _carrera_tecnica_id,
		cursos.horario_id = _horario_id,
		cursos.instructor_id = _instructor_id,
		cursos.salon_id = _salon_id
	WHERE
		cursos.id=_id;
	 
END$$
DELIMTER;

-- cursos delete
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_cursos_delete $$
CREATE PROCEDURE sp_cursos_delete(
	IN _id INT
)
BEGIN
	DELETE FROM
		cursos
	WHERE
		cursos.id=_id;	
END$$
DELIMITER ;

-- asignaciones
DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_create $$
CREATE PROCEDURE sp_asignaciones_alumnos_create(
	IN _alumnos_id VARCHAR(16),
	IN _curso_id INT,
  	IN _fecha_asignacion DATETIME  
)
BEGIN 
	INSERT INTO asignaciones_alumnos(
		alumno_id,
		curso_id,
		fecha_asignacion
    	)
	VALUES(
		_alumnos_id,
		_curso_id,
  		_fecha_asignacion		
			
 );
END$$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_read $$
CREATE PROCEDURE sp_asignaciones_alumnos_read()
BEGIN
	SELECT
		asignaciones_alumnos.id,
		asignaciones_alumnos.alumno_id,
		asignaciones_alumnos.curso_id,
		asignaciones_alumnos.fecha_asignacion
	FROM
		asignaciones_alumnos;
END $$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_read_by_id $$
CREATE PROCEDURE sp_asignaciones_alumnos_read_by_id(IN _id INT)
BEGIN
	SELECT
		asignaciones_alumnos.id,
		asignaciones_alumnos.alumno_id,
		asignaciones_alumnos.curso_id,
		asignaciones_alumnos.fecha_asignacion
	FROM
		asignaciones_alumnos;
END $$
DELIMITER ;


DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_update $$
CREATE PROCEDURE sp_asignaciones_alumnos_update(
	IN _id INT,
	IN _alumno_id VARCHAR(16),
	IN _curso_id INT,
  	IN _fecha_asignacion DATETIME

)	
BEGIN
	UPDATE
		asignaciones_alumnos
	SET
		asignaciones_alumnos.alumno_id = _alumno_id,
		asignaciones_alumnos.curso_id = _curso_id,
		asignaciones_alumnos.fecha_asignacion = _fecha_asignacion
	WHERE
		asignaciones_alumnos.id=_id;
	 
END$$
DELIMITER;


DELIMITER $$
DROP PROCEDURE IF EXISTS sp_asignaciones_alumnos_delete $$
CREATE PROCEDURE sp_asignaciones_alumnos_delete(
	IN _id INT
)
BEGIN
	DELETE FROM
		asignaciones_alumnos
	WHERE
		asignaciones_alumnos.id=_id;	
END$$
DELIMITER ;




CALL sp_alumnos_create("2021001","David","Josue","Davila","Sanum","Gutierrez");
CALL sp_alumnos_create("2021002","Carlo","Pablo","Marroquin","Tzun");
CALL sp_alumnos_create("202003","Lia","Maite","Zapata","Perez");
CALL sp_alumnos_create("2021004","Jorge","Diego","Arturo","Cael","Soto");
CALL sp_alumnos_create("2021005","Valentina","Sof","Calvillo","Cifuentes");
call sp_alumnos_create("2021006","Esau","Carlos","Ambrosio","Jolon");
CALL sp_alumnos_create("2021007","Ronaldo","Cristiano","Marroquin","Morales");
CALL sp_alumnos_create("2021008","Javier","David","Quiñonez","Zeta");
CALL sp_alumnos_create("2021009","Oliva","Pamela","Rodriguez","Valenzuela");
CALL sp_alumnos_create("2021010","Marta","Julia","Renata","Perez","Herrera");


CALL sp_salones_create("C20","Mate",12,"1A",1);
CALL sp_salones_create("C21","Fisica Fundamental",11,"2E",5);
CALL sp_salones_create("C22","Estadistica",5,"R4",7);
CALL sp_salones_create("C23","Etica",9,"5R",6);
CALL sp_salones_create("C24","Ingles",8,"T4",8);
CALL sp_salones_create("C25","Quimica",12,"4R4",9);
CALL sp_salones_create("C26","fisica",5,"R7",3);
CALL sp_salones_create("C27","Taller",2,"9D",6);
CALL sp_salones_create("C28","Tics",12,"3R",76);
CALL sp_salones_create("C29","Calculo",45,"E3",2);


CALL sp_carreras_tecnicas_create("IN5BM","DIBUJO TECNICO","6TO","A","Matutina");
CALL sp_carreras_tecnicas_create("IN5VM","ELECTRICIDAD","6TO","A","Matutina");
CALL sp_carreras_tecnicas_create("IN5TAB","ELECTRICIDAD","6TO","A","Matutina");
CALL sp_carreras_tecnicas_create("PE5QMI","ELECTRICIDAD","6TO","A","Matutina");
CALL sp_carreras_tecnicas_create("P3EDF","MECANICA","5TO","B","Matutina");
CALL sp_carreras_tecnicas_create("P8SJA","MECANICA","5TO","B","Vespertina");
CALL sp_carreras_tecnicas_create("NU8W","AGRONOMIA","5TO","B","Vespertina");
CALL sp_carreras_tecnicas_create("P9NWA","AGRINOMIA","4TO","C","Vespertina");
CALL sp_carreras_tecnicas_create("U38SJ","FINANZAS","4TO","C","Vespertina");
CALL sp_carreras_tecnicas_create("1SJI3","FINANZAS","6TO","A","Vespertina");


call sp_horarios_create('10:00:00','11:50:00',1,5,3,2,4);
call sp_horarios_create('09:00:00','12:00:00',4,5,4,8,7);
call sp_horarios_create('10:00:00','01:00:00',3,6,8,8,2);
call sp_horarios_create('11:00:00','02:00:00',2,6,2,0,12);
call sp_horarios_create('12:00:00','03:00:00',5,9,5,6,1);
call sp_horarios_create('13:00:00','04:00:00',3,0,4,7,8);
call sp_horarios_create('14:00:00','05:00:00',7,5,2,4,3);
call sp_horarios_create('05:00:00','06:00:00',8,3,10,5,4);
call sp_horarios_create('09:00:00','07:00:00',9,4,10,6,5);
call sp_horarios_create('12:00:00','08:00:00',10,9,10,7,6);


call sp_instructores_create("Pablo","Reis","Francisco","Corzo","Escuintla","Matia21@kina.edu.grt","09373121",'2005-02-05');
call sp_instructores_create("Esau","Eladio","Carios","Perez","Gramaja","Chimaltenago","elao@gamil.com","94723645",'2000-02-07');
call sp_instructores_create("Antony","Carlos","Alejandro","Reinaso","Sanum","Zona 10","anasf@gmailcom","93826362",'2005-02-08');
call sp_instructores_create("Henry","Elias","Mario","Betancour","Jimenez","23 Calle Bosques","matis@gmail.com","41669409",'1990-06-05');
call sp_instructores_create("Enrique","Uriel","Etacurar","Perez","Tuna","Chimaltenago","enris@kinal.edu","56789432",'2002-03-16');
call sp_instructores_create("Efrain","Antonio","Pereira","Hernandez","Gomez","amatitlan","efra@kinal.edu","73625143",'1981-09-03');
call sp_instructores_create("Marco","Gabriel","Tercero","Godoy","Felipe","11 av 10","marlonAs@kinal.edu","75849302",'1950-01-22');
call sp_instructores_create("Benj","Andres","","Romero","Sanabria","zona 9","BenjaMatias@kinal.edu","94837163",'1945-12-12');
call sp_instructores_create("Eladio","Cristiano","Rodolfo","Pineda","Molina","Xenacoj","Ronaldo777@kinal.edu","93736273",'2021-01-11');
call sp_instructores_create("Victor","Ramos","Sanum","Morales","Zona 3","VictorRamos@kinal.edu","64321789",'2005-12-19');


call sp_cursos_create("MATEMATICAS","2022",10,8,"IN5BM",1,1,"SA1");
call sp_cursos_create("ESTADISTICA","2021",40,35,"IN5VM",2,2,"SA2");
call sp_cursos_create("LENGUAJE","2022",6,3,"IN5TAB",3,3,"SA3");
call sp_cursos_create("FISICA","2022",16,10,"PE5QMI",4,4,"SA4");
call sp_cursos_create("CALCULO","2022",15,10,"P3EDF",5,5,"SA5");
call sp_cursos_create("TICS","2010",25,10,"P8SJA",6,6,"S6");
call sp_cursos_create("DUBUJO","2020",49,30,"NU8W",7,7,"T3");
call sp_cursos_create("DEPORTE","2023",100,90,"P9NWA",8,8,"Q3");
call sp_cursos_create("DANZA","2018",15,10,"U38SJ",9,9,"O1");
call sp_cursos_create("QUIMICA","2024",20,10,"1SJI3",10,10,"32A");


call sp_asignaciones_alumnos_create("2021061",1,'2022-01-15 07:00');
call sp_asignaciones_alumnos_create("2021062",2,'2022-01-15 07:10');
call sp_asignaciones_alumnos_create("2020833",3,'2022-01-15 07:20');
call sp_asignaciones_alumnos_create("1928321",4,'2022-01-15 7:30');
call sp_asignaciones_alumnos_create("9291393",5,'2022-01-15 07:40');
call sp_asignaciones_alumnos_create("2021090",6,'2022-01-15 07:50');
call sp_asignaciones_alumnos_create("4321882",7,'2022-01-15 08:00');
call sp_asignaciones_alumnos_create("4723714",8,'2022-01-15 08:10');
call sp_asignaciones_alumnos_create("4853848",9,'2022-01-15 08:20');
call sp_asignaciones_alumnos_create("2971562",10,'2022-01-15 08:30');

