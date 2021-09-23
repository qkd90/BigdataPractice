package com.data.data.hmly.service.goods.dao;

import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.goods.entity.Category;
import com.data.data.hmly.service.goods.entity.CategoryType;
import com.data.data.hmly.service.goods.entity.enums.CategoryStatus;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by vacuity on 15/10/14.
 */
@Repository
public class CategoryDao extends DataAccess<Category> {


    public Category get(Long id) {
        Criteria<Category> criteria = new Criteria<Category>(Category.class);
        criteria.eq("id", id);
        return findUniqueByCriteria(criteria);
    }

    // 根据类型查询有效数据
    public List<Category> getValidData(CategoryType type, SysUnit companyUnit) {
        Criteria<Category> criteria = new Criteria<Category>(Category.class);
        criteria.createCriteria("type", "type");
        criteria.createCriteria("sysUnit", "sysUnit", JoinType.INNER_JOIN);
        criteria.eq("sysUnit.companyUnit.id", companyUnit.getId());
        criteria.eq("type.type", type.getType());
        criteria.eq("status", CategoryStatus.SHOW);
        criteria.orderBy("sortOrder", "asc");
        return findByCriteria(criteria);
    }
    // 根据类型查询有效数据
    public List<Category> getValidData(CategoryType type) {
        Criteria<Category> criteria = new Criteria<Category>(Category.class);
        criteria.createCriteria("type", "type");
        criteria.eq("type.type", type.getType());
        criteria.eq("status", CategoryStatus.SHOW);
        criteria.orderBy("sortOrder", "asc");
        return findByCriteria(criteria);
    }

    public List<Category> getValidDataByType(Long type, SysUnit companyUnit) {
        Criteria<Category> criteria = new Criteria<Category>(Category.class);
        if (companyUnit != null) {
            criteria.createCriteria("sysUnit", "sysUnit", JoinType.INNER_JOIN);
            criteria.eq("sysUnit.companyUnit.id", companyUnit.getId());
        }
        criteria.createCriteria("type", "type");
        criteria.eq("type.id", type);
        criteria.orderBy("sortOrder", "asc");
        criteria.eq("status", CategoryStatus.SHOW);
        return findByCriteria(criteria);
    }
    public List<Category> getValidDataByType(String type, SysUnit companyUnit) {
        Criteria<Category> criteria = new Criteria<Category>(Category.class);
        if (companyUnit != null) {
            criteria.createCriteria("sysUnit", "sysUnit", JoinType.INNER_JOIN);
            criteria.eq("sysUnit.companyUnit.id", companyUnit.getId());
        }
        criteria.createCriteria("type", "type");
        criteria.eq("type.type", type);
        criteria.orderBy("sortOrder", "asc");
        criteria.eq("status", CategoryStatus.SHOW);
        return findByCriteria(criteria);
    }

    public List<Category> getValidDataByType(Long type, SysSite sysSite) {
        Criteria<Category> criteria = new Criteria<Category>(Category.class);
        if (sysSite != null) {
            DetachedCriteria unitDetachedCriteria = criteria.createCriteria("sysUnit", "sysUnit");
            unitDetachedCriteria.createCriteria("sysSite", "sysSite");
            criteria.eq("sysSite.id", sysSite.getId());
        }
        criteria.createCriteria("type", "type");
        criteria.eq("type.id", type);
        criteria.orderBy("sortOrder", "asc");
        criteria.eq("status", CategoryStatus.SHOW);
        return findByCriteria(criteria);
    }
    public List<Category> getValidDataByType(String type, SysSite sysSite) {
        Criteria<Category> criteria = new Criteria<Category>(Category.class);
        if (sysSite != null) {
            DetachedCriteria unitDetachedCriteria = criteria.createCriteria("sysUnit", "sysUnit");
            unitDetachedCriteria.createCriteria("sysSite", "sysSite");
            criteria.eq("sysSite.id", sysSite.getId());
        }
        criteria.createCriteria("type", "type");
        criteria.eq("type.type", type);
        criteria.orderBy("sortOrder", "asc");
        criteria.eq("status", CategoryStatus.SHOW);
        return findByCriteria(criteria);
    }

    public int count(CategoryType type, SysUnit companyUnit) {
        Criteria<Category> criteria = new Criteria<Category>(Category.class);
        DetachedCriteria unitCriteria = criteria.createCriteria("sysUnit", "sysUnit", JoinType.INNER_JOIN);
        unitCriteria.add(Restrictions.eq("id", companyUnit.getId()));
        criteria.eq("type", type);
        criteria.ne("status", CategoryStatus.DEL);
        return findByCriteria(criteria).size();
    }

    public int countByType(String type, SysUnit companyUnit) {
        Criteria<Category> criteria = new Criteria<Category>(Category.class);
        DetachedCriteria unitCriteria = criteria.createCriteria("sysUnit", "sysUnit", JoinType.INNER_JOIN);
        criteria.createCriteria("type", "type");
        unitCriteria.add(Restrictions.eq("id", companyUnit.getId()));
        criteria.eq("type.type", type);
        criteria.ne("status", CategoryStatus.DEL);
        return findByCriteria(criteria).size();
    }


    // 根据类型查询有效数据
    public List<Category> getParentData(CategoryType type, SysUnit companyUnit) {
        Criteria<Category> criteria = new Criteria<Category>(Category.class);
        DetachedCriteria unitCriteria = criteria.createCriteria("sysUnit", "sysUnit", JoinType.INNER_JOIN);
        criteria.createCriteria("type", "type");
        unitCriteria.add(Restrictions.eq("id", companyUnit.getId()));
        criteria.eq("type.type", type.getType());
        long rootId = 0;
        criteria.eq("parentId", rootId);
        criteria.ne("status", CategoryStatus.DEL);
        criteria.orderBy("sortOrder", "asc");
        return findByCriteria(criteria);
    }

    public List<Category> getParentDataByType(String type, SysUnit companyUnit) {
        Criteria<Category> criteria = new Criteria<Category>(Category.class);
        DetachedCriteria unitCriteria = criteria.createCriteria("sysUnit", "sysUnit", JoinType.INNER_JOIN);
        criteria.createCriteria("type", "type");
        unitCriteria.add(Restrictions.eq("id", companyUnit.getId()));
        criteria.eq("type.type", type);
        criteria.eq("parentId", 0L);
        criteria.ne("status", CategoryStatus.DEL);
        criteria.orderBy("sortOrder", "asc");
        return findByCriteria(criteria);
    }
    public List<Category> getCategoryList(CategoryType type, Long userId) {
        Criteria<Category> criteria = new Criteria<Category>(Category.class);
        criteria.createCriteria("type", "type");
        criteria.eq("user.id", userId);
        criteria.eq("type.type", type.getType());
        criteria.ne("status", CategoryStatus.DEL);
        criteria.orderBy("sortOrder", "asc");
        return findByCriteria(criteria);
    }
}
