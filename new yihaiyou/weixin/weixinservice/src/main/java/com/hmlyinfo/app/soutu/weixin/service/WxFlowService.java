package com.hmlyinfo.app.soutu.weixin.service;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hmlyinfo.app.soutu.base.properties.Config;
import com.hmlyinfo.app.soutu.weixin.domain.WxFlow;
import com.hmlyinfo.app.soutu.weixin.mapper.WxFlowMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;


@Service
public class WxFlowService extends BaseService<WxFlow, Long> {

	@Autowired
	private WxFlowMapper<WxFlow> mapper;

	/**
	 * 解析日志存入数据库
	 *
	 * @param
	 */
	@Transactional
	public void generateData(Date selDate) {
		File file = new File(Config.get("H5_ACCESS_LOG"));
		if (file != null) {
			File[] files = file.listFiles();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
			SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
			String str = "";
			for (File file1 : files) {
				if (file1.getName().equals("H5.access.log")) {
					continue;
				}
				String fileName = file1.getName().substring(file1.getName().indexOf("g.") + 2);
				try {
					Date fileNameDate = sdf2.parse(fileName);
					if (fileNameDate.getTime() <= selDate.getTime()) {
						FileReader fr = null;
						try {
							fr = new FileReader(file1);
						} catch (FileNotFoundException e) {
							e.printStackTrace();
						}
						BufferedReader br = new BufferedReader(fr);
						try {
							while ((str = br.readLine()) != null) {
								String[] arr = str.split(" ");
								if (arr[4].equals("/")) {
									continue;
								}
								String date = arr[0] + " " + arr[1].substring(0, arr[1].lastIndexOf(","));
								String url = arr[4];
								String responseTime = arr[5];
								String ip = arr[3];
								WxFlow wxFlow = new WxFlow();
								try {
									wxFlow.setDate(sdf.parse(date));
									wxFlow.setUrl(url);
									wxFlow.setResponseTime(responseTime);
									wxFlow.setIp(ip);
									wxFlow.setCreateTime(new Date());
									insert(wxFlow);
								} catch (ParseException e) {
									e.printStackTrace();
								}

							}
							br.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
				} catch (ParseException e) {
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 解析日志存入数据库
	 *
	 * @param
	 */
	@Transactional
	public void generateData2(Date maxDate, Date selDate) {
		SimpleDateFormat sdf2 = new SimpleDateFormat("yyyy-MM-dd");
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:ss:mm");
		File file = new File(Config.get("H5_ACCESS_LOG"));
		File[] files = file.listFiles();
		for (File file1 : files) {
			if (file1.getName().equals("H5.access.log")) {
				continue;
			}
			String fileName = file1.getName().substring(file1.getName().indexOf("g.") + 2);
			try {
				Date fileNameDate = sdf2.parse(fileName);
				if(fileNameDate.getTime() >= maxDate.getTime() && fileNameDate.getTime()<= selDate.getTime()){
					String str = "";
					FileReader fr = null;
					try {
						fr = new FileReader(file1);
					} catch (FileNotFoundException e) {
						e.printStackTrace();
					}
					BufferedReader br = new BufferedReader(fr);
					try {
						while ((str = br.readLine()) != null) {
							String[] arr = str.split(" ");
							if (arr[4].equals("/")) {
								continue;
							}
							String date = arr[0] + " " + arr[1].substring(0, arr[1].lastIndexOf(","));
							String url = arr[4];
							String responseTime = arr[5];
							String ip = arr[3];
							WxFlow wxFlow = new WxFlow();
							try {
								wxFlow.setDate(sdf.parse(date));
								wxFlow.setUrl(url);
								wxFlow.setResponseTime(responseTime);
								wxFlow.setIp(ip);
								wxFlow.setCreateTime(new Date());
								insert(wxFlow);
							} catch (ParseException e) {
								e.printStackTrace();
							}

						}
						br.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 微信点击pv和uv统计
	 * @return
	 */
	public List wxFlowList(Map<String, Object> paramMap) {
		List<WxFlow>  wxFlowList = mapper.wxFlowList(paramMap);
		return wxFlowList;
	}

	/**
	 * 微信页面pv和uv的统计
	 * @return
	 */
	public List wxFlowVistorList(Map<String, Object> paramMap){
		List<WxFlow> wxFlowList = mapper.wxFlowVistorList(paramMap);
		return wxFlowList;
	}

	/**
	 * 获取最大的日期时间
	 * @param paramMap
	 * @return
	 */
	public List maxDateList(Map<String, Object> paramMap) {
		List<WxFlow> maxDateList = mapper.maxDateList(paramMap);
		return maxDateList;
	}

	/**
	 * 获取最大的日期时间
	 * @param paramMap
	 * @return
	 */
	public List maxDateList2(Map<String, Object> paramMap) {
		List<WxFlow> maxDateList = mapper.maxDateList2(paramMap);
		return maxDateList;
	}
	
	@Override
	public BaseMapper<WxFlow> getMapper()
	{
		return mapper;
	}

	@Override
	public String getKey()
	{
		return "id";
	}
}
