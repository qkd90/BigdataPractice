package com.data.data.hmly.action.region;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.commons.lang.StringUtils;

import com.data.data.hmly.action.FrameBaseAction;
import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.area.AreaService;
import com.data.data.hmly.service.area.entity.SupplierCity;
import com.data.data.hmly.service.entity.Label;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.entity.UserType;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.Result;

/**
 * Created by guoshijie on 2015/11/26.
 */
public class AreaAction extends FrameBaseAction{

	@Resource
	private AreaService areaService;

	private List<TbArea> areaList;
	private List<Long> selectedCities;
	@Resource
	private LabelItemService labelItemService;
	@Resource
	private LabelService labelService;
	private List<SupplierCity> supplierCityList = new ArrayList<SupplierCity>();
	private List<Map<String,Object>> supplierCityMapList;
	private Integer			page				= 1;
	private Integer			rows				= 10;
	
	public Result getAreaListByLabel() {
		Page pageInfo = new Page(page,rows);
		String name = (String) getParameter("name");
		String cityId = (String) getParameter("cityId");
		String labelId = (String) getParameter("labelId");
		String tagIds = (String) getParameter("tagIds");
		TbArea area = null;
		TbArea info = null;
		if(StringUtils.isNotBlank(cityId)){
			area = areaService.get(Long.parseLong(cityId));
		}
		if(StringUtils.isNotBlank(name)){
			info = new TbArea();
			info.setName(name);
		}
		
		
		List<TbArea> areaList = areaService.getAreaLabels(info,area,tagIds,pageInfo);
		
		List<AreaLabel> areaLabels = new ArrayList<AreaLabel>();
		for(TbArea sInfo:areaList){
			AreaLabel slabel = new AreaLabel();
			
			slabel.setId(sInfo.getId());
			if(sInfo.getId()==110100L||sInfo.getId()==120100L||sInfo.getId()==310100L||sInfo.getId()==500100L){
				slabel.setName(sInfo.getFullPath());
			}else{
				slabel.setName(sInfo.getName());
			}
			slabel.setCityId(sInfo.getId());
			slabel.setCityName(sInfo.getFullPath());
			
			List<LabelItem> items = labelItemService.findItemByTargId(sInfo.getId(),TargetType.CITY);
			
			List<String> labelNames = new ArrayList<String>();
			List<Integer> itemSorts = new ArrayList<Integer>();
			List<Long> itemIds = new ArrayList<Long>();
			List<Long> labIds = new ArrayList<Long>();
			for(LabelItem it:items){
				if((it.getTargetId()).equals(sInfo.getId())){
					if(StringUtils.isNotBlank(labelId)){
						Long lId = Long.parseLong(labelId);
						List<Label> labels = labelService.findLabelsByParent(lId);
						if(labels.size()>0){
							for(Label la:labels){
								if((la.getId()).equals(it.getLabel().getId())){
									slabel.setSort(it.getOrder());
									itemSorts.add(it.getOrder());
									labelNames.add(it.getLabel().getName());
									itemIds.add(it.getId());
									labIds.add(it.getLabel().getId());
								}
							}
						}else{
							if((lId).equals(it.getLabel().getId())){
								slabel.setSort(it.getOrder());
								itemSorts.add(it.getOrder());
								labelNames.add(it.getLabel().getName());
								itemIds.add(it.getId());
								labIds.add(it.getLabel().getId());
							}
						}
						
					}else{
						slabel.setSort(it.getOrder());
						itemSorts.add(it.getOrder());
						labelNames.add(it.getLabel().getName());
						itemIds.add(it.getId());
						labIds.add(it.getLabel().getId());
					}
				}
				
			}
			
			slabel.setLabelNames(labelNames);
			slabel.setItemSort(itemSorts);
			slabel.setLabelItems(itemIds);
			slabel.setLabelIds(labIds);
			
			
			areaLabels.add(slabel);
		}
		return datagrid(areaLabels, pageInfo.getTotalCount());
		
		
	}
	
	public Result index() {
		areaList = areaService.listAllArea();
		return dispatch();
	}

	public Result selectRecommendCity() {
		areaService.doRecommendCities(selectedCities);
		return redirect("/area/area/index.jhtml");
	}

	public Result supplier() {
		SysUser sysUser = getLoginUser();
		if (sysUser.getUserType() != UserType.CompanyManage) {
			return text("权限错误，请联系公司管理员");
		}
		supplierCityList = areaService.listSupplierCity(sysUser.getSysUnit());
		areaList = areaService.listAllArea();
		supplierCityMapList = new ArrayList<Map<String, Object>>();
		Map<Long, SupplierCity> map = Maps.uniqueIndex(supplierCityList, new Function<SupplierCity, Long>() {
			@Override
			public Long apply(SupplierCity supplierCity) {
				return supplierCity.getCity().getId();
			}
		});
		for (TbArea tbArea : areaList) {
			tbArea.getExtension().put("supplierCity", map.get(tbArea.getId()));
			for (TbArea child : tbArea.getChilds()) {
				child.getExtension().put("supplierCity", map.get(child.getId()));
			}
		}
		return dispatch();
	}

	public Result selectSupplierCity() {
		SysUser sysUser = getLoginUser();
		for (SupplierCity supplierCity : supplierCityList) {
			supplierCity.setSupplier(sysUser.getSysUnit());
			TbArea tbArea = new TbArea();
			tbArea.setId(supplierCity.getCity().getId());
			supplierCity.setCity(tbArea);
		}
		areaService.saveSupplierCity(supplierCityList);
		return text("success");
	}

	public List<TbArea> getAreaList() {
		return areaList;
	}

	public void setAreaList(List<TbArea> areaList) {
		this.areaList = areaList;
	}

	public List<Long> getSelectedCities() {
		return selectedCities;
	}

	public void setSelectedCities(List<Long> selectedCities) {
		this.selectedCities = selectedCities;
	}

	public List<SupplierCity> getSupplierCityList() {
		return supplierCityList;
	}

	public void setSupplierCityList(List<SupplierCity> supplierCityList) {
		this.supplierCityList = supplierCityList;
	}

	public List<Map<String, Object>> getSupplierCityMapList() {
		return supplierCityMapList;
	}

	public void setSupplierCityMapList(List<Map<String, Object>> supplierCityMapList) {
		this.supplierCityMapList = supplierCityMapList;
	}

	public Integer getPage() {
		return page;
	}

	public void setPage(Integer page) {
		this.page = page;
	}

	public Integer getRows() {
		return rows;
	}

	public void setRows(Integer rows) {
		this.rows = rows;
	}
}
