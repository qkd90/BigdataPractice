package com.framework.struts;

import net.sf.json.JsonConfig;
import net.sf.json.processors.JsonValueProcessor;

public class JsonBooleanValueProcessor implements JsonValueProcessor {

    /**
     * JsonDateValueProcessor
     */
    public JsonBooleanValueProcessor() {
        super();
    }

    /**
     * @param value
     * @param jsonConfig
     * @return Object
     */
    public Object processArrayValue(Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    /**
     * @param key
     * @param value
     * @param jsonConfig
     * @return Object
     */
    public Object processObjectValue(String key, Object value, JsonConfig jsonConfig) {
        return process(value);
    }

    /**
     * process
     * 
     * @param value
     * @return
     */
    private Object process(Object value) {
        try {
            return value == null ? "" : value;
        }
        catch (Exception e) {
            return "";
        }

    }
}
