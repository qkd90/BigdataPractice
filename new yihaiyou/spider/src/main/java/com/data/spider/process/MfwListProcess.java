package com.data.spider.process;

import com.data.spider.process.entity.MakeBy;
import com.data.spider.process.entity.Mfwlist;
import com.data.spider.service.DatataskService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.Scenic;
import com.data.spider.util.BaseSpiderProcess;
import com.data.spider.util.TaskFetcher;
import com.zuipin.util.SpringContextHolder;

import java.util.List;
import java.util.concurrent.Semaphore;

public class MfwListProcess extends BaseSpiderProcess {
	private final static DatataskService	datataskService	= SpringContextHolder.getBean("datataskService");

	public MfwListProcess(Datatask datatask) {
		super(datatask);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		try {
			log.debug(datatask.toString());
			Mfwlist list = tripObj(Mfwlist.class);
			log.debug(list);
			if (!list.getScenics().isEmpty()) {
				makeDetailTask(list.getScenics());
				// addNextTask();
			}
			if (datatask.getMadeby() == MakeBy.DB) {
				datataskService.updateTask(datatask);
			}
		} catch (Exception e) {
			log.error(String.format("%s %s", e.getMessage(), datatask.getUrl()), e);
			datatask.setStatus(DatataskStatus.FAILED);
		}
	}

	private void makeDetailTask(List<Scenic> scenics) {
		for (Scenic scenic : scenics) {
			Datatask task = cloneTask();
			task.setClassname(MfwscenicProcess.class.getName());
			task.setName(scenic.getName());
			task.setUrl("http://www.mafengwo.cn" + scenic.getDataSourceUrl());
			TaskFetcher.instance.addTask(task);
		}
	}

	@Override
	public String getXmlName() {
		// TODO Auto-generated method stub
		return "mfw_jdlist";
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
