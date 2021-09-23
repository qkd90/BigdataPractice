package com.data.data.hmly.action.yhy;

import com.danga.MemCached.MemCachedClient;
import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.yhy.vo.TopProductData;
import com.data.data.hmly.service.SysRightService;
import com.data.data.hmly.service.SysRoleService;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.common.ProductService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.entity.*;
import com.data.data.hmly.util.Encryption;
import com.framework.struts.NotNeedLogin;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.CookieUtils;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/11/8.
 */
public class YhyLoginAction extends FrameBaseAction {

    private String yhyAccount;
    private String yhyPassword;
    private String yhyValidateCode;

    private SysUser user = new SysUser();
    private Map<String, Object> result = new HashMap<String, Object>();

    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysRightService sysRightService;
    @Resource
    private ProductService productService;

    @NotNeedLogin
    public Result login() {
        // logged
//        SysUser loginUser = getLoginUser();
//        if (loginUser != null) {
//            return redirect("/yhy/yhyMain/index.jhtml");
//        }
        return dispatch();
    }

    @NotNeedLogin
    public Result doLogin() {
        if (!StringUtils.hasText(yhyAccount)) {
            result.put("success", false);
            result.put("msg", "账户不可为空!");
            return json(JSONObject.fromObject(result));
        }
        if (!StringUtils.hasText(yhyAccount)) {
            result.put("success", false);
            result.put("msg", "密码不可为空!");
            return json(JSONObject.fromObject(result));
        }
        if (!StringUtils.hasText(yhyValidateCode)) {
            result.put("success", false);
            result.put("msg", "验证码不可为空!");
            return json(JSONObject.fromObject(result));
        }
        String checkNum = getSession().getAttribute("checkNum").toString();
        if (!yhyValidateCode.equals(checkNum)) {
            result.put("success", false);
            result.put("msg", "验证码不正确!");
            return json(JSONObject.fromObject(result));
        }
        SysUser sysUser = new SysUser();
        if (yhyPassword.length() <= 20) {
            yhyPassword = Encryption.encry(yhyPassword);
        }
        sysUser.setAccount(yhyAccount);
        sysUser.setPassword(yhyPassword);
        SysUser loginUser = sysUserService.findLoginUser(sysUser);
        MemCachedClient memCachedClient = SpringContextHolder.getBean("memCachedClient");
        if (loginUser != null) {
            if (!loginUser.getIsUse() || loginUser.getDelFlag()) {
                result.put("success", false);
                result.put("msg", "用户未激活(冻结或删除)!");
                getSession().removeAttribute("checkNum");
                return json(JSONObject.fromObject(result));
            }
            // success
            loginUser.setLoginNum(loginUser.getLoginNum() + 1);
            sysUserService.updateUser(loginUser);
            SysSite sysSite = new SysSite();
            sysSite.setId(loginUser.getSysSite().getId());
            sysSite.setArea(loginUser.getSysSite().getArea());
            sysSite.setSitename(loginUser.getSysSite().getSitename());
            sysSite.setSiteurl(loginUser.getSysSite().getSiteurl());
            loginUser.setSysSite(sysSite);
            SysUnit unit = new SysUnit();
            unit.setId(loginUser.getSysUnit().getId());
            unit.setName(loginUser.getSysUnit().getName());
            unit.setUnitType(loginUser.getSysUnit().getUnitType());
            unit.setUnitNo(loginUser.getSysUnit().getUnitNo());
            SysUnitDetail loginUserUd = loginUser.getSysUnit().getSysUnitDetail();
            if (loginUserUd != null) {
                SysUnitDetail ud = new SysUnitDetail();
                ud.setId(loginUserUd.getId());
                ud.setBrandName(loginUserUd.getBrandName());
                ud.setLogoImgPath(loginUserUd.getLogoImgPath());
                unit.setSysUnitDetail(ud);
            }
            SysUnit companyUnit = new SysUnit();
            if (loginUser.getSysUnit().getCompanyUnit() != null) {
                companyUnit.setId(loginUser.getSysUnit().getCompanyUnit().getId());
                companyUnit.setName(loginUser.getSysUnit().getCompanyUnit().getName());
                companyUnit.setUnitNo(loginUser.getSysUnit().getCompanyUnit().getUnitNo());
                companyUnit.setSysSite(sysSite);
                SysUnitDetail loginUserCompUd = loginUser.getSysUnit().getCompanyUnit().getSysUnitDetail();
                if (loginUserCompUd != null) {
                    SysUnitDetail compUd = new SysUnitDetail();
                    compUd.setId(loginUserCompUd.getId());
                    compUd.setBrandName(loginUserCompUd.getBrandName());
                    compUd.setLogoImgPath(loginUserCompUd.getLogoImgPath());
                    compUd.setScenicid(loginUserCompUd.getScenicid());
                    companyUnit.setSysUnitDetail(compUd);
                }
            }
            unit.setCompanyUnit(companyUnit);
            loginUser.setSysUnit(unit);
            getSession().setAttribute("loginuser", loginUser);
            Boolean singleLoogin = propertiesManager.getBoolean("SINGLE_LOGIN", true);
            if (singleLoogin) {
                memCachedClient.delete(loginUser.getId().toString());
                memCachedClient.add(loginUser.getId().toString(), getSession().getId());
            }
            getSession().setAttribute("account", loginUser.getAccount());
            getSession().setAttribute("staffName", loginUser.getUserName());
            // s = true
            // handle user role, resource, menu
            this.handleResource(loginUser);
            // handle cookie
            this.handleCookie();
            // get product
            List<Product> productList = productService.getByCompanyUnit(loginUser.getSysUnit().getCompanyUnit());
            if (!productList.isEmpty()) {
                // select first product
                Product product = productList.get(0);
                TopProductData selProduct = new TopProductData();
                if (product.getOriginId() == null) {
                    selProduct.setId(product.getId());
                } else {
                    selProduct.setId(product.getOriginId());
                }
                selProduct.setName(product.getName());
                getSession().setAttribute("product", selProduct);
            }
//            getSession().setAttribute("productList", productList);
            // to success
            result.put("success", true);
            result.put("msg", "");
            result.put("url", "/yhy/yhyMain/index.jhtml");
            return json(JSONObject.fromObject(result));
        } else {
            result.put("success", false);
            result.put("msg", "用户名或密码错误");
            getSession().removeAttribute("checkNum");
            return json(JSONObject.fromObject(result));
        }
    }

