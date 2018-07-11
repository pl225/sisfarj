CREATE SCHEMA comp3;
SET SCHEMA comp3;
--
CREATE TABLE Associacao (
 matriculaAssociacao INT PRIMARY KEY,
 nome VARCHAR(50) NOT NULL,
 telefone VARCHAR(20) NOT NULL,
 sigla VARCHAR(5) NOT NULL,
 endereco VARCHAR(200) NOT NULL,
 numeroPagamento INT NOT NULL,
 numeroOficio INT NOT NULL,
 dataOficio TIMESTAMP NOT NULL,
 temAcesso CHAR(1) NOT NULL DEFAULT 'F' CHECK (temAcesso IN ('T', 'F'))
);

CREATE TABLE Atleta (
 matriculaAtleta INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1000000, INCREMENT BY 1),
 numeroOficio INT NOT NULL,
 dataOficio TIMESTAMP NOT NULL,
 nome VARCHAR(50) NOT NULL,
 dataNascimento TIMESTAMP NOT NULL,
 numeroPagamento INT NOT NULL,
 matriculaAssociacao INT NOT NULL,
 dataEntrada TIMESTAMP NOT NULL
);

CREATE TABLE LocalCompeticao (
 endereco VARCHAR(300) NOT NULL,
 nome VARCHAR(50) NOT NULL,
 piscina25 CHAR(1) CHECK (piscina25 IN ('T', 'F')),
 piscina50 CHAR(1) CHECK (piscina50 IN ('T', 'F'))
);

ALTER TABLE LocalCompeticao ADD CONSTRAINT PK_LocalCompeticao PRIMARY KEY (endereco);

CREATE TABLE Competicao (
 nome VARCHAR(50) NOT NULL,
 dataCompeticao TIMESTAMP NOT NULL,
 endereco VARCHAR(300) NOT NULL
);

ALTER TABLE Competicao ADD CONSTRAINT PK_Competicao PRIMARY KEY (dataCompeticao,endereco);

CREATE TABLE Pessoa (
 matricula INT PRIMARY KEY GENERATED ALWAYS AS IDENTITY (START WITH 1000000, INCREMENT BY 1),
 senha VARCHAR(15) NOT NULL,
 tipo VARCHAR(20) NOT NULL
);

CREATE TABLE Prova (
 tipoPiscina VARCHAR(5) NOT NULL CHECK (tipoPiscina IN ('50M', '25M')),
 nomeProva VARCHAR(30) NOT NULL CHECK (nomeProva IN ('50L', '100L', '200L', '400L',
 '800L', '1500L', '50C', '100C', '200C', '50P', '100P', '200P', '50B', '100B', '200B',
 '200M', '400M')),
 classe VARCHAR(30) NOT NULL CHECK(classe IN ('MIRIM', 'MIRIM_I_II', 'PETIZ_I_II', 
 'INFANTIL_I_II', 'JUVENIL_I_II', 'JUNIOR_I_II', 'SENIOR', 'MASTER', 'ABSOLUTO', 'INCULADO')),
 categoria VARCHAR(30) NOT NULL CHECK(categoria IN ('FEMININO', 'MASCULINO')),
 dataCompeticao TIMESTAMP NOT NULL,
 endereco VARCHAR(300) NOT NULL
);

CREATE TABLE AtletaProva (
 matriculaAtleta INT NOT NULL,
 nomeProva VARCHAR(30) NOT NULL,
 tipoPiscina VARCHAR (5) NOT NULL,
 classe VARCHAR(30) NOT NULL,
 categoria VARCHAR(30) NOT NULL,
 dataCompeticao TIMESTAMP NOT NULL,
 endereco VARCHAR(300) NOT NULL,
 tempo TIME,
 pontuacao INT
);

ALTER TABLE Associacao ADD CONSTRAINT FK_AssociacaoPessoa FOREIGN KEY (matriculaAssociacao) REFERENCES Pessoa (matricula);
ALTER TABLE Atleta ADD CONSTRAINT FK_Atleta_0 FOREIGN KEY (matriculaAssociacao) REFERENCES Associacao (matriculaAssociacao);
ALTER TABLE Competicao ADD CONSTRAINT FK_Competicao_0 FOREIGN KEY (endereco) REFERENCES LocalCompeticao (endereco);

ALTER TABLE Prova ADD CONSTRAINT PK_Prova PRIMARY KEY (tipoPiscina, nomeProva,classe,categoria,dataCompeticao,endereco);
ALTER TABLE Prova ADD CONSTRAINT FK_ProvaCompeticao FOREIGN KEY (dataCompeticao,endereco) REFERENCES Competicao (dataCompeticao,endereco);

ALTER TABLE AtletaProva ADD CONSTRAINT PK_AtletaProva PRIMARY KEY (tipoPiscina, nomeProva,classe,categoria, dataCompeticao, endereco,matriculaAtleta);
ALTER TABLE AtletaProva ADD CONSTRAINT FK_AtletaProvaAtleta FOREIGN KEY (matriculaAtleta) REFERENCES Atleta (matriculaAtleta);
ALTER TABLE AtletaProva ADD CONSTRAINT FK_AtletaProvaProva FOREIGN KEY (tipoPiscina, nomeProva,classe,categoria, dataCompeticao, endereco) REFERENCES Prova (tipoPiscina, nomeProva,classe,categoria, dataCompeticao, endereco);


INSERT INTO COMP3.PESSOA (SENHA, TIPO) values ('12345', 'DIRETOR');
--INSERT INTO COMP3.PESSOA values ('123', 'DIRETOR');

--insert into comp3.localcompeticao values ('Barra', 'BarraPool', 'T', 'T'), ('Piedade', 'PiedadePool', 'F', 'F');