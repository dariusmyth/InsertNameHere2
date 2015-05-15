package com.insertNameHere.keyWords;

import java.util.concurrent.TimeUnit;

import org.testng.Assert;

import com.insertNameHere.rallyUtils.RallyUtil;
import com.insertNameHere.rallyUtils.TestResultVerdict;
import com.insertNameHere.robotFrameworkUtils.listeners.RoboJavaListenerImplementation;
import com.insertNameHere.selenium.pageInterface.HomePageInterface;
import com.insertNameHere.selenium.pageObjects.EmagHomePage;
import com.insertNameHere.selenium.utils.InsertNameHereRemoteDriver;
import com.insertNameHere.selenium.utils.InsertNameHereSeleniumDriver;
import com.insertNameHere.utils.ApplicationLogger;

public class CustomKey {

	public static final String ROBOT_LIBRARY_SCOPE = "GLOBAL";
	public static final String DEFAULT_FILENAME = "listen_java.txt";
	public static final RoboJavaListenerImplementation ROBOT_LIBRARY_LISTENER = new RoboJavaListenerImplementation();
	
	private String browser;
	private String URL;
	private  InsertNameHereSeleniumDriver remoteDriver;
	private  InsertNameHereRemoteDriver driver;
	protected HomePageInterface homePage;
	private ApplicationLogger LOG = new ApplicationLogger(CustomKey.class);

	public CustomKey(String browser, String URL) {
		LOG.logInfo("Setting variables Browser: " + browser + " and URL: " + URL);
		this.browser=browser;
		this.URL=URL;
	}

	public void initializeDriver() {
		remoteDriver=new InsertNameHereSeleniumDriver(browser, URL);
		driver=remoteDriver.getDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		
	}

	public void closeDriver() {
		driver.close();
		LOG.logInfo("Close Browser");
	}

	public void theUserNavigatesToThePage(String shop) {
		driver.get(String.format("http://www.%s.ro", shop));
		homePage=new EmagHomePage(driver);

	}

	public void thePageIsLoaded() {
		homePage.isPageLoaded();
		
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
		switch (status) {
		case "PASS":{
			RallyUtil.createTestResult(RallyUtil.getTestCaseIDForTestCaseWithName(testName), TestResultVerdict.PASS.getValue(), "Auto test");
			break;
		}
		case "FAIL":{
			String defectDescription =RallyUtil.getTestCaseDescription(testName);
			RallyUtil.createTestResult(RallyUtil.getTestCaseIDForTestCaseWithName(testName), TestResultVerdict.FAIL.getValue(), "Auto test");
			LOG.logError("New Defect added to Rally for test scenario: "+RallyUtil.createOrOpenDefectInRally(testName, defectDescription));	
			break;
		}
		default:
			break;
		}
			
	}

	
}
