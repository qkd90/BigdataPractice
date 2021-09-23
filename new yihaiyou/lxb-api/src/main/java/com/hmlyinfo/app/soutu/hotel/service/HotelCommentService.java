package com.hmlyinfo.app.soutu.hotel.service;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.hotel.domain.CtripHotel;
import com.hmlyinfo.app.soutu.hotel.domain.Hotel;
import com.hmlyinfo.app.soutu.hotel.domain.HotelComment;
import com.hmlyinfo.app.soutu.hotel.domain.HotelRelation;
import com.hmlyinfo.app.soutu.hotel.mapper.HotelCommentMapper;
import com.hmlyinfo.app.soutu.hotel.mapper.HotelMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;
import com.hmlyinfo.base.util.Validate;

@Service
public class HotelCommentService extends BaseService<HotelComment, Long>{

	@Autowired
	private HotelCommentMapper<HotelComment> mapper;
	@Autowired
	private HotelMapper<Hotel> hotelMapper;
	@Autowired
	private HotelRelationService hotelRelationService;
	@Autowired
	private UserService userService;
	@Autowired
	private CtripHotelService ctripHotelService;

	@Override
	public BaseMapper<HotelComment> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	public List<HotelComment> listColumns(Map<String, Object> params, List<String> columns) {
        params.put("needColumns", columns);
        return mapper.listColumns(params);
    }
	
	public long getIndexId(Map<String, Object> paramMap){
		//根据酒店id查询对应的索引id
		long hotelId = Long.parseLong((String) paramMap.get("hotelId"));
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("hotelId", hotelId);
		List<HotelRelation> relationList = hotelRelationService.list(params);
		Validate.isTrue(!relationList.isEmpty(), ErrorCode.ERROR_51001, "索引表中不存在该酒店");
		HotelRelation hotelRelation = ListUtil.getSingle(relationList);
		return hotelRelation.getIndexId();
	}
	
	//插入新的评论
	@Transactional
	public HotelComment addNewComment(Map<String, Object> paramMap){
		
		long indexId = this.getIndexId(paramMap);
		
		//插入一条新的评论
		String content = (String) paramMap.get("content");
		HotelComment comment = new HotelComment();
		comment.setIndexId(indexId);
		comment.setContent(content);
		
		//获取用户信息
		Long userId = MemberService.getCurrentUserId();
		User user = userService.info(userId);
		comment.setNickName(user.getNickname());
		comment.setUserId(userId);
		mapper.insert(comment);
		//酒店评论数加一
		long hotelId = Long.parseLong((String) paramMap.get("hotelId"));
		CtripHotel hotel = ctripHotelService.info(hotelId);
		hotel.setCommentNum(hotel.getCommentNum() + 1);
		ctripHotelService.update(hotel);
		
		return comment;
	}
	
	//根据酒店id查询评论
	public List<HotelComment> listComment(Map<String, Object> paramMap){
		//根据酒店id查询对应的索引id
		
		long indexId = this.getIndexId(paramMap);
		
		//根据索引id查询关联表中的评论id列表
		paramMap.remove("hotelId");
		paramMap.put("indexId", indexId);
		return mapper.list(paramMap);
	}
	
}
