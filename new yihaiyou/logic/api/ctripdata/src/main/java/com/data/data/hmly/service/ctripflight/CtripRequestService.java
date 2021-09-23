package com.data.data.hmly.service.ctripflight;

import com.data.data.hmly.service.ctripflight.request.pojo.FlightSearchHeaderType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltCancelOrderHeaderType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltOrderHeaderType;
import com.data.data.hmly.service.ctripflight.request.pojo.FltViewOrderHeaderType;
import com.data.data.hmly.service.ctripflight.request.pojo.GetAirlineInfosHeaderType;
import com.data.data.hmly.service.ctripflight.request.pojo.GetAirporInfostHeaderType;
import com.data.data.hmly.service.ctripflight.request.pojo.GetCityInfosHeaderType;
import com.data.data.hmly.service.ctripflight.request.pojo.GetCraftInfosHeaderType;
import com.zuipin.util.MD5;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpHost;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.GzipDecompressingEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.params.ConnRoutePNames;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

/**
 * Created by Sane on 15/10/28.
 */
public class CtripRequestService {

    private static final String SOAP_API = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><Request xmlns=\"http://ctripWin8.com/\"><requestXML>%s%s</requestXML></Request></soap:Body></soap:Envelope>";
    private static final String HEAD_API = "<Header AllianceID=\"%d\" SID=\"%d\" TimeStamp=\"%d\" Signature=\"%s\" RequestType=\"%s\" AsyncRequest=\"false\" Timeout=\"0\" MessagePriority=\"3\" />\n";
    private static final String ALLIANCE_ID = "22422";
    private static final String SID = "456399";
    private static final String API_KEY = "47C87868-C59F-4072-A121-AAE03B428240";

    /**
     * 查询航班
     * <ul>
     * <li>必填：请求地址{url=http://openapi.ctrip.com/Flight/DomesticFlight/OTA_FlightSearch.asmx}</li>
     * <li>必填：requestType{请求类型=OTA_FltGetAirportInfos}</li>
     * <li>必填：xmlData{请求参数=<Request><GetAirportInfosRequest><AirportCode></AirportCode></GetAirportInfosRequest></Request>}</li>
     * <li>url:/api/dataplan/list</li>
     * </ul>
     *
     * @return 航班列表
     */

    public static String postForXMLUseGzip(String url, String requestType, String xmlData) {
        String resultStr;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            httpPost.setHeader("Accept-Encoding", "gzip");
            String head = String.format(HEAD_API, ALLIANCE_ID, SID, System.currentTimeMillis() / 1000, API_KEY, requestType);
            httpPost.setEntity(new StringEntity(String.format(SOAP_API, head, StringEscapeUtils.escapeXml(xmlData)), ContentType.TEXT_XML));
            HttpResponse response = httpClient.execute(httpPost);
            resultStr = EntityUtils.toString(new GzipDecompressingEntity(response.getEntity()), "utf-8");
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (resultStr == null) {
            return null;
        }
        return resultStr;
    }

    public static FltCancelOrderHeaderType getCancelOrderHead(String requestType) {
    	FltCancelOrderHeaderType head = new FltCancelOrderHeaderType();
        head.setAllianceID(ALLIANCE_ID);
        head.setSID(SID);
        long timeStamp = System.currentTimeMillis() / 1000;
        head.setSignature(getSignatrue(API_KEY, timeStamp, requestType));
        head.setRequestType(requestType);
        head.setTimeStamp(String.valueOf(timeStamp));
        head.setAsyncRequest("false");
        head.setMessagePriority("3");
        head.setTimeout("0");
        return head;
    }

    public static GetCityInfosHeaderType getCityInfosHeader(String requestType) {
    	GetCityInfosHeaderType head = new GetCityInfosHeaderType();
        head.setAllianceID(ALLIANCE_ID);
        head.setSID(SID);
        long timeStamp = System.currentTimeMillis() / 1000;
        head.setSignature(getSignatrue(API_KEY, timeStamp, requestType));
        head.setRequestType(requestType);
        head.setTimeStamp(String.valueOf(timeStamp));
        head.setAsyncRequest("false");
        head.setMessagePriority("3");
        head.setTimeout("0");
        return head;
    }
    
    
    public static FltOrderHeaderType getOrderHead(String requestType) {
    	FltOrderHeaderType head = new FltOrderHeaderType();
        head.setAllianceID(ALLIANCE_ID);
        head.setSID(SID);
        long timeStamp = System.currentTimeMillis() / 1000;
        head.setSignature(getSignatrue(API_KEY, timeStamp, requestType));
        head.setRequestType(requestType);
        head.setTimeStamp(String.valueOf(timeStamp));
        head.setAsyncRequest("false");
        head.setMessagePriority("3");
        head.setTimeout("0");
        return head;
    }
    
