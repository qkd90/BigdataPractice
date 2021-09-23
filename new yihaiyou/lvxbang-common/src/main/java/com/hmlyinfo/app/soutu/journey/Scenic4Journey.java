package com.hmlyinfo.app.soutu.journey;


import java.util.ArrayList;
import java.util.List;

/**
 * 类Scenic4Journey存放类HotelJourney返回的结果，其中day为主要的数据。
 * public List<Integer> getDay(int iday)函数取出的是每一天的行程。如果输入非法天数，返回空的ArrayList<Integer>()
 * public int getScenic(int iday,int iscenic)函数取出的是每一天的某一个行程，如果输入非法天数，返回0；
 * public int size()函数返回的是这个行程有几天
 */
public class Scenic4Journey {

	public Scenic4Journey() {
		this.size = 0;
	}

	private List<List<Integer>> day;
	private double timeInDay;     //平均每天所花费的时间
	private List<Double> timePlanInDay;     //优化后，每天所要游玩的时间

	public List<Double> getTimePlanInDay() {
		return timePlanInDay;
	}

	public void setTimePlanInDay(List<Double> timePlanInDay) {
		this.timePlanInDay = timePlanInDay;
	}

	private double size;

	public void setSize(double size) {
		this.size = size;
	}

	public double getTimeInDay() {
		return timeInDay;
	}

	public void setTimeInDay(double timeInDay) {
		this.timeInDay = timeInDay;
	}

	public List<List<Integer>> getDay() {
		return day;
	}

	public void setDay(List<List<Integer>> day) {
		this.day = day;
	}

	public List<Integer> getDay(int iday) {
		if ((iday < day.size()) && (iday >= 0)) {
			return day.get(iday);
		}

		return new ArrayList<Integer>();
	}

	public int getScenic(int iday, int iscenic) {
		if ((iday < day.size()) && (iday >= 0)) {
			if ((iscenic < day.get(iday).size()) && (iscenic >= 0)) {
				return day.get(iday).get(iscenic);
			}
		}

		return 0;
	}

	public double size() {
		return this.size;
	}

	public void clear() {
		this.day.clear();
		this.size = 0;
		this.timeInDay = 0;
		this.timePlanInDay.clear();
	}
}
