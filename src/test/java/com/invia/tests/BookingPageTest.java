package com.invia.tests;

import java.util.ArrayList;

import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.invia.base.TestBase;
import com.invia.pages.BookingPage;

public class BookingPageTest extends TestBase{

	BookingPage bookingPage;
	
	@Test(priority=8)
	public void VerifyHotelName() {
		log.info("***** TC-6 : Verify hotel name on booking page vs hotel name on hotel selection page *****");
		bookingPage = new BookingPage();
		ArrayList<String> windows = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(windows.get(2));
		
		//get hotel name
		String currentHotelName = bookingPage.getHotelName();
		log.debug("Current Hotel Name: " + currentHotelName);
		
		//Get hotel name from hotel selection page
		driver.switchTo().window(windows.get(0));
		String expectedHotelName = driver.findElement(By.xpath("//*[@id='hotelname-0']/a")).getText();
		log.debug("Expected Hotel Name: " + expectedHotelName);
		
		Assert.assertEquals(currentHotelName, expectedHotelName);
		log.info("PASSED : Verification of hotel name on booking page vs hotel name on hotel selection page is successful *****");
	}

}
