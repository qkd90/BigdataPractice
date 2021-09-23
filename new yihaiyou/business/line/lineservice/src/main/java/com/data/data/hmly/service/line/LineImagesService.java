package com.data.data.hmly.service.line;

import com.data.data.hmly.service.line.dao.LineImagesDao;
import com.data.data.hmly.service.line.entity.LineImages;
import com.data.data.hmly.service.line.entity.enums.LineImageType;
import com.framework.hibernate.util.Criteria;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by dy on 2016/6/23.
 */
@Service
public class LineImagesService {


    @Resource
    private LineImagesDao lineImagesDao;

    public void delByLineId(Long lineId, LineImageType lineImageType) {
        String hql = " delete LineImages where lineId = ? and lineImageType = ?";
        lineImagesDao.updateByHQL(hql, lineId, lineImageType);
    }

    public void saveAll(List<LineImages> daysPlanInfoImageses) {
        lineImagesDao.save(daysPlanInfoImageses);
    }

    public void delete(Long id) {
        lineImagesDao.delete(id, LineImages.class);
    }

    public void save(LineImages daysPlanInfoImages) {
        lineImagesDao.save(daysPlanInfoImages);
    }

    public List<LineImages> getDaysInfoImagesByTimeInfoId(Long timeInfoId, LineImageType lineImageType) {

        Criteria<LineImages> criteria = new Criteria<LineImages>(LineImages.class);

        if (timeInfoId != null) {
            criteria.eq("lineDaysPlanInfo.id", timeInfoId);
        }

        if (lineImageType != null) {
            criteria.eq("lineImageType", lineImageType);
        }
        return lineImagesDao.findByCriteria(criteria);
    }

    public List<LineImages> listByLineDaysPlanId(Long lineDaysPlanId, LineImageType lineImageType) {
        Criteria<LineImages> criteria = new Criteria<LineImages>(LineImages.class);
        criteria.eq("linedaysplan.id", lineDaysPlanId);
        if (lineImageType != null) {
            criteria.eq("lineImageType", lineImageType);
        }
        return lineImagesDao.findByCriteria(criteria);
    }

    public List<LineImages> listImagesByLineId(long lineId, LineImageType lineImageType) {

        Criteria<LineImages> criteria = new Criteria<LineImages>(LineImages.class);
        criteria.eq("lineId", lineId);
        if (lineImageType != null) {
            criteria.eq("lineImageType", lineImageType);
        }
        return lineImagesDao.findByCriteria(criteria);
    }
}
