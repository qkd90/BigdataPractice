package com.data.data.hmly.service;

import com.data.data.hmly.action.yihaiyou.response.CruiseShipCategoryResponse;
import com.data.data.hmly.action.yihaiyou.response.CruiseShipDateResponse;
import com.data.data.hmly.action.yihaiyou.response.CruiseShipDayResponse;
import com.data.data.hmly.action.yihaiyou.response.CruiseShipDeckResponse;
import com.data.data.hmly.action.yihaiyou.response.CruiseShipResponse;
import com.data.data.hmly.action.yihaiyou.response.CruiseShipRoomResponse;
import com.data.data.hmly.action.yihaiyou.response.CruiseShipRoomTypeResponse;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.cruiseship.CruiseShipDateService;
import com.data.data.hmly.service.cruiseship.CruiseShipDeckService;
import com.data.data.hmly.service.cruiseship.CruiseShipPlanService;
import com.data.data.hmly.service.cruiseship.CruiseShipRoomDateService;
import com.data.data.hmly.service.cruiseship.CruiseShipRoomService;
import com.data.data.hmly.service.cruiseship.CruiseShipService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDeck;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipPlan;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoom;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoomDate;
import com.data.data.hmly.service.cruiseship.entity.enums.CruiseShipRoomType;
import com.data.data.hmly.service.cruiseship.request.CruiseShipSearchRequest;
import com.data.data.hmly.service.cruiseship.vo.CruiseShipSolrEntity;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.goods.CategoryService;
import com.data.data.hmly.service.goods.entity.Category;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.DateUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-09-19,0019.
 */
@Service
public class CruiseShipMobileService {
    @Resource
    private CruiseShipService cruiseShipService;
    @Resource
    private CruiseShipPlanService cruiseShipPlanService;
    @Resource
    private CruiseShipRoomService cruiseShipRoomService;
    @Resource
    private CruiseShipRoomDateService cruiseShipRoomDateService;
    @Resource
    private CruiseShipDateService cruiseShipDateService;
    @Resource
    private CruiseShipDeckService cruiseShipDeckService;
    @Resource
    private CategoryService categoryService;
    @Resource
    private ProductimageService productimageService;

    private String brandType = "cruise_ship_brand";
    private String routeType = "cruise_ship_route";

    public CruiseShipResponse detail(Long dateId) {
        CruiseShipDate date = cruiseShipDateService.findById(dateId);
        if (date == null) {
            cruiseShipService.deleteIndexByEntityId(dateId, SolrType.cruise_ship);
            return null;
        }
        CruiseShip ship = date.getCruiseShip();
        CruiseShipResponse response = new CruiseShipResponse(ship);
        List<Productimage> shipImages = productimageService.findImagesByProductId(ship.getId());
        ArrayList<String> imageList = new ArrayList<>();
        for(Productimage image : shipImages){
            imageList.add(image.getPath());
        }
        response.setPathList(imageList);
        StringBuilder brand = new StringBuilder();
        if (ship.getBrand() != null) {
            if (ship.getBrand().getParentId() > 0) {
                Category category = categoryService.findById(ship.getBrand().getParentId());
                brand.append(category.getName()).append("-");
            }
            brand.append(ship.getBrand().getName());
        }
        response.setBrand(brand.toString());
        List<CruiseShipRoomDate> roomDates = cruiseShipRoomDateService.findByDateId(date.getId(), "salePrice", "asc");
        if (!roomDates.isEmpty()) {
            CruiseShipDateResponse dateResponse = new CruiseShipDateResponse(date);
            dateResponse.setMinSalePrice(roomDates.get(0).getSalePrice());
            response.setDate(dateResponse);
        }
        List<CruiseShipPlan> planList = cruiseShipPlanService.listByCruiseShipId(ship.getId());
        List<CruiseShipDayResponse> planDays = Lists.newArrayList();
        StringBuilder route = new StringBuilder();
        for (CruiseShipPlan cruiseShipPlan : planList) {
            planDays.add(new CruiseShipDayResponse(cruiseShipPlan));
            if (route.length() > 0) {
                route.append("-");
            }
            route.append(cruiseShipPlan.getDayDesc());
        }
        response.setRoute(route.toString());
        response.setPlanDays(planDays);
        response.setRoomTypes(roomSimpleList(ship.getId(), date.getId()));
        List<CruiseShipDeck> deckList = cruiseShipDeckService.listByCruiseShipId(ship.getId());
        response.setDecks(Lists.transform(deckList, new Function<CruiseShipDeck, CruiseShipDeckResponse>() {
            @Override
            public CruiseShipDeckResponse apply(CruiseShipDeck input) {
                return new CruiseShipDeckResponse(input);
            }
        }));
        return response;
    }

