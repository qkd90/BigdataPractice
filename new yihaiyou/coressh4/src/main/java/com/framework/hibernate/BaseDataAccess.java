package com.framework.hibernate;

import com.framework.hibernate.util.Entity;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.zuipin.util.ConvertUtils;
import javassist.Modifier;
import net.sf.json.JSONObject;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.ScrollableResults;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projection;
import org.hibernate.criterion.Projections;
import org.hibernate.internal.CriteriaImpl;
import org.hibernate.internal.SessionImpl;
import org.hibernate.transform.ResultTransformer;
import org.hibernate.transform.RootEntityResultTransformer;
import org.hibernate.transform.Transformers;
import org.hibernate.type.StandardBasicTypes;
import org.hibernate.type.Type;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.orm.hibernate4.HibernateTemplate;

import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import java.io.Serializable;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Kingsley
 */
public abstract class BaseDataAccess<T extends Entity> {
    private HibernateTemplate hibernateTemplate;
    private boolean isCacheable = false;
    private Class<T> entityClass;
    private final static Logger logger = Logger.getLogger(BaseDataAccess.class);

    public void setCacheable(boolean isCacheable) {
        this.isCacheable = isCacheable;
    }

    public boolean getCacheable() {
        return isCacheable;
    }

    public HibernateTemplate getHibernateTemplate() {
        return this.hibernateTemplate;
    }

    public void setHibernateTemplate(HibernateTemplate hibernateTemplate) {
        this.hibernateTemplate = hibernateTemplate;
    }

