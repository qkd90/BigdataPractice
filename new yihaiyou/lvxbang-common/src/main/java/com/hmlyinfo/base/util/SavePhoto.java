package com.hmlyinfo.base.util;

import org.apache.log4j.Logger;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ConcurrentMap;

/**
 * Created by guoshijie on 2014/10/10.
 */
@Controller
public class SavePhoto {

	Logger logger = Logger.getLogger(this.getClass());

	String driver = "com.mysql.jdbc.Driver";
	String url = "jdbc:mysql://192.168.0.222:3306/soutuu_server?rewriteBatchedStatements=true";
	String user = "root";
	String password = "Hmly2013";

	ConcurrentMap<String, Object> photoMap = new ConcurrentHashMap<String, Object>();
	ConcurrentLinkedQueue<String> aidQueue = new ConcurrentLinkedQueue<String>();
	List<String> savedList = new ArrayList<String>();
	int status = 0;
	String processingAId = null;
	String errorMsg = null;

	@RequestMapping("/test/uploadPhoto")
	@ResponseBody
	public String uploadPhoto(@RequestParam String photos) throws IOException {
		Map<String, Object> map = new ObjectMapper().readValue(photos, Map.class);
		aidQueue.add(map.get("aid").toString());
		photoMap.put(map.get("aid").toString(), map.get("list"));
		return "success";
	}

	@RequestMapping("/test/start")
	@ResponseBody
	public String startTask() {
		status = 1;
		uploadPhoto();
		return "success";
	}

	@RequestMapping("/test/stop")
	@ResponseBody
	public String endTask() {
		status = -1;
		return "success";
	}

	@RequestMapping("/test/monitor")
	@ResponseBody
	public String monitorTask() throws UnsupportedEncodingException {
		if (errorMsg != null) {
			return errorMsg;
		}
		StringBuilder b = new StringBuilder();
		b.append("<p>Now processing: ").append(processingAId).append("</p>");
		b.append("<p>there are ").append(aidQueue.size()).append(" aid in queue").append("</p>");
		b.append("<p>saved ").append(savedList.size()).append(" aid").append("</p>");
		if (savedList.size() > 0) {
			b.append("<p>the last one saved is: ").append(savedList.get(savedList.size() - 1)).append("</p>");
		}
		return b.toString();
	}

	private void uploadPhoto() {

		new Thread() {
			@Override
			public void run() {
				logger.info("starting upload photos");
				try {
					Class.forName(driver);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					errorMsg = e.getMessage();
				}
				try {
					FileWriter writer = new FileWriter(new File("f:\\task\\task.txt"));
					Connection conn = DriverManager.getConnection(url, user, password);
					String sql = "insert into tmp_baidu_pic(aid,desc,pic_url,width,height,size,is_cover,pic_id) values(?,?,?,?,?,?,?,?)";
					PreparedStatement statement = conn.prepareStatement(sql);
					while (true) {
						String aid = aidQueue.poll();
						if (aid == null) {
							if (status == -1) {
								break;
							}
							try {
								Thread.sleep(100000);
							} catch (InterruptedException e) {
								e.printStackTrace();
								break;
							}
							continue;
						}
						logger.info("processing aid: " + aid);
						processingAId = aid;
						List<Map<String, Object>> list = (List<Map<String, Object>>) photoMap.get(aid);
						for (Map<String, Object> pic : list) {
							statement.setObject(1, pic.get("aid"));
							statement.setObject(2, pic.get("desc"));
							statement.setObject(3, pic.get("pic_url"));
							statement.setObject(4, pic.get("width"));
							statement.setObject(5, pic.get("height"));
							statement.setObject(6, pic.get("size"));
							statement.setObject(7, pic.get("is_cover"));
							statement.setObject(8, pic.get("pic_id"));
							statement.addBatch();
						}
						statement.executeBatch();
						logger.info("saved successful for aid: " + aid);
						statement.clearBatch();
						writer.write("aid: " + aid + ", inserted: " + list.size() + "\r\n");
						savedList.add(aid);
					}
				} catch (SQLException e) {
					e.printStackTrace();
					errorMsg = e.getMessage();
				} catch (IOException e) {
					e.printStackTrace();
					errorMsg = e.getMessage();
				}
				logger.info("task finished");
			}

		}.start();
	}
}
