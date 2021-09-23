package com.data.data.hmly.service.line;


import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.LabelService;
import com.data.data.hmly.service.SysUserService;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.common.dao.ProductimageDao;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.dao.TbAreaDao;
import com.data.data.hmly.service.entity.*;
import com.data.data.hmly.service.line.dao.*;
import com.data.data.hmly.service.line.entity.*;
import com.data.data.hmly.service.line.entity.enums.Buypay;
import com.data.data.hmly.service.line.entity.enums.LineImageType;
import com.data.data.hmly.service.line.entity.enums.LineStatus;
import com.data.data.hmly.service.line.entity.enums.ProductAttr;
import com.data.data.hmly.service.line.vo.LineSolrEntity;
import com.data.data.hmly.util.Clock;
import com.data.data.solr.MulticoreSolrTemplate;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.zuipin.util.DateUtils;
import com.zuipin.util.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrQuery.ORDER;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrInputDocument;
import org.hibernate.criterion.*;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.util.*;

@Service
public class LineService extends SolrIndexService<Line, LineSolrEntity> {

	Logger logger = Logger.getLogger(LineService.class);

	@Resource
	private LineDao dao;
	@Resource
	private LinetypepriceDao linetypepriceDao;
	@Resource
	private LinetypepriceAgentDao linetypepriceAgentDao;
	@Resource
	private LinetypepricedateDao linetypepricedateDao;
	@Resource
	private LineexplainDao lineexplainDao;
	@Resource
	private LineplaytitleDao lineplaytitleDao;
	@Resource
	private LinedaysDao linedaysDao;
	@Resource
	private LinedaysplanDao linedaysplanDao;
	@Resource
	private ProductimageDao productimageDao;
	@Resource
	private LineacrosscitysDao lineacrosscitysDao;
	@Resource
	private ProductimageService productimageService;
	@Resource
	private MulticoreSolrTemplate solrTemplate;
	@Resource
	private LabelService labelService;

	@Resource
	private LabelItemService labelItemService;

	@Resource
	private TbAreaService tbAreaService;
	@Resource
	private LinetypepricedateService linetypepricedateService;
	@Resource
	private LineImagesService lineImagesService;
	@Resource
	private LineexplainService lineexplainService;
	@Resource
	private LinedaysService linedaysService;
	@Resource
	private LinedaysplanService linedaysplanService;
	@Resource
	private LineDaysPlanInfoService lineDaysPlanInfoService;
	@Resource
	private LinetypepriceService linetypepriceService;

	@Resource
	private SysUserService sysUserService;

	@Resource
	private TbAreaDao areaDao;

	private final static String coreName = "products";
	private final static DecimalFormat df = new DecimalFormat(".##");


	public List<Line> getLineLabels(Line info, TbArea area,
									String tagIds, Page pageInfo) {
		List<Line> infos = new ArrayList<Line>();

		Criteria<Line> criteria = new Criteria<Line>(Line.class);

		if (info != null && info.getName() != null) {
			criteria.like("name", "%" + info.getName() + "%");
		}
		if (area != null) {
			if (area.getLevel() == 0) {
				List<TbArea> areaLevel1 = area.getChilds();
				List<Long> aids = new ArrayList<Long>();
				for (int i = 0; i < areaLevel1.size(); i++) {
					aids.add(areaLevel1.get(i).getId());
					List<TbArea> areaLevel2 = areaLevel1.get(i).getChilds();
					for (int j = 0; j < areaLevel2.size(); j++) {
						aids.add(areaLevel2.get(j).getId());
					}
				}
				if (aids.isEmpty()) {
					return infos;
				}
				criteria.in("startCityId", aids);
			} else if (area.getLevel() == 1) {
				List<TbArea> areas = area.getChilds();
				Long[] aIds = new Long[areas.size()];
				for (int i = 0; i < areas.size(); i++) {
					aIds[i] = areas.get(i).getId();
				}
				if (aIds.length <= 0) {
					return infos;
				}
				criteria.in("startCityId", aIds);
			} else if (area.getLevel() == 2) {
				criteria.eq("startCityId", area.getId());
			}
		}

		if (info.getStatus() != null) {
			criteria.eq("status", info.getStatus());
		}

		if (info.getLineStatus() != null) {
			criteria.eq("lineStatus", info.getLineStatus());
		}

		if (StringUtils.isNotBlank(tagIds)) {
			String[] tIdStrs = tagIds.split(",");
			Long[] ids = new Long[tIdStrs.length];
			for (int i = 0; i < tIdStrs.length; i++) {
				ids[i] = Long.parseLong(tIdStrs[i]);
			}
			criteria.in("id", ids);
			infos =  dao.findByCriteria(criteria, pageInfo);
		} else if (tagIds == null) {
			infos = dao.findByCriteria(criteria, pageInfo);
		}
		return infos;
	}


	public Boolean checkAgentLine(Line line, SysUser sysUser) {

		List<Line> lineList = dao.findLineList(line, sysUser);

		if (lineList.size() > 0) {
			return false;
		} else {
			return true;
		}


	}

	public void update(Line line) {
		dao.update(line);
	}

