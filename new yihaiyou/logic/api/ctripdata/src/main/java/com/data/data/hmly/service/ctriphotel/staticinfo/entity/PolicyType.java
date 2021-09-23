//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.12.02 时间 03:32:04 PM CST 
//


package com.data.data.hmly.service.ctriphotel.staticinfo.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>PolicyType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="PolicyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PolicyInfoCodes" type="{}PolicyInfoCodesType"/>
 *         &lt;element name="PolicyInfo" type="{}PolicyInfoType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PolicyType", propOrder = {
    "policyInfoCodes",
    "policyInfo"
})
public class PolicyType {

    @XmlElement(name = "PolicyInfoCodes", required = true)
    protected PolicyInfoCodesType policyInfoCodes;
    @XmlElement(name = "PolicyInfo", required = true)
    protected PolicyInfoType policyInfo;

    /**
     * 获取policyInfoCodes属性的值。
     * 
     * @return
     *     possible object is
     *     {@link PolicyInfoCodesType }
     *     
     */
    public PolicyInfoCodesType getPolicyInfoCodes() {
        return policyInfoCodes;
    }

    /**
     * 设置policyInfoCodes属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link PolicyInfoCodesType }
     *
     */
    public void setPolicyInfoCodes(PolicyInfoCodesType value) {
        this.policyInfoCodes = value;
    }

    /**
     * 获取policyInfo属性的值。
     * 
     * @return
     *     possible object is
     *     {@link PolicyInfoType }
     *     
     */
    public PolicyInfoType getPolicyInfo() {
        return policyInfo;
    }

    /**
     * 设置policyInfo属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link PolicyInfoType }
     *     
     */
    public void setPolicyInfo(PolicyInfoType value) {
        this.policyInfo = value;
    }

}
