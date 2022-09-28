package com.NopCommerce.stepdefenition;

import java.util.ArrayList;

import org.testng.Assert;

import com.NopCommerce.baseclass.SetUp;
import com.NopCommerce.objectrepository.AdminLoginObject;
import com.NopCommerce.objectrepository.DashboardObject;
import com.NopCommerce.objectrepository.LandingPageObject;
import com.NopCommerce.testdata.LoginData;
import com.NopCommerce.testdata.TestDataImportUI;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class LoginSteps extends SetUp {
	
	private LandingPageObject landingpageObj;
	private AdminLoginObject adminloginObj;
	private DashboardObject dashboardObj;
	private LoginData logindataObj;
	private TestDataImportUI tdImport;
	String[] testData;
	
	public LoginSteps()
	{
		try
		{
			log.info("LoginSteps Constructor");
			landingpageObj = new LandingPageObject(driver);
			adminloginObj = new AdminLoginObject(driver);
			dashboardObj = new DashboardObject(driver);
			logindataObj = new LoginData();
			tdImport = new TestDataImportUI();
			
			tdImport.readSheet("Login");
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}

	@Given("User navigates to login screen")
	public void user_navigates_to_login_screen() {
	    try
	    {
	    	String url = "https://www.nopcommerce.com/en/demo";
		    driver.get(url);
		    waitForElementToLoad(landingpageObj.adminArea);
		    landingpageObj.adminArea.click();
		    ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
	    }
	    catch(Exception e) {
	    	System.out.print("Exception occurred is: "+ e.toString());
	    	e.printStackTrace();
	    	log.error(e);
	    }
	}

	@When("User tries to login with invalid credentials")
	public void user_tries_to_login_with_invalid_credentials() {
	    try
	    {
	    	testData = logindataObj.getInValidLoginData();
	    	adminloginObj.login(testData[0], testData[1]);
	    }
	    catch(Exception e) {
	    	System.out.print("Exception occurred is: "+ e.toString());
	    	e.printStackTrace();
	    	log.error(e);
	    }
	}

	@Then("Verify the error message")
	public void verify_the_error_message() {
	    try
	    {
	    	expectedMsg = "Login was unsuccessful. Please correct the errors and try again.No customer account found";
	    	waitForElementToLoad(adminloginObj.invalidMsg);
	    	actualMsg = adminloginObj.invalidMsg.getText().replace("\n", "");
	    	driver.close();
	    	Assert.assertEquals(expectedMsg, actualMsg);
	    }
	    catch(Exception e) {
	    	System.out.print("Exception occurred is: "+ e.toString());
	    	e.printStackTrace();
	    	Assert.assertEquals(expectedMsg, actualMsg);
	    	log.error(e);
	    }
	}
	
	@When("User tries to login with valid credentials")
	public void user_tries_to_login_with_valid_credentials() {
		try
	    {
			testData = logindataObj.getValidLoginData();
	    	adminloginObj.login(testData[0], testData[1]);
	    }
	    catch(Exception e) {
	    	System.out.print("Exception occurred is: "+ e.toString());
	    	e.printStackTrace();
	    	log.error(e);
	    }
	}

	@Then("Verify the text from dashboard")
	public void verify_the_text_from_dashboard() {
		try
	    {
	    	expectedMsg = "Dashboard";
	    	waitForElementToLoad(dashboardObj.dashboardHeading);
	    	actualMsg = dashboardObj.dashboardHeading.getText();
	    	driver.close();
	    	Assert.assertEquals(expectedMsg, actualMsg);
	    }
	    catch(Exception e) {
	    	System.out.print("Exception occurred is: "+ e.toString());
	    	e.printStackTrace();
	    	Assert.assertEquals(expectedMsg, actualMsg);
	    	log.error(e);
	    }
	}
}
