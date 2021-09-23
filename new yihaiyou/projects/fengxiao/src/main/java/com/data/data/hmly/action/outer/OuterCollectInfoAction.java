package com.data.data.hmly.action.outer;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.outer.OuterCollectInfoService;
import com.data.data.hmly.service.outer.OuterQuestionService;
import com.data.data.hmly.service.outer.entity.OuterCollectInfo;
import com.data.data.hmly.service.outer.entity.OuterQuestion;
import com.framework.struts.AjaxCheck;
import com.framework.struts.NotNeedLogin;
import com.opensymphony.xwork2.Result;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caiys on 2016/11/29.
 */
@NotNeedLogin
public class OuterCollectInfoAction extends FrameBaseAction {
    @Resource
    private OuterQuestionService outerQuestionService;
    @Resource
    private OuterCollectInfoService outerCollectInfoService;
//    @Resource
//    private WxService wxService;
//    @Resource
//    private PropertiesManager propertiesManager;

    private List<OuterQuestion> questions;
    private List<OuterCollectInfo> collectInfos;
    private OuterCollectInfo outerCollectInfo = new OuterCollectInfo();
    private String checkValue;
    private String candidateIds;

    private final String sessionCollectInfoKey = "_outerCollectInfo";
    private Map<String, Object> resultMap = new HashMap<String, Object>();

    /**
     * 入口页面，初始化问题集
     * @return
     */
    public Result index() {
        return dispatch();
    }

    /**
     * 信息填写页面
     * @return
     */
    public Result info() {
        return dispatch();
    }

    /**
     * 问答页面
     * @return
     */
    public Result question() {
        if (StringUtils.isBlank(outerCollectInfo.getParticipator()) || StringUtils.isBlank(outerCollectInfo.getPhone())) {
            checkValue = "您所填写的不完整，请重新填写！";
            return dispatch("/WEB-INF/jsp/outer/outerCollectInfo/info.jsp");
        }
        boolean isExisted = outerCollectInfoService.check(outerCollectInfo.getPhone());
        if (isExisted) {
            checkValue = "您所填写的电话已经参与过了该活动，请重新填写！";
            return dispatch("/WEB-INF/jsp/outer/outerCollectInfo/info.jsp");
        }

        getSession().setAttribute(sessionCollectInfoKey, outerCollectInfo);
        questions = outerQuestionService.getInitQuestion();
        return dispatch();
    }

    /**
     * 完成页面
     * @return
     */
    public Result success() throws Exception {
//        Long accountId = propertiesManager.getLong("WEBCHAT_ACCOUNT_ID");
//        StringBuffer url = getRequest().getRequestURL();
//        resultMap = wxService.doShareConfig(accountId, url.toString());
        return dispatch();
    }

    /**
     * 完成页面
     * @return
     */
    @AjaxCheck
    public Result complete() {
        outerCollectInfo = (OuterCollectInfo) getSession().getAttribute(sessionCollectInfoKey);
        Map<String, Object> map = new HashMap<String, Object>();
        if (outerCollectInfo == null) {
            map.put("success", false);
            map.put("errorMsg", "会话已过期，请重新填写！");
            return jsonResult(map);
        }
        if (StringUtils.isBlank(candidateIds)
                || StringUtils.isBlank(outerCollectInfo.getParticipator()) || StringUtils.isBlank(outerCollectInfo.getPhone())) {
            map.put("success", false);
            map.put("errorMsg", "您所填写的不完整，请重新填写！");
            return jsonResult(map);
        }
        boolean isExisted = outerCollectInfoService.check(outerCollectInfo.getPhone());
        if (isExisted) {
            map.put("success", false);
            map.put("errorMsg", "您所填写的电话已经参与过了该活动，请重新填写！");
            return jsonResult(map);
        }
        outerCollectInfoService.saveCollectInfo(outerCollectInfo, candidateIds);
        getSession().removeAttribute(sessionCollectInfoKey);
        map.put("success", true);
        return jsonResult(map);
    }

    /**
     * 检查是否已经提交过
     * @return
     */
    @AjaxCheck
    public Result check() {
        Map<String, Object> map = new HashMap<String, Object>();
        if (StringUtils.isNotBlank(checkValue)) {
            boolean isExisted = outerCollectInfoService.check(checkValue);
            if (isExisted) {
                map.put("success", false);
                map.put("errorMsg", "您所填写的电话已经参与过了该活动，请重新填写！");
                return jsonResult(map);
            }
        }
        map.put("success", true);
        return jsonResult(map);
    }

    /**
     * 列表显示页面：全对的进入名单，按照提交速度排名，前50名获奖
     * @return
     */
    public Result winner() {
        collectInfos = outerCollectInfoService.list();
        return dispatch();
    }

    public List<OuterQuestion> getQuestions() {
        return questions;
    }

    public void setQuestions(List<OuterQuestion> questions) {
        this.questions = questions;
    }

    public String getCheckValue() {
        return checkValue;
    }

    public void setCheckValue(String checkValue) {
        this.checkValue = checkValue;
    }

    public OuterCollectInfo getOuterCollectInfo() {
        return outerCollectInfo;
    }

    public void setOuterCollectInfo(OuterCollectInfo outerCollectInfo) {
        this.outerCollectInfo = outerCollectInfo;
    }

    public String getCandidateIds() {
        return candidateIds;
    }

    public void setCandidateIds(String candidateIds) {
        this.candidateIds = candidateIds;
    }

    public List<OuterCollectInfo> getCollectInfos() {
        return collectInfos;
    }

    public void setCollectInfos(List<OuterCollectInfo> collectInfos) {
        this.collectInfos = collectInfos;
    }

    public Map<String, Object> getResultMap() {
        return resultMap;
    }

    public void setResultMap(Map<String, Object> resultMap) {
        this.resultMap = resultMap;
    }
}
