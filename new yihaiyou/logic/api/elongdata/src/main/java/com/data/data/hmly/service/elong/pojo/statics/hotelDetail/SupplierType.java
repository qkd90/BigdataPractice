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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>SupplierType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="SupplierType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="AvailPolicy" type="{}AvailPolicyType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="ID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Status" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="HotelCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="WeekendStart" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="WeekendEnd" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="InvokeType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SupplierType", propOrder = {
        "availPolicy"
})
public class SupplierType {

    @XmlElement(name = "AvailPolicy")
    protected AvailPolicyType availPolicy;
    @XmlAttribute(name = "ID")
    protected String id;
    @XmlAttribute(name = "Status")
    protected String status;
    @XmlAttribute(name = "HotelCode")
    protected String hotelCode;
    @XmlAttribute(name = "WeekendStart")
    protected String weekendStart;
    @XmlAttribute(name = "WeekendEnd")
    protected String weekendEnd;
    @XmlAttribute(name = "InvokeType")
    protected String invokeType;

    /**
     * 获取availPolicy属性的值。
     *
     * @return
     *     possible object is
     *     {@link AvailPolicyType }
     *     
     */
    public AvailPolicyType getAvailPolicy() {
        return availPolicy;
    }

    /**
     * 设置availPolicy属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link AvailPolicyType }
     *
     */
    public void setAvailPolicy(AvailPolicyType value) {
        this.availPolicy = value;
    }

    /**
     * 获取id属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getID() {
        return id;
    }

    /**
     * 设置id属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setID(String value) {
        this.id = value;
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

    /**
     * 获取hotelCode属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHotelCode() {
        return hotelCode;
    }

    /**
     * 设置hotelCode属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHotelCode(String value) {
        this.hotelCode = value;
    }

    /**
     * 获取weekendStart属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeekendStart() {
        return weekendStart;
    }

    /**
     * 设置weekendStart属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeekendStart(String value) {
        this.weekendStart = value;
    }

    /**
     * 获取weekendEnd属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWeekendEnd() {
        return weekendEnd;
    }

    /**
     * 设置weekendEnd属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWeekendEnd(String value) {
        this.weekendEnd = value;
    }

    /**
     * 获取invokeType属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvokeType() {
        return invokeType;
    }

    /**
     * 设置invokeType属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvokeType(String value) {
        this.invokeType = value;
    }

}
