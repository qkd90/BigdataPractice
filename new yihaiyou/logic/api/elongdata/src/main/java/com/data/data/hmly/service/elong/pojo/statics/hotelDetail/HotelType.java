//
// 此文件是由 JavaTM Architecture for XML Binding (JAXB) 引用实现 v2.2.8-b130911.1802 生成的
// 请访问 <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// 在重新编译源模式时, 对此文件的所有修改都将丢失。
// 生成时间: 2016.01.28 时间 04:38:59 PM CST 
//


package com.data.data.hmly.service.elong.pojo.statics.hotelDetail;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>HotelType complex type的 Java 类。
 * 
 * <p>以下模式片段指定包含在此类中的预期内容。
 * 
 * <pre>
 * &lt;complexType name="HotelType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Detail" type="{}DetailType"/>
 *         &lt;element name="Rooms" type="{}RoomsType"/>
 *         &lt;element name="Images" type="{}ImagesType"/>
 *         &lt;element name="Review" type="{}ReviewType"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}string" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "HotelType", propOrder = {
        "detail",
        "rooms",
        "images",
        "review"
})
@XmlRootElement(name = "Hotel")
public class HotelType {

    @XmlElement(name = "Detail", required = true)
    protected DetailType detail;
    @XmlElement(name = "Rooms", required = true)
    protected RoomsType rooms;
    @XmlElement(name = "Images", required = true)
    protected ImagesType images;
    @XmlElement(name = "Review", required = true)
    protected ReviewType review;
    @XmlAttribute(name = "Id")
    protected String id;

    /**
     * 获取detail属性的值。
     *
     * @return
     *     possible object is
     *     {@link DetailType }
     *     
     */
    public DetailType getDetail() {
        return detail;
    }

    /**
     * 设置detail属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link DetailType }
     *     
     */
    public void setDetail(DetailType value) {
        this.detail = value;
    }

    /**
     * 获取rooms属性的值。
     *
     * @return
     *     possible object is
     *     {@link RoomsType }
     *     
     */
    public RoomsType getRooms() {
        return rooms;
    }

    /**
     * 设置rooms属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link RoomsType }
     *     
     */
    public void setRooms(RoomsType value) {
        this.rooms = value;
    }

    /**
     * 获取images属性的值。
     *
     * @return
     *     possible object is
     *     {@link ImagesType }
     *     
     */
    public ImagesType getImages() {
        return images;
    }

    /**
     * 设置images属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link ImagesType }
     *     
     */
    public void setImages(ImagesType value) {
        this.images = value;
    }

    /**
     * 获取review属性的值。
     *
     * @return
     *     possible object is
     *     {@link ReviewType }
     *     
     */
    public ReviewType getReview() {
        return review;
    }

    /**
     * 设置review属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link ReviewType }
     *     
     */
    public void setReview(ReviewType value) {
        this.review = value;
    }

    /**
     * 获取id属性的值。
     *
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getId() {
        return id;
    }

    /**
     * 设置id属性的值。
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setId(String value) {
        this.id = value;
    }

}
