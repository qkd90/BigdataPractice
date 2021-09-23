package com.data.data.hmly.service.hotel;

import com.data.data.hmly.service.hotel.dao.HotelRegionDao;
import com.data.data.hmly.service.hotel.entity.HotelRegion;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class HotelRegionService {

//    Logger logger = Logger.getLogger(RegionService.class);

    @Resource
    private HotelRegionDao hotelRegionDao;

    public void save(HotelRegion region) {
        hotelRegionDao.save(region);
    }

    public String getRegionNameById(Long id) {
        String sql = "select name from hotel_region where id=?";
        List<?> names = hotelRegionDao.findBySQL(sql, id);
        if (names != null && !names.isEmpty() && names.get(0) != null) {
            return names.get(0).toString();
        }
        return null;
    }

    public List<HotelRegion> listByCity(String cityId) {
        Criteria<HotelRegion> criteria = new Criteria<HotelRegion>(HotelRegion.class);
        criteria.eq("cityCode", cityId);
        return hotelRegionDao.findByCriteria(criteria);
    }

    public List<HotelRegion> listByCity(String cityId, Page page, String... orderProperties) {
        Criteria<HotelRegion> criteria = new Criteria<HotelRegion>(HotelRegion.class);
        criteria.eq("cityCode", cityId);
        if (orderProperties != null && orderProperties.length == 2 && orderProperties[0] != null && orderProperties[1] != null) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        }
        if (page != null) {
            return hotelRegionDao.findByCriteria(criteria, page);
        }
        return hotelRegionDao.findByCriteria(criteria);
    }
}