    public T changeProxyToEntity(final T t) {
        return (T) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                return ((SessionImpl) session).immediateLoad(getClass().getName(), session.getIdentifier(t));
            }
        });
    }

    @SuppressWarnings("unchecked")
    public T load(Serializable id) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return hibernateTemplate.get(getPOJO(), id);
    }

    public void save(Object entity) {
//        RegUtil.regFilter(entity); //通过注解的方式，过滤字段的属性值
        hibernateTemplate.save(entity);
    }

    public void evict(Object entity) {
        hibernateTemplate.evict(entity);
    }

    public void save(final List<?> objs) {
        hibernateTemplate.executeWithNativeSession(new HibernateCallback() {

            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                for (int i = 0; i < objs.size(); i++) {
                    session.saveOrUpdate(objs.get(i));
                    if (i % 20 == 0) {
                        session.flush();
                        session.clear();
                    }
                }
                return null;
            }
        });
    }

    public void saveOrUpdate(Object entity, Integer id) {
        if (id == null || id <= 0) {
            hibernateTemplate.save(entity);
        } else {
            hibernateTemplate.update(entity);
        }
    }

    public void saveOrUpdate(Object entity, Long id) {
        if (id == null || id <= 0) {
            hibernateTemplate.save(entity);
        } else {
            hibernateTemplate.update(entity);
        }
    }

    public void delete(Object entity) {
        hibernateTemplate.delete(entity);
    }

    public void deleteAll(Collection<T> clazzes) {
        hibernateTemplate.deleteAll(clazzes);
    }

    @SuppressWarnings("unchecked")
    public List<T> findByCriteria(final com.framework.hibernate.util.Criteria<T> criteria) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                org.hibernate.Criteria hibernateCriteria = criteria.getExecutableCriteria(session);
                hibernateCriteria.setCacheable(isCacheable);
                hibernateCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                return hibernateCriteria.list();
            }
        });
    }

    public com.framework.hibernate.util.Criteria<T> getCriteria() {
        return new com.framework.hibernate.util.Criteria<T>(getPOJO());
    }

    @SuppressWarnings("unchecked")
    public T findUniqueByCriteria(final com.framework.hibernate.util.Criteria<T> criteria) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (T) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                org.hibernate.Criteria hibernateCriteria = criteria.getExecutableCriteria(session);
                hibernateCriteria.setCacheable(isCacheable);
                hibernateCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                return hibernateCriteria.uniqueResult();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public Long findLongCriteria(final com.framework.hibernate.util.Criteria<T> criteria) {
        return (Long) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                org.hibernate.Criteria hibernateCriteria = criteria.getExecutableCriteria(session);
                hibernateCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                List<Object> list = hibernateCriteria.list();
                if (!list.isEmpty()) {
                    return list.get(0);
                } else {
                    return null;
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    public Integer findIntegerCriteria(final com.framework.hibernate.util.Criteria<T> criteria) {
        return (Integer) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                org.hibernate.Criteria hibernateCriteria = criteria.getExecutableCriteria(session);
                hibernateCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                List<Object> list = hibernateCriteria.list();
                if (!list.isEmpty()) {
                    return list.get(0);
                } else {
                    return null;
                }
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByHQL(String hql, Object... params) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.find(hql, params);
    }

    @SuppressWarnings("unchecked")
    public Long findLongByHQL(String hql, Object... params) {
        hibernateTemplate.setCacheQueries(isCacheable);
        List list = hibernateTemplate.find(hql, params);
        Long count = 0l;
        try {
            if (list != null && list.size() > 0) {
                Object obj = list.get(0);
                if (obj != null) {
                    count = Long.valueOf(String.valueOf(list.get(0)));
                }
            }
        } catch (Exception e) {
            // TODO
            Logger.getLogger(getClass()).error(e);
            System.out.println("查询结果无法转化为Long型!");
        }
        return count;
    }

    @SuppressWarnings("unchecked")
    public Double findDoubleByHQL(String hql, Object... params) {
        hibernateTemplate.setCacheQueries(isCacheable);
        List list = hibernateTemplate.find(hql, params);
        Double count = 0d;
        try {
            if (list != null && list.size() > 0) {
                Object obj = list.get(0);
                if (obj != null) {
                    count = Double.valueOf(String.valueOf(list.get(0)));
                }
            }
        } catch (Exception e) {
            // TODO
            Logger.getLogger(getClass()).error(e);
            System.out.println("查询结果无法转化为Double型!");
        }
        return count;
    }

    // @SuppressWarnings("unchecked")
    // public Integer findIntegerByHQL(String hql, Object... params) {
    // hibernateTemplate.setCacheQueries(isCacheable);
    // List list = hibernateTemplate.find(hql, params);
    // Integer count = 0;
    // try {
    // if (list != null && list.size() > 0) {
    // count = Integer.valueOf(String.valueOf(list.get(0)));
    // }
    // } catch (Exception e) {
    // // TODO
    // System.out.println("查询结果无法转化为Long型!");
    // }
    // return count;
    // }

    /**
     * @param hql
     * @param params
     * @return 只取一个对象
     */
    @SuppressWarnings("unchecked")
    public T findOneByHQL(String hql, Object... params) {
        hibernateTemplate.setCacheQueries(isCacheable);
        List<T> list = (List<T>) hibernateTemplate.find(hql, params);
        if (null != list && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public void update(Object entity) {
        hibernateTemplate.update(entity);
    }

    public void updateTransisent(Object entity) {
        try {
            String className = entity.getClass().getSimpleName();
            Field[] methods = entity.getClass().getDeclaredFields();
            List<Object> params = new ArrayList<Object>();
            Field idField = null;
            StringBuffer sb = new StringBuffer();
            sb.append("update " + className + " set ");
            Boolean first = true;
            for (Field field : methods) {
                field.setAccessible(true);
                if (field.isAnnotationPresent(Id.class)) {
                    idField = field;
                    continue;
                }
                String name = field.getName();
                Method method = null;
                try {
                    method = entity.getClass().getMethod("get" + name.substring(0, 1).toUpperCase() + name.substring(1, name.length()));
                } catch (Exception e) {
                    // TODO: handle exception
                    continue;
                }
                if (method != null && method.isAnnotationPresent(Id.class)) {
                    idField = field;
                    continue;
                }
                if (!(field.isAnnotationPresent(Transient.class) || method.isAnnotationPresent(Transient.class))
                        && !(field.isAnnotationPresent(javax.persistence.Entity.class) || method
                        .isAnnotationPresent(javax.persistence.Entity.class))
                        && !(field.getClass().isAnnotationPresent(javax.persistence.Entity.class) || method.getReturnType()
                        .isAnnotationPresent(javax.persistence.Entity.class))
                        && !(field.isAnnotationPresent(ManyToOne.class) || method.isAnnotationPresent(ManyToOne.class))
                        && !(field.isAnnotationPresent(OneToMany.class) || method.isAnnotationPresent(OneToMany.class))
                        && !(field.isAnnotationPresent(OneToOne.class) || method.isAnnotationPresent(OneToOne.class))
                        && !(field.isAnnotationPresent(ManyToMany.class) || method.isAnnotationPresent(ManyToMany.class))
                        && !(Modifier.isStatic(field.getModifiers()) || Modifier.isStatic(method.getReturnType().getModifiers()))) {
                    Object obj = field.get(entity);
                    if (obj != null) {
                        if (first) {
                            sb.append(" ");
                        } else {
                            sb.append(", ");
                        }
                        sb.append(name).append(" = ?");
                        params.add(obj);
                        first = false;
                    }
                }
            }
            sb.append(" where " + idField.getName() + " = ?");
            params.add(idField.get(entity));
            updateByHQL(sb.toString(), params.toArray());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void updateAll(final Collection<T> objs) {
        for (T t : objs) {
            hibernateTemplate.update(t);
        }
        // hibernateTemplate.execute(new HibernateCallback<T>() {
        // @Override
        // public T doInHibernate(Session session) throws HibernateException {
        // // TODO Auto-generated method stub
        // Transaction ta = session.beginTransaction();
        // try {
        // int i = 1;
        // for (T t : objs) {
        // session.update(t);
        // if (i++ % 50 == 0) {
        // session.flush();
        // session.clear();
        // }
        // }
        // } catch (Exception e) {
        // // TODO: handle exception
        // ta.rollback();
        // }
        // ta.commit();
        // return null;
        // }
        // });
    }

    public void merge(Object entity) {
        hibernateTemplate.merge(entity);
    }

    @SuppressWarnings("unchecked")
    public T get(Class<T> clazz, Serializable id) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return hibernateTemplate.get(clazz, id);
    }

    @SuppressWarnings("unchecked")
    public List<T> findAll() {
        hibernateTemplate.setCacheQueries(isCacheable);
        return hibernateTemplate.loadAll(getPOJO());
    }

    public void delete(Serializable id, Class<T> poClass) {
        Entity po = hibernateTemplate.load(poClass, id);
        hibernateTemplate.delete(po);
    }

    @SuppressWarnings("unchecked")
    public List<T> selectAll(Class<T> cls) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return hibernateTemplate.loadAll(cls);
    }

    public List<T> findAll(final Page page) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return findByCriteria(new com.framework.hibernate.util.Criteria<T>(getPOJO()), page);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByHQL(final String hqlstr, final Page page) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hqlstr);
                ScrollableResults scrollableResults = query.scroll();
                scrollableResults.last();
                int rownum = scrollableResults.getRowNumber();
                page.setTotalCount(rownum + 1);
                query.setMaxResults(page.getPageSize());
                query.setFirstResult(page.getFirstResult());
                System.out.println(page.getPageSize() + "," + page.getFirstResult());
                query.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                return query.list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByHQL(final String hqlstr, final Page page, final Object... parameters) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hqlstr);
                for (int i = 0; i < parameters.length; i++) {
                    query.setParameter(i, parameters[i]);
                }
                ScrollableResults scrollableResults = query.scroll();
                scrollableResults.last();
                int rownum = scrollableResults.getRowNumber();
                page.setTotalCount(rownum + 1);
                query.setMaxResults(page.getPageSize());
                query.setFirstResult(page.getFirstResult());
                query.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                return query.list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByHQL2(final String hqlstr, final Map<String, Object> mapParam) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hqlstr);
                query.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                for (String key : mapParam.keySet()) {
                    // System.out.println(mapParam.get(key).getClass().getName());
                    if (mapParam.get(key).getClass().getName().equals("java.util.LinkedList")
                            || mapParam.get(key).getClass().getName().equals("java.util.ArrayList")
                            || mapParam.get(key).getClass().getName().equals("com.opensymphony.xwork2.util.XWorkList")) {
                        query.setParameterList(key, (Collection) mapParam.get(key));
                    } else {
                        query.setParameter(key, mapParam.get(key));
                    }
                }
                return query.list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<T> findByHQL2(final String hqlstr, final Page page, final Map<String, Object> mapParam) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hqlstr);
                query.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                for (String key : mapParam.keySet()) {
                    // System.out.println(mapParam.get(key).getClass().getName());
                    if (mapParam.get(key).getClass().getName().equals("java.util.LinkedList")
                            || mapParam.get(key).getClass().getName().equals("java.util.ArrayList")
                            || mapParam.get(key).getClass().getName().equals("com.opensymphony.xwork2.util.XWorkList")) {
                        query.setParameterList(key, (Collection) mapParam.get(key));
                    } else {
                        query.setParameter(key, mapParam.get(key));
                    }
                }
                if (page != null) {
                    ScrollableResults scrollableResults = query.scroll();
                    scrollableResults.last();
                    int rownum = scrollableResults.getRowNumber();
                    page.setTotalCount(rownum + 1);
                    query.setMaxResults(page.getPageSize());
                    query.setFirstResult(page.getFirstResult());
                }
                return query.list();
            }
        });
    }

    public List<T> findByHQL2ForNew(final String hqlstr, final Map<String, Object> mapParam) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hqlstr);
