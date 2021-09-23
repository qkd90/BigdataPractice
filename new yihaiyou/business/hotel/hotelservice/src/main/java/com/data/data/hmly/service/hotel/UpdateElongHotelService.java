package com.data.data.hmly.service.hotel;

import com.data.data.hmly.service.elong.ElongHotelService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Map;

//import com.data.data.hmly.service.ctriphotel.CtripHotelStaticInfoService;
//import com.data.data.hmly.service.ctriphotel.PriceInfoService;

/**
 * Created by vacuity on 15/12/29.
 */

@Service
public class UpdateElongHotelService {


    @Resource
    private ElongHotelService elongHotelService;
    //    @Resource
//    private CtripHotelStaticInfoService ctripHotelStaticInfoService;
//    @Resource
//    private PriceInfoService priceInfoService;
    @Resource
    private HotelElongService hotelElongService;
//    @Resource
//    private AreaRelationService areaRelationService;

//    public void update(Long areaId) {
//
//
//        AreaRelation areaRelation = areaRelationService.get(areaId);
//        int cityId = areaRelation.getElongHotelCity();
//
//        // 更新携程数据
////        elongHotelService.
////        ctripHotelInfoService.insertId(cityId);
////        ctripHotelStaticInfoService.insertHotel(cityId);
////        priceInfoService.insertHotelPrice(cityId);
//
//        // 更新本地数据
////        hotelElongService.doElongToLxbHotel(areaRelation);
//    }

    public void update(Date lastTime) {

        Map<String, String> newHotelIds = elongHotelService.getStaticsHotelList(lastTime.getTime(), true);
        // 更新携程数据
//        elongHotelService.
//        ctripHotelInfoService.insertId(cityId);
//        ctripHotelStaticInfoService.insertHotel(cityId);
//        priceInfoService.insertHotelPrice(cityId);

        // 更新本地数据
        hotelElongService.doElongToLxbHotel(newHotelIds);
    }

    public void updateAll() {

    }
}
