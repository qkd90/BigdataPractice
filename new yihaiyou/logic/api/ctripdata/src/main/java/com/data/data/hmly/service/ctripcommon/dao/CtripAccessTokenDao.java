package com.data.data.hmly.service.ctripcommon.dao;

import com.data.data.hmly.service.ctripcommon.entity.CtripAccessToken;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Repository;

/**
 * Created by caiys on 2016/2/18.
 */
@Repository
public class CtripAccessTokenDao extends DataAccess<CtripAccessToken> {

    /**
     *
     * @param aid
     * @param sid
     * @return
     */
    public CtripAccessToken findUniqueBy(Integer aid, Integer sid) {
        Criteria<CtripAccessToken> criteria = new Criteria<CtripAccessToken>(CtripAccessToken.class);
        criteria.eq("aid", aid);
        criteria.eq("sid", sid);
        return findUniqueByCriteria(criteria);
    }
}
