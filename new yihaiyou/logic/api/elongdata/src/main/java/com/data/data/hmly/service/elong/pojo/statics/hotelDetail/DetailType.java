//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2016.01.28 时间 04:38:59 PM CST 
//


package com.data.data.hmly.service.elong.pojo.statics.hotelDetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>DetailType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="DetailType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Name" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Address" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="StarRate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Category" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Phone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Fax" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EstablishmentDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RenovationDate" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="GroupId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BrandId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IsEconomic" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IsApartment" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="GoogleLat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="GoogleLon" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BaiduLat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BaiduLon" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CityId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BusinessZone" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BusinessZone2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="District" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CreditCards" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IntroEditor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Description" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="GeneralAmenities" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RoomAmenities" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RecreationAmenities" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ConferenceAmenities" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="DiningAmenities" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Traffic" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Features" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Facilities" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Suppliers" type="{}SuppliersType"/>
 *         &lt;element name="ServiceRank" type="{}ServiceRankType"/>
 *         &lt;element name="HasCoupon" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FacilitiesV2" type="{}FacilitiesV2Type"/>
 *         &lt;element name="RoomTotalAmount" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DetailType", propOrder = {
        "name",
        "address",
        "starRate",
        "category",
        "phone",
        "fax",
        "establishmentDate",
        "renovationDate",
        "groupId",
        "brandId",
        "isEconomic",
        "isApartment",
        "googleLat",
        "googleLon",
        "baiduLat",
        "baiduLon",
        "cityId",
        "businessZone",
        "businessZone2",
        "district",
        "creditCards",
        "introEditor",
        "description",
        "generalAmenities",
        "roomAmenities",
        "recreationAmenities",
        "conferenceAmenities",
        "diningAmenities",
        "traffic",
        "features",
        "facilities",
        "suppliers",
        "serviceRank",
        "hasCoupon",
        "facilitiesV2",
        "themes",
        "roomTotalAmount"
})
public class DetailType {

    @XmlElement(name = "Name", required = true)
    protected String name;
    @XmlElement(name = "Address", required = true)
    protected String address;
    @XmlElement(name = "StarRate", required = true)
    protected String starRate;
    @XmlElement(name = "Category", required = true)
    protected String category;
    @XmlElement(name = "Phone", required = true)
    protected String phone;
    @XmlElement(name = "Fax", required = true)
    protected String fax;
    @XmlElement(name = "EstablishmentDate", required = true)
    protected String establishmentDate;
    @XmlElement(name = "RenovationDate", required = true)
    protected String renovationDate;
    @XmlElement(name = "GroupId", required = true)
    protected String groupId;
    @XmlElement(name = "BrandId", required = true)
    protected String brandId;
    @XmlElement(name = "IsEconomic", required = true)
    protected String isEconomic;
    @XmlElement(name = "IsApartment", required = true)
    protected String isApartment;
    @XmlElement(name = "GoogleLat", required = true)
    protected String googleLat;
    @XmlElement(name = "GoogleLon", required = true)
    protected String googleLon;
    @XmlElement(name = "BaiduLat", required = true)
    protected String baiduLat;
    @XmlElement(name = "BaiduLon", required = true)
    protected String baiduLon;
    @XmlElement(name = "CityId", required = true)
    protected String cityId;
    @XmlElement(name = "BusinessZone", required = true)
    protected String businessZone;
    @XmlElement(name = "BusinessZone2", required = true)
    protected String businessZone2;
    @XmlElement(name = "District", required = true)
    protected String district;
    @XmlElement(name = "CreditCards", required = true)
    protected String creditCards;
    @XmlElement(name = "IntroEditor", required = true)
    protected String introEditor;
    @XmlElement(name = "Description", required = true)
    protected String description;
    @XmlElement(name = "GeneralAmenities", required = true)
    protected String generalAmenities;
    @XmlElement(name = "RoomAmenities", required = true)
    protected String roomAmenities;
    @XmlElement(name = "RecreationAmenities", required = true)
    protected String recreationAmenities;
    @XmlElement(name = "ConferenceAmenities", required = true)
    protected String conferenceAmenities;
    @XmlElement(name = "DiningAmenities", required = true)
    protected String diningAmenities;
    @XmlElement(name = "Traffic", required = true)
    protected String traffic;
    @XmlElement(name = "Features", required = true)
    protected String features;
    @XmlElement(name = "Facilities", required = true)
    protected String facilities;
    @XmlElement(name = "Suppliers", required = true)
    protected SuppliersType suppliers;
    @XmlElement(name = "ServiceRank", required = true)
    protected ServiceRankType serviceRank;
    @XmlElement(name = "HasCoupon", required = true)
    protected String hasCoupon;
    @XmlElement(name = "FacilitiesV2", required = true)
    protected FacilitiesV2Type facilitiesV2;
    @XmlElement(name = "Themes")
    protected String themes;
    @XmlElement(name = "RoomTotalAmount", required = true)
    protected String roomTotalAmount;

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
     * 获取address属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置address属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAddress(String value) {
        this.address = value;
    }

