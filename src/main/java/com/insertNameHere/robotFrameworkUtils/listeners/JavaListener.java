package com.insertNameHere.robotFrameworkUtils.listeners;

import java.io.*;
import java.util.Map;
import java.util.List;

public class JavaListener {

	public static final int ROBOT_LISTENER_API_VERSION = 2;
	public static final String DEFAULT_FILENAME = "listen_java.txt";
	private BufferedWriter outfile = null;

	public JavaListener() {
		this(DEFAULT_FILENAME);
	}

	public JavaListener(String filename) {
		String tmpdir = System.getProperty("java.io.tmpdir");
		String sep = System.getProperty("file.separator");
		String outpath = "d:/darius" + sep + filename;
		try {
			outfile = new BufferedWriter(new FileWriter(outpath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void startSuite(String name, Map attrs) throws IOException {
		outfile.write(name + " '" + attrs.get("doc") + "'\n");
	}

	public void startTest(String name, Map attrs) throws IOException {
		outfile.write("- " + name + " '" + attrs.get("doc") + "' [ ");
		List tags = (List) attrs.get("tags");
		for (int i = 0; i < tags.size(); i++) {
			outfile.write(tags.get(i) + " ");
		}
		outfile.write(" ] :: ");
	}

	public void endTest(String name, Map attrs) throws IOException {
		String status = attrs.get("status").toString();
		if (status.equals("PASS")) {
			outfile.write("PASS\n");
		} else {
			outfile.write("FAIL: " + attrs.get("message") + "\n");
		}
	}

	public void endSuite(String name, Map attrs) throws IOException {
		outfile.write(attrs.get("status") + "\n" + attrs.get("message") + "\n");
	}

	public void close() throws IOException {
		outfile.close();
	}

}