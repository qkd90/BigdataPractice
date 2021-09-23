package com.zuipin.util;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public enum GlobalTheadPool {

	instance;
	private final ExecutorService service = Executors.newFixedThreadPool(150);
	private ThreadLocal threadLocal = new ThreadLocal();

	public <T> Future<T> submit(Callable<T> task) {
		return service.submit(task);
	}

	public void set(Object o){
		threadLocal.set(o);
	}

	public <T> T get(){
		return (T)threadLocal.get();
	}

}
