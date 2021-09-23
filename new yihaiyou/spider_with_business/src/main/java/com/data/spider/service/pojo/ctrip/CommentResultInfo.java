package com.data.spider.service.pojo.ctrip;

import java.util.List;

/**
 * Created by Sane on 16/5/5.
 */
public class CommentResultInfo {

    /**
     * Timestamp : /Date(1462414467103+0800)/
     * Ack : Success
     * Errors : []
     * Build : null
     * Version : null
     * Extension : [{"Id":"CLOGGING_TRACE_ID","Version":null,"ContentType":null,"Value":"2556640950681904759"}]
     */

    private ResponseStatusEntity ResponseStatus;


    private CommentResultEntity CommentResult;

    public ResponseStatusEntity getResponseStatus() {
        return ResponseStatus;
    }

    public void setResponseStatus(ResponseStatusEntity ResponseStatus) {
        this.ResponseStatus = ResponseStatus;
    }

    public CommentResultEntity getCommentResult() {
        return CommentResult;
    }

    public void setCommentResult(CommentResultEntity CommentResult) {
        this.CommentResult = CommentResult;
    }

    public static class ResponseStatusEntity {
        private String Timestamp;
        private String Ack;
        /**
         * Id : CLOGGING_TRACE_ID
         * Version : null
         * ContentType : null
         * Value : 2556640950681904759
         */

        private List<ExtensionEntity> Extension;

        public String getTimestamp() {
            return Timestamp;
        }

        public void setTimestamp(String Timestamp) {
            this.Timestamp = Timestamp;
        }

        public String getAck() {
            return Ack;
        }

        public void setAck(String Ack) {
            this.Ack = Ack;
        }


        public List<ExtensionEntity> getExtension() {
            return Extension;
        }

        public void setExtension(List<ExtensionEntity> Extension) {
            this.Extension = Extension;
        }

        public static class ExtensionEntity {
            private String Id;
            private Object Version;
            private Object ContentType;
            private String Value;

            public String getId() {
                return Id;
            }

            public void setId(String Id) {
                this.Id = Id;
            }

            public Object getVersion() {
                return Version;
            }

            public void setVersion(Object Version) {
                this.Version = Version;
            }

            public Object getContentType() {
                return ContentType;
            }

            public void setContentType(Object ContentType) {
                this.ContentType = ContentType;
            }

            public String getValue() {
                return Value;
            }

            public void setValue(String Value) {
                this.Value = Value;
            }
        }
    }

    public static class CommentResultEntity {
        private long TotalCount;


        private List<CommentInfoEntity> CommentInfo;

        public long getTotalCount() {
            return TotalCount;
        }

        public void setTotalCount(long TotalCount) {
            this.TotalCount = TotalCount;
        }

        public List<CommentInfoEntity> getCommentInfo() {
            return CommentInfo;
        }

        public void setCommentInfo(List<CommentInfoEntity> CommentInfo) {
            this.CommentInfo = CommentInfo;
        }

        public static class CommentInfoEntity {
            private long CommentId;
            private long POIId;
            private long DistrictId;
            private String POIName;
            private long ResourceId;
            private long ResourceType;
            private long BusinessId;
            private long BusinessType;
            private Object BusinessTitle;
            private Object BusinessUrl;
            private long UserId;
            private double TotalStar;
            private long TouristType;
            private long PlayYear;
            private long PlayMonth;
            private String Content;
            private long ContentLength;
            private long UsefulCount;
            private boolean UserfulWhether;
            private boolean IsRecommend;
            private boolean IsPicked;
            private String IP;
            private long Platform;
            private Object PlatformVersion;
            private long SourceType;
            private long ParentCommentId;
            private String PublishTime;
            private long PublishStatus;
            private String Auditor;
            private String AuditTime;
            private String LastModifyTime;
            /**
             * SightStar : 5
             * longerestStar : 5
             * CostPerformanceStar : 5
             * PlayTime : 0.0
             * PlayType : 2
             */

