package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.lvxbang.request.PlanOptimizeRequest;
import com.data.data.hmly.service.lvxbang.request.TripNode;
import com.data.data.hmly.service.lvxbang.response.MiniCityResponse;
import com.data.data.hmly.service.lvxbang.response.PlanOptimizeDayResponse;
import com.data.data.hmly.service.lvxbang.response.PlanOptimizeResponse;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;
import com.data.data.hmly.util.Clock;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.HttpUtils;
import com.zuipin.util.PropertiesManager;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Jonathan.Guo
 */
@Service
public class PlanOptimizeService {

    private final Logger logger = Logger.getLogger(PlanOptimizeService.class);

    public static final int TYPE_DAY_NO_CHANGE = 2;
    public static final int TYPE_SCENIC_NO_CHANGE = 1;

    public static String optimizeServer = null;
    public static final String DEFAULT_OPTIMIZE_SERVER = "http://172.16.199.7:8083/smartplan/smartPlan/plan.jhtml";

    public static final long START_CHUJING_CITY_INDEX = 1000000;

    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private PropertiesManager propertiesManager;

    public PlanOptimizeResponse optimize(PlanOptimizeRequest request) {
        //
        //把需要的参数封装为一个JSONObject对象，然后传给smartPlan
        JSONObject json = new JSONObject();
        json.put("days", request.day);
        json.put("hour", request.hour);
        json.put("type", request.type);
        //封装planTrip的type和景点等的id
        JSONArray tripList = new JSONArray();
        for (int i = 0; i < request.scenicList.size(); i++) {
            JSONObject jo = new JSONObject();
            jo.put("type", request.scenicList.get(i).type);
            jo.put("id", request.scenicList.get(i).id);
            tripList.add(jo);
        }
        json.put("cityDays", request.cityDays);
        json.put("list", tripList);
        Clock clock = new Clock();
        if (optimizeServer == null) {
            optimizeServer = propertiesManager.getString("optimize.server.url");
        }
        if (optimizeServer == null) {
            optimizeServer = DEFAULT_OPTIMIZE_SERVER;
        }
//        optimizeServer = "http://localhost:28080/smartplan/smartPlan/plan.jhtml";
        logger.info(String.format("Post Json %s", json.toString()));
        String resultString = HttpUtils.post(optimizeServer, json.toString());
        logger.info("optimize cost " + clock.elapseTime() + "ms." + resultString);
        return createResponse(request.scenicList, resultString);
    }

    @SuppressWarnings("unchecked")
    public PlanOptimizeResponse createResponse(List<TripNode> originNodes, String json) {
        Map<Long, TripNode> presentNodes = Maps.newHashMap(Maps.uniqueIndex(originNodes, new Function<TripNode, Long>() {
            @Override
            public Long apply(TripNode tripNode) {
                return tripNode.id;
            }
        }));
        PlanOptimizeResponse response = new PlanOptimizeResponse();
//        解析得到的行程优化结果
        try {
            if (json != null && json.length() > 2) {
                List<Map<String, List<Map<String, Object>>>> cityArray = new ObjectMapper().readValue(json, List.class);
                for (Map<String, List<Map<String, Object>>> cityJson : cityArray) {
                    parseCItyData(presentNodes, response, cityJson);
                }
                response.setSuccess(true);
            } else {
                response.setSuccess(false);
            }
        } catch (IOException e) {
            logger.error("解析优化结果失败，优化结果为" + json, e);
            response.setSuccess(false);
        }
        response.removedNode = Lists.newArrayList(presentNodes.values());
        return response;
    }

    private void parseCItyData(Map<Long, TripNode> presentNodes, PlanOptimizeResponse response, Map<String, List<Map<String, Object>>> cityJson) {
        for (Map.Entry<String, List<Map<String, Object>>> cityEntry : cityJson.entrySet()) {
            String cityPrefix = cityEntry.getKey();
            Long cityId = Long.parseLong(cityPrefix.trim());
            if (cityId < START_CHUJING_CITY_INDEX) {
                cityId = Long.valueOf(cityPrefix + "00");
            }
            for (Map<String, Object> dayMap : cityEntry.getValue()) {
                parsePlanDay(presentNodes, response, cityId, dayMap);
            }
        }
    }

    private void parsePlanDay(Map<Long, TripNode> nodes, PlanOptimizeResponse response, Long cityId, Map<String, Object> dayMap) {
        for (Map.Entry<String, Object> dayEntry : dayMap.entrySet()) {
            PlanOptimizeDayResponse dayResponse = new PlanOptimizeDayResponse();
            dayResponse.city = new MiniCityResponse();
            dayResponse.day = Integer.parseInt(dayEntry.getKey());
            dayResponse.city.id = cityId;
            dayResponse.tripList = new ArrayList<TripNode>();
            List<Map<String, Object>> tripList = (List) (dayEntry.getValue());
            tripList = collectTrip(tripList);
            for (Map<String, Object> map : tripList) {
                Long scenicId = Long.valueOf(map.get("scenicInfoId").toString());
                TripNode tripNode = new TripNode();
                tripNode.id = scenicId;
                tripNode.type = Integer.parseInt(map.get("tripType").toString());
                dayResponse.tripList.add(tripNode);
                if (nodes.containsKey(scenicId)) {
                    nodes.remove(scenicId);
                } else {
                    response.addNodes.add(tripNode);
                }
            }
            response.data.add(dayResponse);
        }
    }

