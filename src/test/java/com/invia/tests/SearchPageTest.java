package com.invia.tests;

import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import com.invia.base.TestBase;
import com.invia.pages.SearchPage;


public class SearchPageTest extends TestBase{

	SearchPage searchPage;
	
	@BeforeTest
	@Parameters("browser")
	public void setup(@Optional("Chrome") String browser) {
		initialization(browser);
		searchPage = new SearchPage();
	}
	
	@Test
	public void SetSerachCriteria() {
		log.info("***** TC-1 : Set search criteria *****");
		//Set destination - Hotel
		String destination = prop.getProperty("Destination");
		searchPage.setDestination(destination);
		
		//Set arrival and departure dates
		String arrivalDateToSelect = prop.getProperty("ArrivalDate");
		searchPage.setArrivalDate(arrivalDateToSelect);
		String departureDateToSelect = prop.getProperty("DepartureDate");
		searchPage.setDepartureDate(departureDateToSelect);
		
		//Set number of travellers and click search
		int numOfTravellerAdults = Integer.parseInt(prop.getProperty("NumberOfAdults"));
		int numOfTravellerChildren = Integer.parseInt(prop.getProperty("NumberOfChildren"));
		searchPage.setNumOfTravellers(numOfTravellerAdults, numOfTravellerChildren);
		
		searchPage.clickSearch();
		log.info("PASSED : Successfully set the search criteria");
	}
	
	@AfterTest
	public void doCleanUpStuff() throws InterruptedException {
		Thread.sleep(5000);
		driver.quit();
	}
}
