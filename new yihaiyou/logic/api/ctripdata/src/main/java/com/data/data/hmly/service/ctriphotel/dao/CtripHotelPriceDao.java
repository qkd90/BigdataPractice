package com.data.data.hmly.service.ctriphotel.dao;

import com.data.data.hmly.service.ctriphotel.entity.CtripHotelPrice;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vacuity on 15/12/7.
 */

@Repository
public class CtripHotelPriceDao extends DataAccess<CtripHotelPrice> {

    public void saveList(List<CtripHotelPrice> ctripHotelPriceList) {
        save(ctripHotelPriceList);
    }

    public void delAll(Long hotelId) {
        String hql = " delete CtripHotelPrice where hotelId = ?";
        updateByHQL(hql, hotelId);
    }

    public List<CtripHotelPrice> getList(Long hotelId) {
        Criteria<CtripHotelPrice> criteria = new Criteria<CtripHotelPrice>(CtripHotelPrice.class);
        criteria.eq("hotelId", hotelId);
        return findByCriteria(criteria);
    }
}
