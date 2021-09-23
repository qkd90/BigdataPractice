package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.action.yhypc.response.LvjiOrderResponse;
import com.data.data.hmly.action.yhypc.vo.AccountLogVo;
import com.data.data.hmly.action.yhypc.vo.OrderAllVo;
import com.data.data.hmly.action.yhypc.vo.OrderTouristVo;
import com.data.data.hmly.action.yhypc.vo.PlanVo;
import com.data.data.hmly.action.yhypc.vo.PvCodeVo;
import com.data.data.hmly.service.LvjiWebService;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.balance.AccountLogService;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.balance.entity.AccountLog;
import com.data.data.hmly.service.balance.entity.enums.AccountStatus;
import com.data.data.hmly.service.common.ProValidCodeService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.ProValidCode;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.order.FerryOrderService;
import com.data.data.hmly.service.order.LvjiOrderService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.ShenzhouOrderService;
import com.data.data.hmly.service.order.entity.*;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.PlanDay;
import com.data.data.hmly.service.plan.entity.PlanTrip;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.user.TouristService;
import com.data.data.hmly.service.user.entity.Tourist;
import com.data.data.hmly.service.user.entity.enums.TouristStatus;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.BeanUtils;
import com.zuipin.util.DateUtils;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2017/1/24.
 */
public class PersonalWebAction extends YhyAction {

    private Map<String, Object> result = new HashMap<String, Object>();
    private OrderAllVo orderAllVo = new OrderAllVo();
    public LvjiOrderResponse response = new LvjiOrderResponse();
    private List<OrderAllVo> ferryOrderAllVos = new ArrayList<>();
    private Map<Long, OrderAllVo> hotelOrderAllVoMap = new HashMap<Long, OrderAllVo>();
    private Map<Long, OrderAllVo> ticketOrderAllVoMap = new HashMap<Long, OrderAllVo>();
    private Map<Long, OrderAllVo> sailOrderAllVoMap = new HashMap<Long, OrderAllVo>();
    private String orderTypes;
    private OrderStatus orderStatus;
    private Boolean hasComment;
    private String fn;
    private Integer pageNo;
    private Integer pageSize;
    private Member member = new Member();
    private Tourist tourist = new Tourist();
    private Double balance;

    public Long orderId;

    @Resource
    private MemberService memberService;
    @Resource
    private OrderService orderService;
    @Resource
    private ProValidCodeService proValidCodeService;
    @Resource
    private OrderTouristService orderTouristService;
    @Resource
    private FerryOrderService ferryOrderService;
    @Resource
    private ShenzhouOrderService shenzhouOrderService;
    @Resource
    private PlanService planService;
    @Resource
    private TouristService touristService;
    @Resource
    private BalanceService balanceService;
    @Resource
    private AccountLogService accountLogService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private LvjiOrderService lvjiOrderService;
    @Resource
    private LvjiWebService lvjiWebService;

    public Result index() {
        return dispatch();
    }

