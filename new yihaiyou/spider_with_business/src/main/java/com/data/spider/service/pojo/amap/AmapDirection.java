package com.data.spider.service.pojo.amap;

import java.util.List;

/**
 * Created by Sane on 15/12/7.
 */
public class AmapDirection {


    /**
     * status : 1
     * info : OK
     * infocode : 1000
     * count : 1
     * route : {"origin":"116.481028,39.989643","destination":"116.434446,39.90816","taxi_cost":"39.76049999999999","paths":[{"distance":"14635","duration":"3120","strategy":"速度最快","tolls":"0","toll_distance":"0","steps":[{"instruction":"向西南行驶53米右转进入主路","orientation":"西南","distance":"53","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.481216,39.989532;116.48101,39.989315;116.480934,39.989254;116.480904,39.98922","action":"右转","assistant_action":"进入主路","tmcs":[{"lcode":[],"distance":"31","status":"未知"},{"lcode":[],"distance":"9","status":"未知"},{"lcode":[],"distance":"13","status":"未知"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿阜荣街向西北行驶111米右转","orientation":"西北","road":"阜荣街","distance":"111","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.480843,39.989159;116.479965,39.989735;116.479843,39.989819","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"98","status":"缓行"},{"lcode":[],"distance":"13","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿阜通东大街向东北行驶379米右转","orientation":"东北","road":"阜通东大街","distance":"379","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.479843,39.989819;116.480019,39.989971;116.481041,39.99091;116.481125,39.99099;116.482689,39.992451","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"22","status":"未知"},{"lcode":[],"distance":"136","status":"未知"},{"lcode":[],"distance":"10","status":"畅通"},{"lcode":[],"distance":"211","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿望京街向东南行驶596米左转","orientation":"东南","road":"望京街","distance":"596","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.482689,39.992451;116.484474,39.991283;116.486122,39.9902;116.486404,39.990017;116.486496,39.989952;116.487831,39.989067","action":"左转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"199","status":"缓行"},{"lcode":[],"distance":"185","status":"缓行"},{"lcode":[],"distance":"41","status":"严重拥堵"},{"lcode":[],"distance":"171","status":"严重拥堵"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿京密路向东北行驶360米向右前方行驶进入匝道","orientation":"东北","road":"京密路","distance":"360","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.488052,39.989029;116.489632,39.990448;116.49041,39.991173;116.490784,39.991512","action":"向右前方行驶","assistant_action":"进入匝道","tmcs":[{"lcode":[],"distance":"360","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿S12机场高速入口途径S12机场高速行驶5.6千米靠左","orientation":[],"road":"S12机场高速入口","distance":"5599","tolls":"0","toll_distance":"0","toll_road":[],"duration":"240","polyline":"116.490784,39.991512;116.491035,39.991688;116.491165,39.991741;116.49128,39.991749;116.491348,39.991741;116.491409,39.991695;116.49144,39.991661;116.491455,39.991631;116.491508,39.991528;116.491531,39.991371;116.490555,39.990482;116.487419,39.987629;116.485825,39.986214;116.485535,39.985954;116.483879,39.984459;116.482697,39.983356;116.47953,39.980503;116.478012,39.979149;116.475731,39.977077;116.474899,39.976334;116.474648,39.976101;116.47403,39.975555;116.472855,39.974487;116.470161,39.972065;116.468857,39.970879;116.468109,39.9702;116.465714,39.96806;116.46212,39.964867;116.460564,39.963436;116.459602,39.962585;116.459068,39.962112;116.458366,39.961475;116.456993,39.960243;116.456322,39.959625;116.454788,39.958241;116.453979,39.957504;116.453514,39.957088;116.452888,39.95652;116.452644,39.956303;116.451775,39.955517;116.451241,39.955025;116.450882,39.954712;116.449722,39.953632","action":"靠左","assistant_action":[],"tmcs":[{"lcode":[],"distance":"103","status":"畅通"},{"lcode":[],"distance":"543","status":"畅通"},{"lcode":[],"distance":"245","status":"缓行"},{"lcode":[],"distance":"794","status":"缓行"},{"lcode":[],"distance":"197","status":"畅通"},{"lcode":[],"distance":"411","status":"畅通"},{"lcode":[],"distance":"33","status":"畅通"},{"lcode":[],"distance":"589","status":"畅通"},{"lcode":[],"distance":"173","status":"畅通"},{"lcode":[],"distance":"412","status":"畅通"},{"lcode":[],"distance":"962","status":"畅通"},{"lcode":[],"distance":"180","status":"畅通"},{"lcode":[],"distance":"89","status":"畅通"},{"lcode":[],"distance":"201","status":"畅通"},{"lcode":[],"distance":"107","status":"缓行"},{"lcode":[],"distance":"60","status":"缓行"},{"lcode":[],"distance":"82","status":"缓行"},{"lcode":[],"distance":"32","status":"畅通"},{"lcode":[],"distance":"114","status":"畅通"},{"lcode":[],"distance":"70","status":"畅通"},{"lcode":[],"distance":"202","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿S12机场高速向西行驶1.4千米靠右","orientation":"西","road":"S12机场高速","distance":"1399","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.449715,39.953629;116.44899,39.952969;116.448296,39.952332;116.446495,39.950699;116.446213,39.950474;116.446022,39.95034;116.445831,39.950207;116.445679,39.950127;116.445465,39.950031;116.445244,39.949944;116.444847,39.949818;116.444603,39.949757;116.44426,39.949711;116.443977,39.949692;116.442795,39.949665;116.440277,39.949657;116.439774,39.949631;116.439659,39.949631;116.439255,39.949631;116.436806,39.949646;116.435501,39.949627","action":"靠右","assistant_action":[],"tmcs":[{"lcode":[],"distance":"96","status":"畅通"},{"lcode":[],"distance":"387","status":"畅通"},{"lcode":[],"distance":"168","status":"畅通"},{"lcode":[],"distance":"341","status":"缓行"},{"lcode":[],"distance":"43","status":"缓行"},{"lcode":[],"distance":"9","status":"缓行"},{"lcode":[],"distance":"34","status":"缓行"},{"lcode":[],"distance":"210","status":"缓行"},{"lcode":[],"distance":"111","status":"缓行"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"},{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿东直门北桥行驶817米靠左","orientation":[],"road":"东直门北桥","distance":"817","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.435501,39.949623;116.435143,39.949657;116.434944,39.949684;116.434738,39.949734;116.434502,39.949821;116.434395,39.949883;116.43412,39.950066;116.433884,39.950214;116.433647,39.95031;116.433479,39.950344;116.433334,39.950352;116.433197,39.950348;116.433006,39.950279;116.432899,39.950218;116.432846,39.950176;116.432716,39.95005;116.432625,39.949909;116.432571,39.949795;116.432518,39.949604;116.432518,39.949512;116.432533,39.949409;116.432579,39.949257;116.43264,39.949131;116.432709,39.949032;116.432823,39.948891;116.432961,39.948753;116.433105,39.948589;116.433189,39.948475;116.433258,39.948372;116.433365,39.948128;116.433464,39.94783;116.433525,39.947552;116.43354,39.947414;116.433571,39.947189;116.433571,39.946938;116.433548,39.945343","action":"靠左","assistant_action":[],"tmcs":[{"lcode":[],"distance":"254","status":"畅通"},{"lcode":[],"distance":"387","status":"畅通"},{"lcode":[],"distance":"176","status":"缓行"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"},{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿东直门北桥途径东二环行驶4.4千米右转进入右岔路","orientation":[],"road":"东直门北桥","distance":"4412","tolls":"0","toll_distance":"0","toll_road":[],"duration":"300","polyline":"116.43354,39.945339;116.433571,39.944988;116.433594,39.944904;116.43364,39.944653;116.433632,39.943066;116.43364,39.942661;116.433746,39.940208;116.43383,39.938255;116.433853,39.937458;116.433907,39.936306;116.433952,39.935635;116.434044,39.934086;116.43409,39.932316;116.434135,39.93145;116.434151,39.931076;116.434166,39.93034;116.434219,39.929535;116.434235,39.928841;116.434265,39.928303;116.434334,39.926922;116.434525,39.922344;116.434547,39.921658;116.434578,39.921089;116.434616,39.920303;116.434654,39.91946;116.434685,39.918919;116.434715,39.917988;116.434723,39.917767;116.434738,39.917248;116.434769,39.916824;116.434784,39.916447;116.434845,39.915108;116.434891,39.914349;116.434906,39.91423;116.434929,39.913906;116.434959,39.913696;116.435005,39.913429;116.43531,39.912071;116.435356,39.911892;116.435402,39.911713;116.435501,39.911263;116.435555,39.910973;116.435593,39.910679;116.435646,39.91008;116.435646,39.909718;116.435745,39.907413;116.435799,39.906502;116.435837,39.906116;116.43586,39.905941;116.435913,39.905552","action":"右转","assistant_action":"进入右岔路","tmcs":[{"lcode":[],"distance":"77","status":"缓行"},{"lcode":[],"distance":"710","status":"拥堵"},{"lcode":[],"distance":"88","status":"拥堵"},{"lcode":[],"distance":"127","status":"拥堵"},{"lcode":[],"distance":"74","status":"拥堵"},{"lcode":[],"distance":"465","status":"拥堵"},{"lcode":[],"distance":"41","status":"拥堵"},{"lcode":[],"distance":"81","status":"拥堵"},{"lcode":[],"distance":"89","status":"拥堵"},{"lcode":[],"distance":"76","status":"拥堵"},{"lcode":[],"distance":"60","status":"拥堵"},{"lcode":[],"distance":"153","status":"拥堵"},{"lcode":[],"distance":"508","status":"拥堵"},{"lcode":[],"distance":"76","status":"拥堵"},{"lcode":[],"distance":"63","status":"拥堵"},{"lcode":[],"distance":"86","status":"拥堵"},{"lcode":[],"distance":"93","status":"拥堵"},{"lcode":[],"distance":"164","status":"拥堵"},{"lcode":[],"distance":"24","status":"拥堵"},{"lcode":[],"distance":"57","status":"拥堵"},{"lcode":[],"distance":"47","status":"拥堵"},{"lcode":[],"distance":"41","status":"拥堵"},{"lcode":[],"distance":"233","status":"拥堵"},{"lcode":[],"distance":"48","status":"拥堵"},{"lcode":[],"distance":"206","status":"拥堵"},{"lcode":[],"distance":"40","status":"拥堵"},{"lcode":[],"distance":"83","status":"拥堵"},{"lcode":[],"distance":"139","status":"拥堵"},{"lcode":[],"distance":"256","status":"拥堵"},{"lcode":[],"distance":"144","status":"拥堵"},{"lcode":[],"distance":"19","status":"拥堵"},{"lcode":[],"distance":"44","status":"拥堵"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿东二环出口行驶37米右转","orientation":[],"road":"东二环出口","distance":"37","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.435913,39.905548;116.435837,39.905357;116.435806,39.905312;116.435738,39.905247","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"37","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿北京站东街向西行驶286米右转","orientation":"西","road":"北京站东街","distance":"286","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.43573,39.905243;116.435677,39.905212;116.435493,39.90519;116.434807,39.90519;116.43351,39.905182;116.433334,39.905178;116.432381,39.905136","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"22","status":"畅通"},{"lcode":[],"distance":"58","status":"畅通"},{"lcode":[],"distance":"111","status":"畅通"},{"lcode":[],"distance":"14","status":"畅通"},{"lcode":[],"distance":"81","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿大羊毛胡同向北行驶398米右转","orientation":"北","road":"大羊毛胡同","distance":"398","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.432381,39.905128;116.43235,39.905254;116.432343,39.905323;116.432335,39.905407;116.432327,39.905941;116.432312,39.906651;116.432289,39.906876;116.432274,39.907551;116.432259,39.907864;116.432259,39.908455","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"90","status":"畅通"},{"lcode":[],"distance":"78","status":"畅通"},{"lcode":[],"distance":"25","status":"畅通"},{"lcode":[],"distance":"75","status":"畅通"},{"lcode":[],"distance":"130","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"向东行驶30米向右前方行驶进入辅路","orientation":"东","distance":"30","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.432251,39.908455;116.432602,39.908463","action":"向右前方行驶","assistant_action":"进入辅路","tmcs":[{"lcode":[],"distance":"2","status":"未知"}],"cities":[]},{"instruction":"沿建国门内大街向东行驶158米到达目的地","orientation":"东","road":"建国门内大街","distance":"158","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.432602,39.908463;116.432938,39.908363;116.434448,39.908382","action":[],"assistant_action":"到达目的地","tmcs":[{"lcode":[],"distance":"158","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]}]}]}
     */

