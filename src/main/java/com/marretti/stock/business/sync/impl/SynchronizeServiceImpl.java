package com.marretti.stock.business.sync.impl;

import java.io.FileNotFoundException;
import java.util.Calendar;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.marretti.stock.business.sync.ISynchronizeService;
import com.marretti.stock.data.dao.sync.ISynchronizeDAO;
import com.marretti.stock.exception.StockExchangeDAOException;
import com.marretti.stock.exception.StockExchangeException;
import com.marretti.stock.model.Stock;
import com.marretti.stock.util.TimeStampUtils;

@Service
public class SynchronizeServiceImpl implements ISynchronizeService {
	
	/** Refência para logManager */
	private final Logger logManager = LogManager.getLogger(this.getClass());
	
	@Autowired
	private ISynchronizeDAO synchronizeDAO;

	/** {@inheritDoc} */
	@Override
	public void synchronizeB3BovespaDaily(List<Stock> stocks, Calendar startDate, Calendar endDate) throws StockExchangeException {
		try {
			for (int i = startDate.get(Calendar.YEAR); i <= endDate.get(Calendar.YEAR); i++) {
				Calendar year = TimeStampUtils.getToday();
				year.setTimeInMillis(startDate.getTimeInMillis());
				year.set(Calendar.YEAR, i);
				synchronizeDAO.synchronizeB3BovespaDaily(stocks, year, endDate);
			}
		} catch (Throwable e) {
			logManager.error(this, e);
			throw new StockExchangeException(e);
		}
	}

	/** {@inheritDoc} */
	@Override
	public void synchronizeB3BovespaIntraday(List<Stock> stocks, Calendar startDate, Calendar endDate) throws StockExchangeException {
		try {
			Calendar date = TimeStampUtils.getToday();
			date.setTimeInMillis(startDate.getTimeInMillis());
			Calendar eDate = TimeStampUtils.getToday();
			eDate.setTimeInMillis(endDate.getTimeInMillis());
			eDate.add(Calendar.DATE, 1);
			while (date.before(eDate)) {
				try {
					synchronizeDAO.synchronizeB3BovespaIntraday(stocks, date, eDate);
				} catch (StockExchangeDAOException e) {
					if (e.getCause() instanceof FileNotFoundException) {
						// weekend, holiday or file not found, try next one.
					}
					logManager.error(this, e);
				}
				date.add(Calendar.DATE, 1);
			}
		} catch (Throwable e) {
			logManager.error(this, e);
			throw new StockExchangeException(e);
		}
	}

	@Override
	public void synchronizeB3BMFIntraday(List<Stock> stocks, Calendar startDate, Calendar endDate) throws StockExchangeException {
		try {
			Calendar date = TimeStampUtils.getToday();
			date.setTimeInMillis(startDate.getTimeInMillis());
			Calendar eDate = TimeStampUtils.getToday();
			eDate.setTimeInMillis(endDate.getTimeInMillis());
			eDate.add(Calendar.DATE, 1);
			while (date.before(eDate)) {
				try {
					synchronizeDAO.synchronizeB3BMFIntraday(stocks, date, eDate);
				} catch (StockExchangeDAOException e) {
					if (e.getCause() instanceof FileNotFoundException) {
						logManager.info("Arquivo não encontrado: [" + e.getCause().getMessage() + "]");
					} else {
						throw e;
					}
				}
				date.add(Calendar.DATE, 1);
			}
		} catch (Throwable e) {
			logManager.error(this, e);
			throw new StockExchangeException(e);
		}
	}

}
