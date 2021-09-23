package com.hmlyinfo.app.soutu.invitation.service;

import java.util.Collections;
import java.util.Comparator;
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
import com.hmlyinfo.app.soutu.invitation.domain.Invitation;
import com.hmlyinfo.app.soutu.invitation.domain.InvitationComment;
import com.hmlyinfo.app.soutu.invitation.mapper.InvitationCommentMapper;
import com.hmlyinfo.app.soutu.invitation.mapper.InvitationMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;

@Service
public class InvitationCommentService extends BaseService<InvitationComment, Long>{
	
	@Autowired
	private InvitationMapper<Invitation> imapper;
	@Autowired
	private UserService userService;
	@Autowired
	private InvitationCommentMapper<InvitationComment> mapper;

	@Override
	public BaseMapper<InvitationComment> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	

	/*
	 * 评论详情
	 */
	public List<Map<String, Object>> listDetail(Map<String, Object> paramMap)
	{
		List<InvitationComment> ivList = this.list(paramMap);
		
		// 查询用户信息
		List uidList = ListUtil.getIdList(ivList, "userId");
		Map<String, Object> userParamMap = Maps.newHashMap();
		userParamMap.put("ids", uidList);
		List<User> userList = userService.listColumns(userParamMap,
				Lists.newArrayList("id", "nickname", "userface"));
		
		//查询回复对方的信息
		List exUidList = ListUtil.getIdList(ivList, "exUid");
		Map<String, Object> exUserParamMap = Maps.newHashMap();
		exUserParamMap.put("ids", exUidList);
		List<User> exUserList = userService.listColumns(exUserParamMap,
				Lists.newArrayList("id", "nickname"));
		
		List<Map<String, Object>> ivUserList= ListUtil.listJoin(ivList, userList, "userId=id", "author", null);
		return ListUtil.listJoin(ivUserList, exUserList, "exUid=id", "ex", null);
	}
	
	/**
	 * 新增评论
	 * @param ict
	 * @return
	 */
	@Transactional
	public InvitationComment newInvitationComment(InvitationComment ict)
	{
		long userId = MemberService.getCurrentUserId();
		/*
		 * 改变结伴帖中评论数目
		 */
		Map<String, Object> paramNumMap = new HashMap<String, Object>();
		paramNumMap.put("id", ict.getInvitationId());
		paramNumMap.put("commentCounts", 1);
		imapper.changeNum(paramNumMap);
		/*
		 * 插入新的评论
		 */
		ict.setUserId(userId);
		if((ict.getUserId() == ict.getInvitationUserId()) || (ict.getInvitationUserId() == ict.getExUid())){
			ict.setIsInvitationReaded(true);
			ict.setIsInvitationReplied(true);
		}
		if(ict.getUserId() == ict.getExUid()){
			ict.setIsReaded(true);
			ict.setIsReplied(true);
		}
		this.insert(ict);
		return ict;
	}
	
	/**
	 * 通知列表
	 * @return
	 */
	public List<Map<String, Object>> notificationList(Map<String, Object> paramMap)
	{
		long userId = MemberService.getCurrentUserId();
		paramMap.put("userId", userId);
		List<InvitationComment> ivList = mapper.listcomment(paramMap);
		
		// 查询用户信息
		List uidList = ListUtil.getIdList(ivList, "userId");
		Map<String, Object> userParamMap = Maps.newHashMap();
		userParamMap.put("ids", uidList);
		List<User> userList = userService.listColumns(userParamMap,
					Lists.newArrayList("id", "nickname", "userface", "sex", "age", "birthday"));
		return ListUtil.listJoin(ivList, userList, "userId=id", "author", null);
	}
	
	
	/**
	 * 未读通知数目
	 * @return
	 */
	public int notificationCount()
	{
		long userId = MemberService.getCurrentUserId();
		
		//未读回复
		Map<String, Object> paramMapA = new HashMap<String, Object>();
		paramMapA.put("invitationUserId", userId);
		paramMapA.put("isInvitationReaded", "0");
		
		//未读评论
		Map<String, Object> paramMapB = new HashMap<String, Object>();
		paramMapB.put("exUid", userId);
		paramMapB.put("isReaded", "0");
		
		int notificationCount = this.count(paramMapA) + this.count(paramMapB);
		return notificationCount;
	}
	
	
	/**
	 * 设置为已读
	 */
	public void setReaded(Map<String, Object> paramMap)
	{
		long userId = MemberService.getCurrentUserId();
		InvitationComment ic = mapper.selById((String) paramMap.get("id"));
		if(ic.getExUid() == userId)
			ic.setIsReaded(true);
		if(ic.getInvitationUserId() == userId)
			ic.setIsInvitationReaded(true);
		this.update(ic);
	}
	
	
	/**
	 * 设置为已回复
	 */
	public void setReplied(Map<String, Object> paramMap)
	{
		long userId = MemberService.getCurrentUserId();
		InvitationComment ic = mapper.selById((String) paramMap.get("id"));
		if(ic.getExUid() == userId)
			ic.setIsReplied(true);
		if(ic.getInvitationUserId() == userId)
			ic.setIsInvitationReplied(true);
		this.update(ic);
	}
	

}
