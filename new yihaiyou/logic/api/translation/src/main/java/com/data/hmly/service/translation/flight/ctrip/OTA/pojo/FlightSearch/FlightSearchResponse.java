//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.30 时间 01:46:02 PM CST 
//


package com.data.hmly.service.translation.flight.ctrip.OTA.pojo.FlightSearch;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>ResponseType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="ResponseType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Header" type="{}HeaderType"/>
 *         &lt;element name="FlightSearchResponse" type="{}FlightSearchResponseType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightSearchResponse", propOrder = {
    "header",
    "flightSearchResponse"
})
@XmlRootElement(name="Response")
public class FlightSearchResponse {

    @XmlElement(name = "Header", required = true)
    protected ResponseHeaderType header;
    @XmlElement(name = "FlightSearchResponse", required = true)
    protected FlightSearchResponseType flightSearchResponse;

    /**
     * 获取header属性的值。
     * 
     * @return
     *     possible object is
     *     {@link ResponseHeaderType }
     *     
     */
    public ResponseHeaderType getHeader() {
        return header;
    }

    /**
     * 设置header属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link ResponseHeaderType }
     *     
     */
    public void setHeader(ResponseHeaderType value) {
        this.header = value;
    }

    /**
     * 获取flightSearchResponse属性的值。
     * 
     * @return
     *     possible object is
     *     {@link FlightSearchResponseType }
     *     
     */
    public FlightSearchResponseType getFlightSearchResponse() {
        return flightSearchResponse;
    }

    /**
     * 设置flightSearchResponse属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link FlightSearchResponseType }
     *     
     */
    public void setFlightSearchResponse(FlightSearchResponseType value) {
        this.flightSearchResponse = value;
    }

}
