package com.data.data.hmly.service.order.entity;

import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.order.entity.enums.CreditCardIdType;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Created by zzl on 2016/4/27.
 */
@Entity
@Table(name = "credit_card")
public class CreditCard extends com.framework.hibernate.util.Entity implements java.io.Serializable {

    @Id
    @GeneratedValue
    @Column(name = "id", updatable = false, nullable = false)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private Member user;

    @Column(name = "card_num")
    private String cardNum;

    @Column(name = "cvv")
    private String cvv;

    @Column(name = "expiration_year")
    private Integer expirationYear;

    @Column(name = "expiration_month")
    private Integer expirationMonth;

    @Column(name = "holder_name")
    private String holderName;

    @Column(name = "id_type")
    @Enumerated(EnumType.STRING)
    private CreditCardIdType creditCardIdType;

    @Column(name = "id_no")
    private String idNo;

    @Column(name = "card_password")
    private String cardPassword;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Member getUser() {
        return user;
    }

    public void setUser(Member user) {
        this.user = user;
    }

    public String getCardNum() {
        return cardNum;
    }

    public void setCardNum(String cardNum) {
        this.cardNum = cardNum;
    }

    public String getCvv() {
        return cvv;
    }

    public void setCvv(String cvv) {
        this.cvv = cvv;
    }

    public Integer getExpirationYear() {
        return expirationYear;
    }

    public void setExpirationYear(Integer expirationYear) {
        this.expirationYear = expirationYear;
    }

    public Integer getExpirationMonth() {
        return expirationMonth;
    }

    public void setExpirationMonth(Integer expirationMonth) {
        this.expirationMonth = expirationMonth;
    }

    public String getHolderName() {
        return holderName;
    }

    public void setHolderName(String holderName) {
        this.holderName = holderName;
    }

    public CreditCardIdType getCreditCardIdType() {
        return creditCardIdType;
    }

    public void setCreditCardIdType(CreditCardIdType creditCardIdType) {
        this.creditCardIdType = creditCardIdType;
    }

    public String getIdNo() {
        return idNo;
    }

    public void setIdNo(String idNo) {
        this.idNo = idNo;
    }

    public String getCardPassword() {
        return cardPassword;
    }

    public void setCardPassword(String cardPassword) {
        this.cardPassword = cardPassword;
    }
}
