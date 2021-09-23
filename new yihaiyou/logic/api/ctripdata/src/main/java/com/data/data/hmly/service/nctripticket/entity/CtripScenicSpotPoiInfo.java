package com.data.data.hmly.service.nctripticket.entity;


import com.data.data.hmly.service.ctripcommon.enums.RowStatus;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "nctrip_scenic_spot_poi_info")
public class CtripScenicSpotPoiInfo extends com.framework.hibernate.util.Entity {
    @Id
    @Column(name = "id", length = 20)
    private Long id;	            //  同景点ID
    @Column(name = "poiid", length = 20)
    private Long    poiid;		//	poiid
    @Column(name = "gsCityId", length = 20)
    private Long    gsCityId;		//	目的地城市ID
    @Column(name = "gsCityName", length = 256)
    private String  gsCityName;		//	目的地城市中文名
    @Column(name = "gsCityEName", length = 256)
    private String  gsCityEName;		//	目的地城市英文名
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "createTime", length = 19)
    private Date createTime;
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updateTime", length = 19)
    private Date updateTime;
    @Column(name = "rowStatus")
    @Enumerated(EnumType.STRING)
    private RowStatus rowStatus;

    public Long getPoiid() {
        return poiid;
    }

    public void setPoiid(Long poiid) {
        this.poiid = poiid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getGsCityId() {
        return gsCityId;
    }

    public void setGsCityId(Long gsCityId) {
        this.gsCityId = gsCityId;
    }

    public String getGsCityName() {
        return gsCityName;
    }

    public void setGsCityName(String gsCityName) {
        this.gsCityName = gsCityName;
    }

    public String getGsCityEName() {
        return gsCityEName;
    }

    public void setGsCityEName(String gsCityEName) {
        this.gsCityEName = gsCityEName;
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

    public RowStatus getRowStatus() {
        return rowStatus;
    }

    public void setRowStatus(RowStatus rowStatus) {
        this.rowStatus = rowStatus;
    }
}
