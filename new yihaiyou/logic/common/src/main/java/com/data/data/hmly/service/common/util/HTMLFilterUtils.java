package com.data.data.hmly.service.common.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zzl on 2016/1/31.
 * 针对String文本过滤指定的HTML标签和脚本等等
 */

public class HTMLFilterUtils {
    private static final String FILTERED_HTML_TAGS = "html|body|head|title|style|video|canvas|link|iframe|frameset"
                                            + "|frame|object|embed|xml|input|button|textarea|select|pre|option"
                                            + "|plaintext|form|a|span|font|li|div|tr|td|ul|h1|h2|h3|h4|h5|i|hr|p";

    private static final String HTML_TAGS_REGX = "(</?)(" + FILTERED_HTML_TAGS + ")([^>]*>)";

    private static final String HTML_SCRIPT_REGX = "<script[^>]*?>[\\s\\S]*?<\\/script>";

    private static  final String HTML_EVENT_REGX = "(<[^<]*)(on\\w*\\x20*=|javascript:)";

    private static final Pattern HTML_TAGS_PATTERN = Pattern.compile(HTML_TAGS_REGX, Pattern.CASE_INSENSITIVE + Pattern.MULTILINE);

    private static final Pattern HTML_SCRIPT_PATTERN = Pattern.compile(HTML_SCRIPT_REGX, Pattern.CASE_INSENSITIVE + Pattern.MULTILINE);

    private static final Pattern HTML_EVENT_PATTERN = Pattern.compile(HTML_EVENT_REGX, Pattern.CASE_INSENSITIVE + Pattern.MULTILINE);


    public static  String doHTMLFilter(String sourceStr) {
        Matcher matcher;
        if (sourceStr == null || "".equals(sourceStr)) {
            return "";
        }
        // 首先过滤标签, 循环匹配以保证全部过滤, 防止恶意多层嵌套标签
        while ((matcher = HTML_TAGS_PATTERN.matcher(sourceStr)).find()) {
            sourceStr = matcher.replaceAll("");
        }
        // 过滤事件
        while ((matcher = HTML_EVENT_PATTERN.matcher(sourceStr)).find()) {
            sourceStr = matcher.replaceAll("");
        }
        // 过滤脚本
        while ((matcher = HTML_SCRIPT_PATTERN.matcher(sourceStr)).find()) {
            sourceStr = matcher.replaceAll("");
        }
        return sourceStr;
    }

}
