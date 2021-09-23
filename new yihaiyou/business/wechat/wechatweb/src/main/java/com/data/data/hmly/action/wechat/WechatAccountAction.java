package com.data.data.hmly.action.wechat;

import java.util.*;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;

/**
 * Created by vacuity on 15/11/20.
 */
public class WechatAccountAction extends FrameBaseAction {

    @Resource
    private WechatAccountService wechatAccountService;

    private int page;
    private int rows;
    private long id;
    private String account;
    private String appId;
    private String appSecret;
    private String mchKey;
    private String mchId;
    private String defaultTplId;
    private Boolean validFlag;
    private String originalId;

    private Map<String, Object> map = new HashMap<String, Object>();

    public Result list() {
        return dispatch();
    }

    public Result getComboList() {

        List<WechatAccount> accounts = wechatAccountService.findComboList(getLoginUser(), getCompanyUnit(), isSupperAdmin(), isSiteAdmin());

        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("");

        return datagrid(accounts, jsonConfig);
    }

    /**
     * 验证OriId信息
     * @return
     */
    public Result validateOriId() {

    	String valiContent = (String) getParameter("valiContent");
    	String id = (String) getParameter("id");
    	boolean flag = false;
    	if (org.apache.commons.lang.StringUtils.isNotBlank(valiContent)) {
    		if (org.apache.commons.lang.StringUtils.isNotBlank(id)) {
    			flag = wechatAccountService.validateOriId(valiContent, Long.parseLong(id));
        	} else {
        		flag = wechatAccountService.validateOriId(valiContent);
        	}
    		
    	}
    	
    	
    	simpleResult(map, flag, "");
    	return jsonResult(map);
    }
    
    /**
     * 获取公众号信息
     * @return
     */
    public Result getAccountInfo() {
    	
    	String id = (String) getParameter("id");
    	WechatAccount account = null;
    	if (!StringUtils.isEmpty(id)) {
    		 account = wechatAccountService.get(Long.parseLong(id));
    	}
    	JsonConfig jsonConfig = JsonFilter.getFilterConfig("companyUnit", "user");
    	JSONObject jsonObject = JSONObject.fromObject(account, jsonConfig);
    	
        return json(jsonObject);
    }

    /**
     * 获取同一个公司的帐号列表
     * @return
     */
    public Result getAccountList() {
        Page pageInfo = new Page(page, rows);
        SysSite sysSite = getSite();
        SysUnit companyUnit = getCompanyUnit();
        companyUnit.setSysSite(sysSite);
        Boolean isAdmin = isSupperAdmin();
        Boolean isSiteAdmin = isSiteAdmin();
        List<WechatAccount> accountList = wechatAccountService.getAccountList(companyUnit, pageInfo, account, null, isAdmin, isSiteAdmin);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(new Class[]{}, "user");
        return datagrid(accountList, pageInfo.getTotalCount(), jsonConfig);
    }


    public Result saveAccount() {
    	
    	

        SysUser user = getLoginUser();
        SysUnit companyUnit = getCompanyUnit();
        Date createTime = new Date();

        WechatAccount wechatAccount = new WechatAccount();
        wechatAccount.setId(id);
        wechatAccount.setAccount(account);
        wechatAccount.setAppId(appId);
        wechatAccount.setAppSecret(appSecret);
        wechatAccount.setOriginalId(originalId);
        wechatAccount.setMchId(mchId);
        wechatAccount.setMchKey(mchKey);
        wechatAccount.setDefaultTplId(defaultTplId);
        wechatAccount.setCompanyUnit(companyUnit);
        wechatAccount.setTokenExpired(createTime);
        wechatAccount.setUser(user);
        wechatAccount.setValidFlag(true);
        wechatAccount.setCreateTime(createTime);

        wechatAccountService.updateWechatAccount(wechatAccount);
        
        if (wechatAccount.getId() != null) {
        	 simpleResult(map, true, "");
        } else {
        	 simpleResult(map, false, "");
        }
       
        

        return jsonResult(map);
    }

    @AjaxCheck
    public Result changeValid() {
        WechatAccount wechatAccount = wechatAccountService.get(id);
        wechatAccount.setValidFlag(validFlag);
        wechatAccountService.updateWechatAccount(wechatAccount);
        simpleResult(map, true, "");
        return jsonResult(map);
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

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getAppId() {
        return appId;
    }

    public void setAppId(String appId) {
        this.appId = appId;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public void setAppSecret(String appSecret) {
        this.appSecret = appSecret;
    }

    public Boolean getValidFlag() {
        return validFlag;
    }

    public void setValidFlag(Boolean validFlag) {
        this.validFlag = validFlag;
    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

    public String getOriginalId() {
        return originalId;
    }

    public void setOriginalId(String originalId) {
        this.originalId = originalId;
    }

	public String getMchKey() {
		return mchKey;
	}

	public void setMchKey(String mchKey) {
		this.mchKey = mchKey;
	}

	public String getMchId() {
		return mchId;
	}

	public void setMchId(String mchId) {
		this.mchId = mchId;
	}

	public String getDefaultTplId() {
		return defaultTplId;
	}

	public void setDefaultTplId(String defaultTplId) {
		this.defaultTplId = defaultTplId;
	}
}
