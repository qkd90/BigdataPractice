package com.hmlyinfo.base.util;

import com.google.common.collect.Sets;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ListUtil {
	public static <T> T getSingle(List<T> list) {
		return list.size() > 0 ? list.get(0) : null;
	}

	public static List<?> removeItem(List<?> list, String itemName, String itemValue) {
		if (list == null) {
			return null;
		}

		for (int i = 0; i < list.size(); i++) {
			Object o = list.get(i);
			try {
				String propVal = BeanUtils.getProperty(o, itemName);
				if (itemValue.equals(propVal)) {
					list.remove(i);
				}
			} catch (Exception e) {
				// TODO
			}

		}

		return list;
	}

	public static List<Map<String, Object>> listJoin(
		List firstList, List secondList,
		String mapping, String namespace, ColumnGenerator generator) {

		Map<String, String> mappingMap = new HashMap<String, String>();

		String[] exprArr = mapping.split(",");
		for (String expr : exprArr) {
			String[] term = expr.split("=");
			mappingMap.put(term[0], term[1]);
		}

		return listJoin(firstList, secondList, mappingMap, namespace, generator);
	}

	/**
	 * 传入两个Map的List，以及两者之间的关系，secondList的key需要唯一
	 * firstList对secondList中元素的关系应为多对一（也可以是多对零）
	 * 如firstList中有两个Key，分别为A和B，secondList中有三个Key，分别为C,D,E
	 * mapping中定义Key为A，value为C
	 * 那么，当firstList.A==secondList.C时，将secondList中对应行加入到firstList对应行中
	 * 例：A  B | C  D  E
	 * 1  2   3  4  5
	 * 6  7   6  7  8
	 * 返回{A:1,B:2},{A:6,B:7,C:6,D:7,E:8}
	 *
	 * @param firstList  被join的List
	 * @param secondList join的List
	 * @param mapping    两个List之间的对应关系
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @author Carol
	 */
	public static List<Map<String, Object>> listJoin(
		List firstList, List secondList,
		Map<String, String> mapping, String namespace, ColumnGenerator generator) {

		List resultList = null;
		try {
			resultList = listJoinExector(firstList, secondList, mapping, namespace, generator);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return resultList;
	}

	private static List<Map<String, Object>> listJoinExector(List firstList, List secondList,
	                                                         Map<String, String> mapping, String namespace, ColumnGenerator generator) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		List resultList = new ArrayList();

		Map<String, Object> map2 = new LinkedHashMap<String, Object>();
		for (Object o : secondList) {
			String key = "";
			for (Map.Entry<String, String> entry : mapping.entrySet()) {
				key = key + "$$" + BeanUtils.getProperty(o, entry.getValue());
			}
			map2.put(key, o);
		}

		// 根据hashkey进行连接，以list1为基准进行左连接
		for (Object o : firstList) {
			Map resultMap = coverToMap(o);
			String key = "";
			for (Map.Entry<String, String> entry : mapping.entrySet()) {
				key = key + "$$" + BeanUtils.getProperty(o, entry.getKey());
			}
			Map subMap = coverToMap(map2.get(key));

			// 当传递列结果集自定义方法时，使用自定义方法，不使用默认方法
			if (generator != null) {
				Map extMap = generator.generateColumns(resultMap, subMap);
				// 当返回为空的时候不设置连接结果
				if (extMap != null) {
					if (StringUtils.isBlank(namespace)) {
						resultMap.putAll(extMap);
					} else {
						resultMap.put(namespace, extMap);
					}
				}

			}
			// 没有传递自定义结果既生成方法时，使用默认方法
			else {
				if (subMap != null) {
					if (StringUtils.isBlank(namespace)) {
						resultMap.putAll(subMap);
					} else {
						resultMap.put(namespace, subMap);
					}
				}
			}

			resultList.add(resultMap);
		}

		return resultList;
	}

	private static Map coverToMap(Object o) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		if (o == null) {
			return null;
		}
		if (o instanceof Map) {
			return (Map) o;
		}

		Map map = PropertyUtils.describe(o);
		map.remove("class");

		return map;
	}

	/**
	 * 传入两个Map的List，以及两者之间的关系，firstList的key需要唯一
	 * firstList对secondList中元素的关系应为一对多
	 * 如firstList中有两个Key，分别为A和B，secondList中有三个Key，分别为C,D,E
	 * mapping中定义Key为A，value为C
	 * 那么，当firstList.A==secondList.C时，将secondList中对应行加入到firstList对应行中
	 * 例：A  B | C  D  E
	 * 1  2   6  4  5
	 * 6  7   6  7  8
	 * 返回{A:1,B:2,{}},{A:6,B:7,{[C:6,D:7,E:8],[C:6,D:4,E:5]}}
	 *
	 * @param firstList  被join的List
	 * @param secondList join的List
	 * @param mapping    两个List之间的对应关系
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalAccessException
	 * @author Carol
	 */
	public static List<Map<String, Object>> listJoinOne2N(
		List firstList, List secondList,
		Map<String, String> mapping, ColumnGenerator generator, String listName) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
		//处理两个Map之间的对应关系
		String firstKey = new String();
		String secondKey = new String();
		Iterator<String> firstKeySet = null;
		List<String> firstKeyList = new ArrayList<String>();
		List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>();

		if (mapping.size() > 1) {
			firstKeySet = mapping.keySet().iterator();
			while (firstKeySet.hasNext()) {
				String nextKey = firstKeySet.next();
				firstKey = firstKey + "|" + nextKey;
				secondKey = secondKey + "|" + mapping.get(nextKey);
				firstKeyList.add(nextKey);
			}
		}
		Map<Object, Map<String, Object>> firstMap = new HashMap<Object, Map<String, Object>>();

		for (int i = 0; i < firstList.size(); ++i) {
			String key = new String();
			Map<String, Object> tempMap;
			if (firstList.get(i).getClass().equals(HashMap.class)) {
				tempMap = (Map<String, Object>) firstList.get(i);
			} else {
				tempMap = BeanUtils.describe(firstList.get(i));
			}
			if (firstKeyList.size() == 0) {
				key = tempMap.get(firstKey).toString();
			} else {
				for (int j = 0; j < firstKeyList.size(); ++j) {
					key = key + "|" + tempMap.get(firstKeyList.get(j));
				}
			}
			firstMap.put(key, tempMap);
		}

		for (int i = 0; i < secondList.size(); ++i) {
			String key = new String();
			if (firstKeyList.size() == 0) {
				key = BeanUtils.describe(secondList.get(i)).get(secondKey).toString();
			} else {
				for (int j = 0; j < firstKeyList.size(); ++j) {
					key = key + "|" + BeanUtils.describe(secondList.get(i)).get(mapping.get(firstKeyList.get(j)));
				}
			}
			if (firstMap.get(key) != null) {
				if (firstMap.get(key).get(listName) == null) {
					List<Object> joinedList = new ArrayList<Object>();
					firstMap.get(key).put(listName, joinedList);
				}
				List<Object> joinedList = (List<Object>) firstMap.get(key).get(listName);
				if (generator != null) {
					joinedList.add(generator.generateColumns(null, BeanUtils.describe(secondList.get(i))));
				} else {
					joinedList.add(secondList.get(i));
				}

			}
		}

		Iterator keySet = firstMap.keySet().iterator();
		while (keySet.hasNext()) {
			resultList.add(firstMap.get(keySet.next()));
		}
		return resultList;
	}

	public static List getIdList(List srcList, String idName) {
		List resultList = new ArrayList();

		try {
			for (Object o : srcList) {
				resultList.add(BeanUtils.getProperty(o, idName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}

	public static Set getIdSet(List srcList, String idName) {
		Set resultSet = Sets.newHashSet();
		try {
			for (Object o : srcList) {
				resultSet.add(PropertyUtils.getProperty(o, idName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultSet;
	}

	public static List getIdListEx(List srcList, String idName) {
		List resultList = new ArrayList();

		try {
			for (Object o : srcList) {
				resultList.add(PropertyUtils.getProperty(o, idName));
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		return resultList;
	}


}
