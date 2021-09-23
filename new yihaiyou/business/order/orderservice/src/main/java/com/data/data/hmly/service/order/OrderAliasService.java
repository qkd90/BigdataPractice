package com.data.data.hmly.service.order;

import com.data.data.hmly.service.order.dao.OrderAliasDao;
import com.data.data.hmly.service.order.entity.OrderAlias;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by guoshijie on 2015/11/13.
 */
@Service
public class OrderAliasService {

	@Resource
	private OrderAliasDao orderAliasDao;

	public void saveAll(List<OrderAlias> orderAliases) {
		orderAliasDao.save(orderAliases);
	}

	public void save(OrderAlias orderAlias) {
		orderAliasDao.save(orderAlias);
	}


}
