package com.data.data.hmly.action.line;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.service.line.LinetypepriceService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.data.data.hmly.service.line.entity.enums.LinetypepriceStatus;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.StringUtils;
import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import javax.annotation.Resource;
import java.util.*;

/**
 * 线路类别报价
 * @author caiys
 * @date 2015年10月20日 下午8:09:37
 */
public class LinetypepriceAction extends FrameBaseAction  {
	private static final long serialVersionUID = -887346150287189467L;
	@Resource
	private LinetypepriceService		linetypepriceService;
	private Long 						priceId;
	private Long 						productId;
	private String 						quoteName;
	private String						quoteContainDesc;
	private String						quoteNoContainDesc;
	private String						quoteOwn;
	private Float						marketPrice;
	private List<LinetypepriceForm> 	typePriceDate;
	
	// 返回数据
	Map<String, Object>					map					= new HashMap<String, Object>();


	public Result delPrice() {
		if (priceId != null) {
			linetypepriceService.delTypePriceById(priceId);
			simpleResult(map, true, "");
		} else {
			simpleResult(map, false, "");
		}
		return jsonResult(map);
	}

	/**
	 * 保存价格类型
	 * @author caiys
	 * @date 2015年10月20日 下午8:22:37
	 * @return
	 */
	@AjaxCheck
	public Result saveLinePrice() {

		String dateJsonStr = (String) getParameter("dataJson");

		Linetypeprice linetypeprice = new Linetypeprice();
		linetypeprice.setId(priceId);
		Line line = new Line();
		line.setId(productId);
		linetypeprice.setLine(line);
		linetypeprice.setQuoteName(quoteName);
		linetypeprice.setQuoteContainDesc(quoteContainDesc);
		linetypeprice.setQuoteNoContainDesc(quoteNoContainDesc);
		linetypeprice.setMarketPrice(marketPrice);
		linetypeprice.setQuoteOwn(quoteOwn);
		linetypeprice.setUserId(getLoginUser().getId());
		linetypeprice.setCreateTime(new Date());
		linetypeprice.setStatus(LinetypepriceStatus.enable.name());
		List<Linetypepricedate> linetypepricedates = new ArrayList<Linetypepricedate>();
		if (StringUtils.isNotBlank(dateJsonStr)) {

			JSONArray jsonArray = JSONArray.fromObject(dateJsonStr);

//			{"start":"2016-04-02","discountPrice":"23.00","rebate":"3.00","childPrice":"12.00","childRebate":"2.00"}
			List<Float> priceList = new ArrayList<Float>();
			List<Float> marketPriceList = new ArrayList<Float>();
			List<Float> childPriceList = new ArrayList<Float>();
			List<Float> childMarketPriceList = new ArrayList<Float>();
			for (Object o : jsonArray) {
				JSONObject jsonObject = JSONObject.fromObject(o);
				Linetypepricedate linetypepricedate = new Linetypepricedate();
				linetypepricedate.setLineId(productId);
				linetypepricedate.setDay(DateUtils.getDate(jsonObject.get("start").toString(), "yyyy-MM-dd"));
                String discountPriceStr = (String) jsonObject.get("discountPrice");

				Float discountPrice = 0f;
				Float marketPrice = 0f;
				Float childPrice = 0f;
				Float childMarketPrice = 0f;
				if (StringUtils.isNotBlank(discountPriceStr)) {
					discountPrice = Float.valueOf(discountPriceStr);
					linetypepricedate.setDiscountPrice(discountPrice);
					priceList.add(discountPrice);
				}
				String marketPriceStr = (String) jsonObject.get("marketPrice");
				if (StringUtils.isNotBlank(marketPriceStr)) {
					marketPrice = Float.valueOf(marketPriceStr);
					linetypepricedate.setMarketPrice(marketPrice);
					marketPriceList.add(marketPrice);
				}
                String rebate = (String) jsonObject.get("rebate");
                if (rebate != null) {
                    linetypepricedate.setRebate(Float.valueOf(rebate));
                }
                String childPriceStr = (String) jsonObject.get("childPrice");
				if (childPriceStr != null) {
					childPrice = Float.valueOf(childPriceStr);
					linetypepricedate.setChildPrice(childPrice);
					childPriceList.add(childPrice);
				}
				String childMarketPriceStr = (String) jsonObject.get("childMarketPrice");
				if (StringUtils.isNotBlank(childMarketPriceStr)) {
					childMarketPrice = Float.valueOf(childMarketPriceStr);
					linetypepricedate.setChildMarketPrice(childMarketPrice);
					childMarketPriceList.add(childMarketPrice);
				}
                String childRebate = (String) jsonObject.get("childRebate");
				if (childRebate != null) {
					linetypepricedate.setChildRebate(Float.valueOf(childRebate));
				}
                String oasiaHotel = (String) jsonObject.get("oasiaHotel");
				if (oasiaHotel != null) {
					linetypepricedate.setOasiaHotel(Float.valueOf(oasiaHotel));
				}
				String inventory = (String) jsonObject.get("inventory");
				if (inventory != null) {
					linetypepricedate.setInventory(Integer.valueOf(inventory));
				}
				linetypepricedate.setUserId(getLoginUser().getId());
				linetypepricedate.setCreateTime(new Date());
				linetypepricedates.add(linetypepricedate);
			}
			if (!priceList.isEmpty()) {
				linetypeprice.setPrice(Collections.min(priceList));
			}
			if (!childPriceList.isEmpty()) {
				linetypeprice.setChildPrice(Collections.min(childPriceList));
			}
			if (!marketPriceList.isEmpty()) {
				linetypeprice.setMarketPrice(Collections.min(marketPriceList));
			}
			if (!childMarketPriceList.isEmpty()) {
				linetypeprice.setChildMarketPrice(Collections.min(childMarketPriceList));
			}

//			for (LinetypepriceForm item : typePriceDate) {
//				Linetypepricedate linetypepricedate = new Linetypepricedate();
//				linetypepricedate.setLineId(productId);
//				linetypepricedate.setDay(DateUtils.getDate(item.getStart(), "yyyy-MM-dd"));
//				linetypepricedate.setDiscountPrice(item.getDiscountPrice());
//				linetypepricedate.setRebate(item.getRebate());
//				linetypepricedate.setChildPrice(item.getChildPrice());
//				linetypepricedate.setChildRebate(item.getChildRebate());
//				linetypepricedate.setUserId(getLoginUser().getId());
//				linetypepricedate.setCreateTime(new Date());
//				linetypepricedates.add(linetypepricedate);
//			}
		}

		linetypepriceService.saveLinePrice(linetypeprice, linetypepricedates);
		map.put("id", linetypeprice.getId());
		simpleResult(map, true, "");
		return jsonResult(map);
	}
	
