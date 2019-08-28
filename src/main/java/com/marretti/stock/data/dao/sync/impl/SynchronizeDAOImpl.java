package com.marretti.stock.data.dao.sync.impl;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.marretti.stock.data.dao.sync.ISynchronizeDAO;
import com.marretti.stock.exception.StockExchangeDAOException;
import com.marretti.stock.model.Stock;
import com.marretti.stock.model.StockBase;
import com.marretti.stock.model.StockIntraday;
import com.marretti.stock.model.sync.CotacaoHistorica;
import com.marretti.stock.model.sync.CotacaoIntraday;
import com.marretti.stock.model.sync.RegistroCotacaoDiaria;
import com.marretti.stock.util.StockExchangeConstants;
import com.marretti.stock.util.StockExchangeUtils;
import com.marretti.stock.util.TimeStampUtils;

@Repository
public class SynchronizeDAOImpl implements ISynchronizeDAO {

	/** Refência para logManager */
	private final Logger logManager = LogManager.getLogger(this.getClass());

	@Autowired
	private EntityManagerFactory emf;

	/** {@inheritDoc} */
	@Override
	public void synchronizeB3BovespaDaily(List<Stock> stocks, Calendar startDate, Calendar endDate)
			throws StockExchangeDAOException {
		try {
			logManager.info("Sincronizando Bovespa Daily: [" + startDate.getTime() + "," + endDate.getTime() + "]");
			String file = StockExchangeConstants.BOVESPA_HISTORICAL_DAILY_FILENAME.replace("%", String.valueOf(startDate.get(Calendar.YEAR)));
			String fileName = StockExchangeUtils.getPropertiesValue("storage.bovespa.historial.daily").concat(file);
			String urlDownload = StockExchangeUtils.getPropertiesValue("url.bovespa.download.historical.daily").concat(file);
			CotacaoHistorica cotacaoHistorica = StockExchangeUtils.lerLinhasCotacaoDiaria(StockExchangeUtils.readBovespaFile(startDate, fileName, urlDownload));
			List<StockBase> retorno = parseBovespaCotacaoDiaria(cotacaoHistorica, startDate, endDate);
			this.syncSymbols(retorno);
			this.syncSymbolsData(retorno, startDate, endDate);
		} catch (Throwable e) {
			logManager.error(this, e);
			throw new StockExchangeDAOException(e);
		}
	}

