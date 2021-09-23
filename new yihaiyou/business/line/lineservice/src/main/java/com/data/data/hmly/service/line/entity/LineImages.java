package com.data.data.hmly.service.line.entity;

import com.data.data.hmly.service.line.entity.enums.LineImageType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
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
@Table(name = "lineimages")
public class LineImages extends com.framework.hibernate.util.Entity implements Serializable, Cloneable{
    /*
    CREATE TABLE `linetimeimages` (
        `id` bigint(20) NOT NULL AUTO_INCREMENT,
        `lineId` bigint(20) DEFAULT NULL,
        `daysplanId` bigint(20) DEFAULT NULL,
        `linedaysplaninfoId` bigint(20) DEFAULT NULL,
        `imageUrl` varchar(255) DEFAULT NULL COMMENT '图片路径',
        `imageDesc` varchar(500) DEFAULT NULL COMMENT '图片描述',
        PRIMARY KEY (`id`)
    ) ENGINE=InnoDB DEFAULT CHARSET=utf8;
    */
    private static final long serialVersionUID = -3872525549480231213L;

    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @Column(name = "imageUrl")
    private String imageUrl;

    @Column(name = "imageDesc")
    private String imageDesc;

    @Column(name = "lineId")
    private Long lineId;

    @Column(name = "lineImgType")
    @Enumerated(EnumType.STRING)
    private LineImageType lineImageType;

    @ManyToOne
    @JoinColumn(name = "linedaysplanId")
    private Linedaysplan linedaysplan;

    @ManyToOne
    @JoinColumn(name = "linedaysplaninfoId")
    private LineDaysPlanInfo lineDaysPlanInfo;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public String getImageDesc() {
        return imageDesc;
    }

    public void setImageDesc(String imageDesc) {
        this.imageDesc = imageDesc;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Linedaysplan getLinedaysplan() {
        return linedaysplan;
    }

    public void setLinedaysplan(Linedaysplan linedaysplan) {
        this.linedaysplan = linedaysplan;
    }

    public LineDaysPlanInfo getLineDaysPlanInfo() {
        return lineDaysPlanInfo;
    }

    public void setLineDaysPlanInfo(LineDaysPlanInfo lineDaysPlanInfo) {
        this.lineDaysPlanInfo = lineDaysPlanInfo;
    }


    public LineImageType getLineImageType() {
        return lineImageType;
    }

    public void setLineImageType(LineImageType lineImageType) {
        this.lineImageType = lineImageType;
    }

    @Override
    public LineImages clone() throws CloneNotSupportedException {
        return (LineImages) super.clone();
    }
}
