package com.bookmyshow.testcases;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.bookmyshow.baseclass.BaseTest;
import com.bookmyshow.baseclass.PageBase;
import com.bookmyshow.pageclasses.HomePage;
import com.bookmyshow.pageclasses.MoviesPage;

public class MoviesTest extends BaseTest {

	HomePage homePage;
	MoviesPage moviesPage;
	
	@Test
	public void moviesPageTest() {
		logger = report.createTest("Movies Page Test");
		PageBase pageBase = new PageBase(driver, logger);
		try {
			PageFactory.initElements(driver, pageBase);
			homePage = pageBase.OpenApplication();
			homePage.selectCityAlert();
			moviesPage = homePage.navigateToMovies();
			moviesPage.clickOnComingSoon();
			moviesPage.getLanguages();
		}
		catch(Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
}
