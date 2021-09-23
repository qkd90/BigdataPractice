package com.data.data.hmly.service.mobile;

import com.data.data.hmly.enums.ThirdPartyUserType;
import com.data.data.hmly.service.AdsService;
import com.data.data.hmly.service.SysResourceMapService;
import com.data.data.hmly.service.UserRelationService;
import com.data.data.hmly.service.area.SupplierCityService;
import com.data.data.hmly.service.area.entity.SupplierCity;
import com.data.data.hmly.service.dao.MemberDao;
import com.data.data.hmly.service.entity.Ads;
import com.data.data.hmly.service.entity.Member;
import com.data.data.hmly.service.entity.SysResourceMap;
import com.data.data.hmly.service.entity.SysUnit;
import com.data.data.hmly.service.entity.User;
import com.data.data.hmly.service.entity.UserStatus;
import com.data.data.hmly.service.entity.UserType;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.user.dao.ThirdPartyUserDao;
import com.data.data.hmly.service.user.entity.ThirdPartyUser;
import com.data.data.hmly.service.wechat.WechatAccountService;
import com.data.data.hmly.service.wechat.WechatService;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.framework.hibernate.util.Page;
import com.gson.bean.UserInfo;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by guoshijie on 2015/11/20.
 */
@Service
public class MobileService {

	private static final String TOP_SCROLL_BANNER = "mobile_top_roll_banner";
    private static final String QRCODE_SCENE = "account-default";

	@Resource
	private SysResourceMapService sysResourceMapService;
	@Resource
	private AdsService adsService;
	@Resource
	private MemberDao memberDao;
	@Resource
	private ThirdPartyUserDao thirdPartyUserDao;
	@Resource
	private MobileLineService mobileLineService;
	@Resource
	private SupplierCityService supplierCityService;
	@Resource
	private WechatService wechatService;
    @Resource
    private UserRelationService userRelationService;
    @Resource
    private WechatAccountService wechatAccountService;

	public List<Ads> listTopAds() {
		List<SysResourceMap> resourceList = sysResourceMapService.getByDesc(TOP_SCROLL_BANNER);
		List<Ads> topAds = new ArrayList<Ads>();
		//todo: should add sys_site as query condition
//		User user = new User();
//		user.setSysSite(sysSite);
		for (SysResourceMap sysResourceMap : resourceList) {
			Ads condition = new Ads();
			condition.setSysResourceMap(sysResourceMap);
//			condition.setUser(user);
			List<Ads> list = adsService.getAdsList(condition, new Page(0, Integer.MAX_VALUE));
			topAds.addAll(list);
		}
		return topAds;
	}

	public List<Line> listTopLines() {
		return mobileLineService.listTopLines();
	}

	public List<SupplierCity> listSupplierCity(Long supplierId) {
		SysUnit sysUnit = new SysUnit();
		sysUnit.setId(supplierId);
		SupplierCity condition = new SupplierCity();
		condition.setSupplier(sysUnit);
		return supplierCityService.list(condition, true, null);
	}
	
	/**
	 * 判断openid是否已经存在，存在直接返回用户，不存在创建并返回用户
	 * @author caiys
	 * @date 2015年11月26日 下午4:06:17
	 * @param accountId
	 * @param openId
	 * @return
	 */
	public User doLogin(String accountId, String openId) {
		Member user = memberDao.findUserByAccount(openId);
		if (user == null) {
			WechatAccount account = wechatAccountService.get(Long.valueOf(accountId));
			
			user = new Member();
			user.setAccount(openId);
			user.setCreatedTime(new Date());
			user.setUserType(UserType.USER);
			user.setStatus(UserStatus.activity);
			user.setSysSite(account.getCompanyUnit().getSysSite());
			memberDao.save(user);
			
			ThirdPartyUser thirdPartyUser = new ThirdPartyUser();
			thirdPartyUser.setOpenId(openId);
			thirdPartyUser.setUserId(user.getId());
			thirdPartyUser.setAccountId(Long.valueOf(accountId));
			thirdPartyUser.setType(ThirdPartyUserType.weixin);
			thirdPartyUser.setCreatedTime(new Date());
			thirdPartyUserDao.save(thirdPartyUser);
		}
		return user;
	}


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
			UserInfo userInfo = com.gson.oauth.User.getUserInfo(accessToken, childUser.getCurrLoginOpenId());
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


	public String doGetQrCode(Long accountId) {

		try {
			WechatAccount wechatAccount = wechatAccountService.get(accountId);
			if (wechatAccount.getQrcode() == null || "".equals(wechatAccount.getQrcode())) {
				String qrcodeUrl = wechatService.doCreateLimitQrcodeUrl(accountId, QRCODE_SCENE);
				wechatAccount.setQrcode(qrcodeUrl);
				wechatAccountService.updateWechatAccount(wechatAccount);
			}
			return wechatAccount.getQrcode();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}


}
