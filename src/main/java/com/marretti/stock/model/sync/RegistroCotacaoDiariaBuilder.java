package com.marretti.stock.model.sync;

public class RegistroCotacaoDiariaBuilder {
	// TIPREG - TIPO DE REGISTRO
	private String TIPREG;
	// DATA DO PREGÃO
	private String DATAPG;
	// CODBDI - CÓDIGO BDI
	private String CODBDI;
	// CODNEG - CÓDIGO DE NEGOCIAÇÃO DO PAPEL
	private String CODNEG;
	// TPMERC - TIPO DE MERCADO
	private String TPMERC;
	// NOMRES - NOME RESUMIDO DA EMPRESA EMISSORA DO PAPEL
	private String NOMRES;
	// ESPECI - ESPECIFICAÇÃO DO PAPEL
	private String ESPECI;
	// PRAZOT - PRAZO EM DIAS DO MERCADO A TERMO
	private String PRAZOT;
	// MODREF - MOEDA DE REFERÊNCIA
	private String MODREF;
	// PREABE - PREÇO DE ABERTURA DO PAPEL-MERCADO NO PREGÃO
	private String PREABE;
	// PREMAX - PREÇO MÁXIMO DO PAPEL-MERCADO NO PREGÃO
	private String PREMAX;
	// PREMIN - PREÇO MÍNIMO DO PAPEL MERCADO NO PREGÃO
	private String PREMIN;
	// PREMED - PREÇO MÉDIO DO PAPEL MERCADO NO PREGÃO
	private String PREMED;
	// PREULT - PREÇO DO ÚLTIMO NEGÓCIO DO PAPEL-MERCADO NO PREGÃO
	private String PREULT;
	// PREOFC - PREÇO DA MELHOR OFERTA DE COMPRA DO PAPEL-MERCADO
	private String PREOFC;
	// PREOFV - PREÇO DA MELHOR OFERTA DE VENDA DO PAPEL-MERCADO
	private String PREOFV;
	// TOTNEG - NEG. - NÚMERO DE NEGÓCIOS EFETUADOS COM O PAPEL MERCADO
	// NO PREGÃO
	private String TOTNEG;
	// QUATOT - QUANTIDADE TOTAL DE TÍTULOS NEGOCIADOS NESTE PAPEL
	// MERCADO
	private String QUATOT;
	// VOLTOT - VOLUME TOTAL DE TÍTULOS NEGOCIADOS NESTE PAPEL MERCADO
	private String VOLTOT;
	// PREEXE - PREÇO DE EXERCÍCIO PARA O MERCADO DE OPÇÕES OU VALOR DO
	// CONTRATO PARA O MERCADO DE TERMO SECUNDÁRIO
	private String PREEXE;
	// INDOPC - INDICADOR DE CORREÇÃO DE PREÇOS DE EXERCÍCIOS OU VALORES
	// DE CONTRATO PARA OS MERCADOS DE OPÇÕES OU TERMO SECUNDÁRIO
	private String INDOPC;
	// DATVEN - DATA DO VENCIMENTO PARA OS MERCADOS DE OPÇÕES OU TERMO
	// SECUNDÁRIO
	private String DATVEN;
	// FATCOT - FATOR DE COTAÇÃO DO PAPEL
	private String FATCOT;
	// PTOEXE - PREÇO DE EXERCÍCIO EM PONTOS PARA OPÇÕES REFERENCIADAS
	// EM DÓLAR OU VALOR DE CONTRATO EM PONTOS PARA TERMO SECUNDÁRIO
	private String PTOEXE;
	// CODISI - CÓDIGO DO PAPEL NO SISTEMA ISIN OU CÓDIGO INTERNO DO
	// PAPEL
	private String CODISI;
	// DISMES - NÚMERO DE DISTRIBUIÇÃO DO PAPEL
	private String DISMES;

	public RegistroCotacaoDiariaBuilder() {
	}

