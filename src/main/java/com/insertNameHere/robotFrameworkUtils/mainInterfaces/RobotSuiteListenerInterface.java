package com.insertNameHere.robotFrameworkUtils.mainInterfaces;

public interface RobotSuiteListenerInterface {
	public static final int ROBOT_LISTENER_API_VERSION = 2;

	void startSuite(String name, java.util.Map attributes);

	void endSuite(String name, java.util.Map attributes);

}
