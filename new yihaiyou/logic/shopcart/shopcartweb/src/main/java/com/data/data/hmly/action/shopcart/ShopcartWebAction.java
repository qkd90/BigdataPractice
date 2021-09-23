package com.data.data.hmly.action.shopcart;

import com.data.data.hmly.action.user.UserConstans;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.shopcart.ShopCartService;
import com.opensymphony.xwork2.ModelDriven;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import net.sf.json.JSONArray;
import org.apache.log4j.Logger;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/10/27.
 */

public class ShopcartWebAction extends JxmallAction implements ModelDriven<Product> {

	private Logger logger = Logger.getLogger(ShopcartWebAction.class);

	@Resource
	private ShopCartService shopCartService;

	private Product             product     = new Product();
	private Map<String, Object> productInfo = new HashMap<String, Object>();
	private User                user        = new User();
	private Long   priceId;
	private String startDate;
	private int    adultCount;
	private int    childCount;

	public Result order() {

		user = getLoginUser();
		productInfo = shopCartService.orderStraight(product, priceId, startDate);
		if (productInfo == null) {
			logger.error("查询不到Product#" + product.getId());
			return redirect("/");
		}
		return dispatch();
	}

	public Result getDateList() {
		List<Map<String, Object>> list = shopCartService.getPriceDateList(product.getProType(), priceId);
		return json(JSONArray.fromObject(list));
	}

	public Product getProduct() {
		return product;
	}

	public void setProduct(Product product) {
		this.product = product;
	}

	public Map<String, Object> getProductInfo() {
		return productInfo;
	}

	public void setProductInfo(Map<String, Object> productInfo) {
		this.productInfo = productInfo;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Long getPriceId() {
		return priceId;
	}

	public void setPriceId(Long priceId) {
		this.priceId = priceId;
	}

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public int getAdultCount() {
		return adultCount;
	}

	public void setAdultCount(int adultCount) {
		this.adultCount = adultCount;
	}

	public int getChildCount() {
		return childCount;
	}

	public void setChildCount(int childCount) {
		this.childCount = childCount;
	}

	@Override
	public Product getModel() {
		return product;
	}

	public User getLoginUser() {
		User user = (User) getSession().getAttribute(UserConstans.CURRENT_LOGIN_USER);
		if (user != null) {
			return user;
		} else {
			return null;
		}
	}
}
