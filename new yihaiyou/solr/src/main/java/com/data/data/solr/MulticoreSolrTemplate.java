package com.data.data.solr;

import com.framework.hibernate.util.Page;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.request.QueryRequest;
import org.apache.solr.client.solrj.request.UpdateRequest;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.client.solrj.response.UpdateResponse;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.data.solr.core.SolrTemplate;
import org.springframework.data.solr.core.convert.SolrConverter;
import org.springframework.data.solr.core.mapping.SolrDocument;
import org.springframework.data.solr.core.query.Query;
import org.springframework.data.solr.core.query.result.ScoredPage;
import org.springframework.data.solr.server.SolrServerFactory;

import java.util.Collection;
import java.util.List;

public class MulticoreSolrTemplate extends SolrTemplate {

    public static final ThreadLocal<String> THREAD_LOCAL = new ThreadLocal<String>();
    private final Logger log = Logger.getLogger(MulticoreSolrTemplate.class);
    private MulticoreFactory solrServerFactory;

    public MulticoreSolrTemplate(SolrServer solrServer) {
        super(solrServer);
    }

    public MulticoreSolrTemplate(SolrServer solrServer, String core) {
        super(solrServer, core);
    }

    public MulticoreSolrTemplate(SolrServerFactory solrServerFactory) {
        super(solrServerFactory);
    }

    public MulticoreSolrTemplate(SolrServerFactory solrServerFactory, SolrConverter solrConverter) {
        super(solrServerFactory, solrConverter);
    }

    public UpdateResponse commit(String coreName) {
        int i = 0;
        do {
            try {
                SolrServer solrServer = solrServerFactory.getSolrServer(coreName);
                return new UpdateRequest(String.format("/%s/update", coreName)).setAction(UpdateRequest.ACTION.COMMIT, true, true).process(
                        solrServer);
                // return solrServer.commit();
            } catch (Exception e) {
                // TODO: handle exception
                log.error(e.getMessage(), e);
            }
        } while (i++ < 3);
        return null;
    }

    public UpdateResponse commit(Class<?> clazz) {
        int i = 0;
        do {
            try {
                SolrDocument document = clazz.getAnnotation(SolrDocument.class);
                return commit(document.solrCoreName());
            } catch (Exception e) {
                // TODO: handle exception
                log.error(e.getMessage(), e);
            }
        } while (i++ < 3);
        return null;
    }

    public QueryResponse query(SolrQuery query, String coreName) {
        int i = 0;
        do {
            try {
                QueryRequest request = new QueryRequest(query);
                request.setPath(String.format("/%s/select", coreName));
                SolrServer solrServer = getSolrServerFactory().getSolrServer(coreName);
                return request.process(solrServer);
            } catch (Exception e) {
                // TODO: handle exception
                log.error(e.getMessage(), e);
            }
        } while (i++ < 3);
        return null;
    }

    public UpdateResponse deleteById(List<String> ids, String coreName) {
        int i = 0;
        do {
            try {
                SolrServer solrServer = getSolrServerFactory().getSolrServer(coreName);
                UpdateRequest req = new UpdateRequest(String.format("/%s/update", coreName));
                req.setAction(UpdateRequest.ACTION.COMMIT, true, true);
                req.deleteById(ids);
                return req.process(solrServer);
            } catch (Exception e) {
                // TODO: handle exception
                log.error(e.getMessage(), e);
            }
        } while (i++ < 3);
        return null;
    }

    public UpdateResponse deleteByQuery(String query, String coreName) {
        int i = 0;
        do {
            try {
                SolrServer solrServer = getSolrServerFactory().getSolrServer(coreName);
                UpdateRequest req = new UpdateRequest(String.format("/%s/update", coreName));
                req.setAction(UpdateRequest.ACTION.COMMIT, true, true);
                req.deleteByQuery(query);
                return req.process(solrServer);
            } catch (Exception e) {
                log.error(e.getMessage(), e);
            }
        } while (i++ < 3);
        return null;
    }

    public UpdateResponse deleteById(String ids, String coreName) {
        int i = 0;
        do {
            try {
                SolrServer solrServer = getSolrServerFactory().getSolrServer(coreName);
                solrServer.deleteById(ids);
                return commit(coreName);
            } catch (Exception e) {
                // TODO: handle exception
                log.error(e.getMessage(), e);
            }
        } while (i++ < 3);
        return null;
    }

    @Override
    public <T> ScoredPage<T> queryForPage(Query query, Class<T> clazz) {
        // TODO Auto-generated method stub
        int i = 0;
        do {
            SolrDocument document = clazz.getAnnotation(SolrDocument.class);
            THREAD_LOCAL.set(document.solrCoreName());
            try {
                return super.queryForPage(query, clazz);
            } catch (Exception e) {
                // TODO: handle exception
                log.error(e.getMessage(), e);
            } finally {
                THREAD_LOCAL.remove();
            }
        } while (i++ < 3);
        return null;
    }

