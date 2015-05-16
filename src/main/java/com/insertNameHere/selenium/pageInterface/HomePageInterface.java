package com.insertNameHere.selenium.pageInterface;


public interface HomePageInterface {

	void navigateToMainPage();
	
	boolean isPageTitle(String title);
	
	boolean isItemsMenuDisplayed();
	
	boolean areAllTheItemsDisplayedInTheItemsMenu();
	
}
