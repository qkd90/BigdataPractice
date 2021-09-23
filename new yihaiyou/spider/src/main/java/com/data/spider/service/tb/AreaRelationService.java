package com.data.spider.service.tb;

import com.data.spider.service.dao.AreaRelationDao;
import com.data.spider.service.pojo.tb.AreaRelation;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vacuity on 15/12/31.
 */
@Service
public class AreaRelationService {

    @Resource
    private AreaRelationDao areaRelationDao;


    public AreaRelation get(Long id) {
        return areaRelationDao.load(id);
    }

    public AreaRelation findByElongId(Integer elongId) {
        AreaRelation areaRelation = new AreaRelation();
        areaRelation.setElongHotelCity(elongId);
        Criteria<AreaRelation> criteria = createCriteria(areaRelation);
        List<AreaRelation> areaRelationList = areaRelationDao.findByCriteria(criteria);
        if (areaRelationList != null && areaRelationList.size() > 0) {
            return areaRelationList.get(0);
        }
        return null;
    }

    public Criteria<AreaRelation> createCriteria(AreaRelation areaRelation) {
        Criteria<AreaRelation> criteria = new Criteria<AreaRelation>(AreaRelation.class);
        if (areaRelation.getElongHotelCity() != null) {
            criteria.eq("elongHotelCity", areaRelation.getElongHotelCity());
        }
        return criteria;
    }

    public List<AreaRelation> getCtrpCity() {
        Criteria<AreaRelation> criteria = new Criteria<AreaRelation>(AreaRelation.class);
        criteria.isNotNull("ctripHotelCity");
        return areaRelationDao.findByCriteria(criteria);
    }

    public List<AreaRelation> gets(int size, Criteria<AreaRelation> c) {
        Page page = new Page(1, size);
        List<AreaRelation> dis = areaRelationDao.findByCriteriaWithOutCount(c, page); //数据量大时，使用 count会执行缓慢
        return dis;
    }

    public void update(AreaRelation city) {
        areaRelationDao.update(city);
    }
}
