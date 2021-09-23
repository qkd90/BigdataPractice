package com.hmlyinfo.app.soutu.journey;


import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 构造函数public JourneyInDay(Map<Integer,Double> scenicAndTime,List<Integer> allJourney,List<CarTimeBetween> carTimeBetweenSet,double timeWantPlay)
 * 第一个参数，每个景点所对应的建议游玩时间，时间单位为小时
 * 第二个参数，所有景点的列表，这个景点列表是SearchJourney类中Search()函数的返回值，即已经有一个初步规划
 * 第三个参数，CarTimeBetween是每两个景点间的路程损耗
 * public Scenic4Journey search()默认搜索函数
 * 根据mode的内容来判断用哪个搜索函数，mode默认为0，其搜索内容为：
 * 需要的是构造函数的那三个参数为基本相，返回的结果是一个Scenic4Journey类，
 * 这个类里面包含了对景点的每一天的分割，这个分割不考虑构造函数的那三个参数外的信息
 * mode=1时，表示搜索要考虑行程路上所要花费的时间
 * 无需其他输入参数，但是要确保构造函数中的第三个参数中CarTimeBetween类中的cost的内容为路上所花的时间，单位为秒
 */
public class JourneyInDay {

	public JourneyInDay(Map<Integer, Double> scenicAndTime, List<Integer> allJourney, List<CarTimeBetween> carTimeBetweenSet, double timeWantPlay) {
		this.scenicAndTime = scenicAndTime;
		this.allJourney = allJourney;
		this.timeWantPlay = timeWantPlay;
		this.carTimeBetweenSet = carTimeBetweenSet;
		numOfJourney = scenicAndTime.size();
		this.mode = 0;
	}


	private Map<Integer, Double> scenicAndTime;
	private List<Integer> allJourney;
	private double timeWantPlay;
	private List<CarTimeBetween> carTimeBetweenSet;
	private int numOfJourney;
	private int mode;


	public int getMode() {
		return mode;
	}

	public void setMode(int mode) {
		this.mode = mode;
	}

	private double sumOfPlayTime() {
		double sum = 0;
		for (int i = 0; i < numOfJourney; ++i) {
			sum += scenicAndTime.get(allJourney.get(i));
		}


		return sum;
	}

	private List<List<Integer>> getMaxCostList(List<Integer> scenicOrder) {
		List<List<Integer>> res = new ArrayList<List<Integer>>();

		res.add(getMaxCost(scenicOrder));
		res.add(getMaxCost(scenicOrder, res.get(0).get(2)));
		res.add(getMaxCost(scenicOrder, res.get(1).get(2)));


		return res;
	}

	private List<Integer> getMaxCost(List<Integer> scenicOrder) {
		List<Integer> res = new ArrayList<Integer>();
		int max = 0;
		int from = 0;
		int to = 0;
		int temp = 0;
		for (int i = 1; i < scenicOrder.size(); ++i) {
			temp = getcost(scenicOrder.get(i - 1), scenicOrder.get(i));
			if (temp > max) {
				from = scenicOrder.get(i - 1);
				to = scenicOrder.get(i);
				max = temp;
			}
		}

		res.add(from);
		res.add(to);
		res.add(max);


		return res;
	}

	private List<Integer> getMaxCost(List<Integer> scenicOrder, int less) {
		List<Integer> res = new ArrayList<Integer>();
		int max = 0;
		int from = 0;
		int to = 0;
		int temp = 0;
		for (int i = 1; i < scenicOrder.size(); ++i) {
			temp = getcost(scenicOrder.get(i - 1), scenicOrder.get(i));
			if ((temp > max) && (temp < less)) {
				from = scenicOrder.get(i - 1);
				to = scenicOrder.get(i);
				max = temp;
			}
		}

		res.add(from);
		res.add(to);
		res.add(max);


		return res;
	}

	private int getcost(int from, int to) {
		for (int i = 0; i < carTimeBetweenSet.size(); ++i) {
			if ((carTimeBetweenSet.get(i).getFrom() == from) && (carTimeBetweenSet.get(i).getTo() == to)) {
				return carTimeBetweenSet.get(i).getCost();
			}
		}
		return 0;

	}

