package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.action.other.util.VisitHistoryCookieUtils;
import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.lvxbang.SuggestService;
import com.data.data.hmly.service.other.OtherVisitHistoryService;
import com.data.data.hmly.service.other.entity.OtherVisitHistory;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.wechat.WechatDataImgTextService;
import com.data.data.solr.MulticoreSolrTemplate;
import com.data.ikanalysis.utils.IKTokenUtils;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.FileUtil;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.Group;
import org.apache.solr.client.solrj.response.GroupCommand;
import org.apache.solr.client.solrj.response.GroupResponse;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.apache.solr.common.params.GroupParams;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/12/16.
 */
public class IndexWebAction extends JxmallAction {

    private static final long serialVersionUID = 4657888842905628013L;
    @Resource
    private SuggestService suggestService;
    @Resource
    private OtherVisitHistoryService otherVisitHistoryService;
    @Resource
    private MulticoreSolrTemplate solrTemplate;
    @Resource
    private WechatDataImgTextService wechatDataImgTextService;

    // 页面数据
    private List<ScenicInfo> scenics;
    private List<OtherVisitHistory> visitHistorys;
    private List<OtherVisitHistory> visitCountTop;
    private Integer page = 1;
    private Integer rows = 10;
    private String keyword;
    private String type;
    private int allCount;
    private int planCount;
    private int recplanCount;
    private int scenicCount;
    private int hotelCount;
    private int delicacyCount;
    private int lineCount;
    private final Map<String, Object> map = new HashMap<String, Object>();

