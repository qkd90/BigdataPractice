package com.data.data.hmly.service.dao;

import com.data.data.hmly.enums.LabelStatus;
import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.SysUnit;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.zuipin.util.StringUtils;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.transform.RootEntityResultTransformer;
import org.springframework.orm.hibernate4.HibernateCallback;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

@Repository
public class LabelDao extends DataAccess<Label> {

    public List<Label> getTreLabels(Page page, Label labelParent) {

        String sql = "";

        StringBuilder sb = new StringBuilder();

        sb.append("from Label l1 where l1.parent is null");
        if (labelParent.getName() != null || labelParent.getStatus() != null) {
            if (labelParent.getName() != null) {
                sb.append(" and l1.name like '%" + labelParent.getName() + "%'");
            }
            if (labelParent.getStatus() != null) {
                sb.append(" and l1.status = '" + labelParent.getStatus() + "'");
            }
            sb.append(" or l1.id in ");
            sb.append("(select l2.parent from Label l2 where l2.parent is not null");
            if (labelParent.getName() != null) {
                sb.append(" and l2.name like '%" + labelParent.getName() + "%'");
            }
            if (labelParent.getStatus() != null) {
                sb.append(" and l2.status = '" + labelParent.getStatus() + "'");
            }
            sb.append(")");
        }
//        sb.append(" order by createTime DESC");
        sql = sb.toString();
        return findByHQL(sql, page);
    }

    public List<Label> getChildByParent(Label label) {

        Criteria<Label> criteria = new Criteria<Label>(Label.class);

        criteria.eq("parent", label);
        return findByCriteria(criteria);
    }

    public List<Label> listByParent(Long parentId, LabelStatus status) {
        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        criteria.eq("parent.id", parentId);
        if (status.equals(LabelStatus.USE)) {
            criteria.eq("status", status);
        } else {
            criteria.ne("status", LabelStatus.DEL);
        }
        return findByCriteria(criteria);
    }

    public List<Long> listIdsByParent(Long parentId, LabelStatus status) {
        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        criteria.eq("parent.id", parentId);
        criteria.setProjection(Projections.groupProperty("id"));
        if (status.equals(LabelStatus.USE)) {
            criteria.eq("status", status);
        } else {
            criteria.not("status", LabelStatus.DEL);
        }
        List<Long> ids = new ArrayList<Long>();
        List<?> labels = findByCriteria(criteria);
        for (Object id : labels) {
            ids.add((Long) id);
        }
        return ids;
    }

    public List<Label> findAllTree(Page pageInfo) {
        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        criteria.isNull("parent");
        criteria.eq("status", LabelStatus.USE);
        if (pageInfo != null) {
            return findByCriteria(criteria, pageInfo);
        } else {
            return findByCriteria(criteria);
        }
    }

    public List<Label> findLabelSiteTree(List<SysUnit> units, Page pageInfo) {

        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        criteria.isNull("parent");
        criteria.in("sysUnit", units);
        criteria.eq("status", LabelStatus.USE);
        if (pageInfo != null) {
            return findByCriteria(criteria, pageInfo);
        } else {
            return findByCriteria(criteria);
        }

    }

    public List<Label> findLabelUnitTree(SysUnit companyUnit, Page pageInfo) {
        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        criteria.isNull("parent");
        criteria.eq("sysUnit", companyUnit);
        criteria.eq("status", LabelStatus.USE);
        if (pageInfo != null) {
            return findByCriteria(criteria, pageInfo);
        } else {
            return findByCriteria(criteria);
        }
    }

    public List<Label> findLabelSiteBy(List<SysUnit> units, List<Long> lebIds) {
        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        criteria.in("sysUnit", units);
        criteria.in("id", lebIds);
        criteria.eq("status", LabelStatus.USE);
        return findByCriteria(criteria);
    }

    public List<Label> findLabelUnitByTargId(SysUnit sysUnit, List<Long> lebIds) {
        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        criteria.eq("sysUnit", sysUnit);
        if (!lebIds.isEmpty()) {
            criteria.in("id", lebIds);
        } else {
           return new ArrayList<Label>();
        }
        criteria.eq("status", LabelStatus.USE);
        return findByCriteria(criteria);
    }

    public List<Label> findLabelSite(List<SysUnit> units) {
        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        criteria.in("sysUnit", units);
//		criteria.in("id", lebIds);
        criteria.eq("status", LabelStatus.USE);
        return findByCriteria(criteria);
    }

    public List<Label> finLabelsByIds(Long[] ids) {
        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        criteria.in("id", ids);
        criteria.eq("status", LabelStatus.USE);
        return findByCriteria(criteria);
    }

