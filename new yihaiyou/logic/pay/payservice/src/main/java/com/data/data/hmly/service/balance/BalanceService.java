package com.data.data.hmly.service.balance;

import com.data.data.hmly.service.PayManager;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.balance.dao.AccountLogDao;
import com.data.data.hmly.service.balance.entity.AccountLog;
import com.data.data.hmly.service.balance.entity.enums.AccountStatus;
import com.data.data.hmly.service.balance.entity.enums.AccountType;
import com.data.data.hmly.service.common.YhyMsgService;
import com.data.data.hmly.service.common.dao.ProValidCodeDao;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.dao.MemberDao;
import com.data.data.hmly.service.dao.SysUserDao;
import com.data.data.hmly.service.dao.UserDao;
import com.data.data.hmly.service.entity.FerryMember;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.entity.UserType;
import com.data.data.hmly.service.order.*;
import com.data.data.hmly.service.order.dao.FerryOrderDao;
import com.data.data.hmly.service.order.dao.OrderBillSummaryDao;
import com.data.data.hmly.service.order.entity.*;
import com.data.data.hmly.service.order.entity.enums.*;
import com.data.data.hmly.service.order.util.MsgTemplateKey;
import com.data.data.hmly.service.pay.PayService;
import com.data.data.hmly.service.wechat.WechatService;
import com.data.data.hmly.service.wechat.WechatSupportAccountService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.data.data.hmly.service.wechat.entity.WechatSupportAccount;
import com.data.data.hmly.service.wechat.entity.enums.NoticeType;
import com.data.data.hmly.util.GenOrderNo;
import com.framework.hibernate.util.Criteria;
import com.gson.bean.RefundResult;
import com.zuipin.exception.BizLogicException;
import com.zuipin.util.DateUtils;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Projections;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by caiys on 2016/3/30.
 */
@Service
public class BalanceService {

    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private AccountLogDao accountLogDao;
    @Resource
    private AccountLogService accountLogService;
    @Resource
    private SysUserDao sysUserDao;
    @Resource
    private UserDao userDao;
    @Resource
    private SysUserService sysUserService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private WechatSupportAccountService wechatSupportAccountService;
    @Resource
    private WechatService wechatService;
    @Resource
    private MemberDao memberDao;
    @Resource
    private FerryOrderService ferryOrderService;
    @Resource
    private OrderBillSummaryDao orderBillSummaryDao;
    @Resource
    private OrderLogService orderLogService;
    @Resource
    private OrderValidateCodeService orderValidateCodeService;
    @Resource
    private FerryOrderDao ferryOrderDao;
    @Resource
    private ProValidCodeDao proValidCodeDao;
    @Resource
    private YhyMsgService yhyMsgService;
    @Resource
    private LvjiOrderService lvjiOrderService;


    public void doPayedConfirm(Order order, OrderDetail detail, SysUser loginUser) {
        detail.setStatus(OrderDetailStatus.SUCCESS);    //待支付
        detail.setOperator(loginUser);
        orderService.updateOrderDetailBill(detail); // 同时更新账单信息
//        orderDetailService.update(detail);  //更新订单详情
        // 更新订单状态
        orderService.updateOrderStatus(order, OrderStatus.SUCCESS, OrderDetailStatus.SUCCESS);
        doOrderDetailBill(detail);  //账单处理
        List<Map<String, Object>> resultList = orderValidateCodeService.doCreateValidateCode(order, detail);  //生成验证码
        // 短信发送
        if (OrderType.hotel.equals(order.getOrderType())) {
            Map<String, Object> msgData = new HashMap<String, Object>();
            msgData.put("code", resultList.get(0).get("code"));
            msgData.put("proName", detail.getProduct().getName());
            // @WEB_SMS
            yhyMsgService.doSendSMS(msgData, order.getMobile(), MsgTemplateKey.WEB_HOTEL_CFM_SUCCESS_TLE);
        }

    }


