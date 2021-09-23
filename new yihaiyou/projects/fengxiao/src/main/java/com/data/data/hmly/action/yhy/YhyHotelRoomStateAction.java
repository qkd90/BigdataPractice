package com.data.data.hmly.action.yhy;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.ProValidCodeService;
import com.data.data.hmly.service.common.entity.ProValidCode;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.HotelRoomService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.enums.HotelRoomStatus;
import com.data.data.hmly.service.order.HotelRoomStateService;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.HotelRoomMember;
import com.data.data.hmly.service.order.entity.HotelRoomState;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/12/8.
 */
public class YhyHotelRoomStateAction extends FrameBaseAction {

    private Map<String, Object> result = new HashMap<String, Object>();

    private Long productId;
    private Long orderDetailId;
    private List<HotelRoomState> roomStateList = new ArrayList<HotelRoomState>();
    private String roomNums;

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;


    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private HotelRoomService hotelRoomService;
    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;
    @Resource
    private HotelRoomStateService hotelRoomStateService;
    @Resource
    private ProValidCodeService proValidCodeService;

    public Result getHotelRoomStateList() {
        SysUser loginUser = getLoginUser();
        Integer pageIndex = start / length + 1;
        Page page = new Page(pageIndex, length);
        HotelPrice condition = new HotelPrice();
        Hotel conditionHotel = new Hotel();
        conditionHotel.setId(productId);
        condition.setHotel(conditionHotel);
        List<HotelPrice> priceList = hotelPriceService.findByHotel(condition, loginUser.getSysUnit().getCompanyUnit(), null);
        if (priceList != null && priceList.size() > 0) {
            List<Long> priceIdList = new ArrayList<Long>();
            for (HotelPrice hotelPrice : priceList) {
                priceIdList.add(hotelPrice.getId());
            }
            String searchContent = (String) getParameter("searchContent");
            String startDateStr = (String) getParameter("startDate");
            String endDateStr = (String) getParameter("endDate");
            String statusStr = (String) getParameter("status");
            Date startDate = null;
            Date endDate = null;
            OrderDetail orderDetailCondition = new OrderDetail();
            if (StringUtils.hasText(startDateStr)) {
                startDate = DateUtils.getDate(startDateStr, "yyyy-MM-dd");
            }
            if (StringUtils.hasText(endDateStr)) {
                endDate = DateUtils.getDate(endDateStr, "yyyy-MM-dd");
            }
            if (StringUtils.hasText(statusStr)) {
                orderDetailCondition.setStatus(OrderDetailStatus.valueOf(statusStr));
            }
            orderDetailCondition.setMinPlayDate(startDate);
            orderDetailCondition.setMaxPlayDate(endDate);
            List<OrderDetail> orderDetailList = orderDetailService.getByHotelPrice(priceIdList, searchContent, orderDetailCondition, page);
            for (OrderDetail orderDetail : orderDetailList) {
                if (OrderDetailStatus.CHECKIN.equals(orderDetail.getStatus())
                        || OrderDetailStatus.CHECKOUT.equals(orderDetail.getStatus())) {
                    orderDetail.setRoomNums(hotelRoomStateService.getRoomNumsByOrderDetail(orderDetail.getId()));
                }
            }
            result.put("data", orderDetailList);
            result.put("draw", draw);
            result.put("recordsTotal", page.getTotalCount());
            result.put("recordsFiltered", page.getTotalCount());
            result.put("success", true);
            result.put("msg", "");
        } else {
            result.put("success", false);
            result.put("msg", "暂无可用房型!请先添加房型!");
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("order");
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result getCheckInfo() {
        if (orderDetailId != null) {
            OrderDetail orderDetail = orderDetailService.get(orderDetailId);
            Order order = orderDetail.getOrder();
            HotelPrice hotelPrice = hotelPriceService.get(orderDetail.getCostId());
            String roomNumbers = hotelRoomService.getAvailableRoomNumbers(hotelPrice.getId());
            ProValidCode proValidCode = proValidCodeService.findByOrder(order.getId(), orderDetail.getId(), 1);
            List<HotelRoomState> roomStateList = hotelRoomStateService.findByOrderDetail(orderDetail.getId());
            result.put("orderDetailId", orderDetail.getId());
            result.put("recName", order.getRecName());
            result.put("mobile", order.getMobile());
            result.put("playDate", DateUtils.format(orderDetail.getPlayDate(), "yyyy-MM-dd"));
            result.put("leaveDate", DateUtils.format(orderDetail.getLeaveDate(), "yyyy-MM-dd"));
            result.put("remark", orderDetail.getRemark());
            result.put("num", orderDetail.getNum());
            result.put("days", orderDetail.getDays());
            result.put("seatType", orderDetail.getSeatType());
            result.put("totalPrice", orderDetail.getTotalPrice());
            result.put("capacity", hotelPrice.getCapacity());
            result.put("roomNumbers", roomNumbers);
            result.put("roomStateList", roomStateList);
            if (proValidCode != null) {
                result.put("validateCode", proValidCode.getCode());
            }
            result.put("success", true);
            result.put("msg", "");
        } else {
            result.put("success", false);
            result.put("msg", "");
            result.put("msg", "订单详情存在");
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("memberList");
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result doValidateCheckIn() {
        String validateCode = getRequest().getParameter("validateCode");
        if (orderDetailId != null && StringUtils.hasText(validateCode)) {
            OrderDetail orderDetail = orderDetailService.get(orderDetailId);
            Order order = orderDetail.getOrder();
            ProValidCode proValidCode = proValidCodeService.findByOrder(order.getId(), orderDetail.getId(), 0);
            if (proValidCode != null) {
                if (validateCode.equals(proValidCode.getCode())) {
                    result.put("validate", "success");
                    result.put("success", true);
                    result.put("msg", "验证码有效!");
                } else {
                    result.put("validate", "error");
                    result.put("success", true);
                    result.put("msg", "验证码无效!");
                }
            } else {
                result.put("validate", "error");
                result.put("success", true);
                result.put("msg", "验证失败! 订单无可用验证码或验证码已被使用!");
            }
        } else {
            result.put("validate", false);
            result.put("success", false);
            result.put("msg", "验证失败! 订单信息或验证码信息错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result doSaveHotelRoomState() {
        SysUser loginUser = getLoginUser();
        String validateCode = getRequest().getParameter("validateCode");
        if (orderDetailId != null && StringUtils.hasText(validateCode)) {
            OrderDetail orderDetail = orderDetailService.get(orderDetailId);
            Order order = orderDetail.getOrder();
            HotelPrice hotelPrice = hotelPriceService.get(orderDetail.getCostId());
            ProValidCode proValidCode = proValidCodeService.findByOrder(order.getId(), orderDetail.getId(), 0);
            if (proValidCode != null) {
                if (validateCode.equals(proValidCode.getCode())) {
                    for (HotelRoomState hotelRoomState : roomStateList) {
                        hotelRoomState.setOrderDetail(orderDetail);
                        hotelRoomState.setHotelPrice(hotelPrice);
                        hotelRoomState.setOrderDate(order.getPlayDate());
                        hotelRoomState.setCheckInDate(new Date());
                        hotelRoomState.setNights(orderDetail.getDays());
                        hotelRoomState.setCreateTime(new Date());
                        for (HotelRoomMember roomMember : hotelRoomState.getMemberList()) {
                            roomMember.setHotelRoomState(hotelRoomState);
                            roomMember.setCreateTime(new Date());
                        }
                    }
                    hotelRoomStateService.saveHotelRoomStateList(roomStateList);
                    // update hotel room statue
                    String[] roomNums = this.roomNums.split(",");
                    for (String roomNum : roomNums) {
                        hotelRoomService.updateRoomStatus(hotelPrice.getPriceId(), roomNum, HotelRoomStatus.CHECK_IN);
                    }
                    // update order detail status
                    orderDetail.setStatus(OrderDetailStatus.CHECKIN);
                    if (OrderType.hotel.equals(order.getOrderType())) {
                        order.setStatus(OrderStatus.CHECKIN);
                        orderService.update(order);
                    }
                    String remark = getRequest().getParameter("remark");
                    if (StringUtils.hasText(remark)) {
                        orderDetail.setRemark(remark);
                    }
                    // update validate code info
                    proValidCode.setUsed(1);
                    proValidCode.setValidateUser(loginUser);
                    proValidCode.setValidTime(new Date());
                    proValidCode.setUpdateTime(new Date());
                    orderDetailService.update(orderDetail);
                    proValidCodeService.update(proValidCode);
                    result.put("success", true);
                    result.put("msg", "");
                } else {
                    result.put("validate", "error");
                    result.put("success", false);
                    result.put("msg", "验证失败! 验证码不正确!");
                }
            } else {
                result.put("success", false);
                result.put("msg", "验证失败! 订单无可用验证码或验证码已被使用!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "办理入住失败! 无法查询订单详情信息或验证码为空!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result doCheckOut() {
        if (orderDetailId != null) {
            OrderDetail orderDetail = orderDetailService.get(orderDetailId);
            Order order = orderDetail.getOrder();
            HotelPrice hotelPrice = hotelPriceService.get(orderDetail.getCostId());
            List<HotelRoomState> roomStateList = hotelRoomStateService.findByOrderDetail(orderDetail.getId());
            for (HotelRoomState hotelRoomState : roomStateList) {
                // update hotel room state
                hotelRoomState.setCheckOutDate(new Date());
                // update room status
                hotelRoomService.updateRoomStatus(hotelPrice.getPriceId(), hotelRoomState.getRoomNo(), HotelRoomStatus.AVAILABLE);
            }
            hotelRoomStateService.updateAll(roomStateList);
            // update order detail
            orderDetail.setStatus(OrderDetailStatus.CHECKOUT);
            if (OrderType.hotel.equals(order.getOrderType())) {
                order.setStatus(OrderStatus.CHECKOUT);
                orderService.update(order);
            }
            String remark = getRequest().getParameter("remark");
            if (StringUtils.hasText(remark)) {
                orderDetail.setRemark(remark);
            }
            orderDetailService.update(orderDetail);
            result.put("success", true);
            result.put("msg", "");
        } else {
            result.put("success", false);
            result.put("msg", "办理退房失败! 无法查询订单详情信息!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getOrderDetailId() {
        return orderDetailId;
    }

    public void setOrderDetailId(Long orderDetailId) {
        this.orderDetailId = orderDetailId;
    }

    public List<HotelRoomState> getRoomStateList() {
        return roomStateList;
    }

    public void setRoomStateList(List<HotelRoomState> roomStateList) {
        this.roomStateList = roomStateList;
    }

    public String getRoomNums() {
        return roomNums;
    }

    public void setRoomNums(String roomNums) {
        this.roomNums = roomNums;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }
}
