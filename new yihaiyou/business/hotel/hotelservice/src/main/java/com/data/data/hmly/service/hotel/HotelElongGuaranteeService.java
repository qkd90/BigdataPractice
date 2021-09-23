package com.data.data.hmly.service.hotel;

import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.elong.ElongHotelService;
import com.data.data.hmly.service.elong.service.result.HotelDetail;
import com.data.data.hmly.service.elong.service.result.HotelDetail.ResultEntity.HotelsEntity.BookingRulesEntity;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import com.zuipin.util.ConcurrentUtil;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.SpringContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

@Service
public class HotelElongGuaranteeService extends SolrIndexService<Hotel, HotelSolrEntity> {
    @Resource
    private HotelService hotelService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private ElongHotelService elongHotelService;

    private final Log log = LogFactory.getLog(HotelElongGuaranteeService.class);

    @Transactional
    public void doUpdatePrices(List<Long> ids) {
        final Semaphore sem = new Semaphore(20);
        final CountDownLatch down = new CountDownLatch(ids.size());
        for (final Long id : ids) {
            try {
                sem.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            GlobalTheadPool.instance.submit(new Callable<Object>() {

                @Override
                public Object call() throws Exception {
                    return runCall(id, down, sem);
                }
            });
        }
        try {
            down.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }

    private Object runCall(Long id, CountDownLatch down, Semaphore sem) {
        try {

            SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
            doElongToHotel(id);
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            down.countDown();
            sem.release();
        }
        return null;
    }


    @Transactional
    private boolean doElongToHotel(Long id) {
        info("running:" + "\t" + id);
        doElongToHotel2(id);
        return false;
    }


    private Hotel doElongToHotel2(Long id) {
        info("doElongToHotel");
        Hotel hotel = hotelService.get(id);
        info("get prices");
        List<HotelPrice> exists = hotelPriceService.findByHotel(hotel);
        doUpdateGuarantee(String.valueOf(hotel.getSourceId()), hotel.getId(), exists);
        return hotel;
    }

    private void info(String s) {
        log.info(s);
    }

    private void doUpdateGuarantee(String elongId, Long hotelId, List<HotelPrice> exists) {

        List<HotelPrice> newPriceList = new ArrayList<HotelPrice>();
        Map<String, HotelPrice> existIds = new HashMap<>();
        for (HotelPrice p : exists) {
            existIds.put(p.getRatePlanCode() + "_" + p.getRoomId() + "_" + p.getRoomTypeId(), p);
        }
        HotelDetail hotelDetail = elongHotelService.getHotelDetail(Long.parseLong(elongId));
        if (hotelDetail.getResult() == null || hotelDetail.getResult().getHotels() == null || hotelDetail.getResult().getHotels().get(0).getRooms() == null) {
            info("result is null:" + hotelId);
            return;
        }
        Map<String, BookingRulesEntity> bookingRulesMap = new HashMap<String, BookingRulesEntity>();
        for (BookingRulesEntity rule : hotelDetail.getResult().getHotels().get(0).getBookingRules()) {
            bookingRulesMap.put(String.valueOf(rule.getBookingRuleId()), rule);
        }
        for (HotelDetail.ResultEntity.HotelsEntity.RoomsEntity room : hotelDetail.getResult().getHotels().get(0).getRooms()) {
            if (room.getRatePlans() == null)
                continue;
            for (HotelDetail.ResultEntity.HotelsEntity.RoomsEntity.RatePlansEntity ratePlan : room.getRatePlans()) {

                String key = ratePlan.getRatePlanId() + "_" + room.getRoomId();
                if (existIds.containsKey(key)) {
                    HotelPrice p = existIds.get(key);
                    p = getHotelPrice(hotelId, bookingRulesMap, room, ratePlan, p);
                    info("update price:" + p.getId() + "\t" + p.getRoomName() + "\t" + p.getStatus());
                    hotelPriceService.updateWithIndex(p);
                } else {
                    HotelPrice p = getHotelPrice(hotelId, bookingRulesMap, room, ratePlan, null);
                    info("save price:" + p.getId() + "\t" + p.getRoomName() + "\t" + p.getStatus());
                    newPriceList.add(p);
                }
            }
        }
        if (!newPriceList.isEmpty()) {
            hotelPriceService.saveAllWithIndex(newPriceList);
        }
    }

    private HotelPrice getHotelPrice(Long hotelId, Map<String, BookingRulesEntity> bookingRulesMap, HotelDetail.ResultEntity.HotelsEntity.RoomsEntity room, HotelDetail.ResultEntity.HotelsEntity.RoomsEntity.RatePlansEntity ratePlan, HotelPrice p) {
        HotelPrice hotelPrice = null;
        if (p != null) {
            hotelPrice = p;
        } else {
            hotelPrice = new HotelPrice();
        }
        Hotel hotel = new Hotel();
        hotel.setId(hotelId);
        hotelPrice.setHotel(hotel);
        hotelPrice.setRoomId(room.getRoomId());
        hotelPrice.setRoomTypeId(ratePlan.getRoomTypeId());
        hotelPrice.setPrice((float) ratePlan.getTotalRate());
        hotelPrice.setCreateTime(new Date());
        if (ratePlan.getRatePlanName().contains("不含早")) {
            hotelPrice.setBreakfast(false);
        } else {
            hotelPrice.setBreakfast(true);
        }
        hotelPrice.setRatePlanCode(String.valueOf(ratePlan.getRatePlanId()));
        if (ratePlan.getGuaranteeRuleIds().isEmpty()) {
            hotelPrice.setStatus(PriceStatus.UP);
        } else {
            hotelPrice.setStatus(PriceStatus.GUARANTEE);
        }
        hotelPrice.setRoomName(room.getName());
        hotelPrice.setRoomDescription(room.getDescription());
        if (ratePlan.getBookingRuleIds() != null && ratePlan.getBookingRuleIds().length() > 0) {
            String[] ids = ratePlan.getBookingRuleIds().split(",");
            for (String id : ids) {
                BookingRulesEntity bookingRule = bookingRulesMap.get(id);
                if (bookingRule != null)
                    try {
                        hotelPrice.setStart(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(bookingRule.getStartDate()));
                        hotelPrice.setEnd(new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss").parse(bookingRule.getEndDate()));
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
            }
        }
        return hotelPrice;
    }
}
