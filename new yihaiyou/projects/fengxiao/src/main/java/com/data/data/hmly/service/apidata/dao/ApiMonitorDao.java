package com.data.data.hmly.service.apidata.dao;

import com.data.data.hmly.service.apidata.entity.ApiMonitor;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by caiys on 2017/1/4.
 */
@Repository
public class ApiMonitorDao extends DataAccess<ApiMonitor> {

    /**
     * 分页查询
     * @param apiMonitor
     * @param pageInfo
     * @return
     */
    public List<ApiMonitor> list(ApiMonitor apiMonitor, Page pageInfo) {
        Criteria<ApiMonitor> criteria = new Criteria<ApiMonitor>(ApiMonitor.class);
        criteria.orderBy("orderBy", "asc");
        if (pageInfo != null) {
            return findByCriteria(criteria, pageInfo);
        }
        return findByCriteria(criteria);
    }
}
