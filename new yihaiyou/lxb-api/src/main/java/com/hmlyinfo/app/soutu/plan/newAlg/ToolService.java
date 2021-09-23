package com.hmlyinfo.app.soutu.plan.newAlg;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.ArrayListMultimap;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.Point;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.RankQueen;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.Zone;
import com.hmlyinfo.app.soutu.plan.service.DisService;

@Service
public class ToolService {
	
	@Autowired
	private DisService disService;

	
	
	//排序，根据距离，从近到远排序
	public  void sortRankQueen(List<RankQueen> rankQueens){
		Collections.sort(rankQueens, new Comparator<RankQueen>() {

			public int compare(RankQueen r1, RankQueen r2) {
				if((r1.distance-r2.distance)<0)
					return -1;
				else if((r1.distance-r2.distance)>0){
					return 1;
				}
				else return 0;
			}
		});
	}
	
	//对点队列进行排序，按照分区索引号，从小到大，倘若一致，再按照点到对应分区的最短距离
	public  void sortPoint(List<Point> pointList)
	{
		Collections.sort(pointList, new Comparator<Point>() {

			public int compare(Point p1, Point p2) {
				if((p1.zoneId-p2.zoneId)<0)
					return -1;
				else if((p2.zoneId-p1.zoneId)<0){
					return 1;
				}
				else {
					if((p1.distance-p2.distance)<0)
						return -1;
					else {
						return 1;
					}
				}
			}
		});
	}
	
	//对点队列进行排序，按照点的ID
	public void sortPointById(List<Point> pointList)
	{
		Collections.sort(pointList, new Comparator<Point>() {

			public int compare(Point p1, Point p2) {
				if((p1.id-p2.id)<0)
					return -1;
				else if((p2.id-p1.id)<0){
					return 1;
				}
				else {
					return 0;
				}
			}
		});
	}
	
	//对点队列进行排序，按照x坐标,然后按照y
	public  void sortPointByXY(List<Point> pointList)
	{
		Collections.sort(pointList, new Comparator<Point>() {

			public int compare(Point p1, Point p2) {
				if((p1.x-p2.x)<20)
					return -1;
				else if((p2.x-p1.x)<20){
					return 1;
				}
				else {
					if((p1.y-p2.y)<20)
						return -1;
					else {
						return 1;
					}
				}
			}
		});
	}
	//对区间队列进行排序，按照最短路程花费排序，花费总时间排序，从小到大
	public void sortZoneByTime(List<Zone> zoneList)
	{
		Collections.sort(zoneList, new Comparator<Zone>() {

			public int compare(Zone p1, Zone p2) {
				if((p1.minlen-p2.minlen)<0)
					return -1;
				else if((p2.minlen-p1.minlen)<0){
					return 1;
				}
				else {
					if((p1.sumTime-p2.sumTime)<0)
						return -1;
					else {
						return 1;
					}
				}
			}
		});
	}
	
	//对区间队列进行排序，按照ID
	public void sortZoneByID(List<Zone> zoneList)
	{
		Collections.sort(zoneList, new Comparator<Zone>() {

			public int compare(Zone p1, Zone p2) {
				if((p1.id-p2.id)<0)
					return -1;
				else if((p2.id-p1.id)<0){
					return 1;
				}else {
					return 0;
				}
			}
		});
	}
	
	

	
    
