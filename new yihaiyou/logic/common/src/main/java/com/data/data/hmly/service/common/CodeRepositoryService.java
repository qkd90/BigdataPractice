package com.data.data.hmly.service.common;

import com.data.data.hmly.service.common.dao.CodeRepositoryDao;
import com.data.data.hmly.service.common.entity.CodeRepository;
import com.zuipin.util.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by guoshijie on 2015/11/9.
 */
@Service
public class CodeRepositoryService {

	@Resource
	private CodeRepositoryDao  codeRepositoryDao;
	@Resource
	private SerialsCodeService serialsCodeService;

	public void doCreate(String name, int count, String prefix) {
		CodeRepository codeRepository = new CodeRepository();
		codeRepository.setName(name);
		codeRepository.setCount(count);
		if (!StringUtils.isBlank(prefix)) {
			codeRepository.setPrefix(prefix);
		}
		codeRepository.setUpdateTime(new Date());
		codeRepository.setCreateTime(new Date());
		codeRepositoryDao.save(codeRepository);

		serialsCodeService.doCreate(codeRepository);
	}
}
