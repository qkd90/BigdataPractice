package com.data.data.hmly.action.mobile.request;

import com.data.data.hmly.enums.Gender;
import com.data.data.hmly.service.entity.Member;
import com.zuipin.util.StringUtils;

/**
 * Created by huangpeijie on 2016-04-20,0020.
 */
public class SimpleUser {
    private Long id;
    private String head;
    private String nickName;
    private Gender gender;
    private String telephone;
    private String email;
    private Double balance;

    public SimpleUser() {
    }

    public SimpleUser(Member member) {
        this.id = member.getId();
        this.head = cover(member.getHead());
        this.nickName = member.getNickName();
        this.gender = member.getGender();
        this.telephone = member.getTelephone();
        this.email = member.getEmail();
        this.balance = member.getBalance();
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return "";
        } else {
            if (cover.startsWith("http")) {
                return cover;
            } else {
                return "http://7u2inn.com2.z0.glb.qiniucdn.com/" + cover;
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

    public Member toMember(Member member) {
        member.setHead(this.head);
        member.setNickName(this.nickName);
        member.setGender(this.gender);
        member.setTelephone(this.telephone);
        member.setEmail(this.email);
        return member;
    }
}
