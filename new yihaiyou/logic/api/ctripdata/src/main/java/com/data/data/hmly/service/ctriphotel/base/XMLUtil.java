package com.data.data.hmly.service.ctriphotel.base;

import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/5/22.
 */
public class XMLUtil {

    public static Map<String, Object> readStringXmlOut(String xml) {
        try {
            Document doc = DocumentHelper.parseText(xml); // 将字符串转为XML

            Element rootElt = doc.getRootElement(); // 获取根节点

            Iterator iter = rootElt.elementIterator(); // 获取根节点下的子节点head
            return toMap(iter);
        } catch (DocumentException e) {
            e.printStackTrace();
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