//                query.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                for (String key : mapParam.keySet()) {
                    // System.out.println(mapParam.get(key).getClass().getName());
                    if (mapParam.get(key).getClass().getName().equals("java.util.LinkedList")
                            || mapParam.get(key).getClass().getName().equals("java.util.ArrayList")
                            || mapParam.get(key).getClass().getName().equals("com.opensymphony.xwork2.util.XWorkList")) {
                        query.setParameterList(key, (Collection) mapParam.get(key));
                    } else {
                        query.setParameter(key, mapParam.get(key));
                    }
                }
                return query.list();
            }
        });
    }

    public List<T> findByHQL2ForNew(final String hqlstr, final Page page, final Map<String, Object> mapParam) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hqlstr);
//                query.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                for (String key : mapParam.keySet()) {
                    // System.out.println(mapParam.get(key).getClass().getName());
                    if (mapParam.get(key).getClass().getName().equals("java.util.LinkedList")
                            || mapParam.get(key).getClass().getName().equals("java.util.ArrayList")
                            || mapParam.get(key).getClass().getName().equals("com.opensymphony.xwork2.util.XWorkList")) {
                        query.setParameterList(key, (Collection) mapParam.get(key));
                    } else {
                        query.setParameter(key, mapParam.get(key));
                    }
                }
                if (page != null) {
                    ScrollableResults scrollableResults = query.scroll();
                    scrollableResults.last();
                    int rownum = scrollableResults.getRowNumber();
                    page.setTotalCount(rownum + 1);
                    query.setMaxResults(page.getPageSize());
                    query.setFirstResult(page.getFirstResult());
                }
                return query.list();
            }
        });
    }

    public <T> List<T> findBySQL(final String sql, final Object... parameters) {
        hibernateTemplate.setCacheQueries(isCacheable);
        // Class<T> cla = (Class<T>) ((ParameterizedType)
        // getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        return findEntitiesBySQL(sql, null, parameters);
    }

    public <T> List<T> findBySQL(final String sql) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return findEntitiesBySQL(sql);
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findEntitiesBySQL(final String sql, final Class<T> mappedClass, final Object... parameters) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                query.setCacheable(isCacheable);
                for (int i = 0; i < parameters.length; i++) {
                    Object para = parameters[i];
                    query.setParameter(i, para);
                }
                if (mappedClass != null) {
                    // query.addEntity(mappedClass);
                    query.setResultTransformer(Transformers.aliasToBean(mappedClass));

                }
                List<T> list = query.list();
                // System.out.println("===");
                return list;
            }
        });
    }
    @SuppressWarnings("unchecked")
    public <T> List<T> findEntitiesBySQL1(final String sql, final Class<T> mappedClass, final Object... parameters) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                query.setCacheable(isCacheable);
                for (int i = 0; i < parameters.length; i++) {
                    Object para = parameters[i];
                    query.setParameter(i, para);
                }
                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);

                List<Map<String, Object>> resultMapList = query.list();
                List<T> list = Lists.newArrayList();
                for (Map<String, Object> resultMap : resultMapList) {
                    JSONObject jsonObject = JSONObject.fromObject(resultMap);
                    list.add((T) JSONObject.toBean(jsonObject, mappedClass));
                }
                return list;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findEntitiesBySQL(final String sql) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                query.setCacheable(isCacheable);
                List<T> list = query.list();
                // System.out.println("===");
                return list;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findEntitiesBySQL(final String sql, final Class<T> mappedClass, final Map<String, Object> parameters) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                query.setCacheable(isCacheable);
                for (String key : parameters.keySet()) {
                    query.setParameter(key, parameters.get(key));
                }
                if (mappedClass != null) {
                    // query.addEntity(mappedClass);
                    query.setResultTransformer(Transformers.aliasToBean(mappedClass));
                }
                List<T> list = query.list();
                // System.out.println("===");
                return list;
            }
        });
    }
    @SuppressWarnings("unchecked")
    public <T> List<T> findEntitiesBySQL2(final String sql, final Page page, final Class<T> mappedClass, final Map<String, Object> parameters) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                query.setCacheable(isCacheable);
                for (String key : parameters.keySet()) {
                    query.setParameter(key, parameters.get(key));
                }


                if (mappedClass != null) {
                    query.addEntity(mappedClass);
//                    query.setResultTransformer(Transformers.aliasToBean(mappedClass));
                }

                query.setMaxResults(page.getPageSize());
                query.setFirstResult(page.getFirstResult());

                ScrollableResults scrollableResults = query.scroll();
                scrollableResults.last();
                int rownum = scrollableResults.getRowNumber();
                page.setTotalCount(rownum + 1);

                List<T> list = query.list();

                // System.out.println("===");
                return list;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findEntitiesBySQL3(final String sql, final Page page, final Class<T> mappedClass, final Object... parameters) {
//        public <T> List<T> findEntitiesBySQL3(final String sql, final Page page, final Class<T> mappedClass, final Map<String, Object> parameters) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                query.setCacheable(isCacheable);

                if (parameters != null && parameters.length > 0) {
                    for (int i = 0; i < parameters.length; i++) {
                        Object para = parameters[i];
                        query.setParameter(i, para);
                    }
                }

                ScrollableResults scrollableResults = query.scroll();
                scrollableResults.last();
                int rownum = scrollableResults.getRowNumber();
                page.setTotalCount(rownum + 1);

                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                query.setMaxResults(page.getPageSize());
                query.setFirstResult(page.getFirstResult());

                List<Map<String, Object>> resultMapList = query.list();
                List<T> list = Lists.newArrayList();

                for (Map<String, Object> resultMap : resultMapList) {
                    JSONObject jsonObject = JSONObject.fromObject(resultMap);
                    list.add((T) JSONObject.toBean(jsonObject, mappedClass));
                }
                return list;
            }
        });
    }
    @SuppressWarnings("unchecked")
    public List<Map<String, Object>> findEntitiesBySQL4(final String sql, final Page page, final Object... parameters) {
//        public <T> List<T> findEntitiesBySQL3(final String sql, final Page page, final Class<T> mappedClass, final Map<String, Object> parameters) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<Map<String, Object>>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                query.setCacheable(isCacheable);

                if (parameters != null && parameters.length > 0) {
                    for (int i = 0; i < parameters.length; i++) {
                        Object para = parameters[i];
                        query.setParameter(i, para);
                    }
                }

                ScrollableResults scrollableResults = query.scroll();
                scrollableResults.last();
                int rownum = scrollableResults.getRowNumber();
                page.setTotalCount(rownum + 1);

                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                query.setMaxResults(page.getPageSize());
                query.setFirstResult(page.getFirstResult());

                List<Map<String, Object>> resultMapList = query.list();
                return resultMapList;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findObjectBySQL(final String sql, final ResultTransformer transformer, final Object... parameters) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                query.setCacheable(isCacheable);
                for (int i = 0; i < parameters.length; i++) {
                    Object para = parameters[i];
                    query.setParameter(i, para);
                }
                query.setResultTransformer(transformer);
                return query.list();
            }
        });
    }

    public BigDecimal findIntegerBySQL(final String sql, final Object... parameters) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (BigDecimal) hibernateTemplate.execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                query.setCacheable(isCacheable);
                for (int i = 0; i < parameters.length; i++) {
                    Object para = parameters[i];
                    query.setParameter(i, para);
                }
                query.addScalar("result", StandardBasicTypes.BIG_DECIMAL);
                BigDecimal result = (BigDecimal) query.uniqueResult();
                return result;
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findEntitiesBySQL(final Page page, final String sql, final Class<T> mappedClass, final Map<String, Type> map,
                                         final Object... parameters) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                query.setCacheable(isCacheable);
                if (mappedClass != null) {
                    // query.addEntity(mappedClass);
                    query.setResultTransformer(Transformers.aliasToBean(mappedClass));
                    // query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                }
                if (map != null) {
                    for (Entry<String, Type> entry : map.entrySet()) {
                        query.addScalar(entry.getKey(), entry.getValue());
                    }
                }
                if (parameters != null) {
                    for (int i = 0; i < parameters.length; i++) {
                        Object para = parameters[i];
                        query.setParameter(i, para);
                    }
                }
                // ScrollableResults sar = query.scroll();
                // sar.last();
                // page.setTotalCount(sar.getRowNumber() + 1);
                query.setMaxResults(page.getPageSize());
                query.setFirstResult(page.getFirstResult());

                return query.list();
            }
        });
    }

    public List<Map<?, ?>> findMapBySQL(final String sql) {

        hibernateTemplate.setCacheQueries(isCacheable);

        return (List<Map<?, ?>>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {

            @Override

            public Object doInHibernate(Session session) throws HibernateException {

                SQLQuery query = session.createSQLQuery(sql);

                query.setCacheable(isCacheable);

                return query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP).list();

            }

        });

    }

    @SuppressWarnings("unchecked")
    public List<Map<?, ?>> findMapBySQL(final Page page, final String sql, final Object... parameters) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<Map<?, ?>>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                query.setCacheable(isCacheable);
                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                for (int i = 0; i < parameters.length; i++) {
                    Object para = parameters[i];
                    query.setParameter(i, para);
                }
                ScrollableResults sar = query.scroll();
                sar.last();
                if (page != null) {
                    page.setTotalCount(sar.getRowNumber() + 1);
                    query.setMaxResults(page.getPageSize());
                    query.setFirstResult(page.getFirstResult());
                }
                return query.list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<Map<?, ?>> findMapBySQL(final Page page, final String sql, final Map<String, Object> parameters) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<Map<?, ?>>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                query.setCacheable(isCacheable);
                query.setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
                for (String key : parameters.keySet()) {
                    query.setParameter(key, parameters.get(key));
                }
                ScrollableResults sar = query.scroll();
                sar.last();
                page.setTotalCount(sar.getRowNumber() + 1);
                query.setMaxResults(page.getPageSize());
                query.setFirstResult(page.getFirstResult());
                return query.list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findBySQL(final String sql, final Page page, final Object... parameters) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                for (int i = 0; i < parameters.length; i++) {
                    Object para = parameters[i];
                    query.setParameter(i, para);
                }
                ScrollableResults scrollableResults = query.scroll();
                scrollableResults.last();
                int rownum = scrollableResults.getRowNumber();
                page.setTotalCount(rownum + 1);
                query.setMaxResults(page.getPageSize());
                query.setFirstResult(page.getFirstResult());
                return query.list();
            }
        });
    }

    public int updateBySQL(final String sql, final Object... parameters) {
        return (Integer) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                for (int i = 0; i < parameters.length; i++) {
                    Object para = parameters[i];
                    query.setParameter(i, para);
                }
                return query.executeUpdate();
            }
        });
    }

    public int updateByHQL(final String hql, final Object... parameters) {
        return (Integer) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                for (int i = 0; i < parameters.length; i++) {
                    Object para = parameters[i];
                    query.setParameter(i, para);
                }
                return query.executeUpdate();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public int updateByHQL2(final String hql, final Map<String, Object> mapParam) {
        return (Integer) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                Query query = session.createQuery(hql);
                for (String key : mapParam.keySet()) {
                    // System.out.println(mapParam.get(key).getClass().getName());
                    if (mapParam.get(key).getClass().getName().equals("java.util.LinkedList")
                            || mapParam.get(key).getClass().getName().equals("java.util.ArrayList")
                            || mapParam.get(key).getClass().getName().equals("com.opensymphony.xwork2.util.XWorkList")) {
                        query.setParameterList(key, (Collection) mapParam.get(key));
                    } else {
                        query.setParameter(key, mapParam.get(key));
                    }
                }
                return query.executeUpdate();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<T> findByCriteria(final Criteria criteria) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.execute(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                criteria.setCacheable(isCacheable);
                criteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                return criteria.list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByCriteria(final com.framework.hibernate.util.Criteria<T> criteria, final Page page) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                org.hibernate.Criteria countCriteria = criteria.getExecutableCriteria(session);
                Projection projection = ((CriteriaImpl) countCriteria).getProjection();
                if (projection != null) {
                    countCriteria.setProjection(Projections.countDistinct("id"));
                } else {
                    countCriteria.setProjection(Projections.rowCount());
                }
                int totalCount = ConvertUtils.objectToInteger(countCriteria.uniqueResult());
                page.setTotalCount(totalCount);
                criteria.setProjection(projection);
                org.hibernate.Criteria hibernateCriteria = criteria.getExecutableCriteria(session);
                hibernateCriteria.setCacheable(isCacheable);
                hibernateCriteria.setFirstResult(page.getFirstResult());
                hibernateCriteria.setMaxResults(page.getPageSize());
                hibernateCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                return hibernateCriteria.list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByCriteria(final com.framework.hibernate.util.Criteria<T> criteria, final int limit) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                org.hibernate.Criteria countCriteria = criteria.getExecutableCriteria(session);
                Projection projection = ((CriteriaImpl) countCriteria).getProjection();
                if (projection != null) {
                    countCriteria.setProjection(Projections.countDistinct("id"));
                } else {
                    countCriteria.setProjection(Projections.rowCount());
                }
                int totalCount = ConvertUtils.objectToInteger(countCriteria.uniqueResult());
//                page.setTotalCount(totalCount);
                criteria.setProjection(projection);
                org.hibernate.Criteria hibernateCriteria = criteria.getExecutableCriteria(session);
                hibernateCriteria.setCacheable(isCacheable);
//                hibernateCriteria.setFirstResult(page.getFirstResult());
                hibernateCriteria.setMaxResults(limit);
                hibernateCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                return hibernateCriteria.list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public List<T> findByCriteria(final DetachedCriteria criteria) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                org.hibernate.Criteria hibernateCriteria = criteria.getExecutableCriteria(session);
                hibernateCriteria.setCacheable(isCacheable);
                hibernateCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                return hibernateCriteria.list();
            }
        });
    }


    @SuppressWarnings("unchecked")
    public List findByCriteria(final DetachedCriteria criteria, final Page page) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                org.hibernate.Criteria countCriteria = criteria.getExecutableCriteria(session);
                Projection projection = ((CriteriaImpl) countCriteria).getProjection();
                if (projection != null) {
                    countCriteria.setProjection(Projections.countDistinct("id"));
                } else {
                    countCriteria.setProjection(Projections.rowCount());
                }
                int totalCount = ConvertUtils.objectToInteger(countCriteria.uniqueResult());
                page.setTotalCount(totalCount);
                criteria.setProjection(projection);
                org.hibernate.Criteria hibernateCriteria = criteria.getExecutableCriteria(session);
                hibernateCriteria.setCacheable(isCacheable);
                hibernateCriteria.setFirstResult(page.getFirstResult());
                hibernateCriteria.setMaxResults(page.getPageSize());
                hibernateCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                return hibernateCriteria.list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findByCriteriaWithOutCount(final com.framework.hibernate.util.Criteria<T> criteria, final Page page) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                criteria.getExecutableCriteria(session);
                // countCriteria.setProjection(Projections.rowCount());
                // int totalCount =
                // ConvertUtils.objectToInteger(countCriteria.uniqueResult());
                // page.setTotalCount(totalCount);
                criteria.setProjection(null);
                org.hibernate.Criteria hibernateCriteria = criteria.getExecutableCriteria(session);
                hibernateCriteria.setCacheable(isCacheable);
                hibernateCriteria.setFirstResult(page.getFirstResult());
                hibernateCriteria.setMaxResults(page.getPageSize());
                hibernateCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                return hibernateCriteria.list();
            }
        });
    }

    public T findByHQLWithUniqueObject(String hql, Object... parameters) {
        List<T> list = findByHQL(hql, parameters);
        if (list != null && list.size() > 0) {
            return list.get(0);
        }
        return null;
    }

    public abstract void setSessionFactory(SessionFactory sessionFactory);

    @SuppressWarnings("unchecked")
    private Class<T> getPOJO() {
        if (entityClass == null) {
            entityClass = (Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[0];
        }
        return entityClass;
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findBySQL2(final String sql, final Page page, final Map<String, Object> params) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                for (String key : params.keySet()) {
                    // System.out.println(mapParam.get(key).getClass().getName());
                    if (params.get(key).getClass().getName().equals("java.util.LinkedList")
                            || params.get(key).getClass().getName().equals("java.util.ArrayList")
                            || params.get(key).getClass().getName().equals("com.opensymphony.xwork2.util.XWorkList")) {
                        query.setParameterList(key, (Collection) params.get(key));
                    } else {
                        query.setParameter(key, params.get(key));
                    }
                }
                ScrollableResults scrollableResults = query.scroll();
                scrollableResults.last();
                int rownum = scrollableResults.getRowNumber();
                page.setTotalCount(rownum + 1);
                query.setMaxResults(page.getPageSize());
                query.setFirstResult(page.getFirstResult());
                return query.list();
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <T> List<T> findBySQL2(final String sql, final Map<String, Object> params) {
        hibernateTemplate.setCacheQueries(isCacheable);
        return (List<T>) hibernateTemplate.executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                SQLQuery query = session.createSQLQuery(sql);
                for (String key : params.keySet()) {
                    // System.out.println(mapParam.get(key).getClass().getName());
                    if (params.get(key).getClass().getName().equals("java.util.LinkedList")
                            || params.get(key).getClass().getName().equals("java.util.ArrayList")
                            || params.get(key).getClass().getName().equals("com.opensymphony.xwork2.util.XWorkList")) {
                        query.setParameterList(key, (Collection) params.get(key));
                    } else {
                        query.setParameter(key, params.get(key));
                    }
                }
                ScrollableResults scrollableResults = query.scroll();
                scrollableResults.last();

                return query.list();
            }
        });
    }

    /**
     * 查询唯一值，比如：count、min等
     * @param criteria
     * @return
     */
    public Object findUniqueValue(final com.framework.hibernate.util.Criteria<T> criteria) {
        return this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                org.hibernate.Criteria hibernateCriteria = criteria.getExecutableCriteria(session);
                hibernateCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                List<Object> list = hibernateCriteria.list();
                if (!list.isEmpty()) {
                    return list.get(0);
                } else {
                    return null;
                }
            }
        });
    }
}
