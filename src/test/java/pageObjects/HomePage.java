package pageObjects;


import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;


public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//span[normalize-space()='My Account']")
	WebElement lnkMyAccount;
	@FindBy(xpath="//a[normalize-space()='Register']")
	WebElement lnkRegister;
	
	@FindBy(xpath="//a[normalize-space()='Login']") // login link add in 5th step
	WebElement linkLogin;
	
	public void clikMyAccount() {
	    try {
	        // Wait for page to load completely
	        Thread.sleep(3000);

	        // Scroll to the element
	        JavascriptExecutor js = (JavascriptExecutor) driver;
	        js.executeScript("arguments[0].scrollIntoView(true);", lnkMyAccount);

	        // Small pause after scrolling
	        Thread.sleep(1000);

	        // Click using JavaScript
	        js.executeScript("arguments[0].click();", lnkMyAccount);

	    } catch (Exception e) {
	        System.out.println("Unable to click My Account: " + e.getMessage());
	        throw new RuntimeException(e);
	    }
	}
	
	public void clickRegister() {
		lnkRegister.click();
	}

	public void clickLogin() {
		linkLogin.click();
	}
	
}
