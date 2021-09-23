package com.hmlyinfo.app.soutu.scenicTicket.ctrip.service;

import com.google.common.collect.Lists;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.scenic.domain.City;
import com.hmlyinfo.app.soutu.scenic.domain.ScenicInfo;
import com.hmlyinfo.app.soutu.scenic.service.CityService;
import com.hmlyinfo.app.soutu.scenic.service.ScenicInfoService;
import com.hmlyinfo.app.soutu.scenicTicket.ctrip.domain.TicketCtrip;
import com.hmlyinfo.app.soutu.scenicTicket.ctrip.mapper.TicketCtripMapper;
import com.hmlyinfo.base.util.MD5;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 */
@Service
public class TicketCTripService {
    private static final Logger logger = Logger.getLogger(TicketCTripService.class);

    private static final String TICKET_URL = "http://openapi.ctrip.com/vacations/OpenServer.ashx?RequestJson=";

    private static final String ALLIANCE_ID = "22422";
    private static final String SID = "456399";
    private static final String API_KEY = "47C87868-C59F-4072-A121-AAE03B428240";

    @Autowired
    private TicketCtripMapper<TicketCtrip> mapper;
    @Autowired
    private ScenicInfoService sInfoService;
    @Autowired
    private CityService cService;
    
    public List<TicketCtrip> listColumns(Map<String, Object> params, List<String> columns) {
        params.put("needColumns", columns);
        return mapper.listColumns(params);
    }
    
    private ObjectMapper objectMapper = new ObjectMapper();

    public List<Map<String, Object>> test(Map<String, Object> paramMap)
    {
    	long cityId = Long.parseLong((String) paramMap.get("cityId"));
    	String cityName = (String) paramMap.get("cityName");
    	return getCtripTickets(cityId, cityName);
    }
    
    public Map<String, Object> detailTest(Map<String, Object> paramMap)
    {
    	String idString = (String) paramMap.get("idString");
    	return getCtripTicketsInfo(idString);
    }
    
