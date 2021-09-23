package com.hmlyinfo.app.soutu.weixin.mapper;

import java.util.List;
import java.util.Map;

import com.hmlyinfo.app.soutu.weixin.domain.Reply;
import com.hmlyinfo.base.persistent.BaseMapper;

public interface ReplyMapper <T extends Reply> extends BaseMapper<T>{
	public List<Reply> listReplyByGroupId(String groupId);
	public List<Reply> listByKeyword(Map<String, Object> paramMap);
}
