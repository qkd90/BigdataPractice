package com.data.spider.service.pojo.mfw;

import java.util.List;

/**
 * Created by Sane on 16/2/18.
 */
public class TravelnoteDetail {


    private DataEntity data;

    private int is_array;
    private int rc;
    private String rm;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setIs_array(int is_array) {
        this.is_array = is_array;
    }

    public void setRc(int rc) {
        this.rc = rc;
    }

    public void setRm(String rm) {
        this.rm = rm;
    }

    public DataEntity getData() {
        return data;
    }

    public int getIs_array() {
        return is_array;
    }

    public int getRc() {
        return rc;
    }

    public String getRm() {
        return rm;
    }

    public static class DataEntity {
        private int id;
        private int is_travel;
        private String title;
        private String ctime;
        private String mtime;
        private String headimg;
        private String oimgurl;
        private Ex ex;

        public Ex getEx() {
            return ex;
        }

        public void setEx(Ex ex) {
            this.ex = ex;
        }

        /**
         * id : 35153280
         * name : 晓圆
         * gender : 0
         * city : 南京
         * logo : mfwStorage6/M00/70/6F/wKgB4lIx8NGAERz0AAGX8ygtwgc69.head.jpeg
         * logo_60 : http://file26.mafengwo.net/M00/70/6F/wKgB4lIx8NGAERz0AAGX8ygtwgc69.head.w60.jpeg
         * logo_120 : http://file26.mafengwo.net/M00/70/6F/wKgB4lIx8NGAERz0AAGX8ygtwgc69.head.w120.jpeg
         * user_score : 4095
         * user_lv : 16
         * url : http://m.mafengwo.cn/user/?id=35153280
         */

        public class Ex {
            /**
             * sdate : 2014/10/25
             * days : 9
             * who : 和朋友
             * travelstyle : 自由行
             * cost : 8000
             * $aScheduleInfo : {"id":751936,"iid":3402808,"uid":35153280,"title":"台北,淡水,九份,花莲,垦丁9日游","start_day":20141025,"days_count":9,"from_mddid":10684,"to_mddid":0,"day_ids":["751937","751938","751960","751941","751943","751944","751946","751947","751948"],"status":2,"ctime":"2015-06-16 21:52:07","pubtime":"2015-06-17 00:43:19","judgement":"","is_del":0,"flag":0,"cover_img":"","title_setted":1,"info_setted":1,"recommend":0,"exp_honored":1,"src_platform":0,"src_app":0,"is_sample":0,"for_note_catalog":0,"scheduleid":751936,"person":5,"expense":8000,"style_ids":[1],"baggage_ids":[],"up_num":0,"visit_num":3,"share_num":0,"mdd_pois":"","baggage_infos":[],"from_mddname":"南京","personname":"和朋友","stylename":"自由行","day_poi_summary":[["新驛旅店台北車站三館(CityInn Hotel Taipei Station Branch III)"],["新驛旅店台北車站三館(CityInn Hotel Taipei Station Branch III)"],["台北","九份"],["九份","花莲"],["花莲"],["花莲","垦丁"],["垦丁"],["垦丁","台北"],["台北","南京"]]}
             * is_top : 0
             * is_elite : 0
             * is_focus : 1
             */

