package com.data.spider.service.mfw;

import com.data.spider.service.pojo.mfw.PoiListResult;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Created by Sane on 16/4/25.
 */
@Ignore
public class MfwServiceTest {

    private MfwService mfwService = new MfwService();

    @Test
    public void getAdviceTimeFromApp() throws Exception {
        List<PoiListResult.DataEntity.ListEntity> list = mfwService.getAllPoi(10926);
        for (PoiListResult.DataEntity.ListEntity entity : list) {
            String referTime = mfwService.getAdviceTimeFromApp(entity.getId());
            String referTime2 = mfwService.getAdviceTime(entity.getId());
            System.out.println(entity.getName() + "\t" + referTime + "\t" + referTime2);
        }
    }

    @Test
    public void getResponse() throws Exception {

    }

    @Test
    public void getSearchResult() throws Exception {

    }

    @Test
    public void getTravelnodes() throws Exception {

    }

    @Test
    public void getTravelnodeAll() throws Exception {

    }

    @Test
    public void getTravelnoteDetail() throws Exception {

    }

    @Test
    public void getNoteImagesSize() throws Exception {

    }

    @Test
    public void searchMdd() throws Exception {

    }

    @Test
    public void getPoiList() throws Exception {

    }

    @Test
    public void getPoiDetail() throws Exception {

    }

}