    private String status;
    private String info;
    private String infocode;
    private String count;
    /**
     * origin : 116.481028,39.989643
     * destination : 116.434446,39.90816
     * taxi_cost : 39.76049999999999
     * paths : [{"distance":"14635","duration":"3120","strategy":"速度最快","tolls":"0","toll_distance":"0","steps":[{"instruction":"向西南行驶53米右转进入主路","orientation":"西南","distance":"53","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.481216,39.989532;116.48101,39.989315;116.480934,39.989254;116.480904,39.98922","action":"右转","assistant_action":"进入主路","tmcs":[{"lcode":[],"distance":"31","status":"未知"},{"lcode":[],"distance":"9","status":"未知"},{"lcode":[],"distance":"13","status":"未知"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿阜荣街向西北行驶111米右转","orientation":"西北","road":"阜荣街","distance":"111","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.480843,39.989159;116.479965,39.989735;116.479843,39.989819","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"98","status":"缓行"},{"lcode":[],"distance":"13","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿阜通东大街向东北行驶379米右转","orientation":"东北","road":"阜通东大街","distance":"379","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.479843,39.989819;116.480019,39.989971;116.481041,39.99091;116.481125,39.99099;116.482689,39.992451","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"22","status":"未知"},{"lcode":[],"distance":"136","status":"未知"},{"lcode":[],"distance":"10","status":"畅通"},{"lcode":[],"distance":"211","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿望京街向东南行驶596米左转","orientation":"东南","road":"望京街","distance":"596","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.482689,39.992451;116.484474,39.991283;116.486122,39.9902;116.486404,39.990017;116.486496,39.989952;116.487831,39.989067","action":"左转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"199","status":"缓行"},{"lcode":[],"distance":"185","status":"缓行"},{"lcode":[],"distance":"41","status":"严重拥堵"},{"lcode":[],"distance":"171","status":"严重拥堵"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿京密路向东北行驶360米向右前方行驶进入匝道","orientation":"东北","road":"京密路","distance":"360","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.488052,39.989029;116.489632,39.990448;116.49041,39.991173;116.490784,39.991512","action":"向右前方行驶","assistant_action":"进入匝道","tmcs":[{"lcode":[],"distance":"360","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿S12机场高速入口途径S12机场高速行驶5.6千米靠左","orientation":[],"road":"S12机场高速入口","distance":"5599","tolls":"0","toll_distance":"0","toll_road":[],"duration":"240","polyline":"116.490784,39.991512;116.491035,39.991688;116.491165,39.991741;116.49128,39.991749;116.491348,39.991741;116.491409,39.991695;116.49144,39.991661;116.491455,39.991631;116.491508,39.991528;116.491531,39.991371;116.490555,39.990482;116.487419,39.987629;116.485825,39.986214;116.485535,39.985954;116.483879,39.984459;116.482697,39.983356;116.47953,39.980503;116.478012,39.979149;116.475731,39.977077;116.474899,39.976334;116.474648,39.976101;116.47403,39.975555;116.472855,39.974487;116.470161,39.972065;116.468857,39.970879;116.468109,39.9702;116.465714,39.96806;116.46212,39.964867;116.460564,39.963436;116.459602,39.962585;116.459068,39.962112;116.458366,39.961475;116.456993,39.960243;116.456322,39.959625;116.454788,39.958241;116.453979,39.957504;116.453514,39.957088;116.452888,39.95652;116.452644,39.956303;116.451775,39.955517;116.451241,39.955025;116.450882,39.954712;116.449722,39.953632","action":"靠左","assistant_action":[],"tmcs":[{"lcode":[],"distance":"103","status":"畅通"},{"lcode":[],"distance":"543","status":"畅通"},{"lcode":[],"distance":"245","status":"缓行"},{"lcode":[],"distance":"794","status":"缓行"},{"lcode":[],"distance":"197","status":"畅通"},{"lcode":[],"distance":"411","status":"畅通"},{"lcode":[],"distance":"33","status":"畅通"},{"lcode":[],"distance":"589","status":"畅通"},{"lcode":[],"distance":"173","status":"畅通"},{"lcode":[],"distance":"412","status":"畅通"},{"lcode":[],"distance":"962","status":"畅通"},{"lcode":[],"distance":"180","status":"畅通"},{"lcode":[],"distance":"89","status":"畅通"},{"lcode":[],"distance":"201","status":"畅通"},{"lcode":[],"distance":"107","status":"缓行"},{"lcode":[],"distance":"60","status":"缓行"},{"lcode":[],"distance":"82","status":"缓行"},{"lcode":[],"distance":"32","status":"畅通"},{"lcode":[],"distance":"114","status":"畅通"},{"lcode":[],"distance":"70","status":"畅通"},{"lcode":[],"distance":"202","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿S12机场高速向西行驶1.4千米靠右","orientation":"西","road":"S12机场高速","distance":"1399","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.449715,39.953629;116.44899,39.952969;116.448296,39.952332;116.446495,39.950699;116.446213,39.950474;116.446022,39.95034;116.445831,39.950207;116.445679,39.950127;116.445465,39.950031;116.445244,39.949944;116.444847,39.949818;116.444603,39.949757;116.44426,39.949711;116.443977,39.949692;116.442795,39.949665;116.440277,39.949657;116.439774,39.949631;116.439659,39.949631;116.439255,39.949631;116.436806,39.949646;116.435501,39.949627","action":"靠右","assistant_action":[],"tmcs":[{"lcode":[],"distance":"96","status":"畅通"},{"lcode":[],"distance":"387","status":"畅通"},{"lcode":[],"distance":"168","status":"畅通"},{"lcode":[],"distance":"341","status":"缓行"},{"lcode":[],"distance":"43","status":"缓行"},{"lcode":[],"distance":"9","status":"缓行"},{"lcode":[],"distance":"34","status":"缓行"},{"lcode":[],"distance":"210","status":"缓行"},{"lcode":[],"distance":"111","status":"缓行"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"},{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿东直门北桥行驶817米靠左","orientation":[],"road":"东直门北桥","distance":"817","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.435501,39.949623;116.435143,39.949657;116.434944,39.949684;116.434738,39.949734;116.434502,39.949821;116.434395,39.949883;116.43412,39.950066;116.433884,39.950214;116.433647,39.95031;116.433479,39.950344;116.433334,39.950352;116.433197,39.950348;116.433006,39.950279;116.432899,39.950218;116.432846,39.950176;116.432716,39.95005;116.432625,39.949909;116.432571,39.949795;116.432518,39.949604;116.432518,39.949512;116.432533,39.949409;116.432579,39.949257;116.43264,39.949131;116.432709,39.949032;116.432823,39.948891;116.432961,39.948753;116.433105,39.948589;116.433189,39.948475;116.433258,39.948372;116.433365,39.948128;116.433464,39.94783;116.433525,39.947552;116.43354,39.947414;116.433571,39.947189;116.433571,39.946938;116.433548,39.945343","action":"靠左","assistant_action":[],"tmcs":[{"lcode":[],"distance":"254","status":"畅通"},{"lcode":[],"distance":"387","status":"畅通"},{"lcode":[],"distance":"176","status":"缓行"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"},{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿东直门北桥途径东二环行驶4.4千米右转进入右岔路","orientation":[],"road":"东直门北桥","distance":"4412","tolls":"0","toll_distance":"0","toll_road":[],"duration":"300","polyline":"116.43354,39.945339;116.433571,39.944988;116.433594,39.944904;116.43364,39.944653;116.433632,39.943066;116.43364,39.942661;116.433746,39.940208;116.43383,39.938255;116.433853,39.937458;116.433907,39.936306;116.433952,39.935635;116.434044,39.934086;116.43409,39.932316;116.434135,39.93145;116.434151,39.931076;116.434166,39.93034;116.434219,39.929535;116.434235,39.928841;116.434265,39.928303;116.434334,39.926922;116.434525,39.922344;116.434547,39.921658;116.434578,39.921089;116.434616,39.920303;116.434654,39.91946;116.434685,39.918919;116.434715,39.917988;116.434723,39.917767;116.434738,39.917248;116.434769,39.916824;116.434784,39.916447;116.434845,39.915108;116.434891,39.914349;116.434906,39.91423;116.434929,39.913906;116.434959,39.913696;116.435005,39.913429;116.43531,39.912071;116.435356,39.911892;116.435402,39.911713;116.435501,39.911263;116.435555,39.910973;116.435593,39.910679;116.435646,39.91008;116.435646,39.909718;116.435745,39.907413;116.435799,39.906502;116.435837,39.906116;116.43586,39.905941;116.435913,39.905552","action":"右转","assistant_action":"进入右岔路","tmcs":[{"lcode":[],"distance":"77","status":"缓行"},{"lcode":[],"distance":"710","status":"拥堵"},{"lcode":[],"distance":"88","status":"拥堵"},{"lcode":[],"distance":"127","status":"拥堵"},{"lcode":[],"distance":"74","status":"拥堵"},{"lcode":[],"distance":"465","status":"拥堵"},{"lcode":[],"distance":"41","status":"拥堵"},{"lcode":[],"distance":"81","status":"拥堵"},{"lcode":[],"distance":"89","status":"拥堵"},{"lcode":[],"distance":"76","status":"拥堵"},{"lcode":[],"distance":"60","status":"拥堵"},{"lcode":[],"distance":"153","status":"拥堵"},{"lcode":[],"distance":"508","status":"拥堵"},{"lcode":[],"distance":"76","status":"拥堵"},{"lcode":[],"distance":"63","status":"拥堵"},{"lcode":[],"distance":"86","status":"拥堵"},{"lcode":[],"distance":"93","status":"拥堵"},{"lcode":[],"distance":"164","status":"拥堵"},{"lcode":[],"distance":"24","status":"拥堵"},{"lcode":[],"distance":"57","status":"拥堵"},{"lcode":[],"distance":"47","status":"拥堵"},{"lcode":[],"distance":"41","status":"拥堵"},{"lcode":[],"distance":"233","status":"拥堵"},{"lcode":[],"distance":"48","status":"拥堵"},{"lcode":[],"distance":"206","status":"拥堵"},{"lcode":[],"distance":"40","status":"拥堵"},{"lcode":[],"distance":"83","status":"拥堵"},{"lcode":[],"distance":"139","status":"拥堵"},{"lcode":[],"distance":"256","status":"拥堵"},{"lcode":[],"distance":"144","status":"拥堵"},{"lcode":[],"distance":"19","status":"拥堵"},{"lcode":[],"distance":"44","status":"拥堵"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿东二环出口行驶37米右转","orientation":[],"road":"东二环出口","distance":"37","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.435913,39.905548;116.435837,39.905357;116.435806,39.905312;116.435738,39.905247","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"37","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿北京站东街向西行驶286米右转","orientation":"西","road":"北京站东街","distance":"286","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.43573,39.905243;116.435677,39.905212;116.435493,39.90519;116.434807,39.90519;116.43351,39.905182;116.433334,39.905178;116.432381,39.905136","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"22","status":"畅通"},{"lcode":[],"distance":"58","status":"畅通"},{"lcode":[],"distance":"111","status":"畅通"},{"lcode":[],"distance":"14","status":"畅通"},{"lcode":[],"distance":"81","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿大羊毛胡同向北行驶398米右转","orientation":"北","road":"大羊毛胡同","distance":"398","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.432381,39.905128;116.43235,39.905254;116.432343,39.905323;116.432335,39.905407;116.432327,39.905941;116.432312,39.906651;116.432289,39.906876;116.432274,39.907551;116.432259,39.907864;116.432259,39.908455","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"90","status":"畅通"},{"lcode":[],"distance":"78","status":"畅通"},{"lcode":[],"distance":"25","status":"畅通"},{"lcode":[],"distance":"75","status":"畅通"},{"lcode":[],"distance":"130","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"向东行驶30米向右前方行驶进入辅路","orientation":"东","distance":"30","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.432251,39.908455;116.432602,39.908463","action":"向右前方行驶","assistant_action":"进入辅路","tmcs":[{"lcode":[],"distance":"2","status":"未知"}],"cities":[]},{"instruction":"沿建国门内大街向东行驶158米到达目的地","orientation":"东","road":"建国门内大街","distance":"158","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.432602,39.908463;116.432938,39.908363;116.434448,39.908382","action":[],"assistant_action":"到达目的地","tmcs":[{"lcode":[],"distance":"158","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]}]}]
     */

