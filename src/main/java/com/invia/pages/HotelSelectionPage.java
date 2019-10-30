package com.invia.pages;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;

import com.invia.base.TestBase;
import com.invia.util.TestUtil;

public class HotelSelectionPage extends TestBase{

	//Page Factory - Object Repository
	@FindBy(xpath="//span[text()='Früheste Anreise']//ancestor::div[1]")
	WebElement arrivalDate;

	@FindBy(xpath="//span[text()='Späteste Abreise']//ancestor::div[1]")
	WebElement departureDate;
	
	@FindBy(xpath="//div[contains(@class,'datepicker-header')]/div")
	WebElement datePickerHeader;
	
	@FindBy(xpath="//div[contains(@class,'datepicker-header')]/span[contains(@class,'icon-arrow-right-bold')]")
	WebElement arrowRight;

	@FindBy(xpath="//input[@id='optCategory2']//parent::label")
	WebElement chkFourStar;
	
	@FindBy(xpath="//input[@id='5 Punkte']//parent::label")
	WebElement excellent;
	
	@FindBy(id="hotelsorting")
	WebElement sortBy;
	
	@FindBy(id="submit")
	WebElement search;
	
	@FindBy(xpath="//*[@id='hotelname-0']/a")
	WebElement firstHotelName;
	
	
	public HotelSelectionPage() {
		//Initialize PageFactory elements
		PageFactory.initElements(driver, this);
	}
	
	public void clickSearch() {
		js.executeScript("arguments[0].click();", search);
	}

	public void setArrivalDate(String dateToSelect) {
		log.debug("Set new arrival date to : " + dateToSelect);
		arrivalDate.click();
		TestUtil.setDate(dateToSelect, datePickerHeader, arrowRight);
	}

	public void setDepartureDate(String dateToSelect) {
		log.debug("Set new departure date to : " + dateToSelect);
		TestUtil.setDate(dateToSelect, datePickerHeader, arrowRight);
	}

	public void selectHotelStarRating() {
		js.executeScript("arguments[0].click();", chkFourStar);
		log.debug("Selected filter with 'Four stars' hotel");
	}
	
	public void selectCustomerReview() {		
		js.executeScript("arguments[0].click();", excellent);
		log.debug("Selected filter hotels with review as 'Excellent'");
	}
	
	public void sortByHighestPriceValue() {
		try {
			log.debug("Sorting results by highest price...");
			Thread.sleep(5000);
			Select objSelect = new Select(driver.findElement(By.id("hotelsorting")));
			objSelect.selectByIndex(2);
		} catch (InterruptedException e) {
			log.error("Failed to sort results. Error: " + e.getMessage());
		}
	}
	
	public List<WebElement> getResultItems() {
		try {
			Thread.sleep(3000);
			wait.until(ExpectedConditions.visibilityOfAllElements(driver.findElement(By.xpath("//strong[@class='price']"))));
			List<WebElement> items = driver.findElements(By.xpath("//strong[@class='price']"));
			return items;			
		} catch (Exception e) {
			log.error("Failed to get price result items. Error: " + e.getMessage());
			return null;
		}
	}
	
	public void clickOffer(int OfferNum) {
		//Click first offer
		WebElement selectOffer = driver.findElement(By.xpath("//*[@id='hotelList']/div[3]/article[" + OfferNum + "]/div[2]/div[3]/a"));
		js.executeScript("arguments[0].click();", selectOffer);
		log.debug("Offer at position " + OfferNum + " selected sucessfully");
	}
}
