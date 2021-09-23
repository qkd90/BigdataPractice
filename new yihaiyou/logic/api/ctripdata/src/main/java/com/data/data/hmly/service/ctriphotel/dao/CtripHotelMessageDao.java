package com.data.data.hmly.service.ctriphotel.dao;

import com.data.data.hmly.service.ctriphotel.entity.CtripHotelMessage;
import com.framework.hibernate.DataAccess;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vacuity on 15/12/7.
 */

@Repository
public class CtripHotelMessageDao extends DataAccess<CtripHotelMessage> {

    public void saveList(List<CtripHotelMessage> ctripHotelMessageList) {
        save(ctripHotelMessageList);
    }

    public void delAll(Long hotelId) {
        String hql = " delete CtripHotelMessage where hotelId = ?";
        updateByHQL(hql, hotelId);
    }
}
