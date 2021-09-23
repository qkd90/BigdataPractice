package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.entity.*;
import com.data.data.hmly.util.Encryption;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.Constants;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;

import javax.annotation.Resource;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by hmly_03 on 2017/2/13.
 */
public class CompanyWebAction extends FrameBaseAction {


    private SysUnit unit = new SysUnit();
    private SysUser user				= new SysUser();
    private SysUnitDetail unitDetail			= new SysUnitDetail();
    private List<SysUnitImage> unitImages = Lists.newArrayList();
    private String companyLoginUrl;
    private TbArea tbArea = new TbArea();

    private Map<String, Object> map = new HashMap<String, Object>();
    @Resource
    private SysUserService sysUserService;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private TbAreaService tbAreaService;

    public Result getAreaList() {
        List<TbArea> tbAreas = Lists.newArrayList();
        if (tbArea.getId() == null) {
            return json(JSONArray.fromObject(tbAreas));
        }
        tbAreas = tbAreaService.findArea(tbArea.getId(), null, tbArea.getLevel());
        return json(JSONArray.fromObject(tbAreas, JsonFilter.getIncludeConfig()));
    }


    public Result saveCompanyInfo() {
        SysSite site = new SysSite();

        if (StringUtils.isBlank(user.getAccount()) || StringUtils.isBlank(StringUtils.filterString(user.getAccount()))) {
            simpleResult(map, false, "");
            map.put("info", "当前注册用户名为空，请重新注册！");
            return jsonResult(map);
        }

        site.setId(1L);
        user.setSysSite(site);
        user.setCreatedTime(new Date());
        user.setPassword(Encryption.encry(Constants.password));
        user.setUserType(UserType.CompanyManage);
        user.setParent(getLoginUser());
        user.setStatus(UserStatus.activity);
        user.setSuperior(user);
        user.setIsUse(true);
        user.setDelFlag(false);
        unit.setDelFlag(false);

        unit.setStatus(-1);	// 状态：-1待审核；0通过；1不通过；
        unit.setUnitType(UnitType.company);
        unit.setSysSite(site);
        unit.setCreateTime(new Date());

        tbArea = tbAreaService.getArea(unitDetail.getCrtCity().getId());
        unitDetail.setCrtCity(tbArea);

        sysUserService.doInivited(user, unit, unitDetail, unitImages, null);

        map.put("info", "注册成功，您的初始密码为123456，可登录商户系统查看商户信息！ ");
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    public Result doRegister() {
        companyLoginUrl = propertiesManager.getString("COMPANY_LOGIN");
        return dispatch("/WEB-INF/jsp/yhypc/company/businessRegistration.jsp");
    }

    public SysUnit getUnit() {
        return unit;
    }

    public void setUnit(SysUnit unit) {
        this.unit = unit;
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    public SysUnitDetail getUnitDetail() {
        return unitDetail;
    }

    public void setUnitDetail(SysUnitDetail unitDetail) {
        this.unitDetail = unitDetail;
    }

    public List<SysUnitImage> getUnitImages() {
        return unitImages;
    }

    public void setUnitImages(List<SysUnitImage> unitImages) {
        this.unitImages = unitImages;
    }

    public String getCompanyLoginUrl() {
        return companyLoginUrl;
    }

    public void setCompanyLoginUrl(String companyLoginUrl) {
        this.companyLoginUrl = companyLoginUrl;
    }

    public TbArea getTbArea() {
        return tbArea;
    }

    public void setTbArea(TbArea tbArea) {
        this.tbArea = tbArea;
    }
}
