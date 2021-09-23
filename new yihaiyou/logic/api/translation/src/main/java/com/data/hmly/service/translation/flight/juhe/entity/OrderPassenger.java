package com.data.hmly.service.translation.flight.juhe.entity;

/**
 * Created by Sane on 15/12/23.
 */
public class OrderPassenger {

    public OrderPassenger(String passengerType, String passengerName, String passengerMobile, String idType, String idNumber, String finallyPrice, String airportPrice, String fuelPrice) {
        this.passengerType = passengerType;
        this.passengerName = passengerName;
        this.passengerMobile = passengerMobile;
        this.idType = idType;
        this.idNumber = idNumber;
        this.finallyPrice = finallyPrice;
        this.airportPrice = airportPrice;
        this.fuelPrice = fuelPrice;
    }

    /**
     * passengerType : ADULT:成人 CHILD:儿童 BABY:婴儿
     * passengerName : xxx
     * passengerMobile : xxxxxx
     * idType : 0
     * idNumber : xxxxxxxxxxxx
     * finallyPrice : 240
     * airportPrice : 50
     * fuelPrice : 0
     * extInfo : {}
     */

    private String passengerType;
    private String passengerName;
    private String passengerMobile;
    private String idType;
    private String idNumber;
    private String finallyPrice;
    private String airportPrice;
    private String fuelPrice;

    public void setPassengerType(String passengerType) {
        this.passengerType = passengerType;
    }

    public void setPassengerName(String passengerName) {
        this.passengerName = passengerName;
    }

    public void setPassengerMobile(String passengerMobile) {
        this.passengerMobile = passengerMobile;
    }

    public void setIdType(String idType) {
        this.idType = idType;
    }

    public void setIdNumber(String idNumber) {
        this.idNumber = idNumber;
    }

    public void setFinallyPrice(String finallyPrice) {
        this.finallyPrice = finallyPrice;
    }

    public void setAirportPrice(String airportPrice) {
        this.airportPrice = airportPrice;
    }

    public void setFuelPrice(String fuelPrice) {
        this.fuelPrice = fuelPrice;
    }

    public String getPassengerType() {
        return passengerType;
    }

    public String getPassengerName() {
        return passengerName;
    }

    public String getPassengerMobile() {
        return passengerMobile;
    }

    public String getIdType() {
        return idType;
    }

    public String getIdNumber() {
        return idNumber;
    }

    public String getFinallyPrice() {
        return finallyPrice;
    }

    public String getAirportPrice() {
        return airportPrice;
    }

    public String getFuelPrice() {
        return fuelPrice;
    }
}
