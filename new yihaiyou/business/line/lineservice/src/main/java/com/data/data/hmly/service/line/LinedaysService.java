package com.data.data.hmly.service.line;

import com.data.data.hmly.service.line.dao.LinedaysDao;
import com.data.data.hmly.service.line.dao.LinedaysplanDao;
import com.data.data.hmly.service.line.entity.Linedays;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class LinedaysService {
	@Resource
	private LinedaysDao linedaysDao;
	@Resource
	private LinedaysplanDao linedaysplanDao;

	public void save(Linedays linedays) {
		linedaysDao.save(linedays);
	}

	public void update(Linedays linedays) {
		linedaysDao.update(linedays);
	}

	public List<Linedays> list(Linedays linedays, Page page) {
		Criteria<Linedays> criteria = createCriteria(linedays);
		if (page != null) {
			return linedaysDao.findByCriteria(criteria, page);
		}
		return linedaysDao.findByCriteria(criteria);
	}

	public Criteria<Linedays> createCriteria(Linedays linedays) {
		Criteria<Linedays> criteria = new Criteria<Linedays>(Linedays.class);

		if (linedays == null) {
			return criteria;
		}

		if (linedays.getLineId() != null) {
			criteria.eq("lineId", linedays.getLineId());
		}

		return criteria;
	}

	public List<Linedays> getLineDaysByLineId(Long productId) {
		Criteria<Linedays> criteria = new Criteria<Linedays>(Linedays.class);
		if (productId != null) {
			criteria.eq("lineId", productId);
		}
		return linedaysDao.findByCriteria(criteria);
	}
}
