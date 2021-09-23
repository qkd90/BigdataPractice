package com.data.data.hmly.service.ctripuser.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "ctrip_user")
public class CtripUser extends com.framework.hibernate.util.Entity {
	private static final long serialVersionUID = -3980318556871197112L;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", length = 20)
	private Long	id;
	@Column(name = "allianceId", length = 64)
	private String allianceId;
	@Column(name = "sid", length = 64)
	private String sid;
	@Column(name = "uidKey", length = 256)
	private String uidKey;
	@Column(name = "uniqueUid", length = 256)
	private String uniqueUid;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "createTime", length = 19)
	private Date createTime;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getAllianceId() {
		return allianceId;
	}
	public void setAllianceId(String allianceId) {
		this.allianceId = allianceId;
	}
	public String getSid() {
		return sid;
	}
	public void setSid(String sid) {
		this.sid = sid;
	}
	public String getUidKey() {
		return uidKey;
	}
	public void setUidKey(String uidKey) {
		this.uidKey = uidKey;
	}
	public String getUniqueUid() {
		return uniqueUid;
	}
	public void setUniqueUid(String uniqueUid) {
		this.uniqueUid = uniqueUid;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

}