    public Result countMyOrder() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        Order conditionOrder = new Order();
        List<OrderType> orderTypeList = new ArrayList<OrderType>();
        List<OrderType> allTypeList = Lists.newArrayList(OrderType.ticket, OrderType.sailboat, OrderType.yacht, OrderType.huanguyou, OrderType.hotel, OrderType.plan, OrderType.ferry);
        if (StringUtils.hasText(orderTypes)) {
            String[] types = orderTypes.split(",");
            for (String type : types) {
                orderTypeList.add(OrderType.valueOf(type));
            }
        } else {
            orderTypeList = allTypeList;
        }
        if (orderStatus != null) {
            conditionOrder.setStatus(orderStatus);
        }
        if (hasComment != null) {
            conditionOrder.setHasComment(hasComment);
        }
        conditionOrder.setDeleteFlag(false);
        conditionOrder.setUser(loginUser);
        // total count
        Long totalCount = orderService.countMyOrderAll(conditionOrder, orderTypeList);
        result.put("totalCount", totalCount);
        // success count
        conditionOrder.setHasComment(null);
        conditionOrder.setStatus(OrderStatus.SUCCESS);
        Long successCount = orderService.countMyOrderAll(conditionOrder, allTypeList);
        // wait pay count
        conditionOrder.setStatus(OrderStatus.WAIT);
        Long waitPayCount = orderService.countMyOrderAll(conditionOrder, allTypeList);
        // canceled count
        conditionOrder.setStatus(OrderStatus.CANCELED);
        Long canceledCount = orderService.countMyOrderAll(conditionOrder, allTypeList);
        // comment count
        conditionOrder.setStatus(null);
        conditionOrder.setHasComment(true);
        Long commentCount = orderService.countMyOrderAll(conditionOrder, allTypeList);
        // no comment count
        conditionOrder.setHasComment(false);
        Long noCommentCount = orderService.countMyOrderAll(conditionOrder, allTypeList);
        result.put("successCount", successCount);
        result.put("waitPayCount", waitPayCount);
        result.put("canceledCount", canceledCount);
        result.put("commentCount", commentCount);
        result.put("noCommentCount", noCommentCount);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result getMyOrder() {
        Member loginUser = getLoginUser();
        Page page = new Page(pageNo, pageSize);
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        Order conditionOrder = new Order();
        conditionOrder.setUser(loginUser);
        conditionOrder.setDeleteFlag(false);
        List<OrderType> orderTypeList = new ArrayList<OrderType>();
        if (StringUtils.hasText(orderTypes)) {
            String[] types = orderTypes.split(",");
            for (String type : types) {
                orderTypeList.add(OrderType.valueOf(type));
            }
        } else {
            orderTypeList = Lists.newArrayList(OrderType.ticket, OrderType.sailboat, OrderType.yacht, OrderType.huanguyou, OrderType.hotel, OrderType.plan, OrderType.ferry);
        }
        if (orderStatus != null) {
            conditionOrder.setStatus(orderStatus);
        }
        if (hasComment != null) {
            conditionOrder.setHasComment(hasComment);
        }
        List<OrderAll> orderAllList = orderService.searchMyOrderAll(conditionOrder, orderTypeList, page, "createTime", "desc");
        List<OrderAllVo> orderVos = new ArrayList<OrderAllVo>();
        for (OrderAll orderAll : orderAllList) {
            OrderAllVo orderVo = new OrderAllVo();
            orderVo.setId(orderAll.getId());
            orderVo.setName(orderAll.getName());
            orderVo.setOrderNo(orderAll.getOrderNo());
            orderVo.setPrice(orderAll.getPrice());
            orderVo.setCreateTime(DateUtils.format(orderAll.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
            orderVo.setStatus(orderAll.getStatus().getDescription());
            orderVo.setType(orderAll.getOrderType().toString());
            orderVos.add(orderVo);
        }
        result.put("success", true);
        result.put("data", orderVos);
        return json(JSONObject.fromObject(result));
    }

    public Result doDelOrder() {
        final HttpServletRequest request = getRequest();
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        String orderIdStr = request.getParameter("id");
        if (!StringUtils.hasText(orderIdStr) || !StringUtils.isLong(orderIdStr)) {
            result.put("success", false);
            result.put("msg", "订单id错误!");
            return json(JSONObject.fromObject(result));
        }
        String typeStr = request.getParameter("type");
        if (!StringUtils.hasText(typeStr)) {
            result.put("success", false);
            result.put("msg", "订单类型错误!");
            return json(JSONObject.fromObject(result));
        }
        Long orderId = Long.parseLong(orderIdStr);
        OrderType type = OrderType.valueOf(typeStr);
        Object order;
        if (OrderType.ferry.equals(type)) {
            order = ferryOrderService.getOrder(orderId);
        } else if (OrderType.shenzhou.equals(type)) {
            order = shenzhouOrderService.get(orderId);
        } else {
            order = orderService.get(orderId);
        }
        try {
            Method getStatusMethod = order.getClass().getMethod("getStatus");
            Method setDeleteFlagMethod = order.getClass().getMethod("setDeleteFlag", Boolean.class);
            Method getUserMethod = order.getClass().getMethod("getUser");
            getStatusMethod.setAccessible(true);
            setDeleteFlagMethod.setAccessible(true);
            getUserMethod.setAccessible(true);
            if (getStatusMethod.invoke(order) == null || (!getStatusMethod.invoke(order).equals(OrderStatus.CANCELED) && !getStatusMethod.invoke(order).equals(OrderStatus.INVALID))) {
                result.put("success", false);
                result.put("msg", "订单状态错误, 删除失败!");
                return json(JSONObject.fromObject(result));
            }
            if (getUserMethod.invoke(order) != null && ((User) getUserMethod.invoke(order)).getId().equals(loginUser.getId())) {
                if (OrderType.shenzhou.equals(type)) {
                    setDeleteFlagMethod.invoke(order, true);
                    shenzhouOrderService.update((ShenzhouOrder) order);
                } else if (OrderType.ferry.equals(type)) {
                    setDeleteFlagMethod.invoke(order, true);
                    ferryOrderService.updateOrder((FerryOrder) order);
                } else {
                    setDeleteFlagMethod.invoke(order, true);
                    orderService.update((Order) order);
                }
                result.put("success", true);
                return json(JSONObject.fromObject(result));
            } else {
                result.put("success", false);
                result.put("msg", "用户校验失败, 删除失败!");
                return json(JSONObject.fromObject(result));
            }
        } catch (Exception e) {
            result.put("success", false);
            result.put("msg", "删除失败!");
            e.printStackTrace();
            return json(JSONObject.fromObject(result));
        }
    }

    public Result myPlan() {
        return dispatch();
    }

    public Result countMyPlan() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        Long count = planService.countMyPlan(loginUser.getId(), null);
        result.put("success", true);
        result.put("count", count);
        return json(JSONObject.fromObject(result));
    }

    public Result getMyPlan() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        Page page = new Page(pageNo, pageSize);
        List<Plan> myPlans = planService.listMyPlan(loginUser.getId(), page);
        List<PlanVo> myPlanVos = new ArrayList<PlanVo>();
        for (Plan plan : myPlans) {
            PlanVo planVo = new PlanVo();
            Float price = 0F;
            List<String> scenicInfoNames = new ArrayList<String>();
            List<PlanDay> planDays = plan.getPlanDayList();
            planVo.setId(plan.getId());
            planVo.setName(plan.getName());
            planVo.setCover(plan.getCoverPath());
            planVo.setPlanDays(planDays.size());
            planVo.setStartTime(DateUtils.format(plan.getStartTime(), "yyyy-MM-dd"));
            for (PlanDay planDay : planDays) {
                Float ticketCost = planDay.getTicketCost() == null ? 0F : planDay.getTicketCost();
                Float includeSeasonticketCost = planDay.getIncludeSeasonticketCost() == null ? 0F : planDay.getIncludeSeasonticketCost();
                Float trafficCost = planDay.getTrafficCost() == null ? 0F : planDay.getTrafficCost();
                Float returnTrafficCost = planDay.getReturnTrafficCost() == null ? 0F : planDay.getReturnTrafficCost();
                Float hotelCost = planDay.getHotelCost() == null ? 0F : planDay.getHotelCost();
                Float foodCost = planDay.getFoodCost() == null ? 0F : planDay.getFoodCost();
                Float dayTotalCost = ticketCost + includeSeasonticketCost + trafficCost + returnTrafficCost + hotelCost + foodCost;
                price += dayTotalCost;
                List<PlanTrip> planTrips = planDay.getPlanTripList();
                for (PlanTrip planTrip : planTrips) {
                    ScenicInfo scenicInfo = planTrip.getScenicInfo();
                    if (scenicInfo != null) {
                        scenicInfoNames.add(scenicInfo.getName());
                    }
                }
            }
            planVo.setPrice(price);
            planVo.setScenicInfoNames(scenicInfoNames);
            myPlanVos.add(planVo);
        }
        result.put("success", true);
        result.put("data", myPlanVos);
        return json(JSONObject.fromObject(result));
    }
    public Result myCollection() {
        return dispatch();
    }

