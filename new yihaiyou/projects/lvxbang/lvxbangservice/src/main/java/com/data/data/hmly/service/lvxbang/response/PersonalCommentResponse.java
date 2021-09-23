package com.data.data.hmly.service.lvxbang.response;

import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;

/**
 * Created by vacuity on 16/1/18.
 */
public class PersonalCommentResponse {

    private Long id;
    private String time;
    private String targetName;
    private String targetCover;
    private ProductType productType;
    private String content;
    private String detail;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetCover() {
        return targetCover;
    }

    public void setTargetCover(String targetCover) {
        this.targetCover = cover(targetCover);
    }

    public ProductType getProductType() {
        return productType;
    }

    public void setProductType(ProductType productType) {
        this.productType = productType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
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
}
