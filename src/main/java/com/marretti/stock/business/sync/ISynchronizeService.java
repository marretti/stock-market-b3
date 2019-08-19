package com.marretti.stock.business.sync;

import java.util.Calendar;
import java.util.List;

import com.marretti.stock.exception.StockExchangeException;
import com.marretti.stock.model.Stock;

public interface ISynchronizeService {

	/** Synchronize B3 Bovespa Daily Data */
	public void synchronizeB3BovespaDaily(List<Stock> stocks, Calendar startDate, Calendar endDate) throws StockExchangeException;

	/** Synchronize B3 Bovespa Intraday Data */
	public void synchronizeB3BovespaIntraday(List<Stock> stocks, Calendar startDate, Calendar endDate) throws StockExchangeException;
	
	/** Synchronize B3 BM&F Intraday Data */
	public void synchronizeB3BMFIntraday(List<Stock> stocks, Calendar startDate, Calendar endDate) throws StockExchangeException;


}
