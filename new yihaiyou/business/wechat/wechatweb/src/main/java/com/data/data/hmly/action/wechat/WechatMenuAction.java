package com.data.data.hmly.action.wechat;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.WechatMenuService;
import com.data.data.hmly.service.wechat.WechatResourceService;
import com.data.data.hmly.service.wechat.entity.WechatAccountMenu;
import com.data.data.hmly.service.wechat.entity.WechatResource;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import org.codehaus.jackson.map.ObjectMapper;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vacuity on 15/11/25.
 */
public class WechatMenuAction extends FrameBaseAction {


    @Resource
    private WechatMenuService wechatMenuService;
    @Resource
    private WechatResourceService wechatResourceService;
    @Resource
    private WechatAccountService wechatAccountService;

    private Long id;
    private Long accountId;

    private String menuName;
    private Long resourceId;
    private Integer level;
    private Long parentId;
    private Integer orderNo;

    private String data;

    // 返回数据
    Map<String, Object> map = new HashMap<String, Object>();

    public Result manage() {
        return dispatch();
    }

    public Result getList() {
        List<WechatAccountMenu> wechatAccountMenuList = wechatMenuService.getMenu(accountId);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(new Class[]{}, "children", "wechatResource");
        JSONArray json = JSONArray.fromObject(wechatAccountMenuList, jsonConfig);
        return json(json);
    }

    public Result saveOrUpdate() {
        WechatAccountMenu wechatAccountMenu = new WechatAccountMenu();
        if (id != null && id != 0) {
            wechatAccountMenu = wechatMenuService.get(id);
        } else {
            if (!checkCount()) {
                //TODO 数量超过限制需要返回出错信息
                return null;
            }
            wechatAccountMenu.setCreateTime(new Date());
        }
        if (StringUtils.isNotBlank(menuName)) {
            wechatAccountMenu.setMenuName(menuName);
        }
        if (resourceId != null && resourceId != 0) {
            wechatAccountMenu.setWechatResource(wechatResourceService.get(resourceId));
        }
        if (level != null && level != 0) {
            wechatAccountMenu.setLevel(level);
        }
        if (parentId != null && parentId != 0) {
            WechatAccountMenu parentMenu = wechatMenuService.get(parentId);
            wechatAccountMenu.setParentMenu(parentMenu);
        }
        wechatAccountMenu.setWechatAccount(wechatAccountService.get(accountId));
        wechatAccountMenu.setCompanyUnit(getCompanyUnit());
        wechatAccountMenu.setUser(getLoginUser());
        if (orderNo != null && orderNo != 0) {
            wechatAccountMenu.setOrderNo(orderNo);
        }
        wechatMenuService.saveOrUpdate(wechatAccountMenu);
        //TODO 返回类型需要修改已保证正确返回信息
        return null;
    }

    public Result getResourceList() {
        List<WechatResource> resourceList = wechatResourceService.getResourceList();
        return json(JSONArray.fromObject(resourceList));
    }

    public boolean checkCount() {
        Integer count = wechatMenuService.getCount(accountId, level);
        if ((level == 1 && count < 3) || (level == 2 && count < 5)) {
            return true;
        } else {
            return false;
        }
    }

    @AjaxCheck
    public Result saveMenu() {

        SysUser user = getLoginUser();
        SysUnit companyUnit = getCompanyUnit();
        try {
            List<Map<String, Object>> menuMapList = new ObjectMapper().readValue(data, List.class);
            wechatMenuService.saveMenu(accountId, user, companyUnit, menuMapList);
        } catch (IOException e) {
            map.put("exception", "数据格式化异常");
            simpleResult(map, false, "");
            return jsonResult(map);
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

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public String getMenuName() {
        return menuName;
    }

    public void setMenuName(String menuName) {
        this.menuName = menuName;
    }

    public Long getResourceId() {
        return resourceId;
    }

    public void setResourceId(Long resourceId) {
        this.resourceId = resourceId;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public Integer getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(Integer orderNo) {
        this.orderNo = orderNo;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }
}
