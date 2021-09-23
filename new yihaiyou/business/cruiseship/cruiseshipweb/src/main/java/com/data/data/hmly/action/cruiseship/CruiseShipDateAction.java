package com.data.data.hmly.action.cruiseship;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.cruiseship.CruiseShipDateService;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipDate;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import net.sf.json.JSONArray;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by caiys on 2016/9/19.
 */
public class CruiseShipDateAction extends FrameBaseAction {
    @Resource
    private CruiseShipDateService cruiseShipDateService;

    /**
     * 发团日期列表（日历显示）
     */
    public Result listForCalendar() {
        String cruiseShipId = (String) getParameter("cruiseShipId");
        String todayStr = (String) getParameter("today");
        Date today = DateUtils.getDate(todayStr, "yyyy-MM-dd");
        Date dateStart = DateUtils.add(today, Calendar.DAY_OF_MONTH, 1);
        Date dateEnd = DateUtils.add(today, Calendar.MONTH, 3); // 默认三个月
        List<CruiseShipDate> dates = cruiseShipDateService.listCruiseShipDates(Long.valueOf(cruiseShipId), dateStart, dateEnd);
        // 组装结果数据，额外添加日历所需按钮数据
        List<Map<String, String>> list = new ArrayList<Map<String, String>>();
        Set<String> hasDateSet = new HashSet<String>();
        for (CruiseShipDate date : dates) {
            String dateStr = DateUtils.format(date.getDate(), "yyyy-MM-dd");
            Map<String, String> map = new HashMap<String, String>();
            map.put("id", "cal_" + dateStr);
            map.put("dateid", String.valueOf(date.getId()));
            map.put("title", date.getMinDiscountPrice() + "元起");
            map.put("start", dateStr);
            list.add(map);
            // 按钮数据
            Map<String, String> btnMap = new HashMap<String, String>();
            btnMap.put("compType", "button");
            btnMap.put("btnType", "delDate");
            btnMap.put("dateid", String.valueOf(date.getId()));
            btnMap.put("start", dateStr);
            list.add(btnMap);
            hasDateSet.add(dateStr);
        }
        // 未有数据的日历单元格添加额外按钮
        Date curDate = dateStart;
        while (curDate.compareTo(dateEnd) <= 0) {
            String dateStr = DateUtils.format(curDate, "yyyy-MM-dd");
            if (!hasDateSet.contains(dateStr)) {
                // 按钮数据
                Map<String, String> btnMap = new HashMap<String, String>();
                btnMap.put("compType", "button");
                btnMap.put("btnType", "addDate");
                btnMap.put("start", dateStr);
                list.add(btnMap);
            }
            curDate = DateUtils.add(curDate, Calendar.DAY_OF_MONTH, 1);
        }

        JSONArray json = JSONArray.fromObject(list);
        return json(json);
    }

    /**
     * 新增
     * @return
     */
    @AjaxCheck
    public Result save() {
        String cruiseShipId = (String) getParameter("cruiseShipId");
        String dateStr = (String) getParameter("date");
        Date date = DateUtils.getDate(dateStr, "yyyy-MM-dd");

        CruiseShipDate cruiseShipDate = new CruiseShipDate();
        CruiseShip cruiseShip = new CruiseShip();
        cruiseShip.setId(Long.valueOf(cruiseShipId));
        cruiseShipDate.setCruiseShip(cruiseShip);
        cruiseShipDate.setDate(date);
        cruiseShipDate.setUpdateTime(new Date());
        cruiseShipDate.setCreateTime(new Date());
        cruiseShipDate.setMinDiscountPrice(0f);
        cruiseShipDate.setMinSalePrice(0f);
        cruiseShipDate.setMinMarketPrice(0f);
        cruiseShipDateService.saveCruiseShipDate(cruiseShipDate);
        result.put("dateid", String.valueOf(cruiseShipDate.getId()));
        simpleResult(result, true, "");
        return jsonResult(result);
    }

    /**
     * 删除
     * @return
     */
    @AjaxCheck
    public Result del() {
        String dateId = (String) getParameter("dateId");
        cruiseShipDateService.delCruiseShipDate(Long.valueOf(dateId));
        simpleResult(result, true, "");
        return jsonResult(result);
    }

}