    //获取门票列表，其中cityId为携程的景区ID
    public List<Map<String, Object>> getCtripTickets(long cityId, String cityName)
    {
    	
    	String requestStr =
        		"{"+
        		    "\"AllianceID\":"+ "\""+ALLIANCE_ID+"\""+","+
        		    "\"SID\":"+"\""+SID+"\""+","+
        		    "\"ProtocolType\":1,"+
        		    "\"Signature\":" + "\"" + MD5.GetMD5Code(System.currentTimeMillis() + ALLIANCE_ID + MD5.GetMD5Code(API_KEY).toUpperCase() + SID + "TicketSenicSpotSearch").toUpperCase()+"\""+","+
        		    "\"TimeStamp\":"+"\""+System.currentTimeMillis()+"\""+","+
        		    "\"Channel\":\"Vacations\","+
        		    "\"Interface\":\"TicketSenicSpotSearch\","+
        		    "\"IsError\":false,"+
        		    "\"RequestBody\":\"{"+
        		        "\\\"DistributionChannel\\\":" + 9 +","+ /*
        		        "\\\"PagingParameter\\\":{"+
        		            "\\\"PageIndex\\\":1,"+ 
        		            "\\\"PageSize\\\":10"+
        		        "},"+ */
        		        "\\\"SearchParameter\\\":{"+
        		            "\\\"Keyword\\\":\\\""+ cityName +"\\\","+ 
        		            "\\\"SaleCityID\\\":"+ cityId +","+ 
        		        "}"+
        		    "}\""+ "," +

        		    "\"ResponseBody\":\"\","+
        		    "\"ErrorMessage\":\"\""+
        		"}";
    	
    	String encoderJson = "";
		try {
			encoderJson = encoderJson + URLEncoder.encode(requestStr, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String postUrl = TICKET_URL + encoderJson;
  
    	
    	
    	HttpPost httpRequst = new HttpPost(postUrl);
    	String resultStr = "";
    	try {
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
			resultStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	int startIndex = resultStr.indexOf("\"ResponseBody") + 16;
    	int endIndex = resultStr.indexOf("ErrorMessage", startIndex) - 3;
    	resultStr = resultStr.substring(startIndex, endIndex);
    	resultStr = "{" + "\"ResponseBody\":" + resultStr + "}";
    	resultStr = resultStr.replaceAll("\\\\", "");
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	try {
			paramMap = objectMapper.readValue(resultStr, Map.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	List<Map<String, Object>> scenicList = (List<Map<String, Object>>)(((Map) paramMap.get("ResponseBody")).get("ScenicSpotListItemList"));
    	
    	for(Map<String, Object> scenicSpot : scenicList){
    		int countryId = (Integer) scenicSpot.get("CountryID");
    		int provinceId = (Integer) scenicSpot.get("ProvinceID");
    		int districtId = (Integer) scenicSpot.get("DistrictID");
    		int ctripScenicId = (Integer) scenicSpot.get("ID");
    		String districtName = (String) scenicSpot.get("DistrictName");
    		String scenicName = (String) scenicSpot.get("Name");
    		Map<String, Object> cMap = new HashMap<String, Object>();
    		cMap.put("name", districtName);
    		List<City> cityList = cService.list(cMap);
    		long cityCode = 0;
    		if(!cityList.isEmpty()){
    			cityCode = cityList.get(0).getCityCode();
    		}
    		List<Map<String, Object>> productList = (List<Map<String, Object>>) scenicSpot.get("ProductListItemList");
    		for(Map<String, Object> productMap : productList){
    			int productId = (Integer) productMap.get("ID");
    			List<Map<String, Object>> resourceList = (List<Map<String, Object>>) productMap.get("ResourceListItemList");
    			for(Map<String, Object> resourceMap : resourceList){
    				TicketCtrip ticketCtrip = new TicketCtrip();
    				ticketCtrip.setCtripId(productId);
    				ticketCtrip.setCtripResourceId((Integer) resourceMap.get("ID"));
    				ticketCtrip.setName((String) resourceMap.get("Name"));
    				ticketCtrip.setMarketPrice((Integer) resourceMap.get("MarketPrice"));
    				ticketCtrip.setPrice((Integer) resourceMap.get("Price"));
    				ticketCtrip.setCtripScenicId(ctripScenicId);
    				ticketCtrip.setScenicName(scenicName);
    				ticketCtrip.setTicketType((Integer) resourceMap.get("TicketType"));
    				ticketCtrip.setPeopleGroup((Integer) resourceMap.get("PeopleGroup"));
    				ticketCtrip.setReturnCashAmount((Integer) resourceMap.get("ReturnCashAmount"));
    				if(resourceMap.get("IsReturnCash").toString() == "true"){
    					ticketCtrip.setIsReturnCash("true");
    				}else{
    					ticketCtrip.setIsReturnCash("false");
    				}
    				Map<String, Object> params = new HashMap<String, Object>();
    				params.put("name", scenicName);
    				if(cityCode != 0){
    					params.put("cityCode", cityCode);
    				}
    				List<ScenicInfo> scenicInfos = sInfoService.listColumns(params, Lists.newArrayList("id"));
    				if(scenicInfos.isEmpty()){
    					ticketCtrip.setScenicId(0);
    				}else{
    					ticketCtrip.setScenicId(scenicInfos.get(0).getId());
    				}
    				ticketCtrip.setCountryId(countryId);
    				ticketCtrip.setProvinceId(provinceId);
    				ticketCtrip.setDistrictId(districtId);
    				mapper.insert(ticketCtrip);
    			}
    		}
    	}
    		
    	return scenicList;
    	
    }
    
    
    //获取门票详情idString为景点编号链接起来的字符串，以逗号隔开
    public Map<String, Object> getCtripTicketsInfo(String idString)
    {
    	String requestStr =
        		"{"+
        		    "\"AllianceID\":"+ "\""+ALLIANCE_ID+"\""+","+
        		    "\"SID\":"+"\""+SID+"\""+","+
        		    "\"ProtocolType\":1,"+
        		    "\"Signature\":" + "\"" + MD5.GetMD5Code(System.currentTimeMillis() + ALLIANCE_ID + MD5.GetMD5Code(API_KEY).toUpperCase() + SID + "TicketSenicSpotInfo").toUpperCase()+"\""+","+
        		    "\"TimeStamp\":"+"\""+System.currentTimeMillis()+"\""+","+
        		    "\"Channel\":\"Vacations\","+
        		    "\"Interface\":\"TicketSenicSpotInfo\","+
        		    "\"IsError\":false,"+
        		    "\"RequestBody\":\"{"+
        		    
        		        "\\\"DistributionChannel\\\":" + 9 +"," + 
        		        "\\\"ResponseDataType\\\":" + 0 + "," + 
        		        "\\\"ID\\\":" + "[" +
        		            "48767" +
        		        "]," + 
        		        "\\\"ProductId\\\":" + null + "," + 
        		        "\\\"DebugKey\\\":" + null + "," + 
        		        "\\\"ImageSize\\\":" + null + 
        		        
        		    "}\""+ "," +

        		    "\"ResponseBody\":\"\","+
        		    "\"ErrorMessage\":\"\""+
        		"}";
    	
    	String encoderJson = "";
		try {
			encoderJson = encoderJson + URLEncoder.encode(requestStr, "GBK");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	String postUrl = TICKET_URL + encoderJson;
  
    	
    	
    	HttpPost httpRequst = new HttpPost(postUrl);
    	String resultStr = "";
    	try {
			HttpResponse httpResponse = new DefaultHttpClient().execute(httpRequst);
			resultStr = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
		} catch (ClientProtocolException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	
    	System.out.println("111=" + resultStr);
    	
    	int startIndex = resultStr.indexOf("\"ResponseBody") + 16;
    	int endIndex = resultStr.indexOf("ErrorMessage", startIndex) - 3;
    	resultStr = resultStr.substring(startIndex, endIndex);
    	resultStr = "{" + "\"ResponseBody\":" + resultStr + "}";
    	//把格式中的\\\"转化为'
    	resultStr = resultStr.replaceAll("\\\\\\\\\\\\\\\"", "\\\'");
    	//把外层中的\"转化为"
    	resultStr = resultStr.replaceAll("\\\\", "");
    	
    	System.out.println("rrr2=" + resultStr);
    	
    	Map<String, Object> paramMap = new HashMap<String, Object>();
    	try {
			paramMap = objectMapper.readValue(resultStr, Map.class);
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (JsonMappingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return paramMap;
    }


}

