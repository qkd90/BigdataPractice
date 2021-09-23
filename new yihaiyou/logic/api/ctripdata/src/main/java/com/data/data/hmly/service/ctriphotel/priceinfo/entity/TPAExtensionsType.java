//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.12.02 时间 07:09:07 PM CST 
//


package com.data.data.hmly.service.ctriphotel.priceinfo.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>TPA_ExtensionsType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="TPA_ExtensionsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OtherCurrency" type="{}OtherCurrencyType" minOccurs="0"/>
 *         &lt;element name="RebatePromotion" type="{}RebatePromotionType" minOccurs="0"/>
 *         &lt;element name="PayChange" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPA_ExtensionsType", propOrder = {
    "otherCurrency",
    "rebatePromotion",
    "payChange"
})
public class TPAExtensionsType {

    @XmlElement(name = "OtherCurrency")
    protected OtherCurrencyType otherCurrency;
    @XmlElement(name = "RebatePromotion")
    protected RebatePromotionType rebatePromotion;
    @XmlElement(name = "PayChange")
    protected String payChange;

    /**
     * 获取otherCurrency属性的值。
     * 
     * @return
     *     possible object is
     *     {@link OtherCurrencyType }
     *     
     */
    public OtherCurrencyType getOtherCurrency() {
        return otherCurrency;
    }

    /**
     * 设置otherCurrency属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link OtherCurrencyType }
     *     
     */
    public void setOtherCurrency(OtherCurrencyType value) {
        this.otherCurrency = value;
    }

    /**
     * 获取rebatePromotion属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RebatePromotionType }
     *     
     */
    public RebatePromotionType getRebatePromotion() {
        return rebatePromotion;
    }

    /**
     * 设置rebatePromotion属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RebatePromotionType }
     *     
     */
    public void setRebatePromotion(RebatePromotionType value) {
        this.rebatePromotion = value;
    }

    /**
     * 获取payChange属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPayChange() {
        return payChange;
    }

    /**
     * 设置payChange属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPayChange(String value) {
        this.payChange = value;
    }

}
