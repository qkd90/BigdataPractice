package com.data.spider.process;

import java.util.concurrent.Semaphore;

import com.data.spider.service.DatataskService;
import com.data.spider.service.tb.TbTransportationService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.tb.TbTransportation;
import com.data.spider.util.BaseSpiderProcess;
import com.data.spider.util.QiniuUtil;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.SpringContextHolder;

/**
 * 汽车站图片地址更新为七牛地址
 * By ZZL
 * 2015.10.15
 *
 */
public class TransferImgToQiniuProcess extends BaseSpiderProcess {
	
	private final static DatataskService	datataskService		= SpringContextHolder.getBean("datataskService");
	private TbTransportationService tbTransportationService = SpringContextHolder.getBean("tbTransportationService");
	public final static Semaphore			mutex				= new Semaphore(5);//预设5个许可信号量

	public TransferImgToQiniuProcess(Datatask datatask) {
		super(datatask);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Datatask call() throws Exception {
		// TODO Auto-generated method stub
		try {
			if(mutex != null){
				mutex.acquire();
			}
			////将抓取的外部网站的图片上传到七牛云存储,并更新图片url地址
			String _url = QiniuUtil.UploadToQiniu(datatask.getUrl());
			if(_url != null){
				Criteria<TbTransportation> criteria = new Criteria<TbTransportation>(TbTransportation.class);
				criteria.eq("id", Long.parseLong(datatask.getTag()));
				TbTransportation transportation = tbTransportationService.gets(1, criteria).get(0);
				transportation.setCoverImg(_url);
				tbTransportationService.update(transportation);
				datatask.setStatus(DatataskStatus.SUCCESSED);
				datataskService.updateTask(datatask);
				System.err.println("车站图片更新成功!" + "/" + transportation.getName() + "/" + transportation.getCoverImg());
			}else {
				log.error("车站图片上传至七牛错误,车站id: "+datatask.getTag() + "/车站名称: " + datatask.getName());
				datatask.setStatus(DatataskStatus.FAILED);
				datataskService.updateTask(datatask);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			System.err.println("任务异常!检查任务属性!");
			datatask.setStatus(DatataskStatus.FAILED);
			datataskService.updateTask(datatask);
		}finally{
			if(mutex != null){
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
		return null;
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
