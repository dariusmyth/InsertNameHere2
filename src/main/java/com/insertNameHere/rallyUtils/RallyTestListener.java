package com.insertNameHere.rallyUtils;

import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import com.insertNameHere.utils.CommonFileUtils;
import com.insertNameHere.utils.Log4jUtils;

public class RallyTestListener extends TestListenerAdapter {

	private static final boolean updateRally = Boolean.valueOf(CommonFileUtils.getValueFromConfigFile("updateRally"));
	private Log4jUtils LOG = new Log4jUtils(RallyTestListener.class);

	@Override
	public void onTestFailure(ITestResult tr) {
		// TODO create the capture and add the photo to the report
		System.out.println("We got a failure on " + tr.getMethod().getMethodName() + ", RallyId=" + getRallyIdList(tr));
		String[] rallyid = getRallyIdList(tr);
		if (!(rallyid.length == 0) && rallyid != null && updateRally) {
			for (String s : rallyid) {
				RallyUtil.createTestResult(s.trim(), TestResultVerdict.FAIL.getValue(), tr.getThrowable().getMessage());
			}
		}
	}

	@Override
	public void onTestSkipped(ITestResult tr) {
		System.out.println("We got a skip on " + tr.getMethod().getMethodName() + ", RallyId=" + getRallyIdList(tr));
		String[] rallyid = getRallyIdList(tr);
		if (!(rallyid.length == 0) && rallyid != null && updateRally) {
			// TODO for the moment we do not do anything about skipped tests in
			// Rally
			// createTestResult(rallyid, TestResultVerdict.ERROR.getValue(),
			// tr.getThrowable().getMessage());
			for (String s : rallyid) {
				RallyUtil.createTestResult(s.trim(), TestResultVerdict.ERROR.getValue(), tr.getThrowable().getMessage());
			}

		}
	}

	@Override
	public void onTestSuccess(ITestResult tr) {
		System.out.println("We got a pass on " + tr.getMethod().getMethodName() + ", RallyId=" + getRallyIdList(tr));
		String[] rallyid = getRallyIdList(tr);
		LOG.logInfo("Passed TC Rally: https://rally1.rallydev.com/#/search?keywords=" + rallyid);
		if (!(rallyid.length == 0) && updateRally) {
			for (String s : rallyid) {
				RallyUtil.createTestResult(s.trim(), TestResultVerdict.PASS.getValue(), "Test run with Automation Scripts");
			}
		}
	}

	private String[] getRallyIdList(ITestResult tr) {
		boolean existAnnotation = tr.getMethod().getConstructorOrMethod().getMethod().isAnnotationPresent(RallyID.class);
		if (existAnnotation) {
			String[] idresponse = tr.getMethod().getConstructorOrMethod().getMethod().getAnnotation(RallyID.class).rallyid();
			return idresponse;
		}

		return new String[] {};
	}
}