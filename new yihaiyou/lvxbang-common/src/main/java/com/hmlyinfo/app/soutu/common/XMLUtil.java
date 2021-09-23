package com.hmlyinfo.app.soutu.common;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by guoshijie on 2014/8/15.
 */
public class XMLUtil {

	public static Map<String, Object> readStringXmlOut(String xml) {
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML

			Element rootElt = doc.getRootElement(); // 获取根节点

			Iterator iter = rootElt.elementIterator(); // 获取根节点下的子节点head
			map = toMap(iter);

		} catch (DocumentException e) {
			e.printStackTrace();
		}
		return map;
	}

	private static Map<String, Object> toMap(Iterator iter) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 遍历head节点
		while (iter.hasNext()) {
			Element recordEle = (Element) iter.next();
			Iterator iterator = recordEle.elementIterator();
			if (iterator.hasNext()) {
				map.put(recordEle.getName(), toMap(iterator));
			} else {
				map.put(recordEle.getName(), recordEle.getStringValue());
			}
		}
		return map;
	}

}
