package com.data.spider.service.mfw;

import com.data.spider.service.pojo.mfw.ContentEntity;
import com.data.spider.service.pojo.mfw.PoiDetailResult;
import com.data.spider.service.pojo.mfw.PoiListResult;
import com.data.spider.service.pojo.mfw.TravelnoteDetail;
import com.google.gson.Gson;
import org.junit.Test;

/**
 * Created by Sane on 16/2/18.
 */
public class MfwServiceTest {

    @Test
    public void testGetTravelnoteDetail() throws Exception {
        MfwService mfwService = new MfwService();
        TravelnoteDetail detail = mfwService.getTravelnoteDetail(3396880);
        System.out.println(detail.getData().getTitle());
        int i = 0;
        for (ContentEntity content : detail.getData().getContent()) {
            System.out.println("_____________" + i++ + "\t" + content.getType() + "_____________");
            if (content.getType().equals("container")) {
//                for (ContentEntity.Container container : content.getContainer()) {
//                    System.out.println(container.getType() + "\t" + container.getContent() + "\t" + container.getExt());
//                }
            } else if (content.getType().equals("image")) {
                ContentEntity.Image img = content.getImage();
                if (img.getExt() != null) {
                    System.out.println(img.getExt().getType_id() + "\t" + img.getExt().getId() + "\t" + img.getExt().getName() + img.getOimgurl().split("\\?")[0] + "\t" + img.getExt().getKey());
                } else {
                    System.out.println(img.getOimgurl().split("\\?")[0]);
                }
            } else {
                System.out.println(new Gson().toJson(content));
            }
        }
    }


    @Test
    public void testGetPoiDetail() throws Exception {
        MfwService mfwService = new MfwService();
        PoiListResult listResult = mfwService.getPoiList(10579, 0);
        for (PoiListResult.DataEntity.ListEntity listEntity : listResult.getData().getList()) {
            System.out.print(listEntity.getName() + "\t");
            PoiDetailResult detail = mfwService.getPoiDetail(listEntity.getId());
            System.out.println(detail.getData().getPoi().getArea().getName() + "\t" + detail.getData().getPoi().getRefer_time());
        }


    }
}