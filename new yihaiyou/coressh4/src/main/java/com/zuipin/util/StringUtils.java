package com.zuipin.util;

import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.net.InetAddress;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.net.UnknownHostException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils {
    private static String PERCENTAGE_PATTERN = "###0.00%";
    private static String REG_EX = "[１`~!@#$%^&*()+=|{}':;',.\\[\\]<>/?~！@#￥%……&*（）_——－＋+|{}【】‘；：”“’。，、？]";
    private static Pattern pn_1 = Pattern.compile("<script[\\s\\S]*?<\\/script[\\s\\S]*?>", Pattern.CASE_INSENSITIVE | Pattern.MULTILINE
            | Pattern.DOTALL);
    private static Pattern pn_2 = Pattern.compile("['‘’]+", Pattern.CASE_INSENSITIVE);
    private static Pattern pn_3 = Pattern.compile("[<,>]+", Pattern.CASE_INSENSITIVE);

    private static String mobileRegx = "^((13[0-9])|(14[7])|(17[0-9])|(15[0-9])|(18[0-9]))\\d{8}$";
    private static String emailRegx = "^\\\\s*\\\\w+(?:\\\\.{0,1}[\\\\w-]+)*@[a-zA-Z0-9]+(?:[-.][a-zA-Z0-9]+)*\\\\.[a-zA-Z]+\\\\s*$";
    /**
     *    * 把list转换为一个用逗号分隔的字符串   
     */
    public static String listToString(List<?> list) {
        StringBuffer sb = new StringBuffer();
        if (list != null && !list.isEmpty()) {
            for (int i = 0; i < list.size(); i++) {
                if (i < list.size() - 1) {
                    sb.append(list.get(i) + ",");
                } else {
                    sb.append(list.get(i));
                }
            }
        }
        return sb.toString();
    }

    public static String replaceAll(String str) {
        Pattern p = Pattern.compile(REG_EX);
        Matcher m = p.matcher(str);
        return m.replaceAll("").trim();
    }

    public static boolean isPinyin(String str) {
        Pattern p = Pattern.compile("^[A-Za-z0-9]+$");
        Matcher m = p.matcher(str);
        return m.matches();
    }

    public static String[] deleteDuplicat(String[] strings) {
        Set<String> as = new HashSet<String>();
        for (String s : strings) {
            as.add(s);
        }
        return as.toArray(new String[as.size()]);
    }

    public static String encodeUTF8(String s) {
        try {
            return URLEncoder.encode(s, "utf-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return s;
    }

    public static String decodeUTF8(String s) {
        try {
            return URLDecoder.decode(s, "utf-8");
        } catch (UnsupportedEncodingException ex) {
        }
        return s;
    }

    public static String trim(String text) {
        return text == null ? null : text.trim();
    }

    public static boolean hasText(String text) {
        return text != null && text.trim().length() != 0;
    }

    public static boolean equals(String text1, String text2) {
        return text1 == null && text2 == null || text1 != null && text1.equals(text2);
    }

    public static boolean lengthMoreThan(String text, int length) {
        return text != null && text.length() >= length;
    }

    public static boolean lengthBetween(String text, int minLen, int maxLen) {
        return text != null && text.length() >= minLen && text.length() <= maxLen;
    }

    public static String paddingLeft(String text, char paddingChar, int length) {
        // return org.apache.commons.lang.StringUtils.
        int index = 0;
        if (StringUtils.hasText(text)) {
            index = text.length();
        }
        StringBuilder stringBuilder = new StringBuilder(text == null ? "" : text);
        while (index < length) {
            stringBuilder.insert(0, paddingChar);
            index++;
        }
        return stringBuilder.toString();
    }

    public static boolean contains(String text, String target) {
        if (text == null && target != null) {
            return false;
        }
        if (text == null) {
            return true;
        }
        return target == null || text.contains(target);
    }

    public static boolean isEmpty(String text) {
        return !hasText(text);
    }

    public static String truncate(String text, int maxLength) {
        if (text == null) {
            return null;
        }
        if (text.length() <= maxLength) {
            return text;
        }
        return text.substring(0, maxLength);
    }

    public static String replaceNull(String str) {
        if (isBlank(str)) {
            return "";
        }
        return str;
    }

    public static String[] split(String string, String delimit) {
        if (StringUtils.isEmpty(string)) {
            return new String[0];
        }
        String[] strings = string.split(delimit);
        for (int i = 0; i < strings.length; i++) {
            strings[i] = strings[i].trim();
        }
        return strings;

    }

    public static String lowerCase(String value) {
        return value.toLowerCase();
    }

    public static String upperCase(String value) {
        return value.toUpperCase();
    }

    public static String toString(List<String> entries, String delimit, boolean withQuote) {
        if (entries.isEmpty()) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (String entry : entries) {
            if (withQuote) {
                entry = entry.replaceAll("'", "''");
                builder.append('\'');
            }
            builder.append(entry);
            if (withQuote) {
                builder.append('\'');
            }
            builder.append(delimit);
        }
        return builder.substring(0, builder.length() - delimit.length());
    }

    public static boolean isNumber(String input) {
        return (isFloat(input) || isDouble(input) || isInteger(input) || isLong(input));
    }

    public static boolean isLong(String input) {
        try {
            Long.parseLong(input);
            return true;
        } catch (NumberFormatException exe) {
            return false;
        }
    }

    public static boolean isFloat(String input) {
        try {
            float f = Float.parseFloat(input);
            if (Float.isNaN(f) || f == Float.POSITIVE_INFINITY || f == Float.NEGATIVE_INFINITY) {
                return false;
            } else {
                return true;
            }
        } catch (NumberFormatException ex) {
        }
        return false;
    }

    public static boolean isInteger(String input) {
        try {
            Integer.parseInt(input);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }
    }

    public static boolean isDouble(String input) {
        try {
            double d = Double.parseDouble(input);
            return !(Double.isNaN(d) || d == Double.POSITIVE_INFINITY || d == Double.NEGATIVE_INFINITY);
        } catch (NumberFormatException ex) {
        }
        return false;
    }

    public static String formatPerenctage(double percentage) {
        if (percentage == 0) {
            return "0.00%";
        }
        return formatDicimal(percentage, PERCENTAGE_PATTERN);
    }

    public static String formatDicimal(double percentage, String pattern) {
        DecimalFormat f = new DecimalFormat(pattern);
        return f.format(percentage);
    }

    public static String formatDate(Date date) {
        if (date == null) {
            return "";
        }
        SimpleDateFormat f = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return f.format(date);
    }

    public static String formatDicimalWithFractionDigits(double percentage, int minimumFractionDigits, int maximumFractionDigits) {
        DecimalFormat f = new DecimalFormat();
        f.setMaximumFractionDigits(maximumFractionDigits);
        f.setMinimumFractionDigits(minimumFractionDigits);
        return f.format(percentage);
    }

    public static String arrayToString(String[] a, String separator) {
        StringBuffer result = new StringBuffer();
        if (a == null) {
            return "";
        }
        if (a.length > 0) {
            result.append(a[0]);
            for (int i = 1; i < a.length; i++) {
                result.append(separator);
                result.append(a[i]);
            }
        }
        return result.toString();
    }

    public static String htmlEncode(String text) {
        if (text == null) {
            return "";
        }
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < text.length(); i++) {
            char c = text.charAt(i);
            // encode standard ASCII characters into HTML entities where needed
            if (c < '\200') {
                switch (c) {
                    case '"':
                        builder.append("&quot;");
                        break;
                    case '&':
                        builder.append("&amp;");
                        break;
                    case '<':
                        builder.append("&lt;");
                        break;
                    case '>':
                        builder.append("&gt;");
                        break;
                    case '\n':
                        builder.append("<br/>");
                        break;
                    default:
                        builder.append(c);
                }
            } else if (c < '\377') { // encode 'ugly' characters (ie Word
                // "curvy" quotes etc)
                String hexChars = "0123456789ABCDEF";
                int a = c % 16;
                int b = (c - a) / 16;
                String hex = "" + hexChars.charAt(b) + hexChars.charAt(a);
                builder.append("&#x").append(hex).append(";");
            } else { // add other characters back in - to handle charactersets
                // other than ascii
                builder.append(c);
            }
        }
        return builder.toString();
    }

    public static boolean isNotBlank(String text) {
        return text != null && text.trim().length() > 0 && !text.equals("null");
    }

    public static boolean isNotBlank(String... texts) {
        if (texts != null) {
            Boolean notBlank = true;
            for (String text : texts) {
                if (!(text != null && text.trim().length() > 0 && !text.equals("null"))) {
                    notBlank = false;
                    break;
                }
            }
            return notBlank;
        } else {
            return false;
        }

    }

    public static boolean isBlank(String text) {
        return !isNotBlank(text);
    }

    public static String formatCsvStr(String str) {
        if (isNotBlank(str)) {
            str = str.replaceAll("\"", "\"\"");
            str = str.replaceAll(",", "\",\"");
            str = "\"" + str + "\"";
        }
        return str;
    }

    public static String trim(Object obj) {
        String reString = "";
        if (obj == null) {
            reString = "";
        } else {
            reString = obj.toString().trim();
        }
        return reString;
    }

    public static String getNameByEmail(String email) {
        return email.substring(0, email.indexOf("@"));
    }

    public static String getNameByMobile(String mobile) {
        if (mobile != null && mobile.length() == 11) {
            return new StringBuilder(mobile).replace(3, 7, "**").toString();
        } else {
            return mobile;
        }
    }

    /**
     * @param request
     * @return
     * @author:zhengry
     * @email:zryuan@xiangyu.cn
     * @创建日期:2012-8-3
     * @功能说明：获取IP地址
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ipAddress = null;
        ipAddress = request.getHeader("x-forwarded-for");
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getHeader("x-real-ip");    // 根据nginx设置调整
        }
        if (ipAddress == null || ipAddress.length() == 0 || "unknown".equalsIgnoreCase(ipAddress)) {
            ipAddress = request.getRemoteAddr();
            if (ipAddress.equals("127.0.0.1") || ipAddress.equals("0:0:0:0:0:0:0:1")) {
                // 根据网卡取本机配置的IP
                InetAddress inet = null;
                try {
                    inet = InetAddress.getLocalHost();
                } catch (UnknownHostException e) {
                    e.printStackTrace();
                }
                ipAddress = inet.getHostAddress();
            }

        }
        // 对于通过多个代理的情况，第一个IP为客户端真实IP,多个IP按照','分割
        if (ipAddress != null && ipAddress.length() > 15) {
            if (ipAddress.indexOf(",") > 0) {
                ipAddress = ipAddress.substring(0, ipAddress.indexOf(","));
            }
        }
        return ipAddress;
    }

    public static Boolean isBoolean(String value) {
        if (isBlank(value)) {
            return false;
        }
        if (value.equals("true") || value.equals("false")) {
            return true;
        }
        return false;
    }

    public static Boolean toBoolean(String value) {
        if (isNotBlank(value)) {
            try {
                return Boolean.valueOf(value);
            } catch (Exception e) {
                return false;
            }
        }
        return false;
    }

    public static String getDefaultVlaue(String value) {
        if (isBlank(value)) {
            return "";
        }
        return value.trim();
    }

    public static Long getLongValue(String value) {
        try {
            return Long.parseLong(value);
        } catch (Exception e) {
            return 0l;
        }
    }

    public static <T> boolean isEmpty(Collection<T> items) {
        return items == null || items.isEmpty() || items.size() < 1;
    }

    /**
     * 格式化用户名
     *
     * @param userName
     * @return
     * @author:lining
     * @email:lining@xiangyu.cn
     * @创建日期:2012-9-3
     * @功能说明：
     */
    public static String formatUserName(String userName) {
        if (PatternUtils.isMobile(userName)) {
            userName = userName.replace(userName.substring(3, userName.length() - 4), "***");
        } else if (PatternUtils.isEmail(userName)) {
            if (userName.split("@")[0].length() > 4) {
                userName = userName.replace(userName.split("@")[0].substring(2, userName.split("@")[0].length() - 2), "***");
            } else {
                userName = userName.replace(userName.split("@")[0].substring(1, userName.split("@")[0].length() - 1), "***");
            }
        } else {
            if (userName.length() > 10) {
                userName = userName.replace(userName.substring(3, userName.length() - 4), "***");
            }
        }
        return userName;
    }

    /**
     * @param obj 需要转换的值
     * @author:yinyi
     * @创建日期:Oct 16, 2012
     * @功能说明：将其他值类型转换成String类型
     */
    public static String objectToString(Object obj) {
        String result = "";
        try {
            String temp = String.valueOf(obj);

            if (isNotBlank(temp)) {
                result = temp;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * @param randomNum
     * @return
     * @author:lining
     * @email:lining@xiangyu.cn
     * @创建日期:2012-11-27
     * @功能说明：获得不重复的N个随机数
     */
    public static List<Integer> getRandomNum(int randomNum, int length) {
        List<Integer> randoms = new ArrayList<Integer>();
        if (randomNum < length) {
            length = randomNum;
        }
        Random rd = new Random();
        while (randoms.size() < length) {
            int randNum = rd.nextInt(randomNum);
            if (randoms.contains(randNum)) {
                continue;
            } else {
                randoms.add(randNum);
            }
        }
        return randoms;
    }

    public static String filterString(final String value) {
        try {
            if (null == value) {
                return null;
            }
            String v = URLDecoder.decode(value, "UTF-8");
            for (Pattern p : getFilters()) {
                v = p.matcher(v).replaceAll("");
            }
            return v;
        } catch (UnsupportedEncodingException e) {
        }
        return null;
    }

    private static List<Pattern> getFilters() {
        List<Pattern> filters = new ArrayList<Pattern>();
        filters.add(pn_1);
        filters.add(pn_2);
        filters.add(pn_3);
        return filters;
    }

    public static boolean isAjax(HttpServletRequest request) {
        if (request != null && "XMLHttpRequest".equalsIgnoreCase(request.getHeader("X-Requested-With"))) {
            return true;
        }
        return false;
    }

    public static boolean isMobile(String mobile) {
        return Pattern.matches(mobileRegx, mobile);
    }
    public static boolean isEmail(String email) {
//        System.err.print(email + "---" + emailRegx);
        return Pattern.matches(emailRegx, email);
    }
}
