package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.action.yihaiyou.response.CityResponse;
import com.data.data.hmly.enums.LabelStatus;
import com.data.data.hmly.service.CityMobileService;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.TbArea;
import com.data.hmly.service.translation.geo.baidu.BaiduGeoCoderService;
import com.data.hmly.service.translation.geo.baidu.pojo.GeoCoder.Reder;
import com.data.hmly.service.translation.geo.baidu.pojo.GeoCoder.RederReverse;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-04-13,0013.
 */
public class CityWebAction extends BaseAction {
    @Resource
    private LabelService labelService;
    @Resource
    private AreaService areaService;
    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private CityMobileService cityMobileService;
    private final PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
    private final BaiduGeoCoderService baiduGeoCoderService = new BaiduGeoCoderService();

    private final String baiduKey = propertiesManager.getString("BAIDU_KEY");

    public String longitude;
    public String latitude;
    public String keyword;
    public Integer pageNo;
    public Integer pageSize;

    private String address;
    private String city;

    /**
     * 城市列表
     *
     * @return
     */
    @AjaxCheck
    public Result list() {
        List<CityResponse> areaList = Lists.newArrayList();
        if (StringUtils.isNotBlank(longitude) && StringUtils.isNotBlank(latitude)) {
            RederReverse reverse = baiduGeoCoderService.getRederReverse(latitude, longitude, baiduKey);
            RederReverse.Addresscomponent addresscomponent = reverse.result.addressComponent;
            TbArea area = areaService.get(Long.valueOf(addresscomponent.adcode) / 100 * 100);
            result.put("city", JSONObject.fromObject(area, JsonFilter.getIncludeConfig("id", "name", "cityCode")));
        } else {
            TbArea area = new TbArea();
            if (StringUtils.isNotBlank(keyword)) {
                area.setName(keyword);
            }
            area.setLevel(2);
            List<TbArea> areas = tbAreaService.list(area, null, new Page(pageNo, pageSize));
            areaList = Lists.transform(areas, new Function<TbArea, CityResponse>() {
                @Override
                public CityResponse apply(TbArea input) {
                    return new CityResponse(input);
                }
            });
            result.put("cityList", areaList);
        }
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 地理位置逆编码
     *
     * @return
     */
    public Result reverseCity() {
        if (StringUtils.isNotBlank(city) && StringUtils.isNotBlank(address)) {
            Map<String, Object> result = new HashMap<String, Object>();
            Reder reder = baiduGeoCoderService.getReder(address, city, baiduKey);
            if (reder != null && reder.result != null && reder.result.location != null) {
                result.put("lng", reder.result.location.lng);
                result.put("lat", reder.result.location.lat);
                return json(JSONObject.fromObject(result));
            }
        }
        return null;
    }

    /**
     * 当前城市
     *
     * @return
     */
    @AjaxCheck
    public Result getCurrentCity() throws LoginException {

        Member member = getLoginUser();
        if (member != null) {
//            WechatLocationLog log = wechatLocationLogService.findUserLastLocation(member.getId());
//            TbArea area = log.getTbArea();
//            result.put("city", JSONObject.fromObject(area, JsonFilter.getIncludeConfig("id", "name", "cityCode")));
//            result.put("success", true);
            result.put("success", false);
        } else {
//            WechatLocationLog log = wechatLocationLogService.findUserLastLocation(159L);
//            TbArea area = log.getTbArea();
//            result.put("city", JSONObject.fromObject(area, JsonFilter.getIncludeConfig("id", "name", "cityCode")));
//            result.put("success", true);
            result.put("success", false);
        }
        return jsonResult(result);
    }

    /**
     * 热门目的地
     *
     * @return
     */
    @AjaxCheck
    public Result hotCity() {
        String json = getJsonDate(JsonDateFileName.CITY_HOT_CITY, 30);
        if (StringUtils.isNotBlank(json)) {
            return text(json);
        }
        Label labelDest = new Label();
        labelDest.setName("通用目的地-国内");
        labelDest.setStatus(LabelStatus.USE);
        List<Label> labels = labelService.list(labelDest, null);
        if (labels.isEmpty()) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        List<TbArea> sortAreas = areaService.getTrafficAreas(labels.get(0).getId());
        Map<String, List<CityResponse>> sortMap = cityMobileService.sortAreasList(sortAreas);
        List<TbArea> hotAreas = areaService.getHomeHotArea();
        List<CityResponse> hot = Lists.transform(hotAreas, new Function<TbArea, CityResponse>() {
            @Override
            public CityResponse apply(TbArea input) {
                return new CityResponse(input);
            }
        });
        sortMap.put("hot", hot);
        result.put("sortMap", sortMap);
        result.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(result);
        json = jsonObject.toString();
        setJsonDate(JsonDateFileName.CITY_HOT_CITY, json);
        return json(jsonObject);
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }
}
