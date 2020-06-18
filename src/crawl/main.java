package crawl;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
public class main {

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
		System.out.println("Hello");
		WebDriver driver= new FirefoxDriver();

        //Tạo đối tượng JavascriptExecutor
        JavascriptExecutor js = (JavascriptExecutor)driver;
//
        String csvFile = "result.csv";
        FileWriter writer = new FileWriter(csvFile);
//
        //Mở trang web
        driver.get("https://www.google.com/maps");
        driver.findElement(By.id("searchboxinput")).click();
        driver.findElement(By.id("searchboxinput")).clear();

        for(int i = 1; i < 51; i++) {
        	driver.findElement(By.id("searchboxinput")).click();
            driver.findElement(By.id("searchboxinput")).clear();
            driver.findElement(By.id("searchboxinput")).sendKeys( i + " Đặng Thái Thân Đà Nẵng");
            driver.findElement(By.id("searchbox-searchbutton")).click();
            TimeUnit.SECONDS.sleep(5);
            String browserUrl = (String) js.executeScript("return window.location.href");
            System.out.println("Your browser URL is " + browserUrl);
            try {
            	String[] extractString = browserUrl.substring(browserUrl.lastIndexOf("3d")+2).split("!4d", 2);
        		CSVUtils.writeLine(writer, Arrays.asList(String.valueOf(i), "Đặng Thái Thân Đà Nẵng", extractString[0].toString(), extractString[1].toString()));
            } catch (Exception e) {
				// TODO: handle exception
            	continue;
			}
        }
//
//// ==============================================================================
//		String text = "https://www.google.com/maps/place/9+Ng%C3%B4+V%C4%83n+S%E1%BB%9F,+Ho%C3%A0+Kh%C3%A1nh+Nam,+Li%C3%AAn+Chi%E1%BB%83u,+%C4%90%C3%A0+N%E1%BA%B5ng+550000,+Vietnam/@16.0689675,108.1487385,17z/data=!3m1!4b1!4m5!3m4!1s0x31421929c625948f:0x7066e12347fea96!8m2!3d16.0689675!4d108.1509272";
//		String[] text_1 = text.substring(text.lastIndexOf("3d")+2).split("!4d", 2);
//		System.out.println(text_1[1].toString());
//		CSVUtils.writeLine(writer, Arrays.asList("1 Ngô Văn Sở Đà Nẵng", text_1[0].toString(), text_1[1].toString()));

        writer.flush();
        writer.close();
        driver.close();

	}

}
