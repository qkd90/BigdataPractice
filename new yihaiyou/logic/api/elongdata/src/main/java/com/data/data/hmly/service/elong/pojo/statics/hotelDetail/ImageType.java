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
 * <p>ImageType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ImageType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IsRoomCoverImage" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Locations" type="{}LocationsType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Type" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="AuthorType" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="RoomId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="IsCoverImage" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "ImageType", propOrder = {
        "isRoomCoverImage",
        "locations"
})
public class ImageType {

    @XmlElement(name = "IsRoomCoverImage")
    protected String isRoomCoverImage;
    @XmlElement(name = "Locations", required = true)
    protected LocationsType locations;
    @XmlAttribute(name = "Type")
    protected String type;
    @XmlAttribute(name = "AuthorType")
    protected String authorType;
    @XmlAttribute(name = "RoomId")
    protected String roomId;
    @XmlAttribute(name = "IsCoverImage")
    protected String isCoverImage;

    /**
     * 获取isRoomCoverImage属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsRoomCoverImage() {
        return isRoomCoverImage;
    }

    /**
     * 设置isRoomCoverImage属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsRoomCoverImage(String value) {
        this.isRoomCoverImage = value;
    }

    /**
     * 获取locations属性的值。
     *
     * @return
     *     possible object is
     *     {@link LocationsType }
     *     
     */
    public LocationsType getLocations() {
        return locations;
    }

    /**
     * 设置locations属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link LocationsType }
     *     
     */
    public void setLocations(LocationsType value) {
        this.locations = value;
    }

    /**
     * 获取type属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getType() {
        return type;
    }

    /**
     * 设置type属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setType(String value) {
        this.type = value;
    }

    /**
     * 获取authorType属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAuthorType() {
        return authorType;
    }

    /**
     * 设置authorType属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAuthorType(String value) {
        this.authorType = value;
    }

    /**
     * 获取roomId属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomId() {
        return roomId;
    }

    /**
     * 设置roomId属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomId(String value) {
        this.roomId = value;
    }

    /**
     * 获取isCoverImage属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsCoverImage() {
        return isCoverImage;
    }

    /**
     * 设置isCoverImage属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsCoverImage(String value) {
        this.isCoverImage = value;
    }

}
