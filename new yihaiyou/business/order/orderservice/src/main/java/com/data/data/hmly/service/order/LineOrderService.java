package com.data.data.hmly.service.order;

import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.LinetypepricedateService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.data.data.hmly.service.order.entity.OrderDetail;
import com.data.data.hmly.service.order.entity.enums.OrderCostPriceType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by guoshijie on 2015/10/29.
 */
@Service
public class LineOrderService extends ProductOrderService {

	@Resource
	private LineService              lineService;
	@Resource
	private LinetypepricedateService linetypepricedateService;

	@Override
	public boolean isNeedConfirm(OrderDetail orderDetail) {
		Line line = lineService.loadLine(orderDetail.getProduct().getId());
		orderDetail.setProduct(line);
		return "confirm".equals(line.getConfirmAndPay());
	}

	@Override
	public boolean saveOrderDetail(OrderDetail orderDetail) {

		return false;
	}

	@Override
	public void fixPrice(OrderDetail orderDetail) {
		Linetypepricedate linetypepricedate = linetypepricedateService.load(orderDetail.getCostId());
		if (orderDetail.getPriceType() == OrderCostPriceType.CHILD) {
			orderDetail.setUnitPrice(linetypepricedate.getChildPrice());
		} else {
			orderDetail.setUnitPrice(linetypepricedate.getDiscountPrice());
		}
		orderDetail.setCostName(orderDetail.getPriceType().getName());
		orderDetail.setTotalPrice(orderDetail.getUnitPrice() * orderDetail.getNum());
	}

	@Override
	public float getRebate(OrderDetail orderDetail) {
		Linetypepricedate linetypepricedate = linetypepricedateService.load(orderDetail.getCostId());
		if (orderDetail.getPriceType() == OrderCostPriceType.CHILD) {
			return linetypepricedate.getChildRebate();
		} else {
			return linetypepricedate.getRebate();
		}
	}


}
