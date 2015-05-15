package com.insertNameHere.utils;

import java.io.File;
import java.io.IOException;

import org.apache.commons.io.FileUtils;

public class GenerateChartFromGoogleAPI {

	
	//TODO improve the path of the report file get method
	public static void generateChartFromTemplateFile(int failed, int passed) throws IOException{
		File htmlTemplateFile = new File("src/WebTemplate/pie.html");
		File file = new File("report.html");
		String path = file.getCanonicalPath();
		path=path.replace("\\report.html", "\\src\\reports\\report.html");
		System.err.println(path);
		String htmlString = FileUtils.readFileToString(htmlTemplateFile);
		String title = Integer.toString(failed);
		String body = Integer.toString(passed);
		String link= "file:///"+path;
		htmlString = htmlString.replace("$FAILED", title);
		htmlString = htmlString.replace("$PASSED", body);
		htmlString = htmlString.replace("$link", link);
		File newHtmlFile = new File("src/WebTemplate/pieNew.html");
		FileUtils.writeStringToFile(newHtmlFile, htmlString);
	}
}
