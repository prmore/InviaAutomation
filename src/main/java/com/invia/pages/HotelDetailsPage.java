package com.invia.pages;

import java.util.ArrayList;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.invia.base.TestBase;

public class HotelDetailsPage extends TestBase{

	//Page Factory - Object Repository
	@FindBy(linkText="Hoteldetails")
	WebElement hotelDetails;

	@FindBy(xpath="//*[@id='departureTimeRange']/div/div[1]/div")
	WebElement startSlider;

	@FindBy(xpath="//div[@id='departureTimeFilterSkeleton']/div[3]/span[1]")
	WebElement startTimer;

	@FindBy(xpath="//*[@id='departureTimeRange']/div/div[1]/div")
	WebElement arrivalStartSlider;

	@FindBy(xpath="//*[@id='departureTimeRange']/div/div[3]/div")
	WebElement arrivalEndSlider;

	@FindBy(xpath="//*[@id='returnTimeRange']/div/div[1]/div")
	WebElement returnStartSlider;

	@FindBy(xpath="//*[@id='returnTimeRange']/div/div[3]/div")
	WebElement returnEndSlider;

	@FindBy(xpath="//*[contains(text(),'direct flight')]")
	WebElement directFlights;
	
	@FindBy(id="submit")
	WebElement search;


	public HotelDetailsPage() {
		//Initialize PageFactory elements
		PageFactory.initElements(driver, this);
	}
	
	public WebElement getArrivalStartSlider() { return arrivalStartSlider; }
	public WebElement getArrivalEndSlider() { return arrivalEndSlider; }
	public WebElement getReturnStartSlider() { return returnStartSlider; }
	public WebElement getReturnEndSlider() { return returnEndSlider; }

	public void selectDateOfArrival(String date) {
		WebElement dateOfArrival = driver.findElement(By.xpath("//span[text()=' " + date +"']/parent::label"));
		js.executeScript("arguments[0].click();", dateOfArrival);
	}

	public void switchToChildWindow() {
		//Wait until child window gets loaded
		wait.until(ExpectedConditions.numberOfWindowsToBe(2));
		
		ArrayList<String> windows = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(windows.get(1));
	}
	
	public void clickSearch() {
		js.executeScript("arguments[0].click();", search);
	}

	public int getNumOfDirectFlights() {
		List<WebElement> totalDirectFlights = driver.findElements(By.xpath("//*[contains(text(),'direct flight')]"));

		return totalDirectFlights.size();
	}

	public String getDepartureFlightTimeDetails(int resultFlightNum) {
		WebElement departureFlightTimeDetails = 
				driver.findElement(By.xpath("//*[@id='skeletonOffers']/section[4]/article[" + resultFlightNum + "]//div[contains(@class,'duration-departure')]/div/span[1]"));

		return departureFlightTimeDetails.getText();
	}

	public String getReturnFlightTimeDetails(int resultFlightNum) {
		WebElement returnFlightTimeDetails = 
				driver.findElement(By.xpath("//*[@id='skeletonOffers']/section[4]/article[" + resultFlightNum + "]//div[contains(@class,'duration-return')]/div/span[1]"));

		return returnFlightTimeDetails.getText();
	}

	public boolean verifyFlightTimeDetails(String flightTimeDetails, String timeRangeStart, String timeRangeEnd) {

		boolean isInRange = false;
		
		String[] timeParts = flightTimeDetails.replaceAll("[^0-9:-]","").split("-");
		String flightStartTime = timeParts[0];
		String flightEndTime = timeParts[1];
		log.debug("Flight start time: " + timeRangeStart + " and flight end time : " + flightEndTime);
		log.debug("Selected range start time: " + flightStartTime + " and selected range end time : " + timeRangeEnd);
		/*System.out.println("Output " + flightStartTime);
		System.out.println("Output " + flightEndTime);*/

		DateTimeFormatter dtf = DateTimeFormat.forPattern("HH:mm");
		DateTime dtTimeRangeStart = dtf.parseLocalTime(timeRangeStart).toDateTimeToday();
		DateTime dtFlightStartTime = dtf.parseLocalTime(flightStartTime).toDateTimeToday();
		DateTime dtTimeRangeEnd = dtf.parseLocalTime(timeRangeEnd).toDateTimeToday();
		DateTime dtFlightEndTime = dtf.parseLocalTime(flightEndTime).toDateTimeToday();

		//Verify that flight start time is after selected range start time
		//	and flight end time is before selected range end time
		if ((dtTimeRangeStart.isBefore(dtFlightStartTime)) && (dtFlightEndTime.isBefore(dtTimeRangeEnd))) {
			isInRange = true;
		}
		
		return isInRange;
	}
	
	public void clickBooking() {
		List<WebElement> btnBooking = driver.findElements(By.xpath("//span[text()='Zur Buchung']/ancestor::a"));
		js.executeScript("arguments[0].click();", btnBooking.get(0));
	}
}
