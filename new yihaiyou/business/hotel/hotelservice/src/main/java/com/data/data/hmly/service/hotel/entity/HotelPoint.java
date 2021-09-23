package com.data.data.hmly.service.hotel.entity;

import java.util.List;

public class HotelPoint {
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
    private HotelRegion area;
    private List<HotelPoint> childPoints;
    private int tripType;
    private String name;
    private Boolean isThreeLevel;

    /**
     * 对应的分区列表
     */
    private List<HotelRegion> unitAreaList;
    /**
     * 对应的分区
     */
    private HotelRegion unitArea;

    public static boolean oldEqualNewPoint(HotelPoint oldPoint, HotelPoint newPoint) {
        return (oldPoint.x == newPoint.x) && (oldPoint.y == newPoint.y);
    }

    public HotelPoint(long id, double x, double y) {

        this.id = id;
        this.x = x;
        this.y = y;
    }

    public HotelPoint(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public HotelPoint() {

    }

    public HotelPoint(long id, double px, double py, int zoneId, double distance) {
        this.id = id;
        x = px;
        y = py;
        this.zoneId = zoneId;
        this.distance = distance;
    }

    public HotelPoint(int id, double x, double y, int playHours) {
        this.id = id;
        this.x = x;
        this.y = y;
        this.playHours = playHours;
    }

    public HotelRegion getArea() {
        return area;
    }

    public void setArea(HotelRegion area) {
        this.area = area;
    }

    public List<HotelPoint> getChildPoints() {
        return childPoints;
    }

    public void setChildPoints(List<HotelPoint> childPoints) {
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

    public List<HotelRegion> getUnitAreaList() {
        return unitAreaList;
    }

    public void setUnitAreaList(List<HotelRegion> unitAreaList) {
        this.unitAreaList = unitAreaList;
    }

    public HotelRegion getUnitArea() {
        return unitArea;
    }

    public void setUnitArea(HotelRegion unitArea) {
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
