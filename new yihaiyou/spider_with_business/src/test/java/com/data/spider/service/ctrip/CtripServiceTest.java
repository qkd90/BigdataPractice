package com.data.spider.service.ctrip;

import com.data.spider.service.pojo.ctrip.TravelReplyList;
import org.junit.Test;

import java.util.List;


/**
 * Created by Sane on 16/5/9.
 */
public class CtripServiceTest {
    @Test
    public void getTravelAll() throws Exception {

    }

    @Test
    public void getTravelReplyAll() throws Exception {
        CtripService ctripService = new CtripService();

        List<TravelReplyList.ResultEntity> comments = ctripService.getTravelReplyAll(1695136);
        for (TravelReplyList.ResultEntity comment : comments) {
            System.out.println(comment.getReplyDate() + "\t" + comment.getContent());

        }

    }

}