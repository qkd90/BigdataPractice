package com.data.spider.service.pojo;


import com.framework.hibernate.util.Entity;

import java.sql.Timestamp;
import java.util.List;

/**
 * By ZZL 2015.10.22
 */

public class Station extends Entity{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -5996263797005763162L;
	
    private Long id;

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 交通枢纽在去哪儿网的id
     */
    private Long qid;
    
    
    public Long getQid() {
		return qid;
	}
    public void setQid(Long qid) {
		this.qid = qid;
	}
    
    /**
     * 交通枢纽在去哪儿网的父级id
     */
    private Long parentId;
    
    
    public Long getParentId() {
		return parentId;
	}
    public void setParentId(Long parentId) {
		this.parentId = parentId;
	}
   
    /**
     * 交通枢纽在去哪儿网的代码
     */
    private String qunarSeq;
    
    
    public String getQunarSeq() {
		return qunarSeq;
	}
    public void setQunarSeq(String qunarSeq) {
		this.qunarSeq = qunarSeq;
	}
    /**
     * 交通枢纽名称
     */
    private String name;

    
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    
    /**
     * 交通枢纽英文名称
     */
    private String enName;
    
    
    public String getEnName() {
		return enName;
	}
    public void setEnName(String enName) {
		this.enName = enName;
	}
    
    /**
     * 交通枢纽在当地的名称
     */
    private String localName;
    
    
    public void setLocalName(String localName) {
		this.localName = localName;
	}
    public String getLocalName() {
		return localName;
	}
    
    /**
     * 交通枢纽的别名
     */
    private String alias;
    
    
    public String getAlias() {
		return alias;
	}
    public void setAlias(String alias) {
		this.alias = alias;
	}
    
    /**
     * 交通枢纽在去哪儿网的类型id
     */
    private Integer type;

    
    public Integer getType() {
        return type;
    }
    public void setType(Integer type) {
        this.type = type;
    }
    
    /**
     * 交通枢纽在去哪儿网所属大洲的id
     */
    private Long continentId;
    
    
    public Long getContinentId() {
		return continentId;
	}
    public void setContinentId(Long continentId) {
		this.continentId = continentId;
	}
    
    /**
     * 交通枢纽在去哪儿网所属大洲的名称
     */
    private String continentName;
    
    
    public String getContinentName() {
		return continentName;
	}
    public void setContinentName(String continentName) {
		this.continentName = continentName;
	}
    
    /**
     * 交通枢纽在去哪儿网所属子级大洲id
     */
    private Long subContinentId;
    
    
    public Long getSubContinentId() {
		return subContinentId;
	}
    public void setSubContinentId(Long subContinentId) {
		this.subContinentId = subContinentId;
	}
    
    /**
     * 交通枢纽在去哪儿网所属子级大洲名称
     */
    private String subContinentName;
    
    
    public String getSubContinentName() {
		return subContinentName;
	}
    public void setSubContinentName(String subContinentName) {
		this.subContinentName = subContinentName;
	}
    
    /**
     * 交通枢纽所在国家在去哪儿网的id
     */
    private Long countryId;
    
    
    public Long getCountryId() {
		return countryId;
	}
    public void setCountryId(Long countryId) {
		this.countryId = countryId;
	}
    
    /**
     * 交通枢纽所在国家名称
     */
    private String countryName;
    
    
    public String getCountryName() {
		return countryName;
	}
    public void setCountryName(String countryName) {
		this.countryName = countryName;
	}
    
    /**
     * 交通枢纽所在国家的地区在去哪儿网的id
     */
    private Long regionId;
    
    
    public Long getRegionId() {
		return regionId;
	}
    public void setRegionId(Long regionId) {
		this.regionId = regionId;
	}
    
    /**
     * 交通枢纽所在国家的地区名称
     */
    private String regionName;

    
    public String getRegionName() {
        return regionName;
    }
    public void setRegionName(String regionName) {
        this.regionName = regionName;
    }
    
