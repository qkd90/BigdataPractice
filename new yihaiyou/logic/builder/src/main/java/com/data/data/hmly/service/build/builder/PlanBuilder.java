package com.data.data.hmly.service.build.builder;

import com.data.data.hmly.enums.LabelStatus;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Maps;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * @author Jonathan.Guo
 */
@Service
public class PlanBuilder {
    private static final String LVXBANG_PLAN_INDEX_TEMPLATE = "/lvxbang/plan/index.ftl";
    private static final String LVXBANG_PLAN_INDEX_TARGET = "/lvxbang/plan/index.htm";
    private static final String LVXBANG_PLAN_BANNER = "lvxbang_plan_banner";

    private static final int PLAN_NUMBER_SHOW_ON_INDEX = 5;
//    private static final int DESTINATION_NUMBER_SHOW_ON_INDEX = 7;

//    @Resource
//    private RecommendPlanService recommendPlanService;
    @Resource
    private PlanService planService;
    @Resource
    private AreaService areaService;
//    @Resource
//    private TbAreaService tbAreaService;
    @Resource
    private AdsBuilder adsBulider;
    @Resource
    private LabelService labelService;

    public void buildLXBIndex() {
        Map<Object, Object> data = Maps.newHashMap();
        List<Plan> plans = planService.getPlansInPlanModule();
        if (plans.size() < PLAN_NUMBER_SHOW_ON_INDEX) {
            Plan plan = new Plan();
            plan.setStatus(Plan.STATUS_VALID);
            plans.addAll(planService.list(plan, new Page(0, PLAN_NUMBER_SHOW_ON_INDEX - plans.size()), "planStatistic.quoteNum", "desc"));
        }
        List<TbArea> destinations = areaService.getHomeHotArea();
//        if (destinations.size() < DESTINATION_NUMBER_SHOW_ON_INDEX) {
//            List<TbArea> cities = tbAreaService.list(new TbArea(), Order.desc("recommended"));
//            destinations.addAll(cities.subList(0, DESTINATION_NUMBER_SHOW_ON_INDEX - destinations.size()));
//        }
//        List<TbArea> sortAreas =  areaService.getGroupsAreas();
//        List<Map<String, Object>> sortMap = sortAreasList(sortAreas);
//        List<Map<String, List<Object>>> letterSortAreas = letterSortAreasList(sortMap);
        List<Ads> adses = adsBulider.getAds(LVXBANG_PLAN_BANNER);

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

        data.put("abroadAreas", getAbroadArea());
        data.put("adses", adses);
        data.put("hot", destinations);
        data.put("plans", plans);
        data.put("destinations", destinations);
        FreemarkerUtil.create(data, LVXBANG_PLAN_INDEX_TEMPLATE, LVXBANG_PLAN_INDEX_TARGET);
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

    public Map<String, Object> getAbroadArea() {
        Label searchLabel = new Label();
        searchLabel.setName("通用目的地-境外");
        searchLabel.setStatus(LabelStatus.USE);
        Label parent = labelService.findUnique(searchLabel);
        List<Label> firstlList = labelService.getAllChildsLabels(parent);
        Map<String, Object> firstMap = Maps.newLinkedHashMap();
        for (Label first : firstlList) {
            List<TbArea> areaList = areaService.getAreaByLabel(first);
            firstMap.put(first.getAlias(), areaList);
        }
        return firstMap;
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
