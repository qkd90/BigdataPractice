package com.data.data.hmly.service;

import com.data.data.hmly.action.mobile.request.PlanRecommendRequest;
import com.data.data.hmly.action.mobile.request.PlanRecommendSimpleRequest;
import com.data.data.hmly.action.mobile.request.PlanRecommendTripRequest;
import com.data.data.hmly.action.mobile.response.HotelPriceResponse;
import com.data.data.hmly.action.mobile.response.HotelResponse;
import com.data.data.hmly.action.mobile.response.HotelSimpleResponse;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.hotel.HotelCityBrandService;
import com.data.data.hmly.service.hotel.HotelCityServiceService;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.HotelRegionService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelCityBrand;
import com.data.data.hmly.service.hotel.entity.HotelCityService;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelRegion;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.hotel.request.HotelSearchRequest;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import com.data.data.hmly.service.lvxbang.AdvertisingService;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.zuipin.util.ConcurrentUtil;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.SpringContextHolder;
import org.apache.commons.lang3.time.StopWatch;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

/**
 * Created by huangpeijie on 2016-04-26,0026.
 */
@Service
public class HotelMobileService {
    @Resource
    private HotelService hotelService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private ScenicInfoService scenicInfoService;
//    @Resource
//    private HotelRoomIndexService hotelRoomIndexService;

    @Resource
    private AdvertisingService advertisingService;
    @Resource
    private HotelRegionService hotelRegionService;
    @Resource
    private HotelCityBrandService hotelCityBrandService;
    @Resource
    private HotelCityServiceService hotelCityServiceService;

    private final Log log = LogFactory.getLog(HotelMobileService.class);

    public Map<String, Object> getHteiData(Long cityId) {
        Map<String, Object> result = new HashMap<String, Object>();
        List<Ads> topBannerAds = advertisingService.getMobileHteiBanner();
        List<HotelRegion> hotelRegions = hotelRegionService.listByCity(Long.toString(cityId));
        List<HotelCityBrand> hotelCityBrands = hotelCityBrandService.listByCity(cityId.intValue());
        List<HotelCityService> hotelCityServices = hotelCityServiceService.listByCity(cityId.intValue());
        result.put("topBannerAds", topBannerAds);
        result.put("hotelRegions", hotelRegions);
        result.put("hotelCityBrands", hotelCityBrands);
        result.put("hotelCityServices", hotelCityServices);
        return result;
    }

    public List<HotelSimpleResponse> listHotel(HotelSearchRequest hotelSearchRequest, Page page) throws ParseException {
        List<HotelSolrEntity> entities = hotelService.listFromSolr(hotelSearchRequest, page);
        List<HotelSimpleResponse> list = Lists.newArrayList();
        Date start = DateUtils.parseShortTime(hotelSearchRequest.getStartDate());
        Date end = DateUtils.parseShortTime(hotelSearchRequest.getEndDate());
        for (HotelSolrEntity entity : entities) {
            HotelSimpleResponse response = new HotelSimpleResponse(entity);
            Hotel hotel = new Hotel();
            hotel.setId(entity.getId());
            HotelPrice search = new HotelPrice();
            search.setHotel(hotel);
            search.setStart(start);
            search.setEnd(end);
            List<HotelPrice> prices = hotelPriceService.list(search, new Page(1, 1), "price", "asc");
            if (prices.isEmpty()) {
                System.out.print("Hotel has no prices" + entity.getId());
                continue;
            }
            HotelPrice price = prices.get(0);
            response.setPriceId(price.getId());
            response.setPrice(price.getPrice());
            response.setPriceName(price.getName());
            if (PriceStatus.UP.equals(price.getStatus())) {
                response.setPayType("到付");
            } else {
                response.setPayType("担保");
            }
            list.add(response);
        }
        return list;
    }

