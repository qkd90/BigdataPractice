//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.30 时间 02:21:00 PM CST 
//


package com.data.hmly.service.translation.flight.ctrip.OTA.pojo.CityInfos;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>CityInfoEntityType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="CityInfoEntityType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CityCode" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CityId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CityName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CityName_En" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ProvinceId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CountryId" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CountryCNName" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IsDCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IsACity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IsTCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IsDomesticCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "CityInfoEntityType", propOrder = {
    "cityCode",
    "cityId",
    "cityName",
    "cityNameEn",
    "provinceId",
    "countryId",
    "countryCNName",
    "isDCity",
    "isACity",
    "isTCity",
    "isDomesticCity"
})
public class CityInfoEntityType {

    @XmlElement(name = "CityCode", required = true)
    protected String cityCode;
    @XmlElement(name = "CityId", required = true)
    protected String cityId;
    @XmlElement(name = "CityName", required = true)
    protected String cityName;
    @XmlElement(name = "CityName_En", required = true)
    protected String cityNameEn;
    @XmlElement(name = "ProvinceId", required = true)
    protected String provinceId;
    @XmlElement(name = "CountryId", required = true)
    protected String countryId;
    @XmlElement(name = "CountryCNName", required = true)
    protected String countryCNName;
    @XmlElement(name = "IsDCity", required = true)
    protected String isDCity;
    @XmlElement(name = "IsACity", required = true)
    protected String isACity;
    @XmlElement(name = "IsTCity", required = true)
    protected String isTCity;
    @XmlElement(name = "IsDomesticCity", required = true)
    protected String isDomesticCity;

    /**
     * 获取cityCode属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityCode() {
        return cityCode;
    }

    /**
     * 设置cityCode属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityCode(String value) {
        this.cityCode = value;
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
     * 获取cityName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityName() {
        return cityName;
    }

    /**
     * 设置cityName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityName(String value) {
        this.cityName = value;
    }

    /**
     * 获取cityNameEn属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCityNameEn() {
        return cityNameEn;
    }

    /**
     * 设置cityNameEn属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCityNameEn(String value) {
        this.cityNameEn = value;
    }

    /**
     * 获取provinceId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProvinceId() {
        return provinceId;
    }

    /**
     * 设置provinceId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProvinceId(String value) {
        this.provinceId = value;
    }

    /**
     * 获取countryId属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryId() {
        return countryId;
    }

    /**
     * 设置countryId属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryId(String value) {
        this.countryId = value;
    }

    /**
     * 获取countryCNName属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCountryCNName() {
        return countryCNName;
    }

    /**
     * 设置countryCNName属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCountryCNName(String value) {
        this.countryCNName = value;
    }

    /**
     * 获取isDCity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsDCity() {
        return isDCity;
    }

    /**
     * 设置isDCity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsDCity(String value) {
        this.isDCity = value;
    }

    /**
     * 获取isACity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsACity() {
        return isACity;
    }

    /**
     * 设置isACity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsACity(String value) {
        this.isACity = value;
    }

    /**
     * 获取isTCity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsTCity() {
        return isTCity;
    }

    /**
     * 设置isTCity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsTCity(String value) {
        this.isTCity = value;
    }

    /**
     * 获取isDomesticCity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsDomesticCity() {
        return isDomesticCity;
    }

    /**
     * 设置isDomesticCity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsDomesticCity(String value) {
        this.isDomesticCity = value;
    }

}
