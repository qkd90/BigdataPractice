package com.data.hmly.service.translation.flight.juhe.entity;

/**
 * Created by Sane on 15/12/23.
 */
public class OrderFlights {

    public OrderFlights(String takeoffPort, String landingPort, String flightDate, String flightNum, String cabin) {
        this.takeoffPort = takeoffPort;
        this.landingPort = landingPort;
        this.flightDate = flightDate;
        this.flightNum = flightNum;
        this.cabin = cabin;
    }

    /**
     * takeoffPort : XMN
     * landingPort : SHA
     * flightDate : 2016-01-19
     * flightNum : MU5562
     * cabin : T
     */

    private String takeoffPort;
    private String landingPort;
    private String flightDate;
    private String flightNum;
    private String cabin;

    public void setTakeoffPort(String takeoffPort) {
        this.takeoffPort = takeoffPort;
    }

    public void setLandingPort(String landingPort) {
        this.landingPort = landingPort;
    }

    public void setFlightDate(String flightDate) {
        this.flightDate = flightDate;
    }

    public void setFlightNum(String flightNum) {
        this.flightNum = flightNum;
    }

    public void setCabin(String cabin) {
        this.cabin = cabin;
    }

    public String getTakeoffPort() {
        return takeoffPort;
    }

    public String getLandingPort() {
        return landingPort;
    }

    public String getFlightDate() {
        return flightDate;
    }

    public String getFlightNum() {
        return flightNum;
    }

    public String getCabin() {
        return cabin;
    }
}
