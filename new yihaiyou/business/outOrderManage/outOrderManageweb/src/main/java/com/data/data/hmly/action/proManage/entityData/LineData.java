package com.data.data.hmly.action.proManage.entityData;

import java.util.Date;

/**
 * Created by dy on 2016/3/17.
 */
public class LineData {

    private Long id;                //线路编号
    private String name;            //线路名称
    private Integer playDay;        //游玩天数

    private Float adultprice;       //线路成人价格
    private Float childprice;       //线路儿童价格

    private Long companyId;         //供应商编号
    private String companyName;     //供应商名称

    private String companyPhone;    //供应商手机
    private String companyQQ;       //供应商QQ




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

    public Integer getPlayDay() {
        return playDay;
    }

    public void setPlayDay(Integer playDay) {
        this.playDay = playDay;
    }

    public Long getCompanyId() {
        return companyId;
    }

    public void setCompanyId(Long companyId) {
        this.companyId = companyId;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Float getAdultprice() {
        return adultprice;
    }

    public void setAdultprice(Float adultprice) {
        this.adultprice = adultprice;
    }

    public Float getChildprice() {
        return childprice;
    }

    public void setChildprice(Float childprice) {
        this.childprice = childprice;
    }

    public String getCompanyPhone() {
        return companyPhone;
    }

    public void setCompanyPhone(String companyPhone) {
        this.companyPhone = companyPhone;
    }

    public String getCompanyQQ() {
        return companyQQ;
    }

    public void setCompanyQQ(String companyQQ) {
        this.companyQQ = companyQQ;
    }
}
