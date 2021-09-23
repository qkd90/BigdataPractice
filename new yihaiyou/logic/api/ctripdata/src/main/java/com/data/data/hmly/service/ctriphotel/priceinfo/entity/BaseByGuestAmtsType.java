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
 * <p>BaseByGuestAmtsType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="BaseByGuestAmtsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="BaseByGuestAmt" type="{}BaseByGuestAmtType"/>
 *         &lt;element name="TPA_Extensions" type="{}TPA_ExtensionsType" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "BaseByGuestAmtsType", propOrder = {
    "baseByGuestAmt",
    "tpaExtensions"
})
public class BaseByGuestAmtsType {

    @XmlElement(name = "BaseByGuestAmt", required = true)
    protected BaseByGuestAmtType baseByGuestAmt;
    @XmlElement(name = "TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;

    /**
     * 获取baseByGuestAmt属性的值。
     * 
     * @return
     *     possible object is
     *     {@link BaseByGuestAmtType }
     *     
     */
    public BaseByGuestAmtType getBaseByGuestAmt() {
        return baseByGuestAmt;
    }

    /**
     * 设置baseByGuestAmt属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link BaseByGuestAmtType }
     *     
     */
    public void setBaseByGuestAmt(BaseByGuestAmtType value) {
        this.baseByGuestAmt = value;
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

}
