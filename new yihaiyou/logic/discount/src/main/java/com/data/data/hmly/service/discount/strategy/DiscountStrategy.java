package com.data.data.hmly.service.discount.strategy;

/**
 * Created by huangpeijie on 2016-05-09,0009.
 */
public interface DiscountStrategy {

    /**
     * 验证是否符合优惠条件
     *
     * @return
     */
    public Boolean checkUse();

    /**
     * 计算优惠后价格
     *
     * @return
     */
    public Float discount();
}
