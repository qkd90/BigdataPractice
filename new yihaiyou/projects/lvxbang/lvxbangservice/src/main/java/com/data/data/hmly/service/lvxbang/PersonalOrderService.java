package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.lvxbang.response.PersonalOrderDetailResponse;
import com.data.data.hmly.service.lvxbang.response.PersonalOrderResponse;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

/**
 * Created by vacuity on 16/1/12.
 */

@Service
public class PersonalOrderService {


    @Resource
    private TrafficPriceService trafficPriceService;


    public List<PersonalOrderResponse> processOrder(List<Order> orderList) {
        //
        List<PersonalOrderResponse> personalOrderResponseList = new ArrayList<PersonalOrderResponse>();
        for (Order order : orderList) {
            //
            personalOrderResponseList.add(orderToResponse(order));
        }
        return personalOrderResponseList;
    }

    public PersonalOrderResponse orderToResponse(Order order) {
        //
        PersonalOrderResponse personalOrderResponse = new PersonalOrderResponse();
        personalOrderResponse.setId(order.getId());
        personalOrderResponse.setOrderNo(order.getOrderNo());
        personalOrderResponse.setOrderDate(formatDate(order.getCreateTime()));

        if (order.getOrderType() != OrderType.plan && order.getOrderDetails().size() == 1) {
            if (order.getOrderDetails().get(0).getStatus() != null && !"".equals(order.getOrderDetails().get(0).getStatus().getDescription())) {
                personalOrderResponse.setStatus(order.getOrderDetails().get(0).getStatus().getDescription());
            } else {
                personalOrderResponse.setStatus(order.getStatus().getDescription());
            }
        } else if (OrderType.hotel.equals(order.getOrderType())) {
            personalOrderResponse.setStatus(order.getOrderDetails().get(0).getStatus().getDescription());
        } else {
            personalOrderResponse.setStatus(order.getStatus().getDescription());
        }
        personalOrderResponse.setOrderType(order.getOrderType());
        personalOrderResponse.setOrderTypeDesc(order.getOrderType().getDescription());
        personalOrderResponse.setName(order.getName());
        // type seatType num
        if (order.getOrderType() == OrderType.plan) {
            processPlanDetails(order, personalOrderResponse);
        } else {
            processSingleDetail(order, personalOrderResponse);
        }

        return personalOrderResponse;
    }


    //
    private void processSingleDetail(Order order, PersonalOrderResponse personalOrderResponse) {
        //
        List<PersonalOrderDetailResponse> detailList = new ArrayList<PersonalOrderDetailResponse>();
        if (order.getOrderDetails().isEmpty()) {
            return;
        }
        List<OrderDetail> orderDetailList = singleDetailList(order);
        for (OrderDetail orderDetail : orderDetailList) {
            PersonalOrderDetailResponse detail = new PersonalOrderDetailResponse();
            detail.setDetailType(orderDetail.getSeatType());
            // 'plan','ticket','train','flight','hotel'
            detail.setCost(orderDetail.getFinalPrice());
            processSingleDetailType(detail, order, orderDetail);
            HashSet<String> tourist = new HashSet<String>();
            String touristName = "";
            for (OrderTourist orderTourist : orderDetail.getOrderTouristList()) {
                //
                if ("".equals(touristName)) {
                    touristName = orderTourist.getName();
                }
                tourist.add(orderTourist.getName());
            }
            if (tourist.size() > 1) {
                touristName += "……";
            }
            detail.setTourist(touristName);
            detail.setPlayDate(formatDate(orderDetail.getPlayDate()));
            if (orderDetail.getStatus() == null) {
                detail.setStatus(order.getStatus().getDescription());
            } else {
                detail.setStatus(orderDetail.getStatus().getDescription());
            }
            detailList.add(detail);
        }
        personalOrderResponse.setOrderDetailList(detailList);
    }

