package com.spark.service.spark;

import java.io.Serializable;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Semaphore;

import javax.annotation.Resource;

import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.mapreduce.lib.partition.HashPartitioner;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.api.java.JavaSQLContext;
import org.apache.spark.sql.hive.api.java.JavaHiveContext;

import scala.Tuple2;
import scala.reflect.ClassManifestFactory;
import scala.reflect.ClassTag;

import com.spark.service.hbase.pojo.WebRequest;
import com.sparkdemo.demo.Config;
import com.zuipin.util.PropertiesManager;

@SuppressWarnings("serial")
public enum SparkFactory implements Serializable {
	instance;
	/**
	 * 
	 */
	private static final long							serialVersionUID				= 5556748609998160399L;
	private JavaPairRDD<Long, Iterable<WebRequest>>		webRequestRdds;
	private RDD<Tuple2<ImmutableBytesWritable, Result>>	hBaseRDD;
	public static ClassTag<WebRequest>					webRequestClassTag				= ClassManifestFactory.fromClass(WebRequest.class);
	private ClassTag<ImmutableBytesWritable>			immutableBytesWritableClassTag	= ClassManifestFactory.fromClass(ImmutableBytesWritable.class);
	private ClassTag<Result>							resultClassTag					= ClassManifestFactory.fromClass(Result.class);
	@Resource
	private PropertiesManager							propertiesManager;
	private HashPartitioner								partitioner						= new HashPartitioner();
	private Semaphore									saleSemaphore					= new Semaphore(1);
	private Semaphore									userSemaphore					= new Semaphore(1);
	private Semaphore									userInOutSemaphore				= new Semaphore(1);
	public static Boolean								saleInitFinish					= false;
	public static Boolean								userInitFinish					= false;
	public static Boolean								webRequestInitFinish			= false;
	
	public JavaSparkContext								sc;
	public JavaSQLContext								sqlContext;
	public JavaHiveContext								hiveContext;
	public static boolean								run								= true;
	public final BlockingQueue<WebRequest>				queue							= new ArrayBlockingQueue<WebRequest>(10000);
	
	SparkFactory() {
		
		SparkConf conf = new SparkConf();
		conf.set("spark.scheduler.mode", "FAIR");
		conf.set("spark.scheduler.allocation.file", String.format("%s/conf/fairscheduler.xml", Config.SPARK_HOME));
		conf.set("spark.scheduler.pool", "default");
		conf.setSparkHome(Config.SPARK_HOME);
		conf.setJars(JavaSparkContext.jarOfClass(SparkFactory.class));
		sc = new JavaSparkContext(Config.SPARK_MASTER, "DATA ANALYSIS", conf);
		// hiveContext = new JavaHiveContext(sc);
		// JavaStreamingContext ssc = new JavaStreamingContext(sc, new Duration(10000));
		// JavaReceiverInputDStream<WebRequest> rids = ssc.receiverStream(new MyRequestReceiver());
		// // rids.dstream().rememberDuration();
		// DStream<WebRequest> dstream = rids.dstream();
		// dstream.register();
		// // ssc.ssc().graph().addOutputStream(dstream);
		// rids.foreachRDD(new Function<JavaRDD<WebRequest>, Void>() {
		// public Void call(JavaRDD<WebRequest> rdd) throws Exception {
		// List<WebRequest> collect = rdd.collect();
		// int size = collect.size();
		// if (size > 0) {
		// LogFactory.getLog(ReceiverStreamThread.class).info(String.format("collect size is %d", size));
		// JavaRDD<WebRequest> parallelize = SparkFactory.instance.sc.parallelize(collect, 3);
		// WebRequestRddService webRequestRddService = SpringContextHolder.getBean("webRequestRddService");
		// webRequestRddService.appendWebrequestRdd(parallelize);
		// }
		// return null;
		// }
		// });
		// ssc.start();
		
		// SparkConf sparkConf = new
		// SparkConf().setAppName("JavaNetworkWordCount").setMaster(Config.SPARK_MASTER).setSparkHome(System.getenv("SPARK_HOME"))
		// .set("spark.ui.port", "4041").set("spark.streaming.unpersist",
		// "false").set("spark.streaming.blockInterval", "30000");
		
		// new ReceiverStreamThread().start();
		
		// JavaReceiverInputDStream<WebRequest> rids = ssc
		// .receiverStream(new MyRequestReceiver());
		// rids.print();
		// ssc.start();
		// rids.dstream().rememberDuration();
		// rids.foreachRDD(new Function<JavaRDD<WebRequest>, Void>() {
		// public Void call(JavaRDD<WebRequest> rdd) throws Exception {
		// System.out.println("==============" + rdd.collect().size()
		// + "=========");
		// // webRequestRddService.appendWebrequestRdd(rdd);
		// return null;
		// }
		// });
		// rids.map(new Function<WebRequest, WebRequest>() {
		// @Override
		// public WebRequest call(WebRequest req) throws Exception {
		// System.out.println("==============" + req.getBrowser() + "=========");
		// return req;
		// }
		// });
		
	}
	
	public void startReceiver() {
	}
	
	public void receive(WebRequest request) {
		queue.add(request);
	}
	
	public void start() {
		System.out.println("Spark is started");
	}
	
}
