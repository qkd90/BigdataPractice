package com.data.data.hmly.service.ctriphotel;

import com.data.data.hmly.service.ctriphotel.base.HttpClientUtils;
import com.data.data.hmly.service.ctriphotel.dao.CtripHotelIdDao;
import com.data.data.hmly.service.ctriphotel.entity.CtripHotelId;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.util.EntityUtils;
import org.apache.log4j.Logger;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


@Service
public class CtripCommonService {


	public static final Logger logger = Logger.getLogger(CtripCommonService.class);

	private static final String soap_prefix = "<?xml version=\"1.0\" encoding=\"utf-8\"?><soap:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap=\"http://schemas.xmlsoap.org/soap/envelope/\"><soap:Body><Request xmlns=\"http://ctrip.com/\"><requestXML>";
	private static final String soap_suffix = "</requestXML></Request></soap:Body></soap:Envelope>";

	@Resource
	private CtripHotelIdDao ctripHotelIdDao;

	public void dotest(){
		CtripHotelId ctripHotelId = new CtripHotelId();
		ctripHotelId.setCityId(25L);
		ctripHotelId.setHotelId(11111L);
		ctripHotelIdDao.saveOrUpdate(ctripHotelId, ctripHotelId.getId());
	}

	public static Map<String, Object> postForXML(String url, String xmlData) {
		try {
			HttpClient httpClient = HttpClientUtils.getHttpClient();

			HttpPost httpPost = new HttpPost(url);
			httpPost.setEntity(new StringEntity(soap_prefix + StringEscapeUtils.escapeXml(xmlData) + soap_suffix, ContentType.TEXT_XML));
			HttpResponse response = httpClient.execute(httpPost);
			String resultStr = EntityUtils.toString(response.getEntity(), "utf-8");
			resultStr = resultStr.substring(resultStr.indexOf("<RequestResult>") + 15, resultStr.indexOf("</RequestResult>"));
			resultStr = StringEscapeUtils.unescapeXml(resultStr);
			return readStringXmlOut(resultStr);
		} catch (Exception e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	public static Map<String, Object> readStringXmlOut(String xml) {
		try {
			Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML

			Element rootElt = doc.getRootElement(); // 获取根节点

			Iterator iter = rootElt.elementIterator(); // 获取根节点下的子节点head
			return toMap(iter);
		} catch (DocumentException e) {
			logger.error(e.getMessage(), e);
		}
		return null;
	}

	private static Map<String, Object> toMap(Iterator iter) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 遍历head节点
		while (iter.hasNext()) {
			Element recordEle = (Element) iter.next();
			Iterator iterator = recordEle.elementIterator();
			if (iterator.hasNext()) {
				Map<String, Object> subMap = toMap(iterator);
				List attributes = recordEle.attributes();
				for (Object attribute1 : attributes) {
					Attribute attribute = (Attribute) attribute1;
					subMap.put(attribute.getName(), attribute.getValue());
				}
				packMap(map, recordEle.getName(), subMap);

			} else {
				List attributes = recordEle.attributes();
				if (attributes.isEmpty()) {
					packMap(map, recordEle.getName(), recordEle.getData());
					continue;
				}
				Map<String, Object> subMap = new HashMap<String, Object>();
				subMap.put("value", recordEle.getData());
				for (Object attribute1 : attributes) {
					Attribute attribute = (Attribute) attribute1;
					subMap.put(attribute.getName(), attribute.getValue());
				}
				packMap(map, recordEle.getName(), subMap);
			}


		}
		return map;
	}

	@SuppressWarnings("unchecked")
	private static boolean packMap(Map<String, Object> map, String name, Object object) {
		if (!map.containsKey(name)) {
			map.put(name, object);
			return true;
		}
		if (map.get(name) instanceof List) {
			List list = (List) map.get(name);
			list.add(object);
		} else {
			List list = new ArrayList();
			list.add(map.get(name));
			list.add(object);
			map.put(name, list);
		}
		return false;
	}

}
