package phudh.test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;



class CountApprovedItems {
		WebDriver driver;
          @Test
          public void countApprovedItems() throws InterruptedException {
        	  connectDriver();
              driver.get("http://ktvn-test.s3-website.us-east-1.amazonaws.com/ ");
              login("admin@test.com/","test123");
              assertEquals(6, countItemByStatus("Approved"));
              driver.close();       
          }
 
          public void waitEmlement(String xpath) throws InterruptedException
          {
        	  WebElement ele = null;
        	  do
        	  {
        		  try {
        			  ele = driver.findElement(By.xpath(xpath));        			   
        		  }
        		  catch(org.openqa.selenium.NoSuchElementException ex) {
        			  ele = null;
        			  Thread.sleep(100);
        		  }
        	  }
        	  while(ele == null);
          }
          public int countItemByStatus(String status)
          {
	        	List<WebElement> lstEle = driver.findElements(By.xpath("//table[@class='table table-striped table-bordered table-hover table-condensed table-body']/tbody/tr")); 
	          	int total = lstEle.size();
	          	WebElement ele = null;
	          	int countItems = 0;
	          	for (int i=1; i<=total; i++)  
	            { 
	          		try
	          		{
	          			ele = driver.findElement(By.xpath("//table[@class='table table-striped table-bordered table-hover table-condensed table-body']/tbody/tr[" + i + "]/td[2]/div/p[contains(text(),'"+ status + "')]"));
	          		}
	          		catch(org.openqa.selenium.NoSuchElementException ex) {ele = null;}
	          		if (ele != null) countItems++;
	            } 
	          	return countItems;
          }
          
          public void connectDriver()
          {
        	  System.setProperty("webdriver.chrome.driver", "/Users/danghongphu/Downloads/chromedriver");
              driver = new ChromeDriver();
          }
          
          public void login(String username, String password) throws InterruptedException
          {
	        	  WebElement ele = driver.findElement(By.xpath("//input[@name='email']"));
	        	  ele.sendKeys(username);
	        	  ele = driver.findElement(By.xpath("//input[@name='password']"));
	        	  ele.sendKeys(password);
	        	  ele = driver.findElement(By.xpath("//a[text()='LOG IN']"));
	        	  ele.click();
	        	  waitEmlement("//table[@class='table table-hover table-bordered table-condensed table-header']");
	        	  //waitForLoad();
          }
          
          public void waitForLoad() {
              ExpectedCondition<Boolean> pageLoadCondition = new
                      ExpectedCondition<Boolean>() {
                          public Boolean apply(WebDriver driver) {
                              return ((JavascriptExecutor)driver).executeScript("return document.readyState").equals("complete");
                          }
                      };
              WebDriverWait wait = new WebDriverWait(driver, 30);
              wait.until(pageLoadCondition);
          }
}