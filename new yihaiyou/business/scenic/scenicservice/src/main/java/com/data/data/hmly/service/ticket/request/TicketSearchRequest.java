package com.data.data.hmly.service.ticket.request;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.request.SolrSearchRequest;
import com.data.data.hmly.service.ticket.vo.TicketSolrEntity;
import com.google.common.collect.Lists;
import com.zuipin.util.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by huangpeijie on 2015-12-24,0024.
 */
public class TicketSearchRequest extends SolrSearchRequest {
    private Long id;
    private String name;
    private Long scenicId;
    private String ticketType;
    private List<String> ticketTypes;
    private String orderColumn;
    private SolrQuery.ORDER orderType;
    private List<Integer> priceRange = Lists.newArrayList();
    private String supplierName;
    private final SolrType type = SolrType.scenic;

    @Override
    public Class getResultClass() {
        return TicketSolrEntity.class;
    }

    @Override
    public String getSolrQueryStr() {
        Set<String> set = new HashSet<String>();
        if (id != null) {
            set.add(String.format("id:%d", id));
        }
        if (name != null) {
            set.add(String.format("name:%s", name));
        }

        if (scenicId != null) {
            set.add(String.format("scenicId:%d", scenicId));
        }
        if (StringUtils.isNotBlank(supplierName)) {
            set.add(String.format("supplierName:%s", supplierName));
        }

        if (!priceRange.isEmpty()) {
            if (priceRange.size() == 1) {
                set.add(String.format("disCountPrice:[%s TO %s]", priceRange.get(0), Integer.MAX_VALUE));
            } else if (priceRange.size() == 2) {
                set.add(String.format("disCountPrice:[%s TO %s]", priceRange.get(0), priceRange.get(1)));
            }
        }

        if (ticketTypes != null) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (String type : ticketTypes) {
                if (sb.length() > 1) {
                    sb.append(" OR ");
                }
                sb.append("ticketType:").append(type);
            }
            set.add(sb.append(")").toString());
        }
        set.add(String.format("type:%s", type));
        StringBuilder builder = new StringBuilder();
        for (String param : set) {
            if (builder.length() > 0) {
                builder.append(" AND ");
            }
            builder.append(param);
        }
        return builder.toString();
    }

    @Override
    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    @Override
    public SolrQuery.ORDER getOrderType() {
        return orderType;
    }

    public void setOrderType(SolrQuery.ORDER orderType) {
        this.orderType = orderType;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    public SolrType getType() {
        return type;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTicketType() {
        return ticketType;
    }

    public void setTicketType(String ticketType) {
        this.ticketType = ticketType;
    }

    public List<String> getTicketTypes() {
        return ticketTypes;
    }

    public void setTicketTypes(List<String> ticketTypes) {
        this.ticketTypes = ticketTypes;
    }

    public List<Integer> getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(List<Integer> priceRange) {
        this.priceRange = priceRange;
    }

    public String getSupplierName() {
        return supplierName;
    }

    public void setSupplierName(String supplierName) {
        this.supplierName = supplierName;
    }
}
