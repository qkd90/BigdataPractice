package solr;

import org.apache.commons.lang3.time.StopWatch;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.query.Criteria;
import org.springframework.data.solr.core.query.SimpleQuery;
import org.springframework.data.solr.core.query.SolrPageRequest;
import org.springframework.data.solr.core.query.result.ScoredPage;

public class SpringSolr {

	private static ApplicationContext	ac;

	private final static StopWatch		stopWatch	= new StopWatch();

	public void setup() {
		ac = new ClassPathXmlApplicationContext("classpath:applicationContext.xml");
		SolrTemplate solrTemplate = (SolrTemplate) ac.getBean("solrTemplate");
		Criteria criteria = new Criteria();
		SimpleQuery query = new SimpleQuery();
		query.addCriteria(criteria);
		SolrPageRequest pageRequest = new SolrPageRequest(1, 20);
		query.setPageRequest(pageRequest);
		stopWatch.start();
		ScoredPage<Scenic> page = solrTemplate.queryForPage(query, Scenic.class);
		stopWatch.stop();
		System.out.println(stopWatch);
		for (Scenic scenic : page) {
			System.out.println(String.format("%s %s %s", scenic.getAddress(), scenic.getData_source(), scenic.getData_source_url()));
		}
	}

	public static void main(String[] args) {
		SpringSolr solr = new SpringSolr();
		solr.setup();
	}

}
