package com.data.data.hmly.service.common.entity;

import javax.persistence.*;


@Entity
@Table(name = "quantity_sales_detail")
//@Polymorphism(type=PolymorphismType.EXPLICIT)
public class QuantitySalesDetail extends com.framework.hibernate.util.Entity implements java.io.Serializable {
    /**
     *
     */
    private static final long serialVersionUID = -9215023448601457011L;
    @Id
    @GeneratedValue
    @Column(name = "id", unique = true, nullable = false)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "quantity_sales_id")
    private QuantitySales quantitySales;


    @Column(name = "num_start", length = 10)
    private Integer numStart;       //拱量起始数量

    @Column(name = "num_end", length = 10)
    private Integer numEnd;         //拱量截至数量


    @Column(name = "discount", length = 10)
    private Float discount;       //折扣

    @Column(name = "discount_child", length = 10)
    private Float discountChild;         //儿童折扣

    @Column(name = "favorable_price", length = 10)
    private Float favorablePrice;       //优惠金额

    @Column(name = "favorable_price_child", length = 10)
    private Float favorablePriceChild;         //优惠金额


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public QuantitySales getQuantitySales() {
        return quantitySales;
    }

    public void setQuantitySales(QuantitySales quantitySales) {
        this.quantitySales = quantitySales;
    }

    public Integer getNumStart() {
        return numStart;
    }

    public void setNumStart(Integer numStart) {
        this.numStart = numStart;
    }

    public Integer getNumEnd() {
        return numEnd;
    }

    public void setNumEnd(Integer numEnd) {
        this.numEnd = numEnd;
    }


    public Float getDiscount() {
        return discount;
    }

    public void setDiscount(Float discount) {
        this.discount = discount;
    }

    public Float getDiscountChild() {
        return discountChild;
    }

    public void setDiscountChild(Float discountChild) {
        this.discountChild = discountChild;
    }

    public Float getFavorablePrice() {
        return favorablePrice;
    }

    public void setFavorablePrice(Float favorablePrice) {
        this.favorablePrice = favorablePrice;
    }

    public Float getFavorablePriceChild() {
        return favorablePriceChild;
    }

    public void setFavorablePriceChild(Float favorablePriceChild) {
        this.favorablePriceChild = favorablePriceChild;
    }
}
