package com.data.data.hmly.action.ticket;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.common.ProductService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.QuantitySalesDetailService;
import com.data.data.hmly.service.common.QuantitySalesService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.QuantitySales;
import com.data.data.hmly.service.common.entity.QuantitySalesDetail;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.enums.QuantityType;
import com.data.data.hmly.service.common.entity.enums.ShowStatus;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.goods.CategoryService;
import com.data.data.hmly.service.goods.CategoryTypeService;
import com.data.data.hmly.service.goods.entity.Category;
import com.data.data.hmly.service.goods.entity.CategoryType;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.entity.enums.ScenicInfoType;
import com.data.data.hmly.service.ticket.TicketDatepriceService;
import com.data.data.hmly.service.ticket.TicketExplainService;
import com.data.data.hmly.service.ticket.TicketPriceService;
import com.data.data.hmly.service.ticket.TicketPriceTypeExtendService;
import com.data.data.hmly.service.ticket.TicketService;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketDateprice;
import com.data.data.hmly.service.ticket.entity.TicketExplain;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.entity.TicketPriceTypeExtend;
import com.data.data.hmly.service.ticket.entity.enmus.TicketPriceStatus;
import com.data.data.hmly.service.ticket.entity.enmus.TicketType;
import com.data.data.hmly.service.ticket.vo.TicketPriceCalendarVo;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.ibm.icu.text.SimpleDateFormat;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.BeanUtils;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.QiniuUtil;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrServerException;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.text.ParsePosition;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author cjj
 * @date 2015年3月30日
 * @TODO 用户管理
 */
public class TicketAction extends FrameBaseAction {
    private static final long serialVersionUID = 1L;
    @Resource
    private TicketService ticketService;
    @Resource
    private TbAreaService tbareaService;
    @Resource
    private ScenicInfoService scenicInfoService;
    @Resource
    private TicketExplainService ticketExplainService;
    @Resource
    private TicketPriceService ticketPriceService;

    @Resource
    private TicketPriceTypeExtendService ticketPriceTypeExtendService;

    @Resource
    private QuantitySalesService quantitySalesService;

    @Resource
    private QuantitySalesDetailService quantitySalesDetailService;

    @Resource
    private TicketDatepriceService datepriceService;
    @Resource
    private CategoryService categoryService;

    @Resource
    private CategoryTypeService categoryTypeService;

    @Resource
    private LabelItemService labelItemService;

    @Resource
    private ProductService productService;

    @Resource
    private LabelService labelService;
    @Resource
    private PropertiesManager propertiesManager;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private TicketDatepriceService ticketDatepriceService;


    private String fgDomain;

    private TicketForm ticketForm = new TicketForm();

    private List<TbArea> fatherAreas;
    private List<Category> linecategorgs = new ArrayList<Category>();

    private Integer page = 1;
    private Integer rows = 10;

    private String dateStartStr;
    private String dateEndStr;

    private long cateId;

    private Long ticketId;
    private Long productId;
    private Long ticId;
    private Long ticketPriceId;
    private Integer showOrder;
    private String sort;
    private String order;


    private String priceStr;   // 日期价格字符串

    private String agent;


    private String json;

    private String ticketDesc = "ticketDesc";

    private Ticket ticket = new Ticket();

    private TicketPrice ticketPrice;
    private TicketExplain ticketExplain;
    private QuantitySales quantitySales;

    private List<TicketType> filterTypes = new ArrayList<TicketType>();
    private List<Productimage> productimages = new ArrayList<Productimage>();
    private List<Productimage> imgList = new ArrayList<Productimage>();
    private List<TicketPriceCalendarVo> priceCalendarVos = new ArrayList<TicketPriceCalendarVo>();
    private List<TicketPriceTypeExtend> priceTypeExtends = new ArrayList<TicketPriceTypeExtend>();
    private List<Long> ids = new ArrayList<Long>();

    Map<String, Object> map = new HashMap<String, Object>();



    public Result saveSailboatInfo() {
        if (ticket.getId() == null) {
            simpleResult(map, false, "");
            return jsonResult(map);
        }
        if (ticketExplain.getId() == null) {
            simpleResult(map, false, null);
            return jsonResult(map);
        }
        Ticket tempTicket = ticketService.loadTicket(ticket.getId());
        BeanUtils.copyProperties(ticket, tempTicket, false, null, "id", "ticketPriceSet", "labelItems", "productimage", "commentList", "scenicInfo");
        TicketExplain tempTicketExplain = ticketExplainService.load(ticketExplain.getId());
        BeanUtils.copyProperties(ticketExplain, tempTicketExplain, false, null, "id");

        ticketService.update(tempTicket);
        ticketExplainService.update(tempTicketExplain);
        map.put("id", tempTicket.getId());
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 游艇帆船审核编辑页面
     * @return
     */
    public Result sailboatCheckInfo() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
        if (productId != null) {
            ticket = ticketService.loadTicket(productId);
            ticketExplain = ticketExplainService.findExplainByTicketId(productId);
        }
        return dispatch("/WEB-INF/jsp/ticket/sailboat/sailboatCheckInfoEdit.jsp");
    }

    /**
     * 删除门票/游艇帆船
     * @return
     */
    public Result doTicketInfoDel() {
        if (productId == null) {
            simpleResult(map, false, "");
            return json(JSONObject.fromObject(map));
        }
        ticket = ticketService.loadTicket(productId);
//        ticketExplain = ticketExplainService.findExplainByTicketId(ticketId);
        ticket.setStatus(ProductStatus.DEL);
        ticket.setShowStatus(ShowStatus.HIDE_FOR_CHECK);
        ticketService.update(ticket);

        ticketPriceService.doHandleHotelPriceAll(ticket, TicketPriceStatus.DEL);

        simpleResult(map, true, "");
        return jsonResult(map);
    }


    /**
     * 撤销门票/游艇帆船
     * @return
     */
    public Result doTicketInfoReturn() {

        if (productId == null) {
            simpleResult(map, false, "");
            return json(JSONObject.fromObject(map));
        }
        ticket = ticketService.loadTicket(productId);
        TicketExplain tempTicketExplain = ticketExplainService.findExplainByTicketId(productId);

        Ticket orginTicket = ticketService.loadTicket(ticket.getOriginId());
        orginTicket.setStatus(ProductStatus.DOWN);
        orginTicket.setShowStatus(ShowStatus.SHOW);

        ticketService.delete(ticket);
        ticketExplainService.delete(tempTicketExplain);

        ticketPriceService.deleteTempTicketPriceList(ticket);    //删除临时数据
        ticketPriceService.doHandleHotelPriceAll(orginTicket, TicketPriceStatus.DOWN);

        ticketService.update(orginTicket);
        simpleResult(map, true, "");
        return jsonResult(map);
    }


    /**
     * 审核门票产品
     * @return
     */
    public Result doChecked() {
        if (ticketId != null) {
            Ticket ticketTemp = ticketService.loadTicket(ticketId);
            if (ticket.getStatus() == ProductStatus.UP) {
                ticketTemp.setStatus(ProductStatus.UP);
                ticketTemp.setAuditReason("审核成功！");
            } else {
                ticketTemp.setStatus(ProductStatus.REFUSE);
                ticketTemp.setAuditReason("审核不成功！");
            }
            ticketTemp.setAuditBy(getLoginUser().getId());
            ticketTemp.setAuditTime(new Date());
            ticketService.update(ticketTemp);
            map.put("success", true);
        } else {
            map.put("success", false);
        }
        return jsonResult(map);
    }



    /**
     * 票型审核上架通过操作
     * @return
     */
    public Result doCheckTicketPriceInfoUp() {

        if (ticketPriceId == null) {
            simpleResult(map, false, "");
            return json(JSONObject.fromObject(map));
        }
        ticketPrice = ticketPriceService.getPrice(ticketPriceId);
        if (ticketPrice.getOriginId() == null) {
            ticketPrice.setStatus(TicketPriceStatus.UP);
            ticketPrice.setShowStatus(ShowStatus.SHOW);
            ticketPrice.setAuditTime(new Date());
            ticketPrice.setAuditBy(getLoginUser().getId());
            ticketPriceService.update(ticketPrice);

            productimages = productimageService.findAllImagesByProIdAadTarId(ticketPrice.getTicket().getId(), ticketPrice.getId(), "ticket/ticketPrice/");	//找到新增票型数据图片
            productimageService.doCheckImagesStatus(productimages, ticketPrice.getTicket(), ticketPrice.getId());	//设置新增票型数据图片
            ticketService.indexTicket(ticketPrice.getTicket());
        } else {
            TicketPrice orginTicketPrice = ticketPriceService.getPrice(ticketPrice.getOriginId());
            BeanUtils.copyProperties(ticketPrice, orginTicketPrice, false, null, "id", "originId", "ticketPriceTypeExtends");
            orginTicketPrice.setStatus(TicketPriceStatus.UP);
            orginTicketPrice.setShowStatus(ShowStatus.SHOW);
            orginTicketPrice.setAuditTime(new Date());
            orginTicketPrice.setAuditBy(getLoginUser().getId());


            ticketPriceTypeExtendService.deleteByPriceId(orginTicketPrice.getId());

            ticketPriceTypeExtendService.doHandlerPriceExtend(orginTicketPrice, ticketPrice);

            ticketPriceService.delete(ticketPrice);
            ticketPriceService.update(orginTicketPrice);

            productimageService.delImages(orginTicketPrice.getTicket().getId(), orginTicketPrice.getId(), "ticket/ticketPrice/"); 	//删除原始数据票型图片
            productimages = productimageService.findAllImagesByProIdAadTarId(orginTicketPrice.getTicket().getId(), ticketPrice.getId(), "ticket/ticketPrice/");	//获取票型临时数据图片
            productimageService.doCheckImagesStatus(productimages, orginTicketPrice.getTicket(), orginTicketPrice.getId());	//把票型临时数据图片设为票型原始数据图片
            ticketService.indexTicket(ticketPrice.getTicket());
        }
        simpleResult(map, true, "");
        return json(JSONObject.fromObject(map));
    }


