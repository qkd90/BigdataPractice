package com.data.data.hmly.service.comment;

import com.data.data.hmly.service.comment.dao.CommentPhotoDao;
import com.data.data.hmly.service.comment.entity.CommentPhoto;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * Created by HMLY on 2016/1/16.
 */
@Service
public class CommentPhotoService {

    @Resource
    private CommentPhotoDao commentPhotoDao;

    public void save(CommentPhoto commentPhoto) {
        commentPhotoDao.save(commentPhoto);
    }

    public void delById(Long id) {
        commentPhotoDao.delete(id);
    }
}
