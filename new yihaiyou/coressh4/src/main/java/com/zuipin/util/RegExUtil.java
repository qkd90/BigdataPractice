package com.zuipin.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by dy on 2017/2/8.
 */
public class RegExUtil {

    private static String REGEX_PASSWORD = "^[a-zA-Z0-9]{6,16}$";

    private static Pattern pattern = null;
    private static Matcher matcher = null;

    public static boolean checkPassword(String password) {
        pattern = Pattern.compile(REGEX_PASSWORD);
        matcher = pattern.matcher(password);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }

}
