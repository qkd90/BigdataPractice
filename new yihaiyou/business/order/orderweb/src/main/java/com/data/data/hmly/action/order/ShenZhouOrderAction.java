package com.data.data.hmly.action.order;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.order.ShenzhouOrderService;
import com.data.data.hmly.service.order.entity.ShenzhouOrder;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by dy on 2016/9/13.
 */
public class ShenZhouOrderAction extends FrameBaseAction {

    @Resource
    private ShenzhouOrderService shenzhouOrderService;

    private ShenzhouOrder shenzhouOrder = new ShenzhouOrder();
    private Integer page = 1;
    private Integer pageSize = 10;

    public Result shenZhouOrderDetail() {

        if (shenzhouOrder.getId() != null) {
            shenzhouOrder = shenzhouOrderService.get(shenzhouOrder.getId());
        }
        return dispatch();
    }


    public Result shenZhouOrderList() {

        String rStartTime = (String) getParameter("rStartTime");
        String rEndTime = (String) getParameter("rEndTime");

        List<ShenzhouOrder> shenzhouOrderList = new ArrayList<ShenzhouOrder>();

        Page pageInfo = new Page(page, pageSize);

        if (StringUtils.isNotBlank(rStartTime)) {
            shenzhouOrder.setStartTime(DateUtils.getStartDay(DateUtils.toDate(rStartTime), 0));
        }

        if (StringUtils.isNotBlank(rEndTime)) {
            shenzhouOrder.setEndTime(DateUtils.getEndDay(DateUtils.toDate(rEndTime), 0));
        }

        shenzhouOrderList = shenzhouOrderService.getOrderList(shenzhouOrder, pageInfo, "createTime");

        String[] includes = new String[]{"user"};
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(includes);

        return datagrid(shenzhouOrderList, pageInfo.getTotalCount(), jsonConfig);

    }


//    /order/shenZhouOrder/shenZhouOrderManage.jhtml

    public Result shenZhouOrderManage() {
        return dispatch();
    }

    public ShenzhouOrder getShenzhouOrder() {
        return shenzhouOrder;
    }

    public void setShenzhouOrder(ShenzhouOrder shenzhouOrder) {
        this.shenzhouOrder = shenzhouOrder;
    }

    public Integer getPage() {
        return page;
    }

    public void setPage(Integer page) {
        this.page = page;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
