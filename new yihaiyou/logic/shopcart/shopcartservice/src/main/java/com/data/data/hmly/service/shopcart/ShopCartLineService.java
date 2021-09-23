package com.data.data.hmly.service.shopcart;

import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.LineexplainService;
import com.data.data.hmly.service.line.LinetypepriceService;
import com.data.data.hmly.service.line.LinetypepricedateService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Lineexplain;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.data.data.hmly.service.order.entity.enums.OrderCostType;
import com.google.common.collect.Lists;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by guoshijie on 2015/11/6.
 */
@Service
public class ShopCartLineService extends ShopCartProductService {

	@Resource
	private LineService          lineService;
	@Resource
	private LineexplainService   lineexplainService;
	@Resource
	private LinetypepriceService linetypepriceService;
	@Resource
	private LinetypepricedateService linetypepricedateService;
	@Resource
	private TbAreaService        tbAreaService;

	public Linetypepricedate getPriceDate(Long priceDateId) {
		return linetypepricedateService.load(priceDateId);
	}

	public Map<String, Object> getProductInfo(Product product, Long priceId, String startDay) {
		Line line = lineService.loadLine(product.getId());
		if (line == null) {
			return null;
		}
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("product", line);

		Lineexplain lineexplain = lineexplainService.getByLine(line.getId());
		result.put("productDetail", lineexplain);

		Linetypeprice linetypepriceCondition = new Linetypeprice();
		linetypepriceCondition.setLine(line);

		TbArea tbArea = tbAreaService.getById(line.getStartCityId());
		result.put("startCity", tbArea);

		Linetypeprice linetypeprice = linetypepriceService.getLinetypeprice(priceId);

		if (linetypeprice == null) {
			return null;
		}
		Set<Linetypepricedate> linetypepricedates = linetypeprice.getLinetypepricedate();
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		Calendar calendar = Calendar.getInstance();
		Date startDate = null;
		try {
			startDate = simpleDateFormat.parse(startDay);
			calendar.setTime(startDate);
		} catch (ParseException e) {
			for (Linetypepricedate linetypepricedate : linetypepricedates) {
				startDate = linetypepricedate.getDay();
				calendar.setTime(startDate);
				break;
			}
			e.printStackTrace();
		}
		calendar.add(Calendar.DAY_OF_YEAR, lineexplain.getLinedays().size());
		String endDay = simpleDateFormat.format(calendar.getTime());

		if (startDate == null) {
			return null;
		}
		result.put("startDay", startDay);
		result.put("endDay", endDay);

		for (Linetypepricedate linetypepricedate : linetypeprice.getLinetypepricedate()) {
			if (linetypepricedate.getDay().compareTo(startDate) == 0) {
				result.put("priceInfo", createPriceInfo(linetypepricedate));
				break;
			}
		}

		return result;

	}

	public Map<String, Object> createPriceInfo(Object o) {
		Map<String, Object> priceInfo = new HashMap<String, Object>();
		Linetypepricedate datePrice = (Linetypepricedate) o;
		priceInfo.put("dateId", datePrice.getId());
		priceInfo.put("priceId", datePrice.getLinetypeprice().getId());
		priceInfo.put("costType", OrderCostType.line);
		priceInfo.put("costName", datePrice.getLinetypeprice().getQuoteName());
		priceInfo.put("date", datePrice.getDay());
		priceInfo.put("discountPrice", datePrice.getDiscountPrice());
		priceInfo.put("rebate", datePrice.getRebate());
		priceInfo.put("childPrice", datePrice.getChildPrice());
		priceInfo.put("childRebate", datePrice.getChildRebate());
		return priceInfo;
	}

	public List<Linetypepricedate> load(Long priceId) {
		Linetypeprice linetypeprice = linetypepriceService.getLinetypeprice(priceId);
		return Lists.newArrayList(linetypeprice.getLinetypepricedate());
	}

}
