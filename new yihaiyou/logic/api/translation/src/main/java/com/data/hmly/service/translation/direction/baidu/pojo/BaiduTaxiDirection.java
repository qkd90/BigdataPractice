package com.data.hmly.service.translation.direction.baidu.pojo;

/**
 * Created by Sane on 15/9/11.
 */
public class BaiduTaxiDirection {


    public int status;
    public String message;
    public int type;
    public Info info;
    public Result result;

    public class Info {
        public Copyright copyright;
    }

    public class Copyright {
        public String text;
        public String imageUrl;
    }

    public class Result {
        public Route[] routes;
        public Origin origin;
        public Destination destination;
        public Taxi taxi;
        public int traffic_condition;
    }

    public class Origin {
        public int area_id;
        public String cname;
        public String uid;
        public String wd;
        public Originpt originPt;
    }

    public class Originpt {
        public float lng;
        public float lat;
    }

    public class Destination {
        public int area_id;
        public String cname;
        public String uid;
        public String wd;
        public Destinationpt destinationPt;
    }

    public class Destinationpt {
        public float lng;
        public float lat;
    }

    public class Taxi {
        public Detail[] detail;
        public int distance;
        public int duration;
        public String remark;
    }

    public class Detail {
        public String desc;
        public String km_price;
        public String start_price;
        public String total_price;
    }

    public class Route {
        public int distance;
        public int duration;
        public Step[] steps;
        public int toll;
        public Originlocation originLocation;
        public Destinationlocation destinationLocation;
    }

    public class Originlocation {
        public float lng;
        public float lat;
    }

    public class Destinationlocation {
        public float lng;
        public float lat;
    }

    public class Step {
        public int area;
        public int direction;
        public int distance;
        public int duration;
        public String instructions;
        public String path;
        public Pois[] pois;
        public int turn;
        public int type;
        public Steporiginlocation stepOriginLocation;
        public Stepdestinationlocation stepDestinationLocation;
        public String stepOriginInstruction;
        public String stepDestinationInstruction;
    }

    public class Steporiginlocation {
        public float lng;
        public float lat;
    }

    public class Stepdestinationlocation {
        public float lng;
        public float lat;
    }

    public class Pois {
        public Detail1 detail;
        public Location location;
        public String name;
        public int type;
    }

    public class Detail1 {
        public String instructions;
        public int position;
    }

    public class Location {
        public float lng;
        public float lat;
    }


}
