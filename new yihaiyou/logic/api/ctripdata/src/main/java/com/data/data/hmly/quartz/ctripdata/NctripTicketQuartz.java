package com.data.data.hmly.quartz.ctripdata;

import com.data.data.hmly.service.ctripcommon.CtripApiLogService;
import com.data.data.hmly.service.ctripcommon.entity.CtripApiLog;
import com.data.data.hmly.service.ctripcommon.enums.CtripTicketIcode;
import com.data.data.hmly.service.ctripcommon.enums.OrderStatus;
import com.data.data.hmly.service.nctripticket.CtripTicketApiService;
import com.data.data.hmly.service.nctripticket.CtripTicketService;
import com.data.data.hmly.service.nctripticket.entity.CtripOrderFormInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripResourcePriceCalendar;
import com.data.data.hmly.service.nctripticket.entity.CtripScenicSpotInfo;
import com.data.data.hmly.service.nctripticket.entity.CtripScenicSpotResource;
import com.framework.hibernate.util.Page;
import com.zuipin.util.DateUtils;
import com.zuipin.util.PropertiesManager;
import net.sf.json.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

/**
 * 门票数据同步（新接口）
 * Created by caiys on 2016/2/22.
 */
@Component
public class NctripTicketQuartz {
    private Log log = LogFactory.getLog(this.getClass());
    @Resource
    private CtripTicketApiService ctripTicketApiService;
    @Resource
    private CtripTicketService ctripTicketService;
    @Resource
    private CtripApiLogService ctripApiLogService;
    @Resource
    private PropertiesManager propertiesManager;

    private Map<String, String[]> icodeFrequenceMap = new HashMap<String, String[]>();  // 接口频次控制。一分钟300次
    // 执行相关时间（为接口同步后续出错处理提供时间节点）
    private Date execStartTime; // 执行开始时间
    private Date execEndTime;   // 执行结束时间
    private Date priceCalendarStartTime;    // 价格日历开始时间
    private Date priceCalendarEndTime;      // 价格日历结束时间

    private void init() {
        execStartTime = new Date(); // 执行开始时间
        Calendar calendar = Calendar.getInstance();
        // 价格日历开始时间
        priceCalendarStartTime = DateUtils.date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH)) ;    // 携程不返回当天数据
        // 价格日历结束时间
