package com.data.spider.process;
/**
 * 去哪儿城市ID抓取
 * By ZZL
 * 2015.10.21
 */
import java.io.IOException;
import java.lang.reflect.Type;
import java.net.URLEncoder;
import java.text.DateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Semaphore;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.util.EntityUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.data.spider.service.DatataskService;
import com.data.spider.service.tb.TbStationService;
import com.data.spider.service.qunar.TmpCnCityService;
import com.data.spider.service.pojo.Datatask;
import com.data.spider.service.pojo.DatataskStatus;
import com.data.spider.service.pojo.Station;
import com.data.spider.service.pojo.TbStation;
import com.data.spider.service.pojo.TmpCnCity;
import com.data.spider.util.BaseSpiderProcess;
import com.data.spider.util.HttpClientUtils;
import com.framework.hibernate.util.Criteria;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.zuipin.util.SpringContextHolder;

public class QunarCityIDProcess extends BaseSpiderProcess {
	private final static DatataskService	datataskService				= SpringContextHolder.getBean("datataskService");
	private TmpCnCityService 				tmpCnCityService 			= SpringContextHolder.getBean("tmpCnCityService");
	private TbStationService 				tbStationService			= SpringContextHolder.getBean("tbStationService");
	public final static Semaphore			mutex						= new Semaphore(5);//预设5个许可信号量

	private final static String json_url = "http://travel.qunar.com/place/api/poi_search/%s/pois?poi_types=21"
									     + "&_id=%s&dist_type=6&offset=0&limit=100000&csrfToken=";
	
	//提取数字的正则表达式
	private final static String regEx = "[^0-9]";
	//提取数字的匹配模式
	private final static Pattern pattern = Pattern.compile(regEx);
	//提取数字的匹配器
	private Matcher matcher;
	
	public QunarCityIDProcess(Datatask datatask) {
		super(datatask);
		// TODO Auto-generated constructor stub
	}
	@Override
	public Datatask call() throws Exception {
		Semaphore mutex = getMutex();
		try {
			if (mutex != null) {
				////
				mutex.acquire();
			}
			String name = datatask.getName();
			String data_source_url = datatask.getUrl();
			if(data_source_url != null && !"".equals(data_source_url)){
				datatask.setXml(data_source_url);
				execute();
			}else {
				name = URLEncoder.encode(URLEncoder.encode(name, "UTF-8"), "UTF-8");
				String qunar_url = String.format("http://travel.qunar.com/search/all/%s",name);
				datatask.setUrl(qunar_url);
				////开始执行任务
				////
				go(datatask.getUrl());
				//////更新去哪儿城市id对应表
				Long id = Long.parseLong(datatask.getTag());
				updataTmpCnCity(id, null, datatask.getXml());
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		} finally {
			if (mutex != null) {
				mutex.release();
			}
		}
		////执行任务后,返回执行完成的任务
		return datatask;
	}
	@Override
	public void execute() {
		Long id = Long.parseLong(datatask.getTag());
		//获得抓取的城市URL
		String city_url = datatask.getUrl();
		try {
			if(city_url != null){
				//从抓取的城市地址中分离出城市id, 源URL示例: http://travel.qunar.com/p-cs299782-xiamen
				matcher = pattern.matcher(city_url);
				//取出数字id
				Long city_id = Long.parseLong(matcher.replaceAll("").trim());
				String _url = String.format(json_url, city_id, city_id);
				//从去哪儿网获得该城市的交通枢纽
				String json_str = getStationJson(city_id, _url);
//				System.err.println(json_str);
				JSONObject json_data = (JSONObject) JSON.parse(json_str);
				//获得有效交通枢纽json对象
				JSONObject station_map = (JSONObject) JSON.parse(json_data.get("data").toString());
				//获得有效交通枢纽json数据
				String station_list = ((JSONArray) JSON.parse(station_map.get("pois").toString())).toString();
				//清除无用的制表符
				station_list = station_list.replace("	", ",");
//				System.err.println(station_list);
				GsonBuilder gb  = new GsonBuilder();
				gb.registerTypeAdapter(java.util.Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG);
		        gb.registerTypeAdapter(java.util.Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG);
		        Gson gson = gb.create();
		        //解析出交通枢纽列表
				List<Station> stations = gson.fromJson(station_list, new TypeToken<List<Station>>(){}.getType());
//				System.err.println(station_map);
//				System.err.println(station_list);
//				System.err.println(stations.get(0).getCityName());
				//Station == > TbStation 使用getter and setter装配为TbStation(即TbTransportation)
				////
				//存到TbStation表即可/
				for(Station station:stations){
					TbStation tbStation = makeTransportation(station);
					tbStationService.save(tbStation);
				}
				//更新去哪儿城市id对应表
				updataTmpCnCity(id, city_id, null);
				//更新任务信息
				datatask.setStatus(DatataskStatus.SUCCESSED);
				datataskService.updateTask(datatask);
				System.err.println(datatask.getName() + "成功! 去哪儿ID:" + city_id);
			}else if(city_url == null){
				System.err.println("城市获取错误(可能是没有找到该城市!),城市id: "+datatask.getTag() + " 城市名称: " + datatask.getName());
				datatask.setStatus(DatataskStatus.FAILED);
				datataskService.updateTask(datatask);
			}
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
//			StackTraceElement[] err = e.getStackTrace();
//			System.err.println(err[0] + "\n" + err[1] + "\n" + err[3]);
			System.err.println("城市获取错误(可能是没有找到该城市!),城市id: "+datatask.getTag() + " 城市名称: " + datatask.getName());
			datatask.setStatus(DatataskStatus.FAILED);
			datataskService.updateTask(datatask);
		}
	}
	
	private void updataTmpCnCity(Long id,Long dataSourceId,String dataSourceUrl){
		Criteria<TmpCnCity> criteria = new Criteria<TmpCnCity>(TmpCnCity.class);
		criteria.eq("id", id);
		TmpCnCity tmpCnCity = tmpCnCityService.gets(1, criteria).get(0);
		if(dataSourceId != null){
			tmpCnCity.setDataSorceId(dataSourceId);
		}
		if(dataSourceUrl != null){
			tmpCnCity.setDataSourceUrl(dataSourceUrl);
		}
		tmpCnCityService.update(tmpCnCity);
	}
	//Gson适配器配置
	public class DateDeserializer implements JsonDeserializer<java.util.Date> {
		@Override
	    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
	        return new java.util.Date(json.getAsJsonPrimitive().getAsLong());
	    }
	}
	public class DateSerializer implements JsonSerializer<Date> {
		@Override
	    public JsonElement serialize(Date src, Type typeOfSrc, JsonSerializationContext context) {
	        return new JsonPrimitive(src.getTime());
	    }
	}
	public Gson create() {
        GsonBuilder gb = new GsonBuilder();
        gb.registerTypeAdapter(java.util.Date.class, new DateSerializer()).setDateFormat(DateFormat.LONG);
        gb.registerTypeAdapter(java.util.Date.class, new DateDeserializer()).setDateFormat(DateFormat.LONG);
        Gson gson = gb.create();
        return gson;
    }

