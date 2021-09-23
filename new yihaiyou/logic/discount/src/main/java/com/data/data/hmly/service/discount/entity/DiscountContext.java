package com.data.data.hmly.service.discount.entity;

import com.data.data.hmly.service.discount.strategy.DiscountStrategy;

/**
 * Created by huangpeijie on 2016-05-09,0009.
 */
public class DiscountContext {
    private DiscountStrategy discountStrategy;

    public DiscountContext(DiscountStrategy discountStrategy) {
        this.discountStrategy = discountStrategy;
    }

    public Boolean checkUse() {
        return this.discountStrategy.checkUse();
    }

    public Float discount() {
        return this.discountStrategy.discount();
    }
}
