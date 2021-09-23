package com.data.data.hmly.service.build.builder;

import com.data.data.hmly.enums.LabelStatus;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.area.response.DestinationResponse;
import com.data.data.hmly.service.build.enums.BuilderStatus;
import com.data.data.hmly.service.build.response.MonitorResponse;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.restaurant.DelicacyService;
import com.data.data.hmly.service.restaurant.entity.Delicacy;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.util.Clock;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Maps;
import com.zuipin.util.ConcurrentUtil;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.SpringContextHolder;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by zzl on 2015/12/24.
 */
@Service
public class DestinationBuilder {

    private static final String LVXBANG_DESTINATION_INDEX_TEMPLATE = "/lvxbang/destination/index.ftl";
    private static final String LVXBANG_DESTINATION_INDEX_TARGET = "/lvxbang/destination/index.htm";
    private static final String LVXBANG_DESTINATION_DETAIL_TEMPLATE = "/lvxbang/destination/detail.ftl";
    private static final String LVXBANG_DESTINATION_DETAIL_TARGET = "/lvxbang/destination/detail%d.htm";
    private static final String LVXBANG_DESTINATION_HEAD_TEMPLATE = "/lvxbang/destination/head.ftl";
    private static final String LVXBANG_DESTINATION_HEAD_TARGET = "/lvxbang/destination/head%d.htm";

    private static final String LVXBANG_DESTINATION_BANNER = "lvxbang_destination_banner";
//    private static final String LVXBANG_DESTINATION_DETAIL_BANNER = "lvxbang_destination_detail_banner";

//    private static final int DESTINATION_NUMBER_SHOW_ON_INDEX = 6;

    private final AtomicInteger buildingCount = new AtomicInteger();
    private final AtomicLong buildingCost = new AtomicLong();

    private static final Logger LOGGER = Logger.getLogger(DestinationBuilder.class);
    private static final int PAGE_SIZE = 100;
    public static Long currentId;
    private BuilderStatus status;

    @Resource
    private AreaService areaService;
    @Resource
    private ScenicInfoService sInfoService;
    @Resource
    private DelicacyService delicacyService;
    @Resource
    private HotelService hotelService;
    @Resource
    private AdsBuilder adsBulider;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private LabelService labelService;

    public void resetStatus() {
        status = BuilderStatus.IDLE;
    }

    public MonitorResponse monitor() {
        return new MonitorResponse().withId(currentId)
                .withName("destination")
                .withCount(buildingCount.get())
                .withStatus(status)
                .withTime(buildingCost.get());
    }

