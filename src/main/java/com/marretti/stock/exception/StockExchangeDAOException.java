package com.marretti.stock.exception;

public class StockExchangeDAOException extends Throwable {
	
	private static final long serialVersionUID = 4169933828864494609L;

	public StockExchangeDAOException() {
		super();
	}

	public StockExchangeDAOException(String message) {
		super(message);
	}

	public StockExchangeDAOException(String message, Throwable cause) {
		super(message, cause);
	}

	public StockExchangeDAOException(Throwable cause) {
		super(cause);
	}

}
