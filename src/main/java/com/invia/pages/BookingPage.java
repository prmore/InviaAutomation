package com.invia.pages;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.invia.base.TestBase;

public class BookingPage extends TestBase{

	//Page Factory - Object Repository
	@FindBy(xpath="//*[@id='vacationSummary']/ul/ol[1]/li[1]/span[2]")
	WebElement hotelName;
	
	
	public BookingPage() {
		
		
		//Initialize PageFactory elements
		PageFactory.initElements(driver, this);
	}
	
	public String getHotelName() {
		//Click link
		//driver.findElement(By.linkText("Hotelbeschreibung")).click();
		
		wait.until(ExpectedConditions.visibilityOf(hotelName));
		String[] lines = hotelName.getText().split("\\r?\\n");
		return lines[0];
		
	}
	
	public Set<String> getCurrentWindows() {
		System.out.println("inside getCurrentWindows");
		return driver.getWindowHandles();
	}
	
	public void switchToChildWindow() {
		System.out.println("inside switchToChildWindow");
		//Wait until child window gets loaded
		wait.until(ExpectedConditions.numberOfWindowsToBe(3));

		/*//Get window handles
		Set<String> newWindows = driver.getWindowHandles();

		Iterator<String> iterator = newWindows.iterator();
		
		
		//Switch to child window
		while(iterator.hasNext()) {
			String childWindow = iterator.next();

			System.out.println("childWindow:" + childWindow);
			
			if(!HotelDetailsPage.hotelDetailsWindows.contains(childWindow)) {
				System.out.println("Switching to child window: " + childWindow);
				driver.switchTo().window(childWindow);
			}
		}*/
		
		
		ArrayList<String> windows = new ArrayList<>(driver.getWindowHandles());
		driver.switchTo().window(windows.get(2));
		
		
	}
}
