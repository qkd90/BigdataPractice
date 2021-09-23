package com.data.data.service;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.apache.log4j.Logger;

import com.data.data.service.dao.ScenicAreaDao;
import com.data.data.service.pojo.Area;
import com.data.data.service.pojo.ScenicArea;
import com.data.data.service.pojo.ScenicInfo;
import com.hmlyinfo.app.soutu.plan.domain.type.TripType;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.Point;
import com.zuipin.util.SpringContextHolder;

public enum AreaService {
	instance;
	private final ScenicAreaDao			scenicAreaDao	= SpringContextHolder.getBean("scenicAreaDao");
	public Map<String, List<Area>>		cityAreas;
	private final Map<Long, Area>		scenicAreaMap	= new HashMap<Long, Area>();
	private final Map<Long, ScenicInfo>	scenics			= new HashMap<Long, ScenicInfo>();
	private final static Logger			log				= Logger.getLogger(AreaService.class);

	private AreaService() {
		cityAreas = new ConcurrentHashMap<String, List<Area>>();
		List<ScenicArea> scenicAreas = scenicAreaDao.findAll();
		for (ScenicArea scenicArea : scenicAreas) {
			ScenicInfo scenicInfo = scenicArea.getScenicInfo();
			Area area = scenicArea.getArea();
			scenicAreaMap.put(scenicInfo.getId(), area);
		}
		for (ScenicArea scenicArea : scenicAreas) {
			ScenicInfo scenicInfo = scenicArea.getScenicInfo();
			Area area = scenicArea.getArea();
			scenics.put(scenicInfo.getId(), scenicInfo);
			Point point = changeScenicInfoToPoint(scenicInfo);
			String cityCode = scenicInfo.getCity_code();
			if (cityAreas.containsKey(cityCode)) {
				List<Area> areas = cityAreas.get(cityCode);
				if (areas == null) {
					areas = new ArrayList<Area>();
					cityAreas.put(cityCode, areas);
				}
				Boolean contain = false;
				for (Area areaItem : areas) {
					if (areaItem.getId().equals(area.getId())) {
						areaItem.getPoints().add(point);
						contain = true;
						break;
					}
				}
				if (!contain) {
					area.getPoints().add(point);
					areas.add(area);
				}
			}
		}
	}

	public List<Area> findCityArea(String cityCode) {
		return cityAreas.get(cityCode);
	}

	public static Point changeScenicInfoToPoint(ScenicInfo scenicInfo) {
		// TODO Auto-generated method stub
		int distance = 11111;
		DecimalFormat fnum = new DecimalFormat("#.##"); // 格式化取坐标百分位
		Point point = new Point();
		point.setTripType(TripType.SCENIC.value());
		point.x = distance * scenicInfo.getLongitude();
		point.x = Double.parseDouble(fnum.format(point.x));
		point.y = distance * scenicInfo.getLatitude();
		point.y = Double.parseDouble(fnum.format(point.y));
		point.playHours = scenicInfo.getAdvice_hours();
		point.scenicInfoId = scenicInfo.getId();
		point.father = scenicInfo.getFather() == null ? 0 : scenicInfo.getFather();
		point.orderNum = scenicInfo.getRanking() == null ? 1000 : scenicInfo.getRanking();
		point.cityCode = Integer.parseInt(scenicInfo.getCity_code());
		point.setIsCity(scenicInfo.getIs_city() == 1 ? true : false);
		point.setName(scenicInfo.getName());
		point.setArea(AreaService.instance.getArea(point.scenicInfoId));
		return point;
	}

	public Area getArea(long scenicInfoId) {
		// TODO Auto-generated method stub
		return scenicAreaMap.get(scenicInfoId);
	}

	public Point getPoint(long e_id) {
		// TODO Auto-generated method stub
		ScenicInfo scenicInfo = scenics.get(e_id);
		if (scenicInfo != null) {
			return changeScenicInfoToPoint(scenicInfo);
		} else {
			return null;
		}
	}

}
