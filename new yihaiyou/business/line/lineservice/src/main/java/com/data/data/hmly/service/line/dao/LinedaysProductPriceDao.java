package com.data.data.hmly.service.line.dao;

import com.data.data.hmly.service.line.entity.LinedaysProductPrice;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by caiys on 2016/8/17.
 */
@Repository
public class LinedaysProductPriceDao extends DataAccess<LinedaysProductPrice> {

    /**
     * 查询线路产品关联列表
     * @param linedaysProductPrice
     * @return
     */
    public List<LinedaysProductPrice> listLineProduct(LinedaysProductPrice linedaysProductPrice) {
        Criteria<LinedaysProductPrice> c = new Criteria<LinedaysProductPrice>(LinedaysProductPrice.class);
        if (linedaysProductPrice.getLinedaysId() != null) {
            c.eq("linedays.id", linedaysProductPrice.getLinedaysId());
        }
        if (linedaysProductPrice.getProductType() != null) {
            c.eq("productType", linedaysProductPrice.getProductType());
        }
        return findByCriteria(c);
    }

}
