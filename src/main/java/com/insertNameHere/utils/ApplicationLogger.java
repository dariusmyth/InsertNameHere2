package com.insertNameHere.utils;

import org.apache.log4j.Logger;
import org.apache.log4j.xml.DOMConfigurator;

public class ApplicationLogger {

	private Logger logger;

	public <T> ApplicationLogger(Class<T> clazz) {
		DOMConfigurator.configure("log4j.xml");
		logger = Logger.getLogger(clazz);
	}

	public void logInfo(String message) {
		logger.info(message);
	}

	public void logDebug(String message) {
		logger.debug(message);
	}

	public void logError(String message) {
		logger.error(message);
	}

	public void logWarning(String message) {
		logger.warn(message);
	}

}