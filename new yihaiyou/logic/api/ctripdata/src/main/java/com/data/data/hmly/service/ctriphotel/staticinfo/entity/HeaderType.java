//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.12.02 时间 03:32:04 PM CST 
//


package com.data.data.hmly.service.ctriphotel.staticinfo.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>HeaderType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="HeaderType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="ShouldRecordPerformanceTime" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Timestamp" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ReferenceID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="RecentlyTime" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="AccessCount" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="CurrentCount" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ResetTime" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ResultCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HeaderType", propOrder = {
    "value"
})
public class HeaderType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "ShouldRecordPerformanceTime")
    protected String shouldRecordPerformanceTime;
    @XmlAttribute(name = "Timestamp")
    protected String timestamp;
    @XmlAttribute(name = "ReferenceID")
    protected String referenceID;
    @XmlAttribute(name = "RecentlyTime")
    protected String recentlyTime;
    @XmlAttribute(name = "AccessCount")
    protected String accessCount;
    @XmlAttribute(name = "CurrentCount")
    protected String currentCount;
    @XmlAttribute(name = "ResetTime")
    protected String resetTime;
    @XmlAttribute(name = "ResultCode")
    protected String resultCode;

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
     * 获取shouldRecordPerformanceTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getShouldRecordPerformanceTime() {
        return shouldRecordPerformanceTime;
    }

    /**
     * 设置shouldRecordPerformanceTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setShouldRecordPerformanceTime(String value) {
        this.shouldRecordPerformanceTime = value;
    }

    /**
     * 获取timestamp属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTimestamp() {
        return timestamp;
    }

    /**
     * 设置timestamp属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTimestamp(String value) {
        this.timestamp = value;
    }

    /**
     * 获取referenceID属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getReferenceID() {
        return referenceID;
    }

    /**
     * 设置referenceID属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setReferenceID(String value) {
        this.referenceID = value;
    }

    /**
     * 获取recentlyTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecentlyTime() {
        return recentlyTime;
    }

    /**
     * 设置recentlyTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecentlyTime(String value) {
        this.recentlyTime = value;
    }

    /**
     * 获取accessCount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAccessCount() {
        return accessCount;
    }

    /**
     * 设置accessCount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAccessCount(String value) {
        this.accessCount = value;
    }

    /**
     * 获取currentCount属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCurrentCount() {
        return currentCount;
    }

    /**
     * 设置currentCount属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCurrentCount(String value) {
        this.currentCount = value;
    }

    /**
     * 获取resetTime属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResetTime() {
        return resetTime;
    }

    /**
     * 设置resetTime属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResetTime(String value) {
        this.resetTime = value;
    }

    /**
     * 获取resultCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getResultCode() {
        return resultCode;
    }

    /**
     * 设置resultCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setResultCode(String value) {
        this.resultCode = value;
    }

}
