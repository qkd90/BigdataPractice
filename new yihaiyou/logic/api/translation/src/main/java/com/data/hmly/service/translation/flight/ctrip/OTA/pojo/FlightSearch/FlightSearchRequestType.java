//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.30 时间 02:45:34 PM CST 
//


package com.data.hmly.service.translation.flight.ctrip.OTA.pojo.FlightSearch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>FlightSearchRequestType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="FlightSearchRequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SearchType" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Routes" type="{}RoutesType"/>
 *         &lt;element name="SendTicketCity" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IsSimpleResponse" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="IsLowestPrice" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PriceTypeOptions" type="{}PriceTypeOptionsType"/>
 *         &lt;element name="ProductTypeOptions" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="OrderBy" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="Direction" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightSearchRequestType", propOrder = {
    "searchType",
    "routes",
    "sendTicketCity",
    "isSimpleResponse",
    "isLowestPrice",
    "priceTypeOptions",
    "productTypeOptions",
    "orderBy",
    "direction"
})
public class FlightSearchRequestType {

    @XmlElement(name = "SearchType", required = true)
    protected String searchType;
    @XmlElement(name = "Routes", required = true)
    protected RoutesType routes;
    @XmlElement(name = "SendTicketCity", required = true)
    protected String sendTicketCity;
    @XmlElement(name = "IsSimpleResponse", required = true)
    protected String isSimpleResponse;
    @XmlElement(name = "IsLowestPrice", required = true)
    protected String isLowestPrice;
    @XmlElement(name = "PriceTypeOptions", required = true)
    protected PriceTypeOptionsType priceTypeOptions;
    @XmlElement(name = "ProductTypeOptions", required = true)
    protected String productTypeOptions;
    @XmlElement(name = "OrderBy", required = true)
    protected String orderBy;
    @XmlElement(name = "Direction", required = true)
    protected String direction;

    /**
     * 获取searchType属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSearchType() {
        return searchType;
    }

    /**
     * 设置searchType属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSearchType(String value) {
        this.searchType = value;
    }

    /**
     * 获取routes属性的值。
     * 
     * @return
     *     possible object is
     *     {@link RoutesType }
     *     
     */
    public RoutesType getRoutes() {
        return routes;
    }

    /**
     * 设置routes属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link RoutesType }
     *     
     */
    public void setRoutes(RoutesType value) {
        this.routes = value;
    }

    /**
     * 获取sendTicketCity属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSendTicketCity() {
        return sendTicketCity;
    }

    /**
     * 设置sendTicketCity属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSendTicketCity(String value) {
        this.sendTicketCity = value;
    }

    /**
     * 获取isSimpleResponse属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsSimpleResponse() {
        return isSimpleResponse;
    }

    /**
     * 设置isSimpleResponse属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsSimpleResponse(String value) {
        this.isSimpleResponse = value;
    }

    /**
     * 获取isLowestPrice属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIsLowestPrice() {
        return isLowestPrice;
    }

    /**
     * 设置isLowestPrice属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIsLowestPrice(String value) {
        this.isLowestPrice = value;
    }

    /**
     * 获取priceTypeOptions属性的值。
     * 
     * @return
     *     possible object is
     *     {@link PriceTypeOptionsType }
     *     
     */
    public PriceTypeOptionsType getPriceTypeOptions() {
        return priceTypeOptions;
    }

    /**
     * 设置priceTypeOptions属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link PriceTypeOptionsType }
     *     
     */
    public void setPriceTypeOptions(PriceTypeOptionsType value) {
        this.priceTypeOptions = value;
    }

    /**
     * 获取productTypeOptions属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getProductTypeOptions() {
        return productTypeOptions;
    }

    /**
     * 设置productTypeOptions属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setProductTypeOptions(String value) {
        this.productTypeOptions = value;
    }

    /**
     * 获取orderBy属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOrderBy() {
        return orderBy;
    }

    /**
     * 设置orderBy属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOrderBy(String value) {
        this.orderBy = value;
    }

    /**
     * 获取direction属性的值。
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDirection() {
        return direction;
    }

    /**
     * 设置direction属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDirection(String value) {
        this.direction = value;
    }

}
