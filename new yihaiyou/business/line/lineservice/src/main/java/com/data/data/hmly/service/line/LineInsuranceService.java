package com.data.data.hmly.service.line;

import com.data.data.hmly.service.line.dao.LineInsuranceDao;
import com.data.data.hmly.service.line.entity.LineInsurance;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by zzl on 2016/7/7.
 */
@Service
public class LineInsuranceService {

    @Resource
    private LineInsuranceDao lineInsuranceDao;

    public LineInsurance get(Long id) {
        return lineInsuranceDao.load(id);
    }

    public void saveAll(List<LineInsurance> lineInsuranceList) {
        lineInsuranceDao.save(lineInsuranceList);
    }

    public void updateAll(List<LineInsurance> lineInsurances) {
        lineInsuranceDao.updateAll(lineInsurances);
    }

    public LineInsurance getUnique(Long lineId, Long insuranceId) {
        Criteria<LineInsurance> criteria = new Criteria<LineInsurance>(LineInsurance.class);
        criteria.eq("line.id", lineId);
        criteria.eq("insurance.id", insuranceId);
        return lineInsuranceDao.findUniqueByCriteria(criteria);
    }

    public List<LineInsurance> getByLine(Long lineId) {
        Criteria<LineInsurance> criteria = new Criteria<LineInsurance>(LineInsurance.class);
        criteria.eq("line.id", lineId);
        return lineInsuranceDao.findByCriteria(criteria);
    }

    public void delete(LineInsurance lineInsurance) {
        lineInsuranceDao.delete(lineInsurance);
    }
    public List<LineInsurance> list(LineInsurance condition, Page page, String...orderProperties) {
        Criteria<LineInsurance> criteria = createcCriteria(condition, orderProperties);
        if (page != null) {
            return lineInsuranceDao.findByCriteria(criteria, page);
        }
        return lineInsuranceDao.findByCriteria(criteria);
    }

    private Criteria<LineInsurance> createcCriteria(LineInsurance condition, String... orderProperties) {
        Criteria<LineInsurance> criteria = new Criteria<LineInsurance>(LineInsurance.class);
        if (orderProperties != null) {
            if (orderProperties.length > 1 && orderProperties[0] != null && orderProperties[1] != null) {
                criteria.orderBy(orderProperties[0], orderProperties[1]);
            } else if (orderProperties.length == 1 && orderProperties[0] != null) {
                criteria.orderBy(orderProperties[0], "desc");
            }
        }
        if (condition == null) {
            return criteria;
        }
        if (condition.getLine() != null && condition.getLine().getId() != null) {
            criteria.eq("line.id", condition.getLine().getId());
        }
        if (condition.getInsurance() != null && condition.getInsurance().getId() != null) {
            criteria.eq("insurance.id", condition.getInsurance().getId());
        }
        if (condition.getStatus() != null) {
            criteria.eq("status", condition.getStatus());
        }
        return criteria;
    }
}
