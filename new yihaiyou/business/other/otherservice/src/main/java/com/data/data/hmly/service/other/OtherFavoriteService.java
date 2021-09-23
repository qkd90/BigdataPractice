package com.data.data.hmly.service.other;

import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.other.dao.OtherFavoriteDao;
import com.data.data.hmly.service.other.entity.OtherFavorite;
import com.framework.hibernate.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class OtherFavoriteService {
    @Resource
    private OtherFavoriteDao otherFavoriteDao;
    @Resource
    private ProductimageService productimageService;

    /**
     * 添加收藏
     *
     * @param otherFavorite
     * @author caiys
     * @date 2015年12月23日 下午3:27:25
     */
    public void doAddOtherFavorite(OtherFavorite otherFavorite) {
        otherFavoriteDao.save(otherFavorite);
    }

    public String getImgPath(Long productId) {
        Productimage productimage = productimageService.findFirstImgByProId(productId);
        if (productimage != null) {
            return productimage.getPath();
        }
        return null;
    }



    /**
     * 检查是否已经收藏
     *
     * @param favoriteType
     * @param favoriteId
     * @param userId
     * @author caiys
     * @date 2015年12月23日 下午3:28:30
     */
    public boolean checkExists(ProductType favoriteType, Long favoriteId, Long userId) {
        List<OtherFavorite> otherFavorites = otherFavoriteDao.findOtherFavoriteBy(favoriteType, favoriteId, userId);
        if (otherFavorites.size() > 0) {
            return true;
        }
        return false;
    }

    /**
     * 根据标识清除收藏夹
     *
     * @param ids
     * @author caiys
     * @date 2015年12月23日 下午3:33:48
     */
    public void doClearFavoriteBy(String ids, Long userId) {
        if (StringUtils.isNotBlank(ids)) {
            String[] idArray = ids.split(",");
            for (String id : idArray) {
                OtherFavorite otherFavorite = otherFavoriteDao.load(Long.valueOf(id));
                if (userId.equals(otherFavorite.getUserId())) {    // 判断是否是登录人操作
                    otherFavorite.setDeleteFlag(true);
                    otherFavoriteDao.save(otherFavorite);
                }
            }
        }
    }

    /**
     * 批量清除收藏夹
     *
     * @param favoriteType
     * @param userId
     * @author caiys
     * @date 2015年12月23日 下午3:35:02
     */
    public void doClearFavoriteBy(ProductType favoriteType, Long userId, Long favoriteId) {
        otherFavoriteDao.clearFavoriteBy(favoriteType, userId, favoriteId);
    }

    public void doClearFavoritesBy(Long userId, List<Long> ids) {
        otherFavoriteDao.clearFavoritesBy(userId, ids);
    }

    /**
     * 查询列表
     *
     * @param otherFavorite
     * @param page
     * @return
     * @author caiys
     * @date 2015年12月22日 下午2:04:40
     */
    public List<OtherFavorite> findOtherFavoriteList(OtherFavorite otherFavorite, Page page) {
		return otherFavoriteDao.findOtherFavoriteList(otherFavorite, page);
    }

    public Long countOtherFavorite(OtherFavorite otherFavorite) {
        return otherFavoriteDao.countOtherFavorite(otherFavorite);
    }

    /**
     * 分组重新收藏类型
     *
     * @param userId
     * @return
     * @author caiys
     * @date 2016年1月3日 下午5:10:11
     */
    public List<Object> groupByFavoriteType(Long userId) {
        return otherFavoriteDao.groupByFavoriteType(userId);
    }

    /**
     * 根据类型查询列表
     *
     * @param favoriteType
     * @param userId
     * @return
     * @author huangpeijie
     * @date 2016-01-07
     */
    public List<OtherFavorite> findOtherFavoriteBy(ProductType favoriteType, Long userId) {
        return otherFavoriteDao.findOtherFavoriteBy(favoriteType, userId);
    }


    public List<OtherFavorite> findOtherFavoriteBy(Long userId, Page page, ProductType... favoriteType) {
        return otherFavoriteDao.findOtherFavoriteBy(userId, page, favoriteType);
    }
}
