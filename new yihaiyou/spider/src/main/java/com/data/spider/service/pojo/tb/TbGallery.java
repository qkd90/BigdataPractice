package com.data.spider.service.pojo.tb;

import com.framework.hibernate.util.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;

/**
 * Created by Sane on 15/9/15.
 */
@Entity
@Table(name = "tb_gallery")
@XmlRootElement
public class TbGallery extends com.framework.hibernate.util.Entity {

//    @Id
//    @GeneratedValue
    private Long id;
    private Long scenicId;
    private String category;
    private String content;
    private Timestamp modifyTime;
    private Timestamp createTime;

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
    @Column(name = "scenic_id", nullable = false, insertable = true, updatable = true)
    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    @Basic
    @Column(name = "category", nullable = false, insertable = true, updatable = true, length = 16)
    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Basic
    @Column(name = "content", nullable = true, insertable = true, updatable = true, length = 500)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

        TbGallery tbGallery = (TbGallery) o;

        if (id != null ? !id.equals(tbGallery.id) : tbGallery.id != null) return false;
        if (scenicId != null ? !scenicId.equals(tbGallery.scenicId) : tbGallery.scenicId != null) return false;
        if (category != null ? !category.equals(tbGallery.category) : tbGallery.category != null) return false;
        if (content != null ? !content.equals(tbGallery.content) : tbGallery.content != null) return false;
        if (modifyTime != null ? !modifyTime.equals(tbGallery.modifyTime) : tbGallery.modifyTime != null) return false;
        if (createTime != null ? !createTime.equals(tbGallery.createTime) : tbGallery.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (scenicId != null ? scenicId.hashCode() : 0);
        result = 31 * result + (category != null ? category.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }
}
