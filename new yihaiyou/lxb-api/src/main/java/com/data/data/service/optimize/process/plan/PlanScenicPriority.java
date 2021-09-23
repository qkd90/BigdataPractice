package com.data.data.service.optimize.process.plan;

import java.awt.geom.GeneralPath;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.log4j.Logger;

import com.data.data.service.AreaService;
import com.data.data.service.MyTspService;
import com.data.data.service.ScenicService;
import com.data.data.service.optimize.PlanHourType;
import com.data.data.service.optimize.process.DisUtils;
import com.data.data.service.pojo.Area;
import com.data.data.service.pojo.Dis;
import com.data.data.service.pojo.ScenicInfo;
import com.data.data.service.pojo.SolrDis;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.Point;
import com.zuipin.util.ArithUtil;
import com.zuipin.util.SpringContextHolder;

public class PlanScenicPriority extends PlanScenic {

	private final Map<Integer, LinkedList<Point>> daysPlay = new HashMap<Integer, LinkedList<Point>>();
	private final Map<Integer, LinkedList<Point>> resultPlay = new HashMap<Integer, LinkedList<Point>>();
	private final Map<Long, Area> areas = new HashMap<Long, Area>();
	/**
	 * 合并的区域
	 */
	private final List<Area> unitedNoFullArea = new ArrayList<Area>();
	private final List<Integer> unitedDays = new ArrayList<Integer>();
	private final LinkedList<Object> plans = new LinkedList<Object>();
	private final List<Area> fullAreas = new ArrayList<Area>();
	private final List<Area> noFullAreas = new ArrayList<Area>();
	private final List<Area> unitAreas = new ArrayList<Area>();
	private final static Logger log = Logger.getLogger(PlanScenicPriority.class);
	private final ScenicService scenicService = SpringContextHolder.getBean("scenicService");
	private final MyTspService tspService = SpringContextHolder.getBean("myTspService");
	private final static StopWatch stopWatch = new StopWatch();
	private final Map<Long, ScenicInfo> allScenics = new HashMap<Long, ScenicInfo>();
	private final Map<Integer, List<Point>> beContainsPoints = new HashMap<Integer, List<Point>>();
	private final Map<Integer, List<Point>> containsPoints = new HashMap<Integer, List<Point>>();
	private final static ExecutorService service = Executors.newCachedThreadPool();
	/**
	 * 一天超过这个天数
	 */
	private final static int aDayMinutus = 480;
	/**
	 * 一天超过这个天数
	 */
	private final static int canReAppendTime = -120;
	/**
	 * 不为空，为空
	 */
	private final Map<Integer, Integer> containDayMap = new HashMap<Integer, Integer>();

	public PlanScenicPriority(List<Point> points, CountDownLatch downLatch, int days, PlanHourType planHourType, String cityCode,
							  Map<Long, Point> parentPoints) {
		// TODO Auto-generated constructor stub
		super(points, downLatch, days, planHourType, cityCode, parentPoints);
	}

	@Override
	public void execute() {
		sortPoints(points);
		writePoints();
		addPointDisInfo();
		separateToDayPlay();
		// if (daysPlay.size() > days) {
		// // findPointAreas();
		// clearUpDayPlan();
		// } else if (daysPlay.size() < days) {
		// findPointAreas();
		// findAllCityScenics();
		// rebuildDayPlan();
		// } else {
		// // findPointAreas();
		// clearUpDayPlan();
		// // fillLastDayScenic();
		// }
		findPointAreas();
		findAllCityScenics();
		writeArea();
		spitFullAndNotFullArea();
		writeArea();
		if (fullAreas.size() + noFullAreas.size() <= days) {
			rebuildDayPlan();
		} else {
			unitAreas();
		}
		findTheSameAreaDaysAndReBuildIt();
		resortEveryDayPoint();
		dayPlanSort();
	}

	private void findTheSameAreaDaysAndReBuildIt() {

	}

	/**
	 * 高优先级主景点就近吸附排序，产生的中心围绕问题
	 */
	private void resortEveryDayPoint() {
		// TODO Auto-generated method stub

	}

	private void writeArea() {
		log.info("Areas:");
		for (Entry<Long, Area> entry : areas.entrySet()) {
			Area area = entry.getValue();
			writeAreaInfo(area);
		}
		log.info("Full Areas:");
		for (Area area : fullAreas) {
			writeAreaInfo(area);
		}
		log.info("NoFull Areas:");
		for (Area area : noFullAreas) {
			writeAreaInfo(area);
		}
	}

	private void writeAreaInfo(Area area) {
		StringBuilder sb = new StringBuilder();
		sb.append(String.format("%s[%d]    :", area.getName(), area.getLeftTime()));
		for (Point point : area.getPoints()) {
			sb.append(String.format(" %s[%d]", point.getName(), point.playHours));
		}
		log.info(sb.toString());
	}

