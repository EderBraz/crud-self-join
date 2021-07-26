CREATE TABLE pessoa(
id serial not null,
nome varchar,
cpf varchar(11) unique,
data_nascimento timestamp,
estado_civil varchar,
parente int,
CONSTRAINT pessoa_pkey PRIMARY KEY(id),
CONSTRAINT pessoa_fk FOREIGN KEY (parente) REFERENCES pessoa(id)
);
