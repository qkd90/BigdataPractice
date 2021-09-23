package com.data.data.hmly.service.wechat;

import com.data.data.hmly.service.wechat.dao.WechatFollowerDao;
import com.data.data.hmly.service.wechat.dao.WechatSupportAccountDao;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.data.data.hmly.service.wechat.entity.WechatFollower;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vacuity on 15/11/20.
 */

@Service
public class WechatFollowerService {

    @Resource
    private WechatFollowerDao wechatFollowerDao;
    @Resource
    private WechatSupportAccountDao wechatSupportAccountDao;

    public WechatFollower get(Long id) {
    	return wechatFollowerDao.get(id);
    }
    
	public List<WechatFollower> findFollowersList(WechatAccount account, Page page) {
		return wechatFollowerDao.getFollowerList(account, page);
	}
	
	public List<WechatFollower> getFollowerListAll(WechatAccount account) {
		return wechatFollowerDao.getFollowerListAll(account);
	}

	public List<WechatFollower> findFollowersList(WechatFollower follower,
                                                  String isSupport, Page pageInfo) {
        Boolean isHasSupport = wechatSupportAccountDao.checkSupporter(follower.getFollowAccount().getId(), null);
        List<WechatFollower> followerList = wechatFollowerDao.getFollowerList(follower, isSupport, isHasSupport, pageInfo);
        for (WechatFollower wechatFollower : followerList) {
            // 检查是否该公众号下客服
            Boolean isSupporter = wechatSupportAccountDao.checkSupporter(wechatFollower.getFollowAccount().getId(), wechatFollower.getOpenId());
            wechatFollower.setIsSupporter(isSupporter);
        }
        return followerList;
	}

	public WechatFollower getByOpenId(String openId, Long wechatAccountId) {
		return wechatFollowerDao.getByOpenId(openId, wechatAccountId);
	}
}
