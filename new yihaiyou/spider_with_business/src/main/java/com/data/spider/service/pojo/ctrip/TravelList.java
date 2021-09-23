package com.data.spider.service.pojo.ctrip;

import java.util.List;

/**
 * Created by Sane on 16/1/14.
 */
public class TravelList {

    public static class ResponseStatusEntity {
        private String Timestamp;
        private String Ack;
        private String Build;
        private String Version;
        private List<?> Errors;
        /**
         * Id : CLOGGING_TRACE_ID
         * Value : 5115863758980103840
         */

        private List<ExtensionEntity> Extension;

        public void setTimestamp(String Timestamp) {
            this.Timestamp = Timestamp;
        }

        public void setAck(String Ack) {
            this.Ack = Ack;
        }

        public void setBuild(String Build) {
            this.Build = Build;
        }

        public void setVersion(String Version) {
            this.Version = Version;
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

        public String getBuild() {
            return Build;
        }

        public String getVersion() {
            return Version;
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

    private ResponseStatusEntity ResponseStatus;

    public ResponseStatusEntity getResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(ResponseStatusEntity responseStatus) {
        ResponseStatus = responseStatus;
    }

    /**
     * Result : [{"Id":2635099,"TravelType":1}]
     * TotalCount : 447
     * DistrictName : 衢州
     */

    private int TotalCount;
    private String DistrictName;
    /**
     * Id : 2635099
     * TravelType : 1
     */

    private List<ResultEntity> Result;

    public void setTotalCount(int TotalCount) {
        this.TotalCount = TotalCount;
    }

    public void setDistrictName(String DistrictName) {
        this.DistrictName = DistrictName;
    }

    public void setResult(List<ResultEntity> Result) {
        this.Result = Result;
    }

    public int getTotalCount() {
        return TotalCount;
    }

    public String getDistrictName() {
        return DistrictName;
    }

    public List<ResultEntity> getResult() {
        return Result;
    }

    public static class ResultEntity {
        private int Id;
        private int TravelType;
        private String Title;

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public void setTravelType(int TravelType) {
            this.TravelType = TravelType;
        }

        public int getId() {
            return Id;
        }

        public int getTravelType() {
            return TravelType;
        }
    }
}
