package com.framework.hibernate.util;

import com.framework.hibernate.annotation.RegFilter;
import com.zuipin.util.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by dy on 2017/2/22.
 */
public class RegUtil {

    public static void regFilter(Object entity) {
        Class objClass = entity.getClass();
        Method[] methods = objClass.getMethods();
        if (objClass.getAnnotation(RegFilter.class) != null) {

            for(Method m : methods){
                //获取字段中包含RegFilter的注解
                RegFilter meta = m.getAnnotation(RegFilter.class);
                if (meta != null) {
                    Object val = null;
                    try {
                        String getMethodName = m.getName();
                        String setMethodName = "s" + getMethodName.substring(1, getMethodName.length());
                        Method setMethod = objClass.getMethod(setMethodName, m.getReturnType());    //获取setter方法
                        val = m.invoke(entity); //取值
                        if (val != null) {
                            setMethod.invoke(entity, StringUtils.filterString(val.toString())); //设值
                        }
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (NoSuchMethodException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

    }
}
