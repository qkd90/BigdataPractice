package com.data.data.hmly.service.order;

import com.data.data.hmly.service.common.MultiDateService;
import com.data.data.hmly.service.common.ProductService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.cruiseship.CruiseShipRoomDateService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoomDate;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelPriceCalendar;
import com.data.data.hmly.service.line.LinetypepricedateService;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.data.data.hmly.service.order.dao.OrderDetailDao;
import com.data.data.hmly.service.order.entity.CreditCard;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderBillSummary;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderInvoice;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.OrderTouristService;
import com.data.data.hmly.service.order.entity.enums.CreditCardIdType;
import com.data.data.hmly.service.order.entity.enums.OrderBillType;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.order.vo.OrderDataForm;
import com.data.data.hmly.service.order.vo.OrderResult;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.criterion.Subqueries;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/10/14.
 */
@Service
public class OrderDetailService {

    Logger logger = Logger.getLogger(OrderDetailService.class);


    @Resource
    private OrderDetailDao orderDetailDao;
    @Resource
    private ProductService productService;
    @Resource
    private OrderTouristService orderTouristService;
    @Resource
    private TrafficPriceService trafficPriceService;
    @Resource
    private CreditCardService creditCardService;
    @Resource
    private OrderInvoiceService orderInvoiceService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private LinetypepricedateService linetypepricedateService;
    @Resource
    private CruiseShipRoomDateService cruiseShipRoomDateService;
    @Resource
    private MultiDateService multiDateService;
    @Resource
    private HotelPriceService hotelPriceService;

    public OrderDetail get(Long id) {
        return orderDetailDao.get(id);
    }

    public OrderDetail get(Class<OrderDetail> t, Long id) {
        return orderDetailDao.get(t, id);
    }

    public OrderDetail findFullById(Long id) {
        Criteria<OrderDetail> criteria = new Criteria<>(OrderDetail.class);
        criteria.createCriteria("order");
        criteria.createCriteria("orderTouristList");
        criteria.createCriteria("product");
        criteria.eq("id", id);
        return orderDetailDao.findUniqueByCriteria(criteria);
    }

    /**
     * 旅客列表转为证件号与手机对应的map
     *
     * @param orderTourists
     * @return
     */
    public Map<String, Object> orderTouristToMap(List<OrderTourist> orderTourists) {
        Map<String, Object> toristMap = new HashMap<String, Object>();
        for (OrderTourist tourist : orderTourists) {
            toristMap.put(tourist.getIdNumber(), tourist.getTel());
        }
        return toristMap;
    }

    public void update(OrderDetail orderDetail) {
        orderDetailDao.update(orderDetail);
    }

    public void update(List<OrderDetail> orderDetails) {
        orderDetailDao.updateAll(orderDetails);
    }

    public List<OrderDetail> getByOrderId(Long orderId) {
        Criteria<OrderDetail> criteria = new Criteria<OrderDetail>(OrderDetail.class);
//        criteria.createCriteria("order", "order");
        criteria.eq("order.id", orderId);
        return orderDetailDao.findByCriteria(criteria);
    }

    public List<OrderDetail> getByOrderDetailIds(List<Long> orderDetailIds) {
        Criteria<OrderDetail> criteria = new Criteria<OrderDetail>(OrderDetail.class);
//        criteria.createCriteria("order", "order");
        criteria.in("id", orderDetailIds);
        return orderDetailDao.findByCriteria(criteria);
    }

    public List<OrderDetail> getNoCommentList(Long userId, List<ProductType> types, Page page) {
        Criteria<OrderDetail> criteria = new Criteria<OrderDetail>(OrderDetail.class);
        criteria.createCriteria("order", "order");
        criteria.eq("order.user.id", userId);
        criteria.eq("hasComment", false);
        criteria.or(Restrictions.eq("status", OrderDetailStatus.PAYED), Restrictions.eq("status", OrderDetailStatus.SUCCESS));
        if (types != null && !types.isEmpty()) {
            List<SimpleExpression> simpleExpressions = Lists.newArrayList();
            for (ProductType type : types) {
                simpleExpressions.add(Restrictions.eq("productType", type));
            }
            criteria.or(simpleExpressions.toArray(new SimpleExpression[simpleExpressions.size()]));
        }
        if (page != null) {
            return orderDetailDao.findByCriteria(criteria, page);
        }
        return orderDetailDao.findByCriteria(criteria);
    }

    public List<OrderDetail> getBillOrderDetailList() {
        Criteria<OrderDetail> criteria = new Criteria<OrderDetail>(OrderDetail.class);
        criteria.createCriteria("product");
        criteria.eq("status", OrderDetailStatus.SUCCESS);
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        Date billDate = calendar.getTime();
        Calendar yesterdayCalendar = Calendar.getInstance();
        yesterdayCalendar.clear();
        yesterdayCalendar.setTime(billDate);
        yesterdayCalendar.add(Calendar.DATE, -1);
        Date yesterdayDate = yesterdayCalendar.getTime();
        List<OrderBillType> billTypes = new ArrayList<OrderBillType>();
        billTypes.add(OrderBillType.D0);
        billTypes.add(OrderBillType.T0);
        // 获取昨天已结算的D0和T0订单(将用来生成对账单) 与 今天需要结算的单子(将用来生成对账单)
        criteria.or(Restrictions.and(Restrictions.eq("orderBillStatus", 1),
                        Restrictions.eq("orderBillDate", yesterdayDate),
                        Restrictions.in("orderBillType", billTypes)),
                Restrictions.and(Restrictions.eq("orderBillStatus", 0),
                        Restrictions.eq("orderBillDate", billDate)));
//        criteria.eq("orderBillDate", billDate);
        List<OrderDetail> orderDetailList = orderDetailDao.findByCriteria(criteria);
        return orderDetailList;
    }

