package com.marretti.stock.exception;

public class StockExchangeException extends Throwable {

	private static final long serialVersionUID = 4169933828864494609L;
	
	public StockExchangeException() {
		super();
	}

	public StockExchangeException(String message) {
		super(message);
	}

	public StockExchangeException(String message, Throwable cause) {
		super(message, cause);
	}

	public StockExchangeException(Throwable cause) {
		super(cause);
	}

}
