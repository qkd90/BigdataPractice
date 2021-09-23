package com.data.spider.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.concurrent.Callable;
import java.util.concurrent.Semaphore;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;

import org.apache.commons.httpclient.HostConfiguration;
import org.apache.commons.httpclient.params.HttpClientParams;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.webharvest.definition.ScraperConfiguration;
import org.webharvest.runtime.Scraper;
import org.webharvest.runtime.variables.Variable;

import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;

public abstract class BaseSpiderProcess implements Callable<Datatask> {
	protected Datatask			datatask;
	protected final static int	timeout	= 30000;
	protected Log				log		= LogFactory.getLog(this.getClass());

	public abstract String getSource();

	public String getProxy() {
		return null;
	}

	public BaseSpiderProcess(Datatask datatask) {
		// TODO Auto-generated constructor stub
		this.datatask = datatask;
	}

	@Override
	public Datatask call() throws Exception {
		Semaphore mutex = getMutex();
		try {
			if (mutex != null) {
				////
				mutex.acquire();
			}
			////开始执行任务
			////
			go(datatask.getUrl());
			////
			execute();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (mutex != null) {
				mutex.release();
			}
		}
		////执行任务后,返回执行完成的任务
		return datatask;
	}

	protected abstract Semaphore getMutex();

	public abstract void execute();

	protected Variable go(String url) {
		int i = 0;
		do {
			try {
				Variable result = fetch(url);
				datatask.setStatus(DatataskStatus.SUCCESSED);
				return result;
			} catch (Exception e) {
				// TODO: handle exception
				log.error(e.getMessage(), e);
			}
		} while (i++ < 3);
		return null;
	}

	public <T> T tripObj(Class<T> clazz) throws JAXBException {
		// XStream xstream = new XStream();
		// for (Entry<String, Class<?>> entry : maps.entrySet()) {
		// xstream.alias(entry.getKey(), entry.getClass());
		// }
		// return (T) xstream.fromXML(datatask.getHtml());
		JAXBContext context;
		// 通过指定映射的类创建上下文
		context = JAXBContext.newInstance(clazz);
		// 通过上下文创建java转换xml对象(java对象<-->xml文件)
		Unmarshaller u = context.createUnmarshaller();
		@SuppressWarnings("unchecked")
		T t = (T) u.unmarshal(new StringReader(datatask.getXml()));
		return t;
	}

	private Variable fetch(String url) throws FileNotFoundException {
		URL classPath = FetchHtmlToXml.class.getResource("/");
		//按照具体任务,使用指定的配置文件名称(类路径/指定名称.xml)获得指定的webharvest的xml配置文件
		String ctripJdPath = String.format("%sxmls/%s.xml", classPath.getPath(), getXmlName());
		log.debug(ctripJdPath);
		File file = new File(ctripJdPath);
		////
		ScraperConfiguration config = new ScraperConfiguration(file);
		Scraper scraper = new Scraper(config, ".");
		////获得代理配置信息(按继承的具体任务重写的代理获取方法,决定是否使用代理(有的任务不需要配置代理,视具体任务情况而定))
		String proxy = getProxy();
		////如果有代理配置,则进行代理解析并进行设置
		if (proxy != null) {
			String proxys[] = proxy.split(":");
			HostConfiguration hostConfiguration = scraper.getHttpClientManager().getHttpClient().getHostConfiguration();
			////从代理配置中解析出IP地址和端口并设置好
			hostConfiguration.setProxy(proxys[0], Integer.parseInt(proxys[1]));
		}
		HttpClientParams httpClientParams = scraper.getHttpClientManager().getHttpClient().getParams();
		httpClientParams.setSoTimeout(timeout);//设置Socket请求超时
		httpClientParams.setConnectionManagerTimeout(timeout);//设置网络连接超时
		scraper.addVariableToContext("url", url);//给指定的webharvest的xml配置文件中的url传值
		scraper.setDebug(false);
		scraper.execute();
		try {
			String html = URLEncoder.encode(scraper.getContext().getVar("html").toString(), "UTF-8");//获得webharvest的xml配置文件中定义的html变量值(String类型)
			datatask.setHtml(html);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Variable xml = scraper.getContext().getVar("products");//获得webharvest的xml配置文件中定义的products变量值(toString后为xml数据格式类型)
		datatask.setXml(xml.toString());
		return xml;
	}

	public abstract String getXmlName();

	protected Datatask cloneTask() {
		Datatask task = new Datatask();
		task.setClassname(datatask.getClassname());
		task.setInfo(task.getInfo());// ???
		task.setStatus(DatataskStatus.NEW);
		task.setTag("run create");
		task.setUrl(datatask.getUrl());
		return task;
	}

}