    /**
     * 获取starRate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getStarRate() {
        return starRate;
    }

    /**
     * 设置starRate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setStarRate(String value) {
        this.starRate = value;
    }

    /**
     * 获取category属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCategory() {
        return category;
    }

    /**
     * 设置category属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCategory(String value) {
        this.category = value;
    }

    /**
     * 获取phone属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPhone() {
        return phone;
    }

    /**
     * 设置phone属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPhone(String value) {
        this.phone = value;
    }

    /**
     * 获取fax属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFax() {
        return fax;
    }

    /**
     * 设置fax属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFax(String value) {
        this.fax = value;
    }

    /**
     * 获取establishmentDate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEstablishmentDate() {
        return establishmentDate;
    }

    /**
     * 设置establishmentDate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEstablishmentDate(String value) {
        this.establishmentDate = value;
    }

    /**
     * 获取renovationDate属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRenovationDate() {
        return renovationDate;
    }

    /**
     * 设置renovationDate属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRenovationDate(String value) {
        this.renovationDate = value;
    }

    /**
     * 获取groupId属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGroupId() {
        return groupId;
    }

    /**
     * 设置groupId属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGroupId(String value) {
        this.groupId = value;
    }

    /**
     * 获取brandId属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBrandId() {
        return brandId;
    }

    /**
     * 设置brandId属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBrandId(String value) {
        this.brandId = value;
    }

    /**
     * 获取isEconomic属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsEconomic() {
        return isEconomic;
    }

    /**
     * 设置isEconomic属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsEconomic(String value) {
        this.isEconomic = value;
    }

    /**
     * 获取isApartment属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsApartment() {
        return isApartment;
    }

    /**
     * 设置isApartment属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsApartment(String value) {
        this.isApartment = value;
    }

    /**
     * 获取googleLat属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoogleLat() {
        return googleLat;
    }

    /**
     * 设置googleLat属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoogleLat(String value) {
        this.googleLat = value;
    }

    /**
     * 获取googleLon属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGoogleLon() {
        return googleLon;
    }

    /**
     * 设置googleLon属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGoogleLon(String value) {
        this.googleLon = value;
    }

    /**
     * 获取baiduLat属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaiduLat() {
        return baiduLat;
    }

    /**
     * 设置baiduLat属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaiduLat(String value) {
        this.baiduLat = value;
    }

    /**
     * 获取baiduLon属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBaiduLon() {
        return baiduLon;
    }

    /**
     * 设置baiduLon属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBaiduLon(String value) {
        this.baiduLon = value;
    }

    /**
     * 获取cityId属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityId() {
        return cityId;
    }

    /**
     * 设置cityId属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityId(String value) {
        this.cityId = value;
    }

    /**
     * 获取businessZone属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessZone() {
        return businessZone;
    }

    /**
     * 设置businessZone属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessZone(String value) {
        this.businessZone = value;
    }

    /**
     * 获取businessZone2属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBusinessZone2() {
        return businessZone2;
    }

    /**
     * 设置businessZone2属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBusinessZone2(String value) {
        this.businessZone2 = value;
    }

    /**
     * 获取district属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 设置district属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDistrict(String value) {
        this.district = value;
    }

    /**
     * 获取creditCards属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCreditCards() {
        return creditCards;
    }

    /**
     * 设置creditCards属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCreditCards(String value) {
        this.creditCards = value;
    }

    /**
     * 获取introEditor属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIntroEditor() {
        return introEditor;
    }

    /**
     * 设置introEditor属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIntroEditor(String value) {
        this.introEditor = value;
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
     * 获取generalAmenities属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getGeneralAmenities() {
        return generalAmenities;
    }

    /**
     * 设置generalAmenities属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setGeneralAmenities(String value) {
        this.generalAmenities = value;
    }

    /**
     * 获取roomAmenities属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomAmenities() {
        return roomAmenities;
    }

    /**
     * 设置roomAmenities属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomAmenities(String value) {
        this.roomAmenities = value;
    }

    /**
     * 获取recreationAmenities属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRecreationAmenities() {
        return recreationAmenities;
    }

    /**
     * 设置recreationAmenities属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRecreationAmenities(String value) {
        this.recreationAmenities = value;
    }

    /**
     * 获取conferenceAmenities属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getConferenceAmenities() {
        return conferenceAmenities;
    }

    /**
     * 设置conferenceAmenities属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setConferenceAmenities(String value) {
        this.conferenceAmenities = value;
    }

    /**
     * 获取diningAmenities属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDiningAmenities() {
        return diningAmenities;
    }

    /**
     * 设置diningAmenities属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDiningAmenities(String value) {
        this.diningAmenities = value;
    }

    /**
     * 获取traffic属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTraffic() {
        return traffic;
    }

    /**
     * 设置traffic属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTraffic(String value) {
        this.traffic = value;
    }

    /**
     * 获取features属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getFeatures() {
        return features;
    }

    /**
     * 设置features属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setFeatures(String value) {
        this.features = value;
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

    /**
     * 获取suppliers属性的值。
     *
     * @return
     *     possible object is
     *     {@link SuppliersType }
     *     
     */
    public SuppliersType getSuppliers() {
        return suppliers;
    }