    public Result index() {
        setAttribute(LXBConstants.LVXBANG_INDEX_BANNER_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_INDEX_BANNER));
        setAttribute(LXBConstants.LVXBANG_INDEX_KEY, FileUtil.loadHTML(LXBConstants.LVXBANG_INDEX));
        return dispatch();
    }

    public Result mobile() {
        PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
        String pcUrl = propertiesManager.getString("MOBILE_PATH");
        return redirect(pcUrl);
    }

    public Result navigation() {
        setAttribute("NAVIGATION_CONTENT", wechatDataImgTextService.findNewsById(32).getContent());
        return dispatch();
    }

    /**
     * 猜你喜欢
     *
     * @return
     */
    public Result interest() {
        OtherVisitHistory ovh = new OtherVisitHistory();
        ovh.setResType(ProductType.scenic);
        String cookieId = VisitHistoryCookieUtils.getCookieId(getRequest(), getResponse());
        ovh.setCookieId(cookieId);
        ovh.setDeleteFlag(false);
        User user = getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
        if (user != null) {
            ovh.setUserId(user.getId());
        }
        visitCountTop = otherVisitHistoryService.findVisitScenicCountTop(ovh, 6);
        map.put("data", visitCountTop);
        simpleResult(map, true, "");
        JSONObject json = JSONObject.fromObject(map);
        return json(json);
    }

    /**
     * 历史浏览
     *
     * @return
     */
    public Result history() {
        OtherVisitHistory ovh = new OtherVisitHistory();
        ovh.setResType(ProductType.scenic);
        String cookieId = VisitHistoryCookieUtils.getCookieId(getRequest(), getResponse());
        ovh.setCookieId(cookieId);
        ovh.setDeleteFlag(false);
        User user = getSessionAttribute(UserConstans.CURRENT_LOGIN_USER);
        if (user != null) {
            ovh.setUserId(user.getId());
        }
        visitHistorys = otherVisitHistoryService.findOtherVisitHistoryTop(ovh, 6);
        map.put("data", visitHistorys);
        simpleResult(map, true, "");
        JSONObject json = JSONObject.fromObject(map);
        return json(json);
    }

    public Result suggestAll() {
        String name = getParameter("name").toString();
        return json(JSONArray.fromObject(suggestService.suggest(name, 10)));
    }

    public Result headerSearch() {
        if (StringUtils.isBlank(keyword)) {
            return dispatch();
        }

        String keywordToken = null;
        try {
            keywordToken = IKTokenUtils.token(keyword);
            StringBuilder sb = new StringBuilder();
            String[] arr = keywordToken.split(" +");
            if (arr.length > 0) {
                sb.append("(");
                for (String s : arr) {
                    sb.append("name:").append(s).append(" OR ");
                }
                sb.delete(sb.length() - 4, sb.length());
                sb.append(")");
            } else {
                sb.append("name:").append(keyword);
            }
            keywordToken = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        SolrQuery query = new SolrQuery();  // 建立一个新的查询
        StringBuilder conditionStr = new StringBuilder();
//        conditionStr.append("parent:true");
        conditionStr.append(keywordToken);
        conditionStr.append(" AND (type:").append(SolrType.hotel.toString())
                .append(" OR type:").append(SolrType.scenic_info.toString())
                .append(" OR type:").append(SolrType.recommend_plan.toString())
                .append(" OR type:").append(SolrType.plan.toString())
                .append(" OR type:").append(SolrType.delicacy.toString())
                .append(" OR type:").append(SolrType.line.toString()).append(")");
        query.setQuery(conditionStr.toString());
        query.setParam(GroupParams.GROUP, true);
        query.setShowDebugInfo(true);


        query.setParam(GroupParams.GROUP_FIELD, "type");
        QueryResponse qresponse = solrTemplate.query(query, SolrIndexService.CORE_NAME);
        GroupResponse groupResponse = qresponse.getGroupResponse();
        if (groupResponse != null) {
            List<GroupCommand> groupList = groupResponse.getValues();
            for (GroupCommand groupCommand : groupList) {
                List<Group> groups = groupCommand.getValues();
                for (Group group : groups) {
                    if (SolrType.recommend_plan.toString().equals(group.getGroupValue())) {
                        recplanCount = Long.valueOf(group.getResult().getNumFound()).intValue();
                    } else if (SolrType.scenic_info.toString().equals(group.getGroupValue())) {
                        scenicCount = Long.valueOf(group.getResult().getNumFound()).intValue();
                    } else if (SolrType.hotel.toString().equals(group.getGroupValue())) {
                        hotelCount = Long.valueOf(group.getResult().getNumFound()).intValue();
                    } else if (SolrType.delicacy.toString().equals(group.getGroupValue())) {
                        delicacyCount = Long.valueOf(group.getResult().getNumFound()).intValue();
                    } else if (SolrType.plan.toString().equals(group.getGroupValue())) {
                        planCount = Long.valueOf(group.getResult().getNumFound()).intValue();
                    } else if (SolrType.line.toString().equals(group.getGroupValue())) {
                        lineCount = Long.valueOf(group.getResult().getNumFound()).intValue();
                    }
                }
            }
        }
        allCount = planCount + recplanCount + scenicCount + hotelCount + delicacyCount + lineCount;
        return dispatch();
    }

    public Result searchPage() {
        if (StringUtils.isBlank(keyword)) {
            return null;
        }
        // 请求参数
        String solrType = (String) getParameter("solrType");
//        String keyword = (String) getParameter("keyword");

        String keywordToken = null;
        try {
            keywordToken = IKTokenUtils.token(keyword);
            StringBuilder sb = new StringBuilder();
            String[] arr = keywordToken.split(" +");
            sb.append("(");
            for (String s : arr) {
                sb.append("name:").append(s).append(" OR ");
            }
            sb.delete(sb.length() - 4, sb.length());
            sb.append(")");
            keywordToken = sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        Page pageInfo = new Page(page, rows);
        StringBuilder conditionStr = new StringBuilder();
//            conditionStr.append("parent:true");
        conditionStr.append(keywordToken);
        conditionStr.append(" AND (");
        if (SolrType.hotel.toString().equals(solrType)) {
            conditionStr.append("type:").append(SolrType.hotel.toString()).append(" OR ");
            /*HotelSearchRequest searchRequest = new HotelSearchRequest();
            searchRequest.setName(keywordToken);
            List<HotelSolrEntity> list = hotelService.listFromSolr(searchRequest, pageInfo);
            pageInfo.setData(list);
            JSONObject json = JSONObject.fromObject(pageInfo);
            return json(json);*/
        } else if (SolrType.scenic_info.toString().equals(solrType)) {
            conditionStr.append("type:").append(SolrType.scenic_info.toString()).append(" OR ");
            /*ScenicSearchRequest searchRequest = new ScenicSearchRequest();
            searchRequest.setName(keywordToken);
            List<ScenicSolrEntity> list = scenicInfoService.listFromSolr(searchRequest, pageInfo);
            pageInfo.setData(list);
            JSONObject json = JSONObject.fromObject(pageInfo);
            return json(json);*/
        } else if (SolrType.recommend_plan.toString().equals(solrType)) {
            conditionStr.append("type:").append(SolrType.recommend_plan.toString()).append(" OR ");
            /*RecommendPlanSearchRequest searchRequest = new RecommendPlanSearchRequest();
            searchRequest.setName(keywordToken);
            List<RecommendPlanSolrEntity> list = recommendPlanService.listFromSolr(searchRequest, pageInfo);
            pageInfo.setData(list);
            JSONObject json = JSONObject.fromObject(pageInfo);
            return json(json);*/
        } else if (SolrType.delicacy.toString().equals(solrType)) {
            conditionStr.append("type:").append(SolrType.delicacy.toString()).append(" OR ");
            /*DelicacySearchRequest searchRequest = new DelicacySearchRequest();
            searchRequest.setName(keywordToken);
            List<DelicacySolrEntity> list = delicacyService.listFromSolr(searchRequest, pageInfo);
            pageInfo.setData(list);
            JSONObject json = JSONObject.fromObject(pageInfo);
            return json(json);*/
        } else if (SolrType.plan.toString().equals(solrType)) {
            conditionStr.append("type:").append(SolrType.plan.toString()).append(" OR ");
        } else if (SolrType.line.toString().equals(solrType)) {
            conditionStr.append("type:").append(SolrType.line.toString()).append(" OR ");
        } else {
            conditionStr.append("type:").append(SolrType.hotel.toString())
                    .append(" OR type:").append(SolrType.scenic_info.toString())
                    .append(" OR type:").append(SolrType.recommend_plan.toString())
                    .append(" OR type:").append(SolrType.delicacy.toString())
                    .append(" OR type:").append(SolrType.plan.toString())
                    .append(" OR type:").append(SolrType.line.toString()).append(" OR ");
        }
        conditionStr.delete(conditionStr.length() - 4, conditionStr.length());
        conditionStr.append(")");
        SolrQuery query = new SolrQuery();  // 建立一个新的查询
        query.setQuery(conditionStr.toString());
        QueryResponse qresponse = solrTemplate.query(query, SolrIndexService.CORE_NAME, pageInfo);
        SolrDocumentList docs = qresponse.getResults();
        pageInfo.setData(docs);
        pageInfo.setTotalCount(Long.valueOf(docs.getNumFound()).intValue());
        JSONObject json = JSONObject.fromObject(pageInfo);
        return json(json);
    }

    public Result hongbao() {
        return dispatch();
    }

    public List<ScenicInfo> getScenics() {
        return scenics;
    }

    public void setScenics(List<ScenicInfo> scenics) {
        this.scenics = scenics;
    }

    public List<OtherVisitHistory> getVisitHistorys() {
        return visitHistorys;
    }

    public void setVisitHistorys(List<OtherVisitHistory> visitHistorys) {
        this.visitHistorys = visitHistorys;
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

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public int getAllCount() {
        return allCount;
    }


    public void setAllCount(int allCount) {
        this.allCount = allCount;
    }


    public int getPlanCount() {
        return planCount;
    }


    public void setPlanCount(int planCount) {
        this.planCount = planCount;
    }


    public int getRecplanCount() {
        return recplanCount;
    }


    public void setRecplanCount(int recplanCount) {
        this.recplanCount = recplanCount;
    }


    public int getScenicCount() {
        return scenicCount;
    }


    public void setScenicCount(int scenicCount) {
        this.scenicCount = scenicCount;
    }


    public int getHotelCount() {
        return hotelCount;
    }


    public void setHotelCount(int hotelCount) {
        this.hotelCount = hotelCount;
    }


    public int getDelicacyCount() {
        return delicacyCount;
    }


    public void setDelicacyCount(int delicacyCount) {
        this.delicacyCount = delicacyCount;
    }

    public List<OtherVisitHistory> getVisitCountTop() {
        return visitCountTop;
    }

    public void setVisitCountTop(List<OtherVisitHistory> visitCountTop) {
        this.visitCountTop = visitCountTop;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }
}
