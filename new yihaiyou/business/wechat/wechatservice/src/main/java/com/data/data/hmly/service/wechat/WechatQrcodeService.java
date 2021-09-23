package com.data.data.hmly.service.wechat;

import com.data.data.hmly.service.wechat.dao.WechatQrcodeDao;
import com.data.data.hmly.service.wechat.entity.WechatQrcode;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by vacuity on 15/11/26.
 */

@Service
public class WechatQrcodeService {

    @Resource
    private WechatQrcodeDao wechatQrcodeDao;


    public WechatQrcode get(Long id) {
        return wechatQrcodeDao.get(id);
    }

    public void save(WechatQrcode wechatQrcode) {
        wechatQrcodeDao.saveOrUpdate(wechatQrcode, wechatQrcode.getId());
    }

    public List<WechatQrcode> getList(Long accountId, Page page) {
        return wechatQrcodeDao.getList(accountId, page);
    }

    public void del(Long id) {
        WechatQrcode wechatQrcode = get(id);
        wechatQrcodeDao.delete(wechatQrcode);
    }

    public WechatQrcode findQrcodeByAccountAndSceneStr(Long accountId, String sceneStr) {

        return wechatQrcodeDao.findQrcodeByAccountAndSceneStr(accountId, sceneStr);
    }


}
