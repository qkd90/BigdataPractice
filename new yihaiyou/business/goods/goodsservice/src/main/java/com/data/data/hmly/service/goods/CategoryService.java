package com.data.data.hmly.service.goods;

import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.goods.dao.CategoryDao;
import com.data.data.hmly.service.goods.entity.Category;
import com.data.data.hmly.service.goods.entity.CategoryType;
import com.data.data.hmly.service.goods.entity.enums.CategoryStatus;
import com.framework.hibernate.util.Criteria;
import net.sf.json.JSONArray;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by vacuity on 15/10/14.
 */

@Service
public class CategoryService {
    @Resource
    private CategoryDao categoryDao;

    // 获取有效数据列表
    public List<Category> listValid(CategoryType type, SysUnit sysUnit) {
        List<Category> categoryList = categoryDao.getValidData(type, sysUnit);
        return categoryList;
    }
    // 获取有效数据列表
    public List<Category> listValidByType(CategoryType type) {
        List<Category> categoryList = categoryDao.getValidData(type);
        return categoryList;
    }

    public List<Category> listValidByType(Long type, SysUnit sysUnit) {
        List<Category> categoryList = categoryDao.getValidDataByType(type, sysUnit);
        return categoryList;
    }
    public List<Category> listValidByType(String type, SysUnit sysUnit) {
        List<Category> categoryList = categoryDao.getValidDataByType(type, sysUnit);
        return categoryList;
    }
    public List<Category> listValidByType(Long type, SysSite sysSite) {
        List<Category> categoryList = categoryDao.getValidDataByType(type, sysSite);
        return categoryList;
    }
    public List<Category> listValidByType(String type, SysSite sysSite) {
        List<Category> categoryList = categoryDao.getValidDataByType(type, sysSite);
        return categoryList;
    }


    public List<Category> getDataByType(String type, SysUnit sysUnit) {
        List<Category> categoryList = listValidByType(type, sysUnit);
        List<Category> rootCategory = getTreegirdData(categoryList);
        return rootCategory;
    }

    public List<Category> getDataByType(Long type, SysUnit sysUnit) {
        List<Category> categoryList = listValidByType(type, sysUnit);
        List<Category> rootCategory = getTreegirdData(categoryList);
        return rootCategory;
    }
    public List<Category> getDataByType(String type, SysSite sysSite) {
        List<Category> categoryList = listValidByType(type, sysSite);
        List<Category> rootCategory = getTreegirdData(categoryList);
        return rootCategory;
    }

    public List<Category> getDataByType(Long type, SysSite sysSite) {
        List<Category> categoryList = listValidByType(type, sysSite);
        List<Category> rootCategory = getTreegirdData(categoryList);
        return rootCategory;
    }





    /**
     * 组装树形数据
     * @param categoryList
     * @return
     */
    private List<Category> getTreegirdData(List<Category> categoryList) {
        List<Category> rootCategory = new ArrayList<Category>();
        Map<Long, Category> linecategoryMap = new HashMap<Long, Category>();
        for (Category category : categoryList) {
            // 数据暂时存放在map中方便后续取出
            linecategoryMap.put(category.getId(), category);
            // 暂存没有父节点的数据
            if (category.getParentId() == 0) {
                rootCategory.add(category);
            }
        }
        for (Category category : categoryList) {
            // 没有父节点数据
            if (category.getParentId() == 0) {
                continue;
            }
            // 把数据放进父节点的children列表中
            if (linecategoryMap.get(category.getParentId()) == null) {
                rootCategory.add(category);
                continue;
            }
            Category parentCategory = linecategoryMap.get(category.getParentId());
            List<Category> childrenCategory = parentCategory.getChildren();
            if (childrenCategory == null) {
                childrenCategory = new ArrayList<Category>();
            }
            childrenCategory.add(category);
            parentCategory.setChildren(childrenCategory);
        }
        // 返回跟节点组成的数据列表
        return rootCategory;
    }

