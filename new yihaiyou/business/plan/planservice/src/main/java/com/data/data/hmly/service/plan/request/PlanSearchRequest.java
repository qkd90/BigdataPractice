package com.data.data.hmly.service.plan.request;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.request.SolrSearchRequest;
import com.data.data.hmly.service.plan.vo.PlanSolrEntity;
import com.data.ikanalysis.utils.IKTokenUtils;
import com.google.common.collect.Sets;
import com.zuipin.util.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * Created by huangpeijie on 2016-04-29,0029.
 */
public class PlanSearchRequest extends SolrSearchRequest {
    private String name;
    private List<Integer> monthRange = new ArrayList<Integer>();
    private List<Integer> dayRange = new ArrayList<Integer>();
    private List<Integer> costRange = new ArrayList<Integer>();
    private Long userId;
    private Long scenicId;
    private Long hotelId;
    private Long trafficId;
    private List<Long> fromCityIds = new ArrayList<Long>();
    private List<Long> cityIds = new ArrayList<Long>();
    private Integer scenics;
    private String orderColumn;
    private SolrQuery.ORDER orderType;
    private final SolrType type = SolrType.plan;

    @Override
    public Class getResultClass() {
        return PlanSolrEntity.class;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Integer> getMonthRange() {
        return monthRange;
    }

    public void setMonthRange(List<Integer> monthRange) {
        this.monthRange = monthRange;
    }

    public List<Integer> getDayRange() {
        return dayRange;
    }

    public void setDayRange(List<Integer> dayRange) {
        this.dayRange = dayRange;
    }

    public List<Integer> getCostRange() {
        return costRange;
    }

    public void setCostRange(List<Integer> costRange) {
        this.costRange = costRange;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getScenicId() {
        return scenicId;
    }

    public void setScenicId(Long scenicId) {
        this.scenicId = scenicId;
    }

    public Long getHotelId() {
        return hotelId;
    }

    public void setHotelId(Long hotelId) {
        this.hotelId = hotelId;
    }

    public Long getTrafficId() {
        return trafficId;
    }

    public void setTrafficId(Long trafficId) {
        this.trafficId = trafficId;
    }

    public List<Long> getCityIds() {
        return cityIds;
    }

    public void setCityIds(List<Long> cityIds) {
        this.cityIds = cityIds;
    }

    public Integer getScenics() {
        return scenics;
    }

    public void setScenics(Integer scenics) {
        this.scenics = scenics;
    }

    public List<Long> getFromCityIds() {
        return fromCityIds;
    }

    public void setFromCityIds(List<Long> fromCityIds) {
        this.fromCityIds = fromCityIds;
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

    public SolrType getType() {
        return type;
    }

    @Override
    public String getSolrQueryStr() {
        StringBuilder builder = new StringBuilder();
        Set<String> set = Sets.newHashSet();
        if (StringUtils.isNotBlank(name)) {
            set.add(getKeywordToken(name));
        }
        if (!monthRange.isEmpty()) {
            set.add(String.format("startMonth:[%s TO %s]", monthRange.get(0), monthRange.get(1)));
        }
        if (!dayRange.isEmpty()) {
            if (dayRange.size() == 1) {
                set.add(String.format("days:[%s TO %s]", dayRange.get(0), Integer.MAX_VALUE));
            } else if (dayRange.size() == 2) {
                set.add(String.format("days:[%s TO %s]", dayRange.get(0), dayRange.get(1)));
            }
        }
        if (!costRange.isEmpty()) {
            if (costRange.size() == 1) {
                set.add(String.format("cost:[%s TO %s]", costRange.get(0), Integer.MAX_VALUE));
            } else if (costRange.size() == 2) {
                set.add(String.format("cost:[%s TO %s]", costRange.get(0), costRange.get(1)));
            }
        }
        if (userId != null) {
            set.add(String.format("userId:%s", userId));
        }
        if (scenicId != null) {
            set.add(String.format("scenicIds:%s", scenicId));
        }
        if (hotelId != null) {
            set.add(String.format("hotelIds:%s", hotelId));
        }
        if (trafficId != null) {
            set.add(String.format("trafficIds:%s", trafficId));
        }
        if (!cityIds.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (Long cityId : cityIds) {
                if (builder.length() > 1) {
                    sb.append(" OR ");
                }
                if (cityId % 10000 == 0) {
                    sb.append(String.format("cityId:[%d TO %d]", cityId, cityId + 9999));
                } else if (cityId % 100 == 0) {
                    sb.append(String.format("cityId:[%d TO %d]", cityId, cityId + 99));
                } else {
                    sb.append(String.format("cityId:%d", cityId));
                }
            }
            set.add(sb.append(")").toString());
        }
        if (!fromCityIds.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (Long cityId : fromCityIds) {
                if (builder.length() > 1) {
                    sb.append(" OR ");
                }
                if (cityId % 10000 == 0) {
                    sb.append(String.format("fromCityId:[%d TO %d]", cityId, cityId + 9999));
                } else if (cityId % 100 == 0) {
                    sb.append(String.format("fromCityId:[%d TO %d]", cityId, cityId + 99));
                } else {
                    sb.append(String.format("fromCityId:%d", cityId));
                }
            }
            set.add(sb.append(")").toString());
        }
        if (scenics != null) {
            set.add(String.format("scenics:%s", scenics));
        }
        set.add(String.format("type:%s", type));
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

    private String getKeywordToken(String name) {
        String keywordToken = "";
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
        return keywordToken;
    }
}
