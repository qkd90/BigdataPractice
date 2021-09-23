package com.data.spider.process;

import com.data.spider.process.entity.Dds;
import com.data.spider.process.entity.Mfw;
import com.data.spider.process.entity.MfwLabelenum;
import com.data.spider.service.data.ScenicService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.Scenic;
import com.data.spider.util.BaseSpiderProcess;
import com.zuipin.util.SpringContextHolder;

import java.lang.reflect.Field;
import java.util.concurrent.Semaphore;

public class MfwscenicProcess extends BaseSpiderProcess {

	private ScenicService	scenicService	= SpringContextHolder.getBean("scenicService");

	public MfwscenicProcess(Datatask datatask) {
		super(datatask);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		try {
			log.debug(datatask.getHtml());
			Mfw mfw = tripObj(Mfw.class);
			Scenic scenic = new Scenic();
			scenic.setName(datatask.getName());
			scenic.setIntroduction(mfw.getIntroduction());
			scenic.setDataHtml(datatask.getHtml());
			String data_source_id = datatask.getUrl().replace("http://www.mafengwo.cn/poi/", "").replace(".html", "");
			scenic.setDataSourceId(Integer.parseInt(data_source_id));
			scenic.setDataSourceUrl(datatask.getUrl());
			scenic.setDataSource(getSource());
			for (Dds ds : mfw.getDds()) {
				String field = MfwLabelenum.find(ds.getLabel());
				if (field != null) {
					Field[] fields = Scenic.class.getDeclaredFields();
					for (Field field2 : fields) {
						field2.setAccessible(true);
						if (field2.getName().equals(field)) {
							field2.set(scenic, ds.getP() + ds.getDiv());
							break;
						}
					}
				}
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
		return "mfw_jd";
	}

	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return "mfw";
	}

	@Override
	protected Semaphore getMutex() {
		// TODO Auto-generated method stub
		return null;
	}

}