            private String sdate;
            private int days;
            private String who;
            private String travelstyle;
            private int cost;
            /**
             * id : 751936
             * iid : 3402808
             * uid : 35153280
             * title : 台北,淡水,九份,花莲,垦丁9日游
             * start_day : 20141025
             * days_count : 9
             * from_mddid : 10684
             * to_mddid : 0
             * day_ids : ["751937","751938","751960","751941","751943","751944","751946","751947","751948"]
             * status : 2
             * ctime : 2015-06-16 21:52:07
             * pubtime : 2015-06-17 00:43:19
             * judgement :
             * is_del : 0
             * flag : 0
             * cover_img :
             * title_setted : 1
             * info_setted : 1
             * recommend : 0
             * exp_honored : 1
             * src_platform : 0
             * src_app : 0
             * is_sample : 0
             * for_note_catalog : 0
             * scheduleid : 751936
             * person : 5
             * expense : 8000
             * style_ids : [1]
             * baggage_ids : []
             * up_num : 0
             * visit_num : 3
             * share_num : 0
             * mdd_pois :
             * baggage_infos : []
             * from_mddname : 南京
             * personname : 和朋友
             * stylename : 自由行
             * day_poi_summary : [["新驛旅店台北車站三館(CityInn Hotel Taipei Station Branch III)"],["新驛旅店台北車站三館(CityInn Hotel Taipei Station Branch III)"],["台北","九份"],["九份","花莲"],["花莲"],["花莲","垦丁"],["垦丁"],["垦丁","台北"],["台北","南京"]]
             */

            private $aScheduleInfoEntity $aScheduleInfo;
            private int is_top;
            private int is_elite;
            private int is_focus;

            public void setSdate(String sdate) {
                this.sdate = sdate;
            }

            public void setDays(int days) {
                this.days = days;
            }

            public void setWho(String who) {
                this.who = who;
            }

            public void setTravelstyle(String travelstyle) {
                this.travelstyle = travelstyle;
            }

            public void setCost(int cost) {
                this.cost = cost;
            }

            public void set$aScheduleInfo($aScheduleInfoEntity $aScheduleInfo) {
                this.$aScheduleInfo = $aScheduleInfo;
            }

            public void setIs_top(int is_top) {
                this.is_top = is_top;
            }

            public void setIs_elite(int is_elite) {
                this.is_elite = is_elite;
            }

            public void setIs_focus(int is_focus) {
                this.is_focus = is_focus;
            }

            public String getSdate() {
                return sdate;
            }

            public int getDays() {
                return days;
            }

            public String getWho() {
                return who;
            }

            public String getTravelstyle() {
                return travelstyle;
            }

            public int getCost() {
                return cost;
            }

            public $aScheduleInfoEntity get$aScheduleInfo() {
                return $aScheduleInfo;
            }

            public int getIs_top() {
                return is_top;
            }

            public int getIs_elite() {
                return is_elite;
            }

            public int getIs_focus() {
                return is_focus;
            }

            public class $aScheduleInfoEntity {
                private int id;
                private int iid;
                private int uid;
                private String title;
                private int start_day;
                private int days_count;
                private int from_mddid;
                private int to_mddid;
                private int status;
                private String ctime;
                private String pubtime;
                private String judgement;
                private int is_del;
                private int flag;
                private String cover_img;
                private int title_setted;
                private int info_setted;
                private int recommend;
                private int exp_honored;
                private int src_platform;
                private int src_app;
                private int is_sample;
                private int for_note_catalog;
                private int scheduleid;
                private int person;
                private int expense;
                private int up_num;
                private int visit_num;
                private int share_num;
                private String mdd_pois;
                private String from_mddname;
                private String personname;
                private String stylename;
                private List<String> day_ids;
                private List<Integer> style_ids;
                //                private List<?> baggage_ids;
//                private List<?> baggage_infos;
                private List<List<String>> day_poi_summary;

                public void setId(int id) {
                    this.id = id;
                }

                public void setIid(int iid) {
                    this.iid = iid;
                }

                public void setUid(int uid) {
                    this.uid = uid;
                }

                public void setTitle(String title) {
                    this.title = title;
                }

                public void setStart_day(int start_day) {
                    this.start_day = start_day;
                }

                public void setDays_count(int days_count) {
                    this.days_count = days_count;
                }

                public void setFrom_mddid(int from_mddid) {
                    this.from_mddid = from_mddid;
                }

                public void setTo_mddid(int to_mddid) {
                    this.to_mddid = to_mddid;
                }

                public void setStatus(int status) {
                    this.status = status;
                }

                public void setCtime(String ctime) {
                    this.ctime = ctime;
                }

