//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.8-b130911.1802 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.03 at 10:34:35 AM CST 
//


package com.data.data.hmly.service.elong.pojo;

import com.alibaba.fastjson.annotation.JSONField;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import java.math.BigDecimal;
import java.util.List;


/**
 * <p>Java class for HotelRules complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="HotelRules">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BookingRules" type="{}ArrayOfBookingRule" minOccurs="0"/>
 *         &lt;element name="GuaranteeRules" type="{}ArrayOfGuaranteeRule" minOccurs="0"/>
 *         &lt;element name="PrepayRules" type="{}ArrayOfPrepayRule" minOccurs="0"/>
 *         &lt;element name="ValueAdds" type="{}ArrayOfValueAdd" minOccurs="0"/>
 *         &lt;element name="DrrRules" type="{}ArrayOfDrrRule" minOccurs="0"/>
 *         &lt;element name="Gifts" type="{}ArrayOfGift" minOccurs="0"/>
 *         &lt;element name="CurrencyCode" type="{}EnumCurrencyCode"/>
 *         &lt;element name="LowPrice" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="MinPriceRPID" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="HAvailPolicys" type="{}ArrayOfHAvailPolicy" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HotelRules", propOrder = {
        "bookingRules",
        "guaranteeRules",
        "prepayRules",
        "valueAdds",
        "drrRules",
        "gifts",
        "currencyCode",
        "lowPrice",
        "minPriceRPID",
        "hAvailPolicys"
})
public class HotelRules {

    @JSONField(name = "BookingRules")
    protected List<BookingRule> bookingRules;
    @JSONField(name = "GuaranteeRules")
    protected List<GuaranteeRule> guaranteeRules;
    @JSONField(name = "PrepayRules")
    protected List<PrepayRule> prepayRules;
    @JSONField(name = "ValueAdds")
    protected List<ValueAdd> valueAdds;
    @JSONField(name = "DrrRules")
    protected List<DrrRule> drrRules;
    @JSONField(name = "Gifts")
    protected List<Gift> gifts;
    @JSONField(name = "CurrencyCode")
    @XmlSchemaType(name = "string")
    protected EnumCurrencyCode currencyCode;
    @JSONField(name = "LowPrice")
    protected BigDecimal lowPrice;
    @JSONField(name = "MinPriceRPID")
    protected int minPriceRPID;
    @JSONField(name = "HAvailPolicys")
    protected List<HAvailPolicy> hAvailPolicys;

    /**
     * Gets the value of the bookingRules property.
     *
     * @return possible object is
     * {@link List < BookingRule > }
     */
    public List<BookingRule> getBookingRules() {
        return bookingRules;
    }

    /**
     * Sets the value of the bookingRules property.
     *
     * @param value allowed object is
     *              {@link List < BookingRule > }
     */
    public void setBookingRules(List<BookingRule> value) {
        this.bookingRules = value;
    }

    /**
     * Gets the value of the guaranteeRules property.
     *
     * @return possible object is
     * {@link List < GuaranteeRule > }
     */
    public List<GuaranteeRule> getGuaranteeRules() {
        return guaranteeRules;
    }

    /**
     * Sets the value of the guaranteeRules property.
     *
     * @param value allowed object is
     *              {@link List < GuaranteeRule > }
     */
    public void setGuaranteeRules(List<GuaranteeRule> value) {
        this.guaranteeRules = value;
    }

    /**
     * Gets the value of the prepayRules property.
     *
     * @return possible object is
     * {@link List <PrepayRule> }
     */
    public List<PrepayRule> getPrepayRules() {
        return prepayRules;
    }

    /**
     * Sets the value of the prepayRules property.
     *
     * @param value allowed object is
     *              {@link List <PrepayRule> }
     */
    public void setPrepayRules(List<PrepayRule> value) {
        this.prepayRules = value;
    }

    /**
     * Gets the value of the valueAdds property.
     *
     * @return possible object is
     * {@link List < ValueAdd > }
     */
    public List<ValueAdd> getValueAdds() {
        return valueAdds;
    }

    /**
     * Sets the value of the valueAdds property.
     *
     * @param value allowed object is
     *              {@link List < ValueAdd > }
     */
    public void setValueAdds(List<ValueAdd> value) {
        this.valueAdds = value;
    }

    /**
     * Gets the value of the drrRules property.
     *
     * @return possible object is
     * {@link List < DrrRule > }
     */
    public List<DrrRule> getDrrRules() {
        return drrRules;
    }

    /**
     * Sets the value of the drrRules property.
     *
     * @param value allowed object is
     *              {@link List < DrrRule > }
     */
    public void setDrrRules(List<DrrRule> value) {
        this.drrRules = value;
    }

    /**
     * Gets the value of the gifts property.
     *
     * @return possible object is
     * {@link List < Gift > }
     */
    public List<Gift> getGifts() {
        return gifts;
    }

    /**
     * Sets the value of the gifts property.
     *
     * @param value allowed object is
     *              {@link List < Gift > }
     */
    public void setGifts(List<Gift> value) {
        this.gifts = value;
    }

    /**
     * Gets the value of the currencyCode property.
     *
     * @return possible object is
     * {@link EnumCurrencyCode }
     */
    public EnumCurrencyCode getCurrencyCode() {
        return currencyCode;
    }

    /**
     * Sets the value of the currencyCode property.
     *
     * @param value allowed object is
     *              {@link EnumCurrencyCode }
     */
    public void setCurrencyCode(EnumCurrencyCode value) {
        this.currencyCode = value;
    }

    /**
     * Gets the value of the lowPrice property.
     *
     * @return possible object is
     * {@link BigDecimal }
     */
    public BigDecimal getLowPrice() {
        return lowPrice;
    }

    /**
     * Sets the value of the lowPrice property.
     *
     * @param value allowed object is
     *              {@link BigDecimal }
     */
    public void setLowPrice(BigDecimal value) {
        this.lowPrice = value;
    }

    /**
     * Gets the value of the minPriceRPID property.
     */
    public int getMinPriceRPID() {
        return minPriceRPID;
    }

    /**
     * Sets the value of the minPriceRPID property.
     */
    public void setMinPriceRPID(int value) {
        this.minPriceRPID = value;
    }

    /**
     * Gets the value of the hAvailPolicys property.
     *
     * @return possible object is
     * {@link List < HAvailPolicy > }
     */
    public List<HAvailPolicy> getHAvailPolicys() {
        return hAvailPolicys;
    }

    /**
     * Sets the value of the hAvailPolicys property.
     *
     * @param value allowed object is
     *              {@link List < HAvailPolicy > }
     */
    public void setHAvailPolicys(List<HAvailPolicy> value) {
        this.hAvailPolicys = value;
    }

}
