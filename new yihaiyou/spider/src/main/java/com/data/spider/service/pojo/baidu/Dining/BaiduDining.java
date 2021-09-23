package com.data.spider.service.pojo.baidu.Dining;

/**
 * Created by Sane on 15/9/16.
 */
public class BaiduDining {


    public int errno;
    public String msg;
    public Data data;

    public class Data {
        public String desc;
        public Food[] food;
        public int has_stores;
    }

    public class Food {
        public String key;
        public String desc;
        public String pic_url;
        public String type_tag;
        public String[] hot_tags;
        public Store[] stores;
    }

    public class Store {
        public String poid;
        public String name;
        public String pic_url;
    }

}