    // 获取最终需要显示的数据
    @Deprecated
    public List<Category> getData(CategoryType type, SysUnit sysUnit) {
        List<Category> rootCategory = new ArrayList<Category>();
        List<Category> linecategories = listValid(type, sysUnit);
        Map<Long, Category> linecategoryMap = new HashMap<Long, Category>();
        for (Category category : linecategories) {
            // 数据暂时存放在map中方便后续取出
            linecategoryMap.put(category.getId(), category);
            // 没有父节点的数据
            if (category.getParentId() == 0) {
                rootCategory.add(category);
            }
        }

        for (Category category : linecategories) {
            // 没有父节点数据
            if (category.getParentId() == 0) {
                continue;
            }
            // 把数据放进父节点的children列表中
            if (linecategoryMap.get(category.getParentId()) == null) {
                rootCategory.add(category);
                continue;
            }
            Category parentCategory = linecategoryMap.get(category.getParentId());
            List<Category> childrenCategory = parentCategory.getChildren();
            if (childrenCategory == null) {
                childrenCategory = new ArrayList<Category>();
            }
            childrenCategory.add(category);
            parentCategory.setChildren(childrenCategory);
        }

        // 返回跟节点组成的数据列表
        return rootCategory;

    }

    // 把列表数据转化为json串
    public String dataToJson(List<Category> linecategories) {
        JSONArray array = JSONArray.fromObject(linecategories);
        String jsonString = array.toString();
        return jsonString;
    }

    // 获取列表数据并转化为json串
    public String getTableData(CategoryType type, SysUnit sysUnit) {
        List<Category> linecategories = getData(type, sysUnit);
        String jsonString = dataToJson(linecategories);
        return jsonString;
    }

    // 添加数据
    public void saveOrUpdate(Category category) {
        categoryDao.saveOrUpdate(category, category.getId());
    }

    // 更新数据
    public void update(Category category) {
        categoryDao.update(category);
    }

    // 获取当前所有的一级分类
    public List<Category> getRootService(CategoryType type, SysUnit sysUnit) {

        List<Category> rootCategory = categoryDao.getParentData(type, sysUnit);

        return rootCategory;
    }

    public List<Category> getRootServiceByType(String type, SysUnit sysUnit) {
        List<Category> rootCategoryList = categoryDao.getParentDataByType(type, sysUnit);
        return rootCategoryList;
    }
    public Category findById(long id) {
        Category category = categoryDao.get(id);
        return category;
    }

    public int countByType(String type, SysUnit sysUnit) {
        return categoryDao.countByType(type, sysUnit);
    }

    public List<Category> getCategoryList(CategoryType service, long userId) {
        List<Category> l1s = categoryDao.getCategoryList(service, userId);
        List<Category> lls = new ArrayList<Category>();
        for (Category l1 : l1s) {
            if (l1.getParentId() != 0) {
                l1.setName("->" + l1.getName());
            }
            lls.add(l1);
        }


        return lls;
    }

    public List<Category> getValidCategoryList(CategoryType categoryType, SysUser sysUser, SysUnit sysUnit, Boolean isSiteAdmin, Boolean isSupperAdmin) {

        Criteria<Category> criteria = new Criteria<Category>(Category.class);

        criteria.createCriteria("user", "u", JoinType.LEFT_OUTER_JOIN);
        if (!isSupperAdmin) {
            criteria.eq("u.sysSite.id", sysUser.getSysSite().getId());
            if (!isSiteAdmin) {
                criteria.eq("u.id", sysUser.getId());
            }
        }
        criteria.eq("type", categoryType);
        criteria.eq("status", CategoryStatus.SHOW);
        criteria.orderBy("sortOrder", "asc");
        return categoryDao.findByCriteria(criteria);
    }

    public Category getByTypeAndName(CategoryType type, String name) {
        Criteria<Category> criteria = new Criteria<Category>(Category.class);
        criteria.eq("type", type);
        criteria.eq("name", name);
        return categoryDao.findUniqueByCriteria(criteria);
    }

    public List<Category> getCategoryList(Category category) {
        Criteria<Category> criteria = new Criteria<Category>(Category.class);
        if (category.getType() != null) {
            criteria.eq("type", category.getType());
        }
        criteria.eq("parentId", category.getParentId());
        criteria.eq("status", CategoryStatus.SHOW);
        criteria.orderBy("sortOrder", "asc");
        return categoryDao.findByCriteria(criteria);

    }
}
