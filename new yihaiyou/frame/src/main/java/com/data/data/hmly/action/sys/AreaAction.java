package com.data.data.hmly.action.sys;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.entity.TbArea;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONArray;
import net.sf.json.JsonConfig;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AreaAction extends FrameBaseAction {
	/**
	 * 
	 */
	private static final long serialVersionUID = 3012149839002043602L;
	@Resource
	private TbAreaService tbAreaService;

	private Map<String, Object> map = new HashMap<String, Object>();

	public Result selectProvince() {
		long provinceId = Long.parseLong(getParameter("provinceId").toString());
		List<TbArea> citys = tbAreaService.findCity(provinceId);
		return json(JSONArray.fromObject(citys, JsonFilter.getIncludeConfig()));
	}

	public Result getProvinces(){
		List<TbArea> privinces = tbAreaService.findProvince();
		return json(JSONArray.fromObject(privinces, JsonFilter.getIncludeConfig("name", "cityCode")));
	}

    public Result getCNProvinces() {
        List<TbArea> privinces = tbAreaService.findCNProvince();
        return json(JSONArray.fromObject(privinces, JsonFilter.getIncludeConfig("name", "cityCode")));
    }

	public Result getCities(){
		long provinceId = Long.parseLong(getParameter("provinceId").toString());
		List<TbArea> cities = tbAreaService.findCity(provinceId);
		return json(JSONArray.fromObject(cities, JsonFilter.getIncludeConfig("childs.name", "childs.cityCode")));
	}

	public Result getAreas(){
		long cityId = Long.parseLong(getParameter("cityId").toString());
		List<TbArea> areas = tbAreaService.findArea(cityId);
		return json(JSONArray.fromObject(areas, JsonFilter.getIncludeConfig("childs.name", "childs.cityCode")));
	}

	public Result getAreaInfo() {
		Long cityId = Long.parseLong(getParameter("cityId").toString());
		TbArea tbArea = tbAreaService.getArea(cityId);
		if (tbArea.getLevel() == 2) {
			map.put("cityId", tbArea.getId());
			map.put("cityName", tbArea.getName());
			map.put("level", 2);
			if (tbArea.getFather() != null) {
				Long provinceId = tbArea.getFather().getId();
				String provinceName = tbArea.getFather().getName();
				map.put("provinceId", provinceId);
				map.put("provinceName", provinceName);
			}
			if (tbArea.getFather().getFather() != null) {
				Long countryId = tbArea.getFather().getFather().getId();
				String countryName = tbArea.getFather().getFather().getName();
				map.put("countryId", countryId);
				map.put("countryName", countryName);
			}
		} else if (tbArea.getLevel() == 1) {
			map.put("provinceId", tbArea.getId());
			map.put("provinceName", tbArea.getName());
			map.put("level", 1);
			if (tbArea.getFather() != null) {
				Long countryId = tbArea.getFather().getId();
				String countryName = tbArea.getFather().getName();
				map.put("countryId", countryId);
				map.put("countryName", countryName);
			}
		} else if (tbArea.getLevel() == 0) {
			map.put("level", 0);
			map.put("countryId", tbArea.getId());
			map.put("countryName", tbArea.getName());
		}
		simpleResult(map, true, "");
		return jsonResult(map);
	}

	/**
	 * 获取省、市列表
	 * 
	 * @author caiys
	 * @date 2015年10月27日 下午1:59:15
	 * @return
	 */
	public Result listArea() {
		String fatherId = (String) getParameter("fatherId");
		List<TbArea> areas = new ArrayList<TbArea>();
		if (StringUtils.isNotBlank(fatherId)) {
			areas = tbAreaService.getCityByPro(fatherId, 2);
		} else {
			areas = tbAreaService.getCityByPro("100000", 1);
		}
		JsonConfig config = JsonFilter.getIncludeConfig();;
//		JsonConfig config = JsonFilter.getIncludeConfig(null,null);
		JSONArray json = JSONArray.fromObject(areas, config);
		return json(json);
	}

	/**
	 * 获取国家、省、市列表
	 *
	 * @return
	 * @author caiys
	 * @date 2015年10月27日 下午1:59:15
	 */
	public Result listAreaNew() {
		String fatherId = (String) getParameter("fatherId");
		String name = (String) getParameter("name");
		List<TbArea> areas = new ArrayList<TbArea>();
		if (StringUtils.isNotBlank(fatherId)) {
			areas = tbAreaService.findArea(Long.valueOf(fatherId), name, null);
		} else {
			areas = tbAreaService.findArea(null, name, 0);
		}
		JsonConfig config = JsonFilter.getIncludeConfig();
		JSONArray json = JSONArray.fromObject(areas, config);
		return json(json);
	}

}