    public List<HotelSimpleResponse> recommendHotel(PlanRecommendRequest request) throws ParseException {
        List<HotelSimpleResponse> responseList = Lists.newArrayList();
        List<PlanRecommendSimpleRequest> plan = request.getPlan();
        CountDownLatch down = new CountDownLatch(plan.size());

        Date startDate = DateUtils.parseShortTime(request.getStartDate());
        for (PlanRecommendSimpleRequest simpleRequest : plan) {
            HotelSimpleResponse response = new HotelSimpleResponse();
            response.setCityId(simpleRequest.getCityId());
            response.setStartDate(DateUtils.formatShortDate(startDate));
            ScenicInfo coreScenic = getCoreScenic(simpleRequest);
            responseList.add(response);
            Integer day = simpleRequest.getDay();
            startDate = DateUtils.add(startDate, Calendar.DATE, day + 1);
            response.setEndDate(DateUtils.formatShortDate(startDate));
            findHotel(response, coreScenic.getScenicGeoinfo().getBaiduLng(), coreScenic.getScenicGeoinfo().getBaiduLat(), down);
        }
        try {
            down.await();
        } catch (InterruptedException e) {

        }
        return responseList;
    }

    public ScenicInfo getCoreScenic(PlanRecommendSimpleRequest request) {
        List<PlanRecommendTripRequest> tripList = request.getTripList();
        Collections.sort(tripList, new Comparator<PlanRecommendTripRequest>() {
            @Override
            public int compare(PlanRecommendTripRequest o1, PlanRecommendTripRequest o2) {
                return o1.getRanking() - o2.getRanking();
            }
        });
        return scenicInfoService.get(tripList.get(0).getId());
    }

    public void findHotel(final HotelSimpleResponse response, final Double longitude, final Double latitude, final CountDownLatch down) {

        GlobalTheadPool.instance.submit(new Callable<Object>() {

            @Override
            public Object call() throws Exception {
                return fillHotelTask(response, longitude, latitude, down);
            }
        });
    }

    private Object fillHotelTask(HotelSimpleResponse response, Double longitude, Double latitude, CountDownLatch down) {
        SessionFactory sessionFactory = SpringContextHolder.getBean("sessionFactory");
        boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
        try {
            StopWatch watch = new StopWatch();
            watch.start();
            StringBuilder builder = new StringBuilder();
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(DateUtils.parseShortTime(response.getStartDate()));
            builder.append("cityId:").append(response.getCityId()).append(" AND start:[* TO ").append(response.getStartDate()).append("T00:00:00Z] AND end:[").append(response.getEndDate()).append("T23:59:59Z TO *]");
            List<HotelSolrEntity> hotelSolrEntities = hotelService.findNearByHotel(builder.toString(), longitude, latitude, 50f, null);
            if (hotelSolrEntities.isEmpty()) {
                return null;
            }
            for (int i = 0; i < hotelSolrEntities.size(); i++) {
                fillHotel(hotelSolrEntities.get(i).getId(), DateUtils.parseShortTime(response.getStartDate()), DateUtils.parseShortTime(response.getEndDate()), response);
                if (response.getId() != null && response.getId() > 0) {
                    break;
                }
            }
            watch.stop();
            return null;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        } finally {
            down.countDown();
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
        }
        return null;
    }

    private void fillHotel(Long hotelId, Date enterDate, Date leaveDate, HotelSimpleResponse response) {
        HotelPrice priceFilter = new HotelPrice();
        Hotel hotelFilter = new Hotel();
        hotelFilter.setId(hotelId);
        priceFilter.setHotel(hotelFilter);
        priceFilter.setStart(enterDate);
        priceFilter.setEnd(leaveDate);
        List<HotelPrice> hotelPrice = hotelPriceService.list(priceFilter, new Page(1, 1), "price", "asc");
        if (hotelPrice.isEmpty()) {
            return;
        }
        response.completeWithHotelPrice(hotelPrice.get(0));
    }

    public HotelResponse hotelDeatil(Hotel hotel, String startDate, String endDate) throws ParseException {
        HotelPrice search = new HotelPrice();
        search.setHotel(hotel);
        search.setStart(DateUtils.parseShortTime(startDate));
        search.setEnd(DateUtils.parseShortTime(endDate));
        List<HotelPrice> hotelPriceList = hotelPriceService.list(search, null, "price", "asc");
        List<HotelPriceResponse> priceResponseList = Lists.transform(hotelPriceList, new Function<HotelPrice, HotelPriceResponse>() {
            @Override
            public HotelPriceResponse apply(HotelPrice input) {
                return new HotelPriceResponse(input);
            }
        });
        HotelResponse response = new HotelResponse(hotel);
        response.setPrices(priceResponseList);
        return response;
    }
}
