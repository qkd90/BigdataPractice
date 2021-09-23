package com.data.data.hmly.action.yhy;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.ProductService;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.enums.ShowStatus;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.entity.enums.ScenicInfoType;
import com.data.data.hmly.service.ticket.TicketExplainService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketExplain;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.BeanUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-12-02,0002.
 */
public class YhySailboatInfoAction extends FrameBaseAction {

    private Map<String, Object> result = new HashMap<String, Object>();

    private Long id;

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private Ticket ticket = new Ticket();
    private TicketExplain ticketExplain = new TicketExplain();


    @Resource
    private ProductService productService;
    @Resource
    private TicketService ticketService;
    @Resource
    private TicketExplainService ticketExplainService;
    @Resource
    private ScenicInfoService scenicInfoService;

    public Result toSailboatInfo() {
        if (id != null) {
            ShowStatus showStatus = productService.getShowStatus(id);
            if (showStatus == null || ShowStatus.HIDE_FOR_CHECK.equals(showStatus)) {
                return redirect("/yhy/yhyMain/toSailboatList.jhtml");
            }
            getSession().setAttribute("editTicketId", id);
        } else {
            getSession().removeAttribute("editTicketId");
        }
        ScenicInfo scenicInfo = new ScenicInfo();
        scenicInfo.setScenicType(ScenicInfoType.sailboat);
        scenicInfo.setStatus(1);
        List<ScenicInfo> sailboatList = scenicInfoService.list(scenicInfo, null);
        getRequest().setAttribute("sailboatList", sailboatList);
        return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailboat_info.jsp");
    }
     public Result toSailboatInfoDetail() {
         if (id != null) {
             ProductStatus status = productService.getProStatus(id);
             if (status != null && !ProductStatus.DEL.equals(status)) {
                 getSession().setAttribute("ticketId", id);
                 return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailboat_info_detail.jsp");
             }
         }
         getSession().removeAttribute("ticketId");
         return redirect("/yhy/yhyMain/toSailboatList.jhtml");
     }

    public Result getSailboatInfo() {
        if (id != null) {
            // ticket info
            Ticket ticket = ticketService.loadTicket(id);
            TicketExplain ticketExplain = ticketExplainService.findExplainByTicketId(ticket.getId());
            Class productClazz = ticket.getClass().getSuperclass();
            Method[] proMethods = productClazz.getMethods();
            Field[] ticketFields = ticket.getClass().getDeclaredFields();
            Field[] explainFields = ticketExplain.getClass().getDeclaredFields();
            try {
                // reflect product info to map
                for (Method method : proMethods) {
                    if (method.getName().startsWith("get")) {
                        String fName = method.getName().substring(3, method.getName().length());
                        String fieldName = fName.substring(0, 1).toLowerCase() + fName.substring(1);
                        result.put("ticket." + fieldName, method.invoke(ticket));
                    }
                }
                // reflect ticket info to map
                for (Field field : ticketFields) {
                    field.setAccessible(true);
                    if (!"scenicInfo".equals(field.getName())) {
                        result.put("ticket." + field.getName(), field.get(ticket));
                    }
                }
                // reflect ticket explain info to map
                for (Field field : explainFields) {
                    field.setAccessible(true);
                    result.put("ticketExplain." + field.getName(), field.get(ticketExplain));
                }
                // scenic info
                result.put("ticket.scenicInfo.id", ticket.getScenicInfo().getId());

            } catch (IllegalAccessException e) {
                e.printStackTrace();
                result.put("success", false);
                result.put("msg", "数据获取错误!");
            } catch (InvocationTargetException e) {
                e.printStackTrace();
                result.put("success", false);
                result.put("msg", "数据获取错误!");
            }
            result.put("success", true);
            result.put("msg", "");
            JsonConfig jsonConfig = JsonFilter.getIncludeConfig("extend");
            return json(JSONObject.fromObject(result, jsonConfig));
        }
        result.put("success", false);
        result.put("msg", "产品id错误!");
        return json(JSONObject.fromObject(result));
    }

