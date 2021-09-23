package com.zuipin.action.testsale;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Random;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class WeightTreeInfo<Month> implements Iterable<Month> {
	
	private static final int				DEFAULT_WEIGHT	= 100;
	
	private final Random					random			= new Random();
	
	private final SortedMap<Integer, Month>	nodes			= new TreeMap<Integer, Month>();
	
	private int								maxWeight		= 0;
	
	public WeightTreeInfo<Month> putNode(Month node, int weight) {
		if (weight < 0) {
			throw new RuntimeException("weight must be > 0");
		}
		maxWeight += weight;
		nodes.put(maxWeight, node);
		return this;
	}
	
	public Month getNode() {
		int resultIndex = random.nextInt(maxWeight);
		Month ds = null;
		for (Entry<Integer, Month> entry : nodes.entrySet()) {
			if (entry.getKey() > resultIndex) {
				ds = entry.getValue();
				break;
			}
		}
		return ds;
	}
	
	@Override
	public Iterator<Month> iterator() {
		return nodes.values().iterator();
	}
	
	public Set<Entry<Integer, Month>> entrySet() {
		return nodes.entrySet();
	}
	
	@Override
	public String toString() {
		return nodes.toString();
	}
	
	public static <T> WeightTreeInfo<T> buildSingleOne(T one) {
		WeightTreeInfo<T> result = new WeightTreeInfo<T>();
		result.putNode(one, DEFAULT_WEIGHT);
		return result;
	}
	
	public SortedMap<Integer, Month> getNodes() {
		return nodes;
	}
	
	public static void main(String[] args) {
		final WeightTreeInfo<String> weightTreeInfo = new WeightTreeInfo<String>();
		// 构建结构
		weightTreeInfo//
				.putNode("192.168.0.1", 15) //
				.putNode("192.168.0.2", 5) //
				.putNode("192.168.0.3", 25) //
				.putNode("192.168.0.4", 55);
		// 测试构建的结果
		final Map<String, AtomicLong> stats = new HashMap<String, AtomicLong>();
		stats.put("192.168.0.1", new AtomicLong());
		stats.put("192.168.0.2", new AtomicLong());
		stats.put("192.168.0.3", new AtomicLong());
		stats.put("192.168.0.4", new AtomicLong());
		final AtomicLong fail = new AtomicLong();
		final AtomicLong totalCost = new AtomicLong();
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		for (int i = 0; i <= 1000000; i++) {
			executorService.execute(new Runnable() {
				
				@Override
				public void run() {
					long t = System.currentTimeMillis();
					String getNode = weightTreeInfo.getNode();
					totalCost.addAndGet(System.currentTimeMillis() - t);
					AtomicLong stat = stats.get(getNode);
					if (stat != null) {
						stat.incrementAndGet();
					} else {
						fail.incrementAndGet();
					}
				}
			});
		}
		executorService.shutdown();
		try {
			executorService.awaitTermination(1, TimeUnit.HOURS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 输出测试结果
		System.out.println(stats);
		System.out.println(fail);
		System.out.println(totalCost);
		System.exit(0);
	}
}