package com.data.data.hmly.service.mobile;

import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.LineexplainService;
import com.data.data.hmly.service.line.LinetypepricedateService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linedays;
import com.data.data.hmly.service.line.entity.Linedaysplan;
import com.data.data.hmly.service.line.entity.Lineexplain;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.data.data.hmly.service.mobile.request.LineSearchRequest;
import com.framework.hibernate.util.Page;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/11/19.
 */
@Service
public class MobileLineService {

	private final Logger logger = Logger.getLogger(MobileLineService.class);

	private static final int TOP_LINE_NUMBER = 6;

	@Resource
	private LineService lineService;
	@Resource
	private LineexplainService lineexplainService;
	@Resource
	private LinetypepricedateService linetypepricedateService;
	@Resource
	private ScenicInfoService scenicInfoService;

	public Line getLine(Long id) {
		Line line = lineService.loadLine(id);
		//todo: ugly, try to fill scenic info in more efficient way
		if (line.getLineexplain() == null) {
			Lineexplain lineexplain = lineexplainService.getByLine(id);
			if (lineexplain == null) {
				return line;
			}
			line.setLineexplain(lineexplain);
		}
		for (Linedays linedays : line.getLineexplain().getLinedays()) {
			if (linedays.getDayPlan() == null || linedays.getDayPlan().isEmpty()) {
				continue;
			}
			for (Linedaysplan linedaysplan : linedays.getDayPlan()) {
				if ("scenic".equals(linedaysplan.getType())) {
					ScenicInfo scenicInfo = scenicInfoService.get(linedaysplan.getTypeId());
					linedaysplan.setScenicInfo(scenicInfo);
				}
			}
		}
		return line;
	}

	public List<Linetypepricedate> getDate(Long priceTypeId) {
		Linetypeprice linetypeprice = new Linetypeprice();
		linetypeprice.setId(priceTypeId);
		return linetypepricedateService.findByType(linetypeprice);
	}

	//推荐行程
	public Map<String, Object> listLine(LineSearchRequest request) {

		Map<String, Object> result = new HashMap<String, Object>();
		String searchCondition = buildSearchCondition(request);

		try {
			QueryResponse response = lineService.findLine(request.page(), searchCondition, request.orderColumn, request.orderType);
			SolrDocumentList docs = response.getResults();
			result.put("data", docs);
			result.put("totalCount", docs.getNumFound());
		} catch (UnsupportedEncodingException e) {
			logger.error("solr获取线路失败，编码错误", e);
		}

		return result;
	}

	private String buildSearchCondition(LineSearchRequest request) {
		StringBuilder builder = new StringBuilder();
		builder.append("type : line ");
		if (StringUtils.isNotBlank(request.cityNames)) {
			if (!request.cityNames.contains("特别行政区") && !request.cityNames.endsWith("市")) {
				request.cityNames += "市";
			}
			builder.append(" AND crossCitys : ").append(URLDecoder.decode(request.cityNames));
		}
		if (StringUtils.isNotBlank(request.lowestPrice) && StringUtils.isNotBlank(request.highestPrice)) {
			builder.append(String.format(" AND disCountPrice : [%s TO %s]", request.lowestPrice, request.highestPrice));
		} else if (StringUtils.isNotBlank(request.lowestPrice)) {
			builder.append(String.format(" AND disCountPrice : [%s TO %s]", request.lowestPrice, Integer.MAX_VALUE));
		} else if (StringUtils.isNotBlank(request.highestPrice)) {
			builder.append(String.format(" AND disCountPrice : [%s TO %s]", 0, request.highestPrice));
		}
		if (StringUtils.isNotBlank(request.lowestDay) && StringUtils.isNotBlank(request.highestDay)) {
			builder.append(String.format(" AND lineDay : [%s TO %s]", request.lowestDay, request.highestDay));
		} else if (StringUtils.isNotBlank(request.lowestDay)) {
			builder.append(String.format(" AND lineDay : [%s TO %s]", request.lowestDay, Integer.MAX_VALUE));
		} else if (StringUtils.isNotBlank(request.highestDay)) {
			builder.append(String.format(" AND lineDay : [%s TO %s]", 0, request.highestDay));
		}
		if (StringUtils.isNotBlank(request.planName)) {
			builder.append(" AND name : ").append(URLDecoder.decode(request.planName));
		}
		return builder.toString();
	}

	public List<Line> listTopLines() {
		Line lineCondition = new Line();
		SysUser userCondition = new SysUser();
		SysUnit companyUnit = new SysUnit();
		companyUnit.setId(1l);
		SysUnit sysUnit = new SysUnit();
		sysUnit.setCompanyUnit(companyUnit);
		SysSite sysSite = new SysSite();
		sysSite.setId(1l);
		userCondition.setSysUnit(sysUnit);
		userCondition.setSysSite(sysSite);
		return lineService.findLineList(lineCondition, new Page(0, TOP_LINE_NUMBER), userCondition, false, false);
	}
}
