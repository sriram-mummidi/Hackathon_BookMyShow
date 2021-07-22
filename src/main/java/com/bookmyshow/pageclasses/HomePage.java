package com.bookmyshow.pageclasses;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.bookmyshow.baseclass.PageBase;

// Home Page Class
public class HomePage extends PageBase {

	public HomePage(WebDriver driver, ExtentTest logger) {
		super(driver, logger);
	}

	public WebElement selectCity;

	@FindBy(xpath = "//a[text()=\"Movies\"]")
	public WebElement movies;
	
	@FindBy(xpath = "//a[text()=\"Sports\"]")
	public WebElement sports;
	
	@FindBy(xpath = "//*[text()=\"Sign in\"]")
	public WebElement signIn;
	
	@FindBy(xpath = "//div[text()=\"Continue with Google\"]")
	public WebElement continueWithGoogle;

	// Selecting City
	public void selectCityAlert() throws IOException {
		Properties prop = new Properties();
		InputStream readFile = null;
		readFile = new FileInputStream("config.properties");
		prop.load(readFile);
		String city = (String) prop.get("city");
		selectCity = driver.findElement(By.xpath("//span[contains(text(), \""+city+"\")]"));
		elementClick(selectCity);
	}

	// Navigating to Movies Page
	public MoviesPage navigateToMovies() {
		elementClick(movies);
		MoviesPage moviesPage = new MoviesPage(driver,logger);
		PageFactory.initElements(driver, moviesPage);
		logger.log(Status.INFO, "Navigated to Movies Page");
		return moviesPage;
	}
	
	// Navigating to Sports Page
	public SportsPage navigateToSports() {
		elementClick(sports);
		SportsPage sportsPage = new SportsPage(driver, logger);
		PageFactory.initElements(driver, sportsPage);
		logger.log(Status.INFO, "Navigated to Sports Page");
		return sportsPage;
	}
	
	// Navigating to SignIn Page
	public SigninPage navigateToSignin() {
		elementClick(signIn);
		waitLoad(3);
		waitforElement(By.xpath("//div[text()=\"Continue with Google\"]"));
		elementClick(continueWithGoogle);
		SigninPage signinpage = new SigninPage(driver,logger);
		PageFactory.initElements(driver, signinpage);
		logger.log(Status.INFO, "Navigated to SignIn Page");
		return signinpage;
	}
	
}
