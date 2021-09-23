package com.data.spider.service.pojo.tb;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

/**
 * Created by Sane on 15/9/17.
 */
@Entity
@Table(name = "tb_delicacy_res")
public class TbDelicacyRes extends com.framework.hibernate.util.Entity {
    private long id;
    private Long delicacyId;
    private Long resId;
    private Timestamp modifyTime;
    private Timestamp createTime;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "delicacy_id", nullable = true, insertable = true, updatable = true)
    public Long getDelicacyId() {
        return delicacyId;
    }

    public void setDelicacyId(Long delicacyId) {
        this.delicacyId = delicacyId;
    }

    @Basic
    @Column(name = "res_id", nullable = true, insertable = true, updatable = true)
    public Long getResId() {
        return resId;
    }

    public void setResId(Long resId) {
        this.resId = resId;
    }

    @Basic
    @Column(name = "modify_time", nullable = true, insertable = true, updatable = true)
    public Timestamp getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Timestamp modifyTime) {
        this.modifyTime = modifyTime;
    }

    @Basic
    @Column(name = "create_time", nullable = true, insertable = true, updatable = true)
    public Timestamp getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        TbDelicacyRes that = (TbDelicacyRes) o;

        if (id != that.id) return false;
        if (delicacyId != null ? !delicacyId.equals(that.delicacyId) : that.delicacyId != null) return false;
        if (resId != null ? !resId.equals(that.resId) : that.resId != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (delicacyId != null ? delicacyId.hashCode() : 0);
        result = 31 * result + (resId != null ? resId.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
