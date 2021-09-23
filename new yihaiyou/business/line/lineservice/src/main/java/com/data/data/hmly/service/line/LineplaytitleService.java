package com.data.data.hmly.service.line;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.data.data.hmly.service.line.dao.LineplaytitleDao;
import com.data.data.hmly.service.line.entity.Lineplaytitle;

@Service
public class LineplaytitleService {
	@Resource
	private LineplaytitleDao lineplaytitleDao;
	
	/**
	 * 查询线路主题
	 * @author caiys
	 * @date 2015年10月23日 下午11:29:15
	 * @param lineplaytitle
	 * @return
	 */
	public List<Lineplaytitle> findLineplaytitle(Lineplaytitle lineplaytitle) {
		return lineplaytitleDao.findLineplaytitle(lineplaytitle);
	}
}
