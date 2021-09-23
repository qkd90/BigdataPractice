package com.data.data.hmly.service.ctriphotel.dao;

import com.data.data.hmly.service.ctriphotel.entity.CtripHotelPreferential;
import com.framework.hibernate.DataAccess;
import org.springframework.stereotype.Repository;

/**
 * Created by vacuity on 15/12/28.
 */

@Repository
public class CtripHotelPreferentialDao extends DataAccess<CtripHotelPreferential> {

    public void delAll() {
        String hql = " delete CtripHotelPreferential";
        updateByHQL(hql);
    }
}
