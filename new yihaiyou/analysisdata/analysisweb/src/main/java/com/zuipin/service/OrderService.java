package com.zuipin.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zuipin.service.dao.OrderDao;

@Service
public class OrderService {
	@Resource
	private OrderDao	orderDao;
	
	public void test() {
		
	}
}
