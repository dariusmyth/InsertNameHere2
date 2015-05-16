package com.insertNameHere.robotFrameworkUtils.mainInterfaces;

public interface RobotKeywordListenerInterface {
	public static final int ROBOT_LISTENER_API_VERSION = 2;

	void startKeyword(String name, java.util.Map attributes);

	void endKeyword(String name, java.util.Map attributes);

	void close();
}
