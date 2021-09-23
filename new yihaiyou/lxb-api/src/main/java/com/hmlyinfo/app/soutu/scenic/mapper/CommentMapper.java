package com.hmlyinfo.app.soutu.scenic.mapper;

import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.app.soutu.scenic.domain.Comment;

public interface CommentMapper <T extends Comment> extends BaseMapper<T>{
	public Comment selByTwoId(long id, long userId);
	public Integer updateCommentGood(Comment comment);
}