    public Result getYhySailBoatList() {
        SysUser loginUser = getLoginUser();
        SysUnit companyUnit = loginUser.getSysUnit().getCompanyUnit();
        Integer pageIndex = start / length + 1;
        Page page = new Page(pageIndex, length);
        List<Ticket> ticketList = ticketService.getSailboatByCompanyUnit(ticket, companyUnit, page);
        List<Ticket> resultTicketList = Lists.transform(ticketList, new Function<Ticket, Ticket>() {
            @Override
            public Ticket apply(Ticket ticket) {
                if (ticket.getProductimage() != null && ticket.getProductimage().size() > 0) {
                    ticket.setImageTotalCount(ticket.getProductimage().size());
                }
                return ticket;
            }
        });
        result.put("data", resultTicketList);
        result.put("recordsTotal", page.getTotalCount());
        result.put("recordsFiltered", page.getTotalCount());
        result.put("draw", draw);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig()));
    }

    public Result saveSailboatInfo() {
        SysUser loginUser = getLoginUser();
        ticket.setTicketName(StringUtils.htmlEncode(ticket.getTicketName()));
        ticket.setAddress(StringUtils.htmlEncode(ticket.getAddress()));
        ticket.setName(StringUtils.htmlEncode(ticket.getName()));
        if (ticket.getId() != null) {
            ProductStatus status = productService.getProStatus(ticket.getId());
            // edit source data (without temp data)
            if (ticket.getOriginId() == null && ProductStatus.UP.equals(status)) {
                // source ticket info
                Ticket sourceTicket = ticketService.loadTicket(ticket.getId());
                TicketExplain sourceTicketExplain = ticketExplainService.findExplainByTicketId(sourceTicket.getId());
                sourceTicket.setShowStatus(ShowStatus.HIDE_FOR_CHECK);
                sourceTicket.setUpdateTime(new Date());
                // create temp ticket / ticket explain
                Ticket tempTicket = new Ticket();
                TicketExplain tempTicketExplain = new TicketExplain();
                // copy source ticket / ticket explain to tempTicket
                BeanUtils.copyProperties(sourceTicket, tempTicket, false, null, "id", "ticketPriceSet", "labelItems", "productimage", "commentList");
                BeanUtils.copyProperties(sourceTicketExplain, tempTicket, false, null, "id");
                // copy update ticket / ticket explain to tempTicket
                BeanUtils.copyProperties(ticket, tempTicket, false, null, "id");
                BeanUtils.copyProperties(ticketExplain, tempTicketExplain, false, null, "id");
                // set orgin ticket info
                tempTicket.setOriginId(sourceTicket.getId());
                tempTicket.setShowStatus(ShowStatus.SHOW);
                tempTicket.setStatus(ProductStatus.UP_CHECKING);
                tempTicket.setCreateTime(new Date());
                tempTicket.setUpdateTime(new Date());
                // update source ticket
                ticketService.update(sourceTicket);
                // save temp ticket
                ticketService.save(tempTicket);
                tempTicketExplain.setTicketId(tempTicket.getId());
                ticketExplainService.save(tempTicketExplain);
            } else if (ticket.getOriginId() != null || !ProductStatus.UP.equals(status)) {
                // edit temp ticket
                Ticket tempTicket = ticketService.loadTicket(ticket.getId());
                TicketExplain tempTicketExplain = ticketExplainService.findExplainByTicketId(ticket.getId());
                // source ticket
                Ticket sourceTicket;
                TicketExplain sourceTicketExplain;
                if (ticket.getOriginId() != null) {
                    sourceTicket = new Ticket();
                    sourceTicket.setId(tempTicket.getOriginId());
                    sourceTicketExplain = ticketExplainService.findExplainByTicketId(tempTicket.getOriginId());
                } else {
                    sourceTicket = tempTicket;
                    sourceTicketExplain = tempTicketExplain;
                }
                // copy update ticket / ticket explain info to tempTicket
                BeanUtils.copyProperties(ticket, tempTicket, false, null, "id", "ticketPriceSet", "labelItems", "productimage", "commentList");
                BeanUtils.copyProperties(ticketExplain, tempTicketExplain, false, null, "id");
                tempTicket.setStatus(ProductStatus.UP_CHECKING);
                tempTicket.setUpdateTime(new Date());
                // update temp ticket / ticket explain
                ticketService.update(tempTicket);
                ticketExplainService.update(tempTicketExplain);
            }
        } else {
            // new ticket data
            ScenicInfo scenicInfo = scenicInfoService.get(ticket.getScenicInfo().getId());
            ticket.setStatus(ProductStatus.UP_CHECKING);
            ticket.setShowStatus(ShowStatus.SHOW);
            ticket.setCityId(scenicInfo.getCity().getId());
            ticket.setUser(loginUser);
            ticket.setCompanyUnit(loginUser.getSysUnit().getCompanyUnit());
            ticket.setProType(ProductType.scenic);
            ticket.setTopProduct(ticket);
            ticket.setSupplier(loginUser);
            ticket.setSource(ProductSource.LXB);
            ticket.setCreateTime(new Date());
            ticket.setUpdateTime(new Date());
            ticket.setShowOrder(999);
            // save ticket
            ticketService.save(ticket);
            // save ticket explain
            ticketExplain.setTicketId(ticket.getId());
            ticketExplainService.save(ticketExplain);
        }
        result.put("success", true);
        result.put("msg", "");
        return json(JSONObject.fromObject(result));
    }

    public Result downTicket() {
        if (id != null) {
            Ticket ticket = ticketService.loadTicket(id);
            if (ticket != null && ProductStatus.UP.equals(ticket.getStatus())) {
                ticket.setStatus(ProductStatus.DOWN_CHECKING);
                ticketService.update(ticket);
                result.put("success", true);
                result.put("msg", "操作成功!");
            } else {
                result.put("success", false);
                result.put("msg", "产品不存在或状态错误!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "产品id错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result revokeTicket() {
        if (id != null) {
            Ticket ticket = ticketService.loadTicket(id);
            if (ticket != null) {
                ProductStatus status = ticket.getStatus();
                Long originId = ticket.getOriginId();
                if (ProductStatus.UP_CHECKING.equals(status) || ProductStatus.REFUSE.equals(status)) {
                    // temp data
                    if (originId != null) {
                        // delete temp data
                        TicketExplain tempTicketExplain = ticketExplainService.findExplainByTicketId(ticket.getId());
                        ticketExplainService.delete(ticketExplain);
                        ticketService.delete(ticket);
                        // update origin data
                        Ticket sourceTicket = ticketService.loadTicket(originId);
                        sourceTicket.setShowStatus(ShowStatus.SHOW);
                        ticketService.update(sourceTicket);
                    } else {
                        // update source data
                        // back to DOWN status
                        ticket.setStatus(ProductStatus.DOWN);
                        ticketService.update(ticket);
                    }
                    result.put("success", true);
                    result.put("msg", "操作成功!");
                } else if (ProductStatus.DOWN_CHECKING.equals(status)) {
                    // update source data
                    // back to UP status
                    ticket.setStatus(ProductStatus.UP);
                    ticketService.update(ticket);
                    result.put("success", true);
                    result.put("msg", "操作成功!");
                } else {
                    result.put("success", false);
                    result.put("msg", "产品状态错误! 操作失败!");
                }
            } else {
                result.put("success", false);
                result.put("msg", "产品id错误! 请检查产品状态!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "产品id错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result delTicket() {
        if (id != null) {
            Ticket ticket = ticketService.loadTicket(id);
            if (ticket != null && ProductStatus.DOWN.equals(ticket.getStatus())) {
                ticket.setStatus(ProductStatus.DEL);
                ticketService.update(ticket);
                result.put("success", true);
                result.put("msg", "操作成功!");
            } else {
                result.put("success", false);
                result.put("msg", "产品不存在或状态错误!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "产品id错误!");
        }
        return json(JSONObject.fromObject(result));
    }


    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getDraw() {
        return draw;
    }

    public void setDraw(Integer draw) {
        this.draw = draw;
    }

    public Integer getStart() {
        return start;
    }

    public void setStart(Integer start) {
        this.start = start;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public TicketExplain getTicketExplain() {
        return ticketExplain;
    }

    public void setTicketExplain(TicketExplain ticketExplain) {
        this.ticketExplain = ticketExplain;
    }
}
