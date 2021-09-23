package com.data.spider.process;

import com.data.spider.process.entity.Lylist;
import com.data.spider.process.entity.MakeBy;
import com.data.spider.service.DatataskService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.Scenic;
import com.data.spider.util.BaseSpiderProcess;
import com.data.spider.util.TaskFetcher;
import com.data.spider.util.Url;
import com.zuipin.util.SpringContextHolder;

import java.util.List;
import java.util.concurrent.Semaphore;

public class LyListProcess extends BaseSpiderProcess {
	private final static DatataskService	datataskService	= SpringContextHolder.getBean("datataskService");
	public final static Semaphore			mutex			= new Semaphore(5);

	public LyListProcess(Datatask datatask) {
		super(datatask);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		try {
			log.debug(datatask.toString());
			Lylist list = tripObj(Lylist.class);
			log.debug(list);
			if (!list.getScenics().isEmpty()) {
				makeDetailTask(list.getScenics());
				addNextTask();
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
			task.setClassname(LyscenicProcess.class.getName());
			task.setUrl("http://www.ly.com" + scenic.getDataSourceUrl());
			TaskFetcher.instance.addTask(task);
		}
	}

	private void addNextTask() {
		Url url = new Url(datatask.getUrl());
		url.set("page", String.valueOf(url.getInt("page") + 1));
		Datatask task = cloneTask();
		task.setUrl(url.toString());
		task.setMadeby(MakeBy.RUNTIME);
		TaskFetcher.instance.addTask(task);
	}

	@Override
	public String getXmlName() {
		// TODO Auto-generated method stub
		return "ly_jdlist";
	}

	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return "ly";
	}

	@Override
	protected Semaphore getMutex() {
		// TODO Auto-generated method stub
		return mutex;
	}

}