    public Result checkPriceInfo() {
        ticketPriceId = ticketPriceId;
        return dispatch("/WEB-INF/jsp/ticket/sailboat/sailboatCheckPriceTypeInfo.jsp");

    }


    /**
     * 保存门票票型
     * @return
     */
    public Result savePriceInfo() {

        if (ticketPrice.getId() != null) {
            TicketPrice sourceTicketPrice = ticketPriceService.getPrice(ticketPrice.getId());
            ticket = ticketService.loadTicket(sourceTicketPrice.getTicket().getId());
            BeanUtils.copyProperties(ticketPrice, sourceTicketPrice, false, null, "id", "ticketPriceTypeExtends");
            ticketPriceService.update(sourceTicketPrice);
            ticketPriceTypeExtendService.deleteByPriceId(ticketPrice.getId());
            for (TicketPriceTypeExtend extend : priceTypeExtends) {
                extend.setCreateTime(new Date());
                extend.setTicketPrice(sourceTicketPrice);
            }
            ticketPriceTypeExtendService.saveAll(priceTypeExtends);
            productimageService.delImages(ticket.getId(), sourceTicketPrice.getId(), "ticket/ticketPrice/");
            for (Productimage productimage : imgList) {
                productimage.setId(null);
                productimage.setProduct(ticket);
                productimage.setTargetId(sourceTicketPrice.getId());
                productimage.setCreateTime(new Date());
                productimage.setCompanyUnitId(sourceTicketPrice.getTicket().getCompanyUnit().getId());
            }
            productimageService.saveAll(imgList);
            simpleResult(map, true, "");
        } else {
            simpleResult(map, false, "");
        }
        return json(JSONObject.fromObject(map));
    }

