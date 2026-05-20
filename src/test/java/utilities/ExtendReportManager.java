package utilities;

import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;

import testBase.BaseClass;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

public class ExtendReportManager extends BaseClass implements ITestListener{
	
	public ExtentSparkReporter sparkReporter; // UI of report
	public ExtentReports extend; // populate common info on the report
	public ExtentTest test; // creating test case entry in the report and update status of the test method
	String repName;
	public void onStart(ITestContext testContext) {
		
		
		
		String timeStamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date()); // generate time stamp
		repName = "Test-Report-" + timeStamp + ".html";
	    sparkReporter = new ExtentSparkReporter(".\\reports\\"+ repName);  // specify location of the report
		
		sparkReporter.config().setDocumentTitle("opencart Automation Report"); // Title of report
		sparkReporter.config().setReportName("opencart Functional Testing");   // name of the report
		sparkReporter.config().setTheme(Theme.DARK);
		
		
		extend = new ExtentReports();
		extend.attachReporter(sparkReporter);
		
		extend.setSystemInfo("Application","opencart");
		extend.setSystemInfo("Module","Admin");
		extend.setSystemInfo("Sub Module","Customers");
		extend.setSystemInfo("User Name", System.getProperty("user.name"));
		extend.setSystemInfo("Environment","QA");
		
		String os = testContext.getCurrentXmlTest().getParameter("os");
		extend.setSystemInfo("Operating System", os);
		
		String browser = testContext.getCurrentXmlTest().getParameter("browser");
		extend.setSystemInfo("Browser", browser);
		
		List<String> includedGroups = testContext.getCurrentXmlTest().getIncludedGroups();
		if(!includedGroups.isEmpty()) {
			extend.setSystemInfo("Groups", includedGroups.toString());
			
		}
		
		
	}
	
	public void onTestSuccess(ITestResult result) {
		test = extend.createTest(result.getTestClass().getName()); // create a new entry in the report
		test.assignCategory(result.getMethod().getGroups()); // to display groups in report
		test.log(Status.PASS, result.getName() +"got successfully executed");
	}
	
	public void onTestFailure(ITestResult result) {
	    test = extend.createTest(result.getTestClass().getName());
	    test.assignCategory(result.getMethod().getGroups());

	    test.log(Status.FAIL, result.getName() + " Test case failed");
	    test.log(Status.INFO, result.getThrowable());

	    try {
	        String imgPath = captureScreen(result.getName());

	        if (imgPath != null) {
	            test.addScreenCaptureFromPath(imgPath);
	        }

	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	}
	
	@Override
	public void onTestSkipped(ITestResult result) {
	    test = extend.createTest(result.getTestClass().getName());
	    test.assignCategory(result.getMethod().getGroups());
	    test.log(Status.SKIP, result.getName() + " got SKIPPED ");
	    test.log(Status.INFO, result.getThrowable().getMessage());
	}
	
	@Override
	public void onFinish(ITestContext context) {
	    
	    // Write all test information to the Extent Report
	    extend.flush();

	    // Get the full path of the generated report
	    String pathOfExtendReport = System.getProperty("user.dir")
	            + File.separator
	            + "reports"
	            + File.separator
	            + repName;

	    File extendReport = new File(pathOfExtendReport);

	    // Print report location in console
	    System.out.println("Extent Report generated at: " + extendReport.getAbsolutePath());

	    /*
	     * Do NOT open the report automatically in Jenkins.
	     * Jenkins runs as a background service and cannot launch a browser.
	     *
	     * If you want to open the report automatically when running locally,
	     * uncomment the code below.
	     */

	    /*
	    try {
	        if (Desktop.isDesktopSupported() && extendReport.exists()) {
	            Desktop.getDesktop().browse(extendReport.toURI());
	        }
	    } catch (IOException e) {
	        e.printStackTrace();
	    }
	    */
	}
	

}
