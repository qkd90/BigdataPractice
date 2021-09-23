package com.data.data.hmly.service.scenic.request;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.request.SolrSearchRequest;
import com.data.data.hmly.service.scenic.entity.enums.ScenicInfoType;
import com.data.data.hmly.service.scenic.vo.ScenicSolrEntity;
import com.data.ikanalysis.utils.IKTokenUtils;
import com.zuipin.util.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by guoshijie on 2015/12/4.
 */
public class ScenicSearchRequest extends SolrSearchRequest {




    private String name;
    private List<Integer> priceRange = new ArrayList<Integer>();
    private String priceMode;
    private String level;
    private Boolean hasLevel;
    private String theme;
    private Boolean hasTheme;
    private List<String> themes = new ArrayList<String>();
    private List<Long> cityIds = new ArrayList<Long>();
    private Long father;
    private String orderColumn;
    private Double lng;
    private Double lat;
    private Float distance;
    private String labelName;
    private Integer ticketNum;
    private ScenicInfoType scenicInfoType;
    private List<String> region;
    private List<String> outRegion;
    private List<Long> labelIds = new ArrayList<Long>();
    private SolrQuery.ORDER orderType;
    private final SolrType type = SolrType.scenic_info;

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

    public String getPriceMode() {
        return priceMode;
    }

    public void setPriceMode(String priceMode) {
        this.priceMode = priceMode;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Boolean getHasLevel() {
        return hasLevel;
    }

    public void setHasLevel(Boolean hasLevel) {
        this.hasLevel = hasLevel;
    }

    public String getTheme() {
        return theme;
    }

    public void setTheme(String theme) {
        this.theme = theme;
    }

    public Boolean getHasTheme() {
        return hasTheme;
    }

    public void setHasTheme(Boolean hasTheme) {
        this.hasTheme = hasTheme;
    }

    public List<Long> getCityIds() {
        return cityIds;
    }

    public void setCityIds(List<Long> cityIds) {
        this.cityIds = cityIds;
    }

    public Long getFather() {
        return father;
    }

    public void setFather(Long father) {
        this.father = father;
    }

    public String getOrderColumn() {
        return orderColumn;
    }

    public void setOrderColumn(String orderColumn) {
        this.orderColumn = orderColumn;
    }

    public SolrQuery.ORDER getOrderType() {
        return orderType == null ? SolrQuery.ORDER.desc : orderType;
    }

    public List<String> getThemes() {
        return themes;
    }

    public void setThemes(List<String> themes) {
        this.themes = themes;
    }

    public Double getLng() {
        return lng;
    }

    public void setLng(Double lng) {
        this.lng = lng;
    }

    public Double getLat() {
        return lat;
    }

    public void setLat(Double lat) {
        this.lat = lat;
    }

    public Float getDistance() {
        return distance;
    }

    public void setDistance(Float distance) {
        this.distance = distance;
    }

    public String getLabelName() {
        return labelName;
    }

    public void setLabelName(String labelName) {
        this.labelName = labelName;
    }

    public List<Long> getLabelIds() {

        return labelIds;
    }

    public void setLabelIds(List<Long> labelIds) {
        this.labelIds = labelIds;
    }

    public Integer getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(Integer ticketNum) {
        this.ticketNum = ticketNum;
    }

    public ScenicInfoType getScenicInfoType() {
        return scenicInfoType;
    }

    public void setScenicInfoType(ScenicInfoType scenicInfoType) {
        this.scenicInfoType = scenicInfoType;
    }

    @Override
    public Class getResultClass() {
        return ScenicSolrEntity.class;
    }

    public void setOrderType(SolrQuery.ORDER orderType) {
        this.orderType = orderType;
    }

    public List<String> getRegion() {
        return region;
    }

    public void setRegion(List<String> region) {
        this.region = region;
    }

    public List<String> getOutRegion() {
        return outRegion;
    }

    public void setOutRegion(List<String> outRegion) {
        this.outRegion = outRegion;
    }

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
                this.orderColumn = null;
                this.orderType = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (!priceRange.isEmpty()) {
            String pricePart1 = this.priceMode != null && "than".equals(priceMode) ? "{" : "[";
            String pricePart2 = this.priceMode != null && "than".equals(priceMode) ? "}" : "]";
            if (priceRange.size() == 1) {
                set.add(String.format("price:" + pricePart1 + "%s TO %s" + pricePart2, priceRange.get(0), Integer.MAX_VALUE));
            } else if (priceRange.size() == 2) {
                set.add(String.format("price:" + pricePart1 + "%s TO %s" + pricePart2, priceRange.get(0), priceRange.get(1)));
            }
        }
        if (scenicInfoType != null) {
            set.add(String.format("scenicInfoType:%s", scenicInfoType));
        }
        if (StringUtils.isNotBlank(level)) {
            set.add(String.format("level:%s", level));
        }
        if (hasLevel != null && hasLevel) {
            set.add(String.format("level:%", "*"));
        }
        if (StringUtils.isNotBlank(theme)) {
            set.add(String.format("theme:%s", theme));
        }
        if (hasTheme != null && hasLevel) {
            set.add(String.format("themes:%s", "*"));
        }
        if (!themes.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (String th : themes) {
                if (sb.length() > 1) {
                    sb.append(" OR ");
                }
                sb.append("theme:").append(th);
            }
            sb.append(")");
            set.add(sb.toString());
        }

        if (!cityIds.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("(");
            for (Long cityId : cityIds) {
                if (builder.length() > 1) {
                    builder.append(" OR ");
                }
                if (cityId >= 1000000) {
                    builder.append(String.format("cityId:%d", cityId));
                } else if (cityId % 10000 == 0) {
                    builder.append(String.format("cityId:[%d TO %d]", cityId, cityId + 9999));
                } else if (cityId % 100 == 0) {
                    builder.append(String.format("cityId:[%d TO %d]", cityId, cityId + 99));
                } else {
                    builder.append(String.format("cityId:%d", cityId));
                }
            }
            set.add(builder.append(")").toString());
        }
        if (father != null) {
            set.add(String.format("father:%s", father));
        }

        if (ticketNum != null) {
            set.add(String.format("ticketNum:[%d TO *]", ticketNum));
        }
        appendQuery(set, labelIds);

        appendRegion(set, region);

        appendOutRegion(set, outRegion);

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

    public Set<String> appendQuery(Set<String> set, List<Long> labelIds) {
        if (!labelIds.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (Long laId : labelIds) {
                if (sb.length() > 1) {
                    sb.append(" OR ");
                }
                sb.append("labelIds:").append(laId);
            }
            sb.append(")");
            set.add(sb.toString());
        }
        return set;
    }

    public Set<String> appendRegion(Set<String> set, List<String> regions) {
        if (regions != null && !regions.isEmpty()) {
           StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (String re : regions) {
                if (sb.length() > 1) {
                    sb.append(" OR");
                }
                sb.append("region:").append(re);
            }
            sb.append(")");
            set.add(sb.toString());
        }
        return set;
    }

    public Set<String> appendOutRegion(Set<String> set, List<String> outRegions) {
        if (outRegions != null) {
           StringBuilder sb = new StringBuilder();
            for (String re : outRegions) {
                if (sb.length() > 1) {
                    sb.append(" AND");
                }
                sb.append("-region:").append(re);
            }
            set.add(sb.toString());
        }
        return set;
    }

}
