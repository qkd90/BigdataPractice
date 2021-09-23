package com.data.data.hmly.service;

import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.dao.SysUnitImageDao;
import com.data.data.hmly.service.dao.TbAreaDao;
import com.data.data.hmly.service.entity.*;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class SysUnitImageService {

    @Resource
    private SysUnitImageDao sysUnitImageDao;

    /**
     * 批量保存商户相关图片
     * @param unitImages
     * @param unit
     */
    public void save(List<SysUnitImage> unitImages, SysUnit unit) {

        for (int i = 0; i < unitImages.size(); i++) {
            SysUnitImage image = new SysUnitImage();
            image.setPath(unitImages.get(i).getPath());
            image.setType(unitImages.get(i).getType());
            image.setSysUnit(unit);
            image.setSort(i);
            image.setCreateTime(new Date());
            image.setUpdateTime(new Date());
            sysUnitImageDao.save(image);
        }

    }

    public void deleteByUnit(SysUnit unit) {
        String sql = "delete from sys_unit_image where unitId=?";
        sysUnitImageDao.updateBySQL(sql, unit.getId());
    }

    /**
     * 批量删除
     * @param unitImages
     */
    public void deleteAll(List<SysUnitImage> unitImages) {
        sysUnitImageDao.deleteAll(unitImages);
    }

    /**
     * 按条件查询所有
     * @param image
     * @return
     */
    private List<SysUnitImage> getImagesList(SysUnitImage image) {
        Criteria<SysUnitImage> criteria = new Criteria<SysUnitImage>(SysUnitImage.class);
        if (image == null) {
            return sysUnitImageDao.findByCriteria(criteria);
        }
        if (image.getSysUnit() != null) {
            criteria.eq("sysUnit.id", image.getSysUnit().getId());
        }
        if (image.getType() != null) {
            criteria.eq("type", image.getType());
        }
        return sysUnitImageDao.findByCriteria(criteria);
    }
}
