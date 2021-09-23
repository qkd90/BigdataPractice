package com.data.data.hmly.service.line;

import com.data.data.hmly.service.line.dao.LineDepartureDao;
import com.data.data.hmly.service.line.dao.LineDepartureInfoDao;
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
public class LineDepartureInfoService {

    @Resource
    private LineDepartureDao lineDepartureDao;
    @Resource
    private LineDepartureInfoDao lineDepartureInfoDao;

    public void saveDepartureInfo(LineDepartureInfo lineDepartureInfo) {
        lineDepartureInfo.setCreateTime(new Date());
        lineDepartureInfo.setUpdateTime(new Date());
        lineDepartureInfoDao.save(lineDepartureInfo);
    }

    public List<LineDepartureInfo> getDepartureList(LineDepartureInfo lineDepartureInfo) {

        Criteria<LineDepartureInfo> criteria = new Criteria<LineDepartureInfo>(LineDepartureInfo.class);

        if (lineDepartureInfo.getDeparture().getId() != null) {
            criteria.eq("departure.id", lineDepartureInfo.getDeparture().getId());
        }
        return lineDepartureInfoDao.findByCriteria(criteria);

    }

    public void delDepartureInfo(LineDepartureInfo departureInfo) {
        lineDepartureInfoDao.delete(departureInfo);
    }
}