	/**
	 * 获取类别报价中最小优惠价，日期范围第二天和下个月最后一天
	 * @author caiys
	 * @date 2015年10月21日 下午3:36:41
	 * @return
	 */
	@AjaxCheck
	public Result findMinValue() {
		String linetypepriceId = (String) getParameter("linetypepriceId");
		String prop = (String) getParameter("prop");
		Date[] dates = DateUtils.getRangeDate();
		Float propValue = linetypepriceService.findMinValue(Long.valueOf(linetypepriceId), dates[0], dates[1], prop);
		map.put("propValue", propValue);
		simpleResult(map, true, "");
		return jsonResult(map);
	}


	public List<LinetypepriceForm> getTypePriceDate() {
		return typePriceDate;
	}


	public void setTypePriceDate(List<LinetypepriceForm> typePriceDate) {
		this.typePriceDate = typePriceDate;
	}


	public String getQuoteName() {
		return quoteName;
	}


	public void setQuoteName(String quoteName) {
		this.quoteName = quoteName;
	}


	public String getQuoteContainDesc() {
		return quoteContainDesc;
	}

	public void setQuoteContainDesc(String quoteContainDesc) {
		this.quoteContainDesc = quoteContainDesc;
	}

	public String getQuoteNoContainDesc() {
		return quoteNoContainDesc;
	}

	public void setQuoteNoContainDesc(String quoteNoContainDesc) {
		this.quoteNoContainDesc = quoteNoContainDesc;
	}

	public Float getMarketPrice() {
		return marketPrice;
	}


	public void setMarketPrice(Float marketPrice) {
		this.marketPrice = marketPrice;
	}


	public Long getPriceId() {
		return priceId;
	}


	public void setPriceId(Long priceId) {
		this.priceId = priceId;
	}


	public Long getProductId() {
		return productId;
	}


	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public String getQuoteOwn() {
		return quoteOwn;
	}

	public void setQuoteOwn(String quoteOwn) {
		this.quoteOwn = quoteOwn;
	}
}
