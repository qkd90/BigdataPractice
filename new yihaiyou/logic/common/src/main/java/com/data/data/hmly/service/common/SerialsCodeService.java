package com.data.data.hmly.service.common;

import com.data.data.hmly.service.common.dao.SerialsCodeDao;
import com.data.data.hmly.service.common.entity.CodeRepository;
import com.data.data.hmly.service.common.entity.SerialsCode;
import com.framework.hibernate.util.Criteria;
import com.framework.hibernate.util.Page;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by guoshijie on 2015/11/9.
 */
@Service
public class SerialsCodeService {

	private static final int CODE_LENGTH = 8;

	@Resource
	private SerialsCodeDao serialsCodeDao;

	public void save(SerialsCode serialsCode) {
		serialsCodeDao.save(serialsCode);
	}

	public void save(List<SerialsCode> list) {
		serialsCodeDao.save(list);
	}

	public void doCreate(CodeRepository codeRepository) {
		List<SerialsCode> list = new ArrayList<SerialsCode>();
		for (int i = codeRepository.getCount()-1; i >= 0; i--) {
			SerialsCode serialsCode = new SerialsCode();
			serialsCode.setCode(generateCode(i));
			serialsCode.setOccupied(false);
			serialsCode.setRepository(codeRepository);
			serialsCode.setCreateTime(new Date());
			serialsCode.setUpdateTime(new Date());
			list.add(serialsCode);
			if (i % 2000 == 0) {
				save(list);
				list.clear();
			}
		}
		save(list);
	}

	public String generateCode(Object o) {
		String s = o.toString();
		while (s.length() < CODE_LENGTH) {
			s = "0" + s;
		}
		return s;
	}

	public SerialsCode nextCode(Long repositoryId) {
		Criteria<SerialsCode> criteria = new Criteria<SerialsCode>(SerialsCode.class);
		criteria.eq("repository.id", repositoryId);
		criteria.eq("occupied", false);
		List<SerialsCode> list = serialsCodeDao.findByCriteria(criteria, new Page(0, 1));
		if (list.isEmpty()) {
			return null;
		}
		SerialsCode serialsCode = list.get(0);
		serialsCode.setOccupied(true);
		serialsCodeDao.update(serialsCode);
		return serialsCode;
	}

}
