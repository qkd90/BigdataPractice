package com.data.data.hmly.action.mobile;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.opensymphony.xwork2.ActionProxy;

import java.util.List;
import java.util.Map;

/**
 * Created by huangpeijie on 2016-04-25,0025.
 */
//@Ignore
public class ImpressionTest extends BaseTest {
    /**
     * 1.1 保存印象
     *
     * @throws Exception
     */
    public void testSave() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("id", 0);
        map.put("placeName", "老虎滩");
        map.put("targetId", 1270);
        map.put("placeType", "scenic");
        map.put("cover", "gallery/1270/IBlla_7_hQGXs53X5IAG");
        map.put("content", "testasdfsdf");
        map.put("type", 1);

        List<Map<String, Object>> gallery = Lists.newArrayList();
        Map<String, Object> gallery1 = Maps.newHashMap();
        gallery1.put("id", 0);
        gallery1.put("imgUrl", "gallery/1270/0hzxBQ2SgbCfFSjYAr_G");
        gallery1.put("width", 1600);
        gallery1.put("height", 1200);
        gallery.add(gallery1);

        Map<String, Object> gallery2 = Maps.newHashMap();
        gallery2.put("id", 0);
        gallery2.put("imgUrl", "gallery/1270/3thaMt3ohNqlSSEPiZkA");
        gallery2.put("width", 1600);
        gallery2.put("height", 1067);
        gallery.add(gallery2);

        map.put("gallery", gallery);
//        request.setParameter("json", mapper.writeValueAsString(map));
//        ActionProxy proxy = getActionProxy("/impression/save.jhtml");
//        proxy.execute();
    }

    /**
     * 1.3 印象详情
     *
     * @throws Exception
     */
    public void testDetail() throws Exception {
        request.setParameter("imprId", "4");
        ActionProxy proxy = getActionProxy("/impression/detail.jhtml");
        proxy.execute();
    }

    /**
     * 1.4 删除印象
     *
     * @throws Exception
     */
    public void testDelete() throws Exception {
        request.setParameter("imprId", "1");
        ActionProxy proxy = getActionProxy("/impression/delete.jhtml");
        proxy.execute();
    }

    public void testUploadPhoto() throws Exception {
        ActionProxy proxy = getActionProxy("/impression/uploadPhoto.jhtml");
        proxy.execute();
    }

    /**
     * 1.5 搜索印象地点
     *
     * @throws Exception
     */
    public void testPlaceList() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("keyword", "鼓浪屿");
        map.put("type", "scenic");
        map.put("longitude", "118.18134038910694");
        map.put("latitude", "24.49198529225134");
//        request.setParameter("json", mapper.writeValueAsString(map));
        request.setParameter("pageNo", "1");
        request.setParameter("pageSize", "10");
        ActionProxy proxy = getActionProxy("/impression/placeList.jhtml");
        proxy.execute();
    }
}
