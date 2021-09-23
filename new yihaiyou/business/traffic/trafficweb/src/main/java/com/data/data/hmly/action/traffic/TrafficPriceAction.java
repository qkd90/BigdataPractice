package com.data.data.hmly.action.traffic;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.traffic.TrafficPriceCalenderService;
import com.data.data.hmly.service.traffic.TrafficPriceService;
import com.data.data.hmly.service.traffic.TrafficService;
import com.data.data.hmly.service.traffic.entity.Traffic;
import com.data.data.hmly.service.traffic.entity.TrafficPrice;
import com.data.data.hmly.service.traffic.entity.enums.TrafficType;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Sane on 2015/12/28.
 */
public class TrafficPriceAction extends FrameBaseAction{
	@Resource
	private TrafficService trafficService;
	@Resource
	private TrafficPriceService trafficPriceService;
	@Resource
	private TrafficPriceCalenderService calenderService;

	private TrafficPrice trafficPrice = new TrafficPrice();

	private Map<String, Object> map = new HashMap<String, Object>();



	public Result findCalenderPrice() {
		String dateStartStr = (String) getParameter("dateStart");
		String typeRriceIdStr = (String) getParameter("typeRriceId");
		List<Map<String, String>> maps = calenderService.findListByDateAndPriceId(DateUtils.toDate(dateStartStr), Long.parseLong(typeRriceIdStr));
		JSONArray json = JSONArray.fromObject(maps);
		return json(json);
	}

	public Result delTrafficPrice() {
		String typePriceId = (String) getParameter("typePriceId");
		if (StringUtils.isNotBlank(typePriceId)) {
			trafficPrice.setId(Long.parseLong(typePriceId));
			trafficPriceService.delTrafficPrice(trafficPrice);
			calenderService.delByTrafficPrice(trafficPrice);
		}
		simpleResult(map, true, "");
		return jsonResult(map);
	}

//	/traffic/trafficPrice/saveTrafficPrice.jhtml
	public Result saveTrafficPrice() {

		String calenderData = (String) getParameter("dateSource");

		if (trafficPrice.getId() == null) {
			Traffic traffic = new Traffic();
			traffic.setId(trafficPrice.getTraffic().getId());
			trafficPrice.setTraffic(traffic);
			trafficPriceService.save(trafficPrice);
			calenderService.saveCalender(trafficPrice, calenderData);
			map.put("productId", trafficPrice.getTraffic().getId());
			simpleResult(map, true, "");
		} else {
			TrafficPrice tempTrafficPrice = trafficPriceService.get(trafficPrice.getId());
			trafficPriceService.update(tempTrafficPrice);
			calenderService.saveCalender(tempTrafficPrice, calenderData);
			map.put("productId", trafficPrice.getTraffic().getId());
			simpleResult(map, true, "");
		}
		return jsonResult(map);
	}
//	/traffic/trafficPrice/listPrice.jhtml
	public Result listPrice() {
		String productId = (String) getParameter("productId");
		List<TrafficPrice> trafficPrices = new ArrayList<TrafficPrice>();
		if (!StringUtils.isNotBlank(productId)) {
			return datagrid(trafficPrices);
		}
		Long trafficId = Long.parseLong(productId);
		trafficPrices = trafficPriceService.getTrafficPriceListByTraffic(trafficId);
		return datagrid(trafficPrices);
	}

	public Result getTrafficPrices() {
		String trafficId = (String) getParameter("trafficId");
		Date date = getDate((String) getParameter("date"));
		List<TrafficPrice> prices = trafficPriceService.findByIdAndDate(Long.parseLong(trafficId), date);
		return json(JSONArray.fromObject(prices));
	}

	public Result getTrafficPricesByCity() {
		String leaveCity = (String) getParameter("leaveCity");
		String arriveCity = (String) getParameter("arriveCity");
		String trafficType = (String) getParameter("trafficType");
		Date date = getDate((String) getParameter("date"));
		TrafficType type = TrafficType.AIRPLANE;
		if (trafficType == "1"){
			type = TrafficType.SHIP;
		}else if (trafficType == "2") {
			type = TrafficType.TRAIN;
		}
		List<TrafficPrice> prices = trafficPriceService.findByCityAndDate(Long.parseLong(leaveCity),
				Long.parseLong(arriveCity), date,type);
		return json(JSONArray.fromObject(prices));
	}

	private Date getDate(String dateStr) {
		Date date = null;
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		try {
			date = sdf.parse(dateStr);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}

	public TrafficPrice getTrafficPrice() {
		return trafficPrice;
	}

	public void setTrafficPrice(TrafficPrice trafficPrice) {
		this.trafficPrice = trafficPrice;
	}
}
