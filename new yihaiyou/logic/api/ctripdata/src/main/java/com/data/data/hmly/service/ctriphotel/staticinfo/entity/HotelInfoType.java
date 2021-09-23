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
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>HotelInfoType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="HotelInfoType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CategoryCodes" type="{}CategoryCodesType"/>
 *         &lt;element name="Address" type="{}AddressType"/>
 *         &lt;element name="Position" type="{}PositionType"/>
 *         &lt;element name="Services" type="{}ServicesType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="WhenBuilt" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="LastUpdated" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HotelInfoType", propOrder = {
    "categoryCodes",
    "address",
    "position",
    "services"
})
public class HotelInfoType {

    @XmlElement(name = "CategoryCodes", required = true)
    protected CategoryCodesType categoryCodes;
    @XmlElement(name = "Address", required = true)
    protected AddressType address;
    @XmlElement(name = "Position", required = true)
    protected PositionType position;
    @XmlElement(name = "Services", required = true)
    protected ServicesType services;
    @XmlAttribute(name = "WhenBuilt")
    protected String whenBuilt;
    @XmlAttribute(name = "LastUpdated")
    protected String lastUpdated;

    /**
     * 获取categoryCodes属性的值。
     * 
     * @return
     *     possible object is
     *     {@link CategoryCodesType }
     *     
     */
    public CategoryCodesType getCategoryCodes() {
        return categoryCodes;
    }

    /**
     * 设置categoryCodes属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link CategoryCodesType }
     *     
     */
    public void setCategoryCodes(CategoryCodesType value) {
        this.categoryCodes = value;
    }

    /**
     * 获取address属性的值。
     * 
     * @return
     *     possible object is
     *     {@link AddressType }
     *     
     */
    public AddressType getAddress() {
        return address;
    }

    /**
     * 设置address属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link AddressType }
     *     
     */
    public void setAddress(AddressType value) {
        this.address = value;
    }

    /**
     * 获取position属性的值。
     * 
     * @return
     *     possible object is
     *     {@link PositionType }
     *     
     */
    public PositionType getPosition() {
        return position;
    }

    /**
     * 设置position属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link PositionType }
     *     
     */
    public void setPosition(PositionType value) {
        this.position = value;
    }

    /**
     * 获取services属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ServicesType }
     *     
     */
    public ServicesType getServices() {
        return services;
    }

    /**
     * 设置services属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ServicesType }
     *     
     */
    public void setServices(ServicesType value) {
        this.services = value;
    }

    /**
     * 获取whenBuilt属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getWhenBuilt() {
        return whenBuilt;
    }

    /**
     * 设置whenBuilt属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setWhenBuilt(String value) {
        this.whenBuilt = value;
    }

    /**
     * 获取lastUpdated属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLastUpdated() {
        return lastUpdated;
    }

    /**
     * 设置lastUpdated属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLastUpdated(String value) {
        this.lastUpdated = value;
    }

}
