package testPack;

import org.testng.Assert;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import com.google.gson.JsonObject;
import com.insertNameHere.rallyUtils.RallyID;
import com.insertNameHere.rallyUtils.RallyUtil;
import com.rallydev.rest.request.CreateRequest;
import com.rallydev.rest.response.CreateResponse;


public class TestngTestingStuff {
	
	public void firstTestOfPack(){
		Assert.assertEquals(1, 1);
	}
	

	public void seccondTest(){
		RallyUtil.createTestCaseInRally("DE2", "Defect Name", "Defect Description", "State");
	}
}
