package com.invia.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;

import com.invia.base.TestBase;

public class TestUtil extends TestBase{

	public static long IMPLICIT_WAIT = 30;
	public static long PAGE_LOAD_TIMEOUT = 60;
	public static int counter = 1;
	

	public static void setDate(String dateToSelect, WebElement datePickerHeader, WebElement arrowRight) {
		SimpleDateFormat sdf = new SimpleDateFormat("MM.yyyy");
		String[] tempArr;
		boolean isRequiredMonth=false;
		
		while (!isRequiredMonth) {
			tempArr = datePickerHeader.getText().split(" ");
			int curDatePickerMonthNum = getMonthNumByMonthName(tempArr[0]);
			String datePickerHeaderMonthYear = curDatePickerMonthNum + "." + tempArr[1];
					
			tempArr = dateToSelect.split("\\.");
			String expectedMonthYear = tempArr[1] + "." + tempArr[2];
			
			try {
				Date date1 = sdf.parse(datePickerHeaderMonthYear);
				Date date2 = sdf.parse(expectedMonthYear);
		        
		        if(date2.after(date1)) {
		        	js.executeScript("arguments[0].click();", arrowRight);
					Thread.sleep(3000);
					counter++;
		        }else if(date2.equals(date1)){
		        	isRequiredMonth = true;
		        	String day = dateToSelect.split("\\.")[0];
		        	WebElement dayToSelect = driver.findElement(By.xpath("//*[@id='flattrip']//div[contains(@class,'start-input') or contains(@class,'end-input')]/div[2]/div/div/div["+counter+"]/table/tbody/tr/td[text()='" + day+"']"));
		        	js.executeScript("arguments[0].click();", dayToSelect);
		        }
			} catch (Exception e) {
				e.printStackTrace();
				log.error("Failed to set the date. Error: " + e.getMessage());
			}			
		}
	}
	
	public static int getMonthNumByMonthName(String monthName) {
		switch(monthName) {
			case "Januar" : return 1;
			case "Februar" : return 2;
			case "März" : return 3;
			case "April" : return 4;
			case "Mai" : return 5;
			case "Juni" : return 6;
			case "Juli" : return 7;
			case "August" : return 8;
			case "September" : return 9;
			case "Oktober" : return 10;
			case "November" : return 11;
			case "Dezember" : return 12;
		}
		
		return 0;
	}
	
	public static void slide(WebElement startSlider, WebElement endSlider, int startOffset, int endOffset) {
		try {
			Thread.sleep(3000);			
			Actions action= new Actions(driver);
			
			//Set start of slider
			wait.until(ExpectedConditions.visibilityOf(startSlider));
			js.executeScript("arguments[0].scrollIntoView();", startSlider);
			action.dragAndDropBy(startSlider, startOffset, 0).build().perform();

			//Set end of slider
			Thread.sleep(3000);
			wait.until(ExpectedConditions.visibilityOf(endSlider));
			js.executeScript("arguments[0].scrollIntoView();", endSlider);
			action.dragAndDropBy(endSlider, endOffset, 0).build().perform();
		} catch (InterruptedException e) {
			e.printStackTrace();
			log.error("Failed to perform operation on slider. Error: " + e.getMessage());
		}
	}
}
