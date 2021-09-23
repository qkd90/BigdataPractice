package com.data.data.hmly.action.wechat;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang3.StringUtils;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.WechatDataImgTextService;
import com.data.data.hmly.service.wechat.WechatDataItemService;
import com.data.data.hmly.service.wechat.WechatDataTextService;
import com.data.data.hmly.service.wechat.WechatReplyKeywordService;
import com.data.data.hmly.service.wechat.WechatReplyRuleService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.data.data.hmly.service.wechat.entity.WechatDataItem;
import com.data.data.hmly.service.wechat.entity.WechatDataNews;
import com.data.data.hmly.service.wechat.entity.WechatDataText;
import com.data.data.hmly.service.wechat.entity.WechatReplyKeyword;
import com.data.data.hmly.service.wechat.entity.WechatReplyRule;
import com.data.data.hmly.service.wechat.entity.enums.MatchType;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.gson.inf.MsgTypes;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;

/**
 * Created by vacuity on 15/11/20.
 */
public class WechatAutoReplyAction extends FrameBaseAction {
	private static final long serialVersionUID = -617072372295001263L;
	@Resource
    private WechatDataItemService dataItemService;
	@Resource
    private WechatReplyKeywordService keywordService;
	@Resource
    private WechatAccountService accountService;
	@Resource
    private WechatReplyRuleService ruleService;
	@Resource
    private SysUnitService unitService;
	@Resource
    private WechatDataTextService textService;
	@Resource
    private WechatDataImgTextService newsService;
    private Map<String, Object> map = new HashMap<String, Object>();
    private List<WechatAccount> accountList = new ArrayList<WechatAccount>();
    private Integer index;
    private String keyword;
    private Integer key_index;
    private String rulesId;
    private Integer			page				= 1;
   	private Integer			rows				= 10;
    
    
	@AjaxCheck
    public Result ruleDataList() {
		
		List<WechatReplyRule> replyRules = new ArrayList<WechatReplyRule>();
		List<RuleData> ruleDataList = new ArrayList<RuleData>();
		String[] includeConfig = null;
		Page pageInfo = new Page(page, rows);

		String ruleName = (String) getParameter("ruleName");
		String accountId = (String) getParameter("accountId");
		WechatReplyRule replyRule  = new WechatReplyRule();
		if (StringUtils.isNotBlank(accountId)) {
			replyRule.setWechatAccount(accountService.get(Long.parseLong(accountId)));
		}
		replyRule.setName(ruleName);
		
		if (isSupperAdmin()) {
			replyRules = ruleService.findRuleList(pageInfo, replyRule);
		} else if (isSiteAdmin()) {
			SysUnit sysUnit = getLoginUser().getSysUnit();
			SysSite site = getSite();
			List<SysUnit> sysUnList = unitService.getUnitBySite(site); 
			replyRules = ruleService.findRuleList(sysUnList, pageInfo, replyRule);
		} else if (isCommpanyAdmin()) {
			replyRules = ruleService.findRuleList(getCompanyUnit(), getLoginUser().getId(), pageInfo, replyRule);
		}
		
		
		for (WechatReplyRule rule : replyRules) {
			RuleData ruleData  = new RuleData();
			ruleData.setId(rule.getId());
			ruleData.setCreateTime(rule.getCreateTime());
			ruleData.setUpdateTime(rule.getModifyTime());
			ruleData.setName(rule.getName());
			ruleData.setAccount(rule.getWechatAccount());
			
			
			List<WechatDataText> textList = new ArrayList<WechatDataText>();
			List<WechatDataNews> newsList = new ArrayList<WechatDataNews>();
			List<String> replyList = new ArrayList<String>();
			List<WechatReplyKeyword> keywordList = new ArrayList<WechatReplyKeyword>();
			
			if (rule.getDataItem() != null) {
				ruleData.setItemId(rule.getDataItem().getId());
				ruleData.setMsgTypes(rule.getDataItem().getType());
				if (rule.getDataItem().getType() == MsgTypes.news) {
					newsList = newsService.findByItemId(rule.getDataItem().getId());
				} else if (rule.getDataItem().getType() == MsgTypes.text) {
					textList = textService.findByItemId(rule.getDataItem().getId());
				}
				
			}
			
			
			ruleData.setNewsList(newsList);
			ruleData.setTextList(textList);
			
			ruleData.setReplyTitle(replyList);
			
			keywordList = keywordService.findByRuleId(rule.getId());
			
			ruleData.setKeywordList(keywordList);
			
			
			ruleDataList.add(ruleData);
		}
		
		includeConfig = new String[]{"newsList", "textList", "account", "keywordList"};
		JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, includeConfig);
		
