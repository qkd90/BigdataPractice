package com.data.spider.service.pojo.tripAdvisor;

import java.util.List;

/**
 * Created by Sane on 16/3/1.
 */
public class Attractions {

    /**
     * previous : https://api.tripadvisor.cn/api/internal/1.8/zh_CN/location/297407/attractions?offset=50&include_rollups=true&limit=50&currency=RMB&sort=popularity&prefersaves=true&lang=zh_CN&dieroll=25
     * next : https://api.tripadvisor.cn/api/internal/1.8/zh_CN/location/297407/attractions?offset=150&include_rollups=true&limit=50&currency=RMB&sort=popularity&prefersaves=true&lang=zh_CN&dieroll=25
     * skipped : 40
     * results : 50
     * total_results : 255
     */

    private PagingEntity paging;
    /**
     * location_id : 3362020
     * name : 世贸商城
     * latitude : 24.46708
     * longitude : 118.11281
     * num_reviews : 3
     * timezone : Asia/Shanghai
     * photo : {"images":{"small":{"width":"150","url":"https://ccm.ddcdn.com/photo-l/05/16/af/5c/caption.jpg","height":"150"},"thumbnail":{"width":"50","url":"https://ccm.ddcdn.com/photo-t/05/16/af/5c/caption.jpg","height":"50"},"large":{"width":"550","url":"https://ccm.ddcdn.com/photo-s/05/16/af/5c/caption.jpg","height":"412"},"medium":{"width":"250","url":"https://ccm.ddcdn.com/photo-f/05/16/af/5c/caption.jpg","height":"187"}},"is_blessed":false,"uploaded_date":"2013-12-23T05:15:33-0500","caption":"1","id":"85372764","helpful_votes":"0","published_date":"2013-12-24T03:19:24-0500","user":{"user_id":null,"member_id":"2165606","type":"user"}}
     * api_detail_url : https://api.tripadvisor.cn/api/internal/1.8/zh_CN/location/3362020
     * awards : []
     * doubleclick_zone : as.china.fujian.xiamen
     * raw_ranking : 2.5499966144561768
     * ranking_geo : 厦门市
     * ranking_geo_id : 297407
     * ranking_position : 101
     * ranking_denominator : 256
     * ranking_category : attraction
     * ranking_subcategory : 厦门市排名第101的景点 (共256个)
     * subcategory_ranking : 厦门市排名第101的景点 (共256个)
     * ranking : 厦门市排名第101的景点 (共256个)
     * distance : null
     * bearing : null
     * rating : 3.5
     * is_closed : false
     * is_long_closed : false
     * location_string : 厦门市, 福建省
     * description :
     * web_url : http://www.tripadvisor.cn/Attraction_Review-g297407-d3362020-Reviews-Mart_Mall-Xiamen_Fujian.html
     * write_review : http://www.tripadvisor.cn/UserReview-g297407-d3362020-Mart_Mall-Xiamen_Fujian.html
     * ancestors : [{"subcategory":[{"key":"city","name":"城市"}],"name":"厦门市","abbrv":null,"location_id":"297407"},{"subcategory":[{"key":"state","name":"州"}],"name":"福建省","abbrv":null,"location_id":"297404"},{"subcategory":[{"key":"country","name":"国家"}],"name":"中国","abbrv":null,"location_id":"294211"}]
     * category : {"key":"attraction","name":"景点"}
     * subcategory : [{"key":"26","name":"购物"}]
     * parent_display_name : 厦门市
     * is_jfy_enabled : false
     * nearest_metro_station : []
     * phone : 0592-5852400
     * address_obj : {"street1":"厦禾路878-888号(近火车站)","street2":"","city":"厦门市","state":"福建省","country":"中国","postalcode":null}
     * address : 福建省 厦门市 厦禾路878-888号(近火车站)
     * subtype : [{"key":"143","name":"大型购物中心"}]
     */

    private List<DataEntity> data;

    public void setPaging(PagingEntity paging) {
        this.paging = paging;
    }

    public void setData(List<DataEntity> data) {
        this.data = data;
    }

    public PagingEntity getPaging() {
        return paging;
    }

    public List<DataEntity> getData() {
        return data;
    }

