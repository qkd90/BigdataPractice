package com.data.data.hmly.service.ticket;

import com.data.data.hmly.enums.TargetType;
import com.data.data.hmly.service.LabelItemService;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.enums.CommentStatus;
import com.data.data.hmly.service.common.ProductimageService;
import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.common.dao.ProductimageDao;
import com.data.data.hmly.service.common.entity.Product;
import com.data.data.hmly.service.common.entity.Productimage;
import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.common.entity.enums.ShowStatus;
import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.dao.TbAreaDao;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.ticket.dao.TicketDao;
import com.data.data.hmly.service.ticket.dao.TicketExplainDao;
import com.data.data.hmly.service.ticket.dao.TicketPriceAgentDao;
import com.data.data.hmly.service.ticket.entity.Ticket;
import com.data.data.hmly.service.ticket.entity.TicketExplain;
import com.data.data.hmly.service.ticket.entity.TicketPriceAgent;
import com.data.data.hmly.service.ticket.entity.enmus.TicketType;
import com.data.data.hmly.service.ticket.vo.TicketSolrEntity;
import com.data.data.hmly.util.Clock;
import com.data.data.solr.MulticoreSolrTemplate;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.DateUtils;
import com.zuipin.util.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.FacetField;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Restrictions;
import org.hibernate.sql.JoinType;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TicketService extends SolrIndexService<Ticket, TicketSolrEntity> {

	Logger logger = Logger.getLogger(TicketService.class);

	@Resource
	private TicketDao dao;
	@Resource
	private TicketExplainDao explainDao;
	@Resource
	private ProductimageDao productimageDao;
	@Resource
	private TbAreaDao areaDao;
	@Resource
	private TicketPriceService tiPriceservice;

	@Resource
	private MulticoreSolrTemplate solrTemplate;

	@Resource
	private TicketExplainService ticketExplainService;

	@Resource
	private TicketPriceAgentDao ticketPriceAgentDao;
	@Resource
	private LabelItemService labelItemService;
	@Resource
	private ProductimageService productimageService;
	@Resource
	private CommentService commentService;
	@Resource
	private TicketDatepriceService ticketDatepriceService;

	private final static String coreName = "products";





	public boolean checkAgentTicket(Ticket ticket, SysUser sysUser) {

		Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);
		criteria.eq("parent", ticket);
		criteria.eq("user", sysUser);

		if (dao.findByCriteria(criteria).size() > 0) {
			return false;
		} else {
			return true;
		}

	}


	public Ticket findUserById(Long userId) {
		if (userId != null) {
			return dao.get(Ticket.class, userId);
		}
		return null;
	}


	public List<Ticket> findProTicketList(Ticket ticket, Page pageInfo) {
		Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);

		criteria.createCriteria("topProduct", "tp", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eqProperty("id", "tp.id")); // 排除代理线路
		//createTicketCriteria(ticket, criteria);
		if (ticket.getName() != null) {
			criteria.like("name", ticket.getName());
		}
		// 门票类型
		if (ticket.getTicketType() != null) {
			criteria.eq("ticketType", ticket.getTicketType());
		}
		criteria.eq("proType", ticket.getProType());
		criteria.isNull("source");
		criteria.eq("status", ProductStatus.UP);
		criteria.orderBy("updateTime", "desc");
		return  dao.findByCriteria(criteria, pageInfo);

	}

	public void createTicketCriteria(Ticket ticket, Criteria<Ticket> criteria) {

		if (ticket.getName() != null) {
			criteria.like("name", ticket.getName());
		}
		// 门票类型
		if (ticket.getTicketType() != null) {
			criteria.eq("ticketType", ticket.getTicketType());
		}

	}



	public List<Ticket> findTicketByName(Ticket ticket) {

		return dao.findTicketByName(ticket);

	}

	public List<Ticket> findTicketList(Ticket ticket, Page pageInfo) {
		return dao.findTicketList(ticket, pageInfo);
	}



	public List<Ticket> findAgentTicketList(Ticket ticket, Page pageInfo, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {
		return dao.findAgentTicketList(ticket, pageInfo, sysUser, isSiteAdmin, isSupperAdmin);
	}

	public List<Ticket> findTicketList(Ticket ticket, Page pageInfo, SysUser sysUser, Boolean isSiteAdmin, Boolean isSupperAdmin) {
		return dao.findTicketList(ticket, pageInfo, sysUser, isSiteAdmin, isSupperAdmin);
	}

	public void update(Ticket ticket, TicketExplain explain) {

		dao.update(ticket);

		explain.setTicketId(ticket.getId());
		explainDao.update(explain);

	}

	public void insert(Ticket ticket, TicketExplain explain, Productimage productimage) {

		ticket.setTopProduct(ticket);
//		ticket.setParent(ticket);

		dao.save(ticket);

		explain.setTicketId(ticket.getId());

		explainDao.save(explain);

		// productimageDao.save(productimage);

	}

	public Ticket findTicketById(Long ticketId) {
		Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);
		criteria.eq("id", ticketId);

		return dao.findUniqueByCriteria(criteria);
	}

    public Ticket findFullById(Long id) {
        Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);
        criteria.eq("id", id);
        criteria.createCriteria("ticketPriceSet", JoinType.LEFT_OUTER_JOIN);
        criteria.createCriteria("labelItems", JoinType.LEFT_OUTER_JOIN);
        criteria.createCriteria("scenicInfo", JoinType.LEFT_OUTER_JOIN);
        return dao.findUniqueByCriteria(criteria);
    }

	public Ticket loadTicket(Long ticketId) {
		return dao.load(ticketId);
	}

	public void save(Ticket ticket) {
		dao.save(ticket);
	}

	public Ticket findTicketById(Ticket ticket) {
		Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);
		criteria.eq("id", ticket.getId());

		return dao.findUniqueByCriteria(criteria);
	}

	public Long update(String ticketId, String payway, String inputConf) {

		Ticket ticket = new Ticket();

		if (ticketId != null) {
			ticket.setId(Long.parseLong(ticketId));
			ticket = dao.load(Long.parseLong(ticketId));
		}
		if (payway != null) {

			ticket.setPayway(payway);
		}
		if (inputConf != null) {

			ticket.setOrderConfirm(inputConf);
		}
		dao.update(ticket);

		return ticket.getId();
	}

	public void del(Ticket ticket) {

		explainDao.delAllByTicket(ticket);

		tiPriceservice.delPrice(ticket);

		dao.delete(ticket);

	}

	public void delTicket(Long id) {
		Ticket ticket = dao.load(id);
		ticket.setStatus(ProductStatus.DEL);
		dao.save(ticket);
	}

	public void update(Ticket ticket) {
		dao.update(ticket);
	}

	/**
	 * vacuity
	 */
	public void indexTickets() {
		Clock clock = new Clock();

		int pageNo = 1;
		int pageSize = 1000;
		int processed = 0;
		int total;
		Ticket ticket = new Ticket();
		ticket.setIncludeTicketTypeList(Lists.newArrayList(TicketType.sailboat, TicketType.huanguyou, TicketType.yacht));
		ticket.setIncludeTicketStatusList(Lists.newArrayList(ProductStatus.UP, ProductStatus.DOWN_CHECKING));
		while (true) {
			Page page = new Page(pageNo, pageSize);
			List<Ticket> list = findTicketList(ticket, page);
			processed += list.size();
			total = page.getTotalCount();
			if (list.isEmpty()) {
				break;
			}
			List<TicketSolrEntity> entities = Lists.transform(list, new Function<Ticket, TicketSolrEntity>() {
				@Override
				public TicketSolrEntity apply(Ticket ticket) {
					return ticketToEntity(ticket);
				}
			});
			index(entities);
			logger.info("save 1000 record cost " + clock.elapseTime() + "ms");
			if (processed >= total) {
				break;
			}
			pageNo += 1;
		}
		logger.info("finish in " + clock.totalTime() + "ms with " + processed);
	}

	public void indexTicket(Ticket ticket) {
		try {
            if (!ProductStatus.UP.equals(ticket.getStatus()) && !ProductStatus.DOWN_CHECKING.equals(ticket.getStatus())) {
                // 删除状态不为正常的景点索引
                UpdateResponse updateResponse = deleteIndexByEntityId(ticket.getId(), SolrType.scenic);
                if (updateResponse.getStatus() != 0) {
                    logger.error("发生错误: ticket#" + ticket.getId() + "删除索引失败!");
                } else {
                    logger.info("操作成功: ticket#" + ticket.getId() + "删除索引成功!");
                }
                return;
            }
            TicketSolrEntity entity = ticketToEntity(ticket);
            List<TicketSolrEntity> entitys = Lists.newArrayList(entity);
            index(entitys);
        } catch (Exception e) {
			logger.error("未知异常，ticket#" + ticket.getId() + "索引失败", e);
		}
	}

	public TicketSolrEntity ticketToEntity(Ticket ticket) {
		TicketSolrEntity entity = new TicketSolrEntity(ticket);
		Comment comment = new Comment();
		comment.setType(ProductType.sailboat);
		comment.setTargetId(ticket.getId());
		comment.setStatus(CommentStatus.NORMAL);
		Long commentCount = commentService.countMyComment(comment);
		if (commentCount != null) {
			entity.setCommentCount(commentCount.intValue());
		} else {
			entity.setCommentCount(0);
		}
		Integer commentScore = commentService.getAvgScore(comment);
		entity.setProductScore(Math.round(commentScore / 20f));
		Productimage productimage = productimageService.findCover(ticket.getId(), null, ProductType.scenic);
		if (productimage != null) {
			entity.setProductImg(productimage.getPath());
		}
		Float price = ticketDatepriceService.findMinPriceByTicketId(ticket.getId(), new Date(), null, "priPrice");
		entity.setDisCountPrice(price);
		return entity;
	}

	public Map<String, Object> query(long supplierId) throws SolrServerException {
		solrTemplate.getSolrServerFactory().getSolrServer(coreName);
		SolrQuery query = new SolrQuery(); // 建立一个新的查询
		String searchParam = "parent:true";

		if (supplierId != 0) {
			searchParam = searchParam + " AND supplierId : " + supplierId;
		}
		query.setQuery(searchParam);
		query.setFacet(true);
		query.addFacetField("crossCitys", "sceAji", "discountPrice"); // 设置需要facet的字段
		query.setFacetLimit(10);
		query.setFacetMissing(false);
		query.setFacetMinCount(1);
		query.setStart(0);
		query.setRows(0);
		QueryResponse qresponse = solrTemplate.query(query, coreName);

		Map<String, Object> map = new HashMap<String, Object>();
		for (FacetField facetField : qresponse.getFacetFields()) {
			String faceName = facetField.getName();
			List<String> nameList = new ArrayList<String>();
			for (FacetField.Count count : facetField.getValues()) {
				System.out.println(count.getName());
				nameList.add(count.getName());
			}
			map.put(faceName, nameList);
			System.out.println();
		}

		return map;

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
		query.setFacetMinCount(1);
		query.setStart(0);
		query.setRows(10);
		QueryResponse qresponse = solrTemplate.query(query, coreName);
		for (SolrDocument doc : qresponse.getResults()) {
			Object id = doc.getFieldValue("id");
			Object name = doc.getFieldValue("name");
			Object ticketType = doc.getFieldValue("ticketType");
			Object address = doc.getFieldValue("address");
			System.out.println(String.format("%s %s %s %s", id, name, ticketType, address));
		}

		// FacetField facetField = qresponse.getFacetField(Facet_CATEGORY);
	}

	public Map<String, Object> serarchBySelect(Page page, String city, String level, String cost, String mincost, String maxcost,
			long supplierId) throws SolrServerException, UnsupportedEncodingException {

		// 拼接查询条件
		String search = "parent:true AND type : " + ProductType.scenic.name();
		if (notNullAndEmpty(city)) {
			search = search + " AND crossCitys : " + city;
		}
		if (notNullAndEmpty(level)) {
			search = search + " AND sceAji : " + level;
		}

		float minc = 0;
		float maxc = Float.MAX_VALUE;
		if (notNullAndEmpty(mincost) || notNullAndEmpty(maxcost)) {

			if (notNullAndEmpty(mincost)) {
				minc = Float.parseFloat(mincost);
			}
			if (notNullAndEmpty(maxcost)) {
				maxc = Float.parseFloat(maxcost);
			}
			search = search + " AND disCountPrice:[" + minc + " TO " + maxc + "]";
		} else {
			if (notNullAndEmpty(cost)) {
				String[] costIds = cost.split("-");
				minc = Float.parseFloat(costIds[1]);
				if (costIds.length > 2) {
					maxc = Float.parseFloat(costIds[2]);
				}
				search = search + " AND disCountPrice:[" + minc + " TO " + maxc + "]";
			}
		}

		if (supplierId > 0) {
			search = search + " AND supplierId : " + supplierId;
		}

		solrTemplate.getSolrServerFactory().getSolrServer(coreName);
		SolrQuery query = new SolrQuery(); // 建立一个新的查询
		query.setQuery(search);
		query.setStart(0);
		query.setRows(10);
		QueryResponse qresponse = solrTemplate.query(query, coreName, page);

		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> productList = getMapList(qresponse);
		map.put("total", qresponse.getResults().getNumFound());
		map.put("product", productList);
		return map;

		// FacetField facetField = qresponse.getFacetField(Facet_CATEGORY);
	}

	public Map<String, Object> searchByName(Page page, String name, long supplierId) {
		// 拼接查询条件
		String search = "parent:true AND type : " + ProductType.scenic.name();
		if (notNullAndEmpty(name)) {
			search = search + " AND name : " + name;
		}
		if (supplierId > 0) {
			search = search + " AND supplierId : " + supplierId;
		}
		solrTemplate.getSolrServerFactory().getSolrServer(coreName);
		SolrQuery query = new SolrQuery(); // 建立一个新的查询
		query.setQuery(search);
		query.setStart(0);
		query.setRows(10);
		QueryResponse qresponse = solrTemplate.query(query, coreName, page);

		Map<String, Object> map = new HashMap<String, Object>();
		List<Map<String, Object>> productList = getMapList(qresponse);
		map.put("product", productList);
		map.put("total", qresponse.getResults().getNumFound());
		return map;
	}

	public List<Map<String, Object>> getMapList(QueryResponse qresponse) {

		List<Map<String, Object>> productList = new ArrayList<Map<String, Object>>();
		for (SolrDocument doc : qresponse.getResults()) {

			Map<String, Object> ticketMap = new HashMap<String, Object>();
			Object id = doc.getFieldValue("id");
			Object name = doc.getFieldValue("name");
			Object ticketType = doc.getFieldValue("ticketType");
			Object address = doc.getFieldValue("address");
			Object disCountPrice = doc.getFieldValue("disCountPrice");
			Object productAttr = doc.getFieldValue("productAttr");
			ticketMap.put("supplierId", doc.getFirstValue("supplierId"));
			ticketMap.put("supplierLogo", doc.getFirstValue("supplierLogo"));
			ticketMap.put("supplierName", doc.getFieldValue("supplierName"));
			ticketMap.put("id", id);
			ticketMap.put("name", name);
			ticketMap.put("ticketType", ticketType);
			ticketMap.put("address", address);
			DecimalFormat df = new DecimalFormat("#.##");
			ticketMap.put("disCountPrice", df.format(disCountPrice));
			ticketMap.put("productAttr", productAttr);
			ticketMap.put("productImg", doc.getFieldValue("productImg"));
			productList.add(ticketMap);
			System.out.println(String.format("%s %s %s %s %s %s", id, name, ticketType, address, disCountPrice, productAttr));
		}
		return productList;
	}

	private boolean notNullAndEmpty(String string) {
		if (string != null && !"".equals(string)) {
			return true;
		} else {
			return false;
		}
	}

	public List<Ticket> findTicketList(Long userId, TicketType scenic, String ticNameid) {

		Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);

		criteria.eq("user.id", userId);
		//
		criteria.eq("proType", TicketType.scenic);

		if (!StringUtils.isEmpty(ticNameid)) {
			criteria.like("name", "%" + ticNameid + "%");
		}
		return dao.findByCriteria(criteria);
		// return dao.findByCriteria(criteria);
	}

	/**
	 * 代理线路：线路信息（不含线路-其它、线路类别报价、线路报价时间）、线路主题、途径城市、线路说明、行程天、天内行程
	 * 
	 * @author caiys
	 * @date 2015年10月27日 下午6:33:23
	 * @param
	 */
	public void doAgentTicket(Long ticketId, String ticketName, SysUser user, ProductStatus lineStatus, String staticPath) {
		// 线路信息
		Ticket ticket = dao.load(ticketId);
		Long topProductId = ticket.getTopProduct().getId();
		dao.evict(ticket); // 从hibernate缓存中移除对象，否则直接改标识会报错
		ticket.setName(ticketName);

		Ticket pTicket = new Ticket();
		pTicket.setId(ticket.getId());
		ticket.setSupplier(ticket.getUser());
		ticket.setParent(pTicket);
		Ticket topProduct = new Ticket();
		topProduct.setId(topProductId);
		ticket.setTopProduct(topProduct);
		ticket.setId(null);
		ticket.setTicketPriceSet(null);
		ticket.setUser(user);
		ticket.setCompanyUnit(user.getSysUnit().getCompanyUnit());
		ticket.setCreateTime(new Date());
		ticket.setUpdateTime(new Date());
		ticket.setStatus(lineStatus);
		dao.save(ticket);

		// 线路图片（只考虑线路描述的图片）
		Productimage pi = new Productimage();
		Product p = new Product();
		p.setId(Long.valueOf(ticketId));
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
			productimage.setProduct(ticket);
			productimage.setCreateTime(new Date());
			productimage.setUserId(user.getId());
			productimage.setCompanyUnitId(user.getSysUnit().getCompanyUnit().getId());
			productimageDao.save(productimage);
		}

		// 线路说明
		TicketExplain te = new TicketExplain();
		te.setTicketId(ticketId);
		List<TicketExplain> ticketexplains = explainDao.findTicketexplain(te);
		for (TicketExplain ticketexplain : ticketexplains) {
			explainDao.evict(ticketexplain); // 从hibernate缓存中移除对象，否则直接改标识会报错
			ticketexplain.setId(null);
			ticketexplain.setTicketId(ticket.getId());
			explainDao.save(ticketexplain);
		}

	}



	public Map<String, Object> doTicketAgent(Ticket ticket, SysUser user, List<TicketPriceAgent> ticketPriceAgents, ProductStatus ticketStatus, String staticPath) {

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("success", true);
		// 线路信息
		if (ticket.getCompanyUnit().getId().equals(user.getSysUnit().getCompanyUnit().getId())) {
			map.put("success", false);
			map.put("errorMsg", "不能代理自家的商品");
			return map;
		}
		// 线路信息
		Long topProductId = ticket.getTopProduct().getId();
		String name = ticket.getName();
		String ticketName = ticket.getTicketName();
		dao.evict(ticket); // 从hibernate缓存中移除对象，否则直接改标识会报错
		ticket.setName(name);
		ticket.setTicketName(ticketName);
		Ticket pTicket = new Ticket();
		pTicket.setId(ticket.getId());
		ticket.setSupplier(ticket.getUser());
		ticket.setParent(pTicket);
		Ticket topProduct = new Ticket();
		topProduct.setId(topProductId);
		ticket.setTopProduct(topProduct);
		ticket.setId(null);
		ticket.setTicketPriceSet(null);
		ticket.setUser(user);
		ticket.setCompanyUnit(user.getSysUnit().getCompanyUnit());
		ticket.setCreateTime(new Date());
		ticket.setUpdateTime(new Date());
		ticket.setStatus(ticketStatus);
		dao.save(ticket);

		for (TicketPriceAgent agent : ticketPriceAgents) {
			agent.setTicket(ticket);
			ticketPriceAgentDao.save(agent);
		}

		// 线路图片（只考虑线路描述的图片）
		Productimage pi = new Productimage();
		Product p = new Product();
		p.setId(ticket.getId());
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
			productimage.setProduct(ticket);
			productimage.setCreateTime(new Date());
			productimage.setUserId(user.getId());
			productimage.setCompanyUnitId(user.getSysUnit().getCompanyUnit().getId());
			productimageDao.save(productimage);
		}

		// 线路说明
		TicketExplain te = new TicketExplain();
		te.setTicketId(ticket.getId());
		List<TicketExplain> ticketexplains = explainDao.findTicketexplain(te);
		for (TicketExplain ticketexplain : ticketexplains) {
			explainDao.evict(ticketexplain); // 从hibernate缓存中移除对象，否则直接改标识会报错
			ticketexplain.setId(null);
			ticketexplain.setTicketId(ticket.getId());
			explainDao.save(ticketexplain);
		}


		return map;
	}

	public List<Ticket> getTicketLabels(Ticket info, TbArea area,
										String tagIds, Page pageInfo, SysUser loginUser, Boolean siteAdmin, Boolean supperAdmin) {
		List<Ticket> infos = new ArrayList<Ticket>();
		
		Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);
		
		if (info.getName() != null) {
			criteria.like("name", "%" + info.getName() + "%");
		}
		criteria.eq("status", ProductStatus.UP);

		if (!supperAdmin) {
			criteria.createCriteria("companyUnit", "u", JoinType.INNER_JOIN);
			criteria.eq("u.sysSite.id", loginUser.getSysSite().getId());
			if (!siteAdmin) {
				criteria.eq("u.id", loginUser.getSysUnit().getCompanyUnit().getId());
			}
		}

		if (info.getIncludeTicketTypeList() != null && !info.getIncludeTicketTypeList().isEmpty()) {
			if (info.getIncludeTicketTypeList().size() == 1) {
				criteria.eq("ticketType", info.getIncludeTicketTypeList().get(0));
			} else if (info.getIncludeTicketTypeList().size() == 2) {
				criteria.add(Restrictions.or(Restrictions.eq("ticketType", info.getIncludeTicketTypeList().get(0)), Restrictions.eq("ticketType", info.getIncludeTicketTypeList().get(1))));
			}
		}

		if (area != null) {
			if (area.getLevel() == 1) {
				List<TbArea> areas = area.getChilds();
				List<Long> areaIds = Lists.newArrayList();
				Long[] aIds = new Long[areas.size()];
				for (int i = 0; i < areas.size(); i++) {
					areaIds.add(areas.get(i).getId());
				}
				areaIds.add(area.getId());
				criteria.in("cityId", areaIds);
			} else if (area.getLevel() == 2) {
				List<TbArea> areas = area.getChilds();
				List<Long> areaIds = Lists.newArrayList();
				Long[] aIds = new Long[areas.size()];
				for (int i = 0; i < areas.size(); i++) {
					areaIds.add(areas.get(i).getId());
				}
				areaIds.add(area.getId());
				criteria.in("cityId", areaIds);
			} else {
				criteria.eq("cityId", area.getId());
			}
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

	public List<Ticket> getProductList(SysUser sysUser, SysUnit companyUnit,
			String keyword) {
		Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);
		criteria.like("name", "%" + keyword + "%");
//		criteria.eq("user", sysUser);
		criteria.eq("companyUnit", companyUnit);
		criteria.eq("proType", ProductType.scenic);
		return dao.findByCriteria(criteria);
	}

	public Boolean hasTicket(Long scenicId) {
		Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);
		criteria.eq("scenicInfo.id", scenicId);
		return dao.findByCriteria(criteria).size() > 0;
	}


	/**
	 * 功能描述：获取拱量关系下的合作门票产品
	 * @param supplerUnitList
	 * @param loginUser
	 * @param supperAdmin
	 * @param siteAdmin
	 * @param ticket
	 * @param pageInfo
	 * @return
	 */
	public List<Ticket> getTicketListByQuantityUnit(List<SysUnit> supplerUnitList,
													SysUser loginUser, Boolean supperAdmin,
													Boolean siteAdmin, Ticket ticket, Page pageInfo) {



		if (supplerUnitList.isEmpty()) {
			return new ArrayList<Ticket>();
		}
		Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);
		criteria.createCriteria("topProduct", "tp", JoinType.LEFT_OUTER_JOIN);
		criteria.add(Restrictions.eqProperty("id", "tp.id")); // 排除代理线路
		criteria.createCriteria("companyUnit", "u", JoinType.INNER_JOIN);
		// 数据过滤
		if (!supperAdmin) {
			criteria.eq("u.sysSite.id", loginUser.getSysSite().getId());
			if (!siteAdmin) {
				criteria.in("companyUnit", supplerUnitList);
			}
		}
		if (ticket.getProType() != null) {
			criteria.eq("proType", ticket.getProType());
		}
		if (ticket.getName() != null) {
			criteria.like("name", ticket.getName(), MatchMode.ANYWHERE);
		}
		if (ticket.getTicketType() != null) {
			criteria.eq("ticketType", ticket.getTicketType());
		}
		if (ticket.getStatus() != null) {
			criteria.eq("status", ticket.getStatus());
		}
		criteria.isNull("source");
		criteria.orderBy("updateTime", "desc");
		return dao.findByCriteria(criteria, pageInfo);
	}

	public void delLabelItems(Ticket ticket) {
		labelItemService.delItemListByTargetIdId(ticket.getId(), TargetType.PRODUCT);
	}

	public Integer findTicketNumBy(Long scenicInfoId) {

		String hql = "select count(*) from TicketPrice tp, Ticket t where tp.ticket.id = t.id "
				+ "and t.status='UP' and t.scenicInfo.id = ? and type is not null "
				+ "and exists (select 1 from TicketDateprice d where d.ticketPriceId = tp.id) "
				+ "order by tp.discountPrice asc";
		Double result = dao.findDoubleByHQL(hql, scenicInfoId);
		return result.intValue();
	}

	public List<Ticket> getCheckingList(Ticket ticket, SysUser loginUser, Page pageInfo, Boolean superAdmin, String... properties) {

		return dao.getCheckingList(ticket, loginUser, pageInfo, superAdmin, properties);
	}


	public void doChangeStatus(List<Long> ids, ProductStatus status) {
		if (!ids.isEmpty()) {
			List<Ticket> hotels = getTicketListByIds(ids);
			for (Ticket ticket : hotels) {
				ticket.setStatus(status);
				ticket.setUpdateTime(new Date());
				dao.update(ticket);
				indexTicket(ticket);
			}

		}
	}

	public List<Ticket> getTicketListByIds(List<Long> ids) {
		List<Ticket> tickets = new ArrayList<Ticket>();
		for (Long id : ids) {
			Ticket ticket = dao.load(id);
			tickets.add(ticket);
		}
		return tickets;
	}

	public List<Ticket> getSailboatByCompanyUnit(Ticket ticket, SysUnit companyUnit, Page page) {
		Criteria<Ticket> criteria = new Criteria<Ticket>(Ticket.class);
		if (StringUtils.isNotBlank(ticket.getName())) {
			criteria.like("name", ticket.getName(), MatchMode.ANYWHERE);
		}
		if (ticket.getStatus() != null) {
			criteria.eq("status", ticket.getStatus());
		}
		criteria.eq("companyUnit.id", companyUnit.getId());
		List<ProductStatus> notStatuses = Lists.newArrayList(ProductStatus.DEL);
		criteria.notin("status", notStatuses);
		criteria.eq("showStatus", ShowStatus.SHOW);
		List<TicketType> types = Lists.newArrayList(TicketType.sailboat, TicketType.yacht, TicketType.huanguyou);
		criteria.in("ticketType", types);
		List<Ticket> ticketList;
		if (page == null) {
			ticketList = dao.findByCriteria(criteria);
		} else {
			ticketList = dao.findByCriteria(criteria, page);
		}
		for (Ticket t : ticketList) {
			t.setContactName(t.getCompanyUnit().getSysUnitDetail().getContactName());
		}
		return ticketList;
	}

	public void delete(Ticket ticket) {
		dao.delete(ticket);
	}

	public List<Product> findListBySql(Ticket ticket, Page page) {
		List params = Lists.newArrayList();
		StringBuffer sql = new StringBuffer();
		sql.append("select");
		sql.append(" p.id as id, p.name as name, min(tdp.priPrice) as price, pimg.path as imgUrl");
		sql.append(" from \n" +
				"product p left join ticket t on t.productId = p.id left join ticketprice tp on tp.ticketId = p.id \n" +
				"left join ticketdateprice tdp on tdp.ticketPriceId = tp.id left join comment c on c.targetId = p.id \n" +
				"left join comment_score cs on cs.commentId = c.id left join productimage pimg on pimg.productId = p.id");
		sql.append(" where p.origin_id is null");
		sql.append(" and pimg.coverFlag = 1");
		if (!ticket.getIncludeTicketTypeList().isEmpty() && ticket.getIncludeTicketTypeList().size() == 1) {
			sql.append(" and t.ticketType=?");
			params.add(ticket.getIncludeTicketTypeList().get(0).toString());
		} else if (!ticket.getIncludeTicketTypeList().isEmpty() && ticket.getIncludeTicketTypeList().size() == 2) {
			sql.append(" and (t.ticketType=? or t.ticketType=?)");
			params.add(ticket.getIncludeTicketTypeList().get(0).toString());
			params.add(ticket.getIncludeTicketTypeList().get(1).toString());
		} else if (!ticket.getIncludeTicketTypeList().isEmpty() && ticket.getIncludeTicketTypeList().size() == 3) {
			sql.append(" and (t.ticketType=? or t.ticketType=? or t.ticketType=?)");
			params.add(ticket.getIncludeTicketTypeList().get(0).toString());
			params.add(ticket.getIncludeTicketTypeList().get(1).toString());
			params.add(ticket.getIncludeTicketTypeList().get(2).toString());
		}
		if (ticket.getStatus() != null) {
			sql.append(" and p.status=?");
			params.add(ticket.getStatus().toString());
		}

		sql.append(" and tdp.huiDate>=?");
		params.add(DateUtils.format(DateUtils.getStartDay(new Date(), 0), "yyyy-MM-dd HH:mm:ss"));

		sql.append(" group by p.id order by sum(cs.score) desc");
		return dao.findEntitiesBySQL3(sql.toString(), page, Product.class, params.toArray());
	}

	public List<Ticket> getInIdRange(Long startId, Long endId, List<TicketType> ticketTypes, int size) {
		StringBuilder hql = new StringBuilder("from Ticket where id>:startId");
		Map<String, Object> data = Maps.newHashMap();
		data.put("startId", startId);
		if (endId != null) {
			hql.append(" and id<=:endId");
			data.put("endId", endId);
		}
		hql.append(" and status=:status");
		data.put("status", ProductStatus.UP);

		if (!ticketTypes.isEmpty() && ticketTypes.size() == 1) {
			hql.append(" and ticketType=:ticketType");
			data.put("ticketType", ticketTypes.get(0));
		} else if (!ticketTypes.isEmpty() && ticketTypes.size() == 2) {
			hql.append(" and (ticketType=:ticketType_1");
			data.put("ticketType_1", ticketTypes.get(0));
			hql.append(" or ticketType=:ticketType_2)");
			data.put("ticketType_2", ticketTypes.get(1));
		}
		return dao.findByHQL2(hql.toString(), new Page(1, size), data);
	}

	public Ticket findByTicketId(Long id){
		if (id != null) {
			return dao.get(Ticket.class, id);
		}
		return null;
	}
}
