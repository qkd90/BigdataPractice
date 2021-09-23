package com.zuipin.solr;

import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;

public class PathConfig {
	public static String	INDEXPATH	= null;
	
	static {
		PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
		INDEXPATH = propertiesManager.getString("indexpath");
	}
	
	public static String getLoginInOutPath() {
		return getPath("logininout");
	}
	
	public static String getWebRequestPath() {
		// TODO Auto-generated method stub
		return getPath("webrequest");
	}
	
	/**
	 * @return
	 */
	private static String getPath(String dir) {
		// String path = String.format("%s%s", INDEXPATH, dir);
		// File file = new File(path);
		// if (!file.exists()) {
		// file.mkdir();
		// }
		// return path;
		return dir;
	}
	
	public static String getUserPath() {
		// TODO Auto-generated method stub
		return getPath("user");
	}
	
	public static String getSaleDetailPath() {
		// TODO Auto-generated method stub
		return getPath("saledetail");
	}
	
	public static String getSalePath() {
		// TODO Auto-generated method stub
		return getPath("sale");
	}
	
}
