package com.data.data.hmly.action.pay;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.pay.AliPayService;
import com.data.data.hmly.service.pay.PayLogService;
import com.data.data.hmly.service.pay.WeChatPayService;
import com.data.data.hmly.service.pay.entity.PayLog;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by vacuity on 15/10/28.
 */
public class PayAction extends FrameBaseAction {


    private long orderId;
    private String name;
    private String mobile;
    private String begin;
    private String end;

    private int page;
    private int rows;



    @Resource
    private PayLogService payLogService;
    @Resource
    private AliPayService aliPayService;
    @Resource
    private WeChatPayService weChatPayService;





    public Result payLogList() {
        return dispatch();
    }

    // 根据订单，用户名，电话，开始和结束日期查询交易日志
    public Result getList() {
        Page pages = new Page(page, rows);
        Date sDate = formatDate(begin, 1);
        Date eDate = formatDate(end, 2);
        User user = getLoginUser();
        List<PayLog> payLogList = payLogService.getPayLogs(user, pages, orderId, name, mobile, sDate, eDate);

        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(new Class[]{}, new String[]{"user", "order"});

        return datagrid(payLogList, pages.getTotalCount(), jsonConfig);
    }

    public Date formatDate(String time, int type) {
        if (time == null || "".equals(time)) {
            return null;
        }
        if (type == 1) {
            time += " 00:00:00";
        } else {
            time += " 23:59:59";
        }
        String pat = "yyyy-MM-dd HH:mm:ss";
        SimpleDateFormat sdf = new SimpleDateFormat(pat);        // 实例化模板对象
        Date date = null;
        try {
            date = sdf.parse(time);   // 将给定的字符串中的日期提取出来
        } catch (Exception e) {            // 如果提供的字符串格式有错误，则进行异常处理
            e.printStackTrace();       // 打印异常信息
        }

        return date;
    }


    public long getOrderId() {
        return orderId;
    }

    public void setOrderId(long orderId) {
        this.orderId = orderId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getBegin() {
        return begin;
    }

    public void setBegin(String begin) {
        this.begin = begin;
    }

    public String getEnd() {
        return end;
    }

    public void setEnd(String end) {
        this.end = end;
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
