package com.data.data.hmly.action.proManage;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.outOrder.dataEntities.JszxLineDatePrice;
import com.data.data.hmly.action.proManage.entityData.LineData;
import com.data.data.hmly.action.proManage.entityData.TicketData;
import com.data.data.hmly.service.QuantityUnitNumService;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.balance.BalanceService;
import com.data.data.hmly.service.common.ProductService;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.QuantityUnitNum;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.UnitType;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.LinetypepriceAgentService;
import com.data.data.hmly.service.line.LinetypepriceService;
import com.data.data.hmly.service.line.LinetypepricedateService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.LinetypepriceAgent;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.data.data.hmly.service.line.entity.enums.LineStatus;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceAgentService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.entity.TicketPriceAgent;
import com.data.data.hmly.service.ticket.entity.enmus.TicketType;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/3/14.
 */
public class ProductManageAction extends FrameBaseAction {

    @Resource
    private SysUserService sysUserService;

    @Resource
    private TicketService ticketService;

    @Resource
    private LineService lineService;

    @Resource
    private LinetypepriceService linetypepriceService;

    @Resource
    private LinetypepriceAgentService linetypepriceAgentService;

    @Resource
    private LinetypepricedateService linetypepricedateService;

    @Resource
    private TicketPriceAgentService ticketPriceAgentService;

    @Resource
    private TicketPriceService ticketPriceService;

    @Resource
    private QuantityUnitNumService quantityUnitNumService;

    @Resource
    private ProductService productService;

    @Resource
    private TicketDatepriceService ticketDatepriceService;

    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private BalanceService balanceService;

    private Long ticketPriceId;

    private String type;

    private Long lineId;

    private Long toplineId;

    private Long ticketId;

    private Long topTicketId;

    private Line line;

    private Ticket ticket;

    private QuantityUnitNum quantityUnitNum;

    private SysUser user;
    private Integer			page				= 1;
    private Integer			rows				= 10;

    Map<String, Object> map = new HashMap<String, Object>();


    /**
     * 功能描述：获取拱量关系下的合作产品
     * @return
     */
    public Result getProListByQuantityNum() {

        SysUnit sysUnit = getCompanyUnit();

        String type = (String) getParameter("type");
        String ticketName = (String) getParameter("ticketName");
        String ticketType = (String) getParameter("ticketType");

        line = new Line();
        ticket = new Ticket();

        Page pageInfo = new Page(page, rows);

        List<SysUnit> supplerUnitList =
                quantityUnitNumService.getSupplerUintByDealurUnit(sysUnit);

        if (isSiteAdmin() || isSupperAdmin()) {
            supplerUnitList.add(getCompanyUnit());
        }

        if (!StringUtils.isNotBlank(type)) {
            return null;
        }
        if (StringUtils.isNotBlank(ticketName)) {
            ticket.setName(ticketName);
            line.setName(ticketName);
        }
        if (StringUtils.isNotBlank(ticketType)) {
            ticket.setTicketType(ticket.fmtTicketType(ticketType));
        }

        ProductType productType = line.fmtProType(type);

        if (productType == ProductType.line) {
            line.setProType(line.fmtProType(type));
            line.setStatus(ProductStatus.UP);
            line.setLineStatus(LineStatus.show);
            List<Line> productList =
                    lineService.getLineListByQuantityUnit(supplerUnitList, getLoginUser(),
                            isSupperAdmin(), isSiteAdmin(), line, pageInfo);

            JsonConfig jsonConfig = JsonFilter.getIncludeConfig("");

            return datagrid(productList, pageInfo.getTotalCount(), jsonConfig);
        } else {
            ticket.setProType(ticket.fmtProType(type));
            ticket.setStatus(ProductStatus.UP);

            List<Ticket> ticketList = ticketService.getTicketListByQuantityUnit(supplerUnitList, getLoginUser(),
                    isSupperAdmin(), isSiteAdmin(), ticket, pageInfo);

            JsonConfig jsonConfig = JsonFilter.getIncludeConfig("ticketType");

            return datagrid(ticketList, pageInfo.getTotalCount(), jsonConfig);
        }
    }

