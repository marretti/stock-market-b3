-- Database: stockmarket

-- DROP DATABASE stockmarket;

CREATE DATABASE stockmarket
    WITH 
    OWNER = postgres
    ENCODING = 'UTF8'
    LC_COLLATE = 'Portuguese_Brazil.1252'
    LC_CTYPE = 'Portuguese_Brazil.1252'
    TABLESPACE = pg_default
    CONNECTION LIMIT = -1;
    
 -- Table: public.tstock

-- DROP TABLE public.tstock;

CREATE TABLE public.tstock
(
    symbol character varying(50) COLLATE pg_catalog."default" NOT NULL,
    CONSTRAINT tstock_pkey PRIMARY KEY (symbol)
)
WITH (
    OIDS = FALSE
)

ALTER TABLE public.tstock
    OWNER to postgres;

-- Table: public.tintraday

-- DROP TABLE public.tintraday;

CREATE TABLE public.tintraday
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
)
WITH (
    OIDS = FALSE
)

ALTER TABLE public.tintraday
    OWNER to postgres;
    
CREATE EXTENSION IF NOT EXISTS timescaledb CASCADE;
SELECT create_hypertable('tintraday', 'date', 'symbol');
CREATE INDEX ON tintraday (symbol);

-- Table: public.tdaily

-- DROP TABLE public.tdaily;

CREATE TABLE public.tdaily
(
    symbol character varying(255) COLLATE pg_catalog."default" NOT NULL,
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
)
WITH (
    OIDS = FALSE
)

ALTER TABLE public.tdaily
    OWNER to postgres;
    
SELECT create_hypertable('tdaily', 'date', 'symbol');