package com.data.spider.service.pojo.ctrip;

import java.util.List;

/**
 * Created by Sane on 16/2/15.
 */
public class RankDestInfo {

    /**
     * Timestamp : /Date(1455507286209+0800)/
     * Ack : Success
     * Errors : []
     * Extension : [{"Id":"CLOGGING_TRACE_ID","Value":"2836045274005075512"}]
     */

    private ResponseStatusEntity ResponseStatus;
    /**
     * ResponseStatus : {"Timestamp":"/Date(1455507286209+0800)/","Ack":"Success","Errors":[],"Extension":[{"Id":"CLOGGING_TRACE_ID","Value":"2836045274005075512"}]}
     * RankDestInfo : [{"DistrictId":236,"Name":"悉尼","EName":"Sydney","CoverImageId":9298092,"CoverImageUrl":"http://dimg05.c-ctrip.com/images/tg/374/295/715/d0dd6177cf9c4a97be8abd2b8550bfaa_R_200_200.jpg","InChina":false,"ParentDistrictId":25025,"ParentName":"新南威尔士州","CityId":501,"IsCountry":false,"CountryName":"新南威尔士州","CountryId":25025,"IsOverseas":true},{"DistrictId":312,"Name":"墨尔本","EName":"Melbourne","CoverImageId":64323859,"CoverImageUrl":"http://dimg09.c-ctrip.com/images/fd/tg/g3/M05/19/6C/CggYGVad5ieAEJ5xAAG_TANlaYM747_R_200_200.jpg","InChina":false,"ParentDistrictId":25027,"ParentName":"维多利亚州","CityId":358,"IsCountry":false,"CountryName":"维多利亚州","CountryId":25027,"IsOverseas":true},{"DistrictId":456,"Name":"黄金海岸","EName":"Gold coast","CoverImageId":26186777,"CoverImageUrl":"http://dimg02.c-ctrip.com/images/fd/tg/g2/M07/C4/1E/CghzgVUQR3mAKy42AAFfhxrfzHg402_R_200_200.jpg","InChina":false,"ParentDistrictId":25026,"ParentName":"昆士兰州","CityId":1210,"IsCountry":false,"CountryName":"昆士兰州","CountryId":25026,"IsOverseas":true},{"DistrictId":323,"Name":"布里斯班","EName":"Brisbane","CoverImageId":2067659,"CoverImageUrl":"http://dimg09.c-ctrip.com/images/tg/495/757/087/dbf20b8eb4ad4c45ab133cacbe7b8f58_R_200_200.jpg","InChina":false,"ParentDistrictId":25026,"ParentName":"昆士兰州","CityId":680,"IsCountry":false,"CountryName":"昆士兰州","CountryId":25026,"IsOverseas":true},{"DistrictId":453,"Name":"凯恩斯","EName":"cairns","CoverImageId":15760312,"CoverImageUrl":"http://dimg01.c-ctrip.com/images/tg/719/165/130/4b964af850a04f6192acf61e3ec75b42_R_200_200.jpg","InChina":false,"ParentDistrictId":25026,"ParentName":"昆士兰州","CityId":728,"IsCountry":false,"CountryName":"昆士兰州","CountryId":25026,"IsOverseas":true},{"DistrictId":25025,"Name":"新南威尔士州","EName":"New South Wales","CoverImageId":1305143,"CoverImageUrl":"http://dimg08.c-ctrip.com/images/tg/001/266/354/eb8e4db23b5f45688fe03e343a0d417b_R_200_200.jpg","InChina":false,"ParentDistrictId":100048,"ParentName":"澳大利亚","CityId":0,"IsCountry":false,"CountryName":"澳大利亚","CountryId":100048,"IsOverseas":true},{"DistrictId":25027,"Name":"维多利亚州","EName":"Victoria","CoverImageId":64323859,"CoverImageUrl":"http://dimg09.c-ctrip.com/images/fd/tg/g3/M05/19/6C/CggYGVad5ieAEJ5xAAG_TANlaYM747_R_200_200.jpg","InChina":false,"ParentDistrictId":100048,"ParentName":"澳大利亚","CityId":0,"IsCountry":false,"CountryName":"澳大利亚","CountryId":100048,"IsOverseas":true},{"DistrictId":321,"Name":"堪培拉","EName":"Canberra","CoverImageId":53048597,"CoverImageUrl":"http://dimg06.c-ctrip.com/images/fd/tg/g4/M09/59/50/CggYHVXlD1qAG8q_AEvb1-XBeuo676_R_200_200.jpg","InChina":false,"ParentDistrictId":25022,"ParentName":"澳大利亚首都特区","CityId":679,"IsCountry":false,"CountryName":"澳大利亚首都特区","CountryId":25022,"IsOverseas":true},{"DistrictId":25030,"Name":"西澳大利亚州","EName":"Western Australia","CoverImageId":30912957,"CoverImageUrl":"http://dimg06.c-ctrip.com/images/fd/tg/g2/M05/8B/36/Cghzf1Ww3uKAbifaACABIZIA1d0945_R_200_200.jpg","InChina":false,"ParentDistrictId":100048,"ParentName":"澳大利亚","CityId":0,"IsCountry":false,"CountryName":"澳大利亚","CountryId":100048,"IsOverseas":true},{"DistrictId":15844,"Name":"蓝山","EName":"Blue Mountains","CoverImageId":64184327,"CoverImageUrl":"http://dimg08.c-ctrip.com/images/fd/tg/g3/M06/F7/25/CggYGVaXScaAFcI4AAIvGJ9W46g273_R_200_200.jpg","InChina":false,"ParentDistrictId":25025,"ParentName":"新南威尔士州","CityId":0,"IsCountry":false,"CountryName":"新南威尔士州","CountryId":25025,"IsOverseas":true},{"DistrictId":626,"Name":"阿德莱德","EName":"Adelaide","CoverImageId":21260032,"CoverImageUrl":"http://dimg01.c-ctrip.com/images/tg/105/332/853/49830ddfa69246d8b4865ce076fdf96e_R_200_200.jpg","InChina":false,"ParentDistrictId":25029,"ParentName":"南澳大利亚州","CityId":1243,"IsCountry":false,"CountryName":"南澳大利亚州","CountryId":25029,"IsOverseas":true},{"DistrictId":16223,"Name":"卧龙岗市","EName":"Wollongong","CoverImageId":53049732,"CoverImageUrl":"http://dimg05.c-ctrip.com/images/fd/tg/g3/M07/57/2B/CggYGVXlEtaAAkg-AC63R6eSsfs343_R_200_200.jpg","InChina":false,"ParentDistrictId":25025,"ParentName":"新南威尔士州","CityId":0,"IsCountry":false,"CountryName":"新南威尔士州","CountryId":25025,"IsOverseas":true},{"DistrictId":16140,"Name":"拜伦湾","EName":"Byron Bay","CoverImageId":8129806,"CoverImageUrl":"http://p.chanyouji.cn/6295/1348638635140p1780hut49s28n7h8ar1dnf1l0ma.jpg?imageView/2/w/200/h/200","InChina":false,"ParentDistrictId":25025,"ParentName":"新南威尔士州","CityId":0,"IsCountry":false,"CountryName":"新南威尔士州","CountryId":25025,"IsOverseas":true},{"DistrictId":25024,"Name":"塔斯马尼亚州","EName":"Tasmania","CoverImageId":23109407,"CoverImageUrl":"http://dimg08.c-ctrip.com/images/fd/tg/g2/M07/8E/D9/Cghzf1WxFL2AMuu3AAOxoc-XZAg585_R_200_200.jpg","InChina":false,"ParentDistrictId":100048,"ParentName":"澳大利亚","CityId":0,"IsCountry":false,"CountryName":"澳大利亚","CountryId":100048,"IsOverseas":true},{"DistrictId":120044,"Name":"珀斯","EName":"Perth","CoverImageId":21251938,"CoverImageUrl":"http://dimg03.c-ctrip.com/images/tg/934/376/971/6b703cf89596447c9b6502d1bb68bc12_R_200_200.jpg","InChina":false,"ParentDistrictId":25030,"ParentName":"西澳大利亚州","CityId":681,"IsCountry":false,"CountryName":"西澳大利亚州","CountryId":25030,"IsOverseas":true},{"DistrictId":25029,"Name":"南澳大利亚州","EName":"South Australia","CoverImageId":21260032,"CoverImageUrl":"http://dimg01.c-ctrip.com/images/tg/105/332/853/49830ddfa69246d8b4865ce076fdf96e_R_200_200.jpg","InChina":false,"ParentDistrictId":100048,"ParentName":"澳大利亚","CityId":0,"IsCountry":false,"CountryName":"澳大利亚","CountryId":100048,"IsOverseas":true},{"DistrictId":16238,"Name":"史蒂芬斯港","EName":"Port Stephens","CoverImageId":0,"CoverImageUrl":"","InChina":false,"ParentDistrictId":25025,"ParentName":"新南威尔士州","CityId":10128,"IsCountry":false,"CountryName":"新南威尔士州","CountryId":25025,"IsOverseas":true},{"DistrictId":629,"Name":"霍巴特","EName":"Hobart","CoverImageId":28238510,"CoverImageUrl":"http://dimg07.c-ctrip.com/images/fd/tg/g1/M07/7A/13/CghzfFWwqXSAc7qNACp5jl94iNw218_R_200_200.jpg","InChina":false,"ParentDistrictId":25024,"ParentName":"塔斯马尼亚州","CityId":1446,"IsCountry":false,"CountryName":"塔斯马尼亚州","CountryId":25024,"IsOverseas":true},{"DistrictId":627,"Name":"北领地","EName":"Northern Territory","CoverImageId":21251147,"CoverImageUrl":"http://dimg09.c-ctrip.com/images/tg/850/396/092/9b58c9fa052d4351840ce6625d7f492e_R_200_200.jpg","InChina":false,"ParentDistrictId":100048,"ParentName":"澳大利亚","CityId":4032,"IsCountry":false,"CountryName":"澳大利亚","CountryId":100048,"IsOverseas":true},{"DistrictId":57781,"Name":"圣灵群岛","EName":"Whitsunday Islands","CoverImageId":30910826,"CoverImageUrl":"http://dimg03.c-ctrip.com/images/fd/tg/g2/M06/8B/32/Cghzf1Ww3pmAGiVUABZlztKQNhY712_R_200_200.jpg","InChina":false,"ParentDistrictId":16276,"ParentName":"昆士兰州","CityId":61737,"IsCountry":false,"CountryName":"昆士兰州","CountryId":25026,"IsOverseas":true},{"DistrictId":699,"Name":"阳光海岸","EName":"SunShine Coast","CoverImageId":1012951,"CoverImageUrl":"http://dimg08.c-ctrip.com/images/tg/657/759/898/427c10284a9c4e838d353ffdde48b84a_R_200_200.jpg","InChina":false,"ParentDistrictId":25026,"ParentName":"昆士兰州","CityId":1813,"IsCountry":false,"CountryName":"昆士兰州","CountryId":25026,"IsOverseas":true},{"DistrictId":1344,"Name":"汉密尔顿岛","EName":"Hamilton Island","CoverImageId":30910826,"CoverImageUrl":"http://dimg03.c-ctrip.com/images/fd/tg/g2/M06/8B/32/Cghzf1Ww3pmAGiVUABZlztKQNhY712_R_200_200.jpg","InChina":false,"ParentDistrictId":57781,"ParentName":"昆士兰州","CityId":4035,"IsCountry":false,"CountryName":"昆士兰州","CountryId":25026,"IsOverseas":true},{"DistrictId":16916,"Name":"杰拉尔顿","EName":"Geraldton","CoverImageId":20854233,"CoverImageUrl":"http://dimg02.c-ctrip.com/images/tg/052/142/964/536443f07caa461d9559dc3358200f3a_R_200_200.jpg","InChina":false,"ParentDistrictId":25030,"ParentName":"西澳大利亚州","CityId":0,"IsCountry":false,"CountryName":"西澳大利亚州","CountryId":25030,"IsOverseas":true},{"DistrictId":813,"Name":"弗里曼特尔","EName":"Fremantle","CoverImageId":64076038,"CoverImageUrl":"http://dimg07.c-ctrip.com/images/fd/tg/g4/M03/45/4A/CggYHVaSdo-Af-agAAFK2ginJqU941_R_200_200.jpg","InChina":false,"ParentDistrictId":25030,"ParentName":"西澳大利亚州","CityId":0,"IsCountry":false,"CountryName":"西澳大利亚州","CountryId":25030,"IsOverseas":true},{"DistrictId":1692,"Name":"爱丽斯泉","EName":"Alice Springs","CoverImageId":22605439,"CoverImageUrl":"http://dimg08.c-ctrip.com/images/fd/tg/g2/M01/3F/37/CghzgFSeSPWAfhBFAAChx3effTc962_R_200_200.jpg","InChina":false,"ParentDistrictId":15731,"ParentName":"北领地","CityId":3404,"IsCountry":false,"CountryName":"北领地","CountryId":627,"IsOverseas":true},{"DistrictId":16931,"Name":"班伯里","EName":"Bunbury","CoverImageId":0,"CoverImageUrl":"","InChina":false,"ParentDistrictId":25030,"ParentName":"西澳大利亚州","CityId":0,"IsCountry":false,"CountryName":"西澳大利亚州","CountryId":25030,"IsOverseas":true},{"DistrictId":1599,"Name":"袋鼠岛","EName":"Kangaroo Island","CoverImageId":64715985,"CoverImageUrl":"http://dimg02.c-ctrip.com/images/fd/tg/g4/M08/35/38/CggYHlaqzN-AWdTvABcaHGJ8ZcQ542_R_200_200.jpg","InChina":false,"ParentDistrictId":25029,"ParentName":"南澳大利亚州","CityId":4040,"IsCountry":false,"CountryName":"南澳大利亚州","CountryId":25029,"IsOverseas":true},{"DistrictId":136711,"Name":"洛恩","EName":"Lorne","CoverImageId":63067556,"CoverImageUrl":"http://dimg05.c-ctrip.com/images/fd/tg/g4/M0B/2D/97/CggYHlZ6YXKAcAdhAAFSMwVPEXA080_R_200_200.jpg","InChina":false,"ParentDistrictId":25027,"ParentName":"维多利亚州","CityId":0,"IsCountry":false,"CountryName":"维多利亚州","CountryId":25027,"IsOverseas":true},{"DistrictId":1929,"Name":"朗塞斯顿","EName":"launceston","CoverImageId":16962489,"CoverImageUrl":"http://dimg02.c-ctrip.com/images/tg/975/677/025/ab3dd77545a1450b9b5e45b4957ae855_R_200_200.jpg","InChina":false,"ParentDistrictId":25024,"ParentName":"塔斯马尼亚州","CityId":3827,"IsCountry":false,"CountryName":"塔斯马尼亚州","CountryId":25024,"IsOverseas":true},{"DistrictId":16545,"Name":"吉朗","EName":"Geelong","CoverImageId":54076896,"CoverImageUrl":"http://dimg01.c-ctrip.com/images/fd/tg/g3/M08/18/ED/CggYGlYDqzKAJx4jAAQhMLLhzVk714_R_200_200.jpg","InChina":false,"ParentDistrictId":25027,"ParentName":"维多利亚州","CityId":0,"IsCountry":false,"CountryName":"维多利亚州","CountryId":25027,"IsOverseas":true}]
     * TotalCount : 101
     */

