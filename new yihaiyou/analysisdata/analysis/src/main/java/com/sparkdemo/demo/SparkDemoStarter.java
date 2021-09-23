package com.sparkdemo.demo;

import java.io.IOException;

import org.eclipse.jetty.server.Connector;
import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.server.bio.SocketConnector;
import org.eclipse.jetty.webapp.WebAppContext;
import org.springframework.core.io.ClassPathResource;

import com.spark.service.spark.SparkFactory;

public class SparkDemoStarter {
	
	public static void main(String[] args) throws IOException {
		if (args.length < 3) {
			System.out.println("Useage : java -jar jarfile hadoopmaster sparkmaster ");
			System.exit(0);
		}
		
		Config.HADOOP_MASTER = args[1];
		Config.SPARK_MASTER = args[2];
		if (args[4].length() > 1) {
			Config.SPARK_HOME = args[4];
		} else {
			Config.SPARK_HOME = System.getProperty("SPARK_HOME", System.getenv("SPARK_HOME"));
		}
		// SparkFactory spark = SparkFactory.instance;
		// spark.initSaleInfo();
		Server server = new Server();
		try {
			Connector conn = new SocketConnector();
			conn.setPort(Integer.parseInt(args[0]));
			server.setConnectors(new Connector[] { conn });
			WebAppContext webapp = new WebAppContext();
			webapp.setContextPath("/");
			if (args[3].length() > 1) {
				webapp.setResourceBase(args[3]);
			} else {
				webapp.setResourceBase(new ClassPathResource("webapp").getURI().toString());
			}
			server.setHandler(webapp);
			server.start();
			SparkFactory.instance.start();
			server.join();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
