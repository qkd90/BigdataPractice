package spider;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PatternTest {

	public static void main(String[] args) {
		String url = "建议-45分钟-16小时";
		System.out.println(url);
		Pattern pattern = Pattern.compile("(\\d)+(小时|分钟|分钟)");
		Matcher matcher = pattern.matcher(url);
		if (matcher.find()) {
			System.out.printf("%s", matcher.group());
		}
	}
}
