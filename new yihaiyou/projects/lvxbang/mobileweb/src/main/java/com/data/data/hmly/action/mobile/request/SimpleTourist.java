package com.data.data.hmly.action.mobile.request;

import com.data.data.hmly.enums.Gender;
import com.data.data.hmly.service.order.entity.OrderTourist;
import com.data.data.hmly.service.order.entity.enums.OrderTouristIdType;
import com.data.data.hmly.service.order.entity.enums.OrderTouristPeopleType;
import com.data.data.hmly.service.user.entity.Tourist;
import com.data.data.hmly.service.user.entity.enums.TouristIdType;
import com.data.data.hmly.service.user.entity.enums.TouristPeopleType;

/**
 * Created by huangpeijie on 2016-04-25,0025.
 */
public class SimpleTourist {
    private Long touristId;
    private String name;
    private TouristIdType idType;
    private String idNumber;
    private String telephone;
    private Gender gender;
    private TouristPeopleType peopleType;

    public SimpleTourist() {
    }

    public SimpleTourist(Tourist tourist) {
        this.touristId = tourist.getId();
        this.name = tourist.getName();
        this.idType = tourist.getIdType();
        this.idNumber = tourist.getIdNumber();
        this.telephone = tourist.getTel();
        this.gender = tourist.getGender();
        this.peopleType = tourist.getPeopleType();
    }

    public SimpleTourist(OrderTourist tourist) {
        this.touristId = tourist.getId();
        this.name = tourist.getName();
        if (OrderTouristIdType.IDCARD.equals(tourist.getIdType())) {
            this.idType = TouristIdType.IDCARD;
        } else if (OrderTouristIdType.PASSPORT.equals(tourist.getIdType())) {
            this.idType = TouristIdType.PASSPORT;
        } else if (OrderTouristIdType.STUDENTCARD.equals(tourist.getIdType())) {
            this.idType = TouristIdType.STUDENTCARD;
        }
        this.idNumber = tourist.getIdNumber();
        this.telephone = tourist.getTel();
        this.gender = tourist.getGender();
        if (OrderTouristPeopleType.ADULT.equals(tourist.getPeopleType())) {
            this.peopleType = TouristPeopleType.ADULT;
        } else if (OrderTouristPeopleType.CHILD.equals(tourist.getPeopleType())) {
            this.peopleType = TouristPeopleType.KID;
        }
    }

    public Long getTouristId() {
        return touristId;
    }

    public void setTouristId(Long touristId) {
        this.touristId = touristId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public TouristIdType getIdType() {
        return idType;
    }

    public void setIdType(TouristIdType idType) {
        this.idType = idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Gender getGender() {
        return gender;
    }

    public void setGender(Gender gender) {
        this.gender = gender;
    }

    public TouristPeopleType getPeopleType() {
        return peopleType;
    }

    public void setPeopleType(TouristPeopleType peopleType) {
        this.peopleType = peopleType;
    }
}