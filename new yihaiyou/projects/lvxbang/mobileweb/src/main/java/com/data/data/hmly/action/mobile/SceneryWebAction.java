package com.data.data.hmly.action.mobile;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.mobile.response.SceneryResponse;
import com.data.data.hmly.action.mobile.response.SceneryTypePriceResponse;
import com.data.data.hmly.service.ticket.TicketExplainService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketExplain;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.response.TicketPriceAddInfo;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/9/19.
 */
public class SceneryWebAction extends FrameBaseAction {

    private Long id;

    @Resource
    private TicketService ticketService;

    @Resource
    private TicketExplainService ticketExplainService;

    @Resource
    private TicketPriceService priceService;

    private Ticket ticket = new Ticket();
    private Long priceTypeId;

    public Result sceneryDesc() {
        TicketPrice ticketPrice = null;
        if (priceTypeId != null) {
            ticketPrice = priceService.getPrice(priceTypeId);
        } else {
            result.put("success", false);
        }
        if (ticketPrice != null) {
            List<TicketPriceAddInfo> addinfoDetailList = priceService.findTicketPriceAddInfoList(ticketPrice);
            result.put("addinfoDetailList", addinfoDetailList);
//            SceneryTypePriceResponse priceResponse = new SceneryTypePriceResponse(ticketPrice);
//            result.put("priceResponse", priceResponse);
            result.put("success", true);
        } else {
            result.put("success", false);
        }

        return json(JSONObject.fromObject(result));
    }
    public Result sceneryPriceType() {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();
        List<TicketPrice> ticketPriceList = new ArrayList<TicketPrice>();
        if (ticket.getId() != null) {
            ticketPriceList = priceService.findTicketPriceListByTicket(ticket);
        }
        List<SceneryTypePriceResponse> typePriceResponses = new ArrayList<SceneryTypePriceResponse>();
        for (TicketPrice price : ticketPriceList) {
            SceneryTypePriceResponse priceResponse = new SceneryTypePriceResponse(price);
            typePriceResponses.add(priceResponse);
        }

        resultList = getPriceTypeMapList(ticketPriceList);
//        'adult','student','child','oldman','taopiao','other','team'
        result.put("result", resultList);
        result.put("priceResponseList", typePriceResponses);
        result.put("success", true);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig("");
        return json(JSONObject.fromObject(result), jsonConfig);
    }
    /**
     * 根据价格类型进行分类处理
     * @param ticketPriceList
     * @return
     */
    public List<Map<String, Object>> getPriceTypeMapList(List<TicketPrice> ticketPriceList) {
        Map<String, List<SceneryTypePriceResponse>> map = Maps.newHashMap();
        for (TicketPrice price : ticketPriceList) {
            SceneryTypePriceResponse priceResponse = new SceneryTypePriceResponse(price);
            if (StringUtils.isNotBlank(price.getType())) {
                String type = price.getType();

                String typeName = getTypeName(type);

                List<SceneryTypePriceResponse> list = map.get(type);
                if (list == null) {
                    list = new ArrayList<SceneryTypePriceResponse>();
                }
                list.add(priceResponse);
                map.put(type, list);

            }
        }
        List<Map<String, Object>> result = new ArrayList<Map<String, Object>>();
        for (Map.Entry<String, List<SceneryTypePriceResponse>> m : map.entrySet()) {
            Map<String, Object> temp = Maps.newHashMap();
            temp.put("type", m.getKey());
            temp.put("list", m.getValue());
            temp.put("typeName", getTypeName(m.getKey()));
            result.add(temp);
        }
        return result;
    }


    public String getTypeName(String type) {

//        'adult','student','child','oldman','taopiao','other','team'
        if ("adult".equals(type)) {
            return "成人票";
        }

        if ("student".equals(type)) {
            return "学生票";
        }

        if ("child".equals(type)) {
            return "儿童票";
        }

        if ("oldman".equals(type)) {
            return "老人票";
        }

        if ("taopiao".equals(type)) {
            return "套票";
        }

        if ("team".equals(type)) {
            return "团体票";
        }
        return "其他";
    }

    public Result sceneryDetail() {
        ticket = ticketService.loadTicket(ticket.getId());
        TicketExplain ticketExplain = ticketExplainService.findExplainByTicketId(ticket.getId());
        SceneryResponse sceneryResponse = new SceneryResponse(ticket, ticketExplain);
        result.put("scenery", sceneryResponse);
        result.put("success", true);
        return json(JSONObject.fromObject(result));
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Long getPriceTypeId() {
        return priceTypeId;
    }

    public void setPriceTypeId(Long priceTypeId) {
        this.priceTypeId = priceTypeId;
    }
}
