package com.data.data.hmly.action.mall;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;

import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.LinetypepriceService;
import com.data.data.hmly.service.line.LinetypepricedateService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.data.data.hmly.service.line.entity.enums.LineStatus;
import com.framework.hibernate.util.Page;
import com.framework.struts.AjaxCheck;
import com.opensymphony.xwork2.Result;
import com.zuipin.util.DateUtils;
import com.zuipin.util.FileUtil;
import com.zuipin.util.JsonFilter;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.StringUtils;

/**
 * Created by guoshijie on 2015/10/21.
 */
public class LineWebAction extends MallAction {
	private static final long serialVersionUID = -7017066781346847505L;
	@Resource
	private LineService lineService;
	@Resource
	private LinetypepriceService linetypepriceService;
	@Resource
	private LinetypepricedateService linetypepricedateService;
	@Resource
	private PropertiesManager propertiesManager;

	// 返回数据
	private String id;
	private Line lineDisplay;
	private List<Linetypeprice> linetypepricesDisplay;
	Map<String, Object> map = new HashMap<String, Object>();
	private Linetypeprice priceDateCondition = new Linetypeprice();
	private String conditionStr;
	private int pageIndex = 0;
	private String lineName;
	private String city;
	private String productAttr;
	private String lineDay;
	private String supplierName;
	private String cost;
	private String mincost;
	private String maxcost;
    private String supplierId;

	public Result list() throws UnsupportedEncodingException {
		getLineSearcher();
		Page page = new Page(pageIndex + 1, 10);
		makeSearchCondition();
		// if (StringUtils.isBlank(conditionStr)) {
		// }

		QueryResponse results = lineService.findLine(page, conditionStr, getMallConfig().getSiteId());
		SolrDocumentList docs = results.getResults();
		setAttribute("lines", docs);
		setAttribute("pageIndex", pageIndex);
		setAttribute("totalCount", docs.getNumFound());
		return dispatch();
	}

	@AjaxCheck
	public Result searchLine() throws UnsupportedEncodingException {
		Page page = new Page(pageIndex + 1, 10);
		if (StringUtils.isBlank(conditionStr)) {
			makeSearchCondition();
		}
		QueryResponse results = lineService.findLine(page, conditionStr, getMallConfig().getSiteId());
		SolrDocumentList docs = results.getResults();
		page.setTotalCount((int) docs.getNumFound());
		JSONObject o = new JSONObject();
		o.put("lines", docs);
		o.put("page", page);
		return json(o);
	}

	private void makeSearchCondition() throws UnsupportedEncodingException {
		StringBuilder sb = new StringBuilder();
        if (StringUtils.isNotBlank(id)) {
            sb.append(String.format(" AND supplierId : \"%s\"", id));
        }
		if (StringUtils.isNotBlank(city)) {
			sb.append(String.format(" AND crossCitys : \"%s\"", URLDecoder.decode(city)));
		}
		if (StringUtils.isNotBlank(this.cost)) {
			sb.append(String.format(" AND disCountPrice : [%s]", this.cost));
		}
		if (StringUtils.isNotBlank(this.maxcost)) {
			sb.append(String.format(" AND disCountPrice : [%s TO %s]", this.mincost, this.maxcost));
		}
		if (StringUtils.isNotBlank(this.lineDay)) {
			lineDay = URLDecoder.decode(lineDay);
			if (lineDay.contains("天")) {
				sb.append(String.format(" AND lineDay : %s", this.lineDay.replace("天", "")));
			} else if (lineDay.contains("以上")) {
				sb.append(String.format(" AND lineDay : [%s TO 999]", this.lineDay.replace("以上", "")));

			}
		}
		if (StringUtils.isNotBlank(this.lineName)) {
			sb.append(String.format(" AND name : %s", URLDecoder.decode(lineName)));
		}
		if (StringUtils.isNotBlank(this.productAttr)) {
			sb.append(String.format(" AND productAttr : %s", URLDecoder.decode(productAttr)));
		}
		if (StringUtils.isNotBlank(this.supplierName)) {
			sb.append(String.format(" AND supplierName : %s", URLDecoder.decode(supplierName)));
		}
		this.conditionStr = sb.toString();
	}

	public Result detail() {
		if (id == null) {
			return redirect("/");
		}

		setAttribute(MallConstants.LINE_DETAIL_KEY, FileUtil.loadHTML(MallConstants.LINE_DETAIL + id));
		return dispatch();
	}

	public void getLineSearcher() {
        String ss = FileUtil.loadHTML(String.format(MallConstants.LINE_SEARCHER, getMallConfig().getSiteId()));
		setAttribute(MallConstants.LINE_SEARCHER_KEY,
				FileUtil.loadHTML(String.format(MallConstants.LINE_SEARCHER, getMallConfig().getSiteId())));
	}

