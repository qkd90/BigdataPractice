package com.data.spider.service.pojo.baidu.Direction;

/**
 * Created by Sane on 15/9/11.
 */
public class BaiduPlace {
    public String name;//poi名称
    public BaiduPlaceLocation location;//poi经纬度坐标
    public String address;//poi地址信息
    public String telephone;//poi电话信息
    public String uid;//poi的唯一标示
    public BaiduPlaceDetailInfo detail_info;//poi的扩展信息，仅当scope=2时，显示该字段，不同的poi类型，显示的detail_info字段不同。
}

