package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BasePage {

	public HomePage(WebDriver driver) {
		super(driver);
	}
	
	@FindBy(xpath="//span[normalize-space()='My Account']")
	WebElement lnMyacount;
	@FindBy(xpath="//a[normalize-space()='Register']")
	WebElement lnkRegister;
	
	@FindBy(xpath="//a[normalize-space()='Login']") // login link add in 5th step
	WebElement linkLogin;
	
	public void clikMyAccount() {
		lnMyacount.click();
	}
	
	public void clickRegister() {
		lnkRegister.click();
	}

	public void clickLogin() {
		linkLogin.click();
	}
	
}
