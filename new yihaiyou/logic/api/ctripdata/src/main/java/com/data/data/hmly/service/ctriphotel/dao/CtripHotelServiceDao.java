package com.data.data.hmly.service.ctriphotel.dao;

import com.data.data.hmly.service.ctriphotel.entity.CtripHotelService;
import com.framework.hibernate.DataAccess;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vacuity on 15/12/7.
 */

@Repository
public class CtripHotelServiceDao extends DataAccess<CtripHotelService> {

    public void saveList(List<CtripHotelService> ctripHotelServiceList){
        save(ctripHotelServiceList);
    }

    public void delAll(Long hotelId) {
        String hql = " delete CtripHotelService where hotelId = ?";
        updateByHQL(hql, hotelId);
    }
}
