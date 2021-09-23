package com.data.data.hmly.action.outOrder;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.entity.HotelRoomData;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.outOrder.HotelRoomDataService;
import com.data.data.hmly.service.outOrder.JszxOrderDetailService;
import com.data.data.hmly.service.outOrder.entity.JszxOrderDetail;
import com.data.data.hmly.service.outOrder.entity.enums.JszxOrderDetailStatus;
import com.framework.hibernate.util.Page;
import com.framework.struts.JsonDateValueProcessor;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/9/7.
 */
public class HotelRoomAction extends FrameBaseAction {

    @Resource
    private HotelService hotelService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private JszxOrderDetailService jszxOrderDetailService;
    @Resource
    private HotelRoomDataService hotelRoomDataService;



    private Hotel hotel = new Hotel();
    private Long hotelId;
    private Integer days;
    private String customerName;
    private String idNumber;
    private String tel;
    private Long orderDetailId;
    private String optType;

    private Integer page = 1;
    private Integer rows = 10;

    public Result index() {
        return dispatch();
    }



    public Result listRoomByHotel() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Page page = new Page(this.page, this.rows);
        if (hotel.getSource() == null) {
            hotel.setSource(ProductSource.LXB);
        }
        List<Hotel> hotelList = hotelService.getShowHotelList(hotel, page, isSupperAdmin(), isSiteAdmin(), getLoginUser());
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("extend");
        return datagrid(hotelList, page.getTotalCount(), jsonConfig);
    }

    public Result getRoomStatus() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        List<HotelRoomData> roomDataList = new ArrayList<HotelRoomData>();
        if (hotelId != null && days != null) {
            Hotel hotel = hotelService.get(hotelId);
            List<HotelPrice> hotelPriceList = hotelPriceService.findByHotel(hotelId, null);
            for (HotelPrice hotelPrice : hotelPriceList) {
                HotelRoomData hotelRoomData = new HotelRoomData();
                hotelRoomData.setHotelPrice(hotelPrice);
                hotelRoomData.setHotel(hotel);
                // 查找对应的订单
                List<OrderDetail> orderDetailList = orderDetailService.getByHotelPrice(hotelPrice, days);
                hotelRoomData.setOrderDetailList(orderDetailList);
                roomDataList.add(hotelRoomData);
            }
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("orderTouristList");
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd"));
        return datagrid(roomDataList, roomDataList.size(), jsonConfig);
    }

    public Result getHotelRoomDataTable() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (hotelId != null && days != null) {
            List<HotelRoomData> roomDataList = new ArrayList<HotelRoomData>();
            Hotel hotel = hotelService.get(hotelId);
            List<HotelPrice> hotelPriceList = hotelPriceService.findByHotel(hotelId, null);
            for (HotelPrice hotelPrice : hotelPriceList) {
                HotelRoomData hotelRoomData = new HotelRoomData();
                hotelRoomData.setHotelPrice(hotelPrice);
                hotelRoomData.setHotel(hotel);
                // 查找对应的订单
                List<OrderDetail> orderDetailList = orderDetailService.getByHotelPrice(hotelPrice, days);
                hotelRoomData.setOrderDetailList(orderDetailList);
                roomDataList.add(hotelRoomData);
            }
            result = hotelRoomDataService.getHotelRoomDataTable(roomDataList, result, days);
        } else {
            result.put("success", false);
            result.put("msg", "请选择酒店或者日期!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result doVerifyCheckin() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (StringUtils.hasText(customerName) && StringUtils.hasText(idNumber) && hotel.getId() != null) {
            Hotel hotel = hotelService.get(this.hotel.getId());
            if (hotel != null) {
                OrderTourist orderTourist = new OrderTourist();
                orderTourist.setName(customerName);
                orderTourist.setIdNumber(idNumber);
                orderTourist.setTel(tel);
                List<OrderDetail> orderDetailList = orderDetailService.getByCustomer(hotel, orderTourist, days);
                if (!orderDetailList.isEmpty()) {
                    for (OrderDetail orderDetail : orderDetailList) {
                        HotelPrice hotelPrice = hotelPriceService.get(orderDetail.getCostId());
                        orderDetail.setHotelPrice(hotelPrice);
                    }
                    result.put("success", true);
                    result.put("orderDetailList", orderDetailList);
                } else {
                    result.put("success", false);
                    result.put("msg", "没有找到入住登记信息!");
                }
            } else {
                result.put("success", false);
                result.put("msg", "验证的酒店不存在, 请确认!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "酒店或客户信息不完整! 无法验证!");
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd"));
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result confirmCheckinInfo() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (orderDetailId != null) {
            OrderDetail orderDetail = orderDetailService.findFullById(orderDetailId);
            Long costId = orderDetail.getCostId();
            if (orderDetail != null && costId != null) {
                HotelPrice hotelPrice = hotelPriceService.findFullById(costId);
                if (hotelPrice != null) {
                    result.put("success", true);
                    result.put("orderDetail", orderDetail);
                    result.put("hotelPrice", hotelPrice);
                    if ("out".equals(optType)) {
                        // 退房验证用
                        Date nowDate = new Date();
                        Date playDate = orderDetail.getPlayDate();
                        Date leaveDate = orderDetail.getLeaveDate();
                        Calendar nowCalendar = Calendar.getInstance();
                        Calendar playCalendar = Calendar.getInstance();
                        Calendar leaveCalendar = Calendar.getInstance();
                        nowCalendar.setTime(nowDate);
                        playCalendar.setTime(playDate);
                        leaveCalendar.setTime(leaveDate);
                        Integer bookingNights = leaveCalendar.get(Calendar.DAY_OF_YEAR) - playCalendar.get(Calendar.DAY_OF_YEAR);
                        Integer checkinNights = nowCalendar.get(Calendar.DAY_OF_YEAR) - playCalendar.get(Calendar.DAY_OF_YEAR);
                        result.put("nowDate", nowDate);
                        result.put("bookingNights", bookingNights);
                        result.put("checkinNights", checkinNights);
                    }
                } else {
                    result.put("success", false);
                    result.put("msg", "酒店价格信息不存在!");
                }
            } else {
                result.put("success", false);
                result.put("msg", "订单详情或者价格信息id不存在!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "没有订单详细ID, 无法确认!");
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("order", "hotel", "orderTouristList");
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd"));
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result doConfirmCheckin() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (orderDetailId != null) {
            OrderDetail orderDetail = orderDetailService.findFullById(orderDetailId);
            if (orderDetail != null && StringUtils.hasText(orderDetail.getRealOrderId())) {
                Long jszxOrderDetailId = Long.parseLong(orderDetail.getRealOrderId());
                JszxOrderDetail jszxOrderDetail = jszxOrderDetailService.load(jszxOrderDetailId);
                Calendar nowCalendar = Calendar.getInstance();
                Date nowDate = new Date();
                nowCalendar.setTime(nowDate);
                Calendar checkinCalendar = Calendar.getInstance();
                checkinCalendar.setTime(orderDetail.getPlayDate());
                if (checkinCalendar.get(Calendar.DAY_OF_YEAR) != nowCalendar.get(Calendar.DAY_OF_YEAR)) {
                    result.put("success", false);
                    result.put("msg", "办理入住失败(入住时间不正确!, 入住时间应为: " + DateUtils.format(orderDetail.getPlayDate(), "yyyy-MM-dd") + ")");
                } else {
                    orderDetail.setStatus(OrderDetailStatus.CHECKIN);
                    orderDetail.setApiResult(DateUtils.format(nowDate, "yyyy-MM-dd HH:mm:ss") + "已办理入住");
                    orderDetailService.update(orderDetail);
                    jszxOrderDetail.setUseStatus(JszxOrderDetailStatus.CHECKIN);
                    jszxOrderDetailService.update(jszxOrderDetail);
                    result.put("success", true);
                    result.put("msg", "办理入住成功!");
                }
            } else {
                result.put("success", false);
                result.put("msg", "缺少供应商订单详情!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "没有订单详细ID!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result doConfirmCheckout() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Map<String, Object> result = new HashMap<String, Object>();
        if (orderDetailId != null) {
            OrderDetail orderDetail = orderDetailService.findFullById(orderDetailId);
            if (orderDetail != null && StringUtils.hasText(orderDetail.getRealOrderId())) {
                Long jszxOrderDetailId = Long.parseLong(orderDetail.getRealOrderId());
                JszxOrderDetail jszxOrderDetail = jszxOrderDetailService.load(jszxOrderDetailId);
                Calendar nowCalendar = Calendar.getInstance();
                Date nowDate = new Date();
                nowCalendar.setTime(nowDate);
                orderDetail.setStatus(OrderDetailStatus.CHECKOUT);
                orderDetail.setApiResult(orderDetail.getApiResult() + "/" + DateUtils.format(nowDate, "yyyy-MM-dd HH:mm:ss") + "已办理退房");
                orderDetailService.update(orderDetail);
                jszxOrderDetail.setUseStatus(JszxOrderDetailStatus.CHECKOUT);
                jszxOrderDetailService.update(jszxOrderDetail);
                result.put("success", true);
                result.put("msg", "办理退房成功!");
            } else {
                result.put("success", false);
                result.put("msg", "缺少供应商订单详情!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "没有订单详细ID!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Hotel getHotel() {
        return hotel;
    }

    public void setHotel(Hotel hotel) {
        this.hotel = hotel;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Integer getDays() {
        return days;
    }

    public void setDays(Integer days) {
        this.days = days;
    }

    public String getCustomerName() {
        return customerName;
    }

    public void setCustomerName(String customerName) {
        this.customerName = customerName;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public String getOptType() {
        return optType;
    }

    public void setOptType(String optType) {
        this.optType = optType;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }
}
