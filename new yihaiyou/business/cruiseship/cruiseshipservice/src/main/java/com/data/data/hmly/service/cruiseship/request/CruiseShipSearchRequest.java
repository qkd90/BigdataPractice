package com.data.data.hmly.service.cruiseship.request;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.request.SolrSearchRequest;
import com.data.data.hmly.service.cruiseship.vo.CruiseShipSolrEntity;
import com.data.ikanalysis.utils.IKTokenUtils;
import com.google.common.collect.Lists;
import com.zuipin.util.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by huangpeijie on 2016-09-22,0022.
 */
public class CruiseShipSearchRequest extends SolrSearchRequest {
    private String name;
    private List<Integer> priceRange = Lists.newArrayList();
    private List<Integer> dayRange = Lists.newArrayList();
    private List<String> dateRange = Lists.newArrayList();
    private String date;
    private Long brand;
    private Long route;
    private String orderColumn;
    private SolrQuery.ORDER orderType;
    private final SolrType type = SolrType.cruise_ship;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(List<Integer> priceRange) {
        this.priceRange = priceRange;
    }

    public List<Integer> getDayRange() {
        return dayRange;
    }

    public void setDayRange(List<Integer> dayRange) {
        this.dayRange = dayRange;
    }

    public List<String> getDateRange() {
        return dateRange;
    }

    public void setDateRange(List<String> dateRange) {
        this.dateRange = dateRange;
    }

    public Long getBrand() {
        return brand;
    }

    public void setBrand(Long brand) {
        this.brand = brand;
    }

    public Long getRoute() {
        return route;
    }

    public void setRoute(Long route) {
        this.route = route;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public void setOrderType(SolrQuery.ORDER orderType) {
        this.orderType = orderType;
    }

    public SolrType getType() {
        return type;
    }

    @Override
    public String getOrderColumn() {
        return this.orderColumn;
    }

    @Override
    public SolrQuery.ORDER getOrderType() {
        return this.orderType;
    }

    @Override
    public Class getResultClass() {
        return CruiseShipSolrEntity.class;
    }

    @Override
    public String getSolrQueryStr() {
        Set<String> set = new HashSet<String>();
        if (StringUtils.isNotBlank(name)) {
            try {
                String nameToken = IKTokenUtils.token(name);
                String[] strArray = nameToken.split(" +");
                StringBuilder builder = new StringBuilder();
                builder.append("(");
                for (String s : strArray) {
                    if (builder.length() > 1) {
                        builder.append(" OR ");
                    }
                    builder.append("name:").append(s);
                }
                set.add(builder.append(")").toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!priceRange.isEmpty()) {
            set.add(String.format("price:[%s TO %s]", priceRange.get(0), priceRange.get(1)));
        }
        if (!dayRange.isEmpty()) {
            if (dayRange.size() == 1) {
                set.add(String.format("lineDay:[%s TO *]", dayRange.get(0)));
            } if (dayRange.size() == 2) {
                set.add(String.format("lineDay:[%s TO %s]", dayRange.get(0), dayRange.get(1)));
            }

        }

        if (!dateRange.isEmpty()) {
            if (dateRange.size() == 1) {
                set.add(String.format("start:[%s TO *]", dateRange.get(0)));
            } if (dateRange.size() == 2) {
                set.add(String.format("start:[%s TO %s]", dateRange.get(0), dateRange.get(1)));
            }
        }

        if (StringUtils.isNotBlank(date)) {
            set.add(String.format("startDate:%s*", date));
        }
        if (brand != null && brand > 0) {
            set.add(String.format("categoryIds:%s", brand));
        }
        if (route != null && route > 0) {
            set.add(String.format("categoryIds:%s", route));
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
}
