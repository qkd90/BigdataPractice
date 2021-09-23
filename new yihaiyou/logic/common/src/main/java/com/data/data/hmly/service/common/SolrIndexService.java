package com.data.data.hmly.service.common;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.request.SolrSearchRequest;
import com.data.data.hmly.service.common.vo.SolrEntity;
import com.data.data.hmly.service.common.vo.SuggestionEntity;
import com.data.data.solr.MulticoreSolrTemplate;
import com.framework.hibernate.util.Page;
import com.google.common.base.Function;
import com.google.common.collect.Lists;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrDocument;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Jonathan.Guo
 */
@Service
public class SolrIndexService<T, E extends SolrEntity<T>> {

	public static final String CORE_NAME = "products";
//	public static final String CORE_HOTEL_PRICE = "HotelPrice";

	@Resource
	private MulticoreSolrTemplate solrTemplate;

	public UpdateResponse index(List<E> entities) {
		UpdateResponse response = solrTemplate.saveBeans(entities);
		if (response == null) {
			return response;
		}
		response = solrTemplate.commit(CORE_NAME);
		return response;
	}

	public void index(List<E> entities, String coreName) {
		solrTemplate.saveBeans(entities);
		solrTemplate.commit(coreName);
	}

	public long countFromSolr(SolrSearchRequest request) {
		solrTemplate.getSolrServerFactory().getSolrServer(CORE_NAME);
		SolrQuery query = createQuery(request);
		QueryResponse response = solrTemplate.query(query, CORE_NAME);
		if (response == null || response.getResults() == null) {
			return 0;
		}
		return response.getResults().getNumFound();
	}

	public List<E> listFromSolr(SolrSearchRequest request, Page page, String... facets) {

		solrTemplate.getSolrServerFactory().getSolrServer(CORE_NAME);
		SolrQuery query = createQuery(request);
		createFacet(query, facets);
		QueryResponse response = solrTemplate.query(query, CORE_NAME, page);
		if (response == null || response.getResults() == null) {
			return new ArrayList<E>();
		}
		page.setTotalCount(Long.valueOf(response.getResults().getNumFound()).intValue());
		return solrTemplate.convertQueryResponseToBeans(response, request.getResultClass());
	}

	public List<SuggestionEntity> suggest(String query, int num) {

		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(query);
		QueryResponse response = solrTemplate.query(solrQuery, CORE_NAME, new Page(0, num));
		return Lists.transform(response.getResults(), new Function<SolrDocument, SuggestionEntity>() {
			@Override
			public SuggestionEntity apply(SolrDocument entries) {
                SuggestionEntity suggestion = new SuggestionEntity();
                suggestion.setId(entries.get("id").toString());
                suggestion.setName(entries.get("name").toString());
                suggestion.setType(entries.get("type").toString());
//				suggestion.setCityId(entries.get("cityId").toString());
                return suggestion;
            }
        });
	}

    public UpdateResponse deleteIndexByEntityId(Long id, SolrType solrType) {
		String query = "type: " + solrType + " AND id: " + id;
		return solrTemplate.deleteByQuery(query, CORE_NAME);
    }

	public UpdateResponse deleteIndexBy(String prop, String value, String codeName) {
		String query = prop + ":" + value;
		return solrTemplate.deleteByQuery(query, codeName);
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

	private SolrQuery createQuery(SolrSearchRequest request) {
		SolrQuery query = new SolrQuery();
		query.setQuery(request.getSolrQueryStr());
		if (request.getOrderColumn() != null && request.getOrderType() != null) {
			query.setSort(request.getOrderColumn(), request.getOrderType());
		}
		return query;
	}

	public List<SuggestionEntity> listPlace(String query, Page page) {
		SolrQuery solrQuery = new SolrQuery();
		solrQuery.setQuery(query);
		QueryResponse response = solrTemplate.query(solrQuery, CORE_NAME, page);
		page.setTotalCount(Long.valueOf(response.getResults().getNumFound()).intValue());
		return Lists.transform(response.getResults(), new Function<SolrDocument, SuggestionEntity>() {
			@Override
			public SuggestionEntity apply(SolrDocument entries) {
				SuggestionEntity suggestion = new SuggestionEntity();
				suggestion.setId(entries.get("id").toString());
				suggestion.setName(entries.get("name").toString());
				suggestion.setType(entries.get("type").toString());
				return suggestion;
			}
		});
	}
}