    public void doOrderDetailBill(OrderDetail orderDetail) {
        User orderLogUser = orderLogService.getSysOrderLogUser();
        Order order = orderDetail.getOrder();
        Product product = orderDetail.getProduct();
//        SysUser productUser = product.getUser();
        ProductSource productSource = product.getSource();
        Long orderId = order.getId();
        // 订单详情状态
        OrderDetailStatus status = orderDetail.getStatus();
        if (productSource != null && !ProductSource.LXB.equals(productSource)) {
            OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                    + "非本平台产品, 不结算", orderId, orderDetail.getId(), OrderLogLevel.warn);
            orderLogService.loggingOrderLog(log1);
            return;
        }
        if (!OrderDetailStatus.SUCCESS.equals(status)) {
            OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                            + "状态不为成功! 不结算! 现在状态: " + status.getDescription(),
                    orderId, orderDetail.getId(), OrderLogLevel.warn);
            orderLogService.loggingOrderLog(log1);
            return;
        }
        // 结算状态
        Integer billStatus = orderDetail.getOrderBillStatus();
        // 结算方式
        OrderBillType billType = orderDetail.getOrderBillType();
        // 结算价格
        Float billPrice = orderDetail.getOrderBillPrice();
        // 结算日期
        Date billDate = orderDetail.getOrderBillDate();
        Calendar billCalendar = Calendar.getInstance();
        billCalendar.clear();
        billCalendar.setTime(billDate);
        Calendar nowCalendar = Calendar.getInstance();
        if (nowCalendar.get(Calendar.YEAR) != billCalendar.get(Calendar.YEAR)
                || nowCalendar.get(Calendar.MONTH) != billCalendar.get(Calendar.MONTH)
                || nowCalendar.get(Calendar.DATE) != billCalendar.get(Calendar.DATE)) {
            OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                            + "结算日期未到, 结算日期应为: " + DateUtils.format(billCalendar.getTime(), "yyyy-MM-dd"),
                    orderId, orderDetail.getId(), OrderLogLevel.warn);
            orderLogService.loggingOrderLog(log1);
            return;
        }
        if (billStatus != null) {
            switch (billStatus) {
                // 已结算
                case 1:
                    OrderLog log1 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                            + "已经结算! 不能重复结算!", orderId, orderDetail.getId(), OrderLogLevel.warn);
                    orderLogService.loggingOrderLog(log1);
                    // 已结算单的订单, 在此生成对账单(对于前一天的D0和T0结算的订单)
//                    this.doCreateOrderBill(orderDetail);
                    break;
                // 未结算
                case 0:
                    if (billPrice == null) {
                        OrderLog log2 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                                + "结算价格为空, 不能结算", orderId, orderDetail.getId(), OrderLogLevel.warn);
                        orderLogService.loggingOrderLog(log2);
                        break;
                    }
                    // D0或者T0结算(实时结算的订单, 在此立即结算, 但不生成对账单(第二天生成))
                    if (OrderBillType.D0.equals(billType) || OrderBillType.T0.equals(billType)) {
                        // 结算操作
                        String runningNo = order.getOrderNo() + orderDetail.getId();
//                        SysUser proUser = sysUserService.findSysUserById(orderDetail.getProduct().getUser().getId());
                        updateBalanceCompany(billPrice.doubleValue(), AccountType.in, orderDetail.getProduct().getCompanyUnit().getId(),
                                orderDetail.getProduct().getUser().getId(), runningNo, orderDetail.getId(), order.getOrderType(), orderDetail.getProductType());
                        /*productUser.setBalance(productUser.getBalance() + billPrice.doubleValue());
                        sysUserService.update(productUser);*/
                        //
                        OrderLog log3 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                                + "结算成功! 结算价格: " + billPrice, orderId, orderDetail.getId(), OrderLogLevel.warn);
                        orderLogService.loggingOrderLog(log3);
                        // 更新结算状态
                        orderDetail.setOrderBillStatus(1);
                        orderDetailService.update(orderDetail);
                    } else {
                        // 其他结算方式的订单, 未结算的在此生成对账单, 等待商家与平台确认结算
//                        this.doCreateOrderBill(orderDetail);
                    }
                    break;
                // 其他结算状态, 未知
                default:
                    OrderLog log2 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
                                    + "未知的结算状态! 不结算! 现在结算状态: " + orderDetail.getOrderBillStatus(),
                            orderId, orderDetail.getId(), OrderLogLevel.warn);
                    orderLogService.loggingOrderLog(log2);
                    break;
            }
        }
    }

    /**
     * 确认账单
     */
    public Map<String, Object> doCfmOrderBillSummary(Long billSummaryId, SysUser loginUser) {
        OrderBillSummary orderBillSummary = orderBillSummaryDao.load(billSummaryId);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("success", true);
        if (orderBillSummary.getConfirmStatus() != 0) {
            map.put("success", false);
            map.put("errorMsg", "账单已被确认过，请获取最新数据进行操作。");
            return map;
        }
        orderBillSummary.setConfirmStatus(1);   // 确认状态(1: 双方确认, 0: 未确认, -1: 等待商家确认, -2:  等待平台确认)
        orderBillSummary.setStatus(1);  // 结算状态(1: 已结算, 0: 未结算, 2: 部分结算)
        orderBillSummary.setAlreadyBillPrice(orderBillSummary.getNotBillPrice());
        orderBillSummary.setNotBillPrice(0f);
        orderBillSummary.setUnitConfirmor(loginUser);
        orderBillSummary.setUpdateTime(new Date());
        orderBillSummaryDao.update(orderBillSummary);
        // 更新订单明细为已结算
        orderBillSummaryDao.updateDetailBillStatus(billSummaryId, 1);   // 结算状态(1: 已结算, 0: 未结算, 2: 部分结算)
        // 更新账户余额
        SysUser accountUser = findCompanyManager(orderBillSummary.getCompanyUnit().getId());
        Double amount = orderBillSummary.getAlreadyBillPrice().doubleValue();
//        updateBalanceCompany(amount, AccountType.in, accountUser.getId(), loginUser.getId(), orderBillSummary.getBillNo(), orderBillSummary.getId(), order.getOrderType(), detail.getProductType());
        updateBalance(amount, AccountType.in, accountUser.getId(),
                null, loginUser.getId(), orderBillSummary.getBillNo(), orderBillSummary.getId());
        // 订单日志 TODO
//        OrderLog log3 = orderLogService.createOrderLog(orderLogUser, "订单详情#" + orderDetail.getId()
//                + "结算成功! 结算价格: " + billPrice, orderId, orderDetail.getId(), OrderLogLevel.warn);
//        orderLogService.loggingOrderLog(log3);
        return map;
    }

    /**
     * 回写回调状态，并记录日志
     * @param order
     */
    public void savePayResult(Order order) {
        List<OrderDetail> detailList = order.getOrderDetails();
        OrderDetail detail = detailList.get(0);
        SysUser admin = sysUserService.findUserByAccount("admin");  // 默认为admin操作
        if (order.getStatus() == OrderStatus.SUCCESS) {
            detail.setStatus(OrderDetailStatus.SUCCESS);
            updateBalance(order.getPrice().doubleValue(), AccountType.recharge, order.getUser().getId(), null, admin.getId(), order.getOrderNo(), order.getId());
        } else if (order.getStatus() == OrderStatus.FAILED) {
            detail.setStatus(OrderDetailStatus.FAILED);
            // 记录充值失败记录
            AccountLog accountLog = new AccountLog();
            accountLog.setType(AccountType.recharge);
            accountLog.setOrderNo(order.getOrderNo());
            accountLog.setMoney(Double.valueOf(order.getPrice()));
//            accountLog.setBalance(accountUser.getBalance());
            accountLog.setDescription(AccountType.recharge.getDescription());
            accountLog.setStatus(AccountStatus.fail);
            accountLog.setUser(admin);
            accountLog.setCreateTime(new Date());
            accountLog.setCompanyUnit(order.getCompanyUnit());
            accountLog.setSourceId(order.getId());
            accountLogDao.save(accountLog);
        }
        orderDetailService.update(detail);
        orderService.update(order);
    }

    /**
     * 更新账户余额（公司管理员账户的余额），并记录余额变化日志
     * @param amount
     * @param accountType
     * @param accountUserId   待更新账户的用户标识，可以不是公司管理员用户标识
     * @param relCompanyId   影响账户公司标识，可为空
     * @param optUserId   执行操作的用户标识
     * @param runningNo  流水号：便于订单跟踪
     * @param sourceId  来源标识：消费来自订单标识；充值来自充值记录标识等等
     */
    @Deprecated
    public void updateBalance(Double amount, AccountType accountType, Long accountUserId,
                              Long relCompanyId, Long optUserId, String runningNo, Long sourceId) {
        // 查找公司的余额（公司管理员账户的余额）
        SysUser accountUser = findBalanceAccountBy(accountUserId);
        Double balance = calculateBalance(amount, accountType, accountUser.getBalance());
        if (accountType != AccountType.running) {   // 更新账户余额
            accountUser.setBalance(balance);
            sysUserDao.save(accountUser);
        }
        AccountLog accountLog = new AccountLog();
        accountLog.setType(accountType);
        accountLog.setOrderNo(runningNo);
        accountLog.setMoney(amount);
        accountLog.setBalance(accountUser.getBalance());
        accountLog.setDescription(accountType.getDescription());
        accountLog.setStatus(AccountStatus.normal);
        accountLog.setAccountUser(accountUser);
        SysUser optUser = null;
        if (optUserId != null) {
            optUser = new SysUser();
            optUser.setId(optUserId);
            accountLog.setUser(optUser);
        }
        accountLog.setCreateTime(new Date());
        accountLog.setCompanyUnit(accountUser.getSysUnit().getCompanyUnit());
        accountLog.setSourceId(sourceId);
        accountLogDao.save(accountLog);

        // 影响的公司账户处理
        AccountType relAccountType = convertRelAccountType(accountType);
        if (relCompanyId == null || relAccountType == null) {
            return;
        }
        SysUser relAccountUser = sysUserDao.findCompanyManager(relCompanyId);
        Double relBalance = calculateBalance(amount, relAccountType, relAccountUser.getBalance());
        if (relAccountType != AccountType.running) {   // 更新账户余额
            relAccountUser.setBalance(relBalance);
            sysUserDao.save(relAccountUser);
        }
        AccountLog relAccountLog = new AccountLog();
        relAccountLog.setType(relAccountType);
        relAccountLog.setOrderNo(runningNo);
        relAccountLog.setMoney(amount);
        relAccountLog.setBalance(relAccountUser.getBalance());
        relAccountLog.setDescription(relAccountType.getDescription());
        relAccountLog.setStatus(AccountStatus.normal);
        if (optUser != null) {
            relAccountLog.setUser(optUser);
        }
        relAccountLog.setCreateTime(new Date());
        relAccountLog.setCompanyUnit(relAccountUser.getSysUnit().getCompanyUnit());
        relAccountLog.setSourceId(sourceId);
        accountLogDao.save(relAccountLog);
    }

    /**
     * 更新账户余额（公司管理员账户的余额），并记录余额变化日志
     * @param amount
     * @param accountType
     * @param accountUserId   待更新账户的用户标识，可以不是公司管理员用户标识
     * @param optUserId   执行操作的用户标识
     * @param runningNo  流水号：便于订单跟踪
     * @param sourceId  来源标识：消费来自订单标识；充值来自充值记录标识等等
     */
    public void updateBalanceCompany(Double amount, AccountType accountType, Long accountUserId,
                                     Long optUserId, String runningNo, Long sourceId, OrderType orderType, ProductType detailType) {
        // 查找公司的余额（公司管理员账户的余额）
        SysUser accountUser = sysUserDao.findCompanyManager(accountUserId);
        Double balance = calculateBalanceCompany(amount, accountType, accountUser.getBalance());
        if (accountType != AccountType.running) {   // 更新账户余额
            accountUser.setBalance(balance);
            sysUserDao.save(accountUser);
        }
        AccountLog accountLog = new AccountLog();
        accountLog.setType(accountType);
        accountLog.setDetailType(detailType);
        accountLog.setOrderType(orderType);
        accountLog.setOrderNo(runningNo);
        accountLog.setMoney(amount);
        accountLog.setBalance(accountUser.getBalance());
        accountLog.setDescription(accountType.getDescription());
        accountLog.setStatus(AccountStatus.normal);
        accountLog.setAccountUser(accountUser);
        accountLog.setCompanyUnit(accountUser.getSysUnit().getCompanyUnit());
        SysUser optUser = null;
        if (optUserId != null) {
            optUser = new SysUser();
            optUser.setId(optUserId);
            accountLog.setUser(optUser);
        }
        accountLog.setCreateTime(new Date());
        accountLog.setSourceId(sourceId);
        accountLogDao.save(accountLog);
    }

    /**
     * 更新账户余额（游客的余额），并记录余额变化日志
     * @param amount
     * @param accountType
     * @param accountUserId   待更新账户的用户标识
     * @param optUserId   执行操作的用户标识
     * @param runningNo  流水号：便于订单跟踪
     * @param sourceId  来源标识：消费来自订单标识；充值来自充值记录标识等等
     */
    public void updateBalanceMember(Double amount, AccountType accountType, Long accountUserId,
                                     Long optUserId, String runningNo, Long sourceId, OrderType orderType, ProductType detailType) {
        // 查找账户余额（游客的余额）
        Member member = findYhyBalanceAccountBy(accountUserId);
        Double balance = calculateBalance(amount, accountType, member.getBalance());
        if (accountType != AccountType.running) {   // 更新账户余额
            member.setBalance(balance);
            sysUserDao.save(member);
        }
        AccountLog accountLog = new AccountLog();
        accountLog.setType(accountType);
        accountLog.setDetailType(detailType);
        accountLog.setOrderType(orderType);
        accountLog.setOrderNo(runningNo);
        accountLog.setMoney(amount);
        accountLog.setBalance(member.getBalance());
        accountLog.setDescription(accountType.getDescription());
        accountLog.setStatus(AccountStatus.normal);
        accountLog.setAccountUser(member);
        SysUser optUser = null;
        if (optUserId != null) {
            optUser = new SysUser();
            optUser.setId(optUserId);
            accountLog.setUser(optUser);
        }
        accountLog.setCreateTime(new Date());
        accountLog.setSourceId(sourceId);
        accountLogDao.save(accountLog);
    }

    /**
     * !!已废弃，分拆为多个方法，详见：updateBalanceCompany, updateBalanceMember
     * 更新账户余额（公司管理员账户的余额），并记录余额变化日志
     * @param amount
     * @param accountType
     * @param accountUserId   待更新账户的用户标识，可以不是公司管理员用户标识
     * @param relCompanyId   影响账户公司标识，可为空
     * @param optUserId   执行操作的用户标识
     * @param runningNo  流水号：便于订单跟踪
     * @param sourceId  来源标识：消费来自订单标识；充值来自充值记录标识等等
     */
    @Deprecated
    public void updateYhyBalance(Double amount, AccountType accountType, Long accountUserId,
                              Long relCompanyId, Long optUserId, String runningNo, Long sourceId, OrderType orderType, ProductType detailType) {
        // 查找公司的余额（公司管理员账户的余额）
        Member member = findYhyBalanceAccountBy(accountUserId);
        Double balance = calculateBalance(amount, accountType, member.getBalance());
        if (accountType != AccountType.running) {   // 更新账户余额
            member.setBalance(balance);
            sysUserDao.save(member);
        }
        AccountLog accountLog = new AccountLog();
        accountLog.setType(accountType);
        accountLog.setDetailType(detailType);
        accountLog.setOrderType(orderType);
        accountLog.setOrderNo(runningNo);
        accountLog.setMoney(amount);
        accountLog.setBalance(member.getBalance());
        accountLog.setDescription(accountType.getDescription());
        accountLog.setStatus(AccountStatus.normal);
        accountLog.setAccountUser(member);
        SysUser optUser = null;
        if (optUserId != null) {
            optUser = new SysUser();
            optUser.setId(optUserId);
            accountLog.setUser(optUser);
        }
        accountLog.setCreateTime(new Date());
        if (sourceId != null) {
            accountLog.setSourceId(sourceId);
        }
        accountLogDao.save(accountLog);

        // 影响的公司账户处理
        AccountType relAccountType = convertRelAccountType(accountType);
        if (relCompanyId == null || relAccountType == null) {
            return;
        }
        SysUser relAccountUser = sysUserDao.findCompanyManager(relCompanyId);
        Double relBalance = calculateBalance(amount, relAccountType, relAccountUser.getBalance());
        if (relAccountType != AccountType.running) {   // 更新账户余额
            relAccountUser.setBalance(relBalance);
            sysUserDao.save(relAccountUser);
        }
        AccountLog relAccountLog = new AccountLog();
        relAccountLog.setType(relAccountType);
        relAccountLog.setOrderNo(runningNo);
        relAccountLog.setMoney(amount);
        relAccountLog.setBalance(relAccountUser.getBalance());
        relAccountLog.setDescription(relAccountType.getDescription());
        relAccountLog.setStatus(AccountStatus.normal);
        relAccountLog.setAccountUser(relAccountUser);
        if (optUser != null) {
            relAccountLog.setUser(optUser);
        }
        relAccountLog.setCreateTime(new Date());
        relAccountLog.setCompanyUnit(relAccountUser.getSysUnit().getCompanyUnit());
        relAccountLog.setSourceId(sourceId);
        accountLogDao.save(relAccountLog);
    }

    /**
     * 申请提现-通过
     * @param al
     * @param manageUser
     */
    public void doPass(AccountLog al, SysUser manageUser) {
        // 修改资金账户
        Double balance = calculateBalance(al.getMoney(), al.getType(), manageUser.getFrozenBalance());
        manageUser.setFrozenBalance(balance);
        sysUserDao.save(manageUser);
        // 修改账户记录
        al.setUpdateTime(new Date());
        al.setAuditTime(new Date());
//        al.setBalance(balance);
        al.setStatus(AccountStatus.normal);
        accountLogDao.save(al);
    }

    /**
     * 获取余额账户
     * @param accountUserId
     * @return
     */
    public SysUser findBalanceAccountBy(Long accountUserId) {
        // 查找公司的余额（公司管理员账户的余额）
        SysUser accountUser = sysUserDao.load(accountUserId);
        // 如果不是公司管理员账户，查找公司管理员账户
        if (accountUser.getUserType() != UserType.AllSiteManage) {

            if (accountUser.getUserType() != UserType.CompanyManage && accountUser.getUserType() != UserType.SiteManage) {
                SysUser manageUser = sysUserDao.findCompanyManager(accountUser.getSysUnit().getCompanyUnit().getId());
                if (manageUser == null) {
                    throw new RuntimeException("不存在公司管理员账户，无法进行余额操作");
                }
                accountUser = manageUser;
            }
        }
        if (accountUser.getBalance() == null) { // 设置默认值，便于后续计算
            accountUser.setBalance(0d);
        }
        return accountUser;
    }
    /**
     * 获取一海游用户余额账户
     * @param accountUserId
     * @return
     */
    public Member findYhyBalanceAccountBy(Long accountUserId) {
        // 查找公司的余额（公司管理员账户的余额）

        Member member = memberDao.load(accountUserId);
        // 如果不是公司管理员账户，查找公司管理员账户
        /*if (accountUser.getUserType() != UserType.AllSiteManage) {

            if (accountUser.getUserType() != UserType.CompanyManage && accountUser.getUserType() != UserType.SiteManage) {
                SysUser manageUser = sysUserDao.findCompanyManager(accountUser.getSysUnit().getCompanyUnit().getId());
                if (manageUser == null) {
                    throw new RuntimeException("不存在公司管理员账户，无法进行余额操作");
                }
                accountUser = manageUser;
            }
        }*/
        if (member.getBalance() == null) { // 设置默认值，便于后续计算
            member.setBalance(0d);
        }
        return member;
    }

    public Double findYhyBalance(Long id) {
        Criteria<Member> memberCriteria = new Criteria<Member>(Member.class);
        memberCriteria.eq("id", id);
        memberCriteria.setProjection(Projections.property("balance"));
        return (Double) memberDao.findUniqueValue(memberCriteria);
    }

    /**
     * 查询公司管理员
     * @param companyUnitId
     * @return
     */
    public SysUser findCompanyManager(Long companyUnitId) {
        SysUser manageUser = sysUserDao.findCompanyManager(companyUnitId);
        if (manageUser == null) {
            throw new RuntimeException("不存在公司管理员账户，无法进行余额操作");
        }
        return manageUser;
    }

    public AccountLog getAccountLog(Long id) {
        return accountLogDao.load(id);
    }

    public void saveAccountLog(AccountLog accountLog) {
        accountLogDao.saveOrUpdate(accountLog, accountLog.getId());
    }

    /**
     * 计算账户余额（商户）
     * @param amount
     * @param accountType
     * @param balance
     * @return
     */
    private Double calculateBalanceCompany(Double amount, AccountType accountType, Double balance) {
        if (balance == null) {
            balance = 0d;
        }
        if (amount == null) {
            amount = 0d;
        }
        if (accountType == AccountType.recharge || accountType == AccountType.in || accountType == AccountType.consume || accountType == AccountType.outlinerc) {
            return amount + balance;
        } else if (accountType == AccountType.refund) { // 商户退款允许为负数
            return balance - amount;
        } else if (accountType == AccountType.out || accountType == AccountType.withdraw || accountType == AccountType.outlinewd) {
            if (balance < amount) {
                throw new RuntimeException("余额不足，无法进行余额操作");
            }
            return balance - amount;
        } else {
            return balance;
        }
    }

    /**
     * 计算账户余额（游客）
     * @param amount
     * @param accountType
     * @param balance
     * @return
     */
    private Double calculateBalance(Double amount, AccountType accountType, Double balance) {
        if (balance == null) {
            balance = 0d;
        }
        if (amount == null) {
            amount = 0d;
        }
        if (accountType == AccountType.recharge || accountType == AccountType.in || accountType == AccountType.refund || accountType == AccountType.outlinerc) {
            return amount + balance;
        } else if (accountType == AccountType.consume || accountType == AccountType.out || accountType == AccountType.withdraw || accountType == AccountType.outlinewd) {
            if (balance < amount) {
                throw new RuntimeException("余额不足，无法进行余额操作");
            }
            return balance - amount;
        } else {
            return balance;
        }
    }

    /**
     * 关联账户资金类型转换：消费->入账；退款->出账
     * 其他继续扩展
     * @param accountType
     * @return
     */
    private AccountType convertRelAccountType(AccountType accountType) {
        if (accountType == AccountType.consume) {
            return AccountType.in;
        } else if (accountType == AccountType.refund) {
            return AccountType.out;
        } else {
            return null;
        }
    }


    public Order saveUnitBalanceOrder(User user, Double amount) {
        Order order = null;
        if (order == null) {
            order = new Order();
            order.setOrderNo(GenOrderNo.generate(propertiesManager.getString("MACHINE_NO", "")));
            order.setCreateTime(new Date());
        }
        order.setUser(user);
        order.setOrderType(OrderType.withdraw);
        order.setName("提现");
        AccountType accountType = AccountType.withdraw;
        order.setStatus(OrderStatus.WAIT);
        order.setPrice(amount.floatValue());
        Integer minute = Integer.valueOf(propertiesManager.getString("ORDER_BALANCE_PAY_WAIT_TIME"));
        order.setWaitTime(com.data.data.hmly.service.common.util.DateUtils.add(new Date(), Calendar.MINUTE, minute));
        order.setOrderWay(OrderWay.web);
        orderService.save(order);
        savePayResult(order, accountType);
        return order;
    }


    public void savePayResult(Order order, AccountType accountType) {
        List<OrderDetail> detailList = order.getOrderDetails();
        User user = userDao.load(order.getUser().getId());
        if (user.getFrozenBalance() == null) {
            user.setFrozenBalance(0d);
        }
        AccountLog search = new AccountLog();
        search.setOrderNo(order.getOrderNo());
        List<AccountLog> list = accountLogService.findListByAccountLog(search);
        AccountLog accountLog = new AccountLog();
        if (!list.isEmpty()) {
            accountLog = list.get(0);
            accountLog.setUpdateTime(new Date());
        } else {
            accountLog.setCreateTime(new Date());
            accountLog.setType(accountType);
            accountLog.setOrderNo(order.getOrderNo());
            accountLog.setMoney(order.getPrice().doubleValue());
            accountLog.setDescription(accountType.getDescription());
            accountLog.setUser(user);
            accountLog.setCompanyUnit(order.getCompanyUnit());
            accountLog.setSourceId(order.getId());
            accountLog.setAccountUser(user);
        }
        if (accountType.equals(AccountType.withdraw)) {
            if (OrderStatus.CLOSED.equals(order.getStatus()) || OrderStatus.FAILED.equals(order.getStatus())) {
                user.setBalance(user.getBalance() + order.getPrice());
                user.setFrozenBalance(user.getFrozenBalance() - order.getPrice());
                memberDao.update(user);
                accountLog.setStatus(AccountStatus.fail);
            } else if (OrderStatus.WAIT.equals(order.getStatus())) {
                Double balance = calculateBalance(order.getPrice().doubleValue(), accountType, user.getBalance());
                user.setBalance(balance);
                user.setFrozenBalance(user.getFrozenBalance() + order.getPrice());
                memberDao.update(user);
                accountLog.setStatus(AccountStatus.processing);
            } else if (OrderStatus.SUCCESS.equals(order.getStatus())) {
                user.setFrozenBalance(user.getFrozenBalance() - order.getPrice());
                memberDao.update(user);
                accountLog.setStatus(AccountStatus.normal);
            }
        } else if (accountType.equals(AccountType.recharge)) {
            if (order.getStatus().equals(OrderStatus.SUCCESS)) {
                Double balance = calculateBalance(order.getPrice().doubleValue(), accountType, user.getBalance());
                user.setBalance(balance);
                memberDao.update(user);
                accountLog.setStatus(AccountStatus.normal);
                // 发送短信
                Map<String, Object> msgData = new HashMap<String, Object>();
                msgData.put("amount", order.getPrice());
                msgData.put("balance", balance);
                if (UserType.USER.equals(user.getUserType())) {
                    Member member = memberDao.load(user.getId());
                    if (StringUtils.hasText(member.getTelephone())) {
                        // @WEB_SMS
                        yhyMsgService.doSendSMS(msgData, member.getTelephone(), MsgTemplateKey.USER_RECHARGE_NO_GIFT_TLE);
                    }
                }
            } else if (OrderStatus.FAILED.equals(order.getStatus())) {
                accountLog.setStatus(AccountStatus.fail);
            }
        } else if (accountType.equals(AccountType.consume) && order.getStatus() == OrderStatus.PAYED) {
            accountLog.setStatus(AccountStatus.normal);
            for (OrderDetail orderDetail : detailList) {
                orderDetail.setStatus(OrderDetailStatus.PAYED);
                orderDetailService.update(orderDetail);
            }
        }
        accountLog.setOrderType(order.getOrderType());
        order.setModifyTime(new Date());
        accountLog.setBalance(user.getBalance());
        accountLogDao.save(accountLog);
        orderService.update(order);
    }

    public void savePayResult(FerryOrder order) {
//        List<OrderDetail> detailList = order.getOrderDetails();
//        OrderDetail detail = detailList.get(0);
        User user = memberDao.load(order.getUser().getId());
        if (user.getFrozenBalance() == null) {
            user.setFrozenBalance(0d);
        }
        AccountLog search = new AccountLog();
        search.setOrderNo(order.getOrderNumber());
        List<AccountLog> list = accountLogService.findListByAccountLog(search);
        AccountLog accountLog = new AccountLog();
        if (!list.isEmpty()) {
            accountLog = list.get(0);
            accountLog.setUpdateTime(new Date());
        } else {
            accountLog.setCreateTime(new Date());
            accountLog.setType(AccountType.consume);
            accountLog.setOrderNo(order.getOrderNumber());
            accountLog.setMoney(order.getAmount().doubleValue());
            accountLog.setDescription(AccountType.consume.getDescription());
            accountLog.setUser(user);
//            accountLog.setCompanyUnit(order.getCompanyUnit());
            accountLog.setSourceId(order.getId());
            accountLog.setAccountUser(user);
        }
        if (OrderStatus.SUCCESS.equals(order.getStatus())) {
            accountLog.setStatus(AccountStatus.normal);
        }
        accountLog.setOrderType(OrderType.ferry);
        order.setModifyTime(new Date());
        accountLog.setBalance(user.getBalance());
        accountLogDao.save(accountLog);
        ferryOrderService.updateOrder(order);
    }

    public void savePayResult(LvjiOrder order) {
//        List<OrderDetail> detailList = order.getOrderDetails();
//        OrderDetail detail = detailList.get(0);
        User user = memberDao.load(order.getUser().getId());
        if (user.getFrozenBalance() == null) {
            user.setFrozenBalance(0d);
        }
        AccountLog search = new AccountLog();
        search.setOrderNo(order.getOrderNo());
        List<AccountLog> list = accountLogService.findListByAccountLog(search);
        AccountLog accountLog = new AccountLog();
        if (!list.isEmpty()) {
            accountLog = list.get(0);
            accountLog.setUpdateTime(new Date());
        } else {
            accountLog.setCreateTime(new Date());
            accountLog.setType(AccountType.consume);
            accountLog.setOrderNo(order.getOrderNo());
            accountLog.setMoney(order.getPrice().doubleValue());
            accountLog.setDescription(AccountType.consume.getDescription());
            accountLog.setUser(user);
//            accountLog.setCompanyUnit(order.getCompanyUnit());
            accountLog.setSourceId(order.getId());
            accountLog.setAccountUser(user);
        }
        if (OrderStatus.SUCCESS.equals(order.getStatus())) {
            accountLog.setStatus(AccountStatus.normal);
        }
        accountLog.setOrderType(OrderType.lvji);
        order.setModifyTime(new Date());
        accountLog.setBalance(user.getBalance());
        accountLogDao.save(accountLog);
        lvjiOrderService.updateOrder(order);
    }

    public void doSendWXMsg(String content, WechatAccount wechatAccount) {
        List<WechatSupportAccount> wechatSupportAccountList = wechatSupportAccountService.getByAccountList(wechatAccount.getCompanyUnit().getId().toString(), wechatAccount.getAccount());
        try {
            for (WechatSupportAccount wechatSupportAccount : wechatSupportAccountList) {
                wechatService.doSendTplMessage(wechatSupportAccount.getWechatAccount().getId(), wechatSupportAccount.getOpenId(), NoticeType.order, content, null);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Map<String, Object> findFundsFlowInfo(SysUnit companyUnid) {
        List<OrderBillSummary> orderBillSummaries = orderBillSummaryDao.findOrderBillSummaryList(companyUnid);
        Map<String, Object> map = new HashMap<String, Object>();
        for (OrderBillSummary orderBillSummary : orderBillSummaries) {
            if (map.get("hasedBill") == null) {
                map.put("hasedBill", 0F);
            }
            if (map.get("unBill") == null) {
                map.put("unBill", 0F);
            }
            if (orderBillSummary.getStatus() == 1) {
                map.put("hasedBill", (Float) map.get("hasedBill") + orderBillSummary.getTotalBillPrice());
            } else {
                map.put("unBill", (Float) map.get("unBill") + orderBillSummary.getTotalBillPrice());
            }
        }
        return map;
    }

    /**
     * 提现拒绝处理
     * @param al
     * @param manageUser
     */
    public void doReject(AccountLog al, SysUser manageUser) {
        Double frozenBalance = manageUser.getFrozenBalance();
        Double balance = manageUser.getBalance();
        Double money = al.getMoney();

        manageUser.setFrozenBalance(frozenBalance - money);
        manageUser.setBalance(balance + money);
        sysUserDao.save(manageUser);
    }

    /**
     * 预订成功订单退款（商户订单）
     * @param loginUser
     * @return
     */
    public void doRefundOrder(Order order, OrderDetail detail, SysUser loginUser) throws Exception {
        Float amount = detail.getTotalPrice();
        detail.setStatus(OrderDetailStatus.REFUNDED);
        detail.setRefund(amount);
        detail.setRefundBillAmount(detail.getOrderBillPrice());
        detail.setRefundDate(new Date());
        orderDetailService.update(detail);

        // 验证码设置为无效
        proValidCodeDao.updateUnusedStatus(detail.getId());

        // 更新订单状态
        orderService.updateOrderStatus(order, OrderStatus.REFUND, OrderDetailStatus.REFUNDED);

        String runningNo = order.getOrderNo() + "-" + detail.getId();
        // 退款时如果结算方式是T0，才处理商户账户余额
        if (detail.getOrderBillType() == OrderBillType.T0) {
            updateBalanceCompany(detail.getRefundBillAmount().doubleValue(), AccountType.refund, detail.getProduct().getCompanyUnit().getId(), loginUser.getId(), runningNo, detail.getId(), order.getOrderType(), detail.getProductType());
        }
        // 判断支付方式
        if (order.getPayType() == OrderPayType.ONLINE) {    // 余额支付
            updateBalanceMember(amount.doubleValue(), AccountType.refund, order.getUser().getId(), loginUser.getId(), runningNo, detail.getId(), order.getOrderType(),  detail.getProductType());
        } else { // 非余额支付，原来退回
            doThirdPayRefund(order.getPayType(), order.getOrderNo(), runningNo, order.getPrice(), amount);
        }
        // 短信发送
        Map<String, Object> msgData = new HashMap<String, Object>();
        msgData.put("orderNo", order.getOrderNo());
        msgData.put("amount", amount);
        // @WEB_SMS
        yhyMsgService.doSendSMS(msgData, order.getMobile(), MsgTemplateKey.USER_REFUND_SUCCESS_TLE);
    }

    /**
     * 用户已经支付的订单退款，退款给用户账户
     * @param loginUser
     * @return
     */
    public void doFailOrderRefund(Order order, OrderDetail detail, String remark, SysUser loginUser) throws Exception {
        Float amount = detail.getTotalPrice();
        detail.setStatus(OrderDetailStatus.INVALID);
        detail.setRefund(amount);
//        detail.setRefundBillAmount(detail.getOrderBillPrice());
        detail.setRefundDate(new Date());
        orderDetailService.update(detail);

        // 更新订单状态
        orderService.updateOrderStatus(order, OrderStatus.INVALID, OrderDetailStatus.INVALID);

        // 如果是艺龙酒店不做退款
        if (detail.getProduct().getSource() == ProductSource.ELONG) {
            return;
        }

        String runningNo = order.getOrderNo() + "-" + detail.getId();
        // 判断支付方式
        if (order.getPayType() == OrderPayType.ONLINE) {    // 余额支付
            updateBalanceMember(amount.doubleValue(), AccountType.refund, order.getUser().getId(), loginUser.getId(), runningNo, detail.getId(), order.getOrderType(),  detail.getProductType());
        } else { // 非余额支付，原来退回
            doThirdPayRefund(order.getPayType(), order.getOrderNo(), runningNo, order.getPrice(), amount);
        }
        // 短信发送
        if (OrderType.hotel.equals(order.getOrderType())) {
            Map<String, Object> msgData = new HashMap<String, Object>();
            msgData.put("orderNo", order.getOrderNo());
            // @WEB_SMS
            yhyMsgService.doSendSMS(msgData, order.getMobile(), MsgTemplateKey.MERCHANT_HOTEL_CANCEL_TLE);
        }
    }

    /**
     * 用户已经支付的订单退款，退款给用户账户 - 轮渡船票
     * @param loginUser
     * @return
     */
    public void doFailOrderRefundFerry(FerryOrder ferryOrder, String remark, SysUser loginUser) throws Exception {
        Float amount = ferryOrder.getAmount();
        ferryOrder.setStatus(OrderStatus.INVALID);
        ferryOrder.setReturnAmount(amount);
        ferryOrder.setPoundageAmount(0f);
//        detail.setRefundBillAmount(detail.getOrderBillPrice());
        ferryOrder.setRefundDate(new Date());
        ferryOrder.setModifyTime(new Date());
        ferryOrderDao.update(ferryOrder);

        // 如果是行程规划，查询是否有船票订单
        Order order = null;
        if (ferryOrder.getOrderId() != null) {
            order = orderService.get(ferryOrder.getOrderId());
            orderService.updateOrderStatus(order, OrderStatus.INVALID, OrderDetailStatus.INVALID);
        }

        String runningNo = ferryOrder.getOrderNumber() + "-" + ferryOrder.getId();
        OrderType orderType = OrderType.ferry;
        Float totalPrice = ferryOrder.getAmount();
        String orderNo = ferryOrder.getOrderNumber();
        OrderPayType orderPayType = ferryOrder.getPayType();
        if (ferryOrder.getOrderId() != null) {  // 行程订单，取总订单信息
            orderType = OrderType.plan;
            totalPrice = order.getPrice();
            orderPayType = order.getPayType();
        }
        // 判断支付方式
        if (orderPayType == OrderPayType.ONLINE) {    // 余额支付
            updateBalanceMember(amount.doubleValue(), AccountType.refund, ferryOrder.getUser().getId(), loginUser.getId(), runningNo, ferryOrder.getId(), orderType, ProductType.ferry);
        } else { // 非余额支付，原来退回
            doThirdPayRefund(orderPayType, orderNo, runningNo, totalPrice, amount);
        }
        // 发送短信
        Map<String, Object> msgData = new HashMap<String, Object>();
        msgData.put("amount", amount);
        Member member = ferryOrder.getUser();
        FerryMember ferryMember = member.getFerryMember();
        // @WEB_SMS
        // 轮渡船票退款申请短信
        yhyMsgService.doSendSMS(msgData, ferryMember.getMobile(), MsgTemplateKey.USER_FERRY_REFUND_WAIT_TLE);
    }

    /**
     * 第三方支付，调用退款接口，接口失败抛出异常保证事务回滚
     * @param orderPayType
     * @param orderNo
     * @param refundNo
     * @param totalPrice
     * @param refundPrice
     */
    public void doThirdPayRefund(OrderPayType orderPayType, String orderNo, String refundNo, Float totalPrice, Float refundPrice) throws Exception {
        PayManager payManager = PayManager.findService(orderPayType.name());
        PayService payService = payManager.getPayService();
        RefundResult result = payService.refundOrder(orderNo, refundNo, totalPrice, refundPrice);
        if (!result.isSuccess()) {
            StringBuilder errMsg = new StringBuilder("退款失败");
            if (StringUtils.isNotBlank(result.getMsg())) {
                errMsg.append("：").append(result.getMsg());
            } else if (StringUtils.isNotBlank(result.getErrMsg())) {
                errMsg.append("：").append(result.getErrMsg());
            }
            errMsg.append("！");
            throw new BizLogicException(errMsg.toString());
        }
    }
}
