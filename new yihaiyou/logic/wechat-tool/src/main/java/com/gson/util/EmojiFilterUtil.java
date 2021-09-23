package com.gson.util;

import org.apache.commons.lang.StringUtils;

public class EmojiFilterUtil {
	public static String filterEmoji(String source){
		if(StringUtils.isBlank(source)){
			return source;
		}
		else{
			return source.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "");
		}
	}
}
