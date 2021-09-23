package com.data.data.hmly.service.scenic.vo;

import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.vo.SolrEntity;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.scenic.entity.ScenicArea;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.data.hmly.service.scenic.entity.enums.ScenicInfoType;
import com.zuipin.util.PinyinUtil;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;
import org.apache.log4j.Logger;
import org.apache.solr.client.solrj.beans.Field;
import org.hibernate.ObjectNotFoundException;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by guoshijie on 2015/12/4.
 */
@SolrDocument(solrCoreName = "products")
public class ScenicSolrEntity extends SolrEntity<ScenicInfo> {

    @Field
    private Long id;
    @Field
    private String name;
    @Field
    private Float price = 0f;
    @Field
    private String level;
    @Field
    private Integer score;
    @Field
    private Integer ranking;
    @Field
    private Integer productScore;
    @Field
    private String cover;
    @Field
    private Set<String> theme;
    @Field
    private Integer commentCount;
    @Field
    private List<Long> labelIds;
    @Field
    private String city;
    @Field
    private Long cityId;
    @Field
    private String address;
    @Field
    private Double longitude;
    @Field
    private Double latitude;
    @Field
    private String adviceTime;
    @Field
    private Integer adviceMinute;
    @Field
    private ScenicInfoType scenicInfoType;

    @Field
    private Integer satisfaction;
    @Field
    private Integer orderNum;
    @Field
    private String openTime;
    @Field
    private Integer ticketNum;

    @Field
    private Long father;
    @Field
    private String fatherName;

    @Field
    private Set<String> region;


    @Field
    private final SolrType type = SolrType.scenic_info;
    @Field
    private String typeid;
    @Field
    private String shortComment;
    @Field
    private List<String> py;

    private Long recommendPlanId;

    private String recommendPlanName;

    private static final Logger LOGGER = Logger.getLogger(ScenicSolrEntity.class);
    @Field
    private String latlng;

    public ScenicSolrEntity(ScenicInfo scenicInfo) {
        this.id = scenicInfo.getId();
        this.name = scenicInfo.getName();
        String spells = PinyinUtil.converterToSpell(this.name);
        this.py = Arrays.asList(spells.split(" "));
        this.cover = cover(scenicInfo.getCover());
        if (scenicInfo.getScenicStatistics() != null) {
            this.orderNum = scenicInfo.getScenicStatistics().getOrderNum();
            this.satisfaction = scenicInfo.getScenicStatistics().getSatisfaction();
        }

//        List<Float> prices = new ArrayList<>();
//        for (Ticket ticket : scenicInfo.getTicketList()) {
//            for (TicketPrice ticketPrice : ticket.getTicketPriceSet()) {
//                if (ticketPrice.getMinDiscountPrice() != null) {
//                prices.add(Float.parseFloat(ticketPrice.getMinDiscountPrice().toString()));
//                }
//            }
//        }
//        Collections.sort(prices);
//        if (!prices.isEmpty()) {
//            this.price = prices.get(0);
//        } else {
//            this.price = 0f;
//        }
//        this.price = scenicInfo.getPrice();
        this.level = scenicInfo.getLevel();
        this.score = scenicInfo.getScore();
        this.ranking = scenicInfo.getRanking();
        this.ticketNum = scenicInfo.getTicketNum();
        this.productScore = scenicInfo.getScore();
        this.scenicInfoType = scenicInfo.getScenicType();
        if (scenicInfo.getId() != null) {
            fillLabel(scenicInfo);
        }
        if (scenicInfo.getId() != null) {
            fillRegions(scenicInfo);
        }
        if (scenicInfo.getFather() != null) {
            this.father = scenicInfo.getFather().getId();
            this.fatherName = scenicInfo.getFather().getName();
        }
//        if (scenicInfo.getScenicThemeRelations() != null) {
//            this.theme = Sets.newHashSet(Lists.transform(scenicInfo.getScenicThemeRelations(), new Function<ScenicThemeRelation, String>() {
//                @Override
//                public String apply(ScenicThemeRelation scenicThemeRelation) {
//                    return scenicThemeRelation.getScenicTheme().getName();
//                }
//            }));
//        }
        if (scenicInfo.getScenicCommentList() != null) {
            this.commentCount = scenicInfo.getScenicCommentList().size();
        }
        if (scenicInfo.getCity() != null) {
            this.cityId = scenicInfo.getCity().getId();
            this.city = scenicInfo.getCity().getFullPath();
        }
        try {
            if (scenicInfo.getScenicOther() != null) {
                this.address = scenicInfo.getScenicOther().getAddress();
                this.adviceTime = scenicInfo.getScenicOther().getAdviceTimeDesc();
                this.shortComment = scenicInfo.getScenicOther().getRecommendReason();
                this.adviceMinute = scenicInfo.getScenicOther().getAdviceTime();
                this.openTime = scenicInfo.getScenicOther().getOpenTime();
            }
        } catch (ObjectNotFoundException e) {
            LOGGER.warn("id为" + scenicInfo.getId() + "的ScenicInfo的ScenicOther不存在 " + e);
        }
        if (scenicInfo.getScenicGeoinfo() != null) {
            if (scenicInfo.getScenicGeoinfo().getBaiduLat() != null) {
                this.latlng = String.format("%s,%s", scenicInfo.getScenicGeoinfo().getBaiduLat(), scenicInfo.getScenicGeoinfo().getBaiduLng());
                this.longitude = scenicInfo.getScenicGeoinfo().getBaiduLng();
                this.latitude = scenicInfo.getScenicGeoinfo().getBaiduLat();
            } else {
                this.latlng = String.format("%s,%s", scenicInfo.getScenicGeoinfo().getGoogleLat(), scenicInfo.getScenicGeoinfo().getGoogleLng());
                this.longitude = scenicInfo.getScenicGeoinfo().getGoogleLng();
                this.latitude = scenicInfo.getScenicGeoinfo().getGoogleLat();
            }
        }
        this.typeid = this.type.toString() + this.id;
    }

