package com.data.data.hmly.action.mobile;


import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.data.data.hmly.service.mobile.MobileLineService;
import com.data.data.hmly.service.mobile.request.LineSearchRequest;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.JsonFilter;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/11/19.
 */
public class LineMobileAction extends JxmallAction implements ModelDriven<LineSearchRequest> {

	private static final long serialVersionUID = -7017066781346847505L;

	@Resource
	private MobileLineService mobileLineService;

	private Line line;
	private Long priceDateId;
	private Long priceTypeId;

	private final LineSearchRequest request = new LineSearchRequest();

	public Result index() {
		return dispatch();
	}

	/**
	 * 获取推荐行程
	 */
	public Result recommend() {
		Map<String, Object> result = mobileLineService.listLine(request);
		result.put("success", true);
		return json(JSONObject.fromObject(result));
	}

	public Result detail() {
		line = mobileLineService.getLine(request.getLineId());
		Date now = new Date();
		Date nearestDate = null;
		if (priceDateId == null || priceDateId <= 0) {
			for (Linetypeprice linetypeprice : line.getLinetypeprices()) {
				if (linetypeprice.getLinetypepricedate() == null) {
					continue;
				}
				for (Linetypepricedate linetypepricedate : linetypeprice.getLinetypepricedate()) {
					if (linetypepricedate.getDay().before(now)) {
						continue;
					}
					if (nearestDate == null || linetypepricedate.getDay().before(nearestDate)) {
						nearestDate = linetypepricedate.getDay();
						priceDateId = linetypepricedate.getId();
						priceTypeId = linetypepricedate.getLinetypeprice().getId();
					}
				}
			}
		}
		return dispatch();
	}

	public Result date() {
		line = mobileLineService.getLine(request.getLineId());
		return dispatch();
	}

	@ResponseBody
	public Result getDate() {
		List<Linetypepricedate> list = mobileLineService.getDate(priceTypeId);
		if (list == null) {
			list = new ArrayList<Linetypepricedate>();
		}
		return json(JSONArray.fromObject(list, JsonFilter.getIncludeConfig(null, null)));
	}


	@Override
	public LineSearchRequest getModel() {
		return request;
	}

	public Line getLine() {
		return line;
	}

	public void setLine(Line line) {
		this.line = line;
	}

	public Long getPriceDateId() {
		return priceDateId;
	}

	public void setPriceDateId(Long priceDateId) {
		this.priceDateId = priceDateId;
	}

	public Long getPriceTypeId() {
		return priceTypeId;
	}

	public void setPriceTypeId(Long priceTypeId) {
		this.priceTypeId = priceTypeId;
	}
}
