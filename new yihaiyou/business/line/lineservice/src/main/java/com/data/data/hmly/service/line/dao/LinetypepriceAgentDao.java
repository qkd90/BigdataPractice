package com.data.data.hmly.service.line.dao;

import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.LinetypepriceAgent;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.RootEntityResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public class LinetypepriceAgentDao extends DataAccess<LinetypepriceAgent> {


    /**
     * line获取报价列表
     * @param line
     * @return
     */
    public List<LinetypepriceAgent> getTypePriceList(Line line) {
        Criteria<LinetypepriceAgent> criteria = new Criteria<LinetypepriceAgent>(LinetypepriceAgent.class);
        criteria.eq("line", line);
        return findByCriteria(criteria);
    }

}
