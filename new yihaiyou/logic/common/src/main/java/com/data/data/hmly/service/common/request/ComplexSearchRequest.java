package com.data.data.hmly.service.common.request;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.vo.ComplexSolrEntity;
import com.data.ikanalysis.utils.IKTokenUtils;
import com.zuipin.util.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by zzl on 2016/1/11.
 */
public class ComplexSearchRequest extends SolrSearchRequest {

    private String name;
    private SolrType type;
    private String orderColumn;
    private SolrQuery.ORDER orderType;


    public void setName(String name) {
        this.name = name;
    }

    public SolrType getType() {
        return type;
    }

    public void setType(SolrType type) {
        this.type = type;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public void setOrderType(SolrQuery.ORDER orderType) {
        this.orderType = orderType;
    }

    @Override
    public Class getResultClass() {
        return ComplexSolrEntity.class;
    }

    @Override
    public SolrQuery.ORDER getOrderType() {
        return orderType == null ? SolrQuery.ORDER.desc : orderType;
    }

    public String getName() {
        return name;
    }

    @Override
    public String getOrderColumn() {
        return orderColumn;
    }

    @Override
    public String getSolrQueryStr() {
        StringBuilder builder = new StringBuilder();
        Set<String> set = new HashSet<String>();
        String keywordToken = "";
        if (StringUtils.isNotBlank(name)) {
            try {
                keywordToken = IKTokenUtils.token(name);
                StringBuilder tokenSb = new StringBuilder();
                String[] tokenArr = keywordToken.split(" +");
                tokenSb.append("(");
                for (String s : tokenArr) {
                    tokenSb.append("name:").append(s).append(" OR ");
                }
                tokenSb.delete(tokenSb.length() - 4, tokenSb.length());
                tokenSb.append(")");
                keywordToken = tokenSb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        StringBuilder sb = new StringBuilder();
        sb.append(keywordToken);
        sb.append("AND (");
        sb.append("type:").append(type);
//        sb.append(" OR ");
//        sb.append("type:").append(SolrType.delicacy);
//        sb.append(" OR ");
//        sb.append("type:").append(SolrType.hotel);
//        sb.append(" OR ");
//        sb.append("type:").append(SolrType.transportation);
        set.add(sb.append(")").toString());
        if (set.isEmpty()) {
            return "*:*";
        }

        for (String param : set) {
            if (builder.length() > 0) {
                builder.append(" AND ");
            }
            builder.append(param);
        }
        return builder.toString();
    }
}
