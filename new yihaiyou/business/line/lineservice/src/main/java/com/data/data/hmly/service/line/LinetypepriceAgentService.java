package com.data.data.hmly.service.line;

import com.data.data.hmly.service.line.dao.LinetypepriceAgentDao;
import com.data.data.hmly.service.line.dao.LinetypepriceDao;
import com.data.data.hmly.service.line.dao.LinetypepricedateDao;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypeprice;
import com.data.data.hmly.service.line.entity.LinetypepriceAgent;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

@Service
public class LinetypepriceAgentService {
	@Resource
	private LinetypepriceAgentDao linetypepriceAgentDao;


	public void saveAll(List<LinetypepriceAgent> linetypepriceAgents) {
		linetypepriceAgentDao.save(linetypepriceAgents);
	}

	public void delAllTypePrice(Line line) {

		List<LinetypepriceAgent> linetypepriceAgents =  getTypePriceList(line);

		linetypepriceAgentDao.deleteAll(linetypepriceAgents);

	}

	public List<LinetypepriceAgent> getTypePriceList(Line line) {

		return linetypepriceAgentDao.getTypePriceList(line);

	}

}
