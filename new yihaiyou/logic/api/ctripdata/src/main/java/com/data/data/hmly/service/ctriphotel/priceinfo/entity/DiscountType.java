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
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>DiscountType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="DiscountType">
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
@XmlType(name = "DiscountType", propOrder = {
    "value"
})
public class DiscountType {

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
