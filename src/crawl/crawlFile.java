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
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class crawlFile {
	private static String numberAddress;

	public static void main(String[] args) throws IOException, InterruptedException {
		// TODO Auto-generated method stub
		/**
		 * Tập hợp tất cả các variable
		 */

		String line = "";
		String cvsSplitBy = ",";

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

		/**
		 * Khai báo file Input và khởi tạo file Output Default: set fileInput =
		 * "input.csv"
		 */
		String fileInput = null;
		if (args.length == 0) {
			System.out.println("Don't input a crawl file format .csv \nDefault: input.csv");
			fileInput = "input.csv";
		} else {
			fileInput = args[0];
		}
		String fileOutput = fileInput.replace(".csv", "");
		String csvExport = fileOutput + "_result_" + Calendar.getInstance().getTimeInMillis() + ".csv";

		/**
		 * Khởi tạo BufferedReader và Writer
		 */

		BufferedReader bufferedReader = null;
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(csvExport), StandardCharsets.UTF_8));

		try {
			bufferedReader = new BufferedReader(new InputStreamReader(new FileInputStream(fileInput), "UTF8"));
			line = bufferedReader.readLine();

			while ((line = bufferedReader.readLine()) != null) {

				// =================================================
				WebDriver driver = null;
				JavascriptExecutor js = null;
				// =================================================

				/**
				 * Chọn giá trị điểm đầu IndexStart và giá trị điểm cuối IndexEnd
				 */
				String[] subLine = line.split(cvsSplitBy);
				int IndexStart = Integer.valueOf(subLine[2]);
				int IndexEnd = Integer.valueOf(subLine[3]);

				/**
				 * Count là biến đếm, sau 1 giá trị nhất định trình duyệt tự khởi động lại
				 */
				int count = 1;

				for (int i = IndexStart; i < IndexEnd + 1; i++) {

					if (count == 1) {

						driver = new FirefoxDriver();
						js = (JavascriptExecutor) driver;

						driver.get("https://www.google.com/maps");
						String newUrl = driver.getCurrentUrl();
						do {
							Thread.sleep(5);
							newUrl = driver.getCurrentUrl();
						} while (newUrl.contentEquals("https://www.google.com/maps"));
					}
					// =================================================
					driver.findElement(By.id("searchboxinput")).click();
					driver.findElement(By.id("searchboxinput")).clear();
					driver.findElement(By.id("searchboxinput")).sendKeys(i + " " + subLine[0] + " " + subLine[1]);

					/**
					 * Kiểm tra nút SearchButton
					 */
					try {
						WebDriverWait wait = new WebDriverWait(driver, 15);
						wait.until(
								ExpectedConditions.visibilityOfAllElementsLocatedBy(By.id("searchbox-searchbutton")));
					} catch (Exception e) {
						System.out.println(e);
						continue;
					}
					// ========================================================

					driver.findElement(By.id("searchbox-searchbutton")).click();

					/**
					 * Kiểm Tra status của web
					 */
					String browserUrl = (String) js.executeScript("return decodeURIComponent(window.location.href)");
					int countCheckURL = 0;
					while ((!browserUrl.contains("place")) && countCheckURL < 5) {
						TimeUnit.MILLISECONDS.sleep(1000);
						browserUrl = (String) js.executeScript("return decodeURIComponent(window.location.href)");
						countCheckURL++;
					}
					// ========================================================

					System.out.println("Your browser URL is " + browserUrl);

					/**
					 * Data Cleaning
					 */
					try {
						numberAddress = null;

						String[] coordinates = browserUrl.substring(browserUrl.lastIndexOf("3d") + 2).split("!4d", 2);
						String latitude = coordinates[0].toString();
						String longtitude = coordinates[1].toString();

						String address = browserUrl.replaceAll("^.*place/|/@.*$", "").replaceAll("\\+", " ");
						String regex = "^[0-9]+\\S[0-9]*";
						Pattern pattern = Pattern.compile(regex);
						Matcher matcher = pattern.matcher(address);
						while (matcher.find()) {
							numberAddress = matcher.group();
						}
						ConvertUnicode convertUnicode = new ConvertUnicode(address.replaceAll(regex, ""));
						address = convertUnicode.convert();

						CSVUtils.writeLine(writer, Arrays.asList(numberAddress, address.trim(), latitude, longtitude));
						writer.flush();

					} catch (Exception e) {
						System.out.println(e);
						continue;
					}
					count++;
					if (count == 150) {
						driver.quit();
						count = 1;
					}
				}
				driver.quit();
			}

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
//        newFile.delete();
		writer.close();
//        Runtime.getRuntime().exec("taskkill /F /IM " + driverName + " /T");
	}

}