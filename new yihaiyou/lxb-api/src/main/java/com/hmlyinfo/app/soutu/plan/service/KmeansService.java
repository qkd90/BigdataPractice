package com.hmlyinfo.app.soutu.plan.service;

import com.hmlyinfo.app.soutu.plan.newAlg.modal.Point;
import com.hmlyinfo.app.soutu.plan.newAlg.ToolService;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.Zone;
import com.hmlyinfo.app.soutu.plan.newAlg.modal.ZoneCase;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


/**
 * Created by guoshijie on 2014/12/11.
 */

@Service
public class KmeansService {
	
	
	@Autowired
	private DisService disService;
	@Autowired
	private ToolService toolService;

    /**
     * 初始化聚类中心,随机，
     * 第一次取点也会影响到后续的结果
     */
	
	
    public List<Point> generateCenter(List<Point> pointList, int kNum) {

        List<Point> center = new ArrayList<Point>();

        toolService.sortPointByXY(pointList);

        int size = pointList.size(); //一共要旅游的景点数

        if (size <= kNum) { //当游玩天数大于等于景点天数的时候
            for (Point aPointList : pointList) {
                Point tempCenter = new Point(aPointList.id, aPointList.x, aPointList.y);
                center.add(tempCenter);
            }
            return center;
        }
        //否则，按我们以下的办法来处理
        for (int i = 0; i < kNum; i++) {
            Point tempCenter;
            double sumX = 0;
            double sumY = 0;
            int spotNum = 0;
            for (int j = i * size / kNum; j < ((i + 1) * size / kNum); j++) {
                sumX += pointList.get(j).x;
                sumY += pointList.get(j).y;
                spotNum++;
            }
            if (spotNum != 0) {
                tempCenter = new Point(i, sumX / spotNum, sumY / spotNum);
            } else {
                tempCenter = new Point(i, pointList.get(i).x, pointList.get(i).y);
            }
            center.add(tempCenter);
        }

        return center;

    }

    /**
     * 得到一个点所属的聚类的索引
     */
    int getNearestCluster(List<Point> centerList, Point tempPoint) {
        //第一个点和中心的一个点之间的坐标
        double min = Double.MAX_VALUE;
        long index = 0;
        int record = 0;
        for (int i = 0; i < centerList.size(); i++) {
            //double dist = TOOL.getDistance(tempPoint, centerList.get(i));
        	double dist = disService.getTwoPointDis(tempPoint, centerList.get(i));
            if (dist < min) {
                //只有出现更近的点时才会出现
                min = dist;
                index = centerList.get(i).id;
                record = i;
            }
        }
        tempPoint.zoneId = (int) index;
        tempPoint.distance = min;

        return record;
    }


    /**
     * 迭代处理
     */
    public  void iteration(List<Point> pointList, List<Point> centerList, int kNum) {
        boolean flag = true;
        List<Point> tempCenter = new ArrayList<Point>();
        //迭代
        while (flag) {
            //找到每个聚类的中心
            for (Point point : pointList) {
                getNearestCluster(centerList, point);
            }

            tempCenter.clear(); //清空掉上次迭代过程中保存的分区的临时几何中心点

            for (Point point : centerList) { //将现在的几何中心点队列保存到temp队列中
                Point temp = new Point();
                temp.x = point.x;
                temp.y = point.y;
                tempCenter.add(temp);
            }

            //更新聚类的中心
            updateCenter(pointList, centerList, kNum);
            int i;
            for (i = 0; i < centerList.size(); i++) {

                Point oldPoint = tempCenter.get(i);
                Point newPoint = centerList.get(i);
                if (!Point.oldEqualNewPoint(oldPoint, newPoint)) { //假如两个点不相同，则终止掉目前的循环
                    break;
                }
            }
            if (i == centerList.size()) {

                flag = false;
            }
        }
    }


    /**
     * 然后得到每个聚类的中心
     */
     void updateCenter(List<Point> pointList, List<Point> centerList, int num) {
        //清空以前的几何中心
        centerList.clear();
        //得到每个聚类的几何中心
        for (int i = 0; i < num && i < pointList.size(); i++) {

            double sumX = 0;
            double sumY = 0;
            int spotNum = 0;

            for (Point point : pointList) {
                if (point.zoneId == i) {
                    sumX += point.x;
                    sumY += point.y;
                    spotNum++;
                }
            }
            Point center;

        	/*
        	 *考虑到天数大于景点时，是否会出现一些数据异常所以还要对除0溢出的情况进行判定 
        	 */
            if (spotNum != 0) {
                center = new Point(i, sumX / spotNum, sumY / spotNum);
            } else { //假如该分区内没有景点的话，则将该分区的几何分区中心点重新定义为全部景点的几何中心点，再来遍历，目的就是为了消除初次分配K点时不恰当
                double sumAllX = 0;
                double sumAllY = 0;
                for (Point point : pointList) {
                    sumAllX += point.x;
                    sumAllY += point.y;
                }
                center = new Point(i, sumAllX / pointList.size(), sumAllY / pointList.size());
            }
            centerList.add(center);
        }
    }

