package com.data.data.hmly.action.customerBalance;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.enums.ThirdPartyUserType;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.PayManager;
import com.data.data.hmly.service.balance.AccountLogService;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.AccountLog;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.enums.OrderPayType;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.pay.PayService;
import com.data.data.hmly.service.pay.WxService;
import com.data.data.hmly.service.user.ThirdPartyUserService;
import com.data.data.hmly.service.user.entity.ThirdPartyUser;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import javax.security.auth.login.LoginException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/9/7.
 */
public class CustomerBalanceAction extends FrameBaseAction {

    @Resource
    private OrderService orderService;
    @Resource
    private WechatAccountService wechatAccountService = SpringContextHolder.getBean("wechatAccountService");
    @Resource
    private WxService wxService;
    @Resource
    private ThirdPartyUserService thirdPartyUserService;
    @Resource
    private PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
    @Resource
    private BalanceService balanceService;
    @Resource
    private AccountLogService accountLogService;
    @Resource
    private MemberService memberService;

    private Order order = new Order();

    private Integer page = 1;
    private Integer pageSize = 10;

    private Long orderId;
    public static WechatAccount wechatAccount;
    public static String filePath;
    private Map<String, Object> map = new HashMap<String, Object>();
    private Long id;
    private Member member = new Member();
    private AccountLog accountLog = new AccountLog();
    public Result customerBalanceManage() {
        return dispatch();
    }


    public Result getAccountLogList() {
        List<AccountLog> accountLogs = Lists.newArrayList();
        if (accountLog.getAccountUser().getId() == null) {
            return datagrid(accountLogs);
        }
        Page pageInfo = new Page(page, pageSize);
        accountLogs = accountLogService.listAccountLog(accountLog, pageInfo, "createTime", "desc");
        return datagrid(accountLogs, pageInfo.getTotalCount(), JsonFilter.getIncludeConfig("accountUser", "user"));
    }

    /**
     * 游客充值
     * @return
     */
    public Result doCustomerRecharge() {
        if (id == null) {
            simpleResult(map, false, "充值失败！");
            return json(JSONObject.fromObject(result));
        }
        if (member.getBalance() ==  null || !(member.getBalance() > 0)) {
            simpleResult(map, false, "充值金额不能为负值！");
            return json(JSONObject.fromObject(result));
        }
        balanceService.updateBalanceMember(member.getBalance(), AccountType.recharge, id, getLoginUser().getId(), DateUtils.format(new Date(), "YYYYMMDDHHmmss"), null, OrderType.recharge, null);
        simpleResult(map, true, "充值成功！");
        return json(JSONObject.fromObject(map));
    }

    public Result withdrawList() {
        Page pageInfo = new Page(page, pageSize);
        order.setOrderType(OrderType.withdraw);
        List<Order> orderList = orderService.getWithdrawList(order, pageInfo);

        String[] includes = new String[]{"user", "orderDetails"};
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(includes);
        return datagrid(orderList, pageInfo.getTotalCount(), jsonConfig);
    }

    public Result closeTransers() {
        if (orderId != null) {
            order = orderService.get(orderId);
            order.setStatus(OrderStatus.CLOSED);
            orderService.update(order);
            simpleResult(map, true, "操作成功");
        } else {
            simpleResult(map, false, "操作失败");
        }
        return jsonResult(map);
    }

    public Result doTransfers() throws LoginException {

//        propertiesManager = SpringContextHolder.getBean("propertiesManager");
//        wechatAccountService = SpringContextHolder.getBean("wechatAccountService");
        String accountId = propertiesManager.getString("WX_COMPANY_ID");
        wechatAccount = wechatAccountService.get(Long.parseLong(accountId));
        filePath = propertiesManager.getString("CERT_DIR");
        String errorCode = "";
        boolean flag = true;
        if (orderId == null) {
            simpleResult(map, false, "订单编号不能为空！");
            return jsonResult(map);
        }
        Order order = orderService.get(orderId);
        User member = order.getUser();
        PayManager payManager = PayManager.findService(OrderPayType.WECHAT.name());
        PayService payService = payManager.getPayService();
        ThirdPartyUser thirdUser = thirdPartyUserService.getByUserIdAndType(member.getId(), ThirdPartyUserType.weixin, wechatAccount.getId());
        String ip = getRequest().getHeader("x-real-ip");
        if (!StringUtils.isNotBlank(ip))
            ip = getRequest().getRemoteAddr();
        Map resultMap = payService.doTransfersRequest(order, wechatAccount, thirdUser.getOpenId(), ip, filePath);
        if (Boolean.valueOf(resultMap.get("success").toString())) {
            order.setStatus(OrderStatus.SUCCESS);
            order.setRemark("提现成功！");
        } else {
            order.setStatus(OrderStatus.FAILED);
            /*if (WeChatErroCode.equalCode(resultMap.get("errCode").toString())) {
                balanceService.doSendWXMsg(wechatAccount.getAccount() + "企业付款余额不足", wechatAccount);
                errorCode = WeChatErroCode.decodeErrorCode(resultMap.get("errCode").toString());
                order.setRemark(errorCode);
                flag = false;
            }*/
            order.setRemark(errorCode);
        }
        balanceService.savePayResult(order, AccountType.withdraw);
        map.put("orderId", order.getId());
        simpleResult(map, flag, errorCode);
        return jsonResult(map);
    }


    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public static WechatAccount getWechatAccount() {
        return wechatAccount;
    }

    public static void setWechatAccount(WechatAccount wechatAccount) {
        CustomerBalanceAction.wechatAccount = wechatAccount;
    }

    public static String getFilePath() {
        return filePath;
    }

    public static void setFilePath(String filePath) {
        CustomerBalanceAction.filePath = filePath;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public AccountLog getAccountLog() {
        return accountLog;
    }

    public void setAccountLog(AccountLog accountLog) {
        this.accountLog = accountLog;
    }
}
