package com.data.data.hmly.action.lvxbang.response;

import com.data.data.hmly.service.scenic.entity.ScenicGallery;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.google.common.base.Function;
import com.google.common.collect.Lists;

import java.util.List;

/**
 * Created by guoshijie on 2015/12/15.
 */
public class HandDrawScenicResponse {

	private Long id;
	private String name;
	private Integer commentNum;
	private Integer adviceMinute;
	private String address;
	private String shortComment;
	private String cover;
    private Float price;
	private Integer cityId;
    private List<String> galleryList;
    private Float score;
    private Long recommendPlanId;
    private String recommendPlanName;

	public HandDrawScenicResponse(ScenicInfo scenicInfo) {
		this.id = scenicInfo.getId();
		this.name = scenicInfo.getName();
		this.adviceMinute = scenicInfo.getScenicOther().getAdviceTime();
		this.commentNum = scenicInfo.getScenicStatistics().getCommentNum();
		this.address = scenicInfo.getScenicOther().getAddress();
		this.shortComment = scenicInfo.getScenicOther().getRecommendReason();
        this.price = scenicInfo.getPrice();
        this.cover = scenicInfo.getCover();
        this.galleryList = Lists.transform(scenicInfo.getScenicGalleryList(), new Function<ScenicGallery, String>() {
            @Override
            public String apply(ScenicGallery scenicGallery) {
                return scenicGallery.getImgUrl();
            }
        });
        this.score = (float) scenicInfo.getScore();
		this.cityId = scenicInfo.getCityId();
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

	public Integer getCommentNum() {
		return commentNum;
	}

	public void setCommentNum(Integer commentNum) {
		this.commentNum = commentNum;
	}

	public Integer getAdviceMinute() {
		return adviceMinute;
	}

	public void setAdviceMinute(Integer adviceMinute) {
		this.adviceMinute = adviceMinute;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getShortComment() {
		return shortComment;
	}

	public void setShortComment(String shortComment) {
		this.shortComment = shortComment;
	}

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

    public List<String> getGalleryList() {
        return galleryList;
    }

    public void setGalleryList(List<String> galleryList) {
        this.galleryList = galleryList;
    }

    public Float getScore() {
        return score;
    }

    public void setScore(Float score) {
        this.score = score;
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

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
}
