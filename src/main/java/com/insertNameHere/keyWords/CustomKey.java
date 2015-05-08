package com.insertNameHere.keyWords;

import java.util.List;

import org.testng.Assert;

import com.insertNameHere.robotFrameworkUtils.customErrors.ContinueOnErrorCustom;
import com.insertNameHere.robotFrameworkUtils.customErrors.StopOnErrorCustom;
import com.insertNameHere.robotFrameworkUtils.listeners.JavaListener;
import com.insertNameHere.utils.ApplicationLogger;



public class CustomKey  {

	public static final String ROBOT_LIBRARY_SCOPE = "GLOBAL";  
	public static final String DEFAULT_FILENAME = "listen_java.txt";

    public static final JavaListener ROBOT_LIBRARY_LISTENER = new JavaListener(DEFAULT_FILENAME);
    private ApplicationLogger LOG=new ApplicationLogger(CustomKey.class);
    
    public CustomKey(){
    	System.out.println("Initial Logging");
    }
    
	public void customKeyTest(){
		System.out.println("Hello crappy");
	}
	
	public void continueOnFailure(){
		try{
		Assert.assertTrue(1==2);
		}catch(AssertionError e){
			System.out.println("Darius - Continued even thought it failed with message "+e);
			throw new ContinueOnErrorCustom();
		}
		System.out.println("Hello AFter fail");
		LOG.logInfo("SYCCESS continue");
		
	}
	
	public void stopOnFailure(){
		try{
		Assert.assertTrue(1==2);
		}catch(AssertionError e){
			
			System.out.println("Darius - Stopped on failed with message "+e);
			throw new StopOnErrorCustom();
		}
	}
	
	public void verifyEquals(List<String> i, List<String> f){
		Assert.assertEquals(i, f,"Error");
	}
}
