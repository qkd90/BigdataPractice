package com.data.data.hmly.action.mobile;

import com.data.data.hmly.service.area.entity.SupplierCity;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.line.entity.Line;
import com.opensymphony.xwork2.Result;
import com.zuipin.action.JxmallAction;
import com.zuipin.util.FileUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by guoshijie on 2015/11/19.
 */
public class IndexMobileAction extends JxmallAction {

	private List<Ads> adsList = new ArrayList<Ads>();
	private List<Line> lineList = new ArrayList<Line>();
	private List<SupplierCity> cityList;
	private Long supplierId;

	public Result index() {
		supplierId = 1l;
		setAttribute(WeixinConstants.MOBILE_INDEX_KEY, FileUtil.loadHTML(String.format(WeixinConstants.MOBILE_INDEX, supplierId)));
		return dispatch();
	}

	public List<Ads> getAdsList() {
		return adsList;
	}

	public void setAdsList(List<Ads> adsList) {
		this.adsList = adsList;
	}

	public List<Line> getLineList() {
		return lineList;
	}

	public void setLineList(List<Line> lineList) {
		this.lineList = lineList;
	}

	public List<SupplierCity> getCityList() {
		return cityList;
	}

	public void setCityList(List<SupplierCity> cityList) {
		this.cityList = cityList;
	}

	public Long getSupplierId() {
		return supplierId;
	}

	public void setSupplierId(Long supplierId) {
		this.supplierId = supplierId;
	}
}
