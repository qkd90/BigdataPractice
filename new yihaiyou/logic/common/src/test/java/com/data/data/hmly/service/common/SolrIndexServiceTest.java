package com.data.data.hmly.service.common;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.request.SolrSearchRequest;
import com.data.data.hmly.service.common.vo.SolrEntity;
import com.data.data.hmly.service.common.vo.SuggestionEntity;
import com.framework.hibernate.util.Page;
import com.google.common.collect.Lists;
import com.zuipin.util.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.beans.Field;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.util.Assert;

import javax.annotation.Resource;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
@Ignore
public class SolrIndexServiceTest {

    @Resource
    private SolrIndexService<TestEntity, TestSolrEntity> service;

    @Test
    public void testIndex() throws Exception {
        List<TestSolrEntity> entities = Lists.newArrayList();
        for (int i = 1; i <= 5; i++) {
            TestSolrEntity entity = new TestSolrEntity();
            entity.id = i;
            entity.name = "test" + i;
            entity.typeid = entity.type.name() + i;
            entities.add(entity);
        }
        service.index(entities);
    }

    @Test
    public void testCountFromSolr() throws Exception {
        TestSearchRequest request = new TestSearchRequest();
        int result = Long.valueOf(service.countFromSolr(request)).intValue();
        Assert.isTrue(result == 5);
        request.name = "1";
        result = Long.valueOf(service.countFromSolr(request)).intValue();
        Assert.isTrue(result == 1);
    }

    @Test
    public void testListFromSolr() throws Exception {
        TestSearchRequest request = new TestSearchRequest();
        List<TestSolrEntity> list = service.listFromSolr(request, new Page(0, 10));
        Assert.isTrue((list.size() == 5));
        request.name = "1";
        list = service.listFromSolr(request, new Page(0, 10));
        Assert.isTrue((list.size() == 1));
        Assert.isTrue(list.get(0).name.equals("test1"));
        request.name = "3";
        list = service.listFromSolr(request, new Page(0, 10));
        Assert.isTrue((list.size() == 1));
        Assert.isTrue(list.get(0).name.equals("test3"));

    }

    @Test
    public void testSuggest() throws Exception {
        String query = "name:test AND type:test";
        List<SuggestionEntity> suggestions = service.suggest(query, 10);
        Assert.isTrue(suggestions.size() == 5);
    }

    @SolrDocument(solrCoreName = "products")
    public class TestSolrEntity extends SolrEntity<TestEntity> {
        @Field
        long id;
        @Field
        String name;
        @Field
        SolrType type = SolrType.test;
        @Field
        String typeid;

    }

    public class TestEntity {
        long id;
        String name;
    }

    public class TestSearchRequest extends SolrSearchRequest {

        String name;

        @Override
        public String getSolrQueryStr() {
            String query = "type:test";
            if (StringUtils.isNotBlank(name)) {
                return query + " AND " + "name:" + name;
            }
            return query;
        }

        @Override
        public String getOrderColumn() {
            return "id";
        }

        @Override
        public SolrQuery.ORDER getOrderType() {
            return SolrQuery.ORDER.desc;
        }

        @Override
        public Class getResultClass() {
            return TestSolrEntity.class;
        }
    }

}