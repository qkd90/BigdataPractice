package com.data.spider.service.pojo.mfw;

import java.util.List;

/**
 * Created by Sane on 16/2/18.
 */
public class ContentEntity {
    /**
     * type : container
     * content : [{"type":"txt","content":"    感谢蚂蜂窝！2015.6.25此篇游记荣登蜂首。必须截图留念一下！"},{"type":"face","content":"抱抱","ext":"http://file.mafengwo.net/images/i/face/daily/4.gif"}]
     */

    private Object content;
    private List<Container> container;
    private Image image;

    public List<Container> getContainer() {
        return container;
    }

    public void setContainer(List<Container> container) {
        this.container = container;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    /**
     * type : container
     */

    private String type;

    public void setContent(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }


    public void setType(String type) {
        this.type = type;
    }

    public String getType() {
        return type;
    }


    public class Container {
        private String type;
        private String content;
        private Object ext;

        public void setType(String type) {
            this.type = type;
        }

        public void setContent(String content) {
            this.content = content;
        }

        public String getType() {
            return type;
        }

        public String getContent() {
            return content;
        }

        public Object getExt() {
            return ext;
        }

        public void setExt(Object ext) {
            this.ext = ext;
        }
    }

    public static class Image {


        /**
         * pid : 92103864
         * new_iid : 92103321
         * fileid : mfwStorage9/M00/7E/57/wKgBs1aCtP-ALgV3AAhbYiv9yqU60.groupinfo.jpeg
         * o_width : 1024
         * o_height : 768
         * mdd_id : 0
         * poi_name :
         * ctime : 2016-01-12 14:19:03
         * poi_id : 829
         * ptime : 0
         * alid : 0
         * vote_num : 0
         * reply_num : 0
         * share_num : 0
         * imgsurl : http://file110.mafengwo.net/s9/M00/7E/57/wKgBs1aCtP-ALgV3AAhbYiv9yqU60.jpeg?imageView2%2F2%2Fw%2F120%2Fh%2F120%2Fformat%2Fwebp
         * imgurl : http://file110.mafengwo.net/s9/M00/7E/57/wKgBs1aCtP-ALgV3AAhbYiv9yqU60.jpeg?imageView2%2F2%2Fw%2F600%2Fh%2F600%2Fformat%2Fwebp
         * oimgurl : http://file110.mafengwo.net/s9/M00/7E/57/wKgBs1aCtP-ALgV3AAhbYiv9yqU60.jpeg
         * sizes : {"width":600,"height":450}
         * ext : {"id":829,"name":"鼓浪屿","flag":0,"key":"poi","type_id":3,"num_comment":8849,"num_ginfo":1962,"rank":4,"icon":"house","img200":"http://file30.mafengwo.net/M00/6B/C9/wKgBpVW04GWASRcLAAwS_ra-T5U03.rbook_comment.w200x110.jpeg","img200x150":"http://file30.mafengwo.net/M00/6B/C9/wKgBpVW04GWASRcLAAwS_ra-T5U03.rbook_comment.w200x150.jpeg","address":"福建省厦门市思明区鼓浪屿区"}
         */

        private int pid;
        private int new_iid;
        private String fileid;
        private int o_width;
        private int o_height;
        private int mdd_id;
        private String poi_name;
        private String ctime;
        private int poi_id;
        private int ptime;
        private int alid;
        private int vote_num;
        private int reply_num;
        private int share_num;
        private String imgsurl;
        private String imgurl;
        private String oimgurl;
        /**
         * width : 600
         * height : 450
         */

        private SizesEntity sizes;
        /**
         * id : 829
         * name : 鼓浪屿
         * flag : 0
         * key : poi
         * type_id : 3
         * num_comment : 8849
         * num_ginfo : 1962
         * rank : 4
         * icon : house
         * img200 : http://file30.mafengwo.net/M00/6B/C9/wKgBpVW04GWASRcLAAwS_ra-T5U03.rbook_comment.w200x110.jpeg
         * img200x150 : http://file30.mafengwo.net/M00/6B/C9/wKgBpVW04GWASRcLAAwS_ra-T5U03.rbook_comment.w200x150.jpeg
         * address : 福建省厦门市思明区鼓浪屿区
         */

        private ExtEntity ext;

        public void setPid(int pid) {
            this.pid = pid;
        }

        public void setNew_iid(int new_iid) {
            this.new_iid = new_iid;
        }

        public void setFileid(String fileid) {
            this.fileid = fileid;
        }

        public void setO_width(int o_width) {
            this.o_width = o_width;
        }

        public void setO_height(int o_height) {
            this.o_height = o_height;
        }

        public void setMdd_id(int mdd_id) {
            this.mdd_id = mdd_id;
        }

        public void setPoi_name(String poi_name) {
            this.poi_name = poi_name;
        }

        public void setCtime(String ctime) {
            this.ctime = ctime;
        }

        public void setPoi_id(int poi_id) {
            this.poi_id = poi_id;
        }

        public void setPtime(int ptime) {
            this.ptime = ptime;
        }

        public void setAlid(int alid) {
            this.alid = alid;
        }

        public void setVote_num(int vote_num) {
            this.vote_num = vote_num;
        }

        public void setReply_num(int reply_num) {
            this.reply_num = reply_num;
        }

        public void setShare_num(int share_num) {
            this.share_num = share_num;
        }

        public void setImgsurl(String imgsurl) {
            this.imgsurl = imgsurl;
        }

        public void setImgurl(String imgurl) {
            this.imgurl = imgurl;
        }

        public void setOimgurl(String oimgurl) {
            this.oimgurl = oimgurl;
        }

        public void setSizes(SizesEntity sizes) {
            this.sizes = sizes;
        }

        public void setExt(ExtEntity ext) {
            this.ext = ext;
        }

        public int getPid() {
            return pid;
        }

        public int getNew_iid() {
            return new_iid;
        }

        public String getFileid() {
            return fileid;
        }

        public int getO_width() {
            return o_width;
        }

        public int getO_height() {
            return o_height;
        }

        public int getMdd_id() {
            return mdd_id;
        }

        public String getPoi_name() {
            return poi_name;
        }

        public String getCtime() {
            return ctime;
        }

        public int getPoi_id() {
            return poi_id;
        }

        public int getPtime() {
            return ptime;
        }

        public int getAlid() {
            return alid;
        }

        public int getVote_num() {
            return vote_num;
        }

        public int getReply_num() {
            return reply_num;
        }

        public int getShare_num() {
            return share_num;
        }

        public String getImgsurl() {
            return imgsurl;
        }

        public String getImgurl() {
            return imgurl;
        }

        public String getOimgurl() {
            return oimgurl;
        }

        public SizesEntity getSizes() {
            return sizes;
        }

        public ExtEntity getExt() {
            return ext;
        }

        public static class SizesEntity {
            private int width;
            private int height;

            public void setWidth(int width) {
                this.width = width;
            }

            public void setHeight(int height) {
                this.height = height;
            }

            public int getWidth() {
                return width;
            }

            public int getHeight() {
                return height;
            }
        }

        public static class ExtEntity {
            private int id;
            private String name;
            private int flag;
            private String key;
            private int type_id;
            private int num_comment;
            private int num_ginfo;
            private int rank;
            private String icon;
            private String img200;
            private String img200x150;
            private String address;

            public void setId(int id) {
                this.id = id;
            }

            public void setName(String name) {
                this.name = name;
            }

            public void setFlag(int flag) {
                this.flag = flag;
            }

            public void setKey(String key) {
                this.key = key;
            }

            public void setType_id(int type_id) {
                this.type_id = type_id;
            }

            public void setNum_comment(int num_comment) {
                this.num_comment = num_comment;
            }

            public void setNum_ginfo(int num_ginfo) {
                this.num_ginfo = num_ginfo;
            }

            public void setRank(int rank) {
                this.rank = rank;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public void setImg200(String img200) {
                this.img200 = img200;
            }

            public void setImg200x150(String img200x150) {
                this.img200x150 = img200x150;
            }

            public void setAddress(String address) {
                this.address = address;
            }

            public int getId() {
                return id;
            }

            public String getName() {
                return name;
            }

            public int getFlag() {
                return flag;
            }

            public String getKey() {
                return key;
            }

            public int getType_id() {
                return type_id;
            }

            public int getNum_comment() {
                return num_comment;
            }

            public int getNum_ginfo() {
                return num_ginfo;
            }

            public int getRank() {
                return rank;
            }

            public String getIcon() {
                return icon;
            }

            public String getImg200() {
                return img200;
            }

            public String getImg200x150() {
                return img200x150;
            }

            public String getAddress() {
                return address;
            }
        }
    }


}
