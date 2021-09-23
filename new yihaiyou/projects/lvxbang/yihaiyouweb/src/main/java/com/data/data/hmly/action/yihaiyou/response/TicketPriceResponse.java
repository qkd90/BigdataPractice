package com.data.data.hmly.action.yihaiyou.response;

import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.ticket.entity.TicketPrice;
import com.data.data.hmly.service.ticket.entity.TicketPriceTypeExtend;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.zuipin.util.QiniuUtil;
import com.zuipin.util.StringUtils;

import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-10-24,0024.
 */
public class TicketPriceResponse {
    private Long id;
    private String name;
    private String cover;
    private Long ticketId;
    private String scenicName;
    private String ticketName;
    private Float maketPrice;
    private Float discountPrice;
    private String startDate;
    private String endDate;
    private Boolean isTodayValid;
    private Boolean isConditionRefund;
    private Integer orderNum;
    private List<List<TicketPriceTypeExtend>> extendLists;
    private List<Comment> commentList;
    private Long commentCount;
    private Integer score;

    public TicketPriceResponse() {
    }

    public TicketPriceResponse(TicketPrice ticketPrice) {
        this.id = ticketPrice.getId();
        this.name = ticketPrice.getName();
//        this.cover = cover(ticketPrice.getTicket().getTicketImgUrl());
        this.ticketId = ticketPrice.getTicket().getId();
        this.scenicName = ticketPrice.getTicket().getScenicInfo().getName();
        this.ticketName = ticketPrice.getTicket().getName();
        this.maketPrice = ticketPrice.getMaketPrice();
        this.discountPrice = ticketPrice.getDiscountPrice();
        this.isTodayValid = ticketPrice.getIsTodayValid();
        this.orderNum = ticketPrice.getOrderNum();
        this.isConditionRefund = ticketPrice.getIsConditionRefund();
        Map<String, List<TicketPriceTypeExtend>> map = Maps.newLinkedHashMap();
        for (TicketPriceTypeExtend typeExtend : ticketPrice.getTicketPriceTypeExtends()) {
            List<TicketPriceTypeExtend> typeExtends = map.get(typeExtend.getFirstTitle());
            if (typeExtends == null) {
                typeExtends = Lists.newArrayList();
            }
            typeExtends.add(typeExtend);
            map.put(typeExtend.getFirstTitle(), typeExtends);
        }
        this.extendLists = Lists.newArrayList(map.values());
    }

    private String cover(String cover) {
        if (StringUtils.isBlank(cover)) {
            return QiniuUtil.URL + "jingdian.png";
        } else {
            if (cover.startsWith("http")) {
                return cover;
            } else {
                return QiniuUtil.URL + cover;
            }
        }
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

    public Long getTicketId() {
        return ticketId;
    }

    public void setTicketId(Long ticketId) {
        this.ticketId = ticketId;
    }

    public Float getMaketPrice() {
        return maketPrice;
    }

    public void setMaketPrice(Float maketPrice) {
        this.maketPrice = maketPrice;
    }

    public Float getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Float discountPrice) {
        this.discountPrice = discountPrice;
    }

    public List<List<TicketPriceTypeExtend>> getExtendLists() {
        return extendLists;
    }

    public void setExtendLists(List<List<TicketPriceTypeExtend>> extendLists) {
        this.extendLists = extendLists;
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

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getScenicName() {
        return scenicName;
    }

    public void setScenicName(String scenicName) {
        this.scenicName = scenicName;
    }

    public Boolean getIsTodayValid() {
        return isTodayValid;
    }

    public void setIsTodayValid(Boolean isTodayValid) {
        this.isTodayValid = isTodayValid;
    }

    public Boolean getIsConditionRefund() {
        return isConditionRefund;
    }

    public void setIsConditionRefund(Boolean isConditionRefund) {
        this.isConditionRefund = isConditionRefund;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }

    public Long getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Long commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getOrderNum() {
        return orderNum;
    }

    public void setOrderNum(Integer orderNum) {
        this.orderNum = orderNum;
    }

    public Integer getScore() {
        return score;
    }

    public void setScore(Integer score) {
        this.score = score;
    }

    public String getTicketName() {
        return ticketName;
    }

    public void setTicketName(String ticketName) {
        this.ticketName = ticketName;
    }
}
