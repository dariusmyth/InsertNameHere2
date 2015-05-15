package testPack;

import java.io.IOException;

import com.insertNameHere.utils.ApplicationLogger;
import com.insertNameHere.utils.GenerateChartFromGoogleAPI;


public class TestngTestingStuff {
	
	private ApplicationLogger appLogger=new ApplicationLogger(TestngTestingStuff.class);
	public static void main(String[] args) throws IOException {
//		String testName="Scenario: As an editor I can add new translation";
//		String defectDescription =RallyUtil.getTestCaseDescription(testName);
//		RallyUtil.createTestResult(RallyUtil.getTestCaseIDForTestCaseWithName(testName), TestResultVerdict.FAIL.getValue(), "Auto test");
//		System.err.println("TEst case description: "+defectDescription);
//		System.err.println("New Defect from Rally: "+RallyUtil.createOrOpenDefectInRally(testName, defectDescription));
		GenerateChartFromGoogleAPI.generateChartFromTemplateFile(25, 70);
	
	}
	
	
}
