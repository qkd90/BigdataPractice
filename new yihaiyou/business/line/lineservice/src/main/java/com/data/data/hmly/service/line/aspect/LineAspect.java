package com.data.data.hmly.service.line.aspect;

import com.data.data.hmly.service.common.entity.enums.ProductStatus;
import com.data.data.hmly.service.line.LineService;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.Linetypepricedate;
import com.data.data.hmly.service.line.entity.enums.LineStatus;
import com.zuipin.util.PropertiesManager;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Aspect
@Service
public class LineAspect {
	@Resource
	private LineService lineService;
	@Resource
	private PropertiesManager propertiesManager;

	@After("(execution (* com.data.data.hmly.service.line.dao.LineDao.save(..))) or (execution (* com.data.data.hmly.service.line.dao.LineDao.insert(..))) or (execution (* com.data.data.hmly.service.line.dao.LineDao.update(..)))")
	public void lineFinds(JoinPoint joinPoint) {
		try {
			Boolean updateTicketIndex = propertiesManager.getBoolean("UPDATE_LINE_INDEX");
			if (updateTicketIndex != null && !updateTicketIndex) {
				return;
			}
			Object[] objs = joinPoint.getArgs();
            // 判断是单条还是多条
            if (objs[0] instanceof List) {
                List<Line> lineList = (List<Line>) objs[0];
                for (Line line : lineList) {
                    lineService.indexLineById(line.getId());
                    if (line.getLineStatus() != LineStatus.show || line.getStatus() != ProductStatus.UP) {
                        lineService.delLabelItems(line);
                    }
                }
            } else {
                Line line = (Line) objs[0];
                lineService.indexLineById(line.getId());
                if (line.getLineStatus() != LineStatus.show || line.getStatus() != ProductStatus.UP) {
                    lineService.delLabelItems(line);
                }
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@After("(execution (* com.data.data.hmly.service.line.dao.LinetypepricedateDao.save(..))) or (execution (* com.data.data.hmly.service.line.dao.LinetypepricedateDao.insert(..))) or (execution (* com.data.data.hmly.service.line.dao.LinetypepricedateDao.update(..)))")
	public void linetypepricedateVisits(JoinPoint joinPoint) {
		try {
			Boolean updateTicketIndex = propertiesManager.getBoolean("UPDATE_LINE_INDEX");
			if (updateTicketIndex != null && !updateTicketIndex) {
				return;
			}
			Object[] objs = joinPoint.getArgs();
			Linetypepricedate linetypepricedate = null;
			if (objs[0] instanceof List) {
				List<Linetypepricedate> list = (List<Linetypepricedate>) objs[0];
				linetypepricedate = list.get(0);
			} else {
				linetypepricedate = (Linetypepricedate) objs[0];
			}
            Line line = lineService.loadLine(linetypepricedate.getLineId());
			lineService.indexLineById(linetypepricedate.getLineId());
            if (line.getLineStatus() != LineStatus.show || line.getStatus() != ProductStatus.UP) {
                lineService.delLabelItems(line);
            }
//			lineService.doIndexLine(line);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
