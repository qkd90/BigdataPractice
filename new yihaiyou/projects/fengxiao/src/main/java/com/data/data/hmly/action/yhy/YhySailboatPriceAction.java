package com.data.data.hmly.action.yhy;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ShowStatus;
import com.data.data.hmly.service.contract.ContractService;
import com.data.data.hmly.service.contract.entity.Contract;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketPriceTypeExtendService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.entity.TicketPriceTypeExtend;
import com.data.data.hmly.service.ticket.entity.enmus.TicketPriceStatus;
import com.data.data.hmly.service.ticket.vo.TicketPriceCalendarVo;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.BeanUtils;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang.StringUtils;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-12-02,0002.
 */
public class YhySailboatPriceAction extends FrameBaseAction {
    private Map<String, Object> result = new HashMap<String, Object>();

    private Long productId;
    private Long id;
    private Long ticketPriceId;
    private TicketPrice ticketPrice = new TicketPrice();
    private Productimage productimage = new Productimage();
    private List<TicketPriceTypeExtend> priceTypeExtends = new ArrayList<TicketPriceTypeExtend>();
    private List<Productimage> imgList = new ArrayList<Productimage>();
    private List<TicketPriceCalendarVo> priceCalendarVos = new ArrayList<TicketPriceCalendarVo>();


    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String sort;
    private String order;

    @Resource
    private ProductimageService productimageService;
    @Resource
    private TicketPriceService ticketPriceService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;
    @Resource
    private TicketService ticketService;
    @Resource
    private TicketPriceTypeExtendService ticketPriceTypeExtendService;
    @Resource
    private ContractService contractService;

    public Result toSailboatPriceType() {
        if (id != null) {
            ShowStatus showStatus = ticketPriceService.getShowStatus(id);
            if (ShowStatus.HIDE_FOR_CHECK.equals(showStatus)) {
                return redirect("/yhy/yhyMain/toSailboatPriceList.jhtml");
            }
            getSession().setAttribute("editPriceId", id);
        } else {
            getSession().removeAttribute("editPriceId");
        }
        return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailboat_pricetype.jsp");
    }

    public Result toTicketPriceDetail() {
        if (id != null) {
            TicketPriceStatus status = ticketPriceService.getPriceStatus(id);
            if (status != null && !TicketPriceStatus.DEL.equals(status)) {
                getSession().setAttribute("ticketPriceId", id);
                return dispatch("/WEB-INF/jsp/yhy/yhySailboat/sailboat_pricetype_detail.jsp");
            }
        }
        getSession().removeAttribute("ticketPriceId");
        return redirect("/yhy/yhyMain/toSailboatPriceList.jhtml");
    }

    public Result getYhyTicketPriceInfo() {
        if (id != null) {
            // ticket price info
            TicketPrice ticketPrice = ticketPriceService.getPrice(id);
            if (ticketPrice.getOriginId() != null) {
                TicketPrice originTicketPrice = ticketPriceService.getPrice(ticketPrice.getOriginId());
                Ticket originTicket = originTicketPrice.getTicket();
                productimage.setProduct(originTicket);
            } else {
                productimage.setProduct(ticketPrice.getTicket());
            }
            productimage.setTargetId(ticketPrice.getId());
            priceTypeExtends = ticketPrice.getTicketPriceTypeExtends();
            imgList = productimageService.findProductimage(productimage, null);
            // put image data
            result.put("imgData", imgList);
            result.put("priceTypeExtends", priceTypeExtends);
            try {
                Field[] priceFields = ticketPrice.getClass().getDeclaredFields();
                for (Field field : priceFields) {
                    field.setAccessible(true);
                    result.put("ticketPrice." + field.getName(), field.get(ticketPrice));
                }
                result.put("ticket.id", ticketPrice.getTicket().getId());
            } catch (IllegalAccessException e) {
                e.printStackTrace();
                result.put("success", false);
                result.put("msg", "数据获取错误!");
            }
            result.put("success", true);
            result.put("msg", "");
            JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
            return json(JSONObject.fromObject(result, jsonConfig));
        }
        result.put("success", false);
        result.put("msg", "票型id错误!");
        return json(JSONObject.fromObject(result));
    }

