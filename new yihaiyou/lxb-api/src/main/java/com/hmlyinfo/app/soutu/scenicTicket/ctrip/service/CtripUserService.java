package com.hmlyinfo.app.soutu.scenicTicket.ctrip.service;

import java.util.Map;

import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hmlyinfo.app.soutu.scenicTicket.ctrip.domain.CtripUser;
import com.hmlyinfo.app.soutu.scenicTicket.ctrip.mapper.CtripUserMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.HttpClientUtils;
import com.hmlyinfo.base.util.MD5;

@Service
public class CtripUserService extends BaseService<CtripUser, Long>{

	
	private static final String UNIQUEID_URL = "http://openapi.ctrip.com/Hotel/OTA_UserUniqueID.asmx";
	
	private static final String soap_prefix = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><Request xmlns=\"http://ctrip.com/\"><requestXML>";
    private static final String soap_suffix = "</requestXML></Request></soap:Body></soap:Envelope>";

    private static final String ALLIANCE_ID = "22422";
    private static final String SID = "456399";
    private static final String API_KEY = "47C87868-C59F-4072-A121-AAE03B428240";
	
	@Autowired
	private CtripUserMapper<CtripUser> mapper;

	@Override
	public BaseMapper<CtripUser> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	public void getCtripUniqueUid(){
		
		String requestStr = "<Request>"
                + "<Header AllianceID=\"" + ALLIANCE_ID + "\" SID=\"" + SID + "\" TimeStamp=\"" + System.currentTimeMillis()
                + "\" RequestType=\"OTA_UserUniqueID\""
                + " Signature=\"" + MD5.GetMD5Code(System.currentTimeMillis() + ALLIANCE_ID + MD5.GetMD5Code(API_KEY).toUpperCase() + SID + "OTA_UserUniqueID").toUpperCase()
                + "\"/>"
                + "<UserRequest>"
                + "<AllianceID>" + ALLIANCE_ID + "</AllianceID>"
                + "<SID>" + SID + "</SID>"
                + "<UidKey>" + 123 + "</UidKey>"
                + "</UserRequest>"
                + "</Request>";
		String reString = postForXML(UNIQUEID_URL, requestStr);
		System.out.println(reString);
        
	}
	
	
	public String postForXML(String url, String xmlData) {
        try {
            HttpClient httpClient = HttpClientUtils.getHttpClient();

            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(soap_prefix + StringEscapeUtils.escapeXml(xmlData) + soap_suffix, ContentType.TEXT_XML));
            HttpResponse response = httpClient.execute(httpPost);
            String resultStr = EntityUtils.toString(response.getEntity(), "utf-8");
            resultStr = StringEscapeUtils.unescapeXml(resultStr);
            resultStr = resultStr.substring(resultStr.indexOf(""), resultStr.indexOf(""));
            return resultStr;
        } catch (Exception e) {
//            logger.error(e.getMessage(), e);
        }
        return null;
    }

}