    public Result quantityUnitProListManage() {
//        user = sysUserService.load(getLoginUser().getId());
        user = balanceService.findBalanceAccountBy(getLoginUser().getId());
        return dispatch();
    }


    public Result agentTicketDialog() {

        if (ticketId != null) {
            ticket = ticketService.loadTicket(ticketId);
        }
        return dispatch();
    }


    /**
     * 判断线路是否已经被代理
     * @return
     */
    public Result checkAgentLine() {

        boolean flag = false;
        if (lineId != null) {
            line = lineService.loadLine(lineId);
            flag = lineService.checkAgentLine(line, getLoginUser());
        }
        simpleResult(map, flag, "");
        return jsonResult(map);
    }

    /**
     * 判断门票是否已经被代理
     * @return
     */
    public Result checkAgentTicket() {

        boolean flag = false;
        if (ticketId != null) {
            ticket = ticketService.loadTicket(ticketId);
            flag = ticketService.checkAgentTicket(ticket, getLoginUser());
        }
        simpleResult(map, flag, "");
        return jsonResult(map);
    }

    /**
     * 编辑代理线路
     * @return
     */
    public Result doEditAgentLine() {

        String typePriceObject = (String) getParameter("typePriceObj");
        String lineRemark = (String) getParameter("lineRemark");
        String topLineIdStr = (String) getParameter("topLineIdStr");

        if (!StringUtils.isNotBlank(typePriceObject)) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }
        if (!StringUtils.isNotBlank(topLineIdStr)) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }

        if (!StringUtils.isNotBlank(lineRemark)) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }

        if (lineId == null) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }
        String staticPath = propertiesManager.getString("IMG_DIR");

        line = lineService.loadLine(lineId);

        Line topLine = lineService.loadLine(Long.parseLong(topLineIdStr));

        line.setProductRemark(lineRemark);

        //删除旧的记录
        linetypepriceAgentService.delAllTypePrice(line);

        List<LinetypepriceAgent> linetypepriceAgentList = new ArrayList<LinetypepriceAgent>();
        JSONArray jsonArray = JSONArray.fromObject(typePriceObject);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = JSONObject.fromObject(obj);
            LinetypepriceAgent linetypepriceAgent = (LinetypepriceAgent) JSONObject.toBean(jsonObject, LinetypepriceAgent.class);
            linetypepriceAgent.setTopline(topLine);
            linetypepriceAgent.setLine(line);
            linetypepriceAgent.setTopTypePrice(linetypepriceService.getLinetypeprice(linetypepriceAgent.getTopTypePriceId()));
            linetypepriceAgent.setCreateTime(new Date());
            linetypepriceAgent.setUserId(getLoginUser().getId());
            linetypepriceAgentList.add(linetypepriceAgent);
        }

        linetypepriceAgentService.saveAll(linetypepriceAgentList);

        lineService.update(line);

        simpleResult(map, true, "");

        return jsonResult(map);

    }

    public Result checkAgentLineDialog() {

        if (lineId != null) {
            line = lineService.loadLine(lineId);
        }
        return dispatch();
    }

    public Result checkAgentTicketDialog() {

        if (ticketId != null) {
            ticket = ticketService.loadTicket(ticketId);
        }
        return dispatch();
    }


    /**
     * 门票代理：查找自己所代理的门票
     * @return
     */
    public Result agentTicketList() {

        Page pageInfo = new Page(page, rows);

        ticket = new Ticket();
//        ticket.setStatus(ProductStatus.UP);

        List<Ticket> ticketList = ticketService.findAgentTicketList(ticket, pageInfo, getLoginUser(), isSiteAdmin(), isSupperAdmin());
        List<Ticket> newTicketList = new ArrayList<Ticket>();

        for (Ticket ticketObj : ticketList) {
            Long topId = ticketObj.getTopProduct().getId();
            Ticket topTicket = ticketService.loadTicket(topId);
            if (topTicket.getCompanyUnit() != null && topTicket.getCompanyUnit().getName() != null) {
                ticketObj.setSuplierName(topTicket.getCompanyUnit().getName());
            }
            newTicketList.add(ticketObj);
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(newTicketList, pageInfo.getTotalCount(), jsonConfig);
    }



    /**
     * 线路代理：查找自己所代理的线路
     * @return
     */
    public Result agentLineList() {

        Page pageInfo = new Page(page, rows);

        line = new Line();

//        line.setStatus(ProductStatus.UP);

        line.setAgentLine("true");

        List<Line> lineList = lineService.findAgentLineList(line, pageInfo, getLoginUser(), isSiteAdmin(), isSupperAdmin());
        List<Line> newLineList = new ArrayList<Line>();

        for (Line lineObj : lineList) {
            Long topId = lineObj.getTopProduct().getId();
            Line topLine = lineService.loadLine(topId);
            if (topLine.getCompanyUnit() != null && topLine.getCompanyUnit().getName() != null) {
                lineObj.setSuplierName(topLine.getCompanyUnit().getName());
            }
            newLineList.add(lineObj);
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(newLineList, pageInfo.getTotalCount(), jsonConfig);
    }

    /**
     * 线路代理页面
     * @return
     */
    public Result agentTicketLineManage() {
        return dispatch();
    }


    /**
     * 代理门票
     * @return
     */
    public Result doAgentTicket() {
        String typePriceObject = (String) getParameter("typePriceObj");
//        String lineRemark = (String) getParameter("lineRemark");

        if (!StringUtils.isNotBlank(typePriceObject)) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }

//        if (!StringUtils.isNotBlank(lineRemark)) {
//            simpleResult(map, false, "");
//            return jsonResult(map);
//        }

        if (ticketId == null) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }
        String staticPath = propertiesManager.getString("IMG_DIR");

        ticket = ticketService.loadTicket(ticketId);
//        line = lineService.loadLine(lineId);
//        line.setProductRemark(lineRemark);

        List<TicketPriceAgent> ticketPriceAgentList = new ArrayList<TicketPriceAgent>();

        JSONArray jsonArray = JSONArray.fromObject(typePriceObject);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = JSONObject.fromObject(obj);

            String topTypePrice = jsonObject.get("topTypePriceId").toString();
            String rebateStr = jsonObject.get("rebate").toString();


            TicketPrice topTicketPrice = ticketPriceService.getPrice(Long.parseLong(topTypePrice));

            TicketPriceAgent ticketPriceAgent = new TicketPriceAgent();

            ticketPriceAgent.setTopTicketPrice(topTicketPrice);
            ticketPriceAgent.setTopTicket(ticket);
            ticketPriceAgent.setName(topTicketPrice.getName());
            ticketPriceAgent.setDiscountPrice(topTicketPrice.getDiscountPrice());
            ticketPriceAgent.setRebate(Float.parseFloat(rebateStr));
            ticketPriceAgent.setType(topTicketPrice.getType());
            ticketPriceAgent.setAddTime(new Date());
            ticketPriceAgent.setGetTicket(topTicketPrice.getGetTicket());
            ticketPriceAgent.setMaketPrice(topTicketPrice.getMaketPrice());
//            ticketPriceAgent.setStatus(topTicketPrice.getStatus());
            ticketPriceAgent.setUserid(getLoginUser().getId());


            ticketPriceAgentList.add(ticketPriceAgent);
        }

        map = ticketService.doTicketAgent(ticket, getLoginUser(), ticketPriceAgentList, ProductStatus.UP, staticPath);
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 代理线路
     * @return
     */
    public Result doAgentLine() {
        String typePriceObject = (String) getParameter("typePriceObj");
        String lineRemark = (String) getParameter("lineRemark");

        if (!StringUtils.isNotBlank(typePriceObject)) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }

        if (!StringUtils.isNotBlank(lineRemark)) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }

        if (lineId == null) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }
        String staticPath = propertiesManager.getString("IMG_DIR");

        line = lineService.loadLine(lineId);
        line.setProductRemark(lineRemark);

        List<LinetypepriceAgent> linetypepriceAgentList = new ArrayList<LinetypepriceAgent>();
                JSONArray jsonArray = JSONArray.fromObject(typePriceObject);
        for (Object obj : jsonArray) {
            JSONObject jsonObject = JSONObject.fromObject(obj);
            LinetypepriceAgent linetypepriceAgent = (LinetypepriceAgent) JSONObject.toBean(jsonObject, LinetypepriceAgent.class);
            linetypepriceAgent.setTopline(line);
            linetypepriceAgent.setTopTypePrice(linetypepriceService.getLinetypeprice(linetypepriceAgent.getTopTypePriceId()));
            linetypepriceAgent.setCreateTime(new Date());
            linetypepriceAgent.setUserId(getLoginUser().getId());
            linetypepriceAgentList.add(linetypepriceAgent);
        }

        map = lineService.doAgentLine(line, getLoginUser(), LineStatus.show, staticPath, linetypepriceAgentList);
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 代理线路：获取所有价格类型
     * @return
     */
    public Result getLineTypePriceList() {

        if (toplineId != null) {
            line = lineService.loadLine(toplineId);
        }
        List<Linetypeprice> linetypeprices = linetypepriceService.getTypePriceList(line);

        List<LinetypepriceAgent> linetypepriceAgents = new ArrayList<LinetypepriceAgent>();
        List<LinetypepriceAgent> newTypepriceAgents = new ArrayList<LinetypepriceAgent>();
        if (lineId != null) {
            Line agentLine = lineService.loadLine(lineId);
            linetypepriceAgents = linetypepriceAgentService.getTypePriceList(agentLine);
        }

        for (Linetypeprice linetypeprice : linetypeprices) {
            LinetypepriceAgent linetypepriceAgent = new LinetypepriceAgent();
            linetypepriceAgent.setId(linetypeprice.getId());
            for (LinetypepriceAgent priceAgent : linetypepriceAgents) {

                if (priceAgent.getTopTypePrice().getId() == linetypeprice.getId()) {

                    linetypepriceAgent.setAdultRebate(priceAgent.getAdultRebate());
                    linetypepriceAgent.setChildRebate(priceAgent.getChildRebate());
                }
            }
            linetypepriceAgent.setMarketPrice(linetypeprice.getMarketPrice());
            linetypepriceAgent.setStatus(linetypeprice.getStatus());
            linetypepriceAgent.setQuoteDesc(linetypeprice.getQuoteDesc());
            linetypepriceAgent.setQuoteNoContainDesc(linetypeprice.getQuoteNoContainDesc());
            linetypepriceAgent.setQuoteContainDesc(linetypeprice.getQuoteContainDesc());
            linetypepriceAgent.setQuoteName(linetypeprice.getQuoteName());

            newTypepriceAgents.add(linetypepriceAgent);
        }
        return datagrid(newTypepriceAgents);
    }


    /**
     * 代理门票：获取所有价格类型
     * @return
     */
    public Result getTicketTypePriceList() {


        List<TicketPrice> ticketPriceList = new ArrayList<TicketPrice>();
        if (topTicketId != null) {
            ticket = ticketService.loadTicket(topTicketId);
            ticketPriceList = ticketPriceService.findTicketPriceListByTicket(ticket);
        }

        List<TicketPriceAgent> ticketPriceAgentList = new ArrayList<TicketPriceAgent>();
        List<TicketPriceAgent> ticketPriceAgents = new ArrayList<TicketPriceAgent>();
        if (ticketId != null) {
            ticket = null;
            ticket = ticketService.loadTicket(ticketId);
            ticketPriceAgentList = ticketPriceAgentService.findTicketPriceListByTicket(ticket);
        }

        for (TicketPrice ticketPrice : ticketPriceList) {
            TicketPriceAgent ticketPriceAgent = new TicketPriceAgent();
            ticketPriceAgent.setId(ticketPrice.getId());
            for (TicketPriceAgent priceAgent : ticketPriceAgentList) {
                if (priceAgent.getTopTicketPrice().getId() == ticketPrice.getId()) {
                    ticketPriceAgent.setRebate(priceAgent.getRebate());
                }
            }
            ticketPriceAgent.setType(ticketPrice.getType());
            ticketPriceAgent.setName(ticketPrice.getName());
            ticketPriceAgent.setDiscountPrice(ticketPrice.getDiscountPrice());
//            ticketPriceAgent.setStatus(ticketPrice.getStatus());
            ticketPriceAgents.add(ticketPriceAgent);
        }
        return datagrid(ticketPriceAgents);
    }


    public Result agentLineDialog() {
        line = lineService.loadLine(lineId);
        return dispatch();
    }

    public Result agentProductManage() {
        return dispatch();
    }



    public Result getProLineList() {

        Page pageInfo = new Page(page, rows);

        String lineName = (String) getParameter("lineName");
        Line line = new Line();
        line.setName(lineName);
        SysUser sysUser = getLoginUser();

        if (sysUser.getSysUnit().getUnitType() == UnitType.department) {
            SysUnit unit = sysUser.getSysUnit().getCompanyUnit();
            line.setCompanyUnit(unit);
        }

        List<Line> proLineList = lineService.findProLineList(line, pageInfo);

//        List<TicketData> ticketDatas = createTicketDataList(ticketList);

        List<LineData>  lineDataList = createLineDataList(proLineList);
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();


        return datagrid(lineDataList, pageInfo.getTotalCount(), jsonConfig);

    }


    public Result getLineDatePriceList() {

        if (type == null) {
            return null;
        }
        if (lineId == null) {
            return null;
        }

        line = lineService.loadLine(lineId);

        List<JszxLineDatePrice> ticketDatepriceList = new ArrayList<JszxLineDatePrice>();
        String dateString = formatDay(new Date());
        Date date = getDate(dateString);

        if (line != null && line.getPreOrderDay() != null) {
            date = DateUtils.getStartDay(date, line.getPreOrderDay());
        }

        if ("adult".equals(type)) {
            List<Linetypepricedate> linetypepricedates = linetypepricedateService.findTypePriceDate(ticketPriceId, date, null);

            for (Linetypepricedate linetypepricedate : linetypepricedates) {

                JszxLineDatePrice ticketDateprice = new JszxLineDatePrice();

                ticketDateprice.setId(linetypepricedate.getId());
                ticketDateprice.setHuiDate(linetypepricedate.getDate());
                ticketDateprice.setPriPrice(linetypepricedate.getDiscountPrice());
                ticketDateprice.setRebate(linetypepricedate.getRebate());

                ticketDatepriceList.add(ticketDateprice);
            }
        } else {
            List<Linetypepricedate> linetypepricedates = linetypepricedateService.findTypePriceDate(ticketPriceId, date, null);

            for (Linetypepricedate linetypepricedate : linetypepricedates) {

                JszxLineDatePrice ticketDateprice = new JszxLineDatePrice();

                ticketDateprice.setId(linetypepricedate.getId());
                ticketDateprice.setHuiDate(linetypepricedate.getDate());
                ticketDateprice.setPriPrice(linetypepricedate.getChildPrice());
                ticketDateprice.setRebate(linetypepricedate.getRebate());

                ticketDatepriceList.add(ticketDateprice);
            }

        }
        String[] includeConfig = new String[]{};
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, includeConfig);
        JSONArray jsonArray = JSONArray.fromObject(ticketDatepriceList, jsonConfig);
        return json(jsonArray);


    }

    public List<LineData> createLineDataList(List<Line> lineList) {

        List<LineData> lineDataList = new ArrayList<LineData>();

        for (Line line : lineList) {
            LineData data = new LineData();
            data.setName(line.getName());
            Float minAdultPrice = linetypepriceService.getMinLineAdultPrice(line);
//            Float minChildPrice = linetypepriceService.getMinLineChildPrice(line);
            data.setAdultprice(minAdultPrice);
//            data.setChildprice(minChildPrice);
            if (line.getCompanyUnit() != null) {
                data.setCompanyId(line.getCompanyUnit().getId());
            }
            data.setCompanyName(line.getCompanyUnitName());
            data.setCompanyPhone(line.getCompanyPhone());
            data.setCompanyQQ(line.getCompanyQQ());
            data.setId(line.getId());
            data.setPlayDay(line.getPlayDay());

            lineDataList.add(data);
        }

        return lineDataList;
    }



    public Result getTicketPriceList() {

        ticket = ticketService.loadTicket(ticketId);
        List<TicketDateprice> ticketDatepriceList = new ArrayList<TicketDateprice>();
        String dateString = formatDay(new Date());
        Date date = getDate(dateString);

        if (ticket != null && ticket.getPreOrderDay() != null) {
            date = DateUtils.getStartDay(date, ticket.getPreOrderDay());
        }

        ticketDatepriceList = ticketDatepriceService.findTypePriceDate(ticketPriceId, date, null);
        String[] includeConfig = new String[]{};
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, includeConfig);
        JSONArray jsonArray = JSONArray.fromObject(ticketDatepriceList, jsonConfig);
        return json(jsonArray);
    }

    public Result getTicketList() {

        Page pageInfo = new Page(page, rows);

        String ticketName = (String) getParameter("ticketName");
        String ticketType = (String) getParameter("ticketType");

        Ticket ticket = new Ticket();

        if (StringUtils.isNotBlank(ticketName)) {
            ticket.setName(ticketName);
        }
        if (StringUtils.isNotBlank(ticketType)) {

            if ("scenic".equals(ticketType)) {
                ticket.setTicketType(TicketType.scenic);
            }

            if ("sailboat".equals(ticketType)) {
                ticket.setTicketType(TicketType.sailboat);
            }
            if ("shows".equals(ticketType)) {
                ticket.setTicketType(TicketType.shows);
            }

        }

        SysUser sysUser = getLoginUser();

        ticket.setProType(ProductType.scenic);

        if (sysUser.getSysUnit().getUnitType() == UnitType.department) {
            SysUnit unit = sysUser.getSysUnit().getCompanyUnit();
            ticket.setCompanyUnit(unit);
        }

        List<Ticket> ticketList = ticketService.findProTicketList(ticket, pageInfo);

        List<TicketData> ticketDatas = createTicketDataList(ticketList);

        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(ticketDatas, pageInfo.getTotalCount(), jsonConfig);

    }


    public List<TicketData> createTicketDataList(List<Ticket> ticketList) {

        List<TicketData> ticketDataList = new ArrayList<TicketData>();

        for (Ticket ticket : ticketList) {
            TicketData data = new TicketData();

            data.setId(ticket.getId());
            data.setName(ticket.getName());
            data.setType(ticket.getTicketType().toString());


            Float minPrice = ticketPriceService.findTicketMinPrice(ticket);
            data.setPrice(minPrice);

            ticketDataList.add(data);
        }

        return ticketDataList;
    }



    public Result productManage() {
        user = balanceService.findBalanceAccountBy(getLoginUser().getId());
//        user = sysUserService.load(getLoginUser().getId());
        return dispatch();
    }


    private String formatDay(Date date) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        return dateFormater.format(date);
    }

    private Date getDate(String dateString) {
        SimpleDateFormat dateFormater = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormater.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }


    public SysUser getUser() {
        return user;
    }

    public void setUser(SysUser user) {
        this.user = user;
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

    public Long getTicketPriceId() {
        return ticketPriceId;
    }

    public void setTicketPriceId(Long ticketPriceId) {
        this.ticketPriceId = ticketPriceId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Long getLineId() {
        return lineId;
    }

    public void setLineId(Long lineId) {
        this.lineId = lineId;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public Line getLine() {
        return line;
    }

    public void setLine(Line line) {
        this.line = line;
    }

    public Long getToplineId() {
        return toplineId;
    }

    public void setToplineId(Long toplineId) {
        this.toplineId = toplineId;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Long getTopTicketId() {
        return topTicketId;
    }

    public void setTopTicketId(Long topTicketId) {
        this.topTicketId = topTicketId;
    }

    public QuantityUnitNum getQuantityUnitNum() {
        return quantityUnitNum;
    }

    public void setQuantityUnitNum(QuantityUnitNum quantityUnitNum) {
        this.quantityUnitNum = quantityUnitNum;
    }


}