    public List<CruiseShipRoomTypeResponse> roomSimpleList(Long shipId, Long dateId) {
        List<List<CruiseShipRoomResponse>> responsesList = roomList(shipId, dateId);
        List<CruiseShipRoomTypeResponse> roomTypeResponses = Lists.newArrayList();
        for (List<CruiseShipRoomResponse> responses : responsesList) {
            CruiseShipRoomTypeResponse cruiseShipRoomTypeResponse = new CruiseShipRoomTypeResponse();
            cruiseShipRoomTypeResponse.setPrice(responses.get(0).getPrice());
            cruiseShipRoomTypeResponse.setRoomType(responses.get(0).getRoomType());
            cruiseShipRoomTypeResponse.setRoomTypeDesc(responses.get(0).getRoomTypeDesc());
            cruiseShipRoomTypeResponse.setRoomList(responses);
            roomTypeResponses.add(cruiseShipRoomTypeResponse);
        }
        return roomTypeResponses;
    }

    public List<List<CruiseShipRoomResponse>> roomList(Long shipId, Long dateId) {
        List<List<CruiseShipRoomResponse>> responsesList = Lists.newArrayList();
        List<CruiseShipRoom> roomList = cruiseShipRoomService.listByCruiseShipId(shipId);
        Map<String, List<CruiseShipRoomResponse>> map = Maps.newLinkedHashMap();
        map.put(CruiseShipRoomType.inside.name(), new ArrayList<CruiseShipRoomResponse>());
        map.put(CruiseShipRoomType.seascape.name(), new ArrayList<CruiseShipRoomResponse>());
        map.put(CruiseShipRoomType.balcony.name(), new ArrayList<CruiseShipRoomResponse>());
        map.put(CruiseShipRoomType.suite.name(), new ArrayList<CruiseShipRoomResponse>());
        for (CruiseShipRoom cruiseShipRoom : roomList) {
            List<CruiseShipRoomResponse> responseList = map.get(cruiseShipRoom.getRoomType().name());
            CruiseShipRoomResponse response = new CruiseShipRoomResponse(cruiseShipRoom);
            CruiseShipRoomDate date = cruiseShipRoomDateService.findByRoomAndShipDateId(cruiseShipRoom.getId(), dateId);
            if (date == null) {
                continue;
            }
            response.setPrice(date.getSalePrice());
            response.setRoomDateId(date.getId());
            if ((cruiseShipRoom.getForceFlag() && date.getInventory() < cruiseShipRoom.getPeopleNum()) || date.getInventory() < 1) {
                response.setIsOver(true);
            } else {
                response.setIsOver(false);
            }
            responseList.add(response);
            map.put(cruiseShipRoom.getRoomType().name(), responseList);
        }
        for (List<CruiseShipRoomResponse> responses : map.values()) {
            if (!responses.isEmpty()) {
                Collections.sort(responses, new Comparator<CruiseShipRoomResponse>() {
                    @Override
                    public int compare(CruiseShipRoomResponse o1, CruiseShipRoomResponse o2) {
                        return o1.getPrice().intValue() - o2.getPrice().intValue();
                    }
                });
                responsesList.add(responses);
            }
        }
        return responsesList;
    }

    public CruiseShipResponse simpleDetail(Long shipId) {
        CruiseShip ship = cruiseShipService.findById(shipId);
        CruiseShipResponse response = new CruiseShipResponse(ship);
        StringBuilder route = new StringBuilder();
        List<CruiseShipPlan> planList = cruiseShipPlanService.listByCruiseShipId(ship.getId());
        for (CruiseShipPlan cruiseShipPlan : planList) {
            if (route.length() > 0) {
                route.append("-");
            }
            route.append(cruiseShipPlan.getDayDesc());
        }
        response.setRoute(route.toString());
        return response;
    }

