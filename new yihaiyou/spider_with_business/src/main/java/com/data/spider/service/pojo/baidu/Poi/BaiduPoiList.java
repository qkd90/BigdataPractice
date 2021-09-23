package com.data.spider.service.pojo.baidu.Poi;

/**
 * Created by Sane on 15/9/17.
 */
public class BaiduPoiList {
    public int errno;
    public String msg;
    public Data data;

    public class Data {
        //        public int pn;
//        public int rn;
//        public int total;
        //        public object[] tags ;
//        public object[] sorts ;
        public Store[] stores;
    }

    public class Store {
        public String poid;
        //        public String place_uid;
        public String name;
        public String pic_url;
        //        public int overall_rating;
        public int price;
        public String reason;
        //        public int remark_count;
//        public int map_remark_count;
//        public int is_rec;
//        public Point point;
        public String[] recommendation;
    }

    public class Point {
        public float x;
        public float y;
    }

}
