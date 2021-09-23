package com.data.data.hmly.service.lvxbang;

import com.data.data.hmly.enums.ThirdPartyUserType;
import com.data.data.hmly.service.MemberService;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysSite;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.lvxbang.util.WeixinSettings;
import com.data.data.hmly.service.user.ThirdPartyUserService;
import com.data.data.hmly.service.user.entity.ThirdPartyUser;
import com.data.data.hmly.service.user.vo.OpenLoginInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author Jonathan.Guo
 */
@Service
public class ThirdPartyLonginService {

//    @Resource
//    private UserService userService;
    @Resource
    private MemberService memberService;
    @Resource
    private ThirdPartyUserService thirdPartyUserService;

    public Member login(String openId, ThirdPartyUserType type, Long accountId) {
        ThirdPartyUser thirdPartyUser = thirdPartyUserService.getByOpenIdAndType(openId, type, accountId);
        if (thirdPartyUser == null) {
            return null;
        }
        return memberService.get(thirdPartyUser.getUserId());
    }

    public Member create(OpenLoginInfo loginInfo) {
        SysSite sysSite = new SysSite();
        sysSite.setId(-1l);
        SysUnit sysUnit = new SysUnit();
        sysUnit.setId(-1l);
        sysUnit.setSysSite(sysSite);
        return thirdPartyUserService.create(loginInfo, sysUnit, WeixinSettings.LOGIN_ACCOUNT_ID);
    }

    public Member create(OpenLoginInfo loginInfo, Long accountId) {
        SysSite sysSite = new SysSite();
        sysSite.setId(-1l);
        SysUnit sysUnit = new SysUnit();
        sysUnit.setId(-1l);
        sysUnit.setSysSite(sysSite);
        return thirdPartyUserService.create(loginInfo, sysUnit, accountId);
    }

    public Member update(Member member, OpenLoginInfo loginInfo, Long accountId) {
        SysSite sysSite = new SysSite();
        sysSite.setId(-1l);
        SysUnit sysUnit = new SysUnit();
        sysUnit.setId(-1l);
        sysUnit.setSysSite(sysSite);
        return thirdPartyUserService.update(member, loginInfo, sysUnit, accountId);
    }
}
