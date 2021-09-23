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
@Table(name = "tb_gallery_image")
@XmlRootElement
public class TbGalleryImage extends com.framework.hibernate.util.Entity {
    private long id;
    private String addressLarge;
    private String addressMedium;
    private String addressSmall;
    private String title;
    private String content;
    private int width;
    private int height;
    private long size;
    private Integer imageOrder;
    private Timestamp modifyTime;
    private Timestamp createTime;
    private TbGallery tbGalleryByGalleryId;

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = true, insertable = true, updatable = true)
    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "address_large", nullable = true, insertable = true, updatable = true, length = 200)
    public String getAddressLarge() {
        return addressLarge;
    }

    public void setAddressLarge(String addressLarge) {
        this.addressLarge = addressLarge;
    }

    @Basic
    @Column(name = "address_medium", nullable = true, insertable = true, updatable = true, length = 200)
    public String getAddressMedium() {
        return addressMedium;
    }

    public void setAddressMedium(String addressMedium) {
        this.addressMedium = addressMedium;
    }

    @Basic
    @Column(name = "address_small", nullable = true, insertable = true, updatable = true, length = 200)
    public String getAddressSmall() {
        return addressSmall;
    }

    public void setAddressSmall(String addressSmall) {
        this.addressSmall = addressSmall;
    }

    @Basic
    @Column(name = "title", nullable = true, insertable = true, updatable = true, length = 100)
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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
    @Column(name = "width", nullable = true, insertable = true, updatable = true)
    public int getWidth() {
        return width;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    @Basic
    @Column(name = "height", nullable = true, insertable = true, updatable = true)
    public int getHeight() {
        return height;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    @Basic
    @Column(name = "size", nullable = true, insertable = true, updatable = true)
    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }

    @Basic
    @Column(name = "image_order", nullable = true, insertable = true, updatable = true)
    public Integer getImageOrder() {
        return imageOrder;
    }

    public void setImageOrder(Integer imageOrder) {
        this.imageOrder = imageOrder;
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

        TbGalleryImage that = (TbGalleryImage) o;

        if (id != that.id) return false;
        if (width != that.width) return false;
        if (height != that.height) return false;
        if (size != that.size) return false;
        if (addressLarge != null ? !addressLarge.equals(that.addressLarge) : that.addressLarge != null) return false;
        if (addressMedium != null ? !addressMedium.equals(that.addressMedium) : that.addressMedium != null)
            return false;
        if (addressSmall != null ? !addressSmall.equals(that.addressSmall) : that.addressSmall != null) return false;
        if (title != null ? !title.equals(that.title) : that.title != null) return false;
        if (content != null ? !content.equals(that.content) : that.content != null) return false;
        if (imageOrder != null ? !imageOrder.equals(that.imageOrder) : that.imageOrder != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (addressLarge != null ? addressLarge.hashCode() : 0);
        result = 31 * result + (addressMedium != null ? addressMedium.hashCode() : 0);
        result = 31 * result + (addressSmall != null ? addressSmall.hashCode() : 0);
        result = 31 * result + (title != null ? title.hashCode() : 0);
        result = 31 * result + (content != null ? content.hashCode() : 0);
        result = 31 * result + width;
        result = 31 * result + height;
        result = 31 * result + (int) (size ^ (size >>> 32));
        result = 31 * result + (imageOrder != null ? imageOrder.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @ManyToOne
    @JoinColumn(name = "gallery_id", referencedColumnName = "id", nullable = false)
    public TbGallery getTbGalleryByGalleryId() {
        return tbGalleryByGalleryId;
    }

    public void setTbGalleryByGalleryId(TbGallery tbGalleryByGalleryId) {
        this.tbGalleryByGalleryId = tbGalleryByGalleryId;
    }
}