	private Scenic4Journey searchWithNewJourney(List<Integer> newJourney, double timeInDay, int less, int more) {
		Scenic4Journey res = new Scenic4Journey();
		List<List<Integer>> day = new ArrayList<List<Integer>>();
		double lessTimeInDay = timeInDay - less;
		double moreTimeInDay = timeInDay + more;
		int itOfAllJourney = 0;
		List<Double> timePlanInDay = new ArrayList<Double>();
		res.setTimeInDay(timeInDay);
		for (int i = 0; i < timeWantPlay; ++i) {
			List<Integer> everyDay = new ArrayList<Integer>();
			double nowTime = 0;
			while (true) {
				if (itOfAllJourney >= newJourney.size()) {
					day.add(everyDay);
					timePlanInDay.add(nowTime);
					res.setDay(day);
					res.setTimePlanInDay(timePlanInDay);
					return res;
				}
				nowTime += scenicAndTime.get(newJourney.get(itOfAllJourney));
				everyDay.add(newJourney.get(itOfAllJourney));
				if (itOfAllJourney >= newJourney.size() - 1) {
					day.add(everyDay);
					timePlanInDay.add(nowTime);
					res.setDay(day);
					res.setTimePlanInDay(timePlanInDay);
					return res;
				}
				if (nowTime > lessTimeInDay) {
					if (nowTime + scenicAndTime.get(newJourney.get(itOfAllJourney + 1)) < moreTimeInDay) {
						itOfAllJourney++;
						continue;
					} else {
						day.add(everyDay);
						timePlanInDay.add(nowTime);
						itOfAllJourney++;
						break;
					}
				}
				itOfAllJourney++;
			}
		}


		res.setDay(day);
		res.setTimePlanInDay(timePlanInDay);


		return res;
	}

	private double varianceOfDayTime(List<Double> dayTime, double time) {
		double res = 0;

		for (int i = 0; i < dayTime.size(); ++i) {
			res += (dayTime.get(i) - time) * (dayTime.get(i) - time);
		}


		return res;

	}


	public Scenic4Journey search() {
		if (this.mode == 0) {
			return searchBasic();
		}
		if (this.mode == 1) {
			return searchWithTimeOnRoad();
		}


		return new Scenic4Journey();
	}

	private Scenic4Journey searchWithTimeOnRoad() {
		Scenic4Journey res = new Scenic4Journey();

		Scenic4Journey temp = new Scenic4Journey();

		double time = sumOfPlayTime();    //所有景点的建议游玩时间的和
		//timeWantPlay


		double timeInDay = time / timeWantPlay;

		List<List<Integer>> bigCost = getMaxCostList(allJourney);    //
		List<Integer> newJourney = new ArrayList<Integer>();

		for (int i = 0; i < 3; ++i) {
			newJourney.addAll(allJourney.subList(allJourney.indexOf(bigCost.get(i).get(1)), allJourney.size()));
			newJourney.addAll(allJourney.subList(0, allJourney.indexOf(bigCost.get(i).get(1))));
			System.out.println("newJourney" + i + ":" + newJourney);
			int more = 1;
			int less = 1;
			while (true) {
				temp = searchWithNewJourney(newJourney, timeInDay, less, more);
				int count = 0;
				for (int ii = 0; ii < temp.getTimePlanInDay().size(); ii++) {
					count += temp.getDay(ii).size();
				}
				if (timeWantPlay > temp.getTimePlanInDay().size()) {
					less++;
					temp = searchWithNewJourney(newJourney, timeInDay, less, more);
				} else if (count < newJourney.size()) {
					more++;
					temp = searchWithNewJourney(newJourney, timeInDay, less, more);
				} else {
					break;
				}
			}
			System.out.println("每天的分割:" + temp.getDay());
			System.out.println("每天的时间:" + temp.getTimePlanInDay());

			//每天的时间加上路上的消耗
			temp.setSize(timeWantPlay);
			temp = withTimeOnRoad(temp);
			System.out.println("每天的分割计算路上时间:" + temp.getDay());
			System.out.println("每天的时间计算路上时间:" + temp.getTimePlanInDay());
			System.out.println("平均时间：" + temp.getTimeInDay());
			System.out.println("方差：" + varianceOfDayTime(temp.getTimePlanInDay(), temp.getTimeInDay()));

			//每天的时间加上路上的消耗 结束
			if (res.size() == 0) {
				res = temp;
				res.setSize(timeWantPlay);
			} else if (varianceOfDayTime(res.getTimePlanInDay(), res.getTimeInDay()) > varianceOfDayTime(temp.getTimePlanInDay(), temp.getTimeInDay())) {
				res.clear();
				res = temp;
				res.setSize(timeWantPlay);
			}
			newJourney.clear();


		}

		return res;
	}