    public Result myInfo() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        member = memberService.get(loginUser.getId());
//        if (StringUtils.hasText(member.getUserName())) {
//            member.setUserName(member.getUserName().replaceAll("(\\d{1})\\d{" + (member.getUserName().length() - 1) + "}", "$1***"));
//        }
//        if (StringUtils.hasText(member.getIdNumber())) {
//            member.setIdNumber(member.getIdNumber().replaceAll("(\\d{3})\\d{11}(\\d{4})", "$1****$2"));
//        }
//        if (StringUtils.hasText(member.getBankNo())) {
//            member.setBankNo(member.getBankNo().replaceAll("(\\d{4})\\d{" + (member.getBankNo().length() - 7) + "}(\\d{3})", "$1****$2"));
//        }
        return dispatch();
    }

    public Result saveAvatar() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        final  HttpServletRequest request = getRequest();
        String path = request.getParameter("path");
        if (!StringUtils.hasText(path)) {
            result.put("success", false);
            result.put("msg", "头像路径错误");
            return json(JSONObject.fromObject(result));
        }
        loginUser.setHead(path);
        memberService.update(loginUser);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result saveMyInfo() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        loginUser.setNickName(StringUtils.htmlEncode(member.getNickName()));
        loginUser.setTelephone(StringUtils.htmlEncode(member.getTelephone()));
        loginUser.setEmail(StringUtils.htmlEncode(member.getEmail()));
        loginUser.setGender(member.getGender());
        loginUser.setUserName(StringUtils.htmlEncode(member.getUserName()));
        loginUser.setIdNumber(StringUtils.htmlEncode(member.getIdNumber()));
        loginUser.setBankNo(StringUtils.htmlEncode(member.getBankNo()));
        memberService.update(loginUser);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result myContact() {
        return dispatch();
    }

    public Result countMyTourist() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        Tourist tourist = new Tourist();
        tourist.setUser(loginUser);
        Long count = touristService.countMyTourist(tourist);
        result.put("success", true);
        result.put("count", count);
        return json(JSONObject.fromObject(result));
    }

    public Result getMyTourist() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        Page page = new Page(pageNo, pageSize);
        Tourist condition = new Tourist();
        condition.setUser(loginUser);
        List<Tourist> tourists = touristService.list(condition, page);
        List<OrderTouristVo> touristVos = new ArrayList<OrderTouristVo>();
        for (Tourist tourist : tourists) {
            OrderTouristVo touristVo = new OrderTouristVo();
            touristVo.setId(tourist.getId());
            touristVo.setName(tourist.getName());
            touristVo.setGender(tourist.getGender() != null ? tourist.getGender().getDescription(): "");
            if (StringUtils.hasText(tourist.getTel())) {
                touristVo.setTel(tourist.getTel().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            }
            touristVo.setIdType(tourist.getIdType().getDescription());
            touristVo.setType(tourist.getPeopleType().getDescription());
            touristVo.setIdNumber(tourist.getIdNumber().replaceAll("(\\d{1})\\d{" + (tourist.getIdNumber().length() - 2) + "}(\\d{1})", "$1******$2"));
            touristVos.add(touristVo);
        }
        result.put("success", true);
        result.put("data", touristVos);
        return json(JSONObject.fromObject(result));
    }

    public Result getTouristInfo() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        final HttpServletRequest request = getRequest();
        String idStr = request.getParameter("id");
        if (!StringUtils.hasText(idStr)) {
            result.put("success", false);
            result.put("msg", "id不可为空");
            return json(JSONObject.fromObject(result));
        }
        Long id = Long.parseLong(idStr);
        Tourist sourceTourist = touristService.get(id);
        if (!sourceTourist.getUser().getId().equals(loginUser.getId())) {
            result.put("success", false);
            result.put("msg", "用户校验失败");
            return json(JSONObject.fromObject(result));
        }
        Field[] touristFields = sourceTourist.getClass().getDeclaredFields();
        try {
            for (Field field : touristFields) {
                if (!"user".equals(field.getName())) {
                    field.setAccessible(true);
                    result.put("tourist." + field.getName(), field.get(sourceTourist));
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        // 日期处理
        result.put("birthday", DateUtils.format(sourceTourist.getBirthday(), "yyyy-MM-dd"));
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result doSaveTourist() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        String birStr = (String) getParameter("birthday");
        // 数据处理
        if (StringUtils.hasText(tourist.getTel()) && !StringUtils.isMobile(tourist.getTel())) {
            result.put("success", false);
            result.put("msg", "手机格式不正确!");
            return json(JSONObject.fromObject(result));
        }
//        if (StringUtils.hasText(tourist.getEmail()) && !StringUtils.isEmail(tourist.getEmail())) {
//            result.put("success", false);
//            result.put("msg", "邮箱格式不正确!");
//            return json(JSONObject.fromObject(result));
//        }
        if (tourist.getId() > 0) {
            Tourist sourceTourist = touristService.get(tourist.getId());
            if (!sourceTourist.getUser().getId().equals(loginUser.getId())) {
                result.put("success", false);
                result.put("msg", "用户校验失败");
                return json(JSONObject.fromObject(result));
            }
            BeanUtils.copyProperties(tourist, sourceTourist, false, null, "id");
            // 日期处理
            if (StringUtils.hasText(birStr)) {
                sourceTourist.setBirthday(DateUtils.getDate(birStr, "yyyy-MM-dd"));
            }
            touristService.update(sourceTourist);
        } else {
            // 检查重复
            Integer existNum = touristService.countMyTouristByidNumber(loginUser, tourist.getIdNumber());
            if (existNum > 0) {
                result.put("success", false);
                result.put("msg", "联系人已经存在!");
                return json(JSONObject.fromObject(result));
            }
            // 日期处理
            if (StringUtils.hasText(birStr)) {
                tourist.setBirthday(DateUtils.getDate(birStr, "yyyy-MM-dd"));
            }
            tourist.setCreateTime(new Date());
            tourist.setStatus(TouristStatus.normal);
            tourist.setUser(loginUser);
            touristService.save(tourist);
        }
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result doDelTourist() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        String idStr = (String) getParameter("id");
        if (!StringUtils.hasText(idStr)) {
            result.put("success", false);
            result.put("msg", "id不可为空");
            return json(JSONObject.fromObject(result));
        }
        Long id = Long.parseLong(idStr);
        Tourist tourist = touristService.get(id);
        if (!loginUser.getId().equals(tourist.getUser().getId())) {
            result.put("success", false);
            result.put("msg", "用户校验失败");
            return json(JSONObject.fromObject(result));
        }
        tourist.setStatus(TouristStatus.deleted);
        touristService.update(tourist);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Result myWallet() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        balance = balanceService.findYhyBalance(loginUser.getId());
        return dispatch();
    }

    public Result countConsumeRecord() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        AccountLog condition = new AccountLog();
        List<AccountStatus> statuses = Lists.newArrayList(AccountStatus.normal, AccountStatus.processing);
        condition.setAccountUser(loginUser);
        condition.setInStatus(statuses);
        Long count = accountLogService.countAccountLog(condition);
        result.put("success", true);
        result.put("count", count);
        return json(JSONObject.fromObject(result));
    }

    public Result getConsumeRecord() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            result.put("success", false);
            result.put("msg", "请先登录!");
            result.put("code", "req_login");
            return json(JSONObject.fromObject(result));
        }
        Page page = new Page(pageNo, pageSize);
        AccountLog condition = new AccountLog();
        List<AccountStatus> statuses = Lists.newArrayList(AccountStatus.normal, AccountStatus.processing);
        condition.setAccountUser(loginUser);
        condition.setInStatus(statuses);
        List<AccountLog> accountLogs = accountLogService.listAccountLog(condition, page, "createTime", "desc");
        List<AccountLogVo> accountLogVos = new ArrayList<AccountLogVo>();
        for (AccountLog accountLog : accountLogs) {
            AccountLogVo accountLogVo = new AccountLogVo();
            accountLogVo.setId(accountLog.getId());
            accountLogVo.setCreateTime(DateUtils.format(accountLog.getCreateTime(), "yyyy-MM-dd"));
            accountLogVo.setBalance(Double.toString(accountLog.getBalance()));
            accountLogVo.setMoney(Double.toString(accountLog.getMoney()));
            if (accountLog.getOrderType() != null) {
                accountLogVo.setType(accountLog.getOrderType().getDescription());
            }
            accountLogVo.setAccountType(accountLog.getType().getDescription());
            accountLogVos.add(accountLogVo);
        }
        result.put("success", true);
        result.put("data", accountLogVos);
        return json(JSONObject.fromObject(result));
    }

    public Result myRecharge() {
        return dispatch();
    }

    public Result hotelOrderDetail() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        final HttpServletRequest request = getRequest();
        String idStr = request.getParameter("id");
        String typeStr = request.getParameter("type");
        if (!StringUtils.hasText(idStr) || !StringUtils.hasText(typeStr)) {
            return redirect("/yhypc/personal/index.jhtml");
        }
        Long id = Long.parseLong(idStr);
        OrderType type = OrderType.valueOf(typeStr);
        OrderAll orderAll = orderService.getOrderAll(id, type);
        if (orderAll.getUserid() == null || !orderAll.getUserid().equals(loginUser.getId())) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        if (!OrderType.hotel.equals(orderAll.getOrderType())) {
            return redirect("/yhypc/personal/index.jhtml");
        }
        this.makeOrderAllVo(orderAll);
        Integer num = orderAll.getNum();
        Integer day = orderAll.getDay();
        orderAllVo.setNum(num);
        orderAllVo.setDay(day);
        if (num > 0 && day > 0) {
            orderAllVo.setUnitPrice(orderAll.getPrice() / (num * day));
        }
        // 验证码获取
        List<ProValidCode> validateCodes = proValidCodeService.getByOrder(orderAll.getId());
        List<PvCodeVo> pvCodeVos = this.getPvCodeVo(validateCodes);
        //
        List<OrderTourist> orderTourists = orderTouristService.getByOrderId(orderAll.getId());
        List<OrderTouristVo> touristVos = this.getTouristVos(orderTourists);
        orderAllVo.setPvCodeVos(pvCodeVos);
        orderAllVo.setTouristVos(touristVos);
        Order order = orderService.get(id);
        OrderDetail orderDetail = order.getOrderDetails().get(0);
        OrderAllVo detailVo = new OrderAllVo();
        detailVo.setId(orderDetail.getId());
        Hotel hotel = (Hotel) orderDetail.getProduct();
        detailVo.setName(hotel.getName());
        detailVo.setProId(hotel.getId());
        detailVo.setPriceId(orderDetail.getCostId());
        Productimage hotelImage = productimageService.findCover(hotel.getId(), null, ProductType.hotel);
        if (hotelImage != null) {
            detailVo.setCover(hotelImage.getCompletePath());
        }
        orderAllVo.setHasComment(order.getHasComment());
        orderAllVo.setDetails(Lists.newArrayList(detailVo));
        return dispatch();
    }



    public Result sailboatOrderDetail() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        final HttpServletRequest request = getRequest();
        String idStr = request.getParameter("id");
        String typeStr = request.getParameter("type");
        if (!StringUtils.hasText(idStr) || !StringUtils.hasText(typeStr)) {
            return redirect("/yhypc/personal/index.jhtml");
        }
        Long id = Long.parseLong(idStr);
        OrderType type = OrderType.valueOf(typeStr);
        OrderAll orderAll = orderService.getOrderAll(id, type);
        if (orderAll.getUserid() == null || !orderAll.getUserid().equals(loginUser.getId())) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        if (!OrderType.sailboat.equals(orderAll.getOrderType()) && !OrderType.yacht.equals(orderAll.getOrderType()) && !OrderType.huanguyou.equals(orderAll.getOrderType())) {
            return redirect("/yhypc/personal/index.jhtml");
        }
        this.makeOrderAllVo(orderAll);
        Integer num = orderAll.getNum();
        orderAllVo.setNum(num);
        if (num > 0) {
            orderAllVo.setUnitPrice(orderAll.getPrice() / num);
        }
        //
        List<ProValidCode> validateCodes = proValidCodeService.getByOrder(orderAll.getId());
        List<PvCodeVo> pvCodeVos = this.getPvCodeVo(validateCodes);
        //
        List<OrderTourist> orderTourists = orderTouristService.getByOrderId(orderAll.getId());
        List<OrderTouristVo> touristVos = this.getTouristVos(orderTourists);
        orderAllVo.setPvCodeVos(pvCodeVos);
        orderAllVo.setTouristVos(touristVos);
        Order order = orderService.get(id);
        OrderDetail orderDetail = order.getOrderDetails().get(0);
        OrderAllVo detailVo = new OrderAllVo();
        detailVo.setId(orderDetail.getId());
        Ticket ticket = (Ticket) orderDetail.getProduct();
        detailVo.setName(ticket.getName());
        detailVo.setProId(ticket.getId());
        detailVo.setPriceId(orderDetail.getCostId());
        Productimage sailImage = productimageService.findCover(ticket.getId(), null, ProductType.scenic);
        if (sailImage != null) {
            detailVo.setCover(sailImage.getCompletePath());
        }
        orderAllVo.setHasComment(order.getHasComment());
        orderAllVo.setDetails(Lists.newArrayList(detailVo));
        return dispatch();
    }

    public Result yachtOrderDetail() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        final HttpServletRequest request = getRequest();
        String idStr = request.getParameter("id");
        String typeStr = request.getParameter("type");
        if (!StringUtils.hasText(idStr) || !StringUtils.hasText(typeStr)) {
            return redirect("/yhypc/personal/index.jhtml");
        }
        return dispatch("/yhypc/personal/sailboatOrderDetail.jhtml?id=" + idStr + "&type=" + typeStr);
    }

    public Result huanguyouOrderDetail() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        final HttpServletRequest request = getRequest();
        String idStr = request.getParameter("id");
        String typeStr = request.getParameter("type");
        if (!StringUtils.hasText(idStr) || !StringUtils.hasText(typeStr)) {
            return redirect("/yhypc/personal/index.jhtml");
        }
        return dispatch("/yhypc/personal/sailboatOrderDetail.jhtml?id=" + idStr + "&type=" + typeStr);
    }

    public Result ferryOrderDetail() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        final HttpServletRequest request = getRequest();
        String idStr = request.getParameter("id");
        String typeStr = request.getParameter("type");
        if (!StringUtils.hasText(idStr) || !StringUtils.hasText(typeStr)) {
            return redirect("/yhypc/personal/index.jhtml");
        }
        Long id = Long.parseLong(idStr);
        OrderType type = OrderType.valueOf(typeStr);
        OrderAll orderAll = orderService.getOrderAll(id, type);
        if (orderAll.getUserid() == null || !orderAll.getUserid().equals(loginUser.getId())) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        if (!OrderType.ferry.equals(orderAll.getOrderType())) {
            return redirect("/yhypc/personal/index.jhtml");
        }
        this.makeOrderAllVo(orderAll);
        Integer num = orderAll.getNum();
        orderAllVo.setNum(num);
        if (num > 0) {
            orderAllVo.setUnitPrice(orderAll.getPrice() / num);
        }
        // 轮渡船票订单联系人
        FerryOrder ferryOrder = ferryOrderService.getOrder(id);
        List<FerryOrderItem> ferryOrderItems = ferryOrder.getFerryOrderItemList();
        List<OrderTouristVo> touristVos = new ArrayList<OrderTouristVo>();
        for (FerryOrderItem ferryOrderItem : ferryOrderItems) {
            OrderTouristVo orderTouristVo = new OrderTouristVo();
            orderTouristVo.setId(ferryOrderItem.getId());
            orderTouristVo.setName(ferryOrderItem.getName());
            orderTouristVo.setType(ferryOrderItem.getIdTypeName().getDescription());
            orderTouristVo.setIdNumber(ferryOrderItem.getIdnumber());
            orderTouristVo.setTel(ferryOrderItem.getMobile());
            touristVos.add(orderTouristVo);
        }
        orderAllVo.setTouristVos(touristVos);
        return dispatch();
    }

    public Result ljOrderDetail() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        response = lvjiWebService.orderDetail(orderId);
        return dispatch();
    }

    public Result ticketOrderDetail() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        final HttpServletRequest request = getRequest();
        String idStr = request.getParameter("id");
        String typeStr = request.getParameter("type");
        if (!StringUtils.hasText(idStr) || !StringUtils.hasText(typeStr)) {
            return redirect("/yhypc/personal/index.jhtml");
        }
        Long id = Long.parseLong(idStr);
        OrderType type = OrderType.valueOf(typeStr);
        OrderAll orderAll = orderService.getOrderAll(id, type);
        if (orderAll.getUserid() == null || !orderAll.getUserid().equals(loginUser.getId())) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        if (!OrderType.ticket.equals(orderAll.getOrderType())) {
            return redirect("/yhypc/personal/index.jhtml");
        }
        this.makeOrderAllVo(orderAll);
        Integer num = orderAll.getNum();
        orderAllVo.setNum(num);
        if (num > 0) {
            orderAllVo.setUnitPrice(orderAll.getPrice() / num);
        }
        //
        List<ProValidCode> validateCodes = proValidCodeService.getByOrder(orderAll.getId());
        List<PvCodeVo> pvCodeVos = this.getPvCodeVo(validateCodes);
        //
        List<OrderTourist> orderTourists = orderTouristService.getByOrderId(orderAll.getId());
        List<OrderTouristVo> touristVos = this.getTouristVos(orderTourists);
        orderAllVo.setPvCodeVos(pvCodeVos);
        orderAllVo.setTouristVos(touristVos);
        Order order = orderService.get(id);
        OrderDetail orderDetail = order.getOrderDetails().get(0);
        OrderAllVo detailVo = new OrderAllVo();
        detailVo.setId(orderDetail.getId());
        Ticket ticket = (Ticket) orderDetail.getProduct();
        ScenicInfo scenicInfo = ticket.getScenicInfo();
        detailVo.setName(scenicInfo.getName());
        detailVo.setProId(scenicInfo.getId());
        detailVo.setPriceId(orderDetail.getCostId());
        detailVo.setCover(cover(scenicInfo.getCover()));
        orderAllVo.setHasComment(order.getHasComment());
        orderAllVo.setDetails(Lists.newArrayList(detailVo));
        return dispatch();
    }

    public Result cruiseshipOrderDetail() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        final HttpServletRequest request = getRequest();
        String idStr = request.getParameter("id");
        String typeStr = request.getParameter("type");
        if (!StringUtils.hasText(idStr) || !StringUtils.hasText(typeStr)) {
            return redirect("/yhypc/personal/index.jhtml");
        }
        Long id = Long.parseLong(idStr);
        OrderType type = OrderType.valueOf(typeStr);
        OrderAll orderAll = orderService.getOrderAll(id, type);
        if (orderAll.getUserid() == null || !orderAll.getUserid().equals(loginUser.getId())) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        if (!OrderType.cruiseship.equals(orderAll.getOrderType())) {
            return redirect("/yhypc/personal/index.jhtml");
        }
        this.makeOrderAllVo(orderAll);
        Integer num = orderAll.getNum();
        orderAllVo.setNum(num);
        if (num > 0) {
            orderAllVo.setUnitPrice(orderAll.getPrice() / num);
        }
        return dispatch();
    }

    public Result planOrderDetail() {
        Member loginUser = getLoginUser();
        if (loginUser == null) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        final HttpServletRequest request = getRequest();
        String idStr = request.getParameter("id");
        String typeStr = request.getParameter("type");
        if (!StringUtils.hasText(idStr) || !StringUtils.hasText(typeStr)) {
            return redirect("/yhypc/personal/index.jhtml");
        }
        Long id = Long.parseLong(idStr);
        OrderType type = OrderType.valueOf(typeStr);
        Order order = orderService.get(id);
        OrderAll orderAll = orderService.getOrderAll(id, type);
        if (orderAll.getUserid() == null || !orderAll.getUserid().equals(loginUser.getId())) {
            return  dispatch("/yhypc/personal/index.jhtml");
        }
        if (!OrderType.plan.equals(orderAll.getOrderType())) {
            return redirect("/yhypc/personal/index.jhtml");
        }
        this.makeOrderAllVo(orderAll);
        // ferry order
        List<FerryOrder> ferryOrders = ferryOrderService.getByOrderId(id);
        this.makeFerryOrder(ferryOrders);
        // hotel order, ticket order
        List<OrderDetail> orderDetails = order.getOrderDetails();
        this.makeOrderDetail(orderDetails);
        return dispatch();
    }


    public void makeOrderAllVo(OrderAll orderAll) {
        orderAllVo.setId(orderAll.getId());
        orderAllVo.setName(orderAll.getName().replaceAll("^(.{8}).*$", "$1..."));
        orderAllVo.setFullName(orderAll.getName());
        orderAllVo.setOrderNo(orderAll.getOrderNo());
        orderAllVo.setPrice(orderAll.getPrice());
        orderAllVo.setSeatType(orderAll.getSeatType());
        orderAllVo.setPlayDate(DateUtils.format(orderAll.getPlayDate(), "yyyy-MM-dd"));
        orderAllVo.setLeaveDate(DateUtils.format(orderAll.getLeaveDate(), "yyyy-MM-dd"));
        orderAllVo.setStartDate(DateUtils.format(orderAll.getStartDate(), "yyyy-MM-dd HH:mm"));
        orderAllVo.setEndDate(DateUtils.format(orderAll.getEndDate(), "yyyy-MM-dd"));
        orderAllVo.setUserName(orderAll.getUserName());
        orderAllVo.setMobile(orderAll.getMobile());
        orderAllVo.setType(orderAll.getOrderType().toString());
        orderAllVo.setStatus(orderAll.getStatus().getDescription());
        orderAllVo.setSource(orderAll.getSource() != null ? orderAll.getSource().toString() : "LXB");
        orderAllVo.setCreateTime(DateUtils.format(orderAll.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
    }

    private List<PvCodeVo> getPvCodeVo(List<ProValidCode> validateCodes) {
        List<PvCodeVo> pvCodeVos = new ArrayList<PvCodeVo>();
        for (ProValidCode proValidCode : validateCodes) {
            PvCodeVo pvCodeVo = new PvCodeVo();
            pvCodeVo.setId(proValidCode.getId());
            pvCodeVo.setCode(proValidCode.getCode());
            if (proValidCode.getUsed() == 1) {
                pvCodeVo.setStatus("已验");
            } else if (proValidCode.getUsed() == 0) {
                pvCodeVo.setStatus("未验");
            } else if (proValidCode.getUsed() == -1) {
                pvCodeVo.setStatus("无效");
            } else {
                pvCodeVo.setStatus("未知");
            }
            pvCodeVos.add(pvCodeVo);
        }
        return pvCodeVos;
    }

    private List<OrderTouristVo> getTouristVos(List<OrderTourist> orderTourists) {
        List<OrderTouristVo> touristVos = new ArrayList<OrderTouristVo>();
        for (OrderTourist orderTourist : orderTourists) {
            OrderTouristVo orderTouristVo = new OrderTouristVo();
            orderTouristVo.setId(orderTourist.getId());
            if (orderTourist.getPeopleType() != null) {
                orderTouristVo.setType(orderTourist.getPeopleType().getDescription());
            }
            orderTouristVo.setName(orderTourist.getName());
            if (StringUtils.isNotBlank(orderTourist.getIdNumber())) {
                orderTourist.setIdNumber(orderTourist.getIdNumber().replaceAll("(\\d{3})\\d{11}(\\d{4})", "$1******$2"));
            }
            if (StringUtils.isNotBlank(orderTourist.getTel())) {
                orderTouristVo.setTel(orderTourist.getTel().replaceAll("(\\d{3})\\d{4}(\\d{4})", "$1****$2"));
            }
            touristVos.add(orderTouristVo);
        }
        return touristVos;
    }

    private void makeFerryOrder(List<FerryOrder> ferryOrders) {
        for (FerryOrder ferryOrder : ferryOrders) {
            OrderAllVo ferryOrderAllVo = new OrderAllVo();
            ferryOrderAllVo.setId(ferryOrder.getId());
            ferryOrderAllVo.setName(ferryOrder.getFlightLineName());
            ferryOrderAllVo.setSeatType(ferryOrder.getFlightNumber());
            ferryOrderAllVo.setStartDate(ferryOrder.getDepartTime());
            ferryOrderAllVo.setPrice(ferryOrder.getAmount());
            List<FerryOrderItem> ferryOrderItems = ferryOrder.getFerryOrderItemList();
            List<OrderAllVo> details = new ArrayList<>();
            Map<String, OrderAllVo> detailMap = new HashMap<String, OrderAllVo>();
            // ferry order items
            for (FerryOrderItem ferryOrderItem : ferryOrderItems) {
                OrderAllVo detailVo = detailMap.get(ferryOrderItem.getTicketId());
                if (detailVo != null) {
                    detailVo.setNum(detailVo.getNum() + 1);
                } else {
                    detailVo = new OrderAllVo();
                    detailVo.setSeatType(ferryOrderItem.getTicketName());
                    detailVo.setPrice(ferryOrderItem.getPrice());
                    detailVo.setNum(1);
                    detailMap.put(ferryOrderItem.getTicketId(), detailVo);
                }
            }
            for (OrderAllVo orderAllVo : detailMap.values()) {
                details.add(orderAllVo);
            }
            ferryOrderAllVo.setDetails(details);
            ferryOrderAllVos.add(ferryOrderAllVo);
        }
    }

    private void makeOrderDetail(List<OrderDetail> orderDetails) {
        for (OrderDetail orderDetail : orderDetails) {
            Product product = orderDetail.getProduct();
            OrderAllVo orderAllVo = new OrderAllVo();
            orderAllVo.setName(orderDetail.getProduct().getName());
            orderAllVo.setPlayDate(DateUtils.format(orderDetail.getPlayDate(), "yyyy-MM-dd"));
            List<OrderTourist> orderTourists = orderDetail.getOrderTouristList();
            List<OrderTouristVo> touristVos = this.getTouristVos(orderTourists);
            orderAllVo.setTouristVos(touristVos);
            // OrderAllVo detail
            OrderAllVo detailVo = new OrderAllVo();
            detailVo.setSeatType(orderDetail.getSeatType());
            detailVo.setNum(orderDetail.getNum());
            detailVo.setDay(orderDetail.getDay());
            detailVo.setPrice(orderDetail.getTotalPrice());
            detailVo.setHasComment(orderDetail.getHasComment());
            detailVo.setStatus(orderDetail.getStatus().getDescription());
            if (ProductType.hotel.equals(orderDetail.getProductType())) {
                detailVo.setId(orderDetail.getId());
                Hotel hotel = (Hotel) orderDetail.getProduct();
                detailVo.setName(hotel.getName());
                detailVo.setProId(hotel.getId());
                detailVo.setPriceId(orderDetail.getCostId());
                detailVo.setProType(ProductType.hotel.name());
                Productimage hotelImage = productimageService.findCover(hotel.getId(), null, ProductType.hotel);
                if (hotelImage != null) {
                    detailVo.setCover(hotelImage.getCompletePath());
                }
                OrderAllVo hotelOrderAllVo = hotelOrderAllVoMap.get(product.getId());
                if (hotelOrderAllVoMap.get(product.getId()) != null) {
                    hotelOrderAllVo.getDetails().add(detailVo);
                    hotelOrderAllVo.setPrice(hotelOrderAllVo.getPrice() + orderDetail.getTotalPrice());
                } else {
                    orderAllVo.setPrice(orderDetail.getTotalPrice());
                    List<OrderAllVo> details = new ArrayList<OrderAllVo>();
                    details.add(detailVo);
                    orderAllVo.setDetails(details);
                    hotelOrderAllVoMap.put(product.getId(), orderAllVo);
                }
            } else if (ProductType.scenic.equals(orderDetail.getProductType())) {
                detailVo.setId(orderDetail.getId());
                Ticket ticket = (Ticket) orderDetail.getProduct();
                ScenicInfo scenicInfo = ticket.getScenicInfo();
                detailVo.setName(scenicInfo.getName());
                detailVo.setProId(scenicInfo.getId());
                detailVo.setPriceId(orderDetail.getCostId());
                detailVo.setProType(ProductType.scenic.name());
                detailVo.setCover(cover(scenicInfo.getCover()));
                OrderAllVo ticketOrderAllVo = ticketOrderAllVoMap.get(product.getId());
                if (ticketOrderAllVoMap.get(product.getId()) != null) {
                    ticketOrderAllVo.getDetails().add(detailVo);
                    ticketOrderAllVo.setPrice(ticketOrderAllVo.getPrice() + orderDetail.getTotalPrice());
                } else {
                    orderAllVo.setPrice(orderDetail.getTotalPrice());
                    List<OrderAllVo> details = new ArrayList<OrderAllVo>();
                    details.add(detailVo);
                    orderAllVo.setDetails(details);
                    ticketOrderAllVoMap.put(product.getId(), orderAllVo);
                }
            }
        }
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return "";
        }
        if (cover.startsWith("http")) {
            return cover;
        } else {
            return QiniuUtil.URL + cover;
        }
    }


    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public OrderAllVo getOrderAllVo() {
        return orderAllVo;
    }

    public void setOrderAllVo(OrderAllVo orderAllVo) {
        this.orderAllVo = orderAllVo;
    }

    public List<OrderAllVo> getFerryOrderAllVos() {
        return ferryOrderAllVos;
    }

    public void setFerryOrderAllVos(List<OrderAllVo> ferryOrderAllVos) {
        this.ferryOrderAllVos = ferryOrderAllVos;
    }

    public Map<Long, OrderAllVo> getHotelOrderAllVoMap() {
        return hotelOrderAllVoMap;
    }

    public void setHotelOrderAllVoMap(Map<Long, OrderAllVo> hotelOrderAllVoMap) {
        this.hotelOrderAllVoMap = hotelOrderAllVoMap;
    }

    public Map<Long, OrderAllVo> getTicketOrderAllVoMap() {
        return ticketOrderAllVoMap;
    }

    public void setTicketOrderAllVoMap(Map<Long, OrderAllVo> ticketOrderAllVoMap) {
        this.ticketOrderAllVoMap = ticketOrderAllVoMap;
    }

    public String getOrderTypes() {
        return orderTypes;
    }

    public void setOrderTypes(String orderTypes) {
        this.orderTypes = orderTypes;
    }

    public OrderStatus getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderStatus orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getFn() {
        return fn;
    }

    public void setFn(String fn) {
        this.fn = fn;
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

    public Member getMember() {
        return member;
    }

    public void setMember(Member member) {
        this.member = member;
    }

    public Tourist getTourist() {
        return tourist;
    }

    public void setTourist(Tourist tourist) {
        this.tourist = tourist;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
    }

    public Boolean getHasComment() {
        return hasComment;
    }

    public void setHasComment(Boolean hasComment) {
        this.hasComment = hasComment;
    }
}
