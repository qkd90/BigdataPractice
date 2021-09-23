package com.data.data.hmly.service.common;

import com.data.data.hmly.service.common.dao.QuantitySalesDao;
import com.data.data.hmly.service.common.dao.QuantitySalesDetailDao;
import com.data.data.hmly.service.common.entity.QuantitySales;
import com.data.data.hmly.service.common.entity.QuantitySalesDetail;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;


@Service
public class QuantitySalesDetailService {

    @Resource
    private QuantitySalesDao quantitySalesDao;

    @Resource
    private QuantitySalesDetailDao quantitySalesDetailDao;


    /**
     * 根据拱量主体信息和数量获取该数量下的范围detail
     * @param quantitySales
     * @param proCounts
     * @return
     */
    public List<QuantitySalesDetail> getQuantityByProCounts(QuantitySales quantitySales) {

        Criteria<QuantitySalesDetail> criteria = new Criteria<QuantitySalesDetail>(QuantitySalesDetail.class);

        criteria.eq("quantitySales", quantitySales);

//        criteria.ge("numStart", proCounts);

//        criteria.gt("numEnd", proCounts);

        return quantitySalesDetailDao.findByCriteria(criteria);
    }


    /**
     * 根据拱量信息获取详情列表
     * @param quantitySales
     * @return
     */
    public List<QuantitySalesDetail> getListByQuantitySales(QuantitySales quantitySales) {
        Criteria<QuantitySalesDetail> criteria = new Criteria<QuantitySalesDetail>(QuantitySalesDetail.class);
        criteria.eq("quantitySales.id", quantitySales.getId());
        return quantitySalesDetailDao.findByCriteria(criteria);
    }


    /**
     * 根据quantitySalesId删除
     * @param quantitySalesId
     */
    public void delByQuantitySalesId(Long quantitySalesId) {
        QuantitySales quantitySales = new QuantitySales();
        quantitySales.setId(quantitySalesId);
        List<QuantitySalesDetail> quantitySalesDetails = getListByQuantitySales( quantitySales);

        if (!quantitySalesDetails.isEmpty()) {
            quantitySalesDetailDao.deleteAll(quantitySalesDetails);
        }
    }




    /**
     * 批量保存
     * @param quantitySalesDetailList
     */
    public void saveAll(List<QuantitySalesDetail> quantitySalesDetailList) {
        quantitySalesDetailDao.save(quantitySalesDetailList);
    }




}
