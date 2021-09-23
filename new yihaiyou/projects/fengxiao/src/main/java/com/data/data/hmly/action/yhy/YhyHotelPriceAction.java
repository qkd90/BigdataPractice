package com.data.data.hmly.action.yhy;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.action.hotel.vo.HotelPriceCalendarVo;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ShowStatus;
import com.data.data.hmly.service.contract.ContractService;
import com.data.data.hmly.service.contract.entity.Contract;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.HotelRoomService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelPriceCalendar;
import com.data.data.hmly.service.hotel.entity.HotelRoom;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.framework.hibernate.util.Page;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.BeanUtils;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.lang.reflect.Field;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2016/11/21.
 */
public class YhyHotelPriceAction extends FrameBaseAction {

    private Map<String, Object> result = new HashMap<String, Object>();

    private Long id;
    private HotelPrice hotelPrice = new HotelPrice();
    private Productimage productimage = new Productimage();
    private List<Productimage> imgList = new ArrayList<Productimage>();
    private String roomNum;
    private Long productId;
    private Long hotelPriceId;
    private List<HotelPriceCalendarVo> priceCalendarVos = new ArrayList<HotelPriceCalendarVo>();

    private Integer draw;
    private Integer start = 0;
    private Integer length = 10;
    private String sort;
    private String order;

    @Resource
    private HotelService hotelService;
    @Resource
    private HotelPriceService hotelPriceService;
    @Resource
    private HotelRoomService hotelRoomService;
    @Resource
    private ProductimageService productimageService;
    @Resource
    private ContractService contractService;

    public Result toHotelRoomType() {
        if (id != null) {
            ShowStatus showStatus = hotelPriceService.getShowStatus(id);
            if (ShowStatus.HIDE_FOR_CHECK.equals(showStatus)) {
                return redirect("/yhy/yhyMain/toHomeStayPriceList.jhtml");
            }
            getSession().setAttribute("editPriceId", id);
        } else {
            getSession().removeAttribute("editPriceId");
        }
        return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_roomType.jsp");
    }

    public Result toHotelRoomTypeDetail() {
        if (id != null) {
            PriceStatus status = hotelPriceService.getPriceStatus(id);
            if (status != null && !PriceStatus.DEL.equals(status)) {
                getSession().setAttribute("hotelPriceId", id);
                return dispatch("/WEB-INF/jsp/yhy/yhyHotel/homestay_roomType_detail.jsp");
            }
        }
        getSession().removeAttribute("hotelPriceId");
        return redirect("/yhy/yhyMain/toHomeStayPriceList.jhtml");
    }

