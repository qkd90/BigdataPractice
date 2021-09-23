package com.data.data.hmly.action.sys;

import com.danga.MemCached.MemCachedClient;
import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysMenuService;
import com.data.data.hmly.service.SysRightService;
import com.data.data.hmly.service.SysRoleService;
import com.data.data.hmly.service.SysSiteService;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.entity.SysMenu;
import com.data.data.hmly.service.entity.SysResource;
import com.data.data.hmly.service.entity.SysRole;
import com.data.data.hmly.service.entity.SysRoleResource;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUnitDetail;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.SysUserRole;
import com.data.data.hmly.util.Encryption;
import com.framework.struts.NotNeedLogin;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.CookieUtils;
import com.zuipin.util.MD5;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import org.apache.struts2.ServletActionContext;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class LoginAction extends FrameBaseAction implements ModelDriven<SysUser> {

    private static final long serialVersionUID = 4757702436585343391L;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private SysRoleService sysRoleService;
    @Resource
    private SysUnitService sysUnitService;
    @Resource
    private SysMenuService sysMenuService;
    @Resource
    private SysSiteService sysSiteService;
    @Resource
    private SysRightService sysRightService;
    @Resource
    private PropertiesManager propertiesManager;
    private SysUser user = new SysUser();
    private String validateVlue;

    private String json;
    private Integer page = 1;
    private Integer rows = 10;
    private Map<String, Object> result = new HashMap<String, Object>();

    private String userType = "";
    private String userAccount = "";
    private String fgDomain;
    private String chkrem = "";

    /**
     * 功能描述：判断资源权限
     *
     * @param url
     * @return
     * @author : cjj 陈俊杰 @CreatedTime : 2015年4月10日上午11:14:04
     */
    public static Boolean hasUrl(String url) {
        Object obj = ServletActionContext.getRequest().getSession().getAttribute("resourceUrlMap");
        if (obj != null) {
            Map<String, SysResource> resourceUrlMap = (Map<String, SysResource>) obj;
            if (resourceUrlMap.containsKey(url)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 功能描述：判断资源权限
     *
     * @return
     * @author : cjj 陈俊杰 @CreatedTime : 2015年4月10日上午11:14:04
     */
    public static Boolean hasNo(String no) {
        Object obj = ServletActionContext.getRequest().getSession().getAttribute("resourceNoMap");
        if (obj != null) {
            Map<String, SysResource> resourceNoMap = (Map<String, SysResource>) obj;
            if (resourceNoMap.containsKey(no)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 功能描述：跳转到登录页面
     *
     * @return
     * @author : cjj 陈俊杰 @CreatedTime : 2014年12月12日下午3:44:16
     */
    @NotNeedLogin
    public Result login() {
        String hostDomain = getRequest().getServerName();
        SysSite sysSite = sysSiteService.findUniqueByUrl(hostDomain);
        if (sysSite != null) {
            if (StringUtils.isNotBlank(sysSite.getLogoImg())) {
                loginLogoImg = sysSite.getLogoImg();
                int index = loginLogoImg.lastIndexOf(".");
                loginLogoImg = loginLogoImg.substring(0, index) + "_login" + loginLogoImg.substring(index);
            }
        }
        fgDomain = propertiesManager.getString("FG_DOMAIN", "javascript:void(0);");
        // 读取cookie内容：account + "," + password
        Cookie cookie = CookieUtils.getCookie(getRequest(), loginCookieKey);
        if (cookie != null && StringUtils.isNotBlank(cookie.getValue())) {
            String loginCookieValue = cookie.getValue();
            loginCookieValue = Encryption.desDecrypt(loginCookieValue, loginCookieKey);
            String[] valArray = loginCookieValue.split(",");
            if (valArray.length == 2) {
                user.setAccount(valArray[0]);
                user.setPassword(valArray[1]);
                chkrem = "checked";
            }
        }

        return dispatch();
    }

    /**
     * 功能描述：登录操作
     *
     * @author : cjj 陈俊杰 @CreatedTime : 2014年12月12日下午3:12:59
     * @return
     */
    /**
     * 功能描述：
     *
     * @author : cjj 陈俊杰 @CreatedTime : 2015年4月10日上午11:16:47
     * @return
     */
    /**
     * 功能描述：
     *
     * @return
     * @author : cjj 陈俊杰 @CreatedTime : 2015年4月10日上午11:16:50
     */
    @NotNeedLogin
    public Result doLogin() {
        String fail = "/WEB-INF/jsp/sys/login/login.jsp";
        String success = "/index.jsp";

        Boolean s = false;
        Pattern reg = Pattern.compile("^[a-zA-Z0-9\u4e00-\u9fa5]*");
        if (!reg.matcher(user.getAccount()).matches()) {
            json = "用户名不可使用特殊符号！";
            return dispatch(fail);
        }
        if (!StringUtils.isNotBlank(user.getAccount())) {
            json = "请输入用户名!";
        } else if (!StringUtils.isNotBlank(user.getPassword())) {
            json = "请输入登录密码!";
        } else if (!StringUtils.isNotBlank(validateVlue)) {
            json = "请输入验证码!";
        } else {
            String sysCheckCode = (String) getSession().getAttribute("checkNum");
            if (sysCheckCode != null && sysCheckCode.equals(validateVlue)) {
                user.setUserType(null);    // 忽略用户类型
                // 密码检查，用密码长度判断是否为md5加密串，主要来自cookie
                String pwd = user.getPassword().trim();
                if (pwd.length() <= 20) {
                    pwd = Encryption.encry(pwd);
                    user.setPassword(pwd);
                }
                SysUser loginuser = sysUserService.findLoginUser(user);
                MemCachedClient memCachedClient = SpringContextHolder.getBean("memCachedClient");
                if (loginuser == null) {
                    json = "用户名或密码错误!";
                } else if (!loginuser.getIsUse()) {
                    json = "帐号尚未激活或已被冻结!";
                } else if (loginuser.getDelFlag()) {
                    json = "用户已删除!";
                } else {
                    loginuser.loginInfo();
                    sysUserService.updateUser(loginuser);
                    SysSite sysSite = new SysSite();
                    sysSite.setId(loginuser.getSysSite().getId());
                    sysSite.setArea(loginuser.getSysSite().getArea());
                    sysSite.setSitename(loginuser.getSysSite().getSitename());
                    sysSite.setSiteurl(loginuser.getSysSite().getSiteurl());
                    loginuser.setSysSite(sysSite);
                    SysUnit unit = new SysUnit();
                    unit.setId(loginuser.getSysUnit().getId());
                    unit.setName(loginuser.getSysUnit().getName());
                    unit.setUnitType(loginuser.getSysUnit().getUnitType());
                    unit.setUnitNo(loginuser.getSysUnit().getUnitNo());
                    SysUnitDetail loginUserUd = loginuser.getSysUnit().getSysUnitDetail();
                    if (loginUserUd != null) {
                        SysUnitDetail ud = new SysUnitDetail();
                        ud.setId(loginUserUd.getId());
                        ud.setBrandName(loginUserUd.getBrandName());
                        ud.setLogoImgPath(loginUserUd.getLogoImgPath());
                        unit.setSysUnitDetail(ud);
                    }
                    SysUnit companyUnit = new SysUnit();
                    if (loginuser.getSysUnit().getCompanyUnit() != null) {
                        companyUnit.setId(loginuser.getSysUnit().getCompanyUnit().getId());
                        companyUnit.setName(loginuser.getSysUnit().getCompanyUnit().getName());
                        companyUnit.setUnitType(loginuser.getSysUnit().getCompanyUnit().getUnitType());
                        companyUnit.setUnitNo(loginuser.getSysUnit().getCompanyUnit().getUnitNo());
                        companyUnit.setSysSite(sysSite);
                        SysUnitDetail loginUserCompUd = loginuser.getSysUnit().getCompanyUnit().getSysUnitDetail();
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
                    loginuser.setSysUnit(unit);
                    getSession().setAttribute("loginuser", loginuser);
                    Boolean singleLogin = propertiesManager.getBoolean("SINGLE_LOGIN", true);
                    if (singleLogin) {
                        memCachedClient.delete(loginuser.getId().toString());
                        memCachedClient.add(loginuser.getId().toString(), getSession().getId());
                    }
                    getSession().setAttribute("account", loginuser.getAccount());
                    getSession().setAttribute("staffName", loginuser.getUserName());
                    // 已统一为同一首页
                    // 判断当前登录人身份，首页选择（loginuser.getSysSite().getHomePage()取不到对应属性值！！！）
//                    SysSite sysSiteMock = sysSiteService.finSiteById(loginuser.getSysSite().getId());
//                    if (StringUtils.isNotBlank(sysSiteMock.getHomePage())) {
//                        homePage = sysSiteMock.getHomePage();
//                    }
//                    getSession().setAttribute("homePage", homePage);

                    s = true;
                    List<SysUserRole> userRoles = sysRoleService.findUserRolesIsUse(loginuser.getId());
                    loginuser.setSysRoles(userRoles);
                    if (userRoles != null && userRoles.size() > 0) {
                        SysRole defaultRole = userRoles.get(0).getRole();    // 默认角色
                        getSession().setAttribute("defaultRole", defaultRole);
                        getSession().setAttribute("positionName", defaultRole.getName());
                        getSession().setAttribute("usePositionId", defaultRole.getId());
                        Map<Long, SysResource> resourceMap = new HashMap<Long, SysResource>();
                        Map<String, SysResource> resourceUrlMap = new HashMap<String, SysResource>();
                        Map<String, SysResource> resourceNoMap = new HashMap<String, SysResource>();
                        List<SysMenu> rmList = null;
                        List<SysResource> srList = null;
                        if (defaultRole.isSupperAdmin()) {
                            rmList = sysRightService.findAllMenu(0L, false);
                            srList = sysRightService.findAllResource();
                        } else {
                            String roleIds = "";
                            for (int i = 0; i < userRoles.size(); i++) {
                                SysRole role = userRoles.get(i).getRole();
                                if (i != 0) {
                                    roleIds += ",";
                                }
                                roleIds += role.getId();
                            }
                            rmList = sysRightService.findRoleMenuTreeByRoleIds(roleIds, 0l, false);
                            List<SysRoleResource> srrList = sysRightService.findRoleResourceByRoleIds(roleIds);
                            srList = new ArrayList<SysResource>();
                            for (SysRoleResource srr : srrList) {
                                SysResource sr = srr.getResource();
                                srList.add(sr);
                            }
                        }
                        getSession().setAttribute("menuTree", rmList);    // 保存菜单树
                        Map<Long, List<SysMenu>> menuMap = new HashMap<Long, List<SysMenu>>();
                        listMenuToMap(rmList, menuMap);    // 将菜单树转化为map对象
                        getSession().setAttribute("menuMap", menuMap);    // 保存菜单树
                        for (SysResource sr : srList) {
                            resourceMap.put(sr.getId(), sr);
                            if (StringUtils.isNotBlank(sr.getResourceUrl())) {
                                resourceUrlMap.put(sr.getResourceUrl(), sr);
                            }
                            if (StringUtils.isNotBlank(sr.getResourceNo())) {
                                resourceNoMap.put(sr.getResourceNo(), sr);
                            }
                        }

                        getSession().setAttribute("resourceMap", resourceMap);  // 保存资源项
                        getSession().setAttribute("resourceUrlMap", resourceUrlMap);    // 保存资源项
                        getSession().setAttribute("resourceNoMap", resourceNoMap);  // 保存资源项
                    }
                    // 写入cookie内容：account + "," + password
                    handleCookie();
                }
            } else {
                json = "验证码错误!";
            }
        }
        if (s) {
            return dispatch(success);
        } else {
            return dispatch(fail);
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

    public void listMenuToMap(List<SysMenu> rmList, Map<Long, List<SysMenu>> menuMap) {
        for (SysMenu sysMenu : rmList) {
            menuMap.put(sysMenu.getMenuid(), sysMenu.getChildren());
            listMenuToMap(sysMenu.getChildren(), menuMap);
        }
    }

    /**
     * 功能描述：登出
     *
     * @return
     * @author : cjj 陈俊杰 @CreatedTime : 2015年4月10日上午10:09:43
     */
    @NotNeedLogin
    public Result loginOut() {
        getSession().invalidate();
        result.put("success", true);
        json = readJson(result);
        return json(JSONObject.fromObject(json));
    }

    public Result errorjson() {
        JSONObject o = new JSONObject();
        o.put("success", false);
        return json(o);
    }

    public Result error() {
        return text("ERROR");
    }

    /**
     * 功能描述：密码修改
     *
     * @return
     * @author : cjj 陈俊杰 @CreatedTime : 2015年4月10日上午10:10:15
     */
    public Result changePWD() {
        String oldpass = getRequest().getParameter("oldpass");
        String newpass = getRequest().getParameter("newpass");
        String repass = getRequest().getParameter("repass");
        if (StringUtils.isNotBlank(oldpass) && StringUtils.isNotBlank(newpass) && StringUtils.isNotBlank(repass)) {
            SysUser user = sysUserService.findSysUserById(getLoginUser().getId());
            oldpass = MD5.caiBeiMD5(oldpass.trim());
            if (!user.getPassword().equals(oldpass)) {
                simpleResult(result, false, "原密码错误,提交失败!");
            } else if (newpass.trim().length() < 6) {
                simpleResult(result, false, "密码不能小于6位!");
            } else if (!newpass.equals(repass)) {
                simpleResult(result, false, "确认密码与新密码不一致!");
            } else {
                newpass = MD5.caiBeiMD5(newpass.trim());
                user.setPassword(newpass);
                sysUserService.saveOrUpdateUser(user);
                simpleResult(result, true, "新密码设置成功!");
            }
        } else {
            simpleResult(result, false, "请按规范输入旧密码与新密码!");
        }
        return jsonResult(result);
    }

    /**
     * 功能描述：密码修改
     *
     * @return
     * @author : cjj 陈俊杰 @CreatedTime : 2015年4月10日上午10:10:15
     */
    public Result changeFenxiaoPWD() {
        String oldpass = getRequest().getParameter("oldpass");
        String newpass = getRequest().getParameter("newpass");
        String repass = getRequest().getParameter("repass");
        if (StringUtils.isNotBlank(oldpass) && StringUtils.isNotBlank(newpass) && StringUtils.isNotBlank(repass)) {
            SysUser user = sysUserService.findSysUserById(getLoginUser().getId());

            oldpass = Encryption.encry(oldpass.trim());
            if (!user.getPassword().equals(oldpass)) {
                simpleResult(result, false, "原密码错误,提交失败!");
            } else if (newpass.trim().length() < 6) {
                simpleResult(result, false, "密码不能小于6位!");
            } else if (!newpass.equals(repass)) {
                simpleResult(result, false, "确认密码与新密码不一致!");
            } else {
                newpass = Encryption.encry(newpass.trim());
                user.setPassword(newpass);
                sysUserService.saveOrUpdateUser(user);
                simpleResult(result, true, "新密码设置成功!");
            }
        } else {
            simpleResult(result, false, "请按规范输入旧密码与新密码!");
        }
        return jsonResult(result);
    }

    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
    }

    @Override
    public SysUser getModel() {
        return user;
    }

    @Override
    public String getJson() {
        return json;
    }

    @Override
    public void setJson(String json) {
        this.json = json;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public String getValidateVlue() {
        return validateVlue;
    }

    public void setValidateVlue(String validateVlue) {
        this.validateVlue = validateVlue;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
    }

    public String getUserAccount() {
        return userAccount;
    }

    public void setUserAccount(String userAccount) {
        this.userAccount = userAccount;
    }

    public String getFgDomain() {
        return fgDomain;
    }

    public void setFgDomain(String fgDomain) {
        this.fgDomain = fgDomain;
    }

    public String getChkrem() {
        return chkrem;
    }

    public void setChkrem(String chkrem) {
        this.chkrem = chkrem;
    }
}
