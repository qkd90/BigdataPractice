package com.data.data.hmly.service.base;

/**
 * Created by Sane on 15/9/22.
 */
public class CtripRestaurant {


    public Restaurantviewmodel RestaurantViewModel;
    public String[] FoodNameList;
//    public Responsestatus ResponseStatus;

    public class Restaurantviewmodel {
        public int RestaurantId;
        public int POIId;
        public int DistrictId;
        public String DistrictName;
        public String RestaurantName;
        public String EName;
        public String Address;
        public String ImageUrl;
        public float CommentScore;
        public float EnvironmentStar;
        public float TasteStar;
        public float ServiceStar;
        public int AveragePrice;
        public int CommentCount;
        public String CurrencyUnit;
        public String Description;
        public String OpenTime;
        public String[] FacilityNameList;
        //        public object[] PaymentCardNameList ;
        public String[] BookTelList;
        public String SpecialTips;
        public int MichelinRecommendStar;
        public int MichelinEnvironmentLevel;
        public float Lat;
        public float Lon;
        public boolean InChina;
        public int ImageCount;
        public String[] Foods;
        public Label[] Labels;
        public String ImageLabel;
        public Cuisine[] Cuisine;
        public float GoodCommentRate;
        public int AroundShopId;
        public int AroundShopCount;
        public int AroundGrouponCount;
        public int AroundSightCount;
        public int AroundHotelCount;
        public float AroundHotelMinPrice;
        public Reputationrankinglist[] ReputationRankingList;
        public int CityId;
        public String BuyUrl;
        public String WebSite;
//        public Aroundsightlist[] AroundSightList;
//        public Aroundrestaurantlist[] AroundRestaurantList;
    }

    public class Label {
        public int Id;
        public String Name;
    }

    public class Cuisine {
        public int Id;
        public String Name;
    }

    public class Reputationrankinglist {
        public String Name;
        public int SortNo;
        public String Url;
    }

    public class Aroundsightlist {
        public int Id;
        public int PoiId;
        public String Name;
        public String Distance;
        public float Score;
        public int ImgId;
        public String ImgUrl;
    }

    public class Aroundrestaurantlist {
        public int Id;
        public int PoiId;
        public String Name;
        public String Distance;
        public float Score;
        public long ImgId;
        public String ImgUrl;
    }

//    public class Responsestatus {
//        public DateTime Timestamp;
//        public String Ack;
//        public object[] Errors;
//        public object[] Extension;
//    }

}
