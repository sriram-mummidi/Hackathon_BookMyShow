package com.bookmyshow.testcases;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.bookmyshow.baseclass.BaseTest;
import com.bookmyshow.baseclass.PageBase;
import com.bookmyshow.pageclasses.HomePage;
import com.bookmyshow.pageclasses.SigninPage;

public class SignInTest extends BaseTest {

	HomePage homePage;
	SigninPage signinPage;
	
	@Test(dataProvider = "EmailData")
	public void singinPageTest(String emailid) {
		logger = report.createTest("SignIn Page Test");
		PageBase pageBase = new PageBase(driver, logger);
		try {
			PageFactory.initElements(driver, pageBase);
			homePage = pageBase.OpenApplication();
			homePage.selectCityAlert();
			signinPage = homePage.navigateToSignin();
			signinPage.navigatetoSignin();
			signinPage.enterEmail(emailid);
			signinPage.clickNext();
			signinPage.errorMessagecould();
			signinPage.closepopup();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	@DataProvider(name = "EmailData")
	public Object[][] emailData() throws IOException {
		Properties prop = new Properties();
		InputStream readFile = null;
		readFile = new FileInputStream("config.properties");
		prop.load(readFile);
		String emailid = (String) prop.get("emailid");
		return new Object[][] {{emailid}};
	}
	
}