                public void setPubtime(String pubtime) {
                    this.pubtime = pubtime;
                }

                public void setJudgement(String judgement) {
                    this.judgement = judgement;
                }

                public void setIs_del(int is_del) {
                    this.is_del = is_del;
                }

                public void setFlag(int flag) {
                    this.flag = flag;
                }

                public void setCover_img(String cover_img) {
                    this.cover_img = cover_img;
                }

                public void setTitle_setted(int title_setted) {
                    this.title_setted = title_setted;
                }

                public void setInfo_setted(int info_setted) {
                    this.info_setted = info_setted;
                }

                public void setRecommend(int recommend) {
                    this.recommend = recommend;
                }

                public void setExp_honored(int exp_honored) {
                    this.exp_honored = exp_honored;
                }

                public void setSrc_platform(int src_platform) {
                    this.src_platform = src_platform;
                }

                public void setSrc_app(int src_app) {
                    this.src_app = src_app;
                }

                public void setIs_sample(int is_sample) {
                    this.is_sample = is_sample;
                }

                public void setFor_note_catalog(int for_note_catalog) {
                    this.for_note_catalog = for_note_catalog;
                }

                public void setScheduleid(int scheduleid) {
                    this.scheduleid = scheduleid;
                }

                public void setPerson(int person) {
                    this.person = person;
                }

                public void setExpense(int expense) {
                    this.expense = expense;
                }

                public void setUp_num(int up_num) {
                    this.up_num = up_num;
                }

                public void setVisit_num(int visit_num) {
                    this.visit_num = visit_num;
                }

                public void setShare_num(int share_num) {
                    this.share_num = share_num;
                }

                public void setMdd_pois(String mdd_pois) {
                    this.mdd_pois = mdd_pois;
                }

                public void setFrom_mddname(String from_mddname) {
                    this.from_mddname = from_mddname;
                }

                public void setPersonname(String personname) {
                    this.personname = personname;
                }

                public void setStylename(String stylename) {
                    this.stylename = stylename;
                }

                public void setDay_ids(List<String> day_ids) {
                    this.day_ids = day_ids;
                }

                public void setStyle_ids(List<Integer> style_ids) {
                    this.style_ids = style_ids;
                }

//                public void setBaggage_ids(List<?> baggage_ids) {
//                    this.baggage_ids = baggage_ids;
//                }
//
//                public void setBaggage_infos(List<?> baggage_infos) {
//                    this.baggage_infos = baggage_infos;
//                }

                public void setDay_poi_summary(List<List<String>> day_poi_summary) {
                    this.day_poi_summary = day_poi_summary;
                }

                public int getId() {
                    return id;
                }

                public int getIid() {
                    return iid;
                }

                public int getUid() {
                    return uid;
                }

                public String getTitle() {
                    return title;
                }

                public int getStart_day() {
                    return start_day;
                }

                public int getDays_count() {
                    return days_count;
                }

                public int getFrom_mddid() {
                    return from_mddid;
                }

                public int getTo_mddid() {
                    return to_mddid;
                }

                public int getStatus() {
                    return status;
                }

                public String getCtime() {
                    return ctime;
                }

                public String getPubtime() {
                    return pubtime;
                }

                public String getJudgement() {
                    return judgement;
                }

                public int getIs_del() {
                    return is_del;
                }

                public int getFlag() {
                    return flag;
                }

                public String getCover_img() {
                    return cover_img;
                }

                public int getTitle_setted() {
                    return title_setted;
                }

                public int getInfo_setted() {
                    return info_setted;
                }

                public int getRecommend() {
                    return recommend;
                }

                public int getExp_honored() {
                    return exp_honored;
                }

                public int getSrc_platform() {
                    return src_platform;
                }

                public int getSrc_app() {
                    return src_app;
                }

                public int getIs_sample() {
                    return is_sample;
                }

                public int getFor_note_catalog() {
                    return for_note_catalog;
                }

                public int getScheduleid() {
                    return scheduleid;
                }

                public int getPerson() {
                    return person;
                }

