package com.data.data.hmly.action.lvxbang;

import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.lvxbang.response.MiniCityResponse;
import com.google.common.collect.Lists;
import com.opensymphony.xwork2.Result;
import net.sf.json.JSONArray;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author Jonathan.Guo
 */
public class AreaWebAction extends LxbAction {

    @Resource
    private AreaService areaService;

    public String idStr;

    public Result getAreaByIds() {
        List<TbArea> list = areaService.getByIds(idStr);
        List<MiniCityResponse> result = Lists.newArrayList();
        for (TbArea city : list) {
            MiniCityResponse response = new MiniCityResponse();
            response.setId(city.getId());
            response.setName(city.getName());
            result.add(response);
        }
        return json(JSONArray.fromObject(result));
    }

}
