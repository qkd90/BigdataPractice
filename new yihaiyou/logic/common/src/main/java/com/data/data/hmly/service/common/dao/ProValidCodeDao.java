package com.data.data.hmly.service.common.dao;

import com.data.data.hmly.service.common.entity.ProValidCode;
import com.framework.hibernate.DataAccess;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by dy on 2016/12/22.
 */
@Repository
public class ProValidCodeDao extends DataAccess<ProValidCode> {

    /**
     * 更新未使用验证码为无效
     */
    public void updateUnusedStatus(Long orderDetailId) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("update ProValidCode c set c.used = -1 where c.orderDetailId = :orderDetailId and c.used = 0 ");
        params.put("orderDetailId", orderDetailId);
        updateByHQL2(hql.toString(), params);
    }
}
