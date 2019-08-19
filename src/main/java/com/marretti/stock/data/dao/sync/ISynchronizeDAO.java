package com.marretti.stock.data.dao.sync;

import java.util.Calendar;
import java.util.List;

import com.marretti.stock.exception.StockExchangeDAOException;
import com.marretti.stock.model.Stock;

public interface ISynchronizeDAO {

	/** Synchronize B3 Bovespa Daily Data */
	public void synchronizeB3BovespaDaily(List<Stock> stocks, Calendar startDate, Calendar endDate) throws StockExchangeDAOException;

	/** Synchronize B3 Bovespa Daily Data */
	public void synchronizeB3BovespaIntraday(List<Stock> stocks, Calendar startDate, Calendar endDate) throws StockExchangeDAOException;
	
	/** Synchronize B3 BM&F Data */
	public void synchronizeB3BMFIntraday(List<Stock> stocks, Calendar startDate, Calendar endDate) throws StockExchangeDAOException;

}