	/** Synchronize Symbols 
	 * @param <E>*/
	private <E> void syncSymbols(List<E> retorno) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			List<Stock> saveDAO = new ArrayList<>();
			CriteriaQuery<Stock> criteriaQuery = entityManager.getCriteriaBuilder().createQuery(Stock.class);
			criteriaQuery.from(Stock.class);
			List<Stock> retDAO = entityManager.createQuery(criteriaQuery).getResultList();
			if (retorno.get(0) instanceof StockBase) {
				retorno.forEach(stock -> {
					Stock symbol = new Stock(((StockBase) stock).getSymbol());
					if (!retDAO.contains(symbol) && !saveDAO.contains(symbol)) {
						saveDAO.add(symbol);
					}
				});
			}
			if (retorno.get(0) instanceof StockIntraday) {
				retorno.forEach(stock -> {
					Stock symbol = new Stock(((StockIntraday) stock).getSymbol());
					if (!retDAO.contains(symbol) && !saveDAO.contains(symbol)) {
						saveDAO.add(symbol);
					}
				});
			}
			if (!saveDAO.isEmpty()) {
				entityManager.getTransaction().begin();
				saveDAO.forEach(symbol -> entityManager.persist(symbol));
				entityManager.getTransaction().commit();
			}
		} catch (Throwable e) {
			logManager.error(this, e);
			throw e;
		} finally {
			entityManager.close();
		}
	}
	
	/** Synchronize Data of Symbols */
	private void syncSymbolsData(List<StockBase> retorno, Calendar startDate, Calendar endDate) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			this.removeDailyBeforeInsert(startDate, endDate);
			entityManager.getTransaction().begin();
			retorno.forEach(data -> entityManager.persist(data));
			entityManager.getTransaction().commit();
		} catch (Throwable e) {
			logManager.error(this, e);
			throw e;
		} finally {
			entityManager.close();
		}
	}

	/** Remove old data before insert new one */
	private void removeDailyBeforeInsert(Calendar startDate, Calendar endDate) {
		EntityManager entityManager = emf.createEntityManager();
		try {
			entityManager.getTransaction().begin();
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaDelete<StockBase> delete = cb.createCriteriaDelete(StockBase.class);
			Root<StockBase> e = delete.from(StockBase.class);
			delete.where(cb.greaterThanOrEqualTo(e.get("date"), startDate.getTime()), cb.lessThanOrEqualTo(e.get("date"), endDate.getTime()));
			entityManager.createQuery(delete).executeUpdate();
			entityManager.getTransaction().commit();
		} catch (Throwable e) {
			logManager.error(this, e);
			throw e;
		} finally {
			entityManager.close();
		}
	}

	/** Parse Cotacao Diaria */
	private List<StockBase> parseBovespaCotacaoDiaria(CotacaoHistorica cotacaoHistorica, Calendar startDate, Calendar endDate) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyyMMdd");
		List<StockBase> retorno = new ArrayList<StockBase>();
		Iterator<RegistroCotacaoDiaria> iterator = cotacaoHistorica.getRegistroCotacaoDiariaList().iterator();
		while (iterator.hasNext()) {
			StockBase stock = new StockBase();
			RegistroCotacaoDiaria registroCotacaoDiaria = iterator.next();
			String codNeg = registroCotacaoDiaria.getCODNEG();
			if (codNeg.matches(StockExchangeUtils.getPropertiesValue("regex.bovespa.daily"))) {
				stock.setSymbol(registroCotacaoDiaria.getCODNEG());
				stock.setOpen(new BigDecimal(registroCotacaoDiaria.getPREABE()));
				stock.setClose(new BigDecimal(registroCotacaoDiaria.getPREULT()));
				stock.setCloseAdj(new BigDecimal(registroCotacaoDiaria.getPREMED()));
				stock.setHigh(new BigDecimal(registroCotacaoDiaria.getPREMAX()));
				stock.setLow(new BigDecimal(registroCotacaoDiaria.getPREMIN()));
				stock.setVolume(new BigInteger(registroCotacaoDiaria.getQUATOT()));
				stock.setDate(df.parse(registroCotacaoDiaria.getDATAPG()));
				if (TimeStampUtils.dateIsOnInterval(startDate, endDate, stock.getDate())) {
					retorno.add(stock);
				}
			}
		}
		return retorno;
	}

	/** {@inheritDoc} */
	@Override
	public void synchronizeB3BovespaIntraday(List<Stock> stocks, Calendar startDate, Calendar endDate)
			throws StockExchangeDAOException {
		EntityManager entityManager = emf.createEntityManager();
		try {
			logManager.info("Sincronizando Bovespa Intraday: [" + startDate.getTime() + "," + endDate.getTime() + "]");
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			String fileName = StockExchangeUtils.getPropertiesValue("storage.bovespa.historial.intraday").concat(StockExchangeConstants.BOVESPA_HISTORICAL_INTRADAY_FILENAME.replace("%",df.format(startDate.getTime())));
			String urlDownload = StockExchangeUtils.getPropertiesValue("url.bovespa.download.historical.intraday").concat(StockExchangeConstants.BOVESPA_HISTORICAL_INTRADAY_FILENAME.replace("%",df.format(startDate.getTime())));
			CotacaoIntraday cotacaoIntraday = StockExchangeUtils.lerLinhasCotacaoIntraday(StockExchangeUtils.readBovespaFile(startDate, fileName, urlDownload), stocks);
			List<StockIntraday> retorno = this.parseBovespaCotacaoIntraday(cotacaoIntraday, startDate, endDate);
			this.syncSymbols(retorno);
			entityManager.getTransaction().begin();
			retorno.forEach(data -> entityManager.persist(data));
			entityManager.getTransaction().commit();
		} catch (Throwable e) {
			logManager.error(this, e);
			throw new StockExchangeDAOException(e);
		} finally {
			entityManager.close();
		}
	}
	
	/** Parse Cotacao Bovespa Intraday */
	private List<StockIntraday> parseBovespaCotacaoIntraday(CotacaoIntraday cotacaoIntraday, Calendar startDate, Calendar endDate) throws ParseException {
		List<StockIntraday> retorno = new ArrayList<StockIntraday>();
		cotacaoIntraday.getListIntraday().forEach(registroIntraday -> {
			String codNeg = registroIntraday.getSimboloInstrumento();
			if (codNeg.matches(StockExchangeUtils.getPropertiesValue("regex.bovespa.intraday"))) {
				StockIntraday intraday = new StockIntraday();
				intraday.setSymbol(registroIntraday.getSimboloInstrumento());
				intraday.setVolume(registroIntraday.getQuantidade());
				intraday.setDate(TimeStampUtils.convertLocalDateAndTime(registroIntraday.getDataSessao(), registroIntraday.getHorarioNegocio()));
				intraday.setTradeIndicator(registroIntraday.getIndicadorAnulacao());
				intraday.setTradeNumber(registroIntraday.getNumeroNegocio());
				intraday.setPrice(registroIntraday.getPrecoNegocio());
				intraday.setBrokerBuy(registroIntraday.getCodigoCorretoraCompra());
				intraday.setBrokerSell(registroIntraday.getCodigoCorretoraVenda());
				if (TimeStampUtils.dateIsOnInterval(startDate, endDate, intraday.getDate())) {
					retorno.add(intraday);
				}
			}
		});
		return retorno;
	}

	/** {@inheritDoc} */
	@Override
	public void synchronizeB3BMFIntraday(List<Stock> stocks, Calendar startDate, Calendar endDate) throws StockExchangeDAOException {
		EntityManager entityManager = emf.createEntityManager();
		try {
			logManager.info("Sincronizando BM&F Intraday: [" + startDate.getTime() + "," + endDate.getTime() + "]");
			DateFormat df = new SimpleDateFormat("yyyyMMdd");
			String fileName = StockExchangeUtils.getPropertiesValue("storage.bmf.historial.intraday").concat(StockExchangeConstants.BMF_HISTORICAL_INTRADAY_FILENAME.replace("%",df.format(startDate.getTime())));
			String urlDownload = StockExchangeUtils.getPropertiesValue("url.bmf.download.historical.intraday").concat(StockExchangeConstants.BMF_HISTORICAL_INTRADAY_FILENAME.replace("%",df.format(startDate.getTime())));
			CotacaoIntraday cotacaoIntraday = StockExchangeUtils.lerLinhasCotacaoIntraday(StockExchangeUtils.readBovespaFile(startDate, fileName, urlDownload), stocks);
			List<StockIntraday> retorno = this.parseBMFCotacaoIntraday(cotacaoIntraday, startDate, endDate);
			this.syncSymbols(retorno);
			entityManager.getTransaction().begin();
			retorno.forEach(data -> entityManager.persist(data));
			entityManager.getTransaction().commit();
		} catch (Throwable e) {
			logManager.error(this, e);
			throw new StockExchangeDAOException(e);
		} finally {
			entityManager.close();
		}
	}

	/** Parse Cotacao Intraday */
	private List<StockIntraday> parseBMFCotacaoIntraday(CotacaoIntraday cotacaoIntraday, Calendar startDate, Calendar endDate) throws ParseException {
		List<StockIntraday> retorno = new ArrayList<StockIntraday>();
		cotacaoIntraday.getListIntraday().forEach(registroIntraday -> {
			String codNeg = registroIntraday.getSimboloInstrumento();
			if (codNeg.matches(StockExchangeUtils.getPropertiesValue("regex.bmf.intraday"))) {
				StockIntraday intraday = new StockIntraday();
				intraday.setSymbol(registroIntraday.getSimboloInstrumento());
				intraday.setVolume(registroIntraday.getQuantidade());
				intraday.setDate(TimeStampUtils.convertLocalDateAndTime(registroIntraday.getDataSessao(), registroIntraday.getHorarioNegocio()));
				intraday.setTradeIndicator(registroIntraday.getIndicadorAnulacao());
				intraday.setTradeNumber(registroIntraday.getNumeroNegocio());
				intraday.setPrice(registroIntraday.getPrecoNegocio());
				intraday.setBrokerBuy(registroIntraday.getCodigoCorretoraCompra());
				intraday.setBrokerSell(registroIntraday.getCodigoCorretoraVenda());
				if (TimeStampUtils.dateIsOnInterval(startDate, endDate, intraday.getDate())) {
					retorno.add(intraday);
				}
			}
		});
		return retorno;
	}

}
