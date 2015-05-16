package com.insertNameHere.robotFrameworkUtils.listeners;

import java.util.Map;

import com.insertNameHere.rallyUtils.RallyUtil;
import com.insertNameHere.robotFrameworkUtils.mainInterfaces.RobotTestListenerInterface;

public class RobotCustomTestListener implements RobotTestListenerInterface{

	private String testName;
	
	@Override
	public void startTest(String name, Map attributes) {
		testName=name;
	}

	@Override
	public void endTest(String name, Map attributes) {
		String status = attributes.get("status").toString();
		RallyUtil.rallyUpdate(testName, status);
	}

}
