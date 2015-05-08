package com.insertNameHere.selenium.pageInterface;

import org.openqa.selenium.WebDriver;

import com.insertNameHere.selenium.utils.SeleniumCommonMethods;

public abstract class HomePageAbstract extends SeleniumCommonMethods implements HomePageInterface {

	private WebDriver driver;
	public HomePageAbstract(WebDriver driver) {
		this.driver=driver;
	}

	
	
}