	private boolean unitAreas() {
		int day = 1;
		List<Point> removeAblePoints = new ArrayList<Point>();
		removeAblePoints.addAll(points);
		do {
			LinkedList<Point> thisDayPoints = new LinkedList<Point>();
			if (removeAblePoints.size() == 0) {
				if (unitedNoFullArea.size() > 0) {
					Area area = unitedNoFullArea.remove(unitedNoFullArea.size() - 1);
					Area[] areas = spiltAreaToLeftAndRight(area);
					resultPlay.put(area.getDayIndex(), appendDayPoints(areas[0]));
					resultPlay.put(day++, appendDayPoints(areas[1]));
				} else {
					makeOneDayPlan(day++, thisDayPoints);
				}
				continue;
			}
			Point first = removeAblePoints.get(0);
			Area area = first.getUnitArea();
			if (area.getIsFull()) {
				for (Point point : area.getPoints()) {
					thisDayPoints.add(point);
				}
				fullAreas.remove(area);
			} else {
				List<Point> areaPoints = area.getPoints();
				Point lastPoint = null;
				if (areaPoints.size() == 1) {
					lastPoint = first;
				} else {
					List<Point> tspPoints = tspService.tspDis(areaPoints.subList(1, areaPoints.size()), first);
					lastPoint = tspPoints.get(tspPoints.size() - 1);
				}
				if (removeAblePoints.size() > 1) {
					List<Point> pointWithoutThisArea = new ArrayList<Point>();
					pointWithoutThisArea.addAll(removeAblePoints);
					pointWithoutThisArea.removeAll(area.getPoints());
					if (pointWithoutThisArea.size() == 0) {
						thisDayPoints.addAll(area.getPoints());
					} else {
						Area unitArea = findNextMergeArea(area.getLeftTime(), lastPoint, pointWithoutThisArea);
						Area tempArea = mergeArea(area, unitArea);
						tempArea.setDayIndex(day);
						if (tempArea.getIsFull()) {
							thisDayPoints.addAll(tempArea.getPoints());
							noFullAreas.remove(area);
							if (unitArea.getIsFull()) {
								fullAreas.remove(unitArea);
							} else {
								noFullAreas.remove(unitArea);
							}
						} else {
							unitedNoFullArea.add(tempArea);
							noFullAreas.remove(area);
							if (unitArea.getIsFull()) {
								fullAreas.remove(unitArea);
							} else {
								noFullAreas.remove(unitArea);
							}
							for (Point point : area.getPoints()) {
								point.setUnitArea(tempArea);
							}
							for (Point point : unitArea.getPoints()) {
								point.setUnitArea(tempArea);
							}
							noFullAreas.add(tempArea);
							continue;
						}
					}
				} else {
					thisDayPoints.add(first);
				}
			}
			// removeAblePoints.add(0, first);
			removeAblePoints.removeAll(thisDayPoints);
			resultPlay.put(day++, thisDayPoints);

		} while (day <= this.days);

		return false;
	}

	private Area findNextMergeArea(int leftTime, Point lastPoint, List<Point> pointWithoutThisArea) {
		// TODO Auto-generated method stub
		Point centerPoint = findTheAreaCenter(lastPoint.getUnitArea());
		List<Point> leftPointTsps = tspService.tspXYDis(pointWithoutThisArea, centerPoint);

		List<Point> thisDayPoints = new ArrayList<Point>();

		Point newAreaNestestPoint = leftPointTsps.get(0);
		// for (int i = 1; i < leftPointTsps.size(); i++) {
		// Point point = areaPoints.get(i);
		// end = point;
		// SolrDis dis = DisUtils.getPointToPointDis(from, end);
		// if (leftTime - point.playHours - dis.getTaxi_time() > 0) {
		// thisDayPoints.add(point);
		// } else {
		// break;
		// }
		// leftTime -= point.playHours + dis.getTaxi_time();
		// from = point;
		// if (leftTime < canAppendScenicLeftTime) {
		// break;
		// }
		// }
		Area unitArea = newAreaNestestPoint.getUnitArea();
		return unitArea;
	}

	private Point findTheAreaCenter(Area unitArea) {
		double x = 0;
		double y = 0;
		List<Point> points = unitArea.getPoints();
		for (Point point : points) {
			x = ArithUtil.add(x, point.x);
			y = ArithUtil.add(y, point.y);
		}
		x = ArithUtil.div(x, points.size());
		y = ArithUtil.div(y, points.size());
		return new Point(x, y);
	}

