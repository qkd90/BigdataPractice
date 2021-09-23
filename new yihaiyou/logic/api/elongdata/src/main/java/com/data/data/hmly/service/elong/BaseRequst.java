package com.data.data.hmly.service.elong;

import com.data.data.hmly.service.elong.pojo.EnumLocal;
import com.data.data.hmly.service.elong.pojo.HotelListCondition;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlSeeAlso;

@XmlRootElement(name = "Condition")
@XmlSeeAlso({HotelListCondition.class})
public class BaseRequst<T> {

    public BaseRequst(double version, EnumLocal local, T request) {
        Version = version;
        Local = local;
        Request = request;
    }

    @XmlElement(name = "Version")
    public double Version;

    @XmlElement(name = "Local")
    public EnumLocal Local;

    @XmlElement(name = "Request")
    public T Request;

}
