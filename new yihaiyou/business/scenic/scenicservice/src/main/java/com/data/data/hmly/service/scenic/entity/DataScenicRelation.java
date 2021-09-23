package com.data.data.hmly.service.scenic.entity;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.IdClass;
import javax.persistence.Table;

@Entity
@Table(name = "data_scenic_relation")
@IdClass(DataScenicRelationPK.class)
public class DataScenicRelation extends com.framework.hibernate.util.Entity implements java.io.Serializable {
    private long scenicId;
    private String source;
    private long sourceId;
    private String sourceName;
    private String sourceUrl;
    private Integer status;
    private int etlStatus;
    private Integer etlOrder;
    private String siteId;

    @Id
    @Column(name = "scenic_id")
    public long getScenicId() {
        return scenicId;
    }

    public void setScenicId(long scenicId) {
        this.scenicId = scenicId;
    }

    @Id
    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Basic
    @Column(name = "source_id")
    public long getSourceId() {
        return sourceId;
    }

    public void setSourceId(long sourceId) {
        this.sourceId = sourceId;
    }

    @Basic
    @Column(name = "source_name")
    public String getSourceName() {
        return sourceName;
    }

    public void setSourceName(String sourceName) {
        this.sourceName = sourceName;
    }

    @Basic
    @Column(name = "source_url")
    public String getSourceUrl() {
        return sourceUrl;
    }

    public void setSourceUrl(String sourceUrl) {
        this.sourceUrl = sourceUrl;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Basic
    @Column(name = "etl_status")
    public int getEtlStatus() {
        return etlStatus;
    }

    public void setEtlStatus(int etlStatus) {
        this.etlStatus = etlStatus;
    }

    @Basic
    @Column(name = "etl_order")
    public Integer getEtlOrder() {
        return etlOrder;
    }

    public void setEtlOrder(Integer etlOrder) {
        this.etlOrder = etlOrder;
    }

    @Basic
    @Column(name = "site_id")
    public String getSiteId() {
        return siteId;
    }

    public void setSiteId(String siteId) {
        this.siteId = siteId;
    }

}