    public static class PagingEntity {
        private String previous;
        private String next;
        private String skipped;
        private String results;
        private String total_results;

        public void setPrevious(String previous) {
            this.previous = previous;
        }

        public void setNext(String next) {
            this.next = next;
        }

        public void setSkipped(String skipped) {
            this.skipped = skipped;
        }

        public void setResults(String results) {
            this.results = results;
        }

        public void setTotal_results(String total_results) {
            this.total_results = total_results;
        }

        public String getPrevious() {
            return previous;
        }

        public String getNext() {
            return next;
        }

        public String getSkipped() {
            return skipped;
        }

        public String getResults() {
            return results;
        }

        public String getTotal_results() {
            return total_results;
        }
    }

    public static class DataEntity {
        private String location_id;
        private String name;
        private String latitude;
        private String longitude;
        private String num_reviews;
        private String timezone;
        /**
         * images : {"small":{"width":"150","url":"https://ccm.ddcdn.com/photo-l/05/16/af/5c/caption.jpg","height":"150"},"thumbnail":{"width":"50","url":"https://ccm.ddcdn.com/photo-t/05/16/af/5c/caption.jpg","height":"50"},"large":{"width":"550","url":"https://ccm.ddcdn.com/photo-s/05/16/af/5c/caption.jpg","height":"412"},"medium":{"width":"250","url":"https://ccm.ddcdn.com/photo-f/05/16/af/5c/caption.jpg","height":"187"}}
         * is_blessed : false
         * uploaded_date : 2013-12-23T05:15:33-0500
         * caption : 1
         * id : 85372764
         * helpful_votes : 0
         * published_date : 2013-12-24T03:19:24-0500
         * user : {"user_id":null,"member_id":"2165606","type":"user"}
         */

        private PhotoEntity photo;
        private String api_detail_url;
        private String doubleclick_zone;
        private String raw_ranking;
        private String ranking_geo;
        private String ranking_geo_id;
        private String ranking_position;
        private String ranking_denominator;
        private String ranking_category;
        private String ranking_subcategory;
        private String subcategory_ranking;
        private String ranking;
        private Object distance;
        private Object bearing;
        private String rating;
        private boolean is_closed;
        private boolean is_long_closed;
        private String location_string;
        private String description;
        private String web_url;
        private String write_review;
        /**
         * key : attraction
         * name : 景点
         */

        private CategoryEntity category;
        private String parent_display_name;
        private boolean is_jfy_enabled;
        private String phone;
        /**
         * street1 : 厦禾路878-888号(近火车站)
         * street2 :
         * city : 厦门市
         * state : 福建省
         * country : 中国
         * postalcode : null
         */

        private AddressObjEntity address_obj;
        private String address;
        private List<?> awards;
        /**
         * subcategory : [{"key":"city","name":"城市"}]
         * name : 厦门市
         * abbrv : null
         * location_id : 297407
         */

        private List<AncestorsEntity> ancestors;
        /**
         * key : 26
         * name : 购物
         */

        private List<SubcategoryEntity> subcategory;
        private List<?> nearest_metro_station;
        /**
         * key : 143
         * name : 大型购物中心
         */

        private List<SubtypeEntity> subtype;

        public void setLocation_id(String location_id) {
            this.location_id = location_id;
        }

        public void setName(String name) {
            this.name = name;
        }

        public void setLatitude(String latitude) {
            this.latitude = latitude;
        }

        public void setLongitude(String longitude) {
            this.longitude = longitude;
        }

        public void setNum_reviews(String num_reviews) {
            this.num_reviews = num_reviews;
        }

        public void setTimezone(String timezone) {
            this.timezone = timezone;
        }

        public void setPhoto(PhotoEntity photo) {
            this.photo = photo;
        }

        public void setApi_detail_url(String api_detail_url) {
            this.api_detail_url = api_detail_url;
        }

        public void setDoubleclick_zone(String doubleclick_zone) {
            this.doubleclick_zone = doubleclick_zone;
        }

        public void setRaw_ranking(String raw_ranking) {
            this.raw_ranking = raw_ranking;
        }

        public void setRanking_geo(String ranking_geo) {
            this.ranking_geo = ranking_geo;
        }

