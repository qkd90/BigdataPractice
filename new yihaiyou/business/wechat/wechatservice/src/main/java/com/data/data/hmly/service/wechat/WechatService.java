package com.data.data.hmly.service.wechat;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.entity.UserType;
import com.data.data.hmly.service.lxbcommon.CouponMgrService;
import com.data.data.hmly.service.lxbcommon.UserCouponMgrService;
import com.data.data.hmly.service.wechat.dao.WechatAccountDao;
import com.data.data.hmly.service.wechat.dao.WechatAccountMenuDao;
import com.data.data.hmly.service.wechat.dao.WechatFollowerDao;
import com.data.data.hmly.service.wechat.dao.WechatReceiveMsgLogDao;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.data.data.hmly.service.wechat.entity.WechatAccountMenu;
import com.data.data.hmly.service.wechat.entity.WechatFollower;
import com.data.data.hmly.service.wechat.entity.WechatReceiveMsgLog;
import com.data.data.hmly.service.wechat.entity.WechatResource;
import com.data.data.hmly.service.wechat.entity.enums.NoticeType;
import com.data.data.hmly.service.wechat.entity.enums.ResType;
import com.gson.WeChat;
import com.gson.bean.Articles;
import com.gson.bean.NewsOutMessage;
import com.gson.bean.OutMessage;
import com.gson.bean.TemplateData;
import com.gson.bean.TextOutMessage;
import com.gson.bean.UserInfo;
import com.gson.inf.SendAllMsgTypes;
import com.gson.oauth.CustomService;
import com.gson.oauth.Menu;
import com.gson.oauth.Message;
import com.gson.oauth.Qrcode;
import com.gson.oauth.User;
import com.zuipin.util.DateUtils;
import com.zuipin.util.GlobalTheadPool;
import com.zuipin.util.PropertiesManager;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

@Service
public class WechatService {
    @Resource
    private WechatAccountDao wechatAccountDao;
    @Resource
    private MemberService memberService;
    @Resource
    private WechatReceiveMsgLogDao wechatReceiveMsgLogDao;
    @Resource
    private WechatAccountMenuDao wechatAccountMenuDao;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private WechatFollowerDao wechatFollowerDao;
    @Resource
    private CouponMgrService couponMgrService;
    @Resource
    private UserCouponMgrService userCouponMgrService;
    private static final Log LOG = LogFactory.getLog(Menu.class);

