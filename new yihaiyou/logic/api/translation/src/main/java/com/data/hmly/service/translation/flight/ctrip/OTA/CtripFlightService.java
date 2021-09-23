package com.data.hmly.service.translation.flight.ctrip.OTA;


import com.data.hmly.service.translation.flight.ctrip.OTA.pojo.AirlineInfos.AirlineInfosResponse;
import com.data.hmly.service.translation.flight.ctrip.OTA.pojo.AirportInfos.AirportInfosResponse;
import com.data.hmly.service.translation.flight.ctrip.OTA.pojo.CityInfos.CityInfosResponse;
import com.data.hmly.service.translation.flight.ctrip.OTA.pojo.CraftInfos.CraftInfosResponse;
import com.data.hmly.service.translation.flight.ctrip.OTA.pojo.FlightSearch.FlightSearchRequest;
import com.data.hmly.service.translation.flight.ctrip.OTA.pojo.FlightSearch.FlightSearchRequestType;
import com.data.hmly.service.translation.flight.ctrip.OTA.pojo.FlightSearch.FlightSearchResponse;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import java.io.StringReader;
import java.io.StringWriter;

/**
 * Created by Sane on 15/10/27.
 */

public class CtripFlightService {
//    private static Logger logger = Logger.getLogger(CtripFlightService.class);

    private static final String FLIGHT_SEARCH_REQUEST_TYPE = "OTA_FlightSearch";
    private static final String FLIGHT_CRAFT_REQUEST_TYPE = "OTA_FltGetCraftInfos";
    private static final String FLIGHT_CITY_REQUEST_TYPE = "OTA_FltGetCityInfos";
    private static final String FLIGHT_AIRLINE_REQUEST_TYPE = "OTA_FltGetAirlineInfos";
    private static final String FLIGHT_AIRPORT_REQUEST_TYPE = "OTA_FltGetAirportInfos";


    private static final String FLIGHT_SEARCH_URL = "http://openapi.ctrip.com/Flight/DomesticFlight/OTA_FlightSearch.asmx";
    private static final String FLIGHT_CRAFT_URL = "http://openapi.ctrip.com/Flight/FlightProduct/OTA_FltGetCraftInfos.asmx";
    private static final String FLIGHT_CITY_URL = "http://openapi.ctrip.com/Flight/FlightProduct/OTA_FltGetCityInfos.asmx";
    private static final String FLIGHT_AIRLINE_URL = "http://openapi.ctrip.com/Flight/FlightProduct/OTA_FltGetAirlineInfos.asmx";
    private static final String FLIGHT_AIRPORT_URL = "http://openapi.ctrip.com/Flight/FlightProduct/OTA_FltGetAirportInfos.asmx";


    /**
     * @param searchRequest 携程的航班信息请求类型
     * @return 航班信息
     */
    public static FlightSearchResponse getFlightSearchResponse(FlightSearchRequestType searchRequest) {
        FlightSearchRequest flightSearchRequest = new FlightSearchRequest();
        flightSearchRequest.setHeader(CtripRequestService.getRequestHead(FLIGHT_SEARCH_REQUEST_TYPE));
        flightSearchRequest.setFlightSearchRequest(searchRequest);
        try {
            JAXBContext context = JAXBContext.newInstance(FlightSearchResponse.class);
            //将请求转换为xml
            Marshaller marshaller =  context.createMarshaller();
            StringWriter requestData = new StringWriter();
            marshaller.marshal(flightSearchRequest, requestData);
            //提交请求，获得xml响应
            String xmlResponse = CtripRequestService.postForXMLUseGzip(FLIGHT_SEARCH_URL,requestData.toString());
            //将响应转换为实体类
            Unmarshaller unmarshaller = context.createUnmarshaller();
            FlightSearchResponse response = (FlightSearchResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
            return response;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return 航空公司信息
     */
    public static AirlineInfosResponse getAirlineInfos() {
        String requestData = "";
        String xmlResponse = CtripRequestService.postForXMLUseGzip(FLIGHT_AIRLINE_URL,FLIGHT_AIRLINE_REQUEST_TYPE,requestData);
        try {
            JAXBContext context = JAXBContext.newInstance(AirlineInfosResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            AirlineInfosResponse response = (AirlineInfosResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
            return response;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return 机场信息
     */
    public static AirportInfosResponse getAirportInfos() {
        String requestData = "";
        String xmlResponse = CtripRequestService.postForXMLUseGzip(FLIGHT_AIRPORT_URL,FLIGHT_AIRPORT_REQUEST_TYPE,requestData);
        try {
            JAXBContext context = JAXBContext.newInstance(AirportInfosResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            AirportInfosResponse response = (AirportInfosResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
            return response;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return 机型信息
     */
    public static CraftInfosResponse getCraftInfos() {
        String requestData = "";
        String xmlResponse = CtripRequestService.postForXMLUseGzip(FLIGHT_CRAFT_URL,FLIGHT_CRAFT_REQUEST_TYPE,requestData);
        try {
            JAXBContext context = JAXBContext.newInstance(CraftInfosResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            CraftInfosResponse response = (CraftInfosResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
            return response;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * @return 城市信息
     */
    public static CityInfosResponse getCityInfos() {
        String requestData = "";
        String xmlResponse = CtripRequestService.postForXMLUseGzip(FLIGHT_CITY_URL, FLIGHT_CITY_REQUEST_TYPE,requestData);
        try {
            JAXBContext context = JAXBContext.newInstance(CityInfosResponse.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            CityInfosResponse response = (CityInfosResponse) unmarshaller.unmarshal(new StringReader(xmlResponse));
            return response;
        } catch (JAXBException e) {
            e.printStackTrace();
        }
        return null;
    }


}
