package com.data.data.hmly.service.entity;

import com.data.data.hmly.enums.Gender;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by guoshijie on 2015/10/27.
 */
@Entity
@Table(name = "member")
@PrimaryKeyJoinColumn(name = "userId")
@JsonIgnoreProperties
public class Member extends User implements java.io.Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 9177139407343377190L;


    @Column(name = "nickName")
    private String nickName;

    @Column(name = "head")
    private String head;

    @Column(name = "audit_head")
    private String auditHead;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birthday")
    private Date birthday;

    @Column(name = "gender")
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "jifen")
	private Float jifen;

	@Column(name = "telephone")
	private String telephone;

    @Column(name = "is_in_blacklist")
    private Boolean isInBlackList;

    @Column(name = "shenzhouAccessToken")
    private String shenzhouAccessToken;

    @Column(name = "shenzhouRefreshToken")
    private String shenzhouRefreshToken;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "shenzhouTokenDate")
    private Date shenzhouTokenDate;

    @Column(name = "idNumber")
    private String idNumber;

    @ManyToOne
    @JoinColumn(name = "ferryMember")
    private FerryMember ferryMember;

    @Column(name = "auto_login_code")
    private String autoLoginCode;

    @Column(name = "is_auto_login")
    private Boolean isAutoLogin;

    @Transient
    @JsonIgnore
    private Float minJifen;

    @Transient
    @JsonIgnore
    private Float maxJifen;
    @Transient
    private Long teamCount;
    @Transient
    private Long teamCount1;
    @Transient
    private Long teamCount2;
    @Transient
    private Long teamCount3;

    @Transient
    private FerryMember temFerryMember;

    public Member() {
    }

    public Member(Long id, String nickName, String head, String userName) {
        this.id = id;
        this.nickName = nickName;
        this.head = head;
        this.userName = userName;
    }

    @Override
    public String getNickName() {
        return nickName;
    }

    @Override
    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getAuditHead() {
        return auditHead;
    }

    public void setAuditHead(String auditHead) {
        this.auditHead = auditHead;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public Float getJifen() {
        return jifen;
    }

    public void setJifen(Float jifen) {
        this.jifen = jifen;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getIsInBlackList() {
        return isInBlackList;
    }

    public void setIsInBlackList(Boolean isInBlackList) {
        this.isInBlackList = isInBlackList;
    }

    public Float getMinJifen() {
        return minJifen;
    }

    public void setMinJifen(Float minJifen) {
        this.minJifen = minJifen;
    }

    public Float getMaxJifen() {
        return maxJifen;
    }

    public void setMaxJifen(Float maxJifen) {
        this.maxJifen = maxJifen;
    }

    public Gender fmtToGender(Integer sex) {
        if (sex == 1) {
            return Gender.male;
        }
        if (sex == 0) {
            return Gender.female;
        }
        return null;
    }

    public Long getTeamCount() {
        return teamCount;
    }

    public void setTeamCount(Long teamCount) {
        this.teamCount = teamCount;
    }

    public Long getTeamCount1() {
        return teamCount1;
    }

    public void setTeamCount1(Long teamCount1) {
        this.teamCount1 = teamCount1;
    }

    public Long getTeamCount2() {
        return teamCount2;
    }

    public void setTeamCount2(Long teamCount2) {
        this.teamCount2 = teamCount2;
    }

    public Long getTeamCount3() {
        return teamCount3;
    }

    public void setTeamCount3(Long teamCount3) {
        this.teamCount3 = teamCount3;
    }

    public String getShenzhouAccessToken() {
        return shenzhouAccessToken;
    }

    public void setShenzhouAccessToken(String shenzhouAccessToken) {
        this.shenzhouAccessToken = shenzhouAccessToken;
    }

    public String getShenzhouRefreshToken() {
        return shenzhouRefreshToken;
    }

    public void setShenzhouRefreshToken(String shenzhouRefreshToken) {
        this.shenzhouRefreshToken = shenzhouRefreshToken;
    }

    public Date getShenzhouTokenDate() {
        return shenzhouTokenDate;
    }

    public void setShenzhouTokenDate(Date shenzhouTokenDate) {
        this.shenzhouTokenDate = shenzhouTokenDate;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public FerryMember getFerryMember() {
        return ferryMember;
    }

    public void setFerryMember(FerryMember ferryMember) {
        this.ferryMember = ferryMember;
    }

    public String getAutoLoginCode() {
        return autoLoginCode;
    }

    public void setAutoLoginCode(String autoLoginCode) {
        this.autoLoginCode = autoLoginCode;
    }

    public Boolean getIsAutoLogin() {
        return isAutoLogin;
    }

    public void setIsAutoLogin(Boolean isAutoLogin) {
        this.isAutoLogin = isAutoLogin;
    }

    public FerryMember getTemFerryMember() {
        return temFerryMember;
    }

    public void setTemFerryMember(FerryMember temFerryMember) {
        this.temFerryMember = temFerryMember;
    }
}
