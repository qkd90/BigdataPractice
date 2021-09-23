package com.data.data.hmly.service.wechat;

import com.data.data.hmly.enums.ThirdPartyUserType;
import com.data.data.hmly.service.TbAreaService;
import com.data.data.hmly.service.entity.TbArea;
import com.data.data.hmly.service.user.ThirdPartyUserService;
import com.data.data.hmly.service.user.entity.ThirdPartyUser;
import com.data.data.hmly.service.wechat.dao.WechatLocationLogDao;
import com.data.data.hmly.service.wechat.entity.WechatAccount;
import com.data.data.hmly.service.wechat.entity.WechatLocationLog;
import com.data.hmly.service.translation.geo.baidu.BaiduGeoCoderService;
import com.data.hmly.service.translation.geo.baidu.pojo.GeoCoder.RederReverse;
import com.gson.bean.InMessage;
import com.gson.inf.EventTypes;
import com.zuipin.util.PropertiesManager;
import com.zuipin.util.SpringContextHolder;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by Kingsley on 2016/6/2.
 */
@Service
public class WechatEventService {

    @Resource
    private ThirdPartyUserService thirdPartyUserService;

    private BaiduGeoCoderService baiduGeoCoderService = new BaiduGeoCoderService();
    @Resource
    private TbAreaService tbAreaService;
    @Resource
    private WechatLocationLogDao wechatLocationLogDao;

    private PropertiesManager propertiesManager = SpringContextHolder.getBean("propertiesManager");

    private final String baiduKey = propertiesManager.getString("BAIDU_KEY");

    private Log log = LogFactory.getLog(WechatEventService.class);

    public void doHandler(InMessage inMessage, WechatAccount account) {
        if (EventTypes.LOCATION.toString().equals(inMessage.getEvent())) {
            updateUserLocation(inMessage, account);
        }
    }

    private void updateUserLocation(InMessage inMessage, WechatAccount account) {
        String openId = inMessage.getFromUserName();
        RederReverse reverse = baiduGeoCoderService.getRederReverse(inMessage.getLatitude(), inMessage.getLongitude(), baiduKey);
        RederReverse.Addresscomponent addresscomponent = reverse.result.addressComponent;
        TbArea area = tbAreaService.getArea(Long.valueOf(addresscomponent.adcode) / 100 * 100);
        ThirdPartyUser thirdPartyUser = thirdPartyUserService.getByOpenIdAndType(openId, ThirdPartyUserType.weixin, account.getId());
        thirdPartyUser.setTbArea(area);
        thirdPartyUserService.update(thirdPartyUser);
        WechatLocationLog log = new WechatLocationLog();
        log.setTbArea(area);
        log.setCreateTime(new Date());
        log.setLatitude(inMessage.getLatitude());
        log.setLongitude(inMessage.getLongitude());
        log.setThirdPartyUser(thirdPartyUser);
        wechatLocationLogDao.save(log);
    }
}