    private RouteEntity route;

    public void setStatus(String status) {
        this.status = status;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public void setRoute(RouteEntity route) {
        this.route = route;
    }

    public String getStatus() {
        return status;
    }

    public String getInfo() {
        return info;
    }

    public String getInfocode() {
        return infocode;
    }

    public String getCount() {
        return count;
    }

    public RouteEntity getRoute() {
        return route;
    }

    public static class RouteEntity {
        private String origin;
        private String destination;
        private String taxi_cost;
        /**
         * distance : 14635
         * duration : 3120
         * strategy : 速度最快
         * tolls : 0
         * toll_distance : 0
         * steps : [{"instruction":"向西南行驶53米右转进入主路","orientation":"西南","distance":"53","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.481216,39.989532;116.48101,39.989315;116.480934,39.989254;116.480904,39.98922","action":"右转","assistant_action":"进入主路","tmcs":[{"lcode":[],"distance":"31","status":"未知"},{"lcode":[],"distance":"9","status":"未知"},{"lcode":[],"distance":"13","status":"未知"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿阜荣街向西北行驶111米右转","orientation":"西北","road":"阜荣街","distance":"111","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.480843,39.989159;116.479965,39.989735;116.479843,39.989819","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"98","status":"缓行"},{"lcode":[],"distance":"13","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿阜通东大街向东北行驶379米右转","orientation":"东北","road":"阜通东大街","distance":"379","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.479843,39.989819;116.480019,39.989971;116.481041,39.99091;116.481125,39.99099;116.482689,39.992451","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"22","status":"未知"},{"lcode":[],"distance":"136","status":"未知"},{"lcode":[],"distance":"10","status":"畅通"},{"lcode":[],"distance":"211","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿望京街向东南行驶596米左转","orientation":"东南","road":"望京街","distance":"596","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.482689,39.992451;116.484474,39.991283;116.486122,39.9902;116.486404,39.990017;116.486496,39.989952;116.487831,39.989067","action":"左转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"199","status":"缓行"},{"lcode":[],"distance":"185","status":"缓行"},{"lcode":[],"distance":"41","status":"严重拥堵"},{"lcode":[],"distance":"171","status":"严重拥堵"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿京密路向东北行驶360米向右前方行驶进入匝道","orientation":"东北","road":"京密路","distance":"360","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.488052,39.989029;116.489632,39.990448;116.49041,39.991173;116.490784,39.991512","action":"向右前方行驶","assistant_action":"进入匝道","tmcs":[{"lcode":[],"distance":"360","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿S12机场高速入口途径S12机场高速行驶5.6千米靠左","orientation":[],"road":"S12机场高速入口","distance":"5599","tolls":"0","toll_distance":"0","toll_road":[],"duration":"240","polyline":"116.490784,39.991512;116.491035,39.991688;116.491165,39.991741;116.49128,39.991749;116.491348,39.991741;116.491409,39.991695;116.49144,39.991661;116.491455,39.991631;116.491508,39.991528;116.491531,39.991371;116.490555,39.990482;116.487419,39.987629;116.485825,39.986214;116.485535,39.985954;116.483879,39.984459;116.482697,39.983356;116.47953,39.980503;116.478012,39.979149;116.475731,39.977077;116.474899,39.976334;116.474648,39.976101;116.47403,39.975555;116.472855,39.974487;116.470161,39.972065;116.468857,39.970879;116.468109,39.9702;116.465714,39.96806;116.46212,39.964867;116.460564,39.963436;116.459602,39.962585;116.459068,39.962112;116.458366,39.961475;116.456993,39.960243;116.456322,39.959625;116.454788,39.958241;116.453979,39.957504;116.453514,39.957088;116.452888,39.95652;116.452644,39.956303;116.451775,39.955517;116.451241,39.955025;116.450882,39.954712;116.449722,39.953632","action":"靠左","assistant_action":[],"tmcs":[{"lcode":[],"distance":"103","status":"畅通"},{"lcode":[],"distance":"543","status":"畅通"},{"lcode":[],"distance":"245","status":"缓行"},{"lcode":[],"distance":"794","status":"缓行"},{"lcode":[],"distance":"197","status":"畅通"},{"lcode":[],"distance":"411","status":"畅通"},{"lcode":[],"distance":"33","status":"畅通"},{"lcode":[],"distance":"589","status":"畅通"},{"lcode":[],"distance":"173","status":"畅通"},{"lcode":[],"distance":"412","status":"畅通"},{"lcode":[],"distance":"962","status":"畅通"},{"lcode":[],"distance":"180","status":"畅通"},{"lcode":[],"distance":"89","status":"畅通"},{"lcode":[],"distance":"201","status":"畅通"},{"lcode":[],"distance":"107","status":"缓行"},{"lcode":[],"distance":"60","status":"缓行"},{"lcode":[],"distance":"82","status":"缓行"},{"lcode":[],"distance":"32","status":"畅通"},{"lcode":[],"distance":"114","status":"畅通"},{"lcode":[],"distance":"70","status":"畅通"},{"lcode":[],"distance":"202","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]},{"instruction":"沿S12机场高速向西行驶1.4千米靠右","orientation":"西","road":"S12机场高速","distance":"1399","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.449715,39.953629;116.44899,39.952969;116.448296,39.952332;116.446495,39.950699;116.446213,39.950474;116.446022,39.95034;116.445831,39.950207;116.445679,39.950127;116.445465,39.950031;116.445244,39.949944;116.444847,39.949818;116.444603,39.949757;116.44426,39.949711;116.443977,39.949692;116.442795,39.949665;116.440277,39.949657;116.439774,39.949631;116.439659,39.949631;116.439255,39.949631;116.436806,39.949646;116.435501,39.949627","action":"靠右","assistant_action":[],"tmcs":[{"lcode":[],"distance":"96","status":"畅通"},{"lcode":[],"distance":"387","status":"畅通"},{"lcode":[],"distance":"168","status":"畅通"},{"lcode":[],"distance":"341","status":"缓行"},{"lcode":[],"distance":"43","status":"缓行"},{"lcode":[],"distance":"9","status":"缓行"},{"lcode":[],"distance":"34","status":"缓行"},{"lcode":[],"distance":"210","status":"缓行"},{"lcode":[],"distance":"111","status":"缓行"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"},{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿东直门北桥行驶817米靠左","orientation":[],"road":"东直门北桥","distance":"817","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.435501,39.949623;116.435143,39.949657;116.434944,39.949684;116.434738,39.949734;116.434502,39.949821;116.434395,39.949883;116.43412,39.950066;116.433884,39.950214;116.433647,39.95031;116.433479,39.950344;116.433334,39.950352;116.433197,39.950348;116.433006,39.950279;116.432899,39.950218;116.432846,39.950176;116.432716,39.95005;116.432625,39.949909;116.432571,39.949795;116.432518,39.949604;116.432518,39.949512;116.432533,39.949409;116.432579,39.949257;116.43264,39.949131;116.432709,39.949032;116.432823,39.948891;116.432961,39.948753;116.433105,39.948589;116.433189,39.948475;116.433258,39.948372;116.433365,39.948128;116.433464,39.94783;116.433525,39.947552;116.43354,39.947414;116.433571,39.947189;116.433571,39.946938;116.433548,39.945343","action":"靠左","assistant_action":[],"tmcs":[{"lcode":[],"distance":"254","status":"畅通"},{"lcode":[],"distance":"387","status":"畅通"},{"lcode":[],"distance":"176","status":"缓行"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"},{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿东直门北桥途径东二环行驶4.4千米右转进入右岔路","orientation":[],"road":"东直门北桥","distance":"4412","tolls":"0","toll_distance":"0","toll_road":[],"duration":"300","polyline":"116.43354,39.945339;116.433571,39.944988;116.433594,39.944904;116.43364,39.944653;116.433632,39.943066;116.43364,39.942661;116.433746,39.940208;116.43383,39.938255;116.433853,39.937458;116.433907,39.936306;116.433952,39.935635;116.434044,39.934086;116.43409,39.932316;116.434135,39.93145;116.434151,39.931076;116.434166,39.93034;116.434219,39.929535;116.434235,39.928841;116.434265,39.928303;116.434334,39.926922;116.434525,39.922344;116.434547,39.921658;116.434578,39.921089;116.434616,39.920303;116.434654,39.91946;116.434685,39.918919;116.434715,39.917988;116.434723,39.917767;116.434738,39.917248;116.434769,39.916824;116.434784,39.916447;116.434845,39.915108;116.434891,39.914349;116.434906,39.91423;116.434929,39.913906;116.434959,39.913696;116.435005,39.913429;116.43531,39.912071;116.435356,39.911892;116.435402,39.911713;116.435501,39.911263;116.435555,39.910973;116.435593,39.910679;116.435646,39.91008;116.435646,39.909718;116.435745,39.907413;116.435799,39.906502;116.435837,39.906116;116.43586,39.905941;116.435913,39.905552","action":"右转","assistant_action":"进入右岔路","tmcs":[{"lcode":[],"distance":"77","status":"缓行"},{"lcode":[],"distance":"710","status":"拥堵"},{"lcode":[],"distance":"88","status":"拥堵"},{"lcode":[],"distance":"127","status":"拥堵"},{"lcode":[],"distance":"74","status":"拥堵"},{"lcode":[],"distance":"465","status":"拥堵"},{"lcode":[],"distance":"41","status":"拥堵"},{"lcode":[],"distance":"81","status":"拥堵"},{"lcode":[],"distance":"89","status":"拥堵"},{"lcode":[],"distance":"76","status":"拥堵"},{"lcode":[],"distance":"60","status":"拥堵"},{"lcode":[],"distance":"153","status":"拥堵"},{"lcode":[],"distance":"508","status":"拥堵"},{"lcode":[],"distance":"76","status":"拥堵"},{"lcode":[],"distance":"63","status":"拥堵"},{"lcode":[],"distance":"86","status":"拥堵"},{"lcode":[],"distance":"93","status":"拥堵"},{"lcode":[],"distance":"164","status":"拥堵"},{"lcode":[],"distance":"24","status":"拥堵"},{"lcode":[],"distance":"57","status":"拥堵"},{"lcode":[],"distance":"47","status":"拥堵"},{"lcode":[],"distance":"41","status":"拥堵"},{"lcode":[],"distance":"233","status":"拥堵"},{"lcode":[],"distance":"48","status":"拥堵"},{"lcode":[],"distance":"206","status":"拥堵"},{"lcode":[],"distance":"40","status":"拥堵"},{"lcode":[],"distance":"83","status":"拥堵"},{"lcode":[],"distance":"139","status":"拥堵"},{"lcode":[],"distance":"256","status":"拥堵"},{"lcode":[],"distance":"144","status":"拥堵"},{"lcode":[],"distance":"19","status":"拥堵"},{"lcode":[],"distance":"44","status":"拥堵"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿东二环出口行驶37米右转","orientation":[],"road":"东二环出口","distance":"37","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.435913,39.905548;116.435837,39.905357;116.435806,39.905312;116.435738,39.905247","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"37","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿北京站东街向西行驶286米右转","orientation":"西","road":"北京站东街","distance":"286","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.43573,39.905243;116.435677,39.905212;116.435493,39.90519;116.434807,39.90519;116.43351,39.905182;116.433334,39.905178;116.432381,39.905136","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"22","status":"畅通"},{"lcode":[],"distance":"58","status":"畅通"},{"lcode":[],"distance":"111","status":"畅通"},{"lcode":[],"distance":"14","status":"畅通"},{"lcode":[],"distance":"81","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿大羊毛胡同向北行驶398米右转","orientation":"北","road":"大羊毛胡同","distance":"398","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.432381,39.905128;116.43235,39.905254;116.432343,39.905323;116.432335,39.905407;116.432327,39.905941;116.432312,39.906651;116.432289,39.906876;116.432274,39.907551;116.432259,39.907864;116.432259,39.908455","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"90","status":"畅通"},{"lcode":[],"distance":"78","status":"畅通"},{"lcode":[],"distance":"25","status":"畅通"},{"lcode":[],"distance":"75","status":"畅通"},{"lcode":[],"distance":"130","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"向东行驶30米向右前方行驶进入辅路","orientation":"东","distance":"30","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.432251,39.908455;116.432602,39.908463","action":"向右前方行驶","assistant_action":"进入辅路","tmcs":[{"lcode":[],"distance":"2","status":"未知"}],"cities":[]},{"instruction":"沿建国门内大街向东行驶158米到达目的地","orientation":"东","road":"建国门内大街","distance":"158","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.432602,39.908463;116.432938,39.908363;116.434448,39.908382","action":[],"assistant_action":"到达目的地","tmcs":[{"lcode":[],"distance":"158","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]}]
         */

        private List<PathsEntity> paths;

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public void setTaxi_cost(String taxi_cost) {
            this.taxi_cost = taxi_cost;
        }

        public void setPaths(List<PathsEntity> paths) {
            this.paths = paths;
        }

        public String getOrigin() {
            return origin;
        }

        public String getDestination() {
            return destination;
        }

        public String getTaxi_cost() {
            return taxi_cost;
        }

        public List<PathsEntity> getPaths() {
            return paths;
        }

        public static class PathsEntity {
            private String distance;
            private String duration;
            private String strategy;
            private String tolls;
            private String toll_distance;

            /**
             * instruction : 向西南行驶53米右转进入主路
             * orientation : 西南
             * distance : 53
             * tolls : 0
             * toll_distance : 0
             * toll_road : []
             * duration : 60
             * polyline : 116.481216,39.989532;116.48101,39.989315;116.480934,39.989254;116.480904,39.98922
             * action : 右转
             * assistant_action : 进入主路
             * tmcs : [{"lcode":[],"distance":"31","status":"未知"},{"lcode":[],"distance":"9","status":"未知"},{"lcode":[],"distance":"13","status":"未知"}]
             * cities : [{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"朝阳区","adcode":"110105"}]}]
             */

//            private List<StepsEntity> steps;
            public void setDistance(String distance) {
                this.distance = distance;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public void setStrategy(String strategy) {
                this.strategy = strategy;
            }

            public void setTolls(String tolls) {
                this.tolls = tolls;
            }

            public void setToll_distance(String toll_distance) {
                this.toll_distance = toll_distance;
            }

//            public void setSteps(List<StepsEntity> steps) {
//                this.steps = steps;
//            }

            public String getDistance() {
                return distance;
            }

            public String getDuration() {
                return duration;
            }

            public String getStrategy() {
                return strategy;
            }

            public String getTolls() {
                return tolls;
            }

            public String getToll_distance() {
                return toll_distance;
            }

//            public List<StepsEntity> getSteps() {
//                return steps;
//            }

            public static class StepsEntity {
                private String instruction;
                private String orientation;
                private String distance;
                private String tolls;
                private String toll_distance;
                private String duration;
                private String polyline;
                private String action;
                private String assistant_action;
                private List<?> toll_road;
                /**
                 * lcode : []
                 * distance : 31
                 * status : 未知
                 */

                private List<TmcsEntity> tmcs;
                /**
                 * name : 北京市
                 * citycode : 010
                 * adcode : 110100
                 * districts : [{"name":"朝阳区","adcode":"110105"}]
                 */

                private List<CitiesEntity> cities;

                public void setInstruction(String instruction) {
                    this.instruction = instruction;
                }

                public void setOrientation(String orientation) {
                    this.orientation = orientation;
                }

                public void setDistance(String distance) {
                    this.distance = distance;
                }

                public void setTolls(String tolls) {
                    this.tolls = tolls;
                }

                public void setToll_distance(String toll_distance) {
                    this.toll_distance = toll_distance;
                }

                public void setDuration(String duration) {
                    this.duration = duration;
                }

                public void setPolyline(String polyline) {
                    this.polyline = polyline;
                }

                public void setAction(String action) {
                    this.action = action;
                }

                public void setAssistant_action(String assistant_action) {
                    this.assistant_action = assistant_action;
                }

                public void setToll_road(List<?> toll_road) {
                    this.toll_road = toll_road;
                }

                public void setTmcs(List<TmcsEntity> tmcs) {
                    this.tmcs = tmcs;
                }

                public void setCities(List<CitiesEntity> cities) {
                    this.cities = cities;
                }

                public String getInstruction() {
                    return instruction;
                }

                public String getOrientation() {
                    return orientation;
                }

                public String getDistance() {
                    return distance;
                }

                public String getTolls() {
                    return tolls;
                }

                public String getToll_distance() {
                    return toll_distance;
                }

                public String getDuration() {
                    return duration;
                }

                public String getPolyline() {
                    return polyline;
                }

                public String getAction() {
                    return action;
                }

                public String getAssistant_action() {
                    return assistant_action;
                }

                public List<?> getToll_road() {
                    return toll_road;
                }

                public List<TmcsEntity> getTmcs() {
                    return tmcs;
                }

                public List<CitiesEntity> getCities() {
                    return cities;
                }

                public static class TmcsEntity {
                    private String distance;
                    private String status;
                    private List<?> lcode;

                    public void setDistance(String distance) {
                        this.distance = distance;
                    }

                    public void setStatus(String status) {
                        this.status = status;
                    }

                    public void setLcode(List<?> lcode) {
                        this.lcode = lcode;
                    }

                    public String getDistance() {
                        return distance;
                    }

                    public String getStatus() {
                        return status;
                    }

                    public List<?> getLcode() {
                        return lcode;
                    }
                }

                public static class CitiesEntity {
                    private String name;
                    private String citycode;
                    private String adcode;
                    /**
                     * name : 朝阳区
                     * adcode : 110105
                     */

                    private List<DistrictsEntity> districts;

                    public void setName(String name) {
                        this.name = name;
                    }

                    public void setCitycode(String citycode) {
                        this.citycode = citycode;
                    }

                    public void setAdcode(String adcode) {
                        this.adcode = adcode;
                    }

                    public void setDistricts(List<DistrictsEntity> districts) {
                        this.districts = districts;
                    }

                    public String getName() {
                        return name;
                    }

                    public String getCitycode() {
                        return citycode;
                    }

                    public String getAdcode() {
                        return adcode;
                    }

                    public List<DistrictsEntity> getDistricts() {
                        return districts;
                    }

                    public static class DistrictsEntity {
                        private String name;
                        private String adcode;

                        public void setName(String name) {
                            this.name = name;
                        }

                        public void setAdcode(String adcode) {
                            this.adcode = adcode;
                        }

                        public String getName() {
                            return name;
                        }

                        public String getAdcode() {
                            return adcode;
                        }
                    }
                }
            }
        }
    }
}