	public List<Line> findAgentLineList(Line line, Page page, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {
		return dao.findAgentLineList(line, page, sysUser, isSiteAdmin, isSupperAdmin);
	}


	public List<Line> findProLineList(Line line, Page page) {
		return dao.findProLineList(line, page);
	}


	/**
	 * 批量获取线路
	 * @param lineStrArr
	 * @return
	 */
	public List<Line> loadLineList(String[] lineStrArr) {
		List<Line> lineList = new ArrayList<Line>();
		for (String lineIdStr : lineStrArr) {
			lineList.add(dao.load(Long.parseLong(lineIdStr)));
		}
		return lineList;
	}

	/**
	 * 查询线路列表
	 *
	 * @author caiys
	 * @date 2015年10月16日 上午9:29:32
	 * @param line
	 * @return
	 */
	public List<Line> findLineList(Line line, Page page, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {
		return dao.findLineListHql(line, page, sysUser, isSiteAdmin, isSupperAdmin);
	}

	public List<Line> findLineList(Line line, Page page, SysUser sysUser) {
		return findLineList(line, page, sysUser, true, true);
	}

    public List<Line> findLineList(Line line, Page page) {
        return dao.findLineList(line, page);
    }

	public List<Line> findLineBySite(Line line, Page page, SysSite sysSite) {

		return dao.findLineList(line, page, sysSite);
	}

	public List<Line> findBySupplier(Page page, SysUnit supplier) {
		Criteria<Line> criteria = new Criteria<Line>(Line.class);
		criteria.createCriteria("user").createCriteria("sysUnit").createCriteria("companyUnit").add(Restrictions.eq("id", supplier.getId()));
		return dao.findByCriteria(criteria);
	}



	public List<Line> getListListByLabel(Long cityCode, Label label) {
		List<Label> labels = labelService.list(label, null);
		LabelItem labelItem = new LabelItem();
		labelItem.setLabel(labels.get(0));
		Line line = new Line();
		Set<LabelItem> list = new HashSet<LabelItem>();
		list.add(labelItem);
		line.setLabelItems(list);
		line.setCityId(cityCode);
		return findLineList(line, null, null);
	}


	public List<Line> getRecommendLine() {
		Label label = new Label();
		label.setName("热门路线");
		List<Label> labels = labelService.list(label, null);
		LabelItem labelItem = new LabelItem();
		labelItem.setLabel(labels.get(0));
		Line line = new Line();
		Set<LabelItem> list = new HashSet<LabelItem>();
		list.add(labelItem);
		line.setLabelItems(list);
		return findLineList(line, null, null);
	}

    public List<Line> getLineIndexLine(String labelName, Page page) {
        Label label = new Label();
        label.setName(labelName);
        List<Line> lineList = this.getLineByLabel(label, page, new Line());
        for (Line line : lineList) {
            // 提取封面
            List<LineImages> lineImagesList = lineImagesService.listImagesByLineId(line.getId(), LineImageType.home);
            if (lineImagesList != null && !lineImagesList.isEmpty()) {
                line.setCover(lineImagesList.get(0).getImageUrl());
                line.setCoverDesc(lineImagesList.get(0).getImageDesc());
            }
            // 获取最低报价
            Set<Linetypeprice> linetypeprices = line.getLinetypeprices();
            if (linetypeprices.isEmpty()) {
                line.setMinPrice("暂无报价");
                continue;
            }
            // 获取最近7天的最低报价
            Date startDate = new Date();
//            Calendar calendar = Calendar.getInstance();
//            calendar.add(Calendar.DATE, 7);
//            Date endDate = calendar.getTime();
            Float min = linetypepriceDao.findMinDicountPriceByLineId(line.getId(), startDate, null);
            if (min != null && min > 0F) {
                line.setMinPrice(Float.toString(min));
            } else {
                line.setMinPrice("暂无报价");
            }
        }
        return lineList;
    }



    /**
     * 通过任意标签查询线路列表
     * @param label
     * @param page
     * @param line
     * @return
     */
    private List<Line> getLineByLabel(Label label, Page page, Line line) {
        List<Label> labels = labelService.list(label, null);
        LabelItem labelItem = new LabelItem();
        labelItem.setLabel(labels.get(0));
        Set<LabelItem> labelItems = new HashSet<LabelItem>();
        labelItems.add(labelItem);
        line.setLabelItems(labelItems);
		line.setLineStatus(LineStatus.show);
		return this.findLineList(line, page);
    }



	/**
	 * 保存线路
	 *
	 * @author caiys
	 * @date 2015年10月16日 下午5:12:46
	 * @param line
	 */
	public void saveLine(Line line) {
		dao.save(line);
	}

	/**
	 * 更新线路
	 *
	 * @author caiys
	 * @date 2015年10月16日 下午5:12:46
	 * @param line
	 */
	public void updateLine(Line line,  String childFolder) {
		// 删除旧图片
		/*productimageDao.delBy(line.getId(), childFolder);
		if (productimage != null) { // 保存新图片
			productimage.setProduct(line);
			productimageDao.save(productimage);
		}*/
		dao.save(line);
	}

	/**
	 * 根据标识获取线路
	 *
	 * @author caiys
	 * @date 2015年10月23日 下午4:11:16
	 * @param id
	 * @return
	 */
	public Line loadLine(Long id) {
		return dao.load(id);
	}

	/**
	 * 根据标识设置状态，多个逗号分隔
	 *
	 * @author caiys
	 * @date 2015年10月26日 下午8:25:59
	 * @param ids
	 * @param lineStatus
	 */
	public Map<String, Object> updateLineStatus(String ids, LineStatus lineStatus, String reason, User loginUser) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		if (StringUtils.isNotBlank(ids)) {
			String[] idsArray = ids.split(",");

			for (String id : idsArray) {
				Line line = dao.load(Long.valueOf(id));
				List<Line> lines = new ArrayList<Line>();
                if (line.getLineStatus() == LineStatus.checking && (lineStatus == LineStatus.show || lineStatus == LineStatus.hide)) {  // 原状态为待审核，设置审核信息
                    line.setAuditBy(loginUser.getId());
                    line.setAuditTime(new Date());
                    line.setAuditReason(reason);
                }
				if (lineStatus == LineStatus.show) { // 如果要设置为显示，需校验
					// 校验报价和天内行程
					Date[] dates = DateUtils.getRangeDate();
					Integer countPrice = 0;
					if (line.getTopProduct() != null && line.getId() != line.getTopProduct().getId()) { // 如果是代理线路，则检验顶级线路的报价信息
						countPrice = linetypepricedateDao.checkCount(line.getTopProduct().getId(), null, dates[0], dates[1]);
						map.put("errorMsg", "设置失败，原线路【" + line.getName() + "】报价信息不完整");
					} else {
						countPrice = linetypepricedateDao.checkCount(Long.valueOf(id), null, dates[0], dates[1]);
						map.put("errorMsg", "设置失败，线路【" + line.getName() + "】报价信息不完整");
					}
					if (countPrice <= 0) {
						map.put("success", false);
						return map;
					}
					/***取消验证（客户要求）-2016-03-22***/
					/*Integer countDayPlan = linedaysplanDao.checkCount(Long.valueOf(id), null);
					if (countDayPlan <= 0) {
						map.put("success", false);
						map.put("errorMsg", "设置失败，线路【" + line.getName() + "】行程内容信息不完整");
						return map;
					}*/
					line.setLineStatus(lineStatus);
					line.setUpdateTime(new Date());
					line.setStatus(ProductStatus.UP);
					lines.add(line);
					dao.update(lines);
				} else {
					line.setLineStatus(lineStatus);
					line.setStatus(ProductStatus.DOWN);
					line.setUpdateTime(new Date());
					lines.add(line);
					dao.update(lines);
				}
			}
//			if (lines.size() > 0) {
//				dao.update(lines);
//			}
		}
		return map;
	}

	/**
	 * 根据标识设置重发，多个逗号分隔
	 *
	 * @author caiys
	 * @date 2015年10月26日 下午8:25:59
	 * @param ids
	 */
	public void updatePubBatch(String ids) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idsArray = ids.split(",");
			for (String id : idsArray) {
				Line line = dao.load(Long.valueOf(id));
				line.setUpdateTime(new Date());
				dao.update(line);
			}
		}
	}

	/**
	 * 根据标识设置有购物有自费，多个逗号分隔
	 *
	 * @author caiys
	 * @date 2015年10月26日 下午8:25:59
	 * @param ids
	 * @param buypay
	 */
	public void updateBuypay(String ids, Buypay buypay) {
		if (StringUtils.isNotBlank(ids)) {
			String[] idsArray = ids.split(",");
			for (String id : idsArray) {
				Line line = dao.load(Long.valueOf(id));
				line.setBuypay(buypay);
				line.setUpdateTime(new Date());
				dao.update(line);
			}
		}
	}

	/**
	 * 根据标识更新排序，多个逗号分隔，标识和排序值冒号分隔
	 *
	 * @author caiys
	 * @date 2015年10月26日 下午8:25:59
	 * @param idValues
	 */
	public void updateOrderBatch(String idValues) {
		if (StringUtils.isNotBlank(idValues)) {
			String[] idValueArray = idValues.split(",");
			for (String idValue : idValueArray) {
				String[] array = idValue.split(":");
				Line line = dao.load(Long.valueOf(array[0]));
				if (array.length == 2 && StringUtils.isNotBlank(array[1])) {
					line.setShowOrder(Integer.valueOf(array[1]));
				} else {
					line.setShowOrder(null);
				}
				line.setUpdateTime(new Date());
				dao.update(line);
			}
		}
	}

	/**
	 * 复制整条线路：线路信息（不含线路-其它）、线路类别报价、线路报价时间、线路主题、途径城市、线路说明、行程天、天内行程
	 *
	 * @author caiys
	 * @date 2015年10月27日 下午6:33:23
	 * @param lineId
	 */
	public void doCopyLine(Long lineId, SysUser user, String staticPath) {
		// 线路信息
		Line line = dao.load(lineId);
		dao.evict(line); // 从hibernate缓存中移除对象，否则直接改标识会报错

		// 类别报价
		Linetypeprice ltp = new Linetypeprice();
		ltp.setLine(line);
		List<Linetypeprice> linetypeprices = linetypepriceDao.findLinetypepriceList(ltp);

		// 途径城市
		Lineacrosscitys lcc = new Lineacrosscitys();
		lcc.setLine(line);
		List<Lineacrosscitys> lineacrosscitys = lineacrosscitysDao.findLineacrosscitysList(lcc);

		line.setId(null);
		line.setLinestatistic(null);
		line.setLineacrosscitys(null);
		line.setLineexplain(null);
		line.setLinetypeprices(null);
		line.setLineplaytitles(null);
		line.setUser(user);
		line.setCompanyUnit(user.getSysUnit().getCompanyUnit());
		line.setCreateTime(new Date());
		line.setUserId(user.getId());
		line.setUpdateTime(new Date());
		dao.save(line);
		for (Linetypeprice linetypeprice : linetypeprices) {
			linetypepriceDao.evict(linetypeprice); // 从hibernate缓存中移除对象，否则直接改标识会报错
			Linetypepricedate ltpd = new Linetypepricedate();
			ltpd.setLinetypeprice(linetypeprice);
			// ltpd.setLineId(lineId);
			List<Linetypepricedate> linetypepricedates = linetypepricedateDao.findTypePriceDate(ltpd);

			linetypeprice.setId(null);
			linetypeprice.setLine(line);
			linetypeprice.setLinetypepricedate(null);
			linetypeprice.setCreateTime(new Date());
			linetypeprice.setUserId(user.getId());
			linetypepriceDao.save(linetypeprice);
			// 报价时间
			for (Linetypepricedate linetypepricedate : linetypepricedates) {
				linetypepricedateDao.evict(linetypepricedate); // 从hibernate缓存中移除对象，否则直接改标识会报错
				linetypepricedate.setId(null);
				linetypepricedate.setLinetypeprice(linetypeprice);
				linetypepricedate.setLineId(linetypeprice.getLine().getId());
				linetypepricedate.setCreateTime(new Date());
				linetypepricedate.setUserId(user.getId());
				linetypepricedateDao.save(linetypepricedate);
			}
		}

		// 线路主题
		Lineplaytitle lpt = new Lineplaytitle();
		lpt.setLineId(lineId);
		List<Lineplaytitle> lineplaytitles = lineplaytitleDao.findLineplaytitle(lpt);
		for (Lineplaytitle lineplaytitle : lineplaytitles) {
			lineplaytitleDao.evict(lineplaytitle); // 从hibernate缓存中移除对象，否则直接改标识会报错
			lineplaytitle.setId(null);
			lineplaytitle.setLineId(line.getId());
			lineplaytitle.setCreateTime(new Date());
			lineplaytitle.setUserId(user.getId());
			lineplaytitleDao.save(lineplaytitle);
		}

		// 途径城市
		for (Lineacrosscitys lineacrosscity : lineacrosscitys) {
			lineacrosscitysDao.evict(lineacrosscity); // 从hibernate缓存中移除对象，否则直接改标识会报错
			lineacrosscity.setId(null);
			lineacrosscity.setLine(line);
			lineacrosscitysDao.save(lineacrosscity);
		}

		// 线路图片（只考虑线路描述的图片）
		Productimage pi = new Productimage();
		Product p = new Product();
		p.setId(Long.valueOf(lineId));
		pi.setProduct(p);
		List<Productimage> productimages = productimageDao.findProductimage(pi, null);
		for (Productimage productimage : productimages) {
			productimageDao.evict(productimage); // 从hibernate缓存中移除对象，否则直接改标识会报错
			// 文件拷贝
			String path = productimage.getPath();
			if (StringUtils.isNotBlank(path)) {
				String suffix = path.substring(path.lastIndexOf("."));
				String newFileName = System.currentTimeMillis() + suffix;
				String newFilePath = StringUtils.defaultString(productimage.getChildFolder()) + newFileName;
				FileUtils.copy(staticPath + path, staticPath + newFilePath);
				productimage.setPath(newFilePath);
			}
			productimage.setId(null);
			productimage.setProduct(line);
			productimage.setCreateTime(new Date());
			productimage.setUserId(user.getId());
			productimage.setCompanyUnitId(user.getSysUnit().getCompanyUnit().getId());
			productimageDao.save(productimage);
		}

		// 线路说明
		Lineexplain le = new Lineexplain();
		le.setLineId(lineId);
		List<Lineexplain> lineexplains = lineexplainDao.findLineexplain(le);
		for (Lineexplain lineexplain : lineexplains) {
			lineexplainDao.evict(lineexplain); // 从hibernate缓存中移除对象，否则直接改标识会报错
			Linedays ld = new Linedays();
			ld.setLineexplain(lineexplain);
			List<Linedays> linedayss = linedaysDao.findLinedays(ld);

			lineexplain.setId(null);
			lineexplain.setLineId(line.getId());
			lineexplain.setCreateTime(new Date());
			lineexplain.setUserId(user.getId());
			lineexplain.setLinedays(null);
			lineexplainDao.save(lineexplain);
			// 行程天
			for (Linedays linedays : linedayss) {
				linedaysDao.evict(linedays); // 从hibernate缓存中移除对象，否则直接改标识会报错
				Linedaysplan ldp = new Linedaysplan();
				ldp.setLinedays(linedays);
				List<Linedaysplan> linedaysplans = linedaysplanDao.findLinedaysplan(ldp);

				linedays.setId(null);
				linedays.setLineexplain(lineexplain);
				linedays.setLineId(lineexplain.getLineId());
				linedays.setCreateTime(new Date());
				linedays.setUserId(user.getId());
				linedays.setLinedaysplan(null);
				linedaysDao.save(linedays);

				// 天内行程
				for (Linedaysplan linedaysplan : linedaysplans) {
					lineplaytitleDao.evict(linedaysplan); // 从hibernate缓存中移除对象，否则直接改标识会报错
					linedaysplan.setId(null);
					linedaysplan.setLinedays(linedays);
					linedaysplan.setLineId(linedays.getLineId());
					linedaysplan.setCreateTime(new Date());
					linedaysplan.setUserId(user.getId());
					lineplaytitleDao.save(linedaysplan);
				}
			}
		}

	}

	/**
	 * 代理线路：线路信息（不含线路-其它、线路类别报价、线路报价时间）、线路主题、途径城市、线路说明、行程天、天内行程
	 *
	 * @author caiys
	 * @date 2015年10月27日 下午6:33:23
	 * @param line
	 */
	public Map<String, Object> doAgentLine(Line line, SysUser user, LineStatus lineStatus, String staticPath, List<LinetypepriceAgent> linetypepriceAgentList) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		// 线路信息
