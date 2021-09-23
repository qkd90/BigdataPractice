package com.data.data.hmly.service.ctripuser;

import com.data.data.hmly.service.ctripuser.dao.CtripUserDao;
import com.data.data.hmly.service.ctripuser.entity.CtripUser;
import com.zuipin.util.MD5;
import com.zuipin.util.PropertiesManager;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

@Service
public class CtripUserService {
	
	private static final String UNIQUEID_URL = "http://openapi.ctrip.com/user/OTA_UserUniqueID.asmx";
	// 用户生成
    private static final String OTA_USERUNIQUEID = "OTA_UserUniqueID";	
	private static final String soap_prefix = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><Request xmlns=\"http://ctrip.com/\"><requestXML>";
    private static final String soap_suffix = "</requestXML></Request></soap:Body></soap:Envelope>";

	@Resource
	private PropertiesManager propertiesManager;
	@Resource
	private CtripUserDao ctripUserDao;
	
	
	/**
	 * 获取携程用户UserUniqueID
	 * @author caiys
	 * @date 2015年12月4日 上午9:32:31
	 * @param uidKey 系统用户标识
	 */
	public String getCtripUniqueUid(String uidKey) {
    	String allianceId = propertiesManager.getString("CTRIP_ALLIANCE_ID");
	    String sid = propertiesManager.getString("CTRIP_SID");
	    String apiKey = propertiesManager.getString("CTRIP_API_KEY");
		long timeStamp = System.currentTimeMillis();
		String requestStr = "<Request>"
                + "<Header AllianceID=\"" + allianceId + "\" SID=\"" + sid + "\" TimeStamp=\"" + timeStamp
                + "\" RequestType=\"OTA_UserUniqueID\""
                + " Signature=\"" + MD5.caiBeiMD5(timeStamp + allianceId + MD5.caiBeiMD5(apiKey).toUpperCase() + sid + OTA_USERUNIQUEID).toUpperCase()
                + "\"/>"
                + "<UserRequest>"
                + "<AllianceID>" + allianceId + "</AllianceID>"
                + "<SID>" + sid + "</SID>"
                + "<UidKey>" + uidKey + "</UidKey>"
                + "</UserRequest>"
                + "</Request>";
		String result = postForXML(UNIQUEID_URL, requestStr);
		System.out.println("result=" + result);
		if (StringUtils.isNotBlank(result) && result.indexOf("UniqueUID") > -1) {
			result = result.substring(result.indexOf("<UniqueUID>")+"<UniqueUID>".length(), result.indexOf("</UniqueUID>"));
			return result;
		}
		return null;
	}
	

	/**
	 * 获取本地携程用户UserUniqueID，先在本地查找如果已经存在直接返回，否则从携程生成用户并返回
	 * @author caiys
	 * @date 2015年12月4日 上午10:14:44
	 * @param uidKey 本地用户名
	 * @return
	 * @throws Exception 
	 */
	public String doGetUniqueUid(String uidKey) throws Exception {
    	String allianceId = propertiesManager.getString("CTRIP_ALLIANCE_ID");
	    String sid = propertiesManager.getString("CTRIP_SID");
	    CtripUser ctripUser = ctripUserDao.findUniqueCtripUser(allianceId, sid, uidKey);
	    if (ctripUser == null) {
	    	String uniqueUid = getCtripUniqueUid(uidKey);
	    	if (StringUtils.isBlank(uniqueUid)) {
	    		throw new Exception("获取携程用户UserUniqueID失败");
	    	}
	    	ctripUser = new CtripUser();
	    	ctripUser.setAllianceId(allianceId);
	    	ctripUser.setSid(sid);
	    	ctripUser.setUidKey(uidKey);
	    	ctripUser.setUniqueUid(uniqueUid);
	    	ctripUser.setCreateTime(new Date());
	    	ctripUserDao.save(ctripUser);
	    }
	    return ctripUser.getUniqueUid();
	}
	
	/**
	 * 执行请求并返回结果
	 * @author caiys
	 * @date 2015年12月4日 上午9:33:23
	 * @param url
	 * @param xmlData
	 * @return
	 */
	public String postForXML(String url, String xmlData) {
        try {
        	//创建HttpClientBuilder  
        	HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();  
        	//HttpClient  
        	CloseableHttpClient closeableHttpClient = httpClientBuilder.build();  
            HttpPost httpPost = new HttpPost(url);
            httpPost.setEntity(new StringEntity(soap_prefix + StringEscapeUtils.escapeXml(xmlData) + soap_suffix, ContentType.TEXT_XML));
            HttpResponse response = closeableHttpClient.execute(httpPost);
            String resultStr = EntityUtils.toString(response.getEntity(), "utf-8");
            resultStr = StringEscapeUtils.unescapeXml(resultStr);
            resultStr = resultStr.substring(resultStr.indexOf("<RequestResult>")+"<RequestResult>".length(), resultStr.indexOf("</RequestResult>"));
            return resultStr;
        } catch (Exception e) {
        	e.printStackTrace();
        }
        return null;
    }
	
}