    /**
     * 保存票型价格日历
     * @return
     */
    public Result saveTicketDatePrice() {
        if (ticketPriceId != null) {
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
            ticketDatepriceService.delAllByprice(sourceTicketPrice);
            // save new price calendar data
            ticketDatepriceService.saveAll(datepriceList);
            // update ticket price info
            Float minPriPrice = ticketDatepriceService.findMinPrice(sourceTicketPrice, new Date());
            sourceTicketPrice.setDiscountPrice(minPriPrice);
            ticketPriceService.update(sourceTicketPrice);
            ticketService.indexTicket(sourceTicketPrice.getTicket());
            result.put("success", true);
            result.put("msg", "");
            ticketService.indexTicket(sourceTicketPrice.getTicket());
        } else {
            result.put("success", false);
            result.put("msg", "票型或产品信息错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result viewPrcieCalendar() {
        ticketPriceId = ticketPriceId;
        return dispatch("/WEB-INF/jsp/ticket/sailboat/viewPriceCalendar.jsp");
    }

    /**
     * 删除票型
     * @return
     */
    public Result doTicketPriceInfoDel() {
        if (ticketPriceId == null) {
            simpleResult(map, false, "");
            return json(JSONObject.fromObject(map));
        }
        ticketPrice = ticketPriceService.getPrice(ticketPriceId);
        ticketPrice.setShowStatus(ShowStatus.HIDE_FOR_CHECK);
        ticketPrice.setStatus(TicketPriceStatus.DEL);
        ticketPriceService.update(ticketPrice);
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    /**
     * 撤销票型，临时数据被删除，原始数据被下架
     * @return
     */
    public Result doTicketPriceInfoReturn() {
        if (ticketPriceId == null) {
            simpleResult(map, false, "");
            return json(JSONObject.fromObject(map));
        }
        ticketPrice = ticketPriceService.getPrice(ticketPriceId);
        TicketPrice orginTicketPrice = ticketPriceService.getPrice(ticketPrice.getOriginId());
        orginTicketPrice.setStatus(TicketPriceStatus.DOWN);
        orginTicketPrice.setShowStatus(ShowStatus.SHOW);
        ticketPriceService.update(orginTicketPrice);

        productimageService.delImages(ticketPrice.getTicket().getId(), ticketPrice.getId(), "ticket/ticketPrice/");
        ticketPriceTypeExtendService.deleteByPriceId(ticketPrice.getId());
        ticketPriceService.delete(ticketPrice);

        simpleResult(map, true, "");
        return json(JSONObject.fromObject(map));
    }


    /**
     * 票型下架操作
     * @return
     */
    public Result doTicketPriceTypeDown() {
        if (ticketPriceId == null) {
            simpleResult(map, false, "");
            return json(JSONObject.fromObject(map));
        }
        ticketPrice = ticketPriceService.getPrice(ticketPriceId);
        ticketPrice.setStatus(TicketPriceStatus.DOWN);
        ticketPrice.setShowStatus(ShowStatus.SHOW);
        ticketPriceService.update(ticketPrice);
        simpleResult(map, true, "");
        return json(JSONObject.fromObject(map));
    }

    /**
     * 游艇帆船、门票票型上架拒绝操作
     * @return
     */
    public Result doRefuseTicketPriceTypeInfoUp() {
        if (ticketPriceId == null) {
            simpleResult(map, false, "");
            return json(JSONObject.fromObject(map));
        }
        ticketPrice = ticketPriceService.getPrice(ticketPriceId);
        String content = (String) getParameter("content");
        ticketPrice.setStatus(TicketPriceStatus.REFUSE);
//        ticketPrice.setShowStatus(ShowStatus.SHOW);
        ticketPrice.setAuditBy(getLoginUser().getId());
        ticketPrice.setAuditReason(content);
        ticketPrice.setAuditTime(new Date());
        ticketPriceService.update(ticketPrice);
        simpleResult(map, true, "");
        return json(JSONObject.fromObject(map));
    }

    /**
     * 游艇帆船、门票票型审核列表
     * @return
     */
    public Result checkingTypePriceSearch() {

        Page pageInfo = new Page(page, rows);


        if (com.zuipin.util.StringUtils.isBlank(sort)) {
            sort = "modifyTime";
        }
        if (com.zuipin.util.StringUtils.isBlank(order)) {
            order = "desc";
        }

        List<TicketPrice> ticketPriceList = ticketPriceService.list(ticketPrice, pageInfo, sort, order);
        /*List<TicketPrice> resultPriceList = Lists.newArrayList();
        for (HotelPrice price : ticketPriceList) {
            price.setRoomNum(hotelRoomService.getRoomNumbers(price.getId(), null));
            resultPriceList.add(price);
        }*/
        return datagrid(ticketPriceList, pageInfo.getTotalCount(), JsonFilter.getIncludeConfig("ticket"));

    }



    /**
     * 审核产品
     * @return
     */
    public Result doCheckTicketInfoUp() {
        if (productId == null) {
            simpleResult(map, false, "");
            return json(JSONObject.fromObject(map));
        }
        ticket = ticketService.loadTicket(productId);
        if (ticket.getOriginId() == null) {
            ticket.setStatus(ProductStatus.UP);
//            ticket.setUpdateTime(new Date());
            ticket.setShowStatus(ShowStatus.SHOW);
            ticket.setAuditBy(getLoginUser().getId());
            ticket.setAuditTime(new Date());
            ticket.setAuditReason("新增上架审核成功");
            ticketService.update(ticket);

            /*productimages = productimageService.findAllImagesByProIdAadTarId(ticket.getId(), ticket.getId(), "");
            productimageService.doCheckImagesStatus(productimages, ticket, ticket.getId());*/

        } else {
            Ticket orginTicket = ticketService.loadTicket(ticket.getOriginId());
            BeanUtils.copyProperties(ticket, orginTicket, false, null, "id", "originId", "ticketPriceSet");



            TicketExplain tempTicketExplian = ticketExplainService.findExplainByTicketId(ticket.getId());
            if (tempTicketExplian == null) {
                tempTicketExplian = new TicketExplain();
            }
            TicketExplain orginTicketExplian = ticketExplainService.findExplainByTicketId(orginTicket.getId());
            if (orginTicketExplian == null) {
                orginTicketExplian = new TicketExplain();
            } else {
                BeanUtils.copyProperties(tempTicketExplian, orginTicketExplian, false, null, "id", "ticketId");

            }


            orginTicket.setStatus(ProductStatus.UP);
            orginTicket.setShowStatus(ShowStatus.SHOW);
//            orginTicket.setUpdateTime(new Date());
            orginTicket.setAuditBy(getLoginUser().getId());
            orginTicket.setAuditTime(new Date());
            orginTicket.setAuditReason("编辑上架审核成功");

            ticketService.delete(ticket);                       //删除门票临时数据
            ticketService.update(orginTicket);                  //更新门票原始数据


            ticketExplainService.delete(tempTicketExplian);     //删除临时数据
            ticketExplainService.update(orginTicketExplian);    //更新原始数据


//            productimageService.delImages(orginTicket.getId(), orginTicket.getId());	//删除门票原始数据图片
            /*productimages = productimageService.findAllImagesByProIdAadTarId(ticket.getId(), ticket.getId());	//找出民宿临时数据图片
            productimageService.doCheckImagesStatus(productimages, orginTicket, orginTicket.getId());	//把临时数据图片设为原始数据图片*/

        }
        simpleResult(map, true, "");
        return json(JSONObject.fromObject(map));
    }

    /**
     * 拒绝游艇帆船基本信息上架操作
     * @return
     */
    public Result doRefuseTicketInfoUp() {
        if (productId == null) {
            simpleResult(map, false, "");
            return json(JSONObject.fromObject(map));
        }
        String content = (String) getParameter("content");
        ticket = ticketService.loadTicket(productId);
        ticket.setStatus(ProductStatus.REFUSE);
        ticket.setAuditBy(getLoginUser().getId());
        ticket.setAuditTime(new Date());
        ticket.setAuditReason(content);
        ticketService.update(ticket);
        simpleResult(map, true, "");
        return json(JSONObject.fromObject(map));
    }


    /**
     * 游艇帆船下架审核操作
     * @return
     */
    public Result doCheckTicketInfoDown() {
        if (productId == null) {
            simpleResult(map, false, "");
            return json(JSONObject.fromObject(map));
        }
        ticket = ticketService.loadTicket(productId);

        ticketPriceService.deleteTempTicketPriceList(ticket);    //删除临时数据
        ticketPriceService.doDownTicketPriceAll(ticket);	//下架房型

        ticket.setShowStatus(ShowStatus.SHOW);
        ticket.setStatus(ProductStatus.DOWN);
//        ticket.setUpdateTime(new Date());
        ticket.setAuditBy(getLoginUser().getId());
        ticket.setAuditTime(new Date());
        ticket.setAuditReason("下架审核成功");
        ticketService.update(ticket);
        simpleResult(map, true, "");
        return json(JSONObject.fromObject(map));
    }

    /**
     * 游艇帆船下架拒绝操作
     * @return
     */
    public Result doRefuseTicketInfoDown() {
        if (productId == null) {
            simpleResult(map, false, "");
            return json(JSONObject.fromObject(map));
        }
        ticket = ticketService.loadTicket(productId);
        String content = (String) getParameter("content");
        ticket.setStatus(ProductStatus.UP);
        ticket.setShowStatus(ShowStatus.SHOW);
        ticket.setAuditBy(getLoginUser().getId());
        ticket.setAuditTime(new Date());
        ticket.setAuditReason(content);
        ticketService.update(ticket);

        simpleResult(map, true, "");
        return json(JSONObject.fromObject(map));
    }


    public Result selectDatePrice() {

        if (ticketPriceId != null && ticketId != null) {
            ticketPriceId = ticketPriceId;
            ticketId = ticketId;
        }
        return dispatch();
    }

    public Result getTicketPriceList() {

        ticket = ticketService.loadTicket(ticketId);
        List<TicketDateprice> ticketDatepriceList = new ArrayList<TicketDateprice>();
        String dateString = formatDay(new Date());
        Date date = getDate(dateString);

        if (ticket != null && ticket.getPreOrderDay() != null) {
            date = DateUtils.getStartDay(date, ticket.getPreOrderDay());
        }
        ticketDatepriceList = datepriceService.findTypePriceDate(ticketPriceId, date, null);
        String[] includeConfig = new String[]{};
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig(null, includeConfig);
        JSONArray jsonArray = JSONArray.fromObject(ticketDatepriceList, jsonConfig);
        return json(jsonArray);
    }

    /**
     * 初始化获取已有的拱量信息
     * @return
     */
    public Result getQuantitySalesByTicketPrice() {

        List<QuantitySalesDetail> quantitySalesDetails =  new ArrayList<QuantitySalesDetail>();
        String quantitySalesIddStr = (String) getParameter("quantitySalesId");
        if (StringUtils.isNotBlank(quantitySalesIddStr)) {
            quantitySales = quantitySalesService.load(Long.parseLong(quantitySalesIddStr));
            quantitySalesDetails = quantitySalesDetailService.getListByQuantitySales(quantitySales);
        }
        return  datagrid(quantitySalesDetails);
    }

    /**
     * 保存门票拱量数据
     * @return
     */
    public Result saveQuantitySales() {

        String quantitySalesStr = (String) getParameter("quantitySalesStr");


        String quantitySalesIdStr = (String) getParameter("quantitySalesId");
        String ticketPriceIdStr = (String) getParameter("ticketPriceId");
        String flagStr = (String) getParameter("flag");
        String qStartTime = (String) getParameter("q_startTime");
        String qEndTime = (String) getParameter("q_endTime");
        String typeStr = (String) getParameter("type");

        if (StringUtils.isNotBlank(quantitySalesIdStr)) {
            quantitySales = quantitySalesService.load(Long.parseLong(quantitySalesIdStr));
        } else {
            quantitySales = new QuantitySales();
        }

        if (!StringUtils.isNotBlank(ticketPriceIdStr)) {
            simpleResult(map, false, "门票编号获取失败！");
        }

        if (!StringUtils.isNotBlank(flagStr)) {
            simpleResult(map, false, "拱量对象获取失败！");
        }

        if (StringUtils.isNotBlank(qStartTime)) {
           quantitySales.setStartTime(DateUtils.getDate(qStartTime, "yyyy-MM-dd HH:mm"));
        }
        if (StringUtils.isNotBlank(qEndTime)) {
            quantitySales.setEndTime(DateUtils.getDate(qEndTime, "yyyy-MM-dd HH:mm"));
        }

        if (!StringUtils.isNotBlank(typeStr)) {
            simpleResult(map, false, "拱量方式获取失败！");
        }
//        quantitySalesService.delQuantitySalesByTypePriceId(Long.parseLong(ticketPriceIdStr));
        ticketPrice = ticketPriceService.getPrice(Long.parseLong(ticketPriceIdStr));
        quantitySales.setProduct(ticketPrice.getTicket());
        quantitySales.setPriceTypeId(ticketPrice.getId());
        quantitySales.setFlag(quantitySales.setQuantityFlagType(flagStr));
        quantitySales.setType(quantitySales.setQuantityType(typeStr));
        quantitySales.setStatus(1);     //有效
        quantitySales.setCreateTime(new Date());
        quantitySales.setUpdateTime(new Date());
        quantitySales.setPriceTypeId(Long.parseLong(ticketPriceIdStr));

        if (quantitySales.getId() != null) {
            quantitySalesService.update(quantitySales);
        } else {
            quantitySalesService.save(quantitySales);
        }


        List<QuantitySalesDetail> quantitySalesDetails = new ArrayList<QuantitySalesDetail>();

        quantitySalesDetailService.delByQuantitySalesId(quantitySales.getId());

        if (StringUtils.isNotBlank(quantitySalesStr)) {
            JSONArray jsonArray = JSONArray.fromObject(quantitySalesStr);
            for (Object object : jsonArray) {

                QuantitySalesDetail quantitySalesDetail = new QuantitySalesDetail();

                JSONObject jsonObject = JSONObject.fromObject(object);
//                "numStart":0,"numEnd":45,"percent":"23","money":""
                String numStartStr = jsonObject.getString("numStart");
                String numEndStr = jsonObject.getString("numEnd");
                String moneyStr = jsonObject.getString("favorablePrice");
                String percentStr = jsonObject.getString("discount");

                Float discount = null;
                Float favorablePrice = null;
                if (StringUtils.isNotBlank(moneyStr)) {
                    favorablePrice = Float.parseFloat(moneyStr);
                }
                if (StringUtils.isNotBlank(percentStr)) {
                    discount = Float.parseFloat(percentStr);
                }

                if (StringUtils.isNotBlank(numStartStr)) {
                    quantitySalesDetail.setNumStart(Integer.parseInt(numStartStr));
                }

                quantitySalesDetail.setQuantitySales(quantitySales);
                if (StringUtils.isNotBlank(numEndStr)) {
                    quantitySalesDetail.setNumEnd(Integer.parseInt(numEndStr));
                }
                if (quantitySales.getType() == QuantityType.percent) {
                    quantitySalesDetail.setDiscount(discount);
                } else {
                    quantitySalesDetail.setFavorablePrice(favorablePrice);
                }
                quantitySalesDetails.add(quantitySalesDetail);
            }
            quantitySalesDetailService.saveAll(quantitySalesDetails);
            simpleResult(map, true, "保存成功！");

        } else {
            simpleResult(map, false, "保存失败");
        }

        return jsonResult(map);
    }

    /**
     * 拱量设置页面
     * @return
     */
    public Result quantitySalesDialog() {
        if (ticketPriceId != null) {
            ticketPrice = ticketPriceService.getPrice(ticketPriceId);
            quantitySales = quantitySalesService.getQuantitySalesByTypePriceId(ticketPriceId);
        }
        return dispatch();
    }


    public Result addcategory() {
        return dispatch();
    }

    public Result getServiceRoot() {

        CategoryType categoryType = categoryTypeService.getByType("service");

        return getParentCategory(categoryType);

    }

    public Result getParentCategory(CategoryType type) {
        SysUnit sysUnit = getCompanyUnit();
        List<Category> root = categoryService.getRootService(type, sysUnit);
        List<Category> result = new ArrayList<Category>();
        Category linecategory = new Category();
        long rootId = 0;
        linecategory.setId(rootId);
        linecategory.setName("上级分类");
        result.add(linecategory);
        result.addAll(root);
        List<Map<String, Object>> maps = new ArrayList<Map<String, Object>>();
        for (Category category : result) {
            if (cateId != 0 && category.getId() == cateId) {
                continue;
            }
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("id", category.getId());
            map.put("name", category.getName());
            maps.add(map);
        }

        return json(JSONArray.fromObject(maps));
    }

    public Result edit() throws ParseException {

        String proInfo = (String) getParameter("proInfo");
        String privilege = (String) getParameter("privilege");
        String rule = (String) getParameter("rule");
        String enterDesc = (String) getParameter("enterDesc");
        String filePath = (String) getParameter("filePath");
        String openTime = (String) getParameter("openTime");
        String tips = (String) getParameter("tips");
        String ticId = (String) getParameter("ticId");
        String scenicId = (String) getParameter("scenicId");
        String argeeUrlStr = (String) getParameter("agreeRuleStr");
        if (StringUtils.isNotBlank(argeeUrlStr)) {
            ticket.setAgreeRule(argeeUrlStr);
        } else {
            ticket.setAgreeRule("0");
        }
        ticket.setName(com.zuipin.util.StringUtils.htmlEncode(ticket.getName()));
        Productimage productimage = null;
        if (StringUtils.isNotBlank(ticket.getTicketImgUrl())) {
            productimage = new Productimage();
            productimage.setPath(filePath);
            productimage.setProType(ProductType.scenic);
            productimage.setChildFolder(ticketDesc);
            productimage.setCoverFlag(true);
            productimage.setUserId(getLoginUser().getId());
            productimage.setCreateTime(new Date());
        }
        TicketExplain explain = null;
        if (ticket.getId() == null) {
            explain = new TicketExplain();
            if (proInfo != null) {
                explain.setProInfo(proInfo);
            }
            if (rule != null) {
                explain.setRule(rule);
            }
            if (enterDesc != null) {
                explain.setEnterDesc(enterDesc);
            }
            if (privilege != null) {
                explain.setPrivilege(privilege);
            }
            if (com.zuipin.util.StringUtils.isNotBlank(openTime)) {
                explain.setOpenTime(openTime);
            }
            if (com.zuipin.util.StringUtils.isNotBlank(tips)) {
                explain.setTips(tips);
            }
            Date date = new Date();
            ticket.setAddTime(date);
            ticket.setCreateTime(date);
            ticket.setUpdateTime(new Date());
            filePath = ticket.getTicketImgUrl();
            if (StringUtils.isNotBlank(filePath) && filePath.startsWith(QiniuUtil.URL)) {
                String relationFilePath = filePath.substring(QiniuUtil.URL.length());
                ticket.setTicketImgUrl(relationFilePath);
            } else {
                ticket.setTicketImgUrl(null);
            }
            ticket.setUpdateTime(date);
            ticket.setUser(getLoginUser());
            ticket.setProType(ProductType.scenic);
            ticket.setStatus(ProductStatus.DOWN);
//			ticket.setTicketImgUrl(filePath);
            if (ticket.getScenicInfo().getId() != null) {
                ScenicInfo sinfo = scenicInfoService.get(ticket.getScenicInfo().getId());
                ticket.setScenicInfo(sinfo);
            } else {
                ticket.setScenicInfo(null);
            }
            String beginTime = (String) getParameter("beginTime");
            if (beginTime != null && beginTime.length() > 0) {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                date = sdf.parse(beginTime);
                ticket.setStartTime(date);
            }
            ticket.setCompanyUnit(getCompanyUnit());
            ticketService.insert(ticket, explain, productimage);
        } else {
            explain = ticketExplainService.findExplainByTicketId(ticket.getId());
            if (proInfo != null) {
                explain.setProInfo(proInfo);
            }
            if (rule != null) {
                explain.setRule(rule);
            }
            if (enterDesc != null) {
                explain.setEnterDesc(enterDesc);
            }
            if (privilege != null) {
                explain.setPrivilege(privilege);
            }
            if (com.zuipin.util.StringUtils.isNotBlank(openTime)) {
                explain.setOpenTime(openTime);
            }
            if (com.zuipin.util.StringUtils.isNotBlank(tips)) {
                explain.setTips(tips);
            }
            Ticket ticketTemp = ticketService.loadTicket(ticket.getId());
            if (StringUtils.isNotBlank(argeeUrlStr)) {
                ticketTemp.setAgreeRule(argeeUrlStr);
            } else {
                ticketTemp.setAgreeRule("0");
            }
            ticketTemp.setAddress(ticket.getAddress());
            ticketTemp.setCategory(ticket.getCategory());
            ticketTemp.setCityId(ticket.getCityId());
            ticketTemp.setName(ticket.getName());
            ticketTemp.setPreOrderDay(ticket.getPreOrderDay());
            ticketTemp.setTicketName(ticket.getTicketName());
            ticketTemp.setTicketType(ticket.getTicketType());
            ticketTemp.setShowOrder(ticket.getShowOrder());
            ticketTemp.setStartTime(ticket.getStartTime());
            ticketTemp.setNeedConfirm(ticket.getNeedConfirm());
            filePath = ticket.getTicketImgUrl();
            if (StringUtils.isNotBlank(filePath) && filePath.startsWith(QiniuUtil.URL)) {
                String relationFilePath = filePath.substring(QiniuUtil.URL.length());
                ticketTemp.setTicketImgUrl(relationFilePath);
            } else {
                ticketTemp.setTicketImgUrl(null);
            }
            ticketTemp.setAgreeRule(ticket.getAgreeRule());
            ticketTemp.setUpdateTime(new Date());
            ticketTemp.setValidOrderDay(ticket.getValidOrderDay());
            if (ticket.getScenicInfo().getId() != null) {
                ScenicInfo sinfo = scenicInfoService.get(ticket.getScenicInfo().getId());
                ticketTemp.setScenicInfo(sinfo);
            } else {
                ticketTemp.setScenicInfo(null);
            }
            ticket.setId(ticketTemp.getId());

            Date date = new Date();
//			if (ticket.getStartTime() != null) {
//				SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//				date = sdf.parse(beginTime);
//				ticketTemp.setStartTime(date);
//			}
            ticketService.update(ticketTemp, explain);
        }
        map.put("id", ticket.getId());
        map.put("name", ticket.getName());
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    public Result showOrHide() {
        String ticId = (String) getParameter("ticId");
        if (ticId == null && "".equals(ticId)) {
            text("id参数错误！");
        }
        Ticket ticket = ticketService.loadTicket(Long.parseLong(ticId));
        String temp = "";
        if (ProductStatus.UP == ticket.getStatus()) {
            ticket.setStatus(ProductStatus.DOWN);
            ticket.setUpdateTime(new Date());
            temp = "下架成功！";
        } else if (ProductStatus.DOWN == ticket.getStatus()) {
            ticket.setStatus(ProductStatus.UP);
            ticket.setUpdateTime(new Date());
            temp = "上架成功！";
        }

        ticketService.update(ticket);

        if (ticket.getStatus() != ProductStatus.UP) {
            ticketService.delLabelItems(ticket);
        }

        map.put("temp", temp);
        simpleResult(map, true, "");
        return jsonResult(map);
    }

    public Result delTicket() {

        String ticId = (String) getParameter("ticId");

        if (ticId == null && "".equals(ticId)) {
            text("id参数错误！");
        } else {
            /*Ticket ticket = ticketService.loadTicket(Long.parseLong(ticId));
            ticketService.del(ticket);*/
            ticketService.delTicket(Long.parseLong(ticId));
        }

        Ticket ticket = ticketService.loadTicket(Long.parseLong(ticId));
        if (ticket.getStatus() != ProductStatus.UP) {
            ticketService.delLabelItems(ticket);
        }

        simpleResult(map, true, "");
        return jsonResult(map);

    }

    public Result getCategorgList() {
        long userId = getLoginUser().getId();
        SysUnit sysUnit = getCompanyUnit();

        CategoryType categoryType = categoryTypeService.getByType("service");

        List<Category> categorgs = categoryService.getValidCategoryList(categoryType, getLoginUser(), sysUnit, isSiteAdmin(), isSupperAdmin());

//        List<Category> categorgs = categorgService.listValid(categoryType, sysUnit);

        JsonConfig jsonConfig = JsonFilter.getFilterConfig("children", "sysUnit", "user");
        JSONArray json = JSONArray.fromObject(categorgs, jsonConfig);
//		JSONArray json = JSONArray.fromObject(categorgs);
        return json(json);
    }

    public Result getCategorg() {
        String categoryId = (String) getParameter("categoryId");
        if (StringUtils.isNotBlank(categoryId)) {
            Category categorgs = categoryService.findById(Long.parseLong(categoryId));
            JsonConfig jsonConfig = JsonFilter.getFilterConfig("children", "sysUnit", "user");
            JSONObject json = JSONObject.fromObject(categorgs, jsonConfig);
//		JSONArray json = JSONArray.fromObject(categorgs);
            return json(json);
        }
        return json(null);
    }


    /**
     * 通过关键字获取景点列表
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public Result getScenicList() throws UnsupportedEncodingException {
        String keyword = (String) getParameter("name_startsWith");
        String scenicType = (String) getParameter("scenicType");
        if (StringUtils.isBlank(keyword)) {
            return text("关键字不能为空");
        }
        if (StringUtils.isBlank(scenicType)) {
            return text("景点类型不能为空");
        }
        String maxRowsStr = (String) getParameter("maxRows");
        if (StringUtils.isBlank(maxRowsStr)) {
            maxRowsStr = "20";
        }
        Integer maxRows = Integer.valueOf(maxRowsStr);

        Page page = new Page(1, maxRows);
        ScenicInfoType scenicInfoType = ScenicInfoType.valueOf(scenicType);
        List<ScenicInfo> list = scenicInfoService.listByKeyword(keyword, scenicInfoType, page);
        /*
		id : item.id,
		name : item.name+"-"+item.address,
		ticketName : item.name,
		city_code : item.city_code,
		address : item.address,
		star : item.star*/

        List<ScenicTemp> scenicTemps = new ArrayList<ScenicTemp>();
        if (list.size() > 0) {
            for (ScenicInfo s : list) {
                ScenicTemp temp = new ScenicTemp();
                temp.setId(s.getId());
                temp.setName(s.getName());
                temp.setAddress(s.getAddress());
                temp.setCity_code(s.getCityIdLong());
                temp.setStar(s.getStar());
//                    temp.setFatherName(s.getCity().getFullPath());
                temp.setFatherName(s.getAddress());
                scenicTemps.add(temp);
            }
        }


        JSONArray json = JSONArray.fromObject(scenicTemps);
        return json(json);
    }

    private String encode(String keyword) throws UnsupportedEncodingException {
//		return new String(keyword.getBytes("ISO-8859-1"), "UTF-8");
        return keyword;
    }

    /**
     * 获取省
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public Result getProvince() throws UnsupportedEncodingException {

        Object p = getParameter("name_startsWith");
        String keyword = p.toString();
        keyword = encode(keyword);
        if (keyword == null) {
            return text("没有keyword");
        }
        List<TbArea> list = tbareaService.getProByFather(keyword.toString());
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        JSONArray json = JSONArray.fromObject(list, jsonConfig);
//		JSONArray json = JSONArray.fromObject(list);
        return json(json);
    }

    /**
     * 获取市
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public Result getCity() throws UnsupportedEncodingException {

        Object p = getParameter("name_startsWith");
        String keyword = p.toString();
        keyword = encode(keyword);
        if (keyword == null) {
            return text("没有keyword");
        }
        List<TbArea> list = null;
        if (!StringUtils.isEmpty(keyword)) {
            list = tbareaService.getCityByPro(keyword.toString(), 2);
        }


        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        JSONArray json = JSONArray.fromObject(list, jsonConfig);
//		JSONArray json = JSONArray.fromObject(list);
        return json(json);
    }


    /**
     * 通过获取的citycode找到该景点的所属区域
     *
     * @return
     * @throws UnsupportedEncodingException
     */
    public Result getAreaByCitycode() throws UnsupportedEncodingException {

        Object p = getParameter("cityCode");
        String keyword = p.toString();
        keyword = encode(keyword);
        if (keyword == null) {
            return text("没有keyword");
        }
        TbArea tbarea = null;
        if (!"00".equals(keyword) && !StringUtils.isEmpty(keyword)) {
            tbarea = tbareaService.getAreaByCityCode(keyword.toString());
        }


        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        JSONObject json = JSONObject.fromObject(tbarea, jsonConfig);
//		JSONObject json = JSONObject.fromObject(tbarea);
        return json(json);
    }

    /**
     * 获取门票列表
     *
     * @return
     */
    public Result ticketGetList() {

        Page pageInfo = new Page(page, rows);

        ticket = new Ticket();

        String ticketTypeStr = (String) getParameter("ticketType");
        String categoryStr = (String) getParameter("category");
        String sourceStr = (String) getParameter("source");
        String statusStr = (String) getParameter("status");
        String nameStr = (String) getParameter("name");

        if (filterTypes != null && !filterTypes.isEmpty()) {
            ticket.setFilterTicketTypeList(filterTypes);
        }
        if (StringUtils.isNotBlank(sourceStr)) {
            ticket.setSourceStr(sourceStr);
        }
        if (StringUtils.isNotBlank(ticketTypeStr) && !"All".equals(ticketTypeStr)) {
            ticket.setTicketType(TicketType.valueOf(ticketTypeStr));
        }
        if (StringUtils.isNotBlank(categoryStr) && !"All".equals(categoryStr)) {

            ticket.setCategory(Long.parseLong(categoryStr));
        }
        if (StringUtils.isNotBlank(statusStr) && !"All".equals(statusStr)) {
            if ("DEL".equals(statusStr)) {
                ticket.setStatus(ProductStatus.DEL);
            }
            if ("DOWN".equals(statusStr)) {
                ticket.setStatus(ProductStatus.DOWN);
            }
            if ("UP".equals(statusStr)) {
                ticket.setStatus(ProductStatus.UP);
            }
        }

        if (StringUtils.isNotBlank(nameStr)) {
            ticket.setName(nameStr);
        }


        List<Ticket> tickets = ticketService.findTicketList(ticket, pageInfo, getLoginUser(), isSiteAdmin(), isSupperAdmin());
        List<TicketData> ticketLists = new ArrayList<TicketData>();

        for (Ticket t : tickets) {
            TicketData ticketData = new TicketData();
            ticketData.setId(t.getId());
            ticketData.setName(t.getName());
            ticketData.setOrderCounts(t.getOrderCounts());
            ticketData.setPopCounts(t.getPopCounts());
            ticketData.setShowOrder(t.getShowOrder());
            ticketData.setStatus(t.getStatus());
            ticketData.setUpdateTime(t.getUpdateTime());
            ticketData.setTopId(t.getTopProduct().getId());
            if (t.getId() != t.getTopProduct().getId()) {
                ticketData.setAgentFlag(true);
            } else {
                ticketData.setAgentFlag(false);
            }
            if (t.getTopProduct() != null) {
                ticketData.setCompanyUnitName(t.getTopProduct().getCompanyUnit().getName());
            }

            if (t.getCategory() != null) {
                Category category = categoryService.findById(t.getCategory());
                if (category != null) {
                    ticketData.setCateName(category.getName());
                }

            }
            if (t.getTicketType() != null) {
                ticketData.setTicketTypeName(t.getTicketType().getDescription());
            }


            ticketLists.add(ticketData);
        }

        return datagrid(ticketLists, pageInfo.getTotalCount());
    }

    // 页面跳转
    public Result ticketList() {

        long userId = getLoginUser().getId();
//		linecategorgs = categorgService.getCategoryList(LinecategoryType.service, userId);
        SysUser user = getLoginUser();

//		SysUnit  sysUnit = user.getSysUnit();
//		SysUnit sysUnit = getCompanyUnit();
        SysUnit sysUnit = getCompanyUnit();
        CategoryType categoryType = categoryTypeService.getByType("service");
        linecategorgs = categoryService.listValidByType(categoryType);

//		linecategorgs = lls;

        return dispatch();
    }

    public Result ticketCheckingList() {
        long userId = getLoginUser().getId();
        SysUser user = getLoginUser();
        SysUnit sysUnit = getCompanyUnit();
        CategoryType categoryType = categoryTypeService.getByType("service");
        linecategorgs = categoryService.listValidByType(categoryType);
        return dispatch();
    }

    public Result sailboatCheckingList() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
        long userId = getLoginUser().getId();
        SysUser user = getLoginUser();
        SysUnit sysUnit = getCompanyUnit();
        CategoryType categoryType = categoryTypeService.getByType("service");
        linecategorgs = categoryService.listValidByType(categoryType);
        return dispatch("/WEB-INF/jsp/ticket/sailboat/sailboatCheckingList.jsp");
    }


    public Result doEditProductShowOrder() {

        if (productId == null) {
            simpleResult(map, false, "无效ID！");
            return jsonResult(map);
        }
//        if (showOrder == null) {
//            simpleResult(map, false, "无效序号！");
//            return jsonResult(map);
//        }
        ticket = ticketService.loadTicket(productId);
        ticket.setShowOrder(showOrder);
        ticketService.update(ticket);
        simpleResult(map, true, "操作成功！");
        return jsonResult(map);
    }

    public Result doEditTypeShowOrder() {

        if (ticketPriceId == null) {
            simpleResult(map, false, "无效ID！");
            return jsonResult(map);
        }
//        if (showOrder == null) {
//            simpleResult(map, false, "无效序号！");
//            return jsonResult(map);
//        }
        ticketPrice = ticketPriceService.getPrice(ticketPriceId);
        ticketPrice.setShowOrder(showOrder);
        ticketPriceService.update(ticketPrice);
        simpleResult(map, true, "操作成功！");
        return jsonResult(map);
    }

    public Result doChangeTicketStatus() {
        String idStr = (String) getParameter("idStrs");
        if (StringUtils.isNotBlank(idStr)) {
            ticketService.doChangeStatus(fmtIds(idStr), ticket.getStatus());
            simpleResult(map, true, "");
        } else {
            simpleResult(map, false, "");
        }
        return jsonResult(map);
    }

    public Result doSubChecking() {
        String idStr = (String) getParameter("idStrs");
        if (StringUtils.isNotBlank(idStr)) {
            ticketService.doChangeStatus(fmtIds(idStr), ProductStatus.UP_CHECKING);
            simpleResult(map, true, "");
        } else {
            simpleResult(map, false, "");
        }
        return jsonResult(map);
    }

    public Result doHide() {
        String idStr = (String) getParameter("idStrs");
        if (StringUtils.isNotBlank(idStr)) {
            ticketService.doChangeStatus(fmtIds(idStr), ProductStatus.DOWN);
            simpleResult(map, true, "");
        } else {
            simpleResult(map, false, "");
        }
        return jsonResult(map);
    }


    @AjaxCheck
    public Result checkingSearch() throws ParseException {

        Page pageInfo = new Page(page, rows);


        if (com.zuipin.util.StringUtils.isBlank(sort)) {
            sort = "updateTime";
        }
        if (com.zuipin.util.StringUtils.isBlank(order)) {
            order = "desc";
        }

        List<Ticket> tickets = ticketService.getCheckingList(ticket, getLoginUser(), pageInfo, isSupperAdmin(), sort, order);


        List<TicketData> ticketLists = new ArrayList<TicketData>();

        for (Ticket t : tickets) {
            TicketData ticketData = new TicketData();
            ticketData.setId(t.getId());
            ticketData.setName(t.getName());
            ticketData.setOrderCounts(t.getOrderCounts());
            ticketData.setPopCounts(t.getPopCounts());
            ticketData.setShowOrder(t.getShowOrder());
            ticketData.setStatus(t.getStatus());
            ticketData.setUpdateTime(t.getUpdateTime());
            ticketData.setTopId(t.getTopProduct().getId());
            ticketData.setAddress(t.getAddress());

            if (t.getProductimage() != null && t.getProductimage().size() > 0) {
                ticketData.setImageTotalCount(t.getProductimage().size());
            } else {
                ticketData.setImageTotalCount(0);
            }

            if (t.getOriginId() != null) {
                ticketData.setOriginId(t.getOriginId());
            }

            if (t.getSupplier() != null) {
                ticketData.setSupplierName(t.getSupplier().getUserName());
                ticketData.setSupplierMobile(t.getSupplier().getMobile());
            }
            if (t.getId() != t.getTopProduct().getId()) {
                ticketData.setAgentFlag(true);
            } else {
                ticketData.setAgentFlag(false);
            }
            if (t.getTopProduct() != null) {
                ticketData.setCompanyUnitName(t.getTopProduct().getCompanyUnit().getName());
            }

            if (t.getCategory() != null) {
                Category category = categoryService.findById(t.getCategory());
                if (category != null) {
                    ticketData.setCateName(category.getName());
                }

            }
            if (t.getTicketType() != null) {
                ticketData.setTicketTypeName(t.getTicketType().getDescription());
            }




            ticketLists.add(ticketData);
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(ticketLists, pageInfo.getTotalCount(), jsonConfig);
    }

    public Result sailboatImages() {
        if (productId != null) {
            productimages = productimageService.findAllImagesByProIdAadTarId(productId, null, null, "showOrder", "asc");
        }
        return dispatch("/WEB-INF/jsp/ticket/sailboat/sailboatImages.jsp");
    }

    public Result saveProductImageSort() {
        if (ids.isEmpty()) {
            simpleResult(map, false, "操作失败，无数据提交！");
            return jsonResult(map);
        }
        productimageService.saveProductImageSort(ids);
        simpleResult(map, true, "操作成功！");
        return jsonResult(map);

    }

    public Result sailboatTicketList() {
        return dispatch("/WEB-INF/jsp/ticket/sailboat/sailboatTicketList.jsp");
    }

    public Result ticketView() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
        CategoryType categoryType = categoryTypeService.getByType("service");
        linecategorgs = categoryService.listValidByType(categoryType);
        String ticketId = (String) getParameter("ticketId");
        if (ticketId != null) {
            ticket = ticketService.findTicketById(Long.parseLong(ticketId));
            if (ticket.getId() != ticket.getTopProduct().getId()) {
                ticket.setAgent(true);
            }
            if (com.zuipin.util.StringUtils.isNotBlank(ticket.getTicketImgUrl())) {
                ticket.setTicketImgUrl(QiniuUtil.URL + ticket.getTicketImgUrl());
            } else {
                ticket.setTicketImgUrl("\\images\\no_img.jpg");
            }
            ticketExplain = ticketExplainService.findExplainByTicketId(Long.parseLong(ticketId));
        }
        return dispatch();
    }

    public Result sailboatView() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
        CategoryType categoryType = categoryTypeService.getByType("sailboat");
        linecategorgs = categoryService.listValidByType(categoryType);
        String ticketId = (String) getParameter("ticketId");
        if (ticketId != null) {
            ticket = ticketService.findTicketById(Long.parseLong(ticketId));
            if (ticket.getId() != ticket.getTopProduct().getId()) {
                ticket.setAgent(true);
            }
            if (com.zuipin.util.StringUtils.isNotBlank(ticket.getTicketImgUrl())) {
                ticket.setTicketImgUrl(QiniuUtil.URL + ticket.getTicketImgUrl());
            } else {
                ticket.setTicketImgUrl("\\images\\no_img.jpg");
            }
            ticketExplain = ticketExplainService.findExplainByTicketId(Long.parseLong(ticketId));
        }
        return dispatch("/WEB-INF/jsp/ticket/sailboat/sailboatTicketView.jsp");
    }


    public Result ticketEdit() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
//		long userId = getLoginUser().getId();
        long userId = getLoginUser().getId();
//		linecategorgs = categorgService.getCategoryList(LinecategoryType.service, userId);
        SysUser user = getLoginUser();

//		SysUnit  sysUnit = user.getSysUnit();
        SysUnit sysUnit = getCompanyUnit();
        CategoryType categoryType = categoryTypeService.getByType("service");
        linecategorgs = categoryService.listValidByType(categoryType);


        String tIdStr = (String) getParameter("tId");
        String ticketId = (String) getParameter("ticketId");


        if (ticketId != null) {
//			ticketId = Long.parseLong(tIdStr);
            ticket = ticketService.findTicketById(Long.parseLong(ticketId));
//            ticket.setTicketImgUrl(QiniuUtil.URL + ticket.getTicketImgUrl());
            if (ticket.getId() != ticket.getTopProduct().getId()) {
                ticket.setAgent(true);
            }
            if (com.zuipin.util.StringUtils.isNotBlank(ticket.getTicketImgUrl())) {
                ticket.setTicketImgUrl(QiniuUtil.URL + ticket.getTicketImgUrl());
            } else {
                ticket.setTicketImgUrl(null);
            }
            ticketExplain = ticketExplainService.findExplainByTicketId(Long.parseLong(ticketId));
        }

        return dispatch();
    }