    //初始化k个分区，对每个分区各类信息先置为0
    public  void initZone(int knum, List<Zone> zoneList) {

        for (int i = 0; i < knum; i++) {
            Zone tempZone = new Zone(i);
            zoneList.add(tempZone);
        }
    }

    //获取到离各个几何中心centerList，第 （serialNum） 近的 点，然后将其索引修改
    public  void getSerialNumNearest(Point tempPoint, List<Point> centerList, int serialNum) {

        if (serialNum == 0) { //表示离得最近的,因为我不知道存放常量的类放在哪个地方，所以就直接在这边用0替代，
            getNearestCluster(centerList, tempPoint);
            return;
        }
        List<Point> tempCenterList = new ArrayList<Point>();


        for (Point point : centerList) {
            tempCenterList.add(point);
        }
        for (int i = 0; i <= serialNum; i++) {
            int index = getNearestCluster(centerList, tempPoint);
            tempCenterList.remove(index);
        }
    }


    public  void getZoneCase(List<Point> pointList, List<Point> centerList, int kNum) {

        List<ZoneCase> zoneCaseList = new ArrayList<ZoneCase>();
        int playAllTime = 0;

        for (int i = 0; i < kNum; i++) {
            ZoneCase tempZone = new ZoneCase(i);
            zoneCaseList.add(tempZone);
        }
        toolService.sortPoint(pointList);

        for (Point tempPoint : pointList) {
            ZoneCase tempZone = zoneCaseList.get(tempPoint.zoneId);
            tempZone.spotNum++;
            tempZone.sumTime += tempPoint.playHours;
            playAllTime += tempPoint.playHours;
        }

        int timePerDay = playAllTime / kNum; //平均每天的游玩时间
        int maxTimePerDay = (int) (timePerDay * 1.2); //平均每天游玩时间的上限
        int spotPerDay = pointList.size() / kNum;  //平均每天游玩的景点
        int maxSpotPerDay = (int) (spotPerDay * 1.3);  //平均每天游玩的景点上限


        for (int i = 0; i < zoneCaseList.size(); i++) { //遍历开始检查每一个分区

            ZoneCase zoneCase = zoneCaseList.get(i);
            if (zoneCase.sumTime < maxTimePerDay) { //假如游玩时间超过了平均每天游玩时间的上限
                continue;
            }
            if (zoneCase.spotNum <= spotPerDay) {
                //但是其实主要原因是几个旅游景点的旅游时间比较长，而非景点数过多，这样的情况下，就暂时不进行处理
                continue;
            }
            //说明景点数已经超过了我们的每日游玩景点的上限，所以需要进行拆分
            int serialNum = 1;
            while (serialNum <= zoneCaseList.size()) {
                //获取到第serialNum近的Zone的索引ID
                int index = (int) toolService.getSerialNumNearestZone(centerList, zoneCase.id, serialNum);
                //获取到第serialNum近距离的分区
                ZoneCase zoneCaseTemp = zoneCaseList.get(index);

                if (zoneCaseTemp.sumTime > timePerDay || zoneCaseTemp.spotNum > maxSpotPerDay) {
                    serialNum++;
                    continue; //假如，该景点的游玩时间已经超过了平均时间，或者景点数已经超过了数量，则继续循环寻找下一个最近区域
                }
                //并没有大于，就可以进行景点插入和移动出去的处理
                Point tempPoint = toolService.partitionZone(pointList, zoneCase.id, centerList.get(zoneCaseTemp.id));
                //将zoneCase.id分区的 离 zoneCaseTemp.id 最近的一个景点修改为zoneCaseTemp区

                zoneCaseTemp.sumTime += tempPoint.playHours; //总时间
                zoneCaseTemp.spotNum++;  //被划分到景区游玩景点数
                zoneCase.sumTime -= tempPoint.playHours;
                zoneCase.spotNum--;
                if (zoneCase.sumTime < maxTimePerDay) {
                    break;
                }
            }
        }
        toolService.sortPointById(pointList);
    }
}
