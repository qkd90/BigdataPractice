package com.data.spider.service.pojo.tb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * By ZZL 2015.11.18
 */
@Entity
@javax.persistence.Table(name = "data_scenic_relation")
public class DataScenicRelation extends com.framework.hibernate.util.Entity {
    /**
	 * 
	 */
	private static final long serialVersionUID = 3037329253488622345L;
	
	private Long scenicId;
    private String source;
    private Long sourceId;
    private String sourceName;
    private String sourceUrl;
    private Integer status;
    private Integer etlStatus;
    private Integer etlOrder;
    private String siteId;
    
    @Id
    @Column(name = "scenic_id", nullable = false, insertable = true, updatable = true)
    public Long getScenicId() {
		return scenicId;
	}

	public void setScenicId(Long scenicId) {
		this.scenicId = scenicId;
	}

	 @Basic
	 @Column(name = "source", nullable = true, insertable = true, updatable = true, length = 100)
	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	 @Id
	 @Column(name = "source_id", nullable = false, insertable = true, updatable = true)
	public Long getSourceId() {
		return sourceId;
	}

	public void setSourceId(Long sourceId) {
		this.sourceId = sourceId;
	}
	 @Basic
	 @Column(name = "source_name", nullable = true, insertable = true, updatable = true, length = 100)
	public String getSourceName() {
		return sourceName;
	}

	public void setSourceName(String sourceName) {
		this.sourceName = sourceName;
	}

	 @Basic
	 @Column(name = "source_url", nullable = true, insertable = true, updatable = true, length = 200)
	public String getSourceUrl() {
		return sourceUrl;
	}

	public void setSourceUrl(String sourceUrl) {
		this.sourceUrl = sourceUrl;
	}

	 @Id
	 @Column(name = "status", nullable = false, insertable = true, updatable = true)
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
	 @Id
	 @Column(name = "etl_status", nullable = false, insertable = true, updatable = true)
	public Integer getEtlStatus() {
		return etlStatus;
	}

	public void setEtlStatus(Integer etlStatus) {
		this.etlStatus = etlStatus;
	}
	 @Id
	 @Column(name = "etl_order", nullable = false, insertable = true, updatable = true)
	public Integer getEtletlOrder() {
		return etlOrder;
	}

	public void setEtletlOrder(Integer etletlOrder) {
		this.etlOrder = etletlOrder;
	}
	 @Basic
	 @Column(name = "site_id", nullable = true, insertable = true, updatable = true, length = 200)
	public String getSiteId() {
		return siteId;
	}

	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        DataScenicRelation that = (DataScenicRelation) o;

        if (scenicId != that.scenicId) return false;
        if (source != null ? !source.equals(that.source) : that.source != null) return false;
        if (sourceId != null ? !sourceId.equals(that.sourceId) : that.sourceId != null) return false;
        if (sourceName != null ? !sourceName.equals(that.sourceName) : that.sourceName != null) return false;
        if (sourceUrl != null ? !sourceUrl.equals(that.sourceUrl) : that.sourceUrl != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (etlStatus != null ? !etlStatus.equals(that.etlStatus) : that.etlStatus != null) return false;
        if (etlOrder != null ? !etlOrder.equals(that.etlOrder) : that.etlOrder != null) return false;
        if (siteId != null ? !siteId.equals(that.siteId) : that.siteId != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = 0;
        result = 31 * result + (scenicId != null ? scenicId.hashCode() : 0);
        result = 31 * result + (source != null ? source.hashCode() : 0);
        result = 31 * result + (sourceId != null ? sourceId.hashCode() : 0);
        result = 31 * result + (sourceName != null ? sourceName.hashCode() : 0);
        result = 31 * result + (sourceUrl != null ? sourceUrl.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (etlStatus != null ? etlStatus.hashCode() : 0);
        result = 31 * result + (etlOrder != null ? etlOrder.hashCode() : 0);
        result = 31 * result + (siteId != null ? siteId.hashCode() : 0);
        return result;
    }
}
