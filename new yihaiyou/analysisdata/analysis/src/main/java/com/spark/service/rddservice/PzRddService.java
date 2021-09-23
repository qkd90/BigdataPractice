package com.spark.service.rddservice;

import java.io.Serializable;

import org.apache.spark.api.java.JavaRDD;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.spark.service.spark.SparkFactory;
import com.zuipin.util.PropertiesManager;

@Service
public class PzRddService implements Serializable {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6817447848059327607L;
	@Autowired
	private PropertiesManager	propertiesManager;
	
	public String findPz() {
		JavaRDD<String> rdd = SparkFactory.instance.sc.textFile("hdfs://s00022:8020/hbase/hbase.version");
		String jarPath = propertiesManager.getString("jarpath");
		// return String.valueOf(rdd.map(new MapLoadPZStrategyFromJarAndRunIt(runtime, jarPath)).collect());
		return null;
	}
	
}
