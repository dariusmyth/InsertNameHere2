package com.insertNameHere.rallyUtils.objects;

public class RallyTestResult {

	private String TestSet;
	private String TestCase;
	private String Date;
	private String Verdict;
	private String Tester;
	private String Build;
	private String Notes;

	/**
	 * @param testSet
	 * @param testCase
	 * @param date
	 * @param verdict
	 * @param tester
	 * @param build
	 * @param notes
	 */
	public RallyTestResult(String testSet, String testCase, String date, String verdict, String tester, String build, String notes) {
		TestSet = testSet;
		TestCase = testCase;
		Date = date;
		Verdict = verdict;
		Tester = tester;
		Build = build;
		Notes = notes;
	}

	
	public String getTestSet() {
		return TestSet;
	}

	public void setTestSet(String testSet) {
		TestSet = testSet;
	}

	public String getTestCase() {
		return TestCase;
	}

	public void setTestCase(String testCase) {
		TestCase = testCase;
	}

	public String getDate() {
		return Date;
	}

	public void setDate(String date) {
		Date = date;
	}

	public String getVerdict() {
		return Verdict;
	}

	public void setVerdict(String verdict) {
		Verdict = verdict;
	}

	public String getTester() {
		return Tester;
	}

	public void setTester(String tester) {
		Tester = tester;
	}

	public String getBuild() {
		return Build;
	}

	public void setBuild(String build) {
		Build = build;
	}

	public String getNotes() {
		return Notes;
	}

	public void setNotes(String notes) {
		Notes = notes;
	}

}