package com.data.data.hmly.action.yhypc.vo;

/**
 * Created by zzl on 2017/2/13.
 */
public class ScenicInfoVo {

    private Long id;
    private String name;
    private Integer adviceMinute;
    private String address;
    private Float price;
    private String shortComment;
    private String cover;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAdviceMinute() {
        return adviceMinute;
    }

    public void setAdviceMinute(Integer adviceMinute) {
        this.adviceMinute = adviceMinute;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getShortComment() {
        return shortComment;
    }

    public void setShortComment(String shortComment) {
        this.shortComment = shortComment;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }
}
