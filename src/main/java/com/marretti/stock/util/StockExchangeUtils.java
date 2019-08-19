package com.marretti.stock.util;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.InterruptedIOException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.net.SocketException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;
import java.util.zip.GZIPInputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

import org.apache.commons.lang3.StringUtils;

import com.marretti.stock.model.Stock;
import com.marretti.stock.model.sync.CotacaoHistorica;
import com.marretti.stock.model.sync.CotacaoIntraday;
import com.marretti.stock.model.sync.RegistroCotacaoDiaria;
import com.marretti.stock.model.sync.RegistroCotacaoDiariaBuilder;
import com.marretti.stock.model.sync.RegistroTrailer;
import com.marretti.stock.model.sync.TickIntraday;

public class StockExchangeUtils {
	
	/** Construtor privado de classe utilitária */
	private StockExchangeUtils() { 
		throw new IllegalStateException("Utility class");
	}
	
	private static final String CODIGO_REGISTRO_COTACAO_DIARIA = "1";
	
    private static final String CODIGO_REGISTRO_TRAILER = "99";
	
	private static Properties prop = null;

	public static Path downloadFileFromURL(String sourceURL, File targetDirectory) throws IOException {
		Path targetPath = null;
		try {
			URL url = new URL(sourceURL);
			targetPath = targetDirectory.toPath();
			Files.copy(url.openStream(), targetPath, StandardCopyOption.REPLACE_EXISTING);
		} catch (SocketException | InterruptedIOException e) {
			targetPath = downloadFileFromURL(sourceURL, targetDirectory);
		}
		return targetPath;
    }

	/**
	 * Obtém chave de properties
	 */
	public static String getPropertiesValue(String chave) {
		String retorno = null;
		try {
			if (prop == null) {
				prop = new Properties();
				prop.load(new FileInputStream(StockExchangeConstants.PROPERTIES_CONFIG));
			}
			retorno = prop.getProperty(chave);
		} catch (IOException e) {
			return null;
		}
		return retorno;
	}
	
	/** Trata valores */
	public static String trataValores(String valor) {
        return trataValores(valor, false);
    }
	
	/** Trata valores */
	public static String trataValores(String valor, boolean isDecimal) {
        valor = removeZerosAEsquerda(valor.trim());
        if (isDecimal) {
            valor = adicionaPontoDecimalDuasCasas(valor);
        }
        return valor;
    }
	
	/** Remove 0 a esquerda */
	private static String removeZerosAEsquerda(String valor) {
        if (valor != null) {
            while (valor.startsWith("0") && valor.length() > 1) {
                valor = valor.replaceFirst("0", "");
            }
        }
        return valor;
    }
	
	/** Adiciona Ponto Decimal */
	private static String adicionaPontoDecimalDuasCasas(String valor) {
        if (valor != null) {
            while (valor.length() < 3) {
                valor = "0" + valor;
            }
            int tam = valor.length();
            valor = valor.substring(0, tam - 2) + "." + valor.substring(tam - 2, tam);
        }
        return valor;
    }
	
	private static void lerRegistrosArquivoIntraday(String linha, CotacaoIntraday cotacaoIntraday, List<Stock> stocks) {
		final String tipReg = StockExchangeUtils.trataValores(linha.substring(0, 2));
		if ("RH".equals(tipReg) || "RT".equals(tipReg)) {
			//TODO
		} else {
			TickIntraday tickIntraday = new TickIntraday();
			tickIntraday.setDataSessao(LocalDate.of(Integer.valueOf(linha.substring(0, 4)), Integer.valueOf(linha.substring(5, 7)), Integer.valueOf(linha.substring(8, 10))));
			tickIntraday.setSimboloInstrumento(StockExchangeUtils.trataValores(linha.substring(11, 61)));
			tickIntraday.setNumeroNegocio(new BigInteger(StockExchangeUtils.trataValores(linha.substring(62, 72))));
			tickIntraday.setPrecoNegocio(new BigDecimal(StockExchangeUtils.trataValores(linha.substring(73, 93))));
			tickIntraday.setQuantidade(new BigInteger(StockExchangeUtils.trataValores(linha.substring(94, 112))));
			tickIntraday.setHorarioNegocio(LocalTime.of(Integer.valueOf(linha.substring(113, 115)), Integer.valueOf(linha.substring(116, 118)), Integer.valueOf(linha.substring(119, 121)), Integer.valueOf(StringUtils.rightPad(linha.substring(122, 125), 9, '0'))));
			tickIntraday.setIndicadorAnulacao(StockExchangeUtils.trataValores(linha.substring(126, 127)).charAt(0));
			tickIntraday.setCodigoCorretoraCompra(Integer.valueOf(StockExchangeUtils.trataValores(linha.substring(220, 228))));
			tickIntraday.setCodigoCorretoraVenda(Integer.valueOf(StockExchangeUtils.trataValores(linha.substring(229, 237))));
			cotacaoIntraday.addTickIntraday(tickIntraday);
		}
	}
	
