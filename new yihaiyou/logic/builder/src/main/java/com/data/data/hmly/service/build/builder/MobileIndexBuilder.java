package com.data.data.hmly.service.build.builder;

import com.data.data.hmly.service.AdsService;
import com.data.data.hmly.service.SysResourceMapService;
import com.data.data.hmly.service.SysUnitService;
import com.data.data.hmly.service.area.SupplierCityService;
import com.data.data.hmly.service.area.entity.SupplierCity;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.SysResourceMap;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.build.util.FreemarkerUtil;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/11/26.
 */
@Component
public class MobileIndexBuilder {

	private static final String INDEX_LINE_TEMPLATE = "/mobile/index.ftl";
	private static final String INDEX_LINE_TARGET = "/mobile/%d/index.htm";

	private static final String TOP_SCROLL_BANNER = "mobile_top_roll_banner";
	private static final int TOP_LINE_NUMBER = 6;

	@Resource
	private SysUnitService sysUnitService;
	@Resource
	private SysResourceMapService sysResourceMapService;
	@Resource
	private AdsService adsService;
	@Resource
	private LineService lineService;
	@Resource
	private SupplierCityService supplierCityService;

	public void build() {
		List<SysUnit> supplierList = sysUnitService.listAllUnit();
		for (SysUnit supplier : supplierList) {
			buildIndex(supplier);
		}
	}

	public void buildIndex(SysUnit supplier) {
		List<Ads> adsList = listTopAds(supplier);
		List<Line> lineList = listTopLines(supplier);
		List<SupplierCity> cityList = listSupplierCity(supplier.getId());
		Map<Object, Object> data = new HashMap<Object, Object>();
		data.put("adsList", adsList);
		data.put("lineList", lineList);
		data.put("cityList", cityList);
		data.put("supplierId", supplier.getId());
		FreemarkerUtil.create(data, INDEX_LINE_TEMPLATE, String.format(INDEX_LINE_TARGET, supplier.getId()));
	}


	public List<Ads> listTopAds(SysUnit supplier) {
		List<SysResourceMap> resourceList = sysResourceMapService.getByDesc(TOP_SCROLL_BANNER);
		List<Ads> topAds = new ArrayList<Ads>();
		//todo: should add sys_site as query condition
		SysUser user = new SysUser();
		user.setSysUnit(supplier);
		for (SysResourceMap sysResourceMap : resourceList) {
			Ads condition = new Ads();
			condition.setSysResourceMap(sysResourceMap);
						condition.setUser(user);
			List<Ads> list = adsService.getAdsList(condition, new Page(0, Integer.MAX_VALUE));
			topAds.addAll(list);
		}
		return topAds;
	}

	public List<Line> listTopLines(SysUnit supplier) {
		return lineService.findBySupplier(new Page(0, TOP_LINE_NUMBER), supplier);
	}

	public List<SupplierCity> listSupplierCity(Long supplierId) {
		SysUnit sysUnit = new SysUnit();
		sysUnit.setId(supplierId);
		SupplierCity condition = new SupplierCity();
		condition.setSupplier(sysUnit);
		return supplierCityService.list(condition, true, null);
	}
}
