package com.data.data.hmly.service.restaurant;

import com.data.data.hmly.service.common.SolrIndexService;
import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.restaurant.dao.RestaurantDao;
import com.data.data.hmly.service.restaurant.entity.Restaurant;
import com.data.data.hmly.service.restaurant.request.RestaurantSearchRequest;
import com.data.data.hmly.service.restaurant.vo.RestaurantSolrEntity;
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

/**
 * Created by guoshijie on 2015/12/8.
 */
@Service
public class RestaurantService extends SolrIndexService<Restaurant, RestaurantSolrEntity> {

	Logger logger = Logger.getLogger(RestaurantService.class);

	@Resource
	private RestaurantDao restaurantDao;
	@Resource
	private MulticoreSolrTemplate solrTemplate;

	public List<Restaurant> listAll() {
		return list(new Restaurant(), null);
	}

	public List<Restaurant> list(Restaurant restaurant, Page page, String... orderProperties) {
		Criteria<Restaurant> criteria = createCriteria(restaurant, orderProperties);
		if (page != null) {
			return restaurantDao.findByCriteria(criteria, page);
		}
		return restaurantDao.findByCriteria(criteria);
	}

    public Restaurant get(Long id) {
        return restaurantDao.get(Restaurant.class, id);
    }

    public Double getResScore(Long id) {
        String hql = "select new Restaurant(id, score) from Restaurant where id=?";
        Restaurant restaurant = restaurantDao.findByHQLWithUniqueObject(hql, id);
        if (restaurant == null || restaurant.getScore() <= 0) {
            return 100D;
        }
        return restaurant.getScore();
    }

	public Criteria<Restaurant> createCriteria(Restaurant restaurant, String... orderProperties) {
		Criteria<Restaurant> criteria = new Criteria<Restaurant>(Restaurant.class);
		if (orderProperties.length == 2) {
			criteria.orderBy(orderProperties[0], orderProperties[1]);
		} else if (orderProperties.length == 1) {
			criteria.orderBy(Order.desc(orderProperties[0]));
		}
		if (StringUtils.isNotBlank(restaurant.getName())) {
			criteria.like("name", restaurant.getName());
		}
		if (restaurant.getCity() != null) {
			criteria.eq("city.id", restaurant.getCity().getId());
		}
		if (restaurant.getIsShow() != null) {
			criteria.eq("isShow", restaurant.getIsShow());
		}
		if (restaurant.getStatus() != null) {
			criteria.eq("status", restaurant.getStatus());
		}
		if (restaurant.getCreatedBy() != null) {
			criteria.eq("createdBy.id", restaurant.getCreatedBy().getId());
		}
		return criteria;
	}


	public void indexRestaurants() {
		List<Restaurant> list = listAll();
		List<RestaurantSolrEntity> entities = Lists.transform(list, new Function<Restaurant, RestaurantSolrEntity>() {
			@Override
			public RestaurantSolrEntity apply(Restaurant restaurant) {
				return new RestaurantSolrEntity(restaurant);
			}
		});
		index(entities);
	}

	public void indexRestaurant(Restaurant restaurant) {
		try {
			RestaurantSolrEntity entity = new RestaurantSolrEntity(restaurant);
			index(Lists.newArrayList(entity));
		} catch (Exception e) {
			logger.error("未知异常，delicacy#" + restaurant.getId() + "索引失败", e);
		}
	}

	public List<RestaurantSolrEntity> listFromSolr(RestaurantSearchRequest request, Page page, String... facets) {

		solrTemplate.getSolrServerFactory().getSolrServer(CORE_NAME);
		SolrQuery query = createQuery(request);
		createFacet(query, facets);
		QueryResponse response = solrTemplate.query(query, CORE_NAME, page);
		return solrTemplate.convertQueryResponseToBeans(response, RestaurantSolrEntity.class);
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

	private SolrQuery createQuery(RestaurantSearchRequest request) {
		SolrQuery query = new SolrQuery();
		query.setQuery(request.getSolrQueryStr());
		if (request.getOrderColumn() != null && request.getOrderType() != null) {
			query.setSort(request.getOrderColumn(), request.getOrderType());
		}
		return query;
	}

	public List<RestaurantSolrEntity> findNearByRestaurant(Double longitude, Double latitude, Float distance, Page page) {
		SolrQuery query = new SolrQuery("type:" + SolrType.restaurant);
		query.setStart(page.getFirstResult());
		query.setRows(page.getPageSize());
		QueryResponse response = solrTemplate.nearBy(query, latitude + "", longitude + "", RestaurantSolrEntity.class, SolrQuery.ORDER.asc, distance);
		page.setTotalCount(Long.valueOf(response.getResults().getNumFound()).intValue());
		return solrTemplate.convertQueryResponseToBeans(response, RestaurantSolrEntity.class);
	}
}
