package com.data.data.hmly.service.ticket.dao;

import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.goods.dao.CategoryDao;
import com.data.data.hmly.service.goods.entity.Category;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.criterion.SimpleExpression;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Repository;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TicketDao extends DataAccess<Ticket> {
	
	@Resource
	private CategoryDao categoryDao;

	public List<Ticket> findTicketList(Ticket ticket, Page pageInfo, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {
		
			Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);
		if (ticket != null) {
			foramtCond(ticket, criteria);
		}

		if (StringUtils.isNotBlank(ticket.getAgentTicket())) { // 查询被代理线路
			
			if (!isSupperAdmin) {
				criteria.createCriteria("companyUnit", "u", JoinType.INNER_JOIN);
				criteria.eq("u.sysSite.id", sysUser.getSysSite().getId());
				if (!isSiteAdmin) {
					criteria.eq("u.id", sysUser.getSysUnit().getCompanyUnit().getId());
				}
			}
		} else {
			// 数据过滤
			if (!isSupperAdmin) {
				criteria.createCriteria("companyUnit", "u", JoinType.INNER_JOIN);
				criteria.eq("u.sysSite.id", sysUser.getSysSite().getId());
				if (!isSiteAdmin) {
					criteria.eq("u.id", sysUser.getSysUnit().getCompanyUnit().getId());
				}
			}
		}
		
		criteria.orderBy("updateTime", "desc");
		return findByCriteria(criteria, pageInfo);
	}


	public List<Ticket> findAgentTicketList(Ticket ticket, Page pageInfo, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {

		Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);
		if (ticket != null) {
			foramtCond(ticket, criteria);
		}

		criteria.createCriteria("companyUnit", "u", JoinType.INNER_JOIN);
		criteria.createCriteria("u.sysSite", "us", JoinType.INNER_JOIN);

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
		return findByCriteria(criteria, pageInfo);

	}

	public List<Ticket> findTicketByName(Ticket ticket) {

		Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);

		if (ticket != null && ticket.getName() != null) {
			criteria.like("name", ticket.getName());
			criteria.eq("status", ProductStatus.UP);
			return findByCriteria(criteria);
		}
		return new ArrayList<Ticket>();
	}


	public List<Ticket> findTicketList(Ticket ticket, Page pageInfo) {
		
		Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);
		foramtCond(ticket, criteria);
		criteria.orderBy("updateTime", "desc");
		return findByCriteria(criteria, pageInfo);
	}
	
	
	/**
	 * 判断当前分类有没有数据
	 * @param category
	 * @return 数据的总数
	 */
	public Integer ticketCounts(Long category) {
		Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);
		
		criteria.eq("category", category);
		
		List<Ticket> ticketList = findByCriteria(criteria);
		
		return ticketList.size();
	}
	
	/**
	 * 返回当前分类下的子分类ID集合
	 * @param categoryL
	 * @return
	 */
	public List<Long> getCateChild(Long categoryL) {
				
		Criteria<Category> criteriaCate = new Criteria<Category>(Category.class);
		
		criteriaCate.eq("parentId", categoryL);
		
		List<Category> categorys = categoryDao.findByCriteria(criteriaCate);
		
		List<Long> category = new ArrayList<Long>();
		
		for (Category lc:categorys) {
			category.add(lc.getId());
		}
		return category;
	}
	
	/**
	 * 功能描述：查询条件拼接
	 * 
	 * @author zdy 
	 * @date 2015年10月16日 上午9:10:28
	 * @param ticket
	 * @param criteria
	 */
	public void foramtCond(Ticket ticket, Criteria<Ticket> criteria) {
        if (ticket.getFilterTicketTypeList() != null) {
            criteria.notin("ticketType", ticket.getFilterTicketTypeList());
        }
        // 门票类型
        if (ticket.getTicketType() != null) {
            criteria.eq("ticketType", ticket.getTicketType());
        }
        // 门票自定义分类
        if (ticket.getCategory() != null) {

            if (ticketCounts(ticket.getCategory()) != 0) {
                criteria.eq("category", ticket.getCategory());
            } else {

                List<Long> category = getCateChild(ticket.getCategory());
                criteria.in("category", category);
            }
        }
        // 状态
        if (ticket.getStatus() != null) {
            criteria.eq("status", ticket.getStatus());
        }

		if (ticket.getShowStatus() != null) {
			criteria.eq("showStatus", ticket.getShowStatus());
		}

        criteria.ne("status", ProductStatus.DEL);
        // 购物与自费
        if (StringUtils.isNotBlank(ticket.getName())) {
            criteria.like("name", "%" + ticket.getName() + "%");
        }
        if (ticket.getUser() != null && ticket.getUser().getSysSite() != null) {
            criteria.createCriteria("user", "user", JoinType.INNER_JOIN).add(Restrictions.eq("sysSite.id", ticket.getUser().getSysSite().getId()));
        }

        if (ticket.getSourceStr() != null && !"ALL".equals(ticket.getSourceStr())) {
			criteria.eq("source", fomatterProductSource(ticket.getSourceStr()));
        }

		if (ticket.getIncludeTicketTypeList() != null && !ticket.getIncludeTicketTypeList().isEmpty()) {
			if (ticket.getIncludeTicketTypeList().size() == 1) {
				criteria.eq("ticketType", ticket.getIncludeTicketTypeList().get(0));
			} else if (ticket.getIncludeTicketTypeList().size() == 2){
				criteria.or(Restrictions.eq("ticketType", ticket.getIncludeTicketTypeList().get(0)),
						Restrictions.eq("ticketType", ticket.getIncludeTicketTypeList().get(1)));
			} else if (ticket.getIncludeTicketTypeList().size() == 3){
				criteria.or(Restrictions.eq("ticketType", ticket.getIncludeTicketTypeList().get(0)),
						Restrictions.eq("ticketType", ticket.getIncludeTicketTypeList().get(1)),
						Restrictions.eq("ticketType", ticket.getIncludeTicketTypeList().get(2)));
			}
		}

		if (ticket.getIncludeTicketStatusList() != null && !ticket.getIncludeTicketStatusList().isEmpty()) {
			List<SimpleExpression> simpleExpressions = Lists.newArrayList();
			for (ProductStatus status : ticket.getIncludeTicketStatusList()) {
				simpleExpressions.add(Restrictions.eq("status", status));
			}
			criteria.or(simpleExpressions.toArray(new SimpleExpression[simpleExpressions.size()]));
		}

        // 智能筛选

        // 关键字

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
		if ("LXB".equals(sourceStr)) {
			return ProductSource.LXB;
		}
		return null;
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

    public void insert(Object o) {
        super.save(o);
    }

	public List<Ticket> getCheckingList(Ticket ticket, SysUser loginUser, Page pageInfo, Boolean superAdmin, String... orderProperties) {
		Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);
		foramtCond(ticket, criteria);
		if (!superAdmin) {
			criteria.createCriteria("companyUnit", "u", JoinType.INNER_JOIN);
			criteria.eq("u.sysSite.id", loginUser.getSysSite().getId());
		}
		if (orderProperties.length == 2) {
			criteria.orderBy(orderProperties[0], orderProperties[1]);
		} else if (orderProperties.length == 1) {
			criteria.orderBy(Order.asc(orderProperties[0]));
		}
		return findByCriteria(criteria, pageInfo);
	}
}
