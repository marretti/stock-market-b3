# stock-market-b3
[![Build Status](https://travis-ci.org/marretti/stock-market-b3.svg?branch=master)](https://travis-ci.org/marretti/stock-market-b3)

Aplicação Java para sincronização de dados diário e intradiário de ações e derivativos da B3 em uma base de dados PostgreSQL TimescaleDB.

- Sincronização de dados do mercado à vista (BOVESPA) - Diário e Intraday
- Sincronização de dados intradiário do mercado balcão (BMF) - Intraday


## Pré-requisitos:

##### a) Conhecimento intermediário em PostgreSQL.
##### b) Conhecimento intermediário em Java.

# Instalação:

#### 1) Instalar última versão da base de dados Postgre: https://www.postgresql.org/download/

#### 2) Instalar a última versão da extensão TimescaleDB seguindo as instruções disponíveis em: https://docs.timescale.com

#### 3) Executar seguintes scripts SQL para criação da base de dados e tabelas:

```sql
-- Database: stockmarket

-- DROP DATABASE stockmarket;

CREATE DATABASE stockmarket
    ENCODING = 'UTF8'
    CONNECTION LIMIT = -1;
    
CREATE EXTENSION IF NOT EXISTS timescaledb CASCADE;
    
-- Table: tstock

-- DROP TABLE tstock;

CREATE TABLE tstock
(
    symbol character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tstock_pkey PRIMARY KEY (symbol)
);

-- Table: tdaily

-- DROP TABLE tdaily;

CREATE TABLE tdaily
(
    symbol character varying(50) COLLATE pg_catalog."default" NOT NULL,
    date timestamp without time zone NOT NULL,
    close numeric(20,2),
    closeadj numeric(20,2),
    high numeric(20,2),
    low numeric(20,2),
    open numeric(20,2),
    volume numeric(20,2),
    CONSTRAINT tdaily_pkey PRIMARY KEY (symbol, date),
    CONSTRAINT fk_tdaily_symbol FOREIGN KEY (symbol)
        REFERENCES public.tstock (symbol) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

SELECT create_hypertable('tdaily', 'date', 'symbol');

-- Index: tdaily_date_idx

-- DROP INDEX public.tdaily_date_idx;

CREATE INDEX IF NOT EXISTS tdaily_date_idx
    ON tdaily USING btree
    (date DESC);

-- Table: tintraday

-- DROP TABLE tintraday;

CREATE TABLE tintraday
(
    tradenumber bigint NOT NULL,
    symbol character varying(50) COLLATE pg_catalog."default" NOT NULL,
    date timestamp without time zone NOT NULL,
    brokerbuy integer,
    brokersell integer,
    price numeric(20,2),
    tradeindicator character(1) COLLATE pg_catalog."default",
    volume bigint,
    CONSTRAINT tintraday_pkey PRIMARY KEY (tradenumber, symbol, date),
    CONSTRAINT fk_tintraday_symbol FOREIGN KEY (symbol)
        REFERENCES public.tstock (symbol) MATCH SIMPLE
        ON UPDATE NO ACTION
        ON DELETE NO ACTION
);

SELECT create_hypertable('tintraday', 'date', 'symbol');

-- Index: tintraday_date_idx

-- DROP INDEX tintraday_date_idx;

CREATE INDEX IF NOT EXISTS tintraday_date_idx
    ON tintraday USING btree
    (date DESC);

-- Index: tintraday_symbol_idx

-- DROP INDEX tintraday_symbol_idx;

CREATE INDEX IF NOT EXISTS tintraday_symbol_idx
    ON tintraday USING btree
    (symbol COLLATE pg_catalog."default");
    
```

#### 4) Criar as pastas abaixo na raiz do diretório onde a aplicação será executada:
        /marretti/stockexchange/apl --> Sugestão de armazenamento do .jar (compilado disponível na pasta target: marretti-stockexchange-sync-0.0.1-SNAPSHOT.jar)
        /marretti/stockexchange/config --> Arquivos de configurações da aplicação
        /marretti/stockexchange/logs --> Pasta de logs
        
#### 5) Obter o arquivo application.properties da pasta /src/main/resources e copiar para o diretório /marretti/stockexchange/config

        Efetuar os ajustes necessários de base de dados (usuário, senha, etc):
            spring.datasource.url=jdbc:postgresql://localhost:5432/stockmarket
            spring.datasource.username = postgres
            spring.datasource.password = SUA_SENHA
            
        Caso haja necessidade, efetuar os ajustes necessários de Regex para os instrumentos que serão sincronizados:
            regex.bovespa.daily=^[A-Z][A-Z][A-Z][A-Z][3-6]|[A-Z][A-Z][A-Z][A-Z]11$
            regex.bovespa.intraday=^[A-Z][A-Z][A-Z][A-Z][3-6]|[A-Z][A-Z][A-Z][A-Z]11$
            regex.bmf.intraday=^DOL[A-Z][0-9][0-9]|WDO[A-Z][0-9][0-9]|WIN[A-Z][0-9][0-9]|IND[A-Z][0-9][0-9]$
            
        Efetuar os ajustes de data de início da sincronização:
            sync.bovespa.daily.startDate=01/01/2019
            sync.bovespa.intraday.startDate=01/01/2019
            sync.bmf.intraday.startDate=01/01/2019
			
		Executar a aplicação através da linha de comando: java -jar /marretti/stockexchange/apl/marretti-stockexchange-sync-0.0.1-SNAPSHOT.jar
		
		Acompanhar a sincronização por meio do arquivo Data.log disponível na pasta de logs.
            
#### 6) Notebook de exemplo de uso em python disponível na pasta notebook/exemplo.ipynb
            
## To-Do

- Todas as contribuições são bem-vindas.
##### 1) Implementação de schedule para sincronização automática após a primeira carga.

