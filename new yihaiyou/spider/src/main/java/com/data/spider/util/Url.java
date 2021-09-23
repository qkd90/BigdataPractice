package com.data.spider.util;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

public class Url {
	private String				url;
	private final static Log	log		= LogFactory.getLog(Url.class);
	private Map<String, String>	maps	= new HashMap<String, String>();
	private String				host;

	public Url(String url) {
		// TODO Auto-generated constructor stub
		this.url = url;
		int beginIndex = this.url.indexOf("?");
		String queryStr = url.substring(beginIndex + 1);
		this.host = this.url.substring(0, beginIndex);
		String[] items = queryStr.split("&");
		for (String string : items) {
			if (string.trim().length() > 0) {
				String[] entry = string.split("=");
				if (entry.length == 2) {
					maps.put(entry[0], entry[1]);
				} else {
					maps.put(entry[0], "");
				}
			}
		}
		log.info(queryStr);
	}

	public String get(String key) {
		return maps.get(key);
	}

	public int getInt(String key) {
		return Integer.parseInt(maps.get(key));
	}

	public String set(String key, String value) {
		return maps.put(key, value);
	}

	@Override
	public String toString() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder(host).append("?");
		for (Entry<String, String> entry : maps.entrySet()) {
			sb.append("&").append(entry.getKey()).append("=").append(entry.getValue());
		}
		return sb.toString();
	}

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String urlStr = "http://www.ly.com/scenery/SearchList.aspx?&action=getlist&page=1&kw=&pid=16&cid=0&cyid=0&theme=0&grade=0&money=0&sort=0&paytype=0&ismem=0&istuan=0&isnow=0&spType=&isyiyuan=0&lbtypes=&IsNJL=0&classify=0&dctrack=1%CB%871439794077229041%CB%878%CB%8722%CB%873909442124906495%CB%870&iid=0.5961782727390528";
		Url url = new Url(urlStr);
		log.info(url.get("page"));
		log.info(url);
		url.set("page", String.valueOf(22));
		log.info(url.get("page"));
		log.info(url);
	}

}
