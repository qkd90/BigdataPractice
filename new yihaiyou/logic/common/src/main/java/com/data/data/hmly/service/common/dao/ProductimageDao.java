package com.data.data.hmly.service.common.dao;

import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class ProductimageDao extends DataAccess<Productimage> {
	
	/**
	 * 删除产品图片
	 * @author caiys
	 * @date 2015年10月28日 下午4:20:02
	 * @param productId		不为空
	 * @param childFolder	可为空
	 */
	public void delBy(Long productId, String childFolder) {
		StringBuilder hql = new StringBuilder(" delete Productimage where product.id = ? ");
		List<Object> params = new ArrayList<Object>();
		params.add(productId);
		if (StringUtils.isNotBlank(childFolder)) {
			hql.append("and childFolder = ? ");
			params.add(childFolder);
		}
		updateByHQL(hql.toString(), params.toArray());
	}
	
	/**
	 * 查询产品图片
	 * @author caiys
	 * @date 2015年10月28日 下午4:45:37
	 * @param productimage
	 * @return
	 */
	public List<Productimage> findProductimage(Productimage productimage, SysUser user, String... orderProperties) {
		Criteria<Productimage> criteria = new Criteria<Productimage>(Productimage.class);
		// 产品标识
		if (productimage.getProduct() != null && productimage.getProduct().getId() != null) {
			criteria.eq("product.id", productimage.getProduct().getId());
		}
        // 目标id
        if (productimage.getTargetId() != null) {
            criteria.eq("targetId", productimage.getTargetId());
        }
        // 产品标识为空
		if (productimage.getProductIdFlag()) {
			criteria.isNull("product.id");
		}
		// 产品类型
		if (productimage.getProType() != null ) {
			criteria.eq("proType", productimage.getProType());
		}
		// 子类型
		if (StringUtils.isNotBlank(productimage.getChildFolder())) {
			criteria.eq("childFolder", productimage.getChildFolder());
		}
        // 状态
        if (productimage.getStatus() != null) {
            criteria.eq("status", productimage.getStatus());
        }
        // 数据过滤
		if (user != null) {
			criteria.eq("companyUnitId", user.getSysUnit().getCompanyUnit().getId());
		}

		if (orderProperties.length == 2) {
			criteria.orderBy(orderProperties[0], orderProperties[1]);
		} else if (orderProperties.length == 1) {
			criteria.orderBy(Order.asc(orderProperties[0]));
		}
		return findByCriteria(criteria);
	}
}
