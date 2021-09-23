package com.zuipin.service;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.zuipin.service.dao.PingguResultDao;
import com.zuipin.service.entity.PingguResult;

@Service
public class PingguResultService {
	
	@Resource
	private PingguResultDao	pingguResultDao;
	
	public void saveAll(List<PingguResult> list) {
		pingguResultDao.save(list);
	}
	
}