    public void buildLXBDetail() {
        if (status == BuilderStatus.RUNNING) {
            return;
        }
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return buildLXBDetailTask();
            }
        });
    }

    private Object buildLXBDetailTask() {
        status = BuilderStatus.RUNNING;
        buildingCount.set(0);
        buildingCost.set(0);
        currentId = new Long(0);
        int current;
        Clock clock = new Clock();
        do {
            SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
            List<TbArea> tbAreas = areaService.getInIdRange(currentId, null, PAGE_SIZE);
            for (TbArea tbArea : tbAreas) {
                buildLXBDetail(tbArea);
                currentId = tbArea.getId();
                LOGGER.info("build #" + tbArea.getId() + "finished, cost " + clock.elapseTime());
            }
            current = tbAreas.size();
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
        } while (current == PAGE_SIZE);
        LOGGER.info("build finished, cost " + clock.totalTime() + "ms");
        status = BuilderStatus.IDLE;
        return null;
    }

    public void buildLXBDetail(final Long startId, final Long endId) {
        if (status == BuilderStatus.RUNNING) {
            return;
        }
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                return buildLXBDetailTask(startId, endId);
            }
        });
    }

    private Object buildLXBDetailTask(Long startId, Long endId) {
        status = BuilderStatus.RUNNING;
        buildingCount.set(0);
        buildingCost.set(0);
        currentId = startId - 1;
        int current;
        Clock clock = new Clock();
        do {
            SessionFactory sessionFactory = (SessionFactory) SpringContextHolder.getBean("sessionFactory");
            boolean participate = ConcurrentUtil.bindHibernateSessionToThread(sessionFactory);
            List<TbArea> tbAreas = areaService.getInIdRange(currentId, endId, PAGE_SIZE);
            for (TbArea tbArea : tbAreas) {
                buildLXBDetail(tbArea);
                currentId = tbArea.getId();
                LOGGER.info("build #" + tbArea.getId() + "finished, cost " + clock.elapseTime());
            }
            current = tbAreas.size();
            ConcurrentUtil.closeHibernateSessionFromThread(participate, sessionFactory);
        } while (current == PAGE_SIZE);
        LOGGER.info("build finished, cost " + clock.totalTime() + "ms");
        status = BuilderStatus.IDLE;
        return null;
    }

    public void buildLXBDetail(Long id) {
        TbArea area = areaService.get(id);
        buildLXBDetail(area);
    }

    public void buildLXBDetail(TbArea area) {
        Clock clock = new Clock();
        Map<Object, Object> data = Maps.newHashMap();
        List<TbArea> tbAreas = areaService.getChildAreas(area);
        LOGGER.info("areaService.getChildAreas cost " + clock.elapseTime() + "ms");
//        tbAreas.add(area);
        
        Page page = new Page(1, 5);

        List<ScenicInfo> topScenics = sInfoService.getTopSceByDestination(tbAreas, page);
        LOGGER.info("sInfoService.getTopSceByDestination cost " + clock.elapseTime() + "ms");
        List<Delicacy> topDelicacys = delicacyService.getTopDeliByDestination(tbAreas, page);
        LOGGER.info("delicacyService.getTopDeliByDestination cost " + clock.elapseTime() + "ms");
        List<Hotel> topHotels = hotelService.getTopHotelByDestination(area, page);
        LOGGER.info("hotelService.getTopHotelByDestination cost " + clock.elapseTime() + "ms");
//        List<Ads> adses = adsBulider.getAds(LVXBANG_DESTINATION_DETAIL_BANNER);
        Label searchLabel = new Label();
        searchLabel.setName("目的地热门游记");
        
        List<RecommendPlan> hotRecommendPlan = recommendPlanService.getRecplanByDestinationAndLabel(area, searchLabel);
        LOGGER.info("recommendPlanService.getRecplanByDestinationAndLabel cost " + clock.elapseTime() + "ms");

//        List<RecommendPlan> hotRecommendPlan = recommendPlanService.getDestinationRecplan(area);

//        data.put("adses", adses);
        data.put("area", area);
        System.out.println(area.getName());
        data.put("destination", formatArea(area));
        data.put("hotRecommendPlan", hotRecommendPlan);
        data.put("sceTopList", topScenics);
        data.put("deliTopList", topDelicacys);
        data.put("hotelTopList", topHotels);
        FreemarkerUtil.create(data, LVXBANG_DESTINATION_DETAIL_TEMPLATE, String.format(LVXBANG_DESTINATION_DETAIL_TARGET, area.getId()));
        FreemarkerUtil.create(data, LVXBANG_DESTINATION_HEAD_TEMPLATE, String.format(LVXBANG_DESTINATION_HEAD_TARGET, area.getId()));
        currentId = area.getId();
        buildingCount.getAndIncrement();
        buildingCost.getAndAdd(clock.totalTime());
    }

	public void buildLXBIndex() {
        Map<Object, Object> data = Maps.newHashMap();

        /**
         * 首页查询目的地列表
         */
        List<TbArea> hotAreas = areaService.getHotArea();
        List<TbArea> allAreas = areaService.listAllProvinceArea();
        List<TbArea> seasonHotAreas = areaService.getSeasonHotArea();
        List<Ads> adses = adsBulider.getAds(LVXBANG_DESTINATION_BANNER);
        List<TbArea> destinations = areaService.getHomeHotArea();
//        if (destinations.size() > DESTINATION_NUMBER_SHOW_ON_INDEX) {
//            destinations = destinations.subList(0, DESTINATION_NUMBER_SHOW_ON_INDEX);
//        }
//        List<TbArea> suggestAreas = areaService.getDestinationList();
//        List<Map<String, Object>> sortMap = groupBySpelling(suggestAreas);
//        List<Map<String, List<Object>>> letterSortAreas = letterSortAreasList(sortMap);

        Label label = new Label();
        label.setName("通用目的地-国内");
        label.setStatus(LabelStatus.USE);
        List<Label> labels = labelService.list(label, null);
        if (!labels.isEmpty()) {
            List<TbArea> sortAreas = areaService.getTrafficAreas(labels.get(0).getId());
            List<Map<String, Object>> sortMap = sortAreasList(sortAreas);
            List<Map<String, List<Object>>> letterSortAreas = letterSortAreasList(sortMap);
            data.put("letterSortAreas", letterSortAreas);
        }

//        data.put("areaMaps", sortMap);
        data.put("hot", destinations);
        data.put("adses", adses);
        data.put("hotAreas", formatAreaList(hotAreas));
        data.put("allAreas", allAreas);
        data.put("seasonHotAreas", formatAreaList(seasonHotAreas));
        FreemarkerUtil.create(data, LVXBANG_DESTINATION_INDEX_TEMPLATE, LVXBANG_DESTINATION_INDEX_TARGET);
    }


    public List<Map<String, Object>> groupBySpelling(List<TbArea> sortAreas) {

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


    public List<TbArea> formatAreaList(List<TbArea> areaList) {
        for (TbArea area : areaList) {
            if (area.getId() == 110100L || area.getId() == 120100L || area.getId() == 310100L || area.getId() == 500100L) {
                area.setName(area.getName());
            }
        }
        return areaList;
    }

    public DestinationResponse formatArea(TbArea area) {
        DestinationResponse response = new DestinationResponse();
        if (area.getTbAreaExtend() == null) {
            return response;
        }
        if (area.getTbAreaExtend().getAbs() != null) {
            String intro = area.getTbAreaExtend().getAbs().trim();
            intro = clearHtml(intro);
            response.setAbs(intro);
        }
        if (area.getTbAreaExtend().getBestVisitTime() != null) {
            String bestTime = area.getTbAreaExtend().getBestVisitTime().trim();
            bestTime = clearHtml(bestTime);
            response.setBestVisitTime(bestTime);
        }
        response.setAdviceTime(area.getTbAreaExtend().getAdviceTime());
        String other = "";
        String otherName = "";
        if (StringUtils.isNotBlank(area.getTbAreaExtend().getCulture())) {
            other = area.getTbAreaExtend().getCulture();
            otherName = "文化";
        } else if (StringUtils.isNotBlank(area.getTbAreaExtend().getNation())) {
            other = area.getTbAreaExtend().getNation();
            otherName = "民族";
        } else if (StringUtils.isNotBlank(area.getTbAreaExtend().getReligion())) {
            other = area.getTbAreaExtend().getReligion();
            otherName = "宗教";
        } else if (StringUtils.isNotBlank(area.getTbAreaExtend().getWeather())) {
            other = area.getTbAreaExtend().getWeather();
            otherName = "气候";
        } else if (StringUtils.isNotBlank(area.getTbAreaExtend().getEnvironment())) {
            other = area.getTbAreaExtend().getEnvironment();
            otherName = "环境";
        } else if (StringUtils.isNotBlank(area.getTbAreaExtend().getArt())) {
            other = area.getTbAreaExtend().getArt();
            otherName = "艺术";
        } else if (StringUtils.isNotBlank(area.getTbAreaExtend().getGeography())) {
            other = area.getTbAreaExtend().getGeography();
            otherName = "地理";
        }
        other = clearHtml(other);
        response.setOther(other);
        response.setOtherName(otherName);
        response.setSimpleOther(other);
        return response;
    }


    public String clearHtml(String str) {
        String result = "";
        String regExhtml = "<[^>]+>"; //定义HTML标签的正则表达式
        Pattern phtml = Pattern.compile(regExhtml, Pattern.CASE_INSENSITIVE);
        Matcher mhtml = phtml.matcher(str);
        result = mhtml.replaceAll("").trim(); //过滤html标签 
//        result = result.trim();
        return result;
    }

}
