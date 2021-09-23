package com.zuipin.action.collection;

import javax.annotation.Resource;

import org.springframework.data.redis.core.RedisTemplate;

import com.google.common.base.Stopwatch;
import com.opensymphony.xwork2.Result;
import com.spark.service.spark.service.WebRequestRddService;
import com.zuipin.action.JxmallAction;

public class TestAction extends JxmallAction {
	@Resource
	private WebRequestRddService	webRequestRddService;
	private Stopwatch				stopwatch	= new Stopwatch();
	private Integer					count		= 0;
	private Double					kk			= 1d;
	private Float					ff			= 2f;
	private Float					bb			= 2f;
	private Float					cc			= 9f;
	@Resource
	private RedisTemplate			redisTemplate;
	
	public Result test() {
		return text(String.valueOf(cc));
	}
	
	public Result redis() {
		redisTemplate.opsForHash().putIfAbsent("aa", "key", "bb");
		StringBuilder sb = new StringBuilder();
		// redisTemplate.opsForHash().get("aa", "key");
		return text(String.valueOf(redisTemplate.opsForHash().get("aa", "key")));
	}
	
}
