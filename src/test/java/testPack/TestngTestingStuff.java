package testPack;

import com.insertNameHere.rallyUtils.RallyUtil;
import com.insertNameHere.rallyUtils.TestResultVerdict;


public class TestngTestingStuff {
	
	public static void main(String[] args) {
		String testName="Scenario: As an editor I can add new translation";
		String defectDescription =RallyUtil.getTestCaseDescription(testName);
		RallyUtil.createTestResult(RallyUtil.getTestCaseIDForTestCaseWithName(testName), TestResultVerdict.FAIL.getValue(), "Auto test");
		System.err.println("TEst case description: "+defectDescription);
		System.err.println("New Defect from Rally: "+RallyUtil.createOrOpenDefectInRally(testName, defectDescription));
		
	}
	
	
	
}
