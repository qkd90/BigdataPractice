package com.data.spider.service.pojo.ctrip;

import java.util.List;

/**
 * Created by Sane on 15/10/30.
 */
public class ScenicMoreInfo {


    /**
     * Timestamp : /Date(1446173940180+0800)/
     * Ack : Success
     * Errors : []
     * Extension : [{"Id":"CLOGGING_TRACE_ID","Value":"5929326823974725229"}]
     */

    private ResponseStatusEntity ResponseStatus;
    /**
     * ResponseStatus : {"Timestamp":"/Date(1446173940180+0800)/","Ack":"Success","Errors":[],"Extension":[{"Id":"CLOGGING_TRACE_ID","Value":"5929326823974725229"}]}
     * Traffic : <p>公交车：<br />市内有公交2路（青泥洼桥大连商场东侧始发）、4路（五一广场、奥林匹克广场可乘坐）、30路（大连火车站站南广场东侧始发）、403路（中山广场友谊商城正门可乘坐）、404路（星海湾广场、和平广场可乘坐）路，均可至“老虎滩”站下即到，票价均为1元。</p>
     <p>旅游环线：<br />也可乘坐大连环线旅游巴士至老虎滩，该线路每年4月中旬至10月底期间运营，始发和终到站均在大连火车站站南广场，票价10元，且可凭当日车票在途经任意站点多次乘车、任意上下，不需额外购票。</p>
     * Tip : <p>1. 景区外可能会有问你&ldquo;要不要门票&rdquo;的黑导游，请勿贪信小便宜，小心买到假票。<br />2. 建议关注各场馆门前的表演时间表。极地馆的动物表演很精彩，一天约有3-4场，建议早点进场占一个略靠前的位置，观看效果好。节假日时各场馆排队入场时间长，尤其是极地馆，建议先去排队人少的场馆。<br />3. 景区内有多家餐厅和一条小吃街，价格偏贵，若不打算在里面玩一整天，可自带些许干点和水。<br />4. 海上游艇码头靠近海兽馆，价格约为60元/人，主要是在近海开一圈后返回；四维影院的价格约为50元/人。</p>
     * Phone : 0411-82689356
     * Site : http://www.laohutan.com.cn/
     * Address : 大连市中山区滨海中路9号
     * OpenTime : 8:00-17:00
     * TicketDesc : 全园通票冬季200元（通常为11月1日-次年3月31日）、夏季220元；包括大门票、极地馆、珊瑚馆、鸟语林、欢乐剧场（冬季闭馆）、海兽馆等景点。未满18周岁学生凭身份证和学生证可享175元（冬季160元）的优惠票价；70周岁以上老人凭老年证可免费进入公园大门，且参观场馆可以享受半价优惠票价；身高1.3米及以下儿童在成人陪同下可免费入园。
     * TicketDescForH5 : 门市价:200RMB起 全园通票冬季200元（通常为11月1日-次年3月31日）、夏季220元；包括大门票、极地馆、珊瑚馆、鸟语林、欢乐剧场（冬季闭馆）、海兽馆等景点。未满18周岁学生凭身份证和学生证可享175元（冬季160元）的优惠票价；70周岁以上老人凭老年证可免费进入公园大门，且参观场馆可以享受半价优惠票价；身高1.3米及以下儿童在成人陪同下可免费入园。
     * Lat : 38.8778495788574
     * Lng : 121.676864624023
     * InChina : true
     * Name : 老虎滩海洋公园
     * CommentScore : 4.1
     */

    private String Traffic;
    private String Tip;
    private String Phone;
    private String Site;
    private String Address;
    private String OpenTime;
    private String TicketDesc;
    private String TicketDescForH5;
    private double Lat;
    private double Lng;
    private boolean InChina;
    private String Name;
    private double CommentScore;

    public void setResponseStatus(ResponseStatusEntity ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }

    public void setTraffic(String Traffic) {
        this.Traffic = Traffic;
    }

    public void setTip(String Tip) {
        this.Tip = Tip;
    }

    public void setPhone(String Phone) {
        this.Phone = Phone;
    }

    public void setSite(String Site) {
        this.Site = Site;
    }

    public void setAddress(String Address) {
        this.Address = Address;
    }

    public void setOpenTime(String OpenTime) {
        this.OpenTime = OpenTime;
    }

    public void setTicketDesc(String TicketDesc) {
        this.TicketDesc = TicketDesc;
    }

    public void setTicketDescForH5(String TicketDescForH5) {
        this.TicketDescForH5 = TicketDescForH5;
    }

    public void setLat(double Lat) {
        this.Lat = Lat;
    }

    public void setLng(double Lng) {
        this.Lng = Lng;
    }

    public void setInChina(boolean InChina) {
        this.InChina = InChina;
    }

    public void setName(String Name) {
        this.Name = Name;
    }

    public void setCommentScore(double CommentScore) {
        this.CommentScore = CommentScore;
    }

    public ResponseStatusEntity getResponseStatus() {
        return ResponseStatus;
    }

    public String getTraffic() {
        return Traffic;
    }

    public String getTip() {
        return Tip;
    }

    public String getPhone() {
        return Phone;
    }

    public String getSite() {
        return Site;
    }

    public String getAddress() {
        return Address;
    }

    public String getOpenTime() {
        return OpenTime;
    }

    public String getTicketDesc() {
        return TicketDesc;
    }

    public String getTicketDescForH5() {
        return TicketDescForH5;
    }

    public double getLat() {
        return Lat;
    }

    public double getLng() {
        return Lng;
    }

    public boolean isInChina() {
        return InChina;
    }

    public String getName() {
        return Name;
    }

    public double getCommentScore() {
        return CommentScore;
    }

    public static class ResponseStatusEntity {
        private String Timestamp;
        private String Ack;
        private List<?> Errors;
        /**
         * Id : CLOGGING_TRACE_ID
         * Value : 5929326823974725229
         */

        private List<ExtensionEntity> Extension;

        public void setTimestamp(String Timestamp) {
            this.Timestamp = Timestamp;
        }

        public void setAck(String Ack) {
            this.Ack = Ack;
        }

        public void setErrors(List<?> Errors) {
            this.Errors = Errors;
        }

        public void setExtension(List<ExtensionEntity> Extension) {
            this.Extension = Extension;
        }

        public String getTimestamp() {
            return Timestamp;
        }

        public String getAck() {
            return Ack;
        }

        public List<?> getErrors() {
            return Errors;
        }

        public List<ExtensionEntity> getExtension() {
            return Extension;
        }

        public static class ExtensionEntity {
            private String Id;
            private String Value;

            public void setId(String Id) {
                this.Id = Id;
            }

            public void setValue(String Value) {
                this.Value = Value;
            }

            public String getId() {
                return Id;
            }

            public String getValue() {
                return Value;
            }
        }
    }
}