//        priceCalendarEndTime = DateUtils.date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH) + 14);     // 两周
//        priceCalendarEndTime = DateUtils.date(calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH) + 1, calendar.get(Calendar.DAY_OF_MONTH));   // 三个月
        priceCalendarEndTime = DateUtils.add(priceCalendarStartTime, Calendar.DATE, 59);
    }

    private void end() {
        execEndTime = new Date();   // 执行结束时间
    }

    /**
     * 景点、门票资源、价格日历数据同步，接口表同步、本地数据转换
     * 每周更新，景点、门票资源以最后一次更新日期为准增量更新，价格日历每次更新两周数据（携程部分价格库大于两周不一定有值）
     */
    public void syncTicket() throws Exception {
        init();
        syncScenicAndResource();
        syncPriceCalendar(priceCalendarStartTime, priceCalendarEndTime);
        end();
        // 接口同步后续出错处理
        int time = 3;
        while (time < 0) {  // 循环处理次数
            handleScenicSpotInfo();
            handleResourcePriceCalendar();
            time--;
        }
        // 携程门票资源如果不存在，则清除对应的价格日历数据
       //ctripTicketService.doClearPriceNotResource();

        updateTicket();
        updateTicketDatePrice(priceCalendarStartTime, priceCalendarEndTime);
        // 景点静态页面生成
        /*String url = propertiesManager.getString("FG_DOMAIN") + "/build/lxb/buildScenicDetail.jhtml";
        doHttpRequestGet(url, null);*/
    }

    /**
     * 价格日历数据同步，接口表同步、本地数据转换
     * 每天更新（主要是分销价有时会有变化，会影响下单），如果变化频率不高可以考虑取消
     * 价格日历每次更新两周数据（携程部分价格库大于两周不一定有值），
     */
    public void syncTicketDatePrice() throws Exception {
        init();
        syncPriceCalendar(priceCalendarStartTime, priceCalendarEndTime);
        end();
        // 接口同步后续出错处理
        handleResourcePriceCalendar();
        updateTicketDatePrice(priceCalendarStartTime, priceCalendarEndTime);
        // 景点静态页面生成
        /*String url = propertiesManager.getString("FG_DOMAIN") + "/build/lxb/buildScenicDetail.jhtml";
        doHttpRequestGet(url, null);*/
    }

    /**
     * 退单状态同步，
     * 申请“门票退单”每天检查携程退单状态，成功则更新本地订单状态
     * 已废弃，参考：com.data.data.hmly.quartz.ApiOrderCancelQuartz#syncCtripOrderCancel()
     */
    @Deprecated
    public void syncOrderStatus() {
        // 查询订单状态为退订中的订单
        Integer pageIndex = 1;
        Integer pageSize = 20;
        CtripOrderFormInfo orderFormInfo = new CtripOrderFormInfo();
        orderFormInfo.setOrderStatus(OrderStatus.CANCELING);
        orderFormInfo.setCancelHandleTime(new Date());

        Page page = null;
        while (true) {
            page = new Page(pageIndex, pageSize);

            int time = 1;    // 控制出错时，尝试次数
            while (time <= 3) {    // 如果不止一页，循环更新
                try {
                    List<CtripOrderFormInfo> list = ctripTicketService.listCtripOrderFormInfo(orderFormInfo, page);
                    if (list.isEmpty()) {
                        return;
                    }
                    for (CtripOrderFormInfo ctripOrderFormInfo : list) {
                        String uuid = UUID.randomUUID().toString();
                        OrderStatus orderStatus = ctripTicketApiService.doGetOrderStatus(ctripOrderFormInfo.getCtripOrderId(), uuid, null);
                        ctripTicketService.updateOrderStatus(ctripOrderFormInfo.getCtripOrderId(), orderStatus, new Date());
                        ctripTicketService.updateOriginalOrderCanceled(ctripOrderFormInfo.getCtripOrderId());
                    }
                    time = 1;
                } catch (Exception e) {
                    e.printStackTrace();
                    time++;
                }
            }

            if (!page.getHasNext()) {
                break;
            }
            pageIndex++;
        }
    }

    /**
     * 同步携程景点、门票资源相关信息到接口表
     */
    private void syncScenicAndResource() throws Exception {

        /****** 业务接口开始 ******/
        try {

            List<CtripScenicSpotInfo> scenicSpotInfos = ctripTicketApiService.doGetScenicSpotInfo();

            // 更新到本地数据库
            ctripTicketService.updateScenicSpotInfo(scenicSpotInfos);

        } catch (Exception e) {
            e.printStackTrace();

        }

        /**
         * 之前的接口
         */
        // 查询上一次执行的时间
//        Date nextTime = ctripTicketApiService.findMaxNextTime(CtripTicketIcode.SCENICSPOT_ID.getIcode());
//        // 获取景点ID列表
////        String uuid = UUID.randomUUID().toString();
//        List<Long> scenicSpotIdList = new ArrayList<Long>();
//        Long ctripCity = propertiesManager.getLong("CTRIP_CITY");
//        if (ctripCity == null) {    // 携程所有更新景点
//            JSONObject paramJson = new JSONObject();
//            paramJson.put("action", CtripTicketIcode.SCENICSPOT_ID);
//            scenicSpotIdList = ctripTicketApiService.doGetScenicSpotIdList();
//        } else {    // 区域景点，景点必须先维护
//            scenicSpotIdList = ctripTicketService.findCtripScenicIdList(ctripCity);
//        }
        // 分页获取景点信息，出现异常个别处理
//        Integer total = scenicSpotIdList.size();
//        log.info("最后一次更新时间" + DateUtils.format(nextTime, "yyyyMMdd HH:mm:ss") + "后的更新记录数为" + total);
//        Integer pageSize = 20;  // 最大支持20
//        Integer index = 0;
//        initFrequenceMap(CtripTicketIcode.SCENICSPOT_INFO);
//        while (index < total) {
//            Integer toIndex = index + pageSize;
//            if (toIndex > total) {  // 判断是否超过总数
//                toIndex = total;
//            }
//        List<CtripScenicSpotInfo> scenicSpotInfos = ctripTicketApiService.doGetScenicSpotInfo();
//        Integer total = scenicSpotInfos.size();
//        Integer pageSize = 20;
//        Integer index = 0;
//        while (index < total) {
//            Integer toIndex = index + pageSize;
//            if (toIndex > total) {  // 判断是否超过总数
//                toIndex = total;
//            }
        /****** 业务接口开始 ******/
//            try {
//                controlFrequence(CtripTicketIcode.SCENICSPOT_INFO);
//                List<Long> subScenicSpotIdList = scenicSpotIdList.subList(index, toIndex);
//                uuid = UUID.randomUUID().toString();


//                List<CtripScenicSpotInfo> subScenicSpotList = scenicSpotInfos.subList(index, toIndex);
//
//                // 更新到本地数据库
//                ctripTicketService.updateScenicSpotInfo(subScenicSpotList);
//
//            } catch (Exception e) {
//                e.printStackTrace();
        // 更新错误信息
//               ctripApiLogService.updateErrorInfo(uuid, "-1", "非接口异常");
    }
    /****** 业务接口结束 ******/
