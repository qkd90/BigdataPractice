package com.data.data.hmly.util;

import java.util.List;

/**
 * Created by dy on 2016/11/17.
 */
public class JsonTabelDataReader<T> {
    private Long	total;	// 总页数

    private List<T> data;	// 包含实际数组的对象

    private List<T>	footer; // 页面数组对象

    public Long getTotal() {
        return total;
    }

    public void setTotal(Long total) {
        this.total = total;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public List<T> getFooter() {
        return footer;
    }

    public void setFooter(List<T> footer) {
        this.footer = footer;
    }
}
