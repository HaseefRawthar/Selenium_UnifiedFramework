package com.NopCommerce.api.hooks;

import com.NopCommerce.api.stepdefenition.APISteps;
import com.NopCommerce.baseclass.SetUp;

import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;

public class APIHooks extends SetUp {

	private APISteps apistepsObj;
	
	public APIHooks()
	{
		apistepsObj = new APISteps();
	}
	
	@BeforeAll
	public static void before_or_after_all()
	{
		initializeLog("API");
	}
	
	@Before("@API")
	public void setUp()
	{
		try
		{
			apistepsObj.initializeToken();
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}
	
}
