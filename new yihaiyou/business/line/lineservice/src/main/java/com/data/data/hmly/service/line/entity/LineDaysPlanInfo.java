package com.data.data.hmly.service.line.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * Created by dy on 2016/6/22.
 */
@Entity
@Table (name = "linedayplaninfo")
public class LineDaysPlanInfo extends com.framework.hibernate.util.Entity implements Serializable, Cloneable{
/*

    CREATE TABLE `linedaytimeinfo` (
        `id` bigint(20) NOT NULL AUTO_INCREMENT,
        `lineId` bigint(20) DEFAULT NULL,
        `linedayId` bigint(20) DEFAULT NULL,
        `linedaytimeId` bigint(20) DEFAULT NULL,
        `littleTitle` varchar(500) DEFAULT NULL COMMENT '小标题',
        `titleDesc` varchar(1000) DEFAULT NULL COMMENT '小标题描述',
        PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
*/

    private static final long serialVersionUID = -3872525549480231213L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;
    @Column(name = "littleTitle")
    private String littleTitle;
    @Column(name = "titleDesc")
    private String titleDesc;

    @Column(name = "lineId")
    private Long lineId;

    @ManyToOne
    @JoinColumn(name = "linedayId")
    private Linedays linedays;
    @ManyToOne
    @JoinColumn(name = "linedaysplanId")
    private Linedaysplan linedaysplan;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getLittleTitle() {
        return littleTitle;
    }

    public void setLittleTitle(String littleTitle) {
        this.littleTitle = littleTitle;
    }

    public String getTitleDesc() {
        return titleDesc;
    }

    public void setTitleDesc(String titleDesc) {
        this.titleDesc = titleDesc;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Linedays getLinedays() {
        return linedays;
    }

    public void setLinedays(Linedays linedays) {
        this.linedays = linedays;
    }

    public Linedaysplan getLinedaysplan() {
        return linedaysplan;
    }

    public void setLinedaysplan(Linedaysplan linedaysplan) {
        this.linedaysplan = linedaysplan;
    }

    @Override
    public LineDaysPlanInfo clone() throws CloneNotSupportedException {
        return (LineDaysPlanInfo) super.clone();
    }
}
