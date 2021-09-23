package com.data.spider.service.pojo.mfw;

import java.util.List;

/**
 * Created by Sane on 16/2/18.
 */
public class TravelnoteList {


    private DataEntity data;

    private int is_array;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setIs_array(int is_array) {
        this.is_array = is_array;
    }

    public DataEntity getData() {
        return data;
    }

    public int getIs_array() {
        return is_array;
    }

    public static class DataEntity {
        private int has_more;
//        private Object offset;
        private int total;

        private List<ListEntity> list;

        public void setHas_more(int has_more) {
            this.has_more = has_more;
        }


        public void setTotal(int total) {
            this.total = total;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public int getHas_more() {
            return has_more;
        }


        public int getTotal() {
            return total;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            private int id;
            private String title;
            /**
             * id : 10779860
             * name : shady
             * province : 武汉
             * logo : http://file32.mafengwo.net/M00/70/23/wKgBs1Y-BDSAWOs_AACfC2tUwEU71.head.w90.jpeg
             */

//            private UserEntity user;
//            private String cover;
            private int ctime;
//            private int num_comment;
//            private String num_visit;
//            private int vote;
//            private int treasure;
//            private int cream;
//            private String url;
//            private String url_comment;
            private int mddid;
            private String mddname;
//            private int p_mddid;
//            private String p_mddname;
//            private List<?> pre_imgs;
//            private List<String> badges;

            public void setId(int id) {
                this.id = id;
            }

            public void setTitle(String title) {
                this.title = title;
            }

//            public void setUser(UserEntity user) {
//                this.user = user;
//            }
//
//            public void setCover(String cover) {
//                this.cover = cover;
//            }

            public void setCtime(int ctime) {
                this.ctime = ctime;
            }

//            public void setNum_comment(int num_comment) {
//                this.num_comment = num_comment;
//            }
//
//            public void setNum_visit(String num_visit) {
//                this.num_visit = num_visit;
//            }

//            public void setVote(int vote) {
//                this.vote = vote;
//            }
//
//            public void setTreasure(int treasure) {
//                this.treasure = treasure;
//            }
//
//            public void setCream(int cream) {
//                this.cream = cream;
//            }
//
//            public void setUrl(String url) {
//                this.url = url;
//            }
//
//            public void setUrl_comment(String url_comment) {
//                this.url_comment = url_comment;
//            }

            public void setMddid(int mddid) {
                this.mddid = mddid;
            }

            public void setMddname(String mddname) {
                this.mddname = mddname;
            }

//            public void setP_mddid(int p_mddid) {
//                this.p_mddid = p_mddid;
//            }

//            public void setP_mddname(String p_mddname) {
//                this.p_mddname = p_mddname;
//            }
//
//            public void setPre_imgs(List<?> pre_imgs) {
//                this.pre_imgs = pre_imgs;
//            }
//
//            public void setBadges(List<String> badges) {
//                this.badges = badges;
//            }

            public int getId() {
                return id;
            }

            public String getTitle() {
                return title;
            }

//            public UserEntity getUser() {
//                return user;
//            }
//
//            public String getCover() {
//                return cover;
//            }

            public int getCtime() {
                return ctime;
            }

//            public int getNum_comment() {
//                return num_comment;
//            }
//
//            public String getNum_visit() {
//                return num_visit;
//            }
//
//            public int getVote() {
//                return vote;
//            }
//
//            public int getTreasure() {
//                return treasure;
//            }
//
//            public int getCream() {
//                return cream;
//            }
//
//            public String getUrl() {
//                return url;
//            }
//
//            public String getUrl_comment() {
//                return url_comment;
//            }

            public int getMddid() {
                return mddid;
            }

            public String getMddname() {
                return mddname;
            }

//            public int getP_mddid() {
//                return p_mddid;
//            }

//            public String getP_mddname() {
//                return p_mddname;
//            }
//
//            public List<?> getPre_imgs() {
//                return pre_imgs;
//            }
//
//            public List<String> getBadges() {
//                return badges;
//            }

            public static class UserEntity {
                private int id;
                private String name;
                private String province;
                private String logo;

                public void setId(int id) {
                    this.id = id;
                }

                public void setName(String name) {
                    this.name = name;
                }

                public void setProvince(String province) {
                    this.province = province;
                }

                public void setLogo(String logo) {
                    this.logo = logo;
                }

                public int getId() {
                    return id;
                }

                public String getName() {
                    return name;
                }

                public String getProvince() {
                    return province;
                }

                public String getLogo() {
                    return logo;
                }
            }
        }
    }
}
