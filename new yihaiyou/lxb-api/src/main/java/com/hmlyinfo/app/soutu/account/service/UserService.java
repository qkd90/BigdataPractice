package com.hmlyinfo.app.soutu.account.service;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.hmlyinfo.app.soutu.account.domain.ThridPartyUser;
import com.hmlyinfo.app.soutu.account.domain.User;
import com.hmlyinfo.app.soutu.account.mapper.ThridPartyUserMapper;
import com.hmlyinfo.app.soutu.account.mapper.UserMapper;
import com.hmlyinfo.app.soutu.base.ErrorCode;
import com.hmlyinfo.app.soutu.invitation.domain.Invitation;
import com.hmlyinfo.app.soutu.invitation.mapper.InvitationMapper;
import com.hmlyinfo.app.soutu.point.domain.Point;
import com.hmlyinfo.app.soutu.point.service.PointService;
import com.hmlyinfo.app.soutu.scenic.domain.City;
import com.hmlyinfo.app.soutu.scenic.service.CityService;
import com.hmlyinfo.base.exception.BizValidateException;
import com.hmlyinfo.base.persistent.BaseMapper;
import com.hmlyinfo.base.service.BaseService;
import com.hmlyinfo.base.util.ListUtil;
import com.hmlyinfo.base.util.MD5;
import com.hmlyinfo.base.util.ParamUtil;

