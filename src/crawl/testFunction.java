package crawl;
import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
public class testFunction {

	private static String numberAddress;

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub

		WebDriver driver= new FirefoxDriver();
        JavascriptExecutor js = (JavascriptExecutor)driver;
        String csvFile = java.time.LocalDateTime.now() + "_result.csv";
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), StandardCharsets.UTF_8));
        driver.get("https://www.google.com/maps");
        TimeUnit.SECONDS.sleep(5);
        driver.findElement(By.id("searchboxinput")).click();
        driver.findElement(By.id("searchboxinput")).clear();

        for(int i = 1; i < 5; i++) {
        	driver.findElement(By.id("searchboxinput")).click();
            driver.findElement(By.id("searchboxinput")).clear();
            driver.findElement(By.id("searchboxinput")).sendKeys( i + " Nguyễn Văn Linh Đà Nẵng");
            driver.findElement(By.id("searchbox-searchbutton")).click();
            TimeUnit.SECONDS.sleep(1);
            String browserUrl = (String) js.executeScript("return decodeURIComponent(window.location.href)");
            System.out.println("Your browser URL is " + browserUrl);
            try {
            	String[] extractString = browserUrl.substring(browserUrl.lastIndexOf("3d")+2).split("!4d", 2);
        		String address = browserUrl.replaceAll("^.*place/|55000.*$", "").replaceAll("\\+", " ");
        		String regex = "([0-9]+.*[0-9]+|[0-9]+?)";
        		Pattern pattern = Pattern.compile(regex);
        		Matcher matcher = pattern.matcher(address);
        		while(matcher.find()) {
        			numberAddress = matcher.group();
        		}
        		address = address.replaceAll(regex, "");
            	CSVUtils.writeLine(writer, Arrays.asList(numberAddress, address, extractString[0].toString(), extractString[1].toString()));
        		writer.flush();

            } catch (Exception e) {
				// TODO: handle exception
            	continue;
			}
        }
        writer.close();
        driver.close();

	}

}
