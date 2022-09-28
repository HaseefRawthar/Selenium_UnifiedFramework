package com.NopCommerece.testscripts;

import java.util.ArrayList;

import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.NopCommerce.baseclass.SetUp;
import com.NopCommerce.objectrepository.AdminLoginObject;
import com.NopCommerce.objectrepository.LandingPageObject;

public class AdminLogin extends SetUp {

	LandingPageObject landingpageObj;
	AdminLoginObject adminLoginObj;
	
	@BeforeClass
	public void initialize()
	{
		try
		{
			landingpageObj = new LandingPageObject(driver);
			adminLoginObj = new AdminLoginObject(driver);
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
	
	@Test
	public void invalidLogin()
	{
		log.info("Invalid login test");
		try
		{
			expectedMsg = "Login was unsuccessful. Please correct the errors and try again.No customer account found";
			waitForElementToLoad(landingpageObj.adminArea);
			JavascriptExecutor js = (JavascriptExecutor) driver;
			js.executeScript("window.scrollBy(0,350)", "");
			//js.executeScript("arguments[0].click();", homeObj.adminArea);
			landingpageObj.adminArea.click();
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			
			adminLoginObj.login("admin@email.com", "test");
			waitForElementToLoad(adminLoginObj.invalidMsg);
			actualMsg = adminLoginObj.invalidMsg.getText().replace("\n", "");
			
		}
		catch(Exception e)
		{
			e.printStackTrace();
			log.info(e);
		}
		System.out.println(actualMsg);
		Assert.assertEquals(expectedMsg, actualMsg);
		log.info("Assertion passed");
	}
}
