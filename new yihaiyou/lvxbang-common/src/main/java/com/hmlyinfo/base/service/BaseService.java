package com.hmlyinfo.base.service;

import com.hmlyinfo.base.ActionResult;
import com.hmlyinfo.base.ResultList;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.util.ListUtil;
import org.apache.commons.beanutils.BeanUtils;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public abstract class BaseService<T, ID extends Serializable> {
	public abstract BaseMapper<T> getMapper();

	public abstract String getKey();

	/**
	 * 根据主键查询一条记录
	 *
	 * @param id
	 * @return
	 */
	public T info(ID id) {
		return getMapper().selById(id + "");
	}

	public int count(Map<String, Object> paramMap) {
		paramMap.put("operCount", true);
		return getMapper().count(paramMap);
	}

	public ActionResult countAsResult(Map<String, Object> paramMap) {
		paramMap.put("operCount", true);
		int counts = getMapper().count(paramMap);
		ActionResult result = new ActionResult();
		ResultList<Object> resultList = new ResultList<Object>();
		resultList.setCounts(counts);
		result.setResultList(resultList);

		return result;
	}

	public ResultList<T> ListEx(Map<String, Object> paramMap) {
		ResultList<T> resultList = new ResultList<T>();
		resultList.setData(list(paramMap));
		resultList.setCounts(count(paramMap));

		return resultList;
	}

	public List<T> list(Map<String, Object> paramMap) {
		return getMapper().list(paramMap);
	}

	public List<T> listColumns(Map<String, Object> params, List<String> columns) {
		params.put("needColumns", columns);
		return getMapper().list(params);
	}

	public Object one(Map<String, Object> paramMap) {
		return ListUtil.getSingle(list(paramMap));
	}

	public T update(T t) {
		return getMapper().update(t);
	}

	public T insert(T t) {
		return getMapper().insert(t);
	}

	public void del(String id) {
		getMapper().del(id);
	}

	/**
	 * 新增或者更新
	 *
	 * @param t
	 * @return
	 */
	public T edit(T t) {
		T result;
		try {
			String key = BeanUtils.getProperty(t, getKey());
			result = getMapper().selById(key);
			if (result == null) {
				result = getMapper().insert(t);
			} else {
				result = getMapper().update(t);
			}
		} catch (Exception e) {
			e.printStackTrace();
			result = null;
		}

		return result;

	}

}
