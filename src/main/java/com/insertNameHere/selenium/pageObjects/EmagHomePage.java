package com.insertNameHere.selenium.pageObjects;

import org.openqa.selenium.WebDriver;

import com.insertNameHere.selenium.pageInterface.HomePageAbstract;
import com.insertNameHere.utils.CommonFileUtils;

public class EmagHomePage extends HomePageAbstract {

	private WebDriver driver;
	private String pageURL=CommonFileUtils.getValueFromConfigFile("EmagURL");
	public EmagHomePage(WebDriver driver) {
		super(driver);
		this.driver=driver;
	}

	@Override
	public void navigateToMainPage() {
		driver.get("pageURL");
		
	}

	@Override
	public boolean isPageLoaded() {
		// TODO Auto-generated method stub
		return false;
	}

}
