package com.zuipin.util;

import com.thoughtworks.xstream.io.json.JsonHierarchicalStreamDriver;
import com.thoughtworks.xstream.io.xml.DomDriver;

public class XStream {
    private static com.thoughtworks.xstream.XStream xml;
    private static com.thoughtworks.xstream.XStream json;

    static {
        xml = new com.thoughtworks.xstream.XStream(new DomDriver());
        xml.autodetectAnnotations(true);

        json = new com.thoughtworks.xstream.XStream(new JsonHierarchicalStreamDriver());
        json.setMode(com.thoughtworks.xstream.XStream.NO_REFERENCES);
        json.autodetectAnnotations(true);
    }

    public static String toXML(Object object) {
        return xml.toXML(object);
    }

    @SuppressWarnings("unchecked")
    public static <T> T fromXML(String xml) {
        return (T) XStream.xml.fromXML(xml);
    }

    public static String toJSON(Object object) {
        return json.toXML(object);
    }
}
