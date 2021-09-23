package com.data.data.hmly.service.common.entity.interfaces;

import com.data.data.hmly.service.common.entity.enums.ProductType;

import java.util.Date;

/**
 * Created by guoshijie on 2015/12/11.
 */
public interface ProductPrice {

	public Long getProductId();

	public Long getPriceId();

	public String getName();

	public Float getPrice();

	public Date getDate();

	public ProductType getProductType();


}
