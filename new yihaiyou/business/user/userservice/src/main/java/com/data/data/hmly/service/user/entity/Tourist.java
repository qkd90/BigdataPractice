package com.data.data.hmly.service.user.entity;

import com.data.data.hmly.enums.Gender;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.user.entity.enums.TouristIdType;
import com.data.data.hmly.service.user.entity.enums.TouristPeopleType;
import com.data.data.hmly.service.user.entity.enums.TouristStatus;
import com.zuipin.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.util.Date;

@Entity
@Table(name = "tourist")
public class Tourist extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", nullable = false, insertable = true, updatable = true)
    private long id;
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userId")
    private User user;
    @Column(name = "name")
    private String name;
    @Column(name = "tel")
    private String tel;
    @Column(name = "idType")
    @Enumerated(EnumType.STRING)
    private TouristIdType idType;
    @Column(name = "idNumber")
    private String idNumber;
    @Column(name = "remark")
    private String remark;
    @Column(name = "modifyTime")
    private Date modifyTime;
    @Column(name = "createTime")
    private Date createTime;

    @Column(name = "email")
    private String email;
    @Column(name = "peopeloType")
    @Enumerated(EnumType.STRING)
    private TouristPeopleType peopleType;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private TouristStatus status;

    @Column(name = "birthday")
    private Date birthday;
    @Column(name = "address")
    private String address;

    @Column(name = "inFerry")
    private Boolean inFerry = false;

    @Transient
    private String peopleTypeStr;

    @Transient
    private String hiddenTel;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public TouristIdType getIdType() {
        return idType;
    }

    public void setIdType(TouristIdType idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public Date getModifyTime() {
        return modifyTime;
    }

    public void setModifyTime(Date modifyTime) {
        this.modifyTime = modifyTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public TouristPeopleType getPeopleType() {
        return peopleType;
    }

    public void setPeopleType(TouristPeopleType peopleType) {
        this.peopleType = peopleType;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public TouristStatus getStatus() {
        return status;
    }

    public void setStatus(TouristStatus status) {
        this.status = status;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPeopleTypeStr() {
        return peopleType.getDescription();
    }

    public void setPeopleTypeStr(String peopleTypeStr) {
        this.peopleTypeStr = peopleTypeStr;
    }

    public String getHiddenTel() {
        if (StringUtils.isBlank(tel)) {
            return "";
        }
        return tel.substring(0, 3) + "****" + tel.substring(7);
    }

    public void setHiddenTel(String hiddenTel) {
        this.hiddenTel = hiddenTel;
    }

    public Boolean getInFerry() {
        return inFerry;
    }

    public void setInFerry(Boolean inFerry) {
        this.inFerry = inFerry;
    }
}
