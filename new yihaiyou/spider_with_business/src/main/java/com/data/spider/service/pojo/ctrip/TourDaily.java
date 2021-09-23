package com.data.spider.service.pojo.ctrip;

/**
 * Created by Sane on 15/10/26.
 */
public class TourDaily {


    public Responsestatus ResponseStatus;
    public Day[] Days;

    public class Responsestatus {
        //        public DateTime Timestamp ;
        public String Ack;
        //        public object[] Errors ;
        public String Build;
        public String Version;
        public Extension[] Extension;
    }

    public class Extension {
        public String Id;
        public String Value;
    }

    public class Day {
        public int Index;
        public String Title;
        public Tour[] Tours;
    }

    public class Tour {
        public String Start;
        public int Type;
        public String Desc;
        public float Dist;
        public float DTime;
        public float TTime;
        public Scenic[] Scenics;
        public Object[] Hotels;
    }

    public class Scenic {
        public int Id;
        public String Name;
        public String[] Urls;
    }

}
