package com.data.data.hmly.service.search;

import java.util.List;

import org.apache.commons.lang3.time.StopWatch;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse;
import org.apache.solr.client.solrj.response.SpellCheckResponse.Suggestion;
import org.apache.solr.common.params.ModifiableSolrParams;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrPageRequest;
import org.springframework.data.solr.core.query.result.ScoredPage;

import com.data.data.hmly.service.search.entitysearch.QuickSearch;

public class SpringSolr {

	private static ApplicationContext ac;

	private final static StopWatch stopWatch = new StopWatch();

	public void setup() throws SolrServerException {
		ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		SolrTemplate solrTemplate = (SolrTemplate) ac.getBean("solrTemplate");
		ModifiableSolrParams params = new ModifiableSolrParams();
		params.set("qt", "/quicksearch/suggest");// spellhandler
		params.set("q", "鼓");
		// /CommonParams.QT 条件命名
		params.set("spellcheck", "on");
		params.set("spellcheck.build", "true");
		params.set("spellcheck.count", "10");

		stopWatch.start();
		QueryResponse response = solrTemplate.getSolrServer().query(params);
		stopWatch.stop();
		System.out.println(stopWatch);
		SpellCheckResponse spellCheckResponse = response.getSpellCheckResponse();
		if (spellCheckResponse != null) {
			List<Suggestion> suggestionList = spellCheckResponse.getSuggestions();
			for (Suggestion suggestion : suggestionList) {
				List<String> suggestWordList = suggestion.getAlternatives();
				for (String string : suggestWordList) {
					System.out.println(string);
				}
			}
		}

	}

	public void QuciSearch() throws SolrServerException {
		ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		SolrTemplate solrTemplate = (SolrTemplate) ac.getBean("solrTemplate");
		SimpleQuery query = new SimpleQuery();
		Criteria name = new Criteria("name");
		// name.is("鼓浪屿");
		name.is("南瓜");
		query.addCriteria(name);
		stopWatch.reset();
		stopWatch.start();
		query.addProjectionOnFields("name");
		SolrPageRequest pageRequest = new SolrPageRequest(0, 20);
		query.setPageRequest(pageRequest);
		ScoredPage<QuickSearch> page = solrTemplate.queryForPage(query, QuickSearch.class);
		stopWatch.stop();
		System.out.println(stopWatch);
		for (QuickSearch quickSearch : page) {
			System.out.println(String.format("%s", quickSearch.getName()));
		}
	}

	public static void main(String[] args) throws SolrServerException {
		SpringSolr solr = new SpringSolr();
		// solr.setup();
		solr.QuciSearch();
	}

}