    public void updateByResult(OrderDetail orderDetail, OrderResult orderResult) {
//        Long id = orderResult.getOrderDetailId();
        OrderDetailStatus status = orderResult.getOrderDetailStatus();
        String msg = orderResult.getMsg();
        String apiResult = orderResult.getApiResult();
        orderDetail.setStatus(status);
        orderDetail.setApiResult(apiResult);
        orderDetail.setMsg(msg);
        orderDetail.setRealOrderId(orderResult.getRealOrderId());
//        String updateHql = "update OrderDetail set status=?,msg=?,apiResult=? where id=?";
//        orderDetailDao.updateByHQL(updateHql, status, msg, apiResult, id);
        this.update(orderDetail);
    }

    /**
     * 根据结算方式获取订单详情结算日期
     *
     * @param billType
     * @param billDays
     * @return
     */
    public Date getBillDate(OrderBillType billType, Integer billDays) {
        Calendar nowCalendar = Calendar.getInstance();
        nowCalendar.set(Calendar.HOUR_OF_DAY, 0);
        nowCalendar.set(Calendar.MINUTE, 0);
        nowCalendar.set(Calendar.SECOND, 0);
        Date nowDate = nowCalendar.getTime();
        Date billDate = null;
        boolean isHoliday = false;
        switch (billType) {
            case D0:
                billDate = nowDate;
                break;
            case D1:
                nowCalendar.add(Calendar.DATE, 1);
                billDate = nowCalendar.getTime();
                break;
            case DN:
                nowCalendar.add(Calendar.DATE, billDays);
                billDate = nowCalendar.getTime();
                break;
            case T0:
                isHoliday = multiDateService.isHoliday(nowDate);
                if (!isHoliday) {
                    billDate = nowDate;
                } else {
                    billDate = this.findNearBillDate(nowCalendar.getTime());
                }
                break;
            case T1:
                nowCalendar.add(Calendar.DATE, 1);
                isHoliday = multiDateService.isHoliday(nowCalendar.getTime());
                if (!isHoliday) {
                    billDate = nowCalendar.getTime();
                } else {
                    billDate = this.findNearBillDate(nowCalendar.getTime());
                }
                break;
            case TN:
                nowCalendar.add(Calendar.DATE, billDays);
                isHoliday = multiDateService.isHoliday(nowCalendar.getTime());
                if (!isHoliday) {
                    billDate = nowCalendar.getTime();
                } else {
                    billDate = this.findNearBillDate(nowCalendar.getTime());
                }
                break;
            case week:
                Integer week = billDays;
                Integer nowWeek = nowCalendar.get(Calendar.DAY_OF_WEEK);
                Integer diffWeek = nowWeek - week;
                if (diffWeek < 0) {
                    diffWeek += 7;
                }
                nowCalendar.add(Calendar.DATE, diffWeek);
                billDate = nowCalendar.getTime();

            case month:
                Integer day = billDays;
                Calendar calendar = Calendar.getInstance();
                calendar.clear();
                calendar.setTime(nowCalendar.getTime());
                calendar.set(Calendar.DATE, day);
                if (calendar.get(Calendar.DATE) >= nowCalendar.get(Calendar.DATE)) {
                    billDate = calendar.getTime();
                } else {
                    calendar.add(Calendar.MONTH, 1);
                }
            default:
                break;
        }
        return billDate;
    }

