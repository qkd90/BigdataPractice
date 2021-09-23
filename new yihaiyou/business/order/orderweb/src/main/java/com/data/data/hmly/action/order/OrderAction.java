package com.data.data.hmly.action.order;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.order.vo.OrderVo;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.order.OrderMsgService;
import com.data.data.hmly.service.order.OrderService;
import com.data.data.hmly.service.order.entity.Order;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderStatus;
import com.data.data.hmly.service.order.entity.enums.OrderType;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.framework.struts.JsonDateValueProcessor;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by guoshijie on 2015/10/13.
 */
public class OrderAction extends FrameBaseAction {

    private final Logger logger = Logger.getLogger(OrderAction.class);

    @Resource
    private OrderService orderService;
    @Resource
    private OrderMsgService orderMsgService;
    public OrderForm orderForm = new OrderForm();

    private Integer page = 1;
    private Integer rows = 10;


    public Result testOrderMsg() {
        Order order = orderService.get(2947L);
        for (OrderDetail orderDetail : order.getOrderDetails()) {
            orderMsgService.doSendOrderValidteCodeMsg(orderDetail);
        }
//        orderService.doSendOrderMsg(order);
        return text("success");
    }

    public Result index() {
        return dispatch();
    }

    public Result list() {
        Page page = new Page(this.page, rows);
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Boolean isSuperAdmin = isSupperAdmin();
        Boolean isSiteAdmin = isSiteAdmin();
        List<Order> orders = orderService.listByAuthorize(orderForm.getOrder(), page, true, true, loginUser, orderForm.getOrderType());
        List<OrderVo> result = new ArrayList<OrderVo>();
        for (Order order : orders) {
            result.add(new OrderVo(order));
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("orderDetails", "product", "rootProduct", "user");

        return datagrid(result, page.getTotalCount(), jsonConfig);
        //		return jsonResult(list, page.getTotalCount());
    }

    public Result detail() {
        SysUser loginUser = getLoginUser();
        if (loginUser == null) {
            return dispatchlogin();
        }
        Object id = getParameter("id");
        if (id == null) {
            return text("id_required");
        }
        Boolean isSuperAdmin = isSupperAdmin();
        Boolean isSiteAdmin = isSiteAdmin();
        Order order = orderService.getByAuthorize(Long.valueOf(id.toString()), true, true, loginUser);
        if (order == null) {
            return text("order_not_exists");
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(new Class[]{}, "user", "orderDetails", "product", "invoice", "orderInsurances", "insurance", "category");
//		JsonConfig jsonConfig = JsonFilter.getFilterConfig("order", "orderDetailFlattened", "companyUnit", "parent",
//			"supplier", "topProduct", "productimage", "line", "scenicInfo", "ticketPriceSet", "tbArea", "lineexplain",
//			"linetypeprices", "grand", "superior", "sysSite", "sysUnit");
        jsonConfig.registerJsonValueProcessor(Date.class, new JsonDateValueProcessor("yyyy-MM-dd hh:mm:ss"));
        return json(JSONObject.fromObject(order, jsonConfig));
    }

    @AjaxCheck
    public Result updateStatus() {
        User user = new User();
        Order postOrder = orderForm.getOrder();
        Order order = orderService.get(postOrder.getId());
        if (order == null) {
            return text("找不到对象");
        }
        if (orderForm.getConfirmStatus() != order.getStatus()) {
            for (OrderDetail detail : order.getOrderDetails()) {
                if (!detail.getProduct().getTopProduct().getUser().getId().equals(user.getId())) {
                    return text("没有操作权限，请联系供应商");
                }
            }
        }

        if (orderForm.getConfirmStatus() == null) {
            return text("success");
        }
        if (orderForm.getConfirmStatus() == OrderStatus.INVALID) {
            if (!order.canChangeToValid()) {
                return text("非法操作");
            }
            order.setStatus(OrderStatus.INVALID);
            order.setOperationDesc(orderForm.getOperationDesc());
            orderService.update(order);
            return text("success");
        }

        if (order.getStatus() == OrderStatus.UNCONFIRMED) {
            order.setStatus(OrderStatus.WAIT);
            orderService.update(order);
            return text("success");
        }
        if (order.getStatus() == OrderStatus.WAIT && orderForm.getPayStatus() != null) {
            order.setStatus(orderForm.getPayStatus());
            orderService.update(order);
            return text("success");
        }

        return text("success");

    }


    public OrderForm getOrderForm() {
        return orderForm;
    }

    public void setOrderForm(OrderForm orderForm) {
        this.orderForm = orderForm;
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
