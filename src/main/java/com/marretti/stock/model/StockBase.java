package com.marretti.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "tdaily")
public class StockBase implements Serializable {

	/** Versão serial */
	private static final long serialVersionUID = -7881253216408834744L;

	/** Armazena o código do instrumento */
	@JsonInclude(value = Include.NON_NULL)
	@Id
	private String symbol;

	/** Armazena o valor de Abertura da ação */
	@JsonInclude(value = Include.NON_NULL)
	private BigDecimal open;

	/** Armazena o valor de Fechamento da ação */
	@JsonInclude(value = Include.NON_NULL)
	private BigDecimal close;

	/** Armazena o valor de Fechamento Ajustado da ação */
	@JsonInclude(value = Include.NON_NULL)
	@Column(name="closeAdj")
	private BigDecimal closeAdj;

	/** Armazena o maior valor do dia da ação */
	@JsonInclude(value = Include.NON_NULL)
	private BigDecimal high;

	/** Armazena o menor valor do dia da ação */
	@JsonInclude(value = Include.NON_NULL)
	private BigDecimal low;

	/** Armazena o volume do dia */
	@JsonInclude(value = Include.NON_NULL)
	private BigInteger volume;

	/** Armazena a data */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss.SSSXXX")
	@JsonInclude(value = Include.NON_NULL)
	@Id
	private Date date;


	public StockBase() {
		super();
	}

	public StockBase(String symbol) {
		super();
		this.symbol = symbol;
	}

	/**
	 * @return the symbol
	 */
	public String getSymbol() {
		return symbol;
	}

	/**
	 * @param symbol the symbol to set
	 */
	public void setSymbol(String symbol) {
		this.symbol = symbol;
	}

	/**
	 * @return the open
	 */
	public BigDecimal getOpen() {
		return open;
	}

	/**
	 * @param open the open to set
	 */
	public void setOpen(BigDecimal open) {
		this.open = open;
	}

	/**
	 * @return the close
	 */
	public BigDecimal getClose() {
		return close;
	}

	/**
	 * @param close the close to set
	 */
	public void setClose(BigDecimal close) {
		this.close = close;
	}

	/**
	 * @return the closeAdj
	 */
	public BigDecimal getCloseAdj() {
		return closeAdj;
	}

	/**
	 * @param closeAdj the closeAdj to set
	 */
	public void setCloseAdj(BigDecimal closeAdj) {
		this.closeAdj = closeAdj;
	}

	/**
	 * @return the high
	 */
	public BigDecimal getHigh() {
		return high;
	}

	/**
	 * @param high the high to set
	 */
	public void setHigh(BigDecimal high) {
		this.high = high;
	}

	/**
	 * @return the low
	 */
	public BigDecimal getLow() {
		return low;
	}

	/**
	 * @param low the low to set
	 */
	public void setLow(BigDecimal low) {
		this.low = low;
	}

	/**
	 * @return the volume
	 */
	public BigInteger getVolume() {
		return volume;
	}

	/**
	 * @param volume the volume to set
	 */
	public void setVolume(BigInteger volume) {
		this.volume = volume;
	}

	/**
	 * @return the date
	 */
	public Date getDate() {
		return date;
	}

	/**
	 * @param date the date to set
	 */
	public void setDate(Date date) {
		this.date = date;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		StockBase other = (StockBase) obj;
		if (date == null) {
			if (other.date != null)
				return false;
		} else if (!date.equals(other.date))
			return false;
		if (symbol == null) {
			if (other.symbol != null)
				return false;
		} else if (!symbol.equals(other.symbol))
			return false;
		return true;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "StockBase [symbol=" + symbol + ", date=" + date + "]";
	}
	
}
