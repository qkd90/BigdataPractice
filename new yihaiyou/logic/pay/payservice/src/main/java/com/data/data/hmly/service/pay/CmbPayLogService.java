package com.data.data.hmly.service.pay;

import com.data.data.hmly.service.pay.dao.CmbPayLogDao;
import com.data.data.hmly.service.pay.entity.CmbPayLog;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by huangpeijie on 2017-03-03,0003.
 */
@Service
public class CmbPayLogService {
    @Resource
    private CmbPayLogDao cmbPayLogDao;

    public void save(CmbPayLog cmbPayLog) {
        cmbPayLogDao.save(cmbPayLog);
    }

    public void update(CmbPayLog cmbPayLog) {
        cmbPayLogDao.update(cmbPayLog);
    }

    public CmbPayLog get(Long id) {
        return cmbPayLogDao.load(id);
    }

    public List<CmbPayLog> list(CmbPayLog cmbPayLog, Page page, String... orderParams) {
        Criteria<CmbPayLog> criteria = new Criteria<>(CmbPayLog.class);
        if (cmbPayLog.getUser() != null) {
            criteria.eq("user.id", cmbPayLog.getUser().getId());
        }
        if (cmbPayLog.getSuccess() != null) {
            criteria.eq("success", cmbPayLog.getSuccess());
        }
        if (orderParams.length == 1) {
            criteria.orderBy(Order.asc(orderParams[0]));
        } else if (orderParams.length == 2) {
            criteria.orderBy(orderParams[0], orderParams[1]);
        }
        if (page == null) {
            return cmbPayLogDao.findByCriteria(criteria);
        }
        return cmbPayLogDao.findByCriteria(criteria, page);
    }
}
