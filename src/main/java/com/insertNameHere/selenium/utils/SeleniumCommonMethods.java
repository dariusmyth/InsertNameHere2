package com.insertNameHere.selenium.utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

public class SeleniumCommonMethods {

	private InsertNameHereRemoteDriver driver;
	public SeleniumCommonMethods (InsertNameHereRemoteDriver driver){
		this.driver=driver;
	}
	
	public void waitForElemnetToBeDisplayed(By by) {

	}
	
	public WebElement identifyElement(By by){
		return driver.findElement(by);
	}
}
