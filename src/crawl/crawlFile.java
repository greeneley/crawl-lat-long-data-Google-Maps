package crawl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.Charset;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Calendar;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class crawlFile {
	private static String numberAddress;


	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub

		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") +"\\geckodriver.exe");
		String csvFile = "input.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        // ============================

        String csvExport = Calendar.getInstance().getTimeInMillis() + "_result.csv";
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvExport), StandardCharsets.UTF_8));
//        driver.get("https://www.google.com/maps");
        try {
        	br = new BufferedReader(
        			   new InputStreamReader(
        	                      new FileInputStream(csvFile), "UTF8"));
            line = br.readLine();
            while ((line = br.readLine()) != null) {

            	// =================================================
            	WebDriver driver = new FirefoxDriver();
                JavascriptExecutor js = (JavascriptExecutor)driver;
            	driver.get("https://www.google.com/maps");
                TimeUnit.SECONDS.sleep(5);
                driver.findElement(By.id("searchboxinput")).click();
                driver.findElement(By.id("searchboxinput")).clear();
            	// =================================================

                // use comma as separator
                String[] element = line.split(cvsSplitBy);

                for(int i = 1; i < Integer.valueOf(element[2]) + 1; i++) {

                	// =================================================
                	driver.findElement(By.id("searchboxinput")).click();
                    driver.findElement(By.id("searchboxinput")).clear();
                    driver.findElement(By.id("searchboxinput")).sendKeys(i + " " + element[0] + " " + element[1]);
                    driver.findElement(By.id("searchbox-searchbutton")).click();
                    TimeUnit.MILLISECONDS.sleep(1500);
                	// =================================================

                    String browserUrl = (String) js.executeScript("return decodeURIComponent(window.location.href)");
                    System.out.println("Your browser URL is " + browserUrl);
                    try {
                    	String[] extractString = browserUrl.substring(browserUrl.lastIndexOf("3d")+2).split("!4d", 2);
                    	String address = browserUrl.replaceAll("^.*place/|55000.*$", "").replaceAll("\\+", " ");
                		String regex = "([0-9]+.*[0-9]+|[0-9]+?)";
                		Pattern pattern = Pattern.compile(regex);
                		Matcher matcher = pattern.matcher(address);
//                		String numberAddress = null;
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
                driver.close();


            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }  finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        writer.close();
	}

}
