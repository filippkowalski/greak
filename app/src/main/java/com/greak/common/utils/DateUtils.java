package com.greak.common.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtils {

	private static final String DEFAULT_APP_DATE_FORMAT = "HH:mm, dd.MM";
	private static final String DEFAULT_SERVER_DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
	private static final String GMT = "GMT";

	public static String convertDateStringToSimpleFormat(String dateString) {
		try {
			long timeInMillis = getTimeInMillis(dateString);
			return getSimpleDateFormat(timeInMillis);
		} catch (ParseException e) {
			return dateString;
		}
	}

	private static String getSimpleDateFormat(long timestamp) {
		Calendar calendar = Calendar.getInstance();
		TimeZone tz = TimeZone.getDefault();
		calendar.setTimeInMillis(timestamp);
		calendar.add(Calendar.MILLISECOND, tz.getOffset(calendar.getTimeInMillis()));
		SimpleDateFormat sdf = new SimpleDateFormat(DEFAULT_APP_DATE_FORMAT, Locale.getDefault());
		sdf.setTimeZone(TimeZone.getTimeZone(GMT));
		return sdf.format(calendar.getTime());
	}

	public static long getTimeInMillis(String dateString) throws ParseException {
		DateFormat dateFormat = new SimpleDateFormat(DEFAULT_SERVER_DATE_FORMAT, Locale.getDefault());
		dateFormat.setTimeZone(TimeZone.getTimeZone(GMT));
		Date parse = dateFormat.parse(dateString);
		return parse.getTime();
	}
}
