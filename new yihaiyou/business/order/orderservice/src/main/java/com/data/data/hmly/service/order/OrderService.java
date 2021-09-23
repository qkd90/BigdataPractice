package com.data.data.hmly.service.order;

import com.data.data.hmly.enums.Gender;
import com.data.data.hmly.enums.ResourceMapType;
import com.data.data.hmly.service.SendingMsgService;
import com.data.data.hmly.service.SysResourceMapService;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.common.ProductValidateCodeService;
import com.data.data.hmly.service.common.SerialsCodeService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.ProductValidateCode;
import com.data.data.hmly.service.common.entity.SerialsCode;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.contract.ContractService;
import com.data.data.hmly.service.contract.entity.Contract;
import com.data.data.hmly.service.cruiseship.CruiseShipRoomDateService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoomDate;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SendStatus;
import com.data.data.hmly.service.entity.SendingMsg;
import com.data.data.hmly.service.entity.SysResourceMap;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.entity.HotelPriceCalendar;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.LinedaysProductPriceService;
import com.data.data.hmly.service.line.LinedaysService;
import com.data.data.hmly.service.line.LinetypepricedateService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linedays;
import com.data.data.hmly.service.line.entity.LinedaysProductPrice;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.data.data.hmly.service.line.entity.enums.CombineType;
import com.data.data.hmly.service.order.dao.OrderAllDao;
import com.data.data.hmly.service.order.dao.OrderDao;
import com.data.data.hmly.service.order.dao.OrderDetailDao;
import com.data.data.hmly.service.order.entity.FerryOrder;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderAll;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.enums.CmbOrderStatus;
import com.data.data.hmly.service.order.entity.enums.CommissionLevel;
import com.data.data.hmly.service.order.entity.enums.OrderBillType;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderTouristIdType;
import com.data.data.hmly.service.order.entity.enums.OrderTouristPeopleType;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.order.util.XhtmlrendererUtil;
import com.data.data.hmly.service.order.vo.OrderResult;
import com.data.data.hmly.service.sales.InsuranceService;
import com.data.data.hmly.service.sales.entity.Insurance;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.user.dao.TouristDao;
import com.data.data.hmly.service.user.entity.Tourist;
import com.data.data.hmly.service.user.entity.enums.TouristIdType;
import com.data.data.hmly.service.user.entity.enums.TouristPeopleType;
import com.data.data.hmly.service.user.entity.enums.TouristStatus;
import com.data.data.hmly.util.GenOrderNo;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.Constants;
import com.zuipin.util.DateUtils;
import com.zuipin.util.NumberUtil;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapper;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import freemarker.template.TemplateExceptionHandler;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.criterion.Subqueries;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

/**
 * Created by guoshijie on 2015/10/13.
 */
@Service
public class OrderService {

    private final Logger logger = Logger.getLogger(OrderService.class);
    @Resource
    private PropertiesManager propertiesManager;

    private static final long PRODUCT_CODE_REPOSITORY_ID = 1;

    @Resource
    private OrderDao orderDao;
    @Resource
    private OrderAllDao orderAllDao;
    @Resource
    private OrderDetailDao orderDetailDao;
    @Resource
    private TouristDao touristDao;
    @Resource
    private SysResourceMapService sysResourceMapService;
    @Resource
    private OrderCommissionService orderCommissionService;
    @Resource
    private ProductValidateCodeService productValidateCodeService;
    @Resource
    private SerialsCodeService serialsCodeService;
    @Resource
    private SendingMsgService sendingMsgService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private CommentService commentService;
    @Resource
    private TrafficPriceService trafficPriceService;
    @Resource
    private LineService lineService;
    @Resource
    private InsuranceService insuranceService;
    @Resource
    private OrderInsuranceService orderInsuranceService;
    @Resource
    private LinedaysProductPriceService linedaysProductPriceService;
    @Resource
    private LinedaysService linedaysService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private LinetypepricedateService linetypepricedateService;
    @Resource
    private CruiseShipRoomDateService cruiseShipRoomDateService;
    @Resource
    private ContractService contractService;
    @Resource
    private OrderBillService orderBillService;
    @Resource
    private FerryOrderService ferryOrderService;
    @Resource
    private TicketService ticketService;
    private Object put;
//    @Resource
//    private CtripTicketApiService ctripTicketApiService;
//    @Resource
//    private CtripTicketService ctripTicketService;


    public List<Order> list(Order order, Page page, Boolean isSuperAdmin, Boolean isSiteAdmin, SysUser loginUser, org.hibernate.criterion.Order sortOrder) {
        DetachedCriteria criteria = createAuthorizeDetachedCriteria(order, isSuperAdmin, isSiteAdmin, loginUser);
        if (sortOrder != null && StringUtils.hasText(sortOrder.getPropertyName())) {
            criteria.addOrder(sortOrder);
        }
        List<Order> orders = orderDao.findByCriteria(criteria, page);
        return orders;
    }


    /**
     * 订单可退初步判断
     *
     * @param order
     * @param orderDetail
     * @return
     */
    public Map<String, Object> isAbleToCancel(Order order, OrderDetail orderDetail) {
        Map<String, Object> result = new HashMap<String, Object>();
        OrderStatus orderStatus = order.getStatus();
        OrderDetailStatus orderDetailStatus = orderDetail.getStatus();
        if (OrderStatus.WAIT.equals(orderStatus) || OrderStatus.SUCCESS.equals(orderStatus)
                || OrderStatus.CONFIRMED.equals(orderStatus) || OrderStatus.FAILED.equals(orderStatus)
                || OrderStatus.PARTIAL_FAILED.equals(orderStatus) || OrderStatus.UNCONFIRMED.equals(orderStatus)
                || OrderStatus.PAYED.equals(orderStatus) || OrderStatus.PROCESSED.equals(orderStatus)) {
            if (OrderDetailStatus.WAITING.equals(orderDetailStatus)
                    || OrderDetailStatus.PAYED.equals(orderDetailStatus)
                    || OrderDetailStatus.FAILED.equals(orderDetailStatus)
                    || OrderDetailStatus.SUCCESS.equals(orderDetailStatus)
                    || orderDetailStatus == null) {
                result.put("isAbleToCancel", true);
                result.put("msg", "可退");
                return result;
            } else {
                if (orderDetailStatus.equals(OrderDetailStatus.BOOKING)) {
                    result.put("isAbleToCancel", false);
                    result.put("msg", "预定中,不可退");
                    return result;
                } else if (orderDetailStatus.equals(OrderDetailStatus.CANCELING)) {
                    result.put("isAbleToCancel", false);
                    result.put("msg", "取消中,不可退");
                    return result;
                } else if (orderDetailStatus.equals(OrderDetailStatus.CANCELED)) {
                    result.put("isAbleToCancel", false);
                    result.put("msg", "已取消,不可退");
                    return result;
                } else if (orderDetailStatus.equals(OrderDetailStatus.CHECKIN)) {
                    result.put("isAbleToCancel", false);
                    result.put("msg", "已办理入住,不可退");
                    return result;
                } else if (orderDetailStatus.equals(OrderDetailStatus.CHECKOUT)) {
                    result.put("isAbleToCancel", false);
                    result.put("msg", "已办理退房,不可退");
                    return result;
                } else {
                    result.put("isAbleToCancel", false);
                    result.put("msg", "未知状态,不可退");
                    return result;
                }
            }
        } else {
            if (orderStatus.equals(OrderStatus.REFUND)) {
                result.put("isAbleToCancel", false);
                result.put("msg", "已退款,不可再次取消");
                return result;
            } else if (orderStatus.equals(OrderStatus.CLOSED)) {
                result.put("isAbleToCancel", false);
                result.put("msg", "订单已关闭,无需取消");
                return result;
            } else if (orderStatus.equals(OrderStatus.INVALID)) {
                result.put("isAbleToCancel", false);
                result.put("msg", "无效订单,不可退");
                return result;
            } else if (orderStatus.equals(OrderStatus.PROCESSING)) {
                result.put("isAbleToCancel", false);
                result.put("msg", "预定中,不可退");
                return result;
            } else if (order.getDeleteFlag()) {
                result.put("isAbleToCancel", false);
                result.put("msg", "已删除,不可退");
                return result;
            } else {
                result.put("isAbleToCancel", false);
                result.put("msg", "未知状态,不可退");
                return result;
            }
        }
    }

    public Map<String, Object> handleTicketCancelResult(Map<String, Object> result, String msg, OrderDetail orderDetail, Boolean success) {
        String type = orderDetail.getProductType().toString();
        if (success) {
            logger.error("订单详情(" + type + ")#" + orderDetail.getId() + "_退订成功!供应商订单ID#" + orderDetail.getRealOrderId());
        } else {
            logger.error("订单详情(" + type + ")#" + orderDetail.getId() + "_退订失败! 供应商订单ID#" + orderDetail.getRealOrderId() + "_" + msg);
        }
        result.put("isAbleToCancel", success);
        result.put("msg", msg);
        return result;
    }

    public Order get(Long id) {
        Order order = orderDao.get(id);
        // removeOrderFromDetail(order);
        return order;
    }

