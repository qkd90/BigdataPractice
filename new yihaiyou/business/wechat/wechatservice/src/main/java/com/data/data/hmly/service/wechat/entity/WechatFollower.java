package com.data.data.hmly.service.wechat.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.framework.hibernate.util.Entity;

/**
 * 关注用户类
 * 
 * @author huangpeijie
 *
 */
@javax.persistence.Entity
@Table(name = "wx_follower")
public class WechatFollower extends Entity implements Serializable {

	private static final long serialVersionUID = -1690906106930903058L;

	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;

	@Column(name = "openId")
	private String openId;

	//是否关注，0表示没有关注，获取不到其它信息
	@Transient
	private Integer subscribe;

	// 昵称
	@Column(name = "nickName")
	private String nickName;

	// 性别，1表示男性，2表示女性，0表示未知
	@Column(name = "sex")
	private Integer sex;

	// 城市
	@Column(name = "city")
	private String city;

	// 省份
	@Column(name = "province")
	private String province;

	// 国家
	@Column(name = "country")
	private String country;

	// 语言，zh_CN 简体，zh_TW 繁体，en 英语
	@Column(name = "language")
	private String language;

	// 头像，最后一个数值代表正方形头像大小（有0、46、64、96、132数值可选，0代表640*640正方形头像）
	@Column(name = "headImgUrl")
	private String headImgUrl;

	// 关注时间
	@Column(name = "subscribeTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date subscribeTime;

	@Column(name = "unionId")
	private String unionId;

	// 公众号对关注者的备注
	@Column(name = "remark")
	private String remark;

	// 分组
	@Column(name = "groupId")
	private Long groupId;

	// 所关注公众号
	@ManyToOne
	@JoinColumn(name = "followAccountId")
	private WechatAccount followAccount;
	
	//创建时间
	@Column(name="createTime")
	@Temporal(TemporalType.TIMESTAMP)
	private Date createTime;

    @Transient
    private Boolean isSupporter;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOpenId() {
        return openId;
    }

    public void setOpenId(String openId) {
        this.openId = openId;
    }

    public Integer getSubscribe() {
        return subscribe;
    }

    public void setSubscribe(Integer subscribe) {
        this.subscribe = subscribe;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getHeadImgUrl() {
        return headImgUrl;
    }

    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl;
    }

    public Date getSubscribeTime() {
        return subscribeTime;
    }

    public void setSubscribeTime(Date subscribeTime) {
        this.subscribeTime = subscribeTime;
    }

    public String getUnionId() {
        return unionId;
    }

    public void setUnionId(String unionId) {
        this.unionId = unionId;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Long getGroupId() {
        return groupId;
    }

    public void setGroupId(Long groupId) {
        this.groupId = groupId;
    }

    public WechatAccount getFollowAccount() {
        return followAccount;
    }

    public void setFollowAccount(WechatAccount followAccount) {
        this.followAccount = followAccount;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Boolean getIsSupporter() {
        return isSupporter;
    }

    public void setIsSupporter(Boolean isSupporter) {
        this.isSupporter = isSupporter;
    }
}
