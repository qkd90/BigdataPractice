//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.30 时间 01:46:02 PM CST 
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
public class ResponseObjectFactory {

    private final static QName _Response_QNAME = new QName("", "Response");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ResponseObjectFactory() {
    }

    /**
     * Create an instance of {@link FlightSearchResponse }
     * 
     */
    public FlightSearchResponse createResponseType() {
        return new FlightSearchResponse();
    }

    /**
     * Create an instance of {@link FlightSearchResponseType }
     * 
     */
    public FlightSearchResponseType createFlightSearchResponseType() {
        return new FlightSearchResponseType();
    }

    /**
     * Create an instance of {@link DomesticFlightRouteType }
     * 
     */
    public DomesticFlightRouteType createDomesticFlightRouteType() {
        return new DomesticFlightRouteType();
    }

    /**
     * Create an instance of {@link FlightRoutesType }
     * 
     */
    public FlightRoutesType createFlightRoutesType() {
        return new FlightRoutesType();
    }

    /**
     * Create an instance of {@link ResponseHeaderType }
     * 
     */
    public ResponseHeaderType createHeaderType() {
        return new ResponseHeaderType();
    }

    /**
     * Create an instance of {@link DomesticFlightDataType }
     * 
     */
    public DomesticFlightDataType createDomesticFlightDataType() {
        return new DomesticFlightDataType();
    }

    /**
     * Create an instance of {@link FlightsListType }
     * 
     */
    public FlightsListType createFlightsListType() {
        return new FlightsListType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FlightSearchResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Response")
    public JAXBElement<FlightSearchResponse> createResponse(FlightSearchResponse value) {
        return new JAXBElement<FlightSearchResponse>(_Response_QNAME, FlightSearchResponse.class, null, value);
    }

}
