package com.data.data.hmly.service.hotel.aspect;

import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.zuipin.util.PropertiesManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by dy on 2016/7/28.
 */
@Aspect
@Service
public class HotelAspect {

    @Resource
    private HotelService hotelService;

    @Resource
    private PropertiesManager propertiesManager;


    @After("(execution (* com.data.data.hmly.service.hotel.dao.HotelDao.save(..))) or (execution (* com.data.data.hmly.service.hotel.dao.HotelDao.insert(..))) or (execution (* com.data.data.hmly.service.hotel.dao.HotelDao.update(..)))")
    public void hotelFinds(JoinPoint joinPoint) {
        try {
            Boolean updateHotelIndex = propertiesManager.getBoolean("UPDATE_HOTEL_INDEX");
            if (updateHotelIndex != null && !updateHotelIndex) {
                return;
            }
            Object[] objs = joinPoint.getArgs();
            Hotel hotel = (Hotel) objs[0];
            if (hotel != null) {
                hotelService.indexHotel(hotel);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
