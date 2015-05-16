package com.insertNameHere.keyWords;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;

import com.insertNameHere.rallyUtils.RallyUtil;
import com.insertNameHere.robotFrameworkUtils.listeners.RobotCustomJavaListener;
import com.insertNameHere.selenium.pageInterface.HomePageInterface;
import com.insertNameHere.selenium.pageObjects.EmagHomePage;
import com.insertNameHere.selenium.utils.InsertNameHereRemoteDriver;
import com.insertNameHere.selenium.utils.InsertNameHereSeleniumDriver;
import com.insertNameHere.utils.ApplicationLogger;

public class CustomKey {

	public static final String ROBOT_LIBRARY_SCOPE = "GLOBAL";
	public static final String DEFAULT_FILENAME = "listen_java.txt";
	public static final RobotCustomJavaListener ROBOT_LIBRARY_LISTENER = new RobotCustomJavaListener();

	private String browser;
	private String URL;
	private InsertNameHereSeleniumDriver remoteDriver;
	private InsertNameHereRemoteDriver driver;
	protected HomePageInterface homePage;
	private ApplicationLogger LOG = new ApplicationLogger(CustomKey.class);

	public CustomKey(String browser, String URL) {
		LOG.logInfo("Setting variables Browser: " + browser + " and URL: " + URL);
		this.browser = browser;
		this.URL = URL;
	}

	public void initializeDriver() {
		remoteDriver = new InsertNameHereSeleniumDriver(browser, URL);
		driver = remoteDriver.getDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);

	}

	public void closeDriver() {
		driver.close();
		LOG.logInfo("Close Browser");
	}

	public void theUserNavigatesToThePage(String shop) throws IllegalAccessException {
		String shopURL=String.format("http://www.%s.ro", shop);
		LOG.logInfo("Accessing the URL: "+shopURL);
		driver.get(shopURL);
		homePage = new EmagHomePage(driver);

	}

	public void thePageIsLoaded(String title) {
		homePage.isPageTitle(title);

	}

	public void theMenuBarIsDisplayed() {
		homePage.isItemsMenuDisplayed();
	}

	public void itContainsAllTheCorrectLinks() {
		Assert.assertTrue(homePage.areAllTheItemsDisplayedInTheItemsMenu());

	}

	public void testStatus(String status, String testName) {
		LOG.logError("The status of the test is: " + status);
		LOG.logError("The test name is: " + testName);
//		RallyUtil.rallyUpdate(testName, status);

	}

	public void assertEqual(String one, String two) {
		Assert.assertEquals(one, two);
	}

}
