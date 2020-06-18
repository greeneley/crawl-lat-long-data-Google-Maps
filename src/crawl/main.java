package crawl;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
public class main {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub

		WebDriver driver= new FirefoxDriver();

        //Tạo đối tượng JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor)driver;

        //Mở trang web
        driver.get("https://www.google.com/maps");
        driver.findElement(By.id("searchboxinput")).click();
        driver.findElement(By.id("searchboxinput")).clear();
        driver.findElement(By.id("searchboxinput")).sendKeys("1 Ngô Văn Sở Đà Nẵng");
        driver.findElement(By.id("searchbox-searchbutton")).click();
        TimeUnit.SECONDS.sleep(5);
        String browserUrl = (String) js.executeScript("return window.location.href");
        System.out.println("Your browser URL is " + browserUrl);

//        driver.findElement(By.id("searchboxinput")).clear();
//        driver.findElement(By.id("searchboxinput")).sendKeys("2 Ngô Văn Sở Đà Nẵng");
//        driver.findElement(By.id("searchbox-searchbutton")).click();
//        TimeUnit.SECONDS.sleep(5);
//        String browserUrl2 = (String) js.executeScript("return window.location.href");
//        System.out.println("Your browser URL is " + browserUrl2);
//
//        driver.findElement(By.id("searchboxinput")).clear();
//        driver.findElement(By.id("searchboxinput")).sendKeys("3 Ngô Văn Sở Đà Nẵng");
//        driver.findElement(By.id("searchbox-searchbutton")).click();
//        TimeUnit.SECONDS.sleep(5);
//        String browserUrl3 = (String) js.executeScript("return window.location.href");
//        System.out.println("Your browser URL is " + browserUrl3);
//
//        driver.findElement(By.id("searchboxinput")).clear();
//        driver.findElement(By.id("searchboxinput")).sendKeys("4 Ngô Văn Sở Đà Nẵng");
//        driver.findElement(By.id("searchbox-searchbutton")).click();
//        TimeUnit.SECONDS.sleep(5);
//        String browserUrl4 = (String) js.executeScript("return window.location.href");
//        System.out.println("Your browser URL is " + browserUrl4);
//
//        driver.findElement(By.id("searchboxinput")).clear();
//        driver.findElement(By.id("searchboxinput")).sendKeys("5 Ngô Văn Sở Đà Nẵng");
//        driver.findElement(By.id("searchbox-searchbutton")).click();
//        TimeUnit.SECONDS.sleep(5);
//        String browserUrl5 = (String) js.executeScript("return window.location.href");
//        System.out.println("Your browser URL is " + browserUrl5);
//
//        driver.findElement(By.id("searchboxinput")).clear();
//        driver.findElement(By.id("searchboxinput")).sendKeys("6 Ngô Văn Sở Đà Nẵng");
//        driver.findElement(By.id("searchbox-searchbutton")).click();
//        TimeUnit.SECONDS.sleep(5);
//        String browserUrl6 = (String) js.executeScript("return window.location.href");
//        System.out.println("Your browser URL is " + browserUrl6);
//
//        driver.findElement(By.id("searchboxinput")).clear();
//        driver.findElement(By.id("searchboxinput")).sendKeys("7 Ngô Văn Sở Đà Nẵng");
//        driver.findElement(By.id("searchbox-searchbutton")).click();
//        TimeUnit.SECONDS.sleep(5);
//        String browserUrl7 = (String) js.executeScript("return window.location.href");
//        System.out.println("Your browser URL is " + browserUrl7);
	}

}
