//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.12.02 时间 03:32:04 PM CST 
//


package com.data.data.hmly.service.ctriphotel.staticinfo.entity;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>TPA_ExtensionsType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="TPA_ExtensionsType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="RoadCross" type="{}RoadCrossType" minOccurs="0"/>
 *         &lt;element name="TPA_Extension" type="{}TPA_ExtensionType" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="InvBlockCode" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="ThemeCategory" type="{}ThemeCategoryType" minOccurs="0"/>
 *         &lt;element name="CityImportantMessage" type="{}CityImportantMessageType" minOccurs="0"/>
 *         &lt;element name="Roomquantity" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="MasterSubHotelIDs" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "TPA_ExtensionsType", propOrder = {
    "roadCross",
    "tpaExtension",
    "invBlockCode",
    "themeCategory",
    "cityImportantMessage",
    "roomquantity",
    "masterSubHotelIDs"
})
public class TPAExtensionsType {

    @XmlElement(name = "RoadCross")
    protected RoadCrossType roadCross;
    @XmlElement(name = "TPA_Extension")
    protected List<TPAExtensionType> tpaExtension;
    @XmlElement(name = "InvBlockCode")
    protected String invBlockCode;
    @XmlElement(name = "ThemeCategory")
    protected ThemeCategoryType themeCategory;
    @XmlElement(name = "CityImportantMessage")
    protected CityImportantMessageType cityImportantMessage;
    @XmlElement(name = "Roomquantity")
    protected String roomquantity;
    @XmlElement(name = "MasterSubHotelIDs")
    protected String masterSubHotelIDs;

    /**
     * 获取roadCross属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RoadCrossType }
     *     
     */
    public RoadCrossType getRoadCross() {
        return roadCross;
    }

    /**
     * 设置roadCross属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RoadCrossType }
     *     
     */
    public void setRoadCross(RoadCrossType value) {
        this.roadCross = value;
    }

    /**
     * Gets the value of the tpaExtension property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tpaExtension property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTPAExtension().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TPAExtensionType }
     * 
     * 
     */
    public List<TPAExtensionType> getTPAExtension() {
        if (tpaExtension == null) {
            tpaExtension = new ArrayList<TPAExtensionType>();
        }
        return this.tpaExtension;
    }

    /**
     * 获取invBlockCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getInvBlockCode() {
        return invBlockCode;
    }

    /**
     * 设置invBlockCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setInvBlockCode(String value) {
        this.invBlockCode = value;
    }

    /**
     * 获取themeCategory属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ThemeCategoryType }
     *     
     */
    public ThemeCategoryType getThemeCategory() {
        return themeCategory;
    }

    /**
     * 设置themeCategory属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ThemeCategoryType }
     *     
     */
    public void setThemeCategory(ThemeCategoryType value) {
        this.themeCategory = value;
    }

    /**
     * 获取cityImportantMessage属性的值。
     * 
     * @return
     *     possible object is
     *     {@link CityImportantMessageType }
     *     
     */
    public CityImportantMessageType getCityImportantMessage() {
        return cityImportantMessage;
    }

    /**
     * 设置cityImportantMessage属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link CityImportantMessageType }
     *     
     */
    public void setCityImportantMessage(CityImportantMessageType value) {
        this.cityImportantMessage = value;
    }

    /**
     * 获取roomquantity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRoomquantity() {
        return roomquantity;
    }

    /**
     * 设置roomquantity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRoomquantity(String value) {
        this.roomquantity = value;
    }

    /**
     * 获取masterSubHotelIDs属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMasterSubHotelIDs() {
        return masterSubHotelIDs;
    }

    /**
     * 设置masterSubHotelIDs属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMasterSubHotelIDs(String value) {
        this.masterSubHotelIDs = value;
    }

}
