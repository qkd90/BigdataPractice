package com.data.spider.service.pojo;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * By ZZL 2015.10.26
 */

@Entity
@Table(name = "data_qunar_city")
public class TmpCnCity {

    private Long id;
	/**
	 * 去哪儿网的城市id
	 */
    private Long dataSourceId;
    /**
     * 来源
     */
   private String dataSource;
   /**
    * 来源url
    *
    */
   private String dataSourceUrl;
   /**
    * 去哪儿父级id
    */
   private Long parentSourceId;
    /**
     * 父级省份id
     */
    private Integer parentId;
    /**
     * 城市或省份名称
     */
    private String name;
 

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = true, insertable = true, updatable = true)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
    

    @Basic
    @Column(name = "data_source_id", nullable = true, insertable = true, updatable = true)
    public Long getDataSorceId() {
		return dataSourceId;
	}

	public void setDataSorceId(Long dataSourceId) {
		this.dataSourceId = dataSourceId;
	}
	
	 @Basic
	 @Column(name = "data_source", nullable = true, insertable = true, updatable = true)
	 public String getDataSource() {
		return dataSource;
	}

	public void setDataSource(String dataSource) {
		this.dataSource = dataSource;
	}
	 @Basic
	 @Column(name = "parent_source_id", nullable = true, insertable = true, updatable = true)
	public Long getParentSourceId() {
		return parentSourceId;
	}

	public void setParentSourceId(Long parentSourceId) {
		this.parentSourceId = parentSourceId;
	}

	@Basic
	@Column(name = "data_source_url", nullable = true, insertable = true, updatable = true)
	public String getDataSourceUrl() {
		return dataSourceUrl;
	}

	public void setDataSourceUrl(String dataSourceUrl) {
		this.dataSourceUrl = dataSourceUrl;
	}

	@Basic
	 @Column(name = "parent_id", nullable = true, insertable = true, updatable = true)
	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	 @Basic
	 @Column(name = "name", nullable = true, insertable = true, updatable = true, length = 16)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}


	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TmpCnCity tmpCnCity = (TmpCnCity) o;

        if (id != null ? !id.equals(tmpCnCity.id) : tmpCnCity.id != null) return false;
        if (dataSource != null ? !dataSource.equals(tmpCnCity.dataSource) : tmpCnCity.dataSource != null) return false;
        if (parentId != null ? !parentId.equals(tmpCnCity.parentId) : tmpCnCity.parentId != null) return false;
        if (name != null ? !name.equals(tmpCnCity.name) : tmpCnCity.name != null) return false;
        if (dataSourceId != null ? !dataSourceId.equals(tmpCnCity.dataSourceId) : tmpCnCity.dataSourceId != null) return false;
        if (dataSourceUrl != null ? !dataSourceUrl.equals(tmpCnCity.dataSourceUrl) : tmpCnCity.dataSourceUrl != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (dataSource != null ? dataSource.hashCode() : 0);
        result = 31 * result + (parentId != null ? parentId.hashCode() : 0);
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (dataSourceId != null ? dataSourceId.hashCode() : 0);
        result = 31 * result + (dataSourceUrl != null ? dataSourceUrl.hashCode() : 0);
        return result;
    }
}
