package com.framework.struts;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

/**
 * Created by zzl on 2016/4/27.
 */
public class JsonFloatValueProcessor implements JsonValueProcessor {

    public JsonFloatValueProcessor() {
        super();
    }

    @Override
    public Object processArrayValue(Object o, JsonConfig jsonConfig) {
        return process(o);
    }

    @Override
    public Object processObjectValue(String s, Object o, JsonConfig jsonConfig) {
        return process(o);
    }

    private Object process(Object value) {
        try {
            return value == null ? "" : String.valueOf(value);
        } catch (Exception e) {
            return "";
        }

    }
}
