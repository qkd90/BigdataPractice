package com.data.data.hmly.action.wechat;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.lang.StringUtils;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.wechat.WechatDataImgTextService;
import com.data.data.hmly.service.wechat.WechatDataItemService;
import com.data.data.hmly.service.wechat.WechatDataTextService;
import com.data.data.hmly.service.wechat.WechatReplyRuleService;
import com.data.data.hmly.service.wechat.WechatService;
import com.data.data.hmly.service.wechat.entity.WechatDataItem;
import com.data.data.hmly.service.wechat.entity.WechatDataNews;
import com.data.data.hmly.service.wechat.entity.WechatDataText;
import com.data.data.hmly.service.wechat.entity.WechatReplyRule;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.gson.inf.MsgTypes;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;

/**
 * Created by vacuity on 15/11/20.
 */
public class WechatDataAction extends FrameBaseAction {
    private static final long serialVersionUID = -617072372295001263L;
    @Resource
    private WechatService wechatService;
    @Resource
    private WechatDataItemService dataItemService;
    @Resource
    private WechatDataTextService dataTextService;
    @Resource
    private WechatDataImgTextService dataNewsService;
    @Resource
    private SysUnitService unitService;
    @Resource
    private WechatReplyRuleService ruleService;

    private WechatDataText dataText;

    private WechatDataNews dataNews = new WechatDataNews();
    private List<WechatDataNews> dataNewsList = new ArrayList<WechatDataNews>();
    private Integer index;

    private String editJson;
    private String reply;
    private Map<String, Object> map = new HashMap<String, Object>();
    private Integer page = 1;
    private Integer rows = 10;

    @AjaxCheck
    public Result selectText() {

        String textId = (String) getParameter("textId");
        NewsDataGrid dataGrid = new NewsDataGrid();
        JSONObject json = new JSONObject();
        List<WechatDataText> dataTexts = new ArrayList<WechatDataText>();
        if (!StringUtils.isEmpty(textId)) {

            dataText = dataTextService.getTextById(Long.parseLong(textId));
            dataGrid.setId(dataText.getDataItem().getId());
            dataTexts.add(dataText);
            dataGrid.setTextList(dataTexts);

        }

        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("textList");
        if (dataGrid.getId() != null) {
            json = JSONObject.fromObject(dataGrid, jsonConfig);
            json.put("success", true);
        } else {
            json.put("success", false);
        }
        return json(json);

    }


    @AjaxCheck
    public Result selectNews() {

        String itemId = (String) getParameter("itemId");
        NewsDataGrid dataGrid = new NewsDataGrid();
        JSONObject json = new JSONObject();
        if (!StringUtils.isEmpty(itemId)) {

            WechatDataItem dataItem = dataItemService.load(Long.parseLong(itemId));
            dataNewsList = dataNewsService.findNewsListByItem(dataItem);
            dataGrid.setId(dataItem.getId());
            dataGrid.setNewsList(dataNewsList);

        }

        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("newsList");
        if (dataGrid.getId() != null) {
            json = JSONObject.fromObject(dataGrid, jsonConfig);
            json.put("success", true);
        } else {
            json.put("success", false);
        }
        return json(json);

    }

    @AjaxCheck
    public Result addNews() {

        String itemId = (String) getParameter("itemId");
        String replyStr = (String) getParameter("reply");

        if (!StringUtils.isEmpty(replyStr)) {
            reply = replyStr;
        }

        if (!StringUtils.isEmpty(itemId)) {
            WechatDataItem dataItem = dataItemService.load(Long.parseLong(itemId));

			/*{"index":"1","id":"","userId":"","itemId":"","title":"","author":"","img_path":"",
				"is_checked":"","abstractText":"","content":"","url":""}*/

            dataNewsList = dataNewsService.findNewsListByItem(dataItem);


            StringBuilder sb = new StringBuilder();
            String tempStr = "";
            JSONObject obj = new JSONObject();

            int i = 1;
            sb.append("[");
            for (WechatDataNews news : dataNewsList) {
                obj.put("index", i);
                obj.put("id", news.getId());
                obj.put("userId", news.getUser().getId());
                obj.put("itemId", news.getDataItem().getId());
                obj.put("title", news.getTitle());
                obj.put("category", news.getCategory().getId());
                obj.put("author", news.getAuthor());
                obj.put("img_path", news.getImg_path());
                obj.put("is_checked", news.getIsChecked());
                obj.put("abstractText", news.getAbstractText());
                obj.put("content", news.getContent());
                obj.put("url", news.getUrl());
//				sb.append(obj.toJSONString()+",");
                tempStr = tempStr + obj.toString() + ",";
                i++;
            }
            tempStr = tempStr.substring(0, tempStr.length() - 1);
            sb.append(tempStr);
            sb.append("]");

            System.out.println(sb.toString());

            editJson = sb.toString();

        }

        return dispatch();
    }

