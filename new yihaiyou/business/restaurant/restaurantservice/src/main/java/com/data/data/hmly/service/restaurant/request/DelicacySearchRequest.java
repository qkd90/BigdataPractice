package com.data.data.hmly.service.restaurant.request;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.request.SolrSearchRequest;
import com.data.data.hmly.service.restaurant.vo.DelicacySolrEntity;
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
public class DelicacySearchRequest extends SolrSearchRequest {

    private String name;
    private List<Integer> priceRange = new ArrayList<Integer>();
    private List<String> cities = new ArrayList<String>();
    private List<Long> cityIds = new ArrayList<Long>();
    private String cuisine;
    private String taste;
    private String efficacy;
    private String orderColumn;
    private SolrQuery.ORDER orderType;
    private final SolrType type = SolrType.delicacy;


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

    public List<String> getCities() {
        return cities;
    }

    public void setCities(List<String> cities) {
        this.cities = cities;
    }

    public List<Long> getCityIds() {
        return cityIds;
    }

    public void setCityIds(List<Long> cityIds) {
        this.cityIds = cityIds;
    }

    public String getCuisine() {
        return cuisine;
    }

    public void setCuisine(String cuisine) {
        this.cuisine = cuisine;
    }

    public String getTaste() {
        return taste;
    }

    public void setTaste(String taste) {
        this.taste = taste;
    }

    public String getEfficacy() {
        return efficacy;
    }

    public void setEfficacy(String efficacy) {
        this.efficacy = efficacy;
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

    @Override
    public Class getResultClass() {
        return DelicacySolrEntity.class;
    }

    public SolrType getType() {
        return type;
    }

    public void setOrderType(SolrQuery.ORDER orderType) {
        this.orderType = orderType;
    }

    public String getSolrQueryStr() {
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
        set.add(keywordToken);
        if (!priceRange.isEmpty() && priceRange.get(0) > 0) {
            if (priceRange.size() == 1) {
                set.add(String.format("price:[%s TO %s]", priceRange.get(0), Integer.MAX_VALUE));
            } else if (priceRange.size() == 2) {
                set.add(String.format("price:[%s TO %s]", priceRange.get(0), priceRange.get(1)));
            }
        }
        if (StringUtils.isNotBlank(cuisine)) {
            set.add(String.format("cuisine:%s", cuisine));
        }
        if (StringUtils.isNotBlank(taste)) {
            set.add(String.format("taste:%s", taste));
        }
        if (StringUtils.isNotBlank(efficacy)) {
            set.add(String.format("efficacy:%s", efficacy));
        }
        if (!cities.isEmpty()) {
            StringBuilder builder = new StringBuilder();
            builder.append("(");
            for (String city : cities) {
                if (builder.length() > 1) {
                    builder.append(" OR ");
                }
                builder.append("city:").append(city);
            }
            set.add(builder.append(")").toString());
        }
        if (!cityIds.isEmpty()) {
//			List<Long> cityIdList = new ArrayList<>();
//			for (Long cityId : cityIds) {
//				String a = cityId.toString().substring(2, 6);
//				System.out.println(a);
//				if ("0000".equals(cityId.toString().substring(2, 6))) {
//
//				}
//			}
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (Long cityId : cityIds) {

                if (sb.length() > 1) {
                    sb.append(" OR ");
                }
                if ("0000".equals(cityId.toString().substring(2, 6))) {
                    sb.append("cityId:[" + cityId + " TO " + (cityId + 10000) + "]");
                } else {
                    sb.append("cityId:").append(cityId);
                }
            }
            set.add(sb.append(")").toString());
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
}
