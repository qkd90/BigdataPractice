package com.data.data.hmly.action.yihaiyou;

import com.data.data.hmly.service.restaurant.DelicacyService;
import com.data.data.hmly.service.restaurant.request.DelicacySearchRequest;
import com.data.data.hmly.service.restaurant.vo.DelicacySolrEntity;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.List;

/**
 * Created by huangpeijie on 2016-11-25,0025.
 */
public class FoodWebAction extends BaseAction {
    @Resource
    private DelicacyService delicacyService;

    public Integer pageNo;
    public Integer pageSize;
    public String json;

    @AjaxCheck
    public Result list() throws IOException {
        DelicacySearchRequest request = mapper.readValue(json, DelicacySearchRequest.class);
        Page page = new Page(pageNo, pageSize);
        List<DelicacySolrEntity> list = delicacyService.listFromSolr(request, page);
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("success", true);
        result.put("foodList", list);
        return jsonResult(result);
    }
}