    private List<Map<String, Object>> collectTrip(List<Map<String, Object>> tripList) {
        List<Map<String, Object>> result = Lists.newArrayList();
        for (Map<String, Object> map : tripList) {
            result.add(map);
            List<Map<String, Object>> subList = (List<Map<String, Object>>) map.get("childPoints");
            if (subList != null && !subList.isEmpty()) {
                result.addAll(collectTrip(subList));
            }
        }
        return result;
    }

    public void fillInfo(PlanOptimizeResponse response) {
        fillScenicInfo(response);
        fillCityInfo(response);
    }

    private void fillCityInfo(PlanOptimizeResponse response) {
        List<Long> cityIds = Lists.newArrayList(Lists.transform(response.data, new Function<PlanOptimizeDayResponse, Long>() {
            @Override
            public Long apply(PlanOptimizeDayResponse planOptimizeDayResponse) {
                return planOptimizeDayResponse.city.id;
            }
        }));
        if (cityIds.isEmpty()) {
            return;
        }
        List<TbArea> tbAreaList = tbAreaService.getByIds(cityIds);
        Map<Long, TbArea> areaMap = Maps.uniqueIndex(tbAreaList, new Function<TbArea, Long>() {
            @Override
            public Long apply(TbArea tbArea) {
                return tbArea.getId();
            }
        });
        for (PlanOptimizeDayResponse dayResponse : response.data) {
            dayResponse.city.name = areaMap.get(dayResponse.city.id).getName();
        }
    }

    private void fillScenicInfo(PlanOptimizeResponse response) {
        List<TripNode> allNodes = Lists.newArrayList();
        allNodes.addAll(response.removedNode);
        allNodes.addAll(response.addNodes);

        for (PlanOptimizeDayResponse dayResponse : response.data) {
            allNodes.addAll(dayResponse.tripList);
        }
        List<Long> scenicIdList = Lists.newArrayList(Lists.transform(allNodes, new Function<TripNode, Long>() {
            @Override
            public Long apply(TripNode tripNode) {
                return tripNode.id;
            }
        }));
        if (scenicIdList.isEmpty()) {
            return;
        }
//        long start = System.currentTimeMillis();
        List<ScenicSolrEntity> scenicSolrs = scenicInfoService.listScenicFromSolr(scenicIdList);
//        List<ScenicInfo> scenicInfoList = new ArrayList<>();

//        System.out.println(System.currentTimeMillis() - start);
//        List<ScenicInfo> scenicInfoList = scenicInfoService.getScenicByIds(scenicIdList);
//        System.out.println(System.currentTimeMillis() - start);
        Map<Long, ScenicSolrEntity> scenicInfoMap = Maps.uniqueIndex(scenicSolrs, new Function<ScenicSolrEntity, Long>() {
            @Override
            public Long apply(ScenicSolrEntity scenicInfo) {
                return scenicInfo.getId();
            }
        });
        for (TripNode tripNode : allNodes) {
            ScenicSolrEntity scenicInfo = scenicInfoMap.get(tripNode.id);
            if (scenicInfo == null) {
                continue;
            }
            tripNode.name = scenicInfo.getName();
            tripNode.cover = tripNode.cover(scenicInfo.getCover());
            tripNode.score = scenicInfo.getScore();
            tripNode.price = String.valueOf(scenicInfo.getPrice());
            tripNode.ranking = scenicInfo.getRanking();
            tripNode.advice = scenicInfo.getAdviceTime();
//            tripNode.star = scenicInfo.getStar();
            tripNode.lng = scenicInfo.getLongitude();
            tripNode.lat = scenicInfo.getLatitude();
            tripNode.address = scenicInfo.getAddress();
            tripNode.shortIntro = scenicInfo.getShortComment();
            tripNode.cityId = scenicInfo.getCityId().intValue();
        }
    }

    public PlanOptimizeResponse testOptimize(PlanOptimizeRequest request) {
        PlanOptimizeResponse response = new PlanOptimizeResponse();
        response.removedNode = Lists.newArrayList();
        response.removedNode.addAll(request.scenicList.subList(0, 2));
        response.data = Lists.newArrayList();
        List<TripNode> temp = Lists.newArrayList();
        int day = 1;
        for (TripNode tripNode : request.scenicList) {
            temp.add(tripNode);
            if (temp.size() < 3) {
                continue;
            }
            PlanOptimizeDayResponse dayResponse = new PlanOptimizeDayResponse();
            dayResponse.city = new MiniCityResponse();
            dayResponse.city.id = 350200l;
            dayResponse.day = day++;
            dayResponse.tripList = Lists.newArrayList(temp);
            response.data.add(dayResponse);
            temp.clear();
        }
        PlanOptimizeDayResponse dayResponse = new PlanOptimizeDayResponse();
        dayResponse.city = new MiniCityResponse();
        dayResponse.city.id = 350200l;
        dayResponse.day = day;
        dayResponse.tripList = Lists.newArrayList(temp);
        response.data.add(dayResponse);
        fillInfo(response);
        return response;
    }
}