    public Result getYhySailBoatPriceList() {
        SysUser loginUser = getLoginUser();
        SysUnit companyUnit = loginUser.getSysUnit().getCompanyUnit();
        Integer pageIndex = start / length + 1;
        Page page = new Page(pageIndex, length);
        List<TicketPrice> ticketPriceList = ticketPriceService.findByTicket(ticketPrice, companyUnit, page, sort, order);
        result.put("data", ticketPriceList);
        result.put("recordsTotal", page.getTotalCount());
        result.put("recordsFiltered", page.getTotalCount());
        result.put("draw", draw);
        return json(JSONObject.fromObject(result, JsonFilter.getIncludeConfig("ticket")));
    }

    public Result savePriceInfo() {
        SysUser loginUser = getLoginUser();
        // source ticket data
        Ticket sourceTicket = ticketService.loadTicket(productId);
        if (sourceTicket != null && !ProductStatus.DEL.equals(sourceTicket.getStatus())) {
            if (ticketPrice.getId() != null) {
                TicketPriceStatus status = ticketPriceService.getPriceStatus(ticketPrice.getId());
                if (ticketPrice.getOriginId() == null && TicketPriceStatus.UP.equals(status)) {
                    // source ticket price data
                    TicketPrice sourceTicketPrice = ticketPriceService.getPrice(ticketPrice.getId());
                    sourceTicketPrice.setShowStatus(ShowStatus.HIDE_FOR_CHECK);
                    // create temp ticket price info
                    TicketPrice tempTicketPrice = new TicketPrice();
                    // copy source ticket price info
                    BeanUtils.copyProperties(sourceTicketPrice, tempTicketPrice, false, null, "id", "ticketPriceTypeExtends");
                    // copy update ticket price info
                    BeanUtils.copyProperties(ticketPrice, tempTicketPrice, false, null, "id", "ticketPriceTypeExtends");
                    // mark origin ticket price info
                    tempTicketPrice.setOriginId(sourceTicketPrice.getId());
                    tempTicketPrice.setShowStatus(ShowStatus.SHOW);
                    tempTicketPrice.setStatus(TicketPriceStatus.UP_CHECKING);
                    tempTicketPrice.setAddTime(new Date());
                    tempTicketPrice.setModifyTime(new Date());
                    // update source ticket price data
                    sourceTicketPrice.setModifyTime(new Date());
                    ticketPriceService.update(sourceTicketPrice);
                    // save temp ticket price data
                    ticketPriceService.save(tempTicketPrice);
                    // handle ticket price extend info
                    ticketPriceTypeExtendService.deleteByPriceId(tempTicketPrice.getId());
                    for (TicketPriceTypeExtend extend : priceTypeExtends) {
                        extend.setCreateTime(new Date());
                        extend.setTicketPrice(tempTicketPrice);
                    }
                    ticketPriceTypeExtendService.saveAll(priceTypeExtends);
                    // handle product image
                    // delete old UP_CHECKING img
                    productimageService.delImgByUpChecking(sourceTicket.getId(), tempTicketPrice.getId(), ProductStatus.UP_CHECKING, "ticket/ticketPrice/");
                    for (Productimage productimage : imgList) {
                        productimage.setId(null);
                        productimage.setProduct(sourceTicket);
                        productimage.setTargetId(tempTicketPrice.getId());
                        productimage.setStatus(ProductStatus.UP_CHECKING);
                        productimage.setCreateTime(new Date());
                        if (productimage.getShowOrder() == null) {
                            productimage.setShowOrder(999);
                        }
                        productimage.setCompanyUnitId(loginUser.getSysUnit().getCompanyUnit().getId());
                    }
                    productimageService.saveAll(imgList);
                } else if (ticketPrice.getOriginId() != null || !TicketPriceStatus.UP.equals(status)) {
                    // edit temp ticket price
                    TicketPrice tempTicketPrice = ticketPriceService.getPrice(ticketPrice.getId());
                    // source ticket price
                    TicketPrice sourceTicketPrice;
                    if (ticketPrice.getOriginId() != null) {
                        sourceTicketPrice = ticketPriceService.getPrice(ticketPrice.getOriginId());
                    } else {
                        sourceTicketPrice = tempTicketPrice;
                    }
                    // copy update ticket price info to temp ticket price
                    BeanUtils.copyProperties(ticketPrice, tempTicketPrice, false, null, "id");
                    tempTicketPrice.setStatus(TicketPriceStatus.UP_CHECKING);
                    // update temp ticket price
                    tempTicketPrice.setModifyTime(new Date());
                    ticketPriceService.update(tempTicketPrice);
                    // handle ticket price extend info
                    ticketPriceTypeExtendService.deleteByPriceId(tempTicketPrice.getId());
                    for (TicketPriceTypeExtend extend : priceTypeExtends) {
                        extend.setCreateTime(new Date());
                        extend.setTicketPrice(tempTicketPrice);
                    }
                    ticketPriceTypeExtendService.saveAll(priceTypeExtends);
                    // handle product images
                    // del old UP_CHECKING img
                    productimageService.delImgByUpChecking(sourceTicket.getId(), tempTicketPrice.getId(), ProductStatus.UP_CHECKING, "ticket/ticketPrice/");
                    for (Productimage productimage : imgList) {
                        productimage.setId(null);
                        productimage.setProduct(sourceTicket);
                        productimage.setTargetId(tempTicketPrice.getId());
                        productimage.setStatus(ProductStatus.UP_CHECKING);
                        productimage.setCreateTime(new Date());
                        if (productimage.getShowOrder() == null) {
                            productimage.setShowOrder(999);
                        }
                        productimage.setCompanyUnitId(loginUser.getSysUnit().getCompanyUnit().getId());
                    }
                    productimageService.saveAll(imgList);
                }
            } else {
                // new ticket price data
                ticketPrice.setTicket(sourceTicket);
                ticketPrice.setStatus(TicketPriceStatus.UP_CHECKING);
                ticketPrice.setShowStatus(ShowStatus.SHOW);
                ticketPrice.setAddTime(new Date());
                ticketPrice.setModifyTime(new Date());
                ticketPrice.setShowOrder(999);
                // save ticket price info
                ticketPriceService.save(ticketPrice);
                // handle ticket price extend info
                for (TicketPriceTypeExtend extend : priceTypeExtends) {
                    extend.setCreateTime(new Date());
                    extend.setTicketPrice(ticketPrice);
                }
                ticketPriceTypeExtendService.saveAll(priceTypeExtends);
                // handle product images
                int index = 1;
                for (Productimage productimage : imgList) {
                    productimage.setId(null);
                    productimage.setProduct(sourceTicket);
                    productimage.setTargetId(ticketPrice.getId());
                    productimage.setStatus(ProductStatus.UP_CHECKING);
                    productimage.setCreateTime(new Date());
                    productimage.setCompanyUnitId(loginUser.getSysUnit().getCompanyUnit().getId());
                    productimage.setShowOrder(index);
                    index ++;
                }
                productimageService.saveAll(imgList);
            }
            result.put("success", "true");
            result.put("msg", "");
        } else {
            result.put("success", false);
            result.put("msg", "产品信息不存在或已删除!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result downTicketPrice() {
        if (id != null) {
            TicketPrice ticketPrice = ticketPriceService.getPrice(id);
            if (ticketPrice != null && TicketPriceStatus.UP.equals(ticketPrice.getStatus())) {
                // update status
                ticketPrice.setStatus(TicketPriceStatus.DOWN);
                ticketPriceService.update(ticketPrice);
                result.put("success", true);
                result.put("msg", "操作成功!");
            } else {
                result.put("success", false);
                result.put("msg", "票型不存在或状态错误! 操作失败!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "票型id错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result revokeTicketPrice() {
        if (id != null) {
            TicketPrice ticketPrice = ticketPriceService.getPrice(id);
            if (ticketPrice != null) {
                TicketPriceStatus status = ticketPrice.getStatus();
                Long originId = ticketPrice.getOriginId();
                if (TicketPriceStatus.UP_CHECKING.equals(status) || TicketPriceStatus.REFUSE.equals(status)) {
                    // temp data
                    if (originId != null) {
                        TicketPrice sourceTicketPrice = ticketPriceService.getPrice(originId);
                        Ticket sourceTicket = sourceTicketPrice.getTicket();
                        // delete temp data
                        ticketPriceService.delete(ticketPrice);
                        ticketPriceTypeExtendService.deleteByPriceId(ticketPrice.getId());
                        productimageService.delImages(sourceTicket.getId(), ticketPrice.getId(), "ticket/ticketPrice/");
                        // update origin data
                        sourceTicketPrice.setShowStatus(ShowStatus.SHOW);
                        ticketPriceService.update(sourceTicketPrice);
                    } else {
                        // update source data
                        // back to DOWN status
                        ticketPrice.setStatus(TicketPriceStatus.DOWN);
                        ticketPriceService.update(ticketPrice);
                    }
                    result.put("success", true);
                    result.put("msg", "操作成功!");
                } else {
                    result.put("success", false);
                    result.put("msg", "票型状态错误! 操作失败!");
                }
            } else {
                result.put("success", false);
                result.put("msg", "票型id错误! 请检查票型状态!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "票型id错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result delTicketPrice() {
        if (id != null) {
            TicketPrice ticketPrice = ticketPriceService.getPrice(id);
            if (ticketPrice != null && TicketPriceStatus.DOWN.equals(ticketPrice.getStatus())) {
                // update status
                ticketPrice.setStatus(TicketPriceStatus.DEL);
                ticketPriceService.update(ticketPrice);
                result.put("success", true);
                result.put("msg", "操作成功!");
            } else {
                result.put("success", false);
                result.put("msg", "票型不存在或状态错误! 操作失败!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "票型id错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result getTicketDatePrice() {
        SysUser loginUser = getLoginUser();

        // 参数
        String ticketPriceIdStr = (String) getParameter("ticketPriceId");
        String startDateStr = (String) getParameter("startDate");
        String endDateStr = (String) getParameter("endDate");
        Date startDate = null;
        Date endDate = null;
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (StringUtils.isNotBlank(ticketPriceIdStr)) {
            Long ticketPriceId = Long.parseLong(ticketPriceIdStr);
            // 获取计价模式
            Ticket ticket = ticketPriceService.getPrice(ticketPriceId).getTicket();
            Contract contract = contractService.getContractByCompanyB(ticket.getCompanyUnit());
            if (contract != null && contract.getValuationModels() != null) {
                result.put("valuationModels", contract.getValuationModels());
                result.put("valuationValue", contract.getValuationValue());
            } else {
                result.put("success", false);
                result.put("msg", "无效合同或计价方式错误!");
            }


            if (StringUtils.isNotBlank(startDateStr)) {
                startDate = DateUtils.getDate(startDateStr, "yyyy-MM-dd");
            }
            if (StringUtils.isNotBlank(endDateStr)) {
                endDate = DateUtils.getDate(endDateStr, "yyyy-MM-dd");
            }
            // 如果起始时间不为空且结束时间为空，则设置结束时间为开始时间当月最后一天
            if (startDate != null && endDate == null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(startDate);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 6);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
                endDate = calendar.getTime();
            }
            // 如果起始时间为空且结束时间不为空，则设置起始时间为结束时间当月的第一天
            if (startDate == null && endDate != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(endDate);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                startDate = calendar.getTime();
            }
            List<TicketDateprice> ticketDatepriceList = ticketDatepriceService.findTypePriceDate(ticketPriceId, startDate, null);
            for (TicketDateprice dateprice : ticketDatepriceList) {
                if (dateprice.getPriPrice() != null) {
                    Map<String, String> map = new HashMap<String, String>();
                    Date day = dateprice.getHuiDate();
                    map.put("id", "1" + day.getTime());
                    map.put("priPrice", String.valueOf(dateprice.getPriPrice()));
                    map.put("title", "销售价:" + dateprice.getPriPrice());
                    map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
                    list.add(map);
                }
                if (dateprice.getPrice() != null) {
                    Map<String, String> map = new HashMap<String, String>();
                    Date day = dateprice.getHuiDate();
                    map.put("id", "2" + day.getTime());
                    map.put("price", String.valueOf(dateprice.getPrice()));
                    map.put("title", "结算价:" + dateprice.getPrice());
                    map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
                    list.add(map);
                }
                if (dateprice.getInventory() != null) {
                    Map<String, String> map = new HashMap<String, String>();
                    Date day = dateprice.getHuiDate();
                    map.put("id", "3" + day.getTime());
                    map.put("inventory", String.valueOf(dateprice.getInventory()));
                    map.put("title", "库存:" + dateprice.getInventory());
                    map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
                    list.add(map);
                }
            }
            result.put("success", true);
            result.put("msg", "");
            result.put("data", list);
        } else {
            result.put("success", false);
            result.put("msg", "票型id错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result saveTicketDatePrice() {
        if (ticketPriceId != null && productId != null) {
            TicketPrice sourceTicketPrice = ticketPriceService.getPrice(ticketPriceId);
            List<TicketDateprice> datepriceList = new ArrayList<TicketDateprice>();
            for (TicketPriceCalendarVo priceCalendarVo : priceCalendarVos) {
                TicketDateprice ticketDateprice = new TicketDateprice();
                ticketDateprice.setTicketPriceId(sourceTicketPrice);
                ticketDateprice.setHuiDate(DateUtils.getDate(priceCalendarVo.getDate(), "yyyy-MM-dd"));
                ticketDateprice.setPriPrice(priceCalendarVo.getPriPrice());
                ticketDateprice.setPrice(priceCalendarVo.getPrice());
                ticketDateprice.setInventory(priceCalendarVo.getInventory());
                datepriceList.add(ticketDateprice);
            }
            // delete old price calendar data
            ticketDatepriceService.deleteAll(sourceTicketPrice, new Date());
            // save new price calendar data
            ticketDatepriceService.saveAll(datepriceList);
            // update ticket price info
            Float minPriPrice = ticketDatepriceService.findMinPrice(sourceTicketPrice, new Date());
            sourceTicketPrice.setDiscountPrice(minPriPrice);
            ticketPriceService.update(sourceTicketPrice);
            ticketService.indexTicket(sourceTicketPrice.getTicket());
            result.put("success", true);
            result.put("msg", "");
        } else {
            result.put("success", false);
            result.put("msg", "票型或产品信息错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Map<String, Object> getResult() {
        return result;
    }

    public void setResult(Map<String, Object> result) {
        this.result = result;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getTicketPriceId() {
        return ticketPriceId;
    }

    public void setTicketPriceId(Long ticketPriceId) {
        this.ticketPriceId = ticketPriceId;
    }

    public TicketPrice getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(TicketPrice ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public Productimage getProductimage() {
        return productimage;
    }

    public void setProductimage(Productimage productimage) {
        this.productimage = productimage;
    }

    public List<TicketPriceTypeExtend> getPriceTypeExtends() {
        return priceTypeExtends;
    }

    public List<Productimage> getImgList() {
        return imgList;
    }

    public void setImgList(List<Productimage> imgList) {
        this.imgList = imgList;
    }

    public List<TicketPriceCalendarVo> getPriceCalendarVos() {
        return priceCalendarVos;
    }

    public void setPriceCalendarVos(List<TicketPriceCalendarVo> priceCalendarVos) {
        this.priceCalendarVos = priceCalendarVos;
    }

    public void setPriceTypeExtends(List<TicketPriceTypeExtend> priceTypeExtends) {
        this.priceTypeExtends = priceTypeExtends;
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

    public String getSort() {
        return sort;
    }

    public void setSort(String sort) {
        this.sort = sort;
    }

    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }
}
