package com.data.data.hmly.service.user;

import com.data.data.hmly.enums.ThirdPartyUserType;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.entity.UserType;
import com.data.data.hmly.service.user.dao.ThirdPartyUserDao;
import com.data.data.hmly.service.user.entity.ThirdPartyUser;
import com.data.data.hmly.service.user.exception.ThirdPartyUserException;
import com.data.data.hmly.service.user.vo.OpenLoginInfo;
import com.framework.hibernate.util.Criteria;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @author Jonathan.Guo
 */
@Service
public class ThirdPartyUserService {

    @Resource
    private ThirdPartyUserDao thirdPartyUserDao;
    @Resource
    private MemberService memberService;
//    @Resource
//    private UserService userService;
//    @Resource
//    private UserExinfoService userExinfoService;

    public ThirdPartyUser get(Long id) {
        return thirdPartyUserDao.load(id);
    }

    public ThirdPartyUser save(ThirdPartyUser thirdPartyUser) {
        thirdPartyUserDao.save(thirdPartyUser);
        return thirdPartyUser;
    }

    @Transactional
    public Member create(OpenLoginInfo loginInfo, SysUnit sysUnit, Long accountId) {
        if (loginInfo.openId == null || loginInfo.type == null || sysUnit == null) {
            throw new ThirdPartyUserException(ThirdPartyUserException.ERROR_LACK_OF_INFO, "缺少部分数据");
        }

        Member user = new Member();
//        if (StringUtils.isNotBlank(loginInfo.unionId)) {
//            user.setAccount(loginInfo.unionId);
//        } else {
            user.setAccount(loginInfo.openId);
//        }
        user.setBalance(0D);
        user.setCreatedTime(new Date());
        user.setUserType(UserType.USER);
        user.setStatus(UserStatus.activity);
        user.setSysSite(sysUnit.getSysSite());
        user.setUnionId(loginInfo.unionId);
        user.setThirdPartyUserType(loginInfo.type);

        if (StringUtils.isNotBlank(loginInfo.nickName)) {
            String nickName = loginInfo.nickName.replaceAll("[\\ud800\\udc00-\\udbff\\udfff\\ud800-\\udfff]", "*");
//            user.setUserName(nickName);
            user.setNickName(nickName);
        }
        user.setHead(loginInfo.headPath);
        user.loginInfo();
        memberService.save(user);

        ThirdPartyUser thirdPartyUser = new ThirdPartyUser();
        thirdPartyUser.setOpenId(loginInfo.openId);
        thirdPartyUser.setUserId(user.getId());
        if (accountId != null) {
            thirdPartyUser.setAccountId(accountId);
        }
        thirdPartyUser.setType(loginInfo.type);
        thirdPartyUser.setCreatedTime(new Date());
        thirdPartyUserDao.save(thirdPartyUser);
        return user;
    }

    /**
     * 更新时检查对应的openId是否已经存在，不存在新增一条
     *
     * @param member
     * @param loginInfo
     * @param sysUnit
     * @param accountId
     * @return
     */
    public Member update(Member member, OpenLoginInfo loginInfo, SysUnit sysUnit, Long accountId) {
//        member.setAccount(loginInfo.openId);
//        member.setUnionId(loginInfo.unionId);
//        member.setUpdateTime(new Date());
//        member.setNickName(userInfo.getNickname());
//        member.setHeadImgUrl(userInfo.getHeadimgurl());
//        member.setThirdPartyUserType(loginInfo.type);
        member.loginInfo();
        memberService.update(member);
        // openId不存在，则新增一条关联
        ThirdPartyUser thirdPartyUser = getByOpenIdAndType(loginInfo.openId, loginInfo.type, accountId, member.getId());
        if (thirdPartyUser != null) {
            return member;
        }
        thirdPartyUser = new ThirdPartyUser();
        thirdPartyUser.setOpenId(loginInfo.openId);
        thirdPartyUser.setUserId(member.getId());
        if (accountId != null) {
            thirdPartyUser.setAccountId(accountId);
        }
        thirdPartyUser.setType(loginInfo.type);
        thirdPartyUser.setCreatedTime(new Date());
        thirdPartyUserDao.save(thirdPartyUser);
        return member;
    }

    public ThirdPartyUser getByOpenIdAndType(String openId, ThirdPartyUserType type, Long accountId) {
        ThirdPartyUser condition = new ThirdPartyUser();
        condition.setOpenId(openId);
        condition.setType(type);
        condition.setAccountId(accountId);
        Criteria<ThirdPartyUser> criteria = createCriteria(condition);
        return thirdPartyUserDao.findUniqueByCriteria(criteria);
    }

    public ThirdPartyUser getByUserIdAndType(Long userId, ThirdPartyUserType type, Long accountId) {
        ThirdPartyUser condition = new ThirdPartyUser();
        condition.setUserId(userId);
        condition.setType(type);
        condition.setAccountId(accountId);
        Criteria<ThirdPartyUser> criteria = createCriteria(condition);
        return thirdPartyUserDao.findUniqueByCriteria(criteria);
    }

    public ThirdPartyUser getByOpenIdAndType(String openId, ThirdPartyUserType type, Long accountId, Long userId) {
        ThirdPartyUser condition = new ThirdPartyUser();
        condition.setOpenId(openId);
        condition.setType(type);
        condition.setAccountId(accountId);
        condition.setUserId(userId);
        Criteria<ThirdPartyUser> criteria = createCriteria(condition);
        return thirdPartyUserDao.findUniqueByCriteria(criteria);
    }

    private Criteria<ThirdPartyUser> createCriteria(ThirdPartyUser thirdPartyUser, String... orderProperties) {
        Criteria<ThirdPartyUser> criteria = new Criteria<ThirdPartyUser>(ThirdPartyUser.class);
        if (orderProperties.length == 2) {
            criteria.orderBy(orderProperties[0], orderProperties[1]);
        } else if (orderProperties.length == 1) {
            criteria.orderBy(orderProperties[0], "asc");
        }
        if (thirdPartyUser.getId() != null) {
            criteria.eq("id", thirdPartyUser.getId());
        }
        if (StringUtils.isNotBlank(thirdPartyUser.getOpenId())) {
            criteria.eq("openId", thirdPartyUser.getOpenId());
        }
        if (thirdPartyUser.getType() != null) {
            criteria.eq("type", thirdPartyUser.getType());
            if (thirdPartyUser.getType() == ThirdPartyUserType.weixin) {
                criteria.eq("accountId", thirdPartyUser.getAccountId());
            }
        }
        if (thirdPartyUser.getAccountId() != null) {
            criteria.eq("accountId", thirdPartyUser.getAccountId());
        }
        if (thirdPartyUser.getUserId() != null) {
            criteria.eq("userId", thirdPartyUser.getUserId());
        }
        return criteria;
    }

    public void update(ThirdPartyUser thirdPartyUser) {
        thirdPartyUserDao.update(thirdPartyUser);
    }

    /**
     * 查询结果唯一会抛异常
     * @param userid
     * @return
     */
    @Deprecated
    public ThirdPartyUser findByUserId(Long userid) {
        Criteria<ThirdPartyUser> thirdPartyUserCriteria = new Criteria<>(ThirdPartyUser.class);
        thirdPartyUserCriteria.eq("userId", userid);
        return thirdPartyUserDao.findUniqueByCriteria(thirdPartyUserCriteria);
    }
}
