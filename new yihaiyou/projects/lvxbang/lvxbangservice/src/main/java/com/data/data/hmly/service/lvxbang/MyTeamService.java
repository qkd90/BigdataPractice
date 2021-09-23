package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.service.UserRelationService;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.wechat.WechatService;
import com.gson.bean.UserInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by caiys on 2016/7/20.
 */
@Service
public class MyTeamService {
    @Resource
    private WechatService wechatService;
    @Resource
    private UserRelationService userRelationService;


    /**
     * 判断用户是否关注公众号
     *
     * @param accountId
     * @param childUser
     * @param parentId
     * @return
     * @author vacuity
     */
    public Map<String, Object> doSubscribe(Long accountId, User childUser, Long parentId) {

        Map<String, Object> map = new HashMap<String, Object>();

        try {
            // 根据openId获取用户信息
            String accessToken = wechatService.doGetTokenBy(accountId);
            com.gson.oauth.User user = new com.gson.oauth.User();
            UserInfo userInfo = user.getUserInfo(accessToken, childUser.getCurrLoginOpenId());
            // 判断用户是否关注公众号
            if (userInfo.getSubscribe() == 0) {
                // 未关注
                map.put("error", false);
                map.put("subscribe", false);
                return map;
            } else {
                // 已关注
                // 建立上下级关系
                map.put("error", false);
                map.put("subscribe", true);
            }
            Boolean canBeInvited = true;
            // 自己不能点击自己分享的链接
            if (childUser.getId().equals(parentId)) {
                canBeInvited = false;
            }
            if (canBeInvited) { // 检查是否已经被邀请
                canBeInvited = userRelationService.canBeInvited(childUser);
            }

            if (canBeInvited) {
                map.put("canBeInvited", true);
                userRelationService.insertRelation(parentId, childUser);
            } else {
                map.put("canBeInvited", false);
            }

            return map;
        } catch (Exception e) {
            e.printStackTrace();
            map.put("error", true);
            map.put("subscribe", false);
            map.put("errMsg", e.getLocalizedMessage());
            return map;
        }

    }
}
