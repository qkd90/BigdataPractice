package com.hmlyinfo.app.soutu.user.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.service.MemberService;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.user.domain.LeavedMessage;
import com.hmlyinfo.app.soutu.user.mapper.LeavedMessageMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;

@Service
public class LeavedMessageService extends BaseService<LeavedMessage, Long>{

	@Autowired
	private LeavedMessageMapper<LeavedMessage> mapper;
	
	@Autowired
	private UserService userService;

	@Override
	public BaseMapper<LeavedMessage> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	//插入新的留言或者回复留言
	public void newMessage(Map<String, Object> paramMap)
	{
		if(paramMap.get("exid") != null){
			String id = (String) paramMap.get("exid");
			LeavedMessage lm = mapper.selById(id);
			lm.setReplied("T");
			mapper.update(lm);
			
			LeavedMessage newLm = new LeavedMessage();
			newLm.setFirstUser(MemberService.getCurrentUserId());
			newLm.setSecondUser(lm.getFirstUser());
			newLm.setContent((String) paramMap.get("content"));
			mapper.insert(newLm);
		}else{
			LeavedMessage newLm = new LeavedMessage();
			newLm.setFirstUser(MemberService.getCurrentUserId());
			newLm.setSecondUser(Long.parseLong((String) paramMap.get("secondUser")));
			newLm.setContent((String) paramMap.get("content"));
			mapper.insert(newLm);
		}
	}
	
	
	//留言列表
	public List<LeavedMessage> list(Map<String, Object> paramMap)
	{
		List<LeavedMessage> lmList = mapper.list(paramMap);
		List<Long> idList = new ArrayList<Long>();
		List<LeavedMessage> res = new ArrayList<LeavedMessage>();
		for(int i = 0; i < lmList.size(); i++){
			long secId = lmList.get(i).getFirstUser();
			if(!idList.contains(secId)){
				res.add(lmList.get(i));
				idList.add(secId);
			}
		}
		return res;
	}
	
	//未读留言数量
	public int messageCount()
	{
		long uid = MemberService.getCurrentUserId();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("secondUser", uid);
		paramMap.put("readed", "F");
		List<LeavedMessage> lmList = list(paramMap);
		return lmList.size();
	}

	//留言和用户信息
	public List<Map<String, Object>> listMessage()
	{
		long uid = MemberService.getCurrentUserId();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("secondUser", uid);
		List<LeavedMessage> lmList = list(paramMap);
		List<String> uids = ListUtil.getIdList(lmList, "firstUser");
		Map<String, Object> userParamMap = Maps.newHashMap();
		userParamMap.put("ids", uids);
		List<User> userList = userService.listColumns(userParamMap,
				Lists.newArrayList("id", "userface", "nickname", "sex", "birthday"));
		return ListUtil.listJoin(lmList, userList, "secondUser=id", "author", null);
	}
	
	//和某个人的留言及回复记录
	public List<LeavedMessage> listDetail(Map<String, Object> paramMap)
	{
		long uid = MemberService.getCurrentUserId();
		paramMap.put("firstUser", uid);
		return mapper.listrecord(paramMap);
	}
	
	//把某个人的所有留言置为已读
	public void updateStatus(String firstUser)
	{
		long secondUser = MemberService.getCurrentUserId();
		Map<String, Object> paramMap = new HashMap<String, Object>();
		paramMap.put("firstUser", firstUser);
		paramMap.put("secondUser", secondUser);
		List<LeavedMessage> lmList = mapper.list(paramMap);
		for(LeavedMessage lm : lmList){
			lm.setReaded("T");
			mapper.update(lm);
		}
	}
}
