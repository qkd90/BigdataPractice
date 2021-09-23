package com.data.data.hmly.service.wechat;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.wechat.dao.WechatDataImgDao;
import com.data.data.hmly.service.wechat.entity.WechatDataImg;

@Service
public class WechatDataImgService {

	@Resource
	private WechatDataImgDao dataImgDao;
	
	public void save(WechatDataImg wechatDataImg) {
		dataImgDao.save(wechatDataImg);
	}

	public List<WechatDataImg> findDataImgs(WechatDataImg di, SysUser user) {
		return dataImgDao.findDataImgs( di,  user);
	}

    
}
