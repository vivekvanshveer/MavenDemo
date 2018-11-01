package SyncingProjectsTest;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.events.EventFiringWebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;

public class SyncingProjects {
	public static boolean isBrowserOpened=false;
	public static Properties CONFIG=null;
	public static WebDriver driver=null;
	public List<String> ListURLs = new ArrayList<String>();
	public static void openBrowser() throws IOException{
		CONFIG = new Properties();
        FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "//src//Config//Config.properties");
        CONFIG.load(ip);
		if(!isBrowserOpened){
			if(CONFIG.getProperty("browserType").equals("MOZILLA")){
				System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir")+"//geckodriver.exe");
				driver = new FirefoxDriver();

			}else if (CONFIG.getProperty("browserType").equals("IE")){
				System.setProperty("webdriveriver.ie.driveriver", System.getProperty("user.dir")+"//IEdriveriverServer.exe");
				
				driver = new InternetExplorerDriver();
			}else if (CONFIG.getProperty("browserType").equals("CHROME")){
				System.setProperty("webdriveriver.chrome.driveriver", System.getProperty("user.dir")+"//chromedriver.exe");
				driver = new ChromeDriver();
			}
			driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
			driver.manage().window().maximize();
		}
	}
	@BeforeSuite
	public void login() throws IOException{
		//openBrowser();
		 try {
			 	CONFIG = new Properties();
		        FileInputStream ip = new FileInputStream(System.getProperty("user.dir") + "//src//Config//Config.properties");
		        CONFIG.load(ip);
		        
			 	System.setProperty("webdriveriver.chrome.driveriver", System.getProperty("user.dir")+"//chromedriver.exe");
				driver = new ChromeDriver();
			 	driver.get(CONFIG.getProperty("URL"));			 	
	            driver.findElement(By.name(CONFIG.getProperty("uname_tf"))).sendKeys(CONFIG.getProperty("username"));
	            driver.findElement(By.name(CONFIG.getProperty("pwd_tf"))).sendKeys(CONFIG.getProperty("password"));
	            driver.findElement(By.name(CONFIG.getProperty("login_btn"))).click();
	            driver.manage().window().maximize();
	            driver.findElement(By.id(CONFIG.getProperty("closeDialoge"))).click();

	        } catch (Throwable t) {
	            System.out.println("Login Failed ");

	        }
	}

	
	@Test
	public void ProjectsTab(){
		System.out.println("-----------Test-----------");
		driver.findElement(By.linkText("Projects")).click();
		driver.findElement(By.name("go")).click();
		
		
		//rows
		List<WebElement> rowsList= driver.findElements(By.xpath("//div[@id='ext-gen11']/div"));
		List<WebElement> projectNameList=driver.findElements(By.xpath("//div[@id='ext-gen11']//td[4]/div/a"));
		
		for(int i=0;  i<=rowsList.size(); i++){
			for(WebElement projectNameElement : projectNameList){
				ListURLs.add(projectNameElement.getAttribute("href"));
				
				
			}
		}
		
		
		Iterator itr = ListURLs.iterator();
		for(int j=0 ; j<ListURLs.size(); j++){
			driver.navigate().to(ListURLs.get(j));
			System.out.println(ListURLs.get(j) +"Count" + j);
			//page load
			JavascriptExecutor js=(JavascriptExecutor)driver;
			String pageLoad=(String) js.executeScript("return document.readyState");
			
			if(pageLoad.equalsIgnoreCase("Complete")){
				
				WebElement syncToTeamworkBtnElement = driver.findElement(By.name("sync_to_teamwork"));
				WebDriverWait wait = new WebDriverWait(driver, 50);
				wait.until(ExpectedConditions.elementToBeClickable(syncToTeamworkBtnElement));
				syncToTeamworkBtnElement.click();
				driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
				
		}
			
		}
						
			
		
	}
	
}
	