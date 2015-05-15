package com.insertNameHere.selenium.pageInterface;

import com.insertNameHere.selenium.utils.InsertNameHereRemoteDriver;
import com.insertNameHere.selenium.utils.SeleniumCommonMethods;

public abstract class HomePageAbstract extends SeleniumCommonMethods implements HomePageInterface {

	private InsertNameHereRemoteDriver driver;
	
	public HomePageAbstract(InsertNameHereRemoteDriver driver) {
		super(driver);
		this.driver=driver;
	}

	
	
}
