//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2016.01.28 时间 04:38:59 PM CST 
//


package com.data.data.hmly.service.elong.pojo.statics.hotelDetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;
import javax.xml.bind.annotation.XmlValue;


/**
 * <p>ServiceRankType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ServiceRankType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="SummaryScore" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="InstantConfirmScore" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="BookingSuccessScore" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ComplaintScore" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="SummaryRate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="InstantConfirmRate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="BookingSuccessRate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="ComplaintRate" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 *
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ServiceRankType", propOrder = {
        "value"
})
public class ServiceRankType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "SummaryScore")
    protected String summaryScore;
    @XmlAttribute(name = "InstantConfirmScore")
    protected String instantConfirmScore;
    @XmlAttribute(name = "BookingSuccessScore")
    protected String bookingSuccessScore;
    @XmlAttribute(name = "ComplaintScore")
    protected String complaintScore;
    @XmlAttribute(name = "SummaryRate")
    protected String summaryRate;
    @XmlAttribute(name = "InstantConfirmRate")
    protected String instantConfirmRate;
    @XmlAttribute(name = "BookingSuccessRate")
    protected String bookingSuccessRate;
    @XmlAttribute(name = "ComplaintRate")
    protected String complaintRate;

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
     * 获取summaryScore属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummaryScore() {
        return summaryScore;
    }

    /**
     * 设置summaryScore属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummaryScore(String value) {
        this.summaryScore = value;
    }

    /**
     * 获取instantConfirmScore属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstantConfirmScore() {
        return instantConfirmScore;
    }

    /**
     * 设置instantConfirmScore属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstantConfirmScore(String value) {
        this.instantConfirmScore = value;
    }

    /**
     * 获取bookingSuccessScore属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingSuccessScore() {
        return bookingSuccessScore;
    }

    /**
     * 设置bookingSuccessScore属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingSuccessScore(String value) {
        this.bookingSuccessScore = value;
    }

    /**
     * 获取complaintScore属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComplaintScore() {
        return complaintScore;
    }

    /**
     * 设置complaintScore属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComplaintScore(String value) {
        this.complaintScore = value;
    }

    /**
     * 获取summaryRate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSummaryRate() {
        return summaryRate;
    }

    /**
     * 设置summaryRate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSummaryRate(String value) {
        this.summaryRate = value;
    }

    /**
     * 获取instantConfirmRate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInstantConfirmRate() {
        return instantConfirmRate;
    }

    /**
     * 设置instantConfirmRate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInstantConfirmRate(String value) {
        this.instantConfirmRate = value;
    }

    /**
     * 获取bookingSuccessRate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBookingSuccessRate() {
        return bookingSuccessRate;
    }

    /**
     * 设置bookingSuccessRate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBookingSuccessRate(String value) {
        this.bookingSuccessRate = value;
    }

    /**
     * 获取complaintRate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComplaintRate() {
        return complaintRate;
    }

    /**
     * 设置complaintRate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComplaintRate(String value) {
        this.complaintRate = value;
    }

}
