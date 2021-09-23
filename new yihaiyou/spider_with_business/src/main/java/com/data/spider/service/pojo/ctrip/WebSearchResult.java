package com.data.spider.service.pojo.ctrip;


public class WebSearchResult {


    /**
     * List : [{"Id":0,"Name":"厦门","EName":null,"DestId":21,"DestName":"福建","DestEName":null,"Url":"/place/xiamen21.html","TypeID":0,"Type":"icon_des","Address":null,"POIId":0},{"Id":0,"Name":"厦门的全部景点","EName":null,"DestId":0,"DestName":"福建","DestEName":null,"Url":"/sightlist/xiamen21.html","TypeID":101,"Type":"icon_attr_list","Address":null,"POIId":0},{"Id":0,"Name":"厦门的全部酒店","EName":null,"DestId":0,"DestName":"福建","DestEName":null,"Url":"/hotels/xiamen21.html","TypeID":101,"Type":"icon_attr_list","Address":null,"POIId":0},{"Id":8622,"Name":"厦门市博物馆","EName":"","DestId":21,"DestName":"厦门","DestEName":"Xiamen","Url":"/sight/xiamen21/8622.html","TypeID":0,"Type":"icon_attr","Address":null,"POIId":77350},{"Id":8625,"Name":"厦门园林植物园","EName":"","DestId":21,"DestName":"厦门","DestEName":"Xiamen","Url":"/sight/xiamen21/8625.html","TypeID":0,"Type":"icon_attr","Address":null,"POIId":77353},{"Id":14050,"Name":"厦门大学","EName":"xiamendaxue","DestId":21,"DestName":"厦门","DestEName":"Xiamen","Url":"/sight/xiamen21/14050.html","TypeID":0,"Type":"icon_attr","Address":null,"POIId":79124},{"Id":48769,"Name":"厦门海沧大桥旅游区","EName":"","DestId":21,"DestName":"厦门","DestEName":"Xiamen","Url":"/sight/xiamen21/48769.html","TypeID":0,"Type":"icon_attr","Address":null,"POIId":82704},{"Id":63494,"Name":"厦门园林博览苑","EName":"","DestId":21,"DestName":"厦门","DestEName":"Xiamen","Url":"/sight/xiamen21/63494.html","TypeID":0,"Type":"icon_attr","Address":null,"POIId":87435},{"Id":132440,"Name":"厦门音乐广场","EName":"","DestId":21,"DestName":"厦门","DestEName":"Xiamen","Url":"/sight/xiamen21/132440.html","TypeID":0,"Type":"icon_attr","Address":null,"POIId":94473},{"Id":132487,"Name":"厦门大学人类博物馆","EName":"","DestId":21,"DestName":"厦门","DestEName":"Xiamen","Url":"/sight/xiamen21/132487.html","TypeID":0,"Type":"icon_attr","Address":null,"POIId":94517},{"Id":119141,"Name":"厦门观音山沙雕文化公园","EName":"","DestId":21,"DestName":"厦门","DestEName":"Xiamen","Url":"/sight/xiamen21/119141.html","TypeID":0,"Type":"icon_attr","Address":null,"POIId":10537448},{"Id":1408277,"Name":"厦门白鹭园高尔夫球会","EName":"","DestId":21,"DestName":"厦门","DestEName":"Xiamen","Url":"/sight/xiamen21/1408277.html","TypeID":0,"Type":"icon_attr","Address":null,"POIId":10762745}]
     * SearchUrl : /searchsite/?query=%25e5%258e%25a6%25e9%2597%25a8
     * QAUrl : /searchsite/Asks?query=%25e5%258e%25a6%25e9%2597%25a8
     * TravelsUrl : /searchsite/Travels?query=%25e5%258e%25a6%25e9%2597%25a8
     */

    private String SearchUrl;
    private String QAUrl;
    private String TravelsUrl;
    /**
     * Id : 0
     * Name : 厦门
     * EName : null
     * DestId : 21
     * DestName : 福建
     * DestEName : null
     * Url : /place/xiamen21.html
     * TypeID : 0
     * Type : icon_des
     * Address : null
     * POIId : 0
     */

    private java.util.List<ListEntity> List;

    public void setSearchUrl(String SearchUrl) {
        this.SearchUrl = SearchUrl;
    }

    public void setQAUrl(String QAUrl) {
        this.QAUrl = QAUrl;
    }

    public void setTravelsUrl(String TravelsUrl) {
        this.TravelsUrl = TravelsUrl;
    }

    public void setList(java.util.List<ListEntity> List) {
        this.List = List;
    }

    public String getSearchUrl() {
        return SearchUrl;
    }

    public String getQAUrl() {
        return QAUrl;
    }

    public String getTravelsUrl() {
        return TravelsUrl;
    }

    public java.util.List<ListEntity> getList() {
        return List;
    }

    public static class ListEntity {
        private int Id;
        private String Name;
        private Object EName;
        private int DestId;
        private String DestName;
        private Object DestEName;
        private String Url;
        private int TypeID;
        private String Type;
        private Object Address;
        private int POIId;

        public void setId(int Id) {
            this.Id = Id;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public void setEName(Object EName) {
            this.EName = EName;
        }

        public void setDestId(int DestId) {
            this.DestId = DestId;
        }

        public void setDestName(String DestName) {
            this.DestName = DestName;
        }

        public void setDestEName(Object DestEName) {
            this.DestEName = DestEName;
        }

        public void setUrl(String Url) {
            this.Url = Url;
        }

        public void setTypeID(int TypeID) {
            this.TypeID = TypeID;
        }

        public void setType(String Type) {
            this.Type = Type;
        }

        public void setAddress(Object Address) {
            this.Address = Address;
        }

        public void setPOIId(int POIId) {
            this.POIId = POIId;
        }

        public int getId() {
            return Id;
        }

        public String getName() {
            return Name;
        }

        public Object getEName() {
            return EName;
        }

        public int getDestId() {
            return DestId;
        }

        public String getDestName() {
            return DestName;
        }

        public Object getDestEName() {
            return DestEName;
        }

        public String getUrl() {
            return Url;
        }

        public int getTypeID() {
            return TypeID;
        }

        public String getType() {
            return Type;
        }

        public Object getAddress() {
            return Address;
        }

        public int getPOIId() {
            return POIId;
        }
    }
}
