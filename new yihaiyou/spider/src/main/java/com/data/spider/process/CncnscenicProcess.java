package com.data.spider.process;

import com.data.spider.service.data.ScenicService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.Scenic;
import com.data.spider.util.BaseSpiderProcess;
import com.zuipin.util.SpringContextHolder;
import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.htmlcleaner.HtmlCleaner;
import org.htmlcleaner.TagNode;
import org.htmlcleaner.XPatherException;
import org.webharvest.runtime.variables.Variable;

import java.net.URLEncoder;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CncnscenicProcess extends BaseSpiderProcess {

	private ScenicService	scenicService	= SpringContextHolder.getBean("scenicService");

	public CncnscenicProcess(Datatask datatask) {
		super(datatask);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected Variable go(String url) {
		try {
			HttpClient client = new HttpClient();
			HostConfiguration hostConfiguration = client.getHostConfiguration();
			hostConfiguration.setProxy("127.0.0.1", 8087);
			HttpClientParams httpClientParams = client.getParams();
			httpClientParams.setSoTimeout(timeout);
			httpClientParams.setConnectionManagerTimeout(timeout);
			GetMethod get = new GetMethod(datatask.getUrl());
			client.executeMethod(get);
			String buf = get.getResponseBodyAsString(); ////获得请求页面的html源码 ???
			HtmlCleaner clean = new HtmlCleaner();
			TagNode node = clean.clean(buf);
			log.info(node.toString());
			// let $name := $n//div[@class='ndwz']/span/text()
			// let $level := $n//div[@class='info_r']/b/text()
			// let $address := $n//div[@class='info_r']/p/text()
			// let $theme := $n//div[@class='info_type']/p[1]//em/text()
			// let $scenic_type := $n//div[@class='info_type']/p[2]//a/text()
			// let $open_time := $n//div[@class='info_type']/p[3]/span[2]/text()
			// let $price_str := $n//div[@class='info_type']/p[4]/span[2]/text()
			// let $introduce := $n//div[contains(@class,'simple')]
			String name = xPath(node, "//div[@class='ndwz']/span/text()");
			String level = xPath(node, "//div[@class='info_r']/b/text()");
			String address = xPath(node, "//div[@class='info_r']/p/text()");
			String theme = xPath(node, "//div[@class='info_type']/p[1]//em/text()");
			String scenic_type = xPath(node, "//div[@class='info_type']/p[2]//a/text()");
			String open_time = xPath(node, "//div[@class='info_type']/p[3]/span[2]/text()");
			String price_str = xPath(node, "//div[@class='info_type']/p[4]/span[2]/text()");
			String introduction = xPath(node, "//div[@class='txt']/div[1]/text()");
			String html = URLEncoder.encode(buf, "UTF-8");
			datatask.setHtml(html);
			Scenic scenic = new Scenic();
			scenic.setName(name);
			scenic.setLevel(level);
			scenic.setAddress(address);
			scenic.setTheme(theme);
			scenic.setScenicType(scenic_type);
			scenic.setOpenTime(open_time);
			scenic.setPrice(getPrice(price_str));
			scenic.setIntroduction(introduction);
			scenic.setDataHtml(datatask.getHtml());
			String data_source_id = datatask.getUrl().replace("http://www.cncn.com/piao/", "");
			scenic.setDataSourceId(Integer.parseInt(data_source_id));
			scenic.setDataSourceUrl(datatask.getUrl());
			scenic.setDataSource(getSource());
			scenic.setPrice(scenic.getPrice());
			scenicService.save(scenic);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	private String xPath(TagNode node, String xpath) throws XPatherException {
		Object[] nodes = node.evaluateXPath(xpath);
		if (nodes.length > 0) {
			return nodes[0].toString();
		}
		return null;
	}

	@Override
	public void execute() {
	}

	@Override
	public String getXmlName() {
		// TODO Auto-generated method stub
		return "cncn_jd";
	}

	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return "cncn";
	}

	@Override
	protected Semaphore getMutex() {
		// TODO Auto-generated method stub
		return CncnListProcess.mutex;
	}

	public Integer getPrice(String price_str) {
		if (price_str == null) {
			return 0;
		}
		Pattern pattern = Pattern.compile("(\\d)+");
		Matcher matcher = pattern.matcher(price_str);
		if (matcher.find()) {
			try {
				return Integer.parseInt(matcher.group());
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		return 0;
	}

	@Override
	public String getProxy() {
		// TODO Auto-generated method stub
		return "127.0.0.1:8087";
	}
}
