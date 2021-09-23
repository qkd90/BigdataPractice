package com.data.data.hmly.action.yihaiyou.request;

import com.data.data.hmly.enums.Gender;
import com.data.data.hmly.service.entity.Member;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;

/**
 * Created by huangpeijie on 2016-04-20,0020.
 */
public class SimpleUser {
    private Long id;
    private String head;
    private String userName;
    private String nickName;
    private Gender gender;
    private String telephone;
    private String email;
    private Double balance;
    private String idNumber;
    private String ferryUserName;
    private Boolean hasPayPassword;
    private Boolean isReal;
    private String bankNo;

    public SimpleUser() {
    }

    public SimpleUser(Member member) {
        if (member.getFerryMember() != null) {
            this.ferryUserName = member.getFerryMember().getName();
            this.isReal = member.getFerryMember().getIsReal();
        } else {
            this.isReal = false;
        }
        this.id = member.getId();
        this.head = cover(member.getHead());
        this.userName = member.getUserName();
        this.nickName = member.getNickName();
        this.gender = member.getGender();
        this.email = member.getEmail();
        this.balance = member.getBalance();
        this.telephone = member.getTelephone();
        this.hasPayPassword = StringUtils.isNotBlank(member.getPayPassword());
//        if (this.isReal) {
//            if (StringUtils.isNotBlank(member.getIdNumber())) {
//                this.idNumber = member.getIdNumber().substring(0, 1) + "**************" + member.getIdNumber().substring(member.getIdNumber().length() - 1, member.getIdNumber().length());
//            }
//            if (StringUtils.isNotBlank(member.getBankNo())) {
//                this.bankNo = "************" + member.getBankNo().substring(member.getBankNo().length() - 4, member.getBankNo().length());
//            }
//        } else {
        this.idNumber = member.getIdNumber();
        this.bankNo = member.getBankNo();
//        }
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return "";
        } else {
            if (cover.startsWith("http")) {
                return cover;
            } else {
                return QiniuUtil.URL + cover;
            }
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getFerryUserName() {
        return ferryUserName;
    }

    public void setFerryUserName(String ferryUserName) {
        this.ferryUserName = ferryUserName;
    }

    public Boolean getHasPayPassword() {
        return hasPayPassword;
    }

    public void setHasPayPassword(Boolean hasPayPassword) {
        this.hasPayPassword = hasPayPassword;
    }

    public String getBankNo() {
        return bankNo;
    }

    public void setBankNo(String bankNo) {
        this.bankNo = bankNo;
    }

    public Boolean getIsReal() {
        return isReal;
    }

    public void setIsReal(Boolean isReal) {
        this.isReal = isReal;
    }

    public Member toMember(Member member) {
        member.setHead(StringUtils.htmlEncode(this.head));
        member.setUserName(StringUtils.htmlEncode(this.userName));
        member.setNickName(StringUtils.htmlEncode(this.nickName));
        member.setGender(this.gender);
        member.setTelephone(StringUtils.htmlEncode(this.telephone));
        member.setEmail(StringUtils.htmlEncode(this.email));
        member.setIdNumber(StringUtils.htmlEncode(this.idNumber));
        member.setBankNo(StringUtils.htmlEncode(this.bankNo));
        return member;
    }
}
