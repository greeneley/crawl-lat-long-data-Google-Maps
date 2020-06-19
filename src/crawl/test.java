package crawl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Date;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class test {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub

		String browserUrl = "https://www.google.com/maps/place/171+Nguyễn+Văn+Linh,+Bình+Hiên,+Hải+Châu,+Đà+Nẵng+550000,+Vietnam/@16.0608776,108.2205606,17z/data=!3m1!4b1!4m5!3m4!1s0x314219cc54f853e9:0xe6d0a6b0d662980!8m2!3d16.0608725!4d108.2227493";
//		String[] extractString = browserUrl.substring(browserUrl.lastIndexOf("3d")+2).split("!4d", 2);
		String address = browserUrl.replaceAll("^.*place/|55000.*$", "").replaceAll("\\+", " ");

		String regex = "([0-9]+.*[0-9]+|[0-9]+?)";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(address);
		while(m.find()) {
            System.out.println(m.group());
        }
		address = address.replaceAll(regex, "");
		System.out.println(address);
	}
}
