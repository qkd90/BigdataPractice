package com.hmlyinfo.app.soutu.invitation.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.invitation.domain.Invitation;
import com.hmlyinfo.app.soutu.invitation.domain.InvitationJoin;
import com.hmlyinfo.app.soutu.invitation.mapper.InvitationJoinMapper;
import com.hmlyinfo.app.soutu.invitation.mapper.InvitationMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;
import com.hmlyinfo.base.util.Validate;

@Service
public class InvitationJoinService extends BaseService<InvitationJoin, Long>{

	@Autowired
	private UserService userService;
	@Autowired
	private InvitationMapper<Invitation> imapper;
	@Autowired
	private InvitationJoinMapper<InvitationJoin> mapper;

	@Override
	public BaseMapper<InvitationJoin> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	/**
	 * 新增报名
	 * @param ict
	 * @return
	 */
	@Transactional
	public InvitationJoin newInvitationJoin(InvitationJoin ijn)
	{
		/*Validate.isTrue(ijn.getJoinCounts() < 20, ErrorCode.ERROR_50001, "报名人数不能大于20");*/
		long userId = MemberService.getCurrentUserId();
		/*
		 * 改变结伴帖中报名数目
		 */
		Map<String, Object> paramNumMap = new HashMap<String, Object>();
		paramNumMap.put("id", ijn.getInvitationId());
		paramNumMap.put("joinCounts", 1);
		imapper.changeNum(paramNumMap);
		
		/*
		 * 插入新的报名
		 */
		ijn.setUserId(userId);
		this.insert(ijn);
		return ijn;
	}
	
	/**
	 * 查询报名列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> listDetail(Map<String, Object> paramMap)
	{
		List<InvitationJoin> ijList = this.list(paramMap);
		
		// 查询用户信息
		List uidList = ListUtil.getIdList(ijList, "userId");
		Map<String, Object> userParamMap = Maps.newHashMap();
		userParamMap.put("ids", uidList);
		List<User> userList = userService.listColumns(userParamMap,
					Lists.newArrayList("id", "nickname", "userface"));
				
		return ListUtil.listJoin(ijList, userList, "userId=id", "author", null);
	}

}
