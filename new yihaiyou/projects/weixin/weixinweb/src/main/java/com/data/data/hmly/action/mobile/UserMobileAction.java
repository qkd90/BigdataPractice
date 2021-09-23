package com.data.data.hmly.action.mobile;

import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.UserRelationService;
import com.data.data.hmly.service.UserService;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.entity.UserRelation;
import com.data.data.hmly.service.mobile.MobileService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.pay.WxService;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.WechatFollowerService;
import com.data.data.hmly.service.wechat.entity.WechatFollower;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.*;

/**
 * Created by vacuity on 15/11/23.
 */
public class UserMobileAction extends JxmallAction {

    @Resource
    private UserRelationService userRelationService;
    @Resource
    private MobileService mobileService;
    @Resource
    private WxService wxService;
    @Resource
    private WechatAccountService wechatAccountService;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private OrderService orderService;
    @Resource
    private WechatFollowerService wechatFollowerService;
    @Resource
    private UserService userService;

    private Long accountId;
    private Long parentId;
    private String accountName;
    private String url;
    private String qrcode;
    private String weixinDomain;

    private User user = new User();
    private WechatFollower wechatFollower = new WechatFollower();
    private WechatFollower parentFollower = new WechatFollower();

    private int firstCount;
    private int secondCount;
    private int thirdCount;

    private List<User> firstChildren = new ArrayList<User>();
    private List<User> secondChildren = new ArrayList<User>();
    private List<User> thirdChildren = new ArrayList<User>();

    public Result home() {
        weixinDomain = propertiesManager.getString("WEIXIN_DOMAIN");
        accountId = Long.parseLong(getSession().getAttribute(UserConstans.CURRENT_VIEW_ACCOUNTID).toString());
        user = (User) getSession().getAttribute(UserConstans.CURRENT_LOGIN_USER);
        user = userService.get(user.getId());
        wechatFollower = wechatFollowerService.getByOpenId(user.getAccount(), accountId);
        if (user.getParent() != null) {
        	parentFollower = wechatFollowerService.getByOpenId(user.getParent().getAccount(), accountId);
        }
        return dispatch();
    }

    public Result team() {
        user = (User) getSession().getAttribute(UserConstans.CURRENT_LOGIN_USER);
        long userId = user.getId();
        firstChildren = userRelationService.getChild(userId, 1);
        firstCount = firstChildren.size();
        secondChildren = userRelationService.getChild(userId, 2);
        secondCount = secondChildren.size();
        thirdChildren = userRelationService.getChild(userId, 3);
        thirdCount = thirdChildren.size();

        List<Long> childIds = new ArrayList<Long>();
        for (User user : firstChildren) {
            childIds.add(user.getId());
        }
        for (User user : secondChildren) {
            childIds.add(user.getId());
        }
        for (User user : thirdChildren) {
            childIds.add(user.getId());
        }
        List<UserRelation> userRelations = new ArrayList<UserRelation>();
        if (!childIds.isEmpty()) {
            userRelations = userRelationService.getAllChild(childIds);
        }
        Map<String, Integer> map = new HashMap<String, Integer>();
        for (UserRelation userRelation : userRelations) {
            String key = userRelation.getParentUser().getId() + "_" + userRelation.getLevel();
            if (map.get(key) == null) {
                map.put(key, 1);
            } else {
                int count = map.get(key);
                count += 1;
                map.put(key, count);
            }
        }

        for (User user : firstChildren) {
            fillUserLevelCount(map, user);
        }
        for (User user : secondChildren) {
            fillUserLevelCount(map, user);
        }
        for (User user : thirdChildren) {
            fillUserLevelCount(map, user);

        }
        return dispatch();
    }

    public Result doShare() {
        weixinDomain = propertiesManager.getString("WEIXIN_DOMAIN");
        accountId = Long.parseLong(getSession().getAttribute(UserConstans.CURRENT_VIEW_ACCOUNTID).toString());
        accountName = wechatAccountService.get(accountId).getAccount();
        qrcode = mobileService.doGetQrCode(accountId);
        return dispatch();
    }

    public Result getShareConfig() {
        accountId = Long.parseLong(getSession().getAttribute(UserConstans.CURRENT_VIEW_ACCOUNTID).toString());
//        String domain = propertiesManager.getString("WEIXIN_DOMAIN");
        try {
            url = URLDecoder.decode(url, "UTF-8");
        } catch (UnsupportedEncodingException e1) {
            e1.printStackTrace();
        }
//        url = domain+"/mobile/user/home.jhtml?accountId="+accountId;
        Map<String, Object> resultMap = new HashMap<String, Object>();
        try {
            resultMap = wxService.doShareConfig(accountId, url);
            resultMap.put("success", true);
        } catch (Exception e) {
            resultMap.put("success", false);
        }

        return json(JSONObject.fromObject(resultMap));
    }

    public Result doSubscribe() {
        accountId = Long.parseLong(getSession().getAttribute(UserConstans.CURRENT_VIEW_ACCOUNTID).toString());
        user = (User) getSession().getAttribute(UserConstans.CURRENT_LOGIN_USER);
        Map<String, Object> map = mobileService.doSubscribe(accountId, user, parentId);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }

