package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.enums.Gender;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.enums.CommentStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.util.UUIDUtil;
import com.data.data.hmly.service.discount.CouponService;
import com.data.data.hmly.service.discount.UserCouponService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.UserExinfo;
import com.data.data.hmly.service.lvxbang.DiscountService;
import com.data.data.hmly.service.lvxbang.PersonalOrderService;
import com.data.data.hmly.service.lvxbang.PersonalService;
import com.data.data.hmly.service.lvxbang.response.PersonalCommentResponse;
import com.data.data.hmly.service.lvxbang.response.PersonalOrderResponse;
import com.data.data.hmly.service.lvxbang.response.PersonalPlanResponse;
import com.data.data.hmly.service.lvxbang.response.PersonalRecplanResponse;
import com.data.data.hmly.service.lxbcommon.entity.Coupon;
import com.data.data.hmly.service.lxbcommon.entity.UserCoupon;
import com.data.data.hmly.service.lxbcommon.entity.enums.UserCouponStatus;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.other.OtherFavoriteService;
import com.data.data.hmly.service.plan.PlanService;
import com.data.data.hmly.service.plan.RecommendPlanService;
import com.data.data.hmly.service.plan.entity.Plan;
import com.data.data.hmly.service.plan.entity.RecommendPlan;
import com.data.data.hmly.service.user.TouristService;
import com.data.data.hmly.service.user.entity.Tourist;
import com.data.data.hmly.service.user.entity.enums.TouristIdType;
import com.data.data.hmly.service.user.entity.enums.TouristPeopleType;
import com.data.data.hmly.service.user.entity.enums.TouristStatus;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.io.File;
import java.math.BigInteger;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vacuity on 15/12/16.
 */
public class UserWebAction extends LxbAction {

//    Logger logger = Logger.getLogger(UserWebAction.class);

//    @Resource
//    private UserService userService;
    @Resource
    private MemberService memberService;
//    @Resource
//    private UserExinfoService userExinfoService;
    @Resource
    private OrderService orderService;
    @Resource
    private RecommendPlanService recommendPlanService;
    @Resource
    private PlanService planService;
    @Resource
    private CommentService commentService;
    @Resource
    private TouristService touristService;
    @Resource
    private OtherFavoriteService otherFavoriteService;
    @Resource
    private PersonalOrderService personalOrderService;
    @Resource
    private PersonalService personalService;
    @Resource
    private UserCouponService userCouponService;
    @Resource
    private CouponService couponService;
    @Resource
    private DiscountService discountService;

    private Member user;
    private UserExinfo userExinfo;

    private File userFace;

    private String userFaceFileName;

    private int pageNo;
    private int pageSize;

    // 用户信息
    private String userName;
    private String email;
    private String telephone;
    private String nickName;
    private Gender gender;
    private String birthday;
    // 常用联系人(部分信息字段采用以上用户信息字段)
    private String name;
    private TouristIdType idType;
    private String idNumber;
    private String mobile;
    private TouristPeopleType peopleType;

    //
    private OrderType orderType;
    private String orderNo;
    private String startTime;
    private String endTime;
    private Integer orderCategory;

    private String delIds;

    private Long id;
    private ProductType favoriteType;

    // 游记 1-有效 2-无效
    private Integer status;
    // 行程 true－已出发 2-未出发
    private Boolean started;

    private ProductType commentType;

    // 收藏
    private int allCount;
    private int planCount;
    private int recplanCount;
    private int scenicCount;
    private int hotelCount;
    private int delicacyCount;
    private int lineCount;

    //优惠券使用状态
    private UserCouponStatus userCouponStatus;
    private Long couponId;

    /**
     * 个人中心
     *
     * @return
     */
    public Result index() {
        user = getLoginUser();
        user = memberService.get(user.getId());
        return dispatch();
    }

    public Result updateFace() {

        user = getLoginUser();
//        userExinfo = userExinfoService.getByUserId(user.getId());
        Map<String, Object> map = new HashMap<String, Object>();
        try {
//            System.out.println(userFaceFileName);
            String[] fileNameList = userFaceFileName.split("\\.");
            String path = "user" + "/" + UUIDUtil.getUUID() + "." + fileNameList[1];
            System.out.println(path);
            QiniuUtil.upload(userFace, path);
            user.setHead("/" + path);
            memberService.update(user);
//            userExinfoService.save(userExinfo);
            map.put("success", true);
            map.put("path", path);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        } catch (Exception e) {
            map.put("success", false);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }

    }


