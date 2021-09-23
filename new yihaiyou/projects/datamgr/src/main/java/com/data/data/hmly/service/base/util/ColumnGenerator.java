package com.data.data.hmly.service.base.util;

import java.util.Map;

public interface ColumnGenerator {
	
	Map<String, Object> generateColumns(Map<String, Object> srcMap);

}
