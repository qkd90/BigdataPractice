package com.data.data.hmly.service.hotel.vo;

import com.data.data.hmly.service.common.entity.enums.ProductSource;
import com.data.data.hmly.service.common.entity.enums.SolrType;
import com.data.data.hmly.service.common.util.DateUtils;
import com.data.data.hmly.service.common.vo.SolrEntity;
import com.data.data.hmly.service.entity.LabelItem;
import com.data.data.hmly.service.hotel.entity.Hotel;
import com.data.data.hmly.service.hotel.entity.HotelPrice;
import com.data.data.hmly.service.hotel.entity.enums.PriceStatus;
import com.zuipin.util.PinyinUtil;
import com.zuipin.util.StringUtils;
import org.apache.solr.client.solrj.beans.Field;
import org.springframework.data.solr.core.mapping.SolrDocument;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Set;

@SolrDocument(solrCoreName = "products")
public class HotelSolrEntity extends SolrEntity<Hotel> {


    @Field
    private Long id;
    @Field
    private String name;
    @Field
    private Integer star;
    @Field
    private Integer productScore;
    @Field
    private Float price;
    @Field
    private String address;
    @Field
    private Double lng;
    @Field
    private Double lat;
    @Field
    private List<Long> labelIds;
    @Field
    private String comment;
    @Field
    private Integer commentCount;
    @Field
    private String cover;
    @Field
    private String city;
    @Field
    private Long cityId;
    @Field
    private final SolrType type = SolrType.hotel;
    @Field
    private String typeid;
    @Field
    private String latlng;
    @Field
    private Date start;
    @Field
    private Date end;
    @Field
    private List<String> py;

    @Field
    private List<String> brands;
    @Field
    private List<String> generalAmenities;
    @Field
    private List<String> serviceAmenities;
    @Field
    private List<String> facilities;
    //    @Field
//    private Double latlng_0_d;
//    @Field
//    private Double latlng_1_d;
    @Field
    private Long provienceId;
    @Field
    private List<String> region;
    @Field
    private String source;
    @Field
    private Integer showOrder;

    private Long recommendPlanId;

    private String recommendPlanName;

    private List<String> regionNameList;

    public HotelSolrEntity() {

    }

    public HotelSolrEntity(Hotel hotel) {
        this.id = hotel.getId();
        this.name = hotel.getName();
        this.star = hotel.getStar();
        if (hotel.getScore() == null) {
            this.productScore = 0;
        } else {
            this.productScore = hotel.getScore().intValue();
        }
        this.price = hotel.getPrice();
        this.cityId = hotel.getCityId();
//        this.cover = hotel.getCover();
        if (hotel.getExtend() != null) {
            this.address = hotel.getExtend().getAddress();
            this.lng = hotel.getExtend().getLongitude();
            this.lat = hotel.getExtend().getLatitude();
            if (this.lng != null && this.lat != null) {
                this.latlng = String.format("%s,%s", hotel.getExtend().getLatitude(), hotel.getExtend().getLongitude());
            }
            this.commentCount = hotel.getExtend().getCommentNum();
//            this.latlng_0_d = hotel.getExtend().getLatitude();
//            this.latlng_1_d = hotel.getExtend().getLongitude();
        }
        this.comment = hotel.getShortDesc();
        this.typeid = this.type.toString() + this.id;
        if (hotel.getId() != null) {
            Set<LabelItem> itemSets = hotel.getLabelItems();
            if (itemSets != null) {
                List<Long> laIds = new ArrayList<Long>();
                for (LabelItem item : itemSets) {
                    laIds.add(item.getLabel().getId());
                }
                this.labelIds = laIds;
            }
        }
        if (StringUtils.isNotBlank(this.name)) {
            String spells = PinyinUtil.converterToSpell(this.name);
            this.py = Arrays.asList(spells.split(" "));
        }
        if (hotel.getBrandId() != null) {
            List<String> br = new ArrayList<String>();
            br.add(hotel.getBrandId().toString());
            this.brands = br;
        }
        if (hotel.getGeneralAmenities() != null) {
            String[] ss = hotel.getGeneralAmenities().split(",");
            List<String> gener = new ArrayList<String>();
            for (int i = 0; i < ss.length; i++) {
                gener.add(ss[i]);
            }
            this.generalAmenities = gener;
        }
        if (hotel.getServiceAmenities() != null) {
            String[] ss = hotel.getServiceAmenities().split(",");
            List<String> servi = new ArrayList<String>();
            for (int i = 0; i < ss.length; i++) {
                servi.add(ss[i]);
            }
            this.serviceAmenities = servi;
        }
        if (hotel.getFacilities() != null) {
            String[] ss = hotel.getFacilities().split(",");
            List<String> faci = new ArrayList<String>();
            for (int i = 0; i < ss.length; i++) {
                faci.add(ss[i]);
            }
            this.facilities = faci;
        }
        this.provienceId = hotel.getProvienceId();

        if (hotel.getSource() == null) {
            this.source = ProductSource.LXB.name();
        } else {
            this.source = hotel.getSource().name();
        }
        this.showOrder = hotel.getShowOrder();

        // 起始时间
        buildDate(hotel);
    }