    public Result updateUserInfo() {

//        SysSite sysSite = new SysSite();
//        sysSite.setId(-1L);
//        Member oldUser = memberService.findByAccount(telephone, sysSite);
//
//        if (oldUser != null) {
//            Map<String, Object> map = new HashMap<String, Object>();
//            map.put("success", false);
//            map.put("errMsg", "该手机号已注册，请重新输入");
//            JSONObject jsonObject = JSONObject.fromObject(map);
//            return json(jsonObject);
//        }
        user = getLoginUser();
//        userExinfo = userExinfoService.getByUserId(user.getId());
//        if (userExinfo == null) {
//            userExinfo = new UserExinfo();
//            userExinfo.setUser(user);
//        }

        Map<String, Object> map = Maps.newHashMap();

        if (StringUtils.isNotBlank(userName)) {
            user.setUserName(userName);
        }
        if (StringUtils.isNotBlank(email)) {
            user.setEmail(email);
        }
        if (StringUtils.isNotBlank(telephone)) {
//            Integer existCount = userExinfoService.countByTelephone(telephone);
//            if (!telephone.equals(userExinfo.getTelephone())) {
//                if (existCount > 0) {
//                    map.put("success", false);
//                    map.put("errMsg", "该手机号已注册, 请重新输入");
//                    return json(JSONObject.fromObject(map));
//                } else {
////                    user.setAccount(telephone);
//                    userExinfo.setTelephone(telephone);
//                }
//            }
//            userExinfo.setTelephone(telephone);
            user.setTelephone(telephone);
            user.setMobile(telephone);
        }
        if (StringUtils.isNotBlank(nickName)) {
//            userExinfo.setNickName(nickName);
            user.setNickName(nickName);
        }
        if (gender != null) {
//            userExinfo.setGender(gender);
            user.setGender(gender);
        }
        if (StringUtils.isNotBlank(birthday)) {
            Date date = stringToDate(birthday);
//            userExinfo.setBirthday(date);
            user.setBirthday(date);
        }

        try {
//            userService.updateUserInfo(user);
//            userExinfoService.save(userExinfo);
            memberService.update(user);
            // 更新个人信息后应该重新更新session中的当前登录用户信息
            getSession().setAttribute(UserConstans.CURRENT_LOGIN_USER, user);
            getSession().setAttribute("account", user.getAccount());
            getSession().setAttribute("staffName", user.getUserName());
//            getSession().setAttribute("userName", userExinfo.getNickName());
            getSession().setAttribute("userName", user.getNickName());
        } catch (Exception e) {
            map.put("success", false);
            map.put("errMsg", "更新用户信息失败");
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }

        map.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }


    public Result order() {
        //
        user = getLoginUser();
//        user = userService.get(user.getId());
        user = memberService.get(user.getId());
//        user = userService.get(147L);
        return dispatch();
    }

