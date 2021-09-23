package com.spark.service.spark.hbase;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.Transient;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.function.Function;

import scala.Tuple2;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.spark.service.hbase.pojo.HBaseEntity;
import com.spark.service.hbase.pojo.WebRequest;
import com.spark.service.spark.SparkFactory;
import com.spark.service.spark.service.WebRequestRddService;
import com.zuipin.util.DateUtils;
import com.zuipin.util.SpringContextHolder;

public class WebrequestHBase extends SparkHBaseBase {
	
	@Override
	public void run() {
		try {
			Configuration createConfig = createConfig();
			Scan scan = new Scan();
			scan.setStartRow(Bytes.toBytes("2014-09-17 00:00:0000000000000000000000000000000000000000000000"));
			scan.setStopRow(Bytes.toBytes("2014-09-18 00:00:0000000000000000000000000000000000000000000000"));
			scan.addFamily(Bytes.toBytes(HBaseEntity.getFamilyName()));
			scan.addColumn(Bytes.toBytes(HBaseEntity.getFamilyName()), Bytes.toBytes("key"));
			createConfig.set(TableInputFormat.SCAN, convertScanToString(scan));
			JavaPairRDD<ImmutableBytesWritable, Result> requestBaseRDD = SparkFactory.instance.sc.newAPIHadoopRDD(createConfig, TableInputFormat.class,
					ImmutableBytesWritable.class, Result.class);
			JavaRDD<WebRequest> requests = requestBaseRDD.map(new Function<Tuple2<ImmutableBytesWritable, Result>, WebRequest>() {
				@Override
				public WebRequest call(Tuple2<ImmutableBytesWritable, Result> tuple) throws Exception {
					Result result = tuple._2();
					Field[] fields = WebRequest.class.getDeclaredFields();
					WebRequest request = new WebRequest();
					for (Field field : fields) {
						field.setAccessible(true);
						// byte[] visiday = result.getValue(TotalVisitor.family_visitor, TotalVisitor.col_visiday);
						if (!field.isAnnotationPresent(Transient.class)) {
							if (!field.isAnnotationPresent(Id.class)) {
								setFieldBytes(request, field, result.getValue(Bytes.toBytes("default"), Bytes.toBytes(field.getName())));
							} else {
								setFieldBytes(request, field, result.getRow());
							}
						}
					}
					return request;
				}
				
				private <T> T setFieldBytes(T t, Field field, byte[] rowKey) throws IllegalAccessException {
					try {
						if (rowKey == null) {
							return t;
						}
						field.setAccessible(true);
						Object value = null;
						if (field.getType().isAssignableFrom(Double.class)) {
							value = new Double(Bytes.toDouble(rowKey));
						} else if (field.getType().isAssignableFrom(Float.class)) {
							value = new Float(Bytes.toFloat(rowKey));
						} else if (field.getType().isAssignableFrom(String.class)) {
							value = Bytes.toString(rowKey);
						} else if (field.getType().isAssignableFrom(Integer.class)) {
							value = new Integer(Bytes.toInt(rowKey));
						} else if (field.getType().isAssignableFrom(Boolean.class)) {
							value = new Boolean(Bytes.toBoolean(rowKey));
						} else if (field.getType().isAssignableFrom(Long.class)) {
							value = new Long(Bytes.toLong(rowKey));
						} else if (field.getType().isAssignableFrom(Short.class)) {
							value = new Short(Bytes.toShort(rowKey));
						} else if (field.getType().isAssignableFrom(Date.class)) {
							value = DateUtils.getDate(Bytes.toString(rowKey), "yyyy-MM-dd HH:mm:ss");
						} else if (field.getType().isAssignableFrom(List.class)) {
							String back = Bytes.toString(rowKey);
							JSONArray result = JSON.parseArray(back);
							Class clazz = (Class) ((ParameterizedType) (field.getGenericType())).getActualTypeArguments()[0];
							List<Object> lists = new ArrayList();
							for (int i = 0; i < result.size(); i++) {
								Object v = JSON.parseObject(result.getString(i), clazz);
								lists.add(v);
							}
							value = lists;
						}
						field.set(t, value);
					} catch (Exception e) {
					}
					return t;
				}
			});
			requests.cache();
			System.out.println(requests.count());
			WebRequestRddService webRequestRddService = SpringContextHolder.getBean("webRequestRddService");
			webRequestRddService.appendWebrequestRdd(requests);
			
			getLog().error("================WebRequest init finish======================");
			
		} catch (Exception e) {
			getLog().error(e.getMessage(), e);
		}
	}
	
	@Override
	public String getTableName() {
		return WebRequest.class.getSimpleName();
	}
	
}
