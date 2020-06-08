DROP TABLE IF EXISTS LISTA_TAREAS;
CREATE TABLE LISTA_TAREAS(
	ID BIGINT NOT NULL,
	NOMBRE VARCHAR(255) NOT NULL,
	PRIMARY KEY(ID));

DROP TABLE IF EXISTS TAREA;
CREATE TABLE TAREA(
	ID BIGINT NOT NULL,
    DEADLINE DATE NOT NULL,
    DESCRIPCION VARCHAR(255) NOT NULL,
    ESTADO_TAREA VARCHAR(255) NOT NULL,
    NOMBRE VARCHAR(255) NOT NULL,
    PRIORIDAD VARCHAR (255) NOT NULL,
    LISTATAREAS_ID BIGINT NOT NULL,
	PRIMARY KEY(ID));
