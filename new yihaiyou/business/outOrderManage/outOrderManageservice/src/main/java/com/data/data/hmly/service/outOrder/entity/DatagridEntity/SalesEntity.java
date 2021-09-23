package com.data.data.hmly.service.outOrder.entity.DatagridEntity;

/**
 * Created by dy on 2016/5/11.
 */
public class SalesEntity {

    private String name;
    private Integer count;
    private Float totalPrice;
    private Long productId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Float getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Float totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }
}
