package com.data.data.service.optimize.process.plan;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.log4j.Logger;

import com.data.data.service.optimize.PlanHourType;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.Point;

public class PlanScenic implements Callable<PlanScenic> {

	// public PriorityScenics

	protected int							costTime;
	protected List<Integer>					areas					= new ArrayList<Integer>();
	private final static ExecutorService	pool					= Executors.newCachedThreadPool();
	protected CountDownLatch				downLatch;
	protected List<Point>					points;
	protected int							days;
	protected String						cityCode;
	public PlanScenicPriority				planScenicPriority;
	protected PlanScenic					planScenic;
	protected PlanHourType					planHourType;
	public final static int					canAppendScenicLeftTime	= 60;
	public final int						nearDistance			= 3000;
	private final static Logger				log						= Logger.getLogger(PlanScenic.class);

	public PlanScenic() {
		// TODO Auto-generated constructor stub
	}

	public PlanScenic(List<Point> points, CountDownLatch downLatch, int days, PlanHourType planHourType, String cityCode) {
		this.points = points;
		this.downLatch = downLatch;
		this.days = days;
		planScenic = this;
		this.cityCode = cityCode;
		this.planHourType = planHourType;
	}

	public static PlanScenic planScenic(List<Point> points, int days, PlanHourType planHourType, String cityCode) {
		PlanScenic planScenic = new PlanScenic();
		CountDownLatch downLatch = new CountDownLatch(1);
		planScenic.planScenicPriority = new PlanScenicPriority(points, downLatch, days, planHourType, cityCode);
		pool.submit(planScenic.planScenicPriority);
		try {
			downLatch.await();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return planScenic;
	}

	@Override
	public PlanScenic call() throws Exception {
		// TODO Auto-generated method stub
		try {
			execute();
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		} finally {
			downLatch.countDown();
		}
		return planScenic;
	}

	protected void execute() {
		// TODO Auto-generated method stub

	}

}
