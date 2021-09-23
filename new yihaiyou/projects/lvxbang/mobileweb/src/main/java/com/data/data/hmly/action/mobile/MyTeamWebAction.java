package com.data.data.hmly.action.mobile;

import com.data.data.hmly.service.UserRelationService;
import com.data.data.hmly.service.UserShareRecordService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.ShareType;
import com.data.data.hmly.service.lvxbang.MyTeamService;
import com.data.data.hmly.service.pay.WxService;
import com.data.data.hmly.service.wechat.WechatService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caiys on 2016/7/18.
 */
public class MyTeamWebAction extends MobileBaseAction {
    @Resource
    private UserRelationService userRelationService;
    @Resource
    private WxService wxService;
    @Resource
    private MyTeamService myTeamService;
    @Resource
    private WechatService wechatService;
    @Resource
    private UserShareRecordService userShareRecordService;
    @Resource
    private PropertiesManager propertiesManager;

    private Member          member;
    private Integer pageNo;
    private Integer pageSize;

    @AjaxCheck
    public Result login() throws LoginException {
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 我的团队数
     * @return
     */
    @AjaxCheck
    public Result countTeam() {
        String userIdStr = (String) getParameter("userId");
        Long userId = 100803L;  // TODO 测试时使用
        member = (Member) getSession().getAttribute(UserConstants.CURRENT_LOGIN_USER);
        if (member != null) {
            userId = member.getId();
        }
        if (StringUtils.isNotBlank(userIdStr)) {
            userId = Long.valueOf(userIdStr);
        }

        Long firstCount = userRelationService.countChildren(userId, 1);
        Long secondCount = userRelationService.countChildren(userId, 2);
        Long thirdCount = userRelationService.countChildren(userId, 3);

        result.put("success", true);
        result.put("firstCount", firstCount);
        result.put("secondCount", secondCount);
        result.put("thirdCount", thirdCount);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 我的团队列表
     * @return
     */
    @AjaxCheck
    public Result listTeam() {
        String userIdStr = (String) getParameter("userId");
        String levelStr = (String) getParameter("level");
        Long userId = 100803L;  // TODO 测试时使用
        member = (Member) getSession().getAttribute(UserConstants.CURRENT_LOGIN_USER);
        if (member != null) {
            userId = member.getId();
        }
        if (StringUtils.isNotBlank(userIdStr)) {
            userId = Long.valueOf(userIdStr);
        }

        Page page = new Page(pageNo, pageSize);
        List<Member> list = userRelationService.listChildren(userId, Integer.valueOf(levelStr), page);
        List<Member> teamList = new ArrayList<Member>();
        for (Member m : list) {
            Long firstCount = userRelationService.countChildren(m.getId(), 1);
            Long secondCount = userRelationService.countChildren(m.getId(), 2);
            Long thirdCount = userRelationService.countChildren(m.getId(), 3);
            Member mvo = new Member();
            mvo.setTeamCount1(firstCount);
            mvo.setTeamCount2(secondCount);
            mvo.setTeamCount3(thirdCount);
            mvo.setTeamCount(firstCount + secondCount + thirdCount);
            mvo.setId(m.getId());
            mvo.setNickName(m.getNickName());
            mvo.setHead(m.getHead());
            teamList.add(mvo);
        }
        if (page.getPageIndex() >= page.getPageCount()) {
            result.put("nomore", true);
        } else {
            result.put("nomore", false);
        }
        result.put("success", true);
        result.put("teamList", teamList);
        return json(JSONObject.fromObject(result));
    }

    /**
     * 获取wx.config接口需要的信息
     * @return
     */
    @AjaxCheck
    public Result getShareConfig() {
        Long accountId = propertiesManager.getLong("WEBCHAT_ACCOUNT_ID");
        String url = (String) getParameter("url");
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap = wxService.doShareConfig(accountId, url);
            member = (Member) getSession().getAttribute(UserConstants.CURRENT_LOGIN_USER);
            resultMap.put("userId", member.getId());
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("success", false);
        }
        return json(JSONObject.fromObject(resultMap));
    }

    /**
     * 点击分享链接页面
     * @return
     */
    public Result doDistributeShare() {
        String pid = (String) getParameter("pid");
        Long accountId = propertiesManager.getLong("WEBCHAT_ACCOUNT_ID");
        member = (Member) getSession().getAttribute(UserConstants.CURRENT_LOGIN_USER);
        Map<String, Object> map = myTeamService.doSubscribe(accountId, member, Long.valueOf(pid));
        // 获取二维码（数据库要先维护）
        if (!(Boolean) map.get("subscribe")) {
            WechatAccount wechatAccount = wechatService.getWechatAccount(accountId);
            map.put("qrcode", wechatAccount.getQrcode());
        }
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }

    /**
     * 分享成功时记录
     * @return
     */
    public Result doShareSuccess() {
        String shareTypeStr = (String) getParameter("shareType");
        member = (Member) getSession().getAttribute(UserConstants.CURRENT_LOGIN_USER);
        userShareRecordService.doShareSuccess(member.getId(), ShareType.valueOf(shareTypeStr));
        return text("success");
    }

    public Integer getPageNo() {
        return pageNo;
    }

    public void setPageNo(Integer pageNo) {
        this.pageNo = pageNo;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