    public Map<String, Object> doCheckWechatFistIsSub(String openId, WechatAccount wechatAccount) {
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            String token = doGetTokenBy(wechatAccount);
            UserInfo userInfo = com.gson.oauth.User.getUserInfo(token, openId);
            if (userInfo != null) {
                List<WechatReceiveMsgLog> wechatReceiveMsgLogs = wechatReceiveMsgLogDao.findMsgLogByOpenId(openId);
                if (wechatReceiveMsgLogs.isEmpty()) {
                    Member user = memberService.findByUnionId(userInfo.getUnionid());
                    if (user == null) {   //如果为空的话，说明是首次关注，并在这里新增用户数据
                        user = new Member();
                        SysSite sysSite = new SysSite();
                        sysSite.setId(-1l);
                        SysUnit sysUnit = new SysUnit();
                        sysUnit.setId(-1l);
                        sysUnit.setSysSite(sysSite);
                        if (com.zuipin.util.StringUtils.isNotBlank(userInfo.getUnionid())) {
                            user.setAccount(userInfo.getUnionid());
                        } else {
                            user.setAccount(userInfo.getOpenid());
                        }
                        user.setBalance(0D);
                        user.setCreatedTime(new Date());
                        user.setUserType(UserType.USER);
                        user.setStatus(UserStatus.activity);
                        user.setSysSite(sysUnit.getSysSite());
                        user.setUnionId(userInfo.getUnionid());
                        if (com.zuipin.util.StringUtils.isNotBlank(userInfo.getNickname())) {
                            String nickName = userInfo.getNickname().replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
                            user.setUserName(nickName);
                            user.setNickName(nickName);
                        }
                        user.setHead(userInfo.getHeadimgurl());
                        memberService.save(user);
                        userCouponMgrService.saveWechatUserCoupon(user);
                        map.put("success", true);
                    } else {
                        userCouponMgrService.saveWechatUserCoupon(user);
                        map.put("success", true);
                    }
                } else {
                    map.put("success", false);
                }
//                Member user = memberService.findByUnionId(userInfo.getUnionid());
//                if (user == null) {   //如果为空的话，说明是首次关注，并在这里新增用户数据
//                    user = new Member();
//                    SysSite sysSite = new SysSite();
//                    sysSite.setId(-1l);
//                    SysUnit sysUnit = new SysUnit();
//                    sysUnit.setId(-1l);
//                    sysUnit.setSysSite(sysSite);
//                    if (com.zuipin.util.StringUtils.isNotBlank(userInfo.getUnionid())) {
//                        user.setAccount(userInfo.getUnionid());
//                    } else {
//                        user.setAccount(userInfo.getOpenid());
//                    }
//                    user.setBalance(0D);
//                    user.setCreatedTime(new Date());
//                    user.setUserType(UserType.USER);
//                    user.setStatus(UserStatus.activity);
//                    user.setSysSite(sysUnit.getSysSite());
//                    user.setUnionId(userInfo.getUnionid());
//
//                    if (com.zuipin.util.StringUtils.isNotBlank(userInfo.getNickname())) {
//                        String nickName = userInfo.getNickname().replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
//                        user.setUserName(nickName);
//                        user.setNickName(nickName);
//                    }
//                    user.setHead(userInfo.getHeadimgurl());
//
//                    memberService.save(user);
//                    userCouponMgrService.saveWechatUserCoupon(user);
//                    map.put("success", true);
//                } else {
//                    List<WechatReceiveMsgLog> wechatReceiveMsgLogs = wechatReceiveMsgLogDao.findMsgLogByOpenId(openId);
//                    if (wechatReceiveMsgLogs.isEmpty()) {
//                        userCouponMgrService.saveWechatUserCoupon(user);
//                        map.put("success", true);
//                    } else {
//                        map.put("success", false);
//                    }
//                }
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        return map;
    }


    /**
     * 根据公众号信息标识获取token，如果token为空或者token过期重新获取token并返回最新token
     *
     * @param accountId
     * @return
     * @throws Exception
     * @author caiys
     * @date 2015年11月20日 上午10:41:43
     */
    public String doGetTokenBy(Long accountId) throws Exception {
        WechatAccount wechatAccount = wechatAccountDao.load(accountId);
        return doGetTokenBy(wechatAccount);
    }

    /**
     * 根据公众号微信号获取token，如果token为空或者token过期重新 获取token并返回最新token
     *
     * @return
     * @throws Exception
     * @author caiys
     * @date 2015年11月20日 上午10:41:43
     */
    public String doGetTokenBy(String account) throws Exception {
        WechatAccount wechatAccount = wechatAccountDao.findUniqueBy(account,
                null, true);
        return doGetTokenBy(wechatAccount);
    }


    public String doGetTicketBy(Long accountId) throws Exception {
        WechatAccount wechatAccount = wechatAccountDao.get(accountId);
        return doGetTicketBy(wechatAccount);
    }


    public String doGetAccountInfo(WechatAccount wechatAccount) {
        try {
            String token = doGetTokenBy(wechatAccount);


        } catch (Exception e) {
            System.out.println(e);
        }

        return null;
    }


    /**
     * 根据公众号信息获取token，如果token为空或者token过期重新获取token并返回最新token
     *
     * @param wechatAccount
     * @return
     * @throws Exception
     * @author caiys
     * @date 2015年11月20日 上午10:41:43
     */
    public String doGetTokenBy(WechatAccount wechatAccount) throws Exception {
        if (wechatAccount != null && wechatAccount.getValidFlag()) {
            // 判断是否是有效token
            Date nowDate = new Date();
            if (StringUtils.isBlank(wechatAccount.getToken())
                    || wechatAccount.getTokenExpired() == null
                    || wechatAccount.getTokenExpired().before(nowDate)) {
                Map<String, Object> map = null;
                for (int i = 0; i < 2; i++) { // 如果失败尝试第二次获取
                    map = WeChat.getAccessToken(wechatAccount.getAppId(),
                            wechatAccount.getAppSecret());
                    if (StringUtils
                            .isNotBlank((String) map.get("access_token"))) {
                        break;
                    }
                }
                String token = null;
                Date tokenExpired = null;
                if (map != null) {
                    token = (String) map.get("access_token");
                    if (map.get("expires_in") != null) {
                        tokenExpired = DateUtils.add(nowDate, Calendar.SECOND,
                                (Integer) map.get("expires_in"));
                    }
                }
                // 更新最新token
                wechatAccount.setToken(token);
                wechatAccount.setTokenExpired(tokenExpired);
                wechatAccountDao.save(wechatAccount);
            }
            return wechatAccount.getToken();
        }
        return null;
    }

    private String doGetTicketBy(WechatAccount wechatAccount) throws Exception {
        String token = doGetTokenBy(wechatAccount);
        if (token == null) {
            return null;
        }
        if (wechatAccount != null && wechatAccount.getValidFlag()) {
            // 判断是否是有效ticket
            Date nowDate = new Date();
            if (StringUtils.isBlank(wechatAccount.getJsapiTicket())
                    || wechatAccount.getTicketExpired() == null
                    || wechatAccount.getTicketExpired().before(nowDate)) {
                Map<String, Object> map = null;
                for (int i = 0; i < 2; i++) { // 如果失败尝试第二次获取
                    map = WeChat.getTicket(token);
                    if (StringUtils
                            .isNotBlank((String) map.get("ticket"))) {
                        break;
                    }
                }
                String ticket = null;
                Date ticketExpired = null;
                if (map != null) {
                    ticket = (String) map.get("ticket");
                    if (map.get("expires_in") != null) {
                        ticketExpired = DateUtils.add(nowDate, Calendar.SECOND,
                                (Integer) map.get("expires_in"));
                    }
                }
                // 更新最新token
                wechatAccount.setJsapiTicket(ticket);
                wechatAccount.setTicketExpired(ticketExpired);
                wechatAccountDao.save(wechatAccount);
            }
            return wechatAccount.getJsapiTicket();
        }
        return null;
    }

    /**
     * 同步微信菜单
     *
     * @param accountId
     * @return 成功为true，否则为false
     * @throws Exception
     * @author caiys
     * @date 2015年11月20日 下午1:38:30
     */
    @Transactional
    public boolean doSyncWechatMenu(Long accountId) throws Exception {
        List<WechatAccountMenu> childrenLvl1 = wechatAccountMenuDao
                .findChildren(accountId, null);
        if (childrenLvl1.size() <= 0) {
            throw new Exception("微信菜单信息不完整");
        }
        // 查找账户对应站点的域名

        // 遍历一级菜单
        JSONArray menuLvl1s = new JSONArray();
        for (WechatAccountMenu childLvl1 : childrenLvl1) {
            JSONObject menuLvl1 = new JSONObject();
            menuLvl1.put("name", childLvl1.getMenuName());
            List<WechatAccountMenu> childrenLvl2 = wechatAccountMenuDao
                    .findChildren(accountId, childLvl1.getId());
            if (childrenLvl2.size() > 0) { // 包含二级菜单
                JSONArray menuLvl2s = new JSONArray();
                for (WechatAccountMenu childLvl2 : childrenLvl2) { // 遍历二级菜单
                    JSONObject menuLvl2 = new JSONObject();
                    menuLvl2.put("name", childLvl2.getMenuName());
                    // String siteurl =
                    // childLvl2.getCompanyUnit().getSysSite().getSiteurl();
                    buildMenu(childLvl2, accountId, menuLvl2);
                    menuLvl2s.add(menuLvl2);
                }
                menuLvl1.put("sub_button", menuLvl2s);
            } else {
                // String siteurl =
                // childLvl1.getCompanyUnit().getSysSite().getSiteurl();
                buildMenu(childLvl1, accountId, menuLvl1);
            }
            menuLvl1s.add(menuLvl1);
        }
        JSONObject json = new JSONObject();
        json.put("button", menuLvl1s);
        Menu menu = new Menu();
        String accessToken = doGetTokenBy(accountId);
        LOG.info(json.toString());
        return menu.createMenu(accessToken, json.toJSONString());
    }

    /**
     * 同步微信菜单（线程）
     *
     * @param accountId
     * @return 成功为true，否则为false
     * @throws Exception
     * @author caiys
     * @date 2015年11月20日 下午1:38:30
     */
    // @Transactional(propagation=Propagation.REQUIRES_NEW)
    public void doSyncWechatMenuThread(final Long accountId) throws Exception {
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    doSyncWechatMenu(accountId);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    /**
     * 获取在线状态客服，返回一个完整客服帐号，格式为：帐号前缀@公众号微信号
     */
    public String doGetOnlineKf(final Long accountId) {
        try {
            String accessToken = doGetTokenBy(accountId);
            CustomService customService = new CustomService();
            return customService.getOnlineKf(accessToken);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 发消息（给普通用户）
     *
     */
    public void doCustomSend(final Long accountId, final OutMessage outMessage) {
        GlobalTheadPool.instance.submit(new Callable<Object>() {
            @Override
            public Object call() throws Exception {
                try {
                    JSONObject json = buildCustomSendMsg(outMessage);
                    CustomService customService = new CustomService();
                    String accessToken = doGetTokenBy(accountId);
                    LOG.info(json.toString());
                    customService.customSend(accessToken, json.toJSONString());
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return null;
            }
        });
    }

    /**
     * 构建发送消息内容
     * @param outMessage
     * @return
     */
    public JSONObject buildCustomSendMsg(OutMessage outMessage) {
        JSONObject json = new JSONObject();
        json.put("touser", outMessage.getToUserName());
        if (outMessage instanceof TextOutMessage) {
            TextOutMessage textOutMessage = (TextOutMessage) outMessage;
            json.put("msgtype", textOutMessage.getMsgType());
            JSONObject textJson = new JSONObject();
            textJson.put("content", textOutMessage.getContent());
            json.put("text", textJson);
        } else if (outMessage instanceof NewsOutMessage) {
            NewsOutMessage newsOutMessage = (NewsOutMessage) outMessage;
            json.put("msgtype", newsOutMessage.getMsgType());
            JSONArray articlesJson = new JSONArray();
            for (Articles article : newsOutMessage.getArticles()) {
                JSONObject articleJson = new JSONObject();
                articleJson.put("title", article.getTitle());
                articleJson.put("description", article.getDescription());
                articleJson.put("url", article.getUrl());
                articleJson.put("picurl", article.getPicUrl());
                articlesJson.add(articleJson);
            }
            JSONObject newsJson = new JSONObject();
            newsJson.put("articles", articlesJson);
            json.put("news", newsJson);
        } else {
            return null;
        }
        return json;
    }

    /**
     * 创建带参数永久二维码，返回二维码url地址
     *
     * @param accountId
     * @param sceneStr  参数
     * @return
     * @throws Exception
     * @author caiys
     * @date 2015年11月25日 上午11:32:44
     */
    public String doCreateLimitQrcodeUrl(Long accountId, String sceneStr)
            throws Exception {
        Qrcode qrcode = new Qrcode();
        String accessToken = doGetTokenBy(accountId);
        JSONObject jsonObject = qrcode.createLimitScene(accessToken, sceneStr);
        if (jsonObject != null) {
            String ticket = (String) jsonObject.get("ticket");
            return Qrcode.showqrcodeUrl(ticket);
        }
        return null;
    }

    /**
     * 根据菜单构建
     *
     * @return
     * @throws Exception
     * @author caiys
     * @date 2015年11月20日 下午2:46:10
     */
    private void buildMenu(WechatAccountMenu wechatAccountMenu, Long accountId,
                           JSONObject jsonObject) throws Exception {
        if (StringUtils.isNotBlank(wechatAccountMenu.getUrl())) { // 外部链接不为空，优先设置跳转到外部链接
            jsonObject.put("type", ResType.view);
            jsonObject.put("url", wechatAccountMenu.getUrl());
            return;
        }
        if (wechatAccountMenu.getWechatAccount() != null) {
            WechatResource resource = wechatAccountMenu.getWechatResource();
            if (resource != null) {
                ResType resType = resource.getResType();
                String content = resource.getContent();
                String siteurl = propertiesManager.getString("WEIXIN_DOMAIN");
                if (resType == ResType.click) {
                    jsonObject.put("type", ResType.click);
                    jsonObject.put("key", content);
                } else {
                    jsonObject.put("type", ResType.view);
                    jsonObject.put("url", siteurl + "/wx/" + String.valueOf(accountId) + content);
                }
            }
            return;
        }
        throw new Exception("微信一级菜单【" + wechatAccountMenu.getMenuName()
                + "】信息不完整");
    }

    /**
     * 推送模板消息
     *
     * @param accountId
     * @param noticeType 通知类型
     * @param content    通知内容
     * @param url        点击跳转url
     * @throws Exception
     * @author caiys
     * @date 2015年12月7日 下午8:31:54
     */
    @Transactional
    public boolean doSendTplMessage(Long accountId, String touserStr,
                                    NoticeType noticeType, String content, String url) throws Exception {
        return doSendTplMessage(accountId, touserStr, noticeType.getDescription(), content, url);
    }
    
    @Transactional
    public boolean doSendTplMessage(Long accountId, String touserStr,
                                    String title, String content, String url) throws Exception {
        if (StringUtils.isNotBlank(touserStr)) {
            WechatAccount wechatAccount = wechatAccountDao.load(accountId);
            Message message = new Message();
            String accessToken = doGetTokenBy(accountId);
            String[] touserArray = touserStr.split(",");
            for (String touser : touserArray) {
                TemplateData data = new TemplateData(touser,
                        wechatAccount.getDefaultTplId(), url);
                data.push("first", content);
                data.push("keyword1", title);
                data.push("keyword2",
                        DateUtils.format(new Date(), "yyyy年MM月dd日 HH:mm"));
                // data.push("remark", "其他说明");
                JSONObject jsonObject = message.templateSend(accessToken, data);
                if (jsonObject != null) {
                    Integer errcode = jsonObject.getInteger("errcode");
                    Long msgid = jsonObject.getLong("msgid");
                    boolean success = errcode == 0;
                    // 预先保存模版消息发送任务完成后，微信服务器事件推送的记录
                    WechatReceiveMsgLog wechatReceiveMsgLog = new WechatReceiveMsgLog();
                    wechatReceiveMsgLog.setMsgId(msgid);
                    wechatReceiveMsgLog.setCreateTime(new Date());
                    wechatReceiveMsgLog.setStatus(success);
                    if (!success) {
                        String errmsg = jsonObject.getString("errmsg");
                        wechatReceiveMsgLog.setException(errmsg);
                        System.err.println(errmsg);
                    }
                    wechatReceiveMsgLogDao.save(wechatReceiveMsgLog);
                }
            }
            return true;
        }
        return false;
    }

    /**
     * 群发文本消息
     *
     * @param accountId
     * @param content
     * @return
     * @throws Exception
     * @author caiys
     * @date 2015年12月8日 上午10:44:41
     */
    public boolean doMassSendallText(Long accountId, String content)
            throws Exception {
        Message message = new Message();
        String accessToken = doGetTokenBy(accountId);
        JSONObject jsonObject = message.massSendall(accessToken,
                SendAllMsgTypes.TEXT, content, null, null, null, null, true);
        if (jsonObject != null) {
            Integer errcode = jsonObject.getInteger("errcode");
            Long msgid = jsonObject.getLong("msg_id");
            boolean success = errcode == 0;
            // 预先保存模版消息发送任务完成后，微信服务器事件推送的记录
            WechatReceiveMsgLog wechatReceiveMsgLog = new WechatReceiveMsgLog();
            wechatReceiveMsgLog.setMsgId(msgid);
            wechatReceiveMsgLog.setCreateTime(new Date());
            wechatReceiveMsgLogDao.save(wechatReceiveMsgLog);
            return success;
        }
        return false;
    }

    /**
     * 链接中添加参数
     *
     * @param param
     * @return
     * @author caiys
     * @date 2015年11月24日 下午12:00:13
     */
    public String appendParam(String content, String param) {
        if (StringUtils.isNotBlank(content) && StringUtils.isNotBlank(param)) {
            if (content.indexOf("?") > -1) {
                content = content + "&accountId=" + param;
            } else {
                content = content + "?accountId=" + param;
            }
        }
        return content;
    }

    /**
     * 获取关注用户列表
     *
     * @param accountId 公众号id
     * @return 关注用户信息类列表
     * @author huangpeijie
     * @date 2015年12月8日11:33:12
     */
    public List<WechatFollower> doGetFollower(Long accountId) throws Exception {
        String token = doGetTokenBy(accountId);
        User user = new User();
        String nextOpenid = "";
        JSONObject followers = new JSONObject();
        List<String> followersOpenId = new ArrayList<String>();
        List<UserInfo> userInfoList = new ArrayList<UserInfo>();
        List<WechatFollower> followerList = new ArrayList<WechatFollower>();
        WechatAccount account = wechatAccountDao.get(accountId);
        int i = 0;
        do {
            followers = user.getFollwersList(token, nextOpenid);
            List<String> list = JSONArray.parseArray(followers.getJSONObject("data")
                    .getJSONArray("openid").toJSONString(), String.class);
            followersOpenId.addAll(list);
            nextOpenid = followers.getString("next_openid");
            i++;
        } while (followers.getInteger("total") > i * 10000);
        wechatFollowerDao.deleteFollowerList(account);
        for (int j = 0; j < followersOpenId.size(); j += 100) {
            List<String> openIdList = new ArrayList<String>();
            List<WechatFollower> tempFollowerList = new ArrayList<WechatFollower>();
            if (j + 100 < followersOpenId.size())
                openIdList = followersOpenId.subList(j, j + 100);
            else
                openIdList = followersOpenId.subList(j, followersOpenId.size());
            userInfoList = user.getUserInfoList(token, openIdList);
            for (UserInfo info : userInfoList) {
                WechatFollower follower = new WechatFollower();
                follower.setOpenId(info.getOpenid());
                follower.setSubscribe(info.getSubscribe());
                follower.setNickName(info.getNickname());
                follower.setSex(info.getSex());
                follower.setCity(info.getCity());
                follower.setProvince(info.getProvince());
                follower.setCountry(info.getCountry());
                follower.setLanguage(info.getLanguage());
                follower.setHeadImgUrl(info.getHeadimgurl());
                follower.setSubscribeTime(new Date(info.getSubscribe_time() * 1000));
                follower.setUnionId(info.getUnionid());
                follower.setRemark(info.getRemark());
                follower.setGroupId(info.getGroupId());
                follower.setCreateTime(new Date());
                follower.setFollowAccount(account);
                tempFollowerList.add(follower);
                followerList.add(follower);
            }
            wechatFollowerDao.save(tempFollowerList);
        }


        return followerList;
    }

    public WechatAccount getWechatAccount(Long accountId) {
        WechatAccount wechatAccount = wechatAccountDao.get(accountId);
        return wechatAccount;
    }
}