    public Order findByOrderNo(Long orderNo) {
        Criteria<Order> c = new Criteria<>(Order.class);
        c.eq("orderNo", String.valueOf(orderNo));
        return orderDao.findUniqueByCriteria(c);
        // removeOrderFromDetail(order);
    }

    public Order findByBillNoAndCmbTime(String billNo, String cmbTime) {
        Criteria<Order> c = new Criteria<>(Order.class);
        c.eq("billNo", billNo);
        c.eq("cmbTime", cmbTime);
        return orderDao.findUniqueByCriteria(c);
    }

    public List<Order> findByCmbStatus(CmbOrderStatus orderStatus) {
        Criteria<Order> c = new Criteria<>(Order.class);
        c.eq("orderStatus", orderStatus);
        return orderDao.findByCriteria(c);
    }

    public Order getByAuthorize(Long id, Boolean isSuperAdmin, Boolean isSiteAdmin, SysUser loginUser) {
        Criteria<Order> criteria = new Criteria<Order>(Order.class);
        criteria.eq("id", id);
        if (!isSuperAdmin) {
            DetachedCriteria orderDetailCriteria = criteria.createCriteria("orderDetails", "orderDetail");
            DetachedCriteria productCriteria = orderDetailCriteria.createCriteria("product");
            DetachedCriteria userCriteria = productCriteria.createCriteria("user");
            userCriteria.add(Restrictions.eq("sysSite.id", loginUser.getSysSite().getId()));
            if (!isSiteAdmin) {
                productCriteria.add(Restrictions.eq("companyUnit.id", loginUser.getSysUnit().getId()));
            }
        }
        return orderDao.findUniqueByCriteria(criteria);
    }

    public List<Order> myOrder(Long userId) {
        Criteria<Order> criteria = new Criteria<Order>(Order.class);
//        criteria.ne("status", OrderStatus.DELETED);
        criteria.ne("deleteFlag", true);
        criteria.eq("user.id", userId);
        return orderDao.findByCriteria(criteria);
    }

    public List<Order> searchMyOrder(Order order, List<OrderType> types, Page page, String... orderParams) {
        Criteria<Order> criteria = new Criteria<Order>(Order.class);
        criteria.eq("user.id", order.getUser().getId());
        if (types.isEmpty()) {
            criteria.or(
                    Restrictions.eq("orderType", OrderType.plan),
                    Restrictions.eq("orderType", OrderType.ticket),
                    Restrictions.eq("orderType", OrderType.hotel),
                    Restrictions.eq("orderType", OrderType.cruiseship),
                    Restrictions.eq("orderType", OrderType.sailboat),
                    Restrictions.eq("orderType", OrderType.yacht),
                    Restrictions.eq("orderType", OrderType.huanguyou));
        } else {
            List<SimpleExpression> simpleExpressions = Lists.newArrayList();
            for (OrderType type : types) {
                simpleExpressions.add(Restrictions.eq("orderType", type));
            }
            criteria.or(simpleExpressions.toArray(new SimpleExpression[simpleExpressions.size()]));
        }
        if (order.getHasComment() != null) {
            criteria.eq("hasComment", order.getHasComment());
            criteria.eq("status", OrderStatus.SUCCESS);
        }
        if (order.getStatus() == null) {
//            criteria.ne("status", OrderStatus.DELETED);
            criteria.ne("deleteFlag", true);
        } else {
            criteria.eq("status", order.getStatus());
        }
        if (orderParams != null && orderParams.length > 0) {
            if (orderParams.length == 1) {
                criteria.orderBy(org.hibernate.criterion.Order.asc(orderParams[0]));
            } else {
                criteria.orderBy(orderParams[0], orderParams[1]);
            }
        }
        if (page == null) {
            return orderDao.findByCriteria(criteria);
        }
        return orderDao.findByCriteria(criteria, page);
    }

    /**
     * 查询视图，获取全部订单
     *
     * @param order
     * @param types
     * @param page
     * @param orderParams
     * @return
     */
    public List<OrderAll> searchMyOrderAll(Order order, List<OrderType> types, Page page, String... orderParams) {
        Criteria<OrderAll> criteria = new Criteria<OrderAll>(OrderAll.class);
        criteria.eq("userid", order.getUser().getId());
        if (types.isEmpty()) {

        } else {
            List<SimpleExpression> simpleExpressions = Lists.newArrayList();
            for (OrderType type : types) {
                simpleExpressions.add(Restrictions.eq("orderType", type));
            }
            criteria.or(simpleExpressions.toArray(new SimpleExpression[simpleExpressions.size()]));
        }
        if (order.getHasComment() != null) {
            criteria.eq("hasComment", order.getHasComment());
            criteria.eq("status", OrderStatus.SUCCESS);
        }
        if (order.getStatus() != null) {
            criteria.eq("status", order.getStatus());
        }
        if (order.getDeleteFlag() != null) {
            criteria.eq("deleteFlag", order.getDeleteFlag());
        }
        if (orderParams != null && orderParams.length > 0) {
            if (orderParams.length == 1) {
                criteria.orderBy(org.hibernate.criterion.Order.asc(orderParams[0]));
            } else {
                criteria.orderBy(orderParams[0], orderParams[1]);
            }
        }
        if (page == null) {
            return orderAllDao.findByCriteria(criteria);
        }
        return orderAllDao.findByCriteria(criteria, page);
    }

    public OrderAll getOrderAll(Long id, OrderType type) {
        Criteria<OrderAll> criteria = new Criteria<>(OrderAll.class);
        criteria.eq("id", id);
        criteria.eq("orderType", type);
        return orderAllDao.findUniqueByCriteria(criteria);
    }

    public OrderAll getOrderAll(String orderNo) {
        Criteria<OrderAll> criteria = new Criteria<>(OrderAll.class);
        criteria.eq("orderNo", orderNo);
        return orderAllDao.findUniqueByCriteria(criteria);
    }

    public Order getOrderByNo(String orderNo) {
        Criteria<Order> criteria = new Criteria<>(Order.class);
        criteria.eq("orderNo", orderNo);
        return orderDao.findUniqueByCriteria(criteria);
    }


    /**
     * 查询视图，获取全部订单数量(用于一海游PC端分页使用)
     *
     * @param order
     * @param types
     * @return
     */
    public Long countMyOrderAll(Order order, List<OrderType> types) {
        Criteria<OrderAll> criteria = new Criteria<OrderAll>(OrderAll.class);
        criteria.eq("userid", order.getUser().getId());
        if (types != null && !types.isEmpty()) {
            List<SimpleExpression> simpleExpressions = Lists.newArrayList();
            for (OrderType type : types) {
                simpleExpressions.add(Restrictions.eq("orderType", type));
            }
            criteria.or(simpleExpressions.toArray(new SimpleExpression[simpleExpressions.size()]));
        }
        if (order.getHasComment() != null) {
            criteria.eq("hasComment", order.getHasComment());
            criteria.eq("status", OrderStatus.SUCCESS);
        }
        if (order.getStatus() != null) {
            criteria.eq("status", order.getStatus());
        }
        if (order.getDeleteFlag() != null) {
            criteria.eq("deleteFlag", order.getDeleteFlag());
        }
        criteria.setProjection(Projections.rowCount());
        return (Long) orderAllDao.findUniqueValue(criteria);
    }

    public List<Order> searchMyOrder(Long userId, Integer orderCategory, Page page) {
        Criteria<Order> criteria = new Criteria<Order>(Order.class);
        criteria.eq("user.id", userId);
        switch (orderCategory) {
            case 0:
                break;
            case 1:
                criteria.eq("status", OrderStatus.PAYED);
                break;
            case 2:
                criteria.eq("status", OrderStatus.WAIT);
                break;
            case 3:
                criteria.eq("status", OrderStatus.REFUND);
                break;
            default:
                break;
        }
        criteria.ne("orderType", OrderType.recharge);
        criteria.ne("orderType", OrderType.withdraw);
        criteria.orderBy("modifyTime", "desc");
        return orderDao.findByCriteria(criteria, page);
    }

    public List<Order> searchMyOrder(Order order, Integer orderCategory, Date st, Date et, Page page) {
        Criteria<Order> criteria = createMyOrderCriteria(order, orderCategory, st, et);
        criteria.ne("orderType", OrderType.recharge);
        criteria.ne("orderType", OrderType.withdraw);
        return orderDao.findByCriteria(criteria, page);
    }

    public Long myOrderCount(Order order, Integer orderCategory, Date st, Date et) {
        Criteria<Order> criteria = createMyOrderCriteria(order, orderCategory, st, et);
        criteria.setProjection(Projections.rowCount());
        return orderDao.findLongCriteria(criteria);
    }

