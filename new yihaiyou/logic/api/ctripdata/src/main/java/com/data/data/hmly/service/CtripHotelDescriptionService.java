package com.data.data.hmly.service;

import com.data.data.hmly.service.ctriphotel.dao.CtripHotelDescriptionDao;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotelDescription;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by vacuity on 15/12/31.
 */
@Service
public class CtripHotelDescriptionService {

    @Resource
    private CtripHotelDescriptionDao hotelDescriptionDao;

    // 一句话点评
    public CtripHotelDescription getShortDescription(Long hotelId) {
        Criteria<CtripHotelDescription> criteria = new Criteria<CtripHotelDescription>(CtripHotelDescription.class);
        criteria.eq("hotelId", hotelId);
        criteria.eq("category", 8);
        return hotelDescriptionDao.findUniqueByCriteria(criteria);
    }

    // 酒店简介
    // 官方文档说5是长描述，11是简介，但是数据库中的数据100条只有三条有11，所以暂时用长描述代替简介
    public CtripHotelDescription getLongDescription(Long hotelId) {
        Criteria<CtripHotelDescription> criteria = new Criteria<CtripHotelDescription>(CtripHotelDescription.class);
        criteria.eq("hotelId", hotelId);
        criteria.eq("category", 5);
        return hotelDescriptionDao.findUniqueByCriteria(criteria);
    }
}
