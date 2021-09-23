package com.data.data.hmly.service.build.builder;

import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.data.data.hmly.service.common.ProductService;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.dao.SysSiteDao;
import com.data.data.hmly.service.dao.SysUnitDao;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.UnitType;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.zuipin.util.PropertiesManager;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/10/22.
 */
@Component
public class SupplierBuilder {

	private static final String SUPPLIER_HOME_TEMPLATE = "/supplier/home.ftl";
	private static final String SUPPLIER_HOME_TARGET = "/supplier/home{id}.htm";
	private static final String SUPPLIER_ABOUT_TEMPLATE = "/supplier/about.ftl";
	private static final String SUPPLIER_ABOUT_TARGET = "/supplier/about{id}.htm";
	private static final String SUPPLIER_CONTACT_TEMPLATE = "/supplier/contact.ftl";
	private static final String SUPPLIER_CONTACT_TARGET = "/supplier/contact{id}.htm";
	private static final String SUPPLIER_SEARCHER_TEMPLATE = "/supplier/searcher.ftl";
	private static final String SUPPLIER_SEARCHER_TARGET = "/supplier/%d/searcher.htm";

	private static final int PRODUCT_UP_NUMBER = 4;
	private static final String PRODUCT_UP_ORDER_TYPE = "updateTime";
	private static final int DISTRIBUTION_NUMBER = 7;
	private static final String DISTRIBUTION_ORDER_TYPE = "createTime";
	private static final int RECOMMEND_NUMBER = 6;
	private static final String RECOMMEND_ORDER_TYPE = "createTime";

	@Resource
	private SysUserService sysUserService;
	@Resource
	private ProductService productService;
	@Resource
	private SysUnitDao sysUnitDao;
	@Resource
	private SysSiteDao sysSiteDao;
	@Resource
	private PropertiesManager propertiesManager;

	public void build() {
		buildAll();

	}

	public void buildOne(Long id) {
		SysUnit sysUnit = sysUnitDao.load(id);
		SysUser sysUser = new SysUser();
		sysUser.setSysUnit(sysUnit);
		buildAbout(sysUser);
		buildContact(sysUser);
	}

	public void buildAll() {

		buildSearch();
		SysUnit unitCondition = new SysUnit();
		unitCondition.setUnitType(UnitType.company);

		SysUser condition = new SysUser();
		condition.setSysUnit(unitCondition);
//		List<SysUser> sysUserList = sysUserService.findAll(condition);
		Criteria<SysUnit> criteria = new Criteria<SysUnit>(SysUnit.class);
		criteria.eq("unitType", UnitType.company);
		List<SysUnit> sysUnitList = sysUnitDao.findByCriteria(criteria);
		buildDetail(sysUnitList);
		buildAbout(sysUnitList);
		buildContact(sysUnitList);
	}

	private void buildSearch() {
		List<SysSite> sites = sysSiteDao.findAll();
		for (SysSite sysSite : sites) {
			Map<Object, Object> data = new HashMap<Object, Object>();
			Criteria<SysUnit> c = new Criteria<SysUnit>(SysUnit.class);
			c.ne("unitType", UnitType.department);
			c.setProjection(Projections.distinct(Projections.groupProperty("area")));
			c.isNotNull("area");
			List<?> units = sysUnitDao.findByCriteria(c);
			if (!units.isEmpty()) {
				data.put("areas", units);
			}
			Criteria<SysUnit> criteria = new Criteria<SysUnit>(SysUnit.class);
			c.ne("unitType", UnitType.department);
			DetachedCriteria detail = criteria.createCriteria("sysUnitDetail", "detail", JoinType.INNER_JOIN);
			detail.setProjection(Projections.distinct(Projections.groupProperty("detail.supplierType")));

			List<?> supplierTypes = sysUnitDao.findByCriteria(criteria);
			if (!supplierTypes.isEmpty()) {
				data.put("supplierTypes", supplierTypes);
			}

			FreemarkerUtil.create(data, SUPPLIER_SEARCHER_TEMPLATE, String.format(SUPPLIER_SEARCHER_TARGET, sysSite.getId()));
		}

	}

	private void buildDetail(List<SysUnit> sysUnitList) {

		for (SysUnit sysUnit : sysUnitList) {
			SysUser sysUser = new SysUser();
			sysUser.setSysUnit(sysUnit);
			buildDetail(sysUser);
		}
	}

	private void buildDetail(SysUser sysUser) {

		SysUnit sysUnit = sysUser.getSysUnit();
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("sysUser", sysUser);
		data.put("sysUnit", sysUnit);

		Map<String, Object> lastUpProduct = getLastUpProduct(sysUser);
		data.put("lastUpProduct", lastUpProduct);

		Map<String, Object> distributionList = getLastDistributionList(sysUser);
		data.put("lastDistributionList", distributionList);

		Map<String, Object> recommendList = getRecommendProduct(sysUser);
		data.put("recommendList", recommendList);

		Map<String, Object> mostDistributionList = getMostDistributionList(sysUser);
		data.put("mostDistributionList", mostDistributionList);

		int distributorCount = countDistributor(sysUser);
		data.put("distributorCount", distributorCount);
		data.put("imguriPreffix", getImguriPreffix());

		FreemarkerUtil.create(data, SUPPLIER_HOME_TEMPLATE, SUPPLIER_HOME_TARGET.replace("{id}", sysUnit.getId().toString()));
	}

