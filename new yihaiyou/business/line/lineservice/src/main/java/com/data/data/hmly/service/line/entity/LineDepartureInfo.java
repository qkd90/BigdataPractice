package com.data.data.hmly.service.line.entity;

import com.zuipin.util.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.Date;

/**
 * Created by dy on 2016/6/21.
 */
@Entity
@Table (name = "line_departure_info")
public class LineDepartureInfo extends com.framework.hibernate.util.Entity implements Serializable {
    /*
    CREATE TABLE `line_departure_info` (
        `id` bigint(20) NOT NULL,
        `departure_id` bigint(20) DEFAULT NULL COMMENT '发车信息主表',
        `origin_station` varchar(255) DEFAULT NULL COMMENT '发车地点',
        `return_place` varchar(255) DEFAULT NULL COMMENT '返回地点',
        `start_time` datetime DEFAULT NULL COMMENT '发车时间',
        `remark` varchar(500) DEFAULT NULL COMMENT '备注',
        `line_id` bigint(20) DEFAULT NULL COMMENT '关联线路',
        `create_time` datetime DEFAULT NULL,
        `update_time` datetime DEFAULT NULL,
    PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=COMPACT;
    */

    private static final long serialVersionUID = -3872525549480231213L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "origin_station")
    private String originStation;

    @Column(name = "return_place")
    private String returnPlace;

    @Column(name = "start_time")
    private Date departureDate;

    @Column(name = "remark")
    private String remark;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime;

    @ManyToOne
    @JoinColumn(name = "departure_id")
    private LineDeparture departure;

    @ManyToOne
    @JoinColumn(name = "line_id")
    private Line line;


    @Transient
    private String startTime;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOriginStation() {
        return originStation;
    }

    public void setOriginStation(String originStation) {
        this.originStation = originStation;
    }

    public String getReturnPlace() {
        return returnPlace;
    }

    public void setReturnPlace(String returnPlace) {
        this.returnPlace = returnPlace;
    }

    public Date getDepartureDate() {
        return departureDate;
    }

    public void setDepartureDate(Date departureDate) {
        this.departureDate = departureDate;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public LineDeparture getDeparture() {
        return departure;
    }

    public void setDeparture(LineDeparture departure) {
        this.departure = departure;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getDepartureDateStr() {
        if (departureDate != null) {
            return DateUtils.format(departureDate, "HH:mm:ss");
        }
        return "";
    }
}
