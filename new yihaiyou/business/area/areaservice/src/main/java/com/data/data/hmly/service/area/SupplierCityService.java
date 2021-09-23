package com.data.data.hmly.service.area;

import com.data.data.hmly.service.area.dao.SupplierCityDao;
import com.data.data.hmly.service.area.entity.SupplierCity;
import com.data.data.hmly.service.entity.SysUnit;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by guoshijie on 2015/11/27.
 */
@Service
public class SupplierCityService {

	@Resource
	private SupplierCityDao supplierCityDao;

	public void save(List<SupplierCity> list) {
		supplierCityDao.save(list);
	}

	public List<SupplierCity> listBySupplier(SysUnit sysUnit) {
		Criteria<SupplierCity> criteria = new Criteria<SupplierCity>(SupplierCity.class);
		criteria.eq("supplier.id", sysUnit.getId());
		return supplierCityDao.findByCriteria(criteria);
//		return supplierCityDao.findByHQL("from SupplierCity where supplier.id=29");
	}

	public List<SupplierCity> list(SupplierCity supplierCity, Boolean selected, Boolean recommended) {
		Criteria<SupplierCity> criteria = new Criteria<SupplierCity>(SupplierCity.class);
		if (supplierCity == null) {
			return supplierCityDao.findByCriteria(criteria);
		}
		if (supplierCity.getSupplier() != null) {
			criteria.eq("supplier.id", supplierCity.getSupplier().getId());
		}
		if (supplierCity.getCity() != null) {
			criteria.eq("city.id", supplierCity.getCity().getId());
		}
		if (selected != null) {
			criteria.eq("selected", selected);
		}
		if (recommended != null) {
			criteria.eq("recommended", recommended);
		}
		return supplierCityDao.findByCriteria(criteria);
	}

}
