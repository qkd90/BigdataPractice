package com.data.data.hmly.service.area.request;

import com.data.data.hmly.service.area.vo.TbAreaSolrEntity;
import com.data.data.hmly.service.common.request.SolrSearchRequest;
import com.zuipin.util.StringUtils;

import org.apache.solr.client.solrj.SolrQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class TbAreaSearchRequest extends SolrSearchRequest {

    private Long cityCode;
    private String name;
    private String orderColumn;
    private SolrQuery.ORDER orderType;
    private List<Long> labelIds = new ArrayList<Long>();

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public SolrQuery.ORDER getOrderType() {
        return orderType == null ? SolrQuery.ORDER.desc : orderType;
    }

    public void setOrderType(SolrQuery.ORDER orderType) {
        this.orderType = orderType;
    }

    public Long getCityCode() {
        return cityCode;
    }

    public void setCityCode(Long cityCode) {
        this.cityCode = cityCode;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSolrQueryStr() {
        StringBuilder builder = new StringBuilder();
        Set<String> set = new HashSet<String>();
        if (cityCode != null) {
            set.add(String.format("id:%s", cityCode));
        }
        if (StringUtils.isNotBlank(name)) {
            set.add(String.format("name:%s", name));
        }
        if (!labelIds.isEmpty()) {
			for (Long labelId : labelIds) {
				set.add(String.format("labelIds:%s", labelId));
			}
		}
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

    @Override
    public Class getResultClass() {
        return TbAreaSolrEntity.class;
    }

	public List<Long> getLabelIds() {
		return labelIds;
	}

	public void setLabelIds(List<Long> labelIds) {
		this.labelIds = labelIds;
	}
}