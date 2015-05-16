package com.insertNameHere.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class GenerateChartPage {
private static ApplicationLogger appLog= new ApplicationLogger(GenerateChartPage.class);
	
	//TODO improve the path of the report file get method
	public static void generateChartFromTemplateFile(int failed, int passed) throws IOException{
		String version = System.getProperties().getProperty("directory");
		
		File log= new File("log.html");
		System.err.println("Log path: "+log.getAbsolutePath());
		System.err.println("Log parent: "+version);
		File htmlTemplateFile = new File("src/WebTemplate/pie.html");
		File file = new File("report.html");
		String path = file.getCanonicalPath();
		path=path.replace("\\report.html", "\\reports\\report.html");
		appLog.logInfo(path);
		String htmlString = FileUtils.readFileToString(htmlTemplateFile);
		String title = Integer.toString(failed);
		String body = Integer.toString(passed);
		String link= "file:///"+path;
		String linkLog="file:///"+path;
		htmlString = htmlString.replace("$FAILED", title);
		htmlString = htmlString.replace("$PASSED", body);
		htmlString = htmlString.replace("$link", link);
		htmlString = htmlString.replace("$log", link);
		File newHtmlFile = new File("src/WebTemplate/pieNew.html");
		FileUtils.writeStringToFile(newHtmlFile, htmlString);
		appLog.logInfo("");
	}
}
