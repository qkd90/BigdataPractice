package com.data.spider.service.tb;

import com.data.spider.service.dao.TbAreaExtendDao;
import com.data.spider.service.pojo.tb.TbAreaExtend;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TbAreaExtendService {

	@Resource
	private TbAreaExtendDao TbAreaExtendDao;


	public void save(TbAreaExtend tbAreaExtend) {
		TbAreaExtendDao.save(tbAreaExtend);
	}

	public void update(TbAreaExtend tbAreaExtend) {
		TbAreaExtendDao.update(tbAreaExtend);
	}


	public List<TbAreaExtend> gets(int size, Criteria<TbAreaExtend> c) {
		Page page = new Page(1, size);
		List<TbAreaExtend> dis = TbAreaExtendDao.findByCriteriaWithOutCount(c, page);
		return dis;
	}
}
