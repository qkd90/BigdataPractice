package com.data.data.hmly.action.lxbcommon;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.MsgTemplateService;
import com.data.data.hmly.service.common.entity.MsgTemplate;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.BeanUtils;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/12/19.
 */
public class MsgTemplateAction extends FrameBaseAction {

    private Map<String, Object> result = new HashMap<String, Object>();

    private int page;
    private int rows;
    private String orderProperty;
    private String orderType;

    private Long id;
    private MsgTemplate msgTemplate = new MsgTemplate();

    @Resource
    private MsgTemplateService msgTemplateService;

    public Result list() {
        return dispatch();
    }

    public Result getMsgTemplateList() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Page page = new Page(this.page, this.rows);
        List<MsgTemplate> msgTemplateList = msgTemplateService.getMsgTemplateList(msgTemplate, page, orderProperty, orderType);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(msgTemplateList, page.getTotalCount(), jsonConfig);
    }

    public Result getMsgTemplateInfo() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        if (id != null) {
            MsgTemplate msgTemplate = msgTemplateService.get(id);
            if (msgTemplate != null) {
                result.put("msgTemplate.id", msgTemplate.getId());
                result.put("msgTemplate.title", msgTemplate.getTitle());
                result.put("msgTemplate.content", msgTemplate.getContent());
                result.put("msgTemplate.description", msgTemplate.getDescription());
                result.put("success", true);
                result.put("msg", "");
            } else {
                result.put("success", false);
                result.put("msg", "消息模板不存在!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "消息模板id错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result doEdit() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        if (msgTemplate.getId() != null) {
            MsgTemplate targetTemplate = msgTemplateService.get(msgTemplate.getId());
            BeanUtils.copyProperties(msgTemplate, targetTemplate, false, null, "id");
            targetTemplate.setUpdateTime(new Date());
            msgTemplateService.update(targetTemplate);
        }
        result.put("success", true);
        result.put("msg", "");
        return json(JSONObject.fromObject(result));
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
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

    public String getOrderProperty() {
        return orderProperty;
    }

    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MsgTemplate getMsgTemplate() {
        return msgTemplate;
    }

    public void setMsgTemplate(MsgTemplate msgTemplate) {
        this.msgTemplate = msgTemplate;
    }
}
