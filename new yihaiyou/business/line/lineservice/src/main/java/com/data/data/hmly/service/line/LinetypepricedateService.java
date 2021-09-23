package com.data.data.hmly.service.line;

import com.data.data.hmly.service.line.dao.LinetypepriceDao;
import com.data.data.hmly.service.line.dao.LinetypepricedateDao;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Restrictions;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class LinetypepricedateService {
	@Resource
	private LinetypepricedateDao linetypepricedateDao;
	@Resource
	private LinetypepriceDao linetypepriceDao;

	public void save(Linetypepricedate linetypepricedate) {
		linetypepricedateDao.save(linetypepricedate);
	}

	public void update(Linetypepricedate linetypepricedate) {
		linetypepricedateDao.update(linetypepricedate);
	}

	/**
	 * 当前日期起，成人最低价
	 * @param line
	 * @param linetypeprice
	 * @param startTime
	 * @return
	 */
	public Float getMinAdultPrice(Line line, Linetypeprice linetypeprice, Date startTime) {
        Float minDiscountPrice = linetypepriceDao.findMinValue(linetypeprice.getId(), startTime, null, "discountPrice");
        if (minDiscountPrice == null) {
            return 0f;
        }
        return minDiscountPrice;
	}


	/**
	 * 当前日期起，儿童最低价
	 * @param line
	 * @param linetypeprice
	 * @param startTime
	 * @return
	 */
	public Float getMinChildPrice(Line line, Linetypeprice linetypeprice, Date startTime) {
		Float minChildPrice = linetypepriceDao.findMinValue(linetypeprice.getId(), startTime, null, "childPrice");
		if (minChildPrice == null) {
			return 0f;
		}
        return minChildPrice;
	}


	/**
	 * 当前时间起的日历价格列表
	 * @param linetypeprice
	 * @param startTime
	 * @return
	 */
	public List<Linetypepricedate> findByTypeAndTime(Linetypeprice linetypeprice, Date startTime) {

		Criteria<Linetypepricedate> criteria = new Criteria<Linetypepricedate>(Linetypepricedate.class);
		criteria.eq("linetypeprice", linetypeprice);
		criteria.ge("day", startTime);
		criteria.orderBy("day", "asc");
		return linetypepricedateDao.findByCriteria(criteria);

	}





	/**
	 * 查询符合日期段的报价时间
	 * @author caiys
	 * @date 2015年10月23日 上午11:16:17
	 * @param linetypepriceId
	 * @param dateStart
	 * @param dateEnd
	 * @return
	 */
	public List<Linetypepricedate> findTypePriceDate(Long linetypepriceId, Date dateStart, Date dateEnd) {
		Criteria<Linetypepricedate> criteria = new Criteria<Linetypepricedate>(Linetypepricedate.class);
		criteria.eq("linetypeprice.id", linetypepriceId);
		if (dateStart != null) {
			criteria.ge("day", dateStart);
		}

		if (dateEnd != null) {
			criteria.le("day", dateEnd);
		}
		criteria.orderBy("day", "asc");
		return linetypepricedateDao.findByCriteria(criteria);
	}

	public List<Linetypepricedate> findTypePriceDate(Long linetypepriceId, Date dateStart, Date dateEnd, Integer num) {
		Criteria<Linetypepricedate> criteria = new Criteria<Linetypepricedate>(Linetypepricedate.class);
		criteria.eq("linetypeprice.id", linetypepriceId);
		if (dateStart != null) {
			criteria.ge("day", dateStart);
		}

		if (dateEnd != null) {
			criteria.le("day", dateEnd);
		}

		if (num != null) {
			criteria.or(Restrictions.eq("inventory", -1), Restrictions.isNull("inventory"), Restrictions.ge("inventory", num));
		}
		criteria.orderBy("day", "asc");
		return linetypepricedateDao.findByCriteria(criteria);
	}

	public Linetypepricedate load(Long id) {
		return linetypepricedateDao.load(id);
	}

	public List<Linetypepricedate> findAvailableByType(Linetypeprice linetypeprice) {
		Linetypepricedate linetypepricedate = new Linetypepricedate();
		linetypepricedate.setLinetypeprice(linetypeprice);
		linetypepricedate.setDay(new Date());
		return findTypePriceDate(linetypepricedate, null);
	}

	public List<Linetypepricedate> findByType(Linetypeprice linetypeprice) {
		Linetypepricedate linetypepricedate = new Linetypepricedate();
		linetypepricedate.setLinetypeprice(linetypeprice);
		return findTypePriceDate(linetypepricedate, null);
	}

	public List<Linetypepricedate> findTypePriceDate(Linetypepricedate linetypepricedate, Page page, String... orderProperty) {
		Criteria<Linetypepricedate> criteria = createCriteria(linetypepricedate, orderProperty);
		if (page != null) {
			return linetypepricedateDao.findByCriteria(criteria, page);
		}
		return linetypepricedateDao.findByCriteria(criteria);
	}

	public Criteria<Linetypepricedate> createCriteria(Linetypepricedate linetypepricedate, String... orderProperty) {
		Criteria<Linetypepricedate> criteria = new Criteria<Linetypepricedate>(Linetypepricedate.class);
		if (orderProperty.length == 2) {
			criteria.orderBy(orderProperty[0], orderProperty[1]);
		} else if (orderProperty.length == 1) {
			criteria.orderBy(Order.desc(orderProperty[0]));
		}else {
			criteria.orderBy(Order.asc("day"));
		}
		if (linetypepricedate == null) {
			return criteria;
		}
		if (linetypepricedate.getLinetypeprice() != null) {
			criteria.eq("linetypeprice.id", linetypepricedate.getLinetypeprice().getId());
		}
		// 当传递了day参数时，将查询该天之后的数据，此条件被应用于线路详情页面查询价格计划使用，如果需要更改请联系郭世杰
		if (linetypepricedate.getDay() != null) {
			criteria.gt("day", linetypepricedate.getDay());
		}

		return criteria;
	}
}
