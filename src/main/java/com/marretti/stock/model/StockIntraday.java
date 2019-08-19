package com.marretti.stock.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.annotation.DateTimeFormat.ISO;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonInclude.Include;

@Entity
@Table(name = "tintraday")
public class StockIntraday implements Serializable {

	/** serial version */
	private static final long serialVersionUID = -4060626134927150739L;
	
	/** Armazena o código do instrumento */
	@JsonInclude(value = Include.NON_NULL)
	@Id
	private String symbol;
	
	@JsonInclude(value = Include.NON_NULL)
	@Id
	/** Número do negócio */
	private BigInteger tradeNumber;
	
	@JsonInclude(value = Include.NON_NULL)
	/** 1 - Trade, 2 - Cancelled */
	private Character tradeIndicator;
	
	@JsonInclude(value = Include.NON_NULL)
	/** Preço do negócio */
	private BigDecimal price;
	
	/** Armazena o volume do dia */ 
	@JsonInclude(value = Include.NON_NULL)
	private BigInteger volume;
	
	@JsonInclude(value = Include.NON_NULL)
	/** Código de identificação da corretora de compra */
	private Integer brokerSell;
	
	@JsonInclude(value = Include.NON_NULL)
	/** Código de identificação da corretora de venda */
	private Integer brokerBuy;
	
	/** Armazena a data */
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX")
	@DateTimeFormat(iso = ISO.DATE_TIME)
	@JsonInclude(value = Include.NON_NULL)
	@Id
	private Date date;

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
	 * @return the tradeNumber
	 */
	public BigInteger getTradeNumber() {
		return tradeNumber;
	}

	/**
	 * @param tradeNumber the tradeNumber to set
	 */
	public void setTradeNumber(BigInteger tradeNumber) {
		this.tradeNumber = tradeNumber;
	}

	/**
	 * @return the tradeIndicator
	 */
	public Character getTradeIndicator() {
		return tradeIndicator;
	}

	/**
	 * @param tradeIndicator the tradeIndicator to set
	 */
	public void setTradeIndicator(Character tradeIndicator) {
		this.tradeIndicator = tradeIndicator;
	}

	/**
	 * @return the price
	 */
	public BigDecimal getPrice() {
		return price;
	}

	/**
	 * @param price the price to set
	 */
	public void setPrice(BigDecimal price) {
		this.price = price;
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
	 * @return the brokerSell
	 */
	public Integer getBrokerSell() {
		return brokerSell;
	}

	/**
	 * @param brokerSell the brokerSell to set
	 */
	public void setBrokerSell(Integer brokerSell) {
		this.brokerSell = brokerSell;
	}

	/**
	 * @return the brokerBuy
	 */
	public Integer getBrokerBuy() {
		return brokerBuy;
	}

	/**
	 * @param brokerBuy the brokerBuy to set
	 */
	public void setBrokerBuy(Integer brokerBuy) {
		this.brokerBuy = brokerBuy;
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

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((date == null) ? 0 : date.hashCode());
		result = prime * result + ((symbol == null) ? 0 : symbol.hashCode());
		result = prime * result + ((tradeNumber == null) ? 0 : tradeNumber.hashCode());
		return result;
	}

	/* (non-Javadoc)
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
		StockIntraday other = (StockIntraday) obj;
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
		if (tradeNumber == null) {
			if (other.tradeNumber != null)
				return false;
		} else if (!tradeNumber.equals(other.tradeNumber))
			return false;
		return true;
	}
	
}
