package com.data.data.hmly.service.ctriphotel.dao;

import com.data.data.hmly.service.ctriphotel.entity.CtripHotelId;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vacuity on 15/12/4.
 */
@Repository
public class CtripHotelIdDao extends DataAccess<CtripHotelId> {

    public List<CtripHotelId> getList(Long cityId, Page page) {
        Criteria<CtripHotelId> criteria = new Criteria<CtripHotelId>(CtripHotelId.class);
        criteria.eq("cityId", cityId);
        return findByCriteria(criteria, page);
    }

    public void delAll(Long cityId) {
        String hql = " delete CtripHotelId where cityId = ?";
        updateByHQL(hql, cityId);
    }
}
