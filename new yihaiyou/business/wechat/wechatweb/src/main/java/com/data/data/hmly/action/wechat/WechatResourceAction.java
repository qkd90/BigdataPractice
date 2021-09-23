package com.data.data.hmly.action.wechat;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.wechat.WechatResourceService;
import com.data.data.hmly.service.wechat.entity.WechatResource;
import com.data.data.hmly.service.wechat.entity.enums.ResType;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.StringUtils;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vacuity on 15/11/24.
 */

public class WechatResourceAction extends FrameBaseAction {


    @Resource
    private WechatResourceService wechatResourceService;

    private Long id;
    private String resName;
    private ResType resType;
    private String content;
    private String resObjectParam;
    private Boolean validFlag;
    private int page;
    private int rows;

    private String ids;


    Map<String, Object> map = new HashMap<String, Object>();



    public Result list() {
        return dispatch();
    }


    public Result getList() {
        Page pageInfo = new Page(page, rows);
        WechatResource wechatResource = new WechatResource();
        if (StringUtils.isNotBlank(resName)) {
            wechatResource.setResName(resName);
        }
        List<WechatResource> wechatResources = wechatResourceService.getList(wechatResource, pageInfo);
        return datagrid(wechatResources, pageInfo.getTotalCount());
    }


    public Result saveWechatResource() {
        WechatResource wechatResource = wechatResourceService.get(id);
        if (wechatResource == null) {
            wechatResource = new WechatResource();
        }
        if (StringUtils.isNotBlank(resName)) {
            wechatResource.setResName(resName);
        }
        if (resType != null) {
            wechatResource.setResType(resType);
        }
        if (StringUtils.isNotBlank(content)) {
            wechatResource.setContent(content);
        }
//        if (StringUtils.isNotBlank(resObjectParam)) {
            wechatResource.setResObjectParam(resObjectParam);
//        }
        if (validFlag != null) {
            wechatResource.setValidFlag(validFlag);
        }
        if (wechatResource.getId() == null) {
            wechatResource.setCreateTime(new Date());
        }
        wechatResourceService.saveOrUpdate(wechatResource);

        return redirect("/wechat/wechatResource/list.jhtml");
    }

    public Result editRes() {
        WechatResource wechatResource = wechatResourceService.get(id);
        Map<String, Object> resMap = new HashMap<String, Object>();
        resMap.put("id", id);
        resMap.put("resName", wechatResource.getResName());
        resMap.put("resType", wechatResource.getResType());
        resMap.put("content", wechatResource.getContent());
        resMap.put("resObjectParam", wechatResource.getResObjectParam());
        resMap.put("validFlag", wechatResource.getValidFlag());
        return jsonResult(resMap);
    }

    @AjaxCheck
    public Result doValidByIds() {
        String ids = (String) getParameter("ids");
        if (StringUtils.isNotBlank(ids)) {
            wechatResourceService.doValidByIds(ids, true);
        }
        simpleResult(map, true, "");
        return jsonResult(map);
    }


    @AjaxCheck
    public Result doInvalidByIds() {
        String ids = (String) getParameter("ids");
        if (StringUtils.isNotBlank(ids)) {
            wechatResourceService.doValidByIds(ids, false);
        }
        simpleResult(map, true, "");
        return jsonResult(map);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getResName() {
        return resName;
    }

    public void setResName(String resName) {
        this.resName = resName;
    }

    public ResType getResType() {
        return resType;
    }

    public void setResType(ResType resType) {
        this.resType = resType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getResObjectParam() {
        return resObjectParam;
    }

    public void setResObjectParam(String resObjectParam) {
        this.resObjectParam = resObjectParam;
    }

    public Boolean getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(Boolean validFlag) {
        this.validFlag = validFlag;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }

    public String getIds() {
        return ids;
    }

    public void setIds(String ids) {
        this.ids = ids;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
