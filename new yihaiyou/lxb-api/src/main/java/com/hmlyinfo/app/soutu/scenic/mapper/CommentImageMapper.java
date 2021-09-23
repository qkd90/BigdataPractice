package com.hmlyinfo.app.soutu.scenic.mapper;

import java.util.List;
import java.util.Map;

import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.app.soutu.scenic.domain.CommentImage;

public interface CommentImageMapper <T extends CommentImage> extends BaseMapper<T>{
	public List<T> listIn(Map<String, Object> paramMap);
	
}
