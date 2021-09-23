//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.10.30 时间 02:13:44 PM CST 
//


package com.data.hmly.service.translation.flight.ctrip.OTA.pojo.AirportInfos;

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
     * Create an instance of {@link AirportInfosResponse }
     * 
     */
    public AirportInfosResponse createResponseType() {
        return new AirportInfosResponse();
    }

    /**
     * Create an instance of {@link GetAirportInfosResponseType }
     * 
     */
    public GetAirportInfosResponseType createGetAirportInfosResponseType() {
        return new GetAirportInfosResponseType();
    }

    /**
     * Create an instance of {@link ResponseHeaderType }
     * 
     */
    public ResponseHeaderType createHeaderType() {
        return new ResponseHeaderType();
    }

    /**
     * Create an instance of {@link AirportInfosListType }
     * 
     */
    public AirportInfosListType createAirportInfosListType() {
        return new AirportInfosListType();
    }

    /**
     * Create an instance of {@link AirportInfoEntityType }
     * 
     */
    public AirportInfoEntityType createAirportInfoEntityType() {
        return new AirportInfoEntityType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link AirportInfosResponse }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Response")
    public JAXBElement<AirportInfosResponse> createResponse(AirportInfosResponse value) {
        return new JAXBElement<AirportInfosResponse>(_Response_QNAME, AirportInfosResponse.class, null, value);
    }

}
