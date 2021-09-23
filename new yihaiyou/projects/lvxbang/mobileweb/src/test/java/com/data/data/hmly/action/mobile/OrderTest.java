package com.data.data.hmly.action.mobile;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.ActionProxy;
import org.junit.Ignore;

import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-04-25,0025.
 */
@Ignore
public class OrderTest extends BaseTest {
    private ObjectMapper mapper = new ObjectMapper();

    /**
     * 8.1 保存订单
     *
     * @throws Exception
     */
    public void testSave() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", "0");
        map.put("userId", "151");
        map.put("name", "接口测试保存订单");
        map.put("day", "1");
        map.put("playDate", "2016-05-17");

        List<Map<String, Object>> details = Lists.newArrayList();
        Map<String, Object> detail1 = Maps.newHashMap();
        detail1.put("num", "1");
        detail1.put("id", "1720613");
        detail1.put("seatType", "成人票");
        detail1.put("type", "scenic");
        detail1.put("startTime", "2016-05-17");
        detail1.put("endTime", "2016-05-17");
        detail1.put("priceId", "68848");
        detail1.put("price", "728");
        details.add(detail1);
        map.put("details", details);

        map.put("tourists", Lists.newArrayList(160));
        Map<String, Object> contact = Maps.newHashMap();
        contact.put("name", "张三");
        contact.put("telephone", "15131214556");
        map.put("contact", contact);
        map.put("couponId", "18");

        request.setParameter("json", mapper.writeValueAsString(map));
        ActionProxy proxy = getActionProxy("/order/save.jhtml");
        proxy.execute();
    }

    /**
     * 8.2 订单列表
     *
     * @throws Exception
     */
    public void testList() throws Exception {
        request.setParameter("userId", "151");
        request.setParameter("status", "2");
        request.setParameter("pageNo", "1");
        request.setParameter("pageSize", "10");
        ActionProxy proxy = getActionProxy("/order/list.jhtml");
        proxy.execute();
    }

    /**
     * 8.3 订单详情
     *
     * @throws Exception
     */
    public void testDetail() throws Exception {
        request.setParameter("orderId", "1506");
        request.setParameter("userId", "151");
        ActionProxy proxy = getActionProxy("/order/detail.jhtml");
        proxy.execute();
    }

    /**
     * 8.4 删除订单
     *
     * @throws Exception
     */
    public void testDelete() throws Exception {
        request.setParameter("orderId", "1506");
        request.setParameter("userId", "151");
        ActionProxy proxy = getActionProxy("/order/delete.jhtml");
        proxy.execute();
    }

    public void testRefund() throws Exception {
        ActionProxy proxy = getActionProxy("/order/refund.jhtml");
        proxy.execute();
    }

    public void testRefundConfirm() throws Exception {
        ActionProxy proxy = getActionProxy("/order/refundConfirm.jhtml");
        proxy.execute();
    }

    /**
     * 8.7 保存常用旅客
     *
     * @throws Exception
     */
    public void testSaveTourist() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("userId", "151");
        map.put("touristId", "160");
        map.put("name", "张三");
        map.put("idType", "IDCARD");
        map.put("idNumber", "1546512316465335");
        map.put("telephone", "15131214556");
        map.put("gender", "male");
        map.put("peopleType", "ADULT");
        request.setParameter("json", mapper.writeValueAsString(map));
        ActionProxy proxy = getActionProxy("/order/saveTourist.jhtml");
        proxy.execute();
    }

    /**
     * 8.8 常用旅客列表
     *
     * @throws Exception
     */
    public void testTouristList() throws Exception {
        request.setParameter("userId", "151");
        ActionProxy proxy = getActionProxy("/order/touristList.jhtml");
        proxy.execute();
    }

    /**
     * 8.9 红包列表
     *
     * @throws Exception
     */
    public void testCouponList() throws Exception {
        request.setParameter("userId", "151");
        request.setParameter("couponStatus", "unused");
        request.setParameter("limitType", "plan");
        request.setParameter("pageNo", "1");
        request.setParameter("pageSize", "10");
        ActionProxy proxy = getActionProxy("/order/couponList.jhtml");
        proxy.execute();
    }
}
