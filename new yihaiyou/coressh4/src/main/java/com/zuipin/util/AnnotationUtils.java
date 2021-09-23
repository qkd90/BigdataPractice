package com.zuipin.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

public class AnnotationUtils {
	public static List<Field> getHaveAnnotationByField(Class clazz, Class annotationClass) {
		List returnfields = new ArrayList();
		
		Field[] fields = clazz.getDeclaredFields();
		
		for (Field field : fields) {
			if (field.getAnnotation(annotationClass) != null) {
				returnfields.add(field);
			}
		}
		return returnfields;
	}
	
	public static Field getHaveAnnotationByFirstField(Class clazz, Class annotationClass) {
		Field[] fields = clazz.getDeclaredFields();
		
		for (Field field : fields) {
			if (field.getAnnotation(annotationClass) != null) {
				return field;
			}
		}
		return null;
	}
	
	public static List<Method> getHaveAnnotationByMethod(Class clazz, Class annotationClass) {
		List returnmethods = new ArrayList();
		
		Method[] methods = clazz.getDeclaredMethods();
		
		for (Method method : methods) {
			if (method.getAnnotation(annotationClass) != null) {
				returnmethods.add(method);
			}
		}
		return returnmethods;
	}
	
	public static Method getHaveAnnotationByFirstMethod(Class clazz, Class annotationClass) {
		Method[] methods = clazz.getDeclaredMethods();
		
		for (Method method : methods) {
			if (method.getAnnotation(annotationClass) != null) {
				return method;
			}
		}
		return null;
	}
}