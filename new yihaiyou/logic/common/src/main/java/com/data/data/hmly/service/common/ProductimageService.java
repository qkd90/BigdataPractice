package com.data.data.hmly.service.common;

import com.data.data.hmly.service.common.dao.ProductimageDao;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class ProductimageService {
	@Resource
	private ProductimageDao productimageDao;


	/**
	 * 查询产品图片
	 * @author caiys
	 * @date 2015年10月28日 下午4:45:37
	 * @param productimage
	 * @return
	 */
	public List<Productimage> findProductimage(Productimage productimage, SysUser user, String... orderProperties) {
		return productimageDao.findProductimage(productimage, user, orderProperties);
	}


	/**
	 * 保存
	 * @author caiys
	 * @date 2015年10月30日 下午4:19:04
	 * @param productimage
	 */
	public void saveProductimage(Productimage productimage) {
		productimageDao.save(productimage);
	}

	public Productimage getCover(long lineId) {
		Criteria<Productimage> criteria = new Criteria<Productimage>(Productimage.class);
		criteria.eq("product.id", lineId);
		criteria.eq("coverFlag", true);
		List<Productimage> productimages = productimageDao.findByCriteria(criteria);
		if (productimages.isEmpty()) {
			return null;
		}
		return productimages.get(0);
	}

    public Productimage findCover(Long productId, Long targetId, ProductType productType) {
        Criteria<Productimage> criteria = new Criteria<Productimage>(Productimage.class);
        criteria.eq("product.id", productId);
        criteria.eq("proType", productType);
        if (targetId != null) {
            criteria.or(Restrictions.eq("targetId", targetId), Restrictions.isNull("targetId"));
        } else {
			criteria.orderBy(Order.asc("targetId"));
		}
        criteria.eq("coverFlag", true);
        List<Productimage> productimageList = productimageDao.findByCriteria(criteria);
        if (!productimageList.isEmpty()) {
            return productimageList.get(0);
        }
        return null;
    }

	public void saveAll(List<Productimage> productimageList) {
		productimageDao.save(productimageList);
	}

    public void delById(Long id) {
        productimageDao.delete(id, Productimage.class);
    }

    public void doDelCoverByProduct(Long productId) {
        // 先删除之前的封面
        Criteria<Productimage> criteria = new Criteria<Productimage>(Productimage.class);
        criteria.createCriteria("product");
        criteria.eq("coverFlag", true);
        criteria.eq("product.id", productId);
        List<Productimage> productimageList = productimageDao.findByCriteria(criteria);
        for (Productimage productimage : productimageList) {
            productimage.setCoverFlag(false);
        }
        productimageDao.updateAll(productimageList);
    }

    public void doDelCoverByTargetId(Long targetId) {
        // 先删除之前的封面
        Criteria<Productimage> criteria = new Criteria<Productimage>(Productimage.class);
        criteria.createCriteria("product");
        criteria.eq("coverFlag", true);
        criteria.eq("targetId", targetId);
        List<Productimage> productimageList = productimageDao.findByCriteria(criteria);
        for (Productimage productimage : productimageList) {
            productimage.setCoverFlag(false);
        }
        productimageDao.updateAll(productimageList);
    }
    public void doSetCoverById(Long id) {
        Productimage productimage = productimageDao.load(id);
        productimage.setCoverFlag(true);
        productimageDao.update(productimage);
    }

    public void delProImages(long productId) {
		List<Productimage> productimageList = findImagesByProductId(productId);
		productimageDao.deleteAll(productimageList);
	}

	public List<Productimage> findImagesByProductId(long productId) {
		Criteria<Productimage> criteria = new Criteria<Productimage>(Productimage.class);
		criteria.eq("product.id", productId);
		return productimageDao.findByCriteria(criteria);
	}

	public Productimage findFirstImgByProId(Long productId) {
		Criteria<Productimage> criteria = new Criteria<Productimage>(Productimage.class);
		criteria.eq("product.id", productId);
		List<Productimage> productimageList =  productimageDao.findByCriteria(criteria, new Page(0, 1));
		return productimageList != null && !productimageList.isEmpty() ? productimageList.get(0) : null;
	}

	public List<Productimage> getImageListByKeyword(String keyword, SysUser sysUser) {
		Criteria<Productimage> criteria = new Criteria<Productimage>(Productimage.class);
		if (StringUtils.isNotBlank(keyword)) {
			criteria.like("imagDesc", keyword, MatchMode.ANYWHERE);
		}
		// 数据过滤
//		if (sysUser != null) {
//			criteria.eq("companyUnitId", sysUser.getSysUnit().getCompanyUnit().getId());
//		}
		return productimageDao.findByCriteria(criteria);
	}

    /**
     * 查询图片列表
     * @param productId
     * @param coverFlag
     * @param limit
     * @return
     */
	public List<Productimage> listImagesBy(Long productId, Boolean coverFlag, Integer limit) {
		Criteria<Productimage> criteria = new Criteria<Productimage>(Productimage.class);
		criteria.eq("product.id", productId);
		criteria.eq("coverFlag", coverFlag);
//		criteria.eq("status", ProductStatus.UP);
        criteria.orderBy("id", "desc");
        Page page = new Page(1, limit);
		List<Productimage> productImages = productimageDao.findByCriteria(criteria, page);
		return productImages;
	}

	public void doDelByTargetId(Long ticketPriceId) {
		List<Productimage> productimageList = findImagesByTargetId(ticketPriceId);
		productimageDao.deleteAll(productimageList);

	}

	private List<Productimage> findImagesByTargetId(Long ticketPriceId) {
		Criteria<Productimage> criteria = new Criteria<Productimage>(Productimage.class);
		criteria.eq("targetId", ticketPriceId);
		return productimageDao.findByCriteria(criteria);
	}

    public void delImgByUpChecking(Long productId, Long targetId, ProductStatus status, String childFolder) {
		String sql = "delete from productimage where productId=? and status=? and targetId=? and childFolder=?";
        productimageDao.updateBySQL(sql, productId, status.toString(), targetId, childFolder);
    }

	public void delImages(Long productId, Long targetId, String childFolder) {
		String sql = "delete from productimage where productId=? and targetId=? and childFolder=?";
		productimageDao.updateBySQL(sql, productId, targetId, childFolder);
	}


	/**
	 * 查找相应状态下的图片
	 * @param productId
	 * @param status
	 * @return
	 */
	public List<Productimage> findImagesByProductStatus(Long productId, ProductStatus status, String childFolder) {
		Criteria<Productimage> criteria = new Criteria<Productimage>(Productimage.class);
		criteria.eq("product.id", productId);
		criteria.eq("status", status);
		if (StringUtils.isNotBlank(childFolder)) {
			criteria.eq("childFolder", childFolder);
		}
		List<Productimage> productImages = productimageDao.findByCriteria(criteria);
		return productImages;
	}

	/**
	 * 通过productId和targetId查找图片
	 * @param productId
	 * @param targetId
	 * @return
	 */
	public List<Productimage> findAllImagesByProIdAadTarId(Long productId, Long targetId, String childFolder, String... orderProperties) {
		Criteria<Productimage> criteria = new Criteria<Productimage>(Productimage.class);
		criteria.eq("product.id", productId);
		if (targetId != null) {
			criteria.eq("targetId", targetId);
		}
		if (StringUtils.isNotBlank(childFolder)) {
			criteria.eq("childFolder", childFolder);
		}
		if (orderProperties.length == 2) {
			criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
			criteria.orderBy(Order.asc(orderProperties[0]));
		}
        return productimageDao.findByCriteria(criteria);
	}

	/**
	 * 为图片修改状态
	 * @param productimages
	 */
	public void doCheckImagesStatus(List<Productimage> productimages, Product product, Long targetId) {
		if (productimages.size() == 0) {
			return;
		}
		for (Productimage productimage : productimages) {
			productimage.setTargetId(targetId);
			productimage.setProduct(product);
			productimageDao.update(productimage);
		}
	}

	public List<Productimage> listImagesBy(Long productId, Long targetId, boolean coverFlag, int limit) {
		Criteria<Productimage> criteria = new Criteria<Productimage>(Productimage.class);
		criteria.eq("product.id", productId);
		criteria.eq("targetId", targetId);
		criteria.eq("coverFlag", coverFlag);
		criteria.orderBy("id", "desc");
		Page page = new Page(1, limit);
		List<Productimage> productImages = productimageDao.findByCriteria(criteria, page);
		return productImages;
	}

	/**
	 * 查询图片列表 - 七牛图片水印处理使用
	 */
	public List<Productimage> listImagesForQiniu(Page page) {
		Map<String, Object> params = new HashMap<String, Object>();
		StringBuilder hql = new StringBuilder();
		hql.append("from Productimage i ");
		hql.append("where exists (select 1 from Product p where i.product.id = p.id ");
		hql.append("and p.proType in (:type1, :type2) and p.source = :source and p.status in (:status1, :status2)) ");
//		hql.append("and i.id = 1800498 ");
		hql.append("order by i.id asc ");
		params.put("type1", ProductType.hotel);
		params.put("type2", ProductType.scenic);
		params.put("source", ProductSource.LXB);
		params.put("status1", ProductStatus.UP);
		params.put("status2", ProductStatus.UP_CHECKING);

		List<Productimage> productImages = productimageDao.findByHQL2(hql.toString(), page, params);
		return productImages;
	}

	public void update(Productimage productimage) {
		productimageDao.update(productimage);
	}

	/**
	 * 保存图片排序
	 * @param ids
	 */
	public void saveProductImageSort(List<Long> ids) {
		for (int i = 1; i <= ids.size(); i++) {
			Productimage productimage = productimageDao.load(ids.get(i - 1));
			productimage.setShowOrder(i);
			productimageDao.update(productimage);
		}

	}
}
