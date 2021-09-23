//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.12.02 时间 07:09:07 PM CST 
//


package com.data.data.hmly.service.ctriphotel.priceinfo.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RatePlanType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RatePlanType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BookingRules" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BookingRule" type="{}BookingRuleType"/>
 *         &lt;element name="Rates" type="{}RatesType"/>
 *         &lt;element name="Offers" type="{}OffersType"/>
 *         &lt;element name="SellableProducts" type="{}SellableProductsType"/>
 *         &lt;element name="Description" type="{}DescriptionType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="RatePlanCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="RatePlanCategory" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="IsCommissionable" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="RateReturn" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="MarketCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RatePlanType", propOrder = {
    "bookingRules",
    "bookingRule",
    "rates",
    "offers",
    "sellableProducts",
    "description"
})
public class RatePlanType {

    @XmlElement(name = "BookingRules", required = true)
    protected String bookingRules;
    @XmlElement(name = "BookingRule", required = true)
    protected BookingRuleType bookingRule;
    @XmlElement(name = "Rates", required = true)
    protected RatesType rates;
    @XmlElement(name = "Offers", required = true)
    protected OffersType offers;
    @XmlElement(name = "SellableProducts", required = true)
    protected SellableProductsType sellableProducts;
    @XmlElement(name = "Description", required = true)
    protected DescriptionType description;
    @XmlAttribute(name = "RatePlanCode")
    protected String ratePlanCode;
    @XmlAttribute(name = "RatePlanCategory")
    protected String ratePlanCategory;
    @XmlAttribute(name = "IsCommissionable")
    protected String isCommissionable;
    @XmlAttribute(name = "RateReturn")
    protected String rateReturn;
    @XmlAttribute(name = "MarketCode")
    protected String marketCode;

    /**
     * 获取bookingRules属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingRules() {
        return bookingRules;
    }

    /**
     * 设置bookingRules属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingRules(String value) {
        this.bookingRules = value;
    }

    /**
     * 获取bookingRule属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BookingRuleType }
     *     
     */
    public BookingRuleType getBookingRule() {
        return bookingRule;
    }

    /**
     * 设置bookingRule属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BookingRuleType }
     *     
     */
    public void setBookingRule(BookingRuleType value) {
        this.bookingRule = value;
    }

    /**
     * 获取rates属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RatesType }
     *     
     */
    public RatesType getRates() {
        return rates;
    }

    /**
     * 设置rates属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RatesType }
     *     
     */
    public void setRates(RatesType value) {
        this.rates = value;
    }

    /**
     * 获取offers属性的值。
     * 
     * @return
     *     possible object is
     *     {@link OffersType }
     *     
     */
    public OffersType getOffers() {
        return offers;
    }

    /**
     * 设置offers属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link OffersType }
     *     
     */
    public void setOffers(OffersType value) {
        this.offers = value;
    }

    /**
     * 获取sellableProducts属性的值。
     * 
     * @return
     *     possible object is
     *     {@link SellableProductsType }
     *     
     */
    public SellableProductsType getSellableProducts() {
        return sellableProducts;
    }

    /**
     * 设置sellableProducts属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link SellableProductsType }
     *     
     */
    public void setSellableProducts(SellableProductsType value) {
        this.sellableProducts = value;
    }

    /**
     * 获取description属性的值。
     * 
     * @return
     *     possible object is
     *     {@link DescriptionType }
     *     
     */
    public DescriptionType getDescription() {
        return description;
    }

    /**
     * 设置description属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link DescriptionType }
     *     
     */
    public void setDescription(DescriptionType value) {
        this.description = value;
    }

    /**
     * 获取ratePlanCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePlanCode() {
        return ratePlanCode;
    }

    /**
     * 设置ratePlanCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePlanCode(String value) {
        this.ratePlanCode = value;
    }

    /**
     * 获取ratePlanCategory属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRatePlanCategory() {
        return ratePlanCategory;
    }

    /**
     * 设置ratePlanCategory属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRatePlanCategory(String value) {
        this.ratePlanCategory = value;
    }

    /**
     * 获取isCommissionable属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsCommissionable() {
        return isCommissionable;
    }

    /**
     * 设置isCommissionable属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsCommissionable(String value) {
        this.isCommissionable = value;
    }

    /**
     * 获取rateReturn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRateReturn() {
        return rateReturn;
    }

    /**
     * 设置rateReturn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRateReturn(String value) {
        this.rateReturn = value;
    }

    /**
     * 获取marketCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMarketCode() {
        return marketCode;
    }

    /**
     * 设置marketCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMarketCode(String value) {
        this.marketCode = value;
    }

}