//            index = toIndex;

//        }
//    }

    /**
     * 同步携程门票价格库到接口表
     */
    private void syncPriceCalendar(Date startDate, Date endDate) {
        List<Long> idList = ctripTicketService.getTicketIdList();

        //业务接口开始
        try {

            List<CtripScenicSpotResource> resources = ctripTicketService.listResourceBy(idList);
            List<CtripResourcePriceCalendar> resourcePriceCalendars = ctripTicketApiService.doGetResourcePriceCalendar(idList, startDate, endDate);
            // 更新到本地数据库
            ctripTicketService.updateResourcePriceCalendar(resourcePriceCalendars, resources, startDate, endDate);
            // 更新日志状态
        } catch (Exception e) {
            e.printStackTrace();
        }

        /**
         * 之前接口
         */
        //        Integer pageIndex = 1;
//        Integer pageSize = 20;
//        Long count = ctripTicketService.countCtripScenicSpotResource();
//        int pageCount = count.intValue() / pageSize;
//        if (count.intValue() % pageSize != 0) {
//            pageCount = pageCount + 1;
//        }
//        initFrequenceMap(CtripTicketIcode.TICKET_PRICE_CALENDAR);
//        while (pageIndex <= pageCount) {    // 如果不止一页，循环更新
//            List<CtripScenicSpotResource> resources = ctripTicketService.listCtripScenicSpotResource(pageIndex, pageSize);
//            List<Long> ctripRecIdList = new ArrayList<Long>();
//            /*
//             * 去重，携程有出现一个资源（例如：2009287）关联多个产品，所以同个分页结果会出现多条相同资源标识的记录，
//             * 请求携程接口返回回来的数据也会出现重复，在此过滤同分页结果相同资源标识记录，只留一条。
//             */
//            Set<Long> set = new HashSet<Long>();
//            for (CtripScenicSpotResource r : resources) {
//                set.add(r.getCtripResourceId());
////                ctripRecIdList.add(r.getCtripResourceId());
//            }
//            ctripRecIdList.addAll(set);
//            /****** 业务接口开始 ******/
//            String uuid = UUID.randomUUID().toString();
//            try {
//                controlFrequence(CtripTicketIcode.TICKET_PRICE_CALENDAR);
//                List<CtripResourcePriceCalendar> resourcePriceCalendars = ctripTicketApiService.doGetResourcePriceCalendar(ctripRecIdList, startDate, endDate);
//                ctripTicketService.updateResourcePriceCalendar(resourcePriceCalendars, resources, startDate, endDate);
//            } catch (Exception e) {
//                e.printStackTrace();
//                // 更新错误信息
//                ctripApiLogService.updateErrorInfo(uuid, "-1", "非接口异常");
//            }
//            /****** 业务接口结束 ******/
//            pageIndex++;
//        }
    }

    /**
     * 接口表门票数据更新到本地数据
     * 要求：scenic表的ctrip_scenic_id需已赋值
     */
    private void updateTicket() {
        ctripTicketService.updateTicket();
    }

    /**
     * 接口表门票价格日历更新到本地数据
     */
    private void updateTicketDatePrice(Date startDate, Date endDate) {
        ctripTicketService.updateTicketDatePrice(startDate, endDate);
    }

    /**
     * 门票景点数据更新出错处理
     *
     * @return 返回是否有错误处理
     */
    public boolean handleScenicSpotInfo() {
        boolean b = false;
        Integer pageIndex = 1;
        Integer pageSize = 20;
        CtripApiLog al = new CtripApiLog();
        al.setIcode(CtripTicketIcode.SCENICSPOT_INFO.getIcode());
        al.setSuccess(false);
        al.setExecTimeStart(execStartTime);
        al.setExecTimeEnd(execEndTime);
        Long count = ctripApiLogService.countCtripApiLog(al);
        int pageCount = count.intValue() / pageSize;
        if (count.intValue() % pageSize != 0) {
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
                    b = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /****** 业务接口结束 ******/
            }
            pageIndex++;
        }
        return b;
    }

    /**
     * 门票价格日历数据更新出错处理
     *
     * @return 返回是否有错误处理
     */
    public boolean handleResourcePriceCalendar() throws Exception {
        boolean b = false;
        Integer pageIndex = 1;
        Integer pageSize = 20;
        CtripApiLog al = new CtripApiLog();
        al.setIcode(CtripTicketIcode.TICKET_PRICE_CALENDAR.getIcode());
        al.setSuccess(false);
        al.setExecTimeStart(execStartTime);
        al.setExecTimeEnd(execEndTime);
        Long count = ctripApiLogService.countCtripApiLog(al);
        int pageCount = count.intValue() / pageSize;
        if (count.intValue() % pageSize != 0) {
            pageCount = pageCount + 1;
        }
        while (pageIndex <= pageCount) {    // 如果不止一页，循环更新
            List<CtripApiLog> logs = ctripApiLogService.listCtripApiLog(al, 0, pageSize);
            for (CtripApiLog ctripApiLog : logs) {
                /****** 业务接口开始 ******/
                try {
//                    String uuid = UUID.randomUUID().toString();
//                    String[] resourceIdArray = ctripApiLog.getHandleIds().split(",");
//                    List<Long> ctripResourceIdList = new ArrayList<Long>();
//                    for (String idStr : resourceIdArray) {
//                        ctripResourceIdList.add(Long.valueOf(idStr));
//                    }
                    List<Long> ctripResourceIdList = ctripTicketService.getTicketIdList();
                    List<CtripScenicSpotResource> resources = ctripTicketService.listResourceBy(ctripResourceIdList);
                    List<CtripResourcePriceCalendar> resourcePriceCalendars = ctripTicketApiService.doGetResourcePriceCalendar(ctripResourceIdList, priceCalendarStartTime, priceCalendarEndTime);
                    // 更新到本地数据库
                    ctripTicketService.updateResourcePriceCalendar(resourcePriceCalendars, resources, priceCalendarStartTime, priceCalendarEndTime);
                    // 更新日志状态
                    ctripApiLog.setSuccess(true);
                    ctripApiLog.setHandleTime(new Date());
                    ctripApiLogService.updateCtripApiLog(ctripApiLog);
                    b = true;
                } catch (Exception e) {
                    e.printStackTrace();
                }
                /****** 业务接口结束 ******/
            }
            pageIndex++;
        }
        return b;
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
     *
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
     * 调用http请求
     *
     * @param url
     * @param paramStr
     * @return
     * @throws UnsupportedEncodingException
     */
    public String doHttpRequestGet(String url, String paramStr) throws UnsupportedEncodingException {
        if (StringUtils.isNotBlank(paramStr)) {
            url = url + "?" + paramStr;
        }
        //创建HttpClientBuilder
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        //HttpClient
        CloseableHttpClient closeableHttpClient = httpClientBuilder.build();
        HttpGet httpRequst = new HttpGet(url);
        try {
            //执行请求
            HttpResponse httpResponse = closeableHttpClient.execute(httpRequst);
            //获取响应消息实体
            HttpEntity entity = httpResponse.getEntity();
            //判断响应实体是否为空
            if (entity != null) {
                String resultStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
                log.info("返回结果：" + resultStr);
                return resultStr;
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                //关闭流并释放资源
                closeableHttpClient.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}
