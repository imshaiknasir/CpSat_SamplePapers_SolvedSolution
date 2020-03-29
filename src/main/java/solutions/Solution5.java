package solutions;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.imageio.ImageIO;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;

public class Solution5 {
	
	/*
	 * CP-SAT MOCK Exam V2.0 QUESTION SOLUTIONS – 5
	 */
	
	WebDriver driver;
	WebDriverWait wait;
	Properties prop;
	InputStream fileInput;
	Screenshot screenshot;
	
	
	@Before
	public void beforeTest() throws IOException {
		driver = new FirefoxDriver();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		wait = new WebDriverWait(driver, 30);
		prop = new Properties();
		fileInput = new FileInputStream(System.getProperty("user.dir")+ "\\resources\\prop.properties");
		prop.load(fileInput);
	}

	@Test
	public void mainimplmentation() throws IOException {
		/*
		 * Open https://letterboxd.com
		 */
		driver.get(prop.getProperty("url"));
		
		/*
		 * Verify the page heading contains ‘Letterboxd’.
		 */
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//h1[@class='site-logo']//a"))));
		String headerContent = driver.findElement(By.xpath("//h1[@class='site-logo']//a")).getText();
		
		boolean containsHder = headerContent.contains("Letterboxd");
		if (containsHder == true ) {
			System.out.println("The header contains Letterboxd");
		}else {
			System.out.println("Issues with heared.");
		}
		
		/*
		 * Click on ‘PEOPLE’ from the top menu and capture a screenshot of the resulting page.
		 */
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.xpath("//a[@href='/people/']"))));
		driver.findElement(By.xpath("//a[@href='/people/']")).click();
		wait.until(ExpectedConditions.visibilityOf(driver.findElement(By.linkText("View more"))));
		screenshot = new AShot().shootingStrategy(ShootingStrategies.viewportPasting(100)).takeScreenshot(driver);
		ImageIO.write(screenshot.getImage(), "png", new File(".\\resources\\Letterboxd.png"));
		
		/*
		 * Print the names of the reviewers under the ‘Popular this week’ section
		 */
		List<WebElement> popularThisWeekNames = driver.findElements(By.xpath("//h3[contains(@class,'title')]"));
		for (int i = 0; i < popularThisWeekNames.size()-1; i++) {
			System.out.println(popularThisWeekNames.get(i).getText());
		}
		
		/*
		 * In the ‘Popular this week’ section, print the total number of reviews that the first reviewer has.
		 */
		System.out.println("The total number of reviews that the first reviewer has : " + driver.findElement(By.xpath("(//a[contains(@href,'/films/reviews/')])[1]")).getText());
		
	}
	
	
	@After
	public void afterTest() {
		driver.close();
	}
	
}
