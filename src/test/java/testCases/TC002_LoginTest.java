package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;
import testBase.BaseClass;

public class TC002_LoginTest extends BaseClass {

    @Test(groups = {"regression", "master"})
    public void verify_login() {
        logger.info("**** Starting TC002_LoginTest ****");

        try {
            // Home Page
            HomePage hp = new HomePage(driver);
            hp.clikMyAccount();
            hp.clickLogin();

            // Login Page
            LoginPage lp = new LoginPage(driver);
            lp.setEmail(p.getProperty("email"));
            lp.setPassword(p.getProperty("password"));
            lp.clickLogin();

            // My Account Page Validation
            MyAccountPage macc = new MyAccountPage(driver);
            boolean targetPage = macc.isMyAccountPageExists();

            Assert.assertTrue(
                    targetPage,
                    "Login failed: My Account page was not displayed after login."
            );

            logger.info("Login test passed successfully.");

        } catch (Exception e) {
            logger.error("Test failed due to exception: " + e.getMessage(), e);
            Assert.fail("Test failed due to exception: " + e.getMessage());
        }

        logger.info("**** Finished TC002_LoginTest ****");
    }
}