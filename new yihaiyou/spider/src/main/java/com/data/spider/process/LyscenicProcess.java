package com.data.spider.process;

import com.data.spider.service.data.ScenicService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.Scenic;
import com.data.spider.util.BaseSpiderProcess;
import com.zuipin.util.SpringContextHolder;

import java.util.concurrent.Semaphore;

public class LyscenicProcess extends BaseSpiderProcess {

	private ScenicService	scenicService	= SpringContextHolder.getBean("scenicService");

	public LyscenicProcess(Datatask datatask) {
		super(datatask);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		try {
			log.debug(datatask.getHtml());
			Scenic scenic = tripObj(Scenic.class);
			scenic.setDataHtml(datatask.getHtml());
			String data_source_id = datatask.getUrl().replace("http://www.ly.com/scenery/BookSceneryTicket_", "").replace(".html", "");
			scenic.setDataSourceId(Integer.parseInt(data_source_id));
			scenic.setDataSourceUrl(datatask.getUrl());
			scenic.setDataSource(getSource());
			scenicService.save(scenic);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(String.format("%s %s", e.getMessage(), datatask.getUrl()), e);
		}

	}

	@Override
	public String getXmlName() {
		// TODO Auto-generated method stub
		return "ly_jd";
	}

	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return "ly";
	}

	@Override
	protected Semaphore getMutex() {
		// TODO Auto-generated method stub
		return null;
	}

}
