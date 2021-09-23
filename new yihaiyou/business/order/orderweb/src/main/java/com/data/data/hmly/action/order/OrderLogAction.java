package com.data.data.hmly.action.order;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.order.OrderLogService;
import com.data.data.hmly.service.order.entity.OrderLog;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by zzl on 2016/5/23.
 */
public class OrderLogAction extends FrameBaseAction {

    @Resource
    private OrderLogService orderLogService;

    private int page;
    private int rows;


    public Result getOrderLog() {
        final HttpServletRequest request = getRequest();
        String orderIdStr = request.getParameter("orderIdStr");
        String orderDetailIdStr = request.getParameter("orderDetailIdStr");
        Page page = new Page(this.page, this.rows);
        List<OrderLog> orderLogs = orderLogService.getLogs(orderIdStr, orderDetailIdStr, page);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("operator");
        return datagrid(orderLogs, page.getTotalCount(), jsonConfig);
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getRows() {
        return rows;
    }

    public void setRows(int rows) {
        this.rows = rows;
    }
}