    private void fillUserLevelCount(Map<String, Integer> map, User user) {
        String key = user.getId() + "_";
        if (map.get(key + 1) == null) {
            user.setOneLevelCount(0);
        } else {
            user.setOneLevelCount(map.get(key + 1));
        }
        if (map.get(key + 2) == null) {
            user.setTwoLevelCount(0);
        } else {
            user.setTwoLevelCount(map.get(key + 2));
        }
        if (map.get(key + 3) == null) {
            user.setThreeLevelCount(0);
        } else {
            user.setThreeLevelCount(map.get(key + 3));
        }
        user.setTotalLevelCount(user.getOneLevelCount() + user.getTwoLevelCount() + user.getThreeLevelCount());
    }


    public Result order() {
        return dispatch();
    }

    public Result myOrders() {
        user = (User) getSession().getAttribute(UserConstans.CURRENT_LOGIN_USER);
        Long userId = user.getId();
        List<Order> orderList = orderService.myOrder(userId);

        List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
        for (Order order : orderList) {
            Map<String, Object> orderMap = new HashMap<String, Object>();
            //UNCONFIRMED("等待确认"),WAIT("等待支付"),PAYED("已支付"), REFUND("已退款"), DELETED("已删除"), CLOSED("已关闭"), INVALID("无效订单");
            String status = "";
            if (order.getStatus() == OrderStatus.PAYED) {
                status = "已支付";
            } else if (order.getStatus() == OrderStatus.WAIT) {
                status = "等待支付";
            } else if (order.getStatus() == OrderStatus.CLOSED) {
                status = "已关闭";
            } else if (order.getStatus() == OrderStatus.UNCONFIRMED) {
                status = "等待确认";
            } else if (order.getStatus() == OrderStatus.REFUND) {
                status = "已退款";
            } else if (order.getStatus() == OrderStatus.INVALID) {
                status = "无效订单";
            }
            List<OrderDetail> orderDetailLsit = order.getOrderDetails();
            Float fee = 0F;
            String orderName = null;
            Date playDate = null;
            for (OrderDetail orderDetail : orderDetailLsit) {
                fee += orderDetail.getFinalPrice();
                if (!StringUtils.isNotBlank(orderName) && StringUtils.isNotBlank(orderDetail.getProduct().getName())) {
                    orderName = orderDetail.getProduct().getName();
                }
                if (playDate == null && orderDetail.getPlayDate() != null) {
                    playDate = orderDetail.getPlayDate();
                }
            }

            String dateString = playDate.toString().substring(0, 10);
            String orderDateString = order.getCreateTime().toString().substring(0, 19);

            orderMap.put("id", order.getId());
            orderMap.put("name", orderName);
            orderMap.put("status", status);
            orderMap.put("fee", fee);
            orderMap.put("count", orderDetailLsit.size());
            orderMap.put("date", dateString);
            orderMap.put("orderDate", orderDateString);

            mapList.add(orderMap);
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, null);
        JSONArray jsonArray = JSONArray.fromObject(mapList, jsonConfig);

        return json(jsonArray);
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public WechatFollower getWechatFollower() {
        return wechatFollower;
    }

    public void setWechatFollower(WechatFollower wechatFollower) {
        this.wechatFollower = wechatFollower;
    }

    public WechatFollower getParentFollower() {
        return parentFollower;
    }

    public void setParentFollower(WechatFollower parentFollower) {
        this.parentFollower = parentFollower;
    }

    public int getFirstCount() {
        return firstCount;
    }

    public void setFirstCount(int firstCount) {
        this.firstCount = firstCount;
    }

    public int getSecondCount() {
        return secondCount;
    }

    public void setSecondCount(int secondCount) {
        this.secondCount = secondCount;
    }

    public int getThirdCount() {
        return thirdCount;
    }

    public void setThirdCount(int thirdCount) {
        this.thirdCount = thirdCount;
    }

    public List<User> getFirstChildren() {
        return firstChildren;
    }

    public void setFirstChildren(List<User> firstChildren) {
        this.firstChildren = firstChildren;
    }

    public List<User> getSecondChildren() {
        return secondChildren;
    }

    public void setSecondChildren(List<User> secondChildren) {
        this.secondChildren = secondChildren;
    }

    public List<User> getThirdChildren() {
        return thirdChildren;
    }

    public void setThirdChildren(List<User> thirdChildren) {
        this.thirdChildren = thirdChildren;
    }

    public Long getAccountId() {
        return accountId;
    }

    public void setAccountId(Long accountId) {
        this.accountId = accountId;
    }

    public Long getParentId() {
        return parentId;
    }

    public void setParentId(Long parentId) {
        this.parentId = parentId;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getQrcode() {
        return qrcode;
    }

    public void setQrcode(String qrcode) {
        this.qrcode = qrcode;
    }

    public String getWeixinDomain() {
        return weixinDomain;
    }

    public void setWeixinDomain(String weixinDomain) {
        this.weixinDomain = weixinDomain;
    }
}
