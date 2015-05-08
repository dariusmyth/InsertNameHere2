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
import com.insertNameHere.utils.CommonFileUtils;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.response.CreateResponse;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.util.Fetch;
import com.rallydev.rest.util.QueryFilter;

public class RallyUtil {
	public static RallyRestApi restApi;
	protected static SimpleDateFormat dfRally = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss.SSS'Z'");
	public static String userRef = "";
	public static String testSetRef = "";
	public static String testBuild = CommonFileUtils.getValueFromConfigFile("versionString");
	public static String defaultComment = "Test case passed and rally result updated by client automation framework.";
	private static String testSetID = CommonFileUtils.getValueFromConfigFile("testSetID");

	/**
	 * rally connection setup this is where the database connection, usernmane
	 * and password are set should externalize
	 */
	@SuppressWarnings("deprecation")
	public static void setupRallyConnection() {
		// the following code will not report connection failed even if the
		// credentials are not correct
		try {
			System.out.println("Opening connection to Rally...");
			restApi = new RallyRestApi(new URI("https://rally1.rallydev.com"), "qaautomation@kno.com", "(Test123)");
			restApi.setApplicationName("iPad Kno Textbooks");

			System.out.println("restApi se application name");
		} catch (URISyntaxException e) {
			Assert.assertTrue(e != null, "Connection to Rally failed");
			System.err.println("connection entered into Catch exception");
		}
	}

	/**
	 * rally connection close
	 */
	public static void closeRallyConnection() {
		try {
			System.out.println("Closing connection to Rally...");
			restApi.close();
			System.out.println("Connection to Rally Closed");
		} catch (IOException e) {
			Assert.assertTrue(e != null, "Unable to close connection to Rally");
			System.err.println("Connection close entered into Catch exception");
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
				System.out.println("Rally Update possibly successfull.");

			} else {
				System.err.println("Rally was not updated");
			}

		} catch (IOException e) {
			// Assert.fail("Test ID " + testID + " not found");
			System.err.println("Cannot update test results for test " + testID);
			System.err.println("Encountered error: " + e);
			// log("Cannot update test results for test " + testID);
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
				System.out.println("Test Set " + testSetID + "does not exist.");
				// log("Cannot find: " + testID);
				return "";
			}
			testSetRef = resultArray.get(0).getAsJsonObject().get("_ref").toString();
			return testSetRef;
		} catch (IOException e) {
			e.printStackTrace();
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
				System.out.println("Test " + testCaseID + "does not exist anymore");
				// log("Cannot find: " + testID);
				return "";
			}
			return resultArray.get(0).getAsJsonObject().get("_ref").toString();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
