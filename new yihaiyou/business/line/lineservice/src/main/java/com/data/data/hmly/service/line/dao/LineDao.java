package com.data.data.hmly.service.line.dao;

import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.enums.LineStatus;
import com.data.data.hmly.service.line.entity.enums.PaySet;
import com.data.data.hmly.service.line.entity.enums.ScoreExchange;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.DateUtils;
import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class LineDao extends DataAccess<Line> {


	public List<Line> findAgentLineList(Line line, Page page, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {
		Criteria<Line> criteria = new Criteria<Line>(Line.class);
		criteria.createCriteria("companyUnit", "u", JoinType.INNER_JOIN);
		criteria.createCriteria("u.sysSite", "us", JoinType.INNER_JOIN);
		foramtCond(line, criteria);

//		criteria.createCriteria("parent", "p", JoinType.LEFT_OUTER_JOIN);
//		criteria.add(Restrictions.isNotNull("parent")); // 排除原线路

		criteria.createCriteria("topProduct", "top", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.neProperty("id", "top.id")); // 排除原线路

		if (!isSupperAdmin) {
			criteria.eq("u.sysSite.id", sysUser.getSysSite().getId());
			if (!isSiteAdmin) {
				criteria.eq("u.id", sysUser.getSysUnit().getCompanyUnit().getId());
			}
		}
		criteria.orderBy("updateTime", "desc");
		return findByCriteria(criteria, page);
	}


	/**
	 * 直销线路商品列表
	 * @param line
	 * @param page
	 * @return
	 */
	public List<Line> findProLineList(Line line, Page page) {
		Criteria<Line> criteria = new Criteria<Line>(Line.class);
		criteria.createCriteria("topProduct", "tp", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eqProperty("id", "tp.id")); // 排除代理线路
		if (line.getName() != null) {
			criteria.like("name", line.getName(), MatchMode.ANYWHERE);
		}
		criteria.eq("status", ProductStatus.UP);
		criteria.eq("lineStatus", LineStatus.show);
		criteria.isNull("source");
		criteria.orderBy("updateTime", "desc");
		return findByCriteria(criteria, page);
	}

	public List<Line> findLineList(Line line, SysUser sysUser) {

		Criteria<Line> criteria = new Criteria<Line>(Line.class);
		criteria.eq("parent", line);
		criteria.eq("user", sysUser);
		return findByCriteria(criteria);
	}

    /**
     * 通用线路列表查询方法
     * @param line
     * @param page
     * @return
     */
    public List<Line> findLineList(Line line, Page page) {
        Criteria<Line> criteria = new Criteria<Line>(Line.class);
//        criteria.createCriteria("companyUnit", "u", JoinType.INNER_JOIN);
//        criteria.createCriteria("u.sysSite", "us", JoinType.INNER_JOIN);
//        criteria.createCriteria("linetypeprices");
        // criteria.createCriteria("linestatistic");
        foramtCond(line, criteria);
        return findByCriteria(criteria, page);
    }

	/**
	 * 查询线路列表
	 * 
	 * @author caiys
	 * @date 2015年10月16日 上午9:29:32
	 * @param line
	 * @return
	 */
	public List<Line> findLineList(Line line, Page page, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {
		Criteria<Line> criteria = new Criteria<Line>(Line.class);
		criteria.createCriteria("companyUnit", "u", JoinType.INNER_JOIN);
		criteria.createCriteria("u.sysSite", "us", JoinType.INNER_JOIN);
		// criteria.createCriteria("linestatistic");
		foramtCond(line, criteria);
		if (StringUtils.isNotBlank(line.getAgentLine())) { // 查询被代理线路
			criteria.createCriteria("topProduct", "tp", JoinType.LEFT_OUTER_JOIN);
			criteria.add(Restrictions.neProperty("id", "tp.id")); // 排除原线路
			criteria.eq("tp.companyUnit.id", sysUser.getSysUnit().getCompanyUnit().getId());
		} else {
			// 数据过滤
			if (!isSupperAdmin) {
				criteria.eq("u.sysSite.id", sysUser.getSysSite().getId());
				if (!isSiteAdmin) {
					criteria.eq("u.id", sysUser.getSysUnit().getCompanyUnit().getId());
				}
			}
		}
		criteria.orderBy("updateTime", "desc");
		return findByCriteria(criteria, page);
	}

	public List<Line> findLineList(Line line, Page page, SysSite sysSite) {
		Criteria<Line> criteria = new Criteria<Line>(Line.class);
		// criteria.createCriteria("linestatistic");
		foramtCond(line, criteria);
		// 数据过滤
		if (sysSite != null) {
			criteria.createCriteria("user", "u", JoinType.INNER_JOIN);
			criteria.eq("u.sysSite.id", sysSite.getId());
		}
		criteria.orderBy("updateTime", "desc");
		return findByCriteria(criteria, page);
	}

	@Override
	public void save(List<?> objs) {
		super.save(objs);
	}

	@Override
	public void save(Object entity) {
		// TODO Auto-generated method stub
		super.save(entity);
	}

	@Override
	public void update(Object entity) {
		// TODO Auto-generated method stub
		super.update(entity);
	}

	public void update(List<Line> lines) {
		for (Line line : lines) {
			super.update(line);
		}
	}

	/**
	 * 功能描述：查询条件拼接
	 * 
	 * @author caiys
	 * @date 2015年10月16日 上午9:10:28
	 * @param line
	 * @param criteria
	 */
	public void foramtCond(Line line, Criteria<Line> criteria) {
		DetachedCriteria dc = criteria.createCriteria("linestatistic", "ls", JoinType.LEFT_OUTER_JOIN);
		// 线路类型
		if (StringUtils.isNotBlank(line.getLineType())) {
			criteria.eq("lineType", line.getLineType());
		}
		// 线路名称
		if (StringUtils.isNotBlank(line.getName())) {
			criteria.like("name", line.getName());
		}
		// 线路分类
		if (line.getProType() != null) {
			criteria.eq("proType", line.getProType());
		}
		// 产品性质
		if (line.getProductAttr() != null) {
			criteria.eq("productAttr", line.getProductAttr());
		}
		// 自定义分类
		if (line.getCategory() != null) {
			criteria.eq("category", line.getCategory());
		}
		// 城市
		if (line.getCityId() != null) {
			criteria.eq("cityId", line.getCityId());
		}
		// 购物与自费
		if (line.getBuypay() != null) {
			criteria.eq("buypay", line.getBuypay());
		}
		// 智能筛选
		if (StringUtils.isNotBlank(line.getScoreExchangeParticipation())) {
			criteria.eq("scoreExchange", ScoreExchange.participation);
		}
		if (StringUtils.isNotBlank(line.getPaySetNoClose())) {
			criteria.Add(Restrictions.or(Restrictions.eq("paySet", PaySet.earnest), Restrictions.eq("paySet", PaySet.allpay)));
		}
		if (StringUtils.isNotBlank(line.getClickNumLt10())) {
			// DetachedCriteria dc = criteria.createCriteria("linestatistic",
			// "linestatistic", JoinType.INNER_JOIN);
			dc.add(Restrictions.or(Restrictions.isNull("ls.clickNum"), Restrictions.lt("ls.clickNum", 10)));
		}
		if (StringUtils.isNotBlank(line.getOrderNumEq0())) {
			// DetachedCriteria dc = criteria.createCriteria("linestatistic",
			// "linestatistic", JoinType.INNER_JOIN);
			dc.add(Restrictions.or(Restrictions.isNull("ls.orderNum"), Restrictions.le("ls.orderNum", 0)));
		}

		// 关键字
		if (StringUtils.isNotBlank(line.getKeyword())) {
			criteria.or(Restrictions.like("name", line.getKeyword(), MatchMode.ANYWHERE),
					Restrictions.like("productNo", line.getKeyword(), MatchMode.ANYWHERE));
		}

		// 状态
		if (line.getLineStatus() != null) {
			criteria.eq("lineStatus", line.getLineStatus());
		}
		if (line.getLabelItems() != null && !line.getLabelItems().isEmpty()) {
			DetachedCriteria criterion = criteria.createCriteria("labelItems", "labelItem");
			for (LabelItem labelItem : line.getLabelItems()) {
				criterion.add(Restrictions.eq("label.id", labelItem.getLabel().getId()));
			}
            criterion.addOrder(Order.asc("order"));
		}
		criteria.ne("lineStatus", LineStatus.del);
		if (line.getStatus() != null) {
			criteria.eq("status", ProductStatus.UP);
		}

		if (com.zuipin.util.StringUtils.isNotBlank(line.getSourceStr())) {
			if ("PLATFORM".equals(line.getSourceStr())) {
				criteria.isNull("source");
			} else {
				criteria.eq("source", fomatterProductSource(line.getSourceStr()));
			}
		}

	}
	public ProductSource fomatterProductSource(String sourceStr) {
		if ("CTRIP".equals(sourceStr)) {
			return ProductSource.CTRIP;
		}
		if ("QUNAR".equals(sourceStr)) {
			return ProductSource.QUNAR;
		}
		if ("ELONG".equals(sourceStr)) {
			return ProductSource.ELONG;
		}
		if ("JUHE".equals(sourceStr)) {
			return ProductSource.JUHE;
		}
		return null;
	}

	public List<TbArea> getLineStartCity() {
		String hql = "select new TbArea(t.id,t.name) from Line l, TbArea t where l.startCityId = t.id and l.status = 'UP' group by l.startCityId";
		return findByHQL(hql);
	}

	/**
	 * 查询线路列表
	 *
	 * @author caiys
	 * @date 2015年10月16日 上午9:29:32
	 * @param line
	 * @return
	 */
	public List<Line> findLineListHql(Line line, Page page, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {
		StringBuilder hql = new StringBuilder();
        Map<String, Object> params = new HashMap<String, Object>();
        hql.append("select l from Line l left join l.linestatistic ls inner join l.companyUnit u inner join u.sysSite us ");
        if (StringUtils.isNotBlank(line.getAgentLine())) { // 查询被代理线路
            hql.append("inner join l.topProduct tp ");
        }
        hql.append("where l.lineStatus <> '").append(LineStatus.del).append("' ");
        if (StringUtils.isNotBlank(line.getAgentLine())) { // 查询被代理线路
            hql.append("and l.id <> tp.id ");    // 排除原线路
            hql.append("and tp.companyUnit.id = ").append(sysUser.getSysUnit().getCompanyUnit().getId()).append(" ");
        } else {
            // 数据过滤
            if (!isSupperAdmin) {
                hql.append("and u.sysSite.id = ").append(sysUser.getSysSite().getId()).append(" ");
                if (!isSiteAdmin) {
                    hql.append("and u.id = ").append(sysUser.getSysUnit().getCompanyUnit().getId()).append(" ");
                }
            }
        }
        // 页面查询条件
        // 线路类型
        if (StringUtils.isNotBlank(line.getLineType())) {
            hql.append("and l.lineType = :lineType ");
            params.put("lineType", line.getLineType());
        }
        // 线路名称
        if (StringUtils.isNotBlank(line.getName())) {
            hql.append("and l.name like '%'||:name||'%' ");
            params.put("name", line.getName());
        }
        // 线路分类
        if (line.getProType() != null) {
            hql.append("and l.proType = :proType ");
            params.put("proType", line.getProType());
        }
        // 产品性质
        if (line.getProductAttr() != null) {
            hql.append("and l.productAttr = :productAttr ");
            params.put("productAttr", line.getProductAttr());
        }
        // 自定义分类
        if (line.getCategory() != null) {
            hql.append("and l.category = :category ");
            params.put("category", line.getCategory());
        }
        // 购物与自费
        if (line.getBuypay() != null) {
            hql.append("and l.buypay = :buypay ");
            params.put("buypay", line.getBuypay());
        }
        // 关键字
        if (StringUtils.isNotBlank(line.getKeyword())) {
            hql.append("and (l.name like '%'||:keyword||'%' or l.productNo like '%'||:keyword||'%') ");
            params.put("keyword", line.getKeyword());
        }
        // 状态
        if (line.getLineStatus() != null) {
            Date outDate = DateUtils.getStartDay(new Date(), 3);
            if (line.getLineStatus() == LineStatus.show) {
                hql.append("and l.lineStatus = :lineStatus ");
                hql.append("and exists (select ld.id from Linetypepricedate ld where ld.lineId = l.id and ld.day >= :outDate) ");
                params.put("lineStatus", LineStatus.show);
                params.put("outDate", outDate);
            } else if (line.getLineStatus() == LineStatus.outday) {
                hql.append("and l.lineStatus = :lineStatus ");
                hql.append("and not exists (select ld.id from Linetypepricedate ld where ld.lineId = l.id and ld.day >= :outDate) ");
                params.put("lineStatus", LineStatus.show);
                params.put("outDate", outDate);
            } else {
                hql.append("and l.lineStatus = :lineStatus ");
                params.put("lineStatus", line.getLineStatus());
            }
        }

        hql.append(" order by l.updateTime desc ");
		return findByHQL2(hql.toString(), page, params);
	}

}
