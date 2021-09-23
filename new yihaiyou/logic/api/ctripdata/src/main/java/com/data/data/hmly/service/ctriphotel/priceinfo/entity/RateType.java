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
 * <p>RateType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RateType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BaseByGuestAmts" type="{}BaseByGuestAmtsType"/>
 *         &lt;element name="Fees" type="{}FeesType"/>
 *         &lt;element name="GuaranteePolicies" type="{}GuaranteePoliciesType" minOccurs="0"/>
 *         &lt;element name="CancelPolicies" type="{}CancelPoliciesType" minOccurs="0"/>
 *         &lt;element name="MealsIncluded" type="{}MealsIncludedType"/>
 *         &lt;element name="TPA_Extensions" type="{}TPA_ExtensionsType"/>
 *         &lt;element name="BookingRules" type="{}BookingRulesType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="NumberOfUnits" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Start" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="End" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="IsInstantConfirm" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Status" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RateType", propOrder = {
    "baseByGuestAmts",
    "fees",
    "guaranteePolicies",
    "cancelPolicies",
    "mealsIncluded",
    "tpaExtensions",
    "bookingRules"
})
public class RateType {

    @XmlElement(name = "BaseByGuestAmts", required = true)
    protected BaseByGuestAmtsType baseByGuestAmts;
    @XmlElement(name = "Fees", required = true)
    protected FeesType fees;
    @XmlElement(name = "GuaranteePolicies")
    protected GuaranteePoliciesType guaranteePolicies;
    @XmlElement(name = "CancelPolicies")
    protected CancelPoliciesType cancelPolicies;
    @XmlElement(name = "MealsIncluded", required = true)
    protected MealsIncludedType mealsIncluded;
    @XmlElement(name = "TPA_Extensions", required = true)
    protected TPAExtensionsType tpaExtensions;
    @XmlElement(name = "BookingRules")
    protected BookingRulesType bookingRules;
    @XmlAttribute(name = "NumberOfUnits")
    protected String numberOfUnits;
    @XmlAttribute(name = "Start")
    protected String start;
    @XmlAttribute(name = "End")
    protected String end;
    @XmlAttribute(name = "IsInstantConfirm")
    protected String isInstantConfirm;
    @XmlAttribute(name = "Status")
    protected String status;

    /**
     * 获取baseByGuestAmts属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BaseByGuestAmtsType }
     *     
     */
    public BaseByGuestAmtsType getBaseByGuestAmts() {
        return baseByGuestAmts;
    }

    /**
     * 设置baseByGuestAmts属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BaseByGuestAmtsType }
     *     
     */
    public void setBaseByGuestAmts(BaseByGuestAmtsType value) {
        this.baseByGuestAmts = value;
    }

    /**
     * 获取fees属性的值。
     * 
     * @return
     *     possible object is
     *     {@link FeesType }
     *     
     */
    public FeesType getFees() {
        return fees;
    }

    /**
     * 设置fees属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link FeesType }
     *     
     */
    public void setFees(FeesType value) {
        this.fees = value;
    }

    /**
     * 获取guaranteePolicies属性的值。
     * 
     * @return
     *     possible object is
     *     {@link GuaranteePoliciesType }
     *     
     */
    public GuaranteePoliciesType getGuaranteePolicies() {
        return guaranteePolicies;
    }

    /**
     * 设置guaranteePolicies属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link GuaranteePoliciesType }
     *     
     */
    public void setGuaranteePolicies(GuaranteePoliciesType value) {
        this.guaranteePolicies = value;
    }

    /**
     * 获取cancelPolicies属性的值。
     * 
     * @return
     *     possible object is
     *     {@link CancelPoliciesType }
     *     
     */
    public CancelPoliciesType getCancelPolicies() {
        return cancelPolicies;
    }

    /**
     * 设置cancelPolicies属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link CancelPoliciesType }
     *     
     */
    public void setCancelPolicies(CancelPoliciesType value) {
        this.cancelPolicies = value;
    }

    /**
     * 获取mealsIncluded属性的值。
     * 
     * @return
     *     possible object is
     *     {@link MealsIncludedType }
     *     
     */
    public MealsIncludedType getMealsIncluded() {
        return mealsIncluded;
    }

    /**
     * 设置mealsIncluded属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link MealsIncludedType }
     *     
     */
    public void setMealsIncluded(MealsIncludedType value) {
        this.mealsIncluded = value;
    }

    /**
     * 获取tpaExtensions属性的值。
     * 
     * @return
     *     possible object is
     *     {@link TPAExtensionsType }
     *     
     */
    public TPAExtensionsType getTPAExtensions() {
        return tpaExtensions;
    }

    /**
     * 设置tpaExtensions属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link TPAExtensionsType }
     *     
     */
    public void setTPAExtensions(TPAExtensionsType value) {
        this.tpaExtensions = value;
    }

    /**
     * 获取bookingRules属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BookingRulesType }
     *     
     */
    public BookingRulesType getBookingRules() {
        return bookingRules;
    }

    /**
     * 设置bookingRules属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BookingRulesType }
     *     
     */
    public void setBookingRules(BookingRulesType value) {
        this.bookingRules = value;
    }

    /**
     * 获取numberOfUnits属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumberOfUnits() {
        return numberOfUnits;
    }

    /**
     * 设置numberOfUnits属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumberOfUnits(String value) {
        this.numberOfUnits = value;
    }

    /**
     * 获取start属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStart() {
        return start;
    }

    /**
     * 设置start属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStart(String value) {
        this.start = value;
    }

    /**
     * 获取end属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEnd() {
        return end;
    }

    /**
     * 设置end属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEnd(String value) {
        this.end = value;
    }

    /**
     * 获取isInstantConfirm属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsInstantConfirm() {
        return isInstantConfirm;
    }

    /**
     * 设置isInstantConfirm属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsInstantConfirm(String value) {
        this.isInstantConfirm = value;
    }

    /**
     * 获取status属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置status属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStatus(String value) {
        this.status = value;
    }

}
