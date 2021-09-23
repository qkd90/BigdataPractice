package com.data.data.hmly.service.line;

import com.data.data.hmly.service.line.dao.LineDepartureDao;
import com.data.data.hmly.service.line.entity.Line;
import com.data.data.hmly.service.line.entity.LineDeparture;
import com.data.data.hmly.service.line.entity.LineDepartureInfo;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * Created by dy on 2016/6/21.
 */

@Service
public class LineDepartureService {

    @Resource
    private LineDepartureDao lineDepartureDao;
    @Resource
    private LineDepartureInfoService lineDepartureInfoService;

    public void saveDeparture(LineDeparture lineDeparture) {
        lineDeparture.setUpdateTime(new Date());
        lineDeparture.setCreateTime(new Date());
        lineDepartureDao.save(lineDeparture);
    }

    public void updateDeparture(LineDeparture lineDeparture) {
        lineDeparture.setUpdateTime(new Date());
        lineDepartureDao.update(lineDeparture);
    }

    public LineDeparture loadDeparture(Long id) {
        return lineDepartureDao.load(id);
    }

    public LineDeparture getDepartureByLine(Line line) {
        Criteria<LineDeparture> criteria = new Criteria<LineDeparture>(LineDeparture.class);
        criteria.eq("line.id", line.getId());
        List<LineDeparture> lineDepartureList = lineDepartureDao.findByCriteria(criteria);
        if (!lineDepartureList.isEmpty()) {
            return lineDepartureList.get(0);
        }
        return null;
    }

    public LineDeparture getDepartureWithInfoByLine(Line line) {
        LineDeparture lineDeparture = getDepartureByLine(line);
        if (lineDeparture != null) {
            LineDepartureInfo search = new LineDepartureInfo();
            search.setDeparture(lineDeparture);
            lineDeparture.setLineDepartureInfos(lineDepartureInfoService.getDepartureList(search));
        }
        return lineDeparture;
    }

    public void delDepartureInfo(LineDeparture lineDeparture) {
        lineDepartureDao.delete(lineDeparture);
    }
}
