package com.data.data.hmly.service.lvxbang.response;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.interfaces.ProductPrice;

import java.util.Date;

/**
 * Created by guoshijie on 2015/12/11.
 */
public class BookingResponse {

	public Long id;
	public Long productId;
	public String name;
	public Float price;
	public Date date;
	public ProductType type;

    public BookingResponse() {

    }

	public BookingResponse(ProductPrice productPrice) {
		this.id = productPrice.getPriceId();
		this.productId = productPrice.getProductId();
		this.name = productPrice.getName();
		this.price = productPrice.getPrice();
		this.date = productPrice.getDate();
	}

	public BookingResponse withDate(Date date) {
		this.date = date;
		return this;
	}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
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

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public ProductType getType() {
        return type;
    }

    public void setType(ProductType type) {
        this.type = type;
    }
}
