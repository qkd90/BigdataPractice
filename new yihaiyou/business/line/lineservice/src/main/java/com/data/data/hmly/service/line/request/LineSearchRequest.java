package com.data.data.hmly.service.line.request;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.request.SolrSearchRequest;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.line.vo.LineSolrEntity;
import com.zuipin.util.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import java.text.ParseException;
import java.util.*;

/**
 * Created by HMLY on 2016/6/2.
 */
public class LineSearchRequest extends SolrSearchRequest {

    private String name;
    private List<Integer> priceRange = new ArrayList<Integer>();
    private Long cityId;
    private Long startCityId;
    private String trafficType;
    private String lineType;
    private String productAttr;
    private String startDate;
    private String endDate;
    private String lineDay;
    private String theme;
    private String orderColumn;
    private SolrQuery.ORDER orderType;
    private List<Long> destinationIdList = new ArrayList<Long>();
    private List<Integer> days = new ArrayList<Integer>();
    private List<String> featureList = new ArrayList<String>();
    private final SolrType type = SolrType.line;

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

    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public Long getStartCityId() {
        return startCityId;
    }

    public void setStartCityId(Long startCityId) {
        this.startCityId = startCityId;
    }

    public String getTrafficType() {
        return trafficType;
    }

    public void setTrafficType(String trafficType) {
        this.trafficType = trafficType;
    }

    public String getProductAttr() {
        return productAttr;
    }

    public void setProductAttr(String productAttr) {
        this.productAttr = productAttr;
    }

    public String getLineType() {
        return lineType;
    }

    public void setLineType(String lineType) {
        this.lineType = lineType;
    }

    public String getLineDay() {
        return lineDay;
    }

    public void setLineDay(String lineDay) {
        this.lineDay = lineDay;
    }

    @Override
    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public SolrQuery.ORDER getOrderType() {
        return orderType == null ? SolrQuery.ORDER.desc : orderType;
    }

    @Override
    public Class getResultClass() {
        return LineSolrEntity.class;
    }

    public SolrType getType() {
        return type;
    }

    public void setOrderType(SolrQuery.ORDER orderType) {
        this.orderType = orderType;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public List<Long> getDestinationIdList() {
        return destinationIdList;
    }

    public void setDestinationIdList(List<Long> destinationIdList) {
        this.destinationIdList = destinationIdList;
    }

    public List<Integer> getDays() {
        return days;
    }

    public void setDays(List<Integer> days) {
        this.days = days;
    }

    public List<String> getFeatureList() {
        return featureList;
    }

    public void setFeatureList(List<String> featureList) {
        this.featureList = featureList;
    }

    public String getSolrQueryStr(){
        Set<String> set = new HashSet<String>();
        if (!priceRange.isEmpty() && priceRange.get(0) >= 0) {
            if (priceRange.size() == 1) {
                set.add(String.format("price:[%s TO %s]", priceRange.get(0), Integer.MAX_VALUE));
            } else if (priceRange.size() == 2 && priceRange.get(1) > 0) {
                set.add(String.format("price:[%s TO %s]", priceRange.get(0), priceRange.get(1)));
            }
        }
        if (StringUtils.isNotBlank(productAttr)) {
            set.add(String.format("productAttr:%s", productAttr));
        }
        if (StringUtils.isNotBlank(lineType)) {
            set.add(String.format("lineType:%s", lineType));
        }
        if (StringUtils.isNotBlank(lineDay)) {
            set.add(String.format("lineDay:%s", lineDay));
        }

        if (!days.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (Integer lineDay : days) {
                if (sb.length() > 1) {
                    sb.append(" OR ");
                }
                sb.append("lineDay:").append(lineDay);
            }
            sb.append(")");
            set.add(sb.toString());
        }

        if (StringUtils.isNotBlank(name)) {
            set.add(String.format("name:%s", name));
        }

        if (!featureList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (String fea : featureList) {
                if (sb.length() > 1) {
                    sb.append(" OR ");
                }
                sb.append("themes:").append(fea);
            }
            sb.append(")");
            set.add(sb.toString());
        }

        if (!destinationIdList.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (Long cityId : destinationIdList) {
                if (sb.length() > 1) {
                    sb.append(" OR ");
                }
                sb.append("cityId:").append(cityId);
            }
            sb.append(")");
            set.add(sb.toString());
        }

        if (StringUtils.isNotBlank(startDate, endDate)) {
            try {
                Date start = DateUtils.parseShortTime(startDate);
                Date end = DateUtils.parseShortTime(endDate);
                if (start.getTime() <= end.getTime()) {
                    StringBuilder sb = new StringBuilder();
                    sb.append("(");
                    while (start.getTime() <= end.getTime()) {
                        if (sb.length() > 1) {
                            sb.append(" OR ");
                        }
                        sb.append("startDays:").append(DateUtils.formatShortDate(start));
                        start = DateUtils.add(start, Calendar.DAY_OF_MONTH, 1);
                    }
                    sb.append(")");
                    set.add(sb.toString());
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        if (StringUtils.isNotBlank(theme)) {
            set.add(String.format("themes:%s", theme));
        }

        if (cityId != null && cityId > 0) {
            set.add(String.format("cityId:%s", cityId));
        }

        if (startCityId != null && startCityId > 0) {
            set.add(String.format("startCityId:%s", startCityId));
        }

        if (StringUtils.isNotBlank(trafficType)) {
            set.add(String.format("trafficType:%s", trafficType));
        }

        set.add(String.format("type:%s", type));
        if (set.isEmpty()) {
            return "*:*";
        }
        StringBuilder builder = new StringBuilder();
        for (String param : set) {
            if (builder.length() > 0) {
                builder.append(" AND ");
            }
            builder.append(param);
        }

        return builder.toString();
    }


    private void addItemsToSet(Set<String> set, String itemName, List<Long> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (Long item : items) {
            if (sb.length() > 1) {
                sb.append(" OR ");
            }
            sb.append(itemName + ":").append(item);

        }
        set.add(sb.append(")").toString());
    }
}
