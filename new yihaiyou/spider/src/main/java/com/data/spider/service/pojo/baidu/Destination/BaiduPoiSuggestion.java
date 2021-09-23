package com.data.spider.service.pojo.baidu.Destination;

import java.util.List;

/**
 * Created by Sane on 15/9/16.
 */
public class BaiduPoiSuggestion {

    /**
     * errno : 0
     * msg :
     * data : {"suglist":[{"type":"1","sid":"46748b2de74f2ebeee3864f0","parent_sid":"55163f0fd41f9757809972f0","surl":"xiamen","sname":"厦门","parent_sname":"福建","scene_layer":"4","scene_path":"亚洲,中国,福建","ticket":0,"is_china":1,"x":118.093537,"y":24.491862019549},{"type":"1","sid":"0b216dde39224b57f937e3f9","parent_sid":"46748b2de74f2ebeee3864f0","surl":"xiamendaxue","sname":"厦门大学","parent_sname":"厦门","scene_layer":"6","scene_path":"亚洲,中国,福建,厦门","ticket":0,"is_china":1,"x":118.10477402066,"y":24.443314045528},{"type":"1","sid":"ab7214443489a744ec5b6ec5","parent_sid":"77bc6f54bc9175003aadecf9","surl":"xiamenhaidishijie","sname":"厦门海底世界","parent_sname":"鼓浪屿","scene_layer":"6","scene_path":"亚洲,中国,福建,厦门,鼓浪屿","ticket":1,"is_china":1,"x":118.07783895877,"y":24.452080054354},{"type":"1","sid":"ae19dfcc000b2975e4558736","parent_sid":"46748b2de74f2ebeee3864f0","surl":"shamentaiwanxiaochijie","sname":"厦门台湾小吃街","parent_sname":"厦门","scene_layer":"6","scene_path":"亚洲,中国,福建,厦门","ticket":0,"is_china":1,"x":118.08191196578,"y":24.462763022543},{"type":"1","sid":"af979b26aa3f6703cc8538c5","parent_sid":"46748b2de74f2ebeee3864f0","surl":"xiamenkejiguan","sname":"厦门科技馆","parent_sname":"厦门","scene_layer":"6","scene_path":"亚洲,中国,福建,厦门","ticket":1,"is_china":1,"x":118.11446197609,"y":24.496392027038},{"type":"1","sid":"b6d2381b2120f8ccda001fc5","parent_sid":"804875003aad20f5663eea2a","surl":"xiamenbowuguan","sname":"厦门博物馆","parent_sname":"八卦楼","scene_layer":"6","scene_path":"亚洲,中国,福建,厦门,鼓浪屿,八卦楼","ticket":0,"is_china":1,"x":118.11629963967,"y":24.497085761256},{"type":"1","sid":"3eca1acb2b02abe666d64c18","parent_sid":"0b216dde39224b57f937e3f9","surl":"xiamendaxueqingrengu","sname":"厦门大学情人谷","parent_sname":"厦门大学","scene_layer":"6","scene_path":"亚洲,中国,福建,厦门,厦门大学","ticket":0,"is_china":1,"x":118.101747,"y":24.440044019915},{"type":"1","sid":"53ffc005c401f1153f0f77c5","parent_sid":"46748b2de74f2ebeee3864f0","surl":"xiamendaqiao","sname":"厦门大桥","parent_sname":"厦门","scene_layer":"6","scene_path":"亚洲,中国,福建,厦门","ticket":1,"is_china":1,"x":118.10802301203,"y":24.563718057677},{"type":"1","sid":"44d50b158915309ea17169c5","parent_sid":"46748b2de74f2ebeee3864f0","surl":"xiamenzhongshangongyuan","sname":"厦门中山公园","parent_sname":"厦门","scene_layer":"6","scene_path":"亚洲,中国,福建,厦门","ticket":0,"is_china":1,"x":118.09589822324,"y":24.465097952174},{"type":"1","sid":"a6ec9b7e6b2265c83e2be737","parent_sid":"46748b2de74f2ebeee3864f0","surl":"shamentiedaowenhuagongyuan","sname":"厦门铁道文化公园","parent_sname":"厦门","scene_layer":"6","scene_path":"亚洲,中国,福建,厦门","ticket":0,"is_china":1,"x":118.10047095761,"y":24.461081033678}]}
     */

    private int errno;
    private String msg;
    private DataEntity data;

    public void setErrno(int errno) {
        this.errno = errno;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setData(DataEntity data) {
        this.data = data;
    }

    public int getErrno() {
        return errno;
    }

    public String getMsg() {
        return msg;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * type : 1
         * sid : 46748b2de74f2ebeee3864f0
         * parent_sid : 55163f0fd41f9757809972f0
         * surl : xiamen
         * sname : 厦门
         * parent_sname : 福建
         * scene_layer : 4
         * scene_path : 亚洲,中国,福建
         * ticket : 0
         * is_china : 1
         * x : 118.093537
         * y : 24.491862019549
         */

        private List<SuglistEntity> suglist;

        public void setSuglist(List<SuglistEntity> suglist) {
            this.suglist = suglist;
        }

        public List<SuglistEntity> getSuglist() {
            return suglist;
        }

        public static class SuglistEntity {
            private String type;
            private String sid;
            private String parent_sid;
            private String surl;
            private String sname;
            private String parent_sname;
            private String scene_layer;
            private String scene_path;
            private int ticket;
            private int is_china;
            private double x;
            private double y;

            public void setType(String type) {
                this.type = type;
            }

            public void setSid(String sid) {
                this.sid = sid;
            }

            public void setParent_sid(String parent_sid) {
                this.parent_sid = parent_sid;
            }

            public void setSurl(String surl) {
                this.surl = surl;
            }

            public void setSname(String sname) {
                this.sname = sname;
            }

            public void setParent_sname(String parent_sname) {
                this.parent_sname = parent_sname;
            }

            public void setScene_layer(String scene_layer) {
                this.scene_layer = scene_layer;
            }

            public void setScene_path(String scene_path) {
                this.scene_path = scene_path;
            }

            public void setTicket(int ticket) {
                this.ticket = ticket;
            }

            public void setIs_china(int is_china) {
                this.is_china = is_china;
            }

            public void setX(double x) {
                this.x = x;
            }

            public void setY(double y) {
                this.y = y;
            }

            public String getType() {
                return type;
            }

            public String getSid() {
                return sid;
            }

            public String getParent_sid() {
                return parent_sid;
            }

            public String getSurl() {
                return surl;
            }

            public String getSname() {
                return sname;
            }

            public String getParent_sname() {
                return parent_sname;
            }

            public String getScene_layer() {
                return scene_layer;
            }

            public String getScene_path() {
                return scene_path;
            }

            public int getTicket() {
                return ticket;
            }

            public int getIs_china() {
                return is_china;
            }

            public double getX() {
                return x;
            }

            public double getY() {
                return y;
            }
        }
    }
}
