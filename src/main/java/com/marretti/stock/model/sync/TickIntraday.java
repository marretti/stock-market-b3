package com.marretti.stock.model.sync;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.time.LocalDate;
import java.time.LocalTime;

public class TickIntraday {

	/** Data de sessão */
	private LocalDate dataSessao;
	
	/** Símbolo do Instrumento */
	private String simboloInstrumento;
	
	/** Número do negócio */
	private BigInteger numeroNegocio;
	
	/** Preço do negócio */
	private BigDecimal precoNegocio;
	
	/** Quantidade negociada */
	private BigInteger quantidade;
	
	/** Horário da negociação (formato HH:MM:SS.NNN) */
	private LocalTime horarioNegocio;
	
	/** Indicador de Anulação: "1" - ativo / "2" - cancelado */
	private Character indicadorAnulacao;
	
	/** Data da oferta de compra */
	private LocalDate dataOfertaCompra;
	
	/** Número sequencial da oferta de compra */
	private BigInteger sequenciaOfertaCompra;
	
	/** Número de geração (GenerationID) da Oferta de compra. Quando um negócio for gerado por 2 ofertas com quantidade escondida e isso gerar "n" linhas será gravado aqui a maior geração */
	private BigInteger generationIdCompra;
	
	/** Código que identifica a condição da oferta de compra. Pode ser: 0 - Oferta Neutra - é aquela que entra no mercado e não fecha com oferta existente. / 1 - Oferta Agressora - é aquela que ingressa no mercado para fechar com uma oferta existente. / 2 - Oferta Agredida - é a oferta (existente) que é fechada com uma oferta agressora. */
	private Character codicaoOfertaCompra;
	
	/** Data da oferta de venda */
	private LocalDate dataOfertaVenda;
	
	/** Número sequencial da oferta de venda */
	private BigInteger sequenciaOfertaVenda;
	
	/** Número de geração (GenerationID) da Oferta de venda. Quando um negócio for gerado por 2 ofertas com quantidade escondida e isso gerar "n" linhas será gravado aqui a maior geração */
	private BigInteger generationIdVenda;
	
	/** Código que identifica a condição da oferta de venda. Pode ser: 0 - Oferta Neutra - é aquela que entra no mercado e não fecha com oferta existente. / 1 - Oferta Agressora - é aquela que ingressa no mercado para fechar com uma oferta existente. / 2 - Oferta Agredida - é a oferta (existente) que é fechada com uma oferta agressora. */
	private Character codicaoOfertaVenda;
	
	/** Código que identifica se o negócio direto foi intencional: 1 - Intencional / 0 - Não Intencional */
	private Character indicatorDireto;
	
	/** Código de identificação da corretora de compra */
	private Integer codigoCorretoraCompra;
	
	/** Código de identificação da corretora de venda */
	private Integer codigoCorretoraVenda;

	/**
	 * @return the dataSessao
	 */
	public LocalDate getDataSessao() {
		return dataSessao;
	}

	/**
	 * @param dataSessao the dataSessao to set
	 */
	public void setDataSessao(LocalDate dataSessao) {
		this.dataSessao = dataSessao;
	}

	/**
	 * @return the simboloInstrumento
	 */
	public String getSimboloInstrumento() {
		return simboloInstrumento;
	}

	/**
	 * @param simboloInstrumento the simboloInstrumento to set
	 */
	public void setSimboloInstrumento(String simboloInstrumento) {
		this.simboloInstrumento = simboloInstrumento;
	}

	/**
	 * @return the numeroNegocio
	 */
	public BigInteger getNumeroNegocio() {
		return numeroNegocio;
	}

	/**
	 * @param numeroNegocio the numeroNegocio to set
	 */
	public void setNumeroNegocio(BigInteger numeroNegocio) {
		this.numeroNegocio = numeroNegocio;
	}

	/**
	 * @return the precoNegocio
	 */
	public BigDecimal getPrecoNegocio() {
		return precoNegocio;
	}

	/**
	 * @param precoNegocio the precoNegocio to set
	 */
	public void setPrecoNegocio(BigDecimal precoNegocio) {
		this.precoNegocio = precoNegocio;
	}

	/**
	 * @return the quantidade
	 */
	public BigInteger getQuantidade() {
		return quantidade;
	}

	/**
	 * @param quantidade the quantidade to set
	 */
	public void setQuantidade(BigInteger quantidade) {
		this.quantidade = quantidade;
	}

	/**
	 * @return the horarioNegocio
	 */
	public LocalTime getHorarioNegocio() {
		return horarioNegocio;
	}

	/**
	 * @param horarioNegocio the horarioNegocio to set
	 */
	public void setHorarioNegocio(LocalTime horarioNegocio) {
		this.horarioNegocio = horarioNegocio;
	}