	private Area[] spiltAreaToLeftAndRight(Area area) {
		try {
			Area rightArea = area.getRightArea();
			Area lefArea = area.getLeftArea();
			Area tempArea = area.clone();
			tempArea.setIsTempArea(lefArea.getIsTempArea());
			tempArea.getPoints().removeAll(rightArea.getPoints());
			if (tempArea.getIsTempArea()) {
				unitedNoFullArea.add(tempArea);
			}
			return new Area[] { tempArea, rightArea };
		} catch (CloneNotSupportedException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	private Area findRootRightArea(Area rightArea) {
		// TODO Auto-generated method stub

		return rightArea != null && !rightArea.getIsTempArea() ? rightArea : findRootRightArea(rightArea.getRightArea());
	}

	private Area findRootLeftArea(Area left) {
		return left.getLeftArea() == null ? left : findRootLeftArea(left.getLeftArea());
	}

	private Area mergeArea(Area area, Area unitArea) {
		// TODO Auto-generated method stub
		log.info(" ====================== MERGE =======================");
		writeAreaInfo(area);
		writeAreaInfo(unitArea);
		Area newArea = new Area();
		newArea.setLeftArea(area);
		newArea.setRightArea(unitArea);
		newArea.setUnitAreas(new ArrayList<Area>());
		newArea.getUnitAreas().add(area);
		newArea.getUnitAreas().add(unitArea);
		newArea.setCityCode(area.getCityCode());
		newArea.setDescription(area.getDescription());
		newArea.setId(area.getId());
		newArea.setIsTempArea(true);
		newArea.setName(area.getName());
		newArea.setPoints(new ArrayList<Point>());
		newArea.getPoints().addAll(area.getPoints());
		newArea.getPoints().addAll(unitArea.getPoints());
		List<Point> sortPoints = newArea.getPoints();
		sortPoints(sortPoints);
		Point first = sortPoints.get(0);
		List<Point> subList = sortPoints.subList(1, sortPoints.size());
		List<Point> tspPoints = tspService.tspDis(subList, first);
		newArea.setPoints(tspPoints);
		int leftTime = countPointsLeftTime(tspPoints);
		newArea.setLeftTime(leftTime);
		if (leftTime <= canAppendScenicLeftTime) {
			newArea.setIsFull(true);
		} else {
			newArea.setIsFull(false);
		}
		writeAreaInfo(newArea);
		return newArea;
	}

	private int countPointsLeftTime(List<Point> tspPoints) {
		int leftTime = planHourType.getAvg();
		Point start = null;
		Point end = null;
		for (Point point : tspPoints) {
			if (start != null) {
				SolrDis dis = DisUtils.getPointToPointDis(start, end);
				leftTime -= point.playHours + dis.getTaxi_time();
			} else {
				leftTime -= point.playHours;
			}
		}
		return leftTime;
	}

	private LinkedList<Point> appendDayPoints(Area area) {
		LinkedList<Point> points = new LinkedList<Point>();
		points.addAll(area.getPoints());

		appendPoint(countPointsLeftTime(points), points, points.get(points.size() - 1));
		return points;
	}

	private void makeOneDayPlan(int day, LinkedList<Point> thisDayPoints) {
		resultPlay.put(day, thisDayPoints);
		int leftTime = planHourType.getAvg();
		Point first = null;
		first = findTopOrderNumScenic();
		thisDayPoints.add(first);
		Point from = first;
		Point end = from;
		leftTime -= first.playHours;
		appendPoint(leftTime, thisDayPoints, end);

	}

	private void spitFullAndNotFullArea() {
		for (Entry<Long, Area> entry : areas.entrySet()) {
			Area area = entry.getValue();
			List<Point> points = area.getPoints();
			sortPoints(points);
			rebuildDayPlan(points, area);
		}
	}

	private void rebuildDayPlan(List<Point> points, Area area) {

		List<Point> removeAblePoints = new ArrayList<Point>();
		removeAblePoints.addAll(points);
		do {
			writeResultPlan();
			sortPoints(removeAblePoints);
			int leftTime = planHourType.getAvg();
			Point first = null;
			first = removeAblePoints.remove(0);
			LinkedList<Point> thisDayPoints = new LinkedList<Point>();
			thisDayPoints.add(first);
			Point from = first;
			Point end = from;
			leftTime -= first.playHours;
			List<Point> tspDisPoints = tspService.tspDis(removeAblePoints, first);
			if (canBeADay(first)) {
				Area fullArea = new Area();
				fullArea.setCityCode(area.getCityCode());
				fullArea.setId(area.getId());
				fullArea.setName(area.getName());
				fullArea.setPoints(thisDayPoints);
				fullArea.setIsFull(true);
				fullAreas.add(fullArea);
				for (Point point : fullArea.getPoints()) {
					point.setUnitArea(fullArea);
					point.setUnitAreaList(fullAreas);
				}
				removeAblePoints.remove(first);
				continue;
			}
			for (int i = 1; i < tspDisPoints.size(); i++) {
				Point point = tspDisPoints.get(i);
				end = point;
				SolrDis dis = DisUtils.getPointToPointDis(from, end);
				int appendedTime = leftTime - point.playHours - dis.getTaxi_time();
				if (i > 1 && appendedTime > canReAppendTime) {
					thisDayPoints.add(point);
				} else {
					if (i == 1 && !canBeADay(point)) {
						thisDayPoints.add(point);
					} else {
						break;
					}
				}
				leftTime -= point.playHours + dis.getTaxi_time();
				from = point;
				removeAblePoints.remove(point);
				if (leftTime < canAppendScenicLeftTime) {
					break;
				}
			}
			Area fullArea = new Area();
			fullArea.setCityCode(area.getCityCode());
			fullArea.setId(area.getId());
			fullArea.setName(area.getName());
			fullArea.setPoints(thisDayPoints);
			fullArea.setLeftTime(leftTime);
			if (leftTime <= canAppendScenicLeftTime) {
				fullArea.setIsFull(true);
				fullAreas.add(fullArea);
				for (Point point : fullArea.getPoints()) {
					point.setUnitArea(fullArea);
					point.setUnitAreaList(fullAreas);
				}
			} else {
				fullArea.setIsFull(false);
				noFullAreas.add(fullArea);
				for (Point point : fullArea.getPoints()) {
					point.setUnitArea(fullArea);
					point.setUnitAreaList(noFullAreas);
				}
			}
		} while (removeAblePoints.size() > 0);

	}

	private boolean canBeADay(Point point) {
		return point.playHours >= aDayMinutus;
	}

	private void writePoints() {
		// TODO Auto-generated method stub
		StringBuilder sb = new StringBuilder();
		for (Point point : points) {
			sb.append(String.format("%d %s", point.scenicInfoId, point.getName()));
		}
	}

	private void findAllCityScenics() {
		// TODO Auto-generated method stub
		List<ScenicInfo> all = scenicService.findAll(cityCode);
		for (ScenicInfo scenicInfo : all) {
			allScenics.put(scenicInfo.getId(), scenicInfo);
		}
	}

	private void dayPlanSort() {
		writeResultPlan();
		Map<Long, LinkedList<Point>> seq = new HashMap<Long, LinkedList<Point>>();
		List<Point> points = new ArrayList<Point>();
		for (Entry<Integer, LinkedList<Point>> entry : resultPlay.entrySet()) {
			LinkedList<Point> dayPlan = entry.getValue();
			// sortPoints(dayPlan);
			Point point = dayPlan.get(0);
			seq.put(point.scenicInfoId, dayPlan);
			points.add(point);
		}
		writeResultPlan();
		sortPoints(points);
		Point first = points.remove(0);
		points = tspService.tspDis(points, first);
		for (int i = 0; i < points.size(); i++) {
			LinkedList<Point> value = seq.get(points.get(i).scenicInfoId);

			resultPlay.put(i + 1, value);
		}
		writeResultPlan();
	}

	/**
	 * 填充最后一天时间
	 */
	private void fillLastDayScenic() {
		// resultPlay.putAll(daysPlay);
		LinkedList<Point> points = resultPlay.get(days);
		Point from = points.get(0);
		Point end = from;
		int left = planHourType.getAvg() - from.playHours;
		if (left > canAppendScenicLeftTime) {
			for (int i = 1; i < points.size(); i++) {
				end = points.get(i);
				SolrDis dis = DisUtils.getPointToPointDis(from, end);
				left -= dis.getTaxi_time() + end.playHours;
				from = end;
				if (left < canAppendScenicLeftTime) {
					break;
				}
			}
			if (left > canAppendScenicLeftTime) {
				appendScenic(left, end);
			}
		}
	}

	private void rebuildDayPlan() {
		int day = 1;
		List<Point> removeAblePoints = new ArrayList<Point>();
		removeAblePoints.addAll(points);
		do {
			writeResultPlan();
			int leftTime = planHourType.getAvg();
			Point first = null;
			if (removeAblePoints.size() > 0) {
				first = removeAblePoints.remove(0);
			} else {
				first = findTopOrderNumScenic();
				if (first == null) {
					break;
				} else {
					leftTime -= first.playHours;
					LinkedList<Point> thisDayPoints = new LinkedList<Point>();
					thisDayPoints.add(first);
					appendPoint(leftTime, thisDayPoints, first);
					resultPlay.put(day, thisDayPoints);
					continue;
				}
			}
			Area area = null;
			try {
				area = areas.get(first.getArea().getId());
				writeAreaInfo(area);
			} catch (Exception e) {
				// TODO: handle exception
				log.error(e.getMessage(), e);
			}
			List<Point> areaPoints = null;
			if (area != null) {
				areaPoints = area.getPoints();
				sortPoints(areaPoints);
				areaPoints.remove(first);
			}
			LinkedList<Point> thisDayPoints = new LinkedList<Point>();
			thisDayPoints.add(first);
			Point from = first;
			Point end = from;
			leftTime -= first.playHours;
			resultPlay.put(day, thisDayPoints);
			if (areaPoints != null && areaPoints.size() > 1) {
				// sortPoints(areaPoints);
				List<Point> tspDisPoints = tspService.tspDis(areaPoints, first);
				for (int i = 1; i < tspDisPoints.size(); i++) {
					Point point = areaPoints.get(i);
					end = point;
					SolrDis dis = DisUtils.getPointToPointDis(from, end);
					if (leftTime - point.playHours - dis.getTaxi_time() > 0) {
						thisDayPoints.add(point);
					} else {
						break;
					}
					leftTime -= point.playHours + dis.getTaxi_time();
					from = point;
					removeAblePoints.remove(point);
					if (leftTime < canAppendScenicLeftTime) {
						break;
					}
				}
			} else {
				if (!canBeADay(first)) {
					appendPoint(leftTime, thisDayPoints, end);
				}
			}

		} while (++day <= days);
		// findBePlanedPointsInTheSameArea();
	}

	private void writeResultPlan() {
		// TODO Auto-generated method stub
		for (Entry<Integer, LinkedList<Point>> entry : resultPlay.entrySet()) {
			StringBuilder sb = new StringBuilder();
			for (Point point : entry.getValue()) {
				sb.append(point.getName() + " ");
			}
			log.info(String.format("%d %s", entry.getKey(), sb.toString()));
		}
	}

	private Point findTopOrderNumScenic() {
		Set<Long> ids = findBePlanedIds(null);
		ScenicInfo scenicInfo = scenicService.findCityTopScenic(cityCode, ids);
		log.info(String.format("%d %sBe Found", scenicInfo.getId(), scenicInfo.getName()));
		return scenicInfo == null ? null : AreaService.instance.changeScenicInfoToPoint(scenicInfo);
	}

	private Set<Long> findBePlanedIds(LinkedList<Point> thisDayPoints) {
		Set<Long> ids = new HashSet<Long>();
		log.info("======================================================");
		StringBuilder sb = new StringBuilder();
		for (Entry<Integer, LinkedList<Point>> entry : resultPlay.entrySet()) {
			for (Point point : entry.getValue()) {
				ids.add(point.scenicInfoId);
				sb.append("[" + point.scenicInfoId + "," + point.getName() + "]");
			}
		}
		if (thisDayPoints != null) {
			for (Point point : thisDayPoints) {
				ids.add(point.scenicInfoId);
				sb.append("[" + point.scenicInfoId + "," + point.getName() + "]");
			}
		}
		List<Long> beAdd = new ArrayList<Long>();
		for (Entry<Long, ScenicInfo> entry : allScenics.entrySet()) {
			ScenicInfo scenicInfo = entry.getValue();
			for (Long id : ids) {
				if (id.equals(scenicInfo.getFather())) {
					beAdd.add(scenicInfo.getId());
					sb.append("(" + scenicInfo.getId() + "," + scenicInfo.getName() + ")");
				}
			}
		}
		ids.addAll(beAdd);
		log.info(sb.toString());
		log.info("======================================================");
		return ids;
	}

	private void appendPoint(int leftTime, LinkedList<Point> thisDayPoints, Point end) {
		// TODO Auto-generated method stub
		if (leftTime < canAppendScenicLeftTime) {
			return;
		}
		Set<Long> ids = findBePlanedIds(thisDayPoints);
		while (leftTime >= canAppendScenicLeftTime) {
			SolrDis dis = DisUtils.findNearPointScenic(ids, end, leftTime);
			if (dis != null) {
				leftTime -= dis.getTaxi_time() + dis.getAdvice_hours();
				Point point = findPointFromDis(dis);
				thisDayPoints.add(point);
				end = point;
				ids.add(dis.getE_id());
			} else {
				break;
			}
		}
	}

	private int countAreaDay(List<Area> areas) {
		// TODO Auto-generated method stub
		return 0;
	}

	private List<Area> findPointAreas() {
		for (Point point : points) {
			try {
				final Long areaId = point.getArea().getId();
				if (areas.containsKey(areaId)) {
					areas.get(areaId).getPoints().add(point);
				} else {
					areas.put(areaId, point.getArea());
					point.getArea().setPoints(new ArrayList<Point>());
					point.getArea().getPoints().add(point);
				}
			} catch (Exception e) {
				// TODO: handle exception
				log.error(e.getMessage(), e);
			}
		}
		return null;
	}

	/**
	 * 针对高优先级景点做旅行商排天，第二天从优先级最高景点开始
	 */
	private void clearUpDayPlan() {
		makeTopPriorityScenics();

		// 旅行商,按优先级游玩，违背就近游玩不规则，并且会产生附近近战不玩的分区
		// List<Point> points = makeTopPriorityScenics();
		// stopWatch.reset();
		// stopWatch.start();
		// if (points.size() > 3) {
		// tspAndFindBestRound(points);
		// } else {
		// Point point = points.remove(0);
		// List<Point> tspPoints = null;
		// tspPoints = tspService.tsp(points, point);
		// }
		// log.info(String.format("tsp Cost %s", stopWatch));
		// ---------------------------
		// 高优先级景点做旅行商---------------------------------
		// addByTopPriorityAndTsp(points);
		// -----------------------------------

		// 按游玩景点归属区域划分------------------------------------
		// this.points = points;
		findPointAreas();
		findAllCityScenics();
		rebuildDayPlan();
		// ------------------------------------
	}

	public void tspAndFindBestRound(List<Point> points) {
		final CountDownLatch down = new CountDownLatch(3);
		List<Future<TspResult>> list = new ArrayList<Future<TspResult>>();
		for (int i = 0; i < 3; i++) {
			List<Point> removeAble = new ArrayList<Point>();
			removeAble.addAll(points);
			for (int j = 0; j < i; j++) {
				removeAble.remove(removeAble.size() - 1);
			}
			sortPoints(removeAble);
			Future<TspResult> future = service.submit(new TspRunnable(i, removeAble, tspService, planHourType, down));
			list.add(future);
		}
		try {
			down.await();
		} catch (Exception e) {
			// TODO: handle exception
			log.error(e.getMessage(), e);
		}
		checkIfTspRoundIsBest(list);
		// 没有找到最佳结果
		if (resultPlay.size() == 0) {
			findMinDayCostTspIndex(list);
		}
	}

	private int findMinDayCostTspIndex(List<Future<TspResult>> list) {
		// TODO Auto-generated method stub
		Map<Integer, LinkedList<Point>> result = null;
		int max = -1;
		for (int i = 0; i < list.size(); i++) {
			try {
				TspResult res = list.get(i).get();
				Map<Integer, LinkedList<Point>> dayPlan = res.getResult();
				if (max == -1) {
					max = res.getIndex();
					result = dayPlan;
				} else {
					if (res.getIndex() > max) {
						max = res.getIndex();
						result = dayPlan;
					}
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		for (int i = 1; i <= days; i++) {
			resultPlay.put(i, result.get(i));
		}
		return 0;
	}

	public void checkIfTspRoundIsBest(List<Future<TspResult>> list) {
		for (Future<TspResult> future : list) {
			try {
				TspResult result = future.get();
				Map<Integer, LinkedList<Point>> dayPlan = result.getResult();
				if (dayPlan.size() == days) {
					for (int i = 1; i <= days; i++) {
						resultPlay.put(i, dayPlan.get(i));
					}
					break;
				}
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ExecutionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}
	}

	private void balanceDayTimeCost() {
		writeResultPlan();
		Map<Integer, Integer> dayCost = new TreeMap<Integer, Integer>();
		for (Integer day : containsPoints.keySet()) {
			int cost = 0;
			for (Point point : resultPlay.get(day)) {
				cost += point.playHours;
			}
			dayCost.put(cost, day);
		}
		reDispathZeroDay(dayCost);
		balanceOnePointDay(dayCost);
	}

	/**
	 * 点数为1的点 重新加一个点 近似时间平衡
	 *
	 * @param dayCost
	 */
	private void balanceOnePointDay(Map<Integer, Integer> dayCost) {
		for (Entry<Integer, List<Point>> entry : beContainsPoints.entrySet()) {
			List<Point> point = resultPlay.get(entry.getKey());
			if (point.size() == 1) {
				Entry<Integer, Integer> lastItem = null;
				for (Entry<Integer, Integer> item : dayCost.entrySet()) {
					lastItem = item;
				}
				Integer day = lastItem.getValue();
				List<Point> points = tspService.tspDis(resultPlay.get(day), point.get(0));
				Point mvPoint = points.get(1);
				resultPlay.get(entry.getKey()).add(mvPoint);
				resultPlay.get(day).remove(mvPoint);
			}
		}
	}

	/**
	 * 点数为零的点重新划分，可能存在一天里 包含另一天所有景点，故而产生此情况
	 *
	 * @param dayCost
	 */
	private void reDispathZeroDay(Map<Integer, Integer> dayCost) {
		for (Entry<Integer, List<Point>> entry : beContainsPoints.entrySet()) {
			List<Point> points = resultPlay.get(entry.getKey());
			if (points.size() == 0) {
				Integer day = containDayMap.get(entry.getKey());
				LinkedList<Point> dayScenics = resultPlay.get(day);
				Point point = countMaxDisPoint(dayScenics);
				dayScenics.remove(point);
				points.add(point);
			}
		}
	}

	private Point countMaxDisPoint(LinkedList<Point> dayScenics) {
		// TODO Auto-generated method stub
		List<SolrDis> diss = new ArrayList<SolrDis>();
		for (int i = 0; i < dayScenics.size(); i++) {
			for (int j = i + 1; j < dayScenics.size(); j++) {
				SolrDis dis = DisUtils.getPointToPointDis(dayScenics.get(i), dayScenics.get(j));
				diss.add(dis);
			}
		}
		Collections.sort(diss, new Comparator<SolrDis>() {
			@Override
			public int compare(SolrDis o1, SolrDis o2) {
				// TODO Auto-generated method stub
				return o1.getTaxi_time() - o2.getTaxi_time();
			}
		});
		SolrDis maxDis = diss.get(diss.size() - 1);
		return maxDis.getFrom();
	}

	/**
	 * 判断是否存在一个分区的点 在另外一个区域中间的情况 九宫格划分，在中间区块的时候，需要重新分配
	 *
	 * @return
	 */
	private void theAreaContainsOther() {
		// TODO Auto-generated method stub
		writeResultPlan();
		Map<Integer, LinkedList<Point>> map = findThePointNumGiggerThanOne();
		if (map.size() > 1) {
			Map<Integer, GeneralPath> paths = makePaths(map);
			for (Entry<Integer, LinkedList<Point>> entry : map.entrySet()) {
				for (Entry<Integer, GeneralPath> path : paths.entrySet()) {
					for (Point point : entry.getValue()) {
						Point2D.Double po = new Point2D.Double(point.x, point.y);
						if (path.getKey() != entry.getKey() && path.getValue().contains(po)) {
							log.info(String.format("%d contains %s %d", path.getKey(), point.getName(), entry.getKey()));
							List<Point> beContainsPointsList = beContainsPoints.get(entry.getKey());
							if (beContainsPointsList == null) {
								beContainsPointsList = new ArrayList<Point>();
								beContainsPoints.put(entry.getKey(), beContainsPointsList);
							}
							beContainsPointsList.add(point);

							List<Point> containsPointsList = containsPoints.get(path.getKey());
							if (containsPointsList == null) {
								containsPointsList = new ArrayList<Point>();
								containsPoints.put(path.getKey(), containsPointsList);
							}
							containsPointsList.add(point);
							containDayMap.put(entry.getKey(), path.getKey());
						}
					}

				}
			}
		}
	}

	private Map<Integer, GeneralPath> makePaths(Map<Integer, LinkedList<Point>> map) {
		Map<Integer, GeneralPath> paths = new HashMap<Integer, GeneralPath>();
		for (Entry<Integer, LinkedList<Point>> entry : map.entrySet()) {
			double minLon = Double.MAX_VALUE;
			double maxLon = 0;
			double minLat = Double.MAX_VALUE;
			double maxLat = 0;
			for (Point point : entry.getValue()) {
				minLon = Math.min(point.x, minLon);
				maxLon = Math.max(point.x, maxLon);
				minLat = Math.min(point.y, minLat);
				maxLat = Math.max(point.y, maxLat);
			}
			GeneralPath path = new GeneralPath();
			path.moveTo(minLon, minLat);
			path.lineTo(maxLon, minLat);
			path.lineTo(maxLon, maxLat);
			path.lineTo(minLon, maxLat);
			path.lineTo(minLon, minLat);
			paths.put(entry.getKey(), path);
		}
		return paths;
	}

	private Map<Integer, LinkedList<Point>> findThePointNumGiggerThanOne() {
		Map<Integer, LinkedList<Point>> map = new HashMap<Integer, LinkedList<Point>>();
		for (Entry<Integer, LinkedList<Point>> entry : resultPlay.entrySet()) {
			if (entry.getValue().size() > 1) {
				map.put(entry.getKey(), entry.getValue());
			}
		}
		return map;
	}

	/**
	 * 按高优先级景点就近吸附，会产生分天后的包含与被包含场景
	 *
	 * @param points
	 */
	private void addByTopPriorityAndTsp(List<Point> points) {
		// TODO Auto-generated method stub
		int day = 1;
		List<Point> removeAblePoints = new ArrayList<Point>();
		removeAblePoints.addAll(points);
		do {
			sortPoints(removeAblePoints);
			int leftTime = planHourType.getAvg();
			Point first = removeAblePoints.remove(0);
			leftTime -= first.playHours;
			LinkedList<Point> thisDayPlay = new LinkedList<Point>();
			thisDayPlay.add(first);
			resultPlay.put(day, thisDayPlay);
			if (removeAblePoints.size() > 0) {
				if (leftTime <= canAppendScenicLeftTime) {
					continue;
				}
				if (removeAblePoints.size() == 1) {
					thisDayPlay.addAll(removeAblePoints);
					continue;
				}
				List<Point> newSequence = tspService.tspDis(removeAblePoints, first);
				for (int i = 1; i < newSequence.size(); i++) {
					Point point = newSequence.get(i);
					SolrDis dis = DisUtils.getPointToPointDis(first, point);
					if (leftTime - point.playHours - dis.getTaxi_time() > canAppendScenicLeftTime) {
						leftTime -= point.playHours + dis.getTaxi_time();
						removeAblePoints.remove(point);
						thisDayPlay.add(point);
					} else {
						if (leftTime > canAppendScenicLeftTime && thisDayPlay.size() <= 4) {
							continue;
						} else {
							break;
						}
					}
				}
			}
		} while (++day <= days);
	}

	private void appendByArea(List<Point> points) {
		int day = 1;
		List<Point> removeAblePoints = new ArrayList<Point>();
		removeAblePoints.addAll(points);
		do {
			int leftTime = planHourType.getAvg();
			Point first = null;
			if (removeAblePoints.size() > 0) {
				first = removeAblePoints.remove(0);
			} else {
				first = findTopOrderNumScenic();
				if (first == null) {
					break;
				}
			}
			Area area = areas.get(first.getArea().getId());
			List<Point> areaPoints = null;
			if (area != null) {
				areaPoints = area.getPoints();
				sortPoints(areaPoints);
			}
			LinkedList<Point> thisDayPoints = new LinkedList<Point>();
			thisDayPoints.add(first);
			Point from = first;
			Point end = from;
			leftTime -= first.playHours;
			if (areaPoints != null && areaPoints.size() > 1) {
				for (int i = 1; i < areaPoints.size() && leftTime >= canAppendScenicLeftTime; i++) {
					Point point = areaPoints.get(i);
					end = point;
					if (i > 0) {
						SolrDis dis = DisUtils.getPointToPointDis(from, end);
						if (leftTime - point.playHours - dis.getTaxi_time() > 0) {
							thisDayPoints.add(point);
						} else {
							break;
						}
					} else {
						leftTime -= point.playHours;
					}
					leftTime -= point.playHours;
					from = point;
					removeAblePoints.remove(point);
					thisDayPoints.remove(point);
				}
			}
			appendPoint(leftTime, thisDayPoints, end);
			resultPlay.put(day, thisDayPoints);

		} while (++day <= days);
	}

	/**
	 * 去除低优先级景点，同时保证景点数能够填补剩余时间
	 *
	 * @return
	 */
	private List<Point> makeTopPriorityScenics() {
		int totalCost = 0;
		List<Point> cutPoints = new ArrayList<Point>();
		Point first = points.get(0);
		cutPoints.add(first);
		for (int i = 1; i < points.size(); i++) {
			Point end = points.get(i);
			if (end.getTripType() == TripType.RESTAURANT.value()) {
				cutPoints.add(end);
			}
			// SolrDis dis = DisUtils.getPointToPointDis(first, end);
			totalCost += end.playHours;
			cutPoints.add(end);
			// 保证保留的景点一定能够填补时间
			if (totalCost >= (days * planHourType.getAvg())) {
				break;
			} else {
				first = end;
			}
		}
		if (cutPoints.size() > 3) {
			cutPoints.remove(cutPoints.size() - 1);
		}
		return cutPoints;
	}

	private void appendScenic(int left, Point end) {
		Set<Long> ids = findBePlanedIds(null);
		do {
			SolrDis dis = DisUtils.findNearPointScenic(ids, end, left);
			if (dis != null) {
				left -= dis.getTaxi_time() + dis.getAdvice_hours();
				Point point = findPointFromDis(dis);
				resultPlay.get(days).add(point);
				end = point;
				ids.add(dis.getE_id());
			} else {
				break;
			}
		} while (left > canAppendScenicLeftTime);
	}

	private Point findPointFromDis(SolrDis dis) {
		return AreaService.instance.getPoint(dis.getE_id());
	}

	private String makeP2PDisKey(Point from, Point end) {
		return String.format("%d-%d", from.scenicInfoId, end.scenicInfoId);
	}

	private void separateToDayPlay() {
		// TODO Auto-generated method stub
		int day = 1;
		LinkedList<Point> points = new LinkedList<Point>();
		daysPlay.put(day, points);
		Point firstPoint = (Point) plans.get(0);
		points.add(firstPoint);
		int dayCost = firstPoint.playHours;
		int maxDayCostTime = planHourType.getAvg();
		if (plans.size() > 1) {
			for (int i = 1; i < plans.size(); i += 2) {
				SolrDis dis = (SolrDis) plans.get(i);
				Point point = (Point) plans.get(i + 1);
				if (dayCost + point.playHours + dis.getTaxi_time() < maxDayCostTime) {
					LinkedList<Point> dayPoints = daysPlay.get(day);
					dayPoints.add(point);
					dayCost += point.playHours + dis.getTaxi_time();
				} else {
					day++;
					dayCost = point.playHours;
					LinkedList<Point> nextDayPoint = new LinkedList<Point>();
					daysPlay.put(day, nextDayPoint);
					nextDayPoint.add(point);
				}
			}
		}
	}

	private void addPointDisInfo() {
		// TODO Auto-generated method stub
		plans.clear();
		int size = points.size();
		if (size == 1) {
			plans.addAll(points);
			return;
		}
		for (int i = 0; i < size - 1; i++) {
			Point from = points.get(i);
			Point end = points.get(i + 1);
			SolrDis dis = DisUtils.getPointToPointDis(from, end);
			plans.add(points.get(i));
			plans.add(dis);
		}
		plans.add(points.get(size - 1));
	}

	private Dis getPoint2PointDis(Point point, Point point2) {
		// TODO Auto-generated method stub

		return null;
	}

	private void sortPoints(List<Point> points) {

		Collections.sort(points, new Comparator<Point>() {
			@Override
			public int compare(Point o1, Point o2) {
				// TODO Auto-generated method stub
				int diff = o1.orderNum - o2.orderNum;
				if (diff > 0) {
					return 1;
				} else if (diff < 0) {
					return -1;
				} else {
					return 0;
				}

			}
		});
	}

	public Map<Integer, LinkedList<Point>> getResultPlay() {
		return resultPlay;
	}
}
