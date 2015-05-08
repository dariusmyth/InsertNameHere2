package com.insertNameHere.selenium.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.safari.SafariDriver;

public class InitializeWebdriver {

	private WebDriver driver;

	public WebDriver getDriver() {
		return null;

	}

	private WebDriver initLocalDriver(String browser) {
		switch (browser.toLowerCase()) {
		case "firefox":
			return new FirefoxDriver();
		case "chrome":
			return new ChromeDriver();
		case "safari":
			return new SafariDriver();
		case "ie":
			return new InternetExplorerDriver();
		default:
			return null;
		}
	}

}
