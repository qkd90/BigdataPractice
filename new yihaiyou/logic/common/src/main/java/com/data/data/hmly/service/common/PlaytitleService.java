package com.data.data.hmly.service.common;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.data.data.hmly.service.common.dao.PlaytitleDao;
import com.data.data.hmly.service.common.entity.Playtitle;
import com.framework.hibernate.util.Criteria;

@Service
public class PlaytitleService {
	@Resource
	private PlaytitleDao playtitleDao;
	
	/**
	 * 查询主题列表
	 * @author caiys
	 * @date 2015年10月21日 下午6:07:51
	 * @param playtitle
	 * @return
	 */
	public List<Playtitle> findPlaytitleList(Playtitle playtitle) {
		Criteria<Playtitle> criteria = new Criteria<Playtitle>(Playtitle.class);
		criteria.orderBy("id", "asc");
		return playtitleDao.findByCriteria(criteria);
	}
}
