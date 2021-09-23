//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.30 时间 02:45:34 PM CST 
//


package com.data.hmly.service.translation.flight.ctrip.OTA.pojo.FlightSearch;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the generated package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _Request_QNAME = new QName("", "Request");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link FlightSearchRequest }
     * 
     */
    public FlightSearchRequest createRequestType() {
        return new FlightSearchRequest();
    }

    /**
     * Create an instance of {@link RoutesType }
     * 
     */
    public RoutesType createRoutesType() {
        return new RoutesType();
    }

    /**
     * Create an instance of {@link FlightRouteType }
     * 
     */
    public FlightRouteType createFlightRouteType() {
        return new FlightRouteType();
    }

    /**
     * Create an instance of {@link HeaderType }
     * 
     */
    public HeaderType createHeaderType() {
        return new HeaderType();
    }

    /**
     * Create an instance of {@link FlightSearchRequestType }
     * 
     */
    public FlightSearchRequestType createFlightSearchRequestType() {
        return new FlightSearchRequestType();
    }

    /**
     * Create an instance of {@link PriceTypeOptionsType }
     * 
     */
    public PriceTypeOptionsType createPriceTypeOptionsType() {
        return new PriceTypeOptionsType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FlightSearchRequest }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Request")
    public JAXBElement<FlightSearchRequest> createRequest(FlightSearchRequest value) {
        return new JAXBElement<FlightSearchRequest>(_Request_QNAME, FlightSearchRequest.class, null, value);
    }

}
