package com.invia.pages;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.invia.base.TestBase;
import com.invia.util.TestUtil;

public class SearchPage extends TestBase {

	//Page Factory - Object Repository
	@FindBy(id="idestflat")	
	WebElement destinationElement1;

	@FindBy(xpath="//div[@id='ac_old']//input[@id='idestflat']")
	WebElement destinationElement2;

	@FindBy(xpath="//div[@id='ac_old']//span[text()='Regionen']/following-sibling::ul")
	WebElement searchedDest;

	@FindBy(xpath="//span[text()='Früheste Anreise']//ancestor::div[1]")
	WebElement arrivalDate;

	@FindBy(xpath="//span[text()='Späteste Abreise']//ancestor::div[1]")
	WebElement departureDate;

	@FindBy(xpath="//div[contains(@class,'datepicker-header')]/div")
	WebElement datePickerHeader;

	@FindBy(xpath="//div[contains(@class,'datepicker-header')]/span[contains(@class,'icon-arrow-right-bold')]")
	WebElement arrowRight;

	@FindBy(id="travellerSummary")
	WebElement travellerSummary;

	@FindBy(id="adultCount")
	WebElement numOfAdults;

	@FindBy(xpath="//*[@id='adult']/button[1]")
	WebElement addAdult;

	@FindBy(name="children")
	WebElement numOfChildren;

	@FindBy(xpath="//*[@id='child']/button[1]")
	WebElement addChildren;

	@FindBy(xpath="//div[@id='travellerLayer']//button[text()='Auswahl übernehmen']")
	WebElement applyTravellerSelection;

	@FindBy(id="submit")
	WebElement offerSearch;

	public SearchPage() {
		//Initialize PageFactory elements
		PageFactory.initElements(driver, this);
	}

	//Get home page title
	public String getSearchPageTitle() {
		return driver.getTitle();
	}

	public void setDestination(String destination) {
		//Direct send keys doesn't work so need to click the element and then perform send keys
		log.debug("Set search destination to : " + destination);
		destinationElement1.click();
		destinationElement2.sendKeys(destination);
		searchedDest.click();
	}

	public void setArrivalDate(String dateToSelect) {
		log.debug("Set arrival date to : " + dateToSelect);
		arrivalDate.click();
		TestUtil.setDate(dateToSelect, datePickerHeader, arrowRight);
	}

	public void setDepartureDate(String dateToSelect) {
		log.debug("Set departure date to : " + dateToSelect);
		TestUtil.setDate(dateToSelect, datePickerHeader, arrowRight);
	}

	public void setNumOfTravellers(int numOfTravellerAdults, int numOfTravellerChildren) {

		log.debug("Set value of Adults = " + numOfTravellerAdults + " and Children = " + numOfTravellerChildren);
		js.executeScript("arguments[0].click();", travellerSummary);

		//Set adults count
		int curNoOfAdults = Integer.parseInt(numOfAdults.getAttribute("value"));
		while((numOfTravellerAdults - curNoOfAdults) > 0) {
			addAdult.click();
			curNoOfAdults = Integer.parseInt(numOfAdults.getAttribute("value"));
		}

		//Set children count
		int curNumOfChildren = Integer.parseInt(numOfChildren.getAttribute("value"));
		while((numOfTravellerChildren - curNumOfChildren) > 0) {
			addChildren.click();
			curNumOfChildren = Integer.parseInt(numOfChildren.getAttribute("value"));
		}

		js.executeScript("arguments[0].click();", applyTravellerSelection);
	}

	public void clickSearch() {
		js.executeScript("arguments[0].click();", offerSearch);
	}
}
