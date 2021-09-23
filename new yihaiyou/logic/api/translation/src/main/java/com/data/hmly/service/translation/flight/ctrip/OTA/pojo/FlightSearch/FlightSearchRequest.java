//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.30 时间 02:45:34 PM CST 
//


package com.data.hmly.service.translation.flight.ctrip.OTA.pojo.FlightSearch;

import com.data.hmly.service.translation.flight.ctrip.OTA.pojo.RequestHead;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>RequestType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RequestType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Header" type="{}HeaderType"/>
 *         &lt;element name="FlightSearchRequest" type="{}FlightSearchRequestType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "FlightSearchRequest", propOrder = {
    "header",
    "flightSearchRequest"
})
@XmlRootElement(name="Request")
public class FlightSearchRequest {

    @XmlElement(name = "Header", required = true)
    protected RequestHead header;
    @XmlElement(name = "FlightSearchRequest", required = true)
    protected FlightSearchRequestType flightSearchRequest;

    /**
     * 获取header属性的值。
     * 
     * @return
     *     possible object is
     *     {@link HeaderType }
     *     
     */
    public RequestHead getHeader() {
        return header;
    }

    /**
     * 设置header属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link HeaderType }
     *     
     */
    public void setHeader(RequestHead value) {
        this.header = value;
    }

    /**
     * 获取flightSearchRequest属性的值。
     * 
     * @return
     *     possible object is
     *     {@link FlightSearchRequestType }
     *     
     */
    public FlightSearchRequestType getFlightSearchRequest() {
        return flightSearchRequest;
    }

    /**
     * 设置flightSearchRequest属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link FlightSearchRequestType }
     *     
     */
    public void setFlightSearchRequest(FlightSearchRequestType value) {
        this.flightSearchRequest = value;
    }

}