	public void buildAbout(List<SysUnit> list) {
		for (SysUnit sysUnit : list) {
			SysUser sysUser = new SysUser();
			sysUser.setSysUnit(sysUnit);
			buildAbout(sysUser);
		}
	}

	public void buildAbout(SysUser sysUser) {
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("sysUser", sysUser);
		SysUnit sysUnit = sysUser.getSysUnit();
		data.put("sysUnit", sysUnit);
		if (sysUnit.getSysUnitImages() == null || sysUnit.getSysUnitImages().isEmpty()) {
			Map<String, Object> lastUpProduct = getLastUpProduct(sysUser);
			data.put("lastUpProduct", lastUpProduct);
		}

		FreemarkerUtil.create(data, SUPPLIER_ABOUT_TEMPLATE, SUPPLIER_ABOUT_TARGET.replace("{id}", sysUser.getSysUnit().getId().toString()));
	}

	public void buildContact(List<SysUnit> list) {
		for (SysUnit sysUnit : list) {
			SysUser sysUser = new SysUser();
			sysUser.setSysUnit(sysUnit);
			buildContact(sysUser);
		}
	}

	public void buildContact(SysUser sysUser) {
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("sysUser", sysUser);
		data.put("sysUnit", sysUser.getSysUnit());

		FreemarkerUtil.create(data, SUPPLIER_CONTACT_TEMPLATE, SUPPLIER_CONTACT_TARGET.replace("{id}", sysUser.getSysUnit().getId().toString()));
	}

	private Map<String, Object> getLastUpProduct(SysUser user) {
		Map<String, Object> result = new HashMap<String, Object>();
		Product condition = new Product();
		condition.setUser(user);
		condition.setStatus(ProductStatus.UP);
		Page page = new Page(0, PRODUCT_UP_NUMBER);
		List<Product> list = productService.getProductList(condition, page, PRODUCT_UP_ORDER_TYPE);
		result.put("list", list);
		result.put("totalCount", page.getTotalCount());
		return result;
	}

	private Map<String, Object> getRecommendProduct(SysUser user) {
		Map<String, Object> result = new HashMap<String, Object>();
		Product condition = new Product();
		condition.setUser(user);
		condition.setStatus(ProductStatus.UP);
		// todo: 增加推荐依据
		Page page = new Page(0, RECOMMEND_NUMBER);
		List<Product> list = productService.getProductList(condition, page, RECOMMEND_ORDER_TYPE);
		result.put("list", list);
		result.put("totalCount", page.getTotalCount());
		return result;
	}

	private int countDistributor(SysUser sysUser) {
		Criteria<SysUser> criteria = new Criteria<SysUser>(SysUser.class);
		criteria.add(Restrictions.or(Restrictions.eq("parent.id", sysUser.getId()), Restrictions.eq("grand.id", sysUser.getId()),
				Restrictions.eq("superior.id", sysUser.getId())));
		Page page = new Page(0, 1);
		sysUserService.findUserList(sysUser, page, sysUser.getSysUnit().getSysSite(), false);
		return page.getTotalCount();
	}

	private Map<String, Object> getLastDistributionList(SysUser user) {
		Map<String, Object> result = new HashMap<String, Object>();
		Product condition = new Product();
		condition.setStatus(ProductStatus.UP);
		Product topProduct = new Product();
		topProduct.setUser(user);
		condition.setTopProduct(topProduct);
		Page page = new Page(0, DISTRIBUTION_NUMBER);
		List<Product> list = productService.getProductList(condition, page, DISTRIBUTION_ORDER_TYPE);
		result.put("list", list);
		result.put("totalCount", page.getTotalCount());
		return result;
	}

	private Map<String, Object> getMostDistributionList(SysUser user) {
		Map<String, Object> result = new HashMap<String, Object>();
		Product condition = new Product();
		condition.setStatus(ProductStatus.UP);
		Product topProduct = new Product();
		topProduct.setUser(user);
		condition.setTopProduct(topProduct);
		Page page = new Page(0, Integer.MAX_VALUE);
		List<Product> productList = productService.getProductList(condition, page, DISTRIBUTION_ORDER_TYPE);
		Map<Long, Product> topMap = new HashMap<Long, Product>();
		Map<Long, Integer> countMap = new HashMap<Long, Integer>();
		for (Product product : productList) {
			if (product.getId().equals(product.getTopProduct().getId())) {
				continue;
			}
			if (topMap.get(product.getTopProduct().getId()) == null) {
				topMap.put(product.getTopProduct().getId(), product.getTopProduct());
				countMap.put(product.getTopProduct().getId(), 1);
				continue;
			}
			countMap.put(product.getTopProduct().getId(), countMap.get(product.getTopProduct().getId()) + 1);
		}

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		for (Map.Entry<Long, Product> entry : topMap.entrySet()) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("count", countMap.get(entry.getKey()));
			map.put("product", entry.getValue());
			list.add(map);
		}
		result.put("list", list);
		result.put("totalCount", page.getTotalCount());
		return result;
	}

	public String getImguriPreffix() {
		return propertiesManager.getString("mall.imguri.preffix");
	}

}
