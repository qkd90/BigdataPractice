package com.data.spider.service.pojo.ctrip;

import java.util.List;

/**
 * Created by Sane on 16/1/25.
 */
public class PoiTip {

    /**
     * Timestamp : /Date(1453693920514+0800)/
     * Ack : Success
     * Errors : []
     * Extension : [{"Id":"CLOGGING_TRACE_ID","Value":"158404917040610974"}]
     */

    private ResponseStatusEntity ResponseStatus;
    /**
     * PoiId : 0
     * PoiName : 福建
     * PoiType : DISTRICT
     * ResourceId : 100038
     * DistrictId : 110000
     * DistrictName : 中国
     */

    private List<PoiListEntity> PoiList;

    public void setResponseStatus(ResponseStatusEntity ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }

    public void setPoiList(List<PoiListEntity> PoiList) {
        this.PoiList = PoiList;
    }

    public ResponseStatusEntity getResponseStatus() {
        return ResponseStatus;
    }

    public List<PoiListEntity> getPoiList() {
        return PoiList;
    }

    public static class ResponseStatusEntity {
        private String Timestamp;
        private String Ack;
        private List<?> Errors;
        /**
         * Id : CLOGGING_TRACE_ID
         * Value : 158404917040610974
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

    public static class PoiListEntity {
        private int PoiId;
        private String PoiName;
        private String PoiType;
        private int ResourceId;
        private int DistrictId;
        private String DistrictName;

        public void setPoiId(int PoiId) {
            this.PoiId = PoiId;
        }

        public void setPoiName(String PoiName) {
            this.PoiName = PoiName;
        }

        public void setPoiType(String PoiType) {
            this.PoiType = PoiType;
        }

        public void setResourceId(int ResourceId) {
            this.ResourceId = ResourceId;
        }

        public void setDistrictId(int DistrictId) {
            this.DistrictId = DistrictId;
        }

        public void setDistrictName(String DistrictName) {
            this.DistrictName = DistrictName;
        }

        public int getPoiId() {
            return PoiId;
        }

        public String getPoiName() {
            return PoiName;
        }

        public String getPoiType() {
            return PoiType;
        }

        public int getResourceId() {
            return ResourceId;
        }

        public int getDistrictId() {
            return DistrictId;
        }

        public String getDistrictName() {
            return DistrictName;
        }
    }
}
