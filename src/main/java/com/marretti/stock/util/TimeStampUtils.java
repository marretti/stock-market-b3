package com.marretti.stock.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;
import java.util.concurrent.TimeUnit;

public class TimeStampUtils {
	
	/** Construtor privado de classe utilitária */
	private TimeStampUtils() { 
		throw new IllegalStateException("Utility class");
	}

	public static Calendar getToday() {
		Calendar today = Calendar.getInstance(TimeZone.getDefault());
		today.set(Calendar.HOUR_OF_DAY, 0);
		today.set(Calendar.MINUTE, 0);
		today.set(Calendar.SECOND, 0);
		today.set(Calendar.MILLISECOND, 0);
		return today;
	}

	public static boolean isThisYear(Calendar calendar) {
		Calendar today = TimeStampUtils.getToday();
		return today.get(Calendar.YEAR) == calendar.get(Calendar.YEAR);
	}

	public static boolean isMoreThanHourFromNow(long initialTime) {
		Calendar startDate = Calendar.getInstance();
		startDate.setTimeInMillis(initialTime);
		Calendar endDate = Calendar.getInstance();
		long difference = endDate.getTimeInMillis() - startDate.getTimeInMillis();
		return TimeUnit.MILLISECONDS.toHours(difference) >= 1;
	}
	
	public static boolean dateIsOnInterval(Calendar startDate, Calendar endDate, Date data) {
		boolean isBefore = data.before(startDate.getTime());
		boolean isAfter = data.after(endDate.getTime());
		return !isBefore && !isAfter;
	}
	
	public static Date formateDate(String pDate, String pattern, boolean endDay) throws ParseException {
		Date retorno = null;
		DateFormat formatDate = new SimpleDateFormat(pattern);
		retorno = formatDate.parse(pDate);
		if (endDay) {
			Calendar cal = TimeStampUtils.getToday();
			cal.setTime(retorno);
			cal.set(Calendar.HOUR_OF_DAY, 23);
			cal.set(Calendar.MINUTE, 59);
			cal.set(Calendar.SECOND, 59);
			cal.set(Calendar.MILLISECOND, 999);
			retorno = cal.getTime();
		}
		return retorno;
	}
	
	public static long minutesFrom(Date startDate, Date endDate) {
		long difference = startDate.getTime() - endDate.getTime();
		return TimeUnit.MILLISECONDS.toMinutes(difference);
	}
	
	public static long secondsFrom(Date startDate, Date endDate) {
		long difference = startDate.getTime() - endDate.getTime();
		return TimeUnit.MILLISECONDS.toSeconds(difference);
	}
	
	public static long minutesFrom(LocalDateTime startDate, Date endDate) {
		long difference = Date.from(startDate.atZone(ZoneId.systemDefault()).toInstant()).getTime() - endDate.getTime();
		return TimeUnit.MILLISECONDS.toMinutes(difference);
	}
	
	public static Date convertLocalDateAndTime(LocalDate date, LocalTime time) {
		Instant instant = date.atTime(time).atZone(ZoneId.systemDefault()).toInstant();
		return Date.from(instant);
	}

}
