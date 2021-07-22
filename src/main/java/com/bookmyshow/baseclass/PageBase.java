package com.bookmyshow.baseclass;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.By;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.bookmyshow.pageclasses.HomePage;
import com.bookmyshow.utilities.DateUtil;

// PageBase class has the methods used for Automation
public class PageBase extends BaseTest {

	public ExtentTest logger;

	public PageBase(WebDriver driver, ExtentTest logger) {
		this.driver = driver;
		this.logger = logger;
	}

	// Opening Website
	public HomePage OpenApplication() throws IOException {
		Properties prop = new Properties();
		InputStream readFile = null;
		readFile = new FileInputStream("config.properties");
		prop.load(readFile);
		String url = (String) prop.get("URL");
		logger.log(Status.INFO, "Opening the WebSite");
		driver.get(url);
		logger.log(Status.PASS, "Successfully Opened the https: " + url);
		HomePage homepage = new HomePage(driver, logger);
		PageFactory.initElements(driver, homepage);
		return homepage;
	}

	/*
	 * Close browser
	 */
	public void closeBrowser() {
		String title = driver.getTitle();
		logger.log(Status.INFO, "Closing the window - "+title);
		driver.close();
	}

	/*
	 * Quit browser
	 */
	public void quitBrowser() {
		logger.log(Status.INFO, "Quiting the Browser");
		driver.quit();
	}

	/*
	 * Entering test in input box
	 */
	public void enterText(WebElement element, String data) {
		try {
			element.sendKeys(data);
			reportPass(data + " - Entered successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}

	/*
	 * Wait for element
	 */
	public void waitforElement(By locator) {
		WebDriverWait wait = new WebDriverWait(driver, 40);
		logger.log(Status.INFO, "Waiting for element");
		wait.until(ExpectedConditions.presenceOfElementLocated(locator));
		logger.log(Status.PASS, "Found element");
	}

	/*
	 * Clicking element
	 */
	public void elementClick(WebElement element) {

		try {
			String Elemnttext = element.getText();
			element.click();
			reportPass(Elemnttext + " : Element Clicked Successfully");
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
	}
	
	/*
	 * To serify element is Display or not
	 */
	public boolean isElementPresent(WebElement element) {
		try {
			if (element.isDisplayed()) {
				reportPass(element.getText() + " : Element is Displayed");
				return true;
			}
		} catch (Exception e) {
			reportFail(e.getMessage());
		}
		return false;
	}

	/*
	 * Failure report
	 */
	public void reportFail(String reportString) {
		logger.log(Status.FAIL, reportString);
		takeScreenShotOnFailure();
		Assert.assertTrue(false, "Failing the Test after capturing the error!");

		report.flush();
	}

	/*
	 * Test pass report
	 */
	public void reportPass(String reportString) {
		logger.log(Status.PASS, reportString);
	}

	/*
	 * Taking test failure screenshot
	 */
	public void takeScreenShotOnFailure() {
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);

		File destFile = new File(System.getProperty("user.dir") + "\\outputs\\screenshots-failure\\" + DateUtil.getTimeStamp() + ".png");
		try {
			FileUtils.copyFile(sourceFile, destFile);
			logger.addScreenCaptureFromPath(
					System.getProperty("user.dir") + "\\outputs\\screenshots-failure\\" + DateUtil.getTimeStamp() + ".png");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/*
	 * Taking error signin screenshot
	 */
	public void takeScreenShotOnSigninFailure() {
		TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
		File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);

		File destFile = new File(System.getProperty("user.dir") + "\\outputs\\signin-failure\\" + DateUtil.getTimeStamp() + ".png");
		try {
			FileUtils.copyFile(sourceFile, destFile);
			logger.addScreenCaptureFromPath(
					System.getProperty("user.dir") + "\\outputs\\signin-failure\\" + DateUtil.getTimeStamp() + ".png");

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
	
}
