package com.data.spider.service.dao;

import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

import com.data.spider.service.pojo.ScenicArea;
import com.framework.hibernate.DataAccess;

@Repository
public class ScenicAreaDao extends DataAccess<ScenicArea> {


    public ScenicArea findAreaByScenic(Long scenicId) {
        Criteria<ScenicArea> criteria = getCriteria();
        criteria.eq("scenicInfo.id", scenicId);
        return findUniqueByCriteria(criteria);
    }

}
