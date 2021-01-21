package com.healthcare.enrollee.logging;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogManager {

	@SuppressWarnings("rawtypes")
	public final static void writeLog(Class className, String message, String logLevel) {
		Logger logger = LoggerFactory.getLogger(className);
		if (LoggingLevel.DEBUG.equals(logLevel)) {
			logger.debug(message.toString());
		} else if (LoggingLevel.INFO.equals(logLevel)) {
			logger.info(message.toString());
		} else if (LoggingLevel.WARN.equals(logLevel)) {
			logger.warn(message.toString());
		} else if (LoggingLevel.ERROR.equals(logLevel)) {
			logger.error(message);
		} else if (LoggingLevel.TRACE.equals(logLevel)) {
			logger.trace(message);
		}
	}
}
