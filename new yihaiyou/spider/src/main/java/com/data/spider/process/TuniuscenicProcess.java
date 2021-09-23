package com.data.spider.process;

import com.data.spider.service.data.ScenicService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.Scenic;
import com.data.spider.util.BaseSpiderProcess;
import com.zuipin.util.SpringContextHolder;

import java.util.concurrent.Semaphore;

public class TuniuscenicProcess extends BaseSpiderProcess {

	private ScenicService	scenicService	= SpringContextHolder.getBean("scenicService");

	public TuniuscenicProcess(Datatask datatask) {
		super(datatask);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		try {
			log.debug(datatask.getHtml());
			Scenic scenic = tripObj(Scenic.class);
			scenic.setName(datatask.getName());
			scenic.setDataHtml(datatask.getHtml());
			String data_source_id = datatask.getUrl().replace("http://menpiao.tuniu.com/t_", "");
			scenic.setDataSourceId(Integer.parseInt(data_source_id));
			scenic.setDataSourceUrl(datatask.getUrl());
			scenic.setDataSource(getSource());
			String address = scenic.getAddress();
			int index = address.indexOf('|');
			if (index > 0) {
				scenic.setAddress(address.substring(0, index));
				scenic.setOpenTime(address.substring(index + 1));
			}
			scenicService.save(scenic);
			datatask.setStatus(DatataskStatus.SUCCESSED);
		} catch (Exception e) {
			// TODO: handle exception
			log.error(String.format("%s %s", e.getMessage(), datatask.getUrl()), e);
			datatask.setStatus(DatataskStatus.FAILED);
		}

	}

	@Override
	public String getXmlName() {
		// TODO Auto-generated method stub
		return "tuniu_jd";
	}

	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return "tuniu";
	}

	@Override
	protected Semaphore getMutex() {
		// TODO Auto-generated method stub
		return TuniuListProcess.mutex;
	}

}