	/**
	 * 转向分销页面
	 * 
	 * @author caiys
	 * @date 2015年11月3日 上午11:44:58
	 * @return
	 */
	public Result agent() {
		String productId = (String) getParameter("productId");
		lineDisplay = lineService.loadLine(Long.valueOf(productId));
		Linetypeprice linetypeprice = new Linetypeprice();
		linetypeprice.setLine(lineDisplay);
		linetypepricesDisplay = linetypepriceService.findLinetypepriceList(linetypeprice);
		return dispatch();
	}

	/**
	 * 获取类别报价中最小优惠价，日期范围第二天和下个月最后一天
	 * 
	 * @author caiys
	 * @date 2015年10月21日 下午3:36:41
	 * @return
	 */
	@AjaxCheck
	public Result findMinValue() {
		String linetypepriceId = (String) getParameter("linetypepriceId");
		String prop = (String) getParameter("prop");
		// 起始时间 - 当前时间第二天
		Date dateStart = DateUtils.add(new Date(), Calendar.DAY_OF_MONTH, 1);
		String dateStartStr = DateUtils.format(dateStart, "yyyy-MM-dd");
		dateStart = DateUtils.getDate(dateStartStr, "yyyy-MM-dd"); // 除去时分秒
		// 结束时间 - 当前时间次月最后一天
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateStart);
		calendar.set(Calendar.DAY_OF_MONTH, 1);
		calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) + 2);
		calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) - 1);
		Date dateEnd = calendar.getTime();
		Float propValue = linetypepriceService.findMinValue(Long.valueOf(linetypepriceId), dateStart, dateEnd, prop);
		map.put("propValue", propValue);
		JSONObject.fromObject(map);
		return json(JSONObject.fromObject(map));
	}

	/**
	 * 分销
	 * 
	 * @author caiys
	 * @date 2015年10月21日 下午3:36:41
	 * @return
	 */
	@AjaxCheck
	public Result doAgent() {
		String productId = (String) getParameter("productId");
		String lineStatus = (String) getParameter("lineStatus");
		String lineName = (String) getParameter("lineName");
		if (getLoginUser() != null) {
			if (getLoginUser() instanceof SysUser) {
				String staticPath = propertiesManager.getString("IMG_DIR");
//				map = lineService.doAgentLine(Long.valueOf(productId), lineName, (SysUser)getLoginUser(), LineStatus.valueOf(lineStatus), staticPath);
			} else {
				map.put("success", false);
				map.put("errorMsg", "用户类型不正确");
			}
		} else {
			map.put("success", false);
			map.put("errorMsg", "用户未登录");
		}
		JSONObject.fromObject(map);
		return json(JSONObject.fromObject(map));
	}

	@AjaxCheck
	public Result getPriceList() {
		List<Linetypepricedate> list = linetypepricedateService.findAvailableByType(priceDateCondition);
		JsonConfig jsonConfig = JsonFilter.getFilterConfig("linetypepricedate", "line");
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
		String startDate = simpleDateFormat.format(list.get(0).getDay());
		Map<String, Object> result = new HashMap<String, Object>();
		result.put("startDate", startDate);
		result.put("priceList", list);
		result.put("success", true);
		return json(JSONObject.fromObject(result, jsonConfig));
	}

	public Result buildIndexs() {
		lineService.doIndexLines();
		return text("success");
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Line getLineDisplay() {
		return lineDisplay;
	}

	public void setLineDisplay(Line lineDisplay) {
		this.lineDisplay = lineDisplay;
	}

	public List<Linetypeprice> getLinetypepricesDisplay() {
		return linetypepricesDisplay;
	}

	public void setLinetypepricesDisplay(List<Linetypeprice> linetypepricesDisplay) {
		this.linetypepricesDisplay = linetypepricesDisplay;
	}

	public Linetypeprice getPriceDateCondition() {
		return priceDateCondition;
	}

	public void setPriceDateCondition(Linetypeprice priceDateCondition) {
		this.priceDateCondition = priceDateCondition;
	}

	public String getConditionStr() {
		return conditionStr;
	}

	public void setConditionStr(String conditionStr) {
		this.conditionStr = conditionStr;
	}

	public int getPageIndex() {
		return pageIndex;
	}

	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}

	public String getLineName() {
		return lineName;
	}

	public void setLineName(String lineName) {
		this.lineName = lineName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getProductAttr() {
		return productAttr;
	}

	public void setProductAttr(String productAttr) {
		this.productAttr = productAttr;
	}

	public String getLineDay() {
		return lineDay;
	}

	public void setLineDay(String lineDay) {
		this.lineDay = lineDay;
	}

	public String getSupplierName() {
		return supplierName;
	}

	public void setSupplierName(String supplierName) {
		this.supplierName = supplierName;
	}

	public String getCost() {
		return cost;
	}

	public void setCost(String cost) {
		this.cost = cost;
	}

	public String getMincost() {
		return mincost;
	}

	public void setMincost(String mincost) {
		this.mincost = mincost;
	}

	public String getMaxcost() {
		return maxcost;
	}

	public void setMaxcost(String maxcost) {
		this.maxcost = maxcost;
	}

    public String getSupplierId() {
        return supplierId;
    }

    public void setSupplierId(String supplierId) {
        this.supplierId = supplierId;
    }
}
