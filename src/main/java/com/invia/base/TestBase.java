package com.invia.base;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.invia.util.TestUtil;


public class TestBase {
	public static String projectPath;
	public static WebDriver driver;
	public static JavascriptExecutor js;
	public static WebDriverWait wait;
	public static Properties prop;
	public static Logger log;

	public TestBase() {
		//Get Project path
		projectPath = System.getProperty("user.dir");

		//Create log object 
		log = Logger.getLogger("rootCategory");

		//Load the property file
		try {
			File objFile = new File(projectPath + "/src/test/resources/InviaProp.properties");
			FileInputStream objFIS = new FileInputStream(objFile);
			prop = new Properties();
			prop.load(objFIS);
		} catch (IOException e) {
			e.printStackTrace();
			log.error("Failed to load property file" + e.getMessage());
		}
	}

	public static void initialization(String browser){

		log.info("********************** Execution started **********************");
		if(browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", projectPath + "/drivers/chromedriver.exe");
			driver = new ChromeDriver();
			log.info("Chrome browser has been launched...");
		}else if (browser.equalsIgnoreCase("FireFox")) {
			System.setProperty("webdriver.gecko.driver", projectPath + "/drivers/geckodriver.exe");
			driver = new FirefoxDriver();
			log.info("FireFox browser has been launched...");
		}else if (browser.equalsIgnoreCase("IE")) {
			System.setProperty("webdriver.ie.driver", projectPath + "/drivers/IEDriverServer.exe");
			driver = new InternetExplorerDriver();
			log.info("IE browser has been launched...");
		}

		//Define wait object
		wait = new WebDriverWait(driver, 60);

		//Initialize JavaScript executor
		js = (JavascriptExecutor)driver;

		driver.manage().window().maximize();
		driver.manage().deleteAllCookies();
		driver.manage().timeouts().implicitlyWait(TestUtil.IMPLICIT_WAIT,TimeUnit.SECONDS) ;
		driver.manage().timeouts().pageLoadTimeout(TestUtil.PAGE_LOAD_TIMEOUT,TimeUnit.SECONDS) ;

		//Get URL from property file and launch URL
		log.debug("Navigating to URL: " + prop.getProperty("url"));
		driver.navigate().to(prop.getProperty("url"));
	}	
}
