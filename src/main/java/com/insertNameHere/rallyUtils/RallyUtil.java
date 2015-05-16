package com.insertNameHere.rallyUtils;

import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.python.modules.synchronize;
import org.testng.Assert;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.insertNameHere.rallyUtils.objects.RallyTestResult;
import com.insertNameHere.utils.ApplicationLogger;
import com.insertNameHere.utils.CommonFileUtils;
import com.rallydev.rest.RallyRestApi;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.request.GetRequest;
import com.rallydev.rest.request.QueryRequest;
import com.rallydev.rest.request.UpdateRequest;
import com.rallydev.rest.response.CreateResponse;
import com.rallydev.rest.response.GetResponse;
import com.rallydev.rest.response.QueryResponse;
import com.rallydev.rest.response.UpdateResponse;
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
	private static ApplicationLogger appLogger = new ApplicationLogger(RallyUtil.class);

	/**
	 * rally connection setup this is where the database connection, usernmane
	 * and password are set should externalize
	 */
	private static void setupRallyConnection() {

		// the following code will not report connection failed even if the
		// credentials are not correct
		try {
			appLogger.logInfo("Opening connection to Rally...");
			restApi = new RallyRestApi(new URI("https://rally1.rallydev.com"), "_teNVYyPxRB6BteXgZHTiLuU2I3ubfU7CpphnweRfblQ");
			restApi.setApplicationName(rallyProjectName);
			restApi.setApplicationVersion("1.2");
			appLogger.logInfo("restApi se application name");
		} catch (URISyntaxException e) {
			Assert.assertTrue(e != null, "Connection to Rally failed");
			appLogger.logError("connection entered into Catch exception");
		}
	}

	/**
	 * rally connection close
	 */
	private static void closeRallyConnection() {
		try {
			appLogger.logInfo("Closing connection to Rally...");
			restApi.close();
			appLogger.logInfo("Connection to Rally Closed");
		} catch (IOException e) {
			Assert.assertTrue(e != null, "Unable to close connection to Rally");
			appLogger.logError("Connection close entered into Catch exception");
		}
	}

	public static void rallyUpdate(String testName, String status) {
		switch (status) {
		case "PASS": {
			RallyUtil.createTestResult(RallyUtil.getTestCaseIDForTestCaseWithName(testName), TestResultVerdict.PASS.getValue(), "Auto test");
			appLogger.logInfo(String.format("The test with name %s has passed and Rally was updated according",testName));
			break;
		}
		case "FAIL": {
			String defectDescription = RallyUtil.getTestCaseDescription(testName);
			RallyUtil.createTestResult(RallyUtil.getTestCaseIDForTestCaseWithName(testName), TestResultVerdict.FAIL.getValue(), "Auto test");
			appLogger.logError(String.format("The test with name %s has FAILED and Rally was updated according",testName));
			appLogger.logError("New Defect added to Rally : " + RallyUtil.createOrOpenDefectInRally(testName, defectDescription));
			break;
		}
		default:
			break;
		}
	}

	private synchronized static GetResponse getResponseForObject(String objectType, String id) {
		setupRallyConnection();
		GetResponse getResponse = null;
		try {
			String objectRefID = String.format("/%s/%s", objectType, parseRefNumber(getDefectReferenceNumber(id), objectType));
			GetRequest getRequest = new GetRequest(objectRefID);
			getResponse = restApi.get(getRequest);
			Assert.assertTrue(getResponse.wasSuccessful());
			appLogger.logInfo("Rally Update possibly successfull.");
		} catch (IOException e) {
			appLogger.logError("Cannot create defect for test " + id);
			appLogger.logError("Encountered error: " + e.getMessage());
		} finally {
			closeRallyConnection();
		}
		return getResponse;

	}

	private synchronized static UpdateResponse updateObjectWithDataInRally(String objectType, String id, JsonObject updateObject) {
		UpdateResponse updateResponse = null;
		setupRallyConnection();
		try {
			String objectRefID = String.format("/%s/%s", objectType, parseRefNumber(getDefectReferenceNumber(id), objectType));
			UpdateRequest updateRequestx = new UpdateRequest(objectRefID, updateObject);
			updateResponse = restApi.update(updateRequestx);

			Assert.assertTrue(updateResponse.wasSuccessful());
			appLogger.logInfo("Rally Update possibly successfull: " + id);

		} catch (IOException e) {
			appLogger.logError("Cannot create defect for test " + id);
			appLogger.logError("Encountered error: " + e.getMessage());
		} finally {
			closeRallyConnection();
		}
		return updateResponse;

	}

	private synchronized static QueryResponse queryRallyForDefectInformation(String defectTitle) {
		QueryResponse queryResponse = null;
		setupRallyConnection();

		try {
			QueryRequest defectRequest = new QueryRequest("defect");

			defectRequest.setFetch(new Fetch("FormattedID", "Name", "State", "Description"));
			defectRequest.setQueryFilter(new QueryFilter("Name", "=", defectTitle));
			defectRequest.setOrder("Name ASC");

			defectRequest.setPageSize(25);
			defectRequest.setLimit(100);

			queryResponse = restApi.query(defectRequest);

		} catch (IOException e) {
			appLogger.logError("Encountered error: " + e.getMessage());
		} finally {
			closeRallyConnection();
		}
		return queryResponse;
	}

	private synchronized static CreateResponse createObjectInRally(String objectType, JsonObject newObject) {
		CreateResponse createResponse = null;
		setupRallyConnection();
		try {
			CreateRequest createRequest = new CreateRequest(objectType, newObject);
			createResponse = restApi.create(createRequest);
			Assert.assertTrue(createResponse.wasSuccessful());
			appLogger.logInfo("Rally Update possibly successfull.");

		} catch (IOException e) {
			appLogger.logError("Encountered error: " + e.getMessage());
		} finally {
			closeRallyConnection();
		}

		return createResponse;
	}

	private synchronized static QueryResponse queryRallyForTestCaseInformation(String testCaseTitle) {

		QueryResponse queryResponse = null;
		setupRallyConnection();

		try {
			QueryRequest defectRequest = new QueryRequest("testcase");

			defectRequest.setFetch(new Fetch("FormattedID", "Name", "State", "Description"));
			defectRequest.setQueryFilter(new QueryFilter("Name", "=", testCaseTitle));
			defectRequest.setOrder("Name ASC");

			defectRequest.setPageSize(25);
			defectRequest.setLimit(100);

			queryResponse = restApi.query(defectRequest);

		} catch (IOException e) {
			appLogger.logError("Encountered error: " + e.getMessage());
		} finally {
			closeRallyConnection();
		}
		return queryResponse;
	}

	private synchronized static String getTestCaseIDForTestCaseWithName(String title) {
		JsonArray reponseArray = new JsonArray();
		JsonObject responseObject = new JsonObject();
		reponseArray = queryRallyForTestCaseInformation(title).getResults();
		responseObject = reponseArray.get(0).getAsJsonObject();
		return responseObject.get("FormattedID").toString().replace("\"", "").trim();

	}

	private synchronized static String createOrOpenDefectInRally(String title, String description) {

		QueryResponse getInfoForDefect = queryRallyForDefectInformation(title);
		JsonArray jsonArr = new JsonArray();
		jsonArr = getInfoForDefect.getResults().getAsJsonArray();
		int i = jsonArr.size();
		CreateResponse createDefectResponse = null;
		String state = null;
		String id = null;
		if (getInfoForDefect.wasSuccessful() && (i > 0)) {

			JsonObject jsonObj = jsonArr.get(0).getAsJsonObject();

			JsonArray infoForDefectArray = getInfoForDefect.getResults();
			state = infoForDefectArray.get(0).getAsJsonObject().get("State").toString().replace("\"", "").trim();
			if (state.equals("Fixed") || state.equals("Closed")) {

				id = infoForDefectArray.get(0).getAsJsonObject().get("FormattedID").toString().replace("\"", "").toString();
				JsonObject updateObject = new JsonObject();
				updateObject.addProperty("State", "Open");
				updateObject.addProperty("Notes", "Re-opened by automated test");
				updateObjectWithDataInRally("defect", id, updateObject);

			}
		} else {
			JsonObject createDefectObject = new JsonObject();
			createDefectObject.addProperty("Description", description);
			createDefectObject.addProperty("Name", title);
			createDefectObject.addProperty("State", "Submitted");
			createDefectResponse = createObjectInRally("defect", createDefectObject);
			JsonObject createResponseArray = createDefectResponse.getObject();
			id = createResponseArray.get("FormattedID").toString();
		}
		return id;
	}

	private synchronized static String createTestCaseInRally(String title, String description, String preConditions, String postConditions) {

		QueryResponse getInfoForDefect = queryRallyForTestCaseInformation(title);
		JsonObject responseObject = new JsonObject();
		if (!getInfoForDefect.wasSuccessful()) {
			JsonObject createDefectObject = new JsonObject();
			createDefectObject.addProperty("Description", description);
			createDefectObject.addProperty("Name", title);
			createDefectObject.addProperty("Method", "Automated");
			createDefectObject.addProperty("Type", "Acceptance");
			createDefectObject.addProperty("Pre-Conditions", preConditions);
			createDefectObject.addProperty("Post-Conditions", postConditions);
			responseObject = createObjectInRally("defect", createDefectObject).getObject();

		} else {
			responseObject = getInfoForDefect.getResults().getAsJsonArray().get(0).getAsJsonObject();
		}
		return responseObject.get("FormatedID").toString();
	}

	private synchronized static String getTestCaseDescription(String title) {
		JsonArray reponseArray = new JsonArray();
		JsonObject responseObject = new JsonObject();
		reponseArray = queryRallyForTestCaseInformation(title).getResults();
		responseObject = reponseArray.get(0).getAsJsonObject();
		return responseObject.get("Description").toString().replace("\"", "").trim();
	}

	/**
	 * Updates rally with test care result rallyConnection needs to be setup
	 * separately
	 * 
	 */

	private synchronized static void createTestResult(String testSetID, String testID, String verdict, String notes) {
		try {
			setupRallyConnection();

			String testCaseRef = parseRefNumber(getTestCaseReferenceNumber(testID), "testcase");
			String testSetRef = parseRefNumber(getTestSetReferenceNumber(testSetID), "testset");

			// Assert.assertTrue(userRef.length() > 0,
			// "Could not obtain a reference from Rally for user " + user);
			// testSetRef - this is obtained at the start of the test runs in
			// the @BeforeClass

			RallyTestResult rtr = new RallyTestResult(testSetRef, testCaseRef, dfRally.format(new Date()), verdict, userRef, testBuild, notes);

			CreateRequest req = new CreateRequest("testcaseresult", (JsonObject) new Gson().toJsonTree(rtr));

			// System.out.println(req.getBody());

			if (!testCaseRef.isEmpty() && testCaseRef != null && CommonFileUtils.getValueFromConfigFile("updateRally").toLowerCase().equals("true")) {

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

	private synchronized static void createTestResult(String testID, String verdict, String notes) {
		// default testSetID defined in Settings.java
		createTestResult(testSetID, testID, verdict, notes);
	}

	private synchronized static void createTestResult(String testID, String verdict) {
		// default testSetID defined in Settings.java
		createTestResult(testSetID, testID, verdict, defaultComment);
	}

	/**
	 * method that interrogates Rally and finds the
	 * 
	 * @param testSetID
	 * @return
	 */
	private static String getTestSetReferenceNumber(String testSetID) {

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

	private static String parseRefNumber(String _ref, String section) {
		String refNum = _ref.split(String.format("%s/", section))[1].split("\"")[0];
		return refNum;
	}

	private static String getTestCaseReferenceNumber(String testCaseID) {
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
			System.err.println("TEST REF: " + resultArray.get(0).getAsJsonObject().get("_ref").toString());
			return resultArray.get(0).getAsJsonObject().get("_ref").toString();
		} catch (IOException e) {
			appLogger.logError(e.getMessage());
		}
		return "";
	}

	private static String getDefectReferenceNumber(String defectID) {
		try {
			// below code determines the test case reference number
			QueryRequest defectRefRequest = new QueryRequest("Defect");
			defectRefRequest.setFetch(new Fetch("FormattedID", "Name"));
			defectRefRequest.setQueryFilter(new QueryFilter("FormattedID", "=", defectID));
			QueryResponse defectRefResponse;
			defectRefResponse = restApi.query(defectRefRequest);
			JsonArray resultArray = defectRefResponse.getResults();
			if (resultArray.size() == 0) {
				appLogger.logInfo("Defect " + defectID + "does not exist anymore");
				return "";
			}
			System.err.println("DEFECT REF: " + resultArray.get(0).getAsJsonObject().get("_ref").toString());
			return resultArray.get(0).getAsJsonObject().get("_ref").toString();
		} catch (IOException e) {
			appLogger.logError(e.getMessage());
		}
		return "";
	}
}
