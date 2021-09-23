package com.zuipin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.framework.hibernate.util.Page;
import com.zuipin.pojo.VUserProduct;
import com.zuipin.service.dao.UserProductDao;

@Service
public class UserProductService {
	@Resource
	private UserProductDao	userProductDao;
	
	public List<VUserProduct> find(Page page) {
		return userProductDao.find(page);
	}
	
}
