package com.data.spider.service.pojo.ctrip;

import java.util.List;

/**
 * Created by Sane on 16/4/27.
 */
public class CtripRegions {


    private List<RegionEntity> ABCDE;


    private List<RegionEntity> FGHJK;


    private List<RegionEntity> QRSTW;


    private List<RegionEntity> XYZ;

    public List<RegionEntity> getABCDE() {
        return ABCDE;
    }

    public void setABCDE(List<RegionEntity> ABCDE) {
        this.ABCDE = ABCDE;
    }

    public List<RegionEntity> getFGHJK() {
        return FGHJK;
    }

    public void setFGHJK(List<RegionEntity> FGHJK) {
        this.FGHJK = FGHJK;
    }

    public List<RegionEntity> getQRSTW() {
        return QRSTW;
    }

    public void setQRSTW(List<RegionEntity> QRSTW) {
        this.QRSTW = QRSTW;
    }

    public List<RegionEntity> getXYZ() {
        return XYZ;
    }

    public void setXYZ(List<RegionEntity> XYZ) {
        this.XYZ = XYZ;
    }


    public static class RegionEntity {
        private int id;
        private String lng;
        private String lat;
        private String name;
        private String desc;
        /**
         * lng : 118.09480783496
         * lat : 24.450887712466
         */

        private List<PathEntity> path;
//        private Object path;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getLng() {
            return lng;
        }

        public void setLng(String lng) {
            this.lng = lng;
        }

        public String getLat() {
            return lat;
        }

        public void setLat(String lat) {
            this.lat = lat;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

//        public Object getPath() {
//            return path;
//        }
//
//        public void setPath(Object path) {
//            this.path = path;
//        }

        public List<PathEntity> getPath() {
            return path;
        }

        public void setPath(List<PathEntity> path) {
            this.path = path;
        }

        public static class PathEntity {
            private String lng;
            private String lat;

            public String getLng() {
                return lng;
            }

            public void setLng(String lng) {
                this.lng = lng;
            }

            public String getLat() {
                return lat;
            }

            public void setLat(String lat) {
                this.lat = lat;
            }
        }
    }
}