            private SightExtInfoEntity SightExtInfo;
            private Object EntertainmentExtInfo;
            private Object RestaurantExtInfo;
            private Object ShopExtInfo;
            /**
             * UID : 13570326907
             * Auth : null
             * UserId : 17777023
             * UserNick : 伴你同行
             * UserImageSrc : http://images4.c-ctrip.com/target/fd/headphoto/g2/M03/AC/3D/CghzgVW6OTSACnrKAADuaDz9faQ582_50_50.jpg
             * UserHomeUrl : 4001B4040A2046DC946EAFFFAFD904DA
             * UserDistrictName : 广州
             * MedalName : null
             * IdentitiesName : null
             * UserMedalInfos : [{"MedalName":"有头有脸","Level":0,"MedalType":8,"IconUrl":"http://pages.ctrip.com/honor/title/face.png","IsGrowUp":0},{"MedalName":"初露锋芒","Level":0,"MedalType":9,"IconUrl":"http://pages.ctrip.com/honor/title/newer.png","IsGrowUp":0}]
             */

            private UserInfoModelEntity UserInfoModel;
            private long ExternalResourceId;
            /**
             * ImageId : 59470137
             * Name : null
             * UploadTime : /Date(-62135596800000-0000)/
             * PhotoPath : http://dimg02.c-ctrip.com/images/fd/tg/g4/M05/8A/66/CggYHVZKAF-AZu0jAAFlJSMu3NQ380_R_640_10000.jpg
             * ThumbnailUrl : http://dimg02.c-ctrip.com/images/fd/tg/g4/M05/8A/66/CggYHVZKAF-AZu0jAAFlJSMu3NQ380_R_180_180.jpg
             */

            private List<ImagesEntity> Images;

            public long getCommentId() {
                return CommentId;
            }

            public void setCommentId(long CommentId) {
                this.CommentId = CommentId;
            }

            public long getPOIId() {
                return POIId;
            }

            public void setPOIId(long POIId) {
                this.POIId = POIId;
            }

            public long getDistrictId() {
                return DistrictId;
            }

            public void setDistrictId(long DistrictId) {
                this.DistrictId = DistrictId;
            }

            public String getPOIName() {
                return POIName;
            }

            public void setPOIName(String POIName) {
                this.POIName = POIName;
            }

            public long getResourceId() {
                return ResourceId;
            }

            public void setResourceId(long ResourceId) {
                this.ResourceId = ResourceId;
            }

            public long getResourceType() {
                return ResourceType;
            }

            public void setResourceType(long ResourceType) {
                this.ResourceType = ResourceType;
            }

            public long getBusinessId() {
                return BusinessId;
            }

            public void setBusinessId(long BusinessId) {
                this.BusinessId = BusinessId;
            }

            public long getBusinessType() {
                return BusinessType;
            }

            public void setBusinessType(long BusinessType) {
                this.BusinessType = BusinessType;
            }

            public Object getBusinessTitle() {
                return BusinessTitle;
            }

            public void setBusinessTitle(Object BusinessTitle) {
                this.BusinessTitle = BusinessTitle;
            }

            public Object getBusinessUrl() {
                return BusinessUrl;
            }

            public void setBusinessUrl(Object BusinessUrl) {
                this.BusinessUrl = BusinessUrl;
            }

            public long getUserId() {
                return UserId;
            }

            public void setUserId(long UserId) {
                this.UserId = UserId;
            }

            public double getTotalStar() {
                return TotalStar;
            }

            public void setTotalStar(double TotalStar) {
                this.TotalStar = TotalStar;
            }

            public long getTouristType() {
                return TouristType;
            }

            public void setTouristType(long TouristType) {
                this.TouristType = TouristType;
            }

            public long getPlayYear() {
                return PlayYear;
            }

            public void setPlayYear(long PlayYear) {
                this.PlayYear = PlayYear;
            }

            public long getPlayMonth() {
                return PlayMonth;
            }

            public void setPlayMonth(long PlayMonth) {
                this.PlayMonth = PlayMonth;
            }

            public String getContent() {
                return Content;
            }

            public void setContent(String Content) {
                this.Content = Content;
            }

            public long getContentLength() {
                return ContentLength;
            }

            public void setContentLength(long ContentLength) {
                this.ContentLength = ContentLength;
            }

            public long getUsefulCount() {
                return UsefulCount;
            }

            public void setUsefulCount(long UsefulCount) {
                this.UsefulCount = UsefulCount;
            }

            public boolean isUserfulWhether() {
                return UserfulWhether;
            }

            public void setUserfulWhether(boolean UserfulWhether) {
                this.UserfulWhether = UserfulWhether;
            }

            public boolean isIsRecommend() {
                return IsRecommend;
            }

