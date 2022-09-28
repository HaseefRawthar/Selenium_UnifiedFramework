package com.NopCommerce.api.stepdefenition;

import java.io.File;

import org.testng.Assert;

import com.NopCommerce.api.testdata.APITestData;
import com.NopCommerce.api.testdata.TestDataImportAPI;
import com.NopCommerce.baseclass.SetUp;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchema;
import static org.hamcrest.MatcherAssert.assertThat;
import static net.javacrumbs.jsonunit.assertj.JsonAssertions.assertThatJson;

public class APISteps extends SetUp {

	private Response expectedResponse, actualResponse, deleteResponse ;
	private Response tokenResponse = null;
	private int actualResponseCode = 0, expectedResponseCode = 0;
	private long actualResponseTime = 0, expectedResponseTime = 0, actualRepsonseSize = 0, expectedReposneSize = 0;
	private static String token="";
	private int id;
	private File getusersSchema = new File("Schema/getUsersSchema.json");
	private File postUserSchema = new File("Schema/postUserSchema.json");
	private String loginBody;
	private APITestData apitestdataObj;
	private TestDataImportAPI tdImport;
	String body;
	String[] testData;
	
	public APISteps()
	{
		try
		{
			apitestdataObj = new APITestData();
			tdImport = new TestDataImportAPI();
			
			tdImport.readSheet("PostUsers");
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}
	
	public void initializeToken()
	{
		try
		{
			loginBody="{\"email\":\"tester@test.com\",\"password\":\"test@123\"}";
			tokenResponse = RestAssured.given().contentType(ContentType.JSON)
					        .body(loginBody)
					        .when()
					        .post("http://restapi.adequateshop.com/api/authaccount/login");
			
			System.out.println(tokenResponse.getBody().asString());
			
			token = tokenResponse.path("data.Token"); //extract token from JSON response
			System.out.println(token);
					        
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}
	
	@Given("User sends a valid Get Users request")
	public void user_sends_a_valid_get_users_request() {
	    try
	    {
	    	expectedResponseCode = 200;
	    	expectedReposneSize = 3000;
	    	expectedResponseTime = 1000;
	    	
	    	expectedResponse = RestAssured.given()
	    			           .header("Authorization","Bearer "+token)
	    			           .get("http://restapi.adequateshop.com/api/users?page=1");
	    	
	    	actualResponseCode = expectedResponse.getStatusCode(); //get status code from response
	    	System.out.println("Response code: "+actualResponseCode);
	    	
	    	actualResponseTime = expectedResponse.getTime(); //get response time from response
	    	System.out.println("Response Time: "+actualResponseTime);
	    	
	    	actualRepsonseSize = expectedResponse.getBody().asByteArray().length; //get body as a byte array and get its length
	    	System.out.println("Response Size: "+actualRepsonseSize);
	    	
	    	System.out.println("Response body: "+expectedResponse.asPrettyString());
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
			log.error(e);
	    }
	}
	
	@Given("User sends a valid Post Users request")
	public void user_sends_a_valid_post_users_request() {
	    try
	    {
	    	apitestdataObj.generateFakePostData();
	    	testData = apitestdataObj.apiData();
	    	expectedResponseCode = 201;
	    	expectedResponseTime = 1000;
	    	expectedReposneSize = 3000;
	    	body = "{\"name\":\""+testData[0]+"\",\"email\":\""+testData[1]+"\",\"location\":\"USA\"}";
	    	
	    	expectedResponse = RestAssured.given()
	    			           .contentType(ContentType.JSON)
	    			           .header("Authorization","Bearer "+token)
	    			           .body(body)
	    			           .when()
	    			           .post("http://restapi.adequateshop.com/api/users");
	    	
	    	actualResponseCode = expectedResponse.getStatusCode();
	    	System.out.println("Response code: "+actualResponseCode);
	    	
	    	actualResponseTime = expectedResponse.getTime();
	    	System.out.println("Response Time: "+actualResponseTime);
	    	
	    	actualRepsonseSize = expectedResponse.getBody().asByteArray().length;
	    	System.out.println("Response Size: "+actualRepsonseSize);
	    	
	    	System.out.println("Response body: "+expectedResponse.asPrettyString());
	    	
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    	log.error(e);
	    }
	}
	
	@Given("User sends a valid Put User request")
	public void user_sends_a_valid_put_user_request() {
	    try
	    {
	    	apitestdataObj.generateFakePostData();
	    	testData = apitestdataObj.apiData();
	    	expectedResponseCode = 200;
	    	expectedResponseTime = 1000;
	    	expectedReposneSize = 3000;
	    	body = "{\"name\":\""+testData[0]+"\",\"email\":\""+testData[1]+"\",\"location\":\"USA\"}";
	    	
	    	deleteResponse = RestAssured.given()
	    			         .contentType(ContentType.JSON)
			                 .header("Authorization","Bearer "+token)
			                 .body(body)
			                 .when()
			                 .post("http://restapi.adequateshop.com/api/users");
	    	
	    	System.out.println("Post Response body: " + deleteResponse.asPrettyString());
	    	
	    	id = deleteResponse.path("id");
	    	System.out.println(id);
	    	body = "{\"id\": "+id+",\"name\":\"traveler change name\",\"email\":\""+testData[1]+"\",\"location\":\"India\"}";
	    	
	    	expectedResponse = RestAssured.given()
			         		   .contentType(ContentType.JSON)
			         		   .header("Authorization","Bearer "+token)
			         		   .body(body)
			         		   .when()
			         		   .put("http://restapi.adequateshop.com/api/users/"+id);
	    	
	    	actualResponseCode = expectedResponse.getStatusCode();
	    	System.out.println("Response code: "+actualResponseCode);
	    	
	    	actualResponseTime = expectedResponse.getTime();
	    	System.out.println("Response Time: "+actualResponseTime);
	    	
	    	actualRepsonseSize = expectedResponse.getBody().asByteArray().length;
	    	System.out.println("Response Size: "+actualRepsonseSize);
	    	
	    	System.out.println("Response body: "+expectedResponse.asPrettyString());
	    	
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    	log.error(e);
	    }
	}
	
	@Given("User sends a valid Delete User request")
	public void user_sends_a_valid_delete_user_request() {
	    try
	    {
	    	apitestdataObj.generateFakePostData();
	    	testData = apitestdataObj.apiData();
	    	expectedResponseCode = 200;
	    	expectedResponseTime = 1000;
	    	expectedReposneSize = 3000;
	    	body = "{\"name\":\""+testData[0]+"\",\"email\":\""+testData[1]+"\",\"location\":\"USA\"}";
	    	
	    	deleteResponse = RestAssured.given()
			         .contentType(ContentType.JSON)
	                 .header("Authorization","Bearer "+token)
	                 .body(body)
	                 .when()
	                 .post("http://restapi.adequateshop.com/api/users");
	
	    	System.out.println("Post Response body: " + deleteResponse.asPrettyString());
	
	    	id = deleteResponse.path("id");
	    	
	    	expectedResponse = RestAssured.given()
	    			           .header("Authorization","Bearer "+token)
	    			           .when()
	    			           .delete("http://restapi.adequateshop.com/api/users/"+id);
	    	
	    	actualResponseCode = expectedResponse.getStatusCode();
	    	System.out.println("Response code: "+actualResponseCode);
	    	
	    	actualResponseTime = expectedResponse.getTime();
	    	System.out.println("Response Time: "+actualResponseTime);
	    	
	    	actualRepsonseSize = expectedResponse.getBody().asByteArray().length;
	    	System.out.println("Response Size: "+actualRepsonseSize);
	    	
	    	System.out.println("Response body: "+expectedResponse.asPrettyString());
	    	
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    	log.error(e);
	    }
	}

	@When("A success response is received")
	public void a_success_response_is_received() {
	    try
	    {
	    	Assert.assertEquals(actualResponseCode, expectedResponseCode);
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    	log.error(e);
	    }
	}

	@Then("Verify response time and size")
	public void verify_response_time_and_size() {
	    try
	    {
	    	System.out.println("Actual Time: "+ actualResponseTime + " Expected Time: "+ expectedResponseTime);
			System.out.println("Actual Size: "+ actualRepsonseSize + " Expected Size: "+ expectedReposneSize);
			Assert.assertTrue(actualResponseTime <= expectedResponseTime); //actual response time should be less than or equal to expected
			Assert.assertTrue(actualRepsonseSize <= expectedReposneSize); //actual response size should be less than or equal to expected
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    	log.error(e);
	    }
	}

	@And("^Verify (.*) response schema$") //regEx here takes the value from the feature file And statement and the value is given to the method parameter
	public void verify_response_schema(String schema) {
	    try
	    {
	    	if(schema.equals("getUsers"))
	    	{
	    		assertThat(expectedResponse.getBody().asString(),matchesJsonSchema(getusersSchema));
	    	}
	    	else if(schema.equals("postUser"))
	    	{
	    		assertThat(expectedResponse.getBody().asString(), matchesJsonSchema(postUserSchema));
	    	}
	    	
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    	log.error(e);
	    }
	}
	
	@And("^Verify the (.*) using get api$")
	public void verify_the_result_using_get_api(String result) {
	    try
	    {
	    	actualResponse = RestAssured.given()
	    					 .header("Authorization","Bearer "+token)
	    					 .get("http://restapi.adequateshop.com/api/users/"+id);
	    	
	    	if(result.equals("putResult"))
	    	{
	    		String actualJSON = actualResponse.asPrettyString();
		    	String expectedJSON = expectedResponse.asPrettyString();
		    	System.out.println("Expected Response: "+expectedResponse.asString());
				System.out.println("Actual Response: "+actualResponse.asString());
		    	
				assertThatJson(expectedJSON).whenIgnoringPaths("createdat").whenIgnoringPaths("profilepicture").isEqualTo(actualJSON);
	    	}
	    	else if(result.equals("deleteResult"))
	    	{
	    		int responseCode = 404;
	    		actualResponseCode = actualResponse.getStatusCode();
	    		Assert.assertEquals(responseCode, actualResponseCode);
	    	}
	    }
	    catch(Exception e) {
	    	e.printStackTrace();
	    	log.error(e);
	    }
	}
}
