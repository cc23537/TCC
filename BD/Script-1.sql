CREATE TABLE TCC_Clientes (
	idCliente INT auto_increment NOT NULL,
	nomeCliente varchar(20) NOT NULL,
	Email VARCHAR(100) NOT NULL,
	Senha varchar(100) NOT NULL,
	Peso double null,
	altura double null,
	CONSTRAINT TCC_Clientes PRIMARY KEY (idCliente)
	
)

CREATE TABLE TCC_Alimentos (
	idAlimento INT auto_increment NOT NULL,
	nomeAlimento varchar(50) NOT NULL,
	Calorias double NULL,
	Especificacoes varchar(200) NULL,
	CONSTRAINT TCC_Alimentos PRIMARY KEY (idAlimento)
)

create table TCC_ListaDeCompras (
	idCompra INT auto_increment not null,
	idCliente INT not null ,
	idAlimento INT not null,
	Quantidade INT not null,
	constraint TCC_ListaDeCompras primary KEY(idCompra),	
	FOREIGN KEY (idCliente) REFERENCES tcc_clientes(idCliente),
	FOREIGN KEY (idAlimento) REFERENCES tcc_alimentos(idAlimento)
)



ENGINE=InnoDB
DEFAULT CHARSET=utf8mb4
COLLATE=utf8mb4_0900_ai_ci;
