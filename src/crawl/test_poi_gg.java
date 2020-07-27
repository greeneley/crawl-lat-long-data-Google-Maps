package crawl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.Calendar;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class test_poi_gg {
	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		/**
		 * Get file geckodriver.exe để khởi tạo trình duyệt firefox Tạo 1 file
		 * geckodriver_time_current.exe Rồi khởi chạy file đó
		 */

		File nameDriver = new File("geckodriver.exe");
		String nameDriverRunning = "geckodriver_" + Calendar.getInstance().getTimeInMillis() + ".exe";
		Path getPathCurrentDriverRunning = Paths.get(nameDriverRunning);
		Path getPathDriver = nameDriver.toPath();
		Files.copy(getPathDriver, getPathCurrentDriverRunning, StandardCopyOption.REPLACE_EXISTING);

		/**
		 * Set file geckodriver_time_current.exe vào chương trình để khởi chạy @arg[0]:
		 * tên file csv cần crawl
		 */

		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") + "\\" + nameDriverRunning);

		String fileInput = null;
		if (args.length == 0) {
			System.out.println("Don't input a crawl file format .csv \nDefault: input_2.csv");
			fileInput = "input_2.csv";
		} else {
			fileInput = args[0];
		}

		String line = "";
		String cvsSplitBy = "\",";
		String nameOutput = fileInput.replace(".csv", "");
		String fileOutput = nameOutput + "_result_" + Calendar.getInstance().getTimeInMillis() + ".csv";

		BufferedReader bufferedReader = null;
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(fileOutput), StandardCharsets.UTF_8));

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileInput), "UTF8"));
			line = bufferedReader.readLine();
			int count = 1;
			WebDriver driver = null;
			while ((line = bufferedReader.readLine()) != null) {

				String[] subLine = line.split(cvsSplitBy);
				String id = subLine[0].replace("\"", "");
				String name = subLine[1].replace("\"", "");
				String gglink = subLine[5].replace("\"", "");

				System.out.println(subLine[5].replace("\"", ""));

				if (count == 1) {
					driver = new FirefoxDriver();

					driver.get(gglink);
					String newUrl = driver.getCurrentUrl();
					do {
						Thread.sleep(5);
						newUrl = driver.getCurrentUrl();
					} while (newUrl.contentEquals(gglink));

				}

				Boolean isPresent = waitForElementBoolean(driver, By.xpath(
						"//button[normalize-space(@jsaction)=\"pane.rating.category\"][normalize-space(@class)=\"widget-pane-link\"]"));
				String category = "";

				if (isPresent) {
					System.out.println(driver.findElement(By.xpath(
							"//button[normalize-space(@jsaction)=\"pane.rating.category\"][normalize-space(@class)=\"widget-pane-link\"]"))
							.getText());
					category = driver.findElement(By.xpath(
							"//button[normalize-space(@jsaction)=\"pane.rating.category\"][normalize-space(@class)=\"widget-pane-link\"]"))
							.getText();
				}
				CSVUtils.writeLine(writer, Arrays.asList(id, name, gglink, category), ',', '"');
				writer.flush();
				count++;
				if (count == 150) {
					driver.quit();
					count = 1;
				}
			}
			driver.quit();
			writer.close();

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (bufferedReader != null) {
				try {
					bufferedReader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	public static boolean waitForElementBoolean(WebDriver driver, By object) {
		try {
			WebDriverWait wait = new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(object));
			return true;
		} catch (Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
}