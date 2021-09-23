package com.data.data.hmly.service.wechat.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.data.data.hmly.service.entity.SysUser;
import com.data.data.hmly.service.wechat.entity.WechatDataImg;
import com.framework.hibernate.DataAccess;
import com.framework.hibernate.util.Criteria;

/**
 * Created by vacuity on 15/11/20.
 */

@Repository
public class WechatDataImgDao extends DataAccess<WechatDataImg> {

	public List<WechatDataImg> findDataImgs(WechatDataImg di, SysUser user) {
		
		Criteria<WechatDataImg> criteria = new Criteria<WechatDataImg>(WechatDataImg.class);
		if (di.getChildFolder() != null) {
			criteria.eq("childFolder", di.getChildFolder());
		}
		if (user != null) {
			criteria.eq("user", user);
		}
		return findByCriteria(criteria);
	}

}
