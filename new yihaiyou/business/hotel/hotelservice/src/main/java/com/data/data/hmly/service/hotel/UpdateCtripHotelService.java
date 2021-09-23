package com.data.data.hmly.service.hotel;


import com.data.data.hmly.service.common.AreaRelationService;
import com.data.data.hmly.service.common.entity.AreaRelation;
import com.data.data.hmly.service.ctriphotel.CtripHotelInfoService;
import com.data.data.hmly.service.ctriphotel.CtripHotelStaticInfoService;
import com.data.data.hmly.service.ctriphotel.PriceInfoService;
import com.zuipin.util.GlobalTheadPool;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.Callable;

/**
 * Created by vacuity on 15/12/29.
 */

@Service
public class UpdateCtripHotelService {


    @Resource
    private CtripHotelInfoService ctripHotelInfoService;
    @Resource
    private CtripHotelStaticInfoService ctripHotelStaticInfoService;
    @Resource
    private PriceInfoService priceInfoService;
    @Resource
    private HotelCtripService hotelCtripService;
    @Resource
    private AreaRelationService areaRelationService;


    public void ctripUpdate() {
        List<AreaRelation> areaRelationList = areaRelationService.getCtrpCity();
        for (final AreaRelation areaRelation : areaRelationList) {
            if (areaRelation.getCtripHotelCity() == null || areaRelation.getCtripHotelCity().intValue() == 0) {
                continue;
            }
            GlobalTheadPool.instance.submit(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    try {
                        System.out.println("正在更新" + areaRelation.getName() + "携程酒店");
                        Long count = update(areaRelation.getId());
                        System.out.println(areaRelation.getName() + "酒店更新成功,一共" + count + "个");
                    } catch (Exception e) {
                        e.printStackTrace();
                    } finally {
                        System.out.printf("count:");
                    }
                    return null;
                }
            });

        }
    }

    public Long update(Long areaId) {


        AreaRelation areaRelation = areaRelationService.get(areaId);
        Long cityId = areaRelation.getCtripHotelCity();

        // 更新携程数据
        ctripHotelInfoService.insertId(cityId);
        ctripHotelStaticInfoService.insertHotel(cityId);
        priceInfoService.insertHotelPrice(cityId);

        // 更新本地数据
        return hotelCtripService.doCtripToLxbHotel(areaRelation);
    }
}