            public void setIsRecommend(boolean IsRecommend) {
                this.IsRecommend = IsRecommend;
            }

            public boolean isIsPicked() {
                return IsPicked;
            }

            public void setIsPicked(boolean IsPicked) {
                this.IsPicked = IsPicked;
            }

            public String getIP() {
                return IP;
            }

            public void setIP(String IP) {
                this.IP = IP;
            }

            public long getPlatform() {
                return Platform;
            }

            public void setPlatform(long Platform) {
                this.Platform = Platform;
            }

            public Object getPlatformVersion() {
                return PlatformVersion;
            }

            public void setPlatformVersion(Object PlatformVersion) {
                this.PlatformVersion = PlatformVersion;
            }

            public long getSourceType() {
                return SourceType;
            }

            public void setSourceType(long SourceType) {
                this.SourceType = SourceType;
            }

            public long getParentCommentId() {
                return ParentCommentId;
            }

            public void setParentCommentId(long ParentCommentId) {
                this.ParentCommentId = ParentCommentId;
            }

            public String getPublishTime() {
                return PublishTime;
            }

            public void setPublishTime(String PublishTime) {
                this.PublishTime = PublishTime;
            }

            public long getPublishStatus() {
                return PublishStatus;
            }

            public void setPublishStatus(long PublishStatus) {
                this.PublishStatus = PublishStatus;
            }

            public String getAuditor() {
                return Auditor;
            }

            public void setAuditor(String Auditor) {
                this.Auditor = Auditor;
            }

            public String getAuditTime() {
                return AuditTime;
            }

            public void setAuditTime(String AuditTime) {
                this.AuditTime = AuditTime;
            }

            public String getLastModifyTime() {
                return LastModifyTime;
            }

            public void setLastModifyTime(String LastModifyTime) {
                this.LastModifyTime = LastModifyTime;
            }

            public SightExtInfoEntity getSightExtInfo() {
                return SightExtInfo;
            }

            public void setSightExtInfo(SightExtInfoEntity SightExtInfo) {
                this.SightExtInfo = SightExtInfo;
            }

            public Object getEntertainmentExtInfo() {
                return EntertainmentExtInfo;
            }

            public void setEntertainmentExtInfo(Object EntertainmentExtInfo) {
                this.EntertainmentExtInfo = EntertainmentExtInfo;
            }

            public Object getRestaurantExtInfo() {
                return RestaurantExtInfo;
            }

            public void setRestaurantExtInfo(Object RestaurantExtInfo) {
                this.RestaurantExtInfo = RestaurantExtInfo;
            }

            public Object getShopExtInfo() {
                return ShopExtInfo;
            }

            public void setShopExtInfo(Object ShopExtInfo) {
                this.ShopExtInfo = ShopExtInfo;
            }

            public UserInfoModelEntity getUserInfoModel() {
                return UserInfoModel;
            }

            public void setUserInfoModel(UserInfoModelEntity UserInfoModel) {
                this.UserInfoModel = UserInfoModel;
            }

            public long getExternalResourceId() {
                return ExternalResourceId;
            }

            public void setExternalResourceId(long ExternalResourceId) {
                this.ExternalResourceId = ExternalResourceId;
            }

            public List<ImagesEntity> getImages() {
                return Images;
            }

            public void setImages(List<ImagesEntity> Images) {
                this.Images = Images;
            }

            public static class SightExtInfoEntity {
                private long SightStar;
                private long longerestStar;
                private long CostPerformanceStar;
                private double PlayTime;
                private long PlayType;

                public long getSightStar() {
                    return SightStar;
                }

                public void setSightStar(long SightStar) {
                    this.SightStar = SightStar;
                }

                public long getlongerestStar() {
                    return longerestStar;
                }

                public void setlongerestStar(long longerestStar) {
                    this.longerestStar = longerestStar;
                }

                public long getCostPerformanceStar() {
                    return CostPerformanceStar;
                }

                public void setCostPerformanceStar(long CostPerformanceStar) {
                    this.CostPerformanceStar = CostPerformanceStar;
                }

                public double getPlayTime() {
                    return PlayTime;
                }

                public void setPlayTime(double PlayTime) {
                    this.PlayTime = PlayTime;
                }

                public long getPlayType() {
                    return PlayType;
                }

                public void setPlayType(long PlayType) {
                    this.PlayType = PlayType;
                }
            }

