package com.data.data.hmly.service;

import com.data.data.hmly.action.yihaiyou.response.CollectResponse;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.cruiseship.CruiseShipDateService;
import com.data.data.hmly.service.cruiseship.CruiseShipRoomDateService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoomDate;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.other.OtherFavoriteService;
import com.data.data.hmly.service.other.entity.OtherFavorite;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;

/**
 * Created by huangpeijie on 2016-04-26,0026.
 */
@Service
public class CollectMobileService {
    @Resource
    private OtherFavoriteService otherFavoriteService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private CruiseShipDateService cruiseShipDateService;
    @Resource
    private CruiseShipRoomDateService cruiseShipRoomDateService;
    @Resource
    private HotelService hotelService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private TicketService ticketService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private RecommendPlanService recommendPlanService;

    public List<CollectResponse> collectList(Long userId, ProductType productType, Page page) {
        List<OtherFavorite> otherFavorites;
        if (productType == null) {
            otherFavorites = otherFavoriteService.findOtherFavoriteBy(userId, page, ProductType.scenic, ProductType.cruiseship, ProductType.hotel, ProductType.sailboat, ProductType.recplan);
        } else {
            otherFavorites = otherFavoriteService.findOtherFavoriteBy(userId, page, productType);
        }
        List<CollectResponse> list = Lists.newArrayList();
        for (OtherFavorite favorite : otherFavorites) {
            CollectResponse response = new CollectResponse(favorite);
            if (favorite.getFavoriteType().equals(ProductType.scenic)) {
                ScenicInfo scenicInfo = scenicInfoService.get(favorite.getFavoriteId());
                response.setCover(scenicInfo.getCover());
                response.setScore(scenicInfo.getScore());
                response.setPrice(scenicInfo.getPrice());
                if (scenicInfo.getScenicStatistics() != null) {
                    response.setSatisfaction(scenicInfo.getScenicStatistics().getSatisfaction());
                }
            } else if (favorite.getFavoriteType().equals(ProductType.sailboat)) {
                Ticket ticket = ticketService.loadTicket(favorite.getFavoriteId());
                Set<TicketPrice> ticketPriceSet = ticket.getTicketPriceSet();
                Float price = Float.MAX_VALUE;
                if (ticketPriceSet != null) {
                    for (TicketPrice ticketPrice : ticketPriceSet) {
                        price = Math.min(price, ticketPrice.getDiscountPrice() == null ? Float.MAX_VALUE : ticketPrice.getDiscountPrice());
                    }
                }
                if (price == Float.MAX_VALUE) {
                    price = 0f;
                }
                response.setPrice(price);
                Productimage productimage = productimageService.findCover(favorite.getFavoriteId(), null, ProductType.scenic);
                if (productimage != null) {
                    response.setCover(productimage.getPath());
                }
            } else if (favorite.getFavoriteType().equals(ProductType.cruiseship)) {
                CruiseShipDate cruiseShipDate = cruiseShipDateService.findById(favorite.getFavoriteId());
                if (cruiseShipDate == null) {
                    continue;
                }
                CruiseShip cruiseShip = cruiseShipDate.getCruiseShip();
                response.setCover(cruiseShip.getCoverImage());
                response.setSatisfaction(cruiseShip.getSatisfaction());
                List<CruiseShipRoomDate> roomDates = cruiseShipRoomDateService.findByDateId(cruiseShipDate.getId(), "salePrice", "asc");
                if (roomDates.isEmpty()) {
                    response.setPrice(0f);
                } else {
                    response.setPrice(roomDates.get(0).getSalePrice());
                }
                response.setStartDate(DateUtils.formatShortDate(cruiseShipDate.getDate()));
            } else if (favorite.getFavoriteType().equals(ProductType.hotel)) {
                Hotel hotel = hotelService.get(favorite.getFavoriteId());
                Productimage productimage = productimageService.findCover(favorite.getFavoriteId(), null, ProductType.hotel);
                if (productimage != null) {
                    response.setCover(productimage.getPath());
                }
                if (hotel.getScore() == null) {
                    response.setScore(0);
                } else {
                    response.setScore(hotel.getScore().intValue());
                }
                HotelPrice searchCondition = new HotelPrice();
                searchCondition.setHotel(hotel);
                searchCondition.setStatus(PriceStatus.UP);
                List<HotelPrice> priceList = hotelPriceService.list(searchCondition, null, new Page(0, Integer.MAX_VALUE), "price", "asc");
                if (!priceList.isEmpty()) {
                    response.setPrice(priceList.get(0).getPrice());
                }
            } else if (favorite.getFavoriteType().equals(ProductType.recplan)) {
                RecommendPlan recommendPlan = recommendPlanService.get(favorite.getFavoriteId());
                response.setCover(recommendPlan.getCoverPath());
            }
            list.add(response);
        }
        return list;
    }
}