    public CruiseShipDateResponse date(Long dateId) {
        CruiseShipDate date = cruiseShipDateService.findById(dateId);
        return new CruiseShipDateResponse(date);
    }

    public List<CruiseShipDateResponse> dateList(Long shipId) {
        List<CruiseShipDate> dateList = cruiseShipDateService.listByCruiseShipId(shipId, new Date());
        return Lists.transform(dateList, new Function<CruiseShipDate, CruiseShipDateResponse>() {
            @Override
            public CruiseShipDateResponse apply(CruiseShipDate input) {
                return new CruiseShipDateResponse(input);
            }
        });
    }

    public List<CruiseShipResponse> shipList(CruiseShipSearchRequest request, Page page) {
        List<CruiseShipSolrEntity> shipList = cruiseShipService.listFromSolr(request, page);
        return Lists.transform(shipList, new Function<CruiseShipSolrEntity, CruiseShipResponse>() {
            @Override
            public CruiseShipResponse apply(CruiseShipSolrEntity input) {
                return new CruiseShipResponse(input);
            }
        });
    }

    public List<CruiseShip> cruiseShipList(CruiseShip cruiseShip, Page page) {
        List<CruiseShip> shipList = cruiseShipService.list(cruiseShip, page);
        return shipList;
    }

    public List<CruiseShipResponse> indexShip() {
        CruiseShipSearchRequest request = new CruiseShipSearchRequest();
        List<String> dateRangeList = Lists.newArrayList();
        dateRangeList.add(DateUtils.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));
        request.setDateRange(dateRangeList);
        request.setOrderColumn("price");
        request.setOrderType(SolrQuery.ORDER.asc);
        List<CruiseShipSolrEntity> shipList = cruiseShipService.listFromSolr(request, new Page(1, 5));
        return Lists.transform(shipList, new Function<CruiseShipSolrEntity, CruiseShipResponse>() {
            @Override
            public CruiseShipResponse apply(CruiseShipSolrEntity input) {
                return new CruiseShipResponse(input);
            }
        });
    }

    public List<CruiseShipCategoryResponse> shipBrand() {
        List<Category> categoryList = categoryService.listValidByType(brandType, (SysUnit) null);
        return categoryToResponse(categoryList);
    }

    public List<CruiseShipCategoryResponse> shipRoute() {
        List<Category> categoryList = categoryService.listValidByType(routeType, (SysUnit) null);
        return categoryToResponse(categoryList);
    }

    private List<CruiseShipCategoryResponse> categoryToResponse(List<Category> categoryList) {
        Map<Long, List<CruiseShipCategoryResponse>> map = Maps.newHashMap();
        for (Category category : categoryList) {
            CruiseShipCategoryResponse response = new CruiseShipCategoryResponse(category);
            List<CruiseShipCategoryResponse> list = map.get(response.getParentId());
            if (list == null) {
                list = Lists.newArrayList();
            }
            list.add(response);
            map.put(response.getParentId(), list);
        }
        List<CruiseShipCategoryResponse> responses = map.get(0l);
        for (CruiseShipCategoryResponse response : responses) {
            response.setChildren(map.get(response.getId()));
        }
        return responses;
    }

    //查询邮轮列表
    public List<CruiseShipResponse> dateList(Page page, String sortName, String sortOrder, Long brandId, Long routeId, Date startTime) {
        List<CruiseShipDate> shipDateList = cruiseShipDateService.dateList(page, sortName, sortOrder, brandId, routeId, startTime);
        List<CruiseShipResponse> responseList = Lists.newArrayList();
        for (CruiseShipDate date : shipDateList) {
            CruiseShip ship = date.getCruiseShip();
            CruiseShipResponse response = new CruiseShipResponse(ship);
            response = new CruiseShipResponse(ship);
            CruiseShipDateResponse dateResponse = new CruiseShipDateResponse(date);
            dateResponse.setMinSalePrice(date.getMinSalePrice());
            response.setDate(dateResponse);
            responseList.add(response);
        }


        return responseList;
    }
}