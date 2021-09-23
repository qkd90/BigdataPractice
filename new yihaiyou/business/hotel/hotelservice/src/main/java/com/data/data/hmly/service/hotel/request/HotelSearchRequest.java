package com.data.data.hmly.service.hotel.request;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.request.SolrSearchRequest;
import com.data.data.hmly.service.hotel.vo.HotelSolrEntity;
import com.data.ikanalysis.utils.IKTokenUtils;
import com.zuipin.util.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class HotelSearchRequest extends SolrSearchRequest {

    private String name;
    private Integer star;
    private String source;

    private Long region;
    private List<Long> facilities = new ArrayList<>();
    private List<Long> brands = new ArrayList<>();
    private List<Long> generalAenities = new ArrayList<>();
    private List<Long> recreationAmenities = new ArrayList<>();
    private List<Long> serviceAmenities = new ArrayList<>();
    private List<Long> theme = new ArrayList<>();


    private String startDate;
    private String endDate;
    private List<Float> priceRange = new ArrayList<Float>();
    private List<String> cities = new ArrayList<String>();
    private List<Long> cityIds = new ArrayList<Long>();
    private String orderColumn;
    private SolrQuery.ORDER orderType;
    private final SolrType type = SolrType.hotel;

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
        return HotelSolrEntity.class;
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

    public Integer getStar() {
        return star;
    }

    public void setStar(Integer star) {
        this.star = star;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Long getRegion() {
        return region;
    }

    public void setRegion(Long region) {
        this.region = region;
    }

    public List<Long> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<Long> facilities) {
        this.facilities = facilities;
    }

    public List<Long> getBrands() {
        return brands;
    }

    public void setBrands(List<Long> brands) {
        this.brands = brands;
    }

    public List<Long> getGeneralAenities() {
        return generalAenities;
    }

    public void setGeneralAenities(List<Long> generalAenities) {
        this.generalAenities = generalAenities;
    }

    public List<Long> getRecreationAmenities() {
        return recreationAmenities;
    }

    public void setRecreationAmenities(List<Long> recreationAmenities) {
        this.recreationAmenities = recreationAmenities;
    }

    public List<Long> getServiceAmenities() {
        return serviceAmenities;
    }

    public void setServiceAmenities(List<Long> serviceAmenities) {
        this.serviceAmenities = serviceAmenities;
    }

    public List<Long> getTheme() {
        return theme;
    }

    public void setTheme(List<Long> theme) {
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

    public List<Float> getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(List<Float> priceRange) {
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

    public String getSolrQueryStr() {
        Set<String> set = new HashSet<String>();
        makesearchSet(set);
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

    private void makesearchSet(Set<String> set) {
        if (StringUtils.isNotBlank(startDate)) {
            set.add(String.format("start:[* TO %s]", startDate + "T23:59:59Z"));
        }
        if (StringUtils.isNotBlank(endDate)) {
            set.add(String.format("end:[%s TO *]", endDate + "T00:00:00Z"));
        }
        if (!priceRange.isEmpty()) {
            if (priceRange.size() == 1) {
                set.add(String.format("price:[%s TO %s]", priceRange.get(0), Integer.MAX_VALUE));
            } else if (priceRange.size() == 2) {
                set.add(String.format("price:[%s TO %s]", priceRange.get(0), priceRange.get(1)));
            }
        }
//		if (!cities.isEmpty()) {
//			StringBuilder builder = new StringBuilder();
//			builder.append("(");
//			for (String city : cities) {
//				if (builder.length() > 1) {
//					builder.append(" OR ");
//				}
//				builder.append("city:").append(city);
//			}
//			set.add(builder.append(")").toString());
//		}
        if (!cityIds.isEmpty()) {
            StringBuilder sb = new StringBuilder();
            sb.append("(");
            for (Long cityId : cityIds) {

                if (sb.length() > 1) {
                    sb.append(" OR ");
                }
                if (cityId % 10000 == 0) {
                    sb.append("cityId:[" + cityId + " TO " + (cityId + 9999) + "]");
                } else if (cityId % 100 == 0) {
                    sb.append("cityId:[" + cityId + " TO " + (cityId + 99) + "]");
                } else {
                    sb.append("cityId:").append(cityId);
                }
            }
            set.add(sb.append(")").toString());
        }
        if (StringUtils.isNotBlank(name)) {
            try {
                String keywordToken = IKTokenUtils.token(name);
                StringBuilder sb = new StringBuilder();
                String[] arr = keywordToken.split(" +");
                if (arr.length > 0) {
                    sb.append("(");
                    for (String s : arr) {
                        sb.append("name:").append(s).append(" OR ");
                    }
                    sb.delete(sb.length() - 4, sb.length());
                    sb.append(")");
                } else {
                    sb.append("name:").append(name);
                }
//                sb.append("(");
//                for (String s : arr) {
//                    sb.append("name:").append(s).append("*").append(" OR ");
//                }
//                sb.delete(sb.length() - 4, sb.length());
                set.add(sb.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        if (star != null) {
//            if (star == 0) {
//                set.add("star:*");
//            } else {
//                set.add(String.format("star:%s", star));
//            }
            set.add(String.format("star:%s", star));
        }
        if (StringUtils.hasText(source)) {
            set.add(String.format("source:%s", source));
        }
        if (region != null && region != 0) {
            set.add(String.format("region:%s", region));
        }
        if (facilities != null && !facilities.isEmpty()) {
            addItemsToSet(set, "facilities", facilities);
        }
        if (brands != null && !brands.isEmpty()) {
            addItemsToSet(set, "brands", brands);
        }
        if (generalAenities != null && !generalAenities.isEmpty()) {
            addItemsToSet(set, "generalAenities", generalAenities);
        }
        if (recreationAmenities != null && !recreationAmenities.isEmpty()) {

            addItemsToSet(set, "recreationAmenities", recreationAmenities);
        }
        if (serviceAmenities != null && !serviceAmenities.isEmpty()) {
            addItemsToSet(set, "serviceAmenities", serviceAmenities);
        }
        if (theme != null && !theme.isEmpty()) {
            addItemsToSet(set, "theme", theme);
        }
        set.add(String.format("type:%s", type));
    }

    private void addItemsToSet(Set<String> set, String itemName, List<Long> items) {
        StringBuilder sb = new StringBuilder();
        sb.append("(");
        for (Long item : items) {
            if (sb.length() > 1) {
                sb.append(" AND ");
            }
            sb.append(itemName + ":").append(item);

        }
        set.add(sb.append(")").toString());
    }


}