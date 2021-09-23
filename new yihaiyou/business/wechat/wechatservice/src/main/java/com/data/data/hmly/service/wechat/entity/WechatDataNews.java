package com.data.data.hmly.service.wechat.entity;

import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.goods.entity.Category;
import com.framework.hibernate.util.Entity;

import javax.persistence.*;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by vacuity on 15/11/19.
 */

@javax.persistence.Entity
@Table(name = "wx_data_img_text")
public class WechatDataNews extends Entity implements Serializable {

    private static final long	serialVersionUID	= -1690906106930903058L;

    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "title")
    private String title;

    @ManyToOne
    @JoinColumn(name = "category")
    private Category category;
    
    @Column(name = "img_path")
    private String img_path;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "data_item_id")
    private WechatDataItem dataItem;
    
    @Column(name = "content")
    private String content;
    
    @Column(name = "course_img")
    private String isChecked;
    
    @Column(name = "author")
    private String author;
    
    @Column(name = "abstract_text")
    private String abstractText;
    
    @Column(name = "url")
    private String url;

    @Column(name = "updateTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateTime;

    @Column(name = "createTime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date createTime;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private SysUser user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getImg_path() {
		return img_path;
	}

	public void setImg_path(String img_path) {
		this.img_path = img_path;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getAbstractText() {
		return abstractText;
	}

	public void setAbstractText(String abstractText) {
		this.abstractText = abstractText;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public SysUser getUser() {
		return user;
	}

	public void setUser(SysUser user) {
		this.user = user;
	}

	public WechatDataItem getDataItem() {
		return dataItem;
	}

	public void setDataItem(WechatDataItem dataItem) {
		this.dataItem = dataItem;
	}

	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getIsChecked() {
		return isChecked;
	}

	public void setIsChecked(String isChecked) {
		this.isChecked = isChecked;
	}

}
