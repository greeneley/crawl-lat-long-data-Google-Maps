package crawl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class test {
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		WebDriver driver = new FirefoxDriver();
		String csvFile = "input_2.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = "\",";
        // ============================
        String nameOutput = csvFile.replace(".csv", "");
        String csvExport = nameOutput + "_result_" + Calendar.getInstance().getTimeInMillis() + ".csv";
        BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvExport), StandardCharsets.UTF_8));
        try {
            br = new BufferedReader(
                new InputStreamReader(
                    new FileInputStream(csvFile), "UTF8"));
            line = br.readLine();

        while ((line = br.readLine()) != null) {
            String[] element = line.split(cvsSplitBy);
            String gglink = element[5].replace("\"", "");

            System.out.println(element[5].replace("\"", ""));
            driver.get(gglink);
            String newUrl = driver.getCurrentUrl();
            do {
            	Thread.sleep(5);
            	newUrl = driver.getCurrentUrl();
            } while(newUrl.contentEquals(gglink));
    		Boolean isPresent = waitForElementBoolean(driver, By.xpath("//button[normalize-space(@jsaction)=\"pane.rating.category\"][normalize-space(@class)=\"widget-pane-link\"]"));
            String type = "";
        	System.out.println(isPresent);
        	if(isPresent) {
        		System.out.println(driver.findElement(By.xpath("//button[normalize-space(@jsaction)=\"pane.rating.category\"][normalize-space(@class)=\"widget-pane-link\"]")).getText());
                type = driver.findElement(By.xpath("//button[normalize-space(@jsaction)=\"pane.rating.category\"][normalize-space(@class)=\"widget-pane-link\"]")).getText();
        	}
        	CSVUtils.writeLine(writer, Arrays.asList(element[0].replace("\"", ""), element[1].replace("\"", ""), gglink , type),',', '"');
            writer.flush();

            }
        driver.quit();
        writer.close();

	}catch (FileNotFoundException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
	}
	public static boolean waitForElementBoolean(WebDriver driver, By object){
	    try {
	        WebDriverWait wait = new WebDriverWait(driver,5);
	        wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(object));
	        return true;
	    } catch (Exception e) {
	        System.out.println(e.getMessage());
	        return false;
	    }
	}
}
