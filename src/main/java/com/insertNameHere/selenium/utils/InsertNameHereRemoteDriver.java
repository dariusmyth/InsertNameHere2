package com.insertNameHere.selenium.utils;

import java.io.File;
import java.net.URL;

import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.NoSuchWindowException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;


public class InsertNameHereRemoteDriver extends RemoteWebDriver{
	public static final long DEFAULT_TIMEOUT = 40000;
	private static final long DEFAULT_SLEEP = 80;
	private static int MAX_RECURSION_DEPTH = 20;
	private static DesiredCapabilities capabilities;
	
	public InsertNameHereRemoteDriver(URL remoteAddress, Capabilities desiredCapabilities) {
		super(remoteAddress, desiredCapabilities);
	}


	@Override
	public WebElement findElement(By by) {

		WebElement result = null;
		long start = System.currentTimeMillis();
		// while not exceeding the default timeout
		while (start + DEFAULT_TIMEOUT > System.currentTimeMillis()) {
			try {
				// find the element
				result = super.findElement(by);
			} catch (NoSuchElementException e) {
				// sleep if not found
				sleep();
			}
			// return the element only if the element is usable (rendered)
			if (isElementUsable(result))
				return result;
			else {
				// wait for element to be visible.
				sleep();
			}
		}
		if (result == null) {
			System.err.println("Element not found " + by);
		}
		return result;
	}

	/**
	 * Wait until the window is loaded.
	 *
	 * @param windowName
	 *            The window name
	 */
	public void switchToWindow(String windowName) {
		long start = System.currentTimeMillis();
		while (start + DEFAULT_TIMEOUT > System.currentTimeMillis()) {
			try {
				this.switchTo().window(windowName);
				break;
			} catch (NoSuchWindowException e) {
				sleep();
			}
		}
	}

	/**
	 * Checks if a element is usable
	 *
	 * @param element
	 *            The element that is checked
	 * @return boolean value.
	 */
	protected boolean isElementUsable(WebElement element) {
		try {
			return element.isDisplayed()&& element.isEnabled();
		} catch (Exception E) {
			return false;
		}
	}

	/**
	 * Waits for 1000 milliseconds
	 */
	public static void sleep() {
		try {
			Thread.sleep(DEFAULT_SLEEP);
		} catch (InterruptedException e1) {
			e1.printStackTrace();
		}
	}


	/**
	 * This method is used to generate a random number between 0- > 99 using math.random
	 * 
	 * @return The random generated number parsed as String
	 */
	public static String generateRandomIntNumber() {
		return Integer.toString((int) (Math.random() * 9 + 1));

	}

	public static String getFilePathFromResourcesDir(String file) {
		File fileToGet = new File(String.format("./src/main/resources/%s", file));

		// File file = new File("./target/test.txt");
		// String dirPath =
		// file.getAbsoluteFile().getParentFile().getAbsolutePath()

		String absolutePath = fileToGet.getAbsolutePath();
		System.out.println(String.format("DEBUG: The absolute path for the file name %s is: %s", file, absolutePath));
		return absolutePath;
	}

}
