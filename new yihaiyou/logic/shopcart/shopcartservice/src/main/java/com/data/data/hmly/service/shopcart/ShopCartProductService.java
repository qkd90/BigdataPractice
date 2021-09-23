package com.data.data.hmly.service.shopcart;

import com.data.data.hmly.service.common.entity.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/11/6.
 */
@Service
public class ShopCartProductService {

	public Map<String, Object> getProductInfo(Product product, Long priceId, String startDay) {
		return new HashMap<String, Object>();
	}

	public Map<String, Object> createPriceInfo(Object o) {
		return null;
	}

	public <T> List<Map<String, Object>> getPriceDateList(Long priceId) {
		List<T> priceDateList = load(priceId);
		return createPriceDateList(priceDateList);
	}

	public <T> List<Map<String, Object>> createPriceDateList(List<T> priceDateList) {
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (T priceDate : priceDateList) {
			Map<String, Object> map = createPriceInfo(priceDate);
			list.add(map);
		}
		return list;
	}


	public <T> List<T> load(Long priceId) {
		return new ArrayList<T>();
	}

}
