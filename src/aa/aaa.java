package testngpackage;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.Cookie;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;
import org.testng.Assert;
import org.testng.annotations.*;

public class BasicCourseTestNG {
		
	public static String url = "http://10.0.1.86/";
	public static WebDriver driver;
	public static JavascriptExecutor js;
	
	@BeforeClass
	public void loadDrivers(){
		driver = new FirefoxDriver();
		driver.get(url);
		js = (JavascriptExecutor) driver;
		
	}

	@Test
	public void Test_01HomePageTitle()
	{
		String expectedTitle = "TAP Utility Server";
		String actualTitle = (String) js.executeScript("return document.title");
		Assert.assertEquals(expectedTitle , actualTitle);
		
	}

	@Test
	public void Test_02click_Tatoc_Link()
	{
		
		js.executeScript("document.getElementsByTagName('a')[5].click()");
		String expectedTitle = "Welcome - T.A.T.O.C";
		String actualTitle = (String) js.executeScript("return document.title");
		Assert.assertEquals(expectedTitle , actualTitle);
		
	}
	
	@Test(dependsOnMethods = {"Test_02click_Tatoc_Link"})
	public void Test_03clickBasicCourse()
	{
		js.executeScript("document.getElementsByTagName('a')[0].click()");
		String expectedTitle = "Grid Gate - Basic Course - T.A.T.O.C";
		String actualTitle = (String) js.executeScript("return document.title");
		Assert.assertEquals(expectedTitle , actualTitle);
	}
	
	@Test (dependsOnMethods = {"Test_03clickBasicCourse"})
	public void Test_04gridGate()
	{
		js.executeScript("document.querySelector('.greenbox').click();");	
		String expectedTitle = "Frame Dungeon - Basic Course - T.A.T.O.C";
		String actualTitle = (String) js.executeScript("return document.title");
		Assert.assertEquals(expectedTitle , actualTitle);
		
	}
	
	@Test (dependsOnMethods = {"Test_04gridGate"})
	public void Test_05boxColorChange() throws InterruptedException{
		String color1 = (String) js.executeScript("return document.querySelector('#main').contentWindow.document.querySelector('#answer').className;");
		System.out.println(color1);
		String color2 = (String) js.executeScript("return document.querySelector('#main').contentWindow.document.querySelector('#child').contentWindow.document.getElementById('answer').getAttribute('class');");
		while(!color1.equals(color2))
		{
			js.executeScript("document.getElementById('main').contentWindow.document.getElementsByTagName('a')[0].click();");
			Thread.sleep(3000);
			color2=(String)js.executeScript("return document.querySelector('#main').contentWindow.document.querySelector('#child').contentWindow.document.getElementById('answer').getAttribute('class');");
		}		
		js.executeScript("document.getElementById('main').contentWindow.document.getElementsByTagName('a')[1].click();");
	}
	
	@Test (dependsOnMethods = {"Test_05boxColorChange"})
	public void Test_06dragAndDrop(){
		js.executeScript("document.getElementById('dragbox').setAttribute('style','position: relative; left: 32px; top: -59px;')");
		js.executeScript("document.getElementsByTagName('a')[0].click()");
		
	}
	
	@Test
	public void Test_07launchWindow() throws InterruptedException
	{
		String handle = driver.getWindowHandle();
		
		js.executeScript("document.getElementsByTagName('a')[0].click();");	
		for(String webhand: driver.getWindowHandles())
		{
			driver.switchTo().window(webhand);
		}
		Thread.sleep(2500);
		js.executeScript("return document.getElementById('name').setAttribute('value','abc')");
		Thread.sleep(3000);
		js.executeScript("document.getElementById('submit').click();");
		driver.switchTo().window(handle);
		js.executeScript("document.getElementsByTagName('a')[1].click();");
		
	}
	
	@Test
	public void Test_08generateCookies()
	{
		js.executeScript("document.querySelector('a[onclick^=generateToken]').click();");
		String token = (String) js.executeScript("return document.querySelector('#token').innerHTML;");
		token = token.substring(7);	
		js.executeScript("document.cookie = " +token);
		js.executeScript("document.querySelector('a[onclick^=gonext]').click();");
		
	}
	
	@AfterClass
	public void unloadDriver(){
		driver.quit();
	}
	
	  
  }