		return datagrid(ruleDataList, pageInfo.getTotalCount(), jsonConfig);
	}
    
	@AjaxCheck
    public Result automanage() {
		
		
		
		return dispatch();
	}
	@AjaxCheck
    public Result delRule() {
		String ruleId = (String) getParameter("ruleId");
//		JSONArray jsonArr = new JSONArray();
		if (!StringUtils.isEmpty(ruleId)) {
			keywordService.delKeywordByRule(ruleService.load(Long.parseLong(ruleId)));
			ruleService.deletById(Long.parseLong(ruleId));
		}
		simpleResult(map, true, "");
		return jsonResult(map);
	}
	@AjaxCheck
    public Result findkeywordList() {
		String ruleId = (String) getParameter("ruleId");
		JSONArray jsonArr = new JSONArray();
		if (!StringUtils.isEmpty(ruleId)) {
			List<WechatReplyKeyword> keywordList = keywordService.findByRuleId(Long.parseLong(ruleId));
			JsonConfig jsonConfig = JsonFilter.getIncludeConfig("");
			jsonArr = JSONArray.fromObject(keywordList, jsonConfig);
		}
		return json(jsonArr);
	}
	@AjaxCheck
    public Result findText() {
		
		String itemId = (String) getParameter("itemId");
		JSONArray jsonArr = new JSONArray();
		if (!StringUtils.isEmpty(itemId)) {
			List<WechatDataText> textList = textService.findByItemId(Long.parseLong(itemId));
			JsonConfig jsonConfig = JsonFilter.getIncludeConfig("dataItem");
			jsonArr = JSONArray.fromObject(textList, jsonConfig);
		}
		
		return json(jsonArr);
	}
	@AjaxCheck
    public Result findNews() {
		String itemId = (String) getParameter("itemId");
		JSONArray jsonArr = new JSONArray();
		if (!StringUtils.isEmpty(itemId)) {
			List<WechatDataNews> newsList = newsService.findByItemId(Long.parseLong(itemId));
			JsonConfig jsonConfig = JsonFilter.getIncludeConfig("dataItem");
			jsonArr = JSONArray.fromObject(newsList, jsonConfig);
		}
		return json(jsonArr);
	}

	@AjaxCheck
    public Result findRuleById() {
		
		String ruleId = (String) getParameter("ruleId");
		WechatReplyRule replyRule = null;
		
		JSONObject json = new JSONObject();
		if (!StringUtils.isEmpty(ruleId)) {
			replyRule = ruleService.load(Long.parseLong(ruleId));
			
			JsonConfig jsonConfig = JsonFilter.getIncludeConfig("dataItem", "wechatAccount");
			json = JSONObject.fromObject(replyRule, jsonConfig);
			json.put("success", true);
		} else {
			json.put("success", false);
		}
		
		return json(json);
	}
	@AjaxCheck
    public Result autoReplyManage() {
				
		return dispatch();
    }
	
	@AjaxCheck
    public Result submitKeyword() {
		
		String data = (String) getParameter("data");
		
		if (!StringUtils.isEmpty(data)) {
			
			JSONObject obj = JSONObject.fromObject(data);
			
			String ruleIdStr = obj.getString("ruleId");
			
			if ("undefined".equals(ruleIdStr)) {
				WechatReplyRule replyRule = new WechatReplyRule();
				String itemId = obj.get("itmeId").toString();
				WechatDataItem dataItem = null;
				if (!"undefined".equals(itemId) && !"".equals(itemId)) {
					dataItem = dataItemService.getItemById(Long.parseLong(itemId));
					replyRule.setDataItem(dataItem);
				}
				
				String accId = obj.get("accId").toString();
				WechatAccount account = null;
				if (!"undefined".equals(accId) && !"".equals(accId)) {
					account = accountService.load(Long.parseLong(accId));
					replyRule.setWechatAccount(account);
				}
				replyRule.setName(obj.get("ruleName").toString());
				replyRule.setSysUnit(getCompanyUnit());
				
				ruleService.save(replyRule, getLoginUser());
				JSONArray jsonArray = obj.getJSONArray("keyArry");
				for (int i = 0; i < jsonArray.size(); i++) {
		            JSONObject jo = (JSONObject) jsonArray.get(i);
		            WechatReplyKeyword keyword = new WechatReplyKeyword();
		            keyword.setKeyword(jo.getString("keyword"));
		            if ("explicit".equals(jo.getString("pipei"))) {
		            	keyword.setMatchType(MatchType.explicit);
		            } else {
		            	keyword.setMatchType(MatchType.implicit);
		            }
		            keyword.setReplyRule(replyRule);
		            keyword.setSysUnit(getCompanyUnit());
		            keyword.setUserId(getLoginUser().getId());
		            if (account != null) {
		            	 keyword.setWechatAccount(account);
		            }
		           
		            
		            keywordService.save(keyword);

				}
				
			} else {
				WechatReplyRule replyRule = ruleService.load(Long.parseLong(ruleIdStr));
				
				String itemId = obj.get("itmeId").toString();
				WechatDataItem dataItem = null;
				if (!"undefined".equals(itemId) && !"".equals(itemId)) {
					dataItem = dataItemService.getItemById(Long.parseLong(itemId));
					replyRule.setDataItem(dataItem);
				} else {
					replyRule.setDataItem(dataItem);
				}
				
				String accId = obj.get("accId").toString();
				WechatAccount account = null;
				if (!"undefined".equals(accId) && !"".equals(accId)) {
					account = accountService.load(Long.parseLong(accId));
					replyRule.setWechatAccount(account);
				}
				replyRule.setName(obj.get("ruleName").toString());
				replyRule.setSysUnit(getCompanyUnit());
				ruleService.update(replyRule, getLoginUser());
				JSONArray jsonArray = obj.getJSONArray("keyArry");
				
				keywordService.delKeywordByRule(replyRule);
				for (int i = 0; i < jsonArray.size(); i++) {
		            JSONObject jo = (JSONObject) jsonArray.get(i);
		            WechatReplyKeyword keyword = new WechatReplyKeyword();
		            keyword.setKeyword(jo.getString("keyword"));
		            if ("explicit".equals(jo.getString("pipei"))) {
		            	keyword.setMatchType(MatchType.explicit);
		            } else {
		            	keyword.setMatchType(MatchType.implicit);
		            }
		            keyword.setReplyRule(replyRule);
		            keyword.setSysUnit(getCompanyUnit());
		            keyword.setUserId(getLoginUser().getId());
		            if (account != null) {
		            	 keyword.setWechatAccount(account);
		            }
		            
		            keywordService.save(keyword);

				}
				
			}
			
			
			
		}
		simpleResult(map, true, "");
		return jsonResult(map);
    }
	@AjaxCheck
    public Result selectAccountList() {
		
		Boolean  validFlag = true;
//		isCommpanyAdmin()
		if (isSupperAdmin()) {	//全局管理员
			accountList = accountService.getAccountListAll(validFlag);
		} else if (isSiteAdmin()) {	//站点管理员
			SysUnit sysUnit = getLoginUser().getSysUnit();
			SysSite site = getSite();
			List<SysUnit> sysUnList = unitService.getUnitBySite(site); 
			accountList = accountService.getAccountListAll(sysUnList, validFlag);
		} else if (isCommpanyAdmin()) {	//公司管理员
			accountList = accountService.getAccountListAll(getCompanyUnit(), validFlag);
		}
		
		JsonConfig jsonConfig = JsonFilter.getFilterConfig("companyUnit", "user");
		JSONArray json = JSONArray.fromObject(accountList, jsonConfig);
		return json(json);
    }
	
	
	@AjaxCheck
    public Result addTextJsp() {
		
		String indexStr = (String) getParameter("index");
		
		if (!StringUtils.isEmpty(indexStr)) {
			index = Integer.parseInt(indexStr);
		}
		
		return dispatch();
    }
	@AjaxCheck
    public Result addNewsJsp() {
		
		String indexStr = (String) getParameter("index");
		
		if (!StringUtils.isEmpty(indexStr)) {
			index = Integer.parseInt(indexStr);
		}
		
		return dispatch();
    }
	@AjaxCheck
    public Result editKeyword() throws UnsupportedEncodingException {
		
		String indexStr = (String) getParameter("index");
		String key_indexStr = (String) getParameter("key_index");
		
		String keywordStr = (String) getParameter("keyword");
		
		if (!StringUtils.isEmpty(keywordStr)) {
			keywordStr = new String(keywordStr.getBytes("iso8859_1"), "UTF-8");
			keyword = keywordStr;
		}
		if (!StringUtils.isEmpty(key_indexStr)) {
			key_index = Integer.parseInt(key_indexStr);
		}
		if (!StringUtils.isEmpty(indexStr)) {
			index = Integer.parseInt(indexStr);
		}
		
		return dispatch();
	}
	

	@AjaxCheck
    public Result addKeyword() {
		
		String indexStr = (String) getParameter("index");
		
		if (!StringUtils.isEmpty(indexStr)) {
			index = Integer.parseInt(indexStr);
		}
		
		return dispatch();
    }
    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
    }

	public Integer getIndex() {
		return index;
	}

	public void setIndex(Integer index) {
		this.index = index;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public Integer getKey_index() {
		return key_index;
	}
	public void setKey_index(Integer key_index) {
		this.key_index = key_index;
	}
	public List<WechatAccount> getAccountList() {
		return accountList;
	}
	public void setAccountList(List<WechatAccount> accountList) {
		this.accountList = accountList;
	}

	public String getRulesId() {
		return rulesId;
	}

	public void setRulesId(String rulesId) {
		this.rulesId = rulesId;
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
}
