package com.hmlyinfo.base.util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ParamUtil {

	public static Map<String, Object> createIdsMap(List srcList, String idName) {
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("ids", ListUtil.getIdList(srcList, idName));

		return paramMap;
	}

}
