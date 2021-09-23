package com.data.data.hmly.service.ctriphotel.dao;

import com.data.data.hmly.service.ctriphotel.entity.CtripHotelDescription;
import com.framework.hibernate.DataAccess;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vacuity on 15/12/7.
 */

@Repository
public class CtripHotelDescriptionDao extends DataAccess<CtripHotelDescription> {

    public void saveList(List<CtripHotelDescription> ctripHotelDescriptionList){
        save(ctripHotelDescriptionList);
    }

    public void delAll(Long hotelId) {
        String hql = " delete CtripHotelDescription where hotelId = ?";
        updateByHQL(hql, hotelId);
    }
}
