//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, v2.2.4-2 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2015.12.05 at 01:48:07 PM CST 
//


package com.data.data.hmly.service.ctripflight.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "ctrip_passenger_info")
public class Passenger {

	private static final long serialVersionUID = -2996522995238348090L;
	@Id
	@Column(name = "id", length = 11)
	private Integer id;
    @Column(name = "passengerName")
    private String passengerName;
    @Column(name = "birthDay")
    private String birthDay;
    @Column(name = "passportTypeID")
    private String passportTypeID;
    @Column(name = "passportNo")
    private String passportNo;
    @Column(name = "contactTelephone")
    private String contactTelephone;
    @Column(name = "gender")
    private String gender;
    @Column(name = "nationalityCode")
    private String nationalityCode;
    
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getPassengerName() {
		return passengerName;
	}
	public void setPassengerName(String passengerName) {
		this.passengerName = passengerName;
	}
	public String getBirthDay() {
		return birthDay;
	}
	public void setBirthDay(String birthDay) {
		this.birthDay = birthDay;
	}
	public String getPassportTypeID() {
		return passportTypeID;
	}
	public void setPassportTypeID(String passportTypeID) {
		this.passportTypeID = passportTypeID;
	}
	public String getPassportNo() {
		return passportNo;
	}
	public void setPassportNo(String passportNo) {
		this.passportNo = passportNo;
	}
	public String getContactTelephone() {
		return contactTelephone;
	}
	public void setContactTelephone(String contactTelephone) {
		this.contactTelephone = contactTelephone;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getNationalityCode() {
		return nationalityCode;
	}
	public void setNationalityCode(String nationalityCode) {
		this.nationalityCode = nationalityCode;
	}

}
