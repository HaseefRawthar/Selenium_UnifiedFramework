package com.NopCommerce.stepdefenition;

import org.testng.Assert;

import com.NopCommerce.baseclass.SetUp;
import com.NopCommerce.objectrepository.HomeObject;
import com.NopCommerce.objectrepository.LandingPageObject;
import com.NopCommerce.objectrepository.RegisterUserObject;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegisterUserSteps extends SetUp {

	private LandingPageObject landingpageObj;
	private RegisterUserObject registeruserObj;
	private HomeObject homeObj;
	
	public RegisterUserSteps()
	{
		try
		{
			log.info("RegisterUser constructor");
			landingpageObj = new LandingPageObject(driver);
			registeruserObj = new RegisterUserObject(driver);
			homeObj = new HomeObject(driver);
		}
		catch(Exception e) {
			e.printStackTrace();
			log.info(e);
		}
	}
	
	@Given("User navigates to the registration page")
	public void user_navigates_to_the_registration_page() {
		try
	    {
	    	String url = "https://demo.nopcommerce.com";
		    driver.get(url);
		    waitForElementToLoad(landingpageObj.register);
		    landingpageObj.register.click();
		    log.info("user navigated to register page");
	    }
	    catch(Exception e) {
	    	System.out.print("Exception occurred is: "+ e.toString());
	    	e.printStackTrace();
	    	log.error(e);
	    }
	}

	@When("User enters data in all fields")
	public void user_enters_data_in_all_fields() {
	    try
	    {
	    	registeruserObj.registration("Tom", "Smith", "email22@email.com", "Test", "password");
	    }
	    catch(Exception e) {
	    	System.out.print("Exception occurred is: "+ e.toString());
	    	e.printStackTrace();
	    	log.error(e);
	    }
	}
	
	@And("Click on Register button")
	public void click_on_register_button() {
	   try
	   {
		   registeruserObj.registerButton.click();
	   }
	   catch(Exception e) {
		   
	   }
	}

	@Then("Verify registration success message")
	public void verify_registration_success_message() {
	    try
	    {
	    	expectedMsg = "Your registration completed";
	    	waitForElementToLoad(homeObj.regCompleteMsg);
	    	actualMsg = homeObj.regCompleteMsg.getText();
	    	Assert.assertEquals(actualMsg, expectedMsg);
	    }
	    catch(Exception e) {
	    	System.out.print("Exception occurred is: "+ e.toString());
	    	e.printStackTrace();
	    	Assert.assertEquals(actualMsg, expectedMsg);
	    	log.error(e);
	    }
	}
}
