package com.bookmyshow.pageclasses;

import java.util.Iterator;
import java.util.Set;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.bookmyshow.baseclass.PageBase;

// SignIn Page Class
public class SigninPage extends PageBase {

	public SigninPage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
	}
	
	String homepage;
	
	@FindBy(xpath = "//*[@aria-label='Email or phone']")
	public WebElement emailElement;

	@FindBy(xpath = "//span[text()=\"Next\"]")
	public WebElement nextElement;
	
	@FindBy(xpath = "//*[text()=\"Couldn't find your Google Account\"]")
	public WebElement invalidUsername;
	
	// Switching to SignIn window
	public void navigatetoSignin() {
		Set<String> s = driver.getWindowHandles();
		Iterator<String> itr = s.iterator();
		homepage = itr.next();
		String signinpage = itr.next();
		driver.switchTo().window(signinpage);
		logger.log(Status.PASS, "Switched to SignIn Window");
	}
	
	// To enter Email value 
	public void enterEmail(String email) {
		enterText(emailElement, email);
	}
	
	// To click on the Next Button
	public void clickNext() {
		elementClick(nextElement);
	}
	
	// To capture error message
	public void errorMessagecould() {
		if(isElementPresent(invalidUsername))
		{
			invalidUsername.getRect();
			takeScreenShotOnSigninFailure();
		}
	}
	
	// To close the SignIn window and switch to Home Page
	public void closepopup() {
		closeBrowser();
		driver.switchTo().window(homepage);
		logger.log(Status.INFO, "Switching to Home Page Window");
	}

}
