package com.insertNameHere.robotFrameworkUtils.listeners;

import java.io.*;
import java.util.Map;
import java.util.List;

import com.insertNameHere.utils.ApplicationLogger;

public class RobotCustomJavaListener {

	public static final int ROBOT_LISTENER_API_VERSION = 2;
	public static final String DEFAULT_FILENAME = "listen_java.txt";
	private BufferedWriter outfile = null;
	private ApplicationLogger LOG = new ApplicationLogger(RobotCustomJavaListener.class);

	public RobotCustomJavaListener(){
		this(DEFAULT_FILENAME);
	}

	public RobotCustomJavaListener(String filename) {
		String tmpdir = System.getProperty("java.io.tmpdir");
		String sep = System.getProperty("file.separator");
		String outpath = "d:/darius" + sep + filename;
		try {
			outfile = new BufferedWriter(new FileWriter(outpath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startSuite(String name, @SuppressWarnings("rawtypes") Map attrs) throws IOException {
		LOG.logWarning("Start suite values : ");
		LOG.logWarning(name + " '" + attrs.get("doc") + "'\n");
	}

	@SuppressWarnings("rawtypes")
	public void startTest(String name, Map attrs) throws IOException {
		LOG.logWarning("Start test values : ");
		LOG.logWarning("- " + name + " '" + attrs.get("doc") + "' [ ");
		List tags = (List) attrs.get("tags");
		for (int i = 0; i < tags.size(); i++) {
			LOG.logWarning(tags.get(i) + " ");
		}
		LOG.logWarning(" ] :: ");
	}

	public void endTest(String name, @SuppressWarnings("rawtypes") Map attrs) throws IOException {
		LOG.logWarning("End test values : ");

		String status = attrs.get("status").toString();
		if (status.equals("PASS")) {
			LOG.logWarning("PASS\n");
		} else {
			LOG.logWarning("FAIL: " + attrs.get("message") + "\n");
		}
	}

	public void endSuite(String name, @SuppressWarnings("rawtypes") Map attrs) throws IOException {
		LOG.logWarning("End suite values : ");
		LOG.logWarning(attrs.get("status") + "\n" + attrs.get("message") + "\n");
	}

	public void close() throws IOException {
		outfile.close();
	}

}