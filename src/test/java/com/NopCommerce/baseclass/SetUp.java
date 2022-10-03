package com.NopCommerce.baseclass;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeSuite;

public class SetUp {

	public static WebDriver driver;
	public static WebDriverWait wait30Sec;
	public static Logger log;
	public String logPath="";
	public static String projectFolder = System.getProperty("user.dir");
	public static String log4jConfPath =projectFolder+"/src/test/java/com/NopCommerece/resources/log4j.properties";
	public static Date date;
	public static SimpleDateFormat df;
	public static String currentDateTime;
	public static String[] actualArray, expectedArray;
	public static String expectedMsg, actualMsg;
	
	@BeforeSuite
	public void start()
	{
		try {
		 System.setProperty("webdriver.chrome.driver", "/src/main/java/chromedriver.exe");
		 //log = Logger.getLogger(SetUp.class.getName());
		 //System.setProperty("logPath", projectFolder+"/Logs/LogFile");
		 //PropertyConfigurator.configure(log4jConfPath);  //configure log file path
		 
		 //String url = "https://www.nopcommerce.com/en/demo";
		 ChromeOptions options = new ChromeOptions();
		 options.addArguments("window-size=1920,1080");
		 options.addArguments("start-maximized");
		 options.addArguments("headless");
		 driver = new ChromeDriver(options); 
		 //driver = new ChromeDriver();
	     driver.manage().window().maximize();
	     //driver.get(url);
	    
	     log.info("Page opened");
	     
		}
		catch(Exception e) {
			e.printStackTrace();
			log.info(e);
		}
	    
	}
	
	@AfterSuite(alwaysRun=true)
	public void tearDown()
	{
		driver.quit();
	}
	
	public void closeBrowser()
	{
		try
		{
			driver.quit();
		}
		catch(Exception e) {
			e.printStackTrace();
			log.error(e);
		}
	}
	
	public static void initializeLog(String type)
	{
		try
		{
			log = Logger.getLogger(SetUp.class.getName());
			System.setProperty("logPath", projectFolder+"/Logs/LogFile_"+type+"_"+getCurrentDateTime());
			PropertyConfigurator.configure(log4jConfPath); 
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}
	
	public static String getCurrentDateTime() {
	      
		try {
           
            df= new SimpleDateFormat("dd_MM_yyyy_HH_mm_ss");
            date = new Date();
            System.setProperty("currentDateTime", df.format(new Date()));
        }catch(Exception e) {
            e.printStackTrace();
        }
        
		return currentDateTime = df.format(date);
    }
	
	public void waitForElementToLoad(WebElement element)
	{
		try {
		wait30Sec = new WebDriverWait(driver, Duration.ofSeconds(30));
		wait30Sec.until(ExpectedConditions.visibilityOf(element));
		}
		catch(Exception e) {
			e.printStackTrace();
			System.out.println("waited for 30 seconds..");
		}
		
	}

}
