package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.action.yihaiyou.response.CruiseShipCategoryResponse;
import com.data.data.hmly.action.yihaiyou.response.CruiseShipDateResponse;
import com.data.data.hmly.action.yihaiyou.response.CruiseShipResponse;
import com.data.data.hmly.action.yihaiyou.response.CruiseShipRoomResponse;
import com.data.data.hmly.service.CruiseShipMobileService;
import com.data.data.hmly.service.common.vo.SuggestionEntity;
import com.data.data.hmly.service.cruiseship.CruiseShipDateService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.cruiseship.request.CruiseShipSearchRequest;
import com.data.data.hmly.service.lvxbang.SuggestService;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.framework.struts.NeedLogin;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.List;

/**
 * Created by huangpeijie on 2016-09-19,0019.
 */
public class CruiseShipWebAction extends BaseAction {
    @Resource
    private CruiseShipMobileService cruiseShipMobileService;
    @Resource
    private SuggestService suggestService;
    @Resource
    private CruiseShipDateService cruiseShipDateService;

    public Long shipId;
    public Long dateId;
    public String json;
    public Integer pageNo;
    public Integer pageSize;
    public String name;
    public String sortName;
    public String sortOrder;
    public Long brandId;
    public Long routeId;
    public Date startTime;


    @AjaxCheck
    public Result detail() {
        CruiseShipResponse response = cruiseShipMobileService.detail(dateId);
        if (response == null) {
            result.put("success", false);
            return jsonResult(result);
        }
        result.put("ship", response);
        result.put("success", true);
        return jsonResult(result);
    }

    @AjaxCheck
    @NeedLogin
    public Result orderShip() {
        List<List<CruiseShipRoomResponse>> roomsList = cruiseShipMobileService.roomList(shipId, dateId);
        CruiseShipResponse ship = cruiseShipMobileService.simpleDetail(shipId);
        CruiseShipDateResponse date = cruiseShipMobileService.date(dateId);
        result.put("roomsList", roomsList);
        result.put("ship", ship);
        result.put("date", date);
        result.put("success", true);
        return jsonResult(result);
    }

    @AjaxCheck
    public Result dateList() {
        List<CruiseShipDateResponse> responses = cruiseShipMobileService.dateList(shipId);
        result.put("dateList", responses);
        result.put("success", true);
        return jsonResult(result);
    }

    @AjaxCheck
    public Result list() throws IOException {
        CruiseShipSearchRequest request = mapper.readValue(json, CruiseShipSearchRequest.class);
        List<String> dateRangeList = Lists.newArrayList();
        dateRangeList.add(DateUtils.format(new Date(), "yyyy-MM-dd'T'HH:mm:ss'Z'"));
        request.setDateRange(dateRangeList);
        Page page = new Page(pageNo, pageSize);
        List<CruiseShipResponse> responses = cruiseShipMobileService.shipList(request, page);
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("ships", responses);
        result.put("success", true);
        return jsonResult(result);
    }



    @AjaxCheck
    public Result index() {
        List<CruiseShipResponse> responses = cruiseShipMobileService.indexShip();
        result.put("ships", responses);
        result.put("success", true);
        return jsonResult(result);
    }

    @AjaxCheck
    public Result shipBrand() {
        List<CruiseShipCategoryResponse> responses = cruiseShipMobileService.shipBrand();
        result.put("brands", responses);
        result.put("success", true);
        return jsonResult(result);
    }

    @AjaxCheck
    public Result shipRoute() {
        List<CruiseShipCategoryResponse> responses = cruiseShipMobileService.shipRoute();
        result.put("routes", responses);
        result.put("success", true);
        return jsonResult(result);
    }

    public Result suggest() {
        List<SuggestionEntity> entities = suggestService.suggestCruiseShip(name, pageSize);
        result.put("suggestions", entities);
        result.put("success", true);
        return jsonResult(result);
    }

    //查询邮轮列表
    @AjaxCheck
    public Result shipDateList(){
        Page page = new Page(pageNo, pageSize);
        List<CruiseShipResponse> shipList = cruiseShipMobileService.dateList(page, sortName, sortOrder, brandId, routeId, startTime);
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("shipList", shipList);
        result.put("success", true);
        return jsonResult(result);
    }

}
