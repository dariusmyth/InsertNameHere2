package com.insertNameHere.models;

import com.google.gson.Gson;

public abstract class AbstractModel
{

	/**
	 * Returns the class marshalled as JSON (String)
	 */
	@Override
	public String toString()
	{
		return new Gson().toJson(this);
	}

}