    private int TotalCount;
    /**
     * DistrictId : 236
     * Name : 悉尼
     * EName : Sydney
     * CoverImageId : 9298092
     * CoverImageUrl : http://dimg05.c-ctrip.com/images/tg/374/295/715/d0dd6177cf9c4a97be8abd2b8550bfaa_R_200_200.jpg
     * InChina : false
     * ParentDistrictId : 25025
     * ParentName : 新南威尔士州
     * CityId : 501
     * IsCountry : false
     * CountryName : 新南威尔士州
     * CountryId : 25025
     * IsOverseas : true
     */

    private List<RankDestInfoEntity> RankDestInfo;

    public void setResponseStatus(ResponseStatusEntity ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public void setRankDestInfo(List<RankDestInfoEntity> RankDestInfo) {
        this.RankDestInfo = RankDestInfo;
    }

    public ResponseStatusEntity getResponseStatus() {
        return ResponseStatus;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public List<RankDestInfoEntity> getRankDestInfo() {
        return RankDestInfo;
    }

    public static class ResponseStatusEntity {
        private String Timestamp;
        private String Ack;
        private List<?> Errors;
        /**
         * Id : CLOGGING_TRACE_ID
         * Value : 2836045274005075512
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

    public static class RankDestInfoEntity {
        private int DistrictId;
        private String Name;
        private String EName;
        private int CoverImageId;
        private String CoverImageUrl;
        private boolean InChina;
        private int ParentDistrictId;
        private String ParentName;
        private int CityId;
        private boolean IsCountry;
        private String CountryName;
        private int CountryId;
        private boolean IsOverseas;

        public void setDistrictId(int DistrictId) {
            this.DistrictId = DistrictId;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public void setEName(String EName) {
            this.EName = EName;
        }

        public void setCoverImageId(int CoverImageId) {
            this.CoverImageId = CoverImageId;
        }

        public void setCoverImageUrl(String CoverImageUrl) {
            this.CoverImageUrl = CoverImageUrl;
        }

        public void setInChina(boolean InChina) {
            this.InChina = InChina;
        }

        public void setParentDistrictId(int ParentDistrictId) {
            this.ParentDistrictId = ParentDistrictId;
        }

        public void setParentName(String ParentName) {
            this.ParentName = ParentName;
        }

        public void setCityId(int CityId) {
            this.CityId = CityId;
        }

        public void setIsCountry(boolean IsCountry) {
            this.IsCountry = IsCountry;
        }

        public void setCountryName(String CountryName) {
            this.CountryName = CountryName;
        }

        public void setCountryId(int CountryId) {
            this.CountryId = CountryId;
        }

        public void setIsOverseas(boolean IsOverseas) {
            this.IsOverseas = IsOverseas;
        }

        public int getDistrictId() {
            return DistrictId;
        }

        public String getName() {
            return Name;
        }

        public String getEName() {
            return EName;
        }

        public int getCoverImageId() {
            return CoverImageId;
        }

        public String getCoverImageUrl() {
            return CoverImageUrl;
        }

        public boolean isInChina() {
            return InChina;
        }

        public int getParentDistrictId() {
            return ParentDistrictId;
        }

        public String getParentName() {
            return ParentName;
        }

        public int getCityId() {
            return CityId;
        }

        public boolean isIsCountry() {
            return IsCountry;
        }

        public String getCountryName() {
            return CountryName;
        }

        public int getCountryId() {
            return CountryId;
        }

        public boolean isIsOverseas() {
            return IsOverseas;
        }
    }
}
