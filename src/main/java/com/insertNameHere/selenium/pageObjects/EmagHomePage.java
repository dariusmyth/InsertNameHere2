package com.insertNameHere.selenium.pageObjects;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

import com.insertNameHere.selenium.pageInterface.HomePageAbstract;
import com.insertNameHere.selenium.utils.InsertNameHereRemoteDriver;
import com.insertNameHere.utils.ApplicationLogger;
import com.insertNameHere.utils.CommonFileUtils;

public class EmagHomePage extends HomePageAbstract {

	private WebDriver driver;
	// TODO remove
	private String pageURL = CommonFileUtils.getValueFromConfigFile("EmagURL");
	private ApplicationLogger appLog = new ApplicationLogger(EmagHomePage.class);

	public EmagHomePage(InsertNameHereRemoteDriver driver) {
		super(driver);
		this.driver = driver;
	}

	private static final By ITEM_MENU = By.id("emg-mega-menu");

	@Override
	public void navigateToMainPage() {
		driver.get(pageURL);
	}

	@Override
	public boolean isPageLoaded() {
		appLog.logInfo("Is page loaded title " + driver.getTitle());

		return driver.getTitle().equals("eMAG.ro - cea mai variata gama de produse");
	}

	@Override
	public boolean isItemsMenuDisplayed() {
		return identifyElement(ITEM_MENU).isDisplayed();
	}

	@Override
	public boolean areAllTheItemsDisplayedInTheItemsMenu() {
		// TODO Auto-generated method stub
		return false;
	}

}
