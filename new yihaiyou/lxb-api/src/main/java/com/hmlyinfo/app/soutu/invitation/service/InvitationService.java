package com.hmlyinfo.app.soutu.invitation.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.domain.User.Sex;
import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.invitation.domain.Invitation;
import com.hmlyinfo.app.soutu.invitation.domain.InvitationComment;
import com.hmlyinfo.app.soutu.invitation.domain.InvitationImg;
import com.hmlyinfo.app.soutu.invitation.domain.InvitationJoin;
import com.hmlyinfo.app.soutu.invitation.domain.InvitationLike;
import com.hmlyinfo.app.soutu.invitation.mapper.InvitationMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;

@Service
public class InvitationService extends BaseService<Invitation, Long>{

	@Autowired
	private UserService userService;
	@Autowired
	private InvitationImgService imgService;
	@Autowired
	private InvitationCommentService cmtService;
	@Autowired
	private InvitationJoinService joinService;
	@Autowired
	private InvitationLikeService likeService;
	@Autowired
	private InvitationMapper<Invitation> mapper;

	@Override
	public BaseMapper<Invitation> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	

	/**
	 * 根据筛选信息查询结伴帖详细信息
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> listDetail(Map<String, Object> paramMap)
	{
		List<Invitation> ivList = this.list(paramMap);
		
		//查询图片信息
		for (Invitation iv : ivList)
		{
			List<InvitationImg> imgList = imgService.list(ImmutableMap.of("invitationId", (Object)iv.getId()));
			iv.setImgList(imgList);
			if (iv.getStartDate() != null)
			{
				iv.setEndTime(DateUtils.addDays(iv.getStartDate(), iv.getPlanDays()));
			}
		}
		
		// 查询用户信息
		List uidList = ListUtil.getIdList(ivList, "userId");
		Map<String, Object> userParamMap = Maps.newHashMap();
		userParamMap.put("ids", uidList);
		List<User> userList = userService.listColumns(userParamMap,
				Lists.newArrayList("id", "nickname", "userface", "sex", "age", "profession", "hobbies", "hometown", "description", "birthday"));
		
		return ListUtil.listJoin(ivList, userList, "userId=id", "author", null);
	}
	
	/**
	 * 查询某个结伴帖的详情
	 * @param paramMap
	 * @return
	 */
	public Map<String, Object> invitationInfo(String id)
	{
		Map<String, Object> paramMap = ImmutableMap.of("id", (Object)id);
		List<Map<String, Object>> invitationInfoList = listDetail(paramMap);
		return ListUtil.getSingle(invitationInfoList);
	}
	
	
	
	/**
	 * 新增结伴帖
	 * @param ivt
	 * @return
	 */
	@Transactional
	public Invitation newInvitation(Invitation ivt)
	{
		long userId = MemberService.getCurrentUserId();
		User user = userService.info(userId);
		ivt.setUserId(userId);
		String sex = user.getSex();
		if (sex != null)
		{
			ivt.setAuthorSex(sex.toString());
		}
		
		this.insert(ivt);
		/*
		 * 把新的结伴帖中的图片插入 tb_invitation_img
		 */
		List<InvitationImg> imgList = ivt.getImgList();
		for(InvitationImg img : imgList)
		{
			img.setInvitationId(ivt.getId());
			imgService.insert(img);
		}
		return ivt;
	}
	
	
	/**
	 * 编辑结伴帖
	 * @param ivt
	 * @return
	 */
	@Transactional
	public Invitation editInvitation(Invitation ivt)
	{
		this.update(ivt);
		
		/*
		 * 更新图片信息，删除原有图片，插入新的图片
		 */
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("invitationId", ivt.getId());
		List<InvitationImg> imgList = imgService.list(parmMap);
		for(InvitationImg img : imgList)
		{
			imgService.del(img.getId() + "");
		}
		List<InvitationImg> newImgList = ivt.getImgList();
		for(InvitationImg img : newImgList)
		{
			img.setInvitationId(ivt.getId());
			imgService.insert(img);
		}
		return ivt;
	}
	
	
	/**
	 * 删除结伴帖
	 * @param ivt
	 * @return
	 */
	@Transactional
	public void delInvitation(String id)
	{
		this.del(id);
		Map<String, Object> parmMap = new HashMap<String, Object>();
		parmMap.put("invitationId", id);
		
		/*
		 * 删除图片列表
		 */
		List<InvitationImg> imgList = imgService.list(parmMap);
		for(InvitationImg img : imgList)
		{
			imgService.del(img.getId() + "");
		}
		
		/*
		 * 删除评论列表
		 */
		List<InvitationComment> commentList = cmtService.list(parmMap);
		for(InvitationComment ict : commentList)
		{
			cmtService.del(ict.getId() + "");
		}
		
		/*
		 * 删除报名列表
		 */
		List<InvitationJoin> joinList = joinService.list(parmMap);
		for(InvitationJoin jon : joinList)
		{
			joinService.del(jon.getId() + "");
		}
		
		/*
		 * 删除点赞列表
		 */
		List<InvitationLike> likeList = likeService.list(parmMap);
		for(InvitationLike lik : likeList)
		{
			likeService.del(lik.getId() + "");
		}
	}
	
	/**
	 * 根据用户id列表查询，返回每个用户发布的最新结伴帖组成的结伴帖列表
	 * @param uidList
	 * @return
	 */
	public List<Invitation> invitationList(List<Long> uidList)
	{
		List<Invitation> invitationList = null;
		for(long uid : uidList)
		{
			Map<String, Object> paraMap = new HashMap<String, Object>();
			paraMap.put("userId", uid);
			List<Invitation> uInvitationList = mapper.list(paraMap);
			invitationList.add(uInvitationList.get(0));
		}
		return invitationList;
	}
}
