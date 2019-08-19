package com.marretti.stock;

import java.util.Calendar;
import java.util.StringTokenizer;
import java.util.TimeZone;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.core.io.FileSystemResource;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.marretti.stock.business.sync.ISynchronizeService;
import com.marretti.stock.exception.StockExchangeException;
import com.marretti.stock.util.StockExchangeConstants;
import com.marretti.stock.util.StockExchangeUtils;
import com.marretti.stock.util.TimeStampUtils;

@EnableScheduling
@SpringBootApplication
public class Application {
	
	@Autowired
	private ISynchronizeService synchronizeService;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void started() throws StockExchangeException {
		TimeZone.setDefault(TimeZone.getTimeZone("America/Sao_Paulo"));
		initSync();
	}

	private void initSync() throws StockExchangeException {
		StringTokenizer stBovespaDaily = new StringTokenizer(StockExchangeUtils.getPropertiesValue("sync.bovespa.daily.startDate"));
		StringTokenizer stBovespaIntraday = new StringTokenizer(StockExchangeUtils.getPropertiesValue("sync.bovespa.intraday.startDate"));
		StringTokenizer stBmfIntraday = new StringTokenizer(StockExchangeUtils.getPropertiesValue("sync.bmf.intraday.startDate"));
		Calendar startDateBovespaDaily = TimeStampUtils.getToday();
		Calendar startDateBovespaIntraday = TimeStampUtils.getToday();
		Calendar startDateBmfIntraday = TimeStampUtils.getToday();
		Calendar endDate = TimeStampUtils.getToday();
		
		startDateBovespaDaily.set(Calendar.DATE, Integer.valueOf(stBovespaDaily.nextToken("/")));
		startDateBovespaDaily.set(Calendar.MONTH, (Integer.valueOf(stBovespaDaily.nextToken("/")) - 1));
		startDateBovespaDaily.set(Calendar.YEAR, Integer.valueOf(stBovespaDaily.nextToken("/")));
		
		startDateBovespaIntraday.set(Calendar.DATE, Integer.valueOf(stBovespaIntraday.nextToken("/")));
		startDateBovespaIntraday.set(Calendar.MONTH, (Integer.valueOf(stBovespaIntraday.nextToken("/")) - 1));
		startDateBovespaIntraday.set(Calendar.YEAR, Integer.valueOf(stBovespaIntraday.nextToken("/")));
		
		startDateBmfIntraday.set(Calendar.DATE, Integer.valueOf(stBmfIntraday.nextToken("/")));
		startDateBmfIntraday.set(Calendar.MONTH, (Integer.valueOf(stBmfIntraday.nextToken("/")) - 1));
		startDateBmfIntraday.set(Calendar.YEAR, Integer.valueOf(stBmfIntraday.nextToken("/")));
		
		
		synchronizeService.synchronizeB3BovespaDaily(null, startDateBovespaDaily, endDate);
		synchronizeService.synchronizeB3BovespaIntraday(null, startDateBovespaIntraday, endDate);
		synchronizeService.synchronizeB3BMFIntraday(null, startDateBmfIntraday, endDate);
		
	}
	
	@Bean
	public static PropertySourcesPlaceholderConfigurer propertySourcesPlaceholderConfigurer() {
	    PropertySourcesPlaceholderConfigurer properties = new PropertySourcesPlaceholderConfigurer();
	    properties.setLocation(new FileSystemResource(StockExchangeConstants.PROPERTIES_CONFIG));
	    properties.setIgnoreResourceNotFound(false);
	    return properties;
	}

}
