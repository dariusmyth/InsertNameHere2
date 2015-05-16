package com.insertNameHere.robotFrameworkUtils.listeners;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import com.insertNameHere.robotFrameworkUtils.mainInterfaces.RobotListenerGeneralInterface;

public class RoboJavaListenerImplementation implements RobotListenerGeneralInterface {

	@Override
	public void startSuite(String name, Map attributes) {
		System.out.println("Darius - Start Suite");

	}

	@Override
	public void endSuite(String name, Map attributes) {
		System.out.println("Darius - End Suite");
		
	}

	@Override
	public void startTest(String name, Map attributes) {
		
		System.out.println("Darius - Start Test");
	}


	@Override
	public void startKeyword(String name, Map attributes) {
		System.out.println("Darius - Start Keyword");
	}

	@Override
	public void endKeyword(String name, Map attributes) {
		System.out.println("Darius - End Keyword");
	}

	@Override
	public void logMessage(Map message) {
		System.out.println("Darius - LOG Message");
	}

	@Override
	public void message(Map message) {
		System.out.println("Darius - MEssage Listener");
	}

	@Override
	public void outputFile(String path) {
		System.out.println("Darius - Output file");
		
	}

	@Override
	public void logFile(String path) {
		System.out.println("Darius - Log file");
	}

	@Override
	public void reportFile(String path) {
		System.out.println("Darius - Report File");

	}

	@Override
	public void debugFile(String path) {
		System.out.println("Darius - Debug File path");

	}

	@Override
	public void close() {
		System.out.println("Darius - Close");
	}

	@Override
	public void endTest(String name, Map attributes) {
		
		
		
	}

}
