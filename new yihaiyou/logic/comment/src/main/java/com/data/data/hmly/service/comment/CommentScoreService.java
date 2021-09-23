package com.data.data.hmly.service.comment;


import com.data.data.hmly.service.comment.dao.CommentScoreDao;
import com.data.data.hmly.service.comment.entity.CommentScore;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by HMLY on 2015/12/28.
 */
@Service
public class CommentScoreService {

    @Resource
    private CommentScoreDao commentScoreDao;

    public void save(CommentScore commentScore) {
        commentScoreDao.save(commentScore);
    }
}
