package com.hmlyinfo.app.soutu.journey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 构造函数public SearchJourney(List<Integer> journey,List<CarTimeBetween> carTimeBetweenSet)
 * journey存放景点信息，carTimeBetweenSet存放景点间的驾车时间，需要出发景点，到达景点。
 * 搜素函数public List<Integer> Search()
 * 输出结果为一个长度和景点数量相同的数组，第一个点表示一个任意的景点（由于输出结果是一个环，不影响），然后每一个点是
 * 前一个景点的后继结点，最后一个点的后继结点是第一个点
 */

public class SearchJourney {
	private Map<String, Integer> timeMap;
	private List<CarTimeBetween> CarTimeBetweenSet;
	private List<Long> Journey;    //存放tripId
	private Map<Long, String> scenicMap;//存放景点Id
	private String origin;
	private double saveTime;

	private int numOfJourney;
	private static final int max = 999999;

	public double getSaveTime() {
		return saveTime;
	}

	private CarTimeBetween findMinTime(Long from) {            //找到CarTimeBetweenSet中从from发出的驾车时间最小的一项
		CarTimeBetween minCarTimeBetween = new CarTimeBetween();
		minCarTimeBetween.setFrom(from);
		minCarTimeBetween.setCost(max);
		for (Long to : Journey) {
			if (timeMap.get(from + "_" + to) != null && minCarTimeBetween.getCost() > timeMap.get(from + "_" + to)) {
				minCarTimeBetween.setCost(timeMap.get(from + "_" + to));
				minCarTimeBetween.setTo(to);
			}
		}
		for (Long to : Journey) {
			timeMap.put(to + "_" + minCarTimeBetween.getFrom(), max);
		}
		return minCarTimeBetween;
	}

	private int sumOfOrder(Long[] order) {
		int sum = 0;
		for (int i = 0; i < numOfJourney; ++i) {
			if (i == numOfJourney - 1) {
				sum += getCost(order[i], order[0]);
			} else {
				sum += getCost(order[i], order[i + 1]);
			}
		}


		return sum;
	}

	private int sumOfListOrder(List<Long> listorder) {
		int sum = 0;
		for (int i = 0; i < numOfJourney; ++i) {
			if (i == numOfJourney - 1) {
				sum += getCost(listorder.get(i), listorder.get(0));
			} else {
				sum += getCost(listorder.get(i), listorder.get(i + 1));
			}

		}


		return sum;
	}


	private long getCost(Long from, Long to) {
		for (CarTimeBetween aCarTimeBetweenSet : CarTimeBetweenSet) {
			if ((aCarTimeBetweenSet.getFrom().equals(from)) && (aCarTimeBetweenSet.getTo().equals(to))) {
				return aCarTimeBetweenSet.getCost();
			}
		}
		return 0;

	}


	public SearchJourney(List<Long> journey, List<CarTimeBetween> carTimeBetweenSet, String origin, Map<Long, String> scenicMap) {
		//journey存放景点信息，carTimeBetweenSet存放景点间的驾车时间，需要出发景点，到达景点。
		Journey = journey;
		this.scenicMap = scenicMap;
		CarTimeBetweenSet = carTimeBetweenSet;
		numOfJourney = Journey.size();
		this.saveTime = 0;
		this.origin = origin;
		beforSearchTime();
	}

	private void beforSearchTime()    //计算未规划行程时，花费的时间
	{
		for (int i = 1; i < Journey.size(); i++) {
			this.saveTime += getCost(Journey.get(i - 1), Journey.get(i));
		}
	}

	public List<String> Search() {/*输出结果为一个长度和景点数量相同的数组，第一个点表示一个任意的景点（由于输出结果是一个环，不影响），然后每一个点是
	    前一个景点的后继结点，最后一个点的后继结点是第一个点
		*/
		if (numOfJourney < 1) {
			return new ArrayList<String>();
		}

		List<Long> listOrder = new ArrayList<Long>();

		for (int j = 0; j < numOfJourney; j++) {
			if (origin != null && !origin.equals(scenicMap.get(Journey.get(j)))) {
				continue;
			}
			Long[] orderIds = new Long[numOfJourney];
			timeMap = new HashMap<String, Integer>();
			for (CarTimeBetween aCarTimeBetweenSet : CarTimeBetweenSet) {
				CarTimeBetween temp = aCarTimeBetweenSet.myclone();
				timeMap.put(temp.getFrom() + "_" + temp.getTo(), temp.getCost());
			}


			orderIds[0] = Journey.get(j);
			for (int i = 1; i < numOfJourney; ++i) {//在所有路程中找一个最短的，由于每个点只能进出一次，那么一旦选出一条路线，有一个点发出，另一个点进入，那么这两个点
				//的发出和进入信息赋值为最大
				CarTimeBetween minCarTimeBetween = findMinTime(orderIds[i - 1]);
				orderIds[i] = minCarTimeBetween.getTo();
			}
			if (listOrder.isEmpty()) {
				listOrder.addAll(Arrays.asList(orderIds).subList(0, numOfJourney));
			} else if (sumOfOrder(orderIds) < sumOfListOrder(listOrder)) {
				listOrder.clear();
				listOrder.addAll(Arrays.asList(orderIds).subList(0, numOfJourney));
			}
		}

		List<String> result = new ArrayList<String>();
		for (int i = 0; i < listOrder.size(); i++) {
			result.add(scenicMap.get(listOrder.get(i)));
			if (i == 0) {
				continue;
			}
			this.saveTime -= getCost(listOrder.get(i - 1), listOrder.get(i));
		}


		return result;
	}
}
