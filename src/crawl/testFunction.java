package crawl;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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

public class testFunction {

	private static String numberAddress;

	public static void main(String[] args) throws InterruptedException, IOException {
		// TODO Auto-generated method stub
//		System.setProperty("webdriver.gecko.driver", System.getProperty("user.dir") +"\\chromedriver_win32\\geckodriver.exe");

		WebDriver driver = new FirefoxDriver();
		JavascriptExecutor js = (JavascriptExecutor) driver;
		String csvFile = Calendar.getInstance().getTimeInMillis() + "_result.csv";
		BufferedWriter writer = new BufferedWriter(
				new OutputStreamWriter(new FileOutputStream(csvFile), StandardCharsets.UTF_8));
		driver.get("https://www.google.com/maps");

		String newUrl = driver.getCurrentUrl();
		do {
			Thread.sleep(5);
			newUrl = driver.getCurrentUrl();
		} while (newUrl.contentEquals("https://www.google.com/maps"));

		for (int i = 1; i < 5; i++) {

			driver.findElement(By.id("searchboxinput")).click();
			driver.findElement(By.id("searchboxinput")).clear();
			driver.findElement(By.id("searchboxinput")).sendKeys(i + " Lê Văn Thêm Quận 7 Hồ Chí Minh");
			driver.findElement(By.id("searchbox-searchbutton")).click();

			// ===================================

			String browserUrl = (String) js.executeScript("return decodeURIComponent(window.location.href)");
			while (!browserUrl.contains("place")) {
				TimeUnit.MILLISECONDS.sleep(1000);
				browserUrl = (String) js.executeScript("return decodeURIComponent(window.location.href)");
			}

			// ===================================

			System.out.println("Your browser URL is " + browserUrl);
			try {
				numberAddress = null;
				String[] extractString = browserUrl.substring(browserUrl.lastIndexOf("3d") + 2).split("!4d", 2);
				String address = browserUrl.replaceAll("^.*place/|/@.*$", "").replaceAll("\\+", " ");
				String regex = "^[A-z]*\\d+[^\\s,]*\\d*";
				Pattern pattern = Pattern.compile(regex);
				Matcher matcher = pattern.matcher(address);
//        		String numberAddress = null;
				while (matcher.find()) {
					numberAddress = matcher.group();
				}
				address = compoundUnicode(address.replaceAll(regex, ""));
				CSVUtils.writeLine(writer, Arrays.asList(numberAddress, address.trim(), extractString[0].toString(),
						extractString[1].toString()));

				writer.flush();

			} catch (Exception e) {
				// TODO: handle exception
				System.out.println(e);
				;
			}
		}
		driver.close();

	}

