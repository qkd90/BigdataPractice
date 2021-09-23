package com.data.spider.service.pojo.ctrip;

/**
 * Created by Sane on 15/9/28.
 */
public class SearchResult {


    public Head head;
    public Datum[] data;

    public class Head {
        //        public object auth ;
        public int errcode;
    }

    public class Datum {
        public String word;
        public String type;// busstation trainstation airport hotel shop food district
        public String districtname;
        public String url;
        public String price;
        public String star;
        public String zonename;
    }

}
