package com.insertNameHere.robotFrameworkUtils.mainInterfaces;

import java.util.Map;

public interface RobotTestListenerInterface {

	public static final int ROBOT_LISTENER_API_VERSION = 2;

	void startTest(String name, java.util.Map attributes);

	void endTest(String name, Map attributes);
}