	public static String compoundUnicode(String unicode_combining_str) {
		unicode_combining_str = unicode_combining_str.replace("\u0065\u0309", "\u1EBB");
		unicode_combining_str = unicode_combining_str.replace("\u0065\u0301", "\u00E9");
		unicode_combining_str = unicode_combining_str.replace("\u0065\u0300", "\u00E8");
		unicode_combining_str = unicode_combining_str.replace("\u0065\u0323", "\u1EB9");
		unicode_combining_str = unicode_combining_str.replace("\u0065\u0303", "\u1EBD");
		unicode_combining_str = unicode_combining_str.replace("\u00EA\u0309", "\u1EC3");
		unicode_combining_str = unicode_combining_str.replace("\u00EA\u0301", "\u1EBF");
		unicode_combining_str = unicode_combining_str.replace("\u00EA\u0300", "\u1EC1");
		unicode_combining_str = unicode_combining_str.replace("\u00EA\u0323", "\u1EC7");
		unicode_combining_str = unicode_combining_str.replace("\u00EA\u0303", "\u1EC5");
		unicode_combining_str = unicode_combining_str.replace("\u0079\u0309", "\u1EF7");
		unicode_combining_str = unicode_combining_str.replace("\u0079\u0301", "\u00FD");
		unicode_combining_str = unicode_combining_str.replace("\u0079\u0300", "\u1EF3");
		unicode_combining_str = unicode_combining_str.replace("\u0079\u0323", "\u1EF5");
		unicode_combining_str = unicode_combining_str.replace("\u0079\u0303", "\u1EF9");
		unicode_combining_str = unicode_combining_str.replace("\u0075\u0309", "\u1EE7");
		unicode_combining_str = unicode_combining_str.replace("\u0075\u0301", "\u00FA");
		unicode_combining_str = unicode_combining_str.replace("\u0075\u0300", "\u00F9");
		unicode_combining_str = unicode_combining_str.replace("\u0075\u0323", "\u1EE5");
		unicode_combining_str = unicode_combining_str.replace("\u0075\u0303", "\u0169");
		unicode_combining_str = unicode_combining_str.replace("\u01B0\u0309", "\u1EED");
		unicode_combining_str = unicode_combining_str.replace("\u01B0\u0301", "\u1EE9");
		unicode_combining_str = unicode_combining_str.replace("\u01B0\u0300", "\u1EEB");
		unicode_combining_str = unicode_combining_str.replace("\u01B0\u0323", "\u1EF1");
		unicode_combining_str = unicode_combining_str.replace("\u01B0\u0303", "\u1EEF");
		unicode_combining_str = unicode_combining_str.replace("\u0069\u0309", "\u1EC9");
		unicode_combining_str = unicode_combining_str.replace("\u0069\u0301", "\u00ED");
		unicode_combining_str = unicode_combining_str.replace("\u0069\u0300", "\u00EC");
		unicode_combining_str = unicode_combining_str.replace("\u0069\u0323", "\u1ECB");
		unicode_combining_str = unicode_combining_str.replace("\u0069\u0303", "\u0129");
		unicode_combining_str = unicode_combining_str.replace("\u006F\u0309", "\u1ECF");
		unicode_combining_str = unicode_combining_str.replace("\u006F\u0301", "\u00F3");
		unicode_combining_str = unicode_combining_str.replace("\u006F\u0300", "\u00F2");
		unicode_combining_str = unicode_combining_str.replace("\u006F\u0323", "\u1ECD");
		unicode_combining_str = unicode_combining_str.replace("\u006F\u0303", "\u00F5");
		unicode_combining_str = unicode_combining_str.replace("\u01A1\u0309", "\u1EDF");
		unicode_combining_str = unicode_combining_str.replace("\u01A1\u0301", "\u1EDB");
		unicode_combining_str = unicode_combining_str.replace("\u01A1\u0300", "\u1EDD");
		unicode_combining_str = unicode_combining_str.replace("\u01A1\u0323", "\u1EE3");
		unicode_combining_str = unicode_combining_str.replace("\u01A1\u0303", "\u1EE1");
		unicode_combining_str = unicode_combining_str.replace("\u00F4\u0309", "\u1ED5");
		unicode_combining_str = unicode_combining_str.replace("\u00F4\u0301", "\u1ED1");
		unicode_combining_str = unicode_combining_str.replace("\u00F4\u0300", "\u1ED3");
		unicode_combining_str = unicode_combining_str.replace("\u00F4\u0323", "\u1ED9");
		unicode_combining_str = unicode_combining_str.replace("\u00F4\u0303", "\u1ED7");
		unicode_combining_str = unicode_combining_str.replace("\u0061\u0309", "\u1EA3");
		unicode_combining_str = unicode_combining_str.replace("\u0061\u0301", "\u00E1");
		unicode_combining_str = unicode_combining_str.replace("\u0061\u0300", "\u00E0");
		unicode_combining_str = unicode_combining_str.replace("\u0061\u0323", "\u1EA1");
		unicode_combining_str = unicode_combining_str.replace("\u0061\u0303", "\u00E3");
		unicode_combining_str = unicode_combining_str.replace("\u0103\u0309", "\u1EB3");
		unicode_combining_str = unicode_combining_str.replace("\u0103\u0301", "\u1EAF");
		unicode_combining_str = unicode_combining_str.replace("\u0103\u0300", "\u1EB1");
		unicode_combining_str = unicode_combining_str.replace("\u0103\u0323", "\u1EB7");
		unicode_combining_str = unicode_combining_str.replace("\u0103\u0303", "\u1EB5");
		unicode_combining_str = unicode_combining_str.replace("\u00E2\u0309", "\u1EA9");
		unicode_combining_str = unicode_combining_str.replace("\u00E2\u0301", "\u1EA5");
		unicode_combining_str = unicode_combining_str.replace("\u00E2\u0300", "\u1EA7");
		unicode_combining_str = unicode_combining_str.replace("\u00E2\u0323", "\u1EAD");
		unicode_combining_str = unicode_combining_str.replace("\u00E2\u0303", "\u1EAB");
		unicode_combining_str = unicode_combining_str.replace("\u0045\u0309", "\u1EBA");
		unicode_combining_str = unicode_combining_str.replace("\u0045\u0301", "\u00C9");
		unicode_combining_str = unicode_combining_str.replace("\u0045\u0300", "\u00C8");
		unicode_combining_str = unicode_combining_str.replace("\u0045\u0323", "\u1EB8");
		unicode_combining_str = unicode_combining_str.replace("\u0045\u0303", "\u1EBC");
		unicode_combining_str = unicode_combining_str.replace("\u00CA\u0309", "\u1EC2");
		unicode_combining_str = unicode_combining_str.replace("\u00CA\u0301", "\u1EBE");
		unicode_combining_str = unicode_combining_str.replace("\u00CA\u0300", "\u1EC0");
		unicode_combining_str = unicode_combining_str.replace("\u00CA\u0323", "\u1EC6");
		unicode_combining_str = unicode_combining_str.replace("\u00CA\u0303", "\u1EC4");
		unicode_combining_str = unicode_combining_str.replace("\u0059\u0309", "\u1EF6");
		unicode_combining_str = unicode_combining_str.replace("\u0059\u0301", "\u00DD");
		unicode_combining_str = unicode_combining_str.replace("\u0059\u0300", "\u1EF2");
		unicode_combining_str = unicode_combining_str.replace("\u0059\u0323", "\u1EF4");
		unicode_combining_str = unicode_combining_str.replace("\u0059\u0303", "\u1EF8");
		unicode_combining_str = unicode_combining_str.replace("\u0055\u0309", "\u1EE6");
		unicode_combining_str = unicode_combining_str.replace("\u0055\u0301", "\u00DA");
		unicode_combining_str = unicode_combining_str.replace("\u0055\u0300", "\u00D9");
		unicode_combining_str = unicode_combining_str.replace("\u0055\u0323", "\u1EE4");
		unicode_combining_str = unicode_combining_str.replace("\u0055\u0303", "\u0168");
		unicode_combining_str = unicode_combining_str.replace("\u01AF\u0309", "\u1EEC");
		unicode_combining_str = unicode_combining_str.replace("\u01AF\u0301", "\u1EE8");
		unicode_combining_str = unicode_combining_str.replace("\u01AF\u0300", "\u1EEA");
		unicode_combining_str = unicode_combining_str.replace("\u01AF\u0323", "\u1EF0");
		unicode_combining_str = unicode_combining_str.replace("\u01AF\u0303", "\u1EEE");
		unicode_combining_str = unicode_combining_str.replace("\u0049\u0309", "\u1EC8");
		unicode_combining_str = unicode_combining_str.replace("\u0049\u0301", "\u00CD");
		unicode_combining_str = unicode_combining_str.replace("\u0049\u0300", "\u00CC");
		unicode_combining_str = unicode_combining_str.replace("\u0049\u0323", "\u1ECA");
		unicode_combining_str = unicode_combining_str.replace("\u0049\u0303", "\u0128");
		unicode_combining_str = unicode_combining_str.replace("\u004F\u0309", "\u1ECE");
		unicode_combining_str = unicode_combining_str.replace("\u004F\u0301", "\u00D3");
		unicode_combining_str = unicode_combining_str.replace("\u004F\u0300", "\u00D2");
		unicode_combining_str = unicode_combining_str.replace("\u004F\u0323", "\u1ECC");
		unicode_combining_str = unicode_combining_str.replace("\u004F\u0303", "\u00D5");
		unicode_combining_str = unicode_combining_str.replace("\u01A0\u0309", "\u1EDE");
		unicode_combining_str = unicode_combining_str.replace("\u01A0\u0301", "\u1EDA");
		unicode_combining_str = unicode_combining_str.replace("\u01A0\u0300", "\u1EDC");
		unicode_combining_str = unicode_combining_str.replace("\u01A0\u0323", "\u1EE2");
		unicode_combining_str = unicode_combining_str.replace("\u01A0\u0303", "\u1EE0");
		unicode_combining_str = unicode_combining_str.replace("\u00D4\u0309", "\u1ED4");
		unicode_combining_str = unicode_combining_str.replace("\u00D4\u0301", "\u1ED0");
		unicode_combining_str = unicode_combining_str.replace("\u00D4\u0300", "\u1ED2");
		unicode_combining_str = unicode_combining_str.replace("\u00D4\u0323", "\u1ED8");
		unicode_combining_str = unicode_combining_str.replace("\u00D4\u0303", "\u1ED6");
		unicode_combining_str = unicode_combining_str.replace("\u0041\u0309", "\u1EA2");
		unicode_combining_str = unicode_combining_str.replace("\u0041\u0301", "\u00C1");
		unicode_combining_str = unicode_combining_str.replace("\u0041\u0300", "\u00C0");
		unicode_combining_str = unicode_combining_str.replace("\u0041\u0323", "\u1EA0");
		unicode_combining_str = unicode_combining_str.replace("\u0041\u0303", "\u00C3");
		unicode_combining_str = unicode_combining_str.replace("\u0102\u0309", "\u1EB2");
		unicode_combining_str = unicode_combining_str.replace("\u0102\u0301", "\u1EAE");
		unicode_combining_str = unicode_combining_str.replace("\u0102\u0300", "\u1EB0");
		unicode_combining_str = unicode_combining_str.replace("\u0102\u0323", "\u1EB6");
		unicode_combining_str = unicode_combining_str.replace("\u0102\u0303", "\u1EB4");
		unicode_combining_str = unicode_combining_str.replace("\u00C2\u0309", "\u1EA8");
		unicode_combining_str = unicode_combining_str.replace("\u00C2\u0301", "\u1EA4");
		unicode_combining_str = unicode_combining_str.replace("\u00C2\u0300", "\u1EA6");
		unicode_combining_str = unicode_combining_str.replace("\u00C2\u0323", "\u1EAC");
		unicode_combining_str = unicode_combining_str.replace("\u00C2\u0303", "\u1EAA");
		return unicode_combining_str;
	}
}
