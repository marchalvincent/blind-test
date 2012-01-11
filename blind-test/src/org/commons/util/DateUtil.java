package org.commons.util;

import org.joda.time.DateTime;
import org.joda.time.LocalDate;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;

/**
 * Une classe utilitaire pour les dates.
 * @author pitton
 *
 */
public final class DateUtil {

	/**
	 * Retourne la date courante.
	 * @return {@link DateTime} la date courante.
	 */
	final static public DateTime getCurrentDateTime() {
		return new DateTime(System.currentTimeMillis());
	}
	
	/**
	 * Retourne la date courante.
	 * @return {@link LocalDate} la date courante.
	 */
	final static public LocalDate getCurrentDate() {
		return new LocalDate(System.currentTimeMillis());
	}
	
	final static public String format(final DateTime parDateTime) {
		if(parDateTime == null) throw new NullPointerException();
		
		final DateTimeFormatter locFormatter = DateTimeFormat.mediumDateTime();
		return parDateTime.toString(locFormatter);
	}
	
	private DateUtil() {}
}
