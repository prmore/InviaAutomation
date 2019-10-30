package com.invia.tests;

import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.Test;

import com.invia.base.TestBase;
import com.invia.pages.HotelDetailsPage;
import com.invia.util.TestUtil;

public class HotelDetailsPageTest extends TestBase{

	HotelDetailsPage hotelDetailsPage;

	@Test(priority=4)
	public void SetHotelDetailsPageFilters() {
		try {
			log.info("***** TC-4 : Set Hotel details page filters *****");
			hotelDetailsPage = new HotelDetailsPage();

			hotelDetailsPage.switchToChildWindow();

			//Set DepartureTimeArrivalRange
			TestUtil.slide(hotelDetailsPage.getArrivalStartSlider(), hotelDetailsPage.getArrivalEndSlider(), 40,-30);

			//Set DepartureTimereturnRange
			TestUtil.slide(hotelDetailsPage.getReturnStartSlider(), hotelDetailsPage.getReturnEndSlider(), 0,-120);

			//Select date of arrival
			Thread.sleep(3000);
			String dateOfArrival = prop.getProperty("NewArrivalDate");
			hotelDetailsPage.selectDateOfArrival(dateOfArrival);

			//Click search
			hotelDetailsPage.clickSearch();

			//Get direct flights from the first page
			int numOfDirectFlights = hotelDetailsPage.getNumOfDirectFlights();
			log.info("Number of direct flights available on the first page : " + numOfDirectFlights);
			log.info("PASSED : Successfully set hotel details page filters");
			
		} catch (Exception e) {
			log.error("Failed to set filters on hotel details page. Error: " + e.getMessage());
		}
	}

	@Test(priority=5)
	public void VerifyFlightTime() {
		log.info("***** TC-5 : Verify that Flight timings are within selected range *****");
		
		//Verify departure flight timings
		String departureFlightTimeDetails = hotelDetailsPage.getDepartureFlightTimeDetails(1);		
		boolean departureFlightTimeWithinRange = hotelDetailsPage.verifyFlightTimeDetails(departureFlightTimeDetails, "04:00", "21:00");
		Assert.assertTrue(departureFlightTimeWithinRange);

		//Verify return flight timings
		String returnFlightTimeDetails = hotelDetailsPage.getReturnFlightTimeDetails(1);
		boolean returnFlightTimeWithinRange = hotelDetailsPage.verifyFlightTimeDetails(returnFlightTimeDetails, "00:00", "12:00");
		Assert.assertTrue(returnFlightTimeWithinRange);
		
		log.info("PASSED: Verification of Flight timings within selected range is successful");
	}

	@AfterClass
	public void clickBooking() {
		hotelDetailsPage.clickBooking();
	}
}
