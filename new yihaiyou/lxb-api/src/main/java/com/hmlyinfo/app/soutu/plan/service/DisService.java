package com.hmlyinfo.app.soutu.plan.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hmlyinfo.app.soutu.plan.domain.Dis;
import com.hmlyinfo.app.soutu.plan.mapper.DisMapper;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.Point;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;

@Service
public class DisService extends BaseService<Dis, Long>{
	
	public static final Logger logger = Logger.getLogger(DisService.class);
	
	// 交通类型_公交
	public static final int MODE_BUS = 1;
	// 交通类型_步行
	public static final int MODE_WALK = 3;
	// 交通类型_打车
	public static final int MODE_TAXI = 2;
	

	@Autowired
	private DisMapper<Dis> mapper;

	@Override
	public BaseMapper<Dis> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	// 根据mode值返回一个行程中的总时间花费
	public int listTime(List<Point> pointList, int mode)
	{
		List<Long> scenicIdList = new ArrayList<Long>();
		for(Point point : pointList){
			scenicIdList.add(point.scenicInfoId);
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sIds", scenicIdList);
		paramMap.put("eIds", scenicIdList);
		List<Dis> disList = list(paramMap);
		
		Map<String, Integer> resMap = new HashMap<String, Integer>();
		for(Dis dis : disList){
			String index = dis.getSId() + "_" + dis.getEId() + "_";
			resMap.put(index + MODE_BUS, dis.getBusTime());
			resMap.put(index + MODE_TAXI, dis.getTaxiTime());
			resMap.put(index + MODE_WALK, dis.getWalkTime());
		}
		
		int time = 0;
		for(int i = 0; i < pointList.size() - 1; i++){
			
			time += pointList.get(i).playHours;
			// 如果其中一个点没有景点id，直接时间默认30
			if(pointList.get(i).scenicInfoId == 0 || pointList.get(i + 1).scenicInfoId == 0){
				time += 30;
				continue;
			}


			long sId = pointList.get(i).scenicInfoId;
			long eId = pointList.get(i + 1).scenicInfoId;
			String seId = sId + "_" + eId + "_";
			if(resMap.get(seId + MODE_BUS) == null){
				// 记录没有查询到交通时间的两个景点
				logger.info("sId=" + pointList.get(i).scenicInfoId + "    eId=" + pointList.get(i + 1).scenicInfoId + "    :no time");
				time += 30;
				continue;
			}
			if(mode == MODE_BUS){
				if(resMap.get(seId + MODE_BUS) == null){
					time += 30;
					logger.info("sId=" + pointList.get(i).scenicInfoId + "    eId=" + pointList.get(i + 1).scenicInfoId + "    :no time");
					continue;
				}
				time += resMap.get(seId + MODE_BUS) / 60;
			}else if (mode == MODE_TAXI) {
				if(resMap.get(seId + MODE_TAXI) == null){
					time += 30;
					logger.info("sId=" + pointList.get(i).scenicInfoId + "    eId=" + pointList.get(i + 1).scenicInfoId + "    :no time");
					continue;
				}
				time += resMap.get(seId + MODE_TAXI) / 60;
			}else {
				if(resMap.get(seId + MODE_WALK) == null){
					time += 30;
					logger.info("sId=" + pointList.get(i).scenicInfoId + "    eId=" + pointList.get(i + 1).scenicInfoId + "    :no time");
					continue;
				}
				time += resMap.get(seId + MODE_WALK) / 60;
			}
		}
		time += pointList.get(pointList.size() - 1).playHours;
		return time;
	}
	
	// 根据mode值返回距离数据
	public int pointDis(Point sPoint, Point ePoint, int mode)
	{
		// 如果其中一个点没有景点id，直接时间默认30
		if (sPoint.scenicInfoId == 0 || ePoint.scenicInfoId == 0) {
			return getTwoPointDis(sPoint, ePoint);
		}
		// 同一个点
		if(sPoint.scenicInfoId == ePoint.scenicInfoId){
			return 0;
		}
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("sId", sPoint.scenicInfoId);
		paramMap.put("eId", ePoint.scenicInfoId);
		List<Dis> disList = list(paramMap);
		
		// 没有查找到数据就用直线距离返回
		if(disList.isEmpty()){
			// 记录没有查询到交通距离的两个点
			logger.info("sId=" + sPoint.scenicInfoId + "    eId=" + ePoint.scenicInfoId + "    :no dis");
			return getTwoPointDis(sPoint, ePoint);
		}
		// 查询数据库中的距离数据
		Dis dis = ListUtil.getSingle(disList);
		if(mode == MODE_BUS){
			if(dis.getBusDis() == 0){
				logger.info("sId=" + sPoint.scenicInfoId + "    eId=" + ePoint.scenicInfoId + "    :no dis");
				return getTwoPointDis(sPoint, ePoint);
			}
			return dis.getBusDis();
		}else if (mode == MODE_TAXI) {
			if(dis.getTaxiDis() == 0){
				logger.info("sId=" + sPoint.scenicInfoId + "    eId=" + ePoint.scenicInfoId + "    :no dis");
				return getTwoPointDis(sPoint, ePoint);
			}
			return dis.getTaxiDis() + dis.getTaxiEx();
		}else {
			if(dis.getWalkDis() == 0){
				logger.info("sId=" + sPoint.scenicInfoId + "    eId=" + ePoint.scenicInfoId + "    :no dis");
				return getTwoPointDis(sPoint, ePoint);
			}
			return dis.getWalkDis();
		}
	}
	
	// 查询两点之间的距离
    public int getTwoPointDis(Point sPoint, Point ePoint){
    	//地球半径
    	double EARTH_RADIUS = 6371393;
    	//计算用户所在位置和当前位置的距离,单位为m
    	// 
    	double sLng = sPoint.x / 11111;
    	double sLat = sPoint.y / 11111;
    	double eLng = ePoint.x / 11111;
    	double eLat = ePoint.y / 11111;
    	
    	double sRadLat = sLat * Math.PI / 180.0; 
        double eRadLat = eLat * Math.PI / 180.0;
  
        double sRadLng = sLng * Math.PI / 180.0; 
        double eRadLng = eLng * Math.PI / 180.0;  
    
        double a = sRadLat - eRadLat;  
        double b = sRadLng - eRadLng;  
        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(sRadLat)*Math.cos(eRadLat)*Math.pow(Math.sin(b/2),2)));
        s = s * EARTH_RADIUS;
        s = Math.round(s * 10000) / 10000;
        Double d_s=new Double(s);
        return d_s.intValue();
    }
    
}
