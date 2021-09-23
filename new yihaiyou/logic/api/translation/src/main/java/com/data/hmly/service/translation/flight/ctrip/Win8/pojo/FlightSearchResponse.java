package com.data.hmly.service.translation.flight.ctrip.Win8.pojo;

import org.joda.time.DateTime;

/**
 * Created by Sane on 15/10/27.
 */
public class FlightSearchResponse {


    public class Response {

        private ResponseHeader headerField;

        private ResponseFlightSearchResponse flightSearchResponseField;


    }


    public class ResponseHeader {

        private String shouldRecordPerformanceTimeField;

        private String timestampField;

        private String referenceIDField;

        private String resultCodeField;


    }


    public class ResponseFlightSearchResponse {

        private ResponseFlightSearchResponseFlightRoutes flightRoutesField;

    }

    public class ResponseFlightSearchResponseFlightRoutes {

        private ResponseFlightSearchResponseFlightRoutesDomesticFlightRoute domesticFlightRouteField;

    }

    public class ResponseFlightSearchResponseFlightRoutesDomesticFlightRoute {

        private byte recordCountField;

        private String orderByField;

        private String directionField;

        private ResponseFlightSearchResponseFlightRoutesDomesticFlightRouteDomesticFlightData[] flightsListField;


    }

    public class ResponseFlightSearchResponseFlightRoutesDomesticFlightRouteDomesticFlightData {

        private String departCityCodeField;

        private String arriveCityCodeField;

        private DateTime takeOffTimeField;

        private DateTime arriveTimeField;

        private String flightField;

        private String craftTypeField;

        private String airlineCodeField;

        private String classField;

        private String subClassField;

        private String displaySubclassField;

        private double rateField;

        private int priceField;

        private double standardPriceField;

        private int childStandardPriceField;

        private int babyStandardPriceField;

        private String mealTypeField;

        private byte adultTaxField;

        private byte babyTaxField;

        private byte childTaxField;

        private double adultOilFeeField;

        private byte babyOilFeeField;

        private double childOilFeeField;

        private String dPortCodeField;

        private String aPortCodeField;

        private byte dPortBuildingIDField;

        private byte aPortBuildingIDField;

        private byte stopTimesField;

        private String nonrerField;

        private String nonendField;

        private String nonrefField;

        private String rernoteField;

        private String endnoteField;

        private String refnoteField;

        private String remarksField;

        private int ticketTypeField;

        private byte beforeFlyDateField;

        private byte quantityField;

        private String priceTypeField;

        private String productTypeField;

        private byte productSourceField;

        private String inventoryTypeField;

        private byte routeIndexField;

        private String needApplyStringField;

        private byte recommendField;

        private byte refundFeeFormulaIDField;

        private boolean canUpGradeField;

        private Object canSeparateSaleField;

        private boolean canNoDeferField;

        private boolean isFlyManField;

        private boolean onlyOwnCityField;

        private boolean isLowestPriceField;

        private boolean isLowestCZSpecialPriceField;

        private byte punctualityRateField;

//       private uint policyIDField;

        private int allowCPTypeField;

        private boolean outOfPostTimeField;

        private boolean outOfSendGetTimeField;

        private boolean outOfAirlineCounterTimeField;

        private boolean canPostField;

        private boolean canAirlineCounterField;

        private boolean canSendGetField;

        private boolean isRebateField;

        private byte rebateAmountField;

        private Object rebateCPCityField;

    }

}