    private Criteria<Order> createMyOrderCriteria(Order order, Integer orderCategory, Date st, Date et) {
        Criteria<Order> criteria = new Criteria<Order>(Order.class);

        criteria.eq("user.id", order.getUser().getId());
        if (order.getOrderType() != null) {
            criteria.eq("orderType", order.getOrderType());
        }
        if (StringUtils.isNotBlank(order.getOrderNo())) {
            criteria.eq("orderNo", order.getOrderNo());
        }
//        criteria.ne("status", OrderStatus.DELETED);
        criteria.ne("deleteFlag", true);
        if (st != null) {
            criteria.gt("createTime", st);
        }
        if (et != null) {
            criteria.lt("createTime", et);
        }
        switch (orderCategory) {
            case 0:
                // 全部
                break;
            case 1:
                // 未出行
                criteria.eq("status", OrderStatus.PAYED);
                criteria.gt("playDate", new Date());
                break;
            case 2:
                // 待付款
                criteria.eq("status", OrderStatus.WAIT);
//                criteria.lt("playDate", new Date());
                break;
            case 3:
                // 退款
                criteria.eq("status", OrderStatus.REFUND);
                break;
            case 4:
                // 待评价
                criteria.eq("status", OrderStatus.PAYED);
//                criteria.le("playDate", new Date());
                criteria.Add(Restrictions.or(Restrictions.eq("orderType", OrderType.hotel), Restrictions.eq("orderType", OrderType.ticket)));
                break;
            default:
                break;
        }
        criteria.orderBy("modifyTime", "desc");
        return criteria;
    }


    public List<Order> list(Order order, Page page, org.hibernate.criterion.Order sortOrder, Product product, User user) {
        DetachedCriteria criteria = createDetachedCriteria(order);
        if (sortOrder != null && sortOrder.getPropertyName() != null) {
            criteria.addOrder(sortOrder);
        }
        List<Order> orders = orderDao.findByCriteria(criteria, page);
        return orders;
    }

    public List<Order> listByAuthorize(Order order, Page page, Boolean isSuperAdmin, Boolean isSiteAdmin, SysUser loginUser, org.hibernate.criterion.Order sortOrder) {
        DetachedCriteria criteria = createAuthorizeDetachedCriteria(order, isSuperAdmin, isSiteAdmin, loginUser);
        if (sortOrder != null && StringUtils.hasText(sortOrder.getPropertyName())) {
            criteria.addOrder(sortOrder);
        }
        List<Order> orders = orderDao.findByCriteria(criteria, page);
        return orders;
    }

    public List<OrderAll> listOrderAll(Order order, Page page, Boolean isSuperAdmin, Boolean isSiteAdmin, SysUser loginUser, org.hibernate.criterion.Order sortOrder) {
        Criteria<OrderAll> criteria = new Criteria<OrderAll>(OrderAll.class);
        if (!isSuperAdmin) {
            if (!isSiteAdmin) {
                criteria.eq("companyUnitId", loginUser.getSysUnit().getId());
            }
        }
//        if (order.getOrderType() != null) {
//            criteria.eq("orderType", order.getOrderType());
//        }
        createOrderAll(order, criteria);
//        criteria.ne("deleteFlag", true);
        criteria.ne("status", OrderStatus.DELETED);
        criteria.orderBy(sortOrder);
        if (page == null) {
            return orderAllDao.findByCriteria(criteria);
        }
        return orderAllDao.findByCriteria(criteria, page);
    }

    /**
     * 查询最新的订单
     * @param order
     * @return
     */
    public List<OrderAll> listLastestOrderAll(Order order, int limit) {
        Criteria<OrderAll> criteria = new Criteria<OrderAll>(OrderAll.class);
        createOrderAll(order, criteria);
//        criteria.ne("deleteFlag", false);
        criteria.orderBy("createTime", "desc");
        return orderAllDao.findByCriteria(criteria, limit);
    }

    private void createOrderAll(Order order, Criteria criteria) {
        if (StringUtils.hasText(order.getRecName())) {
            criteria.like("userName", order.getRecName().trim());
        }
        if (StringUtils.hasText(order.getOrderNo())) {
            criteria.like("orderNo", order.getOrderNo().trim());
        }
        if (StringUtils.hasText(order.getDepartTime())) {
            criteria.like("departTime", order.getDepartTime(), MatchMode.ANYWHERE);
        }
        if (StringUtils.isNotBlank(order.getSearchKeyword())) {
            criteria.or(Restrictions.like("flightNumber", order.getSearchKeyword().trim(), MatchMode.ANYWHERE), Restrictions.like("name", order.getSearchKeyword().trim(), MatchMode.ANYWHERE), Restrictions.like("orderNo", order.getSearchKeyword().trim(), MatchMode.ANYWHERE));
        }
        if (StringUtils.hasText(order.getMobile())) {
            criteria.like("mobile", order.getMobile().trim(), MatchMode.ANYWHERE);
        }
        if (order.getStatus() != null) {
            criteria.eq("status", order.getStatus());
        }
        if (order.getCompanyUnit() != null && order.getCompanyUnit().getId() != null) {
            criteria.eq("companyUnitId", order.getCompanyUnit().getId());
        }
        if (order.getOrderType() != null) {
            criteria.eq("orderType", order.getOrderType());
        }
        if (order.getFilterOrderTypes() != null && !order.getFilterOrderTypes().isEmpty()) {
            criteria.add(Restrictions.not(Restrictions.in("orderType", order.getFilterOrderTypes())));
        }
        if (order.getThirdOrderSources() != null && !order.getThirdOrderSources().isEmpty()) {
            criteria.add(Restrictions.in("source", order.getThirdOrderSources()));
        } else {
            // 后台查询行程有问题，所以注释
//            criteria.add(Restrictions.eq("source", ProductSource.LXB));
        }
        if (order.getPlayTime() != null) {
            criteria.eq("playDate", order.getPlayTime());
        }
        if (order.getLeaveTime() != null) {
            criteria.eq("leaveDate", order.getLeaveTime());
        }
        if (order.getNeededStatuses() != null && !order.getNeededStatuses().isEmpty()) {
            criteria.add(Restrictions.in("status", order.getNeededStatuses()));
        }

        if (order.getIncludeOrderTypes() != null && !order.getIncludeOrderTypes().isEmpty()) {
            criteria.add(Restrictions.in("orderType", order.getIncludeOrderTypes()));
        }

        if (order.getStartTime() != null) {
            criteria.ge("createTime", order.getStartTime());
        }
        if (order.getEndTime() != null) {
            criteria.le("createTime", order.getEndTime());
        }
    }

    // 批量删除
    public void delByIds(String orderIds, User user) {
        List<Long> idList = new ArrayList<Long>();
        for (String id : orderIds.split(",")) {
            if (id != null && !"".equals(id)) {
                idList.add(Long.parseLong(id));
            }
        }
        Criteria<Order> criteria = new Criteria<Order>(Order.class);
        criteria.in("id", idList);
        criteria.eq("user.id", user.getId());
        List<Order> orderList = orderDao.findByCriteria(criteria);
        if (!orderList.isEmpty()) {
            for (Order order : orderList) {
                order.setDeleteFlag(true);
            }
            orderDao.save(orderList);
        }
    }

    public DetachedCriteria createDetachedCriteria(Order order) {
        DetachedCriteria orderCriteria = DetachedCriteria.forClass(Order.class, "order_");
        DetachedCriteria orderDetailCriteria = DetachedCriteria.forClass(OrderDetail.class, "orderDetail");
        DetachedCriteria productCriteria = orderDetailCriteria.createAlias("product", "product_");
        orderCriteria.createCriteria("user");
        if (order != null) {
            createAliasWithOrder(order, orderCriteria, orderDetailCriteria, productCriteria);
        }
        return orderCriteria;
    }

    public DetachedCriteria createAuthorizeDetachedCriteria(Order order, Boolean isSuperAdmin, Boolean isSiteAdmin, SysUser loginUser) {
        DetachedCriteria orderCriteria = DetachedCriteria.forClass(Order.class, "order_");
        DetachedCriteria orderDetailCriteria = DetachedCriteria.forClass(OrderDetail.class, "orderDetail");
        DetachedCriteria productCriteria = orderDetailCriteria.createAlias("product", "product_");
        DetachedCriteria userCriteria = orderDetailCriteria.createAlias("product_.user", "sysUser_");
        if (order != null) {
            createAliasWithOrder(order, orderCriteria, orderDetailCriteria, productCriteria);
        }
        if (!isSuperAdmin) {
            // 注释的写法也可以
//            orderDetailCriteria.createAlias("sysUser_.sysSite", "sysSite_");
            productCriteria.add(Restrictions.eqProperty("product_.user.id", "sysUser_.id"));
//            userCriteria.add(Restrictions.eq("sysSite_.id", loginUser.getSysSite().getId()));
            userCriteria.add(Restrictions.eq("sysUser_.sysSite.id", loginUser.getSysSite().getId()));
            if (!isSiteAdmin) {
                // 注释的写法也可以
//                orderDetailCriteria.createAlias("product_.companyUnit", "companyUnit_");
//                productCriteria.add(Restrictions.eq("companyUnit_.id", loginUser.getSysUnit().getId()));
                productCriteria.add(Restrictions.eq("product_.companyUnit.id", loginUser.getSysUnit().getId()));
            }
        }
        orderDetailCriteria.add(Restrictions.eqProperty("orderDetail.order.id", "order_.id"));
        orderCriteria.add(Subqueries.exists(orderDetailCriteria.setProjection(Projections.property("orderDetail.id"))));
        return orderCriteria;
    }