    /**
     * 交通枢纽所在国家的省份在去哪儿网的id
     */
    private Long provinceId;
    
    
    public Long getProvinceId() {
		return provinceId;
	}
    public void setProvinceId(Long provinceId) {
		this.provinceId = provinceId;
	}
    
    /**
     * 交通枢纽所在国家的省份名称
     */
    private String provinceName;
    
    
    public String getProvinceName() {
		return provinceName;
	}
    public void setProvinceName(String provinceName) {
		this.provinceName = provinceName;
	}
    
    /**
     * 交通枢纽所在国家的城市在去哪儿网的id
     */
    private Long cityId;
    
    
    public Long getCityId() {
		return cityId;
	}
    public void setCityId(Long cityId) {
		this.cityId = cityId;
	}
    
    /**
     * 交通枢纽所在国家的城市名称
     */
    private String cityName;
    
    
    public String getCityName() {
		return cityName;
	}
    public void setCityName(String cityName) {
		this.cityName = cityName;
	}
    
    /**
     * 交通枢纽所在国家的县级在去哪儿网的id
     */
    private Long countyId;
    
    
    public Long getCountyId() {
		return countyId;
	}
    public void setCountyId(Long countyId) {
		this.countyId = countyId;
	}
    
    /**
     * 交通枢纽所在国家的县级名称
     */
    private String countyName;
    
    
    public String getCountyName() {
		return countyName;
	}
    public void setCountyName(String countyName) {
		this.countyName = countyName;
	}
    
    /**
     *
     *
     */
    private Long score;
    
    
    public Long getScore() {
		return score;
	}
    public void setScore(Long score) {
		this.score = score;
	}
    
    /**
     * 
     * 
     */
    private String trDays;
    
    
    public String getTrDays() {
		return trDays;
	}
    public void setTrDays(String trDays) {
		this.trDays = trDays;
	}
    
    /**
     * 
     * 
     */
    private Long trDay;
    
    
    public Long getTrDay() {
		return trDay;
	}
    public void setTrDay(Long trDay) {
		this.trDay = trDay;
	}
    
    /**
     * 
     * 
     */
    private String trMonth;
    
    
    public String getTrMonth() {
		return trMonth;
	}
    public void setTrMonth(String trMonth) {
		this.trMonth = trMonth;
	}
    
    /**
     * 开放时间
     * 
     */
    private String openTime;
    
    
    public String getOpenTime() {
		return openTime;
	}
    public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
    
    /**
     * 联系电话
     * 
     */
    private String tel;
    
    
    public String getTel() {
		return tel;
	}
    public void setTel(String tel) {
		this.tel = tel;
	}
    
    /**
     * 交通风格
     */
    private String style;
    
    
    public String getStyle() {
		return style;
	}
    public void setStyle(String style) {
		this.style = style;
	}
    
    /**
     *
     *
     */
    private String special;
    
    
    public String getSpecial() {
		return special;
	}
    public void setSpecial(String special) {
		this.special = special;
	}
    
    /**
     * 
     * 
     */
    private List<String> specialties;
    
    
    public List<String> getSpecialties() {
		return specialties;
	}
    public void setSpecialties(List<String> specialties) {
		this.specialties = specialties;
	}
    
    /**
     * 推荐信息
     */
    private String recommend;
    
    
    public String getRecommend() {
		return recommend;
	}
    public void setRecommend(String recommend) {
		this.recommend = recommend;
	}
    
    /**
     * 是否可用
     */
    private Boolean valid;
    
    
    public Boolean getValid() {
		return valid;
	}
	public void setValid(Boolean valid) {
		this.valid = valid;
	}
	
	/**
	 * 图片地址
	 */
	private String image;
	
	
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	
	/**
	 * 交通枢纽地址
	 */
	private String addr;
	
	
	public String getAddr() {
		return addr;
	}
	public void setAddr(String addr) {
		this.addr = addr;
	}
	
	/**
	 * 交通枢纽简介
	 */
	private String intro;
	
	
	public String getIntro() {
		return intro;
	}
	public void setIntro(String intro) {
		this.intro = intro;
	}
	
