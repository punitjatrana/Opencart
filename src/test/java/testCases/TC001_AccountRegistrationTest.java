package testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import pageObjects.AccountRegistrationPage;
import pageObjects.HomePage;
import testBase.BaseClass;

public class TC001_AccountRegistrationTest extends BaseClass {
	
	
	
	@Test(groups={"sanity","master"})
	public void verify_account_registration() {
		logger.info("***** Started TC001_AccountRegistrationTest *****");
		try {
		HomePage hp = new HomePage(driver);
		hp.clikMyAccount();
		logger.info("Click on My Account Link");
		
		hp.clickRegister();
		logger.info("Click on register Link");
		
		AccountRegistrationPage regpage = new AccountRegistrationPage(driver);
		logger.info("provinding customer details...");
		regpage.setFirstName(randomeString().toUpperCase());
		regpage.setLastName(randomeString().toUpperCase());
		regpage.setEmail(randomeString()+"@gmail.com");
		regpage.setTelephone(randomNumber());
		
		String password = randomeAlphaNumeric();
		
		regpage.setPassword(password);
		regpage.setConfirmPassword(password);
		regpage.setPrivacyPollicy();
		regpage.clickContinue();
		
		logger.info("Validating expected message...");
		String confmsg=regpage.getonfirmationMsg();
		
		if(confmsg.equals("Your Account Has Been Created!")) {
			Assert.assertTrue(true);
		}else {
			logger.error("Test Failed..");
			logger.debug("Debug logs..");
			Assert.assertTrue(false);
		}
		//Assert.assertEquals(confmsg, "Your Account Has Been Created!");
		}
		catch(Exception e) {
			
			Assert.fail();
		}
		logger.info("***** Finished TC001_AccountRegistrationTest *****");
	}
	
}
