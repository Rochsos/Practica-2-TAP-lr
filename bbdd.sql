DROP TABLE IF EXISTS usuario;
CREATE TABLE usuario(
	IdUsuario bigint auto_increment,
	nombre varchar(50) not null,
	Contrasenia varchar(50) not null,
	primary key(IdUsuario));

INSERT INTO usuario (IdUsuario, nombre, Contrasenia) VALUES (1, 'Luisa', 'tapufvluisa');
INSERT INTO usuario (IdUsuario, nombre, Contrasenia) VALUES (2, 'Rocio', 'tapufvrocio');

DROP TABLE IF EXISTS lista_tareas;
CREATE TABLE lista_tareas(
	IdListaTareas bigint auto_increment,
	nombre varchar(50) not null,
	IdUsuario bigint not null,
	primary key(IdListaTareas));
	
INSERT INTO lista_tareas (IdListaTareas, nombre, IdUsuario) VALUES (1, 'trabajo', 1);
INSERT INTO lista_tareas (IdListaTareas, nombre, IdUsuario) VALUES (2, 'relaciones internacionales', 2);

DROP TABLE IF EXISTS tarea;
CREATE TABLE tarea(
	IdTarea bigint auto_increment,
	nombre varchar(50) not null,
	Descripcion varchar(100) not null,
	Prioridad varchar(50) not null,
	DeadLine date,
	EstadoTarea varchar(50) not null,
	IdLista bigint not null,
	primary key(IdTarea));
	
INSERT INTO tarea (IdTarea, nombre, Descripcion, Prioridad, EstadoTarea, IdLista) VALUES (1, 'Tarea 1', 'tarea 1 de la lista 1', 'alta', 'completada', 1);
INSERT INTO tarea (IdTarea, nombre, Descripcion, Prioridad, EstadoTarea, IdLista) VALUES (2, 'Tarea 1', 'tarea 1 de la lista 2','baja', 'por completar', 2);