package com.data.spider.service.pojo.tb;

import com.data.spider.service.pojo.Point;
import org.apache.solr.client.solrj.beans.Field;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by Sane on 15/9/10.
 */
@Entity
@javax.persistence.Table(name = "tb_dis")
public class TbDis extends com.framework.hibernate.util.Entity {
    private Long id;

    @Id
    @javax.persistence.Column(name = "id")
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    private Integer taxi_time;

    private Long s_id;

    private Long e_id;


    private Integer city_code;

    private Double s_lng;

    private Double s_lat;

    private Double e_lng;

    private Double e_lat;

    private Integer taxi_dis;

    private Integer taxi_cost;

    private Integer walk_dis;

    private Integer walk_time;

    private Integer bus_dis;

    private Integer bus_cost;

    private Integer bus_time;
    @Temporal(TemporalType.TIMESTAMP)
    private Date modify_time;
    @Temporal(TemporalType.TIMESTAMP)
    private Date create_time;

    private Integer dis_type;

    private Integer taxi_ex;

    private Integer walk_ex;

    private Integer bus_ex;

    private Integer opt_status;

    public Integer getTaxi_time() {
        return taxi_time;
    }

    public void setTaxi_time(Integer taxi_time) {
        this.taxi_time = taxi_time;
    }

    public Long getS_id() {
        return s_id;
    }

    public void setS_id(Long s_id) {
        this.s_id = s_id;
    }

    public Long getE_id() {
        return e_id;
    }

    public void setE_id(Long e_id) {
        this.e_id = e_id;
    }


    public Integer getCity_code() {
        return city_code;
    }

    public void setCity_code(Integer city_code) {
        this.city_code = city_code;
    }

    public Double getS_lng() {
        return s_lng;
    }

    public void setS_lng(Double s_lng) {
        this.s_lng = s_lng;
    }

    public Double getS_lat() {
        return s_lat;
    }

    public void setS_lat(Double s_lat) {
        this.s_lat = s_lat;
    }

    public Double getE_lng() {
        return e_lng;
    }

    public void setE_lng(Double e_lng) {
        this.e_lng = e_lng;
    }

    public Double getE_lat() {
        return e_lat;
    }

    public void setE_lat(Double e_lat) {
        this.e_lat = e_lat;
    }

    public Integer getTaxi_dis() {
        return taxi_dis;
    }

    public void setTaxi_dis(Integer taxi_dis) {
        this.taxi_dis = taxi_dis;
    }

    public Integer getTaxi_cost() {
        return taxi_cost;
    }

    public void setTaxi_cost(Integer taxi_cost) {
        this.taxi_cost = taxi_cost;
    }

    public Integer getWalk_dis() {
        return walk_dis;
    }

    public void setWalk_dis(Integer walk_dis) {
        this.walk_dis = walk_dis;
    }

    public Integer getWalk_time() {
        return walk_time;
    }

    public void setWalk_time(Integer walk_time) {
        this.walk_time = walk_time;
    }

    public Integer getBus_dis() {
        return bus_dis;
    }

    public void setBus_dis(Integer bus_dis) {
        this.bus_dis = bus_dis;
    }

    public Integer getBus_cost() {
        return bus_cost;
    }

    public void setBus_cost(Integer bus_cost) {
        this.bus_cost = bus_cost;
    }

    public Integer getBus_time() {
        return bus_time;
    }

    public void setBus_time(Integer bus_time) {
        this.bus_time = bus_time;
    }

    public Date getModify_time() {
        return modify_time;
    }

    public void setModify_time(Date modify_time) {
        this.modify_time = modify_time;
    }

    public Date getCreate_time() {
        return create_time;
    }

    public void setCreate_time(Date create_time) {
        this.create_time = create_time;
    }

    public Integer getDis_type() {
        return dis_type;
    }

    public void setDis_type(Integer dis_type) {
        this.dis_type = dis_type;
    }

    public Integer getTaxi_ex() {
        return taxi_ex;
    }

    public void setTaxi_ex(Integer taxi_ex) {
        this.taxi_ex = taxi_ex;
    }

    public Integer getWalk_ex() {
        return walk_ex;
    }

    public void setWalk_ex(Integer walk_ex) {
        this.walk_ex = walk_ex;
    }

    public Integer getBus_ex() {
        return bus_ex;
    }

    public void setBus_ex(Integer bus_ex) {
        this.bus_ex = bus_ex;
    }

    public Integer getOpt_status() {
        return opt_status;
    }

    public void setOpt_status(Integer opt_status) {
        this.opt_status = opt_status;
    }
}
