package com.hmlyinfo.app.soutu.plan.service;

import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.plan.domain.ShoppingCartItem;
import com.hmlyinfo.app.soutu.plan.mapper.ShoppingCartItemMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ShoppingCartItemService extends BaseService<ShoppingCartItem, Long> {

    @Autowired
    private ShoppingCartItemMapper<ShoppingCartItem> mapper;

    @Override
    public BaseMapper<ShoppingCartItem> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }

    public void addList(List<Map<String, Object>> itemList) {
        Long userId = MemberService.getCurrentUserId();
        for (Map<String, Object> map : itemList) {
            if (!StringUtils.isEmpty(map.get("id"))) {
                if (!StringUtils.isEmpty(map.get("status")) && !(Boolean) map.get("status")) {
                    del(map.get("id").toString());
                }
                continue;
            }
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem();
            shoppingCartItem.setUserId(userId);
            shoppingCartItem.setTicketId(Long.valueOf(map.get("ticketId").toString()));
            shoppingCartItem.setTicketType(Integer.valueOf(map.get("ticketType").toString()));
            shoppingCartItem.setPassengerId(Long.valueOf(map.get("passengerId").toString()));
            shoppingCartItem.setPlanId(Long.valueOf(map.get("planId").toString()));
            shoppingCartItem.setAdditional1((String) map.get("additional1"));
            shoppingCartItem.setAdditional2((String) map.get("additional2"));
            shoppingCartItem.setPlayDate(new Date((Long) map.get("playDate")));
            shoppingCartItem.setDays(Integer.valueOf(map.get("days").toString()));
            insert(shoppingCartItem);
        }
    }

	@Transactional
	public void deleteByPassengerId(String passengerId) {
		Map<String, Object> paramMap = new HashMap<String, Object>();

		paramMap.put("passengerId", passengerId);
		List<ShoppingCartItem> list = list(paramMap);
		for (ShoppingCartItem shoppingCartItem : list) {
			del(shoppingCartItem.getId().toString());
		}
	}
}
