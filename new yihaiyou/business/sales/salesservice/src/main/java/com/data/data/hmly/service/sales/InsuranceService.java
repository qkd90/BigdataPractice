package com.data.data.hmly.service.sales;

import com.data.data.hmly.service.sales.dao.InsuranceDao;
import com.data.data.hmly.service.sales.entity.Insurance;
import com.data.data.hmly.service.sales.entity.enums.SalesStatus;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by zzl on 2016/7/6.
 */
@Service
public class InsuranceService {

    @Resource
    private InsuranceDao insuranceDao;

    public Insurance get(Long id) {
        return insuranceDao.load(id);
    }

    public List<Insurance> list(Insurance condition, Page page, String...orderProperties) {
        Criteria<Insurance> criteria = createCriteria(condition, orderProperties);
        if (page != null && page.getPageSize() > 1) {
            return insuranceDao.findByCriteria(criteria, page);
        }
        return insuranceDao.findByCriteria(criteria);
    }

    public boolean save(Insurance insurance) {
        try {
            insurance.setCreateTime(new Date());
            insuranceDao.save(insurance);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void update(Insurance insurance) {
        insurance.setUpdateTime(new Date());
        insuranceDao.update(insurance);
    }

    public boolean update(Insurance targetInsurance, Insurance sourceInsurance) {
        try {
            if (StringUtils.hasText(targetInsurance.getName())) {
                sourceInsurance.setName(targetInsurance.getName());
            }
            if (targetInsurance.getCategory() != null) {
                sourceInsurance.setCategory(targetInsurance.getCategory());
            }
            if (StringUtils.hasText(targetInsurance.getCompany())) {
                sourceInsurance.setCompany(targetInsurance.getCompany());
            }
            if (targetInsurance.getPrice() != null && targetInsurance.getPrice() > 0) {
                sourceInsurance.setPrice(targetInsurance.getPrice());
            }
            if (targetInsurance.getStatus() != null) {
                sourceInsurance.setStatus(targetInsurance.getStatus());
            }
//            if (StringUtils.hasText(targetInsurance.getDescription())) {
//                sourceInsurance.setDescription(targetInsurance.getDescription());
//            }
//            if (StringUtils.hasText(targetInsurance.getNotice())) {
//                sourceInsurance.setNotice(targetInsurance.getNotice());
//            }
//            if (StringUtils.hasText(targetInsurance.getTerms())) {
//                sourceInsurance.setTerms(targetInsurance.getTerms());
//            }
            sourceInsurance.setDescription(targetInsurance.getDescription());
            sourceInsurance.setNotice(targetInsurance.getNotice());
            sourceInsurance.setTerms(targetInsurance.getTerms());
            sourceInsurance.setUpdateTime(new Date());
            insuranceDao.update(sourceInsurance);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    private Criteria<Insurance> createCriteria(Insurance condition, String... orderProperties) {
        Criteria criteria = new Criteria<Insurance>(Insurance.class);
        if (orderProperties != null) {
            if (orderProperties.length > 1 && StringUtils.hasText(orderProperties[0]) && StringUtils.hasText(orderProperties[1])) {
                criteria.orderBy(orderProperties[0], orderProperties[1]);
            } else if (orderProperties.length == 1 && StringUtils.hasText(orderProperties[0])) {
                criteria.orderBy(orderProperties[0], "desc");
            }
        }
        if (condition == null) {
            return criteria;
        }
        if (StringUtils.hasText(condition.getName())) {
            criteria.like("name", condition.getName(), MatchMode.ANYWHERE);
        }
        if (condition.getCategory() != null && condition.getCategory().getId() > 0) {
            criteria.eq("category", condition.getCategory());
        }
        if (StringUtils.hasText(condition.getCompany())) {
            criteria.like("company", condition.getCompany(), MatchMode.ANYWHERE);
        }
        if (condition.getStatus() != null) {
            criteria.eq("status", condition.getStatus());
        }
        if (condition.getMinPrice() != null && condition.getMinPrice() > 0) {
            criteria.ge("price", condition.getMinPrice());
        }
        if (condition.getMaxPrice() != null && condition.getMaxPrice() > 0) {
            criteria.le("price", condition.getMaxPrice());
        }
        return criteria;
    }

    public List<Insurance> listLineInsurance() {
        Insurance insurance = new Insurance();
        insurance.setStatus(SalesStatus.up);
        return list(insurance, null, "price", "asc");
    }

}