    public static FltViewOrderHeaderType getFltViewOrder(String requestType) {
    	FltViewOrderHeaderType head = new FltViewOrderHeaderType();
        head.setAllianceID(ALLIANCE_ID);
        head.setSID(SID);
        long timeStamp = System.currentTimeMillis() / 1000;
        head.setSignature(getSignatrue(API_KEY, timeStamp, requestType));
        head.setRequestType(requestType);
        head.setTimeStamp(String.valueOf(timeStamp));
        return head;
    }
    

    public static FlightSearchHeaderType getRequestHead(String requestType) {
        FlightSearchHeaderType head = new FlightSearchHeaderType();
        head.setAllianceID(ALLIANCE_ID);
        head.setSID(SID);
        long timeStamp = System.currentTimeMillis() / 1000;
        head.setSignature(getSignatrue(API_KEY, timeStamp, requestType));
        head.setRequestType(requestType);
        head.setTimeStamp(String.valueOf(timeStamp));
        head.setAsyncRequest("false");
        head.setMessagePriority("3");
        head.setTimeout("0");
        return head;
    }

    private static String getSignatrue(String apiKey, long timeStamp, String requestType) {
        return MD5.caiBeiMD5(timeStamp + ALLIANCE_ID + MD5.caiBeiMD5(API_KEY).toUpperCase() + SID + requestType).toUpperCase();
    }
    
 // 将Soap外壳添加到请求体上
 	private static String AddSoapShell(String patamterValue) throws Exception
 	{
 		
 			return "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n"+
 			        "<soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\">\n"+
 			        "  <soap:Body>\n"+
 			        "    <Request xmlns=\"http://ctrip.com/\">\n"+
 			        "      <requestXML>"+patamterValue+"</requestXML>\n"+
 			        "    </Request>\n"+
 			        "  </soap:Body>\n"+
 			        "</soap:Envelope>\n";
 		
 	}

 	
 	private static String RemoveSoapShell(String source)
	{
		String result = "";
		int indexElementBegin = source.indexOf("<RequestResult>");
		int indexElementEnd = source.indexOf("</RequestResult>");
		if(indexElementBegin > 0 && indexElementEnd > 0)
		{
			result = source.substring(indexElementBegin + "<RequestResult>".length(), indexElementEnd);
		}
		return result;
	}
 	

    public static String postForXMLUseGzip(String url, String request) {
        String resultStr;
        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpPost httpPost = new HttpPost(url);
            HttpHost proxy = new HttpHost("127.0.0.1", 8888);
            httpClient.getParams().setParameter(ConnRoutePNames.DEFAULT_PROXY, proxy);
            request = StringEscapeUtils.escapeXml(request);
            request = AddSoapShell(request);
            httpPost.setHeader("Accept-Encoding", "gzip");
            httpPost.setEntity(new StringEntity(request, ContentType.TEXT_XML));
            HttpResponse response = httpClient.execute(httpPost);
            resultStr = EntityUtils.toString(new GzipDecompressingEntity(response.getEntity()), "utf-8");
            resultStr = RemoveSoapShell(resultStr);
            
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        if (resultStr == null) {
            return null;
        }
        return resultStr;
    }

	public static GetAirporInfostHeaderType getAirportInfosHeader(
			String flightAirportinfosType) {
		
		GetAirporInfostHeaderType head = new GetAirporInfostHeaderType();
        head.setAllianceID(ALLIANCE_ID);
        head.setSID(SID);
        long timeStamp = System.currentTimeMillis() / 1000;
        head.setSignature(getSignatrue(API_KEY, timeStamp, flightAirportinfosType));
        head.setRequestType(flightAirportinfosType);
        head.setTimeStamp(String.valueOf(timeStamp));
//        head.setAsyncRequest("false");
//        head.setMessagePriority("3");
//        head.setTimeout("0");
        return head;
	}

	public static GetAirlineInfosHeaderType getAirlineInfosHeader(
			String flightAirlineinfosType) {
		
		GetAirlineInfosHeaderType head = new GetAirlineInfosHeaderType();
        head.setAllianceID(ALLIANCE_ID);
        head.setSID(SID);
        long timeStamp = System.currentTimeMillis() / 1000;
        head.setSignature(getSignatrue(API_KEY, timeStamp, flightAirlineinfosType));
        head.setRequestType(flightAirlineinfosType);
        head.setTimeStamp(String.valueOf(timeStamp));
        head.setAsyncRequest("false");
        head.setMessagePriority("3");
        head.setTimeout("0");
        return head;
	}

	public static GetCraftInfosHeaderType getCraftInfosHeader(
			String flightCraftinfosType) {
		
		GetCraftInfosHeaderType head = new GetCraftInfosHeaderType();
        head.setAllianceID(ALLIANCE_ID);
        head.setSID(SID);
        long timeStamp = System.currentTimeMillis() / 1000;
        head.setSignature(getSignatrue(API_KEY, timeStamp, flightCraftinfosType));
        head.setRequestType(flightCraftinfosType);
        head.setTimeStamp(String.valueOf(timeStamp));
        head.setAsyncRequest("false");
        head.setMessagePriority("3");
        head.setTimeout("0");
        return head;
		
		
	}


}
