package com.data.data.hmly.action.hotel;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.HotelService;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.HotelPriceCalendar;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.data.data.hmly.service.hotel.entity.vo.HotelPriceDate;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;

import javax.annotation.Resource;
import java.text.DecimalFormat;
import java.util.*;

/**
 * Created by dy on 2016/6/5.
 */
public class HotelRoomTypeAction extends FrameBaseAction {

    //roomType

    @Resource
    private HotelService hotelService;
    @Resource
    private HotelPriceService hotelPriceService;



    private Map<String, Object> map = new HashMap<String, Object>();
    private HotelPrice hotelPrice = new HotelPrice();
    private Long productId;
    private Long typePriceId;

    private List<HotelPriceDate> typePriceDate = new ArrayList<HotelPriceDate>();

    private List<HotelPrice> hotelPrices = new ArrayList<HotelPrice>();


    public Result findTypePriceDate() {

        // 参数
        String typeRriceId = (String) getParameter("typeRriceId");
        String dateStartStr = (String) getParameter("dateStart");
        String dateEndStr = (String) getParameter("dateEnd");
        Date dateStart = null;
        Date dateEnd = null;
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        if (org.apache.commons.lang3.StringUtils.isNotBlank(typeRriceId)) {
            if (org.apache.commons.lang3.StringUtils.isNotBlank(dateStartStr)) {
                dateStart = DateUtils.getDate(dateStartStr, "yyyy-MM-dd");
            }
            if (org.apache.commons.lang3.StringUtils.isNotBlank(dateEndStr)) {
                dateEnd = DateUtils.getDate(dateEndStr, "yyyy-MM-dd");
            }
            // 如果起始时间不为空且结束时间为空，则设置结束时间为开始时间当月最后一天
            if (dateStart != null && dateEnd == null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateStart);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 6);
                calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
                dateEnd = calendar.getTime();
            }
            // 如果起始时间为空且结束时间不为空，则设置起始时间为结束时间当月的第一天
            if (dateStart == null && dateEnd != null) {
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(dateEnd);
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                dateStart = calendar.getTime();
            }
            List<HotelPriceCalendar> hotelPriceCalendars = hotelPriceService.findTypePriceDate(Long.valueOf(typeRriceId), dateStart, dateEnd);
            // 返回页面数据格式
            DecimalFormat df = new DecimalFormat("0.00");
            for (HotelPriceCalendar pd : hotelPriceCalendars) {	// {id:vid,discountPrice:discountPrice,title:'优惠价¥'+discountPrice,start:LineUtil.dateToString(date,'yyyy-MM-dd')}
                if (pd.getMember() != null) {
                    Map<String, String> map = new HashMap<String, String>();
                    Date day = pd.getDate();
                    map.put("id", "1" + day.getTime());
                    map.put("member", pd.getMember().toString());
                    map.put("title", "a.销售：" + pd.getMember());
                    map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
                    list.add(map);
                }
                if (pd.getMarketPrice() != null) {
                    Map<String, String> map = new HashMap<String, String>();
                    Date day = pd.getDate();
                    map.put("id", "2" + day.getTime());
                    map.put("marketPrice", pd.getMarketPrice().toString());
                    map.put("title", "a.市价：" + pd.getMarketPrice());
                    map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
                    list.add(map);
                }
                if (pd.getCost() != null) {
                    Map<String, String> map = new HashMap<String, String>();
                    Date day = pd.getDate();
                    map.put("id", "3" + day.getTime());
                    map.put("cost", pd.getCost().toString());
                    map.put("title", "c.结算：" + pd.getCost());
                    map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
                    list.add(map);
                }
                if (pd.getInventory() != null) {
                    Map<String, String> map = new HashMap<String, String>();
                    Date day = pd.getDate();
                    map.put("id", "4" + day.getTime());
                    map.put("inventory", pd.getInventory().toString());
                    map.put("title", "d.库存：" + pd.getInventory());
                    map.put("start", DateUtils.format(day, "yyyy-MM-dd"));
                    list.add(map);
                }
            }
        }
        JSONArray json = JSONArray.fromObject(list);
        return json(json);

    }

    public Result getShowHotelList() {
        if (productId != null) {
            Hotel hotel = hotelService.get(productId);
            hotelPrices = hotelPriceService.findByHotel(hotel);
        }
        return datagrid(hotelPrices);
    }


    public Result saveRoomPrice() {

        String dataSource = (String) getParameter("dateSource");

        if (StringUtils.isNotBlank(hotelPrice.getRoomName())) {
            hotelPrice.setStatus(PriceStatus.UP);
            hotelPriceService.saveOrUpdate(hotelPrice);
            hotelPriceService.delPriceDate(hotelPrice);
            hotelPriceService.saveDatePrice(dataSource, hotelPrice);
            map.put("productId", hotelPrice.getHotel().getId());
            simpleResult(map, true, "保存成功！");
        } else {
            simpleResult(map, false, "保存失败！");
        }
        return jsonResult(map);
    }


    public Result delRoomType() {
        if (typePriceId != null) {
            hotelPrice = hotelPriceService.get(typePriceId);
            hotelPriceService.delRooType(hotelPrice);
        }
        simpleResult(map, true, "删除成功！");
        return  jsonResult(map);
    }

    public HotelPrice getHotelPrice() {
        return hotelPrice;
    }

    public void setHotelPrice(HotelPrice hotelPrice) {
        this.hotelPrice = hotelPrice;
    }

    public List<HotelPriceDate> getTypePriceDate() {
        return typePriceDate;
    }

    public void setTypePriceDate(List<HotelPriceDate> typePriceDate) {
        this.typePriceDate = typePriceDate;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public Long getTypePriceId() {
        return typePriceId;
    }

    public void setTypePriceId(Long typePriceId) {
        this.typePriceId = typePriceId;
    }
}
