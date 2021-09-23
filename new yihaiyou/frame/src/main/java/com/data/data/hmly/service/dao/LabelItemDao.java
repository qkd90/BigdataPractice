package com.data.data.hmly.service.dao;

import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.TbArea;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.RootEntityResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class LabelItemDao extends DataAccess<LabelItem> {

	@Resource
	private TbAreaDao areaDao;
	
	public List<Object> getScenicLabels(Object clazz,String name,TbArea area, String tagIds, Page pageInfo) throws ClassNotFoundException {
		
		List<Object> infos = new ArrayList<Object>();
		
		Criteria<Object> criteria = new Criteria<Object>(Object.class);

        if (name != null) {
            criteria.like("name", "%" + name + "%");
        }
        if (area != null) {
            if (area.getLevel() == 1) {
                List<TbArea> areas = areaDao.findCityByPro(area.getId());
                criteria.in("city", areas);
            } else if (area.getLevel() == 2) {
                criteria.eq("city", area);
            }
        }
        if (StringUtils.isNotBlank(tagIds)) {
            String[] tIdStrs = tagIds.split(",");
            Long[] ids = new Long[tIdStrs.length];
            for (int i = 0; i < tIdStrs.length; i++) {
                ids[i] = Long.parseLong(tIdStrs[i]);
            }
            criteria.in("id", ids);
            infos = findByCriteria(criteria, pageInfo);
        } else if (tagIds == null) {
        	infos = findByCriteria(criteria, pageInfo);
        }
        return infos;
    }
	/*
	public List<T> getScenicLabels(T clazz,String name,TbArea area, String tagIds, Page pageInfo) throws ClassNotFoundException {
		
		List<T> infos = new ArrayList<T>();
		
		Criteria<Object> criteria = new Criteria<T>(T.class);

        if (name != null) {
            criteria.like("name", "%" + name + "%");
        }
        if (area != null) {
            if (area.getLevel() == 1) {
                List<TbArea> areas = areaDao.findCityByPro(area.getId());
                criteria.in("city", areas);
            } else if (area.getLevel() == 2) {
                criteria.eq("city", area);
            }

        }
        if (StringUtils.isNotBlank(tagIds)) {
            String[] tIdStrs = tagIds.split(",");
            Long[] ids = new Long[tIdStrs.length];
            for (int i = 0; i < tIdStrs.length; i++) {
                ids[i] = Long.parseLong(tIdStrs[i]);
            }
            criteria.in("id", ids);
            infos = findByCriteria(criteria, pageInfo);
        } else if (tagIds == null) {
        	infos = findByCriteria(criteria, pageInfo);
        }
        return infos;
    }
	
	*/
	public List<LabelItem> findNodeByLabel(Label label) {
		
		Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
		
		criteria.eq("label.id", label.getId());
		
		return findByCriteria(criteria);
	}

	public List<LabelItem> findLabelItemByTargIds(List<Long> areaIds) {
		Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
		
		criteria.in("targetId", areaIds);
		
		return findByCriteria(criteria);
	}

	public List<LabelItem> findItemByTagType(long targetId, TargetType type) {
		Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
		criteria.eq("targetId", targetId);
		criteria.eq("targetType", type);
		return findByCriteria(criteria);
	}

	public LabelItem findItemByTagTypeId(Long laId, long targetId,
			TargetType type) {
		Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
		criteria.eq("targetId", targetId);
		criteria.eq("label.id", laId);
		criteria.eq("targetType", type);
		return findUniqueByCriteria(criteria);
	}
	public List<LabelItem> getLabelItemByType(TargetType city) {
		Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        criteria.eq("targetType", city);
		return findByCriteria(criteria);
	}

    /**
     * 通过对象类型和标识删除关联
     * @param targetId
     * @param targetType
     */
    public void delByTarget(Long targetId, TargetType targetType) {
        StringBuilder hql = new StringBuilder();
        hql.append(" delete LabelItem where targetId = ? and targetType = ? ");
        updateByHQL(hql.toString(), targetId, targetType);
    }

    /**
     * 通过标签标识删除关联
     */
    public void delByLabelId(Long labelId) {
        StringBuilder hql = new StringBuilder();
        hql.append(" delete LabelItem where label.id = ? ");
        updateByHQL(hql.toString(), labelId);
    }

    /**
     * 获取已贴标签最大排序值
     * @param labelId
     * @param type
     * @return
     */
    public int getMaxOrder(Long labelId, TargetType type) {
        Criteria<LabelItem> criteria = new Criteria<LabelItem>(LabelItem.class);
        criteria.eq("label.id", labelId);
        criteria.eq("targetType", type);
        criteria.setProjection(Projections.max("order"));
        Integer maxOrder = (Integer) findUniqueCriteria(criteria);
        if (maxOrder == null) {
            return 0;
        }
        return maxOrder;
    }

    /**
     * 查询产品标签
     * @param labelId
     * @param type
     * @return
     */
    public List<LabelItem> findProductLabel(Long labelId, TargetType type, String cityStr) {
        List<LabelItem> list = new ArrayList<LabelItem>();
        StringBuilder hql = new StringBuilder();
        List<Object> params = new ArrayList<Object>();
        params.add(type);
        params.add(labelId);
        if (type == TargetType.SCENIC) {
            hql.append("select new LabelItem(li.id, li.label.id, li.targetId, li.targetType, p.name, p.modifyTime) ");
            hql.append("from LabelItem li, ScenicInfo p where li.targetId = p.id ");
            hql.append("and li.targetType = ? and li.label.id = ? and p.status = 1 ");
            if (StringUtils.isNotBlank(cityStr)) {
                hql.append("and p.city.id = ? ");
                params.add(Long.valueOf(cityStr));
            }
            hql.append("order by li.order ");
        } else if (type == TargetType.CITY) {
            hql.append("select new LabelItem(li.id, li.label.id, li.targetId, li.targetType, p.name) ");
            hql.append("from LabelItem li, TbArea p where li.targetId = p.id ");
            hql.append("and li.targetType = ? and li.label.id = ? ");
            if (StringUtils.isNotBlank(cityStr)) {
                hql.append("and p.id = ? ");
                params.add(Long.valueOf(cityStr));
            }
            hql.append("order by li.order ");
        } else if (type == TargetType.SAILBOAT) {
            hql.append("select new LabelItem(li.id, li.label.id, li.targetId, li.targetType, p.name, p.updateTime) ");
            hql.append("from LabelItem li, Product p where li.targetId = p.id ");
            hql.append("and li.targetType = ? and p.proType = 'scenic' and li.label.id = ? and p.status = 'UP' ");
            if (StringUtils.isNotBlank(cityStr)) {
                hql.append("and p.cityId = ? ");
                params.add(Long.valueOf(cityStr));
            }
            hql.append("order by li.order ");
        }  else if (type == TargetType.CRUISESHIP) {
            hql.append("select new LabelItem(li.id, li.label.id, li.targetId, li.targetType, p.name, p.updateTime) ");
            hql.append("from LabelItem li, Product p where li.targetId = p.id ");
            hql.append("and li.targetType = ? and p.proType = 'cruiseship' and li.label.id = ? and p.status = 'UP' ");
            if (StringUtils.isNotBlank(cityStr)) {
                hql.append("and p.cityId = ? ");
                params.add(Long.valueOf(cityStr));
            }
            hql.append("order by li.order ");
        } else if (type == TargetType.RECOMMEND_PLAN) {
            hql.append("select new LabelItem(li.id, li.label.id, li.targetId, li.targetType, p.planName, p.modifyTime) ");
            hql.append("from LabelItem li, RecommendPlan p where li.targetId = p.id ");
            hql.append("and li.targetType = ? and li.label.id = ? and p.status = 2 and p.deleteFlag = 2 ");
            if (StringUtils.isNotBlank(cityStr)) {
                hql.append("and p.city.id = ? ");
                params.add(Long.valueOf(cityStr));
            }
            hql.append("order by li.order ");
        } else if (type == TargetType.DELICACY) {
            hql.append("select new LabelItem(li.id, li.label.id, li.targetId, li.targetType, p.name) ");
            hql.append("from LabelItem li, Delicacy p where li.targetId = p.id ");
            hql.append("and li.targetType = ? and li.label.id = ? and p.status = 1 ");
            if (StringUtils.isNotBlank(cityStr)) {
                hql.append("and p.city.id = ? ");
                params.add(Long.valueOf(cityStr));
            }
            hql.append("order by li.order ");
        } else if (type == TargetType.PLAN) {
            hql.append("select new LabelItem(li.id, li.label.id, li.targetId, li.targetType, p.name, p.modifyTime) ");
            hql.append("from LabelItem li, Plan p where li.targetId = p.id ");
            hql.append("and li.targetType = ? and li.label.id = ? and p.status = 1 ");
            if (StringUtils.isNotBlank(cityStr)) {
                hql.append("and p.city.id = ? ");
                params.add(Long.valueOf(cityStr));
            }
            hql.append("order by li.order ");
        } else if (type == TargetType.TICKET) {
            hql.append("select new LabelItem(li.id, li.label.id, li.targetId, li.targetType, p.name, p.updateTime) ");
            hql.append("from LabelItem li, Product p where li.targetId = p.id ");
            hql.append("and li.targetType = ? and p.proType = 'scenic' and li.label.id = ? and p.status = 'UP' ");
            if (StringUtils.isNotBlank(cityStr)) {
                hql.append("and p.cityId = ? ");
                params.add(Long.valueOf(cityStr));
            }
            hql.append("order by li.order ");
        } else if (type == TargetType.HOTEL) {
            hql.append("select new LabelItem(li.id, li.label.id, li.targetId, li.targetType, p.name, p.updateTime) ");
            hql.append("from LabelItem li, Product p where li.targetId = p.id ");
            hql.append("and li.targetType = ? and p.proType = 'hotel' and li.label.id = ? and p.status = 'UP' ");
            if (StringUtils.isNotBlank(cityStr)) {
                hql.append("and p.cityId = ? ");
                params.add(Long.valueOf(cityStr));
            }
            hql.append("order by li.order ");
        } else if (type == TargetType.LINE) {
            hql.append("select new LabelItem(li.id, li.label.id, li.targetId, li.targetType, p.name, p.updateTime) ");
            hql.append("from LabelItem li, Line p where li.targetId = p.id ");
            hql.append("and li.targetType = ? and p.proType = 'line' and li.label.id = ? and p.status = 'UP' ");
            if (StringUtils.isNotBlank(cityStr)) {
                hql.append("and p.arriveCityId = ? ");
                params.add(Long.valueOf(cityStr));
            }
            hql.append("order by li.order ");
        } else {
            return list;
        }
        list = findByHQL(hql.toString(), params.toArray());
        return list;
    }

    public Object findUniqueCriteria(final com.framework.hibernate.util.Criteria<LabelItem> criteria) {
        return this.getHibernateTemplate().executeWithNativeSession(new HibernateCallback() {
            @Override
            public Object doInHibernate(Session session) throws HibernateException {
                org.hibernate.Criteria hibernateCriteria = criteria.getExecutableCriteria(session);
                hibernateCriteria.setResultTransformer(RootEntityResultTransformer.INSTANCE);
                List<Object> list = hibernateCriteria.list();
                if (!list.isEmpty()) {
                    return list.get(0);
                } else {
                    return null;
                }
            }
        });
    }

}
