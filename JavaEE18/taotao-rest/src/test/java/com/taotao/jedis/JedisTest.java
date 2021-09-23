package com.taotao.jedis;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.taotao.rest.component.JedisClient;

import redis.clients.jedis.HostAndPort;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisCluster;
import redis.clients.jedis.JedisPool;

public class JedisTest {
	
	// 单机版测试
	@Test
	public void testJedisSingle() throws Exception {
		// 创建一个 Jedis 对象
		Jedis jedis = new Jedis("192.168.25.133", 6379);
		jedis.set("test", "hello jedis");
		String string = jedis.get("test");
		System.out.println(string);
		jedis.close();
	}
	
	// 使用连接池
	@Test
	public void testJedisPool() throws Exception {
		// 创建一个连接池对象
		// 系统中应该是单例的
		JedisPool jedisPool = new JedisPool("192.168.25.133", 6379);
		// 从连接池中获得一个连接
		Jedis jedis = jedisPool.getResource();
		String result = jedis.get("test");
		System.out.println(result);
		// Jedis 必须关闭
		jedis.close();
		
		// 系统关闭时关闭连接池
		jedisPool.close();
	}
	
	// 连接 Redis 集群
	@Test
	public void testJedisCluster() throws Exception {
		// 在 nodes 中指定每个节点的地址
		Set<HostAndPort> nodes = new HashSet<>();
		nodes.add(new HostAndPort("192.168.25.133", 7001));
		nodes.add(new HostAndPort("192.168.25.133", 7002));
		nodes.add(new HostAndPort("192.168.25.133", 7003));
		nodes.add(new HostAndPort("192.168.25.133", 7004));
		nodes.add(new HostAndPort("192.168.25.133", 7005));
		nodes.add(new HostAndPort("192.168.25.133", 7006));
		// 创建一个 JedisCluster 对象
		// jedisCluster 在系统中是单例的
		JedisCluster jedisCluster = new JedisCluster(nodes);
		jedisCluster.set("name", "zhangsan");
		jedisCluster.set("value", "100");
		String name = jedisCluster.get("name");
		String value = jedisCluster.get("value");
		System.out.println(name);
		System.out.println(value);
		
		// 系统关闭时关闭 jedisCluster
		jedisCluster.close();
	}
	
	@Test
	public void testJedisClientSpring() throws Exception {
		//创建一个spring容器
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		//从容器中获得JedisClient对象
		JedisClient jedisClient = applicationContext.getBean(JedisClient.class);
		//jedisClient操作redis
		jedisClient.set("cliet1", "1000");
		String string = jedisClient.get("cliet1");
		System.out.println(string);
	}

}
