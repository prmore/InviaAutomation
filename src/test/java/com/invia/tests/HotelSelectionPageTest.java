package com.invia.tests;

import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.invia.base.TestBase;
import com.invia.pages.HotelSelectionPage;

public class HotelSelectionPageTest extends TestBase{

	HotelSelectionPage hotelSelectionPage;

	@Test(priority=2)
	public void VerifyHotelSelectionPageFilters() {
		log.info("***** TC-2 : Verify Hotel selection page filters *****");
		hotelSelectionPage = new HotelSelectionPage();

		//Set new arrival and departure dates
		String arrivalDateToSelect = prop.getProperty("NewArrivalDate");
		hotelSelectionPage.setArrivalDate(arrivalDateToSelect);
		String departureDateToSelect = prop.getProperty("NewDepartureDate");
		hotelSelectionPage.setDepartureDate(departureDateToSelect);
		
		//Click search
		hotelSelectionPage.clickSearch();

		//Select four star check box and Excellent review
		hotelSelectionPage.selectHotelStarRating();
		hotelSelectionPage.selectCustomerReview();

		log.info("PASSED : Hotel selection page filters verification is successful");
	}
	
	@Test(priority=3)
	public void VerifyResultsSortedByHighestPrice() {
		log.info("***** TC-3 : Verify results sorted by highest price *****");
		//Sort highest price value 
		hotelSelectionPage.sortByHighestPriceValue();

		//Verify returned result items are sorted
		List<WebElement> resultItems = hotelSelectionPage.getResultItems();

		for(int i=1; i< resultItems.size(); i++) {
			int price1 = Integer.parseInt(resultItems.get(i-1).getText().replaceAll("[^0-9]", ""));
			int price2 = Integer.parseInt(resultItems.get(i).getText().replaceAll("[^0-9]", ""));
			
			log.debug("Price 1: " + price1 + " Price 2: " + price2);
			
			Assert.assertTrue(price1 >= price2);
		}
		log.info("PASSED : Verification of results sorted by highest price is successful");
	}
	
	@AfterClass
	public void selectHotel() {
		//Select first offer
		hotelSelectionPage.clickOffer(1);
	}
}
