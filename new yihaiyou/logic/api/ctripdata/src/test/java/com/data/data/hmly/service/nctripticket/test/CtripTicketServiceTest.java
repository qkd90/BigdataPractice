package com.data.data.hmly.service.nctripticket.test;

import com.data.data.hmly.service.ctripcommon.CtripApiLogService;
import com.data.data.hmly.service.ctripcommon.entity.CtripApiLog;
import com.data.data.hmly.service.ctripcommon.enums.CtripTicketIcode;
import com.data.data.hmly.service.ctripcommon.enums.OrderStatus;
import com.data.data.hmly.service.nctripticket.CtripTicketApiService;
import com.data.data.hmly.service.nctripticket.CtripTicketService;
import com.data.data.hmly.service.nctripticket.entity.CtripOrderContactInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripOrderFormInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripOrderFormResourceInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripOrderPassengerInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripResourcePriceCalendar;
import com.data.data.hmly.service.nctripticket.entity.CtripScenicSpotInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripScenicSpotResource;
import com.data.data.hmly.service.nctripticket.pojo.CtripCancelOrderItemVO;
import com.data.data.hmly.service.nctripticket.pojo.CtripOrderCancelCheckVO;
import com.zuipin.util.DateUtils;
import junit.framework.TestCase;
import net.sf.json.JSONObject;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * Created by caiys on 2016/1/27.
 */
//@Ignore
public class CtripTicketServiceTest extends TestCase {
    private final ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext*.xml");
    private final CtripTicketApiService ctripTicketApiService = (CtripTicketApiService) applicationContext.getBean("ctripTicketApiService");
    private final CtripTicketService ctripTicketService = (CtripTicketService) applicationContext.getBean("ctripTicketService");
    private final CtripApiLogService ctripApiLogService = (CtripApiLogService) applicationContext.getBean("ctripApiLogService");
    private Map<String, String[]> icodeFrequenceMap = new HashMap<String, String[]>();  // 接口频次控制。一分钟300次

    @Override
    protected void tearDown() throws Exception {
        ((ClassPathXmlApplicationContext) applicationContext).close();
    }

    /**
     * 测试单条景点数据更新
     * @throws Exception
     */
    public void atestUpdateOneScenicSpotInfo() throws Exception {
        String uuid = UUID.randomUUID().toString();
        List<Long> scenicSpotIDList = new ArrayList<Long>();
//        scenicSpotIDList.add(3757L);
        scenicSpotIDList.add(57405L);
        List<CtripScenicSpotInfo> scenicSpotInfos = ctripTicketApiService.doGetScenicSpotInfo();
        // 更新到本地数据库
        ctripTicketService.updateScenicSpotInfo(scenicSpotInfos);
    }

    /**
     * 测试门票景点数据更新（包括景点相关信息、门票资源等），
     * 只做新增修改，不支持删除
     */
    public void xtestUpdateScenicSpotInfo() throws Exception {
        // 查询上一次执行的时间
        Date nextTime = ctripTicketApiService.findMaxNextTime(CtripTicketIcode.SCENICSPOT_ID.getIcode());
        // 获取景点ID列表
        String uuid = UUID.randomUUID().toString();
        JSONObject paramJson = new JSONObject();
        paramJson.put("action",CtripTicketIcode.SCENICSPOT_ID);
        List<Long> scenicSpotIdList = ctripTicketApiService.doGetScenicSpotIdList();
        // 分页获取景点信息，出现异常个别处理
        Integer total = scenicSpotIdList.size();
        Integer pageSize = 20;  // 最大支持20
        Integer index = 0;
        initFrequenceMap(CtripTicketIcode.SCENICSPOT_INFO);
        while (index < total) {
            Integer toIndex = index + pageSize;
            if (toIndex > total) {  // 判断是否超过总数
                toIndex = total;
            }
            /****** 业务接口开始 ******/
            try {
                controlFrequence(CtripTicketIcode.SCENICSPOT_INFO);
                List<Long> subScenicSpotIdList = scenicSpotIdList.subList(index, toIndex);
                uuid = UUID.randomUUID().toString();
                List<CtripScenicSpotInfo> scenicSpotInfos = ctripTicketApiService.doGetScenicSpotInfo();
                // 更新到本地数据库
                ctripTicketService.updateScenicSpotInfo(scenicSpotInfos);
            } catch (Exception e) {
                e.printStackTrace();
                // 更新错误信息
                ctripApiLogService.updateErrorInfo(uuid, "-1", "非接口异常");
            }
            /****** 业务接口结束 ******/
            index = toIndex;
        }
        System.out.println("更新景点数：" + total);
    }