    public Result sailboatTicketEdit() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");
//		long userId = getLoginUser().getId();
        long userId = getLoginUser().getId();
//		linecategorgs = categorgService.getCategoryList(LinecategoryType.service, userId);
        SysUser user = getLoginUser();

//		SysUnit  sysUnit = user.getSysUnit();
        SysUnit sysUnit = getCompanyUnit();
        CategoryType categoryType = categoryTypeService.getByType("sailboat");
        linecategorgs = categoryService.listValidByType(categoryType);


        String tIdStr = (String) getParameter("tId");
        String ticketId = (String) getParameter("ticketId");


        if (ticketId != null) {
//			ticketId = Long.parseLong(tIdStr);
            ticket = ticketService.findTicketById(Long.parseLong(ticketId));
//            ticket.setTicketImgUrl(QiniuUtil.URL + ticket.getTicketImgUrl());
            if (ticket.getId() != ticket.getTopProduct().getId()) {
                ticket.setAgent(true);
            }
            if (com.zuipin.util.StringUtils.isNotBlank(ticket.getTicketImgUrl())) {
                ticket.setTicketImgUrl(QiniuUtil.URL + ticket.getTicketImgUrl());
            } else {
                ticket.setTicketImgUrl(null);
            }
            ticketExplain = ticketExplainService.findExplainByTicketId(Long.parseLong(ticketId));
        }

