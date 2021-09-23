package com.data.data.hmly.service.common.dao;

import com.data.data.hmly.service.common.entity.ProductValidateRecord;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created by caiys on 2016/3/24.
 */
@Repository
@Deprecated
public class ProductValidateRecordDao extends DataAccess<ProductValidateRecord> {

    /**
     * 查询验票记录
     * @param pvr
     * @param pageInfo
     * @return
     */
    public List<ProductValidateRecord> findValidateRecords(ProductValidateRecord pvr, Page pageInfo) {
        Criteria<ProductValidateRecord> criteria = new Criteria<ProductValidateRecord>(ProductValidateRecord.class);
        if (pvr.getScenicId() != null) {
            criteria.eq("scenicId", pvr.getScenicId());
        }
        if (pvr.getValidateTimeStart() != null) {
            criteria.ge("validateTime", pvr.getValidateTimeStart());
        }
        if (pvr.getValidateTimeEnd() != null) {
            criteria.le("validateTime", pvr.getValidateTimeEnd());
        }
        criteria.orderBy("validateTime", "DESC");
        return findByCriteria(criteria, pageInfo);
    }

}
