package com.bookmyshow.utilities;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;
import com.aventstack.extentreports.reporter.configuration.ChartLocation;


//ExtentReportManager is used to manage the reports for the entire automation
public class ExtentReportManager {

	public static ExtentReports report;
	
	public static ExtentReports getReportInstance() {

		if (report == null) {
			String reportName = DateUtil.getTimeStamp() + ".html";
			ExtentHtmlReporter htmlReporter = new ExtentHtmlReporter(
					System.getProperty("user.dir") + "\\Outputs\\Test-Reports\\" + reportName);
			report = new ExtentReports();
			report.attachReporter(htmlReporter);

			Properties prop = new Properties();
			InputStream readFile = null;
			try {
				readFile = new FileInputStream("config.properties");
				prop.load(readFile);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String browserName = (String) prop.get("Browser");
			report.setSystemInfo("OS", System.getProperty("os.name"));
			report.setSystemInfo("Environment", "UAT");
			report.setSystemInfo("Build Number", "2.1.1");
			report.setSystemInfo("Browser", browserName);

			htmlReporter.config().setDocumentTitle("BookMyShow UI Automation Results");
			htmlReporter.config().setReportName("Test Report");
			htmlReporter.config().setTestViewChartLocation(ChartLocation.TOP);
			htmlReporter.config().setTimeStampFormat("MMM dd, yyyy HH:mm:ss");
		}

		return report;
	}
	
}
