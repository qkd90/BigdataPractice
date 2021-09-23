package com.data.data.hmly.service.cruiseship.dao;

import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.cruiseship.entity.CruiseShip;
import com.data.data.hmly.service.entity.SysUser;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class CruiseShipDao extends DataAccess<CruiseShip> {


    public void save(CruiseShip cruiseShip) {
        super.save(cruiseShip);
    }

    public void saveAll(List<CruiseShip> cruiseShipList) {
        super.save(cruiseShipList);
    }

    public void update(CruiseShip cruiseShip) {
        super.update(cruiseShip);
    }

    public void updateAll(List<CruiseShip> cruiseShipList) {
        super.updateAll(cruiseShipList);
    }


    /**
     * 分页查询
     * @param cruiseShip
     * @param page
     * @param sysUser
     * @param isSiteAdmin
     * @param isSupperAdmin
     * @return
     */
    public List<CruiseShip> pageCruiseShips(CruiseShip cruiseShip, Page page, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {
        Criteria<CruiseShip> criteria = new Criteria<CruiseShip>(CruiseShip.class);
        criteria.ne("status", ProductStatus.DEL);
        // 状态
        if (cruiseShip.getStatus() != null) {
            criteria.eq("status", cruiseShip.getStatus());
        }
        // 关键字
        if (StringUtils.isNotBlank(cruiseShip.getKeyword())) {
            criteria.or(Restrictions.like("name", cruiseShip.getKeyword(), MatchMode.ANYWHERE),
                    Restrictions.like("productNo", cruiseShip.getKeyword(), MatchMode.ANYWHERE));
        }
        // 数据过滤
        if (!isSupperAdmin) {
            criteria.createCriteria("companyUnit", "u", JoinType.INNER_JOIN);
            criteria.eq("u.sysSite.id", sysUser.getSysSite().getId());
            if (!isSiteAdmin) {
                criteria.eq("u.id", sysUser.getSysUnit().getCompanyUnit().getId());
            }
        }
        criteria.orderBy("updateTime", "desc");
        return findByCriteria(criteria, page);
    }

}