    private void processSingleDetailType(PersonalOrderDetailResponse detail, Order order, OrderDetail orderDetail) {
        String num = "";
        String detailUrl = "";
        switch (order.getOrderType()) {
            case ticket:
                detail.setType("门票");
                num = orderDetail.getNum() + "张";
                detail.setName(order.getName());
                detailUrl = "/lvxbang/order/ticketOrderDetail.jhtml?orderId=" + order.getId() + "&detailId=" + orderDetail.getId();
                break;
            case line:
                detail.setType("出游");
                num = orderDetail.getNum() + "张";
                detail.setName(order.getName());
                detailUrl = "/lvxbang/order/lineOrderDetail.jhtml?orderId=" + order.getId();
                break;
            case train:
                detail.setType("火车");
                detail.setName(trafficName(orderDetail.getCostId()));
                num = orderDetail.getNum() + "张";
                if (order.getOrderDetails().size() > 1) {
                    detailUrl = "/lvxbang/order/returnOrderDetail.jhtml?orderId=" + order.getId();
                } else {
                    detail.setName(order.getName());
                    detailUrl = "/lvxbang/order/singleOrderDetail.jhtml?orderId=" + order.getId();
                }
                break;
            case flight:
                detail.setType("飞机");
                detail.setName(trafficName(orderDetail.getCostId()));
                num = orderDetail.getNum() + "张";
                if (order.getOrderDetails().size() > 1) {
                    detailUrl = "/lvxbang/order/returnOrderDetail.jhtml?orderId=" + order.getId();
                } else {
                    detail.setName(order.getName());
                    detailUrl = "/lvxbang/order/singleOrderDetail.jhtml?orderId=" + order.getId();
                }
                break;
            case ship:
                detail.setType("船票");
                detail.setName(trafficName(orderDetail.getCostId()));
                num = orderDetail.getNum() + "张";
                if (order.getOrderDetails().size() > 1) {
                    detailUrl = "/lvxbang/order/returnOrderDetail.jhtml?orderId=" + order.getId();
                } else {
                    detail.setName(order.getName());
                    detailUrl = "/lvxbang/order/singleOrderDetail.jhtml?orderId=" + order.getId();
                }
                break;
            case hotel:
                detail.setType("酒店");
                num = orderDetail.getDays() + "天/" + orderDetail.getNum() + "间";
                detail.setName(order.getName());
                detailUrl = "/lvxbang/order/hotelOrderDetail.jhtml?orderId=" + order.getId();
                break;
            default:
                break;
        }
        detail.setNum(num);
        detail.setDetailUrl(detailUrl);
    }

    private List<OrderDetail> singleDetailList(Order order) {
        List<OrderDetail> orderDetailList = Lists.newArrayList();
        if (order.getOrderType().equals(OrderType.line)) {
            for (OrderDetail orderDetail : order.getOrderDetails()) {
                if (orderDetail.getProductType().equals(ProductType.line)) {
                    orderDetailList.add(orderDetail);
                }
            }
        } else {
            orderDetailList = order.getOrderDetails();
        }
        return orderDetailList;
    }

    // 行程
    public void processPlanDetails(Order order, PersonalOrderResponse personalOrderResponse) {
        //
        personalOrderResponse.setDetailUrl("/lvxbang/order/planOrderDetail.jhtml?orderId=" + order.getId());
        List<PersonalOrderDetailResponse> detailList = new ArrayList<PersonalOrderDetailResponse>();
        PersonalOrderDetailResponse detail = new PersonalOrderDetailResponse();
        detail.setType("线路");
        detail.setDetailType("&nbsp;");
        detail.setName(order.getName());
        detail.setCost(order.getPrice());
        detail.setPlayDate(formatDate(order.getPlayDate()));
        HashSet<String> tourist = new HashSet<String>();
        String touristName = "";
        for (OrderTourist orderTourist : order.getOrderTourists()) {
            //
            if ("".equals(touristName)) {
                touristName = orderTourist.getName();
            }
            tourist.add(orderTourist.getName());
        }
        if (tourist.size() > 1) {
            touristName += "……";
        }
        detail.setNum(tourist.size() + "人");
        detail.setTourist(touristName);
        if (order.getOrderDetails().get(0).getStatus() == null) {
            detail.setStatus(order.getStatus().getDescription());
        } else {
            detail.setStatus(order.getOrderDetails().get(0).getStatus().getDescription());
        }
        detailList.add(detail);
        personalOrderResponse.setOrderDetailList(detailList);
    }

    public String trafficName(Long trafficPriceId) {
        String name = "";
        TrafficPrice trafficPrice = trafficPriceService.get(trafficPriceId);
        if (trafficPrice == null) {
            return name;
        }
        name = trafficPrice.getTraffic().getTrafficCode();
        name += processCityName(trafficPrice.getTraffic().getLeaveCity().getName());
        name += "-";
        name += processCityName(trafficPrice.getTraffic().getArriveCity().getName());
        return name;
    }


    public String statusToString(OrderStatus status) {
        //
        String statusString = null;
        // 'UNCONFIRMED','WAIT','PAYED','REFUND','CLOSED','DELETED','INVALID'
        switch (status) {
            case UNCONFIRMED:
                statusString = "等待确认";
                break;
            case WAIT:
                statusString = "等待支付";
                break;
            case PAYED:
                statusString = "支付成功";
                break;
            case REFUND:
                statusString = "退款中";
                break;
            case CLOSED:
                statusString = "交易关闭";
                break;
            case DELETED:
                statusString = "已删除";
                break;
            case INVALID:
                statusString = "无效订单";
                break;
            case PROCESSING:
                statusString = "处理中";
                break;
            case PROCESSED:
                statusString = "已处理";
                break;
            default:
                break;
        }

        return statusString;
    }

    private String processCityName(String name) {
        //
        String[] names = name.split("市");
        return names[0];
    }

    private String formatDate(Date date) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormater.format(date);
    }

}
