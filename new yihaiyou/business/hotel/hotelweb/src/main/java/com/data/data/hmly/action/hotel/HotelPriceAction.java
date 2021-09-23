package com.data.data.hmly.action.hotel;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.hotel.HotelPriceService;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by caiys on 2016/8/18.
 */
public class HotelPriceAction extends FrameBaseAction {
    @Resource
    private HotelPriceService hotelPriceService;

    private Integer page = 1;
    private Integer rows = 10;

    @AjaxCheck
    public Result listHotelPriceForLine() {
        // 参数
        String hotelIdStr = (String) getParameter("hotelId");
        String hotelNameStr = (String) getParameter("hotelName");
        String linedaysIdStr = (String) getParameter("linedaysId");
        HotelPrice hotelPrice = new HotelPrice();
        hotelPrice.setLinedaysId(Long.valueOf(linedaysIdStr));
        if (StringUtils.isNotBlank(hotelIdStr)) {
            hotelPrice.setHotelId(Long.valueOf(hotelIdStr));
        }
        if (StringUtils.isNotBlank(hotelNameStr)) {
            hotelPrice.setHotelName(hotelNameStr);
        }

        Page pageInfo = new Page(page, rows);
        List<HotelPrice> list = hotelPriceService.listHotelPriceForLine(hotelPrice, pageInfo, getLoginUser(), isSiteAdmin(), isSupperAdmin());

        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        return datagrid(list, pageInfo.getTotalCount(), jsonConfig);
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
}
