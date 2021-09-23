package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.action.yihaiyou.response.ScenicResponse;
import com.data.data.hmly.action.yihaiyou.response.ScenicSimpleResponse;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.ScenicMobileService;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.enums.ScenicInfoType;
import com.data.data.hmly.service.scenic.request.ScenicSearchRequest;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by huangpeijie on 2016-04-13,0013.
 */
public class ScenicWebAction extends BaseAction {
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private ScenicMobileService scenicMobileService;

    @Resource
    private LabelService labelService;

    private final ObjectMapper mapper = new ObjectMapper();

    public String json;
    public Long id;
    public Integer pageNo;
    public Integer pageSize;
    public Double lng;
    public Double lat;
    public Float distance;
    public String labelName;

    /**
     * 景点关键词搜索
     *
     * @return
     */
    @AjaxCheck
    public Result searchScenic() {
        ScenicSearchRequest request = new ScenicSearchRequest();
        Page pageInfo = new Page(1, 5);
        String keyword = (String) getParameter("keyword");
        List<ScenicSolrEntity> list = new ArrayList<ScenicSolrEntity>();
        if (StringUtils.isNotBlank(keyword)) {
            request.setName(keyword);
            list = scenicInfoService.listFromSolr(request, pageInfo);
            if (list.isEmpty()) {
                simpleResult(result, false, "");
            } else {
                result.put("scenicResult", list);
                simpleResult(result, true, "");
            }

        } else {
            simpleResult(result, false, "");
        }
        return jsonResult(result);
    }

    /**
     * 景点主题
     *
     * @return
     */
    @AjaxCheck
    public Result theme() {
        String json = getJsonDate(JsonDateFileName.SCENIC_THEME, 7);
        if (StringUtils.isNotBlank(json)) {
            return text(json);
        }
        List<String> scenicTheme = scenicInfoService.getScenicTheme();
        result.put("theme", scenicTheme);
        result.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(result);
        json = jsonObject.toString();
        setJsonDate(JsonDateFileName.SCENIC_THEME, json);
        return json(jsonObject);
    }

    /**
     * 景点列表
     *
     * @return
     */
    @AjaxCheck
    public Result list() throws IOException {
        ScenicSearchRequest request = mapper.readValue(json, ScenicSearchRequest.class);
        request.setScenicInfoType(ScenicInfoType.scenic);
        request.setTicketNum(0);
        if (labelName != null) {
            Label l = new Label();
            l.setName(labelName);
            l = labelService.findUnique(l);
            if (l != null) {
                request.getLabelIds().add(l.getId());
            }
        }

        Page page = new Page(pageNo, pageSize);
//        Long num = scenicInfoService.countFromSolr(request);
//        Integer maxCount = (pageNo - 1) * pageSize;
        List<ScenicSolrEntity> entities = new ArrayList<ScenicSolrEntity>();
        List<ScenicSimpleResponse> list = new ArrayList<ScenicSimpleResponse>();
//        if (maxCount <= num ) {
//            entities = scenicInfoService.listFromSolr(request, page);
        if (request.getLng() != null && request.getLat() != null) {
            entities = scenicInfoService.findNearByScenic(request, request.getLng(), request.getLat(), request.getDistance(), page);
        } else {
            entities = scenicInfoService.listFromSolr(request, page);
        }
        list = Lists.transform(entities, new Function<ScenicSolrEntity, ScenicSimpleResponse>() {
            @Override
            public ScenicSimpleResponse apply(ScenicSolrEntity input) {
                return new ScenicSimpleResponse(input);
            }
        });
//        }
        if (page.getPageIndex() >= page.getPageCount()) {

            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("scenicList", list);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 景点详情
     *
     * @return
     */
    @AjaxCheck
    public Result detail() {
        Integer status = scenicInfoService.getStatus(id);
        if (status != 1) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        ScenicResponse response = scenicMobileService.scenicDetail(id);
        result.put("scenic", response);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }


    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }
}
