package com.test.utility;

import java.io.File;

import org.testng.ITestResult;
import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeTest;

import com.relevantcodes.extentreports.ExtentReports;
import com.relevantcodes.extentreports.ExtentTest;
import com.relevantcodes.extentreports.LogStatus;

public class TestBase {
	
	public static ExtentReports extent;
	public static ExtentTest logger;
	
	
	@BeforeTest
	public void startReport() {		

		extent = new ExtentReports(System.getProperty("user.dir") + "/test-output/STMExtentReport.html", true);
		extent.addSystemInfo("Host Name", "REST API Exploratory").addSystemInfo("Environment", "REST API Automation Testing")
				.addSystemInfo("User Name", "test user System");
		extent.loadConfig(new File(System.getProperty("user.dir") + "\\extent-config.xml"));		
		
	}
	
	@AfterMethod
	public void getResult(ITestResult result) {
		System.out.println("After Method");
		
		if (result.getStatus() == ITestResult.FAILURE) {
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getName());
			logger.log(LogStatus.FAIL, "Test Case Failed is " + result.getThrowable());
		} else if (result.getStatus() == ITestResult.SKIP) {
			logger.log(LogStatus.SKIP, "Test Case Skipped is " + result.getName());
		}

		extent.endTest(logger);
	}

	@AfterTest
	public void endReport() {

		extent.flush();
		extent.close();
	}

}
