package com.data.spider.process;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.zuipin.util.DateUtils;
/**
 * 百度交通数据抓取，用的是百度的开发者平台，因为每个开发者账号（key）有访问次数和频率限制,
 * 所以用这个类去循环获取我们已经有的若干个key中其中一个key，并且控制频率和次数
 *
 */
public enum KeyMutex {
	instance;
	private List<String>								keys;
	private ConcurrentHashMap<String, AtomicInteger>	counter	= new ConcurrentHashMap<String, AtomicInteger>();
	private final static Log							log		= LogFactory.getLog(KeyMutex.class);
	private final static AtomicInteger					index	= new AtomicInteger(0);

	public synchronized Integer getDayKey() {
		do {
			int indx = (index.incrementAndGet()) % keys.size();
			String key = String.format("%s=%s", DateUtils.format(new Date(), "yyyy-MM-dd"), String.valueOf(indx));
			AtomicInteger cou = null;
			if (!counter.containsKey(key)) {
				counter.putIfAbsent(key, new AtomicInteger(90000));
			}
			// log.info(String.format("++++++++++++++++++ %s", key));
			cou = counter.get(key);
			if (cou.get() > 0) {
				// log.info(String.format("------- %s", key));
				cou.decrementAndGet();
				return indx;
			} else {
				// log.info(String.format("0000000000000000 %s", key));
				try {
					Thread.sleep(5000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		} while (true);
	}

	public static void main(String[] args) {
		ArrayList<String> keys = new ArrayList<String>();
		keys.add("1");
		keys.add("2");
		KeyMutex.instance.setKeys(keys);
		ExecutorService service = Executors.newFixedThreadPool(20);
		for (int i = 0; i < 100; i++) {
			service.submit(new Runnable() {
				@Override
				public void run() {
					long index = KeyMutex.instance.getDayKey();
					log.info(index);
				}
			});
		}

	}

	public List<String> getKeys() {
		return keys;
	}

	public void setKeys(List<String> keys) {
		if (this.keys == null) {
			this.keys = keys;
		}
	}

}
