package com.spark.service.spark.hbase;

import java.io.IOException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.HConstants;
import org.apache.hadoop.hbase.client.Scan;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.protobuf.ProtobufUtil;
import org.apache.hadoop.hbase.protobuf.generated.ClientProtos;
import org.apache.hadoop.hbase.util.Base64;

import scala.Serializable;

import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;

public abstract class SparkHBaseBase extends Thread implements Serializable {
	
	public abstract String getTableName();
	
	protected Log getLog() {
		return LogFactory.getLog(getClass());
	}
	
	protected Configuration createConfig() throws IOException {
		Configuration conf = HBaseConfiguration.create();
		PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");
		conf.setInt(HConstants.ZOOKEEPER_CLIENT_PORT, propertiesManager.getInteger("hbase.port"));
		conf.set(HConstants.ZOOKEEPER_QUORUM, propertiesManager.getString("hbase.host"));
		conf.set(TableInputFormat.INPUT_TABLE, getTableName());
		return conf;
	}
	
	protected static String convertScanToString(Scan scan) throws IOException {
		ClientProtos.Scan proto = ProtobufUtil.toScan(scan);
		return Base64.encodeBytes(proto.toByteArray());
	}
	
}