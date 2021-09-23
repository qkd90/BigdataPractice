package com.data.data.hmly.service.comment;

import com.data.data.hmly.service.comment.entity.Comment;
import com.data.data.hmly.service.comment.entity.CommentScore;
import com.data.data.hmly.service.comment.entity.CommentScoreType;
import com.data.data.hmly.service.common.entity.enums.ProductType;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.User;

import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import javax.annotation.Resource;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by HMLY on 2015/12/25.
 */
@Ignore
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:applicationContext*.xml")
public class CommentServiceTest {

    @Resource
    CommentService commentService;
    @Resource
    CommentScoreService commentScoreService;
    @Test
    @Transactional
    public void testCommentList() throws Exception {
        System.out.println("1");
        Comment comment = new Comment();
        comment.setType(ProductType.delicacy);
        comment.setTargetId(1l);
        Member user = new Member();
        user.setId(1l);
        comment.setUser(user);
        List<Comment> list1 = commentService.list(comment, null);
        for (Comment c : list1) {
            System.out.println(c.getUser().getUserName() + ":" + c.getContent() + ":" +c.getRepliedId());
           List<CommentScore>  css =  c.getCommentScores();
           for(CommentScore cs:css){
               System.out.println(cs.getCommentScoreType().getName());
           }
        }

        Assert.isTrue(!list1.isEmpty());
    }

    @Test
    public void testSaveComment() throws Exception {
        Comment comment = new Comment();
        Member user = new Member();
        user.setId(27l);
        comment.setUser(user);
        comment.setTargetId(1l);
        comment.setType(ProductType.delicacy);
 //       comment.setRepliedId(8l);
        comment.setContent("测试评论");
        commentService.save(comment);
        List<CommentScore> list = new ArrayList<CommentScore>();
        for(int i = 1; i < 5 ; i++){
            CommentScore cs = new CommentScore();
            CommentScoreType cst = new CommentScoreType();
            cst.setId((long)i);
            cs.setCommentScoreType(cst);
            cs.setCommentId(comment.getId());
            cs.setScore(90);
            commentScoreService.save(cs);
        }
    }
}
