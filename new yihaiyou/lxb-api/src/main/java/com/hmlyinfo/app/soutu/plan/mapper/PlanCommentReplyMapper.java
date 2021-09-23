package com.hmlyinfo.app.soutu.plan.mapper;

import com.hmlyinfo.app.soutu.plan.domain.PlanCommentReply;
import com.hmlyinfo.base.persistent.BaseMapper;

import java.util.List;
import java.util.Map;

public interface PlanCommentReplyMapper <T extends PlanCommentReply> extends BaseMapper<T>{
    public List<Map<String, Object>> listWithUser(Map<String, Object> params);
}