    private void createAliasWithOrder(Order order, DetachedCriteria orderCriteria, DetachedCriteria orderDetailCriteria, DetachedCriteria productCriteria) {
        if (StringUtils.hasText(order.getRecName())) {
            orderCriteria.add(Restrictions.like("recName", order.getRecName()));
        }
        if (StringUtils.hasText(order.getOrderNo())) {
            orderCriteria.add(Restrictions.like("orderNo", order.getOrderNo()));
        }
        if (StringUtils.isNotBlank(order.getSearchKeyword())) {
            orderCriteria.add(Restrictions.or(Restrictions.like("recName", order.getSearchKeyword()), Restrictions.eq("mobile", order.getSearchKeyword()), Restrictions.eq("orderNo", order.getSearchKeyword())));
        }
        if (StringUtils.hasText(order.getMobile())) {
            orderCriteria.add(Restrictions.like("mobile", order.getMobile(), MatchMode.ANYWHERE));
        }
        if (order.getStatus() != null) {
            orderCriteria.add(Restrictions.eq("status", order.getStatus()));
        }
        if (order.getOrderType() != null) {
            orderCriteria.add(Restrictions.eq("orderType", order.getOrderType()));
        }
        if (order.getFilterOrderTypes() != null && !order.getFilterOrderTypes().isEmpty()) {
            orderCriteria.add(Restrictions.not(Restrictions.in("orderType", order.getFilterOrderTypes())));
        }

        if (order.getThirdOrderSources() != null && !order.getThirdOrderSources().isEmpty()) {
//            orderDetailCriteria.createAlias("product", "product");
            productCriteria.add(Restrictions.in("product_.source", order.getThirdOrderSources()));
//            orderDetailCriteria.add(Restrictions.in("product.source", order.getThirdOrderSources()));
            orderDetailCriteria.add(Restrictions.eqProperty("orderDetail.order.id", "order_.id"));
            if (order.getPlayTime() != null) {
                orderDetailCriteria.add(Restrictions.ge("orderDetail.playDate", order.getPlayTime()));
            }
            if (order.getLeaveTime() != null) {
                orderDetailCriteria.add(Restrictions.ge("orderDetail.leaveDate", order.getLeaveTime()));
            }

            if (order.getOrderDetails() != null && !order.getOrderDetails().isEmpty()) {
                orderDetailCriteria.add(Restrictions.ge("orderDetail.productType", order.getOrderDetails().get(0).getProductType()));
            }

            orderCriteria.add(Subqueries.exists(orderDetailCriteria.setProjection(Projections.property("orderDetail.id"))));
        } else if (order.getThirdOrderSources() != null && order.getThirdOrderSources().isEmpty()) {
//            orderDetailCriteria.createAlias("product", "product");
            productCriteria.add(Restrictions.eq("product_.source", ProductSource.LXB));
//            orderDetailCriteria.add(Restrictions.isNull("product.source"));
            orderDetailCriteria.add(Restrictions.eqProperty("orderDetail.order.id", "order_.id"));
            if (order.getPlayTime() != null) {
                orderDetailCriteria.add(Restrictions.eq("orderDetail.playDate", order.getPlayTime()));
            }
            if (order.getLeaveTime() != null) {
                orderDetailCriteria.add(Restrictions.eq("orderDetail.leaveDate", order.getLeaveTime()));
            }
            if (order.getOrderDetails() != null && !order.getOrderDetails().isEmpty()) {
                orderDetailCriteria.add(Restrictions.eq("orderDetail.productType", order.getOrderDetails().get(0).getProductType()));
            }
            orderCriteria.add(Subqueries.exists(orderDetailCriteria.setProjection(Projections.property("orderDetail.id"))));
        }
        if (order.getNeededStatuses() != null && !order.getNeededStatuses().isEmpty()) {
            orderCriteria.add(Restrictions.in("status", order.getNeededStatuses()));
        }

        if (order.getIncludeOrderTypes() != null && !order.getIncludeOrderTypes().isEmpty()) {
            orderCriteria.add(Restrictions.in("orderType", order.getIncludeOrderTypes()));
        }

        if (order.getStartTime() != null) {
            orderCriteria.add(Restrictions.ge("createTime", order.getStartTime()));
        }
        if (order.getEndTime() != null) {
            orderCriteria.add(Restrictions.le("createTime", order.getEndTime()));
        }
    }


    private void createAliasWithOrder(Criteria<Order> criteria, Order order, DetachedCriteria orderDetailCriteria) {
        if (StringUtils.hasText(order.getRecName())) {
            criteria.like("recName", order.getRecName());
        }
        if (StringUtils.hasText(order.getOrderNo())) {
            criteria.like("orderNo", order.getOrderNo());
        }
        if (StringUtils.hasText(order.getMobile())) {
            criteria.like("mobile", order.getMobile());
        }
        if (order.getStatus() != null) {
            criteria.eq("status", order.getStatus());
        }
        if (order.getOrderType() != null) {
            criteria.eq("orderType", order.getOrderType());
        }
        if (order.getFilterOrderTypes() != null && !order.getFilterOrderTypes().isEmpty()) {
            criteria.notin("orderType", order.getFilterOrderTypes());
        }
        if (order.getThirdOrderSources() != null && !order.getThirdOrderSources().isEmpty()) {
            DetachedCriteria productCriteria = orderDetailCriteria.createCriteria("product");
            productCriteria.add(Restrictions.in("source", order.getThirdOrderSources()));
        } else if (order.getThirdOrderSources() != null && order.getThirdOrderSources().isEmpty()) {
            DetachedCriteria productCriteria = orderDetailCriteria.createCriteria("product");
            productCriteria.add(Restrictions.isNull("source"));
        }
        if (order.getNeededStatuses() != null && !order.getNeededStatuses().isEmpty()) {
            criteria.in("status", order.getNeededStatuses());
        }
    }

    public List<Order> listByCustomer(Order order, Page page, User user, String... orderProperties) {
        Criteria<Order> criteria = createCustomerCriteria(order, user, orderProperties);
        criteria.orderBy("createTime", "desc");
        if (page == null) {
            return orderDao.findByCriteria(criteria);
        }
        return orderDao.findByCriteria(criteria, page);
    }

    public Criteria<Order> createCustomerCriteria(Order order, User user, String... orderProperties) {
        Criteria<Order> criteria = new Criteria<Order>(Order.class);
        DetachedCriteria orderDetailCriteria = criteria.createCriteria("orderDetails", "orderDetails");
//        criteria.ne("status", OrderStatus.DELETED);
        criteria.ne("deleteFlag", true);
        criteria.eq("user.id", user.getId());
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(org.hibernate.criterion.Order.desc(orderProperties[0]));
        }
        if (order != null) {
            createAliasWithOrder(criteria, order, orderDetailCriteria);
        }

