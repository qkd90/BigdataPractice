package com.spark.service.spark.receiver;

import org.apache.commons.logging.LogFactory;
import org.apache.spark.api.java.StorageLevels;
import org.apache.spark.streaming.receiver.Receiver;

import com.spark.service.hbase.pojo.WebRequest;
import com.spark.service.spark.SparkFactory;

public class MyRequestReceiver extends Receiver<WebRequest> {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8403603768472601090L;
	
	public MyRequestReceiver() {
		super(StorageLevels.MEMORY_ONLY_SER);
	}
	
	@Override
	public void onStart() {
		LogFactory.getLog(MyRequestReceiver.class).info("Start My Request Receiver");
		new Thread() {
			public void run() {
				receive();
			};
		}.start();
	}
	
	@Override
	public void onStop() {
		
	}
	
	private void receive() {
		while (SparkFactory.run) {
			try {
				WebRequest request = SparkFactory.instance.queue.take();
				store(request);
			} catch (InterruptedException e) {
				LogFactory.getLog(MyRequestReceiver.class).error(e.getMessage(), e);
			}
		}
	}
	
}
