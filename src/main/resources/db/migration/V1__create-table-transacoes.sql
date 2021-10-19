CREATE TABLE transacoes
(
  id serial NOT NULL PRIMARY KEY,
  ticker varchar(6) NOT NULL,
  preco decimal(18,2) NOT NULL,
  quantidade int NOT NULL,
  tipo varchar(100) NOT NULL,
  data date NOT NULL
);