	/**
	 * 
	 * 
	 */
	private Integer stars;
	
	
	public Integer getStars() {
		return stars;
	}
	public void setStars(Integer stars) {
		this.stars = stars;
	}
	
	/**
	 * 
	 * 
	 */
	private  Integer sightLevel;
	
	
	public Integer getSightLevel() {
		return sightLevel;
	}
	public void setSightLevel(Integer sightLevel) {
		this.sightLevel = sightLevel;
	}
	
	/**
	 * 推荐次数
	 */
	private Integer recommendCount;
	
	
	public Integer getRecommendCount() {
		return recommendCount;
	}
	public void setRecommendCount(Integer recommendCount) {
		this.recommendCount = recommendCount;
	}
	
	/**
	 * 
	 * 
	 */
	private Integer imageCount;
	
	
	public Integer getImageCount() {
		return imageCount;
	}
	public void setImageCount(Integer imageCount) {
		this.imageCount = imageCount;
	}
	
	/**
	 * 
	 * 
	 */
	private Integer viewCount;
	
	
	public Integer getViewCount() {
		return viewCount;
	}
	public void setViewCount(Integer viewCount) {
		this.viewCount = viewCount;
	}
	
	/**
	 * 
	 * 
	 */
	private Integer referCount;
	
	
	public Integer getReferCount() {
		return referCount;
	}
	public void setReferCount(Integer referCount) {
		this.referCount = referCount;
	}
	
	/**
	 * 
	 * 
	 */
	private Integer commentCount;
	
	
	public Integer getCommentCount() {
		return commentCount;
	}
	public void setCommentCount(Integer commentCount) {
		this.commentCount = commentCount;
	}
	
	
	/**
	 * 百度纬度
	 */
	private Double blat;
	
	
	public Double getBlat() {
		return blat;
	}
	public void setBlat(Double blat) {
		this.blat = blat;
	}
	
	/**
	 * 百度经度
	 */
	private Double blng;
	
	
	public Double getBlng() {
		return blng;
	}
	public void setBlng(Double blng) {
		this.blng = blng;
	}
	
	/**
	 * 谷歌纬度
	 */
	private Double glat;
	
	
	public Double getGlat() {
		return glat;
	}
	public void setGlat(Double glat) {
		this.glat = glat;
	}
	
	/**
	 * 谷歌经度
	 */
	private Double glng;
	
	
	public Double getGlng() {
		return glng;
	}
	public void setGlng(Double glng) {
		this.glng = glng;
	}
	
	/**
	 * 
	 * 
	 */
	private List<String> tradingArea;
	
	
	public List<String> getTradingArea() {
		return tradingArea;
	}
	public void setTradingArea(List<String> tradingArea) {
		this.tradingArea = tradingArea;
	}
	
	/**
	 * 
	 * 
	 */
	private String sightAreaName;
	
	
	public String getSightAreaName() {
		return sightAreaName;
	}
	public void setSightAreaName(String sightAreaName) {
		this.sightAreaName = sightAreaName;
	}
	
	
	/**
	 * 
	 * 
	 */
	private Timestamp cTime;
	
	
	public Timestamp getcTime() {
		return cTime;
	}
	public void setcTime(Timestamp cTime) {
		this.cTime = cTime;
	}
	
	/**
	 * 
	 * 
	 */
	private Timestamp uTime;
	
	
	public Timestamp getuTime() {
		return uTime;
	}
	public void setuTime(Timestamp uTime) {
		this.uTime = uTime;
	}
	
	/**
	 * 
	 * 
	 */
	private List<String> tag;
	
	
	public List<String> getTag() {
		return tag;
	}
	public void setTag(List<String> tag) {
		this.tag = tag;
	}
	
	/**
	 * 
	 */
	private String trTag;
	
	
	public String getTrTag() {
		return trTag;
	}
	public void setTrTag(String trTag) {
		this.trTag = trTag;
	}
	
	/**
	 * 档次
	 */
	private String dangci;
	
	
	public String getDangci() {
		return dangci;
	}
	public void setDangci(String dangci) {
		this.dangci = dangci;
	}
	
