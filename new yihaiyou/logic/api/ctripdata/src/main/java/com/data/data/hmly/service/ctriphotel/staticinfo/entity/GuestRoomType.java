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
 * <p>GuestRoomType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="GuestRoomType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="TypeRoom" type="{}TypeRoomType"/>
 *         &lt;element name="Amenities" type="{}AmenitiesType"/>
 *         &lt;element name="TPA_Extensions" type="{}TPA_ExtensionsType" minOccurs="0"/>
 *         &lt;element name="Features" type="{}FeaturesType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="RoomTypeName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "GuestRoomType", propOrder = {
    "typeRoom",
    "amenities",
    "tpaExtensions",
    "features"
})
public class GuestRoomType {

    @XmlElement(name = "TypeRoom", required = true)
    protected TypeRoomType typeRoom;
    @XmlElement(name = "Amenities", required = true)
    protected AmenitiesType amenities;
    @XmlElement(name = "TPA_Extensions")
    protected TPAExtensionsType tpaExtensions;
    @XmlElement(name = "Features", required = true)
    protected FeaturesType features;
    @XmlAttribute(name = "RoomTypeName")
    protected String roomTypeName;

    /**
     * 获取typeRoom属性的值。
     * 
     * @return
     *     possible object is
     *     {@link TypeRoomType }
     *     
     */
    public TypeRoomType getTypeRoom() {
        return typeRoom;
    }

    /**
     * 设置typeRoom属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link TypeRoomType }
     *     
     */
    public void setTypeRoom(TypeRoomType value) {
        this.typeRoom = value;
    }

    /**
     * 获取amenities属性的值。
     * 
     * @return
     *     possible object is
     *     {@link AmenitiesType }
     *     
     */
    public AmenitiesType getAmenities() {
        return amenities;
    }

    /**
     * 设置amenities属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link AmenitiesType }
     *     
     */
    public void setAmenities(AmenitiesType value) {
        this.amenities = value;
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

    /**
     * 获取features属性的值。
     * 
     * @return
     *     possible object is
     *     {@link FeaturesType }
     *     
     */
    public FeaturesType getFeatures() {
        return features;
    }

    /**
     * 设置features属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link FeaturesType }
     *     
     */
    public void setFeatures(FeaturesType value) {
        this.features = value;
    }

    /**
     * 获取roomTypeName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomTypeName() {
        return roomTypeName;
    }

    /**
     * 设置roomTypeName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomTypeName(String value) {
        this.roomTypeName = value;
    }

}
