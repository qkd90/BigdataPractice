package com.data.data.hmly.action.yhy.vo;

/**
 * Created by zzl on 2016/11/28.
 */
public class TopProductData {

    private Long id;
    private String name;

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

    @Override
    public int hashCode() {
        return super.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null) {
            TopProductData tpd = (TopProductData) obj;
            return tpd.getId().equals(this.id);
        }
        return false;
    }
}
