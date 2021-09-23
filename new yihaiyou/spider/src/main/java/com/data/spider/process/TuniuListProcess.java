package com.data.spider.process;

import com.data.spider.process.entity.MakeBy;
import com.data.spider.process.entity.Tuniulist;
import com.data.spider.service.DatataskService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.Scenic;
import com.data.spider.util.BaseSpiderProcess;
import com.data.spider.util.TaskFetcher;
import com.zuipin.util.SpringContextHolder;

import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class TuniuListProcess extends BaseSpiderProcess {
	private final static DatataskService	datataskService	= SpringContextHolder.getBean("datataskService");
	public static Semaphore					mutex			= new Semaphore(3);

	public TuniuListProcess(Datatask datatask) {
		super(datatask);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void execute() {
		try {
			log.debug(datatask.toString());
			Tuniulist list = tripObj(Tuniulist.class);
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
			task.setClassname(TuniuscenicProcess.class.getName());
			task.setName(scenic.getName());
			task.setUrl("http://menpiao.tuniu.com" + scenic.getDataSourceUrl());
			TaskFetcher.instance.addTask(task);
		}
	}

	private void addNextTask() {
		Pattern pattern = Pattern.compile("(\\d)+.html");
		Matcher matcher = pattern.matcher(datatask.getUrl());
		if (matcher.find()) {
			System.out.printf("%s %d %d %d", matcher.group(), matcher.start(), matcher.end(), matcher.groupCount());
			StringBuilder sb = new StringBuilder(datatask.getUrl().subSequence(0, matcher.start()));
			int page = Integer.parseInt(matcher.group().replace(".html", ""));
			Datatask task = cloneTask();
			task.setUrl(sb.append(String.valueOf(page + 1)).append(".html").toString());
			task.setMadeby(MakeBy.RUNTIME);
			TaskFetcher.instance.addTask(task);
		}
	}

	@Override
	public String getXmlName() {
		// TODO Auto-generated method stub
		return "tuniu_jdlist";
	}

	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return "tuniu";
	}

	@Override
	protected Semaphore getMutex() {
		// TODO Auto-generated method stub
		return mutex;
	}

}
