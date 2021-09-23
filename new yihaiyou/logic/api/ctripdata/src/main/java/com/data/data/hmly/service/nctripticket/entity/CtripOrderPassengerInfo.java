package com.data.data.hmly.service.nctripticket.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.zuipin.util.DateUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.util.Date;

@Entity
@Table(name = "nctrip_order_passenger_info")
public class CtripOrderPassengerInfo extends com.framework.hibernate.util.Entity {
    @Id
    @GeneratedValue
    @Column(name = "id", length = 20)
    private Long        id;		          // 标识
    @Column(name = "orderFormInfoId", length = 20)
    private Long        orderFormInfoId;		          // 订单标识
    @Column(name = "customerInfoId", length = 20)
    private Long customerInfoId;	 // 客人信息ID
    @Column(name = "infoId", length = 11)
    private Integer infoId;	     // 常用姓名Id号
    @Column(name = "cName", length = 64)
    private String cName;   // （必填）中文姓名
    @Column(name = "idCardNo", length = 64)
    private String idCardNo;	// 证件号码
    @Column(name = "idCardType", length = 11)
    private Integer idCardType;	// 证件类型 1:身份证，2：护照，3：学生证，4：军人证，6：驾驶证，7：回乡证，8：台胞证 10：港澳通行证,11:国际海员证，20：外国人永久居留证，22：台湾通行证，99：其他
    @Column(name = "ageType", length = 16)
    private String ageType;	// 类型（成人/儿童/婴儿） ADU=成人 CHI=儿童 BAB=婴儿
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birthDate", length = 19)
    private Date birthDate;	// 出生年月日
    @Column(name = "birthCity", length = 256)
    private String birthCity;	// 出生地
    @Column(name = "cardCity", length = 256)
    private String cardCity;	// 签发地
    @Column(name = "eName", length = 64)
    private String eName; // 英文姓名（如CName不填，EName必填）
    @Column(name = "contactInfo", length = 32)
    private String contactInfo;     // （必填）联系信息（如手机号）
    @Column(name = "gender", length = 11)
    private Integer gender;     // 性别(0:男,1女)
    @Column(name = "visaOrgan", length = 256)
    private String visaOrgan; // 发证机关
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "passportDate", length = 19)
    private Date passportDate; // 发证日期
    @Column(name = "visaCountry", length = 256)
    private String visaCountry; // 发签证国
    @Column(name = "passportNo", length = 256)
    private String passportNo; // 护照号码
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "issueDate", length = 19)
    private Date issueDate;	// 签发日期
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "idCardTimelimit", length = 19)
    private Date idCardTimelimit; // 护照有效期
    @Column(name = "passportType", length = 64)
    private String passportType; // 护照类型
    @Column(name = "nationality", length = 64)
    private String nationality; // 国籍简码(中国：CN)

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderFormInfoId() {
        return orderFormInfoId;
    }

    public void setOrderFormInfoId(Long orderFormInfoId) {
        this.orderFormInfoId = orderFormInfoId;
    }

    @JsonProperty("CustomerInfoID")
    public Long getCustomerInfoId() {
        return customerInfoId;
    }

    public void setCustomerInfoId(Long customerInfoId) {
        this.customerInfoId = customerInfoId;
    }

    @JsonProperty("InfoID")
    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    @JsonProperty("CName")
    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName;
    }

    @JsonProperty("IdCardNo")
    public String getIdCardNo() {
        return idCardNo;
    }

    public void setIdCardNo(String idCardNo) {
        this.idCardNo = idCardNo;
    }

    @JsonProperty("IdCardType")
    public Integer getIdCardType() {
        return idCardType;
    }

    public void setIdCardType(Integer idCardType) {
        this.idCardType = idCardType;
    }

    @JsonProperty("AgeType")
    public String getAgeType() {
        return ageType;
    }

    public void setAgeType(String ageType) {
        this.ageType = ageType;
    }

    @JsonProperty("BirthDate")
    public String getBirthDateStr() {
        return DateUtils.format(birthDate, "yyyy-MM-dd'T'HH:mm:ss");
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    @JsonProperty("BirthCity")
    public String getBirthCity() {
        return birthCity;
    }

    public void setBirthCity(String birthCity) {
        this.birthCity = birthCity;
    }

    @JsonProperty("CardCity")
    public String getCardCity() {
        return cardCity;
    }

    public void setCardCity(String cardCity) {
        this.cardCity = cardCity;
    }

    @JsonProperty("EName")
    public String geteName() {
        return eName;
    }

    public void seteName(String eName) {
        this.eName = eName;
    }

    @JsonProperty("ContactInfo")
    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    @JsonProperty("Gender")
    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    @JsonProperty("VisaOrgan")
    public String getVisaOrgan() {
        return visaOrgan;
    }

    public void setVisaOrgan(String visaOrgan) {
        this.visaOrgan = visaOrgan;
    }

    @JsonProperty("PassportDate")
    public String getPassportDateStr() {
        return DateUtils.format(passportDate, "yyyy-MM-dd'T'HH:mm:ss");
    }

    public Date getPassportDate() {
        return passportDate;
    }

    public void setPassportDate(Date passportDate) {
        this.passportDate = passportDate;
    }

    @JsonProperty("VisaCountry")
    public String getVisaCountry() {
        return visaCountry;
    }

    public void setVisaCountry(String visaCountry) {
        this.visaCountry = visaCountry;
    }

    @JsonProperty("PassportNo")
    public String getPassportNo() {
        return passportNo;
    }

    public void setPassportNo(String passportNo) {
        this.passportNo = passportNo;
    }

    @JsonProperty("IssueDate")
    public String getIssueDateStr() {
        return DateUtils.format(issueDate, "yyyy-MM-dd'T'HH:mm:ss");
    }

    public Date getIssueDate() {
        return issueDate;
    }

    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    @JsonProperty("IdCardTimelimit")
    public String getIdCardTimelimitStr() {
        return DateUtils.format(idCardTimelimit, "yyyy-MM-dd'T'HH:mm:ss");
    }

    public Date getIdCardTimelimit() {
        return idCardTimelimit;
    }

    public void setIdCardTimelimit(Date idCardTimelimit) {
        this.idCardTimelimit = idCardTimelimit;
    }

    @JsonProperty("PassportType")
    public String getPassportType() {
        return passportType;
    }

    public void setPassportType(String passportType) {
        this.passportType = passportType;
    }

    @JsonProperty("Nationality")
    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }
}
