package com.spark.service;

import java.io.Serializable;
import java.util.List;

import org.apache.commons.logging.LogFactory;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.streaming.Duration;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.dstream.DStream;

import scala.reflect.ClassManifestFactory;
import scala.reflect.ClassTag;

import com.spark.service.hbase.pojo.WebRequest;
import com.spark.service.spark.SparkFactory;
import com.spark.service.spark.receiver.MyRequestReceiver;
import com.spark.service.spark.service.WebRequestRddService;
import com.zuipin.util.SpringContextHolder;

public class ReceiverStreamThread extends Thread implements Serializable {
	
	/**
	 * 
	 */
	private static final long			serialVersionUID	= -4983564860422129841L;
	public static ClassTag<WebRequest>	requestClassTag		= ClassManifestFactory.fromClass(WebRequest.class);
	
	public void run() {
		JavaStreamingContext ssc = new JavaStreamingContext(SparkFactory.instance.sc, new Duration(10000));
		JavaReceiverInputDStream<WebRequest> rids = ssc.receiverStream(new MyRequestReceiver());
		// rids.dstream().rememberDuration();
		DStream<WebRequest> dstream = rids.dstream();
		dstream.register();
		// ssc.ssc().graph().addOutputStream(dstream);
		rids.foreachRDD(new Function<JavaRDD<WebRequest>, Void>() {
			public Void call(JavaRDD<WebRequest> rdd) throws Exception {
				List<WebRequest> collect = rdd.collect();
				int size = collect.size();
				if (size > 0) {
					LogFactory.getLog(ReceiverStreamThread.class).info(String.format("collect size is %d", size));
					JavaRDD<WebRequest> parallelize = SparkFactory.instance.sc.parallelize(collect, 3);
					WebRequestRddService webRequestRddService = SpringContextHolder.getBean("webRequestRddService");
					webRequestRddService.appendWebrequestRdd(parallelize);
				}
				return null;
			}
		});
		// rids.print();
		// dstream.saveAsTextFiles("a", "bb");
		// rids.foreach(new Function<JavaRDD<WebRequest>, Void>() {
		// @Override
		// public Void call(JavaRDD<WebRequest> rdd) throws Exception {
		// LogFactory.getLog(ReceiverStreamThread.class).info(String.format("=====%d====", rdd.collect().size()));
		// rdd.cache();
		// WebRequestRddService webRequestRddService = SpringContextHolder.getBean("webRequestRddService");
		// webRequestRddService.appendWebrequestRdd(rdd);
		// return null;
		// }
		// });
		ssc.start();
		// ssc.awaitTermination();
		// rids.dstream().rememberDuration();
	}
	
	public static void main(String[] args) {
		new ReceiverStreamThread().run();
	}
}