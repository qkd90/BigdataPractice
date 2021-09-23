package com.sparkdemo.demo;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.mapred.JobConf;

public class Config {
	public static String	HADOOP_MASTER	= "";
	public static String	SPARK_MASTER	= "";
	public static String	SPARK_HOME		= "";
	
	public static Configuration config(String jobName) {
		JobConf conf = new JobConf(Config.class);
		conf.setJobName(jobName);
		conf.addResource("classpath:/core-site.xml");
		conf.addResource("classpath:/hdfs-site.xml");
		conf.addResource("classpath:/mapred-site.xml");
		conf.set("mapred.jop.tracker", "hdfs://" + HADOOP_MASTER + ":9001");
		conf.set("fs.defaultFS", "hdfs://" + HADOOP_MASTER + ":9000");
		Configuration config = new Configuration(conf);
		return config;
	}
	
}
