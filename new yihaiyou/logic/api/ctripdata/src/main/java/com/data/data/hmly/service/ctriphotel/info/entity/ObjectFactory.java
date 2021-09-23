//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2015.12.02 时间 03:17:21 PM CST 
//


package com.data.data.hmly.service.ctriphotel.info.entity;

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

    private final static QName _Response_QNAME = new QName("", "Response");
    private final static QName _RelativePositionTypeDescriptiveText_QNAME = new QName("", "DescriptiveText");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link ResponseType }
     * 
     */
    public ResponseType createResponseType() {
        return new ResponseType();
    }

    /**
     * Create an instance of {@link RelativePositionType }
     * 
     */
    public RelativePositionType createRelativePositionType() {
        return new RelativePositionType();
    }

    /**
     * Create an instance of {@link HotelResponseType }
     * 
     */
    public HotelResponseType createHotelResponseType() {
        return new HotelResponseType();
    }

    /**
     * Create an instance of {@link OTAHotelSearchRSType }
     * 
     */
    public OTAHotelSearchRSType createOTAHotelSearchRSType() {
        return new OTAHotelSearchRSType();
    }

    /**
     * Create an instance of {@link SubSectionType }
     * 
     */
    public SubSectionType createSubSectionType() {
        return new SubSectionType();
    }

    /**
     * Create an instance of {@link TPAExtensionsType }
     * 
     */
    public TPAExtensionsType createTPAExtensionsType() {
        return new TPAExtensionsType();
    }

    /**
     * Create an instance of {@link PropertyType }
     * 
     */
    public PropertyType createPropertyType() {
        return new PropertyType();
    }

    /**
     * Create an instance of {@link VendorMessageType }
     * 
     */
    public VendorMessageType createVendorMessageType() {
        return new VendorMessageType();
    }

    /**
     * Create an instance of {@link AwardType }
     * 
     */
    public AwardType createAwardType() {
        return new AwardType();
    }

    /**
     * Create an instance of {@link ZoneType }
     * 
     */
    public ZoneType createZoneType() {
        return new ZoneType();
    }

    /**
     * Create an instance of {@link PositionType }
     * 
     */
    public PositionType createPositionType() {
        return new PositionType();
    }

    /**
     * Create an instance of {@link ParagraphType }
     * 
     */
    public ParagraphType createParagraphType() {
        return new ParagraphType();
    }

    /**
     * Create an instance of {@link RateRangeType }
     * 
     */
    public RateRangeType createRateRangeType() {
        return new RateRangeType();
    }

    /**
     * Create an instance of {@link PropertiesType }
     * 
     */
    public PropertiesType createPropertiesType() {
        return new PropertiesType();
    }

    /**
     * Create an instance of {@link VendorMessagesType }
     * 
     */
    public VendorMessagesType createVendorMessagesType() {
        return new VendorMessagesType();
    }

    /**
     * Create an instance of {@link ZoneTypeType }
     * 
     */
    public ZoneTypeType createZoneTypeType() {
        return new ZoneTypeType();
    }

    /**
     * Create an instance of {@link HeaderType }
     * 
     */
    public HeaderType createHeaderType() {
        return new HeaderType();
    }

    /**
     * Create an instance of {@link AddressType }
     * 
     */
    public AddressType createAddressType() {
        return new AddressType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link ResponseType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Response")
    public JAXBElement<ResponseType> createResponse(ResponseType value) {
        return new JAXBElement<ResponseType>(_Response_QNAME, ResponseType.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "DescriptiveText", scope = RelativePositionType.class)
    public JAXBElement<String> createRelativePositionTypeDescriptiveText(String value) {
        return new JAXBElement<String>(_RelativePositionTypeDescriptiveText_QNAME, String.class, RelativePositionType.class, value);
    }

}