    /**
     * 出错处理-Step01
     * 门票景点数据更新出错处理
     */
    public void testHandleScenicSpotInfo() throws Exception {
        Integer pageIndex = 1;
        Integer pageSize = 20;
        CtripApiLog al = new CtripApiLog();
        al.setIcode(CtripTicketIcode.SCENICSPOT_INFO.getIcode());
        al.setSuccess(false);
        al.setExecTimeStart(DateUtils.getDate("2016-06-28 00:00:00", "yyyy-MM-dd HH:mm:ss"));
//        al.setExecTimeEnd(DateUtils.getDate("2016-06-28 10:30:00", "yyyy-MM-dd HH:mm:ss"));
        Long count = ctripApiLogService.countCtripApiLog(al);
        int pageCount = count.intValue()/pageSize;
        if (count.intValue()%pageSize != 0) {
            pageCount = pageCount + 1;
        }
        while (pageIndex <= pageCount) {    // 如果不止一页，循环更新
            List<CtripApiLog> logs = ctripApiLogService.listCtripApiLog(al, 0, pageSize);
            for (CtripApiLog ctripApiLog : logs) {
                /****** 业务接口开始 ******/
                try {
                    String uuid = UUID.randomUUID().toString();
                    String[] scenicSpotIdArray = ctripApiLog.getHandleIds().split(",");
                    List<Long> scenicSpotIdList = new ArrayList<Long>();
                    for (String idStr : scenicSpotIdArray) {
                        scenicSpotIdList.add(Long.valueOf(idStr));
                    }
                    List<CtripScenicSpotInfo> scenicSpotInfos = ctripTicketApiService.doGetScenicSpotInfo();
                    // 更新到本地数据库
                    ctripTicketService.updateScenicSpotInfo(scenicSpotInfos);
                    // 更新日志状态
                    ctripApiLog.setSuccess(true);
                    ctripApiLog.setHandleTime(new Date());
                    ctripApiLogService.updateCtripApiLog(ctripApiLog);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /****** 业务接口结束 ******/
            }
            pageIndex ++;
        }
    }

    /**
     * 测试单条门票价格日历数据更新
     * @throws Exception
     */
    public void testUpdateResourcePriceCalendar() throws Exception {
        String uuid = UUID.randomUUID().toString();
        List<Long> ctripRecIdList = new ArrayList<Long>();
        ctripRecIdList.add(10137673L);  // 厦门园博园
        ctripRecIdList.add(10138540L);  // 厦门科技馆
//        ctripRecIdList.add(1642982L);   // 没数据
        List<CtripScenicSpotResource> resources = new ArrayList<CtripScenicSpotResource>();
        resources.add(new CtripScenicSpotResource(245403L, 10137673L, 63494L));
        resources.add(new CtripScenicSpotResource(247264L, 10138540L, 50128L));
//        resources.add(new CtripScenicSpotResource(119627L, 1642982L, 50128L));
        Date startDate = DateUtils.date(2016, 6, 27); // 5月12日
        Date endDate = DateUtils.date(2016, 9, 26);
        List<CtripResourcePriceCalendar> resourcePriceCalendars = ctripTicketApiService.doGetResourcePriceCalendar(ctripRecIdList, startDate, endDate);
        ctripTicketService.updateResourcePriceCalendar(resourcePriceCalendars, resources, startDate, endDate);
    }

    /**
     * 测试门票价格日历数据更新
     * @throws Exception
     */
    public void xtestUpdateResourcePriceCalendar() throws Exception {
        Integer pageIndex = 1;
        Integer pageSize = 20;
        Long count = ctripTicketService.countCtripScenicSpotResource();
        int pageCount = count.intValue()/pageSize;
        if (count.intValue()%pageSize != 0) {
            pageCount = pageCount + 1;
        }
        Date startDate = DateUtils.date(2017,7,1); // 2月8日
        Date endDate = DateUtils.date(2017, 7, 30);
        initFrequenceMap(CtripTicketIcode.TICKET_PRICE_CALENDAR);
        while (pageIndex <= pageCount) {    // 如果不止一页，循环更新
            List<CtripScenicSpotResource> resources = ctripTicketService.listCtripScenicSpotResource(pageIndex, pageSize);
            List<Long> ctripRecIdList = new ArrayList<Long>();
            for (CtripScenicSpotResource r : resources) {
                ctripRecIdList.add(r.getCtripResourceId());
            }
            /****** 业务接口开始 ******/
//            String uuid = UUID.randomUUID().toString();
            try {
                controlFrequence(CtripTicketIcode.TICKET_PRICE_CALENDAR);
                List<CtripResourcePriceCalendar> resourcePriceCalendars = ctripTicketApiService.doGetResourcePriceCalendar(ctripRecIdList, startDate, endDate);
                ctripTicketService.updateResourcePriceCalendar(resourcePriceCalendars, resources, startDate, endDate);
            } catch (Exception e) {
                e.printStackTrace();
                // 更新错误信息
//                ctripApiLogService.updateErrorInfo(, "-1", "非接口异常");
            }
            /****** 业务接口结束 ******/
            pageIndex ++;
        }
    }

    /**
     * 出错处理-Step02
     * 门票价格日历数据更新出错处理
     */
    public void testHandleResourcePriceCalendar() throws Exception {
        Integer pageIndex = 1;
        Integer pageSize = 20;
        CtripApiLog al = new CtripApiLog();
        al.setIcode(CtripTicketIcode.TICKET_PRICE_CALENDAR.getIcode());
        al.setSuccess(false);
        al.setExecTimeStart(DateUtils.getDate("2016-07-01 00:00:00", "yyyy-MM-dd HH:mm:ss"));
        al.setExecTimeEnd(DateUtils.getDate("2016-07-30 10:30:00", "yyyy-MM-dd HH:mm:ss"));
        Long count = ctripApiLogService.countCtripApiLog(al);
        int pageCount = count.intValue()/pageSize;
        if (count.intValue()%pageSize != 0) {
            pageCount = pageCount + 1;
        }
        Date startDate = DateUtils.date(2017, 7, 01); // 5月12日
        Date endDate = DateUtils.date(2016, 7, 30);
        while (pageIndex <= pageCount) {    // 如果不止一页，循环更新
            List<CtripApiLog> logs = ctripApiLogService.listCtripApiLog(al, 0, pageSize);
            for (CtripApiLog ctripApiLog : logs) {
                /****** 业务接口开始 ******/
                try {
//                    String uuid = UUID.randomUUID().toString();
                    String[] resourceIdArray = ctripApiLog.getHandleIds().split(",");
                    List<Long> ctripResourceIdList = new ArrayList<Long>();
                    for (String idStr : resourceIdArray) {
                        ctripResourceIdList.add(Long.valueOf(idStr));
                    }
                    List<CtripScenicSpotResource> resources = ctripTicketService.listResourceBy(ctripResourceIdList);
                    List<CtripResourcePriceCalendar> resourcePriceCalendars = ctripTicketApiService.doGetResourcePriceCalendar(ctripResourceIdList, startDate, endDate);
                    // 更新到本地数据库
                    ctripTicketService.updateResourcePriceCalendar(resourcePriceCalendars, resources, startDate, endDate);
                    // 更新日志状态
                    ctripApiLog.setSuccess(true);
                    ctripApiLog.setHandleTime(new Date());
                    ctripApiLogService.updateCtripApiLog(ctripApiLog);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /****** 业务接口结束 ******/
            }
            pageIndex ++;
        }
    }

    /**
     * 出错处理-Step03
     * 携程门票资源如果不存在，则清除对应的价格日历数据
     */
    public void testClearPriceNotResource() {
        ctripTicketService.doClearPriceNotResource();
    }

    /**
     * 出错处理-Step04
     * 更新携程同步的门票数据到本地数据结构
     * 要求：scenic表的ctrip_scenic_id需已赋值
     * -- 注意删除后会影响已下单的订单数据，删除已同步的门票数据语句
     * delete from product where ctripId is not null;
     * delete from ticket where ctripTicketId is not null;
     * delete from ticketprice where ctripTicketResourceId is not null;
     * delete from ticketdateprice where ctripTicketPriceDailyId is not null;
     * @author caiys
     * @date 2015年12月29日 下午5:25:42
     */
    public void testUpdateTicket() {
        ctripTicketService.updateTicket();
    }

    /**
     * 出错处理-Step05
     * 更新携程同步的门票价格数据到本地数据结构
     * @author caiys
     * @date 2015年12月29日 下午5:56:25
     */
    public void testUpdateTicketDatePrice() {
        Date startDate = DateUtils.date(2017, 0, 25); // 6月1日
        Date endDate = DateUtils.date(2017, 4, 24);
        ctripTicketService.updateTicketDatePrice(startDate, endDate);
    }

    private void initFrequenceMap(CtripTicketIcode icode) {
        String[] array = new String[2];
        Long nowTime = System.currentTimeMillis();
        array[0] = String.valueOf(0);
        array[1] = String.valueOf(nowTime);
        icodeFrequenceMap.put(icode.getIcode(), array);
    }

    /**
     * 简单控制，每300次判断是不是在一分钟内完成，是休眠后继续
     * @param icode
     */
    private void controlFrequence(CtripTicketIcode icode) {
        String[] array = icodeFrequenceMap.get(icode.getIcode());
        Integer num = Integer.valueOf(array[0]);
        Long startTime = Long.valueOf(array[1]);
        Long nowTime = System.currentTimeMillis();
        if (num >= 300) {   // 超过
            if (nowTime - startTime < 60 * 1000) {  // 一分钟内
                try {
                    Thread.sleep(60 * 1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            array[0] = String.valueOf(0);
            array[1] = String.valueOf(nowTime);
        } else {    // 次数加1
            array[0] = String.valueOf(num + 1);
        }
    }

    /**
     * 门票可订性检查
     */
    public void testOrderBookingCheck() throws Exception {
        // 资源列表
        CtripOrderFormResourceInfo resourceInfo = new CtripOrderFormResourceInfo();
        resourceInfo.setResourceId(10175507L);
        resourceInfo.setUseDate(DateUtils.getDate("2016-12-24", "yyyy-MM-dd"));
        resourceInfo.setQuantity(1);
        resourceInfo.setPrice(0.2f);    // 分销价
        List<CtripOrderFormResourceInfo> resourceInfoList = new ArrayList<CtripOrderFormResourceInfo>();
        resourceInfoList.add(resourceInfo);
        // 订单联系人信息
        CtripOrderContactInfo ctripOrderContactInfo = new CtripOrderContactInfo();
        ctripOrderContactInfo.setName("秋名");
        ctripOrderContactInfo.setMobile("13656039334");
        // 旅客信息列表
        CtripOrderPassengerInfo passengerInfo = new CtripOrderPassengerInfo();
        passengerInfo.setCustomerInfoId(1000509L);   // 本地订单用户ID
        passengerInfo.setcName("秋名");
        passengerInfo.setContactInfo("13656039334");
        List<CtripOrderPassengerInfo> ctripOrderPassengerInfos = new ArrayList<CtripOrderPassengerInfo>();
        ctripOrderPassengerInfos.add(passengerInfo);

        String uuid = UUID.randomUUID().toString();
        boolean success = ctripTicketApiService.doOrderBookingCheck(resourceInfoList, ctripOrderContactInfo, ctripOrderPassengerInfos, uuid);
        Assert.assertTrue(success);
    }

    /**
     * 门票创建订单
     */
    public void testCreateOrder() {
        for (int i = 0; i < 10; i++) {
            // 资源对应景点id
            Long scenicSpotId = 5170L;
            // 资源列表
            CtripOrderFormResourceInfo resourceInfo = new CtripOrderFormResourceInfo();
            resourceInfo.setResourceId(2788342L);//资源id:1665620-2, 2788342-1
            resourceInfo.setUseDate(DateUtils.getDate("2017-03-31", "yyyy-MM-dd"));
            resourceInfo.setQuantity(69);
            resourceInfo.setPrice(1f);    // 分销价
            List<CtripOrderFormResourceInfo> resourceInfoList = new ArrayList<CtripOrderFormResourceInfo>();
            resourceInfoList.add(resourceInfo);

            // 订单联系人信息
            CtripOrderContactInfo ctripOrderContactInfo = new CtripOrderContactInfo();
            ctripOrderContactInfo.setName("蔡艺山");
            ctripOrderContactInfo.setMobile("13656039334");

            // 旅客信息列表
            CtripOrderPassengerInfo passengerInfo = new CtripOrderPassengerInfo();
            passengerInfo.setCustomerInfoId(100803L);   // 本地订单用户ID
            passengerInfo.setcName("蔡艺山");
            passengerInfo.setContactInfo("13656039334");
            List<CtripOrderPassengerInfo> ctripOrderPassengerInfos = new ArrayList<CtripOrderPassengerInfo>();
            ctripOrderPassengerInfos.add(passengerInfo);
            CtripOrderFormInfo ctripOrderFormInfo = ctripTicketService.saveOrderInfo(scenicSpotId, resourceInfoList, ctripOrderContactInfo, ctripOrderPassengerInfos);
            try {
                String uuid = UUID.randomUUID().toString();
                ctripTicketApiService.doCreateOrder(ctripOrderFormInfo, ctripOrderContactInfo, ctripOrderPassengerInfos, uuid);
            } catch (Exception e) {
                e.printStackTrace();
                // 回写失败信息
                ctripTicketApiService.updateOrderFail(ctripOrderFormInfo);
            }
        }
    }

    /**
     * 门票可退检查
     * @throws Exception
     */
    public void testOrderCancelCheck() throws Exception {
        Long ctripOrderId = 3117507093L;
        String uuid = UUID.randomUUID().toString();
        CtripOrderCancelCheckVO ctripOrderCancelCheckVO = ctripTicketApiService.doOrderCancelCheck(ctripOrderId, uuid);
        System.out.println("取消状态：" + ctripOrderCancelCheckVO.getCancelStatus() + "，可退金额：" + ctripOrderCancelCheckVO.getTotalAmount());
    }

    /**
     * 门票退单
     * @throws Exception
     */
    public void testOrderCancel() throws Exception {
        Long ctripOrderId = 3265913714L;
        String reason = "测试";
        String uuid = UUID.randomUUID().toString();
        // 查询订单详情（获取订单状态和订单项信息）
        List<CtripCancelOrderItemVO> cancelItems = new ArrayList<CtripCancelOrderItemVO>(); // 资源退款信息集合
        OrderStatus orderStatus = ctripTicketApiService.doGetOrderStatus(ctripOrderId, uuid, cancelItems);
//        if (orderStatus == OrderStatus.CANCELING || orderStatus == OrderStatus.CANCELED) {  // 如果是退订中或已退订，更新订单状态
//            ctripTicketService.updateOrderStatus(ctripOrderId, orderStatus);
//            return ;
//        }

        Assert.assertTrue(orderStatus == OrderStatus.SUCCESS);
        // 取消订单
        boolean success = ctripTicketApiService.doOrderCancel(ctripOrderId, reason, cancelItems, uuid);
        Assert.assertTrue(success);
        if (success) {  // 更新订单状态为退订中
            ctripTicketService.updateOrderStatus(ctripOrderId, OrderStatus.CANCELING);
        }
    }

    /**
     * 门票订单基本信息查询，并更新订单状态（退订）
     */
    @Test
    public void testUpdateOrderStatus() throws Exception {
        Long ctripOrderId = 3265913714L;
        String uuid = UUID.randomUUID().toString();
        OrderStatus orderStatus = ctripTicketApiService.doGetOrderStatus(ctripOrderId, uuid, null);
        Assert.assertTrue(orderStatus == OrderStatus.CANCELED);
        if (orderStatus == OrderStatus.CANCELED) {  // 如果是已退订，更新订单状态
            ctripTicketService.updateOrderStatus(ctripOrderId, OrderStatus.CANCELED, new Date());
        }
    }

}
