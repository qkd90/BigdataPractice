package com.data.data.hmly.service.ctriphotel.dao;

import com.data.data.hmly.service.ctriphotel.entity.CtripHotelRoom;
import com.framework.hibernate.DataAccess;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vacuity on 15/12/7.
 */

@Repository
public class CtripHotelRoomDao extends DataAccess<CtripHotelRoom> {

    public void saveList(List<CtripHotelRoom> ctripHotelRoomList) {
        save(ctripHotelRoomList);
    }

    public void delAll(Long hotelId) {
        String hql = " delete CtripHotelRoom where hotelId = ?";
        updateByHQL(hql, hotelId);
    }
}
