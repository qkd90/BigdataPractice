package com.data.data.hmly.service.shopcart;

import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/10/27.
 */
@Service
public class ShopCartService {

	@Resource
	private ShopCartLineService   shopCartLineService;
	@Resource
	private ShopCartTicketService shopCartTicketService;

	public void addToCart(Product product) {

	}

	public void checkout() {
		//todo: check out all item in cart
	}

	public Map<String, Object> orderStraight(Product product, Long priceDateId) {
		Linetypepricedate linetypepricedate = shopCartLineService.getPriceDate(priceDateId);
		String startDate = new SimpleDateFormat("yyyy-MM-dd").format(linetypepricedate.getDay());
		return orderStraight(product, linetypepricedate.getLinetypeprice().getId(), startDate);
	}

	/**
	 * 直接从商品到订单页面，用于立即预定
	 *
	 * @param product 商品
	 */
	public Map<String, Object> orderStraight(Product product, Long priceId, String startDate) {
		if (product == null || product.getId() == null) {
			return null;
		}
		Map<String, Object> result = getProductInfo(product, priceId, startDate);

		return result;
	}

	public Map<String, Object> getProductInfo(Product product, Long priceId, String startDate) {
		switch (product.getProType()) {
			case line:
				return shopCartLineService.getProductInfo(product, priceId, startDate);
			case scenic:
				return shopCartTicketService.getProductInfo(product, priceId, startDate);
			default:
				return null;
		}
	}

	public List<Map<String, Object>> getPriceDateList(ProductType productType, Long priceId) {
		switch (productType) {
			case line:
				return shopCartLineService.getPriceDateList(priceId);
			case scenic:
				return shopCartTicketService.getPriceDateList(priceId);
			case restaurant:
			case hotel:
			default:
				return null;
		}
	}

}
