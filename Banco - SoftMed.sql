drop database if exists softmed;
create database if not exists softmed;
use softmed;

CREATE TABLE medicos (
  login VARCHAR(255) BINARY NOT NULL,
  nome VARCHAR(255) NULL,
  sobrenome VARCHAR(255) NULL,
  rg VARCHAR(25) NULL,
  cpf VARCHAR(25) NULL,
  crm VARCHAR(25) NULL,
  email VARCHAR(255) BINARY NULL,
  senha VARCHAR(255) BINARY NULL,
  estado VARCHAR(255) NULL,
  especialidade VARCHAR(255) NULL,
  PRIMARY KEY(login)
);

CREATE TABLE pacientes (
  login VARCHAR(255) BINARY NOT NULL,
  nome VARCHAR(255) NULL,
  sobrenome VARCHAR(255) NULL,
  rg VARCHAR(25) NULL,
  cpf VARCHAR(25) NULL,
  email VARCHAR(255) BINARY NULL,
  senha VARCHAR(255) BINARY NULL,
  estado VARCHAR(255) NULL,
  PRIMARY KEY(login)
);

CREATE TABLE datasMed (
  id INT(10) NOT NULL AUTO_INCREMENT,
  medicos_login VARCHAR(255) BINARY NOT NULL,
  dia DATE NULL,
  horario TIME NULL,
  usado INT(1) UNSIGNED NULL,
  PRIMARY KEY(id),
  foreign key(medicos_login) references medicos(login)
);

CREATE TABLE consultas 	(
  id INT(10) NOT NULL AUTO_INCREMENT,
  datasmed_id int(10),
  medicos_login VARCHAR(255) BINARY NOT NULL,
  pacientes_login VARCHAR(255) BINARY NOT NULL,
  especialidade VARCHAR(255) NULL,
  dataCons DATE NULL,
  localizacao VARCHAR(255) NULL,
  horario TIME NULL,
  estado VARCHAR(25) NULL,
  PRIMARY KEY(id),
  foreign key(medicos_login) references medicos(login),
  foreign key(pacientes_login) references pacientes(login),
  foreign key(datasmed_id) references datasMed(id)
);

CREATE TABLE atividades (
  id INT(10) NOT NULL AUTO_INCREMENT,
  pacientes_login VARCHAR(255) BINARY NOT NULL,
  titulo VARCHAR(255) NULL,
  descricao VARCHAR(255) NULL,
  frequencia VARCHAR(50) NULL,
  estado VARCHAR(25) NULL,
  PRIMARY KEY(id),
  foreign key(pacientes_login) references pacientes(login)
);

CREATE TABLE reco_obs (
  id INT(10) NOT NULL AUTO_INCREMENT,
  pacientes_login VARCHAR(255) BINARY NOT NULL,
  titulo VARCHAR(255) NULL,
  descricao VARCHAR(255) NULL,
  categoria VARCHAR(255) NULL,
  PRIMARY KEY(id),
  foreign key(pacientes_login) references pacientes(login)
);

CREATE TABLE problemas (
  id INT(10) UNSIGNED NOT NULL AUTO_INCREMENT,
  pacientes_login VARCHAR(255) BINARY NOT NULL,
  titulo VARCHAR(255) NULL,
  descricao VARCHAR(255) NULL,
  categoria VARCHAR(255) NULL,
  PRIMARY KEY(id),
  foreign key(pacientes_login) references pacientes(login)
);

insert into medicos values ('alberto12', 'Alberto', 'Souza', '8342929-23', '009.382.836-48', '838-2', 'alberto@gmail.com', '1234', 'Amazonas', 'Cirurgia geral');
insert into pacientes values('ronaldo7', 'Ronaldo', 'Azevedo', '8349194-23', '882.737.719-37', 'ronaldo7@gmail.com', '1234', 'Amazonas');