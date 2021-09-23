package com.data.spider.service;

import com.data.data.hmly.service.comment.CommentPhotoService;
import com.data.data.hmly.service.comment.CommentScoreService;
import com.data.data.hmly.service.comment.CommentService;
import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentPhoto;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.scenic.ScenicInfoService;
import com.data.data.hmly.service.scenic.entity.ScenicInfo;
import com.data.spider.service.ctrip.CtripService;
import com.data.spider.util.QiniuUtil;
import com.framework.hibernate.util.Page;
import com.zuipin.util.SpringContextHolder;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Sane on 16/4/11.
 */
@Ignore
public class CtripCommentsTest {
    private static ApplicationContext ac;

    @Test
    public void doUpdateCommens() throws Exception {
        ac = new ClassPathXmlApplicationContext("classpath:applicationContext*.xml");

        CommentService commentService = SpringContextHolder.getBean("commentService");
        CommentScoreService commentScoreService = SpringContextHolder.getBean("commentScoreService");
        CommentPhotoService commentPhotoService = SpringContextHolder.getBean("commentPhotoService");
        ScenicInfoService scenicInfoService = SpringContextHolder.getBean("scenicInfoService");
        ScenicInfo condition = new ScenicInfo();
        condition.setId(1040495L);
        List<ScenicInfo> scenicInfoList = scenicInfoService.list(condition, new Page(1, 10000));
        System.out.println(scenicInfoList.size());
        CtripService ctripService = new CtripService();
        for (ScenicInfo scenicInfo : scenicInfoList) {
            Integer ctripId = scenicInfo.getCtripScenicId();
            if (ctripId == null || ctripId == 0)
                continue;
            Date limit = new SimpleDateFormat("yyyy-MM-dd").parse("2014-2-1");
            int poiId = CtripService.getScenicDetail(String.valueOf(ctripId)).getSightDetailAggregate().getPoiId();
            List<Comment> comments = ctripService.getCommentLimitTime(poiId, ctripId, limit, scenicInfo.getId());
            for (Comment comment : comments) {
                System.out.println(comment.getCreateTime() + "\t" + comment.getContent());

                commentService.save(comment);
                for (CommentPhoto photo : comment.getCommentPhotos()) {
                    photo.setComment(comment);
                    String address = QiniuUtil.UploadToQiniu(photo.getImagePath(), "comment", String.valueOf(comment.getId()));
                    photo.setImagePath(address);
                    commentPhotoService.save(photo);
                }
                for (CommentScore score : comment.getCommentScores()) {
                    score.setCommentId(comment.getId());
                    commentScoreService.save(score);
                }
            }
        }
    }
}
