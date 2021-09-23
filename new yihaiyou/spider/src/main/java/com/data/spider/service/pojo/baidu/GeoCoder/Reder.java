package com.data.spider.service.pojo.baidu.GeoCoder;

/**
 * Created by Sane on 15/9/21.
 */
public class Reder {


    public int status;//返回结果状态值， 成功返回0，其他值请查看下方返回码状态表。
    public Result result;
    public class Result {
        public int precise;//位置的附加信息，是否精确查找。1为精确查找，0为不精确。
        public int confidence;//可信度
        public String level;//地址类型
        public Location location;//经纬度坐标
    }
    public class Location {

        public float lat;
        public float lng;
    }

}