    /**
     * 设置起始时间
     *
     * @param hotel
     */
    private void buildDate(Hotel hotel) {
        List<HotelPrice> hotelPrices = hotel.getHotelPriceList();
        if (hotelPrices != null && !hotelPrices.isEmpty()) {
            Float minPrice = Float.MAX_VALUE;
            for (HotelPrice hotelPrice : hotelPrices) {
                if ((!hotelPrice.getStatus().equals(PriceStatus.GUARANTEE) && !hotelPrice.getStatus().equals(PriceStatus.UP)) || hotelPrice.getStart() == null || hotelPrice.getEnd() == null) {
                    continue;
                }
                if (start == null || start.after(hotelPrice.getStart())) {
                    start = hotelPrice.getStart();
                }
                if (end == null || end.before(hotelPrice.getEnd())) {
                    end = hotelPrice.getEnd();
                }
                minPrice = Math.min(minPrice, hotelPrice.getPrice());
            }
            if (start != null) {
                start = DateUtils.add(start, Calendar.HOUR_OF_DAY, 8); // solr索引时间需加8个小时
            }
            if (end != null) {
                end = DateUtils.add(end, Calendar.HOUR_OF_DAY, 8); // solr索引时间需加8个小时
            }
            if (minPrice != Float.MAX_VALUE) {
                this.price = minPrice;
            } else {
                this.price = 0f;
            }
        }
    }

    public HotelSolrEntity(Hotel hotel, boolean simple) {
        this.id = hotel.getId();
        this.name = hotel.getName();
        String spells = PinyinUtil.converterToSpell(this.name);
        this.py = Arrays.asList(spells.split(" "));
        this.star = hotel.getStar();
        this.productScore = hotel.getScore().intValue();
        this.price = hotel.getPrice();
        this.cityId = hotel.getCityId();
        this.cover = hotel.getCover();
        if (!simple && hotel.getExtend() != null) {
            this.address = hotel.getExtend().getAddress();
            this.lng = hotel.getExtend().getLongitude();
            this.lat = hotel.getExtend().getLatitude();
            this.latlng = String.format("%s,%s", hotel.getExtend().getLatitude(), hotel.getExtend().getLongitude());
        }
        this.comment = hotel.getShortDesc();
        this.commentCount = hotel.getCommentList().size();

        this.typeid = this.type.toString() + this.id;
        if (hotel.getId() != null) {
            Set<LabelItem> itemSets = hotel.getLabelItems();
            List<Long> laIds = new ArrayList<Long>();
            for (LabelItem item : itemSets) {
                laIds.add(item.getLabel().getId());
            }
            this.labelIds = laIds;
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
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

    public Integer getScore() {
        return productScore;
    }

    public void setScore(Integer score) {
        this.productScore = score;
    }

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
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

    public void setLatlng(String latlng) {
        this.latlng = latlng;
    }

    public String getLatlng() {
        return latlng;
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

    public List<String> getRegionNameList() {
        return regionNameList;
    }

    public void setRegionNameList(List<String> regionNameList) {
        this.regionNameList = regionNameList;
    }

    public Integer getProductScore() {
        return productScore;
    }

    public void setProductScore(Integer productScore) {
        this.productScore = productScore;
    }

    public Date getStart() {
        return start;
    }

    public void setStart(Date start) {
        this.start = start;
    }

    public Date getEnd() {
        return end;
    }

    public void setEnd(Date end) {
        this.end = end;
    }

    public List<String> getPy() {
        return py;
    }

    public void setPy(List<String> py) {
        this.py = py;
    }

    public List<String> getBrands() {
        return brands;
    }

    public void setBrands(List<String> brands) {
        this.brands = brands;
    }

    public List<String> getGeneralAmenities() {
        return generalAmenities;
    }

    public void setGeneralAmenities(List<String> generalAmenities) {
        this.generalAmenities = generalAmenities;
    }

    public List<String> getServiceAmenities() {
        return serviceAmenities;
    }

    public void setServiceAmenities(List<String> serviceAmenities) {
        this.serviceAmenities = serviceAmenities;
    }

    public List<String> getFacilities() {
        return facilities;
    }

    public void setFacilities(List<String> facilities) {
        this.facilities = facilities;
    }

//    public Double getLatlng_0_d() {
//        return latlng_0_d;
//    }
//
//    public void setLatlng_0_d(Double latlng_0_d) {
//        this.latlng_0_d = latlng_0_d;
//    }
//
//    public Double getLatlng_1_d() {
//        return latlng_1_d;
//    }
//
//    public void setLatlng_1_d(Double latlng_1_d) {
//        this.latlng_1_d = latlng_1_d;
//    }

    public Long getProvienceId() {
        return provienceId;
    }

    public void setProvienceId(Long provienceId) {
        this.provienceId = provienceId;
    }

    public List<String> getRegion() {
        return region;
    }

    public void setRegion(List<String> region) {
        this.region = region;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getShowOrder() {
        return showOrder;
    }

    public void setShowOrder(Integer showOrder) {
        this.showOrder = showOrder;
    }
}