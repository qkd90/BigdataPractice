package com.hmlyinfo.app.soutu.hotel.mapper;

import java.util.List;
import java.util.Map;

import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.hotel.domain.HotelComment;

public interface HotelCommentMapper <T extends HotelComment> extends BaseMapper<T>{
	public List<HotelComment> listColumns(Map<String, Object> params);
}
