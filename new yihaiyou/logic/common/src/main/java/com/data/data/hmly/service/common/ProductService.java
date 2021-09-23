package com.data.data.hmly.service.common;

import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.common.dao.ProductDao;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.enums.ShowStatus;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by vacuity on 15/11/2.
 */

@Service
public class ProductService {

    @Resource
    private ProductDao dao;
    @Resource
    private LabelService labelService;


    public Product getRchargeProduct(Float price, ProductType recharge) {

        Criteria<Product> criteria = new Criteria<Product>(Product.class);
        if (price != null) {
            criteria.eq("price", price);
        }
        if (recharge != null) {
            criteria.eq("proType", recharge);
        }
        return dao.findUniqueByCriteria(criteria);

    }

    public List<Product> getProductByNameAndType(Product product) {
        Criteria<Product> criteria = new Criteria<Product>(Product.class);
        if (product.getName() != null) {
            criteria.like("name", product.getName());
        }
        if (product.getProType() != null) {
            criteria.eq("proType", product.getProType());
        }
        criteria.ne("status", ProductStatus.DEL);
        criteria.ne("status", ProductStatus.DOWN);
        return dao.findByCriteria(criteria);
    }


    public List<Product> getByUserIds(List<Long> userIds) {
        Criteria<Product> criteria = new Criteria<Product>(Product.class);
        criteria.in("user.id", userIds);
        return dao.findByCriteria(criteria);
    }

    public List<Product> getByParentIds(List<Long> parentIds) {
        Criteria<Product> criteria = new Criteria<Product>(Product.class);
        criteria.in("parent.id", parentIds);
        return dao.findByCriteria(criteria);
    }

    public List<Product> getProductByUnitIds(List<Long> unitIds) {
        Criteria<Product> criteria = new Criteria<Product>(Product.class);
        DetachedCriteria userCriteria = criteria.createCriteria("user", "user", JoinType.INNER_JOIN);
        DetachedCriteria unitCriteria = userCriteria.createCriteria("sysUnit", "sysUnit", JoinType.INNER_JOIN);
        unitCriteria.add(Restrictions.in("id", unitIds));
        List<Product> productList = dao.findByCriteria(criteria);
        return productList;
    }

    public List<Product> getProductList(Product product, Page page, String... orderProperty) {
        Criteria<Product> criteria = createCriteria(product, orderProperty);
        List<Product> list = dao.findByCriteria(criteria, page);
        for (Product eachProduct : list) {
            for (Productimage productimage : eachProduct.getProductimage()) {
                if (productimage.getCoverFlag()) {
                    eachProduct.setImgUrl(productimage.getPath());
                }
            }
        }
        return list;
    }

    public List<Product> getByCompanyUnit(SysUnit companyUnit) {
        Criteria<Product> criteria = new Criteria<Product>(Product.class);
        criteria.createCriteria("companyUnit", "companyUnit");
        criteria.eq("companyUnit.id", companyUnit.getId());
        List<ProductStatus> notStatuses = Arrays.asList(ProductStatus.DEL);
        criteria.notin("status", notStatuses);
        criteria.ne("showStatus", ShowStatus.HIDE_FOR_CHECK);
        return dao.findByCriteria(criteria);
    }

    public ProductStatus getProStatus(Long productId) {
        String sql = "select status from product where id=?";
        List<?> s = dao.findBySQL(sql, productId);
        if (s != null && !s.isEmpty() && s.get(0) != null) {
            return ProductStatus.valueOf(s.get(0).toString());
        }
        return null;
    }

    public ShowStatus getShowStatus(Long productId) {
        String sql = "select show_status from product where id=?";
        List<?> s = dao.findBySQL(sql, productId);
        if (s != null && !s.isEmpty() && s.get(0) != null) {
            return ShowStatus.valueOf(s.get(0).toString());
        }
        return null;
    }

    private Criteria<Product> createCriteria(Product product, String... orderProperty) {
        Criteria<Product> criteria = new Criteria<Product>(Product.class);
        if (orderProperty.length == 2) {
            criteria.orderBy(orderProperty[0], orderProperty[1]);
        } else if (orderProperty.length == 1) {
            criteria.orderBy(Order.desc(orderProperty[0]));
        }
        if (product == null) {
            return criteria;
        }
        if (product.getUser() != null) {
            SysUser user = product.getUser();
            if (user.getSysUnit() != null) {
                criteria.createCriteria("user", "user", JoinType.INNER_JOIN)
                        .add(Restrictions.eq("sysUnit.id", user.getSysUnit().getId()));
            } else {
                criteria.eq("user.id", user.getId());
            }
        }
        if (product.getTopProduct() != null) {
            Product topProduct = product.getTopProduct();
            if (topProduct.getUser() != null) {
                SysUser user = topProduct.getUser();
                if (user.getSysUnit() != null) {
                    criteria.createCriteria("topProduct", "topProduct", JoinType.INNER_JOIN)
                            .createCriteria("user", "user", JoinType.INNER_JOIN)
                            .add(Restrictions.eq("sysUnit.id", user.getSysUnit().getId()));
                } else {
                    criteria.createCriteria("topProduct", "topProduct", JoinType.INNER_JOIN)
                            .add(Restrictions.eq("user.id", user.getId()));
                }
            } else {
                criteria.eq("topProduct.id", topProduct.getId());
            }
        }

        return criteria;
    }

    public Product get(Long id) {
        return dao.load(id);
    }

    public Product getByOriginId(Long originId) {
        Criteria<Product> criteria = new Criteria<Product>(Product.class);
        criteria.eq("originId", originId);
        return dao.findUniqueByCriteria(criteria);
    }

    public List<Product> getProductByLabelList(Label condition, Page page, String... orderProperties) {
        Label rootThemeLabel = labelService.findUnique(condition);
        return null;
    }
}
