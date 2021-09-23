package com.data.data.hmly.service.entity;

import com.data.data.hmly.enums.ResourceMapType;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by vacuity on 15/10/27.
 */

@Entity
@Table(name = "sys_resource_map")
public class SysResourceMap extends com.framework.hibernate.util.Entity implements java.io.Serializable {


    private static final long serialVersionUID = 1L;


    @Id
    @GeneratedValue
    @Column(name = "ID", unique = true, nullable = false)
    private long id;

    @Column(name = "name")
    private String name;

	@Column(name = "desc")
	private String description;

    @Column(name = "resourceType")
    @Enumerated(EnumType.STRING)
    private ResourceMapType resourceType;

    @Column(name = "addTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date addTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", unique = true, nullable = false, updatable = false)
    private User user;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public ResourceMapType getResourceType() {
        return resourceType;
    }

    public void setResourceType(ResourceMapType resourceType) {
        this.resourceType = resourceType;
    }

    public Date getAddTime() {
        return addTime;
    }

    public void setAddTime(Date addTime) {
        this.addTime = addTime;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
