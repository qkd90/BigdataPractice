package com.data.data.hmly.action.order;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.order.OrderDetailService;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderDetailStatus;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by zzl on 2016/4/1.
 */
public class OrderDetailAction extends FrameBaseAction {

    @Resource
    private OrderDetailService orderDetailService;

    private OrderDetail orderDetail = new OrderDetail();

    private Integer page = 1;
    private Integer rows = 10;
    private String orderProperty;
    private String orderType;

    public Result abnormal() {
        return dispatch();
    }

    public Result abnormalList() {
        Page page = new Page(this.page, rows);
        SysUser loginUser = getLoginUser();
        Boolean isSuperAdmin = isSupperAdmin();
        Boolean isSiteAdmin = isSiteAdmin();
        if (loginUser == null) {
            return dispatchlogin();
        }
        List<OrderDetail> orderDetails = orderDetailService.list(orderDetail, page, isSuperAdmin, isSiteAdmin, loginUser, orderProperty, orderType);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("order", "orderTouristList");
        return datagrid(orderDetails, page.getTotalCount(), jsonConfig);
    }


    public OrderDetail getOrderDetail() {
        return orderDetail;
    }

    public void setOrderDetail(OrderDetail orderDetail) {
        this.orderDetail = orderDetail;
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

    public String getOrderProperty() {
        return orderProperty;
    }

    public void setOrderProperty(String orderProperty) {
        this.orderProperty = orderProperty;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }
}
