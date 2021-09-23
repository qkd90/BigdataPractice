package com.data.data.hmly.action.outOrder.dataEntities;

/**
 * Created by dy on 2016/2/29.
 */
public class JszxOrderDetailEntity {

    private String name;
    private Float price;
    private String type;
    private Integer count;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
