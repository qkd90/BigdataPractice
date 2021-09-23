package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.enums.LabelStatus;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.area.request.TbAreaSearchRequest;
import com.data.data.hmly.service.area.vo.TbAreaSolrEntity;
import com.data.data.hmly.service.common.vo.SuggestionEntity;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.lvxbang.AdvertisingService;
import com.data.data.hmly.service.lvxbang.SuggestService;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.FileUtil;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2015/12/22.
 */
public class DestinationWebAction extends JxmallAction {

    @Resource
    private AreaService areaService;
    @Resource
    private AdvertisingService advertisingService;
    @Resource
    private SuggestService suggestService;
    @Resource
    private LabelService labelService;
    private TbAreaSearchRequest tbAreaSearchRequest = new TbAreaSearchRequest();
    private TbArea tbArea;
    private int pageIndex = 0;
    private Long id;
    private String suggest;
    public Long cityCode;
    public Long provinceId;


    public Result index() {
//        setAttribute(LXBConstants.LVXBANG_DESTINATION_BANNER_KEY, LXBConstants.LVXBANG_DESTINATION_BANNER);
        setAttribute(LXBConstants.LVXBANG_DESTINATION_INDEX_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_DESTINATION_INDEX));
        return dispatch();
    }

    public Result detail() {
//        setAttribute(LXBConstants.LVXBANG_DESTINATION_DETAIL_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_DESTINATION_DETAIL+cityCode));
        switch (cityCode.toString()) {
            case "310100":
                return redirect("/province_310000.html");
            case "110100":
                return redirect("/province_110000.html");
            case "120100":
                return redirect("/province_120000.html");
            case "500100":
                return redirect("/province_500000.html");
            default:
                setAttribute(LXBConstants.LVXBANG_DESTINATION_DETAIL_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_DESTINATION_DETAIL + cityCode));
                setAttribute(LXBConstants.LVXBANG_DESTINATION_HEAD_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_DESTINATION_HEAD + cityCode));
                return dispatch();
        }
    }

    public Result areaList() {
        return dispatch();
    }

    /**
     * 展示所有目的地
     *
     * @return
     */
    public Result getAreaIndex() {
//    	tbArea = areaService.get(350200L);
        areaService.indexArea();
        return text("getAreaIndex");
    }


    /**
     * 展示所有目的地
     *
     * @return
     */
    public Result getAllArea() {
        return json(JSONObject.fromObject(areaService.listAllArea()));
    }

    /**
     * 目的地搜索列表
     *
     * @return
     */
    public Result getAreaList() {
        Page page = new Page(pageIndex + 1, 10);
        List<TbAreaSolrEntity> tbAreaSolrEntities = areaService.listFromSolr(tbAreaSearchRequest, page);
        return json(JSONObject.fromObject(tbAreaSolrEntities));
    }

    /**
     * 首页目的地搜索框，搜索目的地,只查询二级城市
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public Result getSeachAreaList() throws UnsupportedEncodingException {
        ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
        int page = 10;
        String name = (String) getParameter("name");
        Integer level = 2;
        return json(JSONArray.fromObject(suggestService.suggestDestination(name, level, page)));
    }

    /**
     * 首页目的地搜索框，搜索目的地
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public Result getDestSeachAreaList() throws UnsupportedEncodingException {
        ServletActionContext.getResponse().setHeader("Access-Control-Allow-Origin", "*");
        int page = 10;
        String name = (String) getParameter("name");
        return json(JSONArray.fromObject(suggestService.suggestDestinationAndScenic(name, null, page)));
    }

    /**
     * 首页机票搜索框，搜索目的地
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public Result getTrainAreaList() throws UnsupportedEncodingException {

        Page page = new Page(pageIndex + 1, 10);
        String name = (String) getParameter("name");
        List<Long> labelIds = new ArrayList<Long>();
        labelIds.add(53L);
        List<TbAreaSolrEntity> tbAreaSolrEntities = new ArrayList<TbAreaSolrEntity>();
        if (StringUtils.isNotBlank(name)) {
            tbAreaSearchRequest.setName(name);
            tbAreaSearchRequest.setLabelIds(labelIds);
            tbAreaSolrEntities = areaService.listFromSolr(tbAreaSearchRequest, page);
        }
        return json(JSONArray.fromObject(tbAreaSolrEntities));
    }


    /**
     * 首页机票搜索框，搜索目的地
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public Result getFlightAreaList() throws UnsupportedEncodingException {

        Page page = new Page(pageIndex + 1, 10);
        String name = (String) getParameter("name");
        List<Long> labelIds = new ArrayList<Long>();
        labelIds.add(52L);
        List<TbAreaSolrEntity> tbAreaSolrEntities = new ArrayList<TbAreaSolrEntity>();
        if (StringUtils.isNotBlank(name)) {
            tbAreaSearchRequest.setName(name);
            tbAreaSearchRequest.setLabelIds(labelIds);
            tbAreaSolrEntities = areaService.listFromSolr(tbAreaSearchRequest, page);
        }
        return json(JSONArray.fromObject(tbAreaSolrEntities));
    }


    /**
     * 获取单独的城市Id
     *
     * @return
     */
    public Result getAreaInfo() {

        Map<String, Object> map = new HashMap<String, Object>();
        String name = (String) getParameter("name");
        List<SuggestionEntity> areaNames = null;
        String id = "";
        if (StringUtils.isNotBlank(name)) {
            areaNames = suggestService.suggestDestination(name, null, 1);
            if (!areaNames.isEmpty()) {
                id = areaNames.get(0).getId();
                map.put("id", id);
                simpleResult(map, true, "");
            } else {
                simpleResult(map, false, "");
            }
        } else {
            simpleResult(map, false, "");
        }
        return jsonResult(map);
    }

    /**
     * 通过前端获取的多个城市名，获取Id列表
     *
     * @return
     */
    public Result getAreaIds() {

        String nameStr = (String) getParameter("nameStr");
        String typeStr = (String) getParameter("type");

        List<String> areaNames = Arrays.asList(nameStr.split(","));
        List<TbArea> areas = areaService.getAreaListByName(areaNames);
        List<Long> idList = Lists.newArrayList();
        List<String> nameList = Lists.newArrayList();
        if (StringUtils.isNotBlank(typeStr) && Integer.valueOf(typeStr) == 1) {
            for (TbArea area : areas) {
                idList.add(area.getId());
                nameList.add(area.getName());
            }
        } else {
            for (TbArea area : areas) {
                switch (area.getId().toString()) {
                    case "310000":
                        idList.add(310100L);
                        break;
                    case "110000":
                        idList.add(110100L);
                        break;
                    case "120000":
                        idList.add(120100L);
                        break;
                    case "500000":
                        idList.add(500100L);
                        break;
                    default:
                        idList.add(area.getId());
                }
                nameList.add(area.getName());
            }
        }
        List<Object> result = Lists.newArrayList();
        result.add(idList);
        result.add(nameList);
        return json(JSONArray.fromObject(result));
    }

    /**
     * 通过前端获取的多个城市名，获取Id列表
     *
     * @return
     */
    public Result getAreaIdsWithAbroad() {
        String nameStr = (String) getParameter("nameStr");

        List<String> areaNames = Arrays.asList(nameStr.split(","));
        List<TbArea> areas = areaService.getAreaListByName(areaNames);
        List<Long> idList = Lists.newArrayList();
        List<String> nameList = Lists.newArrayList();
        List<Long> abroadIdList = Lists.newArrayList();
        List<String> abroadNameList = Lists.newArrayList();
        for (TbArea area : areas) {
            if (area.getId() < 700000l) {
                switch (area.getId().toString()) {
                    case "310000":
                        idList.add(310100L);
                        break;
                    case "110000":
                        idList.add(110100L);
                        break;
                    case "120000":
                        idList.add(120100L);
                        break;
                    case "500000":
                        idList.add(500100L);
                        break;
                    default:
                        idList.add(area.getId());
                }
                nameList.add(area.getName());
            } else {
                abroadIdList.add(area.getId());
                abroadNameList.add(area.getName());
            }
        }
        Map<String, Object> result = Maps.newHashMap();
        result.put("internal", Lists.newArrayList(idList, nameList));
        result.put("abroad", Lists.newArrayList(abroadIdList, abroadNameList));
        result.put("success", !areas.isEmpty());
        return json(JSONObject.fromObject(result));
    }

    /**
     * 通过前端获取的多个城市名，获取Id列表
     *
     * @return
     */
    public Result getAreaListByParams() {

        Map<String, Object> map = new HashMap<String, Object>();
        String name = (String) getParameter("nameStr");
        List<Long> areaIds = new ArrayList<Long>();
        if (StringUtils.isNotBlank(name)) {
            String[] nameStrs = name.split("；");
            List<String> areaNames = new ArrayList<String>();
            for (int i = 0; i < nameStrs.length; i++) {
                areaNames.add(nameStrs[i]);
            }
            List<TbArea> areas = areaService.getAreaListByName(areaNames);

            if (!areas.isEmpty()) {
                for (TbArea a : areas) {
                    areaIds.add(a.getId());
                }
            }
            map.put("cityIds", areaIds);
            simpleResult(map, true, "");
        } else {
            map.put("cityIds", "a");
            simpleResult(map, true, "");
        }
        return jsonResult(map);
    }

    public Result getIpCity() {
        String ip = getRequest().getHeader("x-real-ip");
        TbArea ipCity = areaService.getLocation(ip);
        if (ipCity == null) {
            TbArea city = new TbArea();
            city.setName("厦门");
            city.setCityCode("350200");
            city.setId(350200l);
            return json(JSONObject.fromObject(city));
        }
        TbArea ipCityResponse = new TbArea();
        ipCityResponse.setName(ipCity.getName());
        ipCityResponse.setCityCode(ipCity.getCityCode());
        ipCityResponse.setId(ipCity.getId());
        return json(JSONObject.fromObject(ipCityResponse));
    }


    /**
     * 目的地首页热门目的地
     *
     * @return
     */
    public Result getHotArea() {
        return json(JSONObject.fromObject(areaService.getHomeHotArea()));
    }

    /**
     * 目的地首页当季热门目的地
     *
     * @return
     */
    public Result getSeasonHotArea() {
        return json(JSONObject.fromObject(areaService.getSeasonHotArea()));
    }

    /**
     * 目的地首页Ads
     *
     * @return
     */
    public Result getAdsList() {
        return json(JSONArray.fromObject(advertisingService.getDestinationBanner()));
    }

    /**
     * 城市信息查询
     * @return
     */
    public Result areaInfo() {
        tbArea = areaService.get(id);
        result.put("city", tbArea);
        result.put("extend", tbArea.getTbAreaExtend());
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    /**
     * 查询所有省份
     * @return
     */
    public Result getAllProvince() {
        List<TbArea> province = areaService.listAllProvinceArea();
        return json(JSONArray.fromObject(province, JsonFilter.getIncludeConfig()));
    }

    /**
     * 根据省份查询城市
     * @return
     */
    public Result getCityByProvince() {
        List<TbArea> city = areaService.listCityByProvince(provinceId);
        return json(JSONArray.fromObject(city, JsonFilter.getIncludeConfig()));
    }

    public Result citySelector() {
        List<TbArea> destinations = areaService.getHomeHotArea();
        result.put("hot", destinations);
        Label labelDest = new Label();
        labelDest.setName("通用目的地-国内");
        labelDest.setStatus(LabelStatus.USE);
        List<Label> labels = labelService.list(labelDest, null);
        if (!labels.isEmpty()) {
            List<TbArea> sortAreas = areaService.getTrafficAreas(labels.get(0).getId());
            List<Map<String, Object>> sortMap = sortAreasList(sortAreas);
            List<Map<String, List<Object>>> letterSortAreas = letterSortAreasList(sortMap);
            result.put("letterSortAreas", letterSortAreas);
        }
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
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

    public Result area() {
        tbArea = areaService.get(id);
        return dispatch();
    }

    public Result indexArea() {
        areaService.indexArea();
        return text("success");
    }

    public TbAreaSearchRequest getTbAreaSearchRequest() {
        return tbAreaSearchRequest;
    }

    public void setTbAreaSearchRequest(TbAreaSearchRequest tbAreaSearchRequest) {
        this.tbAreaSearchRequest = tbAreaSearchRequest;
    }

    public TbArea getTbArea() {
        return tbArea;
    }

    public void setTbArea(TbArea tbArea) {
        this.tbArea = tbArea;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSuggest() {
        return suggest;
    }

    public void setSuggest(String suggest) {
        this.suggest = suggest;
    }

    public Long getCityCode() {
        return cityCode;
    }

    public void setCityCode(Long cityCode) {
        this.cityCode = cityCode;
    }
}
