package com.data.data.hmly.service.hotel;

import com.data.data.hmly.service.hotel.dao.HotelBrandDao;
import com.data.data.hmly.service.hotel.entity.HotelBrand;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HotelBrandService {

//    Logger logger = Logger.getLogger(RegionService.class);

    @Resource
    private HotelBrandDao hotelBrandDao;

    public void save(HotelBrand region) {
        hotelBrandDao.save(region);
    }

    public List<HotelBrand> listByCity(String cityId) {
        Criteria<HotelBrand> criteria = new Criteria<HotelBrand>(HotelBrand.class);
        criteria.eq("cityCode", cityId);
        return hotelBrandDao.findByCriteria(criteria);
    }
}
