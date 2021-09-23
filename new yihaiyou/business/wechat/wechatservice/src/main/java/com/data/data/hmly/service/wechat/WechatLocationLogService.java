package com.data.data.hmly.service.wechat;

import com.data.data.hmly.service.user.ThirdPartyUserService;
import com.data.data.hmly.service.user.entity.ThirdPartyUser;
import com.data.data.hmly.service.wechat.dao.WechatLocationLogDao;
import com.data.data.hmly.service.wechat.entity.WechatLocationLog;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.hibernate.criterion.Order;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Kingsley on 2016/6/2.
 */
@Service
public class WechatLocationLogService {

    @Resource
    private ThirdPartyUserService thirdPartyUserService;
    @Resource
    private WechatLocationLogDao wechatLocationLogDao;

    public WechatLocationLog findUserLastLocation(Long userid) {
        ThirdPartyUser thirdPartyUser = thirdPartyUserService.findByUserId(userid);
        Criteria<WechatLocationLog> c = new Criteria<WechatLocationLog>(WechatLocationLog.class);
        c.eq("thirdPartyUser.id", thirdPartyUser.getId());
        c.addOrder(Order.desc("id"));
        List<WechatLocationLog> locationLogs = wechatLocationLogDao.findByCriteria(c, new Page(1, 1));
        return locationLogs.isEmpty() ? null : locationLogs.get(0);
    }

}