	/** Ler linhas de cotação intraday */
	public static CotacaoIntraday lerLinhasCotacaoIntraday(List<String> lines, List<Stock> stock) {
		CotacaoIntraday cotacaoHistorica = new CotacaoIntraday();
		if (lines != null) {
	        for (String linha : lines) {
	        	lerRegistrosArquivoIntraday(linha, cotacaoHistorica, stock);
	        }	
		}
        return cotacaoHistorica;
    }
	
	/** Ler arquivo B3 - linha a linha */
	public static List<String> readBovespaFile(Calendar year, String diretorio, String urlDownload) throws IOException {
		ZipFile zipFile = null;
		List<String> retorno = null;
		InputStream in = null;
		InputStreamReader inputReader = null;
		BufferedReader br = null;
		File targetDirectory = null;
		Path dirLocal =null;
		try {
			targetDirectory = new File(diretorio);
			new File(targetDirectory.getParent()).mkdirs();
			dirLocal = new File(diretorio).toPath();
			if (!dirLocal.toFile().exists()) {
				StockExchangeUtils.downloadFileFromURL(urlDownload, targetDirectory);
			} else if (TimeStampUtils.isThisYear(year) && diretorio.contains("daily")
					&& TimeStampUtils.isMoreThanHourFromNow(dirLocal.toFile().lastModified())) {
				StockExchangeUtils.downloadFileFromURL(urlDownload, targetDirectory);
			}
			zipFile = new ZipFile(dirLocal.toFile(), StockExchangeConstants.DEFAULT_CHARSET_FILE_DOWNLOAD);
			in = zipFile.getInputStream(new ZipEntry(zipFile.entries().nextElement()));
			inputReader = new InputStreamReader(in, StockExchangeConstants.DEFAULT_CHARSET_FILE_DOWNLOAD);
			br = new BufferedReader(inputReader);
			retorno = br.lines().collect(Collectors.toList());
		} catch (ZipException e) {
			//Corrompido, tentar efetuar download novamente.
			try {
				StockExchangeUtils.downloadFileFromURL(urlDownload, targetDirectory);
				zipFile = new ZipFile(dirLocal.toFile(), StockExchangeConstants.DEFAULT_CHARSET_FILE_DOWNLOAD);
				in = zipFile.getInputStream(new ZipEntry(zipFile.entries().nextElement()));
				inputReader = new InputStreamReader(in, StockExchangeConstants.DEFAULT_CHARSET_FILE_DOWNLOAD);
				br = new BufferedReader(inputReader);
				retorno = br.lines().collect(Collectors.toList());
			} catch (IOException e1) {
				throw e;
			}
		} catch (FileNotFoundException e) {
			try {
				diretorio = diretorio.replace(".zip", ".gz");
				targetDirectory = new File(diretorio);
				urlDownload = urlDownload.replace(".zip", ".gz");
				dirLocal = new File(diretorio).toPath();
				if (!dirLocal.toFile().exists()) {
					StockExchangeUtils.downloadFileFromURL(urlDownload, targetDirectory);
				}
				in = new GZIPInputStream(new FileInputStream(dirLocal.toFile()));
				inputReader = new InputStreamReader(in, StockExchangeConstants.DEFAULT_CHARSET_FILE_DOWNLOAD);
				br = new BufferedReader(inputReader);
				retorno = br.lines().collect(Collectors.toList());
			} catch (IOException e1) {
				throw e;
			}
		}
		catch (IOException e) {
			throw e;
		} finally {
			try {
				if (zipFile != null) {
					zipFile.close();	
				}
				if (in != null) {
					in.close();	
				}
				if (inputReader != null) {
					inputReader.close();	
				}
				if (br != null) {
					br.close();	
				}
			} catch (IOException e) {
				throw e;
			}
		}
		return retorno;
	}
	
