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
 * <p>RoomType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RoomType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Name" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Amount" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Area" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Floor" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="BroadnetAccess" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="BroadnetFee" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Comments" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Description" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="BedType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Capacity" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="Facilities" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 *
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoomType", propOrder = {
        "value"
})
public class RoomType {

    @XmlValue
    protected String value;
    @XmlAttribute(name = "Id")
    protected String id;
    @XmlAttribute(name = "Name")
    protected String name;
    @XmlAttribute(name = "Amount")
    protected String amount;
    @XmlAttribute(name = "Area")
    protected String area;
    @XmlAttribute(name = "Floor")
    protected String floor;
    @XmlAttribute(name = "BroadnetAccess")
    protected String broadnetAccess;
    @XmlAttribute(name = "BroadnetFee")
    protected String broadnetFee;
    @XmlAttribute(name = "Comments")
    protected String comments;
    @XmlAttribute(name = "Description")
    protected String description;
    @XmlAttribute(name = "BedType")
    protected String bedType;
    @XmlAttribute(name = "Capacity")
    protected String capacity;
    @XmlAttribute(name = "Facilities")
    protected String facilities;

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
     * 获取id属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
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
    public void setId(String value) {
        this.id = value;
    }

    /**
     * 获取name属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getName() {
        return name;
    }

    /**
     * 设置name属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setName(String value) {
        this.name = value;
    }

    /**
     * 获取amount属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAmount() {
        return amount;
    }

    /**
     * 设置amount属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAmount(String value) {
        this.amount = value;
    }

    /**
     * 获取area属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getArea() {
        return area;
    }

    /**
     * 设置area属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setArea(String value) {
        this.area = value;
    }

    /**
     * 获取floor属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFloor() {
        return floor;
    }

    /**
     * 设置floor属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFloor(String value) {
        this.floor = value;
    }

    /**
     * 获取broadnetAccess属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBroadnetAccess() {
        return broadnetAccess;
    }

    /**
     * 设置broadnetAccess属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBroadnetAccess(String value) {
        this.broadnetAccess = value;
    }

    /**
     * 获取broadnetFee属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBroadnetFee() {
        return broadnetFee;
    }

    /**
     * 设置broadnetFee属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBroadnetFee(String value) {
        this.broadnetFee = value;
    }

    /**
     * 获取comments属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getComments() {
        return comments;
    }

    /**
     * 设置comments属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setComments(String value) {
        this.comments = value;
    }

    /**
     * 获取description属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescription() {
        return description;
    }

    /**
     * 设置description属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescription(String value) {
        this.description = value;
    }

    /**
     * 获取bedType属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBedType() {
        return bedType;
    }

    /**
     * 设置bedType属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBedType(String value) {
        this.bedType = value;
    }

    /**
     * 获取capacity属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCapacity() {
        return capacity;
    }

    /**
     * 设置capacity属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCapacity(String value) {
        this.capacity = value;
    }

    /**
     * 获取facilities属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFacilities() {
        return facilities;
    }

    /**
     * 设置facilities属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFacilities(String value) {
        this.facilities = value;
    }

}