    /**
     * 设置suppliers属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link SuppliersType }
     *     
     */
    public void setSuppliers(SuppliersType value) {
        this.suppliers = value;
    }

    /**
     * 获取serviceRank属性的值。
     *
     * @return
     *     possible object is
     *     {@link ServiceRankType }
     *     
     */
    public ServiceRankType getServiceRank() {
        return serviceRank;
    }

    /**
     * 设置serviceRank属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link ServiceRankType }
     *     
     */
    public void setServiceRank(ServiceRankType value) {
        this.serviceRank = value;
    }

    /**
     * 获取hasCoupon属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getHasCoupon() {
        return hasCoupon;
    }

    /**
     * 设置hasCoupon属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setHasCoupon(String value) {
        this.hasCoupon = value;
    }

    /**
     * 获取facilitiesV2属性的值。
     *
     * @return
     *     possible object is
     *     {@link FacilitiesV2Type }
     *     
     */
    public FacilitiesV2Type getFacilitiesV2() {
        return facilitiesV2;
    }

    /**
     * 设置facilitiesV2属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link FacilitiesV2Type }
     *     
     */
    public void setFacilitiesV2(FacilitiesV2Type value) {
        this.facilitiesV2 = value;
    }

    public String getThemes() {
        return themes;
    }

    public void setThemes(String themes) {
        this.themes = themes;
    }

    /**
     * 获取roomTotalAmount属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomTotalAmount() {
        return roomTotalAmount;
    }

    /**
     * 设置roomTotalAmount属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomTotalAmount(String value) {
        this.roomTotalAmount = value;
    }

}
