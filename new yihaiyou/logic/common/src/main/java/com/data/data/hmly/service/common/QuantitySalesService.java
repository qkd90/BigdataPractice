package com.data.data.hmly.service.common;

import com.data.data.hmly.service.common.dao.QuantitySalesDao;
import com.data.data.hmly.service.common.entity.QuantitySales;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;


@Service
public class QuantitySalesService {

    @Resource
    private QuantitySalesDao quantitySalesDao;


    /**
     * 更新
     * @param quantitySales
     */
    public void update(QuantitySales quantitySales) {
        quantitySalesDao.update(quantitySales);
    }

    /**
     * 根据bian'h
     * @param id
     * @return
     */
    public QuantitySales load(Long id) {
        return quantitySalesDao.load(id);
    }


    /**
     * 保存
     * @param quantitySales
     */
    public void save(QuantitySales quantitySales) {
        quantitySalesDao.save(quantitySales);
    }


    /**
     * 删除根据产品编号
     * @param typePriceId
     */
    public void delQuantitySalesByTypePriceId(Long typePriceId) {
        String hql = "delete from QuantitySales where priceTypeId=?";
        quantitySalesDao.updateByHQL(hql, typePriceId);
    }

    /**
     * 获取对应产品下所有的拱量信息
     * @param proId
     * @return
     */
    public List<QuantitySales> getQuantitySalesByTicketId(Long proId) {

        Criteria<QuantitySales> criteria = new Criteria<QuantitySales>(QuantitySales.class);
        criteria.eq("product.id", proId);
        return quantitySalesDao.findByCriteria(criteria);
    }

    /**
     * 根据价格类型获取拱量信息
     * @param priceTypeId
     * @return
     */
    public QuantitySales getQuantitySalesByTypePriceId(Long priceTypeId) {
        Criteria<QuantitySales> criteria = new Criteria<QuantitySales>(QuantitySales.class);
        criteria.eq("priceTypeId", priceTypeId);
        return quantitySalesDao.findUniqueByCriteria(criteria);
    }

    /**
     * 保存所有
     * @param quantitySalesList
     */
    public void saveAll(List<QuantitySales> quantitySalesList) {

        quantitySalesDao.save(quantitySalesList);

    }


}
