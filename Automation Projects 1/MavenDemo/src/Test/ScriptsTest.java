package Test;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class ScriptsTest {

	@Test
	public void init(){
		System.out.println("Init function is run");
	}
	
	private WebDriver driver;		
	@Test				
	public void testEasy() {	
		driver.get("http://www.guru99.com/selenium-tutorial.html");  
		String title = driver.getTitle();				 
		Assert.assertTrue(title.contains("Free Selenium Tutorials")); 		
	}	
	@BeforeTest
	public void beforeTest() {	
		System.setProperty("webdriver.chrome.driver", System.getProperty("user.dir") + "//chromedriver.exe");
	    driver = new ChromeDriver();  
	}		
	@AfterTest
	public void afterTest() {
		driver.quit();			
	}		
}
