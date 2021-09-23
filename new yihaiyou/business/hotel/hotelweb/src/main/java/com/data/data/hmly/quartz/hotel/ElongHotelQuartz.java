package com.data.data.hmly.quartz.hotel;

import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.hotel.HotelElongGuaranteeService;
import com.data.data.hmly.service.hotel.HotelElongService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.HotelToUpdateService;
import com.zuipin.util.PropertiesManager;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Sane on 16/4/19.
 */
@Component
public class ElongHotelQuartz {
    @Resource
    HotelToUpdateService hotelToUpdateService;
    @Resource
    HotelElongService hotelElongService;
    @Resource
    HotelService hotelService;
    @Resource
    HotelElongGuaranteeService hotelElongGuaranteeService;
    @Resource
    PropertiesManager propertiesManager;
    private final Log log = LogFactory.getLog(ElongHotelQuartz.class);

    /**
     * 酒店及酒店价格数据同步，
     * 首先同步酒店及酒店价格数据，然后再循环同步酒店价格（艺龙酒店价格存在“访问太频繁”而取不到价格数据）
     *
     * 检查数据同步情况语句：select count(*)
         from product p
         where p.proType = 'hotel' and p.`status` = 'UP' and p.sourceStatus = 1
         and p.`updateTime` >= '2016-11-26 10:00:00';
     *
     *
     *
     * @throws Exception
     */
    public void syncHotelPrice() throws Exception {
        Date now = new Date();
        // 酒店及酒店价格
        Boolean syncHotelAndHotelPrice = propertiesManager.getBoolean("SYNC_HOTEL_AND_HOTEL_PRICE");
        if (syncHotelAndHotelPrice == null || syncHotelAndHotelPrice) {
            log.error(">> update elong hotel and prices start at:" + DateUtils.format(now, "yyyy-MM-dd HH:mm:ss"));
            hotelElongService.doElongToLxbHotel();
            log.error(">> update elong hotel and prices start at:" + DateUtils.format(now, "yyyy-MM-dd HH:mm:ss")
                    + ", end at:" + DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }

        //  酒店价格后续处理，状态正常且没有价格日期的酒店
        int tryTime = 1;  // 循环次数
        Integer syncHotelPriceTime = propertiesManager.getInteger("SYNC_HOTEL_PRICE_TIME");
        if (syncHotelPriceTime  != null) {
            tryTime = syncHotelPriceTime;
        }
        Boolean syncHotelPrice = propertiesManager.getBoolean("SYNC_HOTEL_PRICE");
//        System.setProperty("http.maxConnections", "8");    // see org.apache.http.impl.client.SystemDefaultHttpClient.createClientConnectionManager()
        if (syncHotelPrice == null || syncHotelPrice) {
            now = new Date();
            log.error(">> update elong hotel prices again start at:" + DateUtils.format(now, "yyyy-MM-dd HH:mm:ss"));
            int time = tryTime;
            while (time > 0) {
                boolean flag = hotelElongService.updateEmptyPrices();
                log.error(">> update elong hotel prices again. Try " + (tryTime - time + 1) + "/" + tryTime + " again end at:" + DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
                time--;
                if (!flag) {
                    log.error(">> 没有需要同步的酒店价格数据");
                    break;
                }
            }
            log.error(">> update elong hotel prices again start at:" + DateUtils.format(now, "yyyy-MM-dd HH:mm:ss")
                    + ", end at:" + DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }

        // 酒店索引：一是都同步完再全部索引，二是第一次同步数据后索引，每次更新房型价格时再单独索引
        Boolean syncAllHotelIndex = propertiesManager.getBoolean("SYNC_ALL_HOTEL_INDEX");
        System.setProperty("http.maxConnections", "20");    // see org.apache.http.impl.client.SystemDefaultHttpClient.createClientConnectionManager()
        if (syncAllHotelIndex == null || syncAllHotelIndex) {
            now = new Date();
            log.error(">> update elong hotel index start at:" + DateUtils.format(now, "yyyy-MM-dd HH:mm:ss"));
            hotelElongService.indexHotel();
            log.error(">> update elong hotel index start at:" + DateUtils.format(now, "yyyy-MM-dd HH:mm:ss")
                    + ", end at:" + DateUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"));
        }

    }
}
