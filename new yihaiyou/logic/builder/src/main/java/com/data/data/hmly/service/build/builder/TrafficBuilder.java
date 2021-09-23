package com.data.data.hmly.service.build.builder;

import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2015/12/24.
 */
@Service
public class TrafficBuilder {
//    private static final int DESTINATION_NUMBER_SHOW_ON_INDEX = 7;

    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private AreaService areaService;
    @Resource
    private AdsBuilder adsBulider;
    @Resource
    private LabelService labelService;
    //    @Resource
//    private ScenicInfoService scenicInfoService;
    private static final String LVXBANG_TRAFFIC_BANNER = "lvxbang_traffic_banner";

    private static final String LVXBANG_TRAFFIC_TEMPLATE = "/lvxbang/traffic/index.ftl";
    private static final String LVXBANG_TRAFFIC_TARGET = "/lvxbang/traffic/index.htm";
    private static final String LVXBANG_TRAFFIC_CITY_TEMPLATE = "/lvxbang/traffic/traffic_city.ftl";
    private static final String LVXBANG_TRAFFIC_CITY_TARGET = "/lvxbang/traffic/traffic_city.htm";

    public void buildLxbIndex() {
        Map<Object, Object> data = Maps.newHashMap();
        List<RecommendPlan> hotRecommendPlans = recommendPlanService.getTrafficHotRecommendPlans();
        List<RecommendPlan> goodRecommendPlans = recommendPlanService.getTrafficRecommendPlans();
        List<TbArea> destinations = areaService.getHomeHotArea();
//        List<ScenicInfo> scenics = scenicInfoService.getHomeRecommendScenic();
//        if (destinations.size() > DESTINATION_NUMBER_SHOW_ON_INDEX) {
//            destinations = destinations.subList(0, DESTINATION_NUMBER_SHOW_ON_INDEX);
//        }

        List<Ads> adses = adsBulider.getAds(LVXBANG_TRAFFIC_BANNER);

        Label label = new Label();
        label.setName("飞机目的地");
        List<Label> labels = labelService.list(label, null);
        if (!labels.isEmpty()) {
            List<TbArea> flightSortAreas = areaService.getTrafficAreas(labels.get(0).getId());
            List<Map<String, Object>> flightSortMap = sortAreasList(flightSortAreas);
            List<Map<String, List<Object>>> flightLetterSortAreas = letterSortAreasList(flightSortMap);
            data.put("flightLetterSortAreas", flightLetterSortAreas);
            data.put("flightAreaMaps", flightSortMap);
        }

        label.setName("火车目的地");
        labels = labelService.list(label, null);
        if (!labels.isEmpty()) {
            List<TbArea> trainSortAreas = areaService.getTrafficAreas(labels.get(0).getId());
            List<Map<String, Object>> trainSortMap = sortAreasList(trainSortAreas);
            List<Map<String, List<Object>>> trainLetterSortAreas = letterSortAreasList(trainSortMap);
            data.put("trainLetterSortAreas", trainLetterSortAreas);
            data.put("trainAreaMaps", trainSortMap);
        }

        data.put("hot", destinations);
        data.put("adses", adses);

        data.put("recommendPlans", hotRecommendPlans);
        data.put("goodRecommendPlans", goodRecommendPlans);
        data.put("destinations", destinations);
        FreemarkerUtil.create(data, LVXBANG_TRAFFIC_TEMPLATE, LVXBANG_TRAFFIC_TARGET);
        FreemarkerUtil.create(data, LVXBANG_TRAFFIC_CITY_TEMPLATE, LVXBANG_TRAFFIC_CITY_TARGET);
    }

    /**
     * 城市字母分类
     *
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
}