        return dispatch("/WEB-INF/jsp/ticket/sailboat/sailboatTicketEdit.jsp");
    }

    public Result saveTempTicket() {
        String ticketId = (String) getParameter("ticketId");
        if (ticketId != null) {
            ticket = ticketService.findTicketById(Long.parseLong(ticketId));
            ticId = Long.parseLong(ticketId);
            ticketPrice.setTicket(ticket);
            ticketPrice.setAddTime(new Date());
            ticketPrice.setModifyTime(new Date());
            ticketPrice.setUserid(ticket.getUser().getId());
            ticketPriceService.save(ticketPrice);

            map.put("ticketPriceId", ticketPrice.getId());
            simpleResult(map, true, "");
            return jsonResult(map);
        }
        simpleResult(map, false, "");
        return jsonResult(map);
    }

    /**
     * @return
     * @throws ParseException
     * @author zhoudy
     */
    public Result ticketEditOffer() throws ParseException {
        fgDomain = propertiesManager.getString("FG_DOMAIN");


        if (ticketId == null) {
            ticketPrice = ticketPriceService.getPrice(ticketPriceId);
//			Date endTime = datepriceService.findEndDate(ticketPrice);
//			List<TicketDateprice> ticketDateprices = datepriceService.findAvailableByType(ticketPrice);
        } else if (ticketPriceId != null) {
            ticketPrice = ticketPriceService.getPrice(ticketPriceId);
            ticket = ticketService.findTicketById(ticketId);
            boolean isAgent = true;
            ticket.setAgent(isAgent);
            ticketPrice.setTicket(ticket);
        } else {
            ticket = ticketService.findTicketById(ticketId);
            ticketPrice = new TicketPrice();
            ticketPrice.setTicket(ticket);
        }

        dateStartStr = DateUtils.format(DateUtils.getStartDay(new Date(), 1), "yyyy-MM-dd");

        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.MONTH, 6);
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        endDate = calendar.getTime();
        dateEndStr = DateUtils.format(endDate, "yyyy-MM-dd");

        return dispatch();
    }

    public Result sailboatTicketEditOffer() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");


        if (ticketId == null) {
            ticketPrice = ticketPriceService.getPrice(ticketPriceId);
//			Date endTime = datepriceService.findEndDate(ticketPrice);
//			List<TicketDateprice> ticketDateprices = datepriceService.findAvailableByType(ticketPrice);
        } else if (ticketPriceId != null) {
            ticketPrice = ticketPriceService.getPrice(ticketPriceId);
            ticket = ticketService.findTicketById(ticketId);
            boolean isAgent = true;
            ticket.setAgent(isAgent);
            ticketPrice.setTicket(ticket);
        } else {
            ticket = ticketService.findTicketById(ticketId);
            ticketPrice = new TicketPrice();
            ticketPrice.setTicket(ticket);
        }

        dateStartStr = DateUtils.format(DateUtils.getStartDay(new Date(), 1), "yyyy-MM-dd");

        Date endDate = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(endDate);
        calendar.add(Calendar.MONTH, 3);
        calendar.add(Calendar.DAY_OF_MONTH, 0);
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        endDate = calendar.getTime();
        dateEndStr = DateUtils.format(endDate, "yyyy-MM-dd");

        return dispatch("/WEB-INF/jsp/ticket/sailboat/sailboatTicketEditOffer.jsp");
    }

    public Result lastSave() {

        String ticketId = (String) getParameter("ticketId");

        Long tId = ticketService.update(ticketId, ticket.getPayway(), ticket.getOrderConfirm());

        Map<String, Object> mmp = new HashMap<String, Object>();

        mmp.put("tId", tId);
        simpleResult(mmp, true, "");
        return jsonResult(mmp);
    }

    /**
     * 新增线路向导页面
     *
     * @return
     * @author caiys
     * @date 2015年10月14日
     */
    public Result addWizard() {

        String tId = (String) getParameter("ticketId");

        if (tId != null && !"".equals(tId)) {
            ticId = Long.parseLong(tId);
        }

        return dispatch();
    }

    public Result sailboatAddWizard() {
        String tId = (String) getParameter("ticketId");
        if (tId != null && !"".equals(tId)) {
            ticId = Long.parseLong(tId);
        }
        return dispatch("/WEB-INF/jsp/ticket/sailboat/sailboatAddWizard.jsp");
    }

    /**
     * 新增线路向导页面
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午3:03:54
     */
    public Result ticketEditOfferList() {

        fgDomain = propertiesManager.getString("FG_DOMAIN");

        String ticketId = (String) getParameter("ticketId");

        if (ticketId == null) {
            text("ticketId 为空");
        }

//		Long tid = ticketId;
        Long tpid = ticketPriceId;

        ticket = ticketService.findTicketById(Long.parseLong(ticketId));
        if (ticket.getId() != ticket.getTopProduct().getId()) {
            ticket.setAgent(true);
        } else {
            ticket.setAgent(false);
        }


        return dispatch();
    }

    public Result sailboatTicketEditOfferList() {
        fgDomain = propertiesManager.getString("FG_DOMAIN");

        String ticketId = (String) getParameter("ticketId");

        if (ticketId == null) {
            text("ticketId 为空");
        }

//		Long tid = ticketId;
        Long tpid = ticketPriceId;

        ticket = ticketService.findTicketById(Long.parseLong(ticketId));
        if (ticket.getId() != ticket.getTopProduct().getId()) {
            ticket.setAgent(true);
        } else {
            ticket.setAgent(false);
        }


        return dispatch("/WEB-INF/jsp/ticket/sailboat/sailboatTicketEditOfferList.jsp");
    }

    /**
     * 新增线路向导页面
     *
     * @return
     * @author caiys
     * @date 2015年10月14日 下午3:03:54
     */
    public Result ticketPublishSuccess() {

        String tId = (String) getParameter("ticketId");

        if (tId != null) {
            ticket = ticketService.loadTicket(Long.parseLong(tId));
        }

        return dispatch();
    }

    public Result sailboatTicketPublishSuccess() {
        String tId = (String) getParameter("ticketId");

        if (tId != null) {
            ticket = ticketService.loadTicket(Long.parseLong(tId));
        }

        return dispatch("/WEB-INF/jsp/ticket/sailboat/sailboatTicketPublishSuccess.jsp");
    }

    public Result indexTickets() {

        ticketService.indexTickets();
        return text("indexTickets");
    }

    public Result facetTickets() throws SolrServerException {

//		ticketService.query();
        return text("facetTickets");
    }

    public Result queryTickets() throws SolrServerException, UnsupportedEncodingException {
        String q = (String) getParameter("q");
//		ticketService.queryLine(q);
        return text("facetLines");
    }


    public Result ticketLabelList() {
        Page pageInfo = new Page(page, rows);
        String name = (String) getParameter("name");
        String cityId = (String) getParameter("cityId");
        String labelId = (String) getParameter("labelId");
        String type = (String) getParameter("type");
        String tagIds = (String) getParameter("tagIds");
        TbArea area = null;
        Ticket info = new Ticket();
        if (StringUtils.isNotBlank(cityId)) {
            area = tbareaService.getArea(Long.parseLong(cityId));
        }
        if (StringUtils.isNotBlank(name)) {
            info.setName(name);
        }
        List<Ticket> scenics = new ArrayList<Ticket>();
        List<TicketLabel> scenicLabels = new ArrayList<TicketLabel>();
        if ("TICKET".equals(type)) {
            List<TicketType> ticketTypes = new ArrayList<TicketType>();
            ticketTypes.add(TicketType.scenic);
            info.setIncludeTicketTypeList(ticketTypes);
            scenics = ticketService.getTicketLabels(info, area, tagIds, pageInfo, getLoginUser(), isSiteAdmin(), isSupperAdmin());
            for (Ticket sInfo : scenics) {
                TicketLabel slabel = new TicketLabel();

                slabel.setId(sInfo.getId());
                slabel.setName(sInfo.getName());
                slabel.setUpdateTime(DateUtils.format(sInfo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
                if (sInfo.getCityId() != null) {
                    slabel.setCityId(sInfo.getCityId());
                    slabel.setCityName(tbareaService.getArea(sInfo.getCityId()).getFullPath());
                }


                List<LabelItem> items = labelItemService.findItemByTargId(sInfo.getId(), TargetType.TICKET);

                List<String> labelNames = new ArrayList<String>();
                List<Integer> itemSorts = new ArrayList<Integer>();
                List<Long> itemIds = new ArrayList<Long>();
                List<Long> labIds = new ArrayList<Long>();
                for (LabelItem it : items) {
                    if ((it.getTargetId()).equals(sInfo.getId())) {
                        if (StringUtils.isNotBlank(labelId)) {
                            Long lId = Long.parseLong(labelId);
                            List<Label> labels = labelService.findLabelsByParent(lId);
                            if (labels.size() > 0) {
                                for (Label la : labels) {
                                    if ((la.getId()).equals(it.getLabel().getId())) {
                                        slabel.setSort(it.getOrder());
                                        itemSorts.add(it.getOrder());
                                        labelNames.add(it.getLabel().getName());
                                        itemIds.add(it.getId());
                                        labIds.add(it.getLabel().getId());
                                    }
                                }

                            } else {
                                if (lId.equals(it.getLabel().getId())) {
                                    slabel.setSort(it.getOrder());
                                    itemSorts.add(it.getOrder());
                                    labelNames.add(it.getLabel().getName());
                                    itemIds.add(it.getId());
                                    labIds.add(it.getLabel().getId());
                                }
                            }

                        } else {
                            slabel.setSort(it.getOrder());
                            itemSorts.add(it.getOrder());
                            labelNames.add(it.getLabel().getName());
                            itemIds.add(it.getId());
                            labIds.add(it.getLabel().getId());
                        }
                    }

                }

                slabel.setLabelNames(labelNames);
                slabel.setItemSort(itemSorts);
                slabel.setLabelItems(itemIds);
                slabel.setLabelIds(labIds);
                scenicLabels.add(slabel);
            }
        }

        return datagrid(scenicLabels, pageInfo.getTotalCount());
    }

    public Result yhyTicketLabelList() {
        Page pageInfo = new Page(page, rows);
        String name = (String) getParameter("name");
        String labelId = (String) getParameter("labelId");
        String type = (String) getParameter("type");
        String tagIds = (String) getParameter("tagIds");
        TbArea area = tbareaService.getArea(350200L);
        Ticket info = new Ticket();

        if (StringUtils.isNotBlank(name)) {
            info.setName(name);
        }
        List<Ticket> scenics = new ArrayList<Ticket>();
        List<TicketLabel> scenicLabels = new ArrayList<TicketLabel>();
        if ("TICKET".equals(type)) {
            List<TicketType> ticketTypes = new ArrayList<TicketType>();
            ticketTypes.add(TicketType.scenic);
            info.setIncludeTicketTypeList(ticketTypes);
            scenics = ticketService.getTicketLabels(info, area, tagIds, pageInfo, getLoginUser(), isSiteAdmin(), isSupperAdmin());
            for (Ticket sInfo : scenics) {
                TicketLabel slabel = new TicketLabel();

                slabel.setId(sInfo.getId());
                slabel.setName(sInfo.getName());
                slabel.setUpdateTime(DateUtils.format(sInfo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
                if (sInfo.getCityId() != null) {
                    slabel.setCityId(sInfo.getCityId());
                    slabel.setCityName(tbareaService.getArea(sInfo.getCityId()).getFullPath());
                }


                List<LabelItem> items = labelItemService.findItemByTargId(sInfo.getId(), TargetType.TICKET);

                List<String> labelNames = new ArrayList<String>();
                List<Integer> itemSorts = new ArrayList<Integer>();
                List<Long> itemIds = new ArrayList<Long>();
                List<Long> labIds = new ArrayList<Long>();
                for (LabelItem it : items) {
                    if ((it.getTargetId()).equals(sInfo.getId())) {
                        if (StringUtils.isNotBlank(labelId)) {
                            Long lId = Long.parseLong(labelId);
                            List<Label> labels = labelService.findLabelsByParent(lId);
                            if (labels.size() > 0) {
                                for (Label la : labels) {
                                    if ((la.getId()).equals(it.getLabel().getId())) {
                                        slabel.setSort(it.getOrder());
                                        itemSorts.add(it.getOrder());
                                        labelNames.add(it.getLabel().getName());
                                        itemIds.add(it.getId());
                                        labIds.add(it.getLabel().getId());
                                    }
                                }

                            } else {
                                if (lId.equals(it.getLabel().getId())) {
                                    slabel.setSort(it.getOrder());
                                    itemSorts.add(it.getOrder());
                                    labelNames.add(it.getLabel().getName());
                                    itemIds.add(it.getId());
                                    labIds.add(it.getLabel().getId());
                                }
                            }

                        } else {
                            slabel.setSort(it.getOrder());
                            itemSorts.add(it.getOrder());
                            labelNames.add(it.getLabel().getName());
                            itemIds.add(it.getId());
                            labIds.add(it.getLabel().getId());
                        }
                    }

                }

                slabel.setLabelNames(labelNames);
                slabel.setItemSort(itemSorts);
                slabel.setLabelItems(itemIds);
                slabel.setLabelIds(labIds);
                scenicLabels.add(slabel);
            }
        }

        return datagrid(scenicLabels, pageInfo.getTotalCount());
    }

    public Result yhySailboatLabelList() {
        Page pageInfo = new Page(page, rows);
        String name = (String) getParameter("name");
        String labelId = (String) getParameter("labelId");
        String type = (String) getParameter("type");
        String tagIds = (String) getParameter("tagIds");
        TbArea area = tbareaService.getArea(350200L);
        Ticket info = new Ticket();

        if (StringUtils.isNotBlank(name)) {

            info.setName(name);
        }
        List<Ticket> scenics = new ArrayList<Ticket>();
        List<TicketLabel> scenicLabels = new ArrayList<TicketLabel>();
        if ("SAILBOAT".equals(type)) {
            List<TicketType> ticketTypes = new ArrayList<TicketType>();
            ticketTypes.add(TicketType.sailboat);
            ticketTypes.add(TicketType.yacht);
            info.setIncludeTicketTypeList(ticketTypes);
            scenics = ticketService.getTicketLabels(info, area, tagIds, pageInfo, getLoginUser(), isSiteAdmin(), isSupperAdmin());
            for (Ticket sInfo : scenics) {
                TicketLabel slabel = new TicketLabel();
                slabel.setId(sInfo.getId());
                slabel.setName(sInfo.getName());
                slabel.setUpdateTime(DateUtils.format(sInfo.getUpdateTime(), "yyyy-MM-dd HH:mm:ss"));
                if (sInfo.getCityId() != null) {
                    slabel.setCityId(sInfo.getCityId());
                    slabel.setCityName(tbareaService.getArea(sInfo.getCityId()).getFullPath());
                }


                List<LabelItem> items = labelItemService.findItemByTargId(sInfo.getId(), TargetType.SAILBOAT);

                List<String> labelNames = new ArrayList<String>();
                List<Integer> itemSorts = new ArrayList<Integer>();
                List<Long> itemIds = new ArrayList<Long>();
                List<Long> labIds = new ArrayList<Long>();
                for (LabelItem it : items) {
                    if ((it.getTargetId()).equals(sInfo.getId())) {
                        if (StringUtils.isNotBlank(labelId)) {
                            Long lId = Long.parseLong(labelId);
                            List<Label> labels = labelService.findLabelsByParent(lId);
                            if (labels.size() > 0) {
                                for (Label la : labels) {
                                    if ((la.getId()).equals(it.getLabel().getId())) {
                                        slabel.setSort(it.getOrder());
                                        itemSorts.add(it.getOrder());
                                        labelNames.add(it.getLabel().getName());
                                        itemIds.add(it.getId());
                                        labIds.add(it.getLabel().getId());
                                    }
                                }

                            } else {
                                if (lId.equals(it.getLabel().getId())) {
                                    slabel.setSort(it.getOrder());
                                    itemSorts.add(it.getOrder());
                                    labelNames.add(it.getLabel().getName());
                                    itemIds.add(it.getId());
                                    labIds.add(it.getLabel().getId());
                                }
                            }

                        } else {
                            slabel.setSort(it.getOrder());
                            itemSorts.add(it.getOrder());
                            labelNames.add(it.getLabel().getName());
                            itemIds.add(it.getId());
                            labIds.add(it.getLabel().getId());
                        }
                    }

                }

                slabel.setLabelNames(labelNames);
                slabel.setItemSort(itemSorts);
                slabel.setLabelItems(itemIds);
                slabel.setLabelIds(labIds);
                scenicLabels.add(slabel);
            }
        }

        return datagrid(scenicLabels, pageInfo.getTotalCount());
    }


    public String getJson() {
        return json;
    }

    public void setJson(String json) {
        this.json = json;
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


    public List<Category> getLinecategorgs() {
        return linecategorgs;
    }


    public void setLinecategorgs(List<Category> linecategorgs) {
        this.linecategorgs = linecategorgs;
    }


    public List<TbArea> getFatherAreas() {
        return fatherAreas;
    }


    public void setFatherAreas(List<TbArea> fatherAreas) {
        this.fatherAreas = fatherAreas;
    }

    public TicketForm getTicketForm() {
        return ticketForm;
    }

    public void setTicketForm(TicketForm ticketForm) {
        this.ticketForm = ticketForm;
    }

    public String getDateStartStr() {
        return dateStartStr;
    }

    public void setDateStartStr(String dateStartStr) {
        this.dateStartStr = dateStartStr;
    }

    public String getDateEndStr() {
        return dateEndStr;
    }

    public void setDateEndStr(String dateEndStr) {
        this.dateEndStr = dateEndStr;
    }

    public Long getTicketPriceId() {
        return ticketPriceId;
    }

    public void setTicketPriceId(Long ticketPriceId) {
        this.ticketPriceId = ticketPriceId;
    }

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public TicketPrice getTicketPrice() {
        return ticketPrice;
    }

    public void setTicketPrice(TicketPrice ticketPrice) {
        this.ticketPrice = ticketPrice;
    }

    public TicketExplain getTicketExplain() {
        return ticketExplain;
    }

    public void setTicketExplain(TicketExplain ticketExplain) {
        this.ticketExplain = ticketExplain;
    }


    public Long getTicId() {
        return ticId;
    }


    public void setTicId(Long ticId) {
        this.ticId = ticId;
    }


    public String getPriceStr() {
        return priceStr;
    }


    public void setPriceStr(String priceStr) {
        this.priceStr = priceStr;
    }

    public List<TicketType> getFilterTypes() {
        return filterTypes;
    }

    public void setFilterTypes(List<TicketType> filterTypes) {
        this.filterTypes = filterTypes;
    }

    public static Date getNowDate(Date date) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(date);
        ParsePosition pos = new ParsePosition(8);
        Date currentTim = formatter.parse(dateString, pos);
        return currentTim;
    }

    public long getCateId() {
        return cateId;
    }

    public void setCateId(long cateId) {
        this.cateId = cateId;
    }

    public String getAgent() {
        return agent;
    }

    public void setAgent(String agent) {
        this.agent = agent;
    }

    public String getFgDomain() {
        return fgDomain;
    }

    public void setFgDomain(String fgDomain) {
        this.fgDomain = fgDomain;
    }

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public QuantitySales getQuantitySales() {
        return quantitySales;
    }

    public void setQuantitySales(QuantitySales quantitySales) {
        this.quantitySales = quantitySales;
    }

    private String formatDay(Date date) {
        java.text.SimpleDateFormat dateFormater = new java.text.SimpleDateFormat("yyyy-MM-dd");
        return dateFormater.format(date);
    }

    private Date getDate(String dateString) {
        java.text.SimpleDateFormat dateFormater = new java.text.SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormater.parse(dateString);
        } catch (ParseException e) {
            return null;
        }
    }

    public List<Long> fmtIds(String idStr) {

        String[] idStrs = idStr.split(",");

        for (String id : idStrs) {
            ids.add(Long.parseLong(id));
        }
        return ids;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public List<TicketPriceCalendarVo> getPriceCalendarVos() {
        return priceCalendarVos;
    }

    public void setPriceCalendarVos(List<TicketPriceCalendarVo> priceCalendarVos) {
        this.priceCalendarVos = priceCalendarVos;
    }

    public List<Productimage> getProductimages() {
        return productimages;
    }

    public void setProductimages(List<Productimage> productimages) {
        this.productimages = productimages;
    }

    public List<TicketPriceTypeExtend> getPriceTypeExtends() {
        return priceTypeExtends;
    }

    public void setPriceTypeExtends(List<TicketPriceTypeExtend> priceTypeExtends) {
        this.priceTypeExtends = priceTypeExtends;
    }

    public List<Productimage> getImgList() {
        return imgList;
    }

    public void setImgList(List<Productimage> imgList) {
        this.imgList = imgList;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
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

    public List<Long> getIds() {
        return ids;
    }

    public void setIds(List<Long> ids) {
        this.ids = ids;
    }
}