	/**
	 * 
	 */
	private String typeRank;
	
	
	public String getTypeRank() {
		return typeRank;
	}
	public void setTypeRank(String typeRank) {
		this.typeRank = typeRank;
	}
	
	/**
	 * 
	 * 
	 */
	private String typeTotal;
	
	
	public String getTypeTotal() {
		return typeTotal;
	}
	public void setTypeTotal(String typeTotal) {
		this.typeTotal = typeTotal;
	}
	
	/**
	 * 
	 * 
	 */
	private String goPercent;
	
	
	public String getGoPercent() {
		return goPercent;
	}
	public void setGoPercent(String goPercent) {
		this.goPercent = goPercent;
	}
	
	/**
	 * 
	 * 
	 */
	private String trSeason;
	
	
	public String getTrSeason() {
		return trSeason;
	}
	public void setTrSeason(String trSeason) {
		this.trSeason = trSeason;
	}
	
	/**
	 * 
	 * 
	 */
	private Long trTimeMin;

	
	public Long getTrTimeMin() {
		return trTimeMin;
	}
	public void setTrTimeMin(Long trTimeMin) {
		this.trTimeMin = trTimeMin;
	}
	
	/**
	 * 
	 * 
	 */
	private Long trTimeMax;
	
	
	public Long getTrTimeMax() {
		return trTimeMax;
	}
	public void setTrTimeMax(Long trTimeMax) {
		this.trTimeMax = trTimeMax;
	}
	
	
	/**
	 * 类别
	 */
	private List<String> categories;
	
	
	public List<String> getCategories() {
		return categories;
	}
	public void setCategories(List<String> categories) {
		this.categories = categories;
	}
	
	/**
	 * 
	 * 
	 */
	private List<String> locatedDists;
	
	
	public List<String> getLocatedDists() {
		return locatedDists;
	}
	public void setLocatedDists(List<String> locatedDists) {
		this.locatedDists = locatedDists;
	}
	
	
	/**
	 * 子类别
	 */
	private Integer subType;
	
	
	public Integer getSubType() {
		return subType;
	}
	public void setSubType(Integer subType) {
		this.subType = subType;
	}
	
	/**
	 * 
	 * 
	 */
	private Integer mcityId;
	
	
	public Integer getMcityId() {
		return mcityId;
	}
	public void setMcityId(Integer mcityId) {
		this.mcityId = mcityId;
	}
	
	/**
	 * 
	 */
	private Integer qtScore;
	
	
	public Integer getQtScore() {
		return qtScore;
	}
	public void setQtScore(Integer qtScore) {
		this.qtScore = qtScore;
	}
	
	/**
	 * 
	 */
	private List<Integer> joinIds;
	
	
	public List<Integer> getJoinIds() {
		return joinIds;
	}
	public void setJoinIds(List<Integer> joinIds) {
		this.joinIds = joinIds;
	}
	
	/**
	 * 
	 * 
	 */
	private Integer touchPayPrice;
	
	
	public Integer getTouchPayPrice() {
		return touchPayPrice;
	}
	public void setTouchPayPrice(Integer touchPayPrice) {
		this.touchPayPrice = touchPayPrice;
	}
	
	/**
	 * 
	 * 
	 */
	private Float ratingScore;
	
	
	public Float getRatingScore() {
		return ratingScore;
	}
	public void setRatingScore(Float ratingScore) {
		this.ratingScore = ratingScore;
	}
	
	/**
	 * 
	 * 
	 */
	private Boolean hasDeal;
	
	
	public Boolean getHasDeal() {
		return hasDeal;
	}
	public void setHasDeal(Boolean hasDeal) {
		this.hasDeal = hasDeal;
	}
	
	/**
	 * 
	 * 
	 */
	 private String serialNumber;
	 
	 
	 public String getSerialNumber() {
		return serialNumber;
	}
	 public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	 
	/**
	 * 
	 * 
	 */
	private Integer businessStatus;
	
	
	public Integer getBusinessStatus() {
		return businessStatus;
	}
	public void setBusinessStatus(Integer businessStatus) {
		this.businessStatus = businessStatus;
	}
	
