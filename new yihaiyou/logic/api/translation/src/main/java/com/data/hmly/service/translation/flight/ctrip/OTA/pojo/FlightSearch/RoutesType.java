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
 * <p>RoutesType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="RoutesType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="FlightRoute" type="{}FlightRouteType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "RoutesType", propOrder = {
    "flightRoute"
})
public class RoutesType {

    @XmlElement(name = "FlightRoute", required = true)
    protected FlightRouteType flightRoute;

    /**
     * 获取flightRoute属性的值。
     * 
     * @return
     *     possible object is
     *     {@link FlightRouteType }
     *     
     */
    public FlightRouteType getFlightRoute() {
        return flightRoute;
    }

    /**
     * 设置flightRoute属性的值。
     * 
     * @param value
     *     allowed object is
     *     {@link FlightRouteType }
     *     
     */
    public void setFlightRoute(FlightRouteType value) {
        this.flightRoute = value;
    }

}
