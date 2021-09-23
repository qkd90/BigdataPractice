//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.12.02 时间 03:17:21 PM CST 
//


package com.data.data.hmly.service.ctriphotel.info.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>PropertyType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="PropertyType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="VendorMessages" type="{}VendorMessagesType"/>
 *         &lt;element name="Position" type="{}PositionType"/>
 *         &lt;element name="Address" type="{}AddressType"/>
 *         &lt;element name="Award" type="{}AwardType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="RateRange" type="{}RateRangeType" minOccurs="0"/>
 *         &lt;element name="RelativePosition" type="{}RelativePositionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="TPA_Extensions" type="{}TPA_ExtensionsType" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="HotelCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="HotelCityCode" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="HotelName" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="AreaID" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="HotelId" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "PropertyType", propOrder = {
//    "vendorMessages",
//    "position",
//    "address",
//    "award",
//    "rateRange",
////    "relativePosition",
//    "tpaExtensions"
})
public class PropertyType {

//    @XmlElement(name = "VendorMessages", required = true)
//    protected VendorMessagesType vendorMessages;
//    @XmlElement(name = "Position", required = true)
//    protected PositionType position;
//    @XmlElement(name = "Address", required = true)
//    protected AddressType address;
//    @XmlElement(name = "Award")
//    protected List<AwardType> award;
//    @XmlElement(name = "RateRange")
//    protected RateRangeType rateRange;
////    @XmlElement(name = "RelativePosition")
////    protected List<RelativePositionType> relativePosition;
//    @XmlElement(name = "TPA_Extensions")
//    protected TPAExtensionsType tpaExtensions;
//    @XmlAttribute(name = "HotelCode")
//    protected String hotelCode;
//    @XmlAttribute(name = "HotelCityCode")
//    protected String hotelCityCode;
//    @XmlAttribute(name = "HotelName")
//    protected String hotelName;
//    @XmlAttribute(name = "AreaID")
//    protected String areaID;
    @XmlAttribute(name = "HotelId")
    protected String hotelId;

//    /**
//     * 获取vendorMessages属性的值。
//     *
//     * @return
//     *     possible object is
//     *     {@link VendorMessagesType }
//     *
//     */
//    public VendorMessagesType getVendorMessages() {
//        return vendorMessages;
//    }
//
//    /**
//     * 设置vendorMessages属性的值。
//     *
//     * @param value
//     *     allowed object is
//     *     {@link VendorMessagesType }
//     *
//     */
//    public void setVendorMessages(VendorMessagesType value) {
//        this.vendorMessages = value;
//    }
//
//    /**
//     * 获取position属性的值。
//     *
//     * @return
//     *     possible object is
//     *     {@link PositionType }
//     *
//     */
//    public PositionType getPosition() {
//        return position;
//    }
//
//    /**
//     * 设置position属性的值。
//     *
//     * @param value
//     *     allowed object is
//     *     {@link PositionType }
//     *
//     */
//    public void setPosition(PositionType value) {
//        this.position = value;
//    }
//
//    /**
//     * 获取address属性的值。
//     *
//     * @return
//     *     possible object is
//     *     {@link AddressType }
//     *
//     */
//    public AddressType getAddress() {
//        return address;
//    }
//
//    /**
//     * 设置address属性的值。
//     *
//     * @param value
//     *     allowed object is
//     *     {@link AddressType }
//     *
//     */
//    public void setAddress(AddressType value) {
//        this.address = value;
//    }
//
//    /**
//     * Gets the value of the award property.
//     *
//     * <p>
//     * This accessor method returns a reference to the live list,
//     * not a snapshot. Therefore any modification you make to the
//     * returned list will be present inside the JAXB object.
//     * This is why there is not a <CODE>set</CODE> method for the award property.
//     *
//     * <p>
//     * For example, to add a new item, do as follows:
//     * <pre>
//     *    getAward().add(newItem);
//     * </pre>
//     *
//     *
//     * <p>
//     * Objects of the following type(s) are allowed in the list
//     * {@link AwardType }
//     *
//     *
//     */
//    public List<AwardType> getAward() {
//        if (award == null) {
//            award = new ArrayList<AwardType>();
//        }
//        return this.award;
//    }
//
//    /**
//     * 获取rateRange属性的值。
//     *
//     * @return
//     *     possible object is
//     *     {@link RateRangeType }
//     *
//     */
//    public RateRangeType getRateRange() {
//        return rateRange;
//    }
//
//    /**
//     * 设置rateRange属性的值。
//     *
//     * @param value
//     *     allowed object is
//     *     {@link RateRangeType }
//     *
//     */
//    public void setRateRange(RateRangeType value) {
//        this.rateRange = value;
//    }
//
////    /**
////     * Gets the value of the relativePosition property.
////     *
////     * <p>
////     * This accessor method returns a reference to the live list,
////     * not a snapshot. Therefore any modification you make to the
////     * returned list will be present inside the JAXB object.
////     * This is why there is not a <CODE>set</CODE> method for the relativePosition property.
////     *
////     * <p>
////     * For example, to add a new item, do as follows:
////     * <pre>
////     *    getRelativePosition().add(newItem);
////     * </pre>
////     *
////     *
////     * <p>
////     * Objects of the following type(s) are allowed in the list
////     * {@link RelativePositionType }
////     *
////     *
////     */
////    public List<RelativePositionType> getRelativePosition() {
////        if (relativePosition == null) {
////            relativePosition = new ArrayList<RelativePositionType>();
////        }
////        return this.relativePosition;
////    }
//
//    /**
//     * 获取tpaExtensions属性的值。
//     *
//     * @return
//     *     possible object is
//     *     {@link TPAExtensionsType }
//     *
//     */
//    public TPAExtensionsType getTPAExtensions() {
//        return tpaExtensions;
//    }
//
//    /**
//     * 设置tpaExtensions属性的值。
//     *
//     * @param value
//     *     allowed object is
//     *     {@link TPAExtensionsType }
//     *
//     */
//    public void setTPAExtensions(TPAExtensionsType value) {
//        this.tpaExtensions = value;
//    }
//
//    /**
//     * 获取hotelCode属性的值。
//     *
//     * @return
//     *     possible object is
//     *     {@link String }
//     *
//     */
//    public String getHotelCode() {
//        return hotelCode;
//    }
//
//    /**
//     * 设置hotelCode属性的值。
//     *
//     * @param value
//     *     allowed object is
//     *     {@link String }
//     *
//     */
//    public void setHotelCode(String value) {
//        this.hotelCode = value;
//    }
//
//    /**
//     * 获取hotelCityCode属性的值。
//     *
//     * @return
//     *     possible object is
//     *     {@link String }
//     *
//     */
//    public String getHotelCityCode() {
//        return hotelCityCode;
//    }
//
//    /**
//     * 设置hotelCityCode属性的值。
//     *
//     * @param value
//     *     allowed object is
//     *     {@link String }
//     *
//     */
//    public void setHotelCityCode(String value) {
//        this.hotelCityCode = value;
//    }
//
//    /**
//     * 获取hotelName属性的值。
//     *
//     * @return
//     *     possible object is
//     *     {@link String }
//     *
//     */
//    public String getHotelName() {
//        return hotelName;
//    }
//
//    /**
//     * 设置hotelName属性的值。
//     *
//     * @param value
//     *     allowed object is
//     *     {@link String }
//     *
//     */
//    public void setHotelName(String value) {
//        this.hotelName = value;
//    }
//
//    /**
//     * 获取areaID属性的值。
//     *
//     * @return
//     *     possible object is
//     *     {@link String }
//     *
//     */
//    public String getAreaID() {
//        return areaID;
//    }
//
//    /**
//     * 设置areaID属性的值。
//     *
//     * @param value
//     *     allowed object is
//     *     {@link String }
//     *
//     */
//    public void setAreaID(String value) {
//        this.areaID = value;
//    }

    /**
     * 获取hotelId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHotelId() {
        return hotelId;
    }

    /**
     * 设置hotelId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHotelId(String value) {
        this.hotelId = value;
    }

}
