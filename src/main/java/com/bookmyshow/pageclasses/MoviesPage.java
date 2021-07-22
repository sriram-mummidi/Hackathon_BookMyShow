package com.bookmyshow.pageclasses;

import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.bookmyshow.baseclass.PageBase;
import com.bookmyshow.utilities.WriteExcelData;

// Movies Page Class
public class MoviesPage extends PageBase {

	public MoviesPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
	}
	
	@FindBy(xpath = "//div[@class=\"style__WidgetContainerBody-sc-1ljcxl3-0 jImLDr\"]")
	public WebElement comingSoon;
	
	@FindBy(xpath = "//div[@class=\"style__StyledText-sc-7o7nez-0 boewjJ\"]")
	public List<WebElement> languages;
	
	// To click on coming soon element
	public void clickOnComingSoon() {
		elementClick(comingSoon);
	}
	
	/* 
	 * To get all the languages
	 * Storing languages in Excel 
	 */
	public void getLanguages() throws Exception {
		List<String> movielanguages = new ArrayList<String>();
		for(WebElement i:languages)
			movielanguages.add(i.getText());
		logger.log(Status.INFO, "Retriving All the languages");
		WriteExcelData writeExcelData = new WriteExcelData();
		writeExcelData.writeListData("Movie Lagunages", "List", movielanguages);
		logger.log(Status.PASS, "Stored all Languages in Excel Sheet");
	}
	
}
