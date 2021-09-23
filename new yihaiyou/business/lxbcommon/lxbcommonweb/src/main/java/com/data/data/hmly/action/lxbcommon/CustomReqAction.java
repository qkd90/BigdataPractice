package com.data.data.hmly.action.lxbcommon;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.lxbcommon.CustomRequireService;
import com.data.data.hmly.service.lxbcommon.entity.CustomRequire;
import com.data.data.hmly.service.lxbcommon.entity.enums.CustomStatus;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/6/15.
 */
public class CustomReqAction extends FrameBaseAction {

    private int page;
    private int rows;
    private String orderProperty;
    private String orderType;

    private Long id;

    private CustomRequire customRequire = new CustomRequire();

    @Resource
    private CustomRequireService customRequireService;


    public Result list() {
        return dispatch("/WEB-INF/jsp/lxbcommon/customreq/list.jsp");
    }


    public Result getCustomReqList() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Page page = new Page(this.page, this.rows);
        List<CustomRequire> customRequireList = customRequireService.getCustomReqList(customRequire, page);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("startCity", "member", "plan", "handler", "customRequireDestinations", "city");
        return datagrid(customRequireList, page.getTotalCount(), jsonConfig);
    }

    public Result getCustomReqDetail() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            CustomRequire customRequire = customRequireService.get(id);
            if (customRequire != null) {
                result.put("customRequire.id", customRequire.getId());
                result.put("id", customRequire.getId());
                result.put("customRequire.remark", customRequire.getRemark());
            } else {
                result.put("id", "ID不可为空或该私人定制不存在!");
            }
        } else {
            result.put("id", "ID不可为空或该私人定制不存在!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result modifyCustomReq() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            CustomRequire sourceCustomRequire = customRequireService.get(id);
            if (sourceCustomRequire != null) {
                sourceCustomRequire.setRemark(customRequire.getRemark());
                sourceCustomRequire.setHandler(loginUser);
                sourceCustomRequire.setHandleTime(new Date());
                customRequireService.update(sourceCustomRequire);
                result.put("success", true);
                result.put("msg", "修改成功!");
            } else {
                result.put("success", false);
                result.put("msg", "修改失败!该私人定制不存在!可能已经被删除!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "ID不可为空!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result cancelCustomReq() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            CustomRequire sourceCustomRequire = customRequireService.get(id);
            if (sourceCustomRequire != null) {
                sourceCustomRequire.setStatus(CustomStatus.cancel);
                sourceCustomRequire.setHandler(loginUser);
                sourceCustomRequire.setHandleTime(new Date());
                customRequireService.update(sourceCustomRequire);
                result.put("success", true);
                result.put("msg", "取消成功!");
            } else {
                result.put("success", false);
                result.put("msg", "操作失败! 该私人定制不存在!可能已经被删除!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "操作失败! ID不可为空!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result handleCustomReq() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            CustomRequire sourceCustomRequire = customRequireService.get(id);
            if (sourceCustomRequire != null) {
                sourceCustomRequire.setStatus(CustomStatus.handled);
                sourceCustomRequire.setHandler(loginUser);
                sourceCustomRequire.setHandleTime(new Date());
                customRequireService.update(sourceCustomRequire);
                result.put("success", true);
                result.put("msg", "处理成功!");
            } else {
                result.put("success", false);
                result.put("msg", "操作失败! 该私人定制不存在!可能已经被删除!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "操作失败! ID不可为空!");
        }
        return json(JSONObject.fromObject(result));
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

    public CustomRequire getCustomRequire() {
        return customRequire;
    }

    public void setCustomRequire(CustomRequire customRequire) {
        this.customRequire = customRequire;
    }
}
