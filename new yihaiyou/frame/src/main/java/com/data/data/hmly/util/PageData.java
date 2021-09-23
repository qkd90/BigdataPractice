package com.data.data.hmly.util;

/**
 * Created by dy on 2016/11/18.
 */

import java.util.List;

/**
 * Created by dy on 2016/11/18.
 */
public class PageData<T> {

    private Integer iDisplayLength = 10;         //每页显示多少条
    private Integer iDisplayStart  = 0;          //从第几条记录开始显示
    private List<T> aaData;                 //分页数据
    private Integer iTotalDisplayRecords;   //设置为总条数
    private Integer iTotalRecords;          //设置为总条数
    private Integer sEcho = 1;                  //原样返回

    public Integer getiDisplayStart() {
        return iDisplayStart;
    }

    public void setiDisplayStart(Integer iDisplayStart) {
        this.iDisplayStart = iDisplayStart;
    }

    public List<T> getAaData() {
        return aaData;
    }

    public void setAaData(List<T> aaData) {
        this.aaData = aaData;
    }

    public Integer getiTotalDisplayRecords() {
        return iTotalDisplayRecords;
    }

    public void setiTotalDisplayRecords(Integer iTotalDisplayRecords) {
        this.iTotalDisplayRecords = iTotalDisplayRecords;
    }

    public Integer getiTotalRecords() {
        return iTotalRecords;
    }

    public void setiTotalRecords(Integer iTotalRecords) {
        this.iTotalRecords = iTotalRecords;
    }

    public Integer getsEcho() {
        return sEcho;
    }

    public void setsEcho(Integer sEcho) {
        this.sEcho = sEcho;
    }

    public Integer getiDisplayLength() {
        return iDisplayLength;
    }

    public void setiDisplayLength(Integer iDisplayLength) {
        this.iDisplayLength = iDisplayLength;
    }
}