package com.data.data.hmly.service.ticket.aspect;

import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.zuipin.util.PropertiesManager;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;

@Aspect
@Service
public class TicketAspect {
    @Resource
    private TicketService ticketService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private PropertiesManager propertiesManager;

    @After("(execution (* com.data.data.hmly.service.ticket.dao.TicketDao.save(..))) " +
            "or (execution (* com.data.data.hmly.service.ticket.dao.TicketDao.insert(..))) " +
            "or (execution (* com.data.data.hmly.service.ticket.dao.TicketDao.update(..)))")
    public void lineFinds(JoinPoint joinPoint) {
        try {
            Boolean updateTicketIndex = propertiesManager.getBoolean("UPDATE_TICKET_INDEX");
            if (updateTicketIndex != null && !updateTicketIndex) {
                return;
            }
            Object[] objs = joinPoint.getArgs();
            Ticket ticket = (Ticket) objs[0];
            if (ticket != null) {
                ScenicInfo scenicInfo = new ScenicInfo();
                if (ticket.getScenicInfo() != null) {
                    scenicInfo = scenicInfoService.get(ticket.getScenicInfo().getId());
                }
                if (scenicInfo != null) {
//                    Float minPrice = ticketPriceService.findTicketMinPriceBy(scenicInfo.getId());
//                    Integer ticketCount = ticketService.findTicketNumBy(scenicInfo.getId());
//                    scenicInfo.setTicketNum(ticketCount);
//                    scenicInfo.setPrice(minPrice);
//                    scenicInfoService.update(scenicInfo);
                    scenicInfoService.indexScenicInfo(scenicInfo);
                }
                ticketService.indexTicket(ticket);

            }

            //buildScenicDetail(ticket.getId());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @After("(execution (* com.data.data.hmly.service.ticket.dao.TicketDatepriceDao.save(..))) " +
            "or (execution (* com.data.data.hmly.service.ticket.dao.TicketDatepriceDao.insert(..))) " +
            "or (execution (* com.data.data.hmly.service.ticket.dao.TicketDatepriceDao.update(..)))")
    public void linetypepricedateVisits(JoinPoint joinPoint) {
        try {
            Boolean updateTicketIndex = propertiesManager.getBoolean("UPDATE_TICKET_INDEX");
            if (updateTicketIndex != null && !updateTicketIndex) {
                return;
            }
            Object[] objs = joinPoint.getArgs();
            TicketDateprice ticketDateprice = null;
            if (objs[0] instanceof List) {
                List<TicketDateprice> list = (List<TicketDateprice>) objs[0];
                ticketDateprice = list.get(0);
            } else {
                ticketDateprice = (TicketDateprice) objs[0];
            }
            TicketPrice ticketPrice = ticketDateprice.getTicketPriceId();
            Ticket ticket = ticketPrice.getTicket();
            ticketService.indexTicket(ticket);
            buildScenicDetail(ticket.getId());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void buildScenicDetail(long id) throws UnsupportedEncodingException {
        String url = propertiesManager.getString("FG_DOMAIN") + "/build/lxb/buildScenicDetail.jhtml";
        doHttpRequestGet(url, "scenicId=" + id);
    }

    /**
     * 调用http请求
     *
     * @param url
     * @param paramStr
     * @return
     * @throws java.io.UnsupportedEncodingException
     */
    private String doHttpRequestGet(String url, String paramStr) throws UnsupportedEncodingException {
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
