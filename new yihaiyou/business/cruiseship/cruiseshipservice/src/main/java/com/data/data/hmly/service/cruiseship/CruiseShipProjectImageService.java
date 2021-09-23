package com.data.data.hmly.service.cruiseship;

import com.data.data.hmly.service.cruiseship.dao.CruiseShipProjectImageDao;
import com.data.data.hmly.service.cruiseship.entity.CruiseShipProjectImage;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Think on 2017/6/12.
 */

@Service
public class CruiseShipProjectImageService {

    @Resource
    private CruiseShipProjectImageDao cruiseShipProjectImageDao;

    /**
     * 查询项目图片
     * @param projectimage
     * @return
     */
    public List<CruiseShipProjectImage> findProjectimage(CruiseShipProjectImage projectimage, SysUser user, String... orderProperties) {
        return cruiseShipProjectImageDao.findProjectimage(projectimage, user, orderProperties);
    }


    /**
     * 保存
     * @author caiys
     * @date 2015年10月30日 下午4:19:04
     * @param cruiseShipProjectImage
     */
    public void saveProjectimage(CruiseShipProjectImage cruiseShipProjectImage) {
        cruiseShipProjectImageDao.save(cruiseShipProjectImage);
    }

    public CruiseShipProjectImage getCover(long projectId) {
        Criteria<CruiseShipProjectImage> criteria = new Criteria<CruiseShipProjectImage>(CruiseShipProjectImage.class);
        criteria.eq("cruiseShipProject.id", projectId);
        criteria.eq("coverFlag", true);
        List<CruiseShipProjectImage> projectimages = cruiseShipProjectImageDao.findByCriteria(criteria);
        if (projectimages.isEmpty()) {
            return null;
        }
        return projectimages.get(0);
    }

    public CruiseShipProjectImage findCover(Long projectId) {
        Criteria<CruiseShipProjectImage> criteria = new Criteria<CruiseShipProjectImage>(CruiseShipProjectImage.class);
        criteria.eq("cruiseShipProject.id", projectId);
//        criteria.eq("proType", productType);
//        if (targetId != null) {
//            criteria.or(Restrictions.eq("targetId", targetId), Restrictions.isNull("targetId"));
//        } else {
//            criteria.orderBy(Order.asc("targetId"));
//        }
        criteria.eq("coverFlag", true);
        List<CruiseShipProjectImage> projectimageList = cruiseShipProjectImageDao.findByCriteria(criteria);
        if (!projectimageList.isEmpty()) {
            return projectimageList.get(0);
        }
        return null;
    }

    public void saveAll(List<CruiseShipProjectImage> cruiseShipProjectImageList) {
        cruiseShipProjectImageDao.save(cruiseShipProjectImageList);
    }

    public void delById(Long projectId) {
        cruiseShipProjectImageDao.delete(projectId, CruiseShipProjectImage.class);
    }

    public void doDelCoverByProject(Long projectId) {
        // 先删除之前的封面
        Criteria<CruiseShipProjectImage> criteria = new Criteria<CruiseShipProjectImage>(CruiseShipProjectImage.class);
//        criteria.createCriteria("product");
        criteria.eq("coverFlag", true);
        criteria.eq("cruiseShipProject.id", projectId);
        List<CruiseShipProjectImage> projectimageList = cruiseShipProjectImageDao.findByCriteria(criteria);
        for (CruiseShipProjectImage cruiseShipProjectImage : projectimageList) {
            cruiseShipProjectImage.setCoverFlag(false);
        }
        cruiseShipProjectImageDao.updateAll(projectimageList);
    }

    /*public void doDelCoverByProjectId(Long projectId) {
        // 先删除之前的封面
        Criteria<CruiseShipProjectImage> criteria = new Criteria<CruiseShipProjectImage>(CruiseShipProjectImage.class);
//        criteria.createCriteria("cruise");
        criteria.eq("coverFlag", true);
        criteria.eq("cruiseShipProject.id", projectId);
        List<CruiseShipProjectImage> projectimageList = cruiseShipProjectImageDao.findByCriteria(criteria);
        for (CruiseShipProjectImage cruiseShipProjectImage : projectimageList) {
            cruiseShipProjectImage.setCoverFlag(false);
        }
        cruiseShipProjectImageDao.updateAll(projectimageList);
    }*/
    public void doSetCoverById(Long id) {
        CruiseShipProjectImage cruiseShipProjectImage = cruiseShipProjectImageDao.load(id);
        cruiseShipProjectImage.setCoverFlag(true);
        cruiseShipProjectImageDao.update(cruiseShipProjectImage);
    }

    public void delProImages(long projectId) {
        List<CruiseShipProjectImage> projectimageList = findImagesByProjectId(projectId);
        cruiseShipProjectImageDao.deleteAll(projectimageList);
    }

    public List<CruiseShipProjectImage> findImagesByProjectId(long projectId) {
        Criteria<CruiseShipProjectImage> criteria = new Criteria<CruiseShipProjectImage>(CruiseShipProjectImage.class);
        criteria.eq("cruiseShipProject.id", projectId);
        return cruiseShipProjectImageDao.findByCriteria(criteria);
    }

