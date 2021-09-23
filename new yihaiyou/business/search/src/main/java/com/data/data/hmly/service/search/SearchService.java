package com.data.data.hmly.service.search;

import java.util.List;

import javax.annotation.Resource;

import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrPageRequest;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.stereotype.Service;

import com.data.data.hmly.service.search.entitysearch.QuickSearch;
import com.data.data.solr.MulticoreSolrTemplate;
import com.zuipin.util.CollectionUtils;

@Service
public class SearchService {

	@Resource
	private MulticoreSolrTemplate solrTemplate;

	public List<Suggestion> querySugguest(String core, String name) throws SolrServerException {
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("qt", String.format("/%s/suggest", core));// spellhandler
		params.set("q", name);
		// /CommonParams.QT 条件命名
		params.set("spellcheck", "on");
		params.set("spellcheck.build", "true");
		params.set("spellcheck.count", "10");
		QueryResponse response = solrTemplate.getSolrServer().query(params);
		SpellCheckResponse spellCheckResponse = response.getSpellCheckResponse();
		if (spellCheckResponse != null) {
			List<Suggestion> suggestionList = spellCheckResponse.getSuggestions();
			return suggestionList;
			// for (Suggestion suggestion : suggestionList) {
			// List<String> suggestWordList = suggestion.getAlternatives();
			// for (String string : suggestWordList) {
			// System.out.println(string);
			// }
			// }
		}
		return null;
	}

	public ScoredPage<QuickSearch> QuciSearch(String name, List<String> types) throws SolrServerException {
		SimpleQuery query = new SimpleQuery();
		Criteria nameC = new Criteria("name");
		nameC.is(name);
		query.addCriteria(nameC);
		if (CollectionUtils.isNotEmpty(types)) {
			Criteria typeC = new Criteria("type");
			typeC.is(types);
			query.addCriteria(typeC);
		}
		SolrPageRequest pageRequest = new SolrPageRequest(0, 20);
		query.setPageRequest(pageRequest);
		ScoredPage<QuickSearch> page = solrTemplate.queryForPage(query, QuickSearch.class);
		for (QuickSearch quickSearch : page) {
			System.out.println(String.format("%s", quickSearch.getName()));
		}
		return page;
	}

}
