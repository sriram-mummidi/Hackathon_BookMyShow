package com.bookmyshow.baseclass;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.bookmyshow.utilities.ExtentReportManager;

/* 
 * BaseTest Class is used to select the type of the browser 
 * And also invoke other fuctionalities of the Automation Test 
 */
public class BaseTest {

	public WebDriver driver;
	public ExtentReports report = ExtentReportManager.getReportInstance();
	public ExtentTest logger;

	// To Invoke Browser
	public void invokeBrowser(String browserName) {
		// Select and browser based on the browserName

		try {
			if (browserName.equalsIgnoreCase("Chrome")) {
				ChromeOptions options = new ChromeOptions();
				options.addArguments(Arrays.asList("--no-sandbox", "--ignore-certificate-errors",
						"--homepage=about:blank", "--no-first-run"));
				options.setExperimentalOption("excludeSwitches", new String[] { "enable-automation" });
				options.addArguments("--disable-notifications");
				DesiredCapabilities capabilities = DesiredCapabilities.chrome();
				capabilities.setCapability(ChromeOptions.CAPABILITY, options);
				driver = new RemoteWebDriver(new  URL("http://192.168.1.36:4444/wd/hub"),capabilities);
				
			} else if (browserName.equalsIgnoreCase("Mozilla")) {
				FirefoxOptions options = new FirefoxOptions();
				options.setProfile(new FirefoxProfile());
				options.addArguments(Arrays.asList("--no-sandbox", "--ignore-certificate-errors",
						"--homepage=about:blank", "--no-first-run"));
				options.addPreference("dom.webnotifications.enabled", false);
				DesiredCapabilities capabilities = DesiredCapabilities.firefox();
				capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);
				driver = new RemoteWebDriver(new  URL("http://192.168.1.36:4444/wd/hub"),capabilities);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}

		driver.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(120, TimeUnit.SECONDS);
		driver.manage().window().maximize();

	}
	
	// Wait For pageload
	public void waitLoad(int i) {
		try {
			Thread.sleep(i * 1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	/* Fetch the browser name from the properties file */
	@BeforeClass(groups= {"Regression","Default","Smoke"})
	public void openBrowser() throws IOException {
		Properties prop = new Properties();
		InputStream readFile = null;
		readFile = new FileInputStream("config.properties");
		prop.load(readFile);
		String browser = (String) prop.get("Browser");
		invokeBrowser(browser);
	}
	
	/*
	 * to create a new report
	 * to close the browser
	 */
	@AfterClass(groups= {"Regression","Default","Smoke"})
	public void flushReport() {
		report.flush();
		logger.log(Status.INFO, "Quiting the Browser");
		driver.quit();
	}

}