	/** Ler arquivos de cotação diário */
	private static void lerRegistrosArquivoDiario(String linha, CotacaoHistorica cotacaoHistorica) {
        final String tipReg = StockExchangeUtils.trataValores(linha.substring(0, 2));
        if (CODIGO_REGISTRO_COTACAO_DIARIA.equals(tipReg)) {
            cotacaoHistorica.addCotacaoDiaria(setDadosRegistroCotacaoDiaria(linha));
        } else if (CODIGO_REGISTRO_TRAILER.equals(tipReg)) {
            RegistroTrailer registroTrailer = new RegistroTrailer();
            registroTrailer.setTIPREG(CODIGO_REGISTRO_TRAILER);
            registroTrailer.setNOMARQ(StockExchangeUtils.trataValores(linha.substring(3 - 1, 15)));
            registroTrailer.setCDORIG(StockExchangeUtils.trataValores(linha.substring(16 - 1, 23)));
            registroTrailer.setDATGER(StockExchangeUtils.trataValores(linha.substring(24 - 1, 31)));
            registroTrailer.setTOTREG(StockExchangeUtils.trataValores(linha.substring(32 - 1, 42)));
            cotacaoHistorica.setRegistroTrailer(registroTrailer);
        }
    }
	
	
	 private static RegistroCotacaoDiaria setDadosRegistroCotacaoDiaria(String s) {
	        return new RegistroCotacaoDiariaBuilder()
	                //TIPREG - TIPO DE REGISTRO
	                .withTIPREG(CODIGO_REGISTRO_COTACAO_DIARIA)
	                //DATA DO PREGÃO
	                .withDATAPG(StockExchangeUtils.trataValores(s.substring(3 - 1, 10)))
	                //CODBDI - CÓDIGO BDI
	                .withCODBDI(StockExchangeUtils.trataValores(s.substring(11 - 1, 12)))
	                //CODNEG - CÓDIGO DE NEGOCIAÇÃO DO PAPEL
	                .withCODNEG(StockExchangeUtils.trataValores(s.substring(13 - 1, 24)))
	                //TPMERC - TIPO DE MERCADO
	                .withTPMERC(StockExchangeUtils.trataValores(s.substring(25 - 1, 27)))
	                //NOMRES - NOME RESUMIDO DA EMPRESA EMISSORA DO PAPEL
	                .withNOMRES(StockExchangeUtils.trataValores(s.substring(28 - 1, 39)))
	                //ESPECI - ESPECIFICAÇÃO DO PAPEL
	                .withESPECI(StockExchangeUtils.trataValores(s.substring(40 - 1, 49)))
	                //PRAZOT - PRAZO EM DIAS DO MERCADO A TERMO
	                .withPRAZOT(StockExchangeUtils.trataValores(s.substring(50 - 1, 52)))
	                //MODREF - MOEDA DE REFERÊNCIA
	                .withMODREF(StockExchangeUtils.trataValores(s.substring(53 - 1, 56)))
	                //PREABE - PREÇO DE ABERTURA DO PAPEL-MERCADO NO PREGÃO
	                .withPREABE(StockExchangeUtils.trataValores(s.substring(57 - 1, 69), true))
	                //PREMAX - PREÇO MÁXIMO DO PAPEL-MERCADO NO PREGÃO
	                .withPREMAX(StockExchangeUtils.trataValores(s.substring(70 - 1, 82), true))
	                //PREMIN - PREÇO MÍNIMO DO PAPEL MERCADO NO PREGÃO
	                .withPREMIN(StockExchangeUtils.trataValores(s.substring(83 - 1, 95), true))
	                //PREMED - PREÇO MÉDIO DO PAPEL MERCADO NO PREGÃO
	                .withPREMED(StockExchangeUtils.trataValores(s.substring(96 - 1, 108), true))
	                //PREULT - PREÇO DO ÚLTIMO NEGÓCIO DO PAPEL-MERCADO NO PREGÃO
	                .withPREULT(StockExchangeUtils.trataValores(s.substring(109 - 1, 121), true))
	                //PREOFC - PREÇO DA MELHOR OFERTA DE COMPRA DO PAPEL-MERCADO
	                .withPREOFC(StockExchangeUtils.trataValores(s.substring(122 - 1, 134), true))
	                //PREOFV - PREÇO DA MELHOR OFERTA DE VENDA DO PAPEL-MERCADO
	                .withPREOFV(StockExchangeUtils.trataValores(s.substring(135 - 1, 147), true))
	                //TOTNEG - NEG. - NÚMERO DE NEGÓCIOS EFETUADOS COM O PAPEL MERCADO NO PREGÃO
	                .withTOTNEG(StockExchangeUtils.trataValores(s.substring(148 - 1, 152)))
	                //QUATOT - QUANTIDADE TOTAL DE TÍTULOS NEGOCIADOS NESTE PAPEL MERCADO
	                .withQUATOT(StockExchangeUtils.trataValores(s.substring(153 - 1, 170)))
	                //VOLTOT - VOLUME TOTAL DE TÍTULOS NEGOCIADOS NESTE PAPEL MERCADO
	                .withVOLTOT(StockExchangeUtils.trataValores(s.substring(171 - 1, 188), true))
	                //PREEXE - PREÇO DE EXERCÍCIO PARA O MERCADO DE OPÇÕES OU VALOR DO CONTRATO PARA O MERCADO DE TERMO SECUNDÁRIO
	                .withPREEXE(StockExchangeUtils.trataValores(s.substring(189 - 1, 201)))
	                //INDOPC - INDICADOR DE CORREÇÃO DE PREÇOS DE EXERCÍCIOS OU VALORES DE CONTRATO PARA OS MERCADOS DE OPÇÕES OU TERMO SECUNDÁRIO
	                .withINDOPC(StockExchangeUtils.trataValores(s.substring(202 - 1, 202)))
	                //DATVEN - DATA DO VENCIMENTO PARA OS MERCADOS DE OPÇÕES OU TERMO SECUNDÁRIO
	                .withDATVEN(StockExchangeUtils.trataValores(s.substring(203 - 1, 210)))
	                //FATCOT - FATOR DE COTAÇÃO DO PAPEL
	                .withFATCOT(StockExchangeUtils.trataValores(s.substring(211 - 1, 217)))
	                //PTOEXE - PREÇO DE EXERCÍCIO EM PONTOS PARA OPÇÕES REFERENCIADAS EM DÓLAR OU VALOR DE CONTRATO EM PONTOS PARA TERMO SECUNDÁRIO
	                /*.withPTOEXE(trataValores(s.substring(218 - 1, 230), true))*/
	                //CODISI - CÓDIGO DO PAPEL NO SISTEMA ISIN OU CÓDIGO INTERNO DO PAPEL
	                .withCODISI(StockExchangeUtils.trataValores(s.substring(231 - 1, 242)))
	                //DISMES - NÚMERO DE DISTRIBUIÇÃO DO PAPEL
	                .withDISMES(StockExchangeUtils.trataValores(s.substring(243 - 1, 245)))
	                .build();
	    }

	/** Ler linhas de cotação diaria */
	public static CotacaoHistorica lerLinhasCotacaoDiaria(List<String> lines) {
		CotacaoHistorica cotacaoHistorica = new CotacaoHistorica();
		for (String linha : lines) {
			lerRegistrosArquivoDiario(linha, cotacaoHistorica);
		}
		return cotacaoHistorica;
	}

}