    public Label finLabelById(Long id) {

        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        criteria.eq("id", id);
        criteria.eq("status", LabelStatus.USE);
        return findUniqueByCriteria(criteria);
    }

    public List<Label> findLabelByParent(Long parent) {

        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        criteria.eq("parent.id", parent);
        criteria.eq("status", LabelStatus.USE);
        return findByCriteria(criteria);

    }

    public List<Label> getChildsTreLabels(Label labelParent) {

        Criteria<Label> criteria = new Criteria<Label>(Label.class);

        if (labelParent.getName() != null) {
            criteria.like("name", "%" + labelParent.getName() + "%");
        }
        if (labelParent.getStatus() != null) {
            criteria.eq("status", labelParent.getStatus());
        }

        criteria.isNotNull("parent");
        criteria.not("status", LabelStatus.DEL);
//        criteria.orderBy("createTime", "ASC");

        return findByCriteria(criteria);
    }

    /**
     * 查询标签列表
     * @param label
     * @return
     */
    public List<Label> listLabels(Label label) {
        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        if (label.getParentId() != null) {
            if (label.getParentId() == -1) {
                criteria.isNull("parent.id");
            } else {
                criteria.eq("parent.id", label.getParentId());
            }
        }
        if (label.getStatus() != null) {
            criteria.eq("status", label.getStatus());
        } else {
            criteria.ne("status", LabelStatus.DEL);
        }
        if (StringUtils.isNotBlank(label.getName())) {
            criteria.like("name", label.getName(), MatchMode.ANYWHERE);
        }
        criteria.addOrder(Order.asc("parent.id"));
        criteria.addOrder(Order.asc("sort"));
        return findByCriteria(criteria);
    }

    public List<Label> getNoExistsLabelsList(Label label, TargetType targetType, Long targetId) {

        StringBuilder sb = new StringBuilder();

        sb.append("from Label l");
        List params = Lists.newArrayList();

        if (label.getParentId() != null) {
            sb.append(" where");
            if (label.getParentId() == -1) {
                sb.append(" l.parent.id is null");
            } else {
                sb.append(" l.parent.id = ?");
                params.add(label.getParentId());
            }
        }
        if (label.getStatus() != null) {
            if (sb.length() > 13) {
                sb.append(" and l.status = ?");
                params.add(label.getStatus());
            } else {
                sb.append(" where l.status = ?");
                params.add(label.getStatus());
            }

        } else {
            if (sb.length() > 13) {
                sb.append(" and l.status != ?");
                params.add(LabelStatus.DEL);
            } else {
                sb.append(" where l.status != ?");
                params.add(LabelStatus.DEL);
            }
        }
        if (StringUtils.isNotBlank(label.getName())) {
            if (sb.length() > 13) {
                sb.append(" and l.name like '%" + label.getName() + "%' ");
            } else {
                sb.append(" where l.name like '%" + label.getName() + "%' ");
            }
        }

        if (sb.length() > 13) {
            sb.append(" and not exists (select 1 from LabelItem lit where lit.label.id = l.id");
            if (targetId != null) {
                sb.append(" and lit.targetId = ?");
                params.add(targetId);
            }
            if (targetType != null) {
                sb.append(" and lit.targetType = ?");
                params.add(targetType);
            }
            sb.append(")");
        } else {
            sb.append(" where not exists (select 1 from LabelItem lit where lit.label.id = l.id");
            if (targetId != null) {
                sb.append(" and lit.targetId = ?");
                params.add(targetId);
            }
            if (targetType != null) {
                sb.append(" and lit.targetType = ?");
                params.add(targetType);
            }
            sb.append(")");
        }

        sb.append(" order by l.parent.id asc, l.sort asc");
        return findByHQL(sb.toString(), params.toArray());
    }

    /**
     * 统计子标签数
     * @param parentId
     * @return
     */
    public Long countChildren(Long parentId) {
        Criteria<Label> criteria = new Criteria<Label>(Label.class);
        criteria.eq("parent.id", parentId);
        criteria.ne("status", LabelStatus.DEL);
        criteria.setProjection(Projections.rowCount());
        return (Long) findUniqueCriteria(criteria);
    }

    /**
     * 更新子标签状态
     * @param parenId
     * @param status
     */
    public void updateChildren(Long parenId, LabelStatus status) {
        List<Object> params = new ArrayList<Object>();
        StringBuilder hql = new StringBuilder();
        hql.append(" update Label set status = ? where parent.id = ? and status <> ? ");
        params.add(status);
        params.add(parenId);
        params.add(LabelStatus.DEL);
        updateByHQL(hql.toString(), params.toArray());
    }

    public Object findUniqueCriteria(final com.framework.hibernate.util.Criteria<Label> criteria) {
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
