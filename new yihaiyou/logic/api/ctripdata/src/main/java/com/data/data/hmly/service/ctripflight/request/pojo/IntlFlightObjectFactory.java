//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.04 at 10:51:42 PM CST 
//


package com.data.data.hmly.service.ctripflight.request.pojo;

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
public class IntlFlightObjectFactory {

    private final static QName _Request_QNAME = new QName("", "Request");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public IntlFlightObjectFactory() {
    }

    /**
     * Create an instance of {@link FltOrderRequestType }
     * 
     */
    public IntlFlightRequestType createRequestType() {
        return new IntlFlightRequestType();
    }

    /**
     * Create an instance of {@link TravelerRequestListType }
     * 
     */
    public IntlFlightTravelerRequestListType createTravelerRequestListType() {
        return new IntlFlightTravelerRequestListType();
    }

    /**
     * Create an instance of {@link TravelerRequestType }
     * 
     */
    public IntlFlightTravelerRequestType createTravelerRequestType() {
        return new IntlFlightTravelerRequestType();
    }

    /**
     * Create an instance of {@link OrigDestRequestType }
     * 
     */
    public IntlFlightOrigDestRequestType createOrigDestRequestType() {
        return new IntlFlightOrigDestRequestType();
    }

    /**
     * Create an instance of {@link SortInstructionType }
     * 
     */
    public IntlFlightSortInstructionType createSortInstructionType() {
        return new IntlFlightSortInstructionType();
    }

    /**
     * Create an instance of {@link IntlFlightSearchRequestType }
     * 
     */
    public IntlFlightSearchRequestType createIntlFlightSearchRequestType() {
        return new IntlFlightSearchRequestType();
    }

    /**
     * Create an instance of {@link FltOrderHeaderType }
     * 
     */
    public IntlFlightHeaderType createHeaderType() {
        return new IntlFlightHeaderType();
    }

    /**
     * Create an instance of {@link OrigDestRequestListType }
     * 
     */
    public IntlFlightOrigDestRequestListType createOrigDestRequestListType() {
        return new IntlFlightOrigDestRequestListType();
    }

    /**
     * Create an instance of {@link RouteSearchControlType }
     * 
     */
    public IntlFlightRouteSearchControlType createRouteSearchControlType() {
        return new IntlFlightRouteSearchControlType();
    }

    /**
     * Create an instance of {@link PartitionSearchControlType }
     * 
     */
    public IntlFlightPartitionSearchControlType createPartitionSearchControlType() {
        return new IntlFlightPartitionSearchControlType();
    }

    /**
     * Create an instance of {@link SearchCriteriaType }
     * 
     */
    public IntlFlightSearchCriteriaType createSearchCriteriaType() {
        return new IntlFlightSearchCriteriaType();
    }

    /**
     * Create an instance of {@link ResultControlType }
     * 
     */
    public IntlFlightResultControlType createResultControlType() {
        return new IntlFlightResultControlType();
    }

    /**
     * Create an instance of {@link SearchContextType }
     * 
     */
    public IntlFlightSearchContextType createSearchContextType() {
        return new IntlFlightSearchContextType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link FltOrderRequestType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Request")
    public JAXBElement<IntlFlightRequestType> createRequest(IntlFlightRequestType value) {
        return new JAXBElement<IntlFlightRequestType>(_Request_QNAME, IntlFlightRequestType.class, null, value);
    }

}
