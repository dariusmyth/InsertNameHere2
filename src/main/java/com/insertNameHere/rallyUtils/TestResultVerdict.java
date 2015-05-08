package com.insertNameHere.rallyUtils;

public enum TestResultVerdict
{
	BLOCKED("Blocked"), ERROR("Error"), PASS("Pass"), FAIL("Fail"), INCONCLUSIVE("Inconclusive");

	private String	value;

	private TestResultVerdict(String value)
	{
		this.setValue(value);
	}

	public String getValue()
	{
		return value;
	}

	public void setValue(String value)
	{
		this.value = value;
	}

}