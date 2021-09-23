package com.data.data.hmly.service.base.service;

import com.data.data.hmly.service.base.ResultModel;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.Projections;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 * Created by zzl on 2015/12/7.
 */
public abstract class BaseService<T extends com.framework.hibernate.util.Entity> {

    public T get(Long id, Class<T> clazz) {
        return getDao().get(clazz, id);
    }

    public T load(Long id) {
        return getDao().load(id);
    }

    public T one(Class<T> clazz, Map<String, Object> paramMap) {
        Criteria<T> c = new Criteria<T>(clazz);
        c = makeCriteria(paramMap, c);
        return getDao().findUniqueByCriteria(c);
    }

    public ResultModel<T> page(Class<T> clazz, Map<String, Object> paramMap) {
        ResultModel<T> result = new ResultModel<T>();
        Criteria<T> c = new Criteria<T>(clazz);
        c = makeCriteria(paramMap, c);
        if (paramMap.containsKey("page")) {
            Integer rows = paramMap.get("rows").toString() == null ? 15 : Integer.parseInt(paramMap.get("rows").toString());
            Page page = new Page(Integer.parseInt(paramMap.get("page").toString()), rows);
            result.setRows(getDao().findByCriteria(c, page));
            result.setTotal(page.getTotalCount());
            return result;
        }
        result.setRows(getDao().findByCriteria(c));
        return result;
    }

    public List<T> list(Class<T> clazz, Map<String, Object> paramMap) {
        ResultModel<T> result = page(clazz, paramMap);
        List<T> resultList = result.getRows();
        if (resultList != null && resultList.size() > 0) {
            return resultList;
        }
        return null;
    }

    public Integer count(Class<T> clazz, Map<String, Object> paramMap) {
        Criteria<T> c = new Criteria<T>(clazz);
        c = makeCriteria(paramMap, c);
        c.setProjection(Projections.rowCount());
        return ((Number) getDao().findLongCriteria(c)).intValue();
    }

    public void update(Object entity) {
        getDao().update(entity);
    }

    public void insert(Object entity) {
        getDao().save(entity);
    }

    public void del(Serializable id, Class<T> clazz) {
        getDao().delete(id, clazz);
    }

    public void del(Object entity) {
        getDao().delete(entity);
    }

    public abstract Criteria<T> makeCriteria(Map<String, Object> paramMap, Criteria<T> c);

    public abstract DataAccess<T> getDao();
}
