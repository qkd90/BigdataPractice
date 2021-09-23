package com.data.data.hmly.service.scenic.dao;

import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.entity.enums.ScenicInfoType;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Repository;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class ScenicInfoDao extends DataAccess<ScenicInfo> {

    public ScenicInfo get(Long id) {
        Criteria<ScenicInfo> criteria = new Criteria<ScenicInfo>(ScenicInfo.class);
        criteria.eq("id", id);
        load(id);
        return findUniqueByCriteria(criteria);
    }

    public List<ScenicInfo> list(String keyword, ScenicInfoType scenicType, Page page) {
        StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("select new ScenicInfo(s.id, s.name, s.city.id, s.star, o.address) ");
        hql.append("from ScenicInfo s left join s.scenicOther o where s.status = 1 and s.city.id < 1010000 ");   // 只查询国内
        if (StringUtils.isNotBlank(keyword)) {
            hql.append("and s.name like '%'||:keyword||'%' ");
            params.put("keyword", keyword);
        }
        if (scenicType != null) {
            hql.append("and s.scenicType = :scenicType");
            params.put("scenicType", scenicType);
        }
        return findByHQL2ForNew(hql.toString(), page, params);
    }

    public List<ScenicInfo> getTopSceByDestination(ScenicInfo sceCodition, List<TbArea> tbAreas, Page page) {
        Criteria<ScenicInfo> criteria = createCriteria(sceCodition, tbAreas);
        return findByCriteria(criteria, page);
    }

    private Criteria<ScenicInfo> createCriteria(ScenicInfo scenicInfo, List<TbArea> tbAreas) {
        Criteria<ScenicInfo> criteria = new Criteria<ScenicInfo>(ScenicInfo.class);
//		order = new Order("ranking", true);
//		if (order != null) {
//			criteria.orderBy(order);
//		}
        if (scenicInfo == null) {
            return criteria;
        }
        if (!tbAreas.isEmpty()) {
            criteria.in("city", tbAreas);
        }
        criteria.eq("status", 1);
        criteria.orderBy(Order.asc("ranking"));
//		criteria.orderBy("ranking", "ASC");
//		if (scenicInfo.getLabelItems() != null && (!scenicInfo.getLabelItems().isEmpty())) {
//			DetachedCriteria dclabelItem = criteria.createCriteria("labelItems", "labelItem");
//			for (LabelItem labelItem : scenicInfo.getLabelItems()) {
//				dclabelItem.add(Restrictions.eq("label.id", labelItem.getLabel().getId()));
//			}
//		}
        return criteria;
    }

    public void clear() {
        getHibernateTemplate().clear();
    }

    @Override
    public void save(List<?> objs) {
        super.save(objs);
    }

    @Override
    public void save(Object entity) {
        super.save(entity);
    }

    @Override
    public void update(Object entity) {
        super.update(entity);
    }

}
