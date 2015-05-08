package com.insertNameHere.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CommonFileUtils {

	/**
	 * Retrieve the value from the config file
	 * @param key
	 * @return
	 */
	public static String getValueFromConfigFile(String key){
		Properties prop = new Properties();
		InputStream input = null;
		String value=null;
		try {
	 
			input = new FileInputStream("Config/config.properties");
			prop.load(input);
			value=prop.getProperty(key);
	 
		} catch (IOException ex) {
			ex.printStackTrace();
		} finally {
			if (input != null) {
				try {
					input.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return value;
	}
	
}
