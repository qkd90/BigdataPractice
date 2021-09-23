package com.data.data.hmly.service.handdrawing;

import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.handdrawing.dao.HandDrawMapDao;
import com.data.data.hmly.service.handdrawing.dao.HandDrawScenicDao;
import com.data.data.hmly.service.handdrawing.entity.HandDrawMap;
import com.data.data.hmly.service.handdrawing.entity.HandDrawScenic;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by guoshijie on 2015/12/10.
 */
@Service
public class HandDrawService {

	@Resource
	private HandDrawMapDao handDrawMapDao;
	@Resource
	private HandDrawScenicDao handDrawScenicDao;

	public List<HandDrawMap> getByCity(Long cityId) {
		TbArea tbArea = new TbArea();
		tbArea.setId(cityId);
		HandDrawMap handDrawMap = new HandDrawMap();
		handDrawMap.setCity(tbArea);
		return list(handDrawMap, new Page(0, Integer.MAX_VALUE));
	}

	public List<HandDrawMap> listAll() {
		return list(new HandDrawMap(), null);
	}

	public List<HandDrawMap> list(HandDrawMap handDrawMap, Page page, String... orderProperties) {
		Criteria<HandDrawMap> criteria = createCriteria(handDrawMap, orderProperties);
		if (page == null) {
			return handDrawMapDao.findByCriteria(criteria);
		}
		return handDrawMapDao.findByCriteria(criteria, page);
	}

	public Criteria<HandDrawMap> createCriteria(HandDrawMap handDrawMap, String... orderProperties) {
		Criteria<HandDrawMap> criteria = new Criteria<HandDrawMap>(HandDrawMap.class);
		if (orderProperties.length == 2) {
			criteria.orderBy(orderProperties[0], orderProperties[1]);
		} else if (orderProperties.length == 1) {
			criteria.orderBy(Order.desc(orderProperties[0]));
		}
		if (handDrawMap.getLevel() != null) {
			criteria.eq("level", handDrawMap.getLevel());
		}
		if (handDrawMap.getCity() != null) {
			criteria.eq("city.id", handDrawMap.getCity().getId());
		}
		return criteria;
	}

	public List<HandDrawScenic> listScenicByCity(Long cityId, int level) {
		HandDrawScenic handDrawScenic = new HandDrawScenic();
		TbArea tbArea = new TbArea();
		tbArea.setId(cityId);
		handDrawScenic.setCity(tbArea);
		handDrawScenic.setZoomLevel(level);
		return list(handDrawScenic, new Page(0, Integer.MAX_VALUE));
	}

	public List<HandDrawScenic> listScenicByMap(Long mapId) {
		HandDrawScenic handDrawScenic = new HandDrawScenic();
		HandDrawMap handDrawMap = new HandDrawMap();
		handDrawMap.setId(mapId);
		handDrawScenic.setHandDrawMap(handDrawMap);
		return list(handDrawScenic, new Page(0, Integer.MAX_VALUE));
	}

	public List<HandDrawScenic> list(HandDrawScenic handDrawScenic, Page page, String... orderProperties) {
		Criteria<HandDrawScenic> criteria = createCriteria(handDrawScenic, orderProperties);
		if (page == null) {
			return handDrawScenicDao.findByCriteria(criteria);
		}
		return handDrawScenicDao.findByCriteria(criteria, page);
	}

	public Criteria<HandDrawScenic> createCriteria(HandDrawScenic handDrawScenic, String... orderProperties) {
		Criteria<HandDrawScenic> criteria = new Criteria<HandDrawScenic>(HandDrawScenic.class);
		if (orderProperties.length == 2) {
			criteria.orderBy(orderProperties[0], orderProperties[1]);
		} else if (orderProperties.length == 1) {
			criteria.orderBy(Order.desc(orderProperties[0]));
		}
		if (handDrawScenic.getHandDrawMap() != null) {
			criteria.eq("handDrawMap.id", handDrawScenic.getHandDrawMap().getId());
		}
		if (handDrawScenic.getZoomLevel() != null) {
			criteria.eq("zoomLevel", handDrawScenic.getZoomLevel());
		}
		if (handDrawScenic.getCity() != null) {
			criteria.eq("city.id", handDrawScenic.getCity().getId());
		}
		return criteria;
	}
}
