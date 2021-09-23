package com.data.data.hmly.action.yhyorder;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.order.OrderForm;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderAll;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dy on 2016/12/21.
 */
public class YhyCtripTicketOrderAction extends FrameBaseAction {


    @Resource
    private OrderService orderService;
    @Resource
    private OrderDetailService orderDetailService;

    private OrderForm orderFormVo = new OrderForm();
    private  Order order = new Order();
    private Integer page = 1;
    private Integer rows = 10;
    private Long orderId;
    private List<Long> orderDetailIds;


    public Result orderDetail() {
//        Boolean isSuperAdmin = isSupperAdmin();
//        Boolean isSiteAdmin = isSiteAdmin();
//        SysUser sysUser = getLoginUser();
        if (orderId != null) {
            order = orderService.get(orderId);
//            getOrderDetailByAuthorize(order, isSuperAdmin, isSiteAdmin, sysUser);
//            rows = order.getOrderDetails().size();
        }
        return dispatch("/WEB-INF/jsp/yhyorder/ctripOrder/ctripOrderDetail.jsp");
    }


    // /yhyorder/yhyCtripTicketOrder/getOrderList.jhtml
    public Result getOrderList() {
        Page page = new Page(this.page, rows);
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Boolean isSuperAdmin = isSupperAdmin();
        Boolean isSiteAdmin = isSiteAdmin();
        orderFormVo.setOrderProperty("createTime");
        if (StringUtils.isNotBlank(orderFormVo.getStartTime())) {
            order.setStartTime(DateUtils.getStartDay(DateUtils.toDate(orderFormVo.getStartTime()), 0));
        }
        if (StringUtils.isNotBlank(orderFormVo.getEndTime())) {
            order.setEndTime(DateUtils.getEndDay(DateUtils.toDate(orderFormVo.getEndTime()), 0));
        }
        List<OrderAll> orders = orderService.listOrderAll(order, page, isSuperAdmin, isSiteAdmin, loginUser, orderFormVo.getOrderType());
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("orderDetails", "product", "rootProduct", "user", "operator");
        return datagrid(orders, page.getTotalCount(), jsonConfig);
    }

    // /yhyorder/yhyCtripTicketOrder/ctripOrderManage.jhtml
    public Result ctripOrderManage() {
        return dispatch("/WEB-INF/jsp/yhyorder/ctripOrder/ctripOrderIndex.jsp");
    }

    public void getOrderDetailByAuthorize(Order order, boolean isSuperAdmin, boolean isSiteAdmin, SysUser sysUser) {
        if (!isSuperAdmin) {
            List<OrderDetail> detailList = order.getOrderDetails();
            List<OrderDetail> newDetailList = Lists.newArrayList();
            for (OrderDetail detail : detailList) {
                if (!isSiteAdmin) {
                    if (sysUser.getSysUnit().getId() == detail.getProduct().getCompanyUnit().getId()) {
                        newDetailList.add(detail);
                    }
                } else {
                    if (sysUser.getSysSite().getId() == detail.getProduct().getCompanyUnit().getSysSite().getId()) {
                        newDetailList.add(detail);
                    }
                }
            }
            order.setOrderDetails(newDetailList);
        }
    }

    public OrderForm getOrderFormVo() {
        return orderFormVo;
    }

    public void setOrderFormVo(OrderForm orderFormVo) {
        this.orderFormVo = orderFormVo;
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

    public Integer getRows() {
        return rows;
    }

    public void setRows(Integer rows) {
        this.rows = rows;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public List<Long> getOrderDetailIds() {
        return orderDetailIds;
    }

    public void setOrderDetailIds(List<Long> orderDetailIds) {
        this.orderDetailIds = orderDetailIds;
    }
}
