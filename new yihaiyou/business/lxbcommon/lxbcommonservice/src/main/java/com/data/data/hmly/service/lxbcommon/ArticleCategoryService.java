package com.data.data.hmly.service.lxbcommon;

import com.data.data.hmly.service.lxbcommon.dao.ArticleCategoryDao;
import com.data.data.hmly.service.lxbcommon.entity.ArticleCategory;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zzl on 2016/4/14.
 */
@Service
public class ArticleCategoryService {

    @Resource
    private ArticleCategoryDao articleCategoryDao;


    public List<ArticleCategory> getTreeGirdCategory(ArticleCategory conditon, Page page) {
        List<ArticleCategory> grandCategoryList = getGrandCategory(conditon, page);
        for (ArticleCategory grandCategory : grandCategoryList) {
            List<ArticleCategory> parentCategoryList = getChildCategory(conditon, grandCategory);
            for (ArticleCategory parentCategory : parentCategoryList) {
                List<ArticleCategory> grandSonCategoryList = getGrandSonCategory(conditon, grandCategory, parentCategory);
                parentCategory.setChildren(grandSonCategoryList);
            }
            grandCategory.setChildren(parentCategoryList);
        }
        return grandCategoryList;
    }



    private List<ArticleCategory> getGrandCategory(ArticleCategory grandArticleCategory, Page page) {
        String hql = "";
        StringBuilder sb = new StringBuilder();
        sb.append("from ArticleCategory ac where ac.parentCategory is null");
        if (StringUtils.hasText(grandArticleCategory.getCategoryName())) {
            sb.append(" and ac.categoryName like '%" + grandArticleCategory.getCategoryName() + "%'");
        }
        if (grandArticleCategory.getStatus() != null) {
            sb.append(" and ac.status = '" + grandArticleCategory.getStatus() + "'");
        }
        sb.append(" or ac.id in");
        sb.append(" (select ac2.parentCategory from ArticleCategory ac2");
        sb.append(" where ac2.parentCategory is not null and ac2.grandCategory is null");
        if (StringUtils.hasText(grandArticleCategory.getCategoryName())) {
            sb.append(" and ac2.categoryName like '%" + grandArticleCategory.getCategoryName() + "%'");
        }
        if (grandArticleCategory.getStatus() != null) {
            sb.append(" and ac2.status = '" + grandArticleCategory.getStatus() + "'");
        }
        sb.append(") or ac.id in");
        sb.append(" (select ac3.grandCategory from ArticleCategory ac3");
        sb.append(" where ac3.parentCategory is not null and ac3.grandCategory is not null");
        if (StringUtils.hasText(grandArticleCategory.getCategoryName())) {
            sb.append(" and ac3.categoryName like '%" + grandArticleCategory.getCategoryName() + "%'");
        }
        if (grandArticleCategory.getStatus() != null) {
            sb.append(" and ac3.status = '" + grandArticleCategory.getStatus() + "'");
        }
        sb.append(")");
        hql = sb.toString();
        return articleCategoryDao.findByHQL(hql, page);
    }

    private List<ArticleCategory> getChildCategory(ArticleCategory condition, ArticleCategory parentCatrgory) {
        String hql = "";
        StringBuffer sb = new StringBuffer();
        sb.append("from ArticleCategory ac2");
        sb.append(" where ac2.parentCategory is not null and ac2.grandCategory is null");
        sb.append(" and ac2.parentCategory = " + parentCatrgory.getId());
        if (StringUtils.hasText(condition.getCategoryName())) {
            sb.append(" and ac2.categoryName like '%" + condition.getCategoryName() + "'");
        }
        if (condition.getStatus() != null) {
            sb.append(" and ac2.status = '" + condition.getStatus() + "'");
        }
        sb.append(") or ac2.id in");
        sb.append(" (select ac3.parentCategory from ArticleCategory ac3");
        sb.append(" where ac3.parentCategory is not null and ac3.grandCategory is not null");
        sb.append(" and ac3.grandCategory = " + parentCatrgory.getId());
        if (StringUtils.hasText(condition.getCategoryName())) {
            sb.append(" and ac3.categoryName like '%" + condition.getCategoryName() + "'");
        }
        if (condition.getStatus() != null) {
            sb.append(" and ac3.status = '" + condition.getStatus() + "'");
        }
        sb.append(")");
        hql = sb.toString();
        return articleCategoryDao.findByHQL(hql);
    }

    private List<ArticleCategory> getGrandSonCategory(ArticleCategory condition,
                                                      ArticleCategory grandCategory,
                                                      ArticleCategory parentCategory) {
        Criteria<ArticleCategory> criteria = new Criteria<ArticleCategory>(ArticleCategory.class);
        criteria.eq("level", 3);
        criteria.eq("parentCategory.id", parentCategory.getId());
        criteria.eq("grandCategory.id", grandCategory.getId());
        if (StringUtils.hasText(condition.getCategoryName())) {
            criteria.like("categoryName", condition.getCategoryName());
        }
        if (condition.getStatus() != null) {
            criteria.eq("status", condition.getStatus());
        }
        return articleCategoryDao.findByCriteria(criteria);
    }

}