	/**
	 * 
	 * 
	 */
	private String distName;
	
	
	public String getDistName() {
		return distName;
	}
	public void setDistName(String distName) {
		this.distName = distName;
	}
	
	/**
	 * 经度
	 */
	private Double lng;

    
    public Double getLng() {
        return lng;
    }
    public void setLng(Double lng) {
        this.lng = lng;
    }

    /**
     * 纬度
     */
    private Double lat;

    
    public Double getLat() {
        return lat;
    }
    public void setLat(Double lat) {
        this.lat = lat;
    }
	
    /**
     * 
     * 
     */
	private Boolean abroad;
	
	
	public Boolean getAbroad() {
		return abroad;
	}
	public void setAbroad(Boolean abroad) {
		this.abroad = abroad;
	}

	/**
	 * 
	 * 
	 */
	private Integer distId;
	
	
	public Integer getDistId() {
		return distId;
	}
	public void setDistId(Integer distId) {
		this.distId = distId;
	}
	
	/**
	 * 是否世界遗产
	 * 
	 */
	private Boolean worldHeritage;

	
	public Boolean getWorldHeritage() {
		return worldHeritage;
	}
	public void setWorldHeritage(Boolean worldHeritage) {
		this.worldHeritage = worldHeritage;
	}
	
	/**
	 * 
	 * 
	 */
	private String priceDesc;
	
	
	public String getPriceDesc() {
		return priceDesc;
	}
	public void setPriceDesc(String priceDesc) {
		this.priceDesc = priceDesc;
	}
	
	/**
	 * 
	 * 
	 */
	private Integer priceNumber;
	
	
	public Integer getPriceNumber() {
		return priceNumber;
	}
	public void setPriceNumber(Integer priceNumber) {
		this.priceNumber = priceNumber;
	}
    
	/**
	 * 创建时间
	 */
	private Timestamp createTime;

	
    public Timestamp getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Timestamp createTime) {
		this.createTime = createTime;
	}
	
	/**
	 * 修改时间
	 */
	private Timestamp modifyTime;
	
	
	public Timestamp getModifyTime() {
		return modifyTime;
	}
	public void setModifyTime(Timestamp modifyTime) {
		this.modifyTime = modifyTime;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return getName();
	}
	
	
	@Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Station that = (Station) o;

        if (id != that.id) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (lng != null ? !lng.equals(that.lng) : that.lng != null) return false;
        if (lat != null ? !lat.equals(that.lat) : that.lat != null) return false;
        if (glng != null ? !glng.equals(that.glng) : that.glng != null) return false;
        if (glat != null ? !glat.equals(that.glat) : that.glat != null) return false;
        if (cityId != null ? !cityId.equals(that.cityId) : that.cityId != null) return false;
        if (regionName != null ? !regionName.equals(that.regionName) : that.regionName != null) return false;
        if (addr != null ? !addr.equals(that.addr) : that.addr != null) return false;
        if (tel != null ? !tel.equals(that.tel) : that.tel != null) return false;
        if (image != null ? !image.equals(that.image) : that.image != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        if (modifyTime != null ? !modifyTime.equals(that.modifyTime) : that.modifyTime != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (lng != null ? lng.hashCode() : 0);
        result = 31 * result + (lat != null ? lat.hashCode() : 0);
        result = 31 * result + (glng != null ? glng.hashCode() : 0);
        result = 31 * result + (glat != null ? glat.hashCode() : 0);
        result = 31 * result + (cityId != null ? cityId.hashCode() : 0);
        result = 31 * result + (regionName != null ? regionName.hashCode() : 0);
        result = 31 * result + (addr != null ? addr.hashCode() : 0);
        result = 31 * result + (tel != null ? tel.hashCode() : 0);
        result = 31 * result + (image != null ? image.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (modifyTime != null ? modifyTime.hashCode() : 0);
        return result;
    }
}
