//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.04 at 10:51:42 PM CST 
//


package com.data.data.hmly.service.ctripflight.request.pojo;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;
import java.util.ArrayList;
import java.util.List;


/**
 * <p>Java class for OrigDestRequestListType complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="OrigDestRequestListType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="OrigDestRequest" type="{}OrigDestRequestType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "OrigDestRequestListType", propOrder = {
    "origDestRequest"
})
public class IntlFlightOrigDestRequestListType {

    @XmlElement(name = "OrigDestRequest")
    protected List<IntlFlightOrigDestRequestType> origDestRequest;

    /**
     * Gets the value of the origDestRequest property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the origDestRequest property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOrigDestRequest().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link OrigDestRequestType }
     * 
     * 
     */
    public List<IntlFlightOrigDestRequestType> getOrigDestRequest() {
        if (origDestRequest == null) {
            origDestRequest = new ArrayList<IntlFlightOrigDestRequestType>();
        }
        return this.origDestRequest;
    }

}
