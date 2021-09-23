package com.data.spider.service.pojo;

import java.util.List;

public class Point {
    public long scenicInfoId;
    public long father;
    public long id;
    public double x;
    public double y;
    public int zoneId; // 该景点所归属的旅游天数
    public double distance; // 该点与最近的片区的几何中心的距离
    public int playHours; // 景点的游玩的时间
    // 推荐程度
    public int orderNum;
    public int cityCode;
    private Area area;
    private List<Point> childPoints;
    private int tripType;
    private String name;
    private Boolean isCity;

    /**
     * 对应的分区列表
     */
    private List<Area> unitAreaList;
    /**
     * 对应的分区
     */
    private Area unitArea;

    public static boolean oldEqualNewPoint(Point oldPoint, Point newPoint) {
        return (oldPoint.x == newPoint.x) && (oldPoint.y == newPoint.y);
    }

    public Point(long id, double x, double y) {

        this.id = id;
        this.x = x;
        this.y = y;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public Point() {

    }

    public Point(long id, double px, double py, int zoneId, double distance) {
        this.id = id;
        x = px;
        y = py;
        this.zoneId = zoneId;
        this.distance = distance;
    }

    public Point(int id, double x, double y, int playHours) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.playHours = playHours;
    }

    public Area getArea() {
        return area;
    }

    public void setArea(Area area) {
        this.area = area;
    }

    public List<Point> getChildPoints() {
        return childPoints;
    }

    public void setChildPoints(List<Point> childPoints) {
        this.childPoints = childPoints;
    }

    public int getTripType() {
        return tripType;
    }

    public void setTripType(int tripType) {
        this.tripType = tripType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getIsCity() {
        return isCity;
    }

    public void setIsCity(Boolean isCity) {
        this.isCity = isCity;
    }

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.format("%s-%s", scenicInfoId, name);
    }

    public List<Area> getUnitAreaList() {
        return unitAreaList;
    }

    public void setUnitAreaList(List<Area> unitAreaList) {
        this.unitAreaList = unitAreaList;
    }

    public Area getUnitArea() {
        return unitArea;
    }

    public void setUnitArea(Area unitArea) {
        this.unitArea = unitArea;
    }

}
