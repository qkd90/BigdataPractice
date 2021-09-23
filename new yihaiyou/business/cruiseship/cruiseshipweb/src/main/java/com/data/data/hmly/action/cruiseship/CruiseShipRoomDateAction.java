package com.data.data.hmly.action.cruiseship;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.cruiseship.CruiseShipDateService;
import com.data.data.hmly.service.cruiseship.CruiseShipRoomDateService;
import com.data.data.hmly.service.cruiseship.CruiseShipRoomService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoom;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipRoomDate;
import com.framework.struts.AjaxCheck;
import com.framework.struts.JsonFloatValueProcessor;
import com.framework.struts.JsonIntegerValueProcessor;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by caiys on 2016/9/19.
 */
public class CruiseShipRoomDateAction extends FrameBaseAction {
    @Resource
    private CruiseShipRoomService cruiseShipRoomService;
    @Resource
    private CruiseShipDateService cruiseShipDateService;
    @Resource
    private CruiseShipRoomDateService cruiseShipRoomDateService;

    /**
     * 列表查询：查询可维护的房型价格
     */
    @AjaxCheck
    public Result list() {
        String dateId = (String) getParameter("dateId");
        // 已有的价格信息
        CruiseShipRoomDate cruiseShipRoomDate = new CruiseShipRoomDate();
        cruiseShipRoomDate.setDateId(Long.valueOf(dateId));
        List<CruiseShipRoomDate> cruiseShipRoomDates = cruiseShipRoomDateService.listCruiseShipRoomDates(cruiseShipRoomDate);
        // 发团日期信息
        CruiseShipDate cruiseShipDate = cruiseShipDateService.findById(Long.valueOf(dateId));
        // 已有房型信息
        CruiseShipRoom cruiseShipRoom = new CruiseShipRoom();
        cruiseShipRoom.setCruiseShipId(cruiseShipDate.getCruiseShip().getId());
        List<CruiseShipRoom> cruiseShipRooms = cruiseShipRoomService.listCruiseShipRooms(cruiseShipRoom, getLoginUser(), isSiteAdmin(), isSupperAdmin());
        // 组装结果
        List<CruiseShipRoomDate> list = new ArrayList<CruiseShipRoomDate>();
        for (CruiseShipRoom room : cruiseShipRooms) {
            CruiseShipRoomDate roomDate = new CruiseShipRoomDate();
            roomDate.setRoomId(room.getId());
            roomDate.setRoomName(room.getName());
            roomDate.setRoomType(room.getRoomType());
            roomDate.setDateId(cruiseShipDate.getId());
            for (CruiseShipRoomDate rd : cruiseShipRoomDates) {
                if (room.getId() == rd.getCruiseShipRoom().getId()) {
                    roomDate.setId(rd.getId());
                    roomDate.setDiscountPrice(rd.getDiscountPrice());
                    roomDate.setSalePrice(rd.getSalePrice());
                    roomDate.setMarketPrice(rd.getMarketPrice());
                    roomDate.setInventory(rd.getInventory());
                    roomDate.setDateStr(DateUtils.format(rd.getDate(), "yyyy-MM-dd"));
                    break;
                }
            }
            list.add(roomDate);
        }

        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        // TODO 这两句有报错，待处理
        jsonConfig.registerJsonValueProcessor(Integer.class, new JsonIntegerValueProcessor());
        jsonConfig.registerJsonValueProcessor(Float.class, new JsonFloatValueProcessor());
        return datagrid(list, jsonConfig);
    }

    /**
     * 新增、更新
     * @return
     */
    @AjaxCheck
    public Result save() {
        String id = (String) getParameter("id");
        String dateId = (String) getParameter("dateId");
        String roomId = (String) getParameter("roomId");
        String discountPriceStr = (String) getParameter("discountPrice");
        String salePriceStr = (String) getParameter("salePrice");
        String marketPriceStr = (String) getParameter("marketPrice");
        String inventoryStr = (String) getParameter("inventory");

        CruiseShipRoomDate cruiseShipRoomDate = null;
        if (StringUtils.isNotBlank(id)) {   // 更新
            cruiseShipRoomDate = cruiseShipRoomDateService.findById(Long.valueOf(id));
            cruiseShipRoomDate.setUpdateTime(new Date());
        } else {    // 新增
            cruiseShipRoomDate = new CruiseShipRoomDate();
            cruiseShipRoomDate.setCreateTime(new Date());
            cruiseShipRoomDate.setUpdateTime(new Date());
        }
        if (StringUtils.isNotBlank(discountPriceStr)) {
            cruiseShipRoomDate.setDiscountPrice(Float.valueOf(discountPriceStr));
        }
        if (StringUtils.isNotBlank(salePriceStr)) {
            cruiseShipRoomDate.setSalePrice(Float.valueOf(salePriceStr));
        }
        if (StringUtils.isNotBlank(marketPriceStr)) {
            cruiseShipRoomDate.setMarketPrice(Float.valueOf(marketPriceStr));
        }
        if (StringUtils.isNotBlank(inventoryStr)) {
            cruiseShipRoomDate.setInventory(Integer.valueOf(inventoryStr));
        }
        CruiseShipRoom cruiseShipRoom = new CruiseShipRoom();
        cruiseShipRoom.setId(Long.valueOf(roomId));
        cruiseShipRoomDate.setCruiseShipRoom(cruiseShipRoom);
        CruiseShipDate cruiseShipDate = cruiseShipDateService.findById(Long.valueOf(dateId));
        cruiseShipRoomDate.setCruiseShipDate(cruiseShipDate);
        cruiseShipRoomDate.setCruiseShipId(cruiseShipDate.getCruiseShip().getId());
        cruiseShipRoomDate.setDate(cruiseShipDate.getDate());
        Float discountPrice = cruiseShipRoomDateService.saveOrUpdate(cruiseShipRoomDate);

        result.put("dateid", String.valueOf(cruiseShipDate.getId()));
        result.put("minDiscountPrice", String.valueOf(discountPrice));
        result.put("date", DateUtils.format(cruiseShipDate.getDate(), "yyyy-MM-dd"));
        simpleResult(result, true, "");
        return jsonResult(result);
    }

    /**
     * 删除
     * @return
     */
    @AjaxCheck
    public Result del() {
        String roomDateId = (String) getParameter("roomDateId");
        Float discountPrice = cruiseShipRoomDateService.delCruiseShipRoomDate(Long.valueOf(roomDateId));
        result.put("minDiscountPrice", String.valueOf(discountPrice));
        simpleResult(result, true, "");
        return jsonResult(result);
    }
}
