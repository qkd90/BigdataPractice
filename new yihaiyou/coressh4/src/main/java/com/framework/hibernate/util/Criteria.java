package com.framework.hibernate.util;

import com.zuipin.util.StringUtils;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;

import java.util.Collection;

/**
 * @author Kingsley
 */
public class Criteria<T> extends DetachedCriteria {

	/**
	 *
	 */
	private static final long serialVersionUID = -3689296694447109717L;

	public Criteria(Class<T> clazz) {
		// TODO Auto-generated constructor stub
		super(clazz.getName());
	}

	public Criteria<T> eq(String property, String value) {
		add(Restrictions.eq(property, value));
		return this;
	}

	@Override
	public Criteria<T> createAlias(String associationPath, String alias) {
		this.createAlias(associationPath, alias);
		return this;
	}

	public Criteria<T> ge(String string, Object value) {
		add(Restrictions.ge(string, value));
		return this;
	}

	public Criteria<T> le(String string, Object value) {
		add(Restrictions.le(string, value));
		return this;
	}

	public Criteria<T> gt(String string, Object value) {
		add(Restrictions.gt(string, value));
		return this;
	}

	public Criteria<T> lt(String string, Object value) {
		add(Restrictions.lt(string, value));
		return this;
	}

	public Criteria<T> isNull(String property) {
		add(Restrictions.isNull(property));
		return this;
	}

	public Criteria<T> isNotNull(String property) {
		add(Restrictions.isNotNull(property));
		return this;
	}

	public Criteria<T> al(String property) {
		add(Restrictions.isNull(property));
		return this;
	}

	public Criteria<T> eq(String property, Object value) {
		add(Restrictions.eq(property, value));
		return this;
	}

	public Criteria<T> ne(String property, String value) {
		add(Restrictions.ne(property, value));
		return this;
	}

	public Criteria<T> ge(String property, String value) {
		add(Restrictions.ge(property, value));
		return this;
	}

	public Criteria<T> le(String property, String value) {
		add(Restrictions.le(property, value));
		return this;
	}

	public Criteria<T> Add(Criterion e) {
		add(e);
		return this;
	}

	public Criteria<T> not(String property, Object value) {
		add(Restrictions.not(Restrictions.eq(property, value)));
		return this;
	}

	public Criteria<T> ne(String property, Object value) {
		add(Restrictions.ne(property, value));
		return this;
	}

	public Criteria<T> in(String property, Object[] values) {
		add(Restrictions.in(property, values));
		return this;
	}

	public Criteria<T> in(String property, Collection<?> values) {
		add(Restrictions.in(property, values));
		return this;
	}

	public Criteria<T> notin(String property, Collection<?> values) {
		add(Restrictions.not(Restrictions.in(property, values)));
		return this;
	}

	public Criteria<T> in(String property, String alias, String aliasProperty, Collection<?> values) {
		this.createCriteria(property, alias).add(Restrictions.in(aliasProperty, values));
		return this;
	}

	public Criteria<T> like(String property, String value, MatchMode matchModel) {
		if (StringUtils.hasText(value)) {
			add(Restrictions.like(property, value, matchModel));
		}
		return this;
	}

	public Criteria<T> like(String property, String value) {
		if (StringUtils.hasText(value)) {
			add(Restrictions.like(property, value, MatchMode.ANYWHERE));
		}
		return this;
	}

	public Criteria<T> between(String property, Object minValue, Object maxValue) {
		add(Restrictions.between(property, minValue, maxValue));
		return this;
	}

	public Criteria<T> or(Criterion lhs, Criterion rhs) {
		add(Restrictions.or(lhs, rhs));
		return this;
	}
	public Criteria<T> or(Criterion... criterions) {
		add(Restrictions.or(criterions));
		return this;
	}

	public Criteria<T> eqOrIsNull(String property, Object obj) {
		add(Restrictions.eqOrIsNull(property, obj));
		return this;
	}

	public Criteria<T> orderBy(Order order) {
		addOrder(order);
		return this;
	}

	public Criteria<T> orderBy(String sortName, String sortOrder) {
		if (sortOrder.equals("asc")) {
			addOrder(Order.asc(sortName));
		} else {
			addOrder(Order.desc(sortName));
		}
		return this;
	}

	@Override
	public DetachedCriteria createCriteria(String proName, JoinType joinType) {
		return super.createCriteria(proName, proName, joinType);
	}

	@Override
	public DetachedCriteria createCriteria(String proName) {
		return this.createCriteria(proName, proName, JoinType.INNER_JOIN);
	}

}
