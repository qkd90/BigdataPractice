package com.zuipin.service.dao;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.stereotype.Repository;

import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Page;
import com.zuipin.pojo.Order;
import com.zuipin.pojo.VUserProduct;

@Repository
public class UserProductDao extends DataAccess<Order> {
	private final static Log	log	= LogFactory.getLog(UserProductDao.class);
	
	public List<VUserProduct> find(Page page) {
		Map<String, Type> types = new HashMap<String, Type>();
		types.put("userId", StandardBasicTypes.LONG);
		types.put("itemId", StandardBasicTypes.LONG);
		types.put("preference", StandardBasicTypes.FLOAT);
		List<VUserProduct> users = findEntitiesBySQL(page, "select userId \"userId\",itemId \"itemId\",preference \"preference\" from vUserProducts", VUserProduct.class, types,
				null);
		// log.info(users.size());
		return users;
	}
	
}
