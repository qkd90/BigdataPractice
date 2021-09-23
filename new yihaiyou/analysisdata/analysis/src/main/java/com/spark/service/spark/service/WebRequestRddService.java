package com.spark.service.spark.service;

import java.io.Serializable;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.sql.api.java.JavaSchemaRDD;
import org.apache.spark.sql.api.java.Row;
import org.springframework.stereotype.Service;

import com.spark.service.hbase.pojo.WebRequest;
import com.spark.service.spark.SparkFactory;

@SuppressWarnings("serial")
@Service
public class WebRequestRddService implements Serializable {
	
	private JavaRDD<WebRequest>	requests;
	
	public WebRequestRddService() {
		// TODO Auto-generated constructor stub
		// new WebrequestHBase().start();
	}
	
	/**
	 * @param requests
	 *            添加webrequest请求到rdd当中
	 */
	public void appendWebrequestRdd(List<WebRequest> reqs) {
		JavaRDD<WebRequest> appendRequests = SparkFactory.instance.sc.parallelize(reqs);
		appendWebrequestRdd(appendRequests);
	}
	
	public void setRequests(JavaRDD<WebRequest> requests) {
		this.requests = requests;
	}
	
	public void appendWebrequestRdd(JavaRDD<WebRequest> rdd) {
		if (requests == null) {
			requests = rdd;
			
			// System.out.println(requests.count());
		} else {
			
			JavaRDD<WebRequest> reqs = requests.union(rdd).cache().repartition(3);
			reqs.first();
			// System.out.println(reqs.count());
			requests = reqs;
		}
		// JavaSchemaRDD schemaRequest = SparkFactory.instance.sqlContext.applySchema(requests, WebRequest.class);
		// schemaRequest.registerAsTable("req");
	}
	
	public JavaRDD<WebRequest> getRequests() {
		return requests;
	}
	
	public List<String> count() {
		JavaSchemaRDD result = SparkFactory.instance.sqlContext.sql("select count(*) from req");
		List<String> list = result.map(new Function<Row, String>() {
			public String call(Row row) {
				return "Count: " + row.getLong(0);
			}
		}).collect();
		return list;
	}
	
}
