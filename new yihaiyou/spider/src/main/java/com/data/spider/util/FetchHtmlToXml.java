package com.data.spider.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.ObjectInputStream.GetField;
import java.io.StringReader;
import java.net.URL;

import org.apache.commons.httpclient.params.HttpClientParams;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.variables.Variable;

public class FetchHtmlToXml {

	/**
	 * @param args
	 * @throws FileNotFoundException 
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// TODO Auto-generated method stub
		URL classPath = FetchHtmlToXml.class.getResource("/");
		String ctripJdPath = String.format("%sxmls/ctrip_jd.xml",classPath.getPath());
		String url = "http://you.ctrip.com/sight/gulangyu120058/57405.html";
		System.out.println(ctripJdPath);
		File file = new File(ctripJdPath);
		ScraperConfiguration config = new ScraperConfiguration(file);
		Scraper scraper = new Scraper(config, ".");
		HttpClientParams httpClientParams = scraper.getHttpClientManager().getHttpClient().getParams();
		int timeout = 10000;
		httpClientParams.setSoTimeout(timeout);
		httpClientParams.setConnectionManagerTimeout(timeout);
		scraper.addVariableToContext("url", url);
		scraper.setDebug(false);
		scraper.execute();
		Variable variable = scraper.getContext().getVar("products");
		System.out.println(variable.toString());
	}

}
