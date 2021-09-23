package com.data.data.hmly.service.scenic;

import com.data.data.hmly.service.scenic.dao.RegionDao;
import com.data.data.hmly.service.scenic.entity.Region;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class RegionService {

//    Logger logger = Logger.getLogger(RegionService.class);

    @Resource
    private RegionDao regionDao;

    public void save(Region region) {
        regionDao.save(region);
    }

    public List<Region> list(Region region, Page page) {
        Criteria<Region> criteria = createCriteria(region);
        if (page != null) {
            return regionDao.findByCriteria(criteria, page);
        }
        return regionDao.findByCriteria(criteria);
    }

    public List<Region> listByCriteria(Criteria<Region> criteria, Page page) {
        return regionDao.findByCriteria(criteria, page);
    }

    private Criteria<Region> createCriteria(Region region) {
        Criteria<Region> criteria = new Criteria<Region>(Region.class);

        if (StringUtils.isNotBlank(region.getName())) {
            criteria.like("name", region.getName());
        }
        if (StringUtils.isNotBlank(region.getCityCode())) {
            criteria.eq("cityCode", region.getCityCode());
        }

        return criteria;
    }

}
