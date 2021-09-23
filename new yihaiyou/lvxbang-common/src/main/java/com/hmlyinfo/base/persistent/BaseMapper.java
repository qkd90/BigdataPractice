package com.hmlyinfo.base.persistent;

import java.util.List;
import java.util.Map;

public interface BaseMapper<T> {
	public T selById(String id);

	public List<T> list(Map<String, Object> paramMap);

	public int count(Map<String, Object> paramMap);

	public T update(T t);

	public T insert(T t);

	public void del(String sn);
}
