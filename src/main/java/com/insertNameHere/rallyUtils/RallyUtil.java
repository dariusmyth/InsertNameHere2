package com.insertNameHere.rallyUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.testng.Assert;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.insertNameHere.rallyUtils.objects.RallyDefectObject;
import com.insertNameHere.rallyUtils.objects.RallyTestResult;
import com.insertNameHere.utils.ApplicationLogger;
import com.insertNameHere.utils.CommonFileUtils;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.response.CreateResponse;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.QueryFilter;

public class RallyUtil {

	private static RallyRestApi restApi;
	protected static SimpleDateFormat dfRally = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
	private static final String userRef = "";
	private static final String testBuild = CommonFileUtils.getValueFromConfigFile("versionString");
	private static final String defaultComment = "Test case passed and rally result updated by client automation framework.";
	private static final String testSetID = CommonFileUtils.getValueFromConfigFile("testSetID");
	private static final String rallyProjectName = CommonFileUtils.getValueFromConfigFile("RallyProject");
	private static final String rallyUserName = CommonFileUtils.getValueFromConfigFile("RallyUsername");
	private static final String rallyUserPassword = CommonFileUtils.getValueFromConfigFile("RallyPassword");
	private static ApplicationLogger appLogger = new ApplicationLogger(RallyUtil.class);

	/**
	 * rally connection setup this is where the database connection, usernmane
	 * and password are set should externalize
	 */
	@SuppressWarnings("deprecation")
	public static void setupRallyConnection() {

		// the following code will not report connection failed even if the
		// credentials are not correct
		try {
			appLogger.logInfo("Opening connection to Rally...");
			restApi = new RallyRestApi(new URI("https://rally1.rallydev.com"), rallyUserName, rallyUserPassword);
			restApi.setApplicationName(rallyProjectName);

			appLogger.logInfo("restApi se application name");
		} catch (URISyntaxException e) {
			Assert.assertTrue(e != null, "Connection to Rally failed");
			appLogger.logError("connection entered into Catch exception");
		}
	}

	/**
	 * rally connection close
	 */
	public static void closeRallyConnection() {
		try {
			appLogger.logInfo("Closing connection to Rally...");
			restApi.close();
			appLogger.logInfo("Connection to Rally Closed");
		} catch (IOException e) {
			Assert.assertTrue(e != null, "Unable to close connection to Rally");
			appLogger.logError("Connection close entered into Catch exception");
		}
	}

	public synchronized static void createDefectInRally(String testID, String defectName, String defectDescription) {
		setupRallyConnection();
		try {
			
			String state = "";
			String scheduleState = "";
			// Assert.assertTrue(userRef.length() > 0,
			// "Could not obtain a reference from Rally for user " + user);
			// testSetRef - this is obtained at the start of the test runs in
			// the @BeforeClass
			RallyDefectObject rallyDefectObj = new RallyDefectObject(defectName, defectDescription, state, scheduleState);

			CreateRequest req = new CreateRequest("defect", (JsonObject) new Gson().toJsonTree(rallyDefectObj));
			// System.out.println(req.getBody());

			if (!defectName.isEmpty() && defectDescription != null) // include
																	// here the
																	// condition
																	// for rally
																	// update or
																	// not
			{
				CreateResponse res = restApi.create(req);

				Assert.assertTrue(res.wasSuccessful());
				appLogger.logInfo("Rally Update possibly successfull.");

			} else {
				appLogger.logError("Rally was not updated");
			}

		} catch (IOException e) {
			appLogger.logError("Cannot create defect for test " + testID);
			appLogger.logError("Encountered error: " + e.getMessage());
		} finally {
			closeRallyConnection();
		}
	}

	/**
	 * Updates rally with test care result rallyConnection needs to be setup
	 * separately
	 * 
	 */

	public synchronized static void createTestResult(String testSetID, String testID, String verdict, String notes) {
		try {
			String testCaseRef = getTestCaseReferenceNumber(testID);
			String testSetRef = getTestSetReferenceNumber(testSetID);

			// Assert.assertTrue(userRef.length() > 0,
			// "Could not obtain a reference from Rally for user " + user);
			// testSetRef - this is obtained at the start of the test runs in
			// the @BeforeClass
			RallyTestResult rtr = new RallyTestResult(testSetRef, testCaseRef, dfRally.format(new Date()), verdict, userRef, testBuild, notes);

			CreateRequest req = new CreateRequest("testcaseresult", (JsonObject) new Gson().toJsonTree(rtr));
			// System.out.println(req.getBody());

			if (!testCaseRef.isEmpty() && testCaseRef != null) // include here
																// the condition
																// for rally
																// update or not
			{
				CreateResponse res = restApi.create(req);

				Assert.assertTrue(res.wasSuccessful());
				appLogger.logInfo("Rally Update possibly successfull.");

			} else {
				appLogger.logError("Rally was not updated");
			}

		} catch (IOException e) {
			appLogger.logError("Cannot update test results for test " + testID);
			appLogger.logError("Encountered error: " + e.getMessage());
		}
	}

	public synchronized static void createTestResult(String testID, String verdict, String notes) {
		// default testSetID defined in Settings.java
		createTestResult(testSetID, testID, verdict, notes);
	}

	public synchronized static void createTestResult(String testID, String verdict) {
		// default testSetID defined in Settings.java
		createTestResult(testSetID, testID, verdict, defaultComment);
	}

	/**
	 * method that interrogates Rally and finds the
	 * 
	 * @param testSetID
	 * @return
	 */
	public static String getTestSetReferenceNumber(String testSetID) {

		String testSetRef;
		try {
			// bellow code gets the test set reference number
			QueryRequest testSetRequest = new QueryRequest("TestSet");
			testSetRequest.setFetch(new Fetch("FormattedID", "Name"));
			testSetRequest.setQueryFilter(new QueryFilter("FormattedID", "=", testSetID));
			QueryResponse testSetQueryResponse;
			testSetQueryResponse = restApi.query(testSetRequest);
			JsonArray resultArray = testSetQueryResponse.getResults();
			if (resultArray.size() == 0) {
				appLogger.logInfo("Test Set " + testSetID + "does not exist.");
				return "";
			}
			testSetRef = resultArray.get(0).getAsJsonObject().get("_ref").toString();
			return testSetRef;
		} catch (IOException e) {
			appLogger.logError(e.getMessage());
		}
		return "";
	}

	public static String getTestCaseReferenceNumber(String testCaseID) {
		try {
			// below code determines the test case reference number
			QueryRequest testCaseRequest = new QueryRequest("TestCase");
			testCaseRequest.setFetch(new Fetch("FormattedID", "Name"));
			testCaseRequest.setQueryFilter(new QueryFilter("FormattedID", "=", testCaseID));
			QueryResponse testCaseQueryResponse;
			testCaseQueryResponse = restApi.query(testCaseRequest);
			JsonArray resultArray = testCaseQueryResponse.getResults();
			if (resultArray.size() == 0) {
				appLogger.logInfo("Test " + testCaseID + "does not exist anymore");
				return "";
			}
			return resultArray.get(0).getAsJsonObject().get("_ref").toString();
		} catch (IOException e) {
			appLogger.logError(e.getMessage());
		}
		return "";
	}
}
