package com.data.data.hmly.service.scenic;

import com.data.data.hmly.service.base.Solrconfig;
import com.data.data.hmly.service.scenic.entity.DataScenic;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.entity.ScenicOther;
import com.data.ikanalysis.utils.IKTokenUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrRequest.METHOD;
import org.apache.solr.client.solrj.SolrServerException;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SolrScenicMgrService {

    public List<DataScenic> findScenicByTitle(String key) throws Exception {
        String sname = IKTokenUtils.token(key);
        SolrQuery query = new SolrQuery(sname);
        return queryScenic(query);
    }

    public List<ScenicInfo> findScenicInfoByTitle(String key) throws Exception {
        String sname = IKTokenUtils.token(key);
        SolrQuery query = new SolrQuery(sname);
        return queryScenicInfo(query);
    }

    private List<DataScenic> queryScenic(SolrQuery query) throws SolrServerException {
        QueryResponse response = Solrconfig.SENIC.solrServer.query(query, METHOD.POST);
        SolrDocumentList docs = response.getResults();
        List<DataScenic> scenics = new ArrayList<DataScenic>();
        for (SolrDocument doc : docs) {
            Long id = (Long) doc.getFieldValue("id");
            String address = (String) doc.getFieldValue("address");
            String name = (String) doc.getFieldValue("name");
            String data_source = (String) doc.getFieldValue("data_source");
            String data_source_url = (String) doc.getFieldValue("data_source_url");
            DataScenic dataScenic = new DataScenic();
            dataScenic.setId(id);
            dataScenic.setAddress(address);
            dataScenic.setName(name);
            dataScenic.setDataSource(data_source);
            dataScenic.setDataSourceUrl(data_source_url);
            scenics.add(dataScenic);
        }
        return scenics;
    }

    private List<ScenicInfo> queryScenicInfo(SolrQuery query) throws SolrServerException {
        QueryResponse response = Solrconfig.SENICINFO.solrServer.query(query, METHOD.POST);
        SolrDocumentList docs = response.getResults();
        List<ScenicInfo> scenics = new ArrayList<ScenicInfo>();
        for (SolrDocument doc : docs) {
            Long id = (Long) doc.getFieldValue("id");
            String address = (String) doc.getFieldValue("address");
            String name = (String) doc.getFieldValue("name");
            ScenicInfo scenicInfo = new ScenicInfo();
            ScenicOther scenicOther = new ScenicOther();
            scenicInfo.setId(id);
            scenicOther.setAddress(address);
            scenicInfo.setScenicOther(scenicOther);
            scenicInfo.setName(name);
            scenics.add(scenicInfo);
        }
        return scenics;
    }

}
