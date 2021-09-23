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
 * <p>HotelDescriptiveContentType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="HotelDescriptiveContentType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="HotelInfo" type="{}HotelInfoType"/>
 *         &lt;element name="FacilityInfo" type="{}FacilityInfoType"/>
 *         &lt;element name="Policies" type="{}PoliciesType"/>
 *         &lt;element name="AreaInfo" type="{}AreaInfoType"/>
 *         &lt;element name="AffiliationInfo" type="{}AffiliationInfoType"/>
 *         &lt;element name="MultimediaDescriptions" type="{}MultimediaDescriptionsType"/>
 *         &lt;element name="TPA_Extensions" type="{}TPA_ExtensionsType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="BrandCode" type="{http://www.w3.org/2001/XMLSchema}string" />
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
@XmlType(name = "HotelDescriptiveContentType", propOrder = {
    "hotelInfo",
    "facilityInfo",
    "policies",
    "areaInfo",
    "affiliationInfo",
    "multimediaDescriptions",
    "tpaExtensions"
})
public class HotelDescriptiveContentType {

    @XmlElement(name = "HotelInfo", required = true)
    protected HotelInfoType hotelInfo;
    @XmlElement(name = "FacilityInfo", required = true)
    protected FacilityInfoType facilityInfo;
    @XmlElement(name = "Policies", required = true)
    protected PoliciesType policies;
    @XmlElement(name = "AreaInfo", required = true)
    protected AreaInfoType areaInfo;
    @XmlElement(name = "AffiliationInfo", required = true)
    protected AffiliationInfoType affiliationInfo;
    @XmlElement(name = "MultimediaDescriptions", required = true)
    protected MultimediaDescriptionsType multimediaDescriptions;
    @XmlElement(name = "TPA_Extensions", required = true)
    protected TPAExtensionsType tpaExtensions;
    @XmlAttribute(name = "BrandCode")
    protected String brandCode;
    @XmlAttribute(name = "HotelCode")
    protected String hotelCode;
    @XmlAttribute(name = "HotelCityCode")
    protected String hotelCityCode;
    @XmlAttribute(name = "HotelName")
    protected String hotelName;
    @XmlAttribute(name = "AreaID")
    protected String areaID;
    @XmlAttribute(name = "HotelId")
    protected String hotelId;

    /**
     * 获取hotelInfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link HotelInfoType }
     *
     */
    public HotelInfoType getHotelInfo() {
        return hotelInfo;
    }

    /**
     * 设置hotelInfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link HotelInfoType }
     *
     */
    public void setHotelInfo(HotelInfoType value) {
        this.hotelInfo = value;
    }

    /**
     * 获取facilityInfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link FacilityInfoType }
     *
     */
    public FacilityInfoType getFacilityInfo() {
        return facilityInfo;
    }

    /**
     * 设置facilityInfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link FacilityInfoType }
     *
     */
    public void setFacilityInfo(FacilityInfoType value) {
        this.facilityInfo = value;
    }

    /**
     * 获取policies属性的值。
     *
     * @return
     *     possible object is
     *     {@link PoliciesType }
     *
     */
    public PoliciesType getPolicies() {
        return policies;
    }

    /**
     * 设置policies属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link PoliciesType }
     *
     */
    public void setPolicies(PoliciesType value) {
        this.policies = value;
    }

    /**
     * 获取areaInfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link AreaInfoType }
     *
     */
    public AreaInfoType getAreaInfo() {
        return areaInfo;
    }

    /**
     * 设置areaInfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link AreaInfoType }
     *
     */
    public void setAreaInfo(AreaInfoType value) {
        this.areaInfo = value;
    }

    /**
     * 获取affiliationInfo属性的值。
     *
     * @return
     *     possible object is
     *     {@link AffiliationInfoType }
     *
     */
    public AffiliationInfoType getAffiliationInfo() {
        return affiliationInfo;
    }

    /**
     * 设置affiliationInfo属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link AffiliationInfoType }
     *
     */
    public void setAffiliationInfo(AffiliationInfoType value) {
        this.affiliationInfo = value;
    }

    /**
     * 获取multimediaDescriptions属性的值。
     *
     * @return
     *     possible object is
     *     {@link MultimediaDescriptionsType }
     *
     */
    public MultimediaDescriptionsType getMultimediaDescriptions() {
        return multimediaDescriptions;
    }

    /**
     * 设置multimediaDescriptions属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link MultimediaDescriptionsType }
     *
     */
    public void setMultimediaDescriptions(MultimediaDescriptionsType value) {
        this.multimediaDescriptions = value;
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
     * 获取brandCode属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getBrandCode() {
        return brandCode;
    }

    /**
     * 设置brandCode属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setBrandCode(String value) {
        this.brandCode = value;
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
     * 获取hotelCityCode属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getHotelCityCode() {
        return hotelCityCode;
    }

    /**
     * 设置hotelCityCode属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setHotelCityCode(String value) {
        this.hotelCityCode = value;
    }

    /**
     * 获取hotelName属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getHotelName() {
        return hotelName;
    }

    /**
     * 设置hotelName属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setHotelName(String value) {
        this.hotelName = value;
    }

    /**
     * 获取areaID属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getAreaID() {
        return areaID;
    }

    /**
     * 设置areaID属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setAreaID(String value) {
        this.areaID = value;
    }

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
