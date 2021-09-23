package com.data.data.hmly.service.goods;

import com.data.data.hmly.service.goods.dao.CategoryTypeDao;
import com.data.data.hmly.service.goods.entity.CategoryType;
import com.data.data.hmly.service.goods.entity.enums.CategoryTypeStatus;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zzl on 2016/4/19.
 */
@Service
public class CategoryTypeService {

    @Resource
    private CategoryTypeDao categoryTypeDao;

    public CategoryType get(Long id) {
        return categoryTypeDao.load(id);
    }

    public void update(CategoryType categoryType) {
        categoryTypeDao.update(categoryType);
    }

    public void save(CategoryType categoryType) {
        categoryTypeDao.save(categoryType);
    }

    public CategoryType getByType(String type) {
        Criteria<CategoryType> criteria = new Criteria<CategoryType>(CategoryType.class);
        criteria.eq("type", type);
        return categoryTypeDao.findUniqueByCriteria(criteria);
    }

    public List<CategoryType> getCategoryTypeList(CategoryType condition, Page page, String...orderProperties) {
        Criteria<CategoryType> criteria = createCriteria(condition, orderProperties);
        if (page != null) {
            return categoryTypeDao.findByCriteria(criteria, page);
        }
        return categoryTypeDao.findByCriteria(criteria);
    }

    private Criteria<CategoryType> createCriteria(CategoryType condition, String...orderProperties) {
        Criteria<CategoryType> criteria = new Criteria<CategoryType>(CategoryType.class);
        criteria.ne("status", CategoryTypeStatus.DEL);
        if (orderProperties != null && orderProperties.length > 0) {
            if (orderProperties.length > 1) {
                criteria.orderBy(orderProperties[0], orderProperties[1]);
            } else {
                criteria.orderBy(orderProperties[0], "desc");
            }
        }
        if (condition == null) {
            return criteria;
        }
        if (condition.getType() != null) {
            criteria.eq("type", condition.getType());
        }
        if (condition.getTypeDes() != null) {
            criteria.like("typeDes", condition.getTypeDes());
        }
        if (condition.getStatus() != null) {
            criteria.eq("status", condition.getStatus());
        }
        return criteria;
    }

}
