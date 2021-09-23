package com.data.hmly.service.translation.geo.baidu.pojo.GeoCoder;

/**
 * Created by Sane on 15/9/21.
 */
public class RederReverse {


    public int status;
    public Result result;

    public class Result {
        public Location location;
        public String formatted_address;
        //        public String business;
        public Addresscomponent addressComponent;
        //        public Pois[] pois;
        //        public object[] poiRegions
//        public String sematic_description;
        public int cityCode;
    }

    public class Location {
        public float lng;
        public float lat;
    }

    public class Addresscomponent {
        public String adcode;
        public String city;
        public String country;
        public String direction;
        public String distance;
        public String district;
        public String province;
        public String street;
        public String street_number;
        public int country_code;
    }

    public class Pois {
        public String addr;
        public String cp;
        public String direction;
        public String distance;
        public String name;
        public String poiType;
        public Point point;
        public String tag;
        public String tel;
        public String uid;
        public String zip;
    }

    public class Point {
        public float x;
        public float y;
    }

}
