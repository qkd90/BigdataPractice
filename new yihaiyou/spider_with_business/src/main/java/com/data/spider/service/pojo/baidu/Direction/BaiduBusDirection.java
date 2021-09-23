package com.data.spider.service.pojo.baidu.Direction;

//import org.joda.time.DateTime;

/**
 * Created by Sane on 15/9/11.
 */
public class BaiduBusDirection {

    public int status;
    // public String message ;
    // public int type ;
    // public Info info ;
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
        // public int traffic_condition ;
        // public int error ;
        // public Origin origin ;
        // public Destination destination ;
        // public Taxi taxi ;
    }

    public class Origin {
        public Originpt originPt;
    }

    public class Originpt {
        public float lng;
        public float lat;
    }

    public class Destination {
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
        public Scheme[] scheme;
    }

    public class Scheme {
        // public DateTime arrive_time ;
        public int distance;
        public int duration;
        // public Line_Price[] line_price ;
        // public int plan_trans_type ;
        public int price;
        // public Step[][] steps ;
        // public String tip_label ;
        // public String tip_label_background ;
        // public int tip_label_type ;
        // public int traffic_type ;
        // public Originlocation originLocation ;
        // public Destinationlocation destinationLocation ;
    }

    public class Originlocation {
        public float lng;
        public float lat;
    }

    public class Destinationlocation {
        public float lng;
        public float lat;
    }

    public class Line_Price {
        public int line_price;
        public int line_type;
    }

    public class Step {
        public int distance;
        public int duration;
        // public Object[] pois ;
        public String sname;
        public int type;
        public Vehicle vehicle;
        public Steporiginlocation stepOriginLocation;
        public Stepdestinationlocation stepDestinationLocation;
        public String stepInstruction;
        public String path;
    }

    public class Vehicle {
        public String end_name;
        public String end_time;
        public String end_uid;
        public String name;
        public String start_name;
        public String start_time;
        public String start_uid;
        public int stop_num;
        public int total_price;
        public int type;
        public String uid;
        public int zone_price;
    }

    public class Steporiginlocation {
        public float lng;
        public float lat;
    }

    public class Stepdestinationlocation {
        public float lng;
        public float lat;
    }
}
