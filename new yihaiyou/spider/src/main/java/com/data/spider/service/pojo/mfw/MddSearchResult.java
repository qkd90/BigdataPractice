package com.data.spider.service.pojo.mfw;

import java.util.List;

/**
 * Created by Sane on 16/2/22.
 */
public class MddSearchResult {

    private DataEntity data;
    /**
     * data : {"list":[{"name":"中国","subname":"目的地","url":"http://m.mafengwo.cn/nb/public/sharejump.php?type=10&id=21536&in_tab=1","icon":"http://images.mafengwo.net/mobile/images/travelguide/ic_poi_destination_48.circular.png"},{"name":"中国景点","subname":"32371个","url":"http://m.mafengwo.cn/nb/public/sharejump.php?type=12&mddid=21536&type_id=3","icon":"http://images.mafengwo.net/mobile/images/travelguide/ic_poi_view_48.circular.png"},{"name":"中国酒店","subname":"371078家","url":"http://m.mafengwo.cn/nb/public/sharejump.php?type=12&mddid=21536&type_id=2","icon":"http://images.mafengwo.net/mobile/images/travelguide/ic_poi_hotel_48.circular.png"},{"name":"中国游记","subname":"638篇","url":"http://m.mafengwo.cn/nb/public/sharejump.php?type=11&mddid=21536","icon":"http://images.mafengwo.net/mobile/images/travelguide/ic_poi_note_48.circular.png"},{"name":"中国机+酒","subname":"544个","url":"http://m.mafengwo.cn/nb/public/sharejump.php?type=1006&keyword=%E4%B8%AD%E5%9B%BD&key=21536&tag=3&hide_search=1","icon":"http://images.mafengwo.net/mobile/images/travelguide/ic_poi_ticket_hotel_48.circular.png"},{"name":"中国当地游","subname":"1936个","url":"http://m.mafengwo.cn/nb/public/sharejump.php?type=1006&keyword=%E4%B8%AD%E5%9B%BD&key=21536&tag=10&hide_search=1","icon":"http://images.mafengwo.net/mobile/images/travelguide/ic_poi_localdeal_48.circular.png"},{"name":"中国餐厅","subname":"172027家","url":"http://m.mafengwo.cn/nb/public/sharejump.php?type=12&mddid=21536&type_id=1","icon":"http://images.mafengwo.net/mobile/images/travelguide/ic_poi_food_48.circular.png"},{"name":"中国购物","subname":"4188家","url":"http://m.mafengwo.cn/nb/public/sharejump.php?type=12&mddid=21536&type_id=4","icon":"http://images.mafengwo.net/mobile/images/travelguide/ic_poi_shopping_48.circular.png"},{"name":"中国问答","subname":"693个提问","url":"http://m.mafengwo.cn/nb/public/sharejump.php?type=19&mddid=21536","icon":"http://images.mafengwo.net/mobile/images/travelguide/ic_poi_QA_48.circular.png"}],"participles":["中国"]}
     * is_array : 1
     * rc : 0
     * rm : 成功
     */

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
        /**
         * name : 中国
         * subname : 目的地
         * url : http://m.mafengwo.cn/nb/public/sharejump.php?type=10&id=21536&in_tab=1
         * icon : http://images.mafengwo.net/mobile/images/travelguide/ic_poi_destination_48.circular.png
         */

        private List<ListEntity> list;
        private List<String> participles;

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public void setParticiples(List<String> participles) {
            this.participles = participles;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public List<String> getParticiples() {
            return participles;
        }

        public static class ListEntity {
            private String name;
            private String subname;
            private String url;
            private String icon;

            public void setName(String name) {
                this.name = name;
            }

            public void setSubname(String subname) {
                this.subname = subname;
            }

            public void setUrl(String url) {
                this.url = url;
            }

            public void setIcon(String icon) {
                this.icon = icon;
            }

            public String getName() {
                return name;
            }

            public String getSubname() {
                return subname;
            }

            public String getUrl() {
                return url;
            }

            public String getIcon() {
                return icon;
            }
        }
    }
}
