package com.data.spider.process;

import java.util.concurrent.Semaphore;

import com.data.spider.service.DatataskService;
import com.data.spider.service.tb.TbScenicInfoService;
import com.data.spider.service.baidu.BaiduGeoCoderService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.tb.TbScenicInfo;
import com.data.spider.service.pojo.baidu.GeoCoder.RederReverse;
import com.data.spider.util.BaseSpiderProcess;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.SpringContextHolder;
/**
 * 景点地址补全
 * By ZZL
 * 2015.10.13 
 */
public class ScenicAddressProcess extends BaseSpiderProcess {
	
	 private static String key = "PXhzqOZRCWLy6dzlwQuF3gpV";

	 private final static DatataskService	datataskService		= SpringContextHolder.getBean("datataskService");
	 private TbScenicInfoService 			tbScenicInfoService 	= SpringContextHolder.getBean("tbScenicInfoService");
	 public final static Semaphore			mutex				= new Semaphore(5);//预设5个许可信号量
	 
	public ScenicAddressProcess(Datatask datatask) {
		super(datatask);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Datatask call() throws Exception {
		// TODO Auto-generated method stub
		Semaphore mutex = getMutex();
		try {
			if(mutex != null){
				mutex.acquire();
			}
			String info = datatask.getInfo();
			String[] position = info.split(",");
			BaiduGeoCoderService s = new BaiduGeoCoderService();
			RederReverse reverse = s.getRederReverse(position[1], position[0], key);
			if(reverse != null){
				if(reverse.result.formatted_address != null && !reverse.result.formatted_address.equals("")){
					System.err.println(datatask.getName() + "/" + reverse.result.formatted_address);
					Criteria<TbScenicInfo> criteria = new Criteria<TbScenicInfo>(TbScenicInfo.class);
					criteria.eq("id", Long.parseLong(datatask.getTag()));
					TbScenicInfo scenicInfo = tbScenicInfoService.gets(1, criteria).get(0);
					scenicInfo.setAddress(reverse.result.formatted_address);
					tbScenicInfoService.update(scenicInfo);
					datatask.setStatus(DatataskStatus.SUCCESSED);
					datataskService.updateTask(datatask);
				}else {
					System.err.println("景点地址获取错误,经纬度不正确或者没有找到该景点! 景点id:　" + datatask.getTag() + "/景点名称: " + datatask.getName() + "/景点经纬度:　" + "(" + datatask.getInfo() + ")");
					datatask.setStatus(DatataskStatus.FAILED);
					datataskService.updateTask(datatask);
				}
			}else if(reverse == null){
				System.err.println("景点地址获取错误,经纬度不正确或者没有找到该景点! 景点id:　" + datatask.getTag() + "/景点名称: " + datatask.getName() + "/景点经纬度:　" + "(" + datatask.getInfo() + ")");
				datatask.setStatus(DatataskStatus.FAILED);
				datataskService.updateTask(datatask);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			log.error(e.getMessage(), e);
			datatask.setStatus(DatataskStatus.FAILED);
			datataskService.updateTask(datatask);
		}finally {
			if (mutex != null) {
				mutex.release();
			}
		}
		return datatask;
	}
	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	protected Semaphore getMutex() {
		// TODO Auto-generated method stub
		return mutex;
	}

	@Override
	public void execute() {
		// TODO Auto-generated method stub
	}

	@Override
	public String getXmlName() {
		// TODO Auto-generated method stub
		return null;
	}

}
