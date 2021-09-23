package com.data.data.hmly.action.yhypc;

import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.entity.TbArea;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONArray;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huangpeijie on 2017-03-06,0006.
 */
public class CityWebAction extends YhyAction {
    @Resource
    private TbAreaService tbAreaService;

    public Long provinceId;

    public Result getProvinceList() {
        List<TbArea> tbAreas = tbAreaService.findArea(null, null, 1);
        return json(JSONArray.fromObject(tbAreas, JsonFilter.getIncludeConfig()));
    }

    public Result getCityList() {
        List<TbArea> tbAreas = tbAreaService.findArea(provinceId, null, 2);
        return json(JSONArray.fromObject(tbAreas, JsonFilter.getIncludeConfig()));
    }
}
