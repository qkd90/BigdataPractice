//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.04 at 03:36:59 PM CST 
//


package com.data.data.hmly.service.ctripflight.response.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>Java class for HeaderType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
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
     * Gets the value of the value property.
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
     * Sets the value of the value property.
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
     * Gets the value of the shouldRecordPerformanceTime property.
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
     * Sets the value of the shouldRecordPerformanceTime property.
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
     * Gets the value of the timestamp property.
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
     * Sets the value of the timestamp property.
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
     * Gets the value of the referenceID property.
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
     * Sets the value of the referenceID property.
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
     * Gets the value of the recentlyTime property.
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
     * Sets the value of the recentlyTime property.
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
     * Gets the value of the accessCount property.
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
     * Sets the value of the accessCount property.
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
     * Gets the value of the currentCount property.
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
     * Sets the value of the currentCount property.
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
     * Gets the value of the resetTime property.
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
     * Sets the value of the resetTime property.
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
     * Gets the value of the resultCode property.
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
     * Sets the value of the resultCode property.
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