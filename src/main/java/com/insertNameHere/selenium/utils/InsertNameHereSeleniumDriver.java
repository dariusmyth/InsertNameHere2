package com.insertNameHere.selenium.utils;

import java.net.MalformedURLException;
import java.net.URL;

import org.openqa.selenium.Platform;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import com.insertNameHere.utils.ApplicationLogger;

public class InsertNameHereSeleniumDriver {

	private ApplicationLogger appLogger = new ApplicationLogger(InsertNameHereSeleniumDriver.class);
	private InsertNameHereRemoteDriver driver;
	protected ThreadLocal<InsertNameHereRemoteDriver> threadDriver = null;
	private String browser;
	private String URL;

	public InsertNameHereSeleniumDriver(String browser, String URL) {
		this.browser = browser;
		this.URL = URL;
	}

	/**
	 * This method handles the Before configurations for each test class which
	 * extends this one
	 * 
	 * @param browser
	 *            - taken from testNG.xml file when running the whole file tests
	 *            suite. Otherwise, on individual runs, it will take the @Optional
	 *            value
	 * @param url
	 *            - taken from testNG.xml file when running the whole file tests
	 *            suite. Otherwise, on individual runs, it will take the @Optional
	 *            value
	 * @throws MalformedURLException
	 */

	private void setupDriver(String browser, String url) {

		appLogger.logInfo("Webdriver setup initiated...");
		threadDriver = new ThreadLocal<InsertNameHereRemoteDriver>();
		DesiredCapabilities desiredCapabilitiesc = new DesiredCapabilities();
		switch (browser.toUpperCase()) {
		case "FIREFOX":{
			FirefoxProfile firefoxProfile = new FirefoxProfile();
			desiredCapabilitiesc.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
			desiredCapabilitiesc.setPlatform(Platform.WINDOWS);
			desiredCapabilitiesc.setBrowserName(DesiredCapabilities.firefox().getBrowserName());
			desiredCapabilitiesc.setCapability(CapabilityType.TAKES_SCREENSHOT, true);
			
			break;
		}
		case "":{
			//TODO IMplement chrome driver
		}
		}
		try {
			threadDriver.set(new InsertNameHereRemoteDriver(new URL(url), desiredCapabilitiesc));
			appLogger.logInfo("Webdriver initiated successfully....");
		} catch (MalformedURLException e) {
			appLogger.logError("Encountered an error when setting the driver to the thread local: " + e.getMessage());
		}

	}

	public InsertNameHereRemoteDriver getDriver() {
		setupDriver(browser, URL);
		return threadDriver.get();
	}

	/**
	 * Handling the closing of the driver at the end of each test method
	 */

	public void closeDriver() {

		appLogger.logInfo("Webdriver is closing ...");
		driver.quit();
		appLogger.logInfo("Webdriver is closed with success...");
	}
}
