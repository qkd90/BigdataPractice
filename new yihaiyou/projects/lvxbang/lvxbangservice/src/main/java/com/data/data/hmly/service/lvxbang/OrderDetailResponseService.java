package com.data.data.hmly.service.lvxbang;


import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.lvxbang.response.OrderDetailResponse;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vacuity on 16/1/12.
 */

@Service
public class OrderDetailResponseService {

    @Resource
    private PersonalOrderService personalOrderService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private TrafficPriceService trafficPriceService;

    public OrderDetailResponse orderToTicketDetail(OrderDetail orderDetail) {
        //
        OrderDetailResponse orderDetailResponse = orderToResponse(orderDetail);

        orderDetailResponse.setPriceId(orderDetail.getCostId());
        if (orderDetail.getCity() != null) {
          orderDetailResponse.setCityId(orderDetail.getCity().getId());
        }
        if (orderDetail.getDay() != null) {
          orderDetailResponse.setDay(orderDetail.getDay());
        }
        return orderDetailResponse;
    }

    public OrderDetailResponse orderToHotelDetail(OrderDetail orderDetail) {
        //
        OrderDetailResponse orderDetailResponse = orderToResponse(orderDetail);

        orderDetailResponse.setLeaveDate(orderDetail.getLeaveDate());
        orderDetailResponse.setDays(orderDetail.getDays());
        orderDetailResponse.setSinglePrice(orderDetail.getUnitPrice() / orderDetail.getDays());
        orderDetailResponse.setPriceId(orderDetail.getCostId());
        orderDetailResponse.setSource(String.valueOf(orderDetail.getProduct().getSource()));
//        HotelPrice hotelPrice = new HotelPrice();
//        Hotel hotel = new Hotel();
//        hotel.setId(orderDetail.getProduct().getId());
//        hotelPrice.setHotel(hotel);
//        hotelPrice.setRatePlanCode(orderDetail.getRatePlanCode());
//        List<HotelPrice> hotelPriceList = hotelPriceService.list(hotelPrice, null);
        HotelPrice hotelPrice = hotelPriceService.get(orderDetail.getCostId());
        if (hotelPrice == null) {
            return orderDetailResponse;
        }
        if (hotelPrice.getBreakfast() != null && hotelPrice.getBreakfast()) {
            orderDetailResponse.setBreakfast("含早餐");
        } else {
            orderDetailResponse.setBreakfast("不含早餐");
        }
        orderDetailResponse.setPriceStatus(hotelPrice.getStatus());
        orderDetailResponse.setCreditCard(orderDetail.getCreditCard());
        return orderDetailResponse;
    }

    public OrderDetailResponse orderToTrafficDetail(OrderDetail orderDetail) {
        Order order = orderDetail.getOrder();
        OrderDetailResponse orderDetailResponse = orderToResponse(orderDetail);
        switch (order.getOrderType()) {
            case flight:
                orderDetailResponse.setTrafficType("机票");
                break;
            case train:
                orderDetailResponse.setTrafficType("火车票");
                break;
            default:
                break;
        }
        if (order.getOrderDetails().isEmpty()) {
            return orderDetailResponse;
        }
        Long priceId = orderDetail.getCostId();
        TrafficPrice trafficPrice = trafficPriceService.get(priceId);
        orderDetailResponse.setTrafficTime(timeToString(trafficPrice.getTraffic().getFlightTime()));
        orderDetailResponse.setLeaveTime(trafficPrice.getTraffic().getLeaveTime());
        orderDetailResponse.setArriveTime(trafficPrice.getTraffic().getArriveTime());
        orderDetailResponse.setPriceId(priceId);
        orderDetailResponse.setPrice(trafficPrice.getPrice());
        orderDetailResponse.setCompany(trafficPrice.getTraffic().getCompany());
        orderDetailResponse.setDate(trafficPrice.getLeaveTime());
        orderDetailResponse.setLeaveDate(trafficPrice.getArriveTime());
        orderDetailResponse.setTrafficCode(trafficPrice.getTraffic().getTrafficCode());
        orderDetailResponse.setLeavePort(trafficPrice.getTraffic().getLeaveTransportation().getName());
        orderDetailResponse.setArrivePort(trafficPrice.getTraffic().getArriveTransportation().getName());
        orderDetailResponse.setLeaveCity(processCityName(trafficPrice.getTraffic().getLeaveCity().getName()));
        orderDetailResponse.setArriveCity(processCityName(trafficPrice.getTraffic().getArriveCity().getName()));
        orderDetailResponse.setAdditionalFuelTax(trafficPrice.getAdditionalFuelTax());
        orderDetailResponse.setAirportBuildFee(trafficPrice.getAirportBuildFee());
        return orderDetailResponse;
    }

    public OrderDetailResponse orderToResponse(OrderDetail orderDetail) {
        //
        Order order = orderDetail.getOrder();
        OrderDetailResponse orderDetailResponse = new OrderDetailResponse();
        orderDetailResponse.setId(order.getId());
        orderDetailResponse.setStatus(order.getStatus());
        if (orderDetail.getStatus() == null) {
           orderDetail.setStatus(OrderDetailStatus.WAITING);
        }
        orderDetailResponse.setDetailStatus(orderDetail.getStatus());
        orderDetailResponse.setPrintStatus(personalOrderService.statusToString(order.getStatus()));
        orderDetailResponse.setOrderNo(order.getOrderNo());
        orderDetailResponse.setOrderDate(order.getCreateTime());
        orderDetailResponse.setCost(order.getPrice());
        if (orderDetail.getProductType().equals(ProductType.scenic)) {
            orderDetailResponse.setProductId(((Ticket) orderDetail.getProduct()).getScenicInfo().getId());
        } else {
            orderDetailResponse.setProductId(orderDetail.getProduct().getId());
        }
//        orderDetailResponse.setName(order.getName());
        orderDetailResponse.setName(orderDetail.getProduct().getName());
        orderDetailResponse.setContactsName(order.getRecName());
        orderDetailResponse.setTel(order.getMobile());
        // confirm No.
        orderDetailResponse.setSingleCost(orderDetail.getFinalPrice());
        orderDetailResponse.setDate(orderDetail.getPlayDate());
        orderDetailResponse.setPrice(orderDetail.getUnitPrice());
        orderDetailResponse.setNum(orderDetail.getNum());
        orderDetailResponse.setType(orderDetail.getSeatType());
        orderDetailResponse.setSingleRoomPrice(orderDetail.getSingleRoomPrice());
        List<OrderTourist> orderTouristList = orderDetail.getOrderTouristList();
//        for (OrderTourist orderTourist : orderTouristList) {
//            //
//            String idNum = orderTourist.getIdNumber();
//            orderTourist.setIdNumber(idNum);
//        }
        orderDetailResponse.setTouristList(orderTouristList);
        return orderDetailResponse;
    }


    /**
     * 把136分钟转化为2h16m
     *
     * @param flightTime
     * @return
     */
    private String timeToString(Long flightTime) {
        //
        Long time = flightTime;
        Long day = time / (60 * 24);
        time -= day * 24 * 60;
        Long hour = time / 60;
        time -= hour * 60;
        Long minute = time;
        String flightTimeString = "";
        if (day.intValue() != 0) {
            flightTimeString += day + "d";
        }
        if (hour.intValue() != 0) {
            flightTimeString += hour + "h";
        }
        if (minute.intValue() != 0) {
            flightTimeString += minute + "m";
        }
        return flightTimeString;
    }

    private String processCityName(String name) {
        //
        String[] names = name.split("市");
        return names[0];
    }
}