        return criteria;
    }

    public void update(Order order) {
        orderDao.update(order);
    }

    public void updateByResult(OrderResult orderResult) {
        Long orderId = orderResult.getOrderId();
        OrderStatus status = orderResult.getOrderStatus();
        String msg = orderResult.getMsg();
        String updateHql = "update Order set status=?,msg=? where id=?";
        orderDao.updateByHQL(updateHql, status, msg, orderId);
    }

    public void save(Order order) {
        order.setOrderNo(NumberUtil.getRunningNo());
        orderDao.save(order);
    }

    public void insert(Order order) {
        order.setOrderNo(NumberUtil.getRunningNo());
        orderDao.save(order);
    }

    public void doCalculateCommission(Order order) {
        List<SysResourceMap> resourceMapList = sysResourceMapService.getByType(ResourceMapType.COMMISSION);
        Map<String, SysResourceMap> commissionMap = Maps.uniqueIndex(resourceMapList, new Function<SysResourceMap, String>() {
            @Override
            public String apply(SysResourceMap sysResourceMap) {
                return sysResourceMap.getName();
            }
        });
        List<OrderDetail> orderDetails = order.getOrderDetails();
        for (OrderDetail orderDetail : orderDetails) {
            User seller = orderDetail.getProduct().getUser();
            // 自己卖出的自己赚一点
            orderCommissionService.createCommionssion(seller, orderDetail, commissionMap, CommissionLevel.SELLER);
            // 上面的一级分一点
            User level1 = seller.getParent();
            if (level1 == null) { // 上面没人了，剩下的都归咱
                continue;
            }
            orderCommissionService.createCommionssion(level1, orderDetail, commissionMap, CommissionLevel.LEVEL1);
            // 再上一级分一点
            User level2 = seller.getGrand();
            if (level2 == null) { // 上面没人了，剩下的都归咱
                continue;
            }
            orderCommissionService.createCommionssion(level2, orderDetail, commissionMap, CommissionLevel.LEVEL2);
            // 处在顶点的还没分到，剩下的一点分给他
            User superior = seller.getSuperior();
            if (!level2.getId().equals(superior.getId())) {
                orderCommissionService.createCommionssion(superior, orderDetail, commissionMap, CommissionLevel.TOP);
            }
        }
    }

    public List<Order> getByUser(User user, Page page) {
        Criteria<Order> criteria = new Criteria<Order>(Order.class);
//        criteria.ne("status", OrderStatus.DELETED);
        criteria.ne("deleteFlag", true);
        criteria.eq("user.id", user.getId());
        if (page == null) {
            return orderDao.findByCriteria(criteria);
        } else {
            return orderDao.findByCriteria(criteria, page);
        }

    }

    @Transactional
    public void saveValidateCode(Order order) {
        List<ProductValidateCode> list = new ArrayList<ProductValidateCode>();
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            List<ProductValidateCode> subList = saveValidateCode(orderDetail);
            list.addAll(subList);
        }
        saveValidateMsg(order, list);
    }

    public List<ProductValidateCode> saveValidateCode(OrderDetail orderDetail) {
        Product product = orderDetail.getProduct();
        List<ProductValidateCode> list = new ArrayList<ProductValidateCode>();
        for (int i = orderDetail.getNum(); i > 0; i--) {
            ProductValidateCode productValidateCode = new ProductValidateCode();
            SerialsCode serialsCode = serialsCodeService.nextCode(PRODUCT_CODE_REPOSITORY_ID);
            productValidateCode.setCode(serialsCode.getCode());
            productValidateCode.setProduct(product);
            productValidateCode.setUsed(0);
            productValidateCode.setUpdateTime(new Date());
            productValidateCode.setCreateTime(new Date());
            productValidateCode.setBuyer(orderDetail.getOrder().getUser());
            productValidateCode.setBuyerName(orderDetail.getOrder().getUser().getUserName());
            productValidateCode.setBuyerMobile(orderDetail.getOrder().getUser().getMobile());
            productValidateCode.setOrderNo(orderDetail.getOrder().getOrderNo());
            productValidateCodeService.save(productValidateCode);
            list.add(productValidateCode);
        }
        return list;
    }

    private void saveValidateMsg(Order order, List<ProductValidateCode> list) {
        SendingMsg sendingMsg = new SendingMsg();
        sendingMsg.setReceivernum(order.getUser().getMobile());
        sendingMsg.setStatus(SendStatus.newed);
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("codeList", list);
        try {
            Configuration cfg = new Configuration();
            cfg.setObjectWrapper(new DefaultObjectWrapper());
            cfg.setClassForTemplateLoading(this.getClass(), "");
            // cfg.setServletContextForTemplateLoading(ContextUtil.servletContext, File.separator);
            cfg.setEncoding(Locale.getDefault(), Constants.UTF8);
            cfg.setTemplateExceptionHandler(TemplateExceptionHandler.IGNORE_HANDLER);
            cfg.setClassicCompatible(true);
            Template template = cfg.getTemplate("ProductSMSTemplate.ftl");
            template.setEncoding(Constants.UTF8);
            String context = FreeMarkerTemplateUtils.processTemplateIntoString(template, data);
            sendingMsg.setContext(context);
            sendingMsgService.save(sendingMsg);
        } catch (IOException e) {
            logger.error("发送验证码失败，加载模板失败", e);
        } catch (TemplateException e) {
            logger.error("发送验证码失败，模板渲染失败", e);
        }
    }


    public Order createLxbOrder(String data, Member user) {
        try {
            Map<String, Object> map = formatMap(data);
            Order order = createOrder(map, data, user);
            orderDao.save(order);

            List<Map<String, Object>> detailList = (List<Map<String, Object>>) map.get("details");
            List<OrderDetail> orderDetailList = orderDetailService.createLxbOrderDetails(detailList, order, true);
            order.setOrderDetails(orderDetailList);

            for (OrderDetail orderDetail : orderDetailList) {
                if (orderDetail.getStatus().equals(OrderDetailStatus.UNCONFIRMED) && order.getStatus().equals(OrderStatus.PROCESSING)) {
                    order.setStatus(OrderStatus.UNCONFIRMED);
                    break;
                }
            }
            if (order.getOrderType().equals(OrderType.hotel) && ProductSource.ELONG.equals(orderDetailList.get(0).getProduct().getSource())) {
                order.setStatus(OrderStatus.PAYED);
            }
            if (order.getStatus().equals(OrderStatus.PROCESSING)) {
                orderWaitTime(order);
            }

            Float price = 0F;
            Date playDate = null;
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                price += calPrice(orderDetail);
                if (playDate == null) {
                    playDate = orderDetail.getPlayDate();
                } else if (playDate.after(orderDetail.getPlayDate())) {
                    playDate = orderDetail.getPlayDate();
                }
            }
            order.setPlayDate(playDate);
            order.setPrice(price);
            if (order.getOrderType().equals(OrderType.plan) && order.getPrice() == 0f) {
                order.setStatus(OrderStatus.FAILED);
                order.setDeleteFlag(true);
            }
            orderDao.save(order);
            updateOrderDetailInventory(order);
            return order;
        } catch (Exception e) {
            // 创建订单失败
            e.printStackTrace();
            logger.error("创建订单失败：" + e.getLocalizedMessage());
        }
        return null;
    }

    private Order createOrder(Map<String, Object> map, String data, Member user) {

        // 订单信息
        Order order = null;
        if (map.get("id") != null) {
            Long orderId = Long.parseLong(map.get("id").toString());
            order = get(orderId);
        }
        if (order == null) {
            order = new Order();
            order.setOrderNo(GenOrderNo.generate(propertiesManager.getString("MACHINE_NO", "")));
            order.setCreateTime(new Date());
        }
        order.setUser(user);
        order.setRecName(map.get("contactName").toString());
        order.setMobile(map.get("contactTelephone").toString());
        order.setName(map.get("name").toString());
        order.setModifyTime(new Date());
        order.setHasComment(false);
        order.setStatus(OrderStatus.PROCESSING);

        String orderType = map.get("orderType").toString();

        if (OrderType.sailboat.name().equals(orderType)) {
            order.setOrderType(OrderType.sailboat);
        } else if (OrderType.yacht.name().equals(orderType)) {
            order.setOrderType(OrderType.yacht);
        } else if (OrderType.plan.name().equals(orderType)) {
            order.setOrderType(OrderType.plan);
            order.setDay(Integer.valueOf(map.get("day").toString()));
        } else if (OrderType.ticket.name().equals(orderType)) {
            order.setOrderType(OrderType.ticket);
        } else if (OrderType.hotel.name().equals(orderType)) {
            order.setOrderType(OrderType.hotel);
        } else if (OrderType.cruiseship.name().equals(orderType)) {
            order.setOrderType(OrderType.cruiseship);
        }
        return order;
    }

    public void orderWaitTime(Order order) {
        order.setStatus(OrderStatus.WAIT);
        Integer minute = null;
        switch (order.getOrderType()) {
            case ticket:
                minute = Integer.valueOf(propertiesManager.getString("ORDER_TICKET_PAY_WAIT_TIME"));
                break;
            case hotel:
                minute = Integer.valueOf(propertiesManager.getString("ORDER_HOTEL_PAY_WAIT_TIME"));
                break;
            case cruiseship:
                minute = Integer.valueOf(propertiesManager.getString("ORDER_CRUISESHIP_PAY_WAIT_TIME"));
                break;
            case ferry:
                minute = Integer.valueOf(propertiesManager.getString("ORDER_FERRY_PAY_WAIT_TIME"));
                break;
            case sailboat:
            case yacht:
            case huanguyou:
                minute = Integer.valueOf(propertiesManager.getString("ORDER_SAILBOAT_PAY_WAIT_TIME"));
                break;
            case plan:
                minute = Integer.valueOf(propertiesManager.getString("ORDER_PLAN_PAY_WAIT_TIME"));
                break;
        }
        if (minute != null) {
            order.setWaitTime(DateUtils.add(new Date(), Calendar.MINUTE, minute));
        }
    }

    public void completeInsuranceDetail(Order order, List<Object> ids) {
        Integer num = 0;
        OrderDetail orderDetail = order.getOrderDetails().get(0);
        for (OrderDetail detail : order.getOrderDetails()) {
            num += detail.getNum();
        }
        List<Map<String, Object>> detailList = Lists.newArrayList();
        for (Object id : ids) {
            Insurance insurance = insuranceService.get(Long.valueOf(id.toString()));
            if (insurance != null) {
                Map<String, Object> detail = insuranceToMap(insurance);
                detail.put("startTime", DateUtils.formatDate(orderDetail.getPlayDate()));
                detail.put("num", num);
                detailList.add(detail);
            }
        }
        order.getOrderDetails().addAll(orderDetailService.createLxbOrderDetails(detailList, order, false));
    }

    public Map<String, Object> insuranceToMap(Insurance insurance) {
        Map<String, Object> detail = Maps.newHashMap();
        detail.put("priceId", insurance.getId());
        detail.put("price", insurance.getPrice());
        detail.put("type", "insurance");
        detail.put("seatType", insurance.getName());
        return detail;
    }

    public void completeCombineLineDetail(Order order, List<Map<String, Object>> tourist, Map<String, Object> creditCardDetail) throws Exception {
        OrderDetail orderDetail = order.getOrderDetails().get(0);
        Line line = lineService.loadLine(orderDetail.getProduct().getId());
        if (CombineType.combine.equals(line.getCombineType())) {
            List<Map<String, Object>> detailList = Lists.newArrayList();
            Linedays linedaysCondition = new Linedays();
            linedaysCondition.setLineId(line.getId());
            Date date = order.getPlayDate();
            List<Linedays> linedaysList = linedaysService.list(linedaysCondition, null);
            Map<Long, Map<String, Date>> hotelMap = Maps.newHashMap();
            for (Linedays linedays : linedaysList) {
                LinedaysProductPrice linedaysProductPrice = new LinedaysProductPrice();
                linedaysProductPrice.setLinedays(linedays);
                linedaysProductPrice.setProductType(ProductType.scenic);
                List<LinedaysProductPrice> ticketProductPrices = linedaysProductPriceService.list(linedaysProductPrice, null);
                for (LinedaysProductPrice ticketProductPrice : ticketProductPrices) {
                    TicketDateprice price = ticketDatepriceService.getTicketDatePrice(ticketProductPrice.getPriceId(), date);
                    if (price != null && price.getInventory() > -1 && price.getInventory() < orderDetail.getNum()) {
                        throw new Exception("门票没有价格或没有库存");
                    }
                    detailList.add(ticketPriceToMap(price, orderDetail, tourist));
                }

                linedaysProductPrice.setProductType(ProductType.hotel);
                List<LinedaysProductPrice> hotelProductPrices = linedaysProductPriceService.list(linedaysProductPrice, null);
                if (hotelProductPrices.isEmpty()) {
                    date = DateUtils.add(date, Calendar.DAY_OF_MONTH, 1);
                    continue;
                }
                Map<String, Date> map = hotelMap.get(hotelProductPrices.get(0).getPriceId());
                if (map == null) {
                    map = Maps.newHashMap();
                    map.put("startTime", date);
                }
                map.put("endTime", DateUtils.add(date, Calendar.DAY_OF_MONTH, 1));
                hotelMap.put(hotelProductPrices.get(0).getPriceId(), map);

                date = DateUtils.add(date, Calendar.DAY_OF_MONTH, 1);
            }

            detailList.addAll(hotelPriceToMap(hotelMap, orderDetail, tourist, creditCardDetail));

            Float price = 0f;
            for (Map<String, Object> map : detailList) {
                Float totalPrice = Float.valueOf(map.get("price").toString());
                Integer num = Integer.valueOf(map.get("num").toString());
                if ("hotel".equals(map.get("type").toString())) {
                    Integer days = Integer.valueOf(map.get("days").toString());
                    price += totalPrice * num * days;
                } else {
                    price += totalPrice * num;
                }
            }

            Float combineRate = orderDetail.getFinalPrice() / price;
            for (Map<String, Object> map : detailList) {
                map.put("combineRate", combineRate);
            }

            order.getOrderDetails().addAll(orderDetailService.createLxbOrderDetails(detailList, order, false));

            order.setIsCombineLine(true);
        } else {
            order.setIsCombineLine(false);
        }
    }

    public Map<String, Object> ticketPriceToMap(TicketDateprice price, OrderDetail orderDetail, List<Map<String, Object>> tourist) {
        Map<String, Object> detail = Maps.newHashMap();
        detail.put("id", price.getTicketPriceId().getTicket().getId());
        detail.put("priceId", price.getTicketPriceId().getId());
        detail.put("price", price.getPriPrice());
        detail.put("startTime", DateUtils.formatDate(price.getHuiDate()));
        detail.put("endTime", DateUtils.formatDate(price.getHuiDate()));
        detail.put("num", orderDetail.getNum());
        detail.put("type", "scenic");
        detail.put("seatType", price.getTicketPriceId().getName());
        detail.put("tourist", tourist);
        return detail;
    }

    public List<Map<String, Object>> hotelPriceToMap(Map<Long, Map<String, Date>> hotelMap, OrderDetail orderDetail, List<Map<String, Object>> tourist, Map<String, Object> creditCardDetail) throws Exception {
        List<Map<String, Object>> detailList = Lists.newArrayList();
        for (Long id : hotelMap.keySet()) {
            Map<String, Date> dateMap = hotelMap.get(id);
            List<HotelPriceCalendar> hotelPriceCalendars = hotelPriceService.findTypePriceDate(id, dateMap.get("startTime"), dateMap.get("endTime"));
            if (hotelPriceCalendars.isEmpty() || DateUtils.getDateDiff(dateMap.get("startTime"), dateMap.get("endTime")) != hotelPriceCalendars.size()) {
                throw new Exception("酒店没有价格");
            }
            Float price = 0f;
            for (HotelPriceCalendar hotelPriceCalendar : hotelPriceCalendars) {
                if (hotelPriceCalendar.getInventory() != null && hotelPriceCalendar.getInventory() > -1 && hotelPriceCalendar.getInventory() < orderDetail.getNum()) {
                    throw new Exception("酒店没有库存");
                }
                price += hotelPriceCalendar.getMember();
            }
            price = price / hotelPriceCalendars.size();
            Map<String, Object> detail = Maps.newHashMap();
            detail.put("id", hotelPriceCalendars.get(0).getHotelId());
            detail.put("priceId", id);
            detail.put("price", price);
            detail.put("startTime", DateUtils.formatDate(dateMap.get("startTime")));
            detail.put("endTime", DateUtils.formatDate(dateMap.get("endTime")));
            detail.put("days", DateUtils.getDateDiff(dateMap.get("startTime"), dateMap.get("endTime")));
            detail.put("num", orderDetail.getNum());
            detail.put("type", "hotel");
            detail.put("seatType", hotelPriceCalendars.get(0).getHotelPrice().getRoomName());
            detail.put("tourist", tourist);
            if (hotelPriceCalendars.get(0).getHotelPrice().getStatus().equals(PriceStatus.GUARANTEE)) {
                detail.put("creditCard", creditCardDetail);
            }
            detailList.add(detail);
        }
        return detailList;
    }

    public List<Linetypepricedate> lineInventoryCheck(List<Linetypepricedate> linetypepricedates, Integer num) {
        if (linetypepricedates.isEmpty() || !CombineType.combine.equals(linetypepricedates.get(0).getLinetypeprice().getLine().getCombineType())) {
            return linetypepricedates;
        }
        List<Linetypepricedate> list = Lists.newArrayList();
        Linedays linedaysCondition = new Linedays();
        linedaysCondition.setLineId(linetypepricedates.get(0).getLineId());
        List<Linedays> linedaysList = linedaysService.list(linedaysCondition, null);
        completeLineCheck(linedaysList);
        for (Linetypepricedate linetypepricedate : linetypepricedates) {
            if (linedaysInventoryCheck(linedaysList, linetypepricedate.getDate(), num)) {
                list.add(linetypepricedate);
            }
        }
        return list;
    }

    private void completeLineCheck(List<Linedays> linedaysList) {
        for (Linedays linedays : linedaysList) {
            LinedaysProductPrice linedaysProductPrice = new LinedaysProductPrice();
            linedaysProductPrice.setLinedays(linedays);
            linedaysProductPrice.setProductType(ProductType.scenic);
            List<LinedaysProductPrice> ticketProductPrices = linedaysProductPriceService.list(linedaysProductPrice, null);
            linedays.setTicketProductPrice(ticketProductPrices);

            linedaysProductPrice.setProductType(ProductType.hotel);
            List<LinedaysProductPrice> hotelProductPrices = linedaysProductPriceService.list(linedaysProductPrice, null);
            linedays.setHotelProductPrice(hotelProductPrices);
        }
    }

    private Boolean linedaysInventoryCheck(List<Linedays> linedaysList, Date date, Integer num) {
        for (Linedays linedays : linedaysList) {
            for (LinedaysProductPrice ticketProductPrice : linedays.getTicketProductPrice()) {
                TicketDateprice price = ticketDatepriceService.getTicketDatePrice(ticketProductPrice.getPriceId(), date);
                if (price == null || (price.getInventory() != null && price.getInventory() > -1 && price.getInventory() < num)) {
                    return false;
                }
            }
            List<LinedaysProductPrice> hotelProductPrices = linedays.getHotelProductPrice();
            for (LinedaysProductPrice hotelProductPrice : hotelProductPrices) {
                List<HotelPriceCalendar> hotelPriceCalendars = hotelPriceService.getCalendarList(hotelProductPrice.getPriceId(), date);
                if (hotelPriceCalendars.isEmpty() || (hotelPriceCalendars.get(0).getInventory() != null && hotelPriceCalendars.get(0).getInventory() > -1 && hotelPriceCalendars.get(0).getInventory() < num)) {
                    return false;
                }
            }
            date = DateUtils.add(date, Calendar.DAY_OF_MONTH, 1);
        }
        return true;
    }

    public void updateOrderDetailInventory(Order order) {
        List<Ticket> ticketList = Lists.newArrayList();
        List<TicketDateprice> ticketDatepriceList = Lists.newArrayList();
        List<HotelPriceCalendar> hotelPriceCalendarList = Lists.newArrayList();
        List<Linetypepricedate> linetypepricedateList = Lists.newArrayList();
        List<CruiseShipRoomDate> cruiseShipRoomDateList = Lists.newArrayList();
        Integer invalidNum = 0;
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            switch (orderDetail.getProductType()) {
                case scenic:
                case sailboat:
                case yacht:
                case huanguyou:
                    TicketDateprice ticketDateprice = ticketDatepriceService.getByDate(orderDetail.getCostId(), orderDetail.getPlayDate());
                    Ticket ticket = ticketDateprice.getTicketPriceId().getTicket();
                    if (ticketDateprice.getInventory() == null || ticketDateprice.getInventory() < 0) {
                        continue;
                    }
                    Integer ticketNum = ticketDateprice.getInventory() - orderDetail.getNum();
                    if (ticketNum < 0) {
                        invalidNum++;
                        orderDetail.setStatus(OrderDetailStatus.INVALID);
                        orderDetailService.update(orderDetail);
                        continue;
                    }
                    if (ticket.getOrderCounts() == null || ticket.getOrderCounts() < 0) {
                        ticket.setOrderCounts(orderDetail.getNum());
                    } else {
                        ticket.setOrderCounts(orderDetail.getNum() + ticket.getOrderCounts());
                    }
                    ticketDateprice.setInventory(ticketNum);
                    ticketDatepriceList.add(ticketDateprice);
                    ticketList.add(ticket);
                    break;
                case hotel:
                    List<HotelPriceCalendar> hotelPriceCalendars = hotelPriceService.findTypePriceDate(orderDetail.getCostId(), orderDetail.getPlayDate(), orderDetail.getLeaveDate());
                    for (HotelPriceCalendar hotelPriceCalendar : hotelPriceCalendars) {
                        if (hotelPriceCalendar.getInventory() == null || hotelPriceCalendar.getInventory() < 0) {
                            continue;
                        }
                        Integer hotelNum = hotelPriceCalendar.getInventory() - orderDetail.getNum();
                        if (hotelNum < 0) {
                            invalidNum++;
                            orderDetail.setStatus(OrderDetailStatus.INVALID);
                            orderDetailService.update(orderDetail);
                            continue;
                        }
                        hotelPriceCalendar.setInventory(hotelNum);
                        hotelPriceCalendarList.add(hotelPriceCalendar);
                    }
                    break;
                case cruiseship:
                    CruiseShipRoomDate cruiseShipRoomDate = cruiseShipRoomDateService.findByRoomIdAndDate(orderDetail.getCostId(), orderDetail.getPlayDate());
                    if (cruiseShipRoomDate.getInventory() == null || cruiseShipRoomDate.getInventory() < 0) {
                        continue;
                    }
                    Integer shipNum = cruiseShipRoomDate.getInventory() - orderDetail.getNum();
                    if (shipNum < 0) {
                        invalidNum++;
                        orderDetail.setStatus(OrderDetailStatus.INVALID);
                        orderDetailService.update(orderDetail);
                        continue;
                    }
                    cruiseShipRoomDate.setInventory(shipNum);
                    cruiseShipRoomDateList.add(cruiseShipRoomDate);
                    break;
            }
        }
        if (invalidNum > 0) {
            order.setStatus(OrderStatus.INVALID);
            order.setDeleteFlag(true);
            update(order);
            return;
        }
        for (Ticket ticket : ticketList) {
            ticketService.update(ticket);
            ticketService.indexTicket(ticket);
        }
        for (TicketDateprice ticketDateprice : ticketDatepriceList) {
            ticketDatepriceService.update(ticketDateprice);
        }
        for (HotelPriceCalendar hotelPriceCalendar : hotelPriceCalendarList) {
            hotelPriceService.updateCalendar(hotelPriceCalendar);
        }
        for (Linetypepricedate linetypepricedate : linetypepricedateList) {
            linetypepricedateService.update(linetypepricedate);
        }
        for (CruiseShipRoomDate cruiseShipRoomDate : cruiseShipRoomDateList) {
            cruiseShipRoomDateService.saveOrUpdate(cruiseShipRoomDate);
        }
    }

    public String makeOrderNo() {
        //
        String dateString = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        Integer randomInteger = new Random().nextInt(999);
        randomInteger += 1000;
        return dateString + randomInteger.toString().substring(1);
    }

    private Map<String, Object> formatMap(String data) {
        try {
            Map<String, Object> map = new ObjectMapper().readValue(data, Map.class);
            return map;
        } catch (IOException e) {
            logger.error("数据格式化异常:" + e.getLocalizedMessage());
        }
        return null;
    }


    // 更新订单是否被评价(酒店和门票订单)
    public void processHasComment(Order order) {
        if (order.getOrderType() == OrderType.hotel || order.getOrderType() == OrderType.ticket) {

            Comment comment = new Comment();
            comment.setUser((Member) order.getUser());
            comment.setTargetId(order.getOrderDetails().get(0).getProduct().getId());
            List<Comment> commentList = commentService.list(comment, null);
            if (commentList.isEmpty()) {
                order.setHasComment(false);
            } else {
                order.setHasComment(true);
            }
        }
    }

    public List<OrderType> getFilterOrderTypes() {
        List<OrderType> filterOrderTypes = new ArrayList<OrderType>();
        String filterStr = propertiesManager.getString("FILTER_ORDER_TYPE");
        String[] filterArray;
        if (StringUtils.hasText(filterStr)) {
            filterArray = filterStr.split(",");
            for (String orderType : filterArray) {
                filterOrderTypes.add(OrderType.valueOf(orderType));
            }
        }
        return filterOrderTypes;
    }

    public List<ProductSource> getThirdOrderSource() {
        List<ProductSource> thirdOrderSources = new ArrayList<ProductSource>();
        String thirdOrderSourceStr = propertiesManager.getString("THIRD_ORDER_SOURCE");
        String[] thirdOrderSourceArray;
        if (StringUtils.hasText(thirdOrderSourceStr)) {
            thirdOrderSourceArray = thirdOrderSourceStr.split(",");
            for (String source : thirdOrderSourceArray) {
                thirdOrderSources.add(ProductSource.valueOf(source));
            }
        }
        return thirdOrderSources;
    }

    public Float calPrice(OrderDetail orderDetail) {
        Float price = 0F;
        if (orderDetail.getProductType() == ProductType.hotel && "ELONG".equals(String.valueOf(orderDetail.getProduct().getSource()))) {
            price = 0F;
        } else {
            price = orderDetail.getFinalPrice();
        }
        return price;
    }

    public void doOrderToTourist(Order order) {

        User user = order.getUser();
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            if (orderDetail.getProductType() == ProductType.insurance) {
                continue;
            }
            List<OrderTourist> orderTouristList = doCheckTourist(orderDetail.getOrderTouristList(), user);
            for (OrderTourist orderTourist : orderTouristList) {

                try {
                    Criteria<Tourist> criteria = new Criteria<Tourist>(Tourist.class);
                    criteria.eq("user.id", user.getId());
                    criteria.eq("name", orderTourist.getName());
                    criteria.eq("peopleType", orderTourist.getPeopleType());
                    criteria.eq("idType", orderTourist.getIdType());
                    criteria.eq("idNumber", orderTourist.getIdNumber());
                    criteria.eq("tel", orderTourist.getTel());
                    List<Tourist> oldTouristList = touristDao.findByCriteria(criteria);
                    if (!oldTouristList.isEmpty()) {
                        continue;
                    }
                    Tourist tourist = new Tourist();
                    tourist.setUser(user);
                    tourist.setName(orderTourist.getName());
                    if (null != orderTourist.getPeopleType()) {
                        if (OrderTouristPeopleType.ADULT.equals(orderTourist.getPeopleType())) {
                            tourist.setPeopleType(TouristPeopleType.ADULT);
                        } else {
                            tourist.setPeopleType(TouristPeopleType.KID);
                        }
                    } else {
                        tourist.setPeopleType(TouristPeopleType.ADULT);
                    }
                    if (orderTourist.getIdType() == OrderTouristIdType.IDCARD) {
                        tourist.setIdType(TouristIdType.IDCARD);
                    } else {
                        tourist.setIdType(TouristIdType.PASSPORT);
                    }
                    tourist.setIdNumber(orderTourist.getIdNumber());
                    tourist.setTel(orderTourist.getTel());
                    tourist.setGender(Gender.male);
                    tourist.setStatus(TouristStatus.normal);
                    touristDao.save(tourist);
                } catch (Exception e) {
                    continue;
                }
            }
        }

    }

    //验证常用联系人是否重复
    public List<OrderTourist> doCheckTourist(List<OrderTourist> orderTouristList, User user) {
        List<Tourist> touristList = getMyTourist(user, null, null);
        List<String> idNumberList = Lists.transform(touristList, new Function<Tourist, String>() {
            @Override
            public String apply(Tourist tourist) {
                return tourist.getIdNumber();
            }
        });
        List<OrderTourist> orderTourists = new ArrayList<OrderTourist>();
        for (OrderTourist orderTourist : orderTouristList) {
            if (!idNumberList.contains(orderTourist.getIdNumber())) {
                orderTourists.add(orderTourist);
            }
        }
        return orderTourists;
    }

    public List<Tourist> getMyTourist(User user, String name, Page page) {
        Criteria<Tourist> criteria = new Criteria<Tourist>(Tourist.class);
        criteria.eq("user.id", user.getId());
        criteria.eq("status", TouristStatus.normal);
        if (StringUtils.isNotBlank(name)) {
            criteria.like("name", name);
        }
        if (page != null) {
            return touristDao.findByCriteria(criteria, page);
        }
        return touristDao.findByCriteria(criteria);

    }

    public void createContract(Long orderId, String payWay, String payDate) {
        Map<Object, Object> data = Maps.newHashMap();
        Order order = get(orderId);
        List<OrderTourist> touristList = Lists.newArrayList();
        OrderDetail adultDetail = order.getOrderDetails().get(0);
        OrderDetail childDetail = null;
        Line line = lineService.loadLine(adultDetail.getProduct().getId());
        Date startDate = adultDetail.getPlayDate();
        List<String> startDateList = Lists.newArrayList(DateUtils.formatShortDate(startDate).split("-"));
        Integer playDay = line.getPlayDay();
        Date endDate = DateUtils.add(startDate, Calendar.DAY_OF_MONTH, playDay - 1);
        List<String> endDateList = Lists.newArrayList(DateUtils.formatShortDate(endDate).split("-"));
        touristList.addAll(adultDetail.getOrderTouristList());
        if (order.getOrderDetails().size() == 2) {
            childDetail = order.getOrderDetails().get(1);
            touristList.addAll(childDetail.getOrderTouristList());
        }

        for (OrderTourist tourist : touristList) {
            String idNumber = tourist.getIdNumber();
            tourist.setBirthday(idNumber.substring(6, 10) + "." + idNumber.substring(10, 12) + "." + idNumber.substring(12, 14));
            if (Integer.valueOf(idNumber.substring(16, 17)) % 2 == 0) {
                tourist.setGenderStr("女");
            } else {
                tourist.setGenderStr("男");
            }
        }
        data.put("company", line.getCompanyUnit());
        data.put("payWay", payWay);
        data.put("payDate", payDate);
        data.put("startDate", startDateList);
        data.put("playDay", playDay);
        data.put("endDate", endDateList);
        data.put("orderTourist", touristList);
        data.put("adultDetail", adultDetail);
        data.put("childDetail", childDetail);
        data.put("order", order);
        XhtmlrendererUtil.create(data, "ftl/lvxbang/order/contract.ftl", String.format("contract%d.pdf", orderId));
    }

    public List<Order> getWithdrawList(Order order, Page pageInfo) {
        Criteria<Order> criteria = new Criteria<Order>(Order.class);
        createWithdrawCriteria(order, criteria);
        criteria.orderBy("modifyTime", "desc");
        return orderDao.findByCriteria(criteria, pageInfo);
    }

    private void createWithdrawCriteria(Order order, Criteria<Order> criteria) {

        if (order.getUser() != null) {
            criteria.createCriteria("user", "u", JoinType.INNER_JOIN);
            if (StringUtils.isNotBlank(order.getUser().getUserName())) {
                criteria.like("u.userName", order.getUser().getUserName(), MatchMode.ANYWHERE);
            }
        }

        if (order.getOrderType() != null) {
            criteria.eq("orderType", order.getOrderType());
        }
        if (StringUtils.isNotBlank(order.getOrderNo())) {
            criteria.eq("orderNo", order.getOrderNo());
        }
        if (order.getPriceStart() != null) {
            criteria.ge("price", order.getPriceStart());
        }
        if (order.getPriceEnd() != null) {
            criteria.le("price", order.getPriceEnd());
        }
        if (order.getStatus() != null) {
            criteria.eq("status", order.getStatus());
        }
    }

    public void delByOrder(Order order) {
        orderDao.delete(order);
    }

    public void doConfirmOrder(Order order, OrderDetail detail, SysUser loginUser) {
        detail.setStatus(OrderDetailStatus.WAITING);    //待支付
        detail.setOperator(loginUser);
        orderDetailDao.update(detail);

        // 更新订单状态
        updateOrderStatus(order, OrderStatus.WAIT, OrderDetailStatus.WAITING);
        // 设置支付超时时间
        if (order.getStatus() == OrderStatus.WAIT) {
            orderWaitTime(order);
            orderDao.update(order);
        }
    }

    public void doCancelOrder(Order order, OrderDetail detail, String remark, SysUser loginUser) {
        detail.setStatus(OrderDetailStatus.CANCELED);
        detail.setOperator(loginUser);
        detail.setRemark(remark);
        orderDetailDao.update(detail);
        // 更新订单状态
        updateOrderStatus(order, OrderStatus.CANCELED, OrderDetailStatus.CANCELED);
    }

    public void updateOrderBill(Order order) {
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            updateOrderDetailBill(orderDetail);
        }
    }

    /**
     * 更新订单详情的账单信息
     *
     * @param orderDetail
     */
    public void updateOrderDetailBill(OrderDetail orderDetail) {
        Contract contract = contractService.getContractByCompanyB(orderDetail.getProduct().getCompanyUnit());
        if (contract == null) {
            return;
        }
        switch (contract.getSettlementType()) {
            case week:
                orderDetail.setOrderBillType(OrderBillType.week);
                break;
            case month:
                orderDetail.setOrderBillType(OrderBillType.month);
                break;
            case tday:
                if (contract.getSettlementValue() == 0) {
                    orderDetail.setOrderBillType(OrderBillType.T0);
                } else if (contract.getSettlementValue() == 1) {
                    orderDetail.setOrderBillType(OrderBillType.T1);
                } else {
                    orderDetail.setOrderBillType(OrderBillType.TN);
                }
                break;
        }
        orderDetail.setOrderBillDays(contract.getSettlementValue());
        orderDetail.setOrderBillDate(orderDetailService.getBillDate(orderDetail.getOrderBillType(), orderDetail.getOrderBillDays()));
        orderDetail.setOrderBillStatus(0);
        switch (orderDetail.getProductType()) {
            case scenic:
            case sailboat:
            case yacht:
            case huanguyou:
                TicketDateprice dateprice = ticketDatepriceService.getByDate(orderDetail.getCostId(), orderDetail.getPlayDate());
                orderDetail.setOrderBillPrice(dateprice.getPrice() * orderDetail.getNum());
                break;
            case hotel:
                List<HotelPriceCalendar> calendarList = hotelPriceService.findTypePriceDate(orderDetail.getCostId(), orderDetail.getPlayDate(), orderDetail.getLeaveDate());
                if (calendarList.size() != orderDetail.getDays()) {
                    break;
                }
                Float price = 0f;
                for (HotelPriceCalendar hotelPriceCalendar : calendarList) {
                    price += hotelPriceCalendar.getCost();
                }
                orderDetail.setOrderBillPrice(price * orderDetail.getNum());
                break;
            case cruiseship:
                CruiseShipRoomDate roomDate = cruiseShipRoomDateService.findByRoomIdAndDate(orderDetail.getCostId(), orderDetail.getPlayDate());
                orderDetail.setOrderBillPrice(roomDate.getDiscountPrice() * orderDetail.getNum());
                break;
        }
        orderDetailDao.update(orderDetail);
    }

    public Map<String, Object> doPayedConfirm(Long orderId, List<Long> orderDetailIds, SysUser loginUser) {
        Map<String, Object> map = new HashMap<String, Object>();
        Order order = orderDao.get(orderId);
        List<OrderDetail> orderDetails = order.getOrderDetails();
        if (orderDetailIds == null || orderDetailIds.isEmpty()) {
            map.put("flag", false);
            map.put("reMsg", "订单不存在");
            return map;
        }
        for (Long detailId : orderDetailIds) {
            OrderDetail detail = orderDetailService.get(detailId);
            detail.setStatus(OrderDetailStatus.SUCCESS);    //待支付
            detail.setOperator(loginUser);
            orderDetailDao.update(detail);
            orderBillService.doOrderDetailBill(detail);
        }
        int flag = orderDetails.size();
        for (OrderDetail detail : orderDetails) {
            if (detail.getStatus() == OrderDetailStatus.SUCCESS) {
                flag--;
            }
        }
        if (flag == 0) {
            order.setModifyTime(new Date());
            order.setStatus(OrderStatus.SUCCESS);
            orderDao.update(order);
        } else {
            map.put("reMsg", "确认成功");
            map.put("isAllConfirmed", false);
        }
        return map;
    }

    /**
     * 根据订单详情状态更新订单状态，orderStatus与detailStatus保持一致
     *
     * @param order
     * @param orderStatus
     * @param detailStatus
     */
    public void updateOrderStatus(Order order, OrderStatus orderStatus, OrderDetailStatus detailStatus) {
        List<OrderDetail> allDetails = orderDetailDao.getByOrderId(order.getId());
        int detailCount = allDetails.size();
        int counts = 0;
        for (OrderDetail detail : allDetails) {
            if (detail.getStatus() == detailStatus) {
                counts++;
            }
        }
        // 如果是行程规划，查询是否有船票订单
        if (order.getOrderType() == OrderType.plan) {
            List<FerryOrder> ferryOrders = ferryOrderService.getByOrderId(order.getId());
            detailCount += ferryOrders.size();
            for (FerryOrder ferryOrder : ferryOrders) {
                if (ferryOrder.getStatus() == orderStatus) {
                    counts++;
                }
            }
        }
        if (counts == detailCount) {
            order.setStatus(orderStatus);
            order.setModifyTime(new Date());
            update(order); // 更新订单状态
        }
    }

    public Long getNewestOrderCount(Order order) {

        Criteria<OrderAll> criteria = new Criteria<OrderAll>(OrderAll.class);

        if (order.getOrderType() != null) {
            criteria.eq("orderType", order.getOrderType());
        }

        if (order.getCreateTime() != null) {
            criteria.ge("createTime", order.getCreateTime());
        }

        criteria.setProjection(Projections.rowCount());
        return (Long) orderAllDao.findUniqueValue(criteria);
    }
}
