package com.sparkdemo.demo;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;

import com.spark.service.spark.SparkFactory;

public class SparkYarnTest {
	
	public static void main(String[] args) {
		String SPARK_HOME = System.getenv("SPARK_HOME");
		JavaSparkContext sc = new JavaSparkContext(args[0], "DATA ANALYSIS", SPARK_HOME, JavaSparkContext.jarOfClass(SparkFactory.class));
		JavaRDD<String> rdd = sc.textFile("hdfs://s00023:8020/hbase/hbase.version");
		
		// JavaRDD<String> rdd = sc.textFile("hdfs://http://s00021/:8020/users/chrome.txt");
		System.out.println(rdd.map(new Function<String, String>() {
			/**
			 * 
			 */
			private static final long	serialVersionUID	= 1970713787727649947L;
			
			@Override
			public String call(String str) throws Exception {
				System.out.println(str);
				return str;
			}
		}).collect());
		
	}
	
}