    @AjaxCheck
    public Result editNews() {
        String indexStr = (String) getParameter("index");

        if (!StringUtils.isEmpty(indexStr)) {
            index = Integer.parseInt(indexStr);
        }

        dataNews.setCreateTime(new Date());
        dataNews.setUpdateTime(new Date());

//		dataNewsService.save(dataNews);

        return dispatch();
    }

    @AjaxCheck
    public Result addText() {

        String textId = (String) getParameter("textId");
        String replyStr = (String) getParameter("reply");

        if (!StringUtils.isEmpty(textId)) {
            dataText = dataTextService.getTextById(Long.parseLong(textId));
            String content = dataText.getContent();
            if (StringUtils.isNotBlank(content)) {
                content = content.replaceAll("\n", "<br />\r\n");
            }
            dataText.setContent(content);
        }
        if (!StringUtils.isEmpty(replyStr)) {
            reply = replyStr;
        }

        return dispatch();
    }

    @AjaxCheck
    public Result manage() {
        return dispatch();
    }

    @AjaxCheck
    public Result delItemAndNews() {
        String item = (String) getParameter("itemId");
        if (!StringUtils.isEmpty(item)) {
            WechatDataItem dataItem = dataItemService.load(Long.parseLong(item));
            dataNewsService.delNewsByItem(dataItem);
            dataItemService.delItem(dataItem);

        }
        simpleResult(map, true, "");

        return jsonResult(map);
    }

    @AjaxCheck
    public Result checkItiem() {
        String textId = (String) getParameter("textId");
        String itemId = (String) getParameter("itemId");
        if (!StringUtils.isEmpty(textId)) {

            WechatReplyRule replyRule = new WechatReplyRule();
            WechatDataText dataText = dataTextService.getTextById(Long.parseLong(textId));

//			WechatDataItem dataItem =dataItemService.getItemByTextId(Long.parseLong(textId));

            boolean flag = ruleService.checkRuleHasItem(dataText.getDataItem());
            if (flag) {
//				dataTextService.delTextById(Long.parseLong(textId));
                simpleResult(map, true, "");
            } else {
                simpleResult(map, false, "");
            }

        }

        if (org.apache.commons.lang3.StringUtils.isNotBlank(itemId)) {
            WechatDataItem dataItem = dataItemService.load(Long.parseLong(itemId));
            boolean flag = ruleService.checkRuleHasItem(dataItem);
            if (flag) {
//				dataTextService.delTextById(Long.parseLong(textId));
                simpleResult(map, true, "");
            } else {
                simpleResult(map, false, "");
            }
        }

        return jsonResult(map);
    }

    @AjaxCheck
    public Result delItemAndText() {
        String textId = (String) getParameter("textId");
        if (!StringUtils.isEmpty(textId)) {
            dataTextService.delTextById(Long.parseLong(textId));

        }
        simpleResult(map, true, "");

        return jsonResult(map);
    }

