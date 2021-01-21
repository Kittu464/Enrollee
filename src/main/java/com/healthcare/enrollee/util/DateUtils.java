package com.healthcare.enrollee.util;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.apache.commons.lang3.StringUtils;

import com.healthcare.enrollee.logging.LogManager;
import com.healthcare.enrollee.logging.LoggingLevel;

public class DateUtils {

	public static LocalDate convertStringToDate(String stringDate, String dateFormat) {
		LogManager.writeLog(DateUtils.class,"convertStringToDate() : stringDate : " + stringDate + ", dateFormat : " + dateFormat, LoggingLevel.DEBUG);
		LocalDate date = null;
		DateTimeFormatter formatter = null;
		try {
			if (StringUtils.isNotBlank(stringDate)) {
				formatter = DateTimeFormatter.ofPattern(dateFormat);
				date = LocalDate.parse(stringDate, formatter);
			}
		} catch (Exception e) {
			LogManager.writeLog(DateUtils.class, "Exception occured while parsing date : " + e.getMessage(), LoggingLevel.ERROR);
		}
		return date;
	}

	public static String convertDateToString(LocalDate date, String dateFormat) {
		LogManager.writeLog(DateUtils.class, "convertDateTimeToString() : date : " + date + ", dateFormat : " + dateFormat, LoggingLevel.DEBUG);
		String dateString = null;
		DateTimeFormatter formatter = null;
		try {
			if (date != null) {
				formatter = DateTimeFormatter.ofPattern(dateFormat);
				dateString = date.format(formatter);
			}
		} catch (Exception e) {
			LogManager.writeLog(DateUtils.class, "Exception occured while parsing date : " + e.getMessage(), LoggingLevel.ERROR);
		}
		return dateString;
	}
}
