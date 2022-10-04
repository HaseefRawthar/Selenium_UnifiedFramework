package com.NopCommerce.hooks;


import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;

import com.NopCommerce.baseclass.SetUp;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.cucumber.java.Scenario;
import io.github.bonigarcia.wdm.WebDriverManager;

public class UIHooks extends SetUp {

	@BeforeAll
	public static void before_or_after_all()
	{
		initializeLog("UI");
	}
	
	@Before("@Web")
	public void setUp()
	{
		try
		{
			WebDriverManager.chromedriver().setup();
			start();
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}
	
	
	@After
    public void takeScreenshotAndCloseBrowser(Scenario scenario) {
        if(scenario.isFailed()) {
            final byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
            scenario.attach(screenshot, "image/png", "NopCommerce");
        }
        closeBrowser();
    }
}