    @AjaxCheck
    public Result dataGrid() {

            String type = (String) getParameter("type");

        String keyword = (String) getParameter("keyword");
        Page pageInfo = new Page(page, rows);
        if (!StringUtils.isEmpty(type)) {
            if ("text".equals(type)) {
                List<WechatDataText> dataTexts = null;
                if (isSupperAdmin()) {
                    dataTexts = dataTextService.getTextList(MsgTypes.text, keyword, pageInfo);
                } else if (isSiteAdmin()) {
                    SysUnit sysUnit = getLoginUser().getSysUnit();
                    SysSite site = getSite();
                    List<SysUnit> sysUnList = unitService.getUnitBySite(site);

                    dataTexts = dataTextService.getTextList(MsgTypes.text, sysUnList, keyword, pageInfo);
                } else if (isCommpanyAdmin()) {
                    dataTexts = dataTextService.getTextList(MsgTypes.text, keyword, getCompanyUnit(), getLoginUser(), pageInfo);
                }


                return datagrid(dataTexts, pageInfo.getTotalCount());
            } else if ("news".equals(type)) {
                List<NewsDataGrid> dataGrids = new ArrayList<NewsDataGrid>();
                if (!StringUtils.isEmpty(keyword)) {

                    List<WechatDataItem> dataItems = new ArrayList<WechatDataItem>();

                    if (isSupperAdmin()) {
                        dataItems = dataItemService.findListByKeyword(MsgTypes.news, keyword, pageInfo);
                    } else if (isSiteAdmin()) {
                        SysUnit sysUnit = getLoginUser().getSysUnit();
                        SysSite site = getSite();
                        List<SysUnit> sysUnList = unitService.getUnitBySite(site);

                        dataItems = dataItemService.findListByKeywordByUlist(MsgTypes.news, keyword, sysUnList, pageInfo);
                    } else {
                        dataItems = dataItemService.findListByKeyword(getCompanyUnit(), MsgTypes.news, keyword, pageInfo, getLoginUser());
                    }


                    for (WechatDataItem item : dataItems) {
                        NewsDataGrid dataGrid = new NewsDataGrid();
                        dataGrid.setId(item.getId());
                        dataGrid.setNewsList(dataNewsService.findNewsListByItem(item));
                        List<WechatDataNews> newsList = dataNewsService.findNewsListByItem(item);

                        for (WechatDataNews news : newsList) {
                            if (news.getImg_path() != null) {
                                dataGrid.setImgUrl(news.getImg_path());
                                break;
                            }
                        }
                        dataGrid.setUpdateTime(item.getUpdateTime());

                        dataGrids.add(dataGrid);

                    }


                } else {

                    List<WechatDataItem> dataItems = new ArrayList<WechatDataItem>();

                    if (isSupperAdmin()) {
                        dataItems = dataItemService.findListByKeyword(MsgTypes.news, keyword, pageInfo);
                    } else if (isSiteAdmin()) {
                        SysUnit sysUnit = getLoginUser().getSysUnit();
                        SysSite site = getSite();
                        List<SysUnit> sysUnList = unitService.getUnitBySite(site);
                        dataItems = dataItemService.findListByKeywordByUlist(MsgTypes.news, keyword, sysUnList, pageInfo);
                    } else {
                        dataItems = dataItemService.findListByKeyword(getCompanyUnit(), MsgTypes.news, keyword, pageInfo, getLoginUser());
                    }

//					List<WechatDataItem> dataItems = dataItemService.findList(getCompanyUnit(),MsgTypes.news,pageInfo);


                    for (WechatDataItem item : dataItems) {
                        NewsDataGrid dataGrid = new NewsDataGrid();
                        dataGrid.setId(item.getId());
                        List<WechatDataNews> newsList = dataNewsService.findNewsListByItem(item);
                        dataGrid.setNewsList(newsList);


                        for (WechatDataNews news : newsList) {
                            if (news.getImg_path() != null) {
                                dataGrid.setImgUrl(news.getImg_path());
                                break;
                            }
                        }
                        dataGrid.setUpdateTime(item.getUpdateTime());

                        dataGrids.add(dataGrid);

                    }

                }

//				List<WechatDataNews> newsList = dataNewsService.findList(getLoginUser(),keyword,pageInfo);
                String[] includeConfig = new String[]{"newsList"};
                JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, includeConfig);
                return datagrid(dataGrids, pageInfo.getTotalCount(), jsonConfig);
            }
        }
        return null;


    }

    public Map<String, Object> getMap() {
        return map;
    }

    public void setMap(Map<String, Object> map) {
        this.map = map;
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

    public WechatDataText getDataText() {
        return dataText;
    }

    public void setDataText(WechatDataText dataText) {
        this.dataText = dataText;
    }

    public Integer getIndex() {
        return index;
    }

    public void setIndex(Integer index) {
        this.index = index;
    }

    public WechatDataNews getDataNews() {
        return dataNews;
    }

    public void setDataNews(WechatDataNews dataNews) {
        this.dataNews = dataNews;
    }

    public List<WechatDataNews> getDataNewsList() {
        return dataNewsList;
    }

    public void setDataNewsList(List<WechatDataNews> dataNewsList) {
        this.dataNewsList = dataNewsList;
    }

    public String getEditJson() {
        return editJson;
    }

    public void setEditJson(String editJson) {
        this.editJson = editJson;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }
}
