package com.marretti.stock.util;

import java.nio.charset.Charset;
import java.util.Properties;

public final class StockExchangeConstants {
	
	/** Construtor privado de classe utilitária */
	private StockExchangeConstants() { 
		throw new IllegalStateException("Utility class");
	}
	
	public static final Integer API_GENERIC_ERROR = -99;
	
	/** Armazena o nome do arquivo Bovespa */
	public static final String BOVESPA_HISTORICAL_DAILY_FILENAME = "COTAHIST_A%.zip";
	
	/** Armazena o nome do arquivo Bovespa Intraday */
	public static final String BOVESPA_HISTORICAL_INTRADAY_FILENAME = "NEG_%.zip";
	
	/** Armazena o nome do arquivo BM&F Intraday */
	public static final String BMF_HISTORICAL_INTRADAY_FILENAME = "NEG_BMF_%.zip";
	
	/** Charset para leitura do arquivo */
	public static final Charset DEFAULT_CHARSET_FILE_DOWNLOAD = Charset.forName("ISO-8859-1");
	
	/** Armazena localização do {@link Properties}*/
	public static final String PROPERTIES_CONFIG = "/marretti/stockexchange/config/application.properties";

}
