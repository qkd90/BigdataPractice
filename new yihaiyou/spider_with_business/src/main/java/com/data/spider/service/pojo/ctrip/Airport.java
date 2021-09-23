package com.data.spider.service.pojo.ctrip;

/**
 * Created by Sane on 15/9/28.
 */
public class Airport {


    public Responsestatus ResponseStatus;
    public Airportdetailinfo AirportDetailInfo;

    public class Responsestatus {
        //        public DateTime Timestamp;
        public String Ack;
        //        public object[] Errors;
        public Extension[] Extension;
    }

    public class Extension {
        public String Id;
        public String Value;
    }

    public class Airportdetailinfo {
        //        public int PoiId;
//        public int PoiType;
        public int AirportId;
        public String AirportName;
        //        public int TerminalId;
        public String AirportEname;
        public int DistrictId;
        public String DistrictName;
        public int CityId;
        public double Lon;
        public double Lat;
        //        public boolean InChina;
//        public boolean IsOverSeas;
        public String ThreeCode;
        public String[] Picture;
        //        public int PictureTotalCount;
//        public float TotalStar;
//        public int TotalCommentCount;
        public String Address;
//        public int Distance;
        //        public object[] ReceiveDeliverTicketList;
//        public String Introduction;
//        public String OfficialSite;
//        public int HotelCount;
//        public int HotelPrice;
        //        public object[] RelatedAirports;
//        public object[] RelatedTraffic;
//        public Airportrestaurant[] AirportRestaurants;
        //        public object[] AirportShops;
//        public object[] AirportTips;
//        public Airportweather AirportWeather;
//        public Airportguideroute[] AirportGuideRoutes;
//        public Wikiguideinfo WikiGuideInfo;
    }

    public class Airportweather {
        public int DayTemperature;
        public int NightTemperature;
        public String DayWeather;
        public int DayWeatherNo;
        public String NightWeather;
        public int NightWeatherNo;
//        public DateTime ForecastDate;
    }

    public class Wikiguideinfo {
        public int LikeCount;
        public int TotalCount;
    }

    public class Airportrestaurant {
        public int TerminalId;
        public int BusinessId;
        public int PoiId;
        public String PoiName;
        public String PictureSrc;
        public float TotalStar;
        public int TotalCommentCount;
        public int Pirce;
        public String Currency;
        public float Distance;
        public String[] RestaurantTagNames;
        public int DistrictId;
        public float Lon;
        public float Lat;
//        public boolean IsInChina;
    }

    public class Airportguideroute {
        public int ReSourceType;
        public String CoverImage;
        public String ReSourceName;
    }

}