	private String getStationJson(Long cityId,String url){
		String result = null;
		HttpResponse response;
		HttpGet httpGet = new HttpGet(url);
		HttpClient httpClient = HttpClientUtils.getHttpClient();
		try {
			response = httpClient.execute(httpGet);
			result = EntityUtils.toString(response.getEntity(), "UTF-8");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}

	private TbStation makeTransportation(Station station){
		TbStation tbStation = new TbStation();
		tbStation.setName(station.getName());
		tbStation.setLng(station.getLng());
		tbStation.setLat(station.getLat());
		tbStation.setGcjLng(station.getGlng());
		tbStation.setGcjLat(station.getGlat());
//		tbStation.setCityCode(null);
		tbStation.setRegionName(station.getCityName());
		tbStation.setAddress(station.getAddr());
		tbStation.setTelephone(station.getTel());
		tbStation.setCoverImg(station.getImage());
		tbStation.setType(getTransportationType(station.getStyle()));
//		tbStation.setArriveTime(null);
//		tbStation.setLeaveTime(null);
//		tbStation.setCreateTime(null);//数据库生成
//		tbStation.setModifyTime(null);//数据库生成
		tbStation.setDataSource("qunar");
		tbStation.setDataSourceUrl(datatask.getXml()+"-zhinan?jiaotong_menu=true#3");;
		tbStation.setDataStatus(0);
		return tbStation;
	}
	//转换交通方式
	private Integer getTransportationType(String style){
		if("飞机".equals(style)){
			return 2;
		}else if("火车".equals(style)){
			return 1;
		}else if("汽车".equals(style)){
			return 3;
		}else if("轮船".equals(style)){
			return 4;
		}
		return null;
	}
	@Override
	public String getXmlName() {
		// TODO Auto-generated method stub
		return "qunar_city_id";
	}

	@Override
	public String getSource() {
		// TODO Auto-generated method stub
		return "qunar";
	}

	@Override
	protected Semaphore getMutex() {
		// TODO Auto-generated method stub
		return mutex;
	}
}
