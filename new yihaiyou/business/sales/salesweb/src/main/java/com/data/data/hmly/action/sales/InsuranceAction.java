package com.data.data.hmly.action.sales;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.sales.InsuranceService;
import com.data.data.hmly.service.sales.entity.Insurance;
import com.data.data.hmly.service.sales.entity.enums.SalesStatus;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/7/6.
 */
public class InsuranceAction extends FrameBaseAction {

    @Resource
    private InsuranceService insuranceService;

    private int page;
    private int rows;
    private String orderProperty;
    private String orderType;

    private Long id;
    private Insurance insurance = new Insurance();

    public Result list() {
        return dispatch();
    }
    public Result getInsuranceList() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Page page = new Page(this.page, this.rows);
        List<Insurance> insuranceList = insuranceService.list(insurance, page, orderProperty, orderType);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("category", "salesImagesList");
        return datagrid(insuranceList, page.getTotalCount(), jsonConfig);
    }

    public Result commitInsurance() {
        Map<String, Object> result = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        if (insurance.getId() != null) {
            Insurance sourceInsurance = insuranceService.get(insurance.getId());
            boolean updateSuccess = insuranceService.update(insurance, sourceInsurance);
            result.put("success", updateSuccess);
            if (updateSuccess) {
                result.put("msg", "保险保存成功!");
            } else {
                result.put("msg", "保险保存失败!");
            }
        } else if (insurance.getId() == null) {
            boolean success = insuranceService.save(insurance);
            result.put("success", success);
            if (success) {
                result.put("msg", "保险保存成功!");
            } else {
                result.put("msg", "保险保存失败!");
            }
        }
        return json(JSONObject.fromObject(result));
    }

    public Result detailInsurance() {
        Map<String, Object> result = new HashMap<String, Object>();
        if (id != null && id > 0) {
            Insurance insurance = insuranceService.get(id);
            if (insurance != null) {
                result.put("insurance.id", insurance.getId());
                result.put("insurance.name", insurance.getName());
                result.put("insurance.category.id", insurance.getCategory().getId());
                result.put("insurance.company", insurance.getCompany());
                result.put("insurance.price", insurance.getPrice());
                result.put("insurance.status", insurance.getStatus());
                result.put("insurance.description", insurance.getDescription());
                result.put("insurance.notice", insurance.getNotice());
                result.put("insurance.terms", insurance.getTerms());
            }
        }
        return json(JSONObject.fromObject(result));
    }

    public Result upInsurance() {
        Map<String, Object> result = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        if (id == null || id <= 0) {
            result.put("success", false);
            result.put("msg", "保险ID不可为空或不存在的保险!");
        }
        Insurance insurance = insuranceService.get(id);
        if (insurance != null) {
            insurance.setStatus(SalesStatus.up);
            insuranceService.update(insurance);
            result.put("success", true);
            result.put("msg", "保险上架成功!");
        } else {
            result.put("success", false);
            result.put("msg", "保险ID不可为空或不存在的保险!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result downInsurance() {
        Map<String, Object> result = new HashMap<String, Object>();
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        if (id == null || id <= 0) {
            result.put("success", false);
            result.put("msg", "保险ID不可为空或不存在的保险!");
        }
        Insurance insurance = insuranceService.get(id);
        if (insurance != null) {
            insurance.setStatus(SalesStatus.down);
            insuranceService.update(insurance);
            result.put("success", true);
            result.put("msg", "保险下架成功!");
        } else {
            result.put("success", false);
            result.put("msg", "保险ID不可为空或不存在的保险!");
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

    public Insurance getInsurance() {
        return insurance;
    }

    public void setInsurance(Insurance insurance) {
        this.insurance = insurance;
    }
}
