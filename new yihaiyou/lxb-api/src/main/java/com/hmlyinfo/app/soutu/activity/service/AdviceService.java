package com.hmlyinfo.app.soutu.activity.service;

import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.service.UserService;
import com.hmlyinfo.app.soutu.activity.domain.Advice;
import com.hmlyinfo.app.soutu.activity.domain.Reply;
import com.hmlyinfo.app.soutu.activity.mapper.AdviceMapper;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class AdviceService extends BaseService<Advice, Long>{

	@Autowired
	private AdviceMapper<Advice> mapper;

	@Autowired
	ReplyService replyService;
	@Autowired
	UserService userService; 
	@Override
	public BaseMapper<Advice> getMapper() 
	{
		return mapper;
	}
	
	@Override
	public String getKey() 
	{
		return "id";
	}
	
	/**
	 * 意见和回复
	 * @return
	 */
	public List<Map<String, Object>> listWithReply(Map<String , Object> adviceParamsMap){
		Map<String, Object> replyParamsMap = new HashMap<String, Object>();
		//查询出建议明细
		List<Advice> adviceList = list(adviceParamsMap);
		//查询出回复明细
		List<Reply> replyList = replyService.list(replyParamsMap);
		List<Map<String, Object>> resultList = ListUtil.listJoin(adviceList, replyList, "id=adviceId", "adviceReply", null);
		
		List<Long> userIdList = ListUtil.getIdList(adviceList, "userId");
		Map<String, Object> userParamsMap = new HashMap<String,Object>();
		userParamsMap.put("ids", userIdList);
		List<User> userList = userService.list(userParamsMap);
		List<Map<String, Object>> userMapList = new ArrayList<Map<String,Object>>(); 
		for (int i = 0; i < userList.size(); i++) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("username", userList.get(i).getUsername());
			map.put("userface", userList.get(i).getUserface());
			map.put("id", userList.get(i).getId());
			userMapList.add(map);
		}
		resultList = ListUtil.listJoin(resultList, userList, "userId=id", "user", null);
		return resultList;
		
	}

}
