package com.data.data.hmly.service.area;

import com.data.data.hmly.service.area.dao.DestinationDao;
import com.data.data.hmly.service.area.entity.Destination;
import com.data.data.hmly.service.area.request.DestinationSearchRequest;
import com.data.data.hmly.service.area.vo.DestinationSolrEntity;
import com.data.data.hmly.util.Clock;
import com.data.data.solr.MulticoreSolrTemplate;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class DestinationService {

	Logger logger = Logger.getLogger(DestinationService.class);

	private static final String CORE_NAME = "destination";

	@Resource
	private DestinationDao destinationDao;
	@Resource
	private MulticoreSolrTemplate solrTemplate;

	public List<Destination> listTopOnIndex(int count) {
		Destination destination = new Destination();
		destination.setShowIndex(true);
		return list(destination, new Page(0, count));
	}

	public List<Destination> listAll() {
		return list(new Destination(), null);
	}

	public List<Destination> list(Destination destination, Page page, String... orderProperties) {
		Criteria<Destination> criteria = createCriteria(destination, orderProperties);
		if (page == null) {
			return destinationDao.findByCriteria(criteria);
		}
		return destinationDao.findByCriteria(criteria, page);
	}

	public Criteria<Destination> createCriteria(Destination destination, String... orderProperties) {
		Criteria<Destination> criteria = new Criteria<Destination>(Destination.class);
		if (orderProperties.length == 2) {
			criteria.orderBy(orderProperties[0], orderProperties[1]);
		} else if (orderProperties.length == 1) {
			criteria.orderBy(Order.desc(orderProperties[0]));
		}
		if (StringUtils.isNotBlank(destination.getName())) {
			criteria.like("name", destination.getName());
		}
		if (StringUtils.isNotBlank(destination.getCodeName())) {
			criteria.like("codeName", destination.getCodeName());
		}
		if (StringUtils.isNotBlank(destination.getCityCode())) {
			criteria.like("cityCode", destination.getCityCode());
		}
		if (StringUtils.isNotBlank(destination.getArea())) {
			criteria.like("area", destination.getArea());
		}
		if (StringUtils.isNotBlank(destination.getSurvey())) {
			criteria.like("survey", destination.getSurvey());
		}
		if (StringUtils.isNotBlank(destination.getDays())) {
			criteria.like("days", destination.getDays());
		}
		if (StringUtils.isNotBlank(destination.getScenics())) {
			criteria.like("scenics", destination.getScenics());
		}
		if (StringUtils.isNotBlank(destination.getMonths())) {
			criteria.like("months", destination.getMonths());
		}
		if (StringUtils.isNotBlank(destination.getStyles())) {
			criteria.like("styles", destination.getStyles());
		}
		if (StringUtils.isNotBlank(destination.getLanguage())) {
			criteria.like("language", destination.getLanguage());
		}
		if (StringUtils.isNotBlank(destination.getDaysRecommend())) {
			criteria.like("daysRecommend", destination.getDaysRecommend());
		}
		if (StringUtils.isNotBlank(destination.getConsumer())) {
			criteria.like("consumer", destination.getConsumer());
		}
		if (StringUtils.isNotBlank(destination.getFeature())) {
			criteria.like("feature", destination.getFeature());
		}
		if (StringUtils.isNotBlank(destination.getTripTips())) {
			criteria.like("tripTips", destination.getTripTips());
		}
		if (StringUtils.isNotBlank(destination.getSeasons())) {
			criteria.like("seasons", destination.getSeasons());
		}
		if (StringUtils.isNotBlank(destination.getCustom())) {
			criteria.like("custom", destination.getCustom());
		}
		if (destination.getMapLevel() != null) {
			criteria.eq("mapLevel", destination.getMapLevel());
		}
		if (destination.getHdMinLevel() != null) {
			criteria.eq("hdMinLevel", destination.getHdMinLevel());
		}
		if (destination.getHdMaxLevel() != null) {
			criteria.eq("hdMaxLevel", destination.getHdMaxLevel());
		}
		if (destination.getAdvDay() != null) {
			criteria.eq("advDay", destination.getAdvDay());
		}
		if (destination.getAdvCost() != null) {
			criteria.eq("advCost", destination.getAdvCost());
		}
		if (destination.getMinDay() != null) {
			criteria.eq("minDay", destination.getMinDay());
		}
		if (destination.getMaxDay() != null) {
			criteria.eq("maxDay", destination.getMaxDay());
		}
		if (destination.getShowIndex() != null) {
			criteria.eq("showIndex", destination.getShowIndex());
		}

		return criteria;
	}



	public void indexDelicacy() {
		Clock clock = new Clock();
		List<Destination> list = listAll();
		logger.info("start indexing delicacy, count: " + list.size());

		List<DestinationSolrEntity> entities = Lists.transform(list, new Function<Destination, DestinationSolrEntity>() {
			@Override
			public DestinationSolrEntity apply(Destination delicacy) {
				return new DestinationSolrEntity(delicacy);
			}
		});
		solrTemplate.saveBeans(entities);
		solrTemplate.commit(CORE_NAME);
		logger.info("index delicacy success, cost: " + clock.elapseTime());
	}

	public void indexDelicacy(Destination destination) {
		try {
			DestinationSolrEntity entity = new DestinationSolrEntity(destination);
			solrTemplate.saveBean(entity);
			solrTemplate.commit(CORE_NAME);
		} catch (Exception e) {
			logger.error("未知异常，destination#" + destination.getId() + "索引失败");
		}
	}

	public long countFromSolr(DestinationSearchRequest request) {
		solrTemplate.getSolrServerFactory().getSolrServer(CORE_NAME);
		SolrQuery query = createQuery(request);
		QueryResponse response = solrTemplate.query(query, CORE_NAME);
		return response.getResults().getNumFound();
	}

	public List<DestinationSolrEntity> listFromSolr(DestinationSearchRequest request, Page page, String... facets) {

		solrTemplate.getSolrServerFactory().getSolrServer(CORE_NAME);
		SolrQuery query = createQuery(request);
		createFacet(query, facets);
		QueryResponse response = solrTemplate.query(query, CORE_NAME, page);
		return solrTemplate.convertQueryResponseToBeans(response, DestinationSolrEntity.class);
	}

	private void createFacet(SolrQuery query, String... facets) {
		if (facets.length < 1) {
			return;
		}
		query.setFacet(true);
		query.addFacetField(facets); // 设置需要facet的字段
		query.setFacetLimit(Integer.MAX_VALUE);
		query.setFacetMissing(false);
		query.setFacetMinCount(1);
	}

	private SolrQuery createQuery(DestinationSearchRequest request) {
		SolrQuery query = new SolrQuery();
		query.setQuery(request.getSolrQueryStr());
		if (request.getOrderColumn() != null && request.getOrderType() != null) {
			query.setSort(request.getOrderColumn(), request.getOrderType());
		}
		return query;
	}


}
