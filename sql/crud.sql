CREATE TABLE linhas (
	id_linha int not null,
	codigo varchar(60) not null,
	nome varchar(255) not null,	
	tipo varchar(2) not null,
	
	CONSTRAINT pk_linhas PRIMARY KEY(id_linha)
);

CREATE TABLE coordenadas (
	id_coordenada int not null,
	latitude varchar(60) not null,
	longitude varchar(60) not null,	

	CONSTRAINT pk_coordenadas PRIMARY KEY(id_coordenada)
);

CREATE TABLE itinerarios (
	id_itinerario int not null,
	cod_linha int not null,
	cod_coordenada int not null,	

	CONSTRAINT pk_itinerarios PRIMARY KEY(id_itinerario)
);


ALTER TABLE itinerarios ADD CONSTRAINT fk_linhas_codlinha FOREIGN KEY(cod_linha) REFERENCES linhas(id_linha);
ALTER TABLE itinerarios ADD CONSTRAINT fk_coordenadas_codcoordenada FOREIGN KEY(cod_coordenada) REFERENCES coordenadas(id_coordenada);

-- SQL PARA BUSCAR LINHAS DENTRO DE UM RAIO.
SELECT *,(RAIO_TERRESTRE *acos(cos(radians(PARAMETRO_LATITUDE)) * cos(radians(coordenadas.latitude)) * cos(radians(PARAMETRO_LONGITUDE) - radians(coordenadas.longitude)) + sin(radians(PARAMETRO_LATITUDE)) * sin(radians(coordenadas.latitude)))) AS CAMPOLATITUDE
FROM FROM linhas INNER JOIN itinerarios ON(id_lina=cod_linha) INNER JOIN coordenadas ON (id_coordenada=cod_coordenada) HAVING lcoordenadas.latitude<=10