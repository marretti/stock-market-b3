package com.marretti.stock.model.sync;

public class RegistroTrailer {

	// TIPREG - TIPO DE REGISTRO
	private String TIPREG;
	// NOMARQ - NOME DO ARQUIVO
	private String NOMARQ;
	// CDORIG - CODIGO DA ORIGEM
	private String CDORIG;
	// DATGER - DATA DA GERACAO DO ARQUIVO
	private String DATGER;
	// TOTREG - TOTAL DE REGISTROS
	private String TOTREG;

	public String getTIPREG() {
		return TIPREG;
	}

	public void setTIPREG(String TIPREG) {
		this.TIPREG = TIPREG;
	}

	public String getNOMARQ() {
		return NOMARQ;
	}

	public void setNOMARQ(String NOMARQ) {
		this.NOMARQ = NOMARQ;
	}

	public String getCDORIG() {
		return CDORIG;
	}

	public void setCDORIG(String CDORIG) {
		this.CDORIG = CDORIG;
	}

	public String getDATGER() {
		return DATGER;
	}

	public void setDATGER(String DATGER) {
		this.DATGER = DATGER;
	}

	public String getTOTREG() {
		return TOTREG;
	}

	public void setTOTREG(String TOTREG) {
		this.TOTREG = TOTREG;
	}
}
