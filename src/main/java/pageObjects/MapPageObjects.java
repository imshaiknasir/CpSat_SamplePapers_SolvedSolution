package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class MapPageObjects {

	WebDriver driver;
	
	public MapPageObjects(WebDriver driver) {
		this.driver= driver;
		PageFactory.initElements(driver, this);
	}
	
	@FindBy(xpath="//input[@id='searchboxinput']")
	WebElement seachBar;
	
	@FindBy(xpath="//button[contains(@class,'iRxY3GoUYUY__button')][@aria-label='Directions']")
	WebElement directionButton;
	
	public WebElement directionBtn() {
		return directionButton;
	}
	
	public WebElement searchbar() {
		return seachBar;
	}
}
