package com.hmlyinfo.app.soutu.order.service;

import com.hmlyinfo.app.soutu.order.domain.OrderPassenger;
import com.hmlyinfo.app.soutu.order.mapper.OrderPassengerMapper;
import com.hmlyinfo.app.soutu.plan.service.ShoppingCartItemService;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
public class OrderPassengerService extends BaseService<OrderPassenger, Long> {

    @Autowired
    private OrderPassengerMapper<OrderPassenger> mapper;
	@Autowired
	private ShoppingCartItemService shoppingCartItemService;

    @Override
    public BaseMapper<OrderPassenger> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

	@Transactional
    public void addList(List<Map<String, Object>> list, Long userId) {
        for (Map<String, Object> map : list) {
            OrderPassenger orderPassenger = new OrderPassenger();
            orderPassenger.setUserId(userId);
            orderPassenger.setName((String) map.get("name"));
            orderPassenger.setPhone((String) map.get("phone"));
            orderPassenger.setIdCard((String) map.get("idCard"));
            orderPassenger.setPassport((String) map.get("passport"));
            orderPassenger.setHkAndMacauPermit((String) map.get("hkAndMacauPermit"));
            orderPassenger.setTaiwanPermit((String) map.get("taiwanPermit"));
            orderPassenger.setPinyin((String) map.get("pinyin"));
            orderPassenger.setUserDefinedI((String) map.get("userDefinedI"));
            orderPassenger.setUserDefinedIi((String) map.get("userDefinedIi"));
            insert(orderPassenger);
        }
    }

	@Transactional
	public void delete(String passengerId) {
		shoppingCartItemService.deleteByPassengerId(passengerId);
		del(passengerId);
	}
}