    private void fillLabel(ScenicInfo scenicInfo) {
        Set<LabelItem> itemSets = scenicInfo.getLabelItems();
        Set<String> scenicTheme = new HashSet<String>();
        List<Long> laIds = new ArrayList<Long>();
        if (itemSets != null) {
            for (LabelItem item : itemSets) {
                laIds.add(item.getLabel().getId());
                if (item.getLabel().getParent() != null && "景点主题".equals(item.getLabel().getParent().getName())) {
                    scenicTheme.add(item.getLabel().getName());
                }
            }
        }
        this.labelIds = laIds;
        this.theme = scenicTheme;
    }

    private void fillRegions(ScenicInfo scenicInfo) {
        Set<String> regionSet = new HashSet<String>();
        List<ScenicArea> areaList = scenicInfo.getScenicAreas();
        if (areaList != null) {
            for (ScenicArea a : areaList) {
                regionSet.add(a.getAreaName());
            }
        }
        this.region = regionSet;
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return "";
        } else {
            if (cover.startsWith("http")) {
                return cover;
            } else {
                return QiniuUtil.URL + cover;
            }
        }
    }

    public ScenicSolrEntity() {

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }


    public Set<String> getTheme() {
        return theme;
    }

    public void setTheme(Set<String> theme) {
        this.theme = theme;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getCity() {
        return city;
    }

    public Long getCityId() {

        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public SolrType getType() {
        return type;
    }

    public String getTypeid() {
        return typeid;
    }

    public void setTypeid(String typeid) {
        this.typeid = typeid;
    }

    public List<Long> getLabelIds() {
        return labelIds;
    }

    public void setLabelIds(List<Long> labelIds) {
        this.labelIds = labelIds;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getAdviceTime() {
        return adviceTime;
    }

    public void setAdviceTime(String adviceTime) {
        this.adviceTime = adviceTime;
    }

    public Long getRecommendPlanId() {
        return recommendPlanId;
    }

    public void setRecommendPlanId(Long recommendPlanId) {
        this.recommendPlanId = recommendPlanId;
    }

    public String getRecommendPlanName() {
        return recommendPlanName;
    }

    public void setRecommendPlanName(String recommendPlanName) {
        this.recommendPlanName = recommendPlanName;
    }

    public String getLatlng() {
        return latlng;
    }

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public String getShortComment() {
        return shortComment;
    }

    public void setShortComment(String shortComment) {
        this.shortComment = shortComment;
    }

    public Integer getProductScore() {
        return productScore;
    }

    public void setProductScore(Integer productScore) {
        this.productScore = productScore;
    }

    public String getFatherName() {
        return fatherName;
    }

    public void setFatherName(String fatherName) {
        this.fatherName = fatherName;
    }

    public Long getFather() {
        return father;
    }

    public void setFather(Long father) {
        this.father = father;
    }

    public Integer getRanking() {
        return ranking;
    }

    public void setRanking(Integer ranking) {
        this.ranking = ranking;
    }

    public Integer getAdviceMinute() {
        return adviceMinute;
    }

    public void setAdviceMinute(Integer adviceMinute) {
        this.adviceMinute = adviceMinute;
    }

    public ScenicInfoType getScenicInfoType() {
        return scenicInfoType;
    }

    public void setScenicInfoType(ScenicInfoType scenicInfoType) {
        this.scenicInfoType = scenicInfoType;
    }

    public List getPy() {
        return py;
    }

    public void setPy(List py) {
        this.py = py;
    }

    public Integer getSatisfaction() {
        return satisfaction;
    }

    public void setSatisfaction(Integer satisfaction) {
        this.satisfaction = satisfaction;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public Integer getTicketNum() {
        return ticketNum;
    }

    public void setTicketNum(Integer ticketNum) {
        this.ticketNum = ticketNum;
    }

    public Set<String> getRegion() {
        return region;
    }

    public void setRegion(Set<String> region) {
        this.region = region;
    }

    public String getCompleteCover() {
        if (StringUtils.isBlank(this.cover)) {
            return "";
        }
        if (this.cover.startsWith("http")) {
            return this.cover;
        }
        return QiniuUtil.URL + this.cover;
    }
}