	/**
	 * @return the indicadorAnulacao
	 */
	public Character getIndicadorAnulacao() {
		return indicadorAnulacao;
	}

	/**
	 * @param indicadorAnulacao the indicadorAnulacao to set
	 */
	public void setIndicadorAnulacao(Character indicadorAnulacao) {
		this.indicadorAnulacao = indicadorAnulacao;
	}

	/**
	 * @return the dataOfertaCompra
	 */
	public LocalDate getDataOfertaCompra() {
		return dataOfertaCompra;
	}

	/**
	 * @param dataOfertaCompra the dataOfertaCompra to set
	 */
	public void setDataOfertaCompra(LocalDate dataOfertaCompra) {
		this.dataOfertaCompra = dataOfertaCompra;
	}

	/**
	 * @return the sequenciaOfertaCompra
	 */
	public BigInteger getSequenciaOfertaCompra() {
		return sequenciaOfertaCompra;
	}

	/**
	 * @param sequenciaOfertaCompra the sequenciaOfertaCompra to set
	 */
	public void setSequenciaOfertaCompra(BigInteger sequenciaOfertaCompra) {
		this.sequenciaOfertaCompra = sequenciaOfertaCompra;
	}

	/**
	 * @return the generationIdCompra
	 */
	public BigInteger getGenerationIdCompra() {
		return generationIdCompra;
	}

	/**
	 * @param generationIdCompra the generationIdCompra to set
	 */
	public void setGenerationIdCompra(BigInteger generationIdCompra) {
		this.generationIdCompra = generationIdCompra;
	}

	/**
	 * @return the codicaoOfertaCompra
	 */
	public Character getCodicaoOfertaCompra() {
		return codicaoOfertaCompra;
	}

	/**
	 * @param codicaoOfertaCompra the codicaoOfertaCompra to set
	 */
	public void setCodicaoOfertaCompra(Character codicaoOfertaCompra) {
		this.codicaoOfertaCompra = codicaoOfertaCompra;
	}

	/**
	 * @return the dataOfertaVenda
	 */
	public LocalDate getDataOfertaVenda() {
		return dataOfertaVenda;
	}

	/**
	 * @param dataOfertaVenda the dataOfertaVenda to set
	 */
	public void setDataOfertaVenda(LocalDate dataOfertaVenda) {
		this.dataOfertaVenda = dataOfertaVenda;
	}

	/**
	 * @return the sequenciaOfertaVenda
	 */
	public BigInteger getSequenciaOfertaVenda() {
		return sequenciaOfertaVenda;
	}

	/**
	 * @param sequenciaOfertaVenda the sequenciaOfertaVenda to set
	 */
	public void setSequenciaOfertaVenda(BigInteger sequenciaOfertaVenda) {
		this.sequenciaOfertaVenda = sequenciaOfertaVenda;
	}

	/**
	 * @return the generationIdVenda
	 */
	public BigInteger getGenerationIdVenda() {
		return generationIdVenda;
	}

	/**
	 * @param generationIdVenda the generationIdVenda to set
	 */
	public void setGenerationIdVenda(BigInteger generationIdVenda) {
		this.generationIdVenda = generationIdVenda;
	}

	/**
	 * @return the codicaoOfertaVenda
	 */
	public Character getCodicaoOfertaVenda() {
		return codicaoOfertaVenda;
	}

	/**
	 * @param codicaoOfertaVenda the codicaoOfertaVenda to set
	 */
	public void setCodicaoOfertaVenda(Character codicaoOfertaVenda) {
		this.codicaoOfertaVenda = codicaoOfertaVenda;
	}

	/**
	 * @return the indicatorDireto
	 */
	public Character getIndicatorDireto() {
		return indicatorDireto;
	}

	/**
	 * @param indicatorDireto the indicatorDireto to set
	 */
	public void setIndicatorDireto(Character indicatorDireto) {
		this.indicatorDireto = indicatorDireto;
	}

	/**
	 * @return the codigoCorretoraCompra
	 */
	public Integer getCodigoCorretoraCompra() {
		return codigoCorretoraCompra;
	}

	/**
	 * @param codigoCorretoraCompra the codigoCorretoraCompra to set
	 */
	public void setCodigoCorretoraCompra(Integer codigoCorretoraCompra) {
		this.codigoCorretoraCompra = codigoCorretoraCompra;
	}

	/**
	 * @return the codigoCorretoraVenda
	 */
	public Integer getCodigoCorretoraVenda() {
		return codigoCorretoraVenda;
	}

	/**
	 * @param codigoCorretoraVenda the codigoCorretoraVenda to set
	 */
	public void setCodigoCorretoraVenda(Integer codigoCorretoraVenda) {
		this.codigoCorretoraVenda = codigoCorretoraVenda;
	}

}