	private Scenic4Journey searchBasic() {
		Scenic4Journey res = new Scenic4Journey();

		Scenic4Journey temp = new Scenic4Journey();

		double time = sumOfPlayTime();    //所有景点的建议游玩时间的和
		//timeWantPlay


		double timeInDay = time / timeWantPlay;


		List<List<Integer>> bigCost = getMaxCostList(allJourney);    //
		List<Integer> newJourney = new ArrayList<Integer>();

		for (int i = 0; i < 3; ++i) {
			newJourney.addAll(allJourney.subList(allJourney.indexOf(bigCost.get(i).get(1)), allJourney.size()));
			newJourney.addAll(allJourney.subList(0, allJourney.indexOf(bigCost.get(i).get(1))));
			System.out.println("newJourney" + i + ":" + newJourney);

			temp.setSize(timeWantPlay);
			int more = 1;
			int less = 1;
			while (true) {
				temp = searchWithNewJourney(newJourney, timeInDay, less, more);
				int count = 0;
				for (int ii = 0; ii < temp.getTimePlanInDay().size(); ii++) {
					count += temp.getDay(ii).size();
				}
				if (timeWantPlay > temp.getTimePlanInDay().size()) {
					less++;
					temp = searchWithNewJourney(newJourney, timeInDay, less, more);
				} else if (count < newJourney.size()) {
					more++;
					temp = searchWithNewJourney(newJourney, timeInDay, less, more);
				} else {
					break;
				}
			}

			System.out.println("每天的分割:" + temp.getDay());
			System.out.println("每天的时间:" + temp.getTimePlanInDay());


			if (res.size() == 0) {
				res = temp;
				res.setSize(timeWantPlay);
			} else if (varianceOfDayTime(res.getTimePlanInDay(), res.getTimeInDay()) > varianceOfDayTime(temp.getTimePlanInDay(), temp.getTimeInDay())) {
				res.clear();
				res = temp;
				res.setSize(timeWantPlay);
			}
			newJourney.clear();

		}

		return res;
	}

	private Scenic4Journey withTimeOnRoad(Scenic4Journey sj) {
		for (int i = 0; i < sj.size(); i++) {
			List<Integer> day = sj.getDay(i);

			if (day.size() > 1) {
				for (int j = 0; j < day.size() - 1; j++) {
					double temp = (tripTime(day.get(j), day.get(j + 1)) + sj.getTimePlanInDay().get(i));
					BigDecimal bg = new BigDecimal(temp);
					sj.getTimePlanInDay().set(i, bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
				}
			}
		}
		double sum = 0;
		for (int i = 0; i < sj.size(); i++) {
			sum += sj.getTimePlanInDay().get(i);
		}
		double temp = (double) sum / sj.size();
		BigDecimal bg = new BigDecimal(temp);
		sj.setTimeInDay(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		return sj;
	}

	private double tripTime(int from, int to) {
		double res = 0;
		for (int i = 0; i < carTimeBetweenSet.size(); i++) {
			if ((carTimeBetweenSet.get(i).getFrom() == from) && (carTimeBetweenSet.get(i).getTo() == to)) {
				int temp = carTimeBetweenSet.get(i).getCost();
				res = (double) temp / 3600;
				BigDecimal bg = new BigDecimal(res);
				return bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue();
			}
		}

		return res;

	}

}