    public Result getYhyHotelPriceInfo() {
        if (id != null) {
            // hotel price info
            HotelPrice hotelPrice = hotelPriceService.get(id);
            String roomNumbers = hotelRoomService.getRoomNumbers(hotelPrice.getHotel().getId(), hotelPrice.getId());
            List<Productimage> productimageList = productimageService.findAllImagesByProIdAadTarId(hotelPrice.getHotel().getId(), hotelPrice.getId(), "hotel/hotelRoom/");
            // put image data
            result.put("imgData", productimageList);
            result.put("roomNumbers", roomNumbers);
            try {
                Field[] priceFields = hotelPrice.getClass().getDeclaredFields();
                for (Field field : priceFields) {
                    field.setAccessible(true);
                    result.put("hotelPrice." + field.getName(), field.get(hotelPrice));
                }
                result.put("hotel.id", hotelPrice.getHotel().getId());
            } catch (IllegalAccessException e) {
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
        result.put("msg", "房型id错误!");
        return json(JSONObject.fromObject(result));
    }


    public Result getYhyHotelPriceList() {
        SysUser loginUser = getLoginUser();
        Integer pageIndex = start / length + 1;
        Page page = new Page(pageIndex, length);
        List<HotelPrice> priceList = hotelPriceService.findByHotel(hotelPrice, loginUser.getSysUnit().getCompanyUnit(), page, sort, order);
        for (HotelPrice hotelPrice : priceList) {
            List<HotelRoom> roomList = hotelRoomService.getRoomList(hotelPrice.getHotel().getId(), hotelPrice.getId());
            hotelPrice.setRoomNum(hotelRoomService.getRoomNums(roomList));
            hotelPrice.setRoomAmount(roomList.size());
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        result.put("draw", draw);
        result.put("recordsTotal", page.getTotalCount());
        result.put("recordsFiltered", page.getTotalCount());
        result.put("data", priceList);
        return json(JSONObject.fromObject(result, jsonConfig));
    }

    public Result savePriceInfo() {
        SysUser loginUser = getLoginUser();
        // source hotel data
        Hotel sourceHotel = hotelService.get(productId);
        if (sourceHotel != null && !ProductStatus.DEL.equals(sourceHotel.getStatus())) {
            String[] roomNumbers = roomNum.split(",");
            if (hotelPrice.getId() != null) {
                PriceStatus status = hotelPriceService.getPriceStatus(hotelPrice.getId());
                if (hotelPrice.getOriginId() == null && PriceStatus.UP.equals(status)) {
                    // source hotel price data
                    HotelPrice sourceHotelPrice = hotelPriceService.get(hotelPrice.getId());
                    sourceHotelPrice.setShowStatus(ShowStatus.HIDE_FOR_CHECK);
                    sourceHotelPrice.setModifyTime(new Date());
                    // create temp hotel price data
                    HotelPrice tempHotelPrice = new HotelPrice();
                    // copy source hotel price info
                    BeanUtils.copyProperties(sourceHotelPrice, tempHotelPrice, false, null, "id");
                    // copy update hotel price info
                    BeanUtils.copyProperties(hotelPrice, tempHotelPrice, false, null, "id");
                    // mark origin hotel price info
                    tempHotelPrice.setOriginId(sourceHotelPrice.getId());
                    tempHotelPrice.setShowStatus(ShowStatus.SHOW);
                    tempHotelPrice.setStatus(PriceStatus.UP_CHECKING);
                    tempHotelPrice.setCreateTime(new Date());
                    // update source hotel price data
                    hotelPriceService.update(sourceHotelPrice);
                    // save temp hotel price data
                    hotelPriceService.save(tempHotelPrice);
                    // handle room numbers
                    hotelRoomService.doSaveHotelRooms(roomNumbers, tempHotelPrice);   //新增房型房间号
                    // handle product images
                    // del old UP_CHECKING img
//                    productimageService.delImgByUpChecking(sourceHotel.getId(), tempHotelPrice.getId(), ProductStatus.UP_CHECKING, "hotel/hotelRoom/");
                    productimageService.delImages(sourceHotel.getId(), tempHotelPrice.getId(), "hotel/hotelRoom/");
                    for (Productimage productimage : imgList) {
                        productimage.setId(null);
                        productimage.setProduct(sourceHotel);
                        productimage.setTargetId(tempHotelPrice.getId());
                        productimage.setCreateTime(new Date());
                        if (productimage.getShowOrder() == null) {
                            productimage.setShowOrder(999);
                        }
                        productimage.setCompanyUnitId(loginUser.getSysUnit().getCompanyUnit().getId());
                    }
                    productimageService.saveAll(imgList);
                } else if (hotelPrice.getOriginId() != null || !PriceStatus.UP.equals(status)) {
                    // edit temp hotel price data
                    HotelPrice tempHotelPrice = hotelPriceService.get(hotelPrice.getId());
//
                    // copy update hotel price info to temp hotel price
                    BeanUtils.copyProperties(hotelPrice, tempHotelPrice, false, null, "id");
                    tempHotelPrice.setStatus(PriceStatus.UP_CHECKING);
                    tempHotelPrice.setModifyTime(new Date());
                    // update temp hotel price
                    hotelPriceService.update(tempHotelPrice);
                    // handle room numbers
//                    hotelRoomService.doHandleRoomNum(roomNumbers, sourceHotelPrice);
                    hotelRoomService.doDelHotelRoomNum(tempHotelPrice);   //删除原始房型房间号
                    hotelRoomService.doSaveHotelRooms(roomNumbers, tempHotelPrice); //新增临时房型房间号
                    // handle product images
                    // del old UP_CHECKING img
//                    productimageService.delImgByUpChecking(sourceHotel.getId(), tempHotelPrice.getId(), ProductStatus.UP_CHECKING, "hotel/hotelRoom/");
                    productimageService.delImages(sourceHotel.getId(), tempHotelPrice.getId(), "hotel/hotelRoom/");
                    for (Productimage productimage : imgList) {
                        productimage.setId(null);
                        productimage.setProduct(sourceHotel);
                        productimage.setTargetId(tempHotelPrice.getId());
                        productimage.setCreateTime(new Date());
                        if (productimage.getShowOrder() == null) {
                            productimage.setShowOrder(999);
                        }
                        productimage.setCompanyUnitId(loginUser.getSysUnit().getCompanyUnit().getId());
                    }
                    productimageService.saveAll(imgList);
                }
            } else {
                // new hotel price data
                hotelPrice.setHotel(sourceHotel);
                hotelPrice.setStatus(PriceStatus.UP_CHECKING);
                hotelPrice.setShowStatus(ShowStatus.SHOW);
                hotelPrice.setCreateTime(new Date());
                hotelPrice.setModifyTime(new Date());
                //save hotel price info
                hotelPriceService.save(hotelPrice);
                // handle room numbers
                hotelRoomService.doSaveHotelRooms(roomNumbers, hotelPrice); //新增临时房型房间号
                // handle product image
                int index = 1;
                for (Productimage productimage : imgList) {
                    productimage.setId(null);
                    productimage.setProduct(sourceHotel);
                    productimage.setTargetId(hotelPrice.getId());
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

    public Result getTypePriceDate() {
        SysUser loginUser = getLoginUser();
        // 参数
        String hotelPriceId = (String) getParameter("hotelPriceId");
        String startDateStr = (String) getParameter("startDate");
        String endDateStr = (String) getParameter("endDate");
        Date startDate = null;
        Date endDate = null;
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (StringUtils.isNotBlank(hotelPriceId)) {
            hotelPrice = hotelPriceService.get(Long.parseLong(hotelPriceId));
            Hotel hotel = hotelService.get(hotelPrice.getHotel().getId());
            // 获取计价模式
            Contract contract = contractService.getContractByCompanyB(hotel.getCompanyUnit());
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
            List<HotelPriceCalendar> hotelPriceCalendars = hotelPriceService.findTypePriceDate(Long.valueOf(hotelPriceId), startDate, null);
            // 返回页面数据格式
            DecimalFormat df = new DecimalFormat("0.00");
            for (HotelPriceCalendar pd : hotelPriceCalendars) {	// {id:vid,discountPrice:discountPrice,title:'优惠价¥'+discountPrice,start:LineUtil.dateToString(date,'yyyy-MM-dd')}
                if (pd.getMember() != null) {
                    Map<String, String> map = new HashMap<String, String>();
                    Date day = pd.getDate();
                    map.put("id", "1" + day.getTime());
                    map.put("member", pd.getMember().toString());
                    map.put("title", "销售价：" + pd.getMember());
                    map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
                    list.add(map);
                }
//                if (pd.getMarketPrice() != null) {
//                    Map<String, String> map = new HashMap<String, String>();
//                    Date day = pd.getDate();
//                    map.put("id", "2" + day.getTime());
//                    map.put("marketPrice", pd.getMarketPrice().toString());
//                    map.put("title", "a.市价：" + pd.getMarketPrice());
//                    map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
//                    list.add(map);
//                }
                if (pd.getCost() != null) {
                    Map<String, String> map = new HashMap<String, String>();
                    Date day = pd.getDate();
                    map.put("id", "2" + day.getTime());
                    map.put("cost", pd.getCost().toString());
                    map.put("title", "结算价：" + pd.getCost());
                    map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
                    list.add(map);
                }
                if (pd.getInventory() != null) {
                    Map<String, String> map = new HashMap<String, String>();
                    Date day = pd.getDate();
                    map.put("id", "3" + day.getTime());
                    map.put("inventory", pd.getInventory().toString());
                    map.put("title", "库存：" + pd.getInventory());
                    map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
                    list.add(map);
                }
            }
            result.put("success", true);
            result.put("msg", "");
            result.put("data", list);
        } else {
            result.put("success", false);
            result.put("msg", "民宿房型id错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result saveTypePriceDate() {
        if (hotelPriceId != null && productId != null) {
            HotelPrice sourceHotelPrice = hotelPriceService.get(hotelPriceId);
            List<HotelPriceCalendar> priceCalendarList = new ArrayList<HotelPriceCalendar>();
            for (HotelPriceCalendarVo priceCalendarVo : priceCalendarVos) {
                HotelPriceCalendar priceCalendar = new HotelPriceCalendar();
                priceCalendar.setHotelPrice(sourceHotelPrice);
                priceCalendar.setHotelId(productId);
                priceCalendar.setDate(DateUtils.getDate(priceCalendarVo.getDate(), "yyyy-MM-dd"));
                priceCalendar.setMember(priceCalendarVo.getMember());
                priceCalendar.setCost(priceCalendarVo.getCost());
                priceCalendar.setInventory(priceCalendarVo.getInventory());
                priceCalendar.setStatus(true);
                priceCalendar.setCreateTime(new Date());
                priceCalendarList.add(priceCalendar);
            }
            // delete old price calendar data
            hotelPriceService.delPriceDate(sourceHotelPrice);
            // save new price calendar data
            hotelPriceService.saveCalendarList(priceCalendarList);
            // update hotel price info
            Date start = hotelPriceService.getMinDateByPrice(sourceHotelPrice);
            Date end = hotelPriceService.getMaxDateByPrice(sourceHotelPrice);
            // 明天起, 最低价
            Float price = hotelPriceService.findMinPriceByType(productId, sourceHotelPrice, null);
            sourceHotelPrice.setStart(start);
            sourceHotelPrice.setEnd(end);
            sourceHotelPrice.setPrice(price);
            sourceHotelPrice.setModifyTime(new Date());
            hotelPriceService.update(sourceHotelPrice);
            hotelService.indexHotel(sourceHotelPrice.getHotel());
            result.put("success", true);
            result.put("msg", "");
        } else {
            result.put("success", false);
            result.put("msg", "房型或产品信息错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result revokeHotelPrice() {
        if (id != null) {
            HotelPrice hotelPrice = hotelPriceService.get(id);
            if (hotelPrice != null) {
                PriceStatus status = hotelPrice.getStatus();
                Long originId = hotelPrice.getOriginId();
                if (PriceStatus.UP_CHECKING.equals(status) || PriceStatus.REFUSE.equals(status)) {
                    // temp data
                    if (originId != null) {
                        // delete temp data
                        hotelPriceService.delete(hotelPrice);
                        // update origin data
                        HotelPrice sourceHotelPrice = hotelPriceService.get(originId);
                        Hotel sourceHotel = sourceHotelPrice.getHotel();
                        sourceHotelPrice.setShowStatus(ShowStatus.SHOW);
                        // handle room numbers
                        hotelRoomService.doDelHotelRoomNum(hotelPrice);
                        // handle product images
                        productimageService.delImages(sourceHotel.getId(), hotelPrice.getId(), "hotel/hotelRoom/");
//                    productimageService.delImgByUpChecking(sourceHotel.getId(), hotelPrice.getId(), ProductStatus.UP_CHECKING, "hotel/hotelRoom/");
                    } else {
                        // update source data
                        // back to DOWN status
                        hotelPrice.setStatus(PriceStatus.DOWN);
                        hotelPriceService.update(hotelPrice);
                    }
                    result.put("success", true);
                    result.put("msg", "操作成功!");
                } else {
                    result.put("success", false);
                    result.put("msg", "房型状态错误! 操作失败!");
                }
            } else {
                result.put("success", false);
                result.put("msg", "民宿房型id错误! 请检查房型状态!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "民宿房型id错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result downHotelPrice() {
        if (id != null) {
            HotelPrice hotelPrice = hotelPriceService.get(id);
            if (hotelPrice != null && PriceStatus.UP.equals(hotelPrice.getStatus())) {
                // update status
                hotelPrice.setStatus(PriceStatus.DOWN);
                hotelPriceService.update(hotelPrice);
                result.put("success", true);
                result.put("msg", "操作成功!");
            } else {
                result.put("success", false);
                result.put("msg", "房型不存在或状态错误! 操作失败!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "民宿房型id错误!");
        }
        return json(JSONObject.fromObject(result));
    }

    public Result delHotelPrice() {
        if (id != null) {
            HotelPrice hotelPrice = hotelPriceService.get(id);
            if (hotelPrice != null && PriceStatus.DOWN.equals(hotelPrice.getStatus())) {
                // update status
                hotelPrice.setStatus(PriceStatus.DEL);
                hotelPriceService.update(hotelPrice);
                result.put("success", true);
                result.put("msg", "操作成功!");
            } else {
                result.put("success", false);
                result.put("msg", "房型不存在或状态错误! 操作失败!");
            }
        } else {
            result.put("success", false);
            result.put("msg", "民宿房型id错误!");
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

    public HotelPrice getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(HotelPrice hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public Productimage getProductimage() {
        return productimage;
    }

    public void setProductimage(Productimage productimage) {
        this.productimage = productimage;
    }

    public List<Productimage> getImgList() {
        return imgList;
    }

    public void setImgList(List<Productimage> imgList) {
        this.imgList = imgList;
    }

    public String getRoomNum() {
        return roomNum;
    }

    public void setRoomNum(String roomNum) {
        this.roomNum = roomNum;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getHotelPriceId() {
        return hotelPriceId;
    }

    public void setHotelPriceId(Long hotelPriceId) {
        this.hotelPriceId = hotelPriceId;
    }

    public List<HotelPriceCalendarVo> getPriceCalendarVos() {
        return priceCalendarVos;
    }

    public void setPriceCalendarVos(List<HotelPriceCalendarVo> priceCalendarVos) {
        this.priceCalendarVos = priceCalendarVos;
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
