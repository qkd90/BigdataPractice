package com.hmlyinfo.app.soutu.invitation.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.transform.impl.AddDelegateTransformer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ResponseBody;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.invitation.domain.Invitation;
import com.hmlyinfo.app.soutu.invitation.domain.InvitationComment;
import com.hmlyinfo.app.soutu.invitation.domain.InvitationLike;
import com.hmlyinfo.app.soutu.invitation.mapper.InvitationLikeMapper;
import com.hmlyinfo.app.soutu.invitation.mapper.InvitationMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;

@Service
public class InvitationLikeService extends BaseService<InvitationLike, Long>{
	
	@Autowired
	private InvitationMapper<Invitation> imapper; 
	@Autowired
	private InvitationLikeMapper<InvitationLike> mapper;

	@Override
	public BaseMapper<InvitationLike> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	/**
	 * 判断是否已经点赞
	 * @param invitationId 结伴帖id
	 * @param userId 用户id
	 * @return
	 */
	public  boolean isLiked(final long invitationId, final long userId)
	{
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("invitationId", invitationId);
		paramMap.put("userId", userId);
		if(count(paramMap) == 0)
			return false;
		else
			return true;
	}
	
	
	/**
	 * 改变点赞状态
	 * @param invitationId 结伴帖id
	 * @param userId 用户id
	 */
	@Transactional
	public void changeLike(long invitationId, long userId)
	{
		Map<String, Object> paramNumMap = new HashMap<String, Object>();
		paramNumMap.put("id", invitationId);
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("invitationId", invitationId);
		paramMap.put("userId", userId);
		InvitationLike like = (InvitationLike)one(paramMap);
		if(like == null)
		{
			/*
			 * add a like
			 */
			like = new InvitationLike();
			like.setInvitationId(invitationId);
			like.setUserId(userId);
			insert(like);
			
			/*
			 * change the likeCounts in tb_invitation 
			 */
			paramNumMap.put("likeCounts", 1);
			imapper.changeNum(paramNumMap);
		}
		else
		{
			/*
			 * del a like
			 */
			del(like.getId() + "");
			
			/*
			 * change the likeCounts in tb_invitation
			 */
			paramNumMap.put("likeCounts", -1);
			imapper.changeNum(paramNumMap);
		}
	}

}
