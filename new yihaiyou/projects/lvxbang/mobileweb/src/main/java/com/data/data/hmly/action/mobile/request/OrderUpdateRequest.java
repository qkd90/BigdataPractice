package com.data.data.hmly.action.mobile.request;

import com.data.data.hmly.service.user.entity.Tourist;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-04-21,0021.
 */
public class OrderUpdateRequest {
    private Long id;
    private String name;
    private Integer day;
    private String playDate;
    private List<Map<String, Object>> details;
    private List<Long> tourists;
    private List<Long> childTourists;
    private OrderContact contact;
    private OrderInvoice invoice;
    private Long couponId;
    private String orderType;
    private List<Object> insurances;
    private Map<String, Object> creditCard;

    public void completeDetails(List<Tourist> touristList) {
        if (this.details != null && touristList != null) {
            List<Map<String, Object>> tourists = Lists.newArrayList();
            for (Tourist tourist : touristList) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("name", tourist.getName());
                map.put("phone", tourist.getTel());
                map.put("peopleType", tourist.getPeopleType());
                map.put("idType", tourist.getIdType());
                map.put("idNum", tourist.getIdNumber());
                tourists.add(map);
            }
            if ("line".equals(this.orderType)) {
                this.details.get(0).put("tourist", tourists);
            } else {
                for (Map<String, Object> detail : details) {
                    detail.put("tourist", tourists);
                }
            }
        }
    }

    public void completeChildDetail(List<Tourist> touristList) {
        if (this.details != null && this.details.size() > 1 && touristList != null) {
            List<Map<String, Object>> tourists = Lists.newArrayList();
            for (Tourist tourist : touristList) {
                Map<String, Object> map = Maps.newHashMap();
                map.put("name", tourist.getName());
                map.put("phone", tourist.getTel());
                map.put("peopleType", tourist.getPeopleType());
                map.put("idType", tourist.getIdType());
                map.put("idNum", tourist.getIdNumber());
                tourists.add(map);
            }
            this.details.get(1).put("tourist", tourists);
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

    public Integer getDay() {
        return day;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    public List<Map<String, Object>> getDetails() {
        return details;
    }

    public void setDetails(List<Map<String, Object>> details) {
        this.details = details;
    }

    public List<Long> getTourists() {
        return tourists;
    }

    public void setTourists(List<Long> tourists) {
        this.tourists = tourists;
    }

    public OrderContact getContact() {
        return contact;
    }

    public void setContact(OrderContact contact) {
        this.contact = contact;
    }

    public OrderInvoice getInvoice() {
        return invoice;
    }

    public void setInvoice(OrderInvoice invoice) {
        this.invoice = invoice;
    }

    public String getPlayDate() {
        return playDate;
    }

    public void setPlayDate(String playDate) {
        this.playDate = playDate;
    }

    public Long getCouponId() {
        return couponId;
    }

    public void setCouponId(Long couponId) {
        this.couponId = couponId;
    }

    public String getOrderType() {
        return orderType;
    }

    public void setOrderType(String orderType) {
        this.orderType = orderType;
    }

    public List<Object> getInsurances() {
        return insurances;
    }

    public void setInsurances(List<Object> insurances) {
        this.insurances = insurances;
    }

    public List<Long> getChildTourists() {
        return childTourists;
    }

    public void setChildTourists(List<Long> childTourists) {
        this.childTourists = childTourists;
    }

    public Map<String, Object> getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(Map<String, Object> creditCard) {
        this.creditCard = creditCard;
    }
}