                public int getExpense() {
                    return expense;
                }

                public int getUp_num() {
                    return up_num;
                }

                public int getVisit_num() {
                    return visit_num;
                }

                public int getShare_num() {
                    return share_num;
                }

                public String getMdd_pois() {
                    return mdd_pois;
                }

                public String getFrom_mddname() {
                    return from_mddname;
                }

                public String getPersonname() {
                    return personname;
                }

                public String getStylename() {
                    return stylename;
                }

                public List<String> getDay_ids() {
                    return day_ids;
                }

                public List<Integer> getStyle_ids() {
                    return style_ids;
                }

//                public List<?> getBaggage_ids() {
//                    return baggage_ids;
//                }
//
//                public List<?> getBaggage_infos() {
//                    return baggage_infos;
//                }

                public List<List<String>> getDay_poi_summary() {
                    return day_poi_summary;
                }
            }
        }

        private UserEntity user;
        private int reply;
        private int pv;

        private List<ContentEntity> content;

        public List<ContentEntity> getContent() {
            return content;
        }

        public void setContent(List<ContentEntity> content) {
            this.content = content;
        }

        /**
         * id : 12684
         * name : 台湾
         */

        private MddEntity mdd;
        private int is_audit;
        private int update_cache;
        private String fingerprint;

        public void setId(int id) {
            this.id = id;
        }

        public void setIs_travel(int is_travel) {
            this.is_travel = is_travel;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public void setMtime(String mtime) {
            this.mtime = mtime;
        }

        public void setHeadimg(String headimg) {
            this.headimg = headimg;
        }

        public void setOimgurl(String oimgurl) {
            this.oimgurl = oimgurl;
        }

        public void setUser(UserEntity user) {
            this.user = user;
        }

        public void setReply(int reply) {
            this.reply = reply;
        }

        public void setPv(int pv) {
            this.pv = pv;
        }

        public void setMdd(MddEntity mdd) {
            this.mdd = mdd;
        }

        public void setIs_audit(int is_audit) {
            this.is_audit = is_audit;
        }

        public void setUpdate_cache(int update_cache) {
            this.update_cache = update_cache;
        }

        public void setFingerprint(String fingerprint) {
            this.fingerprint = fingerprint;
        }

        public int getId() {
            return id;
        }

        public int getIs_travel() {
            return is_travel;
        }

        public String getTitle() {
            return title;
        }

        public String getCtime() {
            return ctime;
        }

        public String getMtime() {
            return mtime;
        }

        public String getHeadimg() {
            return headimg;
        }

        public String getOimgurl() {
            return oimgurl;
        }

        public UserEntity getUser() {
            return user;
        }

        public int getReply() {
            return reply;
        }

        public int getPv() {
            return pv;
        }

        public MddEntity getMdd() {
            return mdd;
        }

        public int getIs_audit() {
            return is_audit;
        }

        public int getUpdate_cache() {
            return update_cache;
        }

        public String getFingerprint() {
            return fingerprint;
        }

        public static class UserEntity {
            private String logo;
            private String logo_60;
            private String logo_120;
            private int user_score;
            private int user_lv;
            private String url;

            public void setLogo(String logo) {
                this.logo = logo;
            }

            public void setLogo_60(String logo_60) {
                this.logo_60 = logo_60;
            }

            public void setLogo_120(String logo_120) {
                this.logo_120 = logo_120;
            }

            public void setUser_score(int user_score) {
                this.user_score = user_score;
            }

            public void setUser_lv(int user_lv) {
                this.user_lv = user_lv;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public String getLogo() {
                return logo;
            }

            public String getLogo_60() {
                return logo_60;
            }

            public String getLogo_120() {
                return logo_120;
            }

            public int getUser_score() {
                return user_score;
            }

            public int getUser_lv() {
                return user_lv;
            }

            public String getUrl() {
                return url;
            }
        }

        public static class MddEntity {
            private int id;
            private String name;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }
        }
    }
}