    /*
		根据状态值，获取到已经走过了多少地点，例如
	 	0000111， 表示的，已经去过了，3个地方
	*/
	public  int numOfPoint(int status,int size){
		int sum=0,t=1;
		for(int i=0;i<size;i++){
			if((status&t)!=0){
				sum++;
			}
			t<<=1;
		}
		return sum;
	}
	
	
	//获取到centerList中离 分区几何中心点center最近的一个分区的几何中心点
	public long zoneGetNearestZone(Point center,List<Point> centerList){
		double min=Double.MAX_VALUE;
		long record=0;
		for(int i=0;i<centerList.size();i++){
			//double tempDist=TOOL.getDistance(center, centerList.get(i));
			double tempDist = disService.pointDis(center, centerList.get(i), DisService.MODE_TAXI);
			if(tempDist<min){
				min=tempDist;
				record=centerList.get(i).id;
			}
		}
		return record;
	}
	
	
	//给出centerList,获取到离oldZoneID 分区 第（serialNum）近的几何中心点
	public  long getSerialNumNearestZone(List<Point> centerList,int oldZoneId,int serialNum){
		Point center=centerList.get(oldZoneId);
		if(serialNum == 0) //表示离得最近的
		{
			long nearestIndex=zoneGetNearestZone(center, centerList);
			return nearestIndex;
		}
		
		List<Point> tempCenterList =new ArrayList<Point>();
		for(int i=0;i<centerList.size();i++){
			Point tempCenterPoint=centerList.get(i);
			tempCenterList.add(tempCenterPoint);  
		}//将centerList copy一份副本tempCenterList

		
		long index=0;//不断地做循环，每次取出tempCenterList中离点center最近的点，然后移出，这样循环做serialNum次，就可以取得第serialNum近的分区ID
		for(int i=0;i<=serialNum;i++){
			index=zoneGetNearestZone(center, tempCenterList);
			for(int j=0;j<tempCenterList.size();j++){
				Point temPoint=tempCenterList.get(j);
				if(temPoint.id==index){
					tempCenterList.remove(j);
					break;
				}
			}
		}
		return index;
	}
	
	//将pointList里面,取出所有原本oldZoneID的所有点中  离newCenterPoint最近的点，将其修改为新的zoneID区
	public Point partitionZone(List<Point> pointList,int oldZoneId,Point newCenterPoint){
		List<Point> needPartitionZone=new ArrayList<Point>();
		for(int i=0;i<pointList.size();i++){
			if(pointList.get(i).zoneId==oldZoneId){
				needPartitionZone.add(pointList.get(i));
			}
		}	
		double min=Double.MAX_VALUE;
		int record=0;
		for(int i=0;i<needPartitionZone.size();i++){
			//double tempDist=TOOL.getDistance(needPartitionZone.get(i), newCenterPoint);
			double tempDist = disService.pointDis(needPartitionZone.get(i), newCenterPoint, DisService.MODE_TAXI);
			if(tempDist<min){
				min=tempDist;
				record=i;
			}
		}
		needPartitionZone.get(record).distance=min;
		needPartitionZone.get(record).zoneId=(int) newCenterPoint.id;	
		return needPartitionZone.get(record);
	}
	
	
	public  Multimap<Long, Point> preDoPoint(List<Point> pointList)
	{
		Multimap<Long, Point> resultMap = ArrayListMultimap.create();
		List<Point> tmpList = Lists.newArrayList();
		for (Point point : pointList)
		{
			resultMap.put(point.father, point);
		}
		
		for (Point p : pointList)
		{
			List<Point> children = (List<Point>) resultMap.get(p.scenicInfoId);
			if (children != null && children.size() > 0)
			{
				tmpList.addAll(children);
			}
		}
		
		pointList.removeAll(tmpList);
		
		return resultMap;
	}
	
	public  void afterDoPoint(Map<Integer, List<Point>> kResult, Multimap<Long, Point> pointMap)
	{
		for (Map.Entry<Integer, List<Point>> kentity : kResult.entrySet())
		{
			List<Point> dpList = kentity.getValue();
			List<Point> tmpList = Lists.newArrayList();
			for (Point point : dpList)
			{
				List<Point> cList = (List<Point>) pointMap.get(point.scenicInfoId);
				if (cList != null && cList.size() > 0)
				{
					tmpList.addAll(cList);
				}
			}
			
			dpList.addAll(tmpList);
		}
	}
}