    @Override
    public <T> T queryForObject(Query query, Class<T> clazz) {
        // TODO Auto-generated method stub
        int i = 0;
        do {
            SolrDocument document = clazz.getAnnotation(SolrDocument.class);
            THREAD_LOCAL.set(document.solrCoreName());
            try {
                return super.queryForObject(query, clazz);
            } catch (Exception e) {
                // TODO: handle exception
                log.error(e.getMessage(), e);
            } finally {
                THREAD_LOCAL.remove();
            }
        } while (i++ < 3);
        return null;
    }

    @Override
    public UpdateResponse saveBean(Object obj) {
        // TODO Auto-generated method stub
        int i = 0;
        do {
            SolrDocument document = obj.getClass().getAnnotation(SolrDocument.class);
            THREAD_LOCAL.set(document.solrCoreName());
            try {
                return super.saveBean(obj);
            } catch (Exception e) {
                // TODO: handle exception
                log.error(e.getMessage(), e);
            } finally {
                THREAD_LOCAL.remove();
            }
        } while (i++ < 3);
        return null;
    }

    public UpdateResponse saveBeans(List<?> objs) {
        // TODO Auto-generated method stub
        int i = 0;
        do {
            if (objs == null || objs.isEmpty()) {
                return null;
            }
            SolrDocument document = objs.get(0).getClass().getAnnotation(SolrDocument.class);
            THREAD_LOCAL.set(document.solrCoreName());
            try {
                return super.saveBeans(objs);
            } catch (Exception e) {
                // TODO: handle exception
                log.error(e.getMessage(), e);
            } finally {
                THREAD_LOCAL.remove();
            }
        } while (i++ < 3);
        return null;
    }

    public UpdateResponse saveDocuments(Collection<SolrInputDocument> documents, Class<?> clazz) {
        // TODO Auto-generated method stub
        int i = 0;
        do {
            if (documents == null || documents.isEmpty()) {
                return null;
            }
            SolrDocument document = clazz.getAnnotation(SolrDocument.class);
            THREAD_LOCAL.set(document.solrCoreName());
            try {
                return super.saveDocuments(documents);
            } catch (Exception e) {
                // TODO: handle exception
                log.error(e.getMessage(), e);
            } finally {
                THREAD_LOCAL.remove();
            }
        } while (i++ < 3);
        return null;
    }

    public UpdateResponse saveDocument(String coreName, SolrInputDocument document) {
        // TODO Auto-generated method stub
        int i = 0;
        do {
            THREAD_LOCAL.set(coreName);
            try {
                return super.saveDocument(document);
            } catch (Exception e) {
                // TODO: handle exception
                log.error(e.getMessage(), e);
            } finally {
                THREAD_LOCAL.remove();
            }
        } while (i++ < 3);
        return null;
    }

    public QueryResponse nearBy(SolrQuery query, String lat, String lng, Class<?> clazz, SolrQuery.ORDER order, Float dis) {
//        &fq={!geofilt}&pt=38.882397,121.684126&sfield=latlng&d=5&sort=geodist()+asc
//        SolrQuery query = new SolrQuery(q);
        int i = 0;
        do {
            query.add("fq", "{!geofilt}");
            query.add("pt", String.format("%s,%s", lat, lng));
            query.add("d", String.valueOf(dis));
            query.add("sfield", "latlng");
            query.addSort("geodist()", order);
            try {
                SolrDocument document = clazz.getAnnotation(SolrDocument.class);
                QueryRequest request = new QueryRequest(query);
                request.setPath(String.format("/%s/select", document.solrCoreName()));
                SolrServer solrServer = getSolrServerFactory().getSolrServer(document.solrCoreName());
                return request.process(solrServer);
            } catch (Exception e) {
                // TODO: handle exception
                log.error(e.getMessage(), e);
            }
        } while (i++ < 3);
        return null;
    }

    public MulticoreFactory getSolrServerFactory() {
        return solrServerFactory;
    }

    public void setSolrServerFactory(MulticoreFactory solrServerFactory) {
        this.solrServerFactory = solrServerFactory;
    }

    public QueryResponse query(SolrQuery query, String coreName, Page page) {
        int i = 0;
        do {
            query.setStart(page.getFirstResult());
            query.setRows(page.getPageSize());
            try {
                QueryRequest request = new QueryRequest(query);
                request.setPath(String.format("/%s/select", coreName));
                SolrServer solrServer = getSolrServerFactory().getSolrServer(coreName);
                return request.process(solrServer);
            } catch (Exception e) {
                // TODO: handle exception
                log.error(e.getMessage(), e);
            }
        } while (i++ < 3);
        return null;
    }

}