//		Line line = dao.load(lineId);
		if (line.getCompanyUnit().getId().equals(user.getSysUnit().getCompanyUnit().getId())) {
			map.put("success", false);
			map.put("errorMsg", "不能代理自家的商品");
			return map;
		}
		String lineName = line.getName();
		Long lineId = line.getId();
		Long topProductId = line.getTopProduct().getId();

		dao.evict(line); // 从hibernate缓存中移除对象，否则直接改标识会报错
		line.setName(lineName);

		// 途径城市
		Lineacrosscitys lcc = new Lineacrosscitys();
		lcc.setLine(line);
		List<Lineacrosscitys> lineacrosscitys = lineacrosscitysDao.findLineacrosscitysList(lcc);
		Line pLine = new Line();
		pLine.setId(line.getId());
		line.setParent(pLine);
		Line topProduct = new Line();
		topProduct.setId(topProductId);
		line.setTopProduct(topProduct);
		line.setId(null);
		line.setLinestatistic(null);
		line.setLineacrosscitys(null);
		line.setLineexplain(null);
		line.setLinetypeprices(null);
		line.setLineplaytitles(null);
		line.setUser(user);
		line.setCompanyUnit(user.getSysUnit().getCompanyUnit());
		line.setCreateTime(new Date());
		line.setUserId(user.getId());
		line.setUpdateTime(new Date());
		line.setLineStatus(lineStatus);
		dao.save(line);


		for (LinetypepriceAgent agent : linetypepriceAgentList) {
			agent.setLine(line);
			linetypepriceAgentDao.save(agent);
		}


		// 线路主题
		Lineplaytitle lpt = new Lineplaytitle();
		lpt.setLineId(lineId);
		List<Lineplaytitle> lineplaytitles = lineplaytitleDao.findLineplaytitle(lpt);
		for (Lineplaytitle lineplaytitle : lineplaytitles) {
			lineplaytitleDao.evict(lineplaytitle); // 从hibernate缓存中移除对象，否则直接改标识会报错
			lineplaytitle.setId(null);
			lineplaytitle.setLineId(line.getId());
			lineplaytitle.setCreateTime(new Date());
			lineplaytitle.setUserId(user.getId());
			lineplaytitleDao.save(lineplaytitle);
		}

		// 途径城市
		for (Lineacrosscitys lineacrosscity : lineacrosscitys) {
			lineacrosscitysDao.evict(lineacrosscity); // 从hibernate缓存中移除对象，否则直接改标识会报错
			lineacrosscity.setId(null);
			lineacrosscity.setLine(line);
			lineacrosscitysDao.save(lineacrosscity);
		}

		// 线路图片（只考虑线路描述的图片）
		Productimage pi = new Productimage();
		Product p = new Product();
		p.setId(Long.valueOf(lineId));
		pi.setProduct(p);
		List<Productimage> productimages = productimageDao.findProductimage(pi, null);
		for (Productimage productimage : productimages) {
			productimageDao.evict(productimage); // 从hibernate缓存中移除对象，否则直接改标识会报错
			// 文件拷贝
			String path = productimage.getPath();
			if (StringUtils.isNotBlank(path)) {
				String suffix = path.substring(path.lastIndexOf("."));
				String newFileName = System.currentTimeMillis() + suffix;
				String newFilePath = StringUtils.defaultString(productimage.getChildFolder()) + newFileName;
				FileUtils.copy(staticPath + path, staticPath + newFilePath);
				productimage.setPath(newFilePath);
			}
			productimage.setId(null);
			productimage.setProduct(line);
			productimage.setCreateTime(new Date());
			productimage.setUserId(user.getId());
			productimage.setCompanyUnitId(user.getSysUnit().getCompanyUnit().getId());
			productimageDao.save(productimage);
		}

		// 线路说明
		Lineexplain le = new Lineexplain();
		le.setLineId(lineId);
		List<Lineexplain> lineexplains = lineexplainDao.findLineexplain(le);
		for (Lineexplain lineexplain : lineexplains) {
			lineexplainDao.evict(lineexplain); // 从hibernate缓存中移除对象，否则直接改标识会报错
			Linedays ld = new Linedays();
			ld.setLineexplain(lineexplain);
			List<Linedays> linedayss = linedaysDao.findLinedays(ld);

			lineexplain.setId(null);
			lineexplain.setLineId(line.getId());
			lineexplain.setCreateTime(new Date());
			lineexplain.setUserId(user.getId());
			lineexplain.setLinedays(null);
			lineexplainDao.save(lineexplain);
			// 行程天
			for (Linedays linedays : linedayss) {
				linedaysDao.evict(linedays); // 从hibernate缓存中移除对象，否则直接改标识会报错
				Linedaysplan ldp = new Linedaysplan();
				ldp.setLinedays(linedays);
				List<Linedaysplan> linedaysplans = linedaysplanDao.findLinedaysplan(ldp);

				linedays.setId(null);
				linedays.setLineexplain(lineexplain);
				linedays.setLineId(lineexplain.getLineId());
				linedays.setCreateTime(new Date());
				linedays.setUserId(user.getId());
				linedays.setLinedaysplan(null);
				linedaysDao.save(linedays);

				// 天内行程
				for (Linedaysplan linedaysplan : linedaysplans) {
					lineplaytitleDao.evict(linedaysplan); // 从hibernate缓存中移除对象，否则直接改标识会报错
					linedaysplan.setId(null);
					linedaysplan.setLinedays(linedays);
					linedaysplan.setLineId(linedays.getLineId());
					linedaysplan.setCreateTime(new Date());
					linedaysplan.setUserId(user.getId());
					lineplaytitleDao.save(linedaysplan);
				}
			}
		}
		return map;
	}

	public void doIndexLines() {
		Criteria<Line> criteria = new Criteria<Line>(Line.class);
		criteria.eq("lineStatus", LineStatus.show);
		int pageIndex = 1;
		Page page;
		int pageSize = 20;
		do {
			page = new Page(pageIndex++, pageSize);
			List<Line> lines = dao.findByCriteria(criteria, page);

			for (Line line : lines) {
				doIndexLine(line);
			}
			solrTemplate.commit(coreName);
		} while (page.getPageIndexCount() == pageSize);
	}

	public void indexLine(Long id) {
		Line line = loadLine(id);
		doIndexLine(line);
	}

	public void doIndexLine(Line line) {
		try {
			SolrInputDocument doc = new SolrInputDocument();
			doc.addField("id", line.getId());
			doc.addField("lineDay", line.getPlayDay());
			Lineexplain lineexplain = line.getLineexplain();
			if (lineexplain != null && lineexplain.getLineLightPoint() != null) {
				doc.addField("linePoint", lineexplain.getLineLightPoint());
			}
			doc.addField("parent", true);
			doc.addField("type", ProductType.line.name());
			doc.addField("typeid", String.format("%s%d", ProductType.line.name(), line.getId()));
			doc.addField("name", line.getName());
			doc.addField("productAttr", line.getProductAttr().getDesc());
			doc.addField("status", line.getLineStatus().name());
			if (productimageService.getCover(line.getId()) != null) {
				doc.addField("productImg", productimageService.getCover(line.getId()).getPath());
			}
			Product topProduct = line.getTopProduct();
			try {
				doc.addField("companyUnitId", line.getCompanyUnit().getId());
			} catch (Exception e) {
				// TODO: handle exception
			}
			if (topProduct != null) {
				doc.addField("topProductId", topProduct.getId());
				doc.addField("isTopProduct", line.getId().longValue() == topProduct.getId().longValue());

				User user = sysUserService.load(line.getUserId());
//				User user = topProduct.getUser();
				try {

					SysSite sysSite = user.getSysSite();
					doc.addField("site", sysSite.getId());
				} catch (Exception e) {
					// TODO: handle exception
				}
				SysUser sysUser = (SysUser) user;
				SysUnit sysUnit = sysUser.getSysUnit();
				System.out.println(sysUnit.getId());
				SysUnit companyUnit = sysUnit.getCompanyUnit();
				System.out.println(sysUnit.getId());
				SysUnitDetail sysUnitDetail = companyUnit.getSysUnitDetail();
				try {
					System.out.println("detail" + sysUnitDetail.getId());
					doc.addField("supplierId", companyUnit.getId());
					doc.addField("supplierName", sysUnitDetail.getBrandName());
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					doc.addField("supplierLogo", sysUnitDetail.getLogoImgPath());
				} catch (Exception e) {
					// TODO: handle exception
				}
			} else {
				try {
					doc.addField("site", line.getUser().getSysSite().getId());
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					doc.addField("supplierName", line.getUser().getSysUnit().getCompanyUnit().getSysUnitDetail().getBrandName());
				} catch (Exception e) {
					// TODO: handle exception
				}
				try {
					doc.addField("supplierLogo", line.getUser().getSysUnit().getCompanyUnit().getSysUnitDetail().getLogoImgPath());
				} catch (Exception e) {
					// TODO: handle exception
				}
			}

			Lineplaytitle playtitle = new Lineplaytitle();
			playtitle.setLineId(line.getId());
			List<Lineplaytitle> titles = lineplaytitleDao.findLineplaytitle(playtitle);

//			Set<Lineplaytitle> titles = line.getLineplaytitles();
			for (Lineplaytitle lineplaytitle : titles) {
				doc.addField("lineplaytitle", lineplaytitle.getPlayTitleName());
			}
			// doc.addField("supplierName", line.getUser().getUserName());
			Lineacrosscitys acrosscitys = new Lineacrosscitys();
			acrosscitys.setLine(line);
			List<Lineacrosscitys> citys = lineacrosscitysDao.findLineacrosscitysList(acrosscitys);
//			Set<Lineacrosscitys> citys = line.getLineacrosscitys();
			for (Lineacrosscitys crossCity : citys) {
				doc.addField("crossCitys", crossCity.getTbArea().getName());
			}
			List<SolrInputDocument> childs = new ArrayList<SolrInputDocument>();
			float disCountPrice = Float.MAX_VALUE;
			float yjPrice = 0;
			List<Linetypeprice> linetypeprices = linetypepriceDao.getTypePriceList(line);
			for (Linetypeprice linetypeprice : linetypeprices) {
				Set<Linetypepricedate> price = linetypeprice.getLinetypepricedate();
				for (Linetypepricedate linetypepricedate : price) {
					SolrInputDocument child = new SolrInputDocument();
					child.addField("childPrice", linetypepricedate.getChildPrice());
					child.addField("day", linetypepricedate.getDay());
					child.addField("discountPrice", linetypepricedate.getDiscountPrice());
					disCountPrice = Math.min(disCountPrice,
							linetypepricedate.getDiscountPrice() == null ? Float.MAX_VALUE : linetypepricedate.getDiscountPrice());
					yjPrice = Math.max(yjPrice, linetypepricedate.getRebate() == null ? 0 : linetypepricedate.getRebate());
					child.addField("lineType", linetypeprice.getQuoteName());
					child.addField("type", ProductType.line.name());
					child.addField("typeid", String.format("%s%d", ProductType.line.name(), line.getId()));
					child.addField("id", line.getId());
					child.addField("parent", false);
					childs.add(child);
				}
			}
			if (disCountPrice != Float.MAX_VALUE) {
				doc.addField("disCountPrice", df.format(disCountPrice));
			} else {
				doc.addField("disCountPrice", 0);
			}
			if (yjPrice != 0) {
				doc.addField("yjPrice", df.format(yjPrice));
			} else {
				doc.addField("yjPrice", yjPrice);
			}

			if (line.getLineStatus() != LineStatus.show && line.getStatus() != ProductStatus.UP) {
				deleteIndexByEntityId(line.getId(), SolrType.line);
			} else {
				solrTemplate.saveDocument(coreName, doc);
				solrTemplate.commit(coreName);
			}

		} catch (Exception e1) {
			e1.printStackTrace();
		}
	}

	public QueryResponse createFacets(SysSite sysSite) {
		solrTemplate.getSolrServerFactory().getSolrServer(coreName);
		SolrQuery query = new SolrQuery(); // 建立一个新的查询
		query.setQuery("parent:true");
		// query.setRequestHandler(String.format("/%s/select", coreName));
		query.setFacet(true);
		query.addFacetField("productAttr", "supplierName", "lineDay", "crossCitys"); // 设置需要facet的字段
		query.setFacetLimit(10);
		query.setFacetMissing(false);
		query.setFacetMinCount(1);
		query.setFilterQueries(String.format("site:\"%d\"", sysSite.getId()));
		// query.setParam("facet.query", "price:[0 TO 80]");
		// query.setParam("facet.query", "price:(80 TO 300]");
		Page page = new Page(1, 20);
		QueryResponse qresponse = solrTemplate.query(query, coreName, page);
		return qresponse;

		// FacetField facetField = qresponse.getFacetField(Facet_CATEGORY);
	}

	public void queryLine(String q) throws SolrServerException, UnsupportedEncodingException {
		solrTemplate.getSolrServerFactory().getSolrServer(coreName);
		SolrQuery query = new SolrQuery(); // 建立一个新的查询
		query.setQuery(String.format("parent:true AND name : %s", URLDecoder.decode(q, "UTF-8")));
		// query.setRequestHandler(String.format("/%s/select", coreName));
		// query.setFacet(true);
		// query.addFacetField(new String[] { "productAttr", "supplierName",
		// "lineDay" });// 设置需要facet的字段
		// query.setFacetLimit(10);
		// query.setFacetMissing(false);
		// query.setFacetMinCount(1);
		query.setStart(0);
		query.setRows(10);
		QueryResponse qresponse = solrTemplate.query(query, coreName);
		for (SolrDocument doc : qresponse.getResults()) {
			Object id = doc.getFieldValue("id");
			Object name = doc.getFieldValue("name");
			Object productAttr = doc.getFieldValue("productAttr");
			Object supplierName = doc.getFieldValue("supplierName");
			System.out.println(String.format("%s %s %s %s", id, name, productAttr, supplierName));
		}

		// FacetField facetField = qresponse.getFacetField(Facet_CATEGORY);
	}

	public QueryResponse findLine(Page page, String conditionStr, Long siteId) throws UnsupportedEncodingException {
		solrTemplate.getSolrServerFactory().getSolrServer(coreName);
		SolrQuery query = new SolrQuery(); // 建立一个新的查询
		if (StringUtils.isBlank(conditionStr)) {
			query.setQuery(String.format("type:line"));
//			query.addSort("yjPrice", ORDER.desc);
		} else {
			query.setQuery(String.format("type:line %s", conditionStr));
		}
		QueryResponse qresponse = solrTemplate.query(query, coreName, page);
		// for (SolrDocument doc : qresponse.getResults()) {
		// Object id = doc.getFieldValue("id");
		// Object name = doc.getFieldValue("name");
		// Object productAttr = doc.getFieldValue("productAttr");
		// Object supplierName = doc.getFieldValue("supplierName");
		// System.out.println(String.format("%s %s %s %s", id, name, productAttr, supplierName));
		// }
		return qresponse;

	}

	public QueryResponse findLine(Page page, String conditionStr, String sortProperty, ORDER order) throws UnsupportedEncodingException {
		solrTemplate.getSolrServerFactory().getSolrServer(coreName);
		SolrQuery query = new SolrQuery(); // 建立一个新的查询
		if (StringUtils.isBlank(conditionStr)) {
			query.setQuery(String.format("parent:true AND isTopProduct:true "));
		} else {
			query.setQuery(String.format("parent:true AND isTopProduct:true %s", conditionStr));

		}
		if (StringUtils.isBlank(sortProperty)) {
			query.addSort("yjPrice", ORDER.desc);
		} else {
			query.setSort(sortProperty, order == null ? ORDER.desc : order);
		}
		QueryResponse qresponse = solrTemplate.query(query, coreName, page);
		// for (SolrDocument doc : qresponse.getResults()) {
		// Object id = doc.getFieldValue("id");
		// Object name = doc.getFieldValue("name");
		// Object productAttr = doc.getFieldValue("productAttr");
		// Object supplierName = doc.getFieldValue("supplierName");
		// System.out.println(String.format("%s %s %s %s", id, name, productAttr, supplierName));
		// }
		return qresponse;

	}

	public QueryResponse findSupplierLine(Page page, String conditionStr) throws UnsupportedEncodingException {
		solrTemplate.getSolrServerFactory().getSolrServer(coreName);
		SolrQuery query = new SolrQuery(); // 建立一个新的查询
		if (StringUtils.isBlank(conditionStr)) {
			query.setQuery(String.format("parent:true AND isTopProduct:true AND status:show "));
			query.addSort("yjPrice", ORDER.desc);
		} else {
			query.setQuery(String.format("parent:true AND isTopProduct:true AND status:show %s", conditionStr));
		}
		QueryResponse qresponse = solrTemplate.query(query, coreName, page);
		// for (SolrDocument doc : qresponse.getResults()) {
		// Object id = doc.getFieldValue("id");
		// Object name = doc.getFieldValue("name");
		// Object productAttr = doc.getFieldValue("productAttr");
		// Object supplierName = doc.getFieldValue("supplierName");
		// System.out.println(String.format("%s %s %s %s", id, name, productAttr, supplierName));
		// }
		return qresponse;

	}

	/**
	 * 国内游：通过点击标签下的城市获取相应的游记列表
	 * @param cityCode
	 * @return
	 */
	public List<Line> findLineListByCity(Long cityCode) {

		Criteria<Line> criteria = new Criteria<Line>(Line.class);

		if (cityCode != null) {
			criteria.eq("cityId", cityCode);
		}


		return dao.findByCriteria(criteria);
	}


	/**
	 * 功能描述：获取拱量关系下的合作线路产品
	 * @param supplerUnitList
	 * @param loginUser
	 * @param supperAdmin
	 * @param siteAdmin
	 * @param line
	 * @param pageInfo
	 * @return
	 */
	public List<Line> getLineListByQuantityUnit(List<SysUnit> supplerUnitList,
												SysUser loginUser, Boolean supperAdmin,
												Boolean siteAdmin, Line line, Page pageInfo) {

		Criteria<Line> criteria = new Criteria<Line>(Line.class);
		if (supplerUnitList.isEmpty()) {
			return new ArrayList<Line>();
		}
		criteria.createCriteria("topProduct", "tp", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eqProperty("id", "tp.id")); // 排除代理线路
		// 数据过滤
		if (!supperAdmin) {
			criteria.createCriteria("companyUnit", "u", JoinType.INNER_JOIN);
			criteria.eq("u.sysSite.id", loginUser.getSysSite().getId());
			if (!siteAdmin) {
				criteria.in("companyUnit", supplerUnitList);
			}
		}
		if (line.getProType() != null) {
			criteria.eq("proType", line.getProType());
		}
		if (line.getName() != null) {
			criteria.like("name", line.getName(), MatchMode.ANYWHERE);
		}
		if (line.getStatus() != null) {
			criteria.eq("status", line.getStatus());
		}
		if (line.getLineStatus() != null) {
			criteria.eq("lineStatus", line.getLineStatus());
		}
		criteria.isNull("source");
		criteria.orderBy("updateTime", "desc");
		return dao.findByCriteria(criteria, pageInfo);
	}

	//索引全部线路
	public void indexAllLine() {
		Clock clock = new Clock();
		Criteria<Line> criteria = new Criteria<Line>(Line.class);
		criteria.eq("lineStatus", LineStatus.show);
		List<Line> list = dao.findByCriteria(criteria);
		//		logger.info("start indexing delicacy, count: " + list.size());

		List<LineSolrEntity> entities = Lists.transform(list, new Function<Line, LineSolrEntity>() {
			@Override
			public LineSolrEntity apply(Line line) {
				return lineToEntity(line);
			}
		});
		entities.removeAll(Collections.singleton(null));
		index(entities);
		//		logger.info("index delicacy success, cost: " + clock.elapseTime());
	}

	public void indexLineById(Long id) {
		Line line = loadLine(id);
		indexLine(line);
	}

	public void indexLine(Line line) {
		try {
			if (!LineStatus.show.equals(line.getLineStatus())) {
				// 删除状态不为正常的景点索引
				UpdateResponse updateResponse = deleteIndexByEntityId(line.getId(), SolrType.line);
				if (updateResponse.getStatus() != 0) {
					logger.error("发生错误: line#" + line.getId() + "删除索引失败!");
				} else {
					logger.info("操作成功: line#" + line.getId() + "删除索引成功!");
				}
			} else {
				LineSolrEntity entity = lineToEntity(line);
				if (entity == null) {
					logger.error("发生错误: line#" + line.getId() + "索引失败!");
				} else {
					List<LineSolrEntity> entities = Lists.newArrayList(entity);
					index(entities);
				}
			}
		} catch (Exception e) {
			logger.error("未知异常，line#" + line.getId() + "索引失败", e);
		}
	}

	public LineSolrEntity lineToEntity(Line line) {
		LineSolrEntity lineSolrEntity = new LineSolrEntity(line);
		TbArea startCity = tbAreaService.getById(line.getStartCityId());
		if (startCity != null) {
			lineSolrEntity.setStartCity(startCity.getName());
		}
		completelineEntity(line, lineSolrEntity);
		List<LineImages> images = lineImagesService.listImagesByLineId(line.getId(), LineImageType.home);
		if (!images.isEmpty()) {
			lineSolrEntity.setProductImg(images.get(0).getImageUrl());
		}
		lineSolrEntity.setMuiltStartCity(Lists.newArrayList(getSameCompanyCount(line)));
		return lineSolrEntity;
	}

	private void completelineEntity(Line line, LineSolrEntity entity) {
		List<String> lineDays = Lists.newArrayList();
		List<Linetypeprice> linetypeprices = Lists.newArrayList(line.getLinetypeprices());
		List<Long> typepriceIds = Lists.transform(linetypeprices, new Function<Linetypeprice, Long>() {
			@Override
			public Long apply(Linetypeprice input) {
				return input.getId();
			}
		});
		Date date = new Date();
		try {
			date = DateUtils.parseShortTime(DateUtils.formatShortDate(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}
		date = DateUtils.add(date, Calendar.DAY_OF_MONTH, 1);
		Float min = Float.MAX_VALUE;
		for (Long typepriceId : typepriceIds) {
			List<Linetypepricedate> linetypepricedates = linetypepricedateService.findTypePriceDate(typepriceId, date, null);
			lineDays.addAll(Lists.transform(linetypepricedates, new Function<Linetypepricedate, String>() {
				@Override
				public String apply(Linetypepricedate input) {
					return DateUtils.formatShortDate(input.getDay());
				}
			}));
			for (Linetypepricedate linetypepricedate : linetypepricedates) {
				min = Math.min(min, linetypepricedate.getDiscountPrice() + linetypepricedate.getRebate());
			}
		}
		if (min.equals(Float.MAX_VALUE)) {
			min = 0f;
		}
		entity.setStartDays(lineDays);
		entity.setPrice(min);
	}

	public List<Line> getSameCompanyLine(Line line) {
		Criteria<Line> criteria = new Criteria<Line>(Line.class);
		criteria.eq("companyUnit.id", line.getCompanyUnit().getId());
		criteria.eq("lineStatus", LineStatus.show);
		criteria.eq("arriveCityId", line.getArriveCityId());
		criteria.eq("productAttr", line.getProductAttr());
		criteria.eq("playDay", line.getPlayDay());
		return dao.findByCriteria(criteria);
	}

	public Long getSameCompanyCount(Line line) {
		Criteria<Line> criteria = new Criteria<Line>(Line.class);
		criteria.eq("companyUnit.id", line.getCompanyUnit().getId());
		criteria.eq("lineStatus", LineStatus.show);
		criteria.eq("arriveCityId", line.getArriveCityId());
		criteria.eq("productAttr", line.getProductAttr());
		criteria.eq("playDay", line.getPlayDay());
		criteria.setProjection(Projections.countDistinct("startCityId"));
		return dao.findLongCriteria(criteria);
	}

	public List<Line> getAreaHotLine(Long arriveCityId, Page page) {
		Criteria<Line> criteria = new Criteria<Line>(Line.class);
		criteria.eq("arriveCityId", arriveCityId);
		criteria.eq("lineStatus", LineStatus.show);
		criteria.orderBy(Order.desc("orderSum"));
		if (page != null) {
			return dao.findByCriteria(criteria, page);
		} else {
			return dao.findByCriteria(criteria);
		}
	}

	public List<Line> getAreaHotLine(Long arriveCityId, ProductAttr productAttr, Page page) {
		Criteria<Line> criteria = new Criteria<Line>(Line.class);
		criteria.eq("arriveCityId", arriveCityId);
		criteria.eq("productAttr", productAttr);
		criteria.eq("lineStatus", LineStatus.show);
		criteria.orderBy(Order.desc("orderSum"));
		if (page != null) {
			return dao.findByCriteria(criteria, page);
		} else {
			return dao.findByCriteria(criteria);
		}
	}

	public List<Line> findByLabel(Label label, Page page) {
		Criteria<Line> criteria = new Criteria<Line>(Line.class);
		DetachedCriteria criterion = criteria.createCriteria("labelItems", "labelItem");
		criteria.eq("lineStatus", LineStatus.show);
		criterion.add(Restrictions.eq("label.id", label.getId()));
		criterion.addOrder(Order.asc("order"));
		if (page == null) {
			return dao.findByCriteria(criteria);
		} else {
			return dao.findByCriteria(criteria, page);
		}
	}

	public List<String> getLineLabelName() {
		Label searchLabel = new Label();
		searchLabel.setName("首页-面板-自助自驾-线路特色");
		Label parent = labelService.findUnique(searchLabel);
		List<Label> labels = labelService.getAllChildsLabels(parent);
		List<String> names = Lists.transform(labels, new Function<Label, String>() {
			@Override
			public String apply(Label input) {
				return input.getAlias();
			}
		});
		return names;
	}

	public List<Line> completeLinePrice(List<Line> lineList) {
		List<Line> list = Lists.newArrayList();
		for (Line line : lineList) {
			completeLinePrice(line, list);
		}
		for (Line line : list) {
			List<LineImages> images = lineImagesService.listImagesByLineId(line.getId(), LineImageType.home);
			if (!images.isEmpty()) {
				line.setCover(images.get(0).getImageUrl());
			}
		}
		return list;
	}

	public void completeLinePrice(Line line, List<Line> list) {
		List<Linetypeprice> linetypeprices = Lists.newArrayList(line.getLinetypeprices());
		List<Long> typepriceIds = Lists.transform(linetypeprices, new Function<Linetypeprice, Long>() {
			@Override
			public Long apply(Linetypeprice input) {
				return input.getId();
			}
		});
		Date date = new Date();
		Float min = Float.MAX_VALUE;
		for (Long typepriceId : typepriceIds) {
			List<Linetypepricedate> linetypepricedates = linetypepricedateService.findTypePriceDate(typepriceId, date, null);
			for (Linetypepricedate linetypepricedate : linetypepricedates) {
				min = Math.min(min, linetypepricedate.getDiscountPrice() + linetypepricedate.getRebate());
			}
		}
		if (min.equals(Float.MAX_VALUE)) {
			min = 0f;
		}
		line.setPrice(min);
		list.add(line);
	}

	public String getTuanqi(Long id) {
		Line line = loadLine(id);
		List<Linetypeprice> linetypeprices = Lists.newArrayList(line.getLinetypeprices());
		List<Long> typepriceIds = Lists.transform(linetypeprices, new Function<Linetypeprice, Long>() {
			@Override
			public Long apply(Linetypeprice input) {
				return input.getId();
			}
		});
		Date date = new Date();
		String tuanqi = "";
		for (Long typepriceId : typepriceIds) {
			List<Linetypepricedate> linetypepricedates = linetypepricedateService.findTypePriceDate(typepriceId, date, null);
			int i = 0;
			for (Linetypepricedate linetypepricedate : linetypepricedates) {
				if (i < 3) {
					tuanqi += DateUtils.format(linetypepricedate.getDate(), "MM/dd") + "、";
				} else {
					break;
				}
				i++;
			}

			if (com.zuipin.util.StringUtils.isNotBlank(tuanqi)) {
				tuanqi = tuanqi + "...";
			} else {
				tuanqi = "无团期";
				return tuanqi;
			}

		}
		return tuanqi;
	}


	public Float getLinePrice(Long id) {
		Line line = loadLine(id);
		if (line == null) {
			return 0f;
		}
		List<Line> list = Lists.newArrayList();
		completeLinePrice(line, list);
		if (list.isEmpty()) {
			return 0f;
		}
		return list.get(0).getPrice();
	}

	public String getLineCover(Long id) {
		List<LineImages> images = lineImagesService.listImagesByLineId(id, LineImageType.home);
		if (!images.isEmpty()) {
			return images.get(0).getImageUrl();
		}
		return "";
	}

	public List<TbArea> getLineStartCity() {
		return dao.getLineStartCity();
	}

	public Line addCollect(Long id) {
		Line line = loadLine(id);
		Integer collectNum = line.getCollectSum();
		if (collectNum == null) {
			collectNum = 0;
		}
		collectNum++;
		line.setCollectSum(collectNum);
		dao.update(line);
		return line;
	}

	public Integer deleteCollect(Long id) {
		Line line = loadLine(id);
		Integer collectNum = line.getCollectSum();
		if (collectNum == null || collectNum == 0) {
			return 0;
		}
		collectNum--;
		line.setCollectSum(collectNum);
		dao.update(line);
		return collectNum;
	}

	public Integer getCollectNum(Long id) {
		Line line = loadLine(id);
		Integer collectNum = line.getCollectSum();
		if (collectNum == null) {
			collectNum = 0;
		}
		return collectNum;
	}

	//clone
	public void createCloneLine(Line line) throws CloneNotSupportedException {
		Line newLine = line.clone();
		newLine.setId(null);
		newLine.setLinestatistic(null);
		newLine.setLineacrosscitys(null);
		newLine.setLineexplain(null);
		newLine.setLinetypeprices(null);
		newLine.setLineplaytitles(null);
		newLine.setLabelItems(null);
		newLine.setProductimage(null);
		newLine.setTopProduct(null);
		newLine.setProductNo(null);
		saveLine(newLine);

		List<LineImages> lineImagesList = lineImagesService.listImagesByLineId(line.getId(), LineImageType.home);
		for (LineImages images : lineImagesList) {
			cloneimage(images, newLine);
		}

		cloneExplain(lineexplainService.getByLine(line.getId()), newLine, line.getId());

		Set<Linetypeprice> linetypeprices = Sets.newHashSet();
		for (Linetypeprice linetypeprice : line.getLinetypeprices()) {
			Linetypeprice price = cloneTypePrice(linetypeprice, newLine);
			linetypeprices.add(price);
		}
		newLine.setLinetypeprices(linetypeprices);
		newLine.setTopProduct(newLine);
		newLine.setProductNo(newLine.getId().toString());
		update(line);
	}

	public LineImages cloneimage(LineImages lineImages, Line line) throws CloneNotSupportedException {
		LineImages images = lineImages.clone();
		images.setId(null);
		images.setLineId(line.getId());
		images.setLinedaysplan(null);
		images.setLineDaysPlanInfo(null);
		lineImagesService.save(images);
		return images;
	}

	public Lineexplain cloneExplain(Lineexplain lineexplain, Line line, Long lineId) throws CloneNotSupportedException {
		Lineexplain explain = lineexplain.clone();
		explain.setId(null);
		explain.setLineId(line.getId());
		explain.setLinedays(null);
		lineexplainService.save(explain);

		Linedays linedaysCondition = new Linedays();
		linedaysCondition.setLineId(lineId);
		List<Linedays> linedaysList = linedaysService.list(linedaysCondition, null);
		Set<Linedays> linedayses = Sets.newHashSet();
		for (Linedays linedays : linedaysList) {
			Linedays day = cloneLineDays(linedays, line, explain);
			linedayses.add(day);
		}
		explain.setLinedays(linedayses);
		lineexplainService.update(explain);
		return explain;
	}

	public Linedays cloneLineDays(Linedays linedays, Line line, Lineexplain lineexplain) throws CloneNotSupportedException {
		Linedays day = linedays.clone();
		day.setId(null);
		day.setLineexplain(lineexplain);
		day.setLineId(line.getId());
		day.setLinedaysplan(null);
		linedaysService.save(day);

		Set<Linedaysplan> linedaysplans = Sets.newHashSet();
		for (Linedaysplan linedaysplan : linedays.getLinedaysplan()) {
			Linedaysplan plan = cloneDaysPlan(linedaysplan, line, day);
			linedaysplans.add(plan);
		}
		day.setLinedaysplan(linedaysplans);
		linedaysService.update(day);
		return day;
	}

	public Linedaysplan cloneDaysPlan(Linedaysplan linedaysplan, Line line, Linedays linedays) throws CloneNotSupportedException {
		Linedaysplan plan = linedaysplan.clone();
		plan.setId(null);
		plan.setLineId(line.getId());
		plan.setLinedays(linedays);
		linedaysplanService.save(plan);

		List<LineDaysPlanInfo> lineDaysPlanInfos = lineDaysPlanInfoService.listByLineDaysPlanId(linedaysplan.getId());
		for (LineDaysPlanInfo lineDaysPlanInfo : lineDaysPlanInfos) {
			LineDaysPlanInfo info = lineDaysPlanInfo.clone();
			info.setId(null);
			info.setLineId(line.getId());
			info.setLinedays(linedays);
			info.setLinedaysplan(plan);
			lineDaysPlanInfoService.save(info);
		}

		List<LineImages> lineImages = lineImagesService.listByLineDaysPlanId(linedaysplan.getId(), LineImageType.detail);
		for (LineImages lineImage : lineImages) {
			LineImages images = lineImage.clone();
			images.setId(null);
			images.setLineId(line.getId());
			images.setLinedaysplan(plan);
			images.setLineDaysPlanInfo(null);
			lineImagesService.save(images);
		}
		return plan;
	}

	public Linetypeprice cloneTypePrice(Linetypeprice linetypeprice, Line line) throws CloneNotSupportedException {
		Linetypeprice price = linetypeprice.clone();
		price.setId(null);
		price.setLine(line);
		price.setLinetypepricedate(null);
		linetypepriceService.save(price);
		Set<Linetypepricedate> set = Sets.newHashSet();
		for (Linetypepricedate linetypepricedate : linetypeprice.getLinetypepricedate()) {
			set.add(clonePriceDate(linetypepricedate, price, line));
		}
		price.setLinetypepricedate(set);
		linetypepriceService.update(price);
		return price;
	}

	public Linetypepricedate clonePriceDate(Linetypepricedate linetypepricedate, Linetypeprice linetypeprice, Line line) throws CloneNotSupportedException {
		Linetypepricedate date = linetypepricedate.clone();
		date.setId(null);
		date.setLinetypeprice(linetypeprice);
		date.setLineId(line.getId());
		linetypepricedateService.save(date);
		return date;
	}

	public void delLabelItems(Line line) {
		labelItemService.delItemListByTargetIdId(line.getId(), TargetType.LINE);
	}

	/**
	 * 移动端线路列表查询
	 * @param line
	 * @return
	 */
	public List<Line> getWebLineList(Line line) {
		Criteria<Line> criteria = new Criteria<Line>(Line.class);
		if (line.getProductAttr() != null) {
			criteria.eq("productAttr", line.getProductAttr());
		}
		criteria.eq("status", ProductStatus.UP);
		criteria.eq("lineStatus", LineStatus.show);
		return dao.findByCriteria(criteria);
	}

}