    /**
     * 查询下一个最近的工作日结算日期
     *
     * @param date
     * @return
     */
    private Date findNearBillDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.setTime(date);
        calendar.add(Calendar.DATE, 1);
        Date billDate;
        while (true) {
            boolean isHoliday = multiDateService.isHoliday(calendar.getTime());
            if (!isHoliday) {
                billDate = calendar.getTime();
                break;
            } else {
                calendar.add(Calendar.DATE, 1);
            }
        }
        return billDate;
    }

    /**
     * 获取指定条件下order的orderDetail列表
     *
     * @param orderId
     * @param thirdOrderSources
     * @return
     */
    public List<OrderDetail> getByOrderIdAndSource(Long orderId, List<ProductSource> thirdOrderSources) {
        Criteria<OrderDetail> criteria = new Criteria<OrderDetail>(OrderDetail.class);
        criteria.createCriteria("order", "order");
        criteria.eq("order.id", orderId);
        // 过滤取消的订单详情
        criteria.or(Restrictions.isNull("status"), Restrictions.ne("status", OrderDetailStatus.CANCELED));
        if (thirdOrderSources != null && !thirdOrderSources.isEmpty()) {
            DetachedCriteria productCriteria = criteria.createCriteria("product");
            productCriteria.add(Restrictions.in("source", thirdOrderSources));
        } else if (thirdOrderSources != null && thirdOrderSources.isEmpty()) {
            DetachedCriteria productCriteria = criteria.createCriteria("product");
            productCriteria.add(Restrictions.isNull("source"));
        }
        List<OrderDetail> orderDetails = orderDetailDao.findByCriteria(criteria);
        return orderDetails;
    }

    public List<OrderDetail> getByOrderIdAndProductType(Long orderId, ProductType productType) {
        Criteria<OrderDetail> criteria = new Criteria<OrderDetail>(OrderDetail.class);
        DetachedCriteria productCriteria = criteria.createCriteria("product");
        criteria.createCriteria("order", "order");
        criteria.eq("order.id", orderId);
        // 筛选订单产品类型
        if (productType != null) {
            criteria.eq("product.proType", productType);
        }
        List<OrderDetail> orderDetailList = orderDetailDao.findByCriteria(criteria);
        return orderDetailList;
    }

    /**
     * 根据酒店房型搜索和入住的日期范围搜索指定的订单详情
     *
     * @param hotelPrice
     * @param dayRange
     * @return
     */
    public List<OrderDetail> getByHotelPrice(HotelPrice hotelPrice, Integer dayRange) {
        DetachedCriteria criteria = DetachedCriteria.forClass(OrderDetail.class, "order_detail");
        DetachedCriteria touristCriteria = DetachedCriteria.forClass(OrderTourist.class, "tourist");
//        Criteria<OrderDetail> criteria = new Criteria<OrderDetail>(OrderDetail.class);
        criteria.createAlias("product", "product");
//        criteria.eq("costId", hotelPrice.getId());
//        criteria.eq("productType", ProductType.hotel);
//        criteria.isNull("product.source");
        criteria.add(Restrictions.eq("costId", hotelPrice.getId()));
        criteria.add(Restrictions.eq("productType", ProductType.hotel));
        criteria.add(Restrictions.isNull("product.source"));
        touristCriteria.add(Restrictions.eqProperty("tourist.orderDetail.id", "order_detail.id"));
        criteria.add(Subqueries.exists(touristCriteria.setProjection(Projections.property("tourist.id"))));
        Date nowDate = DateUtils.getDate(DateUtils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(nowDate);
        endCalendar.add(Calendar.DAY_OF_YEAR, dayRange);
        Date endDate = endCalendar.getTime();
//        criteria.lt("playDate", endDate);
//        criteria.gt("playDate", nowDate);
        criteria.add(Restrictions.le("playDate", endDate));
        criteria.add(Restrictions.ge("playDate", nowDate));
        criteria.add(Restrictions.isNotNull("status"));
        criteria.addOrder(org.hibernate.criterion.Order.asc("playDate"));
        return orderDetailDao.findByCriteria(criteria);
    }

    public List<OrderDetail> getByHotelPrice(List<Long> priceIdList, String searchContent, OrderDetail condition, Page page) {
        Criteria<OrderDetail> criteria = new Criteria<OrderDetail>(OrderDetail.class);
        DetachedCriteria orderCriteria = criteria.createCriteria("order", "order");
        criteria.in("costId", priceIdList);
        if (StringUtils.hasText(searchContent)) {
            orderCriteria.add(
                    Restrictions.or(
                            Restrictions.like("orderNo", searchContent, MatchMode.ANYWHERE),
                            Restrictions.like("recName", searchContent, MatchMode.ANYWHERE),
                            Restrictions.like("mobile", searchContent, MatchMode.ANYWHERE)));
        }
        if (condition.getStatus() != null) {
            criteria.eq("status", condition.getStatus());
        }
        if (condition.getMinPlayDate() != null) {
            criteria.ge("playDate", condition.getMinPlayDate());
        }
        if (condition.getMaxPlayDate() != null) {
            criteria.le("playDate", condition.getMaxPlayDate());
        }
        List<OrderDetailStatus> needStatuses = new ArrayList<OrderDetailStatus>();
        needStatuses.add(OrderDetailStatus.SUCCESS);
        needStatuses.add(OrderDetailStatus.CHECKOUT);
        needStatuses.add(OrderDetailStatus.CHECKIN);
        criteria.isNotNull("status");
        criteria.in("status", needStatuses);
        criteria.eq("productType", ProductType.hotel);
        List<OrderDetail> orderDetailList;
        if (page != null) {
            orderDetailList = orderDetailDao.findByCriteria(criteria, page);
        } else {
            orderDetailList = orderDetailDao.findByCriteria(criteria);
        }
        return orderDetailList;
    }

    public List<OrderDetail> getByCustomer(Hotel hotel, OrderTourist orderTourist, Integer dayRange) {
        DetachedCriteria criteria = DetachedCriteria.forClass(OrderDetail.class, "order_detail");
        DetachedCriteria touristCriteria = DetachedCriteria.forClass(OrderTourist.class, "tourist");
        DetachedCriteria priceCriteria = DetachedCriteria.forClass(HotelPrice.class, "hotel_price");
        criteria.createAlias("product", "product");
        priceCriteria.createAlias("hotel", "hotel");
        criteria.add(Restrictions.eq("productType", ProductType.hotel));
        touristCriteria.add(Restrictions.eq("name", orderTourist.getName()));
        touristCriteria.add(Restrictions.eq("idNumber", orderTourist.getIdNumber()));
        if (StringUtils.hasText(orderTourist.getTel())) {
            touristCriteria.add(Restrictions.eq("tel", orderTourist.getTel()));
        }
        touristCriteria.add(Restrictions.eqProperty("tourist.orderDetail.id", "order_detail.id"));
        criteria.add(Subqueries.exists(touristCriteria.setProjection(Projections.property("tourist.id"))));
        priceCriteria.add(Restrictions.eq("hotel.id", hotel.getId()));
        priceCriteria.add(Restrictions.eqProperty("hotel_price.id", "order_detail.costId"));
        criteria.add(Subqueries.exists(priceCriteria.setProjection(Projections.property("hotel_price.id"))));
        //
        Date nowDate = DateUtils.getDate(DateUtils.format(new Date(), "yyyy-MM-dd"), "yyyy-MM-dd");
        Calendar endCalendar = Calendar.getInstance();
        endCalendar.setTime(nowDate);
        endCalendar.add(Calendar.DAY_OF_YEAR, dayRange);
        Date endDate = endCalendar.getTime();
        criteria.add(Restrictions.le("playDate", endDate));
        criteria.add(Restrictions.ge("playDate", nowDate));
        criteria.add(Restrictions.isNotNull("status"));
        criteria.addOrder(org.hibernate.criterion.Order.asc("playDate"));
        return orderDetailDao.findByCriteria(criteria);
    }


    public List<OrderDetail> list(OrderDetail orderDetail) {
        if (orderDetail == null) {
            return orderDetailDao.findAll();
        }
        Criteria<OrderDetail> criteria = createCriteria(orderDetail, true, false, null, null);
        return orderDetailDao.findByCriteria(criteria);
    }

    public List<OrderDetail> list(OrderDetail order, Page page, String... orderProperties) {
        Criteria<OrderDetail> criteria = createCriteria(order, true, false, null, orderProperties);
        if (page == null) {
            return orderDetailDao.findByCriteria(criteria);
        }
        return orderDetailDao.findByCriteria(criteria, page);
    }

    public List<OrderDetail> list(OrderDetail order, Page page, Boolean isSuperAdmin, Boolean isSiteAdmin, SysUser loginUser, String... orderProperties) {
        Criteria<OrderDetail> criteria = createCriteria(order, isSuperAdmin, isSiteAdmin, loginUser, orderProperties);
        if (page == null) {
            return orderDetailDao.findByCriteria(criteria);
        }
        return orderDetailDao.findByCriteria(criteria, page);
    }

    public Criteria<OrderDetail> createCriteria(OrderDetail orderDetail, Boolean isSuperAdmin, Boolean isSiteAdmin, SysUser loginUser, String... orderProperties) {
        Criteria<OrderDetail> criteria = new Criteria<OrderDetail>(OrderDetail.class);
        DetachedCriteria orderDetachedCriteria = criteria.createCriteria("order");
        if (!isSuperAdmin) {
            DetachedCriteria proCriteria = criteria.createCriteria("product");
            DetachedCriteria userCriteria = proCriteria.createCriteria("user");
            userCriteria.add(Restrictions.eq("sysSite.id", loginUser.getSysSite().getId()));
            if (!isSiteAdmin) {
                proCriteria.add(Restrictions.eq("companyUnit.id", loginUser.getSysUnit().getCompanyUnit().getId()));
            }
        }
        if (orderProperties != null) {
            if (orderProperties.length == 2 && orderProperties[0] != null
                    && orderProperties[1] != null) {
                criteria.orderBy(orderProperties[0], orderProperties[1]);
            } else if (orderProperties.length == 1 && orderProperties[0] != null) {
                criteria.orderBy(org.hibernate.criterion.Order.desc(orderProperties[0]));
            }
        }
        if (orderDetail.getStatus() != null) {
            criteria.eq("status", orderDetail.getStatus());
        }
        if (orderDetail.getProductType() != null) {
            criteria.eq("productType", orderDetail.getProductType());
        }
        if (orderDetail.getNeededStatuses() != null && !orderDetail.getNeededStatuses().isEmpty()) {
            criteria.in("status", orderDetail.getNeededStatuses());
        }
        if (orderDetail.getFilterProType() != null && !orderDetail.getFilterProType().isEmpty()) {
            criteria.notin("productType", orderDetail.getFilterProType());

        }
        if (orderDetail.getThirdOrderSources() != null && !orderDetail.getThirdOrderSources().isEmpty()) {
            criteria.createCriteria("product");
            criteria.in("product.source", orderDetail.getThirdOrderSources());
        }
        if (orderDetail.getThirdOrderSources() != null && orderDetail.getThirdOrderSources().isEmpty()) {
            criteria.createCriteria("product");
            criteria.isNull("product.source");
        }
        if (orderDetail.getMaxPlayDate() != null) {
            criteria.lt("playDate", orderDetail.getMaxPlayDate());

        }
        if (orderDetail.getMinPlayDate() != null) {
            criteria.gt("palyDate", orderDetail.getMinPlayDate());
        }
        if (orderDetail.getOrder() != null) {
            Order order = orderDetail.getOrder();
            if (StringUtils.hasText(order.getOrderNo())) {
                criteria.like("order.orderNo", order.getOrderNo());
            }
            if (StringUtils.hasText(order.getRecName())) {
                criteria.like("order.recName", order.getRecName());
            }
            if (StringUtils.hasText(order.getMobile())) {
                criteria.like("order.mobile", order.getMobile());
            }
        }
        return criteria;
    }

    public void save(OrderDetail orderDetail) {
        orderDetailDao.save(orderDetail);
    }

    public List<OrderDetail> createLxbOrderDetails(List<Map<String, Object>> detailMapList, Order order, Boolean deleteFlag) {
        List<OrderDetail> orderDetailList = new ArrayList<OrderDetail>();
        if (deleteFlag && order.getOrderDetails() != null) {
            for (OrderDetail detail : order.getOrderDetails()) {
                orderDetailDao.delete(detail);
            }
        }
        for (Map<String, Object> detailMap : detailMapList) {
            //
            OrderDetail orderDetail = createOrderDetail(detailMap, order);
            List<Map<String, Object>> touristMapList = (List<Map<String, Object>>) detailMap.get("tourist");
            if (touristMapList != null) {
                List<OrderTourist> touristList = orderTouristService.createLxbOrderTourist(touristMapList, order, orderDetail);
                orderDetail.setOrderTouristList(touristList);
            }
            orderDetailList.add(orderDetail);
        }
        return orderDetailList;
    }

    public OrderDetail createOrderDetail(Map<String, Object> detailData, Order order) {
        Map<String, Object> creditCardDetail = (Map<String, Object>) detailData.get("creditCard");
        Boolean status = false;
        if (creditCardDetail != null) {
            status = Boolean.valueOf(creditCardDetail.get("status").toString());
        }
        OrderDetail orderDetail = new OrderDetail();
        Integer num = Integer.parseInt(detailData.get("num").toString());
        if (num < 1) {
            num = 1;
        }
        orderDetail.setNum(num);
        // 商品信息
        if (detailData.get("id") != null) {
            Product product = productService.get(Long.parseLong(detailData.get("id").toString()));
            orderDetail.setProduct(product);
            orderDetail.setNeedConfirm(product.getNeedConfirm() != null && product.getNeedConfirm());
        }
        if (orderDetail.getNeedConfirm() != null && orderDetail.getNeedConfirm()) {
            orderDetail.setStatus(OrderDetailStatus.UNCONFIRMED);
        } else {
            orderDetail.setStatus(OrderDetailStatus.WAITING);
        }
        orderDetail.setSeatType(detailData.get("seatType").toString());
        // 产品使用日期
        if (!"hotel".equals(detailData.get("type").toString())) {
            String playDateString = detailData.get("startTime").toString();
            Date playDate = stringToDate(playDateString);
            orderDetail.setPlayDate(playDate);
        }

        // scenic("门票"), restaurant("餐厅"), hotel("酒店"), line("线路"), train("火车票"), flight("机票");
        String type = detailData.get("type").toString();
        if ("scenic".equals(type)) {
            // 门票
            Long priceId = Long.parseLong(detailData.get("priceId").toString());
            orderDetail.setCostId(priceId);
            if (detailData.get("cityId") != null) {
                TbArea city = new TbArea();
                city.setId(Long.parseLong(detailData.get("cityId").toString()));
                orderDetail.setCity(city);
            }
            if (detailData.get("day") != null) {
                orderDetail.setDay(Integer.parseInt(detailData.get("day").toString()));
            }
            // 门票价格
            TicketDateprice ticketDateprice = ticketDatepriceService.getTicketDatePrice(priceId, orderDetail.getPlayDate());
            Float unitPrice = ticketDateprice.getPriPrice();
            Float combineRate = 1f;
            if (detailData.get("combineRate") != null) {
                combineRate = Float.valueOf(detailData.get("combineRate").toString());
            }
            orderDetail.setUnitPrice(unitPrice);
            orderDetail.setTotalPrice(num * unitPrice);
            orderDetail.setFinalPrice(num * unitPrice * combineRate);
            orderDetail.setCombinePay(num * unitPrice * (1 - combineRate));
            Ticket ticket = ticketDateprice.getTicketPriceId().getTicket();
            switch (ticket.getTicketType()) {
                case sailboat:
                    orderDetail.setProductType(ProductType.sailboat);
                    break;
                case yacht:
                    orderDetail.setProductType(ProductType.yacht);
                    break;
                case huanguyou:
                    orderDetail.setProductType(ProductType.huanguyou);
                    break;
                default:
                    orderDetail.setProductType(ProductType.scenic);
                    break;
            }
        } else if ("hotel".equals(type)) {
            hotelDetailCreate(status, creditCardDetail, orderDetail, order, detailData);
        } else if ("flight".equals(type)) {
            Long priceId = Long.parseLong(detailData.get("priceId").toString());
            orderDetail.setCostId(priceId);
            // 机票
            orderDetail.setProductType(ProductType.flight);
            // 机票价格
            TrafficPrice trafficPrice = trafficPriceService.get(priceId);
            Float unitPrice = trafficPrice.getPrice() + trafficPrice.getAdditionalFuelTax() + trafficPrice.getAirportBuildFee();
            orderDetail.setUnitPrice(unitPrice);
            orderDetail.setFinalPrice(num * unitPrice);
        } else if ("train".equals(type)) {
            // 火车票
            orderDetail.setProductType(ProductType.train);
            Long priceId = Long.parseLong(detailData.get("priceId").toString());
            orderDetail.setCostId(priceId);
            // 机票
            // 机票价格
            TrafficPrice trafficPrice = trafficPriceService.get(priceId);
            Float unitPrice = trafficPrice.getPrice();
            orderDetail.setUnitPrice(unitPrice);
            orderDetail.setFinalPrice(num * unitPrice);
        } else if ("line".equals(type)) {
            orderDetail.setProductType(ProductType.line);
            Long priceId = Long.parseLong(detailData.get("priceId").toString());
            orderDetail.setCostId(priceId);
            List<Linetypepricedate> list = linetypepricedateService.findTypePriceDate(priceId, orderDetail.getPlayDate(), orderDetail.getPlayDate());
            Float unitPrice;
            if ("成人".equals(detailData.get("seatType"))) {
                unitPrice = list.get(0).getDiscountPrice() + list.get(0).getRebate();
            } else {
                unitPrice = list.get(0).getChildPrice() + list.get(0).getChildRebate();
            }
            orderDetail.setUnitPrice(unitPrice);
            orderDetail.setSingleRoomPrice(detailData.get("singleRoomPrice") == null ? 0f : Float.parseFloat(detailData.get("singleRoomPrice").toString()));
            orderDetail.setFinalPrice(num * unitPrice + orderDetail.getSingleRoomPrice());
        } else if ("insurance".equals(type)) {
            orderDetail.setProductType(ProductType.insurance);
            Long priceId = Long.parseLong(detailData.get("priceId").toString());
            orderDetail.setCostId(priceId);
            Float unitPrice = Float.parseFloat(detailData.get("price").toString());
            orderDetail.setUnitPrice(unitPrice);
            orderDetail.setFinalPrice(num * unitPrice);
            orderDetail.setStatus(OrderDetailStatus.SUCCESS);
        } else if ("cruiseship".equals(type)) {
            orderDetail.setProductType(ProductType.cruiseship);
            Long priceId = Long.parseLong(detailData.get("priceId").toString());
            orderDetail.setCostId(priceId);
            CruiseShipRoomDate date = cruiseShipRoomDateService.findByRoomIdAndDate(priceId, orderDetail.getPlayDate());
            Float unitPrice = date.getSalePrice();
            orderDetail.setUnitPrice(unitPrice);
            orderDetail.setTotalPrice(unitPrice * num);
            orderDetail.setFinalPrice(unitPrice * num);
        }

        orderDetail.setOrder(order);
        orderDetail.setHasComment(false);

        orderDetailDao.save(orderDetail);
        return orderDetail;
    }

    //判断酒店需要担保时操作
    private void hotelDetailCreate(Boolean status, Map<String, Object> creditCardDetail, OrderDetail orderDetail, Order order, Map<String, Object> detailData) {

        // 酒店
        orderDetail.setProductType(ProductType.hotel);
        Long priceId = Long.parseLong(detailData.get("priceId").toString());
        orderDetail.setCostId(priceId);
        String dateString = detailData.get("startTime").toString();
        Date startDate = stringToDate(dateString);
        orderDetail.setPlayDate(startDate);
        // 离开酒店日期
        String leaveDateString = detailData.get("endTime").toString();
        Date leaveDate = stringToDate(leaveDateString);
        orderDetail.setLeaveDate(leaveDate);
        Integer days = daysBetween(startDate, leaveDate);
        if (days != null) {
            orderDetail.setDays(days);
        }
        //酒店担保时操作
        if (status) {
            CreditCard creditCard = new CreditCard();
            creditCard.setCardNum(creditCardDetail.get("cardNum").toString());
            creditCard.setCreditCardIdType(CreditCardIdType.valueOf(creditCardDetail.get("creditCardIdType").toString()));
            creditCard.setExpirationYear(Integer.valueOf(creditCardDetail.get("expirationYear").toString()));
            creditCard.setExpirationMonth(Integer.valueOf(creditCardDetail.get("expirationMonth").toString()));
            creditCard.setHolderName(creditCardDetail.get("holderName").toString());
            creditCard.setUser((Member) order.getUser());
            creditCard.setIdNo(creditCardDetail.get("idNo").toString());
            creditCard.setCvv(creditCardDetail.get("cvv").toString());
            creditCardService.save(creditCard);
            orderDetail.setCreditCard(creditCard);
        }

        // 酒店价格
        List<HotelPriceCalendar> calendarList = hotelPriceService.findTypePriceDate(orderDetail.getCostId(), orderDetail.getPlayDate(), orderDetail.getLeaveDate());
        if (calendarList.size() != days) {
            return;
        }
        Float price = 0f;
        for (HotelPriceCalendar hotelPriceCalendar : calendarList) {
            price += hotelPriceCalendar.getMember();
        }
        Float combineRate = 1f;
        Integer num = Integer.parseInt(detailData.get("num").toString());
        if (detailData.get("combineRate") != null) {
            combineRate = Float.valueOf(detailData.get("combineRate").toString());
        }
        orderDetail.setUnitPrice(price / days);
        orderDetail.setTotalPrice(num * price);
        orderDetail.setFinalPrice(num * price * combineRate);
        orderDetail.setCombinePay(num * price * (1 - combineRate));
    }

    public OrderInvoice createOrdreInvoice(Map<String, Object> orderInvoice) {
        if (orderInvoice != null) {
            OrderInvoice invoice = new OrderInvoice();
            invoice.setName((String) orderInvoice.get("name"));
            invoice.setAddress((String) orderInvoice.get("address"));
            invoice.setTelephone((String) orderInvoice.get("telephone"));
            invoice.setTitle((String) orderInvoice.get("title"));
            orderInvoiceService.save(invoice);
            return invoice;
        } else {
            return null;
        }
    }

    private Date stringToDate(String time) {
        if (!StringUtils.isNotBlank(time)) {
            return null;
        }
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date date = sdf.parse(time);
            return date;
        } catch (ParseException e) {
            logger.error("解析时间出现错误：" + e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    private Integer daysBetween(Date smdate, Date bdate) {
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            smdate = sdf.parse(sdf.format(smdate));
            bdate = sdf.parse(sdf.format(bdate));
            Calendar cal = Calendar.getInstance();
            cal.setTime(smdate);
            long time1 = cal.getTimeInMillis();
            cal.setTime(bdate);
            long time2 = cal.getTimeInMillis();
            long betweenDays = (time2 - time1) / (1000 * 3600 * 24);

            return Integer.parseInt(String.valueOf(betweenDays));
        } catch (ParseException e) {
            //
            logger.error("计算酒店天数出现错误：" + e.getLocalizedMessage());
            return null;
        }
    }

    /**
     * 计算几天后日期
     *
     * @param date
     * @param day
     * @return
     */
    private Date getDateAfter(Date date, int day) {
        Calendar now = Calendar.getInstance();
        now.setTime(date);
        now.set(Calendar.DATE, now.get(Calendar.DATE) + day);
        return now.getTime();
    }

    public List<OrderDetail> listByAuthorize(OrderDataForm orderDataFormVo, Page page, Boolean isSuperAdmin, Boolean isSiteAdmin, SysUser loginUser) {


        StringBuffer hqlSb = new StringBuffer("from OrderDetail toda where exists (select 1 from Order tod where toda.order.id = tod.id");
        List params = Lists.newArrayList();
        Map<String, Object> paraMap = new HashMap<String, Object>();
        if (!isSuperAdmin) {
            // 注释的写法也可以

            fomartOrderHql(hqlSb, orderDataFormVo.getOrder(), params);
            hqlSb.append(")");
            hqlSb.append(" and toda.product.user.sysSite.id=?");
            params.add(loginUser.getSysSite().getId());
            if (!isSiteAdmin) {
                // 注释的写法也可以
                hqlSb.append(" and toda.product.companyUnit.id=?");
                params.add(loginUser.getSysUnit().getId());

            }
            fomartOrderDetailHql(hqlSb, orderDataFormVo.getOrderDetail(), params);
        } else {
            hqlSb.append(")");
            fomartOrderDetailHql(hqlSb, orderDataFormVo.getOrderDetail(), params);
        }
        hqlSb.append(" order by toda.id desc");
        return orderDetailDao.findByHQL(hqlSb.toString(), page, params.toArray());
    }

    private void fomartOrderDetailHql(StringBuffer hqlSb, OrderDetail orderDetail, List params) {
        if (orderDetail == null) {
            return;
        }

        if (orderDetail.getPlayTime() != null) {
            hqlSb.append(" and toda.playDate = ?");
            params.add(orderDetail.getPlayTime());
        }

        if (orderDetail.getMinPlayDate() != null) {
            hqlSb.append(" and toda.playDate >= ?");
            params.add(orderDetail.getMinPlayDate());
        }

        if (orderDetail.getMaxPlayDate() != null) {
            hqlSb.append(" and toda.playDate <= ?");
            params.add(orderDetail.getMaxPlayDate());
        }


        if (StringUtils.isNotBlank(orderDetail.getSeatType())) {
            hqlSb.append(" and toda.seatType like '%" + orderDetail.getSeatType() + "%'");
        }

        if (orderDetail.getProduct() != null && orderDetail.getProduct().getId() != null) {
            hqlSb.append(" and toda.product.id = ?");
            params.add(orderDetail.getProduct().getId());
        }

        if (orderDetail.getLeaveTime() != null) {
            hqlSb.append(" and toda.leaveDate = ?");
            params.add(orderDetail.getLeaveTime());
        }

        if (orderDetail.getStatus() != null) {
            hqlSb.append(" and toda.status = ?");
            params.add(orderDetail.getStatus());
        }

        if (orderDetail.getProductType() != null) {
            hqlSb.append(" and toda.productType = ?");
            params.add(orderDetail.getProductType());
        }
    }

    private void fomartOrderHql(StringBuffer hqlSb, Order order, List params) {

        if (order == null) {
            return;
        }
        if (StringUtils.hasText(order.getRecName())) {
            hqlSb.append(" and tod.recName like '%'||?||'%'");
            params.add(order.getRecName());
        }
        if (StringUtils.hasText(order.getOrderNo())) {
            hqlSb.append(" and tod.orderNo like '%'||?||'%'");
            params.add(order.getOrderNo());
        }
        if (StringUtils.isNotBlank(order.getSearchKeyword())) {
            hqlSb.append(" and (tod.recName like '%" + order.getSearchKeyword() + "%'");
//            hqlSb.append(" and (tod.recName like '%'||?||'%'");
//            params.add(order.getSearchKeyword());
            hqlSb.append(" or tod.mobile like '%" + order.getSearchKeyword() + "%'");
//            hqlSb.append(" or tod.mobile like '%'||?||'%'");
//            params.add(order.getSearchKeyword());
            hqlSb.append(" or tod.orderNo like '%" + order.getSearchKeyword() + "%')");
//            hqlSb.append(" or tod.orderNo like '%'||?||'%')");
//            params.add(order.getSearchKeyword());
        }
        if (StringUtils.hasText(order.getMobile())) {
            hqlSb.append(" and tod.mobile = ?");
            params.add(order.getMobile());
        }

        if (order.getIncludeOrderTypes() != null && order.getIncludeOrderTypes().size() > 0) {
            hqlSb.append(" and (");
            List<OrderType> orderTypes = order.getIncludeOrderTypes();
            for (int i = 0; i < orderTypes.size(); i++) {
                if (i == 0) {
                    hqlSb.append(" tod.orderType =?");
                    params.add(orderTypes.get(i));
                } else {
                    hqlSb.append(" or tod.orderType =?");
                    params.add(orderTypes.get(i));
                }
            }
            hqlSb.append(")");
        }

        if (order.getStartTime() != null) {
            hqlSb.append(" and tod.createTime >= ?");
            params.add(DateUtils.getStartDay(order.getStartTime(), 0));
        }
        if (order.getEndTime() != null) {
            hqlSb.append(" and tod.createTime <= ?");
            params.add(DateUtils.getEndDay(order.getEndTime(), 0));
        }

        if (order.getStatus() != null) {
            hqlSb.append(" and tod.status = ?");
            params.add(order.getStatus());
        }
        if (order.getOrderType() != null) {
            hqlSb.append(" and tod.orderType = ?");
            params.add(order.getOrderType());
        }
        if (order.getFilterOrderTypes() != null && !order.getFilterOrderTypes().isEmpty()) {
            hqlSb.append(" and tod.orderType not in (");
            for (OrderType orderType : order.getFilterOrderTypes()) {
                hqlSb.append("?");
                params.add(order.getOrderType());
            }
            hqlSb.append(")");
        }

    }

    private DetachedCriteria createAuthorizeDetachedCriteria(OrderDataForm orderFormVo, Boolean isSuperAdmin, Boolean isSiteAdmin, SysUser loginUser) {
        DetachedCriteria orderCriteria = DetachedCriteria.forClass(Order.class, "order_");
        DetachedCriteria orderDetailCriteria = DetachedCriteria.forClass(OrderDetail.class, "orderDetail");
        DetachedCriteria productCriteria = orderDetailCriteria.createAlias("product", "product_");
        DetachedCriteria userCriteria = orderDetailCriteria.createAlias("product_.user", "sysUser_");

        /*if (orderFormVo.getOrder() != null) {
            createAliasWithOrder(orderFormVo.getOrder(), orderCriteria, orderDetailCriteria, productCriteria);
        }*/
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

    public List<OrderDetail> getOrderBillDetailBySummaryId(OrderBillSummary orderBillSummary, Page page) {
        Criteria<OrderDetail> criteria = new Criteria<OrderDetail>(OrderDetail.class);
        if (orderBillSummary.getId() != null) {
            criteria.eq("billSummaryId", orderBillSummary.getId());
        }
        if (orderBillSummary.getBillDate() != null) {
            criteria.eq("orderBillDate", orderBillSummary.getBillDate());
        }
        if (page == null) {
            return orderDetailDao.findByCriteria(criteria);
        }
        return orderDetailDao.findByCriteria(criteria, page);
    }

    /**
     * 按需字段查询列表
     *
     * @param orderBillSummary
     * @param page
     * @return
     */
    public List<OrderDetail> getOrderBillDetail(OrderBillSummary orderBillSummary, Page page) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("select new OrderDetail(d.id, d.totalPrice, d.orderBillPrice, d.refundBillAmount, o.orderNo, o.name, d.refundDate) ");
        hql.append("from OrderDetail d inner join d.order o where 1 = 1 ");
        if (orderBillSummary.getId() != null) {
            hql.append("and d.billSummaryId = :billSummaryId ");
            params.put("billSummaryId", orderBillSummary.getId());
        }
        if (orderBillSummary.getBillDate() != null) {
            hql.append("and d.orderBillDate = :orderBillDate ");
            params.put("orderBillDate", orderBillSummary.getBillDate());
        }
        if (orderBillSummary.getRefundBillSummaryId() != null) {
            hql.append("and d.refundBillSummaryId = :refundBillSummaryId ");
            params.put("refundBillSummaryId", orderBillSummary.getRefundBillSummaryId());
        }
        if (orderBillSummary.getOrderDetailStatus() != null) {
            hql.append("and d.status = :orderStatus ");
            params.put("orderStatus", orderBillSummary.getOrderDetailStatus());
        }

        hql.append("order by o.createTime desc ");
        if (page != null) {
            return orderDetailDao.findByHQL2ForNew(hql.toString(), page, params);
        }
        return orderDetailDao.findByHQL2ForNew(hql.toString(), params);
    }
}
