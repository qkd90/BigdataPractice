package com.data.data.hmly.service.hotel;

import com.data.data.hmly.service.hotel.dao.HotelCityBrandDao;
import com.data.data.hmly.service.hotel.entity.HotelCityBrand;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HotelCityBrandService {

//    Logger logger = Logger.getLogger(RegionService.class);

    @Resource
    private HotelCityBrandDao hotelCityBrandDao;

    public void save(HotelCityBrand brand) {
        hotelCityBrandDao.save(brand);
    }

    public List<HotelCityBrand> listByCity(Integer cityId) {
        Criteria<HotelCityBrand> criteria = new Criteria<HotelCityBrand>(HotelCityBrand.class);
        criteria.eq("cityId", cityId);
        return hotelCityBrandDao.findByCriteria(criteria);
    }
}