            public static class UserInfoModelEntity {
                private String UID;
                private Object Auth;
                private long UserId;
                private String UserNick;
                private String UserImageSrc;
                private String UserHomeUrl;
                private String UserDistrictName;
                private Object MedalName;
                private Object IdentitiesName;
                /**
                 * MedalName : 有头有脸
                 * Level : 0
                 * MedalType : 8
                 * IconUrl : http://pages.ctrip.com/honor/title/face.png
                 * IsGrowUp : 0
                 */

                private List<UserMedalInfosEntity> UserMedalInfos;

                public String getUID() {
                    return UID;
                }

                public void setUID(String UID) {
                    this.UID = UID;
                }

                public Object getAuth() {
                    return Auth;
                }

                public void setAuth(Object Auth) {
                    this.Auth = Auth;
                }

                public long getUserId() {
                    return UserId;
                }

                public void setUserId(long UserId) {
                    this.UserId = UserId;
                }

                public String getUserNick() {
                    return UserNick;
                }

                public void setUserNick(String UserNick) {
                    this.UserNick = UserNick;
                }

                public String getUserImageSrc() {
                    return UserImageSrc;
                }

                public void setUserImageSrc(String UserImageSrc) {
                    this.UserImageSrc = UserImageSrc;
                }

                public String getUserHomeUrl() {
                    return UserHomeUrl;
                }

                public void setUserHomeUrl(String UserHomeUrl) {
                    this.UserHomeUrl = UserHomeUrl;
                }

                public String getUserDistrictName() {
                    return UserDistrictName;
                }

                public void setUserDistrictName(String UserDistrictName) {
                    this.UserDistrictName = UserDistrictName;
                }

                public Object getMedalName() {
                    return MedalName;
                }

                public void setMedalName(Object MedalName) {
                    this.MedalName = MedalName;
                }

                public Object getIdentitiesName() {
                    return IdentitiesName;
                }

                public void setIdentitiesName(Object IdentitiesName) {
                    this.IdentitiesName = IdentitiesName;
                }

                public List<UserMedalInfosEntity> getUserMedalInfos() {
                    return UserMedalInfos;
                }

                public void setUserMedalInfos(List<UserMedalInfosEntity> UserMedalInfos) {
                    this.UserMedalInfos = UserMedalInfos;
                }

                public static class UserMedalInfosEntity {
                    private String MedalName;
                    private long Level;
                    private long MedalType;
                    private String IconUrl;
                    private long IsGrowUp;

                    public String getMedalName() {
                        return MedalName;
                    }

                    public void setMedalName(String MedalName) {
                        this.MedalName = MedalName;
                    }

                    public long getLevel() {
                        return Level;
                    }

                    public void setLevel(long Level) {
                        this.Level = Level;
                    }

                    public long getMedalType() {
                        return MedalType;
                    }

                    public void setMedalType(long MedalType) {
                        this.MedalType = MedalType;
                    }

                    public String getIconUrl() {
                        return IconUrl;
                    }

                    public void setIconUrl(String IconUrl) {
                        this.IconUrl = IconUrl;
                    }

                    public long getIsGrowUp() {
                        return IsGrowUp;
                    }

                    public void setIsGrowUp(long IsGrowUp) {
                        this.IsGrowUp = IsGrowUp;
                    }
                }
            }

            public static class ImagesEntity {
                private long ImageId;
                private Object Name;
                private String UploadTime;
                private String PhotoPath;
                private String ThumbnailUrl;

                public long getImageId() {
                    return ImageId;
                }

                public void setImageId(long ImageId) {
                    this.ImageId = ImageId;
                }

                public Object getName() {
                    return Name;
                }

                public void setName(Object Name) {
                    this.Name = Name;
                }

                public String getUploadTime() {
                    return UploadTime;
                }

                public void setUploadTime(String UploadTime) {
                    this.UploadTime = UploadTime;
                }

                public String getPhotoPath() {
                    return PhotoPath;
                }

                public void setPhotoPath(String PhotoPath) {
                    this.PhotoPath = PhotoPath;
                }

                public String getThumbnailUrl() {
                    return ThumbnailUrl;
                }

                public void setThumbnailUrl(String ThumbnailUrl) {
                    this.ThumbnailUrl = ThumbnailUrl;
                }
            }
        }
    }
}
