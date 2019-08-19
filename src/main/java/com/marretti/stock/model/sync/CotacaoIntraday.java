package com.marretti.stock.model.sync;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class CotacaoIntraday implements Serializable {
	
	/** seria version */
	private static final long serialVersionUID = -2197720105060020227L;
	
	private List<TickIntraday> listIntraday;
	
	private TickHeaderIntraday headerIntraday;

	/**
	 * @return the listIntraday
	 */
	public List<TickIntraday> getListIntraday() {
		if (listIntraday == null) {
			listIntraday = new ArrayList<TickIntraday>();
		}
		return listIntraday;
	}

	/**
	 * @param listIntraday the listIntraday to set
	 */
	public void setListIntraday(List<TickIntraday> listIntraday) {
		this.listIntraday = listIntraday;
	}

	/**
	 * @return the headerIntraday
	 */
	public TickHeaderIntraday getHeaderIntraday() {
		return headerIntraday;
	}

	/**
	 * @param headerIntraday the headerIntraday to set
	 */
	public void setHeaderIntraday(TickHeaderIntraday headerIntraday) {
		this.headerIntraday = headerIntraday;
	}
	
	public void addTickIntraday(TickIntraday tickIntraday) {
		this.getListIntraday().add(tickIntraday);
	}
	
}

class TickHeaderIntraday {
	
	/** RH Cabeçalho - RT Rodapé */
	private String identificacao;
	
	/** Nome do arquivo */
	private String nomeArquivo;
	
	/** Data Inicial desse arquivo */
	private LocalDate dataInicial;
	
	/** Data Final desse arquivo */
	private LocalDate dataFinal;
	
	/** No caso do rodapé apresenta o total de linhas do arquivo */
	private Long totalLinhas;

	/**
	 * @return the identificacao
	 */
	public String getIdentificacao() {
		return identificacao;
	}

	/**
	 * @param identificacao the identificacao to set
	 */
	public void setIdentificacao(String identificacao) {
		this.identificacao = identificacao;
	}

	/**
	 * @return the nomeArquivo
	 */
	public String getNomeArquivo() {
		return nomeArquivo;
	}

	/**
	 * @param nomeArquivo the nomeArquivo to set
	 */
	public void setNomeArquivo(String nomeArquivo) {
		this.nomeArquivo = nomeArquivo;
	}

	/**
	 * @return the dataInicial
	 */
	public LocalDate getDataInicial() {
		return dataInicial;
	}

	/**
	 * @param dataInicial the dataInicial to set
	 */
	public void setDataInicial(LocalDate dataInicial) {
		this.dataInicial = dataInicial;
	}

	/**
	 * @return the dataFinal
	 */
	public LocalDate getDataFinal() {
		return dataFinal;
	}

	/**
	 * @param dataFinal the dataFinal to set
	 */
	public void setDataFinal(LocalDate dataFinal) {
		this.dataFinal = dataFinal;
	}

	/**
	 * @return the totalLinhas
	 */
	public Long getTotalLinhas() {
		return totalLinhas;
	}

	/**
	 * @param totalLinhas the totalLinhas to set
	 */
	public void setTotalLinhas(Long totalLinhas) {
		this.totalLinhas = totalLinhas;
	}
	
}