	public RegistroCotacaoDiariaBuilder withTIPREG(String TIPREG) {
		this.TIPREG = TIPREG;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withDATAPG(String DATAPG) {
		this.DATAPG = DATAPG;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withCODBDI(String CODBDI) {
		this.CODBDI = CODBDI;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withCODNEG(String CODNEG) {
		this.CODNEG = CODNEG;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withTPMERC(String TPMERC) {
		this.TPMERC = TPMERC;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withNOMRES(String NOMRES) {
		this.NOMRES = NOMRES;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withESPECI(String ESPECI) {
		this.ESPECI = ESPECI;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withPRAZOT(String PRAZOT) {
		this.PRAZOT = PRAZOT;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withMODREF(String MODREF) {
		this.MODREF = MODREF;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withPREABE(String PREABE) {
		this.PREABE = PREABE;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withPREMAX(String PREMAX) {
		this.PREMAX = PREMAX;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withPREMIN(String PREMIN) {
		this.PREMIN = PREMIN;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withPREMED(String PREMED) {
		this.PREMED = PREMED;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withPREULT(String PREULT) {
		this.PREULT = PREULT;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withPREOFC(String PREOFC) {
		this.PREOFC = PREOFC;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withPREOFV(String PREOFV) {
		this.PREOFV = PREOFV;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withTOTNEG(String TOTNEG) {
		this.TOTNEG = TOTNEG;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withQUATOT(String QUATOT) {
		this.QUATOT = QUATOT;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withVOLTOT(String VOLTOT) {
		this.VOLTOT = VOLTOT;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withPREEXE(String PREEXE) {
		this.PREEXE = PREEXE;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withINDOPC(String INDOPC) {
		this.INDOPC = INDOPC;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withDATVEN(String DATVEN) {
		this.DATVEN = DATVEN;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withFATCOT(String FATCOT) {
		this.FATCOT = FATCOT;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withPTOEXE(String PTOEXE) {
		this.PTOEXE = PTOEXE;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withCODISI(String CODISI) {
		this.CODISI = CODISI;
		return this;
	}

	public RegistroCotacaoDiariaBuilder withDISMES(String DISMES) {
		this.DISMES = DISMES;
		return this;
	}

	public RegistroCotacaoDiaria build() {
		RegistroCotacaoDiaria registroCotacaoDiaria = new RegistroCotacaoDiaria();
		registroCotacaoDiaria.setTIPREG(TIPREG);
		registroCotacaoDiaria.setDATAPG(DATAPG);
		registroCotacaoDiaria.setCODBDI(CODBDI);
		registroCotacaoDiaria.setCODNEG(CODNEG);
		registroCotacaoDiaria.setTPMERC(TPMERC);
		registroCotacaoDiaria.setNOMRES(NOMRES);
		registroCotacaoDiaria.setESPECI(ESPECI);
		registroCotacaoDiaria.setPRAZOT(PRAZOT);
		registroCotacaoDiaria.setMODREF(MODREF);
		registroCotacaoDiaria.setPREABE(PREABE);
		registroCotacaoDiaria.setPREMAX(PREMAX);
		registroCotacaoDiaria.setPREMIN(PREMIN);
		registroCotacaoDiaria.setPREMED(PREMED);
		registroCotacaoDiaria.setPREULT(PREULT);
		registroCotacaoDiaria.setPREOFC(PREOFC);
		registroCotacaoDiaria.setPREOFV(PREOFV);
		registroCotacaoDiaria.setTOTNEG(TOTNEG);
		registroCotacaoDiaria.setQUATOT(QUATOT);
		registroCotacaoDiaria.setVOLTOT(VOLTOT);
		registroCotacaoDiaria.setPREEXE(PREEXE);
		registroCotacaoDiaria.setINDOPC(INDOPC);
		registroCotacaoDiaria.setDATVEN(DATVEN);
		registroCotacaoDiaria.setFATCOT(FATCOT);
		registroCotacaoDiaria.setPTOEXE(PTOEXE);
		registroCotacaoDiaria.setCODISI(CODISI);
		registroCotacaoDiaria.setDISMES(DISMES);
		return registroCotacaoDiaria;
	}
}