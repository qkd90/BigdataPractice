package com.data.data.hmly.service.build.builder;

import com.data.data.hmly.enums.LabelStatus;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.build.enums.BuilderStatus;
import com.data.data.hmly.service.build.response.MonitorResponse;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.comment.CommentScoreTypeService;
import com.data.data.hmly.service.comment.entity.CommentScoreType;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.request.RecommendPlanSearchRequest;
import com.data.data.hmly.service.plan.vo.RecommendPlanSolrEntity;
import com.data.data.hmly.util.Clock;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @author Jonathan.Guo
 */
@Service
public class HotelBuilder {

    private final Logger logger = Logger.getLogger(HotelBuilder.class);

    private static final String LVXBANG_HOTEL_INDEX_TEMPLATE = "/lvxbang/hotel/index.ftl";
    private static final String LVXBANG_HOTEL_INDEX_TARGET = "/lvxbang/hotel/index.htm";
    private static final String LVXBANG_HOTEL_DETAIL_TEMPLATE = "/lvxbang/hotel/detail.ftl";
    private static final String LVXBANG_HOTEL_DETAIL_TARGET = "/lvxbang/hotel/detail%s.htm";
    private static final String LVXBANG_HOTEL_HEAD_TEMPLATE = "/lvxbang/hotel/head.ftl";
    private static final String LVXBANG_HOTEL_HEAD_TARGET = "/lvxbang/hotel/head%s.htm";

    private static final int HOTEL_NUMBER_SHOW_ON_INDEX = 5;

    private final AtomicInteger buildingCount = new AtomicInteger();
    private final AtomicLong buildingCost = new AtomicLong();
    private static Long currentId;

    private BuilderStatus status = BuilderStatus.IDLE;

    @Resource
    private LabelService labelService;
    @Resource
    private AreaService areaService;
    @Resource
    private HotelService hotelService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private CommentScoreTypeService commentScoreTypeService;
    @Resource
    private AdsBuilder adsBulider;

    public void resetStatus() {
        status = BuilderStatus.IDLE;
    }

    public MonitorResponse monitor() {
        return new MonitorResponse().withId(currentId)
            .withName("scenic")
            .withCount(buildingCount.get())
            .withStatus(status)
            .withTime(buildingCost.get());
    }

    public void buildLxbIndex() {
        Map<Object, Object> data = new HashMap<Object, Object>();
        Label searchLabel = new Label();
        searchLabel.setName("热门酒店");
        final Label label = labelService.findUnique(searchLabel);
        List<TbArea> areaList = areaService.getHomeHotArea();
        List<Map<String, Object>> cityHotelList = Lists.newArrayList();
        for (TbArea tbArea : areaList) {
            Map<String, Object> result = new HashMap<String, Object>();
            List<Hotel> hotelList = findHotelByLabelAndCity(label, tbArea);
            result.put("city", tbArea);
            result.put("hotelList", hotelList);
            cityHotelList.add(result);
        }
        data.put("hotCityHotel", cityHotelList);
        // 广告部分
        List<Ads> adses = adsBulider.getAds(AdsBuilder.LVXBANG_HOTEL_BANNER);
        List<TbArea> destinations = areaService.getHomeHotArea();
//        if (destinations.size() > IndexBuilder.DESTINATION_NUMBER_SHOW_ON_INDEX) {
//            destinations = destinations.subList(0, IndexBuilder.DESTINATION_NUMBER_SHOW_ON_INDEX);
//        }
        data.put("hot", destinations);
        data.put("adses", adses);
        // 浏览历史
        List<Ads> sideAdses = adsBulider.getAds(AdsBuilder.LVXBANG_HOTEL_SIDE_BANNER);
        data.put("sideAdses", sideAdses);

        Label labelDest = new Label();
        labelDest.setName("通用目的地-国内");
        labelDest.setStatus(LabelStatus.USE);
        List<Label> labels = labelService.list(labelDest, null);
        if (!labels.isEmpty()) {
            List<TbArea> sortAreas = areaService.getTrafficAreas(labels.get(0).getId());
            List<Map<String, Object>> sortMap = sortAreasList(sortAreas);
            List<Map<String, List<Object>>> letterSortAreas = letterSortAreasList(sortMap);
            data.put("letterSortAreas", letterSortAreas);
        }
        FreemarkerUtil.create(data, LVXBANG_HOTEL_INDEX_TEMPLATE, LVXBANG_HOTEL_INDEX_TARGET);
    }

    private List<Hotel> findHotelByLabelAndCity(Label label, TbArea tbArea) {
        Page page = new Page(0, HOTEL_NUMBER_SHOW_ON_INDEX);
        List<Hotel> hotels = hotelService.getHotAreaHotel(label, tbArea, page);
        if (hotels.size() == HOTEL_NUMBER_SHOW_ON_INDEX) {
            fillWithRecommendPlan(hotels);
            return hotels;
        }
        List<Long> hotelIdList = Lists.transform(hotels, new Function<Hotel, Long>() {
            @Override
            public Long apply(Hotel hotel) {
                return hotel.getId();
            }
        });
        Hotel hotelCondition = new Hotel();
        hotelCondition.setCityId(tbArea.getId());
        hotelCondition.setPrice(-1f);
        hotelCondition.setStatus(ProductStatus.UP);
        List<Hotel> additionalHotels = hotelService.listWithoutCount(hotelCondition, new Page(0, HOTEL_NUMBER_SHOW_ON_INDEX), "score", "desc");
        for (Hotel additionalHotel : additionalHotels) {
            if (hotels.size() >= HOTEL_NUMBER_SHOW_ON_INDEX) {
                break;
            }
            if (!hotelIdList.contains(additionalHotel.getId())) {
                hotels.add(additionalHotel);
            }
        }
        fillWithRecommendPlan(hotels);
        return hotels;
    }

