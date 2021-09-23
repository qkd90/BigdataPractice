package com.hmlyinfo.base.util;

import java.util.Map;

public interface ColumnGenerator {

	Map<String, Object> generateColumns(Map<String, Object> leftMap, Map<String, Object> rightMap);

}
