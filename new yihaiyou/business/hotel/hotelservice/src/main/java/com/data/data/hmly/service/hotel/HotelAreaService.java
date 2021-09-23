package com.data.data.hmly.service.hotel;

import com.data.data.hmly.service.hotel.dao.HotelAreaDao;
import com.data.data.hmly.service.hotel.entity.HotelArea;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by HMLY on 2016/5/20.
 */
@Service
public class HotelAreaService {

    @Resource
    private HotelAreaDao hotelAreaDao;

    public List<HotelArea> getByHotel(Long hotelId) {
        Criteria<HotelArea> criteria = new Criteria<HotelArea>(HotelArea.class);
        criteria.eq("hotelId", hotelId);
        return hotelAreaDao.findByCriteria(criteria);
    }
}