    /**
     * vacuity
     * <p/>
     * 我的订单
     * 订单号
     * 预订日期（s－e）
     * 订单类型
     * 订单状态
     *
     * @return
     */
    public Result getMyOrders() {
        Page pageInfo = new Page(pageNo, pageSize);
        Order order = new Order();
        user = getLoginUser();
//        user = userService.get(147L);
        order.setUser(user);
        if (orderType != null) {
            order.setOrderType(orderType);
        }
        if (StringUtils.isNotBlank(orderNo)) {
            order.setOrderNo(orderNo);
        }

        Date st = stringToDate(startTime);
        Date et = stringToDate(endTime);
        if (orderCategory == null) {
            orderCategory = 0;
        }
        List<Order> orderList = orderService.searchMyOrder(order, orderCategory, st, et, pageInfo);
        List<PersonalOrderResponse> personalOrderResponseList = personalOrderService.processOrder(orderList);

        String[] includeConfig = new String[]{};
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, includeConfig);
        JSONArray jsonArray = JSONArray.fromObject(personalOrderResponseList, jsonConfig);
        return json(jsonArray);
    }

    public Result getCategoryCount() {
        //
        Order order = new Order();
        user = getLoginUser();
//        user = userService.get(147L);
        order.setUser(user);
        if (orderType != null) {
            order.setOrderType(orderType);
        }
        if (StringUtils.isNotBlank(orderNo)) {
            order.setOrderNo(orderNo);
        }

        Date st = stringToDate(startTime);
        Date et = stringToDate(endTime);
        Map<String, Object> map = Maps.newHashMap();
        try {
            Long zeroCount = orderService.myOrderCount(order, 0, st, et);
            Long oneCount = orderService.myOrderCount(order, 1, st, et);
            Long twoCount = orderService.myOrderCount(order, 2, st, et);
            Long threeCount = orderService.myOrderCount(order, 3, st, et);
            Long fourCount = orderService.myOrderCount(order, 4, st, et);

            map.put("success", true);
            map.put("zero", zeroCount);
            map.put("one", oneCount);
            map.put("two", twoCount);
            map.put("three", threeCount);
            map.put("four", fourCount);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        } catch (Exception e) {
            map.put("success", false);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }
    }

    public Result myOrderCount() {
        Order order = new Order();
        user = getLoginUser();
        order.setUser(user);
        if (orderType != null) {
            order.setOrderType(orderType);
        }
        if (StringUtils.isNotBlank(orderNo)) {
            order.setOrderNo(orderNo);
        }

        Date st = stringToDate(startTime);
        Date et = stringToDate(endTime);
        if (orderCategory == null) {
            orderCategory = 0;
        }
        Long orderCount = orderService.myOrderCount(order, orderCategory, st, et);
        return text(orderCount + "");
    }


    /**
     * vacuity
     * <p/>
     * 批量删除订单
     *
     * @return
     */
    public Result delOrders() {

        Map<String, Object> map = Maps.newHashMap();
        user = getLoginUser();
        if (user != null && StringUtils.isNotBlank(delIds)) {
            try {
                orderService.delByIds(delIds, user);
            } catch (Exception e) {
                map.put("success", false);
                JSONObject jsonObject = JSONObject.fromObject(map);
                return json(jsonObject);
            }
        }
        map.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }

    public Result favorite() {
        user = getLoginUser();
//        user = userService.get(user.getId());
        user = memberService.get(user.getId());

        List<Object> list = otherFavoriteService.groupByFavoriteType(user.getId());
        for (Object obj : list) {
            Object[] array = (Object[]) obj;
            if (array[1] == null) {
                continue;
            }
            if (ProductType.plan.toString().equals(array[0])) {
                planCount = ((BigInteger) array[1]).intValue();
            } else if (ProductType.recplan.toString().equals(array[0])) {
                recplanCount = ((BigInteger) array[1]).intValue();
            } else if (ProductType.scenic.toString().equals(array[0])) {
                scenicCount = ((BigInteger) array[1]).intValue();
            } else if (ProductType.hotel.toString().equals(array[0])) {
                hotelCount = ((BigInteger) array[1]).intValue();
            } else if (ProductType.delicacy.toString().equals(array[0])) {
                delicacyCount = ((BigInteger) array[1]).intValue();
            } else if (ProductType.line.toString().equals(array[0])) {
                lineCount = ((BigInteger) array[1]).intValue();
            }
        }
        allCount = planCount + recplanCount + scenicCount + hotelCount + delicacyCount + lineCount;
        return dispatch();
    }

    public Result recplan() {
        user = getLoginUser();
//        user = userService.get(user.getId());
        user = memberService.get(user.getId());
        return dispatch();
    }

    /**
     * vacuity
     * <p/>
     * 我的游记
     *
     * @return
     */
    public Result getMyRecplan() {
        user = getLoginUser();
        Page pageInfo = new Page(pageNo, pageSize);
//        Long t1 = new Date().getTime();
        List<RecommendPlan> recommendPlanList = recommendPlanService.getMyRecplan(user, started, pageInfo);
//        Long t2 = new Date().getTime();
        List<PersonalRecplanResponse> personalRecplanResponseList = personalService.processPersonalRecplan(recommendPlanList, started);
//        Long t3 = new Date().getTime();
//        System.out.println("1:" + (t2 - t1));
//        System.out.println("2:" + (t3 - t2));
        String[] includeConfig = new String[]{};
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, includeConfig);
        JSONArray jsonArray = JSONArray.fromObject(personalRecplanResponseList, jsonConfig);
        return json(jsonArray);
    }

    public Result myRecplanCount() {
        user = getLoginUser();
//        Long t1 = System.currentTimeMillis();
        Long count = recommendPlanService.countMyRecplan(user, started);
//        Long t2 = System.currentTimeMillis();
//        System.err.println(t2 - t1);
        return text(count + "");
    }

    /**
     * vacuity
     * <p/>
     * 批量删除游记
     *
     * @return
     */
    public Result delRecplan() {

        Map<String, Object> map = Maps.newHashMap();

        try {
            recommendPlanService.delById(id);
        } catch (Exception e) {
            map.put("success", false);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }
        map.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }


    /**
     * 我的行程
     *
     * @return
     */
    public Result plan() {
        user = getLoginUser();
//        user = userService.get(user.getId());
        user = memberService.get(user.getId());
        return dispatch();
    }

    public Result myPlanCount() {
        user = getLoginUser();
        Long count = planService.countMyPlan(user.getId(), started);
        return text(count + "");
    }

    /**
     * 行程列表
     *
     * @return
     */
    public Result getMyPlan() {
        user = getLoginUser();
        Page pageInfo = new Page(pageNo, pageSize);
        List<Plan> planList = planService.listMyPlan(user.getId(), started, pageInfo);
        List<PersonalPlanResponse> personalPlanResponseList = personalService.processPersonalPlan(planList, started);
        String[] includeConfig = new String[]{};
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, includeConfig);
        JSONArray jsonArray = JSONArray.fromObject(personalPlanResponseList, jsonConfig);
        return json(jsonArray);
    }

    /**
     * 分页显示行程列表
     *
     * @return
     * @author caiys
     * @date 2015年12月22日 下午5:34:10
     */
    public Result page() {
        user = getLoginUser();
        Page pageInfo = new Page(pageNo, pageSize);
        List<Plan> planList = planService.listMyPlan(user.getId(), null, pageInfo);
        List<PersonalPlanResponse> personalPlanResponseList = personalService.processPersonalPlan(planList, null);
        pageInfo.setData(personalPlanResponseList);
        JSONObject json = JSONObject.fromObject(pageInfo);
        return json(json);
    }

    /**
     * vacuity
     * <p/>
     * 删除行程
     *
     * @return
     */
    public Result delPlans() {

        Map<String, Object> map = Maps.newHashMap();

        try {
            planService.delById(id);
        } catch (Exception e) {
            map.put("success", false);
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }
        map.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }

    /**
     * vacuity
     * 行程转化为游记
     *
     * @return
     */
    public Result planToRecommend() {
        // TODO 行程转化为游记
        Map<String, Object> map = Maps.newHashMap();
        map.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }


    public Result comment() {
        user = getLoginUser();
//        user = userService.get(user.getId());
        user = memberService.get(user.getId());
        return dispatch();
    }

    /**
     * vacuity
     * <p/>
     * 我的点评
     *
     * @return
     */
    public Result getMyComments() {
        user = getLoginUser();
        Page pageInfo = new Page(pageNo, pageSize);
        Comment comment = new Comment();
        comment.setUser(user);
        if (commentType != null) {
            comment.setType(commentType);
        }
        comment.setStatus(CommentStatus.NORMAL);
        List<Comment> commentList = commentService.list(comment, pageInfo);
        List<PersonalCommentResponse> personalCommentResponseList = personalService.processCommentResponse(commentList);
        String[] includeConfig = new String[]{};
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, includeConfig);
        JSONArray jsonArray = JSONArray.fromObject(personalCommentResponseList, jsonConfig);
        return json(jsonArray);
    }

    public Result myCommentCount() {
        user = getLoginUser();
        Comment comment = new Comment();
        comment.setUser(user);
        if (commentType != null) {
            comment.setType(commentType);
        }
        comment.setStatus(CommentStatus.NORMAL);
        Long count = commentService.countMyComment(comment);
        return text(count + "");
    }

    /**
     * vacuity
     * <p/>
     * 批量删除点评
     *
     * @return
     */
    public Result delComments() {

        Map<String, Object> map = Maps.newHashMap();
        user = getLoginUser();
//        user = userService.get(11L);
        if (user != null && StringUtils.isNotBlank(delIds)) {
            try {
                commentService.delByIds(delIds, user);
            } catch (Exception e) {
                map.put("success", false);
                JSONObject jsonObject = JSONObject.fromObject(map);
                return json(jsonObject);
            }
        }

        map.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }


    public Result tourist() {
        user = getLoginUser();
//        user = userService.get(user.getId());
        user = memberService.get(user.getId());
        return dispatch();
    }

    /**
     * vacuity
     * <p/>
     * 我的常用联系人
     *
     * @return
     */
    public Result getMyTourists() {
        Page pageInfo = new Page(pageNo, pageSize);
        user = getLoginUser();
//        user = userService.get(11L);
        List<Tourist> touristList = touristService.getMyTourist(user, name, pageInfo);

        String[] includeConfig = new String[]{};
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, includeConfig);
        JSONArray jsonArray = JSONArray.fromObject(touristList, jsonConfig);
        return json(jsonArray);
    }

    public Result myTouristCount() {
        user = getLoginUser();
        Long count = touristService.myTouristCount(user, name);
        return text(count + "");
    }

    /**
     * vacuity
     * <p/>
     * 批量删除常用联系人
     *
     * @return
     */
    public Result delTourists() {

        Map<String, Object> map = Maps.newHashMap();
        user = getLoginUser();
//        user = userService.get(11L);
        if (user != null && StringUtils.isNotBlank(delIds)) {
            try {
                touristService.delByIds(delIds, user);
            } catch (Exception e) {
                map.put("success", false);
                JSONObject jsonObject = JSONObject.fromObject(map);
                return json(jsonObject);
            }
        }

        map.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }

    /**
     * vacuity
     * <p/>
     * 新增常用联系人
     *
     * @return
     */
    public Result saveTourist() {

        Map<String, Object> map = Maps.newHashMap();
        user = getLoginUser();
//        user = userService.get(11L);
        try {
            Tourist tourist = null;
            if (id != null) {
                tourist = touristService.get(id);
            }
            if (tourist == null) {
                tourist = new Tourist();
                tourist.setCreateTime(new Date());
                tourist.setUser(user);
                tourist.setStatus(TouristStatus.normal);
            }
            if (StringUtils.isNotBlank(name)) {
                tourist.setName(name);
            }
            if (gender != null) {
                tourist.setGender(gender);
            }
            if (idType != null) {
                tourist.setIdType(idType);
            }
            if (StringUtils.isNotBlank(idNumber)) {
                if (!idNumber.equals(tourist.getIdNumber())) {
                    Integer count = touristService.countMyTouristByidNumber(user, idNumber);
                    if (count > 0) {
                        map.put("success", false);
                        map.put("msg", "联系人证件号码重复");
                        JSONObject jsonObject = JSONObject.fromObject(map);
                        return json(jsonObject);
                    }
                }
                tourist.setIdNumber(idNumber);
            }
            if (StringUtils.isNotBlank(telephone)) {
                tourist.setTel(telephone);
            }
            if (StringUtils.isNotBlank(mobile)) {
                tourist.setMobile(mobile);
            }
            if (StringUtils.isNotBlank(email)) {
                tourist.setEmail(email);
            }
            if (peopleType != null) {
                tourist.setPeopleType(peopleType);
            }
            touristService.saveTourist(tourist);
        } catch (Exception e) {
            //
            e.printStackTrace();
            map.put("success", false);
            map.put("msg", "网络繁忙,稍候重试!");
            JSONObject jsonObject = JSONObject.fromObject(map);
            return json(jsonObject);
        }

        map.put("success", true);
        JSONObject jsonObject = JSONObject.fromObject(map);
        return json(jsonObject);
    }

    public Result coupon() {
        return dispatch();
    }

    /**
     * 优惠券列表
     *
     * @return
     */
    public Result getMyCoupon() {
        user = getLoginUser();
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setMember(user);
        userCoupon.setStatus(userCouponStatus);
        List<UserCoupon> list = userCouponService.list(userCoupon, new Page(pageNo, pageSize));
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("member", "coupon");
        JSONArray jsonArray = JSONArray.fromObject(list, jsonConfig);
        return json(jsonArray);
    }

    public Result getCouponCount() {
        user = getLoginUser();
        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setMember(user);
        userCoupon.setStatus(userCouponStatus);
        return json(JSONArray.fromObject(userCouponService.count(userCoupon)));
    }

    public Result addCoupon() {
        user = getLoginUser();
        Coupon coupon = couponService.get(couponId);
        if (coupon == null) {
            result.put("success", false);
            return json(JSONObject.fromObject(result));
        }
        discountService.addUserCoupon(coupon, user);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    private Date stringToDate(String time) {
        if (!StringUtils.isNotBlank(time)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
//            Date date = sdf.parse(time);
            return sdf.parse(time);
        } catch (ParseException e) {
            return null;
        }
    }
   public Result checkedLogin() {
       user = getLoginUser(false);
       Map<String, Object> map = Maps.newHashMap();

       if (user == null) {
           map.put("success", false);
           return json(JSONArray.fromObject(map));
       } else {
           map.put("success", true);
//           if (user.getUserExinfo() != null) {
//               map.put("userName", StringUtils.isBlank(user.getUserExinfo().getNickName()) ? user.getAccount() : user.getUserExinfo().getNickName());
//               getSession().setAttribute("userName", StringUtils.isBlank(user.getUserExinfo().getNickName()) ? user.getAccount() : user.getUserExinfo().getNickName());
//           } else {
//               map.put("userName", user.getAccount());
//               getSession().setAttribute("userName", user.getAccount());
           }
           if (StringUtils.hasText(user.getNickName())) {
               map.put("userName", user.getNickName());
               getSession().setAttribute("userName", user.getNickName());
           } else {
               map.put("userName", user.getAccount());
               getSession().setAttribute("userName", user.getAccount());
           }
           return json(JSONArray.fromObject(map, JsonFilter.getIncludeConfig("userExinfo")));
       }

//    public User getUser() {
//        return user;
//    }
//
//    public void setUser(User user) {
//        this.user = user;
//    }


    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }

    public UserExinfo getUserExinfo() {
        return userExinfo;
    }

    public void setUserExinfo(UserExinfo userExinfo) {
        this.userExinfo = userExinfo;
    }

    public File getUserFace() {
        return userFace;
    }

    public void setUserFace(File userFace) {
        this.userFace = userFace;
    }

    public String getUserFaceFileName() {
        return userFaceFileName;
    }

    public void setUserFaceFileName(String userFaceFileName) {
        this.userFaceFileName = userFaceFileName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public OrderType getOrderType() {
        return orderType;
    }

    public void setOrderType(OrderType orderType) {
        this.orderType = orderType;
    }

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public Integer getOrderCategory() {
        return orderCategory;
    }

    public void setOrderCategory(Integer orderCategory) {
        this.orderCategory = orderCategory;
    }

    public String getDelIds() {
        return delIds;
    }

    public void setDelIds(String delIds) {
        this.delIds = delIds;
    }

    public int getPageNo() {
        return pageNo;
    }

    public void setPageNo(int pageNo) {
        this.pageNo = pageNo;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public TouristIdType getIdType() {
        return idType;
    }

    public void setIdType(TouristIdType idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public TouristPeopleType getPeopleType() {
        return peopleType;
    }

    public void setPeopleType(TouristPeopleType peopleType) {
        this.peopleType = peopleType;
    }

    public ProductType getFavoriteType() {
        return favoriteType;
    }

    public void setFavoriteType(ProductType favoriteType) {
        this.favoriteType = favoriteType;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public ProductType getCommentType() {
        return commentType;
    }

    public void setCommentType(ProductType commentType) {
        this.commentType = commentType;
    }

    public Boolean getStarted() {
        return started;
    }

    public void setStarted(Boolean started) {
        this.started = started;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public UserCouponStatus getUserCouponStatus() {
        return userCouponStatus;
    }

    public void setUserCouponStatus(UserCouponStatus userCouponStatus) {
        this.userCouponStatus = userCouponStatus;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public int getLineCount() {
        return lineCount;
    }

    public void setLineCount(int lineCount) {
        this.lineCount = lineCount;
    }
}
