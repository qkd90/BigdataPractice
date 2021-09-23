package com.data.data.hmly.service.scenic.entity;

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
    private Region area;
    private List<Point> childPoints;
    private int tripType;
    private String name;
    private Boolean isThreeLevel;

    /**
     * 对应的分区列表
     */
    private List<Region> unitAreaList;
    /**
     * 对应的分区
     */
    private Region unitArea;

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

    public Region getArea() {
        return area;
    }

    public void setArea(Region area) {
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

    @Override
    public String toString() {
        // TODO Auto-generated method stub
        return String.format("%s-%s", scenicInfoId, name);
    }

    public List<Region> getUnitAreaList() {
        return unitAreaList;
    }

    public void setUnitAreaList(List<Region> unitAreaList) {
        this.unitAreaList = unitAreaList;
    }

    public Region getUnitArea() {
        return unitArea;
    }

    public void setUnitArea(Region unitArea) {
        this.unitArea = unitArea;
    }

    public long getScenicInfoId() {
        return scenicInfoId;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public int getCityCode() {
        return cityCode;
    }

    public Boolean getIsThreeLevel() {
        return isThreeLevel;
    }

    public void setIsThreeLevel(Boolean isThreeLevel) {
        this.isThreeLevel = isThreeLevel;
    }
}
