package com.data.hmly.service.translation.direction.amap.pojo;

import java.util.List;

/**
 * Created by Sane on 16/4/20.
 */
public class AmapTaxiDirectionResult {


    private String status;
    private String info;
    private String infocode;
    private String count;
    private RouteEntity route;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public String getInfocode() {
        return infocode;
    }

    public void setInfocode(String infocode) {
        this.infocode = infocode;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public RouteEntity getRoute() {
        return route;
    }

    public void setRoute(RouteEntity route) {
        this.route = route;
    }

    public static class RouteEntity {
        private String origin;
        private String destination;
        private String taxi_cost;
        /**
         * distance : 2307
         * duration : 600
         * strategy : 速度最快
         * tolls : 0
         * toll_distance : 0
         * steps : [{"instruction":"向西行驶67米右转","orientation":"西","distance":"67","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.434235,39.909073;116.434166,39.909088;116.433975,39.909088;116.433456,39.909088","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"23","status":"未知"},{"lcode":[],"distance":"44","status":"未知"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿贡院东街向北行驶235米左转","orientation":"北","road":"贡院东街","distance":"235","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.433449,39.909084;116.433456,39.909496;116.433456,39.909866;116.433456,39.910069;116.433441,39.910717;116.433441,39.911221","action":"左转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"86","status":"未知"},{"lcode":[],"distance":"22","status":"未知"},{"lcode":[],"distance":"71","status":"未知"},{"lcode":[],"distance":"56","status":"未知"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿贡院东街向西行驶76米右转","orientation":"西","road":"贡院东街","distance":"76","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.433441,39.911221;116.432549,39.911198","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"76","status":"未知"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿贡院东街向北行驶106米右转","orientation":"北","road":"贡院东街","distance":"106","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.432549,39.911194;116.432526,39.912151","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"106","status":"未知"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿东总布胡同向东行驶193米右转","orientation":"东","road":"东总布胡同","distance":"193","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.432518,39.912151;116.433495,39.912174;116.433868,39.912231;116.434792,39.912258","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"84","status":"未知"},{"lcode":[],"distance":"31","status":"未知"},{"lcode":[],"distance":"78","status":"未知"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿建国门北大街途径建国门南大街向南行驶789米右转","orientation":"南","road":"建国门北大街","distance":"789","tolls":"0","toll_distance":"0","toll_road":[],"duration":"120","polyline":"116.434792,39.912258;116.434845,39.911217;116.434959,39.909191;116.434937,39.909012;116.434891,39.908867;116.43483,39.908726;116.434814,39.908649;116.434799,39.908615;116.434799,39.908539;116.434807,39.908493;116.43483,39.908466;116.434875,39.908287;116.434975,39.908104;116.434982,39.907993;116.435051,39.907345;116.435081,39.907219;116.43512,39.907093;116.435173,39.906975;116.435486,39.906509;116.43557,39.906292;116.435593,39.906216;116.435654,39.905956;116.435677,39.905678;116.43573,39.905247","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"116","status":"畅通"},{"lcode":[],"distance":"261","status":"畅通"},{"lcode":[],"distance":"65","status":"畅通"},{"lcode":[],"distance":"206","status":"畅通"},{"lcode":[],"distance":"34","status":"畅通"},{"lcode":[],"distance":"60","status":"畅通"},{"lcode":[],"distance":"47","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿北京站东街向西行驶286米右转","orientation":"西","road":"北京站东街","distance":"286","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.43573,39.905243;116.435677,39.905212;116.435493,39.90519;116.434807,39.90519;116.43351,39.905182;116.433334,39.905178;116.432381,39.905136","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"22","status":"畅通"},{"lcode":[],"distance":"58","status":"畅通"},{"lcode":[],"distance":"111","status":"畅通"},{"lcode":[],"distance":"14","status":"畅通"},{"lcode":[],"distance":"81","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"沿大羊毛胡同向北行驶367米右转","orientation":"北","road":"大羊毛胡同","distance":"367","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.432381,39.905128;116.43235,39.905254;116.432343,39.905323;116.432335,39.905407;116.432327,39.905941;116.432312,39.906651;116.432289,39.906876;116.432274,39.907551;116.432259,39.907864;116.432259,39.908455","action":"右转","assistant_action":[],"tmcs":[{"lcode":[],"distance":"90","status":"拥堵"},{"lcode":[],"distance":"78","status":"畅通"},{"lcode":[],"distance":"25","status":"畅通"},{"lcode":[],"distance":"75","status":"畅通"},{"lcode":[],"distance":"99","status":"畅通"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]},{"instruction":"向东行驶30米向右前方行驶进入辅路","orientation":"东","distance":"30","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.432251,39.908455;116.432602,39.908463","action":"向右前方行驶","assistant_action":"进入辅路","tmcs":[{"lcode":[],"distance":"2","status":"未知"}],"cities":[]},{"instruction":"沿建国门内大街向东行驶158米到达目的地","orientation":"东","road":"建国门内大街","distance":"158","tolls":"0","toll_distance":"0","toll_road":[],"duration":"60","polyline":"116.432602,39.908463;116.432938,39.908363;116.434448,39.908382","action":[],"assistant_action":"到达目的地","tmcs":[{"lcode":[],"distance":"158","status":"缓行"}],"cities":[{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]}]
         * traffic_lights : 2
         */

        private List<PathsEntity> paths;

        public String getOrigin() {
            return origin;
        }

        public void setOrigin(String origin) {
            this.origin = origin;
        }

        public String getDestination() {
            return destination;
        }

        public void setDestination(String destination) {
            this.destination = destination;
        }

        public String getTaxi_cost() {
            return taxi_cost;
        }

        public void setTaxi_cost(String taxi_cost) {
            this.taxi_cost = taxi_cost;
        }

        public List<PathsEntity> getPaths() {
            return paths;
        }

        public void setPaths(List<PathsEntity> paths) {
            this.paths = paths;
        }

        public static class PathsEntity {
            private String distance;
            private String duration;
            private String strategy;
            private String tolls;
            private String toll_distance;
            private String traffic_lights;
            /**
             * instruction : 向西行驶67米右转
             * orientation : 西
             * distance : 67
             * tolls : 0
             * toll_distance : 0
             * toll_road : []
             * duration : 60
             * polyline : 116.434235,39.909073;116.434166,39.909088;116.433975,39.909088;116.433456,39.909088
             * action : 右转
             * assistant_action : []
             * tmcs : [{"lcode":[],"distance":"23","status":"未知"},{"lcode":[],"distance":"44","status":"未知"}]
             * cities : [{"name":"北京市","citycode":"010","adcode":"110100","districts":[{"name":"东城区","adcode":"110101"}]}]
             */

            public String getDistance() {
                return distance;
            }

            public void setDistance(String distance) {
                this.distance = distance;
            }

            public String getDuration() {
                return duration;
            }

            public void setDuration(String duration) {
                this.duration = duration;
            }

            public String getStrategy() {
                return strategy;
            }

            public void setStrategy(String strategy) {
                this.strategy = strategy;
            }

            public String getTolls() {
                return tolls;
            }

            public void setTolls(String tolls) {
                this.tolls = tolls;
            }

            public String getToll_distance() {
                return toll_distance;
            }

            public void setToll_distance(String toll_distance) {
                this.toll_distance = toll_distance;
            }

            public String getTraffic_lights() {
                return traffic_lights;
            }

            public void setTraffic_lights(String traffic_lights) {
                this.traffic_lights = traffic_lights;
            }


        }
    }
}
