package com.data.spider.util;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.data.spider.process.BaiduBaikeTrainPictureProcess;
import com.data.spider.process.CncnscenicProcess;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.zuipin.util.PropertiesManager;

public class Spider {
	private AtomicLong					page	= new AtomicLong(0);//让Long的操作保持原子型
	private int							threads;//
	public static Semaphore				mutex;//信号(互斥)量
	private AtomicBoolean				hasNext	= new AtomicBoolean(true);//让Boolean的操作保持原子型
	private final static Log			log		= LogFactory.getLog(Spider.class);
	private static ApplicationContext	ac;
	private Boolean						run		= true;
	public static ThreadPoolExecutor	executorService;//线程池
	
	public Spider() {
		// TODO Auto-generated constructor stub
		ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		PropertiesManager propertiesManager = (PropertiesManager) ac.getBean("propertiesManager");
		threads = propertiesManager.getInteger("threads");
		mutex = new Semaphore(threads);//信号量(连接池，最大数)
	}
	public void start() {
		executorService = new ThreadPoolExecutor(threads, threads, 0L, TimeUnit.MILLISECONDS, new LinkedBlockingQueue<Runnable>()) {
			@Override
			protected void afterExecute(Runnable r, Throwable t) {
				try {
					//获取任务执行结果
					Datatask context = (Datatask) ((FutureTask<?>) r).get();
					if (context.getStatus() == DatataskStatus.SUCCESSED) {
						log.debug(context.getUrl());
					} else {
						context.setStatus(DatataskStatus.FAILED);
						context.setHtml("");
						// submit(new SpiderProcess(context));
					}
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (ExecutionException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally {
					mutex.release();
				}
			}
		};
		while (run) {
			try {
				mutex.acquire();//从该信号量获得许可,当无可用许可时候,则阻塞
				Datatask datatask = TaskFetcher.instance.getTask();//从data_tasks表中获取任务
				//执行任务 ?
				BaseSpiderProcess process = (BaseSpiderProcess) Class.forName(datatask.getClassname())
						.getConstructor(Datatask.class)
						.newInstance(datatask);
				executorService.submit(process);//线程池开始执行任务(某任务的call()开始执行)
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
		}
		//当队列中等待的任务不为0时，sleep 1秒，接着再次判断，接着循环。当为0时，跳出结束
		while (true) {
			if (mutex.getQueueLength() != 0) {
				try {
					Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			} else {
				break;
			}
		}
		executorService.shutdownNow();//关闭线程池
	}
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		new Spider().start();
	}
}