import org.apache.commons.beanutils.PropertyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class UserService extends BaseService<User, Long> implements IUserService{

    @Autowired
    UserMapper<User> mapper;
    @Autowired
    InvitationMapper<Invitation> iMapper;
    @Autowired
    PointService pointService;
    @Autowired
    CityService cityService;
    @Autowired
    ThridPartyUserMapper<ThridPartyUser> thridPartyUserMapper;

    @Override
    public BaseMapper<User> getMapper() {
        return mapper;
    }

    @Override
    public String getKey() {
        return "id";
    }
    
    public User summaryinfo(Long id)
    {
    	return mapper.summaryinfo(id);
    }

    public Map<String, Object> detail(Long id) {
        User user = info(id);
        //user.setUsername(null);
        user.setEmail(null);
        try {
            Map<String, Object> map = PropertyUtils.describe(user);
            //通过积分计算等级
            List<Point> points = pointService.list(Collections.<String, Object>singletonMap("userId", id));
            
            if(user.getHometown() != null && !user.getHometown().equals(""))
            {
            	City hometown = cityService.getByCityCode(user.getHometown());
            	map.put("hometown", hometown);
            }else {
            	map.put("hometown", null);
			}
            if(user.getLocation() != null && !user.getLocation().equals(""))
            {
            	City location = cityService.getByCityCode(user.getLocation());
            	map.put("location", location);
            }else {
            	map.put("location", null);
			}

            if (points == null || points.isEmpty()) {
                map.put("level", 1);
                return map;
            }
            int level = points.get(0).getPoint() / 1000;
            if (level < 10) {
                map.put("level", level + 1);
            } else if (level < 20) {
                map.put("level", 11);
            } else {
                map.put("level", 12);
            }

            return map;
        } catch (Exception e) {
            throw new BizValidateException(ErrorCode.ERROR_51001);
        }
    }

    public void updatePassword(Map<String, Object> params) {
        params.put("password", MD5.GetMD5Code(params.get("oldPassword").toString()));
        List<User> users = list(params);
        if (users != null && !users.isEmpty()) {
            params.put("password", MD5.GetMD5Code(params.get("newPassword").toString()));
            mapper.updatePassword(params);
        } else {
            throw new BizValidateException(ErrorCode.ERROR_51005);
        }
    }

    public List<User> listColumns(Map<String, Object> params, List<String> columns) {
        params.put("needColumns", columns);
        return mapper.listColumns(params);
    }
    
    /**
	 * 更新激活状态
	 * <ul>
	 * 	<li>必选：用户编号:{userId}</li>
	 *  <li>必选：激活状态:{status}</li>
	 * </ul>
	 * @return
	 */
	public void updateUserStatus(Map<String, Object> paramMap) {
		User user = mapper.selById(paramMap.get("userId").toString());
		user.setStatus(Integer.valueOf((String) paramMap.get("status")));
		mapper.update(user);
	}
	
	
	
	/**
	 * 查询附近驴友列表
	 * @param paramMap
	 * @return
	 */
	public List<Map<String, Object>> listAround(Map<String, Object> paramMap){
		double lng = Double.parseDouble((String) paramMap.get("lng"));
		double lat = Double.parseDouble((String) paramMap.get("lat"));
		//
		int days = Integer.parseInt((String) paramMap.get("days"));
		Date endDate = new Date(System.currentTimeMillis() - days * 24 * 3600 * 1000); //以后的时间
		/*
		DateFormat df = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM,Locale.CHINA);
		String dt = df.format(date);
		System.out.println(dt);
		*/
		//地球半径
		double EARTH_RADIUS = 6371393;
		
		//附近人最大距离
		double distance = 20000;
		
		// 计算经度弧度,从弧度转换为角度
		double dLongitude = 2 * (Math.asin(Math.sin(distance
	            / (2 * EARTH_RADIUS))
	            / Math.cos(Math.toRadians(lat))));
	    dLongitude = Math.toDegrees(dLongitude);
	    dLongitude = Math.abs(dLongitude);
	    
	    // 计算纬度角度
	    double dLatitude = distance / EARTH_RADIUS;
	    dLatitude = Math.toDegrees(dLatitude);
	    dLatitude = Math.abs(dLatitude);
	    
	    //根据经纬度范围和性别查询
	    Map<String, Object> squareMap = new HashMap<String, Object>();
	    squareMap.put("lngS", lng - dLongitude);
	    squareMap.put("lngE", lng + dLongitude);
	    squareMap.put("latS", lat - dLatitude);
	    squareMap.put("latE", lat + dLatitude);
	    squareMap.put("endDate", endDate);
	    if(paramMap.get("sex") != null)
	    	squareMap.put("sex", paramMap.get("sex"));
	    List<User> userList = this.list(squareMap);
	    
	    //算出用户列表中每个人和当前位置的距离，得到距离列表
	    List<Map<String, Object>> disList = new ArrayList<Map<String, Object>>();
	    for(User user : userList)
	    {
	    	Map<String, Object> disMap = new HashMap<String, Object>();
	    	disMap.put("uid", user.getId());
	    	
	    	//计算用户所在位置和当前位置的距离
	    	double radLat1 = lat * Math.PI / 180.0; 
	        double radLat2 = user.getLat() * Math.PI / 180.0;
	  
	        double radLon1 = lng * Math.PI / 180.0; 
	        double radLon2 = user.getLng() * Math.PI / 180.0;  
	    
	        double a = radLat1 - radLat2;  
	        double b = radLon1 - radLon2;  
	        double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a/2),2)+Math.cos(radLat1)*Math.cos(radLat2)*Math.pow(Math.sin(b/2),2)));  
	        s = s * EARTH_RADIUS;
	        s = Math.round(s * 10000) / 10000;
	        disMap.put("dis", s);
	        disList.add(disMap);
	    }
	    
	    //合并用户列表和距离列表
	    List<Map<String, Object>> resList = ListUtil.listJoin(userList, disList, "id=uid", "author", null);
	    
	    //对列表进行排序
	    Collections.sort(resList, new Comparator<Map<String, Object>>()
	    		{
	    			@Override
	    			public int compare(Map<String, Object> res1, Map<String, Object> res2) 
	    			{
	    				double dis1 = (Double) ((Map<String, Object>) res1.get("author")).get("dis");
	    				double dis2 = (Double) ((Map<String, Object>) res2.get("author")).get("dis");
	    				if(dis1 < dis2)
	    					return -1;
	    				else {
							return 1;
						}
	    			}
	    		});
	    
	    //删除距离超过distance的数据项和用户自身
	    int resIndex;
	    //long userId = MemberService.getCurrentUserId();
	    long userId = 1000;
	    for(resIndex = 0; resIndex < resList.size(); resIndex++)
	    {
	    	double dis = (Double) ((Map<String, Object>) resList.get(resIndex).get("author")).get("dis");
	    	if((dis > distance) || (userId == (Long)resList.get(resIndex).get("id")))
	    	{
	    		resList.remove(resIndex);
	    	}
	    }
	    
	    //获取列表中用户的结伴帖数据
	    List<Invitation> iList = new ArrayList<Invitation>();
	    for(Map<String, Object> res : resList)
	    {
	    	Map<String, Object> userInfo = new HashMap<String, Object>();
	    	userInfo.put("userId", res.get("id"));
	    	List<Invitation> uiList = iMapper.list(userInfo);
	    	if(uiList.isEmpty())
	    	{
	    		Invitation iv = new Invitation();
	    		iv.setUserId((Long) res.get("id"));
	    		iList.add(iv);
	    		continue;
	    	}
	    	iList.add(uiList.get(0));
	    }
	    
	    return ListUtil.listJoin(resList, iList, "id=userId", "res", null);
	}

    public ThridPartyUser getThirdPartyUser(Long userId) {
        List<ThridPartyUser> thridPartyUserList = thridPartyUserMapper.list(Collections.<String, Object>singletonMap("userId", userId));
        if (thridPartyUserList.isEmpty()) {
            return null;
        }
        return thridPartyUserList.get(0);
    }

	@Override
	public boolean isUserExists(String username) {
		
		int counts = mapper.count(ImmutableMap.of("username", (Object)username));
		
		return counts > 0;
	}

	@Override
	public void updatePassword(String username, String password) {
		
		User user = (User) this.one(ImmutableMap.of("username", (Object)username));
		if (user != null)
		{
			Map<String, Object> paramMap = Maps.newHashMap();
			paramMap.put("password", MD5.GetMD5Code(password));
			paramMap.put("id", user.getId());
			
			mapper.updatePassword(paramMap);
		}
		
	}
}
