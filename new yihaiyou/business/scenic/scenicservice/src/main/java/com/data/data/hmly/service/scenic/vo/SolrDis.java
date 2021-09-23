package com.data.data.hmly.service.scenic.vo;

import com.data.data.hmly.service.scenic.entity.Point;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.Date;

@SolrDocument(solrCoreName = "dis")
public class SolrDis {

    @Field("id")
    private long id;
    @Field("f_to")
    private String fTo;
    @Field("areaId")
    private int areaId;
    @Field("endCost")
    private Float endCost;
    @Field("taxi_time")
    private int taxiTime;
    @Field("s_id")
    private long sId;
    @Field("e_id")
    private long eId;
    @Field("order_num")
    private int orderNum;
    @Field("advice_hours")
    private int adviceHours;
    @Field("from_name")
    private String fromName;
    @Field("end_name")
    private String endName;

    private Point from;
    private Point end;
    @Field("father")
    private int father;
    @Field("city_code")
    private int cityCode;
    @Field("s_lng")
    private Double sLng;
    @Field("s_lat")
    private Double sLat;
    @Field("e_lng")
    private Double eLng;
    @Field("e_lat")
    private Double eLat;
    @Field("taxi_dis")
    private int taxiDis;
    @Field("taxi_cost")
    private int taxiCost;
    @Field("walk_dis")
    private int walkDis;
    @Field("walk_time")
    private int walkTime;
    @Field("bus_dis")
    private int busDis;
    @Field("bus_cost")
    private int busCost;
    @Field("bus_time")
    private int busTime;
    @Field("modify_time")
    private Date modifyTime;
    @Field("create_time")
    private Date createTime;
    @Field("dis_type")
    private int disType;
    @Field("taxi_ex")
    private int taxiEx;
    @Field("walk_ex")
    private int walkEx;
    @Field("bus_ex")
    private int busEx;
    private int optStatus;


    public static SolrDis build() {
        SolrDis dis = new SolrDis();
        dis.setEndCost(30f);
        dis.setTaxiTime(30);
        return dis;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getfTo() {
        return fTo;
    }

    public void setfTo(String fTo) {
        this.fTo = fTo;
    }

    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    public Float getEndCost() {
        return endCost;
    }

    public void setEndCost(Float endCost) {
        this.endCost = endCost;
    }

    public int getTaxiTime() {
        return taxiTime;
    }

    public void setTaxiTime(int taxiTime) {
        this.taxiTime = taxiTime;
    }

    public long getsId() {
        return sId;
    }

    public void setsId(long sId) {
        this.sId = sId;
    }

    public long geteId() {
        return eId;
    }

    public void seteId(long eId) {
        this.eId = eId;
    }

    public int getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(int orderNum) {
        this.orderNum = orderNum;
    }

    public int getAdviceHours() {
        return adviceHours;
    }

    public void setAdviceHours(int adviceHours) {
        this.adviceHours = adviceHours;
    }

    public String getFromName() {
        return fromName;
    }

    public void setFromName(String fromName) {
        this.fromName = fromName;
    }

    public String getEndName() {
        return endName;
    }

    public void setEndName(String endName) {
        this.endName = endName;
    }

    public Point getFrom() {
        return from;
    }

    public void setFrom(Point from) {
        this.from = from;
    }

    public Point getEnd() {
        return end;
    }

    public void setEnd(Point end) {
        this.end = end;
    }

    public int getFather() {
        return father;
    }

    public void setFather(int father) {
        this.father = father;
    }

    public int getCityCode() {
        return cityCode;
    }

    public void setCityCode(int cityCode) {
        this.cityCode = cityCode;
    }

    public Double getsLng() {
        return sLng;
    }

    public void setsLng(Double sLng) {
        this.sLng = sLng;
    }

    public Double getsLat() {
        return sLat;
    }

    public void setsLat(Double sLat) {
        this.sLat = sLat;
    }

    public Double geteLng() {
        return eLng;
    }

    public void seteLng(Double eLng) {
        this.eLng = eLng;
    }

    public Double geteLat() {
        return eLat;
    }

    public void seteLat(Double eLat) {
        this.eLat = eLat;
    }

    public int getTaxiDis() {
        return taxiDis;
    }

    public void setTaxiDis(int taxiDis) {
        this.taxiDis = taxiDis;
    }

    public int getTaxiCost() {
        return taxiCost;
    }

    public void setTaxiCost(int taxiCost) {
        this.taxiCost = taxiCost;
    }

    public int getWalkDis() {
        return walkDis;
    }

    public void setWalkDis(int walkDis) {
        this.walkDis = walkDis;
    }

    public int getWalkTime() {
        return walkTime;
    }

    public void setWalkTime(int walkTime) {
        this.walkTime = walkTime;
    }

    public int getBusDis() {
        return busDis;
    }

    public void setBusDis(int busDis) {
        this.busDis = busDis;
    }

    public int getBusCost() {
        return busCost;
    }

    public void setBusCost(int busCost) {
        this.busCost = busCost;
    }

    public int getBusTime() {
        return busTime;
    }

    public void setBusTime(int busTime) {
        this.busTime = busTime;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public int getDisType() {
        return disType;
    }

    public void setDisType(int disType) {
        this.disType = disType;
    }

    public int getTaxiEx() {
        return taxiEx;
    }

    public void setTaxiEx(int taxiEx) {
        this.taxiEx = taxiEx;
    }

    public int getWalkEx() {
        return walkEx;
    }

    public void setWalkEx(int walkEx) {
        this.walkEx = walkEx;
    }

    public int getBusEx() {
        return busEx;
    }

    public void setBusEx(int busEx) {
        this.busEx = busEx;
    }

    public int getOptStatus() {
        return optStatus;
    }

    public void setOptStatus(int optStatus) {
        this.optStatus = optStatus;
    }
}