    public CruiseShipProjectImage findFirstImgByProId(Long projectId) {
        Criteria<CruiseShipProjectImage> criteria = new Criteria<CruiseShipProjectImage>(CruiseShipProjectImage.class);
        criteria.eq("cruiseShipProject.id", projectId);
        List<CruiseShipProjectImage> projectimageList =  cruiseShipProjectImageDao.findByCriteria(criteria, new Page(0, 1));
        return projectimageList != null && !projectimageList.isEmpty() ? projectimageList.get(0) : null;
    }

    public List<CruiseShipProjectImage> getImageListByKeyword(String keyword, SysUser sysUser) {
        Criteria<CruiseShipProjectImage> criteria = new Criteria<CruiseShipProjectImage>(CruiseShipProjectImage.class);
        if (StringUtils.isNotBlank(keyword)) {
            criteria.like("imagDesc", keyword, MatchMode.ANYWHERE);
        }
        // 数据过滤
//		if (sysUser != null) {
//			criteria.eq("companyUnitId", sysUser.getSysUnit().getCompanyUnit().getId());
//		}
        return cruiseShipProjectImageDao.findByCriteria(criteria);
    }

    /**
     * 查询图片列表
     * @param projectId
     * @param coverFlag
     * @param limit
     * @return
     */
    public List<CruiseShipProjectImage> listImagesBy(Long projectId, Boolean coverFlag, Integer limit) {
        Criteria<CruiseShipProjectImage> criteria = new Criteria<CruiseShipProjectImage>(CruiseShipProjectImage.class);
        criteria.eq("cruiseShipProject.id", projectId);
        criteria.eq("coverFlag", coverFlag);
//		criteria.eq("status", ProductStatus.UP);
        criteria.orderBy("id", "desc");
        Page page = new Page(1, limit);
        List<CruiseShipProjectImage> projectImages = cruiseShipProjectImageDao.findByCriteria(criteria, page);
        return projectImages;
    }

    public void doDelByTargetId(Long ticketPriceId) {
        List<CruiseShipProjectImage> projectImageList = findImagesByTargetId(ticketPriceId);
        cruiseShipProjectImageDao.deleteAll(projectImageList);

    }

    private List<CruiseShipProjectImage> findImagesByTargetId(Long ticketPriceId) {
        Criteria<CruiseShipProjectImage> criteria = new Criteria<CruiseShipProjectImage>(CruiseShipProjectImage.class);
        criteria.eq("targetId", ticketPriceId);
        return cruiseShipProjectImageDao.findByCriteria(criteria);
    }

    public void delImgByUpChecking(Long projectId) {
        String sql = "delete from cruise_ship_project_image where projectId=?";
        cruiseShipProjectImageDao.updateBySQL(sql, projectId);
//        cruiseShipProjectImageDao.updateBySQL(sql, projectId, status.toString(), targetId, childFolder);
    }

    public void delImages(Long projectId) {
        String sql = "delete from cruise_ship_project_image where projectId=?";
        cruiseShipProjectImageDao.updateBySQL(sql, projectId);
//        cruiseShipProjectImageDao.updateBySQL(sql, productId, targetId, childFolder);
    }


    /**
     * 查找相应状态下的图片
     * @param projectId
     * @param status
     * @return
     */
   /* public List<CruiseShipProjectImage> findImagesByProductStatus(Long productId, ProductStatus status, String childFolder) {
        Criteria<CruiseShipProjectImage> criteria = new Criteria<CruiseShipProjectImage>(Productimage.class);
        criteria.eq("product.id", productId);
        criteria.eq("status", status);
        if (StringUtils.isNotBlank(childFolder)) {
            criteria.eq("childFolder", childFolder);
        }
        List<Productimage> productImages = productimageDao.findByCriteria(criteria);
        return productImages;
    }*/

    /**
     * 通过projectId查找图片
     * @param projectId
     * @return
     */
    public List<CruiseShipProjectImage> findAllImagesByProIdAadTarId(Long projectId, String... orderProperties) {
        Criteria<CruiseShipProjectImage> criteria = new Criteria<CruiseShipProjectImage>(CruiseShipProjectImage.class);
        criteria.eq("cruiseShipProject.id", projectId);
        /*if (targetId != null) {
            criteria.eq("targetId", targetId);
        }*/
       /* if (StringUtils.isNotBlank(childFolder)) {
            criteria.eq("childFolder", childFolder);
        }*/
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(Order.asc(orderProperties[0]));
        }
        return cruiseShipProjectImageDao.findByCriteria(criteria);
    }
    public List<CruiseShipProjectImage> queryImagesById(Long projectId) {
        Criteria<CruiseShipProjectImage> criteria = new Criteria<CruiseShipProjectImage>(CruiseShipProjectImage.class);
        criteria.eq("cruiseShipProject.id", projectId);

        return cruiseShipProjectImageDao.findByCriteria(criteria);
    }

    /**
     * 为图片修改状态
     * @param productimages
     *//*
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
*/
    /**
     * 查询图片列表 - 七牛图片水印处理使用
     *//*
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
    }*/

    /**
     * 保存图片排序
     * @param ids
     */
    public void saveProductImageSort(List<Long> ids) {
        for (int i = 1; i <= ids.size(); i++) {
            CruiseShipProjectImage cruiseShipProjectImage = cruiseShipProjectImageDao.load(ids.get(i - 1));
            cruiseShipProjectImage.setShowOrder(i);
            cruiseShipProjectImageDao.update(cruiseShipProjectImage);
        }

    }
}
