package org.apache.ibatis.reflection.wrapper;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;

public class MapWrapper extends BaseWrapper {

	private Map map;

	public MapWrapper(MetaObject metaObject, Map map) {
		super(metaObject);
		this.map = map;
	}

	public Object get(PropertyTokenizer prop) {
		if (prop.getIndex() != null) {
			Object collection = resolveCollection(prop, map);
			return getCollectionValue(prop, collection);
		} else {
			return map.get(prop.getName());
		}
	}

	public void set(PropertyTokenizer prop, Object value) {
		if (prop.getIndex() != null) {
			Object collection = resolveCollection(prop, map);
			setCollectionValue(prop, collection, value);
		} else {
//			 map.put(prop.getName(), value);
			
			// 默认情况下，映射实例如下"shop_name" -> "shopName"
			String propName = changDBColumnToPropertyName(prop.getName());
			// 如果propName是全大写格式，则不处理
			if (!StringUtils.isAllUpperCase(propName))
			{
				map.put(changDBColumnToPropertyName(prop.getName()),value);
			}
		}
	}

	/**
     * 将数据库字段转换为Camel命名形式
     * 事例：
     * input: shop_name
     * output:shopName
     * @param column
     * @return
     */
    private String changDBColumnToPropertyName(String column){
        if (StringUtils.isBlank(column) 
        		|| column.equals("_parameter")//默认内置参数名
        		|| column.startsWith("__frch")//foreach标签相关
        		|| column.indexOf("_") == -1) {//不含下划线
            return column;
        }

        String[] words = column.toLowerCase().split("_");//全部先转换为小写再拆分
        StringBuilder builder = new StringBuilder(words[0]);
        for (int i = 1; i < words.length; i++) {
            builder.append(StringUtils.capitalize(words[i]));
        }

        return builder.toString();
    }
    
	public String findProperty(String name) {
		return name;
	}

	public String[] getGetterNames() {
		return (String[]) map.keySet().toArray(new String[map.keySet().size()]);
	}

	public String[] getSetterNames() {
		return (String[]) map.keySet().toArray(new String[map.keySet().size()]);
	}

	public Class getSetterType(String name) {
		PropertyTokenizer prop = new PropertyTokenizer(name);
		if (prop.hasNext()) {
			MetaObject metaValue = metaObject.metaObjectForProperty(prop
					.getIndexedName());
			if (metaValue == MetaObject.NULL_META_OBJECT) {
				return Object.class;
			} else {
				return metaValue.getSetterType(prop.getChildren());
			}
		} else {
			// 直接将CREATE_TIME映射类型定义成long
//			if ("create_time".equals(name.toLowerCase()))
//			{
//				return com.hmlyinfo.base.TimestampLong.class;
//			}
			// JAVA的字段是DB字段的Camel形式，需要转换
			String javaName = changDBColumnToPropertyName(name);
			if (map.get(javaName) != null) {
				return map.get(javaName).getClass();
			}
			else {
				return Object.class;
			}
		}
	}

	public Class getGetterType(String name) {
		PropertyTokenizer prop = new PropertyTokenizer(name);
		if (prop.hasNext()) {
			MetaObject metaValue = metaObject.metaObjectForProperty(prop
					.getIndexedName());
			if (metaValue == MetaObject.NULL_META_OBJECT) {
				return Object.class;
			} else {
				return metaValue.getGetterType(prop.getChildren());
			}
		} else {
			if (map.get(name) != null) {
				return map.get(name).getClass();
			} else {
				return Object.class;
			}
		}
	}

	public boolean hasSetter(String name) {
		return true;
	}

	public boolean hasGetter(String name) {
		PropertyTokenizer prop = new PropertyTokenizer(name);
		if (prop.hasNext()) {
			if (map.containsKey(prop.getIndexedName())) {
				MetaObject metaValue = metaObject.metaObjectForProperty(prop
						.getIndexedName());
				if (metaValue == MetaObject.NULL_META_OBJECT) {
					return map.containsKey(name);
				} else {
					return metaValue.hasGetter(prop.getChildren());
				}
			} else {
				return false;
			}
		} else {
			return map.containsKey(name);
		}
	}

	public MetaObject instantiatePropertyValue(String name,
			PropertyTokenizer prop, ObjectFactory objectFactory) {
		HashMap map = new HashMap();
		set(prop, map);
		return MetaObject.forObject(map, metaObject.getObjectFactory(),
				metaObject.getObjectWrapperFactory());
	}

}
