package com.data.data.hmly.action.hotel;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.hotel.HotelAmenitiesService;
import com.data.data.hmly.service.hotel.entity.HotelAmenities;
import com.data.data.hmly.service.hotel.entity.vo.AmenititesTree;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by dy on 2016/6/3.
 */
public class HotelAmenitiesAction extends FrameBaseAction {

    @Resource
    private HotelAmenitiesService amenitiesService;

    private HotelAmenities hotelAmenities = new HotelAmenities();

    private List<HotelAmenities> hotelAmenitiesList = new ArrayList<HotelAmenities>();
    private Map<String, Object> map = new HashMap<String, Object>();
    private String json;
    /**
     * 获取酒店属性
     * @return
     */
    public Result getListByFatherName() {
        List<AmenititesTree> amenititesTreeList = new ArrayList<AmenititesTree>();
        String ids = (String) getParameter("ids");
        if (hotelAmenities.getId() != null) {
            amenititesTreeList = amenitiesService.doGetListTree(hotelAmenities.getId());
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        JSONArray json = JSONArray.fromObject(amenititesTreeList, jsonConfig);
        return json(json);
    }

    /**
     * 获取酒店属性
     * @return
     */
    public Result getGeneralAmenities() {
        List<AmenititesTree> amenititesTreeList = new ArrayList<AmenititesTree>();
        String ids = (String) getParameter("ids");

        if (StringUtils.isNotBlank(ids)) {

            String[] strings = ids.split(",");
            List<Integer> idList = new ArrayList<Integer>();
            for (String id : strings) {
                idList.add(Integer.parseInt(id));
            }

            if (!idList.isEmpty()) {
                hotelAmenitiesList = amenitiesService.listByParent(idList);
            }
        }
        JsonConfig jsonConfig = JsonFilter.getIncludeConfig();
        JSONArray json = JSONArray.fromObject(hotelAmenitiesList, jsonConfig);
        return json(json);
    }

    public HotelAmenities getHotelAmenities() {
        return hotelAmenities;
    }

    public void setHotelAmenities(HotelAmenities hotelAmenities) {
        this.hotelAmenities = hotelAmenities;
    }

    @Override
    public String getJson() {
        return json;
    }

    @Override
    public void setJson(String json) {
        this.json = json;
    }
}