    @NotNeedLogin
    public Result checkValidateCode() {
        final HttpServletRequest request = getRequest();
        String code = request.getParameter("yhyValidateCode");
        String checkNum = getSession().getAttribute("checkNum").toString();
        if (StringUtils.hasText(code) && code.equals(checkNum)) {
            result.put("valid", true);
            return json(JSONObject.fromObject(result));
        }
        result.put("valid", false);
        return json(JSONObject.fromObject(result));
    }

    @NotNeedLogin
    public Result doLogout() {
        getSession().invalidate();
        return redirect("/yhy/yhyLogin/login.jhtml");
    }

    public void handleResource(SysUser loginUser) {
        List<SysUserRole> userRoles = sysRoleService.findUserRolesIsUse(loginUser.getId());
        loginUser.setSysRoles(userRoles);
        if (userRoles != null && !userRoles.isEmpty()) {
            String roleIds = "";
            for (int i = 0; i < userRoles.size(); i++) {
                SysRole role = userRoles.get(i).getRole();
                roleIds = i != 0 ? roleIds + "," : roleIds + role.getId();
            }
            // 只查询子系统菜单
            List<SysMenu> hasMenuTree = sysRightService.findRoleMenuTreeByRoleIds(roleIds, 0L, true);
            List<SysMenu> hasSubMenus = new ArrayList<SysMenu>();
            for (SysMenu menuLvl1 : hasMenuTree) {
                if (menuLvl1.getChildren() != null && !menuLvl1.getChildren().isEmpty()) {
                    hasSubMenus.addAll(menuLvl1.getChildren());
                }
            }
            if (!hasSubMenus.isEmpty()) {
                getSession().setAttribute("hasSubMenus", hasSubMenus);
            }
        }
    }

    public void listMenuToMap(List<SysMenu> rmList, Map<Long, List<SysMenu>> menuMap) {
        for (SysMenu sysMenu : rmList) {
            menuMap.put(sysMenu.getMenuid(), sysMenu.getChildren());
            listMenuToMap(sysMenu.getChildren(), menuMap);
        }
    }

    /**
     * 写入cookie内容：account + "," + password
     */
    private void handleCookie() {
        String chkrem = (String) getParameter("chkrem");
        if (StringUtils.isNotBlank(chkrem)) {
            String loginCookieValue = user.getAccount() + "," + user.getPassword();
            loginCookieValue = Encryption.desEncrypt(loginCookieValue, loginCookieKey);
            Cookie cookie = new Cookie(loginCookieKey, loginCookieValue);
            cookie.setMaxAge(Integer.MAX_VALUE);
            cookie.setPath("/");
            getResponse().addCookie(cookie);
        } else {
            Cookie cookie = CookieUtils.getCookie(getRequest(), loginCookieKey);
            if (cookie != null) {
                CookieUtils.delCookie(getRequest(), getResponse(), loginCookieKey);
            }
        }
    }

    public String getYhyAccount() {
        return yhyAccount;
    }

    public void setYhyAccount(String yhyAccount) {
        this.yhyAccount = yhyAccount;
    }

    public String getYhyPassword() {
        return yhyPassword;
    }

    public void setYhyPassword(String yhyPassword) {
        this.yhyPassword = yhyPassword;
    }

    public String getYhyValidateCode() {
        return yhyValidateCode;
    }

    public void setYhyValidateCode(String yhyValidateCode) {
        this.yhyValidateCode = yhyValidateCode;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }
}
