package com.zuipin.util;

import com.fasterxml.jackson.annotation.JsonAutoDetect.Visibility;
import com.fasterxml.jackson.annotation.PropertyAccessor;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.FilterProvider;
import com.fasterxml.jackson.databind.ser.impl.SimpleBeanPropertyFilter;
import com.fasterxml.jackson.databind.ser.impl.SimpleFilterProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * 项目名称：taxi 类描述： Jackson实用类 创建人：吴鹏 创建时间：2013-6-23 下午7:43:07
 */
public class Jacksons {
    private ObjectMapper objectMapper;

    public static Jacksons me() {
        return new Jacksons();
    }

    private Jacksons() {
        objectMapper = new ObjectMapper();
        // 设置输入时忽略在JSON字符串中存在但Java对象实际没有的属性
        objectMapper.setVisibility(PropertyAccessor.FIELD, Visibility.ANY);
        objectMapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        objectMapper.setDateFormat(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss"));
    }

    public Jacksons filter(String[] filterName, String[]... properties) {

        SimpleFilterProvider sfp = new SimpleFilterProvider();

        for (int i = 0; i < filterName.length; i++) {
            sfp.addFilter(filterName[i], SimpleBeanPropertyFilter.serializeAllExcept(properties[i]));
        }

        FilterProvider filterProvider = sfp;

        objectMapper.setFilters(filterProvider);
        return this;
    }

    public Jacksons setDateFormate(DateFormat dateFormat) {
        objectMapper.setDateFormat(dateFormat);
        return this;
    }

    public <T> T json2Obj(String json, Class<T> clazz) {
        try {
            return objectMapper.readValue(json, clazz);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解析json错误");
        }
    }

    public String readAsString(Object obj) {
        try {
            return objectMapper.writeValueAsString(obj);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("解析对象错误");
        }
    }

}