        public void setRanking_geo_id(String ranking_geo_id) {
            this.ranking_geo_id = ranking_geo_id;
        }

        public void setRanking_position(String ranking_position) {
            this.ranking_position = ranking_position;
        }

        public void setRanking_denominator(String ranking_denominator) {
            this.ranking_denominator = ranking_denominator;
        }

        public void setRanking_category(String ranking_category) {
            this.ranking_category = ranking_category;
        }

        public void setRanking_subcategory(String ranking_subcategory) {
            this.ranking_subcategory = ranking_subcategory;
        }

        public void setSubcategory_ranking(String subcategory_ranking) {
            this.subcategory_ranking = subcategory_ranking;
        }

        public void setRanking(String ranking) {
            this.ranking = ranking;
        }

        public void setDistance(Object distance) {
            this.distance = distance;
        }

        public void setBearing(Object bearing) {
            this.bearing = bearing;
        }

        public void setRating(String rating) {
            this.rating = rating;
        }

        public void setIs_closed(boolean is_closed) {
            this.is_closed = is_closed;
        }

        public void setIs_long_closed(boolean is_long_closed) {
            this.is_long_closed = is_long_closed;
        }

        public void setLocation_string(String location_string) {
            this.location_string = location_string;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setWeb_url(String web_url) {
            this.web_url = web_url;
        }

        public void setWrite_review(String write_review) {
            this.write_review = write_review;
        }

        public void setCategory(CategoryEntity category) {
            this.category = category;
        }

        public void setParent_display_name(String parent_display_name) {
            this.parent_display_name = parent_display_name;
        }

        public void setIs_jfy_enabled(boolean is_jfy_enabled) {
            this.is_jfy_enabled = is_jfy_enabled;
        }

        public void setPhone(String phone) {
            this.phone = phone;
        }

        public void setAddress_obj(AddressObjEntity address_obj) {
            this.address_obj = address_obj;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public void setAwards(List<?> awards) {
            this.awards = awards;
        }

        public void setAncestors(List<AncestorsEntity> ancestors) {
            this.ancestors = ancestors;
        }

        public void setSubcategory(List<SubcategoryEntity> subcategory) {
            this.subcategory = subcategory;
        }

        public void setNearest_metro_station(List<?> nearest_metro_station) {
            this.nearest_metro_station = nearest_metro_station;
        }

        public void setSubtype(List<SubtypeEntity> subtype) {
            this.subtype = subtype;
        }

        public String getLocation_id() {
            return location_id;
        }

        public String getName() {
            return name;
        }

        public String getLatitude() {
            return latitude;
        }

        public String getLongitude() {
            return longitude;
        }

        public String getNum_reviews() {
            return num_reviews;
        }

        public String getTimezone() {
            return timezone;
        }

        public PhotoEntity getPhoto() {
            return photo;
        }

        public String getApi_detail_url() {
            return api_detail_url;
        }

        public String getDoubleclick_zone() {
            return doubleclick_zone;
        }

        public String getRaw_ranking() {
            return raw_ranking;
        }

        public String getRanking_geo() {
            return ranking_geo;
        }

        public String getRanking_geo_id() {
            return ranking_geo_id;
        }

        public String getRanking_position() {
            return ranking_position;
        }

        public String getRanking_denominator() {
            return ranking_denominator;
        }

        public String getRanking_category() {
            return ranking_category;
        }

        public String getRanking_subcategory() {
            return ranking_subcategory;
        }

        public String getSubcategory_ranking() {
            return subcategory_ranking;
        }

        public String getRanking() {
            return ranking;
        }

        public Object getDistance() {
            return distance;
        }

        public Object getBearing() {
            return bearing;
        }

        public String getRating() {
            return rating;
        }

        public boolean isIs_closed() {
            return is_closed;
        }

        public boolean isIs_long_closed() {
            return is_long_closed;
        }

        public String getLocation_string() {
            return location_string;
        }

        public String getDescription() {
            return description;
        }

        public String getWeb_url() {
            return web_url;
        }

        public String getWrite_review() {
            return write_review;
        }

        public CategoryEntity getCategory() {
            return category;
        }

        public String getParent_display_name() {
            return parent_display_name;
        }

        public boolean isIs_jfy_enabled() {
            return is_jfy_enabled;
        }

        public String getPhone() {
            return phone;
        }

        public AddressObjEntity getAddress_obj() {
            return address_obj;
        }

        public String getAddress() {
            return address;
        }

        public List<?> getAwards() {
            return awards;
        }

        public List<AncestorsEntity> getAncestors() {
            return ancestors;
        }

        public List<SubcategoryEntity> getSubcategory() {
            return subcategory;
        }

        public List<?> getNearest_metro_station() {
            return nearest_metro_station;
        }

        public List<SubtypeEntity> getSubtype() {
            return subtype;
        }

        public static class PhotoEntity {
            /**
             * small : {"width":"150","url":"https://ccm.ddcdn.com/photo-l/05/16/af/5c/caption.jpg","height":"150"}
             * thumbnail : {"width":"50","url":"https://ccm.ddcdn.com/photo-t/05/16/af/5c/caption.jpg","height":"50"}
             * large : {"width":"550","url":"https://ccm.ddcdn.com/photo-s/05/16/af/5c/caption.jpg","height":"412"}
             * medium : {"width":"250","url":"https://ccm.ddcdn.com/photo-f/05/16/af/5c/caption.jpg","height":"187"}
             */

            private ImagesEntity images;
            private boolean is_blessed;
            private String uploaded_date;
            private String caption;
            private String id;
            private String helpful_votes;
            private String published_date;
            /**
             * user_id : null
             * member_id : 2165606
             * type : user
             */

            private UserEntity user;

            public void setImages(ImagesEntity images) {
                this.images = images;
            }

            public void setIs_blessed(boolean is_blessed) {
                this.is_blessed = is_blessed;
            }

            public void setUploaded_date(String uploaded_date) {
                this.uploaded_date = uploaded_date;
            }

            public void setCaption(String caption) {
                this.caption = caption;
            }

            public void setId(String id) {
                this.id = id;
            }

            public void setHelpful_votes(String helpful_votes) {
                this.helpful_votes = helpful_votes;
            }

            public void setPublished_date(String published_date) {
                this.published_date = published_date;
            }

            public void setUser(UserEntity user) {
                this.user = user;
            }

            public ImagesEntity getImages() {
                return images;
            }

            public boolean isIs_blessed() {
                return is_blessed;
            }

            public String getUploaded_date() {
                return uploaded_date;
            }

            public String getCaption() {
                return caption;
            }

            public String getId() {
                return id;
            }

            public String getHelpful_votes() {
                return helpful_votes;
            }

            public String getPublished_date() {
                return published_date;
            }

            public UserEntity getUser() {
                return user;
            }

            public static class ImagesEntity {
                /**
                 * width : 150
                 * url : https://ccm.ddcdn.com/photo-l/05/16/af/5c/caption.jpg
                 * height : 150
                 */

                private SmallEntity small;
                /**
                 * width : 50
                 * url : https://ccm.ddcdn.com/photo-t/05/16/af/5c/caption.jpg
                 * height : 50
                 */

                private ThumbnailEntity thumbnail;
                /**
                 * width : 550
                 * url : https://ccm.ddcdn.com/photo-s/05/16/af/5c/caption.jpg
                 * height : 412
                 */

                private LargeEntity large;
                /**
                 * width : 250
                 * url : https://ccm.ddcdn.com/photo-f/05/16/af/5c/caption.jpg
                 * height : 187
                 */

                private MediumEntity medium;

                public void setSmall(SmallEntity small) {
                    this.small = small;
                }

                public void setThumbnail(ThumbnailEntity thumbnail) {
                    this.thumbnail = thumbnail;
                }

                public void setLarge(LargeEntity large) {
                    this.large = large;
                }

                public void setMedium(MediumEntity medium) {
                    this.medium = medium;
                }

                public SmallEntity getSmall() {
                    return small;
                }

                public ThumbnailEntity getThumbnail() {
                    return thumbnail;
                }

                public LargeEntity getLarge() {
                    return large;
                }

                public MediumEntity getMedium() {
                    return medium;
                }

                public static class SmallEntity {
                    private String width;
                    private String url;
                    private String height;

                    public void setWidth(String width) {
                        this.width = width;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public void setHeight(String height) {
                        this.height = height;
                    }

                    public String getWidth() {
                        return width;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public String getHeight() {
                        return height;
                    }
                }

                public static class ThumbnailEntity {
                    private String width;
                    private String url;
                    private String height;

                    public void setWidth(String width) {
                        this.width = width;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public void setHeight(String height) {
                        this.height = height;
                    }

                    public String getWidth() {
                        return width;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public String getHeight() {
                        return height;
                    }
                }

                public static class LargeEntity {
                    private String width;
                    private String url;
                    private String height;

                    public void setWidth(String width) {
                        this.width = width;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public void setHeight(String height) {
                        this.height = height;
                    }

                    public String getWidth() {
                        return width;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public String getHeight() {
                        return height;
                    }
                }

                public static class MediumEntity {
                    private String width;
                    private String url;
                    private String height;

                    public void setWidth(String width) {
                        this.width = width;
                    }

                    public void setUrl(String url) {
                        this.url = url;
                    }

                    public void setHeight(String height) {
                        this.height = height;
                    }

                    public String getWidth() {
                        return width;
                    }

                    public String getUrl() {
                        return url;
                    }

                    public String getHeight() {
                        return height;
                    }
                }
            }

            public static class UserEntity {
                private Object user_id;
                private String member_id;
                private String type;

                public void setUser_id(Object user_id) {
                    this.user_id = user_id;
                }

                public void setMember_id(String member_id) {
                    this.member_id = member_id;
                }

                public void setType(String type) {
                    this.type = type;
                }

                public Object getUser_id() {
                    return user_id;
                }

                public String getMember_id() {
                    return member_id;
                }

                public String getType() {
                    return type;
                }
            }
        }

        public static class CategoryEntity {
            private String key;
            private String name;

            public void setKey(String key) {
                this.key = key;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getKey() {
                return key;
            }

            public String getName() {
                return name;
            }
        }

        public static class AddressObjEntity {
            private String street1;
            private String street2;
            private String city;
            private String state;
            private String country;
            private Object postalcode;

            public void setStreet1(String street1) {
                this.street1 = street1;
            }

            public void setStreet2(String street2) {
                this.street2 = street2;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public void setState(String state) {
                this.state = state;
            }

            public void setCountry(String country) {
                this.country = country;
            }

            public void setPostalcode(Object postalcode) {
                this.postalcode = postalcode;
            }

            public String getStreet1() {
                return street1;
            }

            public String getStreet2() {
                return street2;
            }

            public String getCity() {
                return city;
            }

            public String getState() {
                return state;
            }

            public String getCountry() {
                return country;
            }

            public Object getPostalcode() {
                return postalcode;
            }
        }

        public static class AncestorsEntity {
            private String name;
            private Object abbrv;
            private String location_id;
            /**
             * key : city
             * name : 城市
             */

            private List<SubcategoryEntity> subcategory;

            public void setName(String name) {
                this.name = name;
            }

            public void setAbbrv(Object abbrv) {
                this.abbrv = abbrv;
            }

            public void setLocation_id(String location_id) {
                this.location_id = location_id;
            }

            public void setSubcategory(List<SubcategoryEntity> subcategory) {
                this.subcategory = subcategory;
            }

            public String getName() {
                return name;
            }

            public Object getAbbrv() {
                return abbrv;
            }

            public String getLocation_id() {
                return location_id;
            }

            public List<SubcategoryEntity> getSubcategory() {
                return subcategory;
            }

            public static class SubcategoryEntity {
                private String key;
                private String name;

                public void setKey(String key) {
                    this.key = key;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public String getKey() {
                    return key;
                }

                public String getName() {
                    return name;
                }
            }
        }

        public static class SubcategoryEntity {
            private String key;
            private String name;

            public void setKey(String key) {
                this.key = key;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getKey() {
                return key;
            }

            public String getName() {
                return name;
            }
        }

        public static class SubtypeEntity {
            private String key;
            private String name;

            public void setKey(String key) {
                this.key = key;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getKey() {
                return key;
            }

            public String getName() {
                return name;
            }
        }
    }
}