    private void fillWithRecommendPlan(List<Hotel> hotels) {
        RecommendPlanSearchRequest request = new RecommendPlanSearchRequest();
        for (Hotel hotel : hotels) {
            if (hotel.getPrice() == null || hotel.getPrice() == 0) {
                hotelService.setPrice(hotel);
            }
            request.setHotelId(hotel.getId());
            request.setOrderColumn("viewNum");
            request.setOrderType(SolrQuery.ORDER.desc);
            List<RecommendPlanSolrEntity> planList = recommendPlanService.listFromSolr(request, new Page(0, 1));
            if (planList != null && !planList.isEmpty()) {
                hotel.setRecommendPlanId(planList.get(0).getId());
                hotel.setRecommendPlanName(planList.get(0).getName());
            }
        }
    }

    public void buildLxbDetail() {
        buildLxbDetail(0l, null);
    }

    public void buildLxbDetail(Long startId, Long endId) {

        Clock clock = new Clock();

        currentId = startId - 1;
        int pageSize = 100;
        int processed = 0;
        CommentScoreType commentScoreType = new CommentScoreType();
        commentScoreType.setTargetType(ProductType.hotel);
        List<CommentScoreType> commentScoreTypes = commentScoreTypeService.list(commentScoreType, null);
        while (true) {
            List<Hotel> list = hotelService.listByIdRange(currentId, endId, pageSize);
            processed += list.size();
            if (list.isEmpty()) {
                break;
            }
            for (Hotel hotel : list) {
                currentId = hotel.getId();
                buildLxbDetail(hotel, commentScoreTypes);
                buildingCount.getAndIncrement();
            }
            logger.info("build 100/" + processed + " hotel cost " + clock.elapseTime() + "/" + clock.totalTime() + "ms, speed " + ((float) processed / clock.totalTime() * 1000) + "/s");
            if (list.size() < pageSize) {
                break;
            }
            hotelService.clear();
        }
        logger.info("finish in " + clock.totalTime() + "ms with " + processed);
    }

    public void buildLxbDetail(Long hotelId) {
        Hotel hotel = hotelService.get(hotelId);
        if (hotel == null) {
            logger.error("酒店#" + hotelId + "不存在");
        }
        CommentScoreType commentScoreType = new CommentScoreType();
        commentScoreType.setTargetType(ProductType.hotel);
        List<CommentScoreType> commentScoreTypes = commentScoreTypeService.list(commentScoreType, null);
        buildLxbDetail(hotel, commentScoreTypes);
    }

    private void buildLxbDetail(Hotel hotel, List<CommentScoreType> commentScoreTypes) {
        Map<Object, Object> data = Maps.newHashMap();
        TbArea city = areaService.get(hotel.getCityId());
        data.put("hotel", hotel);
        data.put("city", city);
        data.put("commentScoreTypes", commentScoreTypes);
        FreemarkerUtil.create(data, LVXBANG_HOTEL_DETAIL_TEMPLATE, String.format(LVXBANG_HOTEL_DETAIL_TARGET, hotel.getId()));
        FreemarkerUtil.create(data, LVXBANG_HOTEL_HEAD_TEMPLATE, String.format(LVXBANG_HOTEL_HEAD_TARGET, hotel.getId()));

    }

    public List<Map<String, Object>> sortAreasList(List<TbArea> sortAreas) {

        Map<String, List<TbArea>> map = Maps.newHashMap();
        for (TbArea a : sortAreas) {
            if (a.getPinyin() != null) {
                String first = a.getPinyin().substring(0, 1).toUpperCase();
                List<TbArea> list = map.get(first);
                if (list == null) {
                    list = new ArrayList();
                }
                list.add(a);
                map.put(first, list);

            }
        }
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Map.Entry<String, List<TbArea>> entry : map.entrySet()) {
            Map<String, Object> temp = Maps.newHashMap();
            temp.put("name", entry.getKey());
            temp.put("list", entry.getValue());
            result.add(temp);
        }
        Collections.sort(result, new Comparator<Map<String, Object>>() {
            @Override
            public int compare(Map<String, Object> o1, Map<String, Object> o2) {
                char a = o1.get("name").toString().charAt(0);
                char b = o2.get("name").toString().charAt(0);
                return a - b;
            }
        });
        return result;
    }

    /**
     * 城市字母分类
     * @param sortMap
     * @return
     */
    public List<Map<String, List<Object>>> letterSortAreasList(List<Map<String, Object>> sortMap) {
        List<Map<String, List<Object>>> result = new ArrayList<Map<String, List<Object>>>();
        String[] letterRanges = new String[]{"A-E", "F-J", "K-P", "Q-W", "X-Z"};
        for (int i = 0; i < letterRanges.length; i++) {
            Map<String, List<Object>> rangeMap = Maps.newHashMap();
            rangeMap.put("letterRange", new ArrayList<Object>());
            result.add(rangeMap);
        }
        for (Map<String, Object> map : sortMap) {
            for (int i = 0; i < letterRanges.length; i++) {
                String letterRange = letterRanges[i];
                String[] letters = letterRange.split("-");
                if (letters[0].compareTo((String) map.get("name")) <= 0 && letters[1].compareTo((String) map.get("name")) >= 0) {
                    result.get(i).get("letterRange").add(map);
                }
            }
        }
        return result;
    }
}
