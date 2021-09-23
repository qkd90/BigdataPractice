package com.data.spider.service.pojo.ctrip;

/**
 * Created by Sane on 15/9/28.
 */
public class Trainstation {


    public Responsestatus ResponseStatus;
    public Trainstationinfo TrainStationInfo;

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

    public class Trainstationinfo {
        public int PoiId;
        public int TrainStationId;
        public String TrainStationName;
        public int DistrictId;
        public String DistrictName;
        public int CityId;
        public double Lon;
        public double Lat;
        public boolean InChina;
        public boolean IsOverSeas;
        public String[] Picture;
        public int PictureTotalCount;
        public float TotalStar;
        public int TotalCommentCount;
        public String Address;
        public int Distance;
        //        public object[] ReceiveDeliverTicketList;
        public String Telephone;
        //        public object[] HotelInfoList;
        public int HotelCount;
        public Restaurantinfolist[] RestaurantInfoList;
        public int RestaurantCount;
        public int ShopCount;
        //        public object[] TrainStationInfos;
        public Weather Weather;
//        public object[] GuideRoutes;
    }

    public class Weather {
        public int DayTemperature;
        public int NightTemperature;
        public String DayWeather;
        public int DayWeatherNo;
        public String NightWeather;
        public int NightWeatherNo;
//        public DateTime ForecastDate;
    }

    public class Restaurantinfolist {
        public int BusinessId;
        public int PoiId;
        public String PoiName;
        public String PictureSrc;
        public float TotalStar;
        public int TotalCommentCount;
        public float Pirce;
        public float Distance;
        public String[] RestaurantTagNames;
        public int DistrictId;
        public float Lon;
        public float Lat;
        public boolean IsInChina;
    }

}
