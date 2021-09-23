//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2016.01.28 时间 04:38:59 PM CST 
//


package com.data.data.hmly.service.elong.pojo.statics.hotelDetail;

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

    private final static QName _Hotel_QNAME = new QName("", "Hotel");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: generated
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link HotelType }
     * 
     */
    public HotelType createHotelType() {
        return new HotelType();
    }

    /**
     * Create an instance of {@link SupplierType }
     * 
     */
    public SupplierType createSupplierType() {
        return new SupplierType();
    }

    /**
     * Create an instance of {@link DetailType }
     * 
     */
    public DetailType createDetailType() {
        return new DetailType();
    }

    /**
     * Create an instance of {@link AvailPolicyType }
     * 
     */
    public AvailPolicyType createAvailPolicyType() {
        return new AvailPolicyType();
    }

    /**
     * Create an instance of {@link ServiceRankType }
     * 
     */
    public ServiceRankType createServiceRankType() {
        return new ServiceRankType();
    }

    /**
     * Create an instance of {@link RoomsType }
     * 
     */
    public RoomsType createRoomsType() {
        return new RoomsType();
    }

    /**
     * Create an instance of {@link LocationsType }
     * 
     */
    public LocationsType createLocationsType() {
        return new LocationsType();
    }

    /**
     * Create an instance of {@link ImagesType }
     * 
     */
    public ImagesType createImagesType() {
        return new ImagesType();
    }

    /**
     * Create an instance of {@link ImageType }
     * 
     */
    public ImageType createImageType() {
        return new ImageType();
    }

    /**
     * Create an instance of {@link SuppliersType }
     * 
     */
    public SuppliersType createSuppliersType() {
        return new SuppliersType();
    }

    /**
     * Create an instance of {@link RoomType }
     * 
     */
    public RoomType createRoomType() {
        return new RoomType();
    }

    /**
     * Create an instance of {@link FacilitiesV2Type }
     * 
     */
    public FacilitiesV2Type createFacilitiesV2Type() {
        return new FacilitiesV2Type();
    }

    /**
     * Create an instance of {@link LocationType }
     * 
     */
    public LocationType createLocationType() {
        return new LocationType();
    }

    /**
     * Create an instance of {@link ReviewType }
     * 
     */
    public ReviewType createReviewType() {
        return new ReviewType();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link HotelType }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "", name = "Hotel")
    public JAXBElement<HotelType> createHotel(HotelType value) {
        return new JAXBElement<HotelType>(_Hotel_QNAME, HotelType.class, null, value);
    }

}
