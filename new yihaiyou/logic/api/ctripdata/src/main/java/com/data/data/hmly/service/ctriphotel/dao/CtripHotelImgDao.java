package com.data.data.hmly.service.ctriphotel.dao;

import com.data.data.hmly.service.ctriphotel.entity.CtripHotelImg;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vacuity on 15/12/7.
 */

@Repository
public class CtripHotelImgDao extends DataAccess<CtripHotelImg> {

    public void saveList(List<CtripHotelImg> ctripHotelImgList) {
        save(ctripHotelImgList);
    }

    public void delAll(Long hoteId) {
        String hql = " delete CtripHotelImg where hotelId = ?";
        updateByHQL(hql, hoteId);
    }

    public List<CtripHotelImg> getList(Long hotelId) {
        Criteria<CtripHotelImg> criteria = new Criteria<CtripHotelImg>(CtripHotelImg.class);
        criteria.eq("hotelId", hotelId);
        return findByCriteria(criteria);
    }
}
