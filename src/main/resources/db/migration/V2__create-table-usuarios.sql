CREATE TABLE usuarios
(
  id SERIAL NOT NULL PRIMARY KEY,
  nome varchar(100)  NOT NULL ,
  login varchar(100)  NOT NULL ,
  senha varchar(100)  NOT NULL 
);