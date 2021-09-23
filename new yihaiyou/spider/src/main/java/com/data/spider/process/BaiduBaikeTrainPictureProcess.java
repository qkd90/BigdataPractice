package com.data.spider.process;
/**
 * 百度百科火车站/汽车站图片抓取
 * By ZZL
 * 2015.10.12
 */

import com.data.spider.service.DatataskService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.tb.TbTransportation;
import com.data.spider.service.tb.TbTransportationService;
import com.data.spider.util.BaseSpiderProcess;
import com.data.spider.util.QiniuUtil;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.SpringContextHolder;

import java.net.URLEncoder;
import java.util.concurrent.Semaphore;

public class BaiduBaikeTrainPictureProcess extends BaseSpiderProcess {
	private final static DatataskService	datataskService				= SpringContextHolder.getBean("datataskService");
	private TbTransportationService 		tbTransportationService 	= SpringContextHolder.getBean("tbTransportationService");
	public final static Semaphore			mutex						= new Semaphore(5);//预设5个许可信号量

	public BaiduBaikeTrainPictureProcess(Datatask datatask) {
		super(datatask);
		// TODO Auto-generated constructor stub
	}
	@Override
	public Datatask call() throws Exception {
		Semaphore mutex = getMutex();
		try {
			if (mutex != null) {
				////
				mutex.acquire();
			}
			String name = datatask.getName();
			/**
			 * 处理不正确的火车站名称-步骤1
			 */
//			if(name.contains("站火车站")){
//				name = name.replace("站火车站", "火车站");
//			}
			/**
			 * 处理不正确的火车站名称-步骤2(先把步骤1跑一遍)
			 */
//			if(name.contains("站火车站")){
//				name = name.replace("火车站", "");
//			}
//			if(name.contains("火车站")){
//				name = name.replace("火车站", "站");
//			}
//			System.err.println(name);
			name = URLEncoder.encode(name, "utf-8");
			String baike_url = String.format("http://wapbaike.baidu.com/item/%s/",name);
//			String baike_url = URLEncoder.encode(String.format("http://wapbaike.baidu.com/item/%s/", name), "utf-8");
			datatask.setUrl(baike_url);
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
	@Override
	public void execute() {
		Long id = Long.parseLong(datatask.getTag());
		String cover_img = datatask.getXml();
		try {
			if(cover_img != null){
				//从抓取的图片地址中分离出大图的url地址
				String hd_img_url = cover_img.split("&src=")[1];
				String _url = QiniuUtil.UploadToQiniu(hd_img_url);
				if(_url != null){
					Criteria<TbTransportation> criteria = new Criteria<TbTransportation>(TbTransportation.class);
					criteria.eq("id", id);
					TbTransportation tbTransportation = tbTransportationService.gets(1, criteria).get(0);
					tbTransportation.setCoverImg(_url);
					tbTransportationService.update(tbTransportation);
					
					datatask.setStatus(DatataskStatus.SUCCESSED);
					datataskService.updateTask(datatask);
				}else if(_url == null){
					log.error("车站图片上传至七牛错误,车站id: "+datatask.getTag() + "车站名称: " + datatask.getName());
					datatask.setStatus(DatataskStatus.FAILED);
					datataskService.updateTask(datatask);
				}
			}else if(cover_img == null){
				log.error("车站图片获取错误(可能是没有找到该站!),车站id: "+datatask.getTag() + "车站名称: " + datatask.getName());
				datatask.setStatus(DatataskStatus.FAILED);
				datataskService.updateTask(datatask);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//			StackTraceElement[] err = e.getStackTrace();
//			System.err.println(err[0] + "\n" + err[1] + "\n" + err[3]);
			log.error("车站图片获取错误(可能是没有找到该站!),车站id: "+datatask.getTag() + " 车站名称: " + datatask.getName());
			datatask.setStatus(DatataskStatus.FAILED);
			datataskService.updateTask(datatask);
		}
//		try {
//			
//			log.debug(datatask.toString());
//			Lylist list = tripObj(Lylist.class);
//			log.debug(list);
//			if (!list.getScenics().isEmpty()) {
//				makeDetailTask(list.getScenics());
//				addNextTask();
//			}
//			if (datatask.getMadeby() == MakeBy.DB) {
//				datataskService.updateTask(datatask);
//			}
//		} catch (Exception e) {
//			log.error(String.format("%s %s", e.getMessage(), datatask.getUrl()), e);
//			datatask.setStatus(DatataskStatus.FAILED);
//		}
	}


//
//	private void addNextTask() {
//		Url url = new Url(datatask.getUrl());
//		url.set("page", String.valueOf(url.getInt("page") + 1));
//		Datatask task = cloneTask();
//		task.setUrl(url.toString());
//		task.setMadeby(MakeBy.RUNTIME);
//		TaskFetcher.instance.addTask(task);
//	}

	@Override
	public String getXmlName() {
		// TODO Auto-generated method stub
		return "baike_train_pic";
	}

	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return "baike";
	}

	@Override
	protected Semaphore getMutex() {
		// TODO Auto-generated method stub
		return mutex;
	}

}
