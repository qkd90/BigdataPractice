package com.data.data.hmly.service.hotel;

import com.data.data.hmly.service.hotel.dao.HotelExtendDao;
import com.data.data.hmly.service.hotel.entity.HotelExtend;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * Created by vacuity on 15/12/31.
 */
@Service
public class HotelExtendService {

    @Resource
    private HotelExtendDao hotelExtendDao;

    public HotelExtend getByHotel(Long hotelId) {
        Criteria<HotelExtend> criteria = new Criteria<HotelExtend>(HotelExtend.class);
        criteria.eq("hotel.id", hotelId);
        return hotelExtendDao.findUniqueByCriteria(criteria);
    }
    @Transactional
    public void save(HotelExtend hotelExtend) {
        hotelExtendDao.save(hotelExtend);
    }

    public void saveExtend(HotelExtend hotelExtend) {
        hotelExtendDao.save(hotelExtend);
    }

    public void updateExtend(HotelExtend hotelExtend) {
        hotelExtendDao.update(hotelExtend);
    }


    public void update(HotelExtend hotelExtend) {
        hotelExtendDao.update(hotelExtend);
    }

    public void delete(HotelExtend hotelExtend) {
        hotelExtendDao.delete(hotelExtend);
    }

    @Transactional
    public void updatTransactional(HotelExtend hotelExtend) {
        hotelExtendDao.update(hotelExtend);
    }
}
