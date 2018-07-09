CREATE SCHEMA comp3;
SET SCHEMA comp3;
--
CREATE TABLE Associacao (
 matriculaAssociacao INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),
 nome VARCHAR(50) NOT NULL,
 telefone VARCHAR(20) NOT NULL,
 sigla VARCHAR(5) NOT NULL,
 endereco VARCHAR(200) NOT NULL,
 numeroPagamento INT NOT NULL,
 numeroOficio INT NOT NULL,
 dataOficio TIMESTAMP NOT NULL
);

ALTER TABLE Associacao ADD CONSTRAINT PK_Associacao PRIMARY KEY (matriculaAssociacao);


CREATE TABLE Atleta (
 matriculaAtleta INT NOT NULL,
 numeroOficio INT NOT NULL,
 dataOficio TIMESTAMP NOT NULL,
 nome VARCHAR(50) NOT NULL,
 dataNascimento TIMESTAMP NOT NULL,
 numeroPagamento INT NOT NULL,
 matriculaAssociacao INT NOT NULL,
 dataEntrada TIMESTAMP NOT NULL
);

ALTER TABLE Atleta ADD CONSTRAINT PK_Atleta PRIMARY KEY (matriculaAtleta);

CREATE TABLE LocalCompeticao (
 endereco VARCHAR(300) NOT NULL,
 nome VARCHAR(10) NOT NULL,
 piscina25 BOOLEAN NOT NULL,
 piscina50 BOOLEAN NOT NULL
);

ALTER TABLE LocalCompeticao ADD CONSTRAINT PK_LocalCompeticao PRIMARY KEY (endereco);

CREATE TABLE Competicao (
 nome VARCHAR(10) NOT NULL,
 dataCompeticao VARCHAR(10) NOT NULL,
 endereco VARCHAR(300) NOT NULL
);

ALTER TABLE Competicao ADD CONSTRAINT PK_Competicao PRIMARY KEY (dataCompeticao,endereco);


CREATE TABLE Pessoa (
 matricula INT PRIMARY KEY,
 senha VARCHAR(15) NOT NULL,
 tipo VARCHAR(50) NOT NULL
);

CREATE TABLE TecnicoAssociacao (
 matriculaAssociacao INT,
 matricula INT
);


CREATE TABLE Prova (
 nomeProva VARCHAR(10) NOT NULL,
 classe VARCHAR(10) NOT NULL,
 categoria VARCHAR(10) NOT NULL
);

ALTER TABLE Prova ADD CONSTRAINT PK_Prova PRIMARY KEY (nomeProva,classe,categoria);

CREATE TABLE AtletaProva (
 matriculaAtleta INT NOT NULL,
 nomeProva VARCHAR(10) NOT NULL,
 classe VARCHAR(10) NOT NULL,
 categoria VARCHAR(10) NOT NULL
);

ALTER TABLE AtletaProva ADD CONSTRAINT PK_AtletaProva PRIMARY KEY (nomeProva,classe,categoria,matriculaAtleta);
ALTER TABLE AtletaProva ADD CONSTRAINT FK_AtletaProvaAtleta FOREIGN KEY (matriculaAtleta) REFERENCES Atleta (matriculaAtleta);
ALTER TABLE AtletaProva ADD CONSTRAINT FK_AtletaProvaProva FOREIGN KEY (nomeProva,classe,categoria) REFERENCES Prova (nomeProva,classe,categoria);


CREATE TABLE CompeticaoProva (
 nomeProva VARCHAR(10) NOT NULL,
 classe VARCHAR(10) NOT NULL,
 categoria VARCHAR(10) NOT NULL,
 dataCompeticao VARCHAR(10) NOT NULL,
 endereco VARCHAR(300) NOT NULL
);

ALTER TABLE CompeticaoProva ADD CONSTRAINT PK_CompeticaoProva PRIMARY KEY (nomeProva,classe,categoria,dataCompeticao,endereco);
ALTER TABLE CompeticaoProva ADD CONSTRAINT FK_CompeticaoProvaCompeticao FOREIGN KEY (dataCompeticao,endereco) REFERENCES Competicao (dataCompeticao,endereco);
ALTER TABLE CompeticaoProva ADD CONSTRAINT FK_CompeticaoProvaProva FOREIGN KEY (nomeProva,classe,categoria) REFERENCES Prova (nomeProva,classe,categoria);

ALTER TABLE Atleta ADD CONSTRAINT FK_Atleta_0 FOREIGN KEY (matriculaAssociacao) REFERENCES Associacao (matriculaAssociacao);


ALTER TABLE TecnicoAssociacao ADD CONSTRAINT FK_TecnicoAssociacao_0 FOREIGN KEY (matriculaAssociacao) REFERENCES Associacao (matriculaAssociacao);
ALTER TABLE TecnicoAssociacao ADD CONSTRAINT FK_TecnicoPessoa FOREIGN KEY (matricula) REFERENCES Pessoa (matricula);


ALTER TABLE Competicao ADD CONSTRAINT FK_Competicao_0 FOREIGN KEY (endereco) REFERENCES LocalCompeticao (endereco);


INSERT INTO COMP3.PESSOA values (780252, 'Pl2252122*', 'DIRETOR');