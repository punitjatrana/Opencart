package testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager; // log4j2
import org.apache.logging.log4j.Logger;    // log4j2
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Parameters;

public class BaseClass {

	public WebDriver driver;
    public Logger logger;
    public Properties p;

    @BeforeClass(groups = { "regression", "master" })
    @Parameters({ "os", "browser" })
    public void setup(String os, String br) throws IOException {

        // Loading config.properties file
        FileInputStream file = new FileInputStream("./src/test/resources/config.properties");
        p = new Properties();
        p.load(file);

        // Initialize logger
        logger = LogManager.getLogger(this.getClass());

        // =========================
        // Remote Execution (Grid)
        // =========================
        if (p.getProperty("execute_env").equalsIgnoreCase("remote")) {

            // Convert OS string to Platform
            Platform platform;

            if (os.equalsIgnoreCase("window")) {
                platform = Platform.WIN11;
            } else if (os.equalsIgnoreCase("mac")) {
                platform = Platform.MAC;
            } else if (os.equalsIgnoreCase("linux")) {
                platform = Platform.LINUX;
            } else {
                throw new IllegalArgumentException("No matching OS: " + os);
            }

            // Browser selection using Options (Selenium 4 recommended approach)
            switch (br.toLowerCase()) {

                case "chrome":
                	ChromeOptions chromeOptions = new ChromeOptions();
                	chromeOptions.setPlatformName(platform.name());

                	driver = new RemoteWebDriver(
                	        java.net.URI.create("http://localhost:4444").toURL(),
                	        chromeOptions);
                    break;

                case "firefox":
                	FirefoxOptions firefoxOptions = new FirefoxOptions();
                	firefoxOptions.setPlatformName(platform.name());

                	driver = new RemoteWebDriver(
                	        java.net.URI.create("http://localhost:4444").toURL(),
                	        firefoxOptions);
                    break;

                case "edge":
                    EdgeOptions edgeOptions = new EdgeOptions();
                    edgeOptions.setPlatformName(platform.name());

                    driver = new RemoteWebDriver(
                            java.net.URI.create("http://localhost:4444").toURL(),
                            edgeOptions);
                    break;

                default:
                    throw new IllegalArgumentException("No matching browser: " + br);
            }
        }

        // =========================
        // Local Execution
        // =========================
        else if (p.getProperty("execute_env").equalsIgnoreCase("local")) {

            switch (br.toLowerCase()) {
                case "chrome":
                    driver = new ChromeDriver();
                    break;

                case "firefox":
                    driver = new FirefoxDriver();
                    break;

                case "edge":
                    driver = new EdgeDriver();
                    break;

                default:
                    throw new IllegalArgumentException("Invalid browser name: " + br);
            }
        }

        // =========================
        // Common Browser Settings
        // =========================
        driver.manage().deleteAllCookies();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        driver.manage().window().maximize();
        driver.get(p.getProperty("appURL"));
    }
    @AfterClass(alwaysRun = true, groups = {"regression", "master"})
    public void tearDown() {
        try {
            if (driver != null) {
                driver.quit();
            }
        } catch (Exception e) {
            System.out.println("Error while closing browser: " + e.getMessage());
        } finally {
            driver = null;
        }
    }
    public String randomeString() {
        return RandomStringUtils.randomAlphabetic(5);
    }

    public String randomNumber() {
        return RandomStringUtils.randomNumeric(10);
    }

    public String randomeAlphaNumeric() {
        String generatedString = RandomStringUtils.randomAlphabetic(5);
        String generatedNumber = RandomStringUtils.randomNumeric(10);
        return generatedString + "@" + generatedNumber;
    }

    public String captureScreen(String tname) throws IOException {

        // Check if driver is initialized
        if (driver == null) {
            System.out.println("Driver is null. Screenshot cannot be captured.");
            return null;
        }

        String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());

        TakesScreenshot takeScreenShot = (TakesScreenshot) driver;
        File sourceFile = takeScreenShot.getScreenshotAs(OutputType.FILE);

        // Create screenshots directory if it does not exist
        String screenshotsDir = System.getProperty("user.dir") + File.separator + "screenshots";
        File dir = new File(screenshotsDir);
        if (!dir.exists()) {
            dir.mkdirs();
        }

        String targetFilePath = screenshotsDir
                + File.separator
                + tname + "_"
                + timeStamp
                + ".png";

        File targetFile = new File(targetFilePath);

        FileUtils.copyFile(sourceFile, targetFile);

        return targetFilePath;
    }
}