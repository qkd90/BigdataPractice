package com.hmlyinfo.app.soutu.scenicTicket.ctrip.service;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenicTicket.ctrip.domain.TicketCtrip;
import com.hmlyinfo.app.soutu.scenicTicket.ctrip.mapper.TicketCtripMapper;
import com.hmlyinfo.base.util.MD5;

@Service
public class TicketCtripOrderService {
	
	private static final String TICKET_URL = "http://openapi.ctrip.com/vacations/OpenServer.ashx?RequestJson=";

    private static final String ALLIANCE_ID = "22422";
    private static final String SID = "456399";
    private static final String API_KEY = "47C87868-C59F-4072-A121-AAE03B428240";
    
    @Autowired
    private TicketCTripService tcService;
    @Autowired
    private TicketCtripMapper<TicketCtrip> tcMapper;
    
    private ObjectMapper objectMapper = new ObjectMapper();
    
    //门票可订性检查，接收参数参考携程文档（http://open.ctrip.com/help/CommonParams2.aspx）
    public Map<String, Object> checkCanBooking(List<Map<String, Object>> orderList)
    {
    	
    	int orderPrice = 0;
    	String orderString = "";
    	
    	for(Map<String, Object> orderMap : orderList){
    		int resourceId = (Integer) orderMap.get("resourceId");
    		TicketCtrip ticketCtrip = tcMapper.selByResourceId(resourceId);
    		if(ticketCtrip == null){
    			return null;
    		}
    		int price = ticketCtrip.getPrice();
    		int useQuantity = (Integer) orderMap.get("UseQuantity");
    		String useDate = (String) orderMap.get("UseDate");
    		int totalPrice = price * useQuantity;
    		orderString = orderString + "{";
    		orderString = orderString + "\\\"ResourceID\\\":" + resourceId + ",";
    		orderString = orderString + "\\\"TotalPrice\\\":" + totalPrice + ",";
    		orderString = orderString + "\\\"UseDate\\\":" + "\\\"" + useDate + "\\\"" + ",";
    		orderString = orderString + "\\\"UseQuantity\\\":" + useQuantity;
    		orderString = orderString + "}";
    		orderPrice += totalPrice;
    	}
    	

    	String requestStr =
        		"{"+
        		    "\"AllianceID\":"+ "\""+ALLIANCE_ID+"\""+","+
        		    "\"SID\":"+"\""+SID+"\""+","+
        		    "\"ProtocolType\":1,"+
        		    "\"Signature\":" + "\"" + MD5.GetMD5Code(System.currentTimeMillis() + ALLIANCE_ID + MD5.GetMD5Code(API_KEY).toUpperCase() + SID + "TicketOrderCanBooking").toUpperCase()+"\""+","+
        		    "\"TimeStamp\":"+"\""+System.currentTimeMillis()+"\""+","+
        		    "\"Channel\":\"Vacations\","+
        		    "\"Interface\":\"TicketOrderCanBooking\","+
        		    "\"IsError\":false,"+
        		    "\"RequestBody\":\"{"+
        		    
        		        "\\\"DistributionChannel\\\":" + 9 +"," + 
        		        "\\\"OrderTotalPrice\\\":" + orderPrice + "," + 
        		        "\\\"PromotionList\\\":" + null + "," + 
        		        "\\\"ResourceCanBookingRequestItemList\\\":" + "[" +

        		        	orderString +
        		        	
        		        "]" +
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
    	System.out.println("pos=" + postUrl);
    	
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
    
    //创建门票订单
    public Map<String, Object> createCtripOrder(Map<String, Object> paramMap)
    {
    	List<Map<String, Object>> clientInfoList = (List<Map<String, Object>>) paramMap.get("ClientInfoList");
    	List<Map<String, Object>> ticketInfoList = (List<Map<String, Object>>) paramMap.get("TicketInfo");
    	String clientString = "";
    	for(Map<String, Object> clientMap : clientInfoList){
    		clientString = clientString + "{";
    		clientString = clientString + "\\\"AgeType\\\":"  + "\\\"" + clientMap.get("AgeType") + "\\\"" + ","; 
    		clientString = clientString + "\\\"BirthCity\\\":" + clientMap.get("BirthCity") + ","; 
    		clientString = clientString + "\\\"BirthDate\\\":" + resolveString((String) clientMap.get("BirthDate")) + ",";
    		clientString = clientString + "\\\"CardCity\\\":" + clientMap.get("CardCity") + ",";
    		clientString = clientString + "\\\"ClientName\\\":" + resolveString((String) clientMap.get("ClientName")) + ",";
    		clientString = clientString + "\\\"ClientName_E\\\":" + resolveString((String) clientMap.get("ClientName_E")) + ",";
    		clientString = clientString + "\\\"ContactInfo\\\":" + resolveString((String) clientMap.get("ContactInfo")) + ",";
    		clientString = clientString + "\\\"Gender\\\":" + clientMap.get("Gender") + ",";
    		clientString = clientString + "\\\"HzAdd\\\":" + resolveString((String) clientMap.get("HzAdd")) + ",";
    		clientString = clientString + "\\\"HzDate\\\":" + resolveString((String) clientMap.get("HzDate")) + ",";
    		clientString = clientString + "\\\"HzNo\\\":" + resolveString((String) clientMap.get("HzNo")) + ",";
    		clientString = clientString + "\\\"IDCardNo\\\":" + resolveString((String) clientMap.get("IDCardNo")) + ",";
    		clientString = clientString + "\\\"IDCardTimelimit\\\":" + resolveString((String) clientMap.get("IDCardTimelimit")) + ",";
    		clientString = clientString + "\\\"IDCardType\\\":" + clientMap.get("IDCardType") + ",";
    		clientString = clientString + "\\\"InfoID\\\":" + clientMap.get("InfoID") + ",";
    		clientString = clientString + "\\\"IssueDate\\\":" + resolveString((String) clientMap.get("IssueDate")) + ",";
    		clientString = clientString + "\\\"Nationality\\\":" + clientMap.get("Nationality") + ",";
    		clientString = clientString + "\\\"PassportType\\\":" + "\\\"" + clientMap.get("PassportType") + "\\\"" + ","; 
    		clientString = clientString + "\\\"VisaCountry\\\":" + clientMap.get("VisaCountry") + ",";
    		clientString = clientString + "}";

    	}
    	int productId = 0;
    	double totalPrice = 0f;
    	String ticketString = "";
    	for(Map<String, Object> ticketMap : ticketInfoList){
    		int resourceId = (Integer) ticketMap.get("TicketID");
    		int quantity = (Integer) ticketMap.get("Quantity");
    		TicketCtrip ticketCtrip = tcMapper.selByResourceId(resourceId);
    		productId = ticketCtrip.getCtripId();
    		double ticketPrice = ticketCtrip.getPrice() * quantity;
    		totalPrice += ticketPrice;
    		ticketString = ticketString + "{";
    		ticketString = ticketString + "\\\"Price\\\":" + ticketPrice + ","; 
    		ticketString = ticketString + "\\\"TicketID\\\":" + resourceId + ","; 
    		ticketString = ticketString + "\\\"UseDate\\\":" + "\\\"" + ticketMap.get("UseDate") + "\\\"" + ","; 
    		ticketString = ticketString + "\\\"Quantity\\\":" + quantity + ","; 
    		ticketString = ticketString + "\\\"TicketType\\\":" + ticketMap.get("TicketType");
    		ticketString = ticketString + "}";
    	}
    	/**
    	 * 上面只拼了部分json串，具体参考携程文档（http://open.ctrip.com/help/CommonParams2.aspx）
    	 */
    	/*
    	String requestStr =
        		"{"+
        		    "\"AllianceID\":"+ "\""+ALLIANCE_ID+"\""+","+
        		    "\"SID\":"+"\""+SID+"\""+","+
        		    "\"ProtocolType\":1,"+
        		    "\"Signature\":" + "\"" + MD5.GetMD5Code(System.currentTimeMillis() + ALLIANCE_ID + MD5.GetMD5Code(API_KEY).toUpperCase() + SID + "TicketOrderCreateForPrepay_V2").toUpperCase()+"\""+","+
        		    "\"TimeStamp\":"+"\""+System.currentTimeMillis()+"\""+","+
        		    "\"Channel\":\"Vacations\","+
        		    "\"Interface\":\"TicketOrderCreateForPrepay_V2\","+
        		    "\"IsError\":false,"+
        		    "\"RequestBody\":\"{"+
        		    	"\\\"AdultNumber\\\":" + paramMap.get("AdultNumber") + "," + 
        		    	"\\\"Amount\\\":" + totalPrice + "," + 
        		    	"\\\"ClientInfoList\\\": [" +
        		    	clientString + "]," +
        		    	"\\\"DistributorOrderID\\\":" + "\\\"" + paramMap.get("DistributorOrderID") + "\\\"" + "," + 
        		        "\\\"ContactInfo\\\": {" +
        		            "\\\"ContactAddress\\\":" + resolveString((String) paramMap.get("ContactAddress")) + "," + 
        		            "\\\"ContactEmail\\\":" + resolveString((String) paramMap.get("ContactEmail")) + "," + 
        		            "\\\"ContactFax\\\":" + resolveString((String) paramMap.get("ContactFax")) + "," +
        		            //实际用户手机
        		            "\\\"ContactMobile\\\":" + "\\\"" + paramMap.get("ContactMobile") + "\\\"" + "," + 
        		            //分销商名称，不是订单用户名
        		            "\\\"ContactName\\\":" + "\\\"" + paramMap.get("ContactName") + "\\\"" + "," +
        		            "\\\"ContactTel\\\":" + resolveString((String) paramMap.get("ContactTel")) + "," +
        		        "}," +
        		        "\\\"ProductInfo\\\":{" +
        		                "\\\"ProductID\\\":" + productId + "," + 
        		            },
        		            */



        		    
        		    
    	return null;
    }
    
    //处理可能没有传值的字符串，如果传值就加上引号
    public String resolveString(String s)
    {
    	if(s == null){
    		return s;
    	}else{
    		String reString = "\\\"" + s + "\\\"";
    		return reString;
    	}
    }
  
}
