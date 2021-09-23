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
import javax.xml.bind.annotation.XmlValue;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>OfferType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="OfferType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OfferRules" type="{}OfferRulesType"/>
 *         &lt;element name="OfferDescription" type="{}OfferDescriptionType"/>
 *         &lt;element name="Discount" maxOccurs="unbounded" minOccurs="0">
 *           &lt;complexType>
 *             &lt;simpleContent>
 *               &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *                 &lt;attribute name="NightsRequired" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="NightsDiscounted" type="{http://www.w3.org/2001/XMLSchema}string" />
 *                 &lt;attribute name="DiscountPattern" type="{http://www.w3.org/2001/XMLSchema}string" />
 *               &lt;/extension>
 *             &lt;/simpleContent>
 *           &lt;/complexType>
 *         &lt;/element>
 *       &lt;/sequence>
 *       &lt;attribute name="OfferCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OfferType", propOrder = {
    "offerRules",
    "offerDescription",
    "discount"
})
public class OfferType {

    @XmlElement(name = "OfferRules", required = true)
    protected OfferRulesType offerRules;
    @XmlElement(name = "OfferDescription", required = true)
    protected OfferDescriptionType offerDescription;
    @XmlElement(name = "Discount")
    protected List<OfferType.Discount> discount;
    @XmlAttribute(name = "OfferCode")
    protected String offerCode;

    /**
     * 获取offerRules属性的值。
     * 
     * @return
     *     possible object is
     *     {@link OfferRulesType }
     *     
     */
    public OfferRulesType getOfferRules() {
        return offerRules;
    }

    /**
     * 设置offerRules属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link OfferRulesType }
     *     
     */
    public void setOfferRules(OfferRulesType value) {
        this.offerRules = value;
    }

    /**
     * 获取offerDescription属性的值。
     * 
     * @return
     *     possible object is
     *     {@link OfferDescriptionType }
     *     
     */
    public OfferDescriptionType getOfferDescription() {
        return offerDescription;
    }

    /**
     * 设置offerDescription属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link OfferDescriptionType }
     *     
     */
    public void setOfferDescription(OfferDescriptionType value) {
        this.offerDescription = value;
    }

    /**
     * Gets the value of the discount property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the discount property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDiscount().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OfferType.Discount }
     * 
     * 
     */
    public List<OfferType.Discount> getDiscount() {
        if (discount == null) {
            discount = new ArrayList<OfferType.Discount>();
        }
        return this.discount;
    }

    /**
     * 获取offerCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOfferCode() {
        return offerCode;
    }

    /**
     * 设置offerCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOfferCode(String value) {
        this.offerCode = value;
    }


    /**
     * <p>anonymous complex type的 Java 类。
     * 
     * <p>以下模式片段指定包含在此类中的预期内容。
     * 
     * <pre>
     * &lt;complexType>
     *   &lt;simpleContent>
     *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
     *       &lt;attribute name="NightsRequired" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="NightsDiscounted" type="{http://www.w3.org/2001/XMLSchema}string" />
     *       &lt;attribute name="DiscountPattern" type="{http://www.w3.org/2001/XMLSchema}string" />
     *     &lt;/extension>
     *   &lt;/simpleContent>
     * &lt;/complexType>
     * </pre>
     * 
     * 
     */
    @XmlAccessorType(XmlAccessType.FIELD)
    @XmlType(name = "", propOrder = {
        "value"
    })
    public static class Discount {

        @XmlValue
        protected String value;
        @XmlAttribute(name = "NightsRequired")
        protected String nightsRequired;
        @XmlAttribute(name = "NightsDiscounted")
        protected String nightsDiscounted;
        @XmlAttribute(name = "DiscountPattern")
        protected String discountPattern;

        /**
         * 获取value属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getValue() {
            return value;
        }

        /**
         * 设置value属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setValue(String value) {
            this.value = value;
        }

        /**
         * 获取nightsRequired属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNightsRequired() {
            return nightsRequired;
        }

        /**
         * 设置nightsRequired属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNightsRequired(String value) {
            this.nightsRequired = value;
        }

        /**
         * 获取nightsDiscounted属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getNightsDiscounted() {
            return nightsDiscounted;
        }

        /**
         * 设置nightsDiscounted属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setNightsDiscounted(String value) {
            this.nightsDiscounted = value;
        }

        /**
         * 获取discountPattern属性的值。
         * 
         * @return
         *     possible object is
         *     {@link String }
         *     
         */
        public String getDiscountPattern() {
            return discountPattern;
        }

        /**
         * 设置discountPattern属性的值。
         * 
         * @param value
         *     allowed object is
         *     {@link String }
         *     
         */
        public void setDiscountPattern(String value) {
            this.discountPattern = value;
        }

    }

}
