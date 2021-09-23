package com.data.data.hmly.service.hotel;

import com.data.data.hmly.service.hotel.dao.HotelCityServiceDao;
import com.data.data.hmly.service.hotel.entity.HotelCityService;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service
public class HotelCityServiceService {

//    Logger logger = Logger.getLogger(RegionService.class);

    @Resource
    private HotelCityServiceDao hotelCityServiceDao;

    public void save(HotelCityService service) {
        hotelCityServiceDao.save(service);
    }

    public List<HotelCityService> listByCity(Integer cityId) {
        Criteria<HotelCityService> criteria = new Criteria<HotelCityService>(HotelCityService.class);
        criteria.eq("cityId", cityId);
        return hotelCityServiceDao.findByCriteria(criteria);
    }

    public List<HotelCityService> listByServiceIdsAndCity(String idsStr, Integer cityId) {
        List<Integer> ids = new ArrayList<Integer>();
        String[] idStrArr = idsStr.split(",");
        for (String id : idStrArr) {
            id = id.trim();
            ids.add(Integer.parseInt(id));
        }
        Criteria<HotelCityService> criteria = new Criteria<HotelCityService>(HotelCityService.class);
        criteria.in("serviceId", ids);
        criteria.eq("cityId", cityId);
        return hotelCityServiceDao.findByCriteria(criteria);
    }

    public String getServiceNames(List<HotelCityService> cityServices) {
        StringBuffer sb = new StringBuffer();
        int i = 0;
        for (HotelCityService cityService : cityServices) {

            if (i < cityServices.size() - 1) {
                sb.append(cityService.getServiceName());
                sb.append(",");
            } else {
                sb.append(cityService.getServiceName());
            }

            i++;
        }
        return sb.toString();
    }
}
