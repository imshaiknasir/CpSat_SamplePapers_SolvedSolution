package testCases;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import pageObjects.MapPageObjects;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class LocationSearch {

	WebDriver driver;
	WebDriverWait wait;
	Properties prop;
	InputStream propFile;
	Screenshot screenshot;
	
	
	@Before
	public void beforetheTest() throws IOException {
		driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 30);
		driver.manage().window().maximize();
		prop= new Properties();
		propFile = new FileInputStream(System.getProperty("user.dir") + "\\resources\\prop.properties");
		prop.load(propFile);
		
	}
	
	@Test
	public void testexecute() throws IOException {
		driver.get(prop.getProperty("url"));
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[@id='searchboxinput']"))));
		driver.findElement(By.xpath("//input[@id='searchboxinput']")).sendKeys(prop.getProperty("desitnationLocation"));
		driver.findElement(By.xpath("//input[@id='searchboxinput']")).sendKeys(Keys.RETURN);
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//div[contains(@class, 'section-hero')]//div//h1"))));
		System.out.println("You've searched for: " + driver.findElement(By.xpath("//div[contains(@class, 'section-hero')]//div//h1")).getText());
		
		screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
		ImageIO.write(screenshot.getImage(), "png" , new File(".\\resources\\fullimage.png"));
		
		driver.findElement(By.xpath("//button[contains(@class,'iRxY3GoUYUY__button')][@aria-label='Directions']")).click();
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//input[contains(@aria-label,'Choose starting point')]"))));
		driver.findElement(By.xpath("//input[contains(@aria-label,'Choose starting point')]")).sendKeys(prop.getProperty("sourceLocation"));

		driver.findElement(By.xpath("//input[contains(@aria-label,'Starting point')]")).sendKeys(Keys.ENTER);
		
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1[@id='section-directions-trip-title-0']"))));
		System.out.println("first option distance: " + driver.findElement(By.xpath("//h1[@id='section-directions-trip-title-0']")).getText());
	}
	
	@After
	public void afterTest() {
		driver.close();
	}
	
}
