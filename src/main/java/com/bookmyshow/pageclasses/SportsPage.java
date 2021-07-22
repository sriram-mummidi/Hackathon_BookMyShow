package com.bookmyshow.pageclasses;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.bookmyshow.baseclass.PageBase;

// Sports Page Class
public class SportsPage extends PageBase {

	public SportsPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
	}

	@FindBy(xpath = "//div[text() = \"This Weekend\"][@class=\"style__StyledText-sc-7o7nez-0 boewjJ\"]")
	public WebElement thisWeekend;

	@FindBy(xpath = "//div[contains(@style,\"flex-end\")]/div[1]/div[1]/div[1]")
	public WebElement date;

	@FindBy(xpath = "//div[@id='super-container']//div[contains(@class,'commonStyles__HorizontalFlexBox')]/div[1]/div[contains(@class,'style__StyledText')]")
	List<WebElement> sportsNamesElement;

	@FindBy(xpath = "//div[@id='super-container']//div[contains(@class,'commonStyles__HorizontalFlexBox')]/div[4]/div[contains(@class,'style__StyledText')]")
	List<WebElement> sportsPriceElement;

	String names[];
	int price[];

	// To Click on WeekEnd Filter option
	public void clickWeekEnd() {
		elementClick(thisWeekend);
	}

	// To get all the Events
	public void getAllElements() {
		JavascriptExecutor js = (JavascriptExecutor) driver;
		js.executeScript("arguments[0].scrollIntoView();", sportsPriceElement.get(sportsPriceElement.size() - 1));
		waitLoad(2);
		int noOfResults = sportsPriceElement.size();
		logger.log(Status.INFO, "Number of Event: " + noOfResults);
		names = new String[noOfResults];
		price = new int[noOfResults];
		System.out.println(noOfResults);
		for (int i = 0; i < noOfResults; i++) {
			String priceDetails = sportsPriceElement.get(i).getText();
			String[] splitedPrice = priceDetails.split(" ");
			price[i] = Integer.parseInt(splitedPrice[1]);
			names[i] = sportsNamesElement.get(i).getText();
		}
		logger.log(Status.PASS, "Stored all the Events");
	}

	// To sort the events according to the event price
	public void sortAccordingToPrice() {
		for (int i = 0; i < names.length; i++) {
			for (int j = i + 1; j < names.length; j++) {
				if (price[i] > price[j]) {
					int temp = price[j];
					price[j] = price[i];
					price[i] = temp;
					String tempName = names[j];
					names[j] = names[i];
					names[i] = tempName;
				}
			}
		}
		logger.log(Status.PASS, "Sorted events according to price");
	}

	// To Write the Events details into Excel Sheet
	public void writeExcelData() throws FileNotFoundException {
		logger.log(Status.INFO, "Writing Sports name and date of the event into Excel");
		XSSFWorkbook wbe = new XSSFWorkbook();
		XSSFSheet sports_list = wbe.createSheet("Sports Activities List");
		sports_list.createRow(0).createCell(0).setCellValue("Sports Name");
		sports_list.getRow(0).createCell(1).setCellValue("Date");

		for (int i = 0; i < names.length; i++) {
			Row r = sports_list.createRow(i + 1);
			waitLoad(2);
			WebElement element = driver.findElement(By.partialLinkText(names[i]));
			JavascriptExecutor js = (JavascriptExecutor) driver;
			r.createCell(0).setCellValue(names[i]);
			String link = element.findElement(By.xpath("././.")).getAttribute("href");
			System.out.println(names[i] + " - " + link + " - " + price[i]);
			js.executeScript("window.open('" + link + "','_blank');");
			ArrayList<String> tabs = new ArrayList<String>(driver.getWindowHandles());
			driver.switchTo().window(tabs.get(1));
			js.executeScript("window.scrollBy(0,700)", "");
			waitLoad(2);
			r.createCell(1).setCellValue(date.getText());
			driver.close();
			driver.switchTo().window(tabs.get(0));
			waitLoad(2);
		}
		sports_list.autoSizeColumn(0);
		sports_list.autoSizeColumn(1);
		FileOutputStream writefile = new FileOutputStream(
				System.getProperty("user.dir") + "//Outputs//ExcelFiles//Sports Activities List.xlsx");
		try {
			wbe.write(writefile);
			wbe.close();

		} catch (IOException e) {
			logger.log(Status.FAIL, e.getMessage());
		}
		try {
			writefile.close();
		} catch (IOException e) {
			logger.log(Status.FAIL, e.getMessage());
		}
		logger.log(Status.PASS, "Stored all events data in the Excel Sheet");
	